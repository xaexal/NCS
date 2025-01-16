<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel='stylesheet' type='text/css' href='css/member.css' />
<jsp:include page="base.jsp" />
<section class='login-form'>
	<h1 style='font-size:54px;color:aqua;'><a href="/" style='text-decoration:none;'>프로그래밍 연습</a></h1>
	<h1>등록</h1>
	<form action="" id=frmsignup method="post">
        <div class='int-area'>
			<input type="text" name="mobile" id="mobile" autocomplete=off required>
			<label for=mobile id=lblMobile>모바일번호(010빼고)</label>
		</div>
		<div class='int-area'>
			<input type="password" name="passcode" id="passcode" autocomplete=off required>
			<label for='passcode'>비밀번호</label></td><td>
		</div>
		<div class='int-area'>
			<input type="password" name="passcode1" id="passcod1e" autocomplete=off required>
			<label for='passcode1'>비밀번호 확인</label></td><td>
		</div>
		<div class='btn-area'>
			<button type="submit" id=btnSubmit>등록(가입)</button>
		</div>
		<div class='caption'>
			<a href="/">홈으로</a>&nbsp;&nbsp;
			<a href="/findPassword">비밀번호 찾기</a>&nbsp;&nbsp;
			<a href="/login">로그인</a>
		</div>
	</form>
</section>
<div style='display:none;' id=dvClass>
<table>
<tr><td>
	<label for='selClass'>과정 선택</label><br>
	<select name=selClass id=selClass size=5>
	<c:forEach var="course" items="${classes}">
		<option value='${course.classcode},${course.seat_cnt},${course.col_cnt}'>${course.title}[${course.period1}~${course.period2}]</option>
	</c:forEach>
	</select>
</td></tr>
</table>
</div>
<script>
$(document)
.ready(function(){
// 	$('#btnSubmit').prop('disabled',true);
/*	$('#dvClass').dialog({
		autoOpen:false
		,modal:true
		,width:720
		,height:300
		,buttons:[
			{text:'선택완료'
			,click:function(){
				let pstr=$('#selClass option:selected').text();
				let pcode = $('#selClass').val().split(',');
				console.log(pcode[0]);
				$('#class').text(pstr);
				$('#classcode').val(pcode[0]);
				$(this).dialog('close');
			}
			,{text:'취소'
			,click:function(){
				$(this).dialog('close');
			}
		]
	});
	return false;*/
})
.on('click','#btnClass',function(){
	$('#dvClass').dialog('open');
	return false;
})
.on('focus','#mobile',function(){
	$('#lblMobile').text('모바일번호(010빼고)');
	return false;
})
.on('blur','#mobile',function(){
	let pstr=$('#mobile').val();
	pstr=$.trim(pstr);
	if(pstr!=''){
		$('#mobile').val(pstr);
		$.post("/duplicate",{mobile:pstr},function(data){
			$('#lblMobile').text(data['msg']);
			if(data['result']=='0'){
// 				$('#btnSubmit').prop('disabled',false);
			}
		},'json');
	}
	return false;
})
.on('click','#btnSubmit',function(){
	$.ajax({url:'/member/insert',type:'post',dataType:'text',
		data:{mobile:$('#mobile').val(),passcode:$('#passcode').val()},
		beforeSend:function(){
			let mobile=$('#mobile').val();
			mobile=$.trim($('#mobile').val());
			$('#passcode').val($.trim($('#passcode').val()));
			if(mobile.substr(0,3)=='010'){
				mobile=mobile.substr(3);
			} else if(mobile.substr(0,2)=='10'){
				mobile=mobile(2);
			}
			$('#mobile').val(mobile);
			if($('#mobile').val()==''){
				$('#mobile').next('label').addClass('warning');
				setTimeout(function(){
					$('#mobile').removeClass('warning');
				},1500);
				alert('모바일번호를 입력하십시오.');
				return false;
			}
			if($('#passcode').val()==''){
				$('#passcode').next('label').addClass('warning');
				setTimeout(function(){
					$('#passcode').removeClass('warning');
				},1500);
				alert('비밀번호를 입력하십시오');
				return false;
			}
		},
		success:function(data){
			if(data=='0'){
			} else if(data=='1'){
			} else {
			}
		}
		})

	return true;
})
;
</script>
<jsp:include page="footer.jsp" />