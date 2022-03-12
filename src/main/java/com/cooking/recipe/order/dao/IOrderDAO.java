package com.cooking.recipe.order.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cooking.recipe.member.dto.MemberDTO;
import com.cooking.recipe.order.dto.DeliveryDTO;
import com.cooking.recipe.order.dto.OrderDTO;
import com.cooking.recipe.order.dto.OrderDetailDTO;
import com.cooking.recipe.order.dto.SalesDTO;

@Repository
public interface IOrderDAO {

	public void updateAddr(MemberDTO member);

	public void insertOrder(OrderDTO order);

	public void updateStock(@Param("productNum")int productNum, @Param("amount")int amount, @Param("oper")String operation);

	public void deleteCart(int cartNum);

	public ArrayList<String> selectOrderNum(String id);

	public  ArrayList<OrderDetailDTO> selectOrderHistory(String id);

	public void insertDelivery(DeliveryDTO delivery);

	public ArrayList<OrderDetailDTO> selectOrderDetail(String orderNum);

	public DeliveryDTO selectDelivery(String orderNum);

	public ArrayList<OrderDTO> selectOrderCancel(String orderNum);

	public void deleteOrder(String orderNum);

	public void deleteDelivery(String orderNum);

	public ArrayList<SalesDTO> selectBestSales(@Param("term")String term);

}
