package net.iyiguo.simplepoker.service;

import com.google.common.collect.Lists;
import net.iyiguo.simplepoker.dao.PokerDao;
import net.iyiguo.simplepoker.entity.Poker;
import net.iyiguo.simplepoker.enums.PokerRoleEnum;
import net.iyiguo.simplepoker.service.dto.PokerDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author leeyee
 * @date 2021/08/08
 */
@Service
public class PokerService {

    private final PokerDao pokerDao;

    public PokerService(PokerDao pokerDao) {
        this.pokerDao = pokerDao;
    }

    public Optional<PokerDto> getPokerById(Long pokerId) {
        return pokerDao.findById(pokerId).map(PokerDto::from);
    }

    public Optional<PokerDto> getPokerByName(String name) {
        return findAllPokers().stream()
                .filter(poker -> name.equalsIgnoreCase(poker.getName()))
                .findFirst();
    }

    public List<PokerDto> findAllPokers() {
        return Lists.newArrayList(pokerDao.findAll()).stream()
                .map(poker -> new PokerDto(poker.getId(), poker.getName(), poker.getRole()))
                .collect(Collectors.toList());
    }

    public Poker savePoker(String name) {
        Poker poker = new Poker();
        poker.setName(name);
        poker.setRole(PokerRoleEnum.POKER);
        return pokerDao.save(poker);
    }
}
