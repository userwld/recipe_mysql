package com.cooking.recipe.order;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cooking.recipe.order.service.ICartService;

@Controller
public class CartController {
	@Autowired ICartService service;
		
	@RequestMapping(value="/cartViewProc")
	public String cartViewProc(Model model) {
		boolean check = service.cartViewProc(model);
		if(check == false) return "forward:join";
		return "forward:cart";
	}
	
	@RequestMapping(value = "/cartInsert", produces = "application/json; charset=utf-8" )
	@ResponseBody		// ajax에서 done이나, success쓰려면 리턴해줘야함(void 안됨)..!
	public Map<String,String> cartInsert(@RequestBody Map<String,String>map) {
		int productNum = Integer.parseInt(map.get("num"));
		int amount = Integer.parseInt(map.get("amount"));
		
		service.cartInsert(productNum, amount);
		return map;
	}
	

}
