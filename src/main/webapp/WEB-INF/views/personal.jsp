<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="base.jsp" />
<style>
input[type=button] {
	width:50%; height: 50px; background: #166cea; color: #fff; font-size: 24px; border: none; border-radius: 25px;
	cursor: pointer;
}
table,input,select,textarea {
	font-size:24px;
}
input[type=button] {
	width:100%; height: 50px; background: #166cea; color: #fff; font-size: 20px; border: none; border-radius: 25px;
	cursor: pointer;
}
.navbar-brand {
	color:white;
}
</style>
<!--<link rel="stylesheet" href="member.css">-->
<header>
<h1>${sessionScope.title}</h1>
<nav class='navbar navbar-expand-lg navbar_dark bg-primary'>
	<table style='width:100%;color:white;'>
	<tr>
		<td style='text-align:left;'>
<c:if test="${sessionScope.level == '0'}">
		<a class='navbar-brand' href='/drillViewT'>과제진행</a>
		<a class="navbar-brand" href='/drillT'>과제관리</a>
		<a class="navbar-brand" href='/showRank'>결과보기</a>
</c:if>
<c:if test="${sessionScope.level != '0'}">
		<a class="navbar-brand" href="/drillViewS">과제수행</a>
</c:if>
		<a class="navbar-brand" href="/notice/list" style='color:white'>공지사항</a>&nbsp;&nbsp;
		<a class="navbar-brand" style='color:white' href="/list">자유게시판</a>&nbsp;&nbsp;

		</td>
		<td style='text-align:right;'>
		<a class="navbar-brand" href='/personal'>${sessionScope.name}</a>&nbsp;&nbsp;
		<a class="navbar-brand" href='/logout'>로그아웃</a>
		</td>
	</tr>
	</table>
</nav>
</header>
<section style="margin:auto;width:80%;">
	<h1 style="color:rgb(128, 0, 255);font-size:36px;">나의 등록정보</h1>
	<form action="/member/updateBySelf" id=frmPersonal method="post">
		<input type="hidden" id="member_id" name="member_id" value="${sessionScope.member_id}">
		<input type="hidden" id="level" name="level" value="${member.level}">
		<table align=center>
		<tr>
			<td><label for=mobile>모바일번호</label></td><td></td>
			<td><input type="text" name="mobile" id="mobile" autocomplete=off required value='${member.mobile}'></td>
		</tr>
		<tr>
			<td><label for='name'>실명</label></td><td></td>
			<td><input type="text" name="name" id="name" autocomplete=off required value='${member.name}'></td>
		</tr>
		<tr>
			<td><label for='gender'>성별</label></td><td></td>
			<td><input type="radio" name="gender" id="male" value='M'
			<c:if test="${member.gender == 'M' }">
				checked
			</c:if>
			>남성
			<input type="radio" name="gender" id="female" value='F'
			<c:if test="${member.gender == 'F' }">
				checked
			</c:if>
			>여성</td>
		</tr>
		<tr>
			<td><label for='birthday'>생년월일</label></td><td></td>
			<td><input type="date" name="birthday" id="birthday" autocomplete=off required value='${member.birthday}'></td>
		</tr>
		<tr>
			<td><label for='email'>이메일</label></td><td></td>
			<td><input type="text" name="email" id="email" autocomplete=off value='${member.email}'></td>
		</tr>
		<tr>
			<td><label for='address'>자택주소</label></td><td></td>
			<td><button id=btnAddress>주소찾기</button><br>
				<textarea name="address" id="address" rows=3 cols=24>${member.address}</textarea></td>
		</tr>
		<tr>
			<td><label for='passcode'>비밀번호확인</label></td><td></td>
			<td><input type="password" name="passcode" id="passcode" autocomplete=off></td>
		</tr>
		<tr>
			<td></td>
			<td colspan=2 align=center><input type="button" id=btnSubmit value='수정완료'></td>
		</tr>
		</table>
	</form>
	<div class='caption'><a href='/changePassword'>비밀번호 변경</a></div>
</section>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="js/personal.js"></script>
<jsp:include page="footer.jsp" />