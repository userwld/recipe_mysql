package com.cooking.recipe.product.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cooking.recipe.member.config.PageConfig;
import com.cooking.recipe.member.dto.MemberDTO;
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

	@Override
	public void productList(Model model, int currentPage) {	
		int[] page = PageConfig.setPage(dao.productCount(), currentPage);
		// page[] ={한 페이지당 시작번호, 한 페이지당 끝번호, 페이지당 개수(10), 전체 데이터 개수}
		ArrayList<MemberDTO> productList = dao.selectAll(page[0], page[1]);
		model.addAttribute("list", productList);
		
		String url="/recipe/productListViewProc?currentPage=";
		model.addAttribute("page", PageConfig.getNavi(currentPage, page[2], page[3], url));
		
	}

	@Override
	public void productSearch(Model model, int currentPage, String searchWord) {
		int[] page = PageConfig.setPage(dao.searchCount(searchWord), currentPage);
	
		ArrayList<MemberDTO> searchList = dao.selectSearch(page[0], page[1], searchWord);
		model.addAttribute("list", searchList);
		
		String url="/recipe/productListViewProc?currentPage=";
		model.addAttribute("page", PageConfig.getNavi(currentPage, page[2], page[3], url));
		
	}

	@Override
	public void updateImg(String productImg, String num) {
		int productNum = Integer.parseInt(num);
		dao.updateImg(productImg, productNum);	
	}

	@Override
	public void updateProduct(String productName, String newPrice, String newStock, String num) {
		int price = Integer.parseInt(newPrice);
		int stock = Integer.parseInt(newStock);
		int produceNum = Integer.parseInt(num);
		
		ProductDTO product = new ProductDTO();
		product.setProductNum(produceNum); product.setProductName(productName);
		product.setPrice(price); product.setStock(stock);
		
		dao.updateProduct(product);
	}

	@Override
	public void deleteProduct(String num) {
		int productNum = Integer.parseInt(num);
		dao.deleteProduct(productNum);
	}

	@Override
	public void productViewProc(int productNum, Model model) {
		ProductDTO product = dao.selectProductNum(productNum);
		model.addAttribute("result", product);
	}
	
	/* 레시피 상세페이지에서 밀키트 보러가기 클릭시 레시피명을 포함하는 상품있는지 리턴*/
	@Override	
	public String isExistProduct(String productName) {
		ProductDTO product = dao.selectProductName(productName);
		if(product == null) return "없음";
		else return "있음"; 
	}

	@Override
	public void cartInsert(int productNum, int amount) {
		String id = (String)session.getAttribute("loginId");
		dao.insertCart(productNum, amount, id);
	}

}
