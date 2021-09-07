package net.iyiguo.simplepoker.web;

import net.iyiguo.simplepoker.entity.Room;
import net.iyiguo.simplepoker.enums.RoomTypeEnum;
import net.iyiguo.simplepoker.service.PokerService;
import net.iyiguo.simplepoker.service.RoomPokersService;
import net.iyiguo.simplepoker.service.RoomService;
import net.iyiguo.simplepoker.service.dto.RoomDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * @author leeyee
 * @date 2021/08/08
 */
@Controller
@RequestMapping("/rooms")
public class RoomController {
    private RoomService roomService;
    private PokerService pokerService;
    private RoomPokersService roomPokersService;

    public RoomController(RoomService roomService, PokerService pokerService, RoomPokersService roomPokersService) {
        this.roomService = roomService;
        this.pokerService = pokerService;
        this.roomPokersService = roomPokersService;
    }

    @GetMapping
    public String rooms(Model model) {
        model.addAttribute("rooms", roomService.findAllRooms());
        return "room_list";
    }

    @GetMapping("/{roomNo}")
    public String room(@PathVariable("roomNo") Long roomNo, Model model) {
        Optional<RoomDto> roomOptional = roomService.getRoomByNo(roomNo);
        if (roomOptional.isPresent()) {
            model.addAttribute("room", roomOptional.get());
            if (roomOptional.get().getType().equals(RoomTypeEnum.TEST)) {
                model.addAttribute("pokers", pokerService.findAllPokers());
            } else {
                model.addAttribute("pokers", roomPokersService.getPokers(roomNo));
            }
            return "room_detail";
        }
        throw new RuntimeException("Can not found room #" + roomNo);
    }

    @GetMapping("/create")
    public String createRoom() {
        return "create_room";
    }

    @PostMapping
    public String createRoom(RoomDto roomDto) {
        Room room = roomService.saveRoom(roomDto);
        return String.format("redirect:/pokers/%d/room/%d", room.getOwnerId(), room.getId());
    }
}
