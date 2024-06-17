package com.ict.edu.guestbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.edu.guestbook.repository.GuestBookRepository;
import com.ict.edu.guestbook.vo.GuestBookVO;

@Service
public class GuestBookService {
    
    @Autowired
    private GuestBookRepository guestBookRepository;

    public List<GuestBookVO> getGuestBookList(){
        return guestBookRepository.getGuestBookList();
    }

    public GuestBookVO getGuestBookDetail(String idx){
        return guestBookRepository.getGuestBookDetail(idx);
    }

    public int postGuestBookInsert(GuestBookVO gvo) {
        return guestBookRepository.postGuestBookInsert(gvo);
    }

}
