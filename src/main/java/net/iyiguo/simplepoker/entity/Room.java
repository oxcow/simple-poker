package net.iyiguo.simplepoker.entity;

import net.iyiguo.simplepoker.enums.RoomTypeEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author leeyee
 * @date 2021/08/08
 */
@Table("rooms")
public class Room {
    @Id
    private Long id;
    private String name;
    @Transient
    private transient String pass;
    private RoomTypeEnum type;

    @Column("owner")
    private Long ownerId;

    @MappedCollection(idColumn = "id")
    private Poker owner;

    public Room() {
    }

    public Room(String name, RoomTypeEnum type) {
        this.name = name;
        this.type = type;
    }

    public Room(Long id, String name, RoomTypeEnum type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public RoomTypeEnum getType() {
        return type;
    }

    public void setType(RoomTypeEnum type) {
        this.type = type;
    }

    public Poker getOwner() {
        return owner;
    }

    public void setOwner(Poker owner) {
        this.owner = owner;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
