<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:url var="root" value="/"/>
<head>
<title>장바구니 페이지</title>
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
		<div class="logo"><h2><a href="${root}">Yoon's Recipe</a></h2></div>
		<div class="content">
			<div class="cart_user_label" align="left"><h3><label>XXX</label>고객님의 장바구니<i class="bi bi-cart4"></i></h3></div>
			<div class="cartList" align="left">
				<table class="cartTb">
					<colgroup>
						<col style="width : 13%">
						<col style="width : 25%">
						<col style="width : auto">
						<col style="width : 15%">
						<col style="width : 15%">
						<col style="width : 8%">					
					</colgroup>
					<tr>
						<th><input type="checkbox" class="chkAll" id = "chkAll" value="all" onclick="checkAll();"><label>전체선택</label></th>
						<th colspan="2">상품정보</th>
						<th>수량</th>
						<th>금액</th>
						<th>삭제</th>
					</tr>
					<tr>
						<c:set var="index" value="1"/>
						<c:set var="unitPrice" value="5900"/>
						<c:set var="size" value="2"/>
						<td><input type="checkbox" class="check${index}" value="${index}" name="check" onclick="check();"></td>
						<td><img src="${pageContext.request.contextPath}/resources/images/main/slide4.jpg" onclick = "location.href='productViewProc';"></td>
						<td><span class="product_name" onclick = "location.href='productViewProc';">열무비빔밥</span></td>
						<td>	
							<div class="input-group mb-3">
							  <button class="btn btn-outline-secondary" type="button" id="minusBtn" onclick="calcOrder('-',${index},${unitPrice});">-</button>
							  <button class="btn btn-outline-secondary" type="button" id="plusBtn" onclick="calcOrder('+',${index},${unitPrice});">+</button>
							  <input type="text" class="form-control" value="1" id="orderCount${index}" name="orderCount" onkeyup="setCount();" readonly="readonly">
							</div>
						</td>
						<td><span class="itemPrice${index}"><fmt:formatNumber value="${unitPrice}" pattern="#,###"/></span>원</td>
						<td><button type="button" class="btn btn-outline-danger">삭제</button></td>
					</tr>
					<tr>
						<c:set var="index" value="2"/>
						<c:set var="unitPrice" value="10000"/>
						<td><input type="checkbox" class="check${index}" value="${index}" name="check" onclick="check();"></td>
						<td><img src="${pageContext.request.contextPath}/resources/images/main/slide3.jpg"></td>
						<td><span class="product_name">기가막힌 스테이크</span></td>
						<td>
							<div class="input-group mb-3">
							  <button class="btn btn-outline-secondary" type="button" id="minusBtn" onclick="calcOrder('-',${index},${unitPrice});">-</button>
							  <button class="btn btn-outline-secondary" type="button" id="plusBtn" onclick="calcOrder('+',${index},${unitPrice});">+</button>
							  <input type="text" class="form-control" value="1" id="orderCount${index}" name="orderCount" onkeyup="setCount();" readonly="readonly">
							</div>
						</td>
						<td><span class="itemPrice${index}"><fmt:formatNumber value="${unitPrice}" pattern="#,###"/></span>원</td>
						<td><button type="button" class="btn btn-outline-danger">삭제</button></td>
					</tr>									
				</table>
			</div>
			
			
			<div class="total_price_area">
				<div class="container">
					 <div class="row">
					   <div class="col"><strong>총 결제 금액</strong></div>
					   <div class="col-6"></div>
					   <div class="col"><span class="totalPrice">0</span>원</div>
					</div>
				</div>
			</div>
			
			<div class="order_btn_area">
				<button type="button" class="btn btn-primary btn-lg" onclick="location.href='${root}';">취소</button>
				<button type="button" class="btn btn-secondary btn-lg" onclick="location.href='orderViewProc';">주문하기</button>
			</div>
			
			
			
			
		</div>
	
	</div>
</center>