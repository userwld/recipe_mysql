package com.cooking.recipe.order.dto;

public class OrderDetailDTO extends OrderDTO {		// 주문 상세정보 출력 위해 주문 테이블 외 상품테이블에서 조인해서 가져오는 항목들 
	private String productImg;
	private int price;
	
	public String getProductImg() {
		return productImg;
	}
	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	
}
