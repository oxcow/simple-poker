package net.iyiguo.simplepoker.service;

import net.iyiguo.simplepoker.enums.PokerActionEnum;
import net.iyiguo.simplepoker.model.EntityCreatedEvent;
import net.iyiguo.simplepoker.model.PokerEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author leeyee
 * @date 2021/08/08
 */
@Service
public class PokerActionHandler {

    private PokerVotesService pokerVotesService;
    private ApplicationEventPublisher eventPublisher;

    public PokerActionHandler(PokerVotesService pokerVotesService, ApplicationEventPublisher eventPublisher) {
        this.pokerVotesService = pokerVotesService;
        this.eventPublisher = eventPublisher;
    }

    /**
     * 接收不需要数据改变的动作
     *
     * @param event
     */
    public void commandAction(PokerEvent event) {
        eventPublisher.publishEvent(new EntityCreatedEvent<>(event));
    }

    /**
     * Poker 投票
     *
     * @param pokerId
     * @param roomNo
     * @param votes
     */
    public void voteAction(Long pokerId, Long roomNo, Integer votes) {
        PokerActionEnum actionEnum = PokerActionEnum.VOTE;
        if (Objects.nonNull(votes)) {
            pokerVotesService.votes(pokerId, votes, roomNo);
        } else {
            actionEnum = PokerActionEnum.PASS;
            pokerVotesService.cleanVote(pokerId, roomNo);
        }
        eventPublisher.publishEvent(new EntityCreatedEvent<>(new PokerEvent(roomNo, pokerId, actionEnum)));
    }

}
