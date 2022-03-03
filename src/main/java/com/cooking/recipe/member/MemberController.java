package com.cooking.recipe.member;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cooking.recipe.member.config.KakaoConfig;
import com.cooking.recipe.member.config.NaverConfig;
import com.cooking.recipe.member.service.IMemberService;

@Controller
public class MemberController {
	@Autowired IMemberService service;
	@Autowired KakaoConfig kakao;
	@Autowired NaverConfig naver;
		
	/* 소셜로그인창 url 반환해주는 메소드 -> 받아서 자바스크립트에서 열어줌 */
	@RequestMapping(value = "/getUrl", produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String,String> getUrl(String page) {
		Map<String, String> map = new HashMap<>();
		if(page.equals("kakao")) {
			map.put("url", kakao.getUrl());		
		}else {
			map.put("url", naver.getUrl());
		}
		return map;
	}
	
	/* 카카오 로그인 토큰발급, 로그인회원 정보 얻어온 후 이미가입된 계정인지 확인 후 회원가입창 여는 메소드 */
	@RequestMapping(value = "/kakaoLogin")	
	public String kakaoLogin(String code, HttpSession session,Model model) { 
		// 기존에 네이버 소셜로그인해서 세션에 담겨있으면 삭제 후 진행
		if(session.getAttribute("accessToken_n") != null) {
			session.removeAttribute("accessToken_n");
			session.removeAttribute("social");
		}
		
		if(session.getAttribute("accessToken_k") == null) {
			String accessToken = kakao.getAccessToken(code);	
			HashMap<String, Object> userInfo = kakao.getUserInfo(accessToken);
				
			session.setAttribute("accessToken_k", accessToken);
			session.setAttribute("social", userInfo.get("kakaoId"));
		}
		
		// 기존에 가입된 소셜계정인지 확인 후 회원가입 모달 열기(이미 가입되어있으면 모달창 안열림)
		service.isExistSocial(model);
		return "forward:join";			
	}
	
	/* 네이버 로그인 토큰발급, 로그인회원 정보 얻어온 후 이미가입된 계정인지 확인 후 회원가입창 여는 메소드 */
	@RequestMapping(value="/naverLogin")
	public String naverLogin(String code, String state, HttpSession session, Model model) {
		
		if(session.getAttribute("accessToken_k") != null) {
			session.removeAttribute("accessToken_k");
			session.removeAttribute("social");
		}
		
		if(session.getAttribute("accessToken_n") == null) {
			String accessToken = naver.getAccessToken(code,state);		
			HashMap<String, Object> userInfo = naver.getUserInfo(accessToken);
			session.setAttribute("accessToken_n", accessToken);	
			session.setAttribute("social", userInfo.get("naverMail"));
		}
		
		service.isExistSocial(model);
		return "forward:join";	
	}
	
	/* 아이디 유효성 및 중복확인 */
	@RequestMapping(value = "/isExistId", produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String,String> isExistId(@RequestBody Map<String,String> map){
		String id = map.get("id");
		map.put("msg",service.isExistId(id));
		return map;
	}
	
	/* 휴대폰 유효성 체크 후 SMS 인증번호 전송 */
	@RequestMapping(value = "/sendAuth", produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String,String> sendAuth(@RequestBody Map<String,String> map){
		String phone = map.get("phone");
		boolean check = service.numberCheck(phone);
		if(check == false) {
			map.put("msg", "휴대폰 번호를 다시 확인해주세요.");
		}else{
			service.sendAuth(phone);
			map.put("msg", "인증번호가 전송되었습니다. 휴대폰을 확인해주세요.");
		}
		return map;
	}
	
	/* 인증번호와 사용자 입력 인증번호 비교*/
	@RequestMapping(value = "/phoneAuth", produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String,String> phoneAuth(@RequestBody Map<String,String> map){
		String authNum = map.get("authNum");
		map.put("msg", service.authPhone(authNum));
		return map;
	}
	
	/* 유효성 다시 체크, 비밀번호 암호화 후 최종가입 - DB저장 */
	@RequestMapping(value = "/joinProc", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> joinProc(HttpServletRequest req){
		Map<String,String> map = new HashMap<String, String>();
		map.put("msg", service.joinProc(req));
		return map;
	}
	
	/* 로그인 */	
	@RequestMapping(value = "/loginProc", produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String,String> loginProc(@RequestBody Map<String,String> map){
		String id = map.get("id"); String pw = map.get("pw");
		map.put("msg", service.loginProc(id,pw));		
		return map;
	}
	
	
	/* 로그아웃 */
	@RequestMapping(value="/logoutProc")
	public String logoutProc(HttpSession session) {
		session.invalidate();
		return "forward:index";
	}
	

	@RequestMapping(value="/memberListViewProc")
	public String memberListViewProc() {
		return "forward:index?formpath=memberList";
	}
	
}
