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
	$.post(url_delDrillType,{dtid:$('#dtid').val()},function(rec){
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
	$.post(url_addDrillType,{dtid:$('#dtid').val(),typename:$('#typename').val()},function(rec){
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
.on('dblclick','#selDrillAll option',function(){
	$('#btnAppend').trigger('click');
	return false;
})
.on('click','#btnAppend',function(){
	let cid=$('#selCourse').val().split(',')[0];
	$('#selDrillAll option:selected').each(function(){
		let did=$(this).val();
		let dtype=$(this).attr('dtype');
		let htmlstr='<option value='+did+' dtype='+dtype+'>'+$(this).html()+'</option>';
		console.log(`did [${did}] cid [${cid}] dtype [${dtype}]`)
		$.ajax({url:url_addDrill2List,data:{cid:cid,did:did},
			dataType:'json',method:'post',
			success:function(data){
				if(data['result']=='-1'){
					alert(data['msg']); return false;
				}
				console.log(htmlstr)
				$('#selDrillAdded').append(htmlstr);
			}
		});
		$(this).remove();
	});
	return false;
})
.on('dblclick','#selDrillAdded',function(){
	$('#btnRemove').trigger('click');
	return false;
})
.on('click','#btnRemove',function(){
	let cid=$('#selCourse').val().split(',')[0];
	$('#selDrillAdded option:selected').each(function(){
		let did=$(this).val();
		let dtype=$(this).attr('dtype');
		let htmlstr='<option value='+did+' dtype='+dtype+'>'+$(this).html()+'</option>';
		console.log(`did [${did}] cid [${cid}]`)
		$.ajax({url:url_delDrill4List,data:{cid:cid,did:did},
			dataType:'json',method:'post',
			success:function(data){
				if(data['result']=='-1'){
					alert(data['msg']); return false;
				}
				console.log(htmlstr)
				$('#selDrillAll').append(htmlstr);
			}
		});
		$(this).remove();
	});
	return false;
})
.on('click','#selDrillAll option,#selDrillAdded option',function(){
	if($(this).find(':selected').length>1) {
		$('#btnClear').trigger('click');
		return false;
	}
	$('#did').val($(this).parent().val());
	
	$.post(url_getDrill,{did:$('#did').val()},function(json){
		let data=json['rec'][0];
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
	$.post(url_addDrill,{did:$('#did').val(),name:$('#txtDrillName').val(),
						 comment:$('#txtComment').val(),type_id:$('#selDrillType').val()},
		function(json){
			if(json['result']=='0'){
				$('#btnClear').trigger('click');
				drillList();
			} else {
				alert(json['msg']);
			}
		}
	,'json');	
	return false;
})
.on('click','#btnDelete',function(){
	if($('#did').val()==''){
		alert('삭제할 과제를 선택하십시오.');
		return false;
	}
	$.post(url_delDrill,{did:$('#did').val()},function(json){
		if(json['result']=='0'){
			$('#btnClear').trigger('click');
			drillList();
		} else {
			alert(json['msg']);
		}
	},'json');	
	return false;
})
.on('change','#selDrillTypeAdded',function(){
	if($(this).val()=='all'){
		$('#selDrillAdded option').each(function(){
			$(this).show();
		})
	} else {
		let value=$(this).val();
		$('#selDrillAdded option').each(function(){
			if($(this).attr('dtype')==value){ // .prop()사용안됨
				$(this).show();
			} else {
				$(this).hide();
			}
		})
	}
	$('#btnClear').trigger('click')
	return false;
})
.on('change','#selDrillTypeAll',function(){
	if($(this).val()=='all'){
		$('#selDrillAll option').each(function(){
			$(this).show();
		})
	} else {
		let value=$(this).val();
		$('#selDrillAll option').each(function(){
			if($(this).attr('dtype')==value){ // .prop()사용안됨
				$(this).show();
			} else {
				$(this).hide();
			}
		})
	}
	$('#btnClear').trigger('click')
	return false;
})


function drillList(){
	// get drill list for current class
	$.post(url_drillAdded,{cid:$('#selCourse').val().split(',')[0]},function(json){
		if(bDebug) console.log(json)
		console.log(json)
		$('#selDrillAdded').empty();
		$.each(json['added'],function(k,v){
			$('#selDrillAdded').append(`<option value="${v['did']}" dtype="${v['dtype_id']}">${v['name']}</option>`);
		});
	},'json');
	$.post(url_drillAll,{cid:$('#selCourse').val().split(',')[0]},function(json){
		$('#selDrillAll').empty();
		$.each(json['all'],function(k,v){
			let pstr=`<option value="${v['did']}" dtype="${v['dtype_id']}">${v['name']}</option>`
			console.log(pstr)
			$('#selDrillAll').append(pstr);
		});
	},'json');
}

function drillTypeList(){
	console.log('drillTypeList()')
	$.post(url_drillTypeList,{},function(json){
		$('select[name=selDrillType]').empty();
		$('#selDrillTypeAdded,#selDrillTypeAll').append('<option value=all>All</option>');
		for(rec of json['rec']){
			console.log(rec)
			$('#dtid,#typename').val('');
			let str=`<option value=${rec['dtid']}>${rec['typename']}</option>`;
			$('select[name=selDrillType').append(str);
		}
	},'json');
}