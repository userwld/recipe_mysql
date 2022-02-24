
/* 상품 상세 페이지 - 수량 plus or minus 버튼 눌렀을 때 (1~99까지의 수량만 가능)*/
function calcOrder(oper){
	orderCount = $('#orderCount').val();
	
	if(oper == '+'){
		if(orderCount >= 99) orderCount = 99;
		else orderCount++;	
	}else{
		if(orderCount <= 1) orderCount = 1;
		else orderCount--;
	}
	
	$('#orderCount').val(orderCount);
}
 
/* 수량 직접 입력할때 (0~99까지만 입력가능) --> readonly로 수량직접 입력 막은 상태 / 해제하면 작동 */
function setCount(){
	orderCount = $('#orderCount').val();
	if(orderCount < 0) orderCount = 1;
	if(orderCount > 99) orderCount = 99;
	$('#orderCount').val(orderCount);
}
