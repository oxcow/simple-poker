package net.iyiguo.simplepoker.dao;

import net.iyiguo.simplepoker.entity.Poker;
import org.springframework.data.repository.CrudRepository;

/**
 * @author leeyee
 * @date 2021/08/08
 */
public interface PokerDao extends CrudRepository<Poker, Long> {

}
