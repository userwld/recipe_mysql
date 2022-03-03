<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
<title>관리자 페이지 - 회원관리</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/member.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="<c:url value="/resources/js/member.js" />"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

</head>

<center>
	<div id="wrap">
		<div class="member_content">
			<div class="member_search">
			  <input type="search" class="form-control rounded" placeholder="아이디 입력" id="searchWord" name="searchWord" />
			  <button type="button" class="btn btn-secondary" onclick="memberSearch();">회원검색</button>
			</div>		
				
			<table class="memberListTb" border="1">
				<colgroup>
					<col style="width: 40%">
					<col style="width: 40%">
					<col style="width: 20%">
				</colgroup>
				<tr>
					<th>아이디</th>
					<th>소셜계정</th>
					<th>삭제</th>
				</tr>
				
				<c:choose>
					<c:when test="${list.isEmpty() == false}">
						<c:forEach var="member" items="${list}">
							<tr>
								<td>${member.id}</td>
								<td>${member.social}</td>
								<td> <button type="button" class="btn btn-secondary" onclick="location.href='memberDelete?deleteId='+'${member.id}';">삭제</button></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr><td colspan="3">등록된 회원이 없습니다.</td></tr>
					</c:otherwise>
				</c:choose>
			</table>
			
		<div class="memberList_paging">
			${page}
		</div>
			
		<div class="margin_bottom"></div>
		</div>
	</div>
</center>