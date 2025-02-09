$(document)
.on('click','#btnSend',function(){
	if($('#mobile').val()=='') {
		alert('모바일번호를 입력하십시오'); return false;
	}
	if($('#email').val()=='') {
		alert('이메일을 입력하십시오'); return false;
	}
	let oParam={}
	oParam['mobile']=$('#mobile').val();
	oParam['email']=$('#email').val();
	$.post('/sendPasscode',oParam,function(data){
		console.log(data)
		if(data!='0'){
			alert('임시비밀번호 생성 실패'); return false;
		}
		document.location="/login";
	},'text')
	return false;
})