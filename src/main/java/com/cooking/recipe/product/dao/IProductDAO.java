package com.cooking.recipe.product.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cooking.recipe.member.dto.MemberDTO;
import com.cooking.recipe.product.dto.ProductDTO;

@Repository
public interface IProductDAO {

	public void insertProduct(ProductDTO product);

	public int productCount();

	public ArrayList<MemberDTO> selectAll(@Param("b")int begin, @Param("e")int end);

	public int searchCount(String searchWord);

	public ArrayList<MemberDTO> selectSearch(@Param("b")int begin, @Param("e")int end, @Param("searchWord")String searchWord);

	public void updateImg(@Param("productImg")String productImg, @Param("productNum")int productNum);

	public void updateProduct(ProductDTO product);

	public void deleteProduct(int productNum);

	public ProductDTO selectProductNum(int productNum);
	
	public ProductDTO selectProductName(String productName);
	
	public ArrayList<Integer> selectGroup();
	
	public ArrayList<Integer> selectSales();

	public int selectStock(int productNum);

}
