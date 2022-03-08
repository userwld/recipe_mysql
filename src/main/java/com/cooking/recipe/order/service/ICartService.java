package com.cooking.recipe.order.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface ICartService {

	public boolean cartViewProc(Model model);

	public void cartInsert(int productNum, int amount);

	public void itemDelete(int cartNum);

	public boolean updateCart(HttpServletRequest req);

}
