package com.ict.edu.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.edu.member.repository.MemberRepository;
import com.ict.edu.member.vo.MemberVO;

@Service
public class MemberService {
    
    @Autowired
    private MemberRepository memberRepository;

    public MemberVO postLogin(MemberVO mvo) {
        return memberRepository.postLogin(mvo);
    }

    public int postJoin(MemberVO mvo){
        return memberRepository.postJoin(mvo);
    }

}
