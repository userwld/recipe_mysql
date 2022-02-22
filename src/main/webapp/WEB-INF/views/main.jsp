<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<head>
<meta charset="UTF-8">
<title>Yoon's Recipe</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">

<script src="<c:url value="/resources/js/main.js" />"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<style>
	
</style>

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
		<select class="select" data-mdb-filter="true">
		  <option value="sel">Category</option>
		  <option value="recipe">레시피</option>
		  <option value="product">상품</option> 
		</select>
		
		<div class="search_group">
		  <input type="search" class="form-control rounded" placeholder="Search" aria-label="Search" aria-describedby="search-addon" />
		  <button type="button" class="btn btn-outline-primary" onclick="location.href='searchProc';">search</button>
		</div>
	</div>
	
	<div class="bestRecipe">
		<label class="br_label">Best Recipe</label><button class="daily">일간</button><button class="weekly">주간</button>
		<div class="container">
		  <div class="row">
		    <div class="col-md">
		    	<label>1</label>
		      	<img src="${pageContext.request.contextPath}/resources/images/main/slide1.jpg" onclick="location.href='${root}';">
		    	<p>스테이크</p>
		    </div>
		      <div class="col-md" >
		      	<label>2</label>
		      	<img src="${pageContext.request.contextPath}/resources/images/main/slide2.jpg" >
		    	<p>스테이크</p>
		    </div>
		     <div class="col-md" >
		     	<label>3</label>
		      	<img src="${pageContext.request.contextPath}/resources/images/main/slide3.jpg">
		    	<p>스테이크</p>
		    </div>
		     <div class="col-md" >
		     	<label>4</label>
		      	<img src="${pageContext.request.contextPath}/resources/images/main/slide4.jpg">
		    	<p>스테이크</p>
		    </div>
		     <div class="col-md">
		     	<label>5</label>
		      	<img src="${pageContext.request.contextPath}/resources/images/main/slide1.jpg">
		    	<p>스테이크</p>
		    </div>
		  </div>
		</div>
	</div>
	
	<div class="bestProduct">
		<label class="bp_label">Best Product</label><button class="daily">일간</button><button class="weekly">주간</button>
		<div class="container">
			  <div class="row">
			    <div class="col-md">
			    	<label>1</label>
			      	<img src="${pageContext.request.contextPath}/resources/images/main/slide1.jpg" >
			    	<p>스테이크</p>
			    </div>
			      <div class="col-md" >
			      	<label>2</label>
			      	<img src="${pageContext.request.contextPath}/resources/images/main/slide2.jpg" >
			    	<p>스테이크</p>
			    </div>
			     <div class="col-md" >
			     	<label>3</label>
			      	<img src="${pageContext.request.contextPath}/resources/images/main/slide3.jpg">
			    	<p>스테이크</p>
			    </div>
			     <div class="col-md" >
			     	<label>4</label>
			      	<img src="${pageContext.request.contextPath}/resources/images/main/slide4.jpg">
			    	<p>스테이크</p>
			    </div>
			     <div class="col-md">
			     	<label>5</label>
			      	<img src="${pageContext.request.contextPath}/resources/images/main/slide1.jpg">
			    	<p>스테이크</p>
			    </div>
			  </div>
			</div>

		</div>
	</div>
</center>