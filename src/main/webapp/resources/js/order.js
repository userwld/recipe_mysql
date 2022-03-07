
/* 장바구니 페이지 */

// 수량 조정 버튼 클릭 시 
function calcOrder(oper, i,unitPrice, stock){
	orderCount = $('#orderCount'+i).val();
	
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
			checkList.push(check[i].value);
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

function payProc(totalPrice){
	var name = document.getElementById("name").value;
	var addr1 = document.getElementById("addr1").innerHTML;
	var addr2 = document.getElementById("addr2").value;
		
	if(name == '' || addr2 == '' || addr1.substr(0,3) == '등록된' ){
		alert('배송지 입력칸에 빈 항목이 있습니다. 모두 작성해주세요.');
		return;
	}
	
	var info = {name : name, addr1 : addr1, addr2 : addr2, totalPrice : totalPrice};
	
	$.ajax({		
		url: "payProc", type: "POST",		
		data: JSON.stringify(info), 			
		contentType: "application/json; charset=utf-8", 	
		dataType: "json",
	
		success : function(result){	
			var resURL = result.next_redirect_pc_url;
			location.href=resURL;
		},
		error : function(){
			alert('error!');
		}
	})	
	
	
}
