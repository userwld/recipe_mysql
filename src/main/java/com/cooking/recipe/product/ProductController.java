package com.cooking.recipe.product;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductController {

	@RequestMapping(value="/productViewProc")
	public String productViewProc() {
		return "forward:index?formpath=productDetail";
	}
	
	
}
