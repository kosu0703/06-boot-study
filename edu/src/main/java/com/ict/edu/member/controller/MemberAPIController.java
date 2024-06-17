package com.ict.edu.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ict.edu.member.service.MemberService;
import com.ict.edu.member.vo.MemberVO;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberAPIController {

    @Autowired
    private MemberService memberService;

    @PostMapping("login.do")
    @ResponseBody
    public String postLogin(MemberVO mvo) {
        MemberVO m_vo = memberService.postLogin(mvo);
        if (m_vo == null) {
            return "0";
        } else {
            return "1";
        }
    }

    //  쇼핑몰 예제 로그인
    @PostMapping("login2.do")
    @ResponseBody
    public MemberVO postLogin2(MemberVO mvo) {
        MemberVO m_vo = memberService.postLogin(mvo);
        if (m_vo != null) {
            return m_vo;
        }
        return null;
    }

    @PostMapping("join.do")
    @ResponseBody
    public String postJoin(MemberVO mvo) {
        int result = memberService.postJoin(mvo);
        if (result > 0) {
            return "1";
        }
        return "0";
    }

    


}