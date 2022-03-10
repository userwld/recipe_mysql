package com.cooking.recipe.order.dto;

public class OrderDetailDTO extends OrderDTO {
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
