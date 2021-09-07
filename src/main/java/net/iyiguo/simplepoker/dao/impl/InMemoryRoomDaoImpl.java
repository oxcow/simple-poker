package net.iyiguo.simplepoker.dao.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.iyiguo.simplepoker.config.PokerCacheProperties;
import net.iyiguo.simplepoker.dao.RoomDao;
import net.iyiguo.simplepoker.entity.Room;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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
public class InMemoryRoomDaoImpl implements RoomDao {

    private Map<Long, Room> roomsCache;
    private AtomicLong idGenerator;

    private PokerCacheProperties pokerCacheProperties;

    public InMemoryRoomDaoImpl(PokerCacheProperties pokerCacheProperties) {
        this.pokerCacheProperties = pokerCacheProperties;
    }

    @PostConstruct
    public void init() {
        idGenerator = new AtomicLong(0L);
        roomsCache = Maps.newConcurrentMap();
        pokerCacheProperties.getRooms().forEach(room -> save(room));
    }

    @PreDestroy
    public void destroy() {
        roomsCache = null;
        idGenerator = null;
    }

    @Override
    public <S extends Room> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Room> findById(Long id) {
        return Optional.ofNullable(roomsCache.get(id));
    }

    @Override
    public boolean existsById(Long id) {
        return roomsCache.containsKey(id);
    }

    @Override
    public List<Room> findAll() {
        return Lists.newArrayList(roomsCache.values());
    }

    @Override
    public Iterable<Room> findAllById(Iterable<Long> ids) {
        List<Room> rooms = Lists.newArrayList();
        ids.forEach(id -> rooms.add(roomsCache.get(id)));
        return rooms;
    }

    @Override
    public long count() {
        return roomsCache.size();
    }

    @Override
    public void deleteById(Long id) {
        roomsCache.remove(id);
    }

    @Override
    public void delete(Room entity) {
        roomsCache.remove(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
        ids.forEach(id -> deleteById(id));
    }

    @Override
    public void deleteAll(Iterable<? extends Room> entities) {
        entities.forEach(entity -> roomsCache.remove(entities));
    }

    @Override
    public void deleteAll() {
        roomsCache.clear();
    }

    @Override
    public Room save(Room room) {
        Long id = idGenerator.addAndGet(1L);
        room.setId(id);
        roomsCache.put(id, room);
        return room;
    }


}
