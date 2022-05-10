package com.cooking.recipe.order.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface ICartService {
	
	/* 장바구니 페이지 - 로그인 여부 확인 후 아이디가 담은 장바구니 정보 셋팅 후 출력*/
	public boolean cartViewProc(Model model);

	/* 상품 상세 페이지에서 장바구니 담기 클릭시 */
	public boolean cartInsert(int productNum, int amount);

	/* 장바구니 페이지에서 삭제버튼 클릭시 */
	public void itemDelete(int cartNum);

	/* 장바구니 페이지에서 주문하기 눌렀을 때, 주문수량 조정했을 수 있으므로 해당 아이디의 장바구니에 담긴것 전부 업데이트*/
	public boolean updateCart(HttpServletRequest req);

}
