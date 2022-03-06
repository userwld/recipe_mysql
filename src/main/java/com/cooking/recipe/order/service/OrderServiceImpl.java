package com.cooking.recipe.order.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cooking.recipe.order.dao.IOrderDAO;

@Service
public class OrderServiceImpl implements IOrderService{
	@Autowired IOrderDAO dao;
	@Autowired HttpSession session;

	@Override
	public void orderNowProc(String productNum, String productName, String price, String amount, Model model) {
		Map<String, Object> map = new HashMap<String,Object>();
		String id = (String)session.getAttribute("loginId");
		map.put("id", id); map.put("productNum", Integer.parseInt(productNum)); map.put("productName", productName);
		map.put("price", Integer.parseInt(price)); map.put("amount", Integer.parseInt(amount));
		
		model.addAttribute("state", "now");
		model.addAttribute("orderInfo", map);
	
	}

}
