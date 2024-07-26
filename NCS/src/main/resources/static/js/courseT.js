let bDebug=true;

$(document)
.ready(function(){
	courseList();
})
.on('click','#courseList option',function(){
	$('#btnClearMember,#btnClearCourse').trigger('click');
	$('#selStudentPresent,#selStudentApplied').empty();
	
	let cid=$(this).val();
	console.log(`cid [${cid}]`)
	$.post(url_getCourse,{cid:cid},function(result){
		console.log(result);
		rec=result['rec'][0]
		$('#frmCourse input,textarea,label').each(function(){
			let id=$(this).prop('id');
			if($(this).prop('type')=='checkbox'){
				if(rec[id]=='1')	$('#'+id).prop('checked',true);
				else $('#'+id).prop('checked',false);
			} else if(id!=''&&$(this).prop('type')!='button') {
				$('#'+id).val(rec[id]);
			}
		})
		$('#cid').val(cid);
		$.post(url_studentPresent,{cid:cid},function(result){
			console.log('studentPresent',result)
			$.each(result['present'],function(ndx,rec){
				for( value in rec){
					if(rec[value]==null || rec[value]==undefined || rec[value]=='null') {
						console.log(value+':'+rec[value]);
						rec[value]='';
					}
				}
				let str=`<option value="${rec['member__mid']},${rec['sid']}">`+
						`${rec['member__name']},\t${rec['member__mobile']},\t`+
						`${rec['member__birthday']}</option>`;
				console.log(str);
				$('#selStudentPresent').append(str);
			});
		},'json');
		$.post(url_studentApplied,{cid:cid},function(result){
			$.each(result['applied'],function(ndx,rec){
				for( value in rec){
					if(rec[value]==null || rec[value]==undefined || rec[value]=='null') {
						console.log(value+':'+rec[value]);
						rec[value]='';
					}
				}
				let str=`<option value="${rec['member__mid']},${rec['sid']}">`+
						`${rec['member__name']},\t${rec['member__mobile']},\t`+
						`${rec['member__birthday']}</option>`;
				console.log(str);
				$('#selStudentApplied').append(str);
			});
		},'json');
	},'json');
	return false;
})
.on('click','#btnAddCourse',function(){
	$.ajax({url:url_addCourse,data:$('#frmCourse').serialize(),dataType:'json',method:'post',
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
			console.log($('#frmCourse').serialize())
		},
		success:function(result){
			if(parseInt(result['result'])<0) {
				alert(result['msg']);
				return false;
			}
			courseList();
	}});
	return false;
})
.on('click','#btnDelCourse',function(){
	$.ajax({url:url_delCourse,data:{cid:$('#cid').val()},dataType:'json',method:'post',
		beforeSend:function(){
			if($('#cid').val()=='') {
				alert('삭제할 과정을 선택하십시오')
				return false;
			}
			if(!confirm('삭제할까요?')) return false;
		},
		success:function(data){
			if(parseInt(data['result'])<0) {
				alert(result['msg']); return false;
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
.on('click','#selStudentPresent option,#selStudentApplied option',function(){
	let pstr=($(this).val()).split(',');
	console.log(pstr)
		
	$('#member_id').val(pstr[0]);
	$('#sid').val(pstr[1]);
	$.post(url_getStudent,{member_id:$('#member_id').val()},(data)=>{
		
		let student=data['student'][0]
		console.log(student)
		$('#member__name').val(student['member__name'])
		$('#member__mobile').val(student['member__mobile'])
		$('#member__birthday').val(student['member__birthday'])
		$('#member__school').val(student['member__school'])
		$('#member__address').val(student['member__address'])
		$('#seq').val(student['seq'])
		$('#member__active').val(student['member__active'])
		$('#member_id').val(student['member_id'])
		$('input[name=member__gender][val='+student['member__gender']+']').prop('checked',true)
	},'json')
	return false;
})
.on('click','#btnPersonal',function(){	
	$.post(url_getStudent,{member_id:$('#member_id').val()},function(data){
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
.on('dblclick','#selStudentPresent option',function(){
	console.log('sid ['+$('#sid').val()+']')
	let thisone=$(this);
	$.post(url_del4Present,{sid:$('#sid').val()},function(data){
		if(data['result']!='0'){
			alert(data['msg']); return false;
		}
		console.log(thisone.html())
		$('#selStudentApplied').append(thisone.parent().html());
		thisone.remove();
	},'json')
	return false;
})
.on('dblclick','#selStudentApplied option',function(){
	console.log('sid ['+$('#sid').val()+']')
	let thisone=$(this);
	$.post(url_add2Present,{sid:$('#sid').val()},function(data){
		if(data['result']!='0'){
			alert(data['msg']); return false;
		}
		console.log(thisone.html())
		$('#selStudentPresent').append(thisone.parent().html());
		thisone.remove();
	},'json')
	return false;
})
.on('click','#btnUpdateMember',function(){
	oParam={'sid':$('#sid').val(),'member_id':$('#member_id').val()};
	if($('#sid').val()=='' || $('#member_id').val()=='') { 
		alert('학생을 선택하십시오');
		return false;
	}
	
	oParam['name']=$('#member__name').val();
	oParam['mobile']=$('#member__mobile').val();
	oParam['birthday']=$('#member__birthday').val();
	oParam['gender']=$('input[name=member__gender]:checked').attr('val'); // .prop()안됨
	oParam['school']=$('#member__school').val();
	oParam['seq']=$('#seq').val();
	oParam['address']=$('#member__address').val();
	oParam['active']=$('#member__active').prop('checked')?'1':'0';	
	console.log(oParam);					
	$.post(url_addStudent,oParam,
		function(data){
			if(data['result']!='0'){
				alert(data['msg']);
				return false;
			}
			$('#btnClearMember').trigger('click')
		},'json')
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
	$.post(url_courseListAll,{},function(data){
		console.log(data)
		$('#courseList').empty();
		$.each(data['All'],function(ndx,course){
			$('#courseList').append(`<option value=${course.cid}>${course.title}</option>`);
		});
				
	},'json');
}