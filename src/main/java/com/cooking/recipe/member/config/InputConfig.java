package com.cooking.recipe.member.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class InputConfig {
	
	public boolean idCheck(String id) {		// 아이디 : 소문자로 시작, 소문자/숫자/-_만 가능(5~12자)
		String regId = "^[a-z][a-z0-9-_]{4,12}$";
		return !id.matches(regId) ? false : true;
	}
	
	public boolean pwCheck(String pw) {		// 비밀번호 : 영문자와 숫자가 반드시 하나이상 포함된 조합(8~16자)
											// 특수문자는 선택사항 -_!@#$%^&*? 가능
		String regPw = "^(?=.*[a-zA-Z])(?=.*?[0-9])[A-Za-z\\d-_!@#$%^&*?]{8,16}$";
		return !pw.matches(regPw)? false : true;
	}
	
	public boolean phoneCheck(String phone) { // 핸드폰번호 : 10,11자리 숫자만 가능 010,011,016,017,018,019중 하나로 시작
		String phone1 = phone.substring(0,3);
		String phone2 = phone.substring(3,7);
		String phone3 = phone.substring(7);
		
		if(phone.length() == 10) {
			phone2 = phone.substring(3,6);
			phone3 = phone.substring(6);
		}
						
		String phone1Reg = "^01(?:0|1|[6-9])";						
		String phone2Reg = "\\d{3,4}$";
		String phone3Reg = "\\d{4}$";
		
		return !phone1.matches(phone1Reg) || !phone2.matches(phone2Reg) || !phone3.matches(phone3Reg)? false : true;
	}

}
