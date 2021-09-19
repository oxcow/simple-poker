package net.iyiguo.simplepoker.handler;

import net.iyiguo.simplepoker.enums.PokerActionEnum;
import net.iyiguo.simplepoker.model.EntityCreatedEvent;
import net.iyiguo.simplepoker.model.PokerEmitter;
import net.iyiguo.simplepoker.model.PokerEvent;
import net.iyiguo.simplepoker.service.SubscriberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.ServerResponse;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

/**
 * @author leeyee
 * @date 2021/9/18
 */
@Service
public class SubscriberHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriberHandler.class);

    private SubscriberService subscriberService;

    private ApplicationEventPublisher eventPublisher;

    public SubscriberHandler(SubscriberService subscriberService, ApplicationEventPublisher eventPublisher) {
        this.subscriberService = subscriberService;
        this.eventPublisher = eventPublisher;
    }


    public ServerResponse subscriber(Long pokerId, Long roomNo, String lastEventIdStr) {
        return ServerResponse.sse(sse -> {

            Long lastEventId = 0L;

            if (Objects.nonNull(lastEventIdStr)) {
                lastEventId = Long.valueOf(lastEventIdStr);
            }

            Optional<PokerEmitter> pokerEmitter = subscriberService.getSubscribers(roomNo, pokerId);

            if (!pokerEmitter.isPresent()) {
                PokerEmitter newPoker = new PokerEmitter(roomNo, pokerId, lastEventId, null);
                newPoker.setSseBuilder(sse);
                subscriberService.addSubscriber(newPoker);
            } else {
                if (Objects.nonNull(pokerEmitter.get().getSseBuilder())) {
                    pokerEmitter.get().getSseBuilder().complete();
                }
                pokerEmitter.get().setSseBuilder(sse);
            }

            LOGGER.info("Poker#{}进入房间#{}. 当前房间人数为:{}", pokerId, roomNo, subscriberService.getNumOfSubscribers(roomNo));

            eventPublisher.publishEvent(new EntityCreatedEvent<>(new PokerEvent(roomNo, pokerId, PokerActionEnum.ONLINE)));
        }, Duration.ofMillis(-1L));
    }

    public ServerResponse unsubscribe(Long pokerId, Long roomNo) {
        return ServerResponse.ok().body(subscriberService.unsubscribe(pokerId, roomNo));
    }

}