package net.iyiguo.simplepoker.web;

import net.iyiguo.simplepoker.model.PokerEmitter;
import net.iyiguo.simplepoker.service.SubscriberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Optional;
import javax.servlet.http.HttpServletResponse;

/**
 * @author leeyee
 * @date 2021/08/08
 */
@Controller
@RequestMapping("/api/subscriber")
public class SubscriberController {

    private SubscriberService subscriberService;

    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @GetMapping("/{pokerId}/subscribe/{roomNo}")
    public SseEmitter subscribe(@PathVariable("pokerId") Long pokerId,
                                @PathVariable("roomNo") Long roomNo,
                                @RequestHeader(value = "Last-Event-ID", defaultValue = "0") Long lastEventId,
                                HttpServletResponse response) {
        response.setHeader("X-Accel-Buffering", "no");
        Optional<PokerEmitter> pokerEmitter = subscriberService.subscribe(pokerId, roomNo, lastEventId);
        return pokerEmitter.orElseThrow(() -> new RuntimeException("subscribe exception.")).getEmitter();
    }

    @PostMapping("/{pokerId}/unsubscribe/{roomNo}")
    @ResponseBody
    public boolean unsubscribe(@PathVariable("pokerId") Long pokerId, @PathVariable("roomNo") Long roomNo) {
        return subscriberService.unsubscribe(pokerId, roomNo);
    }
}
