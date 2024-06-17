package com.ict.exam.guestbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.ict.exam.guestbook.vo.GuestBookVO;

@Mapper
public interface GuestBookMapper {

    List<GuestBookVO> guestBookList();

    @Select("select * from guestbook where idx = #{idx}")
    GuestBookVO guestBookDetail(String idx);

}
