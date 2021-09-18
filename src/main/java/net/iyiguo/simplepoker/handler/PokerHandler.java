package net.iyiguo.simplepoker.handler;

import net.iyiguo.simplepoker.model.PokerEvent;
import net.iyiguo.simplepoker.service.PokerActionHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.Optional;

/**
 * @author leeyee
 * @date 2021/9/16
 */
@Service
public class PokerHandler {
    private PokerActionHandler pokerActionHandler;

    public PokerHandler(PokerActionHandler pokerActionHandler) {
        this.pokerActionHandler = pokerActionHandler;
    }

    public ServerResponse command(ServerRequest request) throws Exception {
        PokerEvent pokerEvent = request.body(PokerEvent.class);
        pokerActionHandler.commandAction(pokerEvent);
        return ServerResponse.ok().body(true);
    }

    public ServerResponse vote(ServerRequest request) throws Exception {
        Long pokerId = Long.valueOf(request.pathVariable("pokerId"));
        Long roomNo = Long.valueOf(request.param("roomNo").orElse("-1"));
        Integer votes = null;
        Optional<String> voteStr = request.param("vote");
        if (voteStr.isPresent()) {
            votes = Integer.valueOf(voteStr.get());
        }
        pokerActionHandler.voteAction(pokerId, roomNo, votes);
        return ServerResponse.ok().body(true);
    }
}
