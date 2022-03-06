<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<head>
<title>상품 상세 페이지</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/product.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css" />

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="<c:url value="/resources/js/product.js" />"></script>
</head>

<center>
	<div id="wrap">
		<div class="content">
			<div class="product_basic">
				<div class="product_img_area">
					<img src="${pageContext.request.contextPath}/resources/images/product/${result.productImg}">
				</div>
				<div class="product_basic_area">
					<ul>
						<li><i class="bi bi-suit-heart"></i>상품명 : <label>${result.productName}</label></li>	
						<li><i class="bi bi-coin"></i>가격 : <label><fmt:formatNumber value="${result.price}" pattern="#,###"/>원</label><label class="product_stock"><i class="bi bi-check-lg"></i>재고 : ${result.stock}개</label></li>
						<li><i class="bi bi-tags"></i>카테고리 : <label>${result.category}</label></li>
						<li><i class="bi bi-box-seam"></i>구성품</li>
						<li><span class="product_con">${result.component}</span></li>
					</ul>
					
					<div class="order_area">
						<div class="container">
						 	 <div class="row row-cols-4">
							    <div class="col"><label class="count_sel">수량 선택</label></div>
							    <div class="col">
							    	<label class="cart_in_btn" id="cartIn" onclick="cartInsert(${result.productNum});">장바구니 담기</label></div> 
							</div>
							 <div class="row row-cols-4">
							 	 <div class="col">
								 	<div class="input-group mb-3">
										  <button class="btn btn-outline-secondary" type="button" id="minusBtn" onclick="calcOrder('-',${result.stock});">-</button>
										  <button class="btn btn-outline-secondary" type="button" id="plusBtn" onclick="calcOrder('+',${result.stock});">+</button>
										  <input type="text" class="form-control" <c:if test="${result.stock == 0}"> value="0" </c:if> value="1" id="orderCount" name="orderCount" onkeyup="setCount();" readonly="readonly">
									</div>
								</div>
							    <div class="col"><button class="order_now_btn" id="orderNow" onclick="orderNow(${result.productNum}, '${result.productName}', ${result.price});">바로 구매</button></div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
				<div class="product_detail">
					<i class="bi bi-chat-dots"></i><label>상품 설명</label>
					<ul>
						<li>${result.info}</li>					
					</ul>
				</div>
			</div>	
		</div>
	
</center>