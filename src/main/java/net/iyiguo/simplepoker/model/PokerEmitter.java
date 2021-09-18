package net.iyiguo.simplepoker.model;

import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @author leeyee
 * @date 2021/08/08
 */
public class PokerEmitter {
    private Long roomNo;
    private Long pokerId;
    private long lastEventId;
    private SseEmitter emitter;
    private ServerResponse.SseBuilder sseBuilder;

    public PokerEmitter() {
    }

    public PokerEmitter(Long roomNo, Long pokerId, long lastEventId, SseEmitter emitter) {
        this.roomNo = roomNo;
        this.pokerId = pokerId;
        this.lastEventId = lastEventId;
        this.emitter = emitter;
    }

    public Long getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(Long roomNo) {
        this.roomNo = roomNo;
    }

    public Long getPokerId() {
        return pokerId;
    }

    public void setPokerId(Long pokerId) {
        this.pokerId = pokerId;
    }

    public long getLastEventId() {
        return lastEventId;
    }

    public void setLastEventId(long lastEventId) {
        this.lastEventId = lastEventId;
    }

    public SseEmitter getEmitter() {
        return emitter;
    }

    public void setEmitter(SseEmitter emitter) {
        this.emitter = emitter;
    }

    public ServerResponse.SseBuilder getSseBuilder() {
        return sseBuilder;
    }

    public void setSseBuilder(ServerResponse.SseBuilder sseBuilder) {
        this.sseBuilder = sseBuilder;
    }
}
