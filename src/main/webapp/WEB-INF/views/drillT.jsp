<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="base.jsp" />
<style>
#selDrill, #selDrillAll {
	width:100%;
	font-size:14px;
}
</style>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" >
<input type=hidden id=classid value='${classcode}'>
<input type=hidden id=seat_cnt value='${seat_cnt}'>
<header>
<h2>${title}</h2>
</header>
<nav class='navbar navbar-expand-lg navbar_dark bg-primary'>
	<table style='width:100%;color:white;'>
	<tr>
		<td style='text-align:left;'>
			<a class='navbar-brand' style='color:white;' href='/drillViewT'>과제진행</a>
			<a class="navbar-brand" style='color:white;' href='/courseT'>과정관리</a>
			<a class="navbar-brand" style='color:white;' href='/drillT'>과제관리</a>
			<a class="navbar-brand" style='color:white;' href='/showRank'>결과보기</a>
			<select id=selCourse style='font-size:20px;width:64%;'>
			<c:forEach var="course" items="${courses}">
				<option value='${course.cid},${course.seat_cnt},${course.col_cnt}' style='font-size:20px;'>
					${course.title} [${course.period1}~${course.period2}]</option>
			</c:forEach>
			</select>
		</td>
		<td style='text-align:right;'>
			<a class="navbar-brand" style='color:white;' href='/personal'>${sessionScope.name}</a>
			&nbsp;&nbsp;<a class="navbar-brand" style='color:white;' href='/logout'>로그아웃</a>
		</td>
	</tr>
	</table>
</nav>
<section>
<div class='container'>
	<div class='row'>
		<div class='col-3'>
			<tr><td><select id=selDrilltype_Exercise style='width:300px;' name="selDrillType"></select></td></tr>
			<select id=selExercise size=30 multiple style='width:300px;'></select>
		</div>
		<div class='col-1' style='display:flex;align-items:center;'>
			<table style='height:100%;'>
			<tr><td valign=middle>
					<button type=button id=btnAppend class='btn btn-success'>Append</button><br>
					<button type=button id=btnRemove class='btn btn-danger'>Remove</button>
			</td></tr></table>
		</div>
		<div class='col-3'>
			<table valign=top>
			<tr><td><select id=selDrilltype_Drill style='width:300px;' name="selDrillType"></select></td></tr>
			<tr><td><select id=selDrill size=30 multiple style='width:300px;'></select></td></tr>
			</table>
		</div>
		<div class='col-1'>
		</div>
		<div class='col-4'>
			<table valign=top class='table'>
			<tr><td>분류</td><td align=left>
			<select id=selDrillType name="selDrillType">
			</select>
			<button id=btnDrillType>분류관리</button>
			</td></tr>
			<tr><td>과제명</td><td align=left><input type=text id=txtDrillName size=40>
								<input type=hidden id=did>
			</td></tr>
			<tr><td>설명</td><td align=left><textarea id=txtComment rows=20 cols=40></textarea></td></tr>
			<tr><td>작성시각</td><td align="left"><label id=created></label></td></tr>
			<tr><td>수정시각</td><td align=left><label id=updated></label></td align=left></tr>
			<tr><td colspan=2 align=center>
				<button id=btnAdd class='btn-sm btn-primary'>Add</button>
				<button id=btnDelete class='btn-sm btn-secondary'>Delete</button>
				<button id=btnClear class='btn-sm btn-warning'>Clear</button></td></tr>
			</table>
		</div>
	</div>
</div>
</section>
<style>
#tblDrillType td:nth-child(1) { text-align:right; }
#tblDrillType td:nth-child(2) { text-align:left; }
</style>
<div id="dvDrillType" style="display:none;">
<h1>과제분류관리</h1>
<table>
<tr>
	<td>
		<select id=selDrillTypeDlg style="width:150px;" size="10" name="selDrillType">
		</select>
	</td>
	<td style="vertical-align:top;">
		<table id="tblDrillType">
		<tr height="40px"><td>분류코드</td><td><input type=text id=dtid size=10 readonly></td></tr>
		<tr height="40px"><td>분류명</td><td><input type=text id=typename size=20></td></tr height="40px">
		<tr  height="40px"><td colspan=2>&nbsp;</td></tr>
		<tr height="40px"><td colspan="2" style="text-align:center;">
			<button id=btnAddDT>등록</button>&nbsp;&nbsp;<button id=btnDelDT>삭제</button></td></tr>
		</table>
	</td>
</tr>
</table>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="js/drillT.js"></script>
<jsp:include page="footer.jsp" />