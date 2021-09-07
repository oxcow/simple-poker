package net.iyiguo.simplepoker.service;

import net.iyiguo.simplepoker.dao.PokerVoteDao;
import net.iyiguo.simplepoker.entity.PokerVote;
import net.iyiguo.simplepoker.service.dto.PokerVoteDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author leeyee
 * @date 2021/08/08
 */
@Service
public class PokerVotesService {

    private PokerVoteDao pokerVoteDao;

    public PokerVotesService(PokerVoteDao pokerVoteDao) {
        this.pokerVoteDao = pokerVoteDao;
    }

    public List<PokerVoteDto> findAllVote(Long roomNo) {
        return pokerVoteDao.findByRoomId(roomNo).stream()
                .map(PokerVoteDto::from)
                .collect(Collectors.toList());
    }

    public Map<Long, Integer> getPokerVotes(Long roomNo) {
        List<PokerVoteDto> pokerVotes = this.findAllVote(roomNo);
        return pokerVotes.stream().collect(Collectors.toMap(PokerVoteDto::getPokerId, PokerVoteDto::getVotes));
    }

    public void votes(Long pokerId, Integer votes, Long roomNo) {
        PokerVote pokerVote = new PokerVote(roomNo, pokerId, votes);
        pokerVoteDao.save(pokerVote);
    }

    public void cleanVote(Long pokerId, Long roomNo) {
        pokerVoteDao.delByRoomIdAndPokerId(roomNo, pokerId);
    }

    public void cleanAllVote(Long roomNo) {
        pokerVoteDao.delByRoomId(roomNo);
    }

}
