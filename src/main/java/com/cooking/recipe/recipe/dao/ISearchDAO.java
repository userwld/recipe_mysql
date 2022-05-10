package com.cooking.recipe.recipe.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cooking.recipe.product.dto.ProductDTO;
import com.cooking.recipe.recipe.dto.SearchDTO;

@Repository
public interface ISearchDAO {

	public void insertView(@Param("recipeName")String recipeName, @Param("img")String img);

	public int isExistView(@Param("recipeName")String recipeName,@Param("nowDate")String nowDate);

	public void updateView(@Param("recipeName")String recipeName,@Param("nowDate")String nowDate);

	public ArrayList<ProductDTO> searchProduct(String searchWord);

	public ArrayList<SearchDTO> selectBestRecipe(@Param("term")String term);

}
