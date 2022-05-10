<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:url var="root" value="/"/>
<head>
<title>주문 페이지</title>
<link rel="icon" href="${pageContext.request.contextPath}/resources/images/yoon_ico.ico" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/order.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css" />

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js" ></script>
<script src="<c:url value="/resources/js/order.js" />"></script>

</head>

<center>
	<div id="wrap">
		<div class="order_logo"><h2><a href="${root}">Yoon's Recipe</a></h2></div>
		<div class="content">
		<div class="order_label" align="left"><h3>주문 및 배송<i class="bi bi-truck"></i></h3></div>
		
		<div class="orderList">
			<table class="orderListTb">
				<tr><th>주문 상품 목록</th></tr>
				<c:choose>
					<c:when test="${state == 'now'}">	<!-- 바로주문클릭하고 주문페이지 들어왔을 때 -->
						<c:set var="totalPrice" value="${orderInfo.price * orderInfo.amount}"/>
						<tr><td>${orderInfo.productName} | ${orderInfo.amount}개 | <fmt:formatNumber value="${totalPrice}" pattern="#,###"/>원</td></tr>
					</c:when>
					<c:otherwise>	<!-- 장바구니 페이지에서 주문페이지로 들어왔을 때 -->
						<c:set var="totalPrice" value="0"/>
						<c:choose>
							<c:when test="${not empty orderInfo}">
								<c:forEach var="cart" items="${orderInfo}">
									<c:set var="itemPrice" value="${cart.amount * cart.price}"/>
									<c:set var="totalPrice" value="${totalPrice + itemPrice}"/>
									<tr><td>${cart.productName} | ${cart.amount}개 | <fmt:formatNumber value="${itemPrice}" pattern="#,###"/>원</td></tr>
								</c:forEach>
							</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
				<tr><td class="total_price">총 결제 금액 : <fmt:formatNumber value="${totalPrice}" pattern="#,###"/>원</td></tr>	
			</table>
		</div>
		
		<label class="deliverInfo_label">배송지 정보</label>
		<div class="deliverInfo">
			<table class="deliverTb">
				<tr>
					<th>주문자 이름</th>
					<c:choose>
						<c:when test="${not empty memberInfo.name}">
							<td colspan="2"><input type="text" id="name" value="${memberInfo.name}" readonly="readonly" class="name"></td>	
						</c:when>
						<c:otherwise>
							<td><input type="text" id="name" name="name" placeholder="주문자 이름을 입력해주세요." class="name"></td>
						</c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<th>배송지 주소</th>
					<c:choose>
						<c:when test="${not empty memberInfo.addr1}">
							<td class="addr" colspan="2"><label class="addr1" id="addr1">${memberInfo.addr1}</label></td>
						</c:when>
						<c:otherwise>
							<td class="addr" colspan="2"><label class="addr1" id="addr1">등록된 주소가 없습니다. 주소입력 버튼을 눌러주세요.</label></td>
						</c:otherwise>
					</c:choose>						
				</tr>
				<tr>
					<th></th>
					<c:choose>
						<c:when test="${not empty memberInfo.addr2}">
							<td><input type="text" id="addr2" value="${memberInfo.addr2 }" class="addr2"></td>
						</c:when>
						<c:otherwise>
							<td><input type="text" placeholder="상세주소를 입력해주세요. ex) 101동 101호" class="addr2" id="addr2"></td>
						</c:otherwise>
					</c:choose>
					<td class="addrBtn"><button type="button" class="btn btn-outline-info" onclick="daumPost();">주소 입력 및 변경</button></td>
				</tr>
				<tr>
					<th>연락처</th>
					<td colspan="2">${memberInfo.phone}</td>	
				</tr>
			</table>		
		</div>
		
		<label class="pay_label">결제 정보</label>
		<div class="payInfo">
			<table class="payTb">
				<tr>
					<th>총 결제 금액</th>
					<td><label id="total"><fmt:formatNumber value="${totalPrice}" pattern="#,###"/></label>원</td>				
				</tr>
				<tr>
					<th>결제 수단</th>
					<td><input type="radio" checked="checked"><i class="bi bi-credit-card"></i></td>
				</tr>
			</table>
			
			<div class="order_btn_area">
				<button type="button" class="btn btn-primary btn-lg" onclick="location.href='${root}';">취소</button>
				<button type="button" class="btn btn-secondary btn-lg" onclick="payProc(${totalPrice}, '${state}', ${orderInfo.size()});">결제하기</button>
			</div>
			<div class="margin_bottom"></div>
			</div>
		</div>
	</div>
</center>
