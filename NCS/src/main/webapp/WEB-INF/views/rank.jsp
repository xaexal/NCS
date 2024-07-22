<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="base.jsp" />
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<input type=hidden id=cid value='{{course.cid}}'>
<input type=hidden id=seat_cnt value='{{course.seat_cnt}}'>
<style>
td {
	border:1px solid grey;
}
</style>
<header>
<h2>{{title}}</h2>
</header>
<nav class='navbar navbar-expand-lg gb-light'>
	<div class='container-fluid'>
		<a class="navbar-brand" href='{% url 'ncs:courseT' %}'>과정관리</a>
		<a class="navbar-brand" href='{% url 'ncs:drillT' %}'>과제관리</a>
		<a class="navbar-brand" href='{% url 'ncs:showRank' %}'>결과보기</a>
		<a class="navbar-brand" href="{% url 'board:list' %}">자유게시판</a>&nbsp;&nbsp;
		<div class='col'>
			<select id=selCourse style='font-size:20px;'>
			{% for course in courses %}
				<option value='{{course.cid}},{{course.seat_cnt}},{{course.col_cnt}}' style='font-size:20px;'>
					{{course.title}} [{{course.period1}}~{{course.period2}}]</option>
			{% endfor %}
			</select>
		</div>
		<a class="navbar-brand" href='{% url 'ncs:personal' %}'>{{request.session.name}}</a>
		&nbsp;&nbsp;<a class="navbar-brand" href='{% url 'ncs:logout' %}'>로그아웃</a>
	</div>
</nav>
<section>
<div class='container'>
	<div class='row'>
	<div class='col-10'>
		<table align=center style='border-collapse:collapse;' id=tblRank>
		</table>
	</div>
	</div>
</div>
<script src="js/rank.js"></script>
<jsp:include page="footer.jsp" />