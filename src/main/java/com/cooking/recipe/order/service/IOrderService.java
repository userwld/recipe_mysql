package com.cooking.recipe.order.service;

import java.util.Map;

import org.springframework.ui.Model;

public interface IOrderService {

	public boolean orderNowProc(String productNum, String productName, String price, String amount, Model model);

	public boolean orderCart(String[] orderItems, Model model);
	
	public String payProc(Map<String, String> map);
		
	public int payApprove(String pg_token);
	
	public String[] payFail();

	public void addrUpdate();

	public void orderInsert();		

	public void stockUpdate();

	public void ordersInsert();

	public void stocksUpdate();

	public void cartDelete();

	public void orderHistory(Model model);

	public String putCart(int productNum);

	public boolean stockCheck(String state);

	public void orderDetail(String orderNum, Model model);

	public int orderCancel(String orderNum, String orderDate, Model model);
	
	public void stockPlus(String orderNum);
	
	public void orderDelete(String orderNum);

	public void deliveryDelete(String orderNum);



	

	

}
