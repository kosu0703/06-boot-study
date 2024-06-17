package com.ict.edu.member.repository;

import org.apache.ibatis.annotations.Mapper;

import com.ict.edu.member.vo.MemberVO;

@Mapper
public interface MemberRepository {
    
    public MemberVO postLogin(MemberVO mvo);

    public int postJoin(MemberVO mvo);
    
}
