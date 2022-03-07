package com.cooking.recipe.order.service;

import java.util.Map;

import org.springframework.ui.Model;

public interface IOrderService {

	public boolean orderNowProc(String productNum, String productName, String price, String amount, Model model);

	public String payProc(Map<String, String> map);
	
	public int payApprove(String pg_token);
	
	public String[] payFail();

	public void addrUpdate();

	public void orderInsert();

	public void stockUpdate();

}
