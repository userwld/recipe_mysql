package com.cooking.recipe.product;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cooking.recipe.product.service.IProductService;

@Controller
public class ProductController {
	@Autowired IProductService service;
	
	@RequestMapping(value="/productViewProc")
	public String productViewProc() {
		return "forward:index?formpath=productDetail";
	}
	
	@RequestMapping(value="/productListViewProc")
	public String productListViewProc() {
		return "forward:index?formpath=productList";
	}
	
	@RequestMapping(value = "/insertProduct",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> insertProduct(HttpServletRequest req) {
		Map<String,String> map = new HashMap<String,String>();		
		map.put("msg", service.insertProduct(req));
		return map;
	}
	
}
