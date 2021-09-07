package net.iyiguo.simplepoker.service.dto;

import com.google.common.base.MoreObjects;
import net.iyiguo.simplepoker.entity.Poker;
import net.iyiguo.simplepoker.enums.PokerRoleEnum;

/**
 * @author leeyee
 * @date 2021/08/08
 */
public class PokerDto {
    private Long id;
    private String name;
    private PokerRoleEnum role;

    private PokerVoteDto vote;

    public PokerDto() {
    }

    public PokerDto(Long id, String name, PokerRoleEnum role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public static PokerDto from(Poker poker) {
        return new PokerDto(poker.getId(), poker.getName(), poker.getRole());
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

    public PokerVoteDto getVote() {
        return vote;
    }

    public void setVote(PokerVoteDto vote) {
        this.vote = vote;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("id", id)
                .add("name", name)
                .add("role", role)
                .add("vote", vote)
                .toString();
    }
}
