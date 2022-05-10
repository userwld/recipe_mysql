<%@page import="java.util.Iterator"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>
<%@page import="com.cooking.recipe.order.dto.OrderDetailDTO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:url var="root" value="/"/>
<head>
<title>주문 내역 페이지</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/order.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css" />

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="<c:url value="/resources/js/order.js" />"></script>

</head>

<c:if test="${not empty msg}">
	<script>alert('${msg}');</script>
</c:if>

<center>
	<div id="wrap">
		<div class="content">
			<div class="order_History_label" align="left">
				<h3>주문 내역 페이지</h3>
				<p><i class="bi bi-exclamation-diamond"></i>주문취소는 <strong>결제 당일</strong>만 가능합니다.</p>
			</div>
			
			<div class="orderHistory">
			<c:choose>
				<c:when test="${not empty result}">
					<%
						LinkedHashMap<String,ArrayList<OrderDetailDTO>> result = (LinkedHashMap<String,ArrayList<OrderDetailDTO>>) session.getAttribute("result");
						Iterator itr = result.keySet().iterator();
						while(itr.hasNext()) {
							String orderNumber = (String)itr.next();
							ArrayList<OrderDetailDTO> order = result.get(orderNumber);
							pageContext.setAttribute("order", order);	
							pageContext.setAttribute("orderNumber", orderNumber);
					%>
							<c:set var="orderNumber" value="${orderNumber}"/>
							<c:set var="orderValue" value="${order}"/>
							<table class="orderHistoryTb">
								<colgroup>
									<col style="width : 20%;"></col>
									<col style="width : auto;"></col>
									<col style="width : 20%;"></col>
								</colgroup>
								<tr class="orderDate">
									<c:set var ="orderDate"><fmt:formatDate value="${orderValue.get(0).orderDate}" pattern="YY/MM/dd"/></c:set>
									<c:set var ="now" value="<%=new Date() %>"/>
									<c:set var ="nowDate"><fmt:formatDate value="${now}" pattern="YY/MM/dd"/></c:set>
									<td><strong>${orderDate}</strong> 주문 내역</td>
									<td>
										<label onclick="location.href='orderDetailViewProc?orderNum='+'${orderNumber}';" class="orderDetail">주문 상세 보기</label>
										<c:if test="${orderDate == nowDate}"><label class="orderCancel" onclick="location.href='orderCancelProc?orderNum='+'${orderValue.get(0).orderNum}'+'&orderDate='+'${orderDate}';">주문 취소</label></c:if>	<!-- 당일 결제만 취소레이블 보여짐 -->
										
							<!--  		orderDate : ${orderDate} | nowDate = ${nowDate} | ${orderDate==nowDate} 확인용 -->
									</td>
									<td class="cart_in_label">장바구니 담기</td>
								</tr>
																			
					<% 		for(OrderDetailDTO dto : order) {
								pageContext.setAttribute("dto", dto);
					%>
								<c:set var="dtoValue" value="${dto}"/>
								<tr>
									<td><img src="${pageContext.request.contextPath}/resources/images/product/${dtoValue.productImg}" onclick="location.href='productViewProc?productNum='+'${dtoValue.productNum}';"></td>
									<td><label class="product_name" onclick="location.href='productViewProc?productNum='+'${dtoValue.productNum}';"> ${dtoValue.productName} </label></td>
									<td class="cart_in_btn_area"><button type="button" class="btn btn-outline-success"onclick="putCart('${dtoValue.productNum}');">담기</button></td>
								</tr>
							<%}%>
							</table>
					<%}	%>
				
				</c:when>
				<c:otherwise>
							<table class="orderHistoryTb">
								<colgroup>
									<col style="width : 20%;"></col>
									<col style="width : auto;"></col>
									<col style="width : 20%;"></col>
								</colgroup>
								<tr class="orderDate">
									<td>주문 내역</td>
									<td></td>
									<td class="cart_in_label">장바구니 담기</td>
								</tr>
								<tr><td colspan="3"><p align="center" style="margin-top:20px;">주문 내역이 존재하지 않습니다.</p></td></tr>
							</table>
				</c:otherwise>
			</c:choose>
			
					
			<div class="margin_bottom"></div>
		</div>
	</div>	
	</div>
</center>