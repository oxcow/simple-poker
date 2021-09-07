package net.iyiguo.simplepoker.dao.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.iyiguo.simplepoker.config.PokerCacheProperties;
import net.iyiguo.simplepoker.dao.PokerDao;
import net.iyiguo.simplepoker.entity.Poker;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author leeyee
 * @date 2021/08/08
 */
@Repository
@Profile("local")
public class InMemoryPokerDaoImpl implements PokerDao {
    private Map<Long, Poker> pokersCache;
    private AtomicLong idGenerator;

    private PokerCacheProperties pokerCacheProperties;

    public InMemoryPokerDaoImpl(PokerCacheProperties pokerCacheProperties) {
        this.pokerCacheProperties = pokerCacheProperties;
    }

    @PostConstruct
    public void init() {
        idGenerator = new AtomicLong(0L);
        pokersCache = Maps.newConcurrentMap();
        pokerCacheProperties.getPokers().forEach(poker -> save(poker));
    }

    @PreDestroy
    public void destroy() {
        pokersCache = null;
        idGenerator = null;
    }

    @Override
    public <S extends Poker> Iterable<S> saveAll(Iterable<S> entities) {
        entities.forEach(entity -> this.save(entity));
        return entities;
    }

    @Override
    public Optional<Poker> findById(Long id) {
        return Optional.ofNullable(pokersCache.get(id));
    }

    @Override
    public boolean existsById(Long id) {
        return pokersCache.containsKey(id);
    }

    @Override
    public List<Poker> findAll() {
        return Lists.newArrayList(pokersCache.values());
    }

    @Override
    public Iterable<Poker> findAllById(Iterable<Long> ids) {
        List<Poker> list = new ArrayList<>();
        ids.forEach(id -> list.add(pokersCache.get(id)));
        return list;
    }

    @Override
    public long count() {
        return pokersCache.size();
    }

    @Override
    public void deleteById(Long id) {
        pokersCache.remove(id);
    }

    @Override
    public void delete(Poker entity) {
        pokersCache.remove(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
        for (Long id : ids) {
            pokersCache.remove(id);
        }
    }

    @Override
    public void deleteAll(Iterable<? extends Poker> entities) {
        for (Poker entity : entities) {
            pokersCache.remove(entity.getId());
        }
    }

    @Override
    public void deleteAll() {
        pokersCache.clear();
    }

    @Override
    public Poker save(Poker poker) {
        Long id = idGenerator.addAndGet(1L);
        poker.setId(id);
        pokersCache.put(id, poker);
        return poker;
    }
}
