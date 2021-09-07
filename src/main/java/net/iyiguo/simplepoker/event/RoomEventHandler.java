package net.iyiguo.simplepoker.event;

import net.iyiguo.simplepoker.service.PokerService;
import net.iyiguo.simplepoker.service.PokerVotesService;
import net.iyiguo.simplepoker.service.RoomPokersService;
import net.iyiguo.simplepoker.service.dto.PokerDto;
import net.iyiguo.simplepoker.service.dto.PokerVoteDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author leeyee
 * @date 2021/08/14
 */
@Service
public class RoomEventHandler {

    private final PokerService pokerService;
    private final PokerVotesService pokerVotesService;
    private final RoomPokersService roomPokersService;

    public RoomEventHandler(PokerService pokerService, PokerVotesService pokerVotesService, RoomPokersService roomPokersService) {
        this.pokerService = pokerService;
        this.pokerVotesService = pokerVotesService;
        this.roomPokersService = roomPokersService;
    }

    public Optional<PokerDto> onlineHandler(Long pokerId) {
        return pokerService.getPokerById(pokerId);
    }

    public boolean offlineHandler(Long pokerId, Long roomNo) {
        pokerVotesService.cleanVote(pokerId, roomNo);
        return roomPokersService.leaveRoom(pokerId, roomNo);
    }

    public Optional<List<PokerVoteDto>> flopHandler(Long roomNo) {
        List<PokerVoteDto> votes = pokerVotesService.findAllVote(roomNo);
        return Objects.isNull(votes) || votes.isEmpty() ? Optional.empty() : Optional.of(votes);
    }

    public boolean shuffleHandler(Long roomNo) {
        pokerVotesService.cleanAllVote(roomNo);
        return true;
    }
}
