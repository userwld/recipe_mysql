package com.cooking.recipe.member.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.HashMap;

import org.springframework.context.annotation.Configuration;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Configuration
public class NaverConfig {	/* 네이버 소셜 로그인 */
	
	/* ajax로 네이버 소셜 로그인 url요처 들어오면 url 완성해서 전달 */
	public String getUrl() {
		String clientId = "jOQwwf9klbzWSUXExj3l"; //애플리케이션 클라이언트 아이디값";
	    String redirectURI;
	    String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
		try {
			redirectURI = URLEncoder.encode("http://localhost:8085/recipe/naverLogin", "UTF-8");
			SecureRandom random = new SecureRandom();
		    String state = new BigInteger(130, random).toString();			// state값 랜덤으로 생성해서 넘겨줘야 함
		    
		    apiURL += "&client_id=" + clientId;
		    apiURL += "&redirect_uri=" + redirectURI;
		    apiURL += "&state=" + state;
		} catch (Exception e) {
			e.printStackTrace();
		}

	    return apiURL;
	}
	
	/* 엑세스 토큰 얻기 */
	public String getAccessToken(String code, String state) {
		String clientId = "jOQwwf9klbzWSUXExj3l";	//애플리케이션 클라이언트 아이디값";
	    String clientSecret = "aYrlmPmlDH";			//애플리케이션 클라이언트 시크릿값";
	    String access_token = "";
	    
	    try {
	    	String redirectURI = URLEncoder.encode("http://localhost:8085/recipe/naverLogin", "UTF-8");
	  	    String apiURL;											// 발급 : authorization_code / 갱신 : refresh_token / 삭제 : delete
	  	    apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
	  	    apiURL += "client_id=" + clientId;
	  	    apiURL += "&client_secret=" + clientSecret;
	  	    apiURL += "&redirect_uri=" + redirectURI;
	  	    apiURL += "&code=" + code;
	  	    apiURL += "&state=" + state;
    	
	      URL url = new URL(apiURL);
	      HttpURLConnection con = (HttpURLConnection)url.openConnection();
	      con.setRequestMethod("GET");
	      int responseCode = con.getResponseCode();
	      BufferedReader br;
//	      System.out.print("responseCode="+responseCode);
	      if(responseCode==200) { // 정상 호출
	        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	      } else {  // 에러 발생
	        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	      }
	      
	      String inputLine;
	      StringBuffer res = new StringBuffer();
	      while ((inputLine = br.readLine()) != null) {
	        res.append(inputLine);
	      }
	      
	      br.close();
	      if(responseCode==200) {
//	        System.out.println(res.toString());
	      }
	      
	      JsonParser parser = new JsonParser();
	      JsonElement element = parser.parse(res.toString());
	      access_token = element.getAsJsonObject().get("access_token").getAsString();

	    } catch (Exception e) {
	      System.out.println(e);
	    }
	    
	    return access_token;
	}
	/* 엑세스 토큰 통해 유저정보 얻기 */
	public HashMap<String,Object> getUserInfo(String accessToken){
		HashMap<String,Object> userInfo = new HashMap<String,Object>();
		String reqURL = "https://openapi.naver.com/v1/nid/me";
				
		try {
	        URL url = new URL(reqURL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("POST");
	        conn.setDoOutput(true);
	        
	        // 요청에 필요한 Header에 포함될 내용
	        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
	                
	        int responseCode = conn.getResponseCode();
//	        System.out.println("responseCode : " + responseCode);
//	        System.out.println(conn.getResponseMessage());
	       	
	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String line = "";
	        String result = "";
	        while ((line = br.readLine()) != null) {
	            result += line;
	        }
//	        System.out.println("response body : " + result);
//	        System.out.println("result.split : "+result.split(","));
	        
	        JsonParser parser = new JsonParser();
	        JsonElement element = parser.parse(result);
	        	        
	        JsonObject response = element.getAsJsonObject().get("response").getAsJsonObject();
	        String email = response.getAsJsonObject().get("email").getAsString();
	     	        
	        userInfo.put("naverMail", email); 	// 네이버 메일 계정 db에 저장 -> 소셜 가입 여부 판단하는 값(중복 계정 가입x)
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return userInfo;
		
	}
	

}
