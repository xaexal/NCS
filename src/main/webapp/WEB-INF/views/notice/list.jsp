<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../base.jsp" />
<style>
input[type=button] {
	width:50%; height: 50px; background: #166cea; color: #fff; font-size: 24px; border: none; border-radius: 25px;
	cursor: pointer;
}
/* table,input,select,textarea {
	font-size:24px;
}
 */input[type=button] {
	width:100%; height: 50px; background: #166cea; color: #fff; font-size: 20px; border: none; border-radius: 25px;
	cursor: pointer;
}
.navbar-brand {
	color:white;
}
section {
	display:flex; justify-content:center; align-items:center;height:100vh;
	vertical-align:top;
}
.auto td {
	border:1px solid black;
}
.L {
	text-align:left;
}
.auto tr:hover {
	background-color:aqua;
	cursor:pointer;
}

</style>
<header>
<h1>프로그래밍 연습</h1>
</header>
<nav class='navbar navbar-expand-lg navbar_dark bg-primary'>
	<table style='width:100%;'>
	<tr>
		<td style='text-align:left;'>
<c:if test="${sessionScope.level == '0'}">
			<a class='navbar-brand' style='color:white;' href='/drillViewT'>과제진행</a>
			<a class="navbar-brand" style='color:white;' href='/courseT'>과정관리</a>
			<a class="navbar-brand" style='color:white;' href='/drillT'>과제관리</a>
			<a class="navbar-brand" style='color:white;' href='/showRank'>결과보기</a>
</c:if>
			<a class="navbar-brand" href="/notice/list" style='color:white'>공지사항</a>&nbsp;&nbsp;
			<a class="navbar-brand" href="/freeboard" style='color:white'>자유게시판</a>&nbsp;&nbsp;
<c:if test="${sessionScope.level == '0'}">
			<select id=selCourse style='font-size:20px;width:64%;'>
			<c:forEach var="course" items="${courses}">
				<option value='${course.cid},${course.seat_cnt},${course.col_cnt}' style='font-size:20px;'>
					${course.title} [${course.period1}~${course.period2}]</option>
			</c:forEach>
			</select>
</c:if>
		</td>
		<td style='text-align:right;'>
<c:if test="${sessionScope.name == null}">
            <a class="navbar-brand" href='/login' style='color:white'>로그인</a>
            <a class="navbar-brand" href='/signup' style='color:white'>회원가입</a>
</c:if>
<c:if test="${sessionScope.name != null}">
            <a class="navbar-brand" href='/personal' style='color:white'>${sessionScope.name}</a>
            <a class="navbar-brand" style='color:white;' href='/logout'>로그아웃</a>
</c:if>
		</td>
	</tr>
	</table>
</nav>
<div class="container" style="padding-top:50px;">
<h1>공지사항</h1>
<table class='table table-bordered table-hover' style='cursor:pointer'>
<tr style='background-color:black;color:white;'>
	<th style='width:50px;'>No.</th><th  style='width:100px;'>작성시각</th>
	<th style='width:300px;'>제목</th><th style='width:60px;'>작성자</th>
	<th style='width:50px;'>조회수</th>
</tr>
<c:forEach var="notice" items="${zero}">
<tr id="${notice.id}">
	<td >-</td><td>${notice.updated}</td><td>${notice.title}</td>
	<td>${notice.name}</td>
	<td  >${notice.hit}</td>
</tr>
<tr >
	<td colspan=5  ><pre>${notice.content}</pre></td>
</tr>
</c:forEach>
<c:forEach var="notice" items="${first}">
<tr id="${notice.id}" >
	<td >-</td><td>${notice.updated}</td><td>${notice.title}</td>
	<td>${notice.name}</td>
	<td >${notice.hit}</td>
</tr>
</c:forEach>
<c:set var="start" value="${start}" />
<c:forEach var="notice" items="${arNotice}">
<tr id="${notice.id}">
	<c:set var="start" value="${start+1}" />
	<td >${start}</td><td>${notice.updated}</td><td>${notice.title}</td>
	<td>${notice.name}</td>
	<td >${notice.hit}</td>
</tr>
</c:forEach>
</table>
</div>
<script>
$(document)
.ready(()=>{})
.on('click','tr',function(){
	console.log($(this).prop('id'));
	if($(this).prop('id')=='') return true;
	let linkstr="/notice/view?id="+$(this).prop('id');
	console.log(linkstr);
	document.location="/notice/view?id="+$(this).prop('id');
	return false;
});
</script>
<jsp:include page="../footer.jsp" />