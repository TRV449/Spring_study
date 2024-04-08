package com.example.spring_study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class FirstController {
    @GetMapping("/hi")
    public String niceToMeetYou(Model model){
        model.addAttribute("username","donghyeon");
        return "greetings";
    }
    @GetMapping("/bye")
    public String seeYouNext(Model model){
        model.addAttribute("nickname","donghyeon");
        return "goodbye";
    }
}