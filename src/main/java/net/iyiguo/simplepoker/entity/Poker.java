package net.iyiguo.simplepoker.entity;

import com.google.common.base.MoreObjects;
import net.iyiguo.simplepoker.enums.PokerRoleEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author leeyee
 * @date 2021/08/08
 */
@Table("pokers")
public class Poker {
    @Id
    private Long id;
    private String name;
    private PokerRoleEnum role;

    public Poker() {
    }

    public Poker(String name, PokerRoleEnum role) {
        this.name = name;
        this.role = role;
    }

    public Poker(Long id, String name, PokerRoleEnum role) {
        this.id = id;
        this.name = name;
        this.role = role;
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

    public PokerRoleEnum getRole() {
        return role;
    }

    public void setRole(PokerRoleEnum role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("role", role)
                .toString();
    }
}
