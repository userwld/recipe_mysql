package com.cooking.recipe.recipe.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.ui.Model;

import com.cooking.recipe.product.dto.ProductDTO;

public interface ISearchService {

	public Map<String, Object> searchRecipe(String searchWord);

	public void recipeDetail(String recipeName, Model model);

	public ArrayList<ProductDTO> searchProduct(String searchWord);

	public void bestRecipe(String term);
	
	
	
	
}
