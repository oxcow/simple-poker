package net.iyiguo.simplepoker.service;

import com.google.common.collect.Sets;
import net.iyiguo.simplepoker.enums.PokerActionEnum;
import net.iyiguo.simplepoker.model.EntityCreatedEvent;
import net.iyiguo.simplepoker.model.PokerEmitter;
import net.iyiguo.simplepoker.model.PokerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author leeyee
 * @date 2021/08/08
 */
@Service
public class SubscriberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriberService.class);

    private Set<PokerEmitter> subscribers = Sets.newConcurrentHashSet();

    private ApplicationEventPublisher eventPublisher;

    public SubscriberService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public Set<PokerEmitter> getSubscribers(Long roomNo) {
        return subscribers.stream().filter(obj -> obj.getRoomNo().equals(roomNo)).collect(Collectors.toSet());
    }

    public Optional<PokerEmitter> getSubscribers(Long roomNo, Long pokerId) {
        return subscribers.stream()
                .filter(obj -> obj.getRoomNo().equals(roomNo) && obj.getPokerId().equals(pokerId))
                .findFirst();
    }

    public Optional<PokerEmitter> subscribe(Long pokerId, Long roomNo, Long lastEventId) {

        Optional<PokerEmitter> subscriber = this.getSubscribers(roomNo, pokerId);

        if (subscriber.isPresent()) {
            /*
             始终只保留最后一个订阅。
             当前订阅用户是否重新新开页面或者浏览器登录
             如果是同一个用户的不同浏览器页面，那么当前新开页面接收消息ID与之前的相同
             sseEmitter = subscriber.get().getEmitter();
             lastEventId = subscriber.get().getLastEventId();
            */
        } else {
            PokerEmitter newPoker = new PokerEmitter(roomNo, pokerId, lastEventId, new SseEmitter(-1L));
            subscribers.add(newPoker);
            subscriber = Optional.of(newPoker);
        }

        LOGGER.info("Poker#{}进入房间#{}. 当前房间人数为:{}", pokerId, roomNo, getNumOfSubscribers(roomNo));

        eventPublisher.publishEvent(new EntityCreatedEvent<>(new PokerEvent(roomNo, pokerId, PokerActionEnum.ONLINE)));

        return subscriber;
    }

    public boolean unsubscribe(Long pokerId, Long roomNo) {
        subscribers.stream()
                .filter(obj -> obj.getRoomNo().equals(roomNo) && obj.getPokerId().equals(pokerId))
                .forEach(subscribers::remove);
        eventPublisher.publishEvent(new EntityCreatedEvent<>(new PokerEvent(roomNo, pokerId, PokerActionEnum.OFFLINE)));
        LOGGER.info("Poker#{}离开房间#{}. 当前房间人数为:{}", pokerId, roomNo, getNumOfSubscribers(roomNo));
        return true;
    }

    private long getNumOfSubscribers(Long roomNo) {
        return subscribers.stream().filter(obj -> obj.getRoomNo().equals(roomNo)).count();
    }

    @Scheduled(cron = "5/15 * * * * ?")
    private void heart() {
        Set<PokerEmitter> offlinePokers = Sets.newHashSet();
        subscribers.forEach(subscriber -> {
            SseEmitter emitter = subscriber.getEmitter();
            if (Objects.nonNull(emitter)) {
                try {
                    emitter.send(SseEmitter.event().comment("heart"));
                } catch (IOException e) {
                    LOGGER.info("和Poker#{} 失去连接. 房间Room#{}", subscriber.getPokerId(), subscriber.getRoomNo());
                    emitter.completeWithError(e);
                    offlinePokers.add(subscriber);
                }
            }
        });
        offlinePokers.forEach(subscriber -> this.unsubscribe(subscriber.getPokerId(), subscriber.getRoomNo()));
    }
}
