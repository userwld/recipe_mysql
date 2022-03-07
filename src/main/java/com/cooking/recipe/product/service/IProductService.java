package com.cooking.recipe.product.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface IProductService {

	public String insertProduct(HttpServletRequest req);

	public void productList(Model model, int currentPage);

	public void productSearch(Model model, int currentPage, String searchWord);

	public void updateImg(String productImg, String num);

	public void updateProduct(String productName, String newPrice, String newStock, String num);

	public void deleteProduct(String num);

	public void productViewProc(int productNum, Model model);

	public String isExistProduct(String productName);

}
