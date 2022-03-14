package com.cooking.recipe.product;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cooking.recipe.product.service.IProductService;

@Controller
public class ProductController {
	@Autowired IProductService service;
	
	/* 상품이미지 또는 상품명 클릭시 상품상세정보 가져와서 출력*/
	@RequestMapping(value="/productViewProc")
	public String productViewProc(String productNum, Model model) {
		service.productViewProc(Integer.parseInt(productNum), model);
		return "forward:index?formpath=productDetail";
	}
	
	/* 레시피 상세페이지에서 밀키트 보러가기 클릭시 해당 레시피명을 포함하는 상품있는지 확인 */
	@RequestMapping(value = "/isExistProduct", produces = "application/json; charset=utf-8" )
	@ResponseBody
	public Map<String,String> isExistProduct(@RequestBody Map<String,String>map) {
		String productName = map.get("name");
		map.put("msg",service.isExistProduct(productName));
		return map;
	}
	
	/* 관리자 - 상품관리 페이지 셋팅*/	
	@RequestMapping(value="/productListViewProc")
	public String productListViewProc(Model model, @RequestParam(value = "currentPage", required = false, defaultValue = "1")int currentPage, String searchWord) {
		if(searchWord == null) {
			service.productList(model, currentPage);				// 검색 x -> 전체상품 조회
		}else {
			service.productSearch(model,currentPage,searchWord);	// 검색 o -> 검색된 상품명 포함하는 것들만 출력
		}
		
		model.addAttribute("cp", currentPage);
		return "forward:index?formpath=productList";
	}
	
	/* 관리자 - 상품관리 페이지에서 상품추가 */
	@RequestMapping(value = "/insertProduct",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> insertProduct(HttpServletRequest req) {
		Map<String,String> map = new HashMap<String,String>();		
		map.put("msg", service.insertProduct(req));
		return map;
	}
	
	/* 관리자- 상품관리 페이지에서 상품목록에 이미지 클릭해서 변경했을 때 상품DB에 해당 상품 이미지 변경 */
	@RequestMapping(value = "/updateImg", produces = "application/json; charset=utf-8" )
	@ResponseBody
	public void updateImg(@RequestBody Map<String,String>map) {
		service.updateImg(map.get("newImgName"), map.get("num"));
	}
	
	/* 관리자 - 상품관리 페이지에서 상품 정보 수정 후 확정 눌렀을 때 상품DB에 해당 상품 정보 수정*/
	@RequestMapping(value = "/updateProduct", produces = "application/json; charset=utf-8" )
	@ResponseBody
	public void updateProduct(@RequestBody Map<String,String>map) {
		String productName = map.get("productName");
		String newPrice = map.get("price");
		String newStock = map.get("stock");
		String num = map.get("num");
		service.updateProduct(productName, newPrice, newStock, num);
	}
	
	/* 관리자 - 상품관리 페이지에서 상품 삭제버튼 클릭시 상품DB에서 삭제*/
	@RequestMapping(value = "/deleteProduct", produces = "application/json; charset=utf-8" )
	@ResponseBody
	public void deleteProduct(@RequestBody Map<String,String>map) {
		service.deleteProduct(map.get("num"));
	}
	
	
}
