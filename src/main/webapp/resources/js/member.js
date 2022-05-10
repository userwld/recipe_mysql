
/* 소셜 로그인 후 join열릴 때 회원가입 modal여는 형식 ---> <script> import순서 주의! --member.js가 가장 늦게 로드! */
$(document).ready(function(){	
    var modal = document.getElementById('modalOpen').value;
    if(modal == 'yes'){
		$('#staticBackdrop').modal('show');	
	}
});

/* 회원가입 모달 닫는 메소드 */
function modalHide(){
	$('#staticBackdrop').modal('hide');
}


/* 카카오 로그인 버튼 클릭시 서버에서 url반환받아서 카카오 로그인창 여는 메소드 */
function socialLogin(page){		
	$.ajax({		
		url: "getUrl?page="+page, type: "GET",				
		contentType: "application/json; charset=utf-8", 	
		dataType: "json", 
	
		success : function(result){		
			location.href=result.url;
		},
		error : function(){
			alert('error!');
		}
	})	
}

function idCheck(){
	var i = document.getElementById("id").value;		
	var info = {id : i};
	
	$.ajax({		
		url: "isExistId", type: "POST",		
		data: JSON.stringify(info), 			
		contentType: "application/json; charset=utf-8", 	
		dataType: "json",
	
		success : function(result){		
			$('#idLabel').text(result.msg);	
			if(result.msg == "사용 가능한 아이디 입니다."){
				$('#id').prop('readonly', true);
				$('#idLabel').css("color","green");
				$('#authId').val("yes");
			}else{
				$('#authId').val("no");
				$('#idLabel').css("color","red");
			}

		},
		error : function(){
			$('#idLabel').text('error');
		}
	})	

}

function pwCheck(){
	var pw = document.getElementById("pw").value;
	var reg = /^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z\d-_!@#$%^&*?]{8,16}$/;
	
	if(!reg.test(pw)){		
		$('#pwLabel').text("영문자+숫자 조합 / 특수문자는 -_!@#$%^&*?만 가능합니다.(8~16자)");		
		$('#pwLabel').css("color","red");
		return false;
	}else{
		$('#pwLabel').text("사용가능한 비밀번호 입니다.");	
		$('#pwLabel').css("color","green");
		return true;
	}
}

function reCheck(){	
	var pw = document.getElementById("pw").value;
	var rePw = document.getElementById("rePw").value;
	if(pw == ""){
		$('#rePwLabel').text("비밀번호 확인을 해주세요.");	
		$('#rePwLabel').css("color","red");
		return false;
	}
	
	if(pw != rePw){
		$('#rePwLabel').text("비밀번호가 일치하지 않습니다.");	
		$('#rePwLabel').css("color","red");
		return false;
	}else{
		$('#rePwLabel').text("");	
		return true;
	}	
	
}

/* 입력형식이 숫자가 아니면 false */
function numCheck(num){	
	var reg = /([^0-9])/;
	if(num.match(reg) != null){
		return false;
	}else{
		return true;
	}	
}

function phoneCheck(){
	var phone = document.getElementById('phone').value;
	
	if(numCheck(phone) == false){
		$('#phoneLabel').text("번호만 입력해 주세요.");
	}else{
		$('#phoneLabel').text("");
	}
}

function sendAuth(){
	var p = document.getElementById("phone").value;
	info = {phone : p};
	
	$.ajax({		
		url: "sendAuth", type: "POST",		
		data: JSON.stringify(info), 			
		contentType: "application/json; charset=utf-8", 	
		dataType: "json",
	
		success : function(result){		
			$('#phoneLabel').text(result.msg);
		},
		error : function(){
			$('#phoneLabel').text('error');
		}
	})	
}

function authCheck(){
	var authNum = document.getElementById('authNum').value;
	
	if(numCheck(authNum) == false){
		$('#authLabel').text("번호만 입력해 주세요.");
	}else{
		$('#authLabel').text("");
	}
}

function phoneAuth(){
	var a = document.getElementById("authNum").value;
	info = {authNum : a};
	
	$.ajax({		
		url: "phoneAuth", type: "POST",		
		data: JSON.stringify(info), 			
		contentType: "application/json; charset=utf-8", 	
		dataType: "json",
	
		success : function(result){		
			$('#authLabel').text(result.msg);
			if(result.msg == '인증 성공'){
				$('#authLabel').css("color","green");
				$('#phone').prop("readonly",true);
				$('#authPhone').val("yes");
			}else{
				$('#authLabel').css("color","red");
				$('#authPhone').val("no");
			}
		},
		error : function(){
			$('#authLabel').text('error');
		}
	})
}

function joinProc(){
	var authId = $('#authId').val();
	var authPhone = $('#authPhone').val();
	
	if(authId != 'yes'){
		$('#idLabel').text("아이디 중복 확인을 해주세요");
		$('#idLabel').css("color","red");
	}
	
	if(authPhone != 'yes'){
		$('#phoneLabel').text("휴대폰 인증을 해주세요");
	}
	
	var pwChk = pwCheck(); var rePwChk = reCheck();
	
	if(pwChk == true && rePwChk == true && authId == 'yes' && authPhone == 'yes'){
		var form = $("#joinForm").serialize();
		
		$.ajax({		
			url: "joinProc", type: "POST",		
			data: form, 			
			dataType: "json", 
		
			success : function(result){		
				if(result.msg == "회원가입 성공"){
					alert('가입을 축하합니다. 회원 로그인 후 이용해주세요.');
					modalHide();
				}else{
					alert('회원가입 실패, 모든 항목을 다시 확인해주세요.');
				}
			},
			error : function(){
				alert('error!');
			}
		})		
	}	
}


function loginProc(){
	var i = document.getElementById('userId').value;
	var p = document.getElementById('userPw').value;
	
	if(i == '' || p == '') $('#loginCheck').text("모든 항목을 입력해주세요.");
	else{
		info = {id : i, pw : p};
		$.ajax({		
			url: "loginProc", type: "POST",		
			data: JSON.stringify(info), 			
			contentType: "application/json; charset=utf-8", 	
			dataType: "json",
		
			success : function(result){		
				if(result.msg == '로그인 성공') location.href='index';
				else $('#loginCheck').text(result.msg);
			},
			error : function(){
				$('#loginCheck').text('error');
			}
		})		
	}
}

function loginInputEnter(){
	if(window.event.keyCode == 13){
		loginProc();
	}
}


/* 회원관리 페이지 - 회원검색 */
function memberSearch(){
	var searchWord = document.getElementById('searchMember').value;
	if(searchWord == '') alert('검색할 아이디를 입력해주세요');
	else location.href='memberListViewProc?searchWord='+searchWord;
	
}

function memberInputEnter(){
	if(window.event.keyCode == 13) memberSearch();
}

