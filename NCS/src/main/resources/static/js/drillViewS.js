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
	$.post(url_getSID,{course_id:$('#selCourse').val(),member_id:$('#member_id').val()},
		function(data){
			$('#sid').val(data['sid']);
			dayCount($('#selCourse').val(),$('#lblDays'));
			refreshPage();
		},'json')
	return false;
})
.on('click','#btnRefresh',function(){
	refreshPage();
	return false;
})
.on('click','#tblDrill td',function(){
	oParam={};
	oParam['drill_id']=$(this).parent().attr('drill_id'); // drill_id
	if($(this).index()!=1)	{
//		console.log('did ['+oParam['drill_id']+']');
		$('#dvComment').text(drill_info[oParam['drill_id']]);
		return false;
	}
	curtd=$(this);
	$('#tblDrill tbody tr').css('background-color','white');
//	$(this).parent().css('background-color','yellow');

	oParam['sid']=$('#sid').val();
//	console.log(oParam)
	$.post(url_changeDrillStatus,oParam,function(data){
//		console.log(data)
		if(data['result']!='0') return false;
		curtd.removeClass().text(data['newStatus']);
		switch(data['newStatus']){
		case '작업중': curtd.addClass('border working'); break;
		case '확인중': curtd.addClass('border checking'); break;
		case '완료':	 curtd.addClass('border done')
		}
	},'json');
	return false;	
})
;

function refreshPage(){
	getDrillList();
	return false;
}

function getDrillList(){
	tag=(tag==1)?0:1;
//	console.log(`tag [${tag}]`)
	$.post(url_getDrillList,{cid:$('#selCourse').val()},function(data){
//		console.log(data)
//		$('#tblDrill tbody').empty();
		$.each(data['rec'],function(ndx,rec){
			let skip=false;
			$('#tblDrill tbody tr').each(function(){
				if($(this).attr('drill_id')==rec['drill_id']){
					$(this).attr('tag',tag);
					skip=true;
					return false;
				}
			})
			if(!skip){
				let pstr=`<tr drill_id="${rec['drill_id']}" tag="${tag}"><td class='border'>${rec['drill__name']}</td><td class='border working'>작업중</td></tr>`;
				$('#tblDrill tbody').prepend(pstr);
				drill_info[rec['drill_id']]=rec['drill__comment']+
						'\n\n\n[작성:'+rec['drill__created']+']\n[수정:'+rec['drill__updated']+']';
			}
		})	
		$('#tblDrill tbody tr').each(function(){
			if($(this).attr('tag')!=tag){
				$(this).remove();
			}
		});
		getDrillStatus4Student();
//		rownum=$('#tblDrill tbody tr').length;
//		console.log('rownum2 ['+rownum+']')
	},'json')
}

function getDrillStatus4Student(){
//	console.log($('#sid').val());
	$.post(url_getDrillStatus4Student,{sid:$('#sid').val()},function(data){
		$.each(data['rec'],function(ndx,rec){
			let curtd=$('#tblDrill tbody tr[drill_id='+rec['drill_id']+']').find('td:eq(1)')
			if(curtd.text()!=rec['status']){
				curtd.removeClass('working checking done')
//				console.log(curtd.text(),rec['status'])
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
