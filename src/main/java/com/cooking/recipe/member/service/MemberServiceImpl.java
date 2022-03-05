package com.cooking.recipe.member.service;

import java.util.ArrayList;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cooking.recipe.member.config.InputConfig;
import com.cooking.recipe.member.config.PageConfig;
import com.cooking.recipe.member.dao.IMemberDAO;
import com.cooking.recipe.member.dto.MemberDTO;

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
		
		MemberDTO dto = dao.selectId(id);
		if(dto != null) msg = "중복 아이디 입니다.";
		else msg = "사용 가능한 아이디 입니다.";
		
		return msg;
	}
	
	@Override
	public void isExistSocial(Model model) {
		String social = (String)session.getAttribute("social");
		
		int check = dao.selectSocial(social);
		
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
		MemberDTO member = dao.selectId(id);
		
		if(member == null) return "가입되지 않은 계정입니다.";
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		boolean pwCheck = encoder.matches(pw, member.getPw());
		
		if(pwCheck == false) return "비밀번호를 다시 확인해주세요";
		
		session.setAttribute("loginId", id);
		return "로그인 성공";
	}

	@Override
	public void memberList(Model model, int currentPage) {	
		int[] page = PageConfig.setPage(dao.memberCount(), currentPage);
		// page[] ={한 페이지당 시작번호, 한 페이지당 끝번호, 페이지당 개수(10), 전체 데이터 개수}
		ArrayList<MemberDTO> memberList = dao.selectAll(page[0], page[1]);
		model.addAttribute("list", memberList);
		
		String url="/recipe/memberListViewProc?currentPage=";
		model.addAttribute("page", PageConfig.getNavi(currentPage, page[2], page[3], url));
	}

	@Override
	public void memberSearch(Model model, int currentPage, String searchWord) {
		int[] page = PageConfig.setPage(dao.searchCount(searchWord), currentPage);
		
		ArrayList<MemberDTO> searchList = dao.selectSearch(page[0], page[1],searchWord);
		model.addAttribute("list", searchList);
		
		String url="/recipe/memberListViewProc?currentPage=";
		model.addAttribute("page", PageConfig.getNavi(currentPage, page[2], page[3], url));
		
	}

	@Override
	public void memberDelete(String deleteId) {
		dao.deleteMember(deleteId);		
	}






	
	
	
	
	
}
