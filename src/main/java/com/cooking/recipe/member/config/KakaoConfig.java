package com.cooking.recipe.member.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.springframework.context.annotation.Configuration;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Configuration
public class KakaoConfig {
	
	// 카카오 로그인창 여는 url -> ajax로 요청시 url반환해서 응답받은곳에서 열어줌
	public String getUrl() {
		String reqURL= "";
		String restKey = "24d1826f93f5ef832e2398885563dee4";
		String redirectUri = "http://localhost:8085/recipe/kakaoLogin";
		
		reqURL = "https://kauth.kakao.com/oauth/authorize?client_id="
		+restKey+"&redirect_uri="+redirectUri+"&response_type=code";
		
		return reqURL;
	}
	
	//https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#refresh-token
	public String getAccessToken (String code) {
		String accessToken = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";
		String redirectUri = "&redirect_uri=http://localhost:8085/recipe/kakaoLogin";
		
		try {
			String sendMessage = "grant_type=authorization_code" 
					+ "&client_id=24d1826f93f5ef832e2398885563dee4"  // RestAPI key를 넣음!
					+ redirectUri
					+ "&code=" + code;

			URL url = new URL(reqURL); 
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();	
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			
			OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());	
			BufferedWriter bw = new BufferedWriter(osw);								
			bw.write(sendMessage); 	
			bw.flush();				
			
			int responseCode = conn.getResponseCode(); // 결과 코드가 200이라면 성공
//			System.out.println("responseCode : " + responseCode);
			
			// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			InputStreamReader isr = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			String line = "", result = "";
			while ((line = br.readLine()) != null) {		// readLine() 데이터를 라인단위로 읽음
				result += line;
			}
			System.out.println("response body : " + result);
			System.out.println("result.split : "+result.split(","));
			
			//Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
			JsonParser parser = new JsonParser(); 
			JsonElement element = parser.parse(result);		// 응답을 읽은 결과 result를 JsonElement형태로 파싱, 필요에 따라 적절히 변환(기본적으로 getter통해서 Json요소 가져오면 이 형태로 받아옴)
			accessToken = element.getAsJsonObject().get("access_token").getAsString();	// JsonElement에서 JsonObject(key-value형태로 존재)를 얻어와서 키를 통해 value얻음(get), 이를 ""를 뺀 문자열로 변환
//			System.out.println("access_token : " + accessToken);
			
			br.close();	// 사용후 버퍼 닫아서 자원반환
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return accessToken;			
	}
	
	//https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info
	public HashMap<String, Object> getUserInfo (String accessToken) {
	    HashMap<String, Object> userInfo = new HashMap<String, Object>();
	    String reqURL = "https://kapi.kakao.com/v2/user/me";
	    
	    try {
	        URL url = new URL(reqURL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("POST");
	        conn.setDoOutput(true);
	        
	        // 요청에 필요한 Header에 포함될 내용
	        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
	                
	        int responseCode = conn.getResponseCode();
	        System.out.println("responseCode : " + responseCode);
	        System.out.println(conn.getResponseMessage());

	       	
	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String line = "";
	        String result = "";
	        while ((line = br.readLine()) != null) {
	            result += line;
	        }
	        System.out.println("response body : " + result);
	        System.out.println("result.split : "+result.split(","));
	        
	        JsonParser parser = new JsonParser();
	        JsonElement element = parser.parse(result);
	        	        
	        JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
	        String kakaoId = element.getAsJsonObject().get("id").getAsString();
//	        String nickname = properties.getAsJsonObject().get("nickname").getAsString();	
	    	
	        userInfo.put("kakaoId", kakaoId);
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return userInfo;
	}
	
	 // https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#logout
	public void logout(String accessToken) {
	    String reqURL = "https://kapi.kakao.com/v1/user/logout";
	    
	    try {
	        URL url = new URL(reqURL); // URL 객체 생성
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
	        
	        conn.setRequestMethod("POST"); // 메소드 설정
	        
	        // 요청 정보 헤더에 담기.
	        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
	        // 응답 코드 확인
	        int responseCode = conn.getResponseCode();
//	        System.out.println("responseCode : " + responseCode);
	    }catch (Exception e) {
			e.printStackTrace();
		}
	}
}