<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
					<img src="${pageContext.request.contextPath}/resources/images/main/slide1.jpg">
				</div>
				<div class="product_basic_area">
					<ul>
						<li><i class="bi bi-suit-heart"></i>상품명 : <label>나물비빔밥</label></li>	
						<li><i class="bi bi-coin"></i>가격 : <label>5,900원</label></li>
						<li><i class="bi bi-tags"></i>카테고리 : 밥<label></label></li>
						<li><i class="bi bi-box-seam"></i>구성품</li>
						<li><span class="product_con">이것저것이것저것이것저것이것저것이것저것이것저것이것저것이것저것이것저것이것저것이것저것이것저것이것저것이것저것이것저것이것저것이것저것이것저것이것저것이것저것이것저것이것저것이것저것이것저것이것저것이것저것이것저것이것저것이것저것이것저것이것저것이것저것이것저것이것저것이것저것이것저것이것저것이것저것</span></li>
					</ul>
					
					<div class="order_area">
						<div class="container">
						 	 <div class="row row-cols-4">
							    <div class="col"><label class="count_sel">수량 선택</label></div>
							    <div class="col"><label class="cart_in_btn">장바구니 담기</label></div> 
							</div>
							 <div class="row row-cols-4">
							 	 <div class="col">
								 	<div class="input-group mb-3">
										  <button class="btn btn-outline-secondary" type="button" id="plusBtn" onclick="orderCalc('+');">+</button>
										  <button class="btn btn-outline-secondary" type="button" id="minusBtn" onclick="orderCalc('-');">-</button>
										  <input type="text" class="form-control" value="1" id="orderCount" onkeyup="countSet();" readonly="readonly">
									</div>
								</div>
							    <div class="col"><button class="order_now_btn">바로 구매</button></div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
				<div class="product_detail">
					<i class="bi bi-chat-dots"></i><label>상품 설명</label>
					<ul>
						<li>양지머리로 육수를 낸 후 식혀 기름을 걷어낸 후, 불린 쌀을 넣어 고슬고슬하게 밥을 짓는다.</li>
						<li>안심은 불고기 양념하여 30분간 재워 국물 없이 구워 한 김 식으면 한입 크기로 자른다.</li>
						<li>청포묵은 고기와 비슷한 크기로 잘라 끓는 물에 데쳐내고 계란은 노른자와 흰자를 분리해 지단부쳐 곱게 채썬다.</li>
						<li>콩나물과 숙주, 미나리는 데쳐서 국간장과 참기름으로 간하고, 고사리와 도라지는 참기름을 두른 프라이팬에 살짝 볶아놓는다.</li>
						<li>밥을 참기름으로 무쳐 그릇에 담고 준비한 재료를 고루 얹는다.</li>								
					</ul>
				</div>
			</div>	
		</div>
	
</center>