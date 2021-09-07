package net.iyiguo.simplepoker.dao;

import net.iyiguo.simplepoker.entity.PokerVote;

import java.util.List;

/**
 * @author leeyee
 * @date 2021/08/08
 */
public interface PokerVoteDao {
    List<PokerVote> findByRoomId(Long roomId);

    void save(PokerVote pokerVote);

    void delByRoomId(Long roomId);

    void delByRoomIdAndPokerId(Long roomId, Long pokerId);
}
