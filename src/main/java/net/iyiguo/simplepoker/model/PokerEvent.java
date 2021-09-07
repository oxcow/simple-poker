package net.iyiguo.simplepoker.model;

import net.iyiguo.simplepoker.enums.PokerActionEnum;

/**
 * @author leeyee
 * @date 2021/08/08
 */
public class PokerEvent {
    private Long roomNo;
    private Long pokerId;
    private PokerActionEnum action;

    public PokerEvent() {
    }

    public PokerEvent(Long roomNo, Long pokerId, PokerActionEnum action) {
        this.roomNo = roomNo;
        this.pokerId = pokerId;
        this.action = action;
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

    public PokerActionEnum getAction() {
        return action;
    }

    public void setAction(PokerActionEnum action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "PokerEvent{" +
                "roomNo=" + roomNo +
                ", pokerId=" + pokerId +
                ", action=" + action +
                '}';
    }
}
