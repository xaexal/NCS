let bDebug=true;

$(document)
.ready(function(){
	courseList();
})
.on('click','#courseList option',function(){
	$('#btnClearMember,#btnClearCourse').trigger('click');
	$('#selStudentEnrolled,#selStudentApplied').empty();

	let cid=$(this).val();
	console.log(`cid [${cid}]`)
	$.post('/course/get',{cid:cid},function(data){
		console.log(data);
		$('#frmCourse input,textarea,label').each(function(){
			let id=$(this).prop('id');
			if($(this).prop('type')=='checkbox'){
				if(data[id]=='1')	$('#'+id).prop('checked',true);
				else $('#'+id).prop('checked',false);
			} else if(id!=''&&$(this).prop('type')!='button') {
				$('#'+id).val(data[id]);
			}
		})
		$('#cid').val(cid);
		$.post('/student/list',{cid:cid},function(data){
			console.log('studentEnrolled',data)
			$.each(data,function(ndx,rec){
				for( value in rec){
					if(rec[value]==null || rec[value]==undefined || rec[value]=='null') {
						console.log(value+':'+rec[value]);
						rec[value]='';
					}
				}
				let str=`<option value="${rec['member_id']},${rec['sid']}">`+
						`${rec['name']},\t${rec['mobile']},\t`+
						`${rec['birthday']}</option>`;
				console.log(str);
				$('#selStudentEnrolled').append(str);
			});
		},'json');
		$.post('/course/applied',{cid:cid},function(result){
			$.each(result['applied'],function(ndx,rec){
				for( value in rec){
					if(rec[value]==null || rec[value]==undefined || rec[value]=='null') {
						console.log(value+':'+rec[value]);
						rec[value]='';
					}
				}
				let str=`<option value="${rec['mid']},${rec['sid']}">`+
						`${rec['name']},\t${rec['mobile']},\t`+
						`${rec['birthday']}</option>`;
				console.log(str);
				$('#selStudentApplied').append(str);
			});
		},'json');
	},'json');
	return false;
})
.on('click','#btnAddCourse',function(){
	$.ajax({url:'/course/add',data:$('#frmCourse').serialize(),dataType:'text',method:'post',
		beforeSend:function(){
			$('#title').val($.trim($('#title').val()));
			if($('#title').val()==''){
				alert("Title is necessary."); return false;
			}
			if($('#period1').val()!='' && $('#period2').val()!='' && $('#period1').val()>=$('#period2').val()){
				alert('개강일은 수료일보다 앞선 날짜여야 합니다.'); return false;
			}
			if(parseInt($('#seat_cnt').val())%parseInt($('#col_cnt').val())!=0){
				alert('좌석수는 열숫자의 배수여야 합니다.'); return false;
			}
			let datastr=$('#frmCourse').serialize();
			if($('#alive').prop('checked')) datastr = datastr.replace('alive=','alive=1')
			else datastr += '&alive=0';
			this.data = datastr;
		},
		success:function(result){
			if(result=='0') {
				alert('등록 실패');
				return false;
			}
			courseList();
	}});
	return false;
})
.on('click','#btnDelCourse',function(){
	$.ajax({url:'/course/delete',data:{cid:$('#cid').val()},dataType:'text',method:'post',
		beforeSend:function(){
			if($('#cid').val()=='') {
				alert('삭제할 과정을 선택하십시오')
				return false;
			}
			if(!confirm('삭제할까요?')) return false;
			console.log(this.data);
		},
		success:function(data){
			if(data=='0') {
				alert('삭제 실패'); return false;
			}
			$('#courseList option[value='+$('#cid').val()+']').remove();
			courseList();
		}
	})
	return false;
})
.on('click','#btnClearCourse',function(){
	$('#frmCourse input,textarea').each(function(){
		let id=$(this).prop('id');
		switch($(this).prop('type')){
		case 'checkbox','radio':
			$('#'+id).prop('checked',false); break;
		case 'button': break;
		default:
			if(id!=''&&$(this).prop('type')!='button') $('#'+id).val('');
		}
	})
	return false;
})
.on('click','#selStudentEnrolled option,#selStudentApplied option',function(){
	let pstr=($(this).val()).split(','); //[member_id,student_id]
	console.log(pstr)

	$('#member_id').val(pstr[0]);
	$('#sid').val(pstr[1]);
	$.post('/student/get',{sid:$('#sid').val()},(student)=>{

		console.log(student)
		$('#name').val(student['name'])
		$('#mobile').val(student['mobile'])
		$('#birthday').val(student['birthday'])
		$('#school').val(student['school'])
		$('#address').val(student['address'])
		$('#seq').val(student['seq'])
		$('#selStatus').val(student['status']);
		$('#member_id').val(student['member_id'])
		$('input[name=gender][val='+student['gender']+']').prop('checked',true)
	},'json')
	return false;
})
.on('click','#btnPersonal',function(){
	$.post('/student/list',{member_id:$('#member_id').val()},function(data){
		$('#btnClearMember').trigger('click')
		rec=data['student'][0]
		$('#frmStudent input,textarea,label').each(function(){
			let id=$(this).prop('id');
			if($(this).prop('type')=='checkbox'){
				if(rec[id]=='1')	$('#'+id).prop('checked',true);
				else $('#'+id).prop('checked',false);
			} else if($(this).prop('type')=='radio'){
				let name=$(this).prop('name')
				$('input[name='+name+'][val="'+rec[name]+'"]').prop('checked',true);
			} else if(id!=''&&$(this).prop('type')!='button') {
				$('#'+id).val(rec[id]);
			}
		})
	},'json')
	return false;
})
.on('dblclick','#selStudentEnrolled option',function(){
	console.log('dblclick selStudentEnrolled sid ['+$('#sid').val()+']')
	let thisone=$(this);
	$.post('/student/delete',{sid:$('#sid').val()},function(data){
		console.log(data);
		if(data!='1'){
			alert('신청학생 목록으로 옮길 수 없습니다'); return false;
		}
		console.log(thisone.html())
		$('#selStudentApplied').append(thisone.parent().html());
		thisone.remove();
	},'text')
	return false;
})
.on('dblclick','#selStudentApplied option',function(){
	console.log('sid ['+$('#sid').val()+']')
	let thisone=$(this);
	$.post('/student/add',{sid:$('#sid').val()},function(data){
		if(data['result']!='0'){
			alert(data['msg']); return false;
		}
		console.log(thisone.html())
		$('#selStudentEnrolled').append(thisone.parent().html());
		thisone.remove();
	},'text')
	return false;
})
.on('click','#btnUpdateMember',function(){
	if($('#sid').val()=='' || $('#member_id').val()=='') {
		alert('학생을 선택하십시오');
		return false;
	}

	oParam={'sid':$('#sid').val(),'member_id':$('#member_id').val()};
	oParam['name']=$('#name').val();
	oParam['mobile']=$('#mobile').val();
	oParam['birthday']=$('#birthday').val();
	oParam['gender']=$('input[name=gender]:checked').attr('val'); // .prop()안됨
	oParam['school']=$('#school').val();
	oParam['seq']=$('#seq').val();
	oParam['address']=$('#address').val();
	oParam['status']=$('#selStatus').val();
	$.ajax({url:'/member/updateByAdmin',type:'post',dataType:'text',
		data:oParam,
		beforeSend:function(){
			console.log(this.data);
		},
		success:function(data){
			if(data=='0'){
				alert('변경 실패');
				return false;
			}
			$('#btnClearMember').trigger('click')
		}
	});
	return false;
})
.on('click','#btnClearMember',function(){
	$('#frmStudent input,textarea').each(function(){
		let id=$(this).prop('id');
		switch($(this).prop('type')){
		case 'checkbox','radio':
			$(this).prop('checked',false); break;
		case 'button': break;
		default:
			if(id!=''&&$(this).prop('type')!='button') $('#'+id).val('');
		}
	})
	return false;
})
;

function courseList(){
	$('#btnClearCourse').trigger('click');
	$.post('/course/listAll',{},function(data){
		console.log(data)
		$('#courseList').empty();
		$.each(data,function(ndx,course){
			$('#courseList').append(`<option value=${course.cid}>${course.title}</option>`);
		});

	},'json');
}