package net.iyiguo.simplepoker.service.dto;

import com.google.common.base.MoreObjects;
import net.iyiguo.simplepoker.entity.PokerVote;

/**
 * @author leeyee
 * @date 2021/08/08
 */
public class PokerVoteDto {
    private Long pokerId;
    private Integer votes;

    public PokerVoteDto() {
    }

    public PokerVoteDto(Long pokerId, Integer votes) {
        this.pokerId = pokerId;
        this.votes = votes;
    }

    public static PokerVoteDto from(PokerVote pokerVote) {
        return new PokerVoteDto(pokerVote.getPorkId(), pokerVote.getVotes());
    }

    public Long getPokerId() {
        return pokerId;
    }

    public void setPokerId(Long pokerId) {
        this.pokerId = pokerId;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("pokerId", pokerId)
                .add("votes", votes)
                .toString();
    }
}
