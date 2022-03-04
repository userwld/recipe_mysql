package com.cooking.recipe.recipe;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cooking.recipe.recipe.service.ISearchService;

@Controller
public class SearchController {
	@Autowired ISearchService service;
	
	@RequestMapping(value="/searchProc")
	public String searchProc(String sel, String searchWord, Model model) {
		Map<String,Object> result = new HashMap<String,Object>();
		if(sel.equals("recipe")) {
			result = service.searchRecipe(searchWord);
		}else {
			
		}
		model.addAttribute("searchWord", searchWord);
		model.addAttribute("sel", sel);
		model.addAttribute("result", result);
		return "forward:index?formpath=search";
	}
	
	@RequestMapping(value="/recipeViewProc")
	public String recipeViewProc(String recipeName, Model model) {
		service.recipeDetail(recipeName, model);
		return "forward:index?formpath=recipeDetail";
	}
	
	
	
}
