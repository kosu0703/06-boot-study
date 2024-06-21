package com.ict.edu2.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class KakaoService {

	
    public String getAccessToken(String code){

        String reqURL = "https://kauth.kakao.com/oauth/token";

        HttpURLConnection conn = null;
		try {
			URL url = new URL(reqURL);
			conn = (HttpURLConnection) url.openConnection();

			// POST 요청
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			// 헤더 요청
			conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded; charset=utf-8");

			// 본문
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

			StringBuffer sb = new StringBuffer();

			sb.append("grant_type=authorization_code");
			sb.append("&client_id=84f22c8078987862ac7f5051394c4959");
			sb.append("&redirect_uri=http://localhost:8080/api/kakaologin");
			sb.append("&code=" + code);

			bw.write(sb.toString());
			bw.flush();

			// 결과 코드가 200 이면 성공
			int responseCode = conn.getResponseCode();
            System.out.println("결과 : " + responseCode);

			if (responseCode == HttpsURLConnection.HTTP_OK) {

				// 토큰 요청을 통한 결과를 얻는데 JSON 타입이다.
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

				String line = "";
				StringBuffer sb2 = new StringBuffer();

				// 한줄씩 읽다가 더이상 읽을 수 없으면
				while ((line = br.readLine()) != null) {
					sb2.append(line);
				}

				// 결과를 보자
				String result = sb2.toString();
				System.out.println("result : " + result);

				// JSON 파싱
				JSONObject kakao_token = new JSONObject(result);
				String access_token = kakao_token.optString("access_token", "N/A");

                return access_token;
            }
        } catch (Exception e) {
			System.out.println("getAccessToken : " + e);
		} finally {
			try {
				conn.disconnect();
			} catch (Exception e2) {

			}
		}
        return null;
    }
    
    
    public String getKakaoProfile(String access_token){

        HttpURLConnection conn = null;

        //	유저 정보 가지러 가기
		String apiURL = "https://kapi.kakao.com/v2/user/me";

        try {
            URL url = new URL(apiURL);
			conn = (HttpURLConnection) url.openConnection();
					
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
					
			//	요청 : 엑세스 토큰 방식
			//	필수 - 헤더 (Authorization, Content-type)
			conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
			conn.setRequestProperty("Authorization", "Bearer " + access_token);
					
			//	200 이면 성공과 같은 의미 (HttpURLConnection.HTTP_OK)
			int responseCode = conn.getResponseCode();
						
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						
				String line = "";
				StringBuffer sb = new StringBuffer();
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				String result = sb.toString();
				System.out.println(result);

				// JSON 파싱하기 
				JSONObject jsonObject = new JSONObject(result);
				JSONObject kakaoAccount = jsonObject.getJSONObject("kakao_account");
       			String email = kakaoAccount.getString("email");

				// DB 에서 email 체크하기
				
				// DB 에 있으면 로그인 성공 => UserVO 리턴 프로필 리스트 페이지로
				
				// DB 에 없으면 로그인 실패 => UserVO(email) 리턴 회원가입 페이지로
				
				return email;
            }
        } catch (Exception e) {
            System.out.println("getKakaoProfile : " + e);
        } finally {
            try {
				conn.disconnect();
			} catch (Exception e2) {

			}
        }
        return null;
    }


}
