package net.iyiguo.simplepoker.entity;

/**
 * @author leeyee
 * @date 2021/08/08
 */
public class PokerVote {
    private Long id;
    private Long roomId;
    private Long porkId;
    private Integer votes;

    public PokerVote() {
    }

    public PokerVote(Long roomId, Long porkId, Integer votes) {
        this.roomId = roomId;
        this.porkId = porkId;
        this.votes = votes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getPorkId() {
        return porkId;
    }

    public void setPorkId(Long porkId) {
        this.porkId = porkId;
    }


    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer vote) {
        this.votes = vote;
    }
}
