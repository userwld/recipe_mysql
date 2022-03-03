<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url var="root" value="/"/>
<head>

<title>회원가입 / 로그인</title>
<link rel="icon" href="${pageContext.request.contextPath}/resources/images/yoon_ico.ico" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/member.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="<c:url value="/resources/js/member.js" />"></script>
</head>

<c:if test="${not empty msg}">
	<script>alert('${msg}');</script>
</c:if> 

<center>
	<div id="wrap">
		<div class="join_logo"><h2><a href="${root}">Yoon's Recipe</a></h2></div>
		<!-- 회원가입 / 로그인 메인 -->
		<div class="join_content">
			<div class="social">
				<div class="social_label"><label>간편 회원가입</label></div>
				<p><strong>소셜 로그인</strong>을 통해 간단히 가입할 수 있습니다.</p>
				<p>회원가입 후 반드시 <strong>소셜 계정 로그아웃</strong>을 해주세요.</p>
				<div class="social_btn_area">
					<c:if test="${empty modal}"><c:set var="modal" value="no"/></c:if>
					<input type="hidden" value="${modal}" id="modalOpen">
					<button><img src="${pageContext.request.contextPath}/resources/images/member/naver.png" class="naver_btn" title="naver" onclick="socialLogin('naver');"></button>
					<button><img src="${pageContext.request.contextPath}/resources/images/member/kakao.png" class="kakao_btn" title="kakao" onclick="socialLogin('kakao');"></button>
				</div>
			</div>
			<form id="loginForm">
				<div class="member_login">
					<div class="login_label"><label>로그인</label></div>
					
					<div class="login_input_area">
						<div class="input-group flex-nowrap">
						  	<span class="input-group-text" id="addon-wrapping"><img src="${pageContext.request.contextPath}/resources/images/member/user_ico.png"></span>
						 	<input type="text" class="form-control" placeholder="UserId" id ="userId" name="userId">
						</div>
						<div class="input-group flex-nowrap">
							<span class="input-group-text" id="addon-wrapping"><img src="${pageContext.request.contextPath}/resources/images/member/pw_ico.png"></span>
						 	<input type="password" class="form-control" placeholder="UserPassword" id="userPw" name="userPw" >
						</div>
						<label class="loginCheckLabel" id="loginCheck"></label>
						<div class="login_btn_area">
							<button type="button" onclick="loginProc();">Login</button>
						</div>
						
					</div>
				</div>
			</form>
		</div>
		
		
		<!-- 회원가입 modal -->
		<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
		  <div class="modal-dialog">
		  <form id="joinForm" method="post">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="staticBackdropLabel">Yoon's Recipe 회원가입</h5>
		        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
		      </div>
		      <div class="modal-body">
		     
		       	<div class="join_input_area">
		       		<div class="input-group mb-3">
					  <input type="text" class="form-control" id="id" name = "id" placeholder="아이디 입력" maxlength="12">
					  <button class="btn btn-outline-secondary" type="button" onclick="idCheck();">중복확인</button>
					  <input type="hidden" id="authId" value="">
					</div>
					<label id="idLabel">소문자로 시작하며 소문자, 숫자, -_기호만 가능합니다.(5~12자)</label>
					
					<div class="input-group mb-3">
					  <input type="password" class="form-control" placeholder="비밀번호 입력" id="pw" name= "pw" onkeyup="pwCheck();" maxlength="16">
					</div>
					<label id="pwLabel">영문자+숫자 조합 / 특수문자는 -_!@#$%^&*?만 가능합니다.(8~16자)</label>
					 
					<div class="input-group mb-3">
					  <input type="password" class="form-control" placeholder="비밀번호 확인" id="rePw" name="rePw" onkeyup="reCheck();" maxlength="16">
					</div>
					<label id="rePwLabel"></label>
		    		
					<div class="input-group mb-3">
					  <input type="text" class="form-control" placeholder="핸드폰 번호 입력(숫자만 입력)" id="phone" name="phone" onkeyup="phoneCheck();" maxlength="11">
					  <button class="btn btn-outline-secondary" type="button" id="button-addon2" onclick="sendAuth();">인증번호 요청</button>
					</div>
					<label id="phoneLabel" style="color: red;"></label>
					
					<div class="input-group mb-3">
					  <input type="text" class="form-control" placeholder="인증번호를 입력해주세요." onkeyup="authCheck();" id= "authNum" name ="authNum" maxlength="6">
					  <button class="btn btn-outline-secondary" type="button" onclick="phoneAuth();">인증하기</button>
					</div>
					<label id="authLabel"></label>
					<input type="hidden" id="authPhone" value="">
					
					
		       	</div>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
		        <button type="button" class="btn btn-primary" onclick="joinProc();">가입하기</button>
		      </div>
		    </div>
		    </form>
		  </div>
		</div>
		
		
	</div>
	
</center>
