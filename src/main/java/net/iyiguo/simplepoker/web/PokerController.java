package net.iyiguo.simplepoker.web;

import net.iyiguo.simplepoker.entity.Poker;
import net.iyiguo.simplepoker.service.PokerService;
import net.iyiguo.simplepoker.service.RoomPokersService;
import net.iyiguo.simplepoker.service.dto.PokerDto;
import net.iyiguo.simplepoker.service.dto.RoomPokersDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

/**
 * @author leeyee
 * @date 2021/08/08
 */
@Controller
@RequestMapping("/pokers")
public class PokerController {

    private final PokerService pokerService;
    private final RoomPokersService roomPokersService;

    public PokerController(PokerService pokerService, RoomPokersService roomPokersService) {
        this.pokerService = pokerService;
        this.roomPokersService = roomPokersService;
    }

    @GetMapping("/{pokerId}/room/{roomNo}")
    public String enterRoom(@PathVariable("pokerId") Long pokeId, @PathVariable("roomNo") Long roomNo, Model model) {
        RoomPokersDto roomPokersDto = roomPokersService.enterRoom(pokeId, roomNo);
        model.addAttribute("roomInfo", roomPokersDto);
        return "room";
    }

    @PostMapping("/room/{roomNo}")
    public String joinRoom(@PathVariable("roomNo") Long roomNo, @RequestParam("name") String name) {
        Optional<PokerDto> pokerOpt = pokerService.getPokerByName(name);
        if (pokerOpt.isPresent()) {
            return String.format("redirect:/pokers/%d/room/%d", pokerOpt.get().getId(), roomNo);
        }
        Poker poker = pokerService.savePoker(name);
        return String.format("redirect:/pokers/%d/room/%d", poker.getId(), roomNo);
    }

}
