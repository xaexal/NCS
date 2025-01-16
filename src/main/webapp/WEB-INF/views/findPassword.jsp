<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="base.jsp" />
<link rel="stylesheet" href="css/member.css">
<section class='login-form'>
	<h1 style='font-size:54px;color:aqua;'><a href="/" style='text-decoration:none;'>프로그래밍 연습</a></h1>
	<h1>비밀번호 찾기</h1>
	<label id=lblMsg style="color:red;">${msg}</label>
	<form id=frmFind >
		<div class='int-area'>
			<input type="text" name="mobile" id="mobile" autocomplete=off required>
			<label for=mobile>모바일번호</label>
		</div>
		<div class='int-area'>
			<input type="text" name="email" id="email" autocomplete=off required>
			<label for="email">임시비밀번호를 받을 이메일주소</label>
		</div>
		<div class='btn-area'>
			<button id=btnSend>임시비밀번호 발급</button>
		</div>
		<div class='caption'>
			<a href="/">홈으로</a>&nbsp;&nbsp;
			<a href="/login">로그인</a>&nbsp;&nbsp;
			<a href="/signup">회원가입</a>
		</div>
	</form>
</section>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="js/findPassword.js"></script>
<jsp:include page="footer.jsp" />