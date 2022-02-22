<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>   

<link rel="icon" href="${pageContext.request.contextPath}/resources/images/yoon_ico.ico" />

<body>
	<c:choose>
		<c:when test="${formpath == 'main'}">
			<div id="header_section">
				<%@ include file="common/header.jsp" %>
			</div>
		</c:when>
		<c:otherwise>
			<div id="header_section">
				<%@ include file="common/header2.jsp" %>
			</div>
		</c:otherwise>
	</c:choose>
		<c:import url="/${formpath }" /> 
		
		<div id="footer_section">
			<%@ include file="common/footer.jsp" %>
		</div>
</body>
