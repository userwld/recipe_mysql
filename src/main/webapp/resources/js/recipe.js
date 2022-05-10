

$(function () {
	onload();
});

function onload(){
	sumTip();
}

/* 레시피 상세페이지 요약에 마우스 오버시 툴팁 나옴 */
function sumTip(){
	$('[data-toggle="tooltip"]').tooltip();
}

/* 밀키트보러가기 버튼 클릭시 레시피명과 일치하는 상품있는지 확인하고 있으면 상품검색결과로 이동 */
function isExistProduct(recipeName){
	var info= {name : recipeName};
	
	$.ajax({
	url: "isExistProduct", type:"POST",
	data: JSON.stringify(info),	
	contentType: "application/json; charset=utf-8",
	dataType: "json", 

	success : function(result){			// 같은 이름을 가진 상품이 여러개 존재할 수도 있으므로 상품검색결과로 이동
		if(result.msg == '없음') alert(recipeName+'은(는) 아직 준비된 상품이 없습니다..');
		else location.href='searchProc?sel=product&searchWord='+recipeName;
	},
	error : function(){}
	})		
}