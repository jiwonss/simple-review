package com.example.simple.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/pages")
public class PageController {

    @GetMapping("/main")
    public ModelAndView main(){
        return new ModelAndView("main");
    }

//    @GetMapping("/join")
//    public ModelAndView join(){
//        return new ModelAndView("join");
//    }
//
//    @GetMapping("/login")
//    public ModelAndView login(){
//        return new ModelAndView("login");
//    }
}
