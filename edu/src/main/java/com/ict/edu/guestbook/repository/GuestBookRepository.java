package com.ict.edu.guestbook.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.ict.edu.guestbook.vo.GuestBookVO;

@Mapper
public interface GuestBookRepository {
    
    @Select("select * from guestbook order by idx")
    List<GuestBookVO> getGuestBookList();

    @Select("select * from guestbook where idx = #{idx}")
    GuestBookVO getGuestBookDetail(String idx);

    int postGuestBookInsert(GuestBookVO gvo);
    
}
