package com.ict.edu2.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 토큰을 추출하고, 유효성 검사하여, 유효한 경우만 사용자 정보를 로드
// 즉, 보호된 리소스에 대한 접근이 가능한 사용자인지 확인
@Component
public class JwtRequestFilter extends OncePerRequestFilter{

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
            throws ServletException, IOException {
        // 리액트에서 보내준 토큰을 저장
        final String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;
        
        // 토큰이 있어야하고, 헤더에 Bearer 로 시작되면
        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")){
            // 'Bearer ' 이후의 글자가 토큰이므로
            jwtToken = requestTokenHeader.substring(7);
            //System.out.println(jwtToken);
            try {
                username = jwtUtil.extractUsername(jwtToken);
                System.out.println(username);
            } catch (Exception e) {
                System.out.println("JWT exception");
            }
        }else{
            System.out.println("JWT 없음");
        }

        // 사용자 아이디 추출 , 현재 SecurityContext 에 인증정보가 없는지 확인
        // 로그인해서 들어온 사람은 계속 SecurityContext 를 가지고 있다.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 사용자 아이디 가지고 현재 DB 에서 정보가져오기
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);    

            // 토큰 검사하기
            if (jwtUtil.validateToken(jwtToken, userDetails)) {
                // 인증 객체 생성
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken 
                    = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                // 인증 객체에 추가 세부 정보를 설정
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // 컨트롤러로 요청을 넘기기 전에 추가 작업을 수행
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
    
}
