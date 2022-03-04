package com.cooking.recipe.recipe.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ISearchDAO {

	public void insertView(String recipeName);

	public int isExistView(@Param("recipeName")String recipeName,@Param("nowDate")String nowDate);

	public void updateView(@Param("recipeName")String recipeName,@Param("nowDate")String nowDate);

}
