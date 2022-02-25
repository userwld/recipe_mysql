<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url var="root" value="/"/>
<head>

<title>회원가입 / 로그인</title>
<link rel="icon" href="${pageContext.request.contextPath}/resources/images/yoon_ico.ico" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/join.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="<c:url value="/resources/js/join.js" />"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

</head>

<center>
	<div id="wrap">
		<div class="join_logo"><h2><a href="${root}">Yoon's Recipe</a></h2></div>
		<!-- 회원가입 / 로그인 메인 -->
		<div class="join_content">
			<div class="social">
				<div class="social_label"><label>간편 회원가입</label></div>
				<p><strong>소셜 로그인</strong>을 통해 간단히 가입할 수 있습니다.</p>
				<div class="social_btn_area">
					<button><img src="${pageContext.request.contextPath}/resources/images/member/naver.png" class="naver_btn" title="naver" onclick="modalOpen(2);"></button>
					<button><img src="${pageContext.request.contextPath}/resources/images/member/kakao.png" class="kakao_btn" title="kakao"></button>
				</div>
			</div>
			<form action="loginProc">
				<div class="member_login">
					<div class="login_label"><label>로그인</label></div>
					
					<div class="login_input_area">
						<div class="input-group flex-nowrap">
						  	<span class="input-group-text" id="addon-wrapping"><img src="${pageContext.request.contextPath}/resources/images/member/user_ico.png"></span>
						 	<input type="text" class="form-control" placeholder="UserName" aria-label="UserName" aria-describedby="addon-wrapping">
						</div>
						<div class="input-group flex-nowrap">
							<span class="input-group-text" id="addon-wrapping"><img src="${pageContext.request.contextPath}/resources/images/member/pw_ico.png"></span>
						 	<input type="password" class="form-control" placeholder="UserPassword" aria-label="UserPassword" aria-describedby="addon-wrapping">
						</div>
						<div class="login_btn_area">
							<button type="submit">Login</button>
						</div>
					</div>
				</div>
			</form>
		</div>
		
		<!-- 회원가입 modal -->
		<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
		  <div class="modal-dialog">
		  <form>
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="staticBackdropLabel">Yoon's Recipe 회원가입</h5>
		        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
		      </div>
		      <div class="modal-body">
		     
		       	<div class="join_input_area">
		       		<div class="input-group mb-3">
					  <input type="text" class="form-control" placeholder="아이디 입력">
					  <button class="btn btn-outline-secondary" type="button" id="button-addon2">중복확인</button>
					</div>
					<label>소문자로 시작하며 소문자, 숫자, -_기호만 가능합니다.(5~12자)</label>
					
					<div class="input-group mb-3">
					  <input type="password" class="form-control" placeholder="비밀번호 입력" >
					</div>
					<label>영문자+숫자 조합 / 특수문자는 -_!@#$%^&*?만 가능합니다.(8~16자)</label>
					 
					<div class="input-group mb-3">
					  <input type="password" class="form-control" placeholder="비밀번호 확인" >
					</div>
					<label></label>
		    		
					<div class="input-group mb-3">
					  <input type="text" class="form-control" placeholder="핸드폰 번호 입력(숫자만 입력)">
					  <button class="btn btn-outline-secondary" type="button" id="button-addon2">휴대폰 인증</button>
					</div>
					<label></label>
					
		       	</div>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
		        <button type="button" class="btn btn-primary" onclick="modalHide();">가입하기</button>
		      </div>
		    </div>
		    </form>
		  </div>
		</div>
		
		
	</div>
	
</center>
