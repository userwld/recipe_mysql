<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url var="root" value="/"/>

<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<center>
	<div id="header_section">
		<ul>
			<li class="logo"><a href="${root}">Yoon's Recipe</a></li>
			<li class="menu"><a href="${root}join">회원가입 / 로그인</a></li>
			<li class="menu"><a href="${root}cartViewProc">장바구니</a></li>
			<li class="menu"><a href="${root}orderHistoryViewProc">주문내역</a></li>
		</ul>
		<div class="search_wrap">
			<select class="select" data-mdb-filter="true">
			  <option value="sel">Category</option>
			  <option value="recipe">레시피</option>
			  <option value="product">상품</option> 
			</select>
			
			<div class="search_group">
			  <input type="search" class="form-control rounded" placeholder="검색어를 입력하세요." aria-label="Search" aria-describedby="search-addon" />
			  <button type="button" class="btn btn-outline-primary" onclick="location.href='searchProc';">search</button>
			</div>		
		</div>
	</div>
	
</center>
