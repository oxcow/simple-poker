package net.iyiguo.simplepoker.config;

import com.google.common.collect.Lists;
import net.iyiguo.simplepoker.entity.Poker;
import net.iyiguo.simplepoker.entity.Room;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;


@Profile("local")
@Component
@ConfigurationProperties("poker-cache")
public class PokerCacheProperties {

    private final List<Room> rooms = Lists.newArrayList();

    private final List<Poker> pokers = Lists.newArrayList();

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Poker> getPokers() {
        return pokers;
    }
}
