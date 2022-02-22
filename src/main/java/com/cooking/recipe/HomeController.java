package com.cooking.recipe;

import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	

	 @RequestMapping(value = "/")
	   public String main(Model model, HttpSession session) {	    
		 model.addAttribute("formpath", "main");
	      return "index";
	   }
	 
	   @RequestMapping(value = "/main")
	   public void main() { }
	   
	   @RequestMapping(value = "/index")
	   public String index(Model model, HttpSession session, String formpath) {
	      if(formpath == null) {
	    	  formpath = "main";
	      }	      
	      model.addAttribute("formpath", formpath);
	      logger.warn("formpath : " + formpath);
	      return "index";
	   }
	   
	   @RequestMapping(value="/join")
	   public String join() {
		   return "member/join";
	   }
	
}
