package com.cooking.recipe.recipe;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecipeController {
	
	@RequestMapping(value="/recipeViewProc")
	public String recipeViewProc() {
		return "forward:index?formpath=recipeDetail";
	}
	
}
