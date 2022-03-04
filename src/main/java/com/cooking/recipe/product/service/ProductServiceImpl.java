package com.cooking.recipe.product.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooking.recipe.product.dao.IProductDAO;
import com.cooking.recipe.product.dto.ProductDTO;

@Service
public class ProductServiceImpl implements IProductService{
	@Autowired IProductDAO dao;
	@Autowired HttpSession session;

	@Override
	public String insertProduct(HttpServletRequest req) {
		String productName = req.getParameter("productName");
		String productImg = req.getParameter("productImg");
		String category = req.getParameter("category");
		String price = req.getParameter("price");
		String stock = req.getParameter("stock");
		String component = req.getParameter("component");
		String info = req.getParameter("info");
		
		if(productName == "" || productImg == "" || category =="" || price == "" ||
				stock == "" || component == "" || info == "") {
			return "모든 항목을 채워주세요";
		}
		
		int p = Integer.parseInt(price); int s = Integer.parseInt(stock);
		
		ProductDTO product = new ProductDTO();
		product.setProductImg(productImg); product.setProductName(productName);
		product.setCategory(category); product.setPrice(p); product.setStock(s);
		product.setComponent(component); product.setInfo(info);
		
		dao.insertProduct(product);
		
		return "등록 성공";
	}

}
