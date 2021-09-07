package net.iyiguo.simplepoker.model;

import net.iyiguo.simplepoker.enums.PokerActionEnum;

/**
 * @author leeyee
 * @date 2021/08/08
 */
public class PokerMessage {
    private Long id;
    private PokerActionEnum type;
    private Object data;

    public PokerMessage() {
    }

    public PokerMessage(Long id, PokerActionEnum type) {
        this.id = id;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PokerActionEnum getType() {
        return type;
    }

    public void setType(PokerActionEnum type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PokerMessage{" +
                "id=" + id +
                ", type=" + type +
                ", data='" + data + '\'' +
                '}';
    }
}
