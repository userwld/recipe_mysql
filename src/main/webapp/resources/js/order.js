

/* 장바구니 페이지 */

// 수량 조정 버튼 클릭 시 
function calcOrder(oper, i,unitPrice){
	orderCount = $('#orderCount'+i).val();
	
	if(oper == '+'){
		if(orderCount >= 99) orderCount = 99;
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