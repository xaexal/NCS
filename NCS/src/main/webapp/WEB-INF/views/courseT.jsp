<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="base.jsp" />
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<header>
<h2>${title}</h2>
</header>
<nav class='navbar navbar-expand-lg gb-light'>
	<div class='container-fluid'>
		<div class="col-6">&nbsp;</div>
		<div class="col-6">
		<a class='navbar-brand' href='/drillViewT'>과제진행</a>
		<a class="navbar-brand" href='/drillT'>과제관리</a>
		<a class="navbar-brand" href='/showRank'>결과보기</a>
		<a class="navbar-brand" href='/personal'>${sessionScope.name}</a>&nbsp;&nbsp;
		<a class="navbar-brand" href='/logout'>로그아웃</a>
		</div>
	</div>
</nav>
<section>
<div class='container'>
	<div class=row style='border:1px double blueviolet' ><div class=col><h4>과정관리</h4></div></div>
	<div class=row style='height:20px;'>&nbsp;</div>
	<div class='row'>
		<div class='col-6'>
			<select id=courseList style='width:500px' size=12></select>
		</div>
		<div class='col-6'>
			<form id="frmCourse" method='post'>
			<div class="row">
				<div class="col-2 left-col">기관명</div>
				<div class="col-4 right-col">
					<input type=hidden id=cid name=cid>
					<input type="text" name="orgname" id="orgname">
				</div>
			</div>
			<div class=row>
				<div class='col-2 left-col'>과정명</div>
				<div class='col-4 right-col'>			
					<textarea id=title name=title rows=3 cols=24 required></textarea>
				</div>
			</div>
			<div class=row>
				<div class='col-2 left-col'>과정정원(명)</div>
				<div class='col-1 right-col'><input type=number id=seat_cnt name=seat_cnt value=1 min=1 style='width:40px'></div>
				<div class='col-2 left-col'>좌석열갯수</div>
				<div class='col-1 right-col'><input type=number id=col_cnt name=col_cnt value=1 min=1 style='width:40px'></div>
			</div>
			<div class=row>
				<div class='col-2 left-col'>개강일</div>
				<div class='col-4 right-col'><input type=date id=period1 name=period1 required></div>
			</div>
			<div class=row>
				<div class='col-2 left-col'>수료일</div>
				<div class='col-4 right-col'><input type=date id=period2 name=period2 required></div>
			</div>
<!-- 			<div class=row>
				<div class='col-2 left-col'>활성화</div>
				<div class='col-4 right-col'><input type=checkbox id=alive name=alive></div>
			</div>
 -->			<div class=row>
				<div class='col-2 left-col'>작성시각</div>
				<div class='col-4 right-col'><input size="16" id=created name=created readonly></div>
			</div>
			<div class=row>
				<div class='col-2 left-col'>수정시각</div>
				<div class='col-4 right-col'><input size="16" id=updated name=updated readonly></div>
			</div>
			<div class=row>
				<div class='col-6 left-col right-col' style='text-align:center'>
				<input type=button id=btnAddCourse class='btn-sm btn-primary' value=Save>&nbsp;&nbsp;
				<input type=button id=btnDelCourse class='btn-sm btn-secondary' value=Delete> &nbsp;&nbsp;
				<input type=button id=btnClearCourse class='btn-sm btn-warning' value=Clear>
				</div>
			</div>
			</form>
		</div>
	</div>
	<div class=row style='height:20px;'>&nbsp;</div>
	<div class=row style='border:1px double blueviolet;'><div class=col><h4>학생관리</h4></div></div>
	<div class=row style='height:10px;'>&nbsp;</div>
	<div class=row>
		<div class='col-3'>
			<label>현재과정 재학생</label>
			<select id=selStudentPresent style='width:240px;' size=30></select>
`		</div>
		<div class="col-3">
			<label>현재과정 신청학생</label>
			<select id="selStudentApplied" style='width:240px;' size=30></select>
		</div>
		<div class='col-6'>
			<form id=frmStudent>
			<div class="row">
				<div class="col-6"><input type=button value='인적사항보기' id=btnPersonal></div>
			</div>
			<div class=row>
				<div class='col-2 first-col left-col'>이름</div>
				<div class='col-4 right-col'>
					<input type=text id=member__name required>
					<input type=hidden id=member_id><input type="hidden" id="sid">
				</div></div>
			<div class=row><div class='col-2 left-col'>모바일</div>
			<div class='col-4 right-col'><input type=text id=member__mobile required></div></div>
			<div class=row><div class='col-2 left-col'>생년월일</div>
			<div class='col-4 right-col'><input type=date id=member__birthday></div></div>
			<div class=row><div class='col-2 left-col'>성별</div>
				<div class='col-4 right-col'>
					<input type=radio id=male name='member__gender' value='남' val='M'>남
					<input type=radio id=female name='member__gender' value='여' val='F'>여</div></div>
			<div class=row><div class='col-2 left-col'>최종학력 </div>
			<div class='col-4 right-col'><input type=text id=member__school></div></div>
			<div class=row><div class='col-2 left-col'>좌석번호</div>
			<div class='col-4 right-col'><input type=number id=seq></div></div>
			<div class=row><div class='col-2 left-col'>주소</div>
			<div class='col-4 right-col'><textarea id=member__address rows="3" cols="22"></textarea></div></div>
			<div class=row><div class='col-2 left-col'>&nbsp;</div>
			<div class='col-4 right-col'><input type=checkbox id=member__active>Active</div></div>
			<div class=row><div class='col-6 left-col right-col' style='text-align:center'>
					<input type="button" id=btnUpdateMember class='btn-sm btn-primary' value='Save'>
						<!--<button id=btnDelStudent class='btn-sm btn-secondary'>Delete</button>-->
					<input type="button" id=btnClearMember class='btn-sm btn-warning' value='Clear'>
			</div></div>
			</form>
		</div>
	</div>
</div>
</section>
<script>
let url_add2Present="/add2Present";
let url_del4Present="/del4Present";
</script>
<script src="js/courseT.js"></script>
<jsp:include page="footer.jsp" />