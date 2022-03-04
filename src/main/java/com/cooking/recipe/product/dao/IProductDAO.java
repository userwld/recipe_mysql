package com.cooking.recipe.product.dao;

import org.springframework.stereotype.Repository;

import com.cooking.recipe.product.dto.ProductDTO;

@Repository
public interface IProductDAO {

	public void insertProduct(ProductDTO product);

}
