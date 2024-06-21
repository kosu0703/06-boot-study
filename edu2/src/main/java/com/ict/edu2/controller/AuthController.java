package com.ict.edu2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ict.edu2.jwt.JwtDecode;
import com.ict.edu2.service.AdminService;
import com.ict.edu2.service.AuthService;
import com.ict.edu2.service.GuestBookService;
import com.ict.edu2.service.KakaoService;
import com.ict.edu2.service.NaverService;
import com.ict.edu2.vo.GuestBookVO;
import com.ict.edu2.vo.MembersVO;

@RestController
@RequestMapping("/api")
public class AuthController {
    
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> postLogin(@RequestBody MembersVO mvo){
        return  authService.authenticate(mvo);
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
    
    // 카카오 로그인
	@Autowired
	private KakaoService kakaoservice;

	@GetMapping("/kakaologin")
	public String kakaoLogin(@RequestParam("code") String code) {
		// 1. 인가 코드 받기
        System.out.println("code : " + code);

		// 2. 엑세드 토큰 받기
		String access_token = kakaoservice.getAccessToken(code);

		// 3. 카카오 유저 정보 가지러 가기
		String result = kakaoservice.getKakaoProfile(access_token);

		return result;
	}

	// 네이버 로그인
	@Autowired
	private NaverService naverService;

	@GetMapping("/naverlogin")
	public String naverLogin(@RequestParam("code") String code) {
		// 1. 인가 코드 받기
		System.out.println("code : " + code);

		// 2. 엑세스 토큰 받기
		String access_token = naverService.getAccessToken(code);

		// 3. 네이버 유저정보 가져오기
		String result = naverService.getNaverProfile(access_token);

		return result;
	}

	// 토큰 디코딩
	@GetMapping("/token")
	public String getMethodName(@RequestParam("param") String param) {
		try {
			System.out.println("Received token: " + param);
			JwtDecode jwtDecode = new JwtDecode(param);
			String id = jwtDecode.getId();
			String email = jwtDecode.getEmail();
			String phone = jwtDecode.getPhone();
			return "디코딩 완료: ID = "+id+", Email = "+email+", Phone = "+phone;
		} catch (Exception e) {
			return "디코딩 실패: " + e.getMessage();
		}
	}

}
