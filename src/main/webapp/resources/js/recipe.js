

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

