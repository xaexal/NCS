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
			<a class="navbar-brand" href="/notice/list" style='color:white'>공지사항</a>&nbsp;&nbsp;
			<a class="navbar-brand" href="/freeboard" style='color:white'>자유게시판</a>&nbsp;&nbsp;
		</td>
		<td style='text-align:right;'>
            <a class="navbar-brand" href='/login' style='color:white'>로그인</a>
            <a class="navbar-brand" href='/signup' style='color:white'>회원가입</a>
		</td>
	</tr>
	</table>
</nav>
<input type=hidden id=id name=id value='${notice.id}'>
<table class='border auto spx' style='width:80%;height:640px;'>
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
		<input type=hidden name=member_id id=member_id value="${sessionScope['memberID']}">
	</c:if>
	</td>
</tr>
<tr>
	<td class="R">내용</td>
	<td style='text-align:left;vertical-align:top;'>
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
	<td>
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
	<td class="R">상세</td><td class="L">작성시각:${notice.created} / 수정시각:${notice.updated}</td>
</tr>
<tr style='height:32px;'>
	<td colspan=2 align=center>
<c:if test="${mode != 'view'}">
	<input type="button" class='btn-lg btn-primary' value='등록 ▷' id="btnDone">&nbsp;
</c:if>
<c:if test="${sessionScope['memberID']==notice.member_id}">
	<input type="button" class='btn-lg btn-primary' value='수정 ▷' id="btnUpdate">&nbsp;
</c:if>
<c:if test="${sessionScope['memberID']==notice.member_id}">
	<input type="button" class='btn-lg btn-secondary' value='삭제 ▷' id="btnDelete">
</c:if>
</td>
</tr>
</table>
<a href='/notice/list'>목록보기</a>
<script src="/js/write.js"></script>
<jsp:include page="../footer.jsp" />