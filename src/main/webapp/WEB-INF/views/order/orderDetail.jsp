<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:url var="root" value="/"/>
<head>
<title>주문 상세 페이지</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/order.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css" />

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="<c:url value="/resources/js/order.js" />"></script>

</head>

<center>
	<div id="wrap">
		<div class="content">
			<div class="order_detail_label" align="left"><h3>주문 상세 페이지<i class="bi bi-truck"></i></h3></div>
			
			<div class="orderList">
				<table class="orderListTb">
					<tr><th class="orderHistory"><strong><fmt:formatDate value="${orderDetail.get(0).orderDate}" pattern="yy/MM/dd"/></strong> 주문 내역</th></tr>
					<c:forEach var="detail" items="${orderDetail}">
						<tr><td>${detail.productName} | ${detail.amount}개 | <fmt:formatNumber value="${detail.amount * detail.price }" pattern="#,###"/>원</td></tr>
					</c:forEach>
					<tr><td class="total_price">총 결제 금액 :  <fmt:formatNumber value="${orderDetail.get(0).totalPrice}" pattern="#,###"/>원</td></tr>	
				</table>
			</div>
			
			<label class="deliverInfo_label">주문 정보</label>
			<div class="deliverInfo">
				<table class="deliverTb">
					<tr>
						<th>주문자 이름</th>
						<td>${deliverInfo.name}</td>							
					</tr>
					<tr>
						<th>배송지 주소</th>
						<td class="addr" ><label class="addr">${deliverInfo.addr1}<br>${deliverInfo.addr2} </label></td>					
					</tr>
					<tr>
						<th>연락처</th>
						<td>${deliverInfo.phone}</td>	
					</tr>
					<tr>
						<th>결제 수단</th>
						<td><i class="bi bi-credit-card"></i></td>
					</tr>
				</table>		
			</div>
		
			<div class="margin_bottom"></div>
		</div>
	</div>
	
</center>
