package com.ict.edu.guestbook.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ict.edu.guestbook.service.GuestBookService;
import com.ict.edu.guestbook.vo.GuestBookVO;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/api/guestbook")
@RequiredArgsConstructor
public class GuestBookAPIController {
    
    @Autowired
    private GuestBookService guestBookService;

    @GetMapping("hello")
    @ResponseBody 
    public String getHello(){
        return "<h2>HelloWorld</h2>";
    }

    @GetMapping("list.do")
    @ResponseBody
    public List<GuestBookVO> getGuestBookList(){
        return guestBookService.getGuestBookList();
    }
    
    @GetMapping("detail.do")
    @ResponseBody
    public GuestBookVO getGuestBookDetail(String idx){
        return guestBookService.getGuestBookDetail(idx);
    }

    @PostMapping("insert.do")
    public String postGuestBookInsert(GuestBookVO gvo) {
        int result = guestBookService.postGuestBookInsert(gvo);
        if (result > 0) {
            return "삽입성공";
        }else{
            return "삽입실패";
        }


    }

    



}
