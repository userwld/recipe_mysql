package com.cooking.recipe.order.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cooking.recipe.order.dao.ICartDAO;
import com.cooking.recipe.order.dto.CartDTO;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class CartServiceImpl implements ICartService{
	@Autowired ICartDAO dao;
	@Autowired HttpSession session;
	
	@Override
	public boolean cartViewProc(Model model) {
		String id = (String)session.getAttribute("loginId");
		if(id == null) return false;
		
		ArrayList<CartDTO> cartList = dao.selectCart(id);
		model.addAttribute("cartList", cartList);
		
		return true;
	}

	@Override
	public boolean cartInsert(int productNum, int amount) {
		String id = (String)session.getAttribute("loginId");
		if(id == null) return false;
		
		if(dao.isExistCart(productNum, id) == null) {
			dao.insertCart(productNum, amount, id);
		}else {
			dao.updateCart(productNum, amount, id);
		}
		return true;
	}

	@Override
	public void itemDelete(int cartNum) {
		dao.deleteItem(cartNum);
	}

	/* 장바구니 페이지에서 주문하기 눌렀을 때, , 주문수량 조정했을 수 있으므로 해당 아이디의 장바구니에 담긴것 전부 업데이트*/
	@Override
	public boolean updateCart(HttpServletRequest req) {
		String id = (String)session.getAttribute("loginId");
		if(id == null)
			return false;

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
	        String line = "";
	        String result = "";
	    
	        while ((line = br.readLine()) != null) {
	            result += line;
	        }       
	//      System.out.println(result);
	        
	        JsonElement element = new JsonParser().parse(result);
	        JsonArray arr = element.getAsJsonObject().get("data").getAsJsonArray();
	//      System.out.println(arr);
	        
	        for(int i=0; i< arr.size(); i++) {
	        	int productNum = Integer.parseInt(arr.get(i).getAsJsonObject().get("productNum").getAsString());
	        	int amount = Integer.parseInt(arr.get(i).getAsJsonObject().get("amount").getAsString());
	        	
	        	dao.updateCart(productNum, amount, id);
	        }
	        	
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}



}
