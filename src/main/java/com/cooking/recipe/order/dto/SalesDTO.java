package com.cooking.recipe.order.dto;
	
public class SalesDTO {					// 메인에 베스트 메뉴를 db에서 조회해서 담아오기 위한 DTO
	private int sales;
	private int productNum;
	private String productName;
	private String productImg;
	
	public int getSales() {
		return sales;
	}
	public void setSales(int sales) {
		this.sales = sales;
	}
	public int getProductNum() {
		return productNum;
	}
	public void setProductNum(int productNum) {
		this.productNum = productNum;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductImg() {
		return productImg;
	}
	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}
	
	
	
}
