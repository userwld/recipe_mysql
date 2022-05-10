package com.cooking.recipe.order.service;

import java.util.Map;

import org.springframework.ui.Model;

public interface IOrderService {
	
	/* 상품상세페이지에서 바로주문 눌렀을 때 주문페이지 셋팅*/
	public boolean orderNowProc(String productNum, String productName, String price, String amount, Model model);

	/* 장바구니 페이지에서 주문하기 눌렀을 때 주문페이지 셋팅 - 장바구니페이지에서 체크한 항목들만 주문페이지에서 출력 */
	public boolean orderCart(String[] orderItems, Model model);
	
	/* 주문페이지 - 결제버튼 클릭시 최종적으로 상품 재고가 남아있는지 확인 후 주문가능 여부 반환 */
	public boolean stockCheck(String state);	
	
	/* 주문페이지 - 결제버튼 클릭시 카카오 페이 결제 준비(map에 담긴 state로 바로주문인지, 장바구니에서 온 주문인지 구분해서 파라미터값 다르게 가져감) */
	public String payProc(Map<String, String> map);
		
	/* 카카오페이에서 결제 준비 성공했을 때, 결제 승인 단계*/
	public int payApprove(String pg_token);
	
	/* 결제 취소 또는 실패시 이전 페이지로 돌아가기 위한 파라미터들 */
	public String[] payFail();

	/* 결제 승인 성공시, 회원테이블에 이름, 주소 업데이트 */
	public void addrUpdate();

	/* 단건 결제 승인 성공시(바로주문), 주문테이블에 단건 주문 삽입 / 배송테이블 배송지 정보 삽입 메소드 호출 */
	public void orderInsert();		

	/* 단건 결제 승인 성공시(바로주문), 상품테이블에 단건 상품 재고 수정 */
	public void stockUpdate();

	/* 다건 결제 승인 성공시(장바구니 담긴 품목 주문), 주문테이블에 다건 주문 삽입 / 배송테이블 배송지 정보 삽입 메소드 호출 */
	public void ordersInsert();

	/* 다건 결제 승인 성공시(장바구니 담긴 품목 주문), 상품테이블에 다건 상품 재고 수정 */
	public void stocksUpdate();

	/* 다건 결제 승인 성공시(장바구니 담긴 품목 주문), 장바구니 테이블에 주문된 상품 삭제 */
	public void cartDelete();

	/* 주문내역 페이지 셋팅 */
	public boolean orderHistory(Model model);
	
	/* 주문 내역 페이지에서 주문 상세페이지 클릭시 해당 주문번호에 맞게 페이지 셋팅 */
	public void orderDetail(String orderNum, Model model);

	/* 주문내역 페이지에서 장바구니 담기 */
	public String putCart(int productNum);
	
	/* 주문 내역 페이지에서 주문 취소 클릭시 - 오늘날짜 주문인지 확인 후 취소*/
	public int orderCancel(String orderNum, String orderDate, Model model);
	
	/* 결제 취소 후 상품 테이블 재고 증가*/
	public void stockPlus(String orderNum);
	
	/* 결제 취소 후 주문테이블 주문내역 삭제 */
	public void orderDelete(String orderNum);

	/* 결제 취소 후 배송테이블 배송정보 삭제 */
	public void deliveryDelete(String orderNum);

	/* 메인 화면에 로드시 베스트 판매상품 셋팅, 주간/일간 버튼 눌렀을 때*/
	public void bestSales(String term);



	

	

}
