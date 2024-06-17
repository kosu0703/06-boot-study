package com.ict.edu2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.edu2.service.AdminService;
import com.ict.edu2.service.AuthService;
import com.ict.edu2.service.GuestBookService;
import com.ict.edu2.vo.GuestBookVO;
import com.ict.edu2.vo.MembersVO;

@RestController
@RequestMapping("/api")
public class AuthController {
    
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> postLogin(@RequestBody MembersVO mvo){
        return authService.authenticate(mvo);
    }

    @Autowired
    private GuestBookService guestBookService;

    @GetMapping("/guestbook")
    public List<GuestBookVO> getGuestBook() {
        return guestBookService.getGuestBook();
    }
    
    @Autowired
    private AdminService adminService;

    @GetMapping("/adminlist")
    public List<MembersVO> getAdminList() {
        return adminService.getAdminList();
    }
    


}
