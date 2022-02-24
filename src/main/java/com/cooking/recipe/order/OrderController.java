package com.cooking.recipe.order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrderController {
	
	@RequestMapping(value="/cartInsertProc")
	public void cartInsertProc() {}
	
	@RequestMapping(value="/cartViewProc")
	public String cartViewProc() {
		return "forward:cart";
	}
	
	@RequestMapping(value="/orderViewProc")
	public String orderViewProc() {
		return "forward:order";
	}
	
}
