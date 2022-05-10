package com.cooking.recipe.product.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface IProductService {
	
	/* 상품이미지 혹은 상품이름 클릭시 해당 상품의 정보 담아오는 메소드 */
	public void productViewProc(int productNum, Model model);

	/* 레시피 상세페이지에서 밀키트 보러가기 클릭시 해당 레시피명을 포함하는 상품있는지 확인 후 리턴*/
	public String isExistProduct(String productName);
	
	/* 관리자 - 상품관리 페이지 셋팅*/
	public void productList(Model model, int currentPage);	

	/* 관리자 - 상품관리 페이지에서 상품명으로 검색했을 때 상품관리 페이지 셋팅*/
	public void productSearch(Model model, int currentPage, String searchWord);

	/* 관리자 - 상품관리 페이지에서 상품추가 */
	public String insertProduct(HttpServletRequest req);

	/* 관리자- 상품관리 페이지에서 상품목록에 이미지 클릭해서 변경했을 때 상품DB에 해당 상품 이미지 변경 */
	public void updateImg(String productImg, String num);

	/* 관리자 - 상품관리 페이지에서 상품 정보 수정 후 확정 눌렀을 때 상품DB에 해당 상품 정보 수정*/
	public void updateProduct(String productName, String newPrice, String newStock, String num);

	/* 관리자 - 상품관리 페이지에서 상품 삭제버튼 클릭시 상품DB에서 삭제*/
	public void deleteProduct(String num);



}
