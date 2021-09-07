package net.iyiguo.simplepoker.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author leeyee
 * @date 2021/08/15
 */
@Controller
@RequestMapping("/")
public class IndexPageController {
    @GetMapping
    public String index() {
        return "forward:/rooms";
    }
}
