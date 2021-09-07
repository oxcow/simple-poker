package net.iyiguo.simplepoker.service.dto;

import net.iyiguo.simplepoker.entity.Room;
import net.iyiguo.simplepoker.enums.RoomTypeEnum;

/**
 * @author leeyee
 * @date 2021/08/08
 */
public class RoomDto {
    private Long roomNo;
    private String name;
    private RoomTypeEnum type;
    private String desc;
    private String owner;

    public RoomDto() {
    }

    public RoomDto(Long roomNo, String name, RoomTypeEnum type) {
        this.roomNo = roomNo;
        this.name = name;
        this.type = type;
    }

    public static RoomDto from(Room room) {
        return new RoomDto(room.getId(), room.getName(), room.getType());
    }

    public Long getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(Long roomNo) {
        this.roomNo = roomNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoomTypeEnum getType() {
        return type;
    }

    public void setType(RoomTypeEnum type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
