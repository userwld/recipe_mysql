package com.cooking.recipe.recipe;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SearchController {
	
	@RequestMapping(value="/searchProc")
	public String searchProc() {
		return "forward:index?formpath=search";
	}
	
}
