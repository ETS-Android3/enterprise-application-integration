package com.eai.Statboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("sum_broken", 0);
        model.addAttribute("sum_fixed", 10);
        return "index";
    }

}