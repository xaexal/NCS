<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="base.jsp" />
<style>
/* button {
	width:50%; height: 50px; background: #166cea; color: #fff; font-size: 24px; border: none; border-radius: 25px;
	cursor: pointer;
} */
</style>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
<header>
<h2>${sessionScope.title}</h2>	
</header>
<nav class='navbar navbar-expand-lg navbar_dark bg-primary'>
	<a class="navbar-brand" href="#"></a>
	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo02" aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
	  <span class="navbar-toggler-icon"></span>
	</button>

	<div class="collapse navbar-collapse" id="navbarTogglerDemo02">
		<ul class="navbar-nav mr-auto mt-2 mt-lg-0">
			<li class='nav-item active'>
				<a class="navbar-brand" href="/drillViewS" style='color:white'>과제수행</a>
			</li>
			<li class='nav-item active'>
				<a class="navbar-brand" href="/board:list" style='color:white'>자유게시판</a>&nbsp;&nbsp;
			</li>
			<li class='nav-item active'>
				<a class="navbar-brand" href='/personal' style='color:white'>${sessionScope.name}</a>&nbsp;&nbsp;
			</li>
			<li class='nav-item active'>
				<a class="navbar-brand" href='/logout' style='color:white'>로그아웃</a>
				<input type="hidden" id="member_id" value="${sessionScope.member_id}">
			</li>
		</ul>
	</div>
</nav>
<section>
<div class='container'>
	<div class='row'>
		<table >
		<tr>
			<td valign=top>
				<h2>신청한 교육과정</h2>
				<select id=selApplied name=selApplied size=7 style='width:540px;'
				title="신청한 교육과정을 더블클릭하면 신청이 취소됩니다. 신청한 교육과정은 관리자의 확인 후에 수강중인 과정으로 편성됩니다">
				</select><br><br>
				<h2>현재 수강중인 과정:</h2>
				<select id=selEnrolled name=selEnrolled readonly size=7 style='width:540px;'
				title="신청한 교육과정은 관리자의 확인 후 현재 수강중인 과정으로 편성됩니다. 수강생은 변경할 수 없습니다.">
				</select><br><br>
				<h2>수료한 과정:</h2>
				<select id=selCompleted name=selCompleted readonly size=7 style='width:540px;'
				title="수료한 과정은 관리자에 의해서만 변경될 수 있습니다.">
				</select>
			</td>
			<td valign=top>
				<h2>신청가능한 교육과정</h2>
				<select id=selApplicable name=selApplicable size=10 style='width:540px;height:320px;'
				title="학생은 교육과정을 선택 후 더블클릭하여 신청할 수 있으며, 관리자 확인후 수강중인 과정으로 편성됩니다.">
				</select><br><br>
				<textarea style='width:540px;height:320px;' id="txtCourse"></textarea>
			</td>
		</tr>
		</table>
	</div>
</div>
</section>
<script src="js/courseS.js"></script>
<div id=p_info style='display:none;position:absolute;background-color:aquamarine;border:1px solid cyan;font-size:12px;text-align:left;'></div>
<jsp:include page="footer.jsp" />