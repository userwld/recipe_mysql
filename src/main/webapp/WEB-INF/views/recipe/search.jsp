<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
<title>검색 결과</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/search.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="<c:url value="/resources/js/search.js" />"></script>
</head>
<center>
	<div id="wrap">
		<div class="search_result_label"><h3>검색하신 <label>${searchWord}</label>에 대한 결과입니다.</h3></div>
		<div class="container" style="max-width: 980px;">
			<div class="row">
				<c:choose>
					<c:when test="${sel == 'recipe'}">
						<c:choose>
							<c:when test="${result.recipeName != '없음'}">
								 <div class="col-md">
							      	<img src="${result.img}" onclick="location.href='recipeViewProc?recipeName='+'${result.recipeName}';">
							    	<p onclick="location.href='recipeViewProc?recipeName='+'${result.recipeName}';">${result.recipeName}</p>
						    	</div>
							</c:when>
							<c:otherwise>
									<p class="noResult">레시피 검색 결과가 없습니다. 검색어를 확인해주세요. </p>
							</c:otherwise>
						</c:choose>					
					</c:when>
					
					<c:otherwise>
						<c:choose>
							<c:when test="${result.isEmpty() == false}">
								<c:set var="end" value="${result.size()}"/>
								<c:forEach var = "i" begin="0" end="${end-1}">
									 <div class="col-md">
								      	<img src="${pageContext.request.contextPath}/resources/images/product/${result[i].productImg}" onclick="location.href='productViewProc?productNum='+'${result[i].productNum}';">
								    	<p onclick="location.href='productViewProc?productNum='+'${result[i].productNum}';">${result[i].productName}   / 주문가능수량 : ${result[i].stock}</p>
							    	</div>
								</c:forEach>		<!-- 한줄에 3개씩 넣고 간격 맞추기 위해 3으로 나눠서 0이 아닐경우 빈 div넣어서 간격조정 -->
								<c:if test="${end % 3 != 0 }">
									<c:forEach begin="1" end="${3-end%3 }">
										<div class="col-md"></div>
									</c:forEach>
								</c:if>
							</c:when>
							<c:otherwise>
								<p class="noResult">상품 검색 결과가 없습니다. 검색어를 확인해주세요. </p>
							</c:otherwise>						
						</c:choose>						
					</c:otherwise>
				</c:choose>
			
			</div>
		</div>
	</div>
</center>


