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
	drillTypeList();
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
			$('#lastCreated').val('');
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
.on('change','#selDrillType',function(){
	console.log("change ["+$(this).val()+"]")
	if($(this).val()=='all'){
		
		$('#tblExercise tbody tr').show();
	} else {
		let value=$(this).val();
		$('#tblExercise tbody tr').each(function(){
			if($(this).attr('dtype')==value){ // .prop()사용안됨
				$(this).show();
			} else {
				$(this).hide();
			}
		})
	}
	return false;
})

;

function refreshPage(){
	$.ajax({type:'post',dataType:'text',url:'/exercise/lastCreated',
		data:{cid:$('#selCourse').val()},
		beforeSend:function(){},
		success:function(data){
//			console.log("data ["+data+"] lastCreated ["+$('#lastCreated').val()+"]");
			if(data>$('#lastCreated').val()) {
				$('#lastCreated').val(data);
				exerciseList();
			}
		},
		complete:function(){
			$.ajax({type:'post',dataType:'text',url:'/status/lastUpdated',
				data:{sid:$('#sid').val()},
				beforeSend:function(){
					console.log(this.data);
				},
				success:function(data){
//					console.log("data ["+data+"] lastUpdated ["+$('#lastUpdated').val()+"]");
					if(data>$('#lastUpdated').val()){
						$('#lastUpdated').val(data);
						myExerciseStatus();
					}
				}
			})
		}
	})
	return false;
}

function exerciseList(){
	tag=(tag==1)?0:1;
//	console.log(`tag [${tag}]`)
	$.post('/exercise/list',{cid:$('#selCourse').val()},function(data){
//		console.log(data)
		$('#tblExercise tbody').empty();
		for(rec of data){
			let skip=false;
			$('#tblExercise tbody tr').each(function(){
				if($(this).attr('eid')==rec['eid']){
					$(this).attr('tag',tag);
					skip=true;
					return false;
				}
			})
			if(!skip){
				let pstr=`<tr eid="${rec['eid']}" tag="${tag}" dtype="${rec['dtype_id']}"><td class='border'>${rec['name']}</td><td class='border working'>작업중</td></tr>`;
				$('#tblExercise tbody').prepend(pstr);
				drill_info[rec['eid']]=rec['comment']+
						'\n\n\n[작성:'+rec['created']+']\n[수정:'+rec['updated']+']';
				if($('#lastCreated').val()=='') $('#lastCreated').val(rec['created']);
			}
		}
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
		for(rec of data){
			let curtd=$('#tblExercise tbody tr[eid='+rec['eid']+']').find('td:eq(1)')
			if(curtd.text()!=rec['status']){
				curtd.removeClass('working checking done')
				curtd.text(rec['status']);
				switch(rec['status']){
				case '작업중': curtd.addClass('working'); break;
				case '확인중': curtd.addClass('checking'); break;
				case '완료':	 curtd.addClass('done')
				}
			}
		}
	},'json');
}

function drillTypeList(){
	$.post('/drilltype/list',{},function(data){
		$('#selDrillType').empty();
		$('#selDrillType').append('<option value=all>All</option>');
		console.log(data);
		for(rec of data){
			let str=`<option value=${rec['dtid']}>${rec['typename']}</option>`;
			$('#selDrillType').append(str);
		}
	},'json');
}