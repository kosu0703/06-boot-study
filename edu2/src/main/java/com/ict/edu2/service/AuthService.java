package com.ict.edu2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.ict.edu2.jwt.JWTUtil;
import com.ict.edu2.vo.MembersVO;

@Service
public class AuthService {
 
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTUtil jwtUtil;

    public ResponseEntity<?> authenticate(MembersVO mvo){
        try {
            Authentication authentication 
            = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(mvo.getId(), mvo.getPassword()));

            // 사용자의 아이디와 패스워드를 가지고 있다.
            //final UserDetails userDetails = userDetailsService.loadUserByUsername(mvo.getId());

            // 그거를 가지고 jwt 토큰을 만든다.
            //final String jwt = jwtUtil.generateToken(userDetails);

            //return ResponseEntity.ok(new JwtResponse(jwt));
            return ResponseEntity.ok(mvo.getId());
        } catch (Exception e) {
            return ResponseEntity.status(401).body("error");
        }
    }


}
