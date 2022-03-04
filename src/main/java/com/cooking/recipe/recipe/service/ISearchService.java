package com.cooking.recipe.recipe.service;

import java.util.Map;

import org.springframework.ui.Model;

public interface ISearchService {

	public Map<String, Object> searchRecipe(String searchWord);

	public void recipeDetail(String recipeName, Model model);
	
	
	
	
}
