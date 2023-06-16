package net.iyiguo.simplepoker.service;

import com.google.common.collect.Lists;
import net.iyiguo.simplepoker.dao.PokerDao;
import net.iyiguo.simplepoker.dao.RoomDao;
import net.iyiguo.simplepoker.entity.Poker;
import net.iyiguo.simplepoker.entity.Room;
import net.iyiguo.simplepoker.enums.PokerRoleEnum;
import net.iyiguo.simplepoker.service.dto.RoomDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author leeyee
 * @date 2021/08/08
 */
@Service
public class RoomService {

    private final RoomDao roomDao;
    private final PokerDao pokerDao;

    public RoomService(RoomDao roomDao, PokerDao pokerDao) {
        this.roomDao = roomDao;
        this.pokerDao = pokerDao;
    }

    public List<RoomDto> findAllRooms() {
        return Lists.newArrayList(roomDao.findAll()).stream()
                .map(RoomDto::from)
                .collect(Collectors.toList());
    }

    public Optional<RoomDto> getRoomByNo(Long roomNo) {
        return roomDao.findById(roomNo).map(RoomDto::from);
    }

    @Transactional(rollbackFor = Exception.class)
    public Room saveRoom(RoomDto roomDto) {
        Room room = new Room();
        room.setName(roomDto.getName());
        room.setType(roomDto.getType());
        if (Objects.nonNull(roomDto.getOwner())) {
            Poker poker = new Poker(roomDto.getOwner(), PokerRoleEnum.MASTER);
            poker = pokerDao.save(poker);
            room.setOwnerId(poker.getId());

        }
        return roomDao.save(room);
    }
}
