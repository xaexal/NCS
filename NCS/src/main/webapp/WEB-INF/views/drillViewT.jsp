<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="base.jsp" />
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<input type=hidden id=cid value='${course.cid}'>
<input type=hidden id=seat_cnt value='${course.seat_cnt}'>
<header>
<h2>${title}</h2>
</header>
<nav class='navbar navbar-expand-lg gb-light'>
	<div class='container-fluid'>
		<a class="navbar-brand" href='/courseT'>과정관리</a>
		<a class="navbar-brand" href='/drillT'>과제관리</a>
		<a class="navbar-brand" href='/showRank'>결과보기</a>
		<a class="navbar-brand" href="/list">자유게시판</a>&nbsp;&nbsp;
		<div class='col'>
			<select id=selCourse style='font-size:20px;'>
			<c:forEach items="${Courses}" var="course">
				<option value='${course.cid},${course.seat_cnt},${course.col_cnt}' style='font-size:20px;'>
					${course.title} [${course.period1}~${course.period2}]</option>
			</c:forEach>
			</select>
		</div>
		<label id="lblDays"></label>
		<a class="navbar-brand" href='/personal'>${sessionScope.name}</a>
		&nbsp;&nbsp;<a class="navbar-brand" href='/logout'>로그아웃</a>
	</div>
</nav>
<section>
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
<script src="js/drillViewT.js"></script>
<jsp:include page="footer.jsp" />