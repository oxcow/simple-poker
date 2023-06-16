package net.iyiguo.simplepoker.dao.impl;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import net.iyiguo.simplepoker.dao.PokerVoteDao;
import net.iyiguo.simplepoker.entity.PokerVote;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;

/**
 * @author leeyee
 * @date 2021/08/08
 */
@Repository
//@Profile("local")
public class InMemoryPokerVoteDaoImpl implements PokerVoteDao {
    /**
     * Room:Poker:Votes
     */
    private Table<Long, Long, Integer> pokerVotesCache;

    @PostConstruct
    public void init() {
        pokerVotesCache = HashBasedTable.create();
    }

    @PreDestroy
    public void destroy() {
        pokerVotesCache = null;
    }

    @Override
    public List<PokerVote> findByRoomId(Long roomId) {
        List<PokerVote> pokerVotes = Lists.newArrayList();
        Map<Long, Integer> pokerVoteCols = pokerVotesCache.row(roomId);
        for (Map.Entry<Long, Integer> pv : pokerVoteCols.entrySet()) {
            pokerVotes.add(new PokerVote(roomId, pv.getKey(), pv.getValue()));
        }
        return pokerVotes;
    }

    @Override
    public void delByRoomId(Long roomId) {
        Map<Long, Integer> pokerVoteCols = Maps.newHashMap(pokerVotesCache.row(roomId));
        for (Long aLong : pokerVoteCols.keySet()) {
            pokerVotesCache.remove(roomId, aLong);
        }
    }

    @Override
    public void delByRoomIdAndPokerId(Long roomId, Long pokerId) {
        pokerVotesCache.remove(roomId, pokerId);
    }

    @Override
    public void save(PokerVote pokerVote) {
        pokerVotesCache.put(pokerVote.getRoomId(), pokerVote.getPorkId(), pokerVote.getVotes());
    }
}
