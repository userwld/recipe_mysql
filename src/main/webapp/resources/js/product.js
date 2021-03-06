
/* 상품 상세 페이지 - 수량 plus or minus 버튼 눌렀을 때 (1~상품재고 수량만 가능)*/
function calcOrder(oper, stock){
	orderCount = $('#orderCount').val();
	
	if(stock <= 0){
		alert('현재 재고가 없습니다.');
		$('#orderCount').val("0");		
		return;
	}
	
	if(oper == '+'){
		if(orderCount >= stock) orderCount = stock;
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

/* 장바구니 담기 클릭시 -> db에 넣고 장바구니 페이지로 이동할것인지 선택창뜸 */
function cartInsert(num){
	var amount = document.getElementById("orderCount").value;
	if(amount <= 0){
		alert("현재 재고가 없습니다.");
		return;
	}
	
	var info = {num : num, amount : amount};
	
	$.ajax({
	url: "cartInsert", type:"POST",
	data: JSON.stringify(info),	
	contentType: "application/json; charset=utf-8",
	dataType: "json"
	
	})	
	.done(function(result){
		if(result.msg == '로그인 필요'){
			location.href='join';
			return;
		}
		
		var moveCart = confirm('장바구니에 담겼습니다. 장바구니로 이동할까요?');
		if(moveCart) location.href='cartViewProc';
		else location.reload();		
	});
}

/* 바로 구매 버튼 클릭시 */
function orderNow(num, name, price){
	var amount = document.getElementById("orderCount").value;
	if(amount <= 0){
		alert('현재 재고가 없습니다.'); return;
	}
	
	location.href='orderNowProc?productNum='+num+"&productName="+name+"&price="+price+"&amount="+amount;

}



/* 상품 관리 페이지 */

/* 상품추가 버튼 클릭시 상품 정보 입력 모달 나옴 */
function insertModal(){
	$('#staticBackdrop').modal('show');
}

/* 가격과 재고 0이상의 숫자만 입력가능 -> 서버 파싱에러 방지 */
function numCheck(num){	
	var reg = /([^0-9])/;
	if(num.value.match(reg) != null || num.value < 0){
		alert('0이상의 숫자만 가능합니다.')
		num.value="";
	}
}
/* 상품정보 모두 입력 후 상품추가버튼 눌렀을 때*/
function insertProduct(){
	$.ajax({
		url: "insertProduct", type:"POST",
		data: $("#insertProductForm").serialize(), 			
		dataType: "json", 
	
		success : function(result){		
			if(result.msg == "등록 성공"){
				modalHide();
				// 상품 추가 후 입력창 모두 지우기
				var eraseInput = document.getElementsByClassName('inputText');
				for(var i=0; i<eraseInput.length; i++){	
					eraseInput[i].value = '';
				}
				location.reload();
			}else{
				alert(result.msg);
			}
		},
		error : function(){
			alert('error!');
		}
	})
}
/* 상품추가 모달 닫기 */
function modalHide(){
	$('#staticBackdrop').modal('hide');
}

/* 상품명 검색 클릭시 */
function productSearch(){
	var searchWord = document.getElementById("searchProduct").value;
	if(searchWord == "") alert('상품명을 입력해주세요');
	else location.href='productListViewProc?searchWord='+searchWord;
}

/* 상품명 검색창에서 엔터 쳤을때 -> productSearch 메소드 호출  */
function productInputEnter(){
	if(window.event.keyCode==13) productSearch();
}

/* 이미지 클릭시 파일선택창 띄우는 메소드 */
function updateImg(i){
	$('.file'+i).click();
}

/* 파일 선택되서 이미지 변경시 이미지 경로 제외하고 이미지 이름만 추출 후 db에 변경요청 */
function updateFileName(i, num){
	var updateImg = $('.file'+i).val().split("\\");
	if(updateImg.length != 0){
		var newImgName = updateImg[updateImg.length-1];
		var info = {newImgName : newImgName, num : num};
		
		$.ajax({
		url: "updateImg", type:"POST",
		data: JSON.stringify(info),	
		contentType: "application/json; charset=utf-8",
		dataType: "json" })
	
		.always(function(){
			location.reload();
		})
	}	
}

/* 상품명 클릭시 readonly 해제 */
function enableModify(i){
	$('.modifyItem'+i).attr('readonly',false);
}

/* 확정 버튼 클릭시 ajax로 정보 수정 후 다시 readonly */
function productModify(i, num){	
	var modifyItems = document.getElementsByClassName('modifyItem'+i);
	var productName = modifyItems[0].value;
	var price = modifyItems[1].value;
	var stock = modifyItems[2].value;
	
	if(productName == "" || price == "" || stock == ""){
		alert('수정할 항목을 모두 채워주세요.');
		return;
	}
	
	$('.modifyItem'+i).attr('readonly',true);
	var info = {num : num, productName : productName, price : price, stock : stock};
		
	$.ajax({
	url: "updateProduct", type:"POST",
	data: JSON.stringify(info),	
	contentType: "application/json; charset=utf-8",
	dataType: "json"})
	
	.always(function(){
		alert(productName + ' 정보가 수정되었습니다.');
		location.reload();
	})
}

/* 삭제 버튼 클릭 시 */
function productDelete(num){
	var info = {num : num};
	
	$.ajax({
	url: "deleteProduct", type:"POST",
	data: JSON.stringify(info),	
	contentType: "application/json; charset=utf-8",
	dataType: "json"})
	
	.always(function(){
		location.reload();
	})
}