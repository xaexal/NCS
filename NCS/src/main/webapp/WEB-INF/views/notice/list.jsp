<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../base.jsp" />
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
 /* Center image should be larger */
        .carousel-item img {
            transition: transform 0.5s ease;
        }

        /* Default image size */
        .carousel-inner img {
            width: 500px;
            height: auto;
        }

        /* Center image size */
        .carousel-item.active img {
            width: 500px;
            height: auto;
            transform: scale(1.2);
        }

        .carousel-inner {
            display: flex;
            justify-content: center;
            align-items: center;
        }
</style>
<!--<link rel="stylesheet" href="member.css">-->
<header>
<h1>코딩 부트캠프</h1>	
</header>
<nav class='navbar navbar-expand-lg navbar_dark bg-primary'>
	<table style='width:100%'>
	<tr>
		<td style='width:50%;text-align:left;'>
			<a class="navbar-brand" href="/freeboard" style='color:white'>자유게시판</a>&nbsp;&nbsp;
		</td>
		<td style='text-align:right;'>
            <a class="navbar-brand" href='/login' style='color:white'>로그인</a>
            <a class="navbar-brand" href='/signup' style='color:white'>회원가입</a>
		</td>
	</tr>
	</table>
</nav>

<h2>공지사항 목록</h2>
<table class='border spx auto'>
<c:if test="${sessionScope['level'] <= 5}">
<tr><td colspan=4 style='text-align:right;border:none;'><a href='/notice/write'>공지 작성</a></td></tr>
</c:if>
<tr>	
	<th style='text-align:center;'>No.</th><th style='width:240px'>작성시각</th>
	<th style='width:500px;'>제목</th><th style='width:200px'>작성자</th>
	<th style='width:100px;'>조회수</th>
</tr>
<c:forEach var="notice" items="${zero}">
<tr id="${notice.id}">
	<td style='text-align:right;'>-</td><td>${notice.updated}</td><td>${notice.title}</td>
	<td>${notice.name}</td>
	<td style='text-align:right;'>${notice.hit}</td>
</tr>
<tr style='height:200px;background-color:yellow;'>
	<td colspan=5 style='text-align:left;vertical-align:top'><pre>${notice.content}</pre></td>
</tr>
</c:forEach>
<c:forEach var="notice" items="${first}">
<tr id="${notice.id}" style='color:red;'>
	<td style='text-align:right;'>-</td><td>${notice.updated}</td><td>${notice.title}</td>
	<td>${notice.name}</td>
	<td style='text-align:right;'>${notice.hit}</td>
</tr>
</c:forEach>
<c:set var="start" value="${start}" />
<c:forEach var="notice" items="${arNotice}">
<tr id="${notice.id}">
	<c:set var="start" value="${start+1}" />
	<td style='text-align:right;'>${start}</td><td>${notice.updated}</td><td>${notice.title}</td>
	<td>${notice.name}</td>
	<td style='text-align:right;'>${notice.hit}</td>
</tr>
</c:forEach>
</table>
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