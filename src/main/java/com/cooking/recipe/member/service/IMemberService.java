package com.cooking.recipe.member.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface IMemberService {

	public String isExistId(String id);			// 아이디 유효성 & 중복체크
	
	public void isExistSocial(Model model);		// 소셜계정 가입여부 체크(이미 가입된 계정 가입 x)
	
	public boolean numberCheck(String phone);	// 핸드폰 번호 유효성 체크

	public void sendAuth(String phone);			// 핸드폰 SMS 인증번호 전송

	public String authPhone(String authNum);	// 핸드폰에 전송된 인증번호와 사용자 입력 인증번호 일치여부 확인

	public String joinProc(HttpServletRequest req);	 // 유효성 전부 체크 후 비밀번호 암호화 후 최종 가입

	public String loginProc(String id, String pw);	// 아이디, 비밀번호 확인 후 로그인 
	
	public void removeSession();					// 로그아웃시 세션지우는 메소드 (메인 베스트 세션을 남겨야 하므로 메인 베스트 세션 제외 삭제)

	public void memberList(Model model, int currentPage);	// 관리자 - 회원 관리 페이지 - 모든 회원 조회

	public void memberSearch(Model model, int currentPage, String searchWord); // 관리자 - 회원 관리 페이지 - 회원 검색

	public void memberDelete(String deleteId);	// 관리자 - 회원 관리 페이지 - 회원 삭제



}
