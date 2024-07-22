<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="base.jsp" />
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
<link rel="stylesheet" type="text/css" href="{% static 'style.css' %}">
<input type=hidden id=mobile value="{{ student.mobile }}">
<header>
<h2>{{title}}</h2>
</header>
<section>
<nav class='navbar navbar-expand-lg gb-light'>
	<div class='container-fluid'>
		<div class='col-3'></div>
		<div class="col-6">
			<input type=hidden id=member_id value="{{request.session.member_id}}">
			<input type=hidden id=sid>
			<select id="selCourse" style="font-size:24px;">
			{% for crs in course %}
				<option value="{{crs.cid}}">{{crs.title}}</option>
			{% endfor %}				
			</select>
		</div>
		<label id="lblDays"></label>
		<div class="col-3">
			<a class="navbar-brand" href="{% url 'ncs:courseS' %}">과정관리</a>&nbsp;&nbsp;
			<a class="navbar-brand" href="{% url 'board:list' %}">자유게시판</a>&nbsp;&nbsp;
			<a class="navbar-brand" href='{% url 'ncs:personal' %}'>{{request.session.name}}</a>&nbsp;&nbsp;
			<a class="navbar-brand" href='{% url 'ncs:logout' %}'>로그아웃</a>
		</div>
	</div>
</nav>
<div class='container'>
	<div class=row>
		<div class=col>
			<table id=tblDrill style='width:100%;' class=border>
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