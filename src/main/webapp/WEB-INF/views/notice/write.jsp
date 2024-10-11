<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../base.jsp" />
<!-- <link rel="stylesheet" type="text/css"  href="/css/member.css"> -->
<style>/* 
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
} */
.navbar-brand {
	color:white;
}
section {
	display:flex; justify-content:center; align-items:center;height:100vh;
}
.auto td {
	border:1px solid black;
}
.L {
	text-align:left;
}

</style>
<header>
<h1>코딩 부트캠프</h1>	
</header>
<nav class='navbar navbar-expand-lg navbar_dark bg-primary'>
	<table style='width:100%'>
	<tr>
		<td style='width:50%;text-align:left;'>
<c:if test="${sessionScope.level == '0'}">	
			<a class='navbar-brand' style='color:white;' href='/drillViewT'>과제진행</a>
			<a class="navbar-brand" style='color:white;' href='/courseT'>과정관리</a>
			<a class="navbar-brand" style='color:white;' href='/drillT'>과제관리</a>
			<a class="navbar-brand" style='color:white;' href='/showRank'>결과보기</a>
</c:if>			
			<a class="navbar-brand" href="/notice/list" style='color:white'>공지사항</a>&nbsp;&nbsp;
			<a class="navbar-brand" href="/freeboard" style='color:white'>자유게시판</a>&nbsp;&nbsp;
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
<section>
<input type=hidden id=id name=id value='${notice.id}'>
<table class='auto spx' style='height:640px;width:640px;margin:0;border-collapse:collapsed;'>
<tr style='height:32px;'>
	<td class="R" style='width:100px'>제목</td>
	<td class="L">
		<c:if test="${mode == 'view'}"> 
		${notice.title}
		</c:if>
		<c:if test="${mode != 'view'}">
		<input type="text" name="title" id="title" value='${notice.title}' size=40>
		</c:if>
	</td>
</tr>
<tr style='height:32px;'>
	<td class="R">작성자</td>
	<td class="L">
		${notice.name}
	<c:if test="${mode != 'new'}">
		<input type=hidden name=member_id id=member_id value='${notice.member_id}'>
	</c:if>
	<c:if test="${mode == 'new'}">
		<input type=hidden name=member_id id=member_id value="${sessionScope['member_id']}">
	</c:if>
	</td>
</tr>
<tr>
	<td class="R">내용</td>
	<td class="L" style='vertical-align:top;'>
		<c:if test="${mode == 'view'}"> 
		<pre>${notice.content}</pre>
		</c:if>
		<c:if test="${mode != 'view'}">
		<textarea id=content name=content rows=21 cols=64>${notice.content}</textarea>
		</c:if>
	</td>
</tr>
<c:if test="${mode != 'view'}">
<tr style='height:32px;'>
	<td class="R">공지종류</td>
	<td class="L" style='text-align:left;'>
		<select name="level" id="level" >
			<option value="20"
	<c:if test="${notice.level == '20'}">
	selected
	</c:if>
			>일반공지</option>
			<option value="0" 
	<c:if test="${notice.level == '0'}">
	selected
	</c:if>
				>내용오픈 공지사항(맨위에 내용보이게 표시)</option>
			<option value="10"
	<c:if test="${notice.level == '10'}">
	selected
	</c:if>
			>맨윗줄공지(목록맨위에 표시)</option>
		</select>
	</td>
</tr>
</c:if>	
<tr style="height:32px">
	<td class="R">상세</td><td style='text-align:left;'>작성시각:${notice.created}<br>수정시각:${notice.updated}</td>
</tr>
<tr style='height:32px;'>
	<td colspan=2 align=center>
<c:if test="${mode != 'view'}">
	<input type="button" class='btn-lg btn-primary' value='등록 ▷' id="btnDone">&nbsp;
</c:if>
<c:if test="${sessionScope['member_id']==notice.member_id}">
	<input type="button" class='btn-lg btn-primary' value='수정 ▷' id="btnUpdate">&nbsp;
</c:if>
<c:if test="${sessionScope['member_id']==notice.member_id}">
	<input type="button" class='btn-lg btn-secondary' value='삭제 ▷' id="btnDelete">
</c:if>
<a href='/notice/list'>목록보기</a>
</td>
</tr>
</table>
</section>
<script src="/js/write.js"></script>
<jsp:include page="../footer.jsp" />