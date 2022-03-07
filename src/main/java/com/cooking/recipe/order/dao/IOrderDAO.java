package com.cooking.recipe.order.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cooking.recipe.member.dto.MemberDTO;
import com.cooking.recipe.order.dto.OrderDTO;

@Repository
public interface IOrderDAO {

	public void updateAddr(MemberDTO member);

	public void insertOrder(OrderDTO order);

	public void updateStock(@Param("productNum")int productNum, @Param("amount")int amount);

}
