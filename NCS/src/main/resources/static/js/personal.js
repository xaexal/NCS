$(document)
.on('submit','#frmPersonal',function(){
	if($('input[name=gender]:checked').val()===undefined){
		alert('성별을 선택하시오');
		return false;
	}
	if($('#email').val()==''){
		alert('이메일주소를 입력하십시오. 비밀번호 확인과 모바일번호의 본인확인에 사용될 수 있습니다.'); return false;
	}
	if($('#passcode').val()==''){
		alert('본인 확인을 위한 (로그인)비밀번호확인을 입력하십시오.'); return false;
	}
	return true;
})
.on('click','#btnAddress',function(){
    new daum.Postcode({
        oncomplete: function(data) {
			console.log(data);
            $('#address').val(data.address).focus();
        }
    }).open();
    return false;
})