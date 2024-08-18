let bDebug=true;
let drill_info={};
let tag=1;

$(document)
.ready(function(){
	let today=new Date();
/*	let gap=Math.ceil((new Date($('#end_p').val())-today)/(60*60*24*1000));
	$('#gap').text('');
	if(gap>0){
		today=today.getFullYear()+(today.getMonth()<9?'0':'')+(today.getMonth()+1)+(today.getDate()<10?'0':'')+today.getDate();
		oParam={end_date:($('#end_p').val()).replace(/\-/g,''),today:today};
		if(bDebug) console.log('gap:',gap);
		$.post(url_holiday,oParam,function(json){
			if(bDebug) console.log(json);
			gap=gap-parseInt(json['cnt']);
			$('#gap').text('('+gap+'일)');
		},'json');	
	}
*/	
	let dt=new Date();
//	if( dt.getHours()>9 && dt.getHours()<18){
		setInterval(refreshPage,5000);
//	}
	$('#selCourse').trigger('change')
	
})
.on('change','#selCourse',function(){
	$.post('/student/getSID',{course_id:$('#selCourse').val(),member_id:$('#member_id').val()},
		function(data){
			console.log("sid ["+data+"]");
			$('#sid').val(data);
//			dayCount($('#selCourse').val(),$('#lblDays'));
			refreshPage();
		},'json')
	return false;
})
.on('click','#btnRefresh',function(){
	refreshPage();
	return false;
})
.on('dblclick','#tblExercise td',function(){
	oParam={};
	oParam['eid']=$(this).parent().attr('eid'); // eid
	if($(this).index()!=1)	{
//		console.log('did ['+oParam['eid']+']');
		$('#dvComment').text(drill_info[oParam['eid']]);
		return false;
	}
	curtd=$(this);
	$('#tblExercise tbody tr').css('background-color','white');
//	$(this).parent().css('background-color','yellow');

	oParam['sid']=$('#sid').val();
	$.ajax({url:'/status/update',type:'post',dataType:'text',
		data:oParam,
		beforeSend:function(){
			console.log(this.data);
		},
		success:data=>{
			console.log(data)
			if(data=='') return false;
			curtd.removeClass().text(data);
			switch(data){
			case '작업중': curtd.addClass('border working'); break;
			case '확인중': curtd.addClass('border checking'); break;
			case '완료':	 curtd.addClass('border done')
			}
		}
	});
	return false;	
})
;

function refreshPage(){
	exerciseList();
	return false;
}

function exerciseList(){
	tag=(tag==1)?0:1;
//	console.log(`tag [${tag}]`)
	$.post('/exercise/list',{cid:$('#selCourse').val()},function(data){
//		console.log(data)
		$('#tblExercise tbody').empty();
		$.each(data,function(ndx,rec){
			let skip=false;
			$('#tblExercise tbody tr').each(function(){
				if($(this).attr('eid')==rec['eid']){
					$(this).attr('tag',tag);
					skip=true;
					return false;
				}
			})
			if(!skip){
				let pstr=`<tr eid="${rec['eid']}" tag="${tag}"><td class='border'>${rec['name']}</td><td class='border working'>작업중</td></tr>`;
				$('#tblExercise tbody').prepend(pstr);
				drill_info[rec['eid']]=rec['comment']+
						'\n\n\n[작성:'+rec['created']+']\n[수정:'+rec['updated']+']';
			}
		})	
		$('#tblExercise tbody tr').each(function(){
			if($(this).attr('tag')!=tag){
				$(this).remove();
			}
		});
		myExerciseStatus();
//		rownum=$('#tblExercise tbody tr').length;
//		console.log('rownum2 ['+rownum+']')
	},'json')
}

function myExerciseStatus(){
	console.log("myExerciseStatus ["+$('#sid').val()+"]");
	$.post('/status/list4student',{student_id:$('#sid').val()},function(data){
//		console.log(data);
		$.each(data,function(ndx,rec){
			let curtd=$('#tblExercise tbody tr[eid='+rec['drill_id']+']').find('td:eq(1)')
			if(curtd.text()!=rec['status']){
				curtd.removeClass('working checking done')
				curtd.text(rec['status']);
				switch(rec['status']){
				case '작업중': curtd.addClass('working'); break;
				case '확인중': curtd.addClass('checking'); break;
				case '완료':	 curtd.addClass('done')
				}
			}
		})
	},'json');
}
