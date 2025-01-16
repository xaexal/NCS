let bDebug=false;

$(document)
.ready(function(){
	drillTypeList();
	$('#dvDrillType').dialog({
		autoOpen:false
		,width:500,height:360
		,modal:true
		,open:function(){
			drillTypeList();
			$('#typename').focus();
		}
		,buttons:[{
			text:'닫기',
			click:function(){
				$(this).dialog('close');
			}
		}]
	});
	$('#selCourse').trigger('change');
	return false;
})
.on('click','#selDrillTypeDlg',function(){
	$('#dtid').val($(this).val());
	$('#typename').val($(this).find('option:selected').text());
	return false;
})
.on('click','#btnDrillType',function(){
	$('#dvDrillType').dialog('open');
	return false;
})
.on('click','#btnDelDT',function(){
	if($('#dtid').val()==''){
		alert('지울 데이터를 먼저 선택하십시오.'); return false;
	}
	$.post('/drilltype/delete',{dtid:$('#dtid').val()},function(rec){
		console.log(rec)
		if(rec['result']!='0'){
			alert(rec['msg']); return false;
		}
	},'json');
	return false;
})
.on('keydown','#typename',function(e,u){
	if(e.keyCode==13) $('#btnAddDT').trigger('click');
})
.on('click','#btnAddDT',function(){
	if($('#typename').val()==''){
		alert('내용을 입력하십시오.'); return false;
	}
	$.post('/drilltype/add',{dtid:$('#dtid').val(),typename:$('#typename').val()},function(rec){
		if(rec['result']!='0'){
			alert('새 분류 등록 실패'); return false;
		}
		drillTypeList();
		$('#typename').focus();
	},'json');
	return false;
})
.on('change','#selCourse',function(){
	drillList();
	return false;	
})
.on('dblclick','#selDrill option',function(){
	$('#btnAppend').trigger('click');
	return false;
})
.on('click','#btnAppend',function(){
	let cid=$('#selCourse').val().split(',')[0];
	$('#selDrill option:selected').each(function(){
		let did=$(this).val();
		let dtype=$(this).attr('dtype');
		let htmlstr='<option value='+did+' dtype='+dtype+'>'+$(this).html()+'</option>';
		console.log(`did [${did}] cid [${cid}] dtype [${dtype}]`)
		$.ajax({url:'/exercise/add',data:{cid:cid,did:did},
			dataType:'text',method:'post',
			beforeSend:function(){console.log(this.data)},
			success:function(data){
				if(data=='0'){
					alert('추가실패'); return false;
				}
				console.log(htmlstr)
				$('#selExercise').append(htmlstr);
			}
		});
		$(this).remove();
	});
	return false;
})
.on('dblclick','#selExercise',function(){
	$('#btnRemove').trigger('click');
	return false;
})
.on('click','#btnRemove',function(){
	let cid=$('#selCourse').val().split(',')[0];
	$('#selExercise option:selected').each(function(){
		let did=$(this).val();
		let dtype=$(this).attr('dtype');
		let htmlstr='<option value='+did+' dtype='+dtype+'>'+$(this).html()+'</option>';
		console.log(`did [${did}] cid [${cid}]`)
		$.ajax({url:'/exercise/delete',data:{cid:cid,did:did},
			dataType:'text',method:'post',
			success:function(data){
				if(data=='='){
					alert('제외 실패'); return false;
				}
				console.log(htmlstr)
				$('#selDrill').append(htmlstr);
			}
		});
		$(this).remove();
	});
	return false;
})
.on('click','#selDrill option,#selExercise option',function(){
	if($(this).find(':selected').length>1) {
		$('#btnClear').trigger('click');
		return false;
	}
	$('#did').val($(this).parent().val());
	
	$.post('/drill/get',{did:$('#did').val()},function(data){
		$('#txtDrillName').val(data['name']);
		$('#selDrillType').val(data['dtype_id']);
		$('#txtComment').val(data['comment']);
		$('#created').text(data['created']);
		$('#updated').text(data['updated']);
	},'json')
	return false;
})
.on('click','#btnClear',function(){
	$('#did,#txtDrillName,#txtComment').val('');
	return false;
})
.on('click','#btnAdd',function(){
	$('#txtDrillName').val($.trim($('#txtDrillName').val()));
	$('#txtComment').val($.trim($('#txtComment').val()));
	if($('#txtDrillName').val()==''){
		alert('과제명은 반드시 입력하십시오.');
		return false;
	}
	// insert if did is null, else update
	
	$.ajax({url:'/drill/add',dataType:'text',type:'post',
			data:{did:$('#did').val(),name:$('#txtDrillName').val(),
				  comment:$('#txtComment').val(),type_id:$('#selDrillType').val()},
		beforeSend:function(){
//			console.log(this.data);
		},
		success:function(data){
			console.log(data);
			if(data=='1'){
				$('#btnClear').trigger('click');
				drillList();
			} else {
				alert('등록실패');
			}
		}
	});	
	return false;
})
.on('click','#btnDelete',function(){
	if($('#did').val()==''){
		alert('삭제할 과제를 선택하십시오.');
		return false;
	}
	$.post('/drill/delete',{did:$('#did').val()},function(data){
		if(data=='1'){
			$('#btnClear').trigger('click');
			drillList();
		} else {
			alert('삭제 실패');
		}
	},'text');	
	return false;
})
.on('change','#selDrilltype_Exercise',function(){
	if(isHandlingChange) return false;
	isHandlingChange=true;
	
	if($(this).val()=='all'){
		$('#selExercise option').each(function(){
			$(this).show();
		})
	} else {
		let value=$(this).val();
		$('#selExercise option').each(function(){
			if($(this).attr('dtype')==value){ // .prop()사용안됨
				$(this).show();
			} else {
				$(this).hide();
			}
		})
	}
	$('#btnClear').trigger('click')
	$('#selDrilltype_Drill').val($(this).val()).trigger('change');
	$('#selDrillType').val($(this).val());
	isHandlingChange=false;
	return false;
})
.on('change','#selDrilltype_Drill',function(){
	if(isHandlingChange) return false;
	isHandlingChange=true;
	
	if($(this).val()=='all'){
		$('#selDrill option').each(function(){
			$(this).show();
		})
	} else {
		let value=$(this).val();
		$('#selDrill option').each(function(){
			if($(this).attr('dtype')==value){ // .prop()사용안됨
				$(this).show();
			} else {
				$(this).hide();
			}
		})
	}
	$('#btnClear').trigger('click')
	$('#selDrilltype_Exercise').val($(this).val()).trigger('change');
	$('#selDrillType').val($(this).val());
	isHandlingChange=false;
	return false;
})

let isHandlingChange = false;

function drillList(){
	// get drill list for current class
	$.post('/exercise/list',{cid:$('#selCourse').val().split(',')[0]},function(json){
		$('#selExercise').empty();
		$.each(json,function(k,v){
			$('#selExercise').append(`<option value="${v['did']}" dtype="${v['dtype_id']}">${v['name']}</option>`);
		});
	},'json');
	$.post('/drill/list',{cid:$('#selCourse').val().split(',')[0]},function(json){
		$('#selDrill').empty();
		$.each(json,function(k,v){
			let pstr=`<option value="${v['did']}" dtype="${v['dtype_id']}">${v['name']}</option>`
			$('#selDrill').append(pstr);
		});
	},'json');
}

function drillTypeList(){
	$.post('/drilltype/list',{},function(data){
		$('select[name=selDrillType]').empty();
		$('#selDrilltype_Exercise,#selDrilltype_Drill').append('<option value=all>All</option>');
		for(rec of data){
			$('#dtid,#typename').val('');
			let str=`<option value=${rec['dtid']}>${rec['typename']}</option>`;
			$('select[name=selDrillType').append(str);
		}
	},'json');
}