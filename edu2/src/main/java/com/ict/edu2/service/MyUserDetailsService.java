package com.ict.edu2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ict.edu2.mapper.MemberMapper;
import com.ict.edu2.vo.MembersVO;
import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService{
    
    @Autowired
    private MemberMapper memberMapper;
    
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        MembersVO mvo = memberMapper.selectMember(id);
        if (mvo == null) {
            throw new UsernameNotFoundException("user not found : " + id);
        }
        return new User(mvo.getId(), mvo.getPassword(), new ArrayList<>());
    }
}
