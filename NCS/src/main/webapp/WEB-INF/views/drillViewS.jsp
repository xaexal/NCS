<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="base.jsp" />
<input type=hidden id=mobile value="${sessionScope.mobile}">
<header>
<h2>${sessionScope.title}</h2>
</header>
<section>
<nav class='navbar navbar-expand-lg navbar_dark bg-primary'>
	<table style='width:100%;color:white;'>
	<tr>
		<td style='width:50%;text-align:left;'>
			<a class="navbar-brand" href="/courseS" style='color:white'>과정관리</a>&nbsp;&nbsp;
			<a class="navbar-brand" href="/notice/list" style='color:white'>공지사항</a>&nbsp;&nbsp;
			<a class="navbar-brand" href="/freeboard" style='color:white'>자유게시판</a>&nbsp;&nbsp;			
		</td>
		<td style='text-align:right;'>
			<a class="navbar-brand" href='/personal' style='color:white'>${sessionScope.name}</a>&nbsp;&nbsp;
			<a class="navbar-brand" href='/logout' style='color:white'>로그아웃</a>
		</td>
	</tr>
	</table>
</nav>
<input type=hidden id=member_id value="${sessionScope.member_id}">
<input type=hidden id=sid>
<input type=hidden id=lastCreated>
<input type=hidden id=lastUpdated>
		<label id="lblDays"></label>
<select id="selCourse" style="font-size:24px;">
<c:forEach items="${Courses}"  var="crs">
	<option value="${crs.cid}">${crs.title}</option>
</c:forEach>
</select>
<div class='container'>
	<div class=row>
		<div class=col>
			<select id=selDrillType></select>
			<table id=tblExercise style='width:100%;' class=border>
		        <thead><tr style='background-color:white;height:40px;'>
		        	<th align=center>과제</th>
		        	<th align=center>상태&nbsp;&nbsp;&nbsp;&nbsp;
						<input type=button id=btnRefresh value='새로고침'>
					</th>
					</tr>
				</thead>
		        <tbody>
		        </tbody>
		    </table>
		</div>
		<div class=col>
			<table class=table style='width:100%;height:100%;'>
			<tr style='height:40px;background-color:grey;color:white;'><td>과제설명</td></tr>
			<tr><td style='background-color:white;color:blue;'><textarea id=dvComment readonly style='width:100%' rows="20"></textarea></td></tr>
			</table>
		</div>
	</div>
</div>
</section>
<script src="js/drillViewS.js"></script>
<jsp:include page="footer.jsp" />