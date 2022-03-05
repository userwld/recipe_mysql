<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
<title>관리자 페이지 - 상품관리</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/product.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css" />

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="<c:url value="/resources/js/product.js" />"></script>
</head>

<center>
	<div id="wrap">
		<div class="content">
			<div class="product_search">
			  <input type="search" class="form-control rounded" placeholder="상품명 입력" id="searchProduct" name="searchProduct"/>
			  <button type="button" class="btn btn-secondary" onclick="productSearch();">상품검색</button>		
			</div>
			
			<div class="product_insert">
				<button type="button" class="btn btn-success" onclick="insertModal();">상품추가</button>
			</div>
			
			<div class="modifyInfo">
				<label><i class="bi bi-exclamation-circle"></i>정보 수정은 <strong>이미지, 상품명, 단가, 재고</strong>만 가능합니다. 이미지는 이미지를 클릭하면 수정 가능합니다.</label>
				<label>다른 항목은 상품명을 클릭하면 수정 가능합니다. 수정 후 <strong>확정</strong>버튼을 눌러주세요.</label>
			</div>
			<table class="productListTb">
				<colgroup>
					<col style="width:20%;">
					<col style="width:15%;">
					<col style="width:15%;">
					<col style="width:10%;">
					<col style="width:auto;">	
					<col style="width:10%;">	
					<col style="width:10%;">	
				</colgroup>
				<tr>
					<th>이미지</th>
					<th>상품명</th>
					<th>단가</th>
					<th>현재고</th>
					<th>판매량</th>
					<th>확정</th>
					<th>삭제</th>
				</tr>
		
					<c:choose>
						<c:when test="${list.isEmpty() == false}">
							<c:forEach var="product" items="${list}" varStatus="index">
								<tr>
									
									<td><input type="file" style="display: none;" class="file${index.count}" onchange="updateFileName(${index.count},${product.productNum});"><img src="${pageContext.request.contextPath}/resources/images/product/${product.productImg}" onclick="updateImg(${index.count});" title="이미지 변경시 클릭"></td>
									<td><input type="text" value="${product.productName}" readonly="readonly" id="modifyItem1" class="modifyItem${index.count}" onclick="enableModify(${index.count});"></td>
									<td><input type="text" value="${product.price}" readonly="readonly" id="modifyItem2" class="modifyItem${index.count}" onkeyup="numCheck(this);"></td>
									<td><input type="text" value="${product.stock}" readonly="readonly" id="modifyItem3" class="modifyItem${index.count}" onkeyup="numCheck(this);"></td>
									<td>${index.count}</td>
									<td><button type="button" class="btn btn-secondary" onclick="productModify(${index.count}, ${product.productNum});">확정</button></td>
									<td><button type="button" class="btn btn-outline-secondary" onclick="productDelete(${product.productNum});">삭제</button></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr><td colspan="7">등록된 상품이 없습니다.</td></tr>
						</c:otherwise>
					</c:choose>											
			</table>
			
			
		<!-- 상품 추가 modal -->
		<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
		  <div class="modal-dialog">
		  <form id="insertProductForm">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="staticBackdropLabel">관리자 - 상품추가</h5>
		        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
		      </div>
		      <div class="modal-body">
	      		<input type="text" class="inputText" placeholder="이미지 경로" id="productImg" name="productImg"><br>
	      		<input type="text" class="inputText" placeholder="상품명" id="productName" name="productName"><br>
	      		<input type="text" class="inputText" placeholder="카테고리" id="category" name="category"><br>
	      		<input type="text" class="inputText" placeholder="가격(숫자만 입력)" id="price" name="price" onkeyup="numCheck(this);"><br>
	      		<input type="text" class="inputText" placeholder="재고(숫자만 입력)" id="stock" name="stock" onkeyup="numCheck(this);"><br>
	      		<input type="text" class="inputText" placeholder="구성품(ex-된장 10g,쌀 100g,...)" id="component" name="component"><br>
	      		<input type="text" class="inputText" placeholder="상세설명" id="info" name="info"><br>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-outline-success" data-bs-dismiss="modal">취소</button>
		       	<button type="button" class="btn btn-success" onclick="insertProduct();">상품추가</button>
		      </div>
		    </div>
		    </form>
		  </div>
		</div>
				
		<div class="productPaging">
			${page}
		</div>
						
		<div class="margin_bottom"></div>
		</div>
	</div>
</center>