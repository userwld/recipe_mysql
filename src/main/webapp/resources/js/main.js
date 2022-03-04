
function searchProc(){
	var sel = document.getElementById("sel").value;
	var searchWord =document.getElementById("searchWord").value;
	
	if(sel == 'noSelect'){
		alert('카테고리를 선택해주세요.'); return;
	}
	
	if(searchWord == ''){
		alert('검색어를 입력해주세요.'); return;
	}

	if(sel == 'recipe'){
		location.href='searchProc?sel='+sel+'&searchWord='+searchWord;
	}else{
		
	}

}