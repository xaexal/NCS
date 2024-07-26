<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="base.jsp" />
<style>
input[type=button] {
	width:50%; height: 50px; background: #166cea; color: #fff; font-size: 24px; border: none; border-radius: 25px;
	cursor: pointer;
}
table,input,select,textarea {
	font-size:28px;
}
tr {
	
}
td:nth-child(1) {
	text-align:right;height:60px;
}
td:nth-child(2) {
	width:20px;
}
td:nth-child(3) {
	text-align:left;
}
input[type=button] {
	width:100%; height: 50px; background: #166cea; color: #fff; font-size: 20px; border: none; border-radius: 25px;
	cursor: pointer;
}
</style>
<link rel="stylesheet" href="{% static 'member.css' %}">
<section class='login-form'>
	<h1 style='font-size:54px;color:aqua;'>{{title}}</h1>
	<h1>비밀번호 찾기</h1>
	<label id=lblMsg style="color:yellow;font-size:24px;">{{msg}}</label>
		{% csrf_token %}
		<input type="hidden" id="level" name="level" value="{{member.level}}">
		<table>
		<tr>
			<td><label for=mobile>모바일번호</label></td><td></td>
			<td><input type="text" name="mobile" id="mobile" autocomplete=off required value='{{member.mobile}}'></td>
		</tr>
		<tr>
			<td><label for='passcode'>실명</label></td><td></td>
			<td><input type="text" name="name" id="name" autocomplete=off required value='{{member.name|default_if_none:""}}'></td>
		</tr>
		<tr>
			<td><label for='gender'>성별</label></td><td></td>
			<td><input type="radio" name="gender" id="male" value='M' 
			{% if member.gender in 'M' %}  checked  {% endif %}>남성
			<input type="radio" name="gender" id="female" value='F' {% if member.gender in "F" %} checked {% endif %}>여성</td>
		</tr>
		<tr>
			<td><label for='birthday'>생년월일</label></td><td></td>
			<td><input type="date" name="birthday" id="birthday" autocomplete=off required value='{{member.birthday|default_if_none:""}}'></td>
		</tr>
		<tr>
			<td><label for='email'>이메일</label></td><td></td>
			<td><input type="text" name="email" id="email" autocomplete=off required value='{{member.email|default_if_none:""}}'></td>
		</tr>
		<tr>
			<td></td>
			<td colspan=2 align=center><input type="button" value='입력완료' id="btnSubmit"></td>
		</tr>
		<tr>
			<td></td>
			<td colspan=2 class='caption' style="text-align:center;"><a href="{% url 'ncs:login' %}">로그인</a>&nbsp;&nbsp;
			<a href="{% url 'ncs:signup' %}">회원가입</a></td>
		</tr>
		</table>
</section>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="js/findPassword.js"></script>
<jsp:include page="footer.jsp" />