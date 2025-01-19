<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="base.jsp" />
<style>
/* button {
	width:50%; height: 50px; background: #166cea; color: #fff; font-size: 24px; border: none; border-radius: 25px;
	cursor: pointer;
} */
</style>
<header>
<h2>${sessionScope.title}</h2>
</header>
<nav class='navbar navbar-expand-lg navbar_dark bg-primary'>
	<table style='width:100%'>
	<tr>
		<td style='width:50%;text-align:left;'>
<!-- 			<a class="navbar-brand" href="/notice/list" style='color:white'>공지사항</a>&nbsp;&nbsp;
			<a class="navbar-brand" href="/board:list" style='color:white'>자유게시판</a>&nbsp;&nbsp; -->
            <a class="navbar-brand" href="/drillViewS" style='color:white'>과제수행</a>
		</td>
		<td style='text-align:right;'>
		    <a class="navbar-brand" href='/personal' style='color:white'>${sessionScope.name}</a>&nbsp;&nbsp;
            <a class="navbar-brand" href='/logout' style='color:white'>로그아웃</a>
			<input type="hidden" id="member_id" value="${sessionScope.member_id}">
		</td>
	</tr>
	</table>
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