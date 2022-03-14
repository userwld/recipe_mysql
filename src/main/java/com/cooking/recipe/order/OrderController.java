package com.cooking.recipe.order;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.cooking.recipe.order.service.IOrderService;

@Controller
public class OrderController {
	@Autowired IOrderService service;
	
	/* 상품상세 페이지에서 바로주문 눌렀을 때 */
	@RequestMapping(value="/orderNowProc")	
	public String orderNowProc(String productNum, String productName, String price, String amount, Model model) {
		boolean check = service.orderNowProc(productNum,productName,price,amount,model);
		if(check == false) return "forward:join";
		return "forward:order";
	}
	
	/* 장바구니 페이지에서 주문하기 눌렀을 때 -> 카트에 담긴거 전부 가져와서 출력*/
	@RequestMapping(value="/orderCartProc")
	public String orderCartProc(String[] orderItems, Model model) {
		boolean check = service.orderCart(orderItems, model);
		if(check == false) return "forward:join";
		return "forward:order";
	}
	
	/* 주문페이지에서 결제하기 눌렀을 때 */
	@RequestMapping(value = "/payProc", produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String,String> payProc(@RequestBody Map<String,String> map){
		boolean stockCheck = service.stockCheck(map.get("state"));
		if(stockCheck == true) {
			map.put("msg", service.payProc(map));
		}else {
			map.put("msg", "재고 없음");
		}	
		return map;
	}
	
	/* 결제 준비 완료 후 결제 승인으로 리다이렉트 됐을때*/
	@RequestMapping(value="/payApprove")
	public String payApprove(String pg_token, String state) {
		String resURL = "";
		
		// 결제 승인
		int resCode = service.payApprove(pg_token);

		if(resCode == 200) {
			service.addrUpdate();		// 결제 성공시 member테이블에 이름과 배송지 정보 업데이트
			
			if(state.equals("now")) {	// 바로 주문 결제 성공시
				service.orderInsert(); 	// 단건 주문 주문테이블에 삽입 / 배송테이블에 배송정보 삽입
				service.stockUpdate();	// 단건 주문 상품 -> 상품테이블에서 재고 수정
				
			}else {						// 장바구니 페이지 담았던것 주문 결제 성공시
				service.ordersInsert();	// 여러건 주문 주문테이블에 삽입 / 배송테이블에 배송정보 삽입
				service.stocksUpdate();	// 여러건 주문 상품 -> 상품테이블에서 재고 수정
				service.cartDelete();	// 주문한 상품들 장바구니테이블에서 삭제
			}
						
			resURL = "forward:orderHistoryViewProc";
		}else {
			resURL = "forward:orderDetailViewProc";
		}
				
		return resURL;
	}
	
	/* 결제 준비 후 결제 실패로 리다이렉트 시*/
	@RequestMapping(value="/payFail")
	public String payFail(String state) {
		if(state.equals("now")) {
			String[] param = service.payFail();		// 바로주문에서 결제 실패시 -> 다시 바로주문 페이지로
			return "forward:orderNowProc?productNum="+param[0]+"&productName="+param[1]+"&price="+param[2]+"&amount="+param[3];
		}else return "forward:cartViewProc";		// 장바구니 결제 실패시 -> 다시 장바구니 페이지로
		
	}
	
	/* 결제 준비 후 결제 취소로 리다이렉트 시*/
	@RequestMapping(value="/payCancel")
	public String payCancel(String state) {
		if(state.equals("now")) {
			String[] param = service.payFail();		// 바로주문에서 결제 취소시
			return "forward:orderNowProc?productNum="+param[0]+"&productName="+param[1]+"&price="+param[2]+"&amount="+param[3];
		}else return "forward:cartViewProc";		// 장바구니 결제 실패시 -> 다시 장바구니 페이지로
	}
	
	/* 주문내역 페이지 */
	@RequestMapping(value="/orderHistoryViewProc")
	public String orderHistoryViewProc(Model model){
		boolean check = service.orderHistory(model);
		if(check == false) return "forward:join";
		return "forward:index?formpath=orderHistory";
	}
	
	/* 주문내역 페이지에서 주문상세보기 클릭시 */
	@RequestMapping(value="/orderDetailViewProc")
	public String orderDetailViewProc(String orderNum, Model model) {
		service.orderDetail(orderNum, model);
		return "forward:index?formpath=orderDetail";
	}
		
	/* 주문내역 페이지에서 장바구니 담기 버튼 클릭시 -> 재고 조회 후 있으면 장바구니 테이블에 수량 1개로 담음 */
	@RequestMapping(value = "/putCart", produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String,String> putCart(@RequestBody Map<String,String> map){
		map.put("msg", service.putCart(Integer.parseInt(map.get("num"))));
		return map;
	}
	
	/* 주문내역 페이지에서 주문취소 눌렀을 때 (당일 취소일 경우에만 결제취소)*/
	@RequestMapping(value="/orderCancelProc")
	public String orderCalcelProc(String orderNum, String orderDate, Model model) {	// orderDate -  YY/MM/dd 형태로 옴
		int responseCode = service.orderCancel(orderNum, orderDate , model);
		if(responseCode == 200) {				// 결제 취소 성공시
			service.stockPlus(orderNum);		// 상품테이블 재고증가 / 주문 테이블에서 주문내역 가져와야 하므로 주문내역 삭제전 실행
			service.orderDelete(orderNum);		// 주문테이블 주문내역삭제
			service.deliveryDelete(orderNum);	// 배송테이블 배송정보삭제
		}
		return "forward:orderHistoryViewProc";
	}
	
	/* 메인 페이지에서 베스트 상품 일간/주간 버튼 클릭시 */
	@RequestMapping(value="/bestSales", produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String,String> bestSales(@RequestBody Map<String,String> map) {
		service.bestSales(map.get("term"));
		return map;
	}

}
