<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url var="root" value="/"/>

<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css" />
</head>

<center>
	<div id="header_section">
		<div class="logo"><a href="${root}">Yoon's Recipe</a></div>
		<ul class="nav justify-content-end">
		<c:choose>
			<c:when test="${empty loginId}">
				<li class="nav-item"><a href="${root}join">회원가입 / 로그인</a></li>
				<li class="nav-item"><a href="${root}cartViewProc">장바구니</a></li>
				<li class="nav-item"><a href="${root}orderHistoryViewProc">주문내역</a></li>
			</c:when>
			<c:when test="${not empty loginId and loginId eq 'admin' }">
				<li class="nav-item" ><a href="${root}logoutProc">로그아웃</a></li>
				<li class="nav-item"><a href="${root}memberListViewProc">회원관리</a></li>
				<li class="nav-item"><a href="${root}productListViewProc">상품관리</a></li>
			</c:when>
			<c:otherwise>
				<li class="nav-item"><a href="${root}logoutProc">로그아웃</a></li>
				<li class="nav-item"><a href="${root}cartViewProc">장바구니</a></li>
				<li class="nav-item"><a href="${root}orderHistoryViewProc">주문내역</a></li>
			</c:otherwise>
		</c:choose>
	</ul>
	</div>
</center>