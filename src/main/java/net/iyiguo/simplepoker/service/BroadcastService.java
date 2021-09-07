package net.iyiguo.simplepoker.service;

import net.iyiguo.simplepoker.model.PokerEmitter;
import net.iyiguo.simplepoker.model.PokerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Set;

/**
 * @author leeyee
 * @date 2021/08/08
 */
@Service
public class BroadcastService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BroadcastService.class);

    private final SubscriberService subscriberService;

    public BroadcastService(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    public void broadcast(Long roomNo, PokerMessage message) {
        Set<PokerEmitter> subscribers = subscriberService.getSubscribers(roomNo);
        subscribers.forEach(subscriber -> {
            SseEmitter emitter = subscriber.getEmitter();
            try {
                emitter.send(SseEmitter.event()
                        .id(String.valueOf(message.getId()))
                        .name(message.getType().name())
                        .reconnectTime(60 * 1000)
                        .data(message.getData(), MediaType.APPLICATION_JSON));
                LOGGER.info("发送广播消息{} 给 Poker#{}", message, subscriber.getPokerId());
            } catch (IOException e) {
                emitter.completeWithError(e);
            }
        });
    }
}
