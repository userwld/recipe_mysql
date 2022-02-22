<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url var="root" value="/"/>
<!DOCTYPE html>

<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css" />
</head>

<center>
	<div id="header_section">
		<ul>
			<li class="logo"><a href="${root}">Yoon's Recipe</a></li>
			<li class="menu"><a href="${root}join">회원가입 / 로그인</a></li>
			<li class="menu"><a href="#">장바구니</a></li>
			<li class="menu"><a href="#">주문내역</a></li>
		</ul>
	</div>
</center>