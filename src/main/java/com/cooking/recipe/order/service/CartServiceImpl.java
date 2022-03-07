package com.cooking.recipe.order.service;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cooking.recipe.order.dao.ICartDAO;
import com.cooking.recipe.order.dto.CartDTO;

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
	public void cartInsert(int productNum, int amount) {
		String id = (String)session.getAttribute("loginId");
		
		if(dao.isExistCart(productNum, id) == null) {
			dao.insertCart(productNum, amount, id);
		}else {
			dao.updateCart(productNum, amount, id);
		}
		

	}

}
