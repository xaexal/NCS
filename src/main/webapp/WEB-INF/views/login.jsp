<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="base.jsp" />
<link rel='stylesheet' type='text/css' href='css/member.css' />
<section class='login-form'>
	<h1 style='font-size:54px;color:aqua;'><a href="/" style='text-decoration:none;'>코딩 부트캠프</a></h1>
	<h1>로그인</h1>
	<label id=lblMsg style="color:red;">${msg}</label>
	<form action="/checkuser" id=frmLogin method="post">
		<div class='int-area'>
			<input type="text" name="mobile" id="mobile" autocomplete=off required>
			<label for=mobile>모바일번호</label>
		</div>
		<div class='int-area'>
			<input type="password" name="passcode" id="passcode" autocomplete=off required>
			<label for='passcode'>비밀번호</label>
		</div>
		<div class='btn-area'>
			<button type="submit">Login</button>
		</div>
		<div class='caption'>
			<a href="/">홈으로</a>&nbsp;&nbsp;
			<a href="/findPassword">비밀번호 찾기</a>&nbsp;&nbsp;
			<a href="/signup">회원가입</a>
		</div>
	</form>
</section>
<script>
$(document)
.on('submit','#frmLogin',function(){
	$('#mobile').val($.trim($('#mobile').val()));
	$('#passcode').val($.trim($('#passcode').val()));
	if($('#mobile').val()==''){
		$('#mobile').next('label').addClass('warning');
		setTimeout(function(){
			$('#mobile').removeClass('warning');
		},1500);
		alert('모바일번호를 입력하십시오.');
		return false;
	}
	if($('#passcode').val()==''){
		$('#passcode').next('label').addClass('warning');
		setTimeout(function(){
			$('#passcode').removeClass('warning');
		},1500);
		alert('비밀번호를 입력하십시오');
		return false;
	}
	return true;
})
;
</script>
<jsp:include page="footer.jsp" />