$(document)
.on('click','#btnSubmit',function(){
	if($('#mobile').val()=='') {
		alert('모바일번호를 입력하십시오'); return false;
	}
	if($('#name').val()=='') {
		alert('실명을 입력하십시오'); return false;
	}
	if($('#birthday').val()=='') {
		alert('생년월일을 입력하십시오'); return false;
	}
	if($('#email').val()=='') {
		alert('이메일을 입력하십시오'); return false;
	}
	let gender=$('input[name=gender]:checked').val();
	if(gender!='M' && gender!='F'){
		alert('성별을 선택하십시오'); return false;
	}
	let oParam={}
	oParam['mobile']=$('#mobile').val();
	oParam['name']=$('#name').val();
	oParam['gender']=$('input[name=gender]:checked').val();
	oParam['birthday']=$('#birthday').val();
	oParam['email']=$('#email').val();
	$.post(url_sendPassword,oParam,function(data){
		console.log(data)
		if(data['result']!='0'){
			alert(data['msg']); return false;
		}
		$('#lblMsg').text('비밀번호 ['+data['rec']['passcode']+']');
	},'json')
	return false;
})