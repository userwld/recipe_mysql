<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
<title>레시피 상세 페이지</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/recipe.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="<c:url value="/resources/js/recipe.js" />"></script>

</head>
<center>
	<div id="wrap">
		<div class="content">
			<div class="recipe_summary">
				<div class="recipe_img_area">
					<img src="${pageContext.request.contextPath}/resources/images/main/slide1.jpg">
				</div>
				<div class="recipe_basic_area">
					<c:set var="sumTest" value="요약 잘린내용 여기 나와요"/>
					<ul>
						<li><i class="bi bi-suit-heart-fill"></i>레시피 명 : <label>나물비빔밥</label></li>	<!-- 요약이 너무 길면 ...으로 나오고, 요약 위에 마우스 오버시 tooltip에 나오도록 함 -->
						<li class="summary" data-toggle="tooltip" data-placement="top" title="${sumTest}"><i class="bi bi-info-circle"></i>요약 : <label>채소가 어쩌구저쩌구 신선해요</label></li>
						<li><i class="bi bi-tags"></i>카테고리 : <label></label></li>
						<li><i class="bi bi-alarm"></i>조리시간 : <label></label></li>
						<li><i class="bi bi-123"></i>칼로리 : <label></label></li>
						<li><i class="bi bi-people-fill"></i>양 : <label></label></li>
						<li><i class="bi bi-emoji-laughing"></i>난이도 : <label>중상</label></li>
					</ul>
					<div class="mealKit_btn_area">
						<button class="mealKitBtn" onclick="location.href='productViewProc';">밀키트 보러가기</button>
					</div>
				</div>
			</div>
			
			<div class="ingredient">
				<i class="bi bi-cart-check-fill"></i><label>재료</label>
				<div class="container">
				  <div class="row row-cols-4">
				    <div class="col">재료 1</div>
				    <div class="col">재료 2</div>
				    <div class="col">Column</div>
				    <div class="col">Column</div>
				    <div class="col">Column</div>
				    <div class="col">Column</div>
				    <div class="col">Column</div>
				    <div class="col">Column</div>
				    <div class="col">Column</div>
				    <div class="col">Column</div>
				  </div>
				</div>
				<div class="recipe_detail">
					<i class="bi bi-list-ol"></i><label>상세 과정</label>
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
	
	</div>


</center>