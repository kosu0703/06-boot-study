package com.ict.exam.guestbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.exam.guestbook.mapper.GuestBookMapper;
import com.ict.exam.guestbook.vo.GuestBookVO;

@Service
public class GuestBookService {
    
    @Autowired
    private GuestBookMapper guestBookMapper;

    public List<GuestBookVO> guestBookList() {
        return guestBookMapper.guestBookList();
    }

    public GuestBookVO guestBookDetail(String idx) {
        return guestBookMapper.guestBookDetail(idx);
    }

}
