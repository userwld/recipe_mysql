/* 검색버튼 클릭시 */
function searchProc(){
	var sel = document.getElementById("sel").value;
	var searchWord =document.getElementById("searchWord").value;
	
	if(sel == 'noSelect'){
		alert('카테고리를 선택해주세요.'); return;
	}
	
	if(searchWord == ''){
		alert('검색어를 입력해주세요.'); return;
	}

	location.href='searchProc?sel='+sel+'&searchWord='+searchWord;
}

/* 검색창에서 엔터키 쳤을 때 -> searchProc 메소드 호출 <form>태그로 묶여있으면서, input 하나면 submit되서 페이지 전환안됨 주의 */
function inputEnter(){
	if(window.event.keyCode==13){
		searchProc();
	}
}