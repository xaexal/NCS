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
		if(data['result']!='0'){
			alert(data['msg']); return false;
		}
		$('#lblMsg').text('비밀번호 ['+data['rec']['passcode']+']');
	},'json')
	return false;
})