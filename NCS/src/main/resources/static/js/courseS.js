let courseInfo={}

$(document)
.ready(function(){
	courseList();
})
.tooltip()
.on('dblclick','#selCourseAll option',function(){
	console.log($(this).val()+','+$(this).text());
	let course_id=$(this).val();
	let selCourseAll=$(this);
	$.post(url_apply2Course,{course_id:course_id},function(data){
		console.log(data);
		if(data['msg']!=''){
			alert(data['msg']);
		} else {
			$('#selCourseApplied').append(selCourseAll.html());
		}
		courseList();
	},'json');
	return false;
})
.on('click','#selCourseAll option',function(){
	let thisone=$(this).val();
	$.each(courseInfo['All'],function(ndx,course){
		if(course['cid']==thisone){
			let pstr='수업기간: '+course['period1']+' ~ '+course['period2'];
			pstr +='\n정원: '+course['seat_cnt']+'명'
			$('#txtCourse').val(pstr);
		}
	})
})
.on('dblclick','#selCourseApplied option',function(){
	console.log($(this).val()+','+$(this).find('option:selected').text());
	let course_id=$(this).val();
	let selCourseApplied=$(this);
	$.post(url_revokeCourse,{course_id:course_id},function(data){
		if(data['msg']!=''){
			alert(data['msg']);
		} else {
			$('#selCourseAll').append(selCourseApplied.html());
		}
		courseList();
	},'json');
	return false;
})
.on('click','#btnPresented',function(){
	let course_id=$('#selCourseApplied').val();
	let title=$('#selCourseApplied option:selected').text();
	console.log(`title [${title}]`)
	$.post(url_setDefaultCourse,{course_id:course_id},function(data){
		if(data['result']=='-1') {
			alert(data['msg']); return false;	
		}
		$('#presented').val(course_id);
		$('#txtPresented').val(title);
	},'json');
	return false;
})
.on('click','#btnClear',function(){
	$.post(url_resetDefaultCourse,{},function(data){
		if(data['result']=='-1'){
			alert(data['msg']); return false;
		}
		$('#txtPresented,#presented').val('')
	},'json')
})
;
function courseList(){
	console.log('Unenrolled')
	$.post('/course/unenrolled',{member_id:$('#member_id').val()},function(data){
		console.log(data)
		courseInfo=data;
		$('#selCourseAll').empty();
		$.each(data,function(ndx,course){
			$('#selCourseAll').append(`<option value=${course['cid']}>${course['title']}</option>`);
		});
	},'json');
	console.log('Applied')
	$.post('/course/applied',{member_id:$('#member_id').val()},function(data){
		console.log(data)
		$('#selCourseApplied').empty();
		$.each(data,function(ndx,course){
			$('#selCourseApplied').append(`<option value=${course['cid']}>${course['title']}</option>`);
		});
	},'json');
	console.log('Present')
	$.post('/course/present',{member_id:$('#member_id').val()},function(data){
		console.log(data)
		$('#selCoursePresent').empty();
		$.each(data,function(ndx,course){
			$('#selCoursePresent').append(`<option value=${course['cid']}>${course['title']}</option>`);
		});
	},'json');
	console.log('Complete')
	$.post('/course/complete',{member_id:$('#member_id').val()},function(data){
		console.log(data)
		$('#selCourseComplete').empty();
		$.each(data,function(ndx,course){
			$('#selCourseComplete').append(`<option value=${course['cid']}>${course['title']}</option>`);
		});
	},'json');
}