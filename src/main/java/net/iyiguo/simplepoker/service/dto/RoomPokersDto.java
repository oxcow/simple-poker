package net.iyiguo.simplepoker.service.dto;

import java.util.List;

/**
 * @author leeyee
 * @date 2021/08/08
 */
public class RoomPokersDto {
    private RoomDto room;
    private PokerDto self;
    private List<PokerDto> pokers;

    public RoomDto getRoom() {
        return room;
    }

    public void setRoom(RoomDto room) {
        this.room = room;
    }

    public PokerDto getSelf() {
        return self;
    }

    public void setSelf(PokerDto self) {
        this.self = self;
    }

    public List<PokerDto> getPokers() {
        return pokers;
    }

    public void setPokers(List<PokerDto> pokers) {
        this.pokers = pokers;
    }
}
