<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url var="root" value="/"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css" />
</head>
<body id ="header_section2">
	<ul>
		<li class="logo2"><a href="${root}">Yoon's Recipe</a></li>
		<li class="menu2"><a href="#">회원가입 / 로그인</a></li>
		<li class="menu2"><a href="#">장바구니</a></li>
		<li class="menu2"><a href="#">주문내역</a></li>
	</ul>
</body>
</html>