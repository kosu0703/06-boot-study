package com.ict.exam.guestbook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ict.exam.guestbook.service.GuestBookService;
import com.ict.exam.guestbook.vo.GuestBookVO;

import lombok.Data;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/guestbook")
public class GuestBookController {
    
    @Autowired
    private GuestBookService guestBookService;

    @PostMapping("/list")
    public List<GuestBookVO> guestBookList() {
        return guestBookService.guestBookList();
    }

    // 포스트맨에서 form-data 방식을 사용할 경우
    @PostMapping("/detail")
    public GuestBookVO guestBookDetail(String idx) {
        return guestBookService.guestBookDetail(idx);
    }

    // 포스트맨에서 raw 방식을 사용할 경우
    /* 
    @PostMapping("/detail")
    public GuestBookVO guestBookDetail(@RequestBody IdxRequest idxRequest) {
        return guestBookService.guestBookDetail(idxRequest.getIdx());
    }
    // 요청 본문을 담을 DTO 클래스
    @Data
    public static class IdxRequest {
        private String idx;
    }
    */

    
}
