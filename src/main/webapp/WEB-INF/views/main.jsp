<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<head>
<meta charset="UTF-8">
<title>Yoon's Recipe</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="<c:url value="/resources/js/main.js" />"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

</head>
<center>
<div id = "content">
	<div class="slide">
	    <ul>
	      <li><img src="${pageContext.request.contextPath}/resources/images/main/slide1.jpg"></li>
	      <li><img src="${pageContext.request.contextPath}/resources/images/main/slide2.jpg"></li>
	      <li><img src="${pageContext.request.contextPath}/resources/images/main/slide3.jpg"></li>
	      <li><img src="${pageContext.request.contextPath}/resources/images/main/slide4.jpg"></li>
	    </ul>
		</div>

		<div class="search_wrap">
			<div class="search_group">
				<select class="select" data-mdb-filter="true" id="sel">
				  <option value="noSelect">Category</option>
				  <option value="recipe">레시피</option>
				  <option value="product">상품</option> 
				</select>
			
				  <input type="search" class="form-control rounded" placeholder="검색어를 입력하세요." id="searchWord" name="searchWord" onkeypress="inputEnter();"/>
				  <button type="button" class="btn btn-outline-primary" onclick="searchProc();">search</button>
			</div>
		</div>

	<div class="bestRecipe">
		<label class="br_label">Best Recipe | <span class="term">${recipeTerm}</span></label><button class="daily" onclick="bestRecipe('day');">일간</button><button class="weekly" onclick="bestRecipe('week')">주간</button>
		<div class="container" style="max-width: 955px;">
		  <div class="row">
		  <c:choose>
			  	<c:when test="${not empty bestRecipe}">
			  		<c:set var="end" value="${bestRecipe.size() >= 5 ? 5 : bestRecipe.size()}" />
			  		<c:forEach var= "i" begin="0" end="${end-1}" >
			  			 <div class="col-md">
					    	<label>${i+1}</label>
					      	<img src="${bestRecipe.get(i).img}" onclick="location.href='recipeViewProc?recipeName='+'${bestRecipe.get(i).recipeName}';">
					    	<p onclick="location.href='recipeViewProc?recipeName='+'${bestRecipe.get(i).recipeName}';">${bestRecipe.get(i).recipeName}</p>
					    </div>
			  		</c:forEach>
			  		<c:if test="${bestRecipe.size() < 5}">	<!-- 담아온 레시피 검색 리스트가 5개 미만이면 나머지 순위없음으로 채움 -->
			  			<c:forEach var = "i" begin="${bestRecipe.size()+1}" end="5" >
				  			 <div class="col-md">
						    	<label>${i}</label>
						      	<img src="http://file.okdab.com/UserFiles/searching/recipe/061400.jpg" >
						    	<p>아직 순위 없음</p>
						    </div>		
  						</c:forEach>
			  		</c:if>
			  	</c:when>
			  	<c:otherwise>
			  		<c:forEach begin="1" end="5" varStatus="index">
			  			 <div class="col-md">
					    	<label>${index.count}</label>
					      	<img src="http://file.okdab.com/UserFiles/searching/recipe/061400.jpg" >
					    	<p>아직 순위 없음</p>
					    </div>		
  					</c:forEach>
  				</c:otherwise>
 			 </c:choose>
		  </div>
		</div>
	</div>
	
	<div class="bestProduct">
		<label class="bp_label">Best Product | <span class="term" id="term">${term}</span></label><button class="daily" onclick="bestSales('day');">일간</button><button class="weekly" onclick="bestSales('week');">주간</button>
		<div class="container" style="max-width: 955px;">
			  <div class="row">
			  <c:choose>
			  	<c:when test="${not empty sales}">
			  		<c:set var="end" value="${sales.size() >= 5 ? 5 : sales.size()}" />
			  		<c:forEach var= "i" begin="0" end="${end-1}" >
			  			 <div class="col-md">
					    	<label>${i+1}</label>
					      	<img src="${pageContext.request.contextPath}/resources/images/product/${sales.get(i).productImg}" onclick="location.href='productViewProc?productNum='+'${sales.get(i).productNum}';">
					    	<p onclick="location.href='productViewProc?productNum='+'${sales.get(i).productNum}';">${sales.get(i).productName}</p>
					    </div>
			  		</c:forEach>
			  		<c:if test="${sales.size() < 5}">	<!-- 담아온 상품 판매량 리스트가 5개 미만이면 나머지 순위없음으로 채움 -->
			  			<c:forEach var = "i" begin="${sales.size()+1}" end="5" >
				  			 <div class="col-md">
						    	<label>${i}</label>
						      	<img src="${pageContext.request.contextPath}/resources/images/main/slide1.jpg" >
						    	<p>아직 순위 없음</p>
						    </div>		
  						</c:forEach>
			  		</c:if>
			  	</c:when>
			  	<c:otherwise>
			  		<c:forEach begin="1" end="5" varStatus="index">
			  			 <div class="col-md">
					    	<label>${index.count}</label>
					      	<img src="${pageContext.request.contextPath}/resources/images/main/slide1.jpg" >
					    	<p>아직 순위 없음</p>
					    </div>		
  					</c:forEach>
  				</c:otherwise>
 			 </c:choose>
  			</div>
			</div>
		</div>
	</div>
</center>