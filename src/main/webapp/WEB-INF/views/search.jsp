<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
<meta charset="UTF-8">
<title>검색 결과</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/search.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<center>
	<div id="wrap">
		<div class="search_result_label"><h3>검색하신 <label>XXX</label>에 대한 결과입니다.</h3></div>
		<div class="container" >
			<div class="row">
				<c:set var="end" value="10"/>
				<c:forEach begin="1" end="${end}">
					 <div class="col-md">
				      	<img src="${pageContext.request.contextPath}/resources/images/main/slide1.jpg">
				    	<p>스테이크</p>
			    	</div>
				</c:forEach>		<!-- 한줄에 3개씩 넣고 간격 맞추기 위해 3으로 나눠서 0이 아닐경우 빈 div넣어서 간격조정 -->
				<c:if test="${end % 3 != 0 }">
					<c:forEach begin="1" end="${3-end%3 }">
						<div class="col-md"></div>
					</c:forEach>
				</c:if>

			</div>
		</div>
	</div>
</center>


