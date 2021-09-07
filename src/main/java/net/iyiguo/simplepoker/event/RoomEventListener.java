package net.iyiguo.simplepoker.event;

import net.iyiguo.simplepoker.model.EntityCreatedEvent;
import net.iyiguo.simplepoker.model.PokerEvent;
import net.iyiguo.simplepoker.model.PokerMessage;
import net.iyiguo.simplepoker.service.BroadcastService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author leeyee
 * @date 2021/08/08
 */
@Service
public class RoomEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomEventListener.class);

    private AtomicLong atomicLong = new AtomicLong(1);

    private final BroadcastService broadcastService;

    private final RoomEventHandler roomEventHandler;

    public RoomEventListener(BroadcastService broadcastService, RoomEventHandler roomEventHandler) {
        this.broadcastService = broadcastService;
        this.roomEventHandler = roomEventHandler;
    }

    @EventListener
    @Async
    public void onEventListener(EntityCreatedEvent<PokerEvent> cmdEvent) {
        PokerEvent event = (PokerEvent) cmdEvent.getSource();
        consoleAction(event);
        PokerMessage message = new PokerMessage(atomicLong.getAndIncrement(), event.getAction());
        switch (event.getAction()) {
            case ONLINE -> roomEventHandler.onlineHandler(event.getPokerId()).ifPresent(data -> message.setData(data));
            case OFFLINE -> {
                roomEventHandler.offlineHandler(event.getPokerId(), event.getRoomNo());
                message.setData(event);
            }
            case VOTE, PASS -> message.setData(event);
            case FLOP -> roomEventHandler.flopHandler(event.getRoomNo()).ifPresent(data -> message.setData(data));
            case SHUFFLE -> message.setData(roomEventHandler.shuffleHandler(event.getRoomNo()));
            default -> LOGGER.info("Do Nothing!!");
        }
        if (Objects.nonNull(message.getData())) {
            broadcastService.broadcast(event.getRoomNo(), message);
        }
    }

    private void consoleAction(PokerEvent event) {
        long pokerId = event.getPokerId();
        long roomNo = event.getRoomNo();
        switch (event.getAction()) {
            case ONLINE -> LOGGER.info("Poker#{} 进入了 Room#{}", pokerId, roomNo);
            case OFFLINE -> LOGGER.info("Poker#{} 离开了 Room#{}", pokerId, roomNo);
            case VOTE -> LOGGER.info("Poker#{} 在 Room#{} 投票了", pokerId, roomNo);
            case PASS -> LOGGER.info("Poker#{} 在 Room#{} 取消投票了", pokerId, roomNo);
            case FLOP -> LOGGER.info("Poker#{} 在 Room#{} 翻牌了", pokerId, roomNo);
            case SHUFFLE -> LOGGER.info("Poker#{} 在 Room#{} 洗牌了", pokerId, roomNo);
            case SEND -> LOGGER.info("Poker#{} 在 Room#{} 发送了一条消息", pokerId, roomNo);
        }
    }
}
