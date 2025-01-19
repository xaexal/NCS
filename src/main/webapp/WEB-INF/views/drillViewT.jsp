<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="base.jsp" />
<input type=hidden id=cid value='${course.cid}'>
<input type=hidden id=seat_cnt value='${course.seat_cnt}'>
<header>
<h2>${sessionScope.title}</h2>
<nav class='navbar navbar-expand-lg navbar_dark bg-primary'>
	<table style='width:100%;color:white;'>
	<tr>
		<td style='text-align:left;'>
		<a class="navbar-brand" style='color:white' href='/courseT'>과정관리</a>
		<a class="navbar-brand" style='color:white' href='/drillT'>과제관리</a>
<!-- 		<a class="navbar-brand" style='color:white' href='/showRank'>결과보기</a>
		<a class="navbar-brand" href="/notice/list" style='color:white'>공지사항</a>&nbsp;&nbsp;
		<a class="navbar-brand" style='color:white' href="/list">자유게시판</a>&nbsp;&nbsp; -->
			<select id=selCourse style='font-size:20px;width:64%;'>
			<c:forEach items="${Courses}" var="course">
				<option value='${course.cid},${course.seat_cnt},${course.col_cnt},${course.period1},${course.period2}' style='font-size:20px;'>
					${course.title} [${course.period1}~${course.period2}]</option>
			</c:forEach>
			</select>
		</td>
		<td style='text-align:right;'>
		<a class="navbar-brand" style='color:white' href='/personal'>${sessionScope.name}</a>
		&nbsp;&nbsp;<a class="navbar-brand" style='color:white' href='/logout'>로그아웃</a>
		</td>
	</tr>
	</table>
</nav>
</header>
<main>
<section>
	<label id="lblDays"></label>
	<input type=hidden id=lastUpdated>
	<div class='container'>
	<div class='row'>
	<div class='col-3'>
		<select id=selExercise size=39 style='width:300px;'></select>
	</div>
	<div class='col-9'>
		<table align=center style='border-collapse:collapse;' id=tblSeat style="width:100px;height:100px;">
		</table>
	</div>
	</div>
</div>
<div id=p_info style='display:none;position:absolute;background-color:aquamarine;border:1px solid cyan;font-size:12px;text-align:left;'></div>
</section>
</main>
<script src="js/common.js"></script>
<script src="js/drillViewT.js"></script>
<jsp:include page="footer.jsp" />