package com.ict.edu2.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ict.edu2.vo.GuestBookVO;
import com.ict.edu2.vo.MembersVO;
import java.util.List;

// xml 메퍼와 연결되는 인터페이스 메퍼
@Mapper
public interface MemberMapper {
    
    MembersVO selectMember(@Param("id") String id);
    
    List<GuestBookVO> getGuestBook();

    List<MembersVO> getAdminList();

}
