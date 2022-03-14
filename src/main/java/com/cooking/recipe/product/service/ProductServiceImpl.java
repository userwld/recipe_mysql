package com.cooking.recipe.product.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
	
	/* 상품이미지 혹은 상품이름 클릭시 해당 상품의 정보 담아오는 메소드 */
	@Override
	public void productViewProc(int productNum, Model model) {
		ProductDTO product = dao.selectProductNum(productNum);
		model.addAttribute("result", product);
	}
	
	/* 레시피 상세페이지에서 밀키트 보러가기 클릭시 해당 레시피명을 포함하는 상품있는지 확인 후 리턴*/
	@Override	
	public String isExistProduct(String productName) {	// 상품 존재여부만 확인하기 위해 rowNum=1사용(같은 이름의 상품 존재할수있으므로)
		ProductDTO product = dao.selectProductName(productName);
		if(product == null) return "없음";
		else return "있음"; 
	}
		
	/* 관리자 - 상품관리 페이지 셋팅*/
	@Override
	public void productList(Model model, int currentPage) {	
		int[] page = PageConfig.setPage(dao.productCount(), currentPage);
		// page[] ={한 페이지당 시작번호, 한 페이지당 끝번호, 페이지당 개수(10), 전체 데이터 개수}
		ArrayList<MemberDTO> productList = dao.selectAll(page[0], page[1]);
		model.addAttribute("list", productList);
		model.addAttribute("sales", productSales());
		
		String url="/recipe/productListViewProc?currentPage=";
		model.addAttribute("page", PageConfig.getNavi(currentPage, page[2], page[3], url));	
	}

	/* 관리자 - 상품관리 페이지에서 상품명으로 검색했을 때 상품관리 페이지 셋팅*/
	@Override
	public void productSearch(Model model, int currentPage, String searchWord) {
		int[] page = PageConfig.setPage(dao.searchCount(searchWord), currentPage);
	
		ArrayList<MemberDTO> searchList = dao.selectSearch(page[0], page[1], searchWord);
		model.addAttribute("list", searchList);
		model.addAttribute("sales", productSales());
		
		String url="/recipe/productListViewProc?currentPage=";
		model.addAttribute("page", PageConfig.getNavi(currentPage, page[2], page[3], url));
	}
	
	/* 관리자 - 상품관리 페이지 셋팅시 상품번호에 따른 판매량을 주문테이블에서 구해서 key-상품번호 : value-상품의총판매량 으로 반환 */
	private Map<Integer,Integer> productSales(){
		Map<Integer,Integer> sales = new HashMap<Integer,Integer>();
		ArrayList<Integer> productNum = dao.selectGroup();
		ArrayList<Integer> groupSales = dao.selectSales();
		
		if(productNum.size() != 0) {
			for(int i=0; i < productNum.size(); i++) {
				sales.put(productNum.get(i), groupSales.get(i));
			}
		}
		return sales;
	}

	/* 관리자 - 상품관리 페이지에서 상품추가 */
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
	
	/* 관리자- 상품관리 페이지에서 상품목록에 이미지 클릭해서 변경했을 때 상품DB에 해당 상품 이미지 변경 */
	@Override
	public void updateImg(String productImg, String num) {
		int productNum = Integer.parseInt(num);
		dao.updateImg(productImg, productNum);	
	}

	/* 관리자 - 상품관리 페이지에서 상품 정보 수정 후 확정 눌렀을 때 상품DB에 해당 상품 정보 수정*/
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

	/* 관리자 - 상품관리 페이지에서 상품 삭제버튼 클릭시 상품DB에서 삭제*/
	@Override
	public void deleteProduct(String num) {
		int productNum = Integer.parseInt(num);
		dao.deleteProduct(productNum);
	}
		

	

}
