
/* 장바구니 페이지 */

$(function (){
	$('#chkAll').attr("checked", true);
	checkAll();
})

// 수량 조정 버튼 클릭 시 
function calcOrder(oper, i,unitPrice, stock){
	orderCount = $('#orderCount'+i).val();
	
	if(orderCount <= 0){
		alert('현재 재고가 없습니다. 이 상품은 제외하고 주문해주세요.');
		return;
	}
	
	if(oper == '+'){
		if(orderCount >= stock) orderCount = stock;
		else orderCount++;	
	}else{
		if(orderCount <= 1) orderCount = 1;
		else orderCount--;
	}
	
	$('#orderCount'+i).val(orderCount);
	
	calcItemPrice(orderCount,i,unitPrice);		// 수량 * 단가 계산해서 금액칸에 표시하는 함수
	check();									// 수량버튼 클릭하고 체크박스 눌렀을때 체크박스에 체크된것만 금액 계산
}

function calcItemPrice(orderCount,i,unitPrice){		
	itemPrice = unitPrice * orderCount;	
	itemPrice = itemPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");		// 금액 세 자리수마다 ,찍혀져서 표시되게 함
	$('.itemPrice'+i).text(itemPrice);	
}


// 전체선택 체크박스 체크시 전체선택/해제
function checkAll(){	// id는 하나, name여러개 가능(배열로) - Elements인것 주의!!
	var chkAll = document.getElementById("chkAll").checked;
	var check = document.getElementsByName("check");
	
	if(chkAll === true){
		for(var i=0; i< check.length; i++){
			check[i].checked = true;
		}
	}else{
		for(var i=0; i< check.length; i++){
			check[i].checked = false;
		}
	}
	
	checkList();		// 체크박스 전체 체크/해제시에 총 금액 계산 위해 호출
	
}
// 전체선택 누른다음 개별체크박스 해제시 -> 전체선택해제구현
function check(){
	var check = document.getElementsByName("check");
	var chkAll = document.getElementById("chkAll");
	
	for(var i=0; i<check.length;i++){
		if(check[i].checked == false){
			chkAll.checked = false;
			break;
		}
	}
	checkList();		// 체크박스 클릭시 체크박스 체크된 것만 금액 계산해서 총 금액 표시위해 호출
}

// 체크박스 체크된 것들의 금액만 배열에 담아서, 품목의 가격(수량*단가) 추출해서 더한 후 총 금액 창에 표시
function checkList(){
	var check = document.getElementsByName("check");		
	var checkList = new Array();
	
	for(var i=0; i<check.length;i++){
		if(check[i].checked == true){
			checkList.push(i+1);
		}
	}
	
	totalPrice = 0;

	for(var i = 0; i < checkList.length; i++){
		itemPrice = $('.itemPrice'+checkList[i]).text().replace(',','');
		itemPrice = Number(itemPrice);
		totalPrice += itemPrice;
	}
	totalPrice = totalPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	$('.totalPrice').text(totalPrice);
}

/* 장바구니 페이지에서 삭제 눌렀을때 */
function itemDelete(num){
	var info = {num:num};
	
	$.ajax({
		url: "itemDelete", type:"POST",
		data: JSON.stringify(info),	
		contentType: "application/json; charset=utf-8",
		dataType: "json"	
	})
	.done(function(){
		location.reload();
	})
}

/* 장바구니 페이지에서 주문하기 눌렀을 때, 모든 항목 다시 cart db에 업데이트 한 후(수량조정됐을수있으므로), 체크된 카트번호만 넘겨서 주문페이지로 가져가는 메소드 호출 */
function orderCart(size){			
	var cartData = new Object();	// 서버에 json형태로 넘겨주기 위한 작업
	var dataArr = new Array();
	
	var productNum =  document.getElementsByName("productNum");
	var amount =  document.getElementsByName("orderCount");
	
	for(var i= 0; i < size; i++){	// 해당 아이디의 장바구니에 담긴 모든 항목들의 상품번호와 양을 담음
		var data = new Object();
				
	    data.productNum = productNum[i].value; data.amount = amount[i].value; 
		dataArr.push(data);
	}
	
	cartData.data = dataArr;
//	console.log(JSON.stringify(cartData));
	
	$.ajax({						
		url: "cartUpdate", type:"POST",
		data: JSON.stringify(cartData),	
		contentType: "application/json; charset=utf-8",
		dataType: "json"	
	})
	.done(function(result){			// 카트 업데이트 후 해당 회원이 체크한 항목들의 카트번호들만 담아서 전달하는 메소드 호출
		if(result == true) orderCheck();
	})
		
}

function orderCheck(){
	var check = document.getElementsByName("check");
	var checkCart = new Array();		// 체크된 카트번호만 들어가있는 배열 -> 체크된것만 주문
	for(var i=0; i < check.length; i++){
		if(check[i].checked == true) checkCart.push(check[i].value);
	}
	
	location.href='orderCartProc?orderItems='+checkCart;
		
}
/* 주문 페이지 */

/* 카카오 주소 */
function daumPost(){		
	new daum.Postcode({
		oncomplete:function(data){	
			var addr = "";
			if(data.userSelectedType === "R"){	
				addr = data.roadAddress;
			}else{	
				addr = data.jibunAddress;
			}
			
			var totalAddr = "(" + data.zonecode + ") " + addr; 		
			$('.addr1').text(totalAddr);
			$('.addr2').focus();
		}
	}).open();
}

/* 결제 */

function payProc(totalPrice, state, itemCount){		// 총 결제금액, 바로주문인지 장바구니에 넘어온것인지 구분, 주문 상품 갯수
	var name = document.getElementById("name").value;
	var addr1 = document.getElementById("addr1").innerHTML;
	var addr2 = document.getElementById("addr2").value;
	
	if(itemCount == 0){
		alert('주문할 상품이 없습니다. 주문목록을 확인해주세요'); 
		return;
	}
		
	if(name == '' || addr2 == '' || addr1.substr(0,3) == '등록된' ){
		alert('배송지 입력칸에 빈 항목이 있습니다. 모두 작성해주세요.');
		return;
	}
		
	var info = {name : name, addr1 : addr1, addr2 : addr2, totalPrice : totalPrice, state:state};
	
	$.ajax({		
		url: "payProc", type: "POST",		
		data: JSON.stringify(info), 			
		contentType: "application/json; charset=utf-8", 	
		dataType: "json",
	
		success : function(result){	
			var resURL = result.msg;
			
			if(result.msg == '재고 없음'){
				alert('죄송합니다. 재고가 소진된 상품이 존재합니다. 메인화면으로 돌아갑니다.');
				resURL = 'index';
			}

			location.href=resURL;
		},
		error : function(){
			alert('error!');
		}
	})	
}


/* 주문내역 페이지 - orderHistory.jsp */

/* 장바구니 담기 버튼 클릭시 - 상품번호로 재고 있는지 db에서 조회 후 있으면 수량 1개로 장바구니 테이블에 담음 */
function putCart(num){
	var info = {num : num};
	$.ajax({		
		url: "putCart", type: "POST",		
		data: JSON.stringify(info), 			
		contentType: "application/json; charset=utf-8", 	
		dataType: "json",
	
		success : function(result){	
			alert(result.msg);
		},
		error : function(){
			alert('error!');
		}
	})	
}
