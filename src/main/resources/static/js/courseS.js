let courseInfo={}

$(document)
.ready(function(){
	courseList();
})
.tooltip()
.on('dblclick','#selApplicable option',function(){
	console.log($(this).val()+','+$(this).text());
	let course_id=$(this).val();
	let selApplicable=$(this);
	$.ajax({url:'/student/add',type:'post',dataType:'text',
		data:{cid:course_id,mid:$('#member_id').val()},
		beforeSend:function(){},
		success:function(data){
			console.log(data);
			if(data=='0'){
				alert('신청 실패');
			} else {
				courseList();
			}
		}
	});
	return false;
})
.on('click','#selApplicable option',function(){
	let thisone=$(this).val();
	$.each(courseInfo['All'],function(ndx,course){
		if(course['cid']==thisone){
			let pstr='수업기간: '+course['period1']+' ~ '+course['period2'];
			pstr +='\n정원: '+course['seat_cnt']+'명'
			$('#txtCourse').val(pstr);
		}
	})
})
.on('dblclick','#selApplied option',function(){
	console.log($(this).val()+','+$(this).find('option:selected').text());
	let course_id=$(this).val();
	let selApplied=$(this);
	$.ajax({url:'/student/delete',	type:'post',dataType:'text',
			data:{cid:course_id,mid:$('#member_id').val()},
		beforeSend:function(){
			console.log(this.data);
		},
		success:function(data){
			if(data=='0'){
				alert('신청철회 실패');
			}
			courseList();
		}
	});
	return false;
})
.on('click','#btnEnrolled',function(){
	let course_id=$('#selApplied').val();
	let title=$('#selApplied option:selected').text();
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
	console.log('Applicable')
	$.post('/course/applicable',{member_id:$('#member_id').val()},function(data){
		console.log(data)
		courseInfo=data;
		$('#selApplicable').empty();
		$.each(data,function(ndx,course){
			$('#selApplicable').append(`<option value=${course['cid']}>${course['title']}</option>`);
		});
	},'json');
	console.log('Applied')
	$.post('/course/applied',{member_id:$('#member_id').val()},function(data){
		console.log(data)
		$('#selApplied').empty();
		$.each(data,function(ndx,course){
			$('#selApplied').append(`<option value=${course['cid']}>${course['title']}</option>`);
		});
	},'json');
	console.log('Present')
	$.post('/course/enrolled',{member_id:$('#member_id').val()},function(data){
		console.log(data)
		$('#selEnrolled').empty();
		$.each(data,function(ndx,course){
			$('#selEnrolled').append(`<option value=${course['cid']}>${course['title']}</option>`);
		});
	},'json');
	console.log('Complete')
	$.post('/course/completed',{member_id:$('#member_id').val()},function(data){
		console.log(data)
		$('#selCompleted').empty();
		$.each(data,function(ndx,course){
			$('#selCompleted').append(`<option value=${course['cid']}>${course['title']}</option>`);
		});
	},'json');
}