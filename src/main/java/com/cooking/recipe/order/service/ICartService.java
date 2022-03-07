package com.cooking.recipe.order.service;

import org.springframework.ui.Model;

public interface ICartService {

	public boolean cartViewProc(Model model);

	public void cartInsert(int productNum, int amount);

}
