package com.cooking.recipe.order.service;

import org.springframework.ui.Model;

public interface IOrderService {

	public void orderNowProc(String productNum, String productName, String price, String amount, Model model);

}
