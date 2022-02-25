package com.cooking.recipe.member;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MemberController {
	

	@RequestMapping(value="/memberListViewProc")
	public String memberListViewProc() {
		return "forward:index?formpath=memberList";
	}
	
	@RequestMapping(value="/loginProc")
	public String loginProc(HttpSession session) {
		session.setAttribute("id", "admin");
		return "forward:/";
	}
	
	@RequestMapping(value="/logoutProc")
	public String logoutProc(HttpSession session) {
		session.removeAttribute("id");
		return "forward:index";
	}
	
}
