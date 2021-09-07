package net.iyiguo.simplepoker.web;

import net.iyiguo.simplepoker.model.PokerEvent;
import net.iyiguo.simplepoker.service.PokerActionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author leeyee
 * @date 2021/08/08
 */
@RestController
@RequestMapping("/api/pokers")
public class PokerRestController {

    private PokerActionHandler pokerActionHandler;

    public PokerRestController(PokerActionHandler pokerActionHandler) {
        this.pokerActionHandler = pokerActionHandler;
    }

    @PostMapping("/{pokerId}/cmd")
    public boolean command(@PathVariable("pokerId") Long pokerId, @RequestBody PokerEvent pokerEvent) {
        pokerActionHandler.commandAction(pokerEvent);
        return true;
    }

    @PostMapping("/{pokerId}/vote")
    public boolean vote(@PathVariable("pokerId") Long pokerId,
                        @RequestParam("roomNo") Long roomNo,
                        @RequestParam(name = "vote", required = false) Integer votes) {
        pokerActionHandler.voteAction(pokerId, roomNo, votes);
        return true;
    }
}
