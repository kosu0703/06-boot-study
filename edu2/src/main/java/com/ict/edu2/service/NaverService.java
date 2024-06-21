package com.ict.edu2.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class NaverService {

    public String getAccessToken(String code){

        // 엑세스 토큰 받기 (인가 코드 가지고)
		String reqURL = "https://nid.naver.com/oauth2.0/token";

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
			sb.append("&client_id=YBlxtHy5leLiBnTL_u06");
			sb.append("&client_secret=Gi6JIdaE97");
			sb.append("&code=" + code);
			sb.append("&state=test");

			bw.write(sb.toString());
			bw.flush();

			// 결과 코드가 200 이면 성공
			int responseCode = conn.getResponseCode();
			System.out.println(responseCode);

			if (responseCode == HttpURLConnection.HTTP_OK) {

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
				System.out.println(result);

                // JSON 파싱 
				JSONObject naver_token = new JSONObject(result);
				String access_token = naver_token.optString("access_token", "N/A");

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

    // 유저정보 가져오기
    public String getNaverProfile(String access_token){

        String apiURL = "https://openapi.naver.com/v1/nid/me";
		
		HttpURLConnection conn = null;
		try {
			URL url = new URL(apiURL);
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
				
			conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
			conn.setRequestProperty("Authorization", "Bearer " + access_token);
			
			int responseCode = conn.getResponseCode();
			
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader br = 
						new BufferedReader(new InputStreamReader(conn.getInputStream()));
				
				String line = "";
				StringBuffer sb = new StringBuffer();
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				String result = sb.toString();

                // JSON 파싱하기 
				JSONObject jsonObject = new JSONObject(result);
				JSONObject response = jsonObject.getJSONObject("response");
                String email = response.getString("email");
                String name = response.getString("name");
                String birthyear = response.getString("birthyear");
                String birthday = response.getString("birthday");
                String gender = response.getString("gender");

                System.out.println("Name: " + name);            // 이름
                System.out.println("Email: " + email);          // 이메일
                System.out.println("Birthyear: " + birthyear);  // 생년 : 1993
                System.out.println("Birthday: " + birthday);    // 월일 : 07-03
                System.out.println("Gender: " + gender);        // 성별 : M

                // DB 에서 email 체크하기
				
				// DB 에 있으면 로그인 성공 => UserVO 리턴 프로필 리스트 페이지로
				
				// DB 에 없으면 로그인 실패 => UserVO(name, email, birth, gender) 리턴 회원가입 페이지로

				return result;
			}
			
		} catch (Exception e) {
			System.out.println("getNaverProfile : " + e);
		} finally {
			try {
				conn.disconnect();
			} catch (Exception e2) {

			}
		}
		return null;

    }

}
