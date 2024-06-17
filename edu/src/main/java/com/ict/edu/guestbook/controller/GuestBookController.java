package com.ict.edu.guestbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class GuestBookController {
    
    @GetMapping("hi")
    public String hello(Model model) {
        model.addAttribute("name","홍길동");
        model.addAttribute("age",34);
        //  resources-templates 안에 hello.html 호출
        return "hello";
    }
}

