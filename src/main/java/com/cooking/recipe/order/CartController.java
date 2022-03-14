package com.cooking.recipe.order;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
	
	/* 장바구니 페이지 - 로그인 여부 확인 후 아이디가 담은 장바구니 정보 셋팅 후 출력*/
	@RequestMapping(value="/cartViewProc")
	public String cartViewProc(Model model) {
		boolean check = service.cartViewProc(model);
		if(check == false) return "forward:join";
		return "forward:cart";
	}
	
	/* 상품 상세 페이지에서 장바구니 담기 클릭시 */
	@RequestMapping(value = "/cartInsert", produces = "application/json; charset=utf-8" )
	@ResponseBody		// ajax에서 done이나, success쓰려면 리턴해줘야함(void 안됨)..!
	public Map<String,String> cartInsert(@RequestBody Map<String,String>map) {
		int productNum = Integer.parseInt(map.get("num"));
		int amount = Integer.parseInt(map.get("amount"));
		
		boolean check = service.cartInsert(productNum, amount);
		if(check == false) map.put("msg", "로그인 필요");
		return map;
	}
	
	/* 장바구니 페이지에서 삭제버튼 클릭시 */
	@RequestMapping(value = "/itemDelete", produces = "application/json; charset=utf-8" )
	@ResponseBody		
	public Map<String,String> itemDelete(@RequestBody Map<String,String>map) {
		int cartNum = Integer.parseInt(map.get("num"));
		service.itemDelete(cartNum);
		
		return map;
	}
	
	/* 장바구니 페이지에서 주문하기 눌렀을 때, 주문수량 조정했을 수 있으므로 해당 아이디의 장바구니에 담긴것 전부 업데이트*/
	@RequestMapping(value = "/cartUpdate", produces = "application/json; charset=utf-8" )
	@ResponseBody		
	public boolean cartUpdate(HttpServletRequest req) {
		return service.updateCart(req);
	}
	
	

}
