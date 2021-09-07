package net.iyiguo.simplepoker;

import com.google.common.collect.Lists;
import net.iyiguo.simplepoker.dao.PokerDao;
import net.iyiguo.simplepoker.entity.Poker;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SimplePokerApplication implements ApplicationRunner {

    private final PokerDao pokerDao;

    public SimplePokerApplication(PokerDao pokerDao) {
        this.pokerDao = pokerDao;
    }

    public static void main(String[] args) {
        SpringApplication.run(SimplePokerApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Poker> pokers = Lists.newArrayList(pokerDao.findAll());
        System.out.println(pokers);
    }
}
