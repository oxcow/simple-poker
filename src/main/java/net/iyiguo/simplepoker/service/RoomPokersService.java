package net.iyiguo.simplepoker.service;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;
import net.iyiguo.simplepoker.service.dto.PokerDto;
import net.iyiguo.simplepoker.service.dto.PokerVoteDto;
import net.iyiguo.simplepoker.service.dto.RoomDto;
import net.iyiguo.simplepoker.service.dto.RoomPokersDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author leeyee
 * @date 2021/08/08
 */
@Service
public class RoomPokersService {
    private SetMultimap<Long, PokerDto> roomPokersCache = HashMultimap.create();

    private final RoomService roomService;
    private final PokerService pokerService;
    private final PokerVotesService pokerVotesService;

    public RoomPokersService(RoomService roomService, PokerService pokerService, PokerVotesService pokerVotesService) {
        this.roomService = roomService;
        this.pokerService = pokerService;
        this.pokerVotesService = pokerVotesService;
    }

    public Set<PokerDto> getPokers(Long roomNo) {
        return roomPokersCache.get(roomNo);
    }

    public boolean isInRoom(Long pokerId, Long roomNo) {
        Set<PokerDto> pokers = roomPokersCache.get(roomNo);
        return pokers.stream().anyMatch(poker -> pokerId.equals(poker.getId()));
    }

    public RoomPokersDto enterRoom(Long pokerId, Long roomNo) {
        RoomPokersDto roomPokersDto = new RoomPokersDto();
        Optional<RoomDto> roomOptional = roomService.getRoomByNo(roomNo);
        Optional<PokerDto> pokerOptional = pokerService.getPokerById(pokerId);
        if (roomOptional.isPresent() && pokerOptional.isPresent()) {

            if (!isInRoom(pokerId, roomNo)) {
                roomPokersCache.put(roomNo, pokerOptional.get());
            }

            roomPokersDto.setRoom(roomOptional.get());

            Map<Long, Integer> pokerVoteMap = pokerVotesService.getPokerVotes(roomNo);

            List<PokerDto> pokers = Lists.newArrayList();
            roomPokersCache.get(roomNo).forEach(poker -> {
                PokerDto pokerVo = new PokerDto(poker.getId(), poker.getName(), poker.getRole());

                if (pokerVoteMap.containsKey(poker.getId())) {
                    pokerVo.setVote(new PokerVoteDto(poker.getId(), pokerVoteMap.get(poker.getId())));
                }
                if (poker.getId().equals(pokerId)) {
                    roomPokersDto.setSelf(pokerVo);
                } else {
                    pokers.add(pokerVo);
                }
                roomPokersDto.setPokers(pokers);
            });
        }
        return roomPokersDto;
    }

    public boolean leaveRoom(Long pokerId, Long roomNo) {
        Set<PokerDto> pokers = roomPokersCache.get(roomNo);
        pokers.stream()
                .filter(obj -> obj.getId().equals(pokerId))
                .collect(Collectors.toSet())
                .forEach(pokers::remove);
        return true;
    }
}
