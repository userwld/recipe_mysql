package com.cooking.recipe.order.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cooking.recipe.order.dto.CartDTO;

@Repository
public interface ICartDAO {

	public ArrayList<CartDTO> selectCart(String id);

	public void insertCart(@Param("productNum")int productNum, @Param("amount")int amount, @Param("id")String id);
	
	public CartDTO isExistCart(@Param("productNum")int productNum, @Param("id")String id);

	public void updateCart(@Param("productNum")int productNum, @Param("amount")int amount, @Param("id")String id); 


}
