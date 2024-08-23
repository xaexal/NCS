var gInterval=null;
let p_info={};

$(document)
.ready(function(){
	let today=strDate(new Date());
//	console.log(today);
	$('#selCourse option').each(function(){
//		console.log($(this).val())
		let ar = $(this).val().split(',');
		let start = ar[3].replace('-','');
		let end = ar[4].replace('-','');
		
		if(today >=start && today<=end) {
			$(this).prop('selected',true);
			return false;
		}
	})
	$('#selCourse').trigger('change');
	return false;
})
.on('change','#selCourse',function(){
	if($(this).find('option').length<1) {
		alert('등록된 과정이 아직 없습니다.');
		document.location=url_coursePage;
		return false;	
	}
	let ar=($(this).val()).split(',');
//	console.log(ar)
	$('#cid').val(ar[0]);
	$('#tblSeat').empty();
	outstr='<tr>';
	for(let i=parseInt(ar[1])-1; i>-1; i--){
		outstr+='<td class=student valign=top><table width=100% height=100% id=tbls'+(i+1)+'></table></td>';
		if(i%parseInt(ar[2])==0){
			outstr+='</tr><tr>';
		}
	}
	outstr+='</tr>';
//	console.log(outstr);
	$('#tblSeat').append(outstr);

//	dayCount($('#cid').val(),$('#lblDays'));
		
	// 해당과정의 재학생명단 가져오기
	$.ajax({url:'/student/list',data:{cid:$('#cid').val()},type:'post',dataType:'json',
		beforeSend:function(){
//			console.log(this.data);
		},
		success:function(data){
//			console.log(data);
			if(data['result']=='-1'){
				alert(data['msg']); return false;
			}
			$.each(data,function(ndx,student){
				outstr=`<tr height=20px><td class=palm sid=${student['sid']}>${student['name']}</td>`+
						'<td aligh=right style="font-size:12px">&nbsp;</td></tr>'+
						`<tr><td colspan=2 align=center class="working" id=${student['sid']}>작업중</td></tr>`;
				$('#tbls'+student['seq']).append(outstr);
				p_info[student['sid']]=
					{'name':student['name'],'seq':student['seq'],
					 'school':(student['school']==undefined?'최종학력':student['school']),
					 'address':(student['address']==undefined?'주소':student['address']),
					 'birth':(student['birthday']==undefined?'생년월일':student['birthday']),
					 'mobile':(student['mobile']==undefined?'모바일번호':student['mobile'])};
		 	});
		}
	});
	exerciseList();
})
.on('click','#selExercise option',function(){
	if(gInterval) clearInterval(gInterval);

	title=$(this).text();
	if(title=='-') {
		$('#selExercise').val('');
		return false;
	}
	//$('#txtDrill').val(title);
	
	
//	$('table[id^=tbls]').each(function(){
//		$(this).find('tr:eq(1) td:eq(0)').removeClass().addClass('working');
//	});
	exerciseStatus();
	let dt=new Date();
//	if(dt.getHours()>9 && dt.getHours()<18){
		gInterval=setInterval(exerciseStatus,5000);
//	}
	return false;
})
.on('click','table[id^=tbls] td',function(){
//	console.log('td click')
	if($(this).index()!=0 || $(this).parent().index()!=1) return false;
//	if(bDebug) console.log($('#selExercise').val());
	if($('#selExercise').val()==null) {
		alert('과제를 선택해야 상태를 변경할 수 있습니다.');
		return false;	
	}
	thisSeat=$(this);
	let oParam={sid:thisSeat.prop('id'),eid:$('#selExercise').val()};
//	console.log(oParam);
	$.post('/status/update',oParam,function(data){
//		console.log(data)
		if(data=='') {
			alert('로그인확인바람');
			return false;
		}
		thisSeat.removeClass().text(data);
		switch(data){
		case '작업중': thisSeat.addClass('working'); break;
		case '확인중': thisSeat.addClass('checking'); break;
		case '완료':	 thisSeat.addClass('done');
		}
	},'text');
	return false;
})
.on('click','.palm',function(e,u){
	let sid=$(this).attr('sid')
	if(sid=='') return false;
	$('#p_info').html(p_info[sid]['mobile']+'<br>'+p_info[sid]['birth']+'<br>'+p_info[sid]['school']).show();
	$('#p_info').css({left:e.clientX+30,top:e.clientY-30});
	return false;
})
.on('mousemove','.palm',function(e,u){
	$('#p_info').css({left:e.clientX+30,top:e.clientY-30});
	return false;
})
.on('mouseout','.palm',function(){
	$('#p_info').empty().hide();
	return false;
})

/*
작업중 <-> 완료 
확인요청 -> 완료
*/

function exerciseList(){
//	console.log('cid='+$('#cid').val());
	$.post('/exercise/list',{cid:$('#cid').val()},function(data){
//		console.log(data)
		$('#selExercise').empty();
		$.each(data,function(k,rec){
			let pstr=`<option value="${rec['eid']}">${rec['name']}</option>`;
//			console.log(pstr)
			$('#selExercise').prepend(pstr);
		});
	},'json');
}

function exerciseStatus(){
	if($('#selExercise').val()=='') return false;

	let arStudent=[];
	$.post('/status/list',{eid:$('#selExercise').val()},function(data){
		$.each(data,function(ndx,rec){
			arStudent.push(parseInt(rec['student_id']));
			if($('#'+rec['student_id']).text()==rec['status']) return true;
//			console.log(rec)
			switch(rec['status']){
//			case '작업중': $('#'+rec['student_id']).removeClass().addClass('working').text(rec['status']); break;
			case '확인중': $('#'+rec['student_id']).removeClass().addClass('checking').text(rec['status']); break;
			case '완료':	 $('#'+rec['student_id']).removeClass().addClass('done').text(rec['status'])
			}
		});
//		console.log(arStudent)
		$.each(p_info,(k,v)=>{
			if(!arStudent.includes(parseInt(k))) $('#'+k).removeClass().addClass('working').text('작업중');
		})
	},'json');
}
