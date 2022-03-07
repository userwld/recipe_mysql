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
	
	@RequestMapping(value="/productViewProc")
	public String productViewProc(String productNum, Model model) {
		service.productViewProc(Integer.parseInt(productNum), model);
		return "forward:index?formpath=productDetail";
	}
	
	@RequestMapping(value="/productListViewProc")
	public String productListViewProc(Model model, @RequestParam(value = "currentPage", required = false, defaultValue = "1")int currentPage, String searchWord) {
		if(searchWord == null) {
			service.productList(model, currentPage);
		}else {
			service.productSearch(model,currentPage,searchWord);
		}
		
		model.addAttribute("cp", currentPage);
		return "forward:index?formpath=productList";
	}
	
	@RequestMapping(value = "/insertProduct",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> insertProduct(HttpServletRequest req) {
		Map<String,String> map = new HashMap<String,String>();		
		map.put("msg", service.insertProduct(req));
		return map;
	}
	
	@RequestMapping(value = "/updateImg", produces = "application/json; charset=utf-8" )
	@ResponseBody
	public void updateImg(@RequestBody Map<String,String>map) {
		service.updateImg(map.get("newImgName"), map.get("num"));
	}
	
	@RequestMapping(value = "/updateProduct", produces = "application/json; charset=utf-8" )
	@ResponseBody
	public void updateProduct(@RequestBody Map<String,String>map) {
		String productName = map.get("productName");
		String newPrice = map.get("price");
		String newStock = map.get("stock");
		String num = map.get("num");
		service.updateProduct(productName, newPrice, newStock, num);
	}
	
	@RequestMapping(value = "/deleteProduct", produces = "application/json; charset=utf-8" )
	@ResponseBody
	public void deleteProduct(@RequestBody Map<String,String>map) {
		service.deleteProduct(map.get("num"));
	}
	
	@RequestMapping(value = "/isExistProduct", produces = "application/json; charset=utf-8" )
	@ResponseBody
	public Map<String,String> isExistProduct(@RequestBody Map<String,String>map) {
		String productName = map.get("name");
		map.put("msg",service.isExistProduct(productName));
		return map;
	}
		
}
