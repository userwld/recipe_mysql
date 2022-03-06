package com.cooking.recipe.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cooking.recipe.order.service.IOrderService;

@Controller
public class OrderController {
	@Autowired IOrderService service;
	
	/* 상품상세 페이지에서 바로주문 눌렀을 때 */
	@RequestMapping(value="/orderNowProc")	
	public String orderNowProc(String productNum, String productName, String price, String amount, Model model) {
		service.orderNowProc(productNum,productName,price,amount,model);
		return "forward:order";
	}
	
	/* 장바구니 페이지에서 주문하기 눌렀을 때 -> 카트에 담긴거 전부 가져와서 출력*/
	@RequestMapping(value="/orderViewProc")
	public String orderViewProc() {
		return "forward:order";
	}
		
	@RequestMapping(value="/orderDetailViewProc")
	public String orderDetailViewProc() {
		return "forward:index?formpath=orderDetail";
	}
	
	@RequestMapping(value="/orderHistoryViewProc")
	public String orderHistoryViewProc(){
		return "forward:index?formpath=orderHistory";
	}
	
}
