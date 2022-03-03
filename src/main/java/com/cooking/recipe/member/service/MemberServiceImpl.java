package com.cooking.recipe.member.service;

import java.util.HashMap;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cooking.recipe.member.config.InputConfig;
import com.cooking.recipe.member.dao.IMemberDAO;
import com.cooking.recipe.member.dto.MemberDTO;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Service
public class MemberServiceImpl implements IMemberService{
	
	@Autowired HttpSession session;
	@Autowired IMemberDAO dao;
	@Autowired InputConfig ic;

	@Override
	public String isExistId(String id) {
		String msg = "";		
		if(ic.idCheck(id) == false) {
			msg = "소문자로 시작하며 소문자, 숫자, -_기호만 가능합니다.(5~12자)";
			return msg;
		}
		
		MemberDTO dto = dao.isExistId(id);
		if(dto != null) msg = "중복 아이디 입니다.";
		else msg = "사용 가능한 아이디 입니다.";
		
		return msg;
	}
	
	@Override
	public void isExistSocial(Model model) {
		String social = (String)session.getAttribute("social");
		
		int check = dao.isExistSocial(social);
		
		if(check == 1) {
			model.addAttribute("msg", "이미 가입된 계정입니다.");	
		}else {
			model.addAttribute("modal", "yes");	
		}	
	}
	
	@Override
	public boolean numberCheck(String phone) {				// 휴대폰번호 10 또는 11자리만 가능
		if(!(phone.length() == 10 || phone.length() == 11)) {
			return false;
		}else return ic.phoneCheck(phone);

	}

	@Override
	public void sendAuth(String phone) {
		Random random = new Random();
		String authNum = String.format("%06d", random.nextInt(1000000));
		
		session.setAttribute("savedNum", authNum);
		System.out.println(authNum);

/*		휴대폰 SMS 전송 기능 확인 후 주석처리 -> 주석해제 후 보내는 사람 전화번호 넣으면 동작
		String api_key = "NCSMF9LZBWWUKF5X";
		String api_secret = "DKRMQD20SNOPL7GSF5EATACYD0GGU0OL";
		
		Message coolsms = new Message(api_key, api_secret);
		
		HashMap<String,String> msg = new HashMap<String, String>();
		msg.put("to", phone);
		msg.put("from", "");   // 보내는 사람 전화번호
		msg.put("type", "SMS");
		msg.put("text", "[Yoon's Recipe] 인증번호는 " + "["+authNum+"] 입니다.");
		msg.put("app_version", "test 1.0");
				
		try {
			JSONObject obj = (JSONObject)coolsms.send(msg);
			System.out.println(obj.toString());
		}catch(CoolsmsException e){
			System.out.println(e.getMessage());
			System.out.println(e.getCode());
		}
*/		
	}

	@Override
	public String authPhone(String authNum) {
		String errorMsg = "인증번호를 다시 확인해주세요";
		
		if(authNum.length()!= 6) return errorMsg;
		
		String savedNum = (String)session.getAttribute("savedNum");	
		if(!authNum.equals(savedNum)) return errorMsg;
		else return "인증 성공";
	}

	@Override
	public String joinProc(HttpServletRequest req) {
		
		String id = (String)req.getParameter("id");
		String pw = (String)req.getParameter("pw");
		String phone = (String)req.getParameter("phone");
		String social = (String)session.getAttribute("social");
		
		if(ic.idCheck(id) == false || ic.pwCheck(pw) == false || numberCheck(phone) == false || social == null) {
			return "회원가입 실패";
		}
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String securePw = encoder.encode(pw);
		
		MemberDTO member = new MemberDTO(id, securePw, phone, social);
		dao.insertMember(member);
						
		return "회원가입 성공";
	}

	@Override
	public String loginProc(String id, String pw) {
		MemberDTO member = dao.isExistId(id);
		
		if(member == null) return "가입되지 않은 계정입니다.";
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		boolean pwCheck = encoder.matches(pw, member.getPw());
		
		if(pwCheck == false) return "비밀번호를 다시 확인해주세요";
		
		session.setAttribute("loginId", id);
		return "로그인 성공";
	}




	
	
	
	
	
}
