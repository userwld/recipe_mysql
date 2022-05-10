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
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="<c:url value="/resources/js/recipe.js" />"></script>

</head>
<center>
	<div id="wrap">
		<div class="content">
			<div class="recipe_summary">
				<div class="recipe_img_area">
					<img src="${resultBasic.img}">
				</div>
				<div class="recipe_basic_area">
					<c:set var="sumTest" value="${resultBasic.summary}"/>
					<ul>
						<li><i class="bi bi-suit-heart-fill"></i>레시피명 : <label>${resultBasic.recipeName}</label></li>	<!-- 요약이 너무 길면 ...으로 나오고, 요약 위에 마우스 오버시 tooltip에 나오도록 함 -->
						<li class="summary" data-toggle="tooltip" data-placement="top" title="${sumTest}"><i class="bi bi-info-circle"></i>요약 : <label>${resultBasic.summary}</label></li>
						<li><i class="bi bi-tags"></i>카테고리 : <label>${resultBasic.category}</label></li>
						<li><i class="bi bi-alarm"></i>조리시간 : <label>${resultBasic.cookingTime}</label></li>
						<li><i class="bi bi-123"></i>칼로리 : <label>${resultBasic.calorie}</label></li>
						<li><i class="bi bi-people-fill"></i>양 : <label>${resultBasic.qnt}</label></li>
						<li><i class="bi bi-emoji-laughing"></i>난이도 : <label>${resultBasic.level}</label></li>
					</ul>
					<div class="mealKit_btn_area">
						<button class="mealKitBtn" onclick="isExistProduct('${resultBasic.recipeName}');">밀키트 보러가기</button>
					</div>
				</div>
			</div>
			
			<div class="ingredient">
				<i class="bi bi-cart-check-fill"></i><label>재료</label>
				<div class="container">
				  <div class="row row-cols-4">
				  	<c:forEach var="i" begin="0" end="${resultIng.size()-1}">
				  		<c:set var="key" value="ing${i}" />
				  		 <div class="col">${resultIng.get(key)}</div>
				  	</c:forEach>
				  </div>
				</div>
				<div class="recipe_detail">
					<i class="bi bi-list-ol"></i><label>상세 과정</label>
					<ul>
						<c:forEach var="i" begin="0" end="${resultStep.size()-1}">
							<c:set var="key" value="dc${i}"/>
							<li>${resultStep.get(key)}</li>
						</c:forEach>							
					</ul>
				</div>
			</div>	
		</div>
	
	</div>


</center>