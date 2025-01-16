$(document)
.ready(function(){
	$('#selCourse').trigger('change')
})
.on('change','#selCourse',function(){
	$('#tblRank').empty()
	let rowstr='';
	let cid=$('#selCourse').val().split(',')[0];
	$.post(url_getRank,{cid:cid},function(data){
		console.log(data)
		let pstr='<tr><td>&nbsp;</td>';
		$.each(data['student'],function(ndx,rec){
			console.log(rec);
			pstr+=`<td id="${rec['sid']}">${rec['member__name']}</td>`;
			rowstr+='<td>&nbsp;</td>'
		})
		pstr+='</tr>'
		rowstr+='</tr>'
		$('#tblRank').append(pstr);
		
		$.each(data['dl'],function(ndx,rec){
			console.log(rec)
			pstr=`<tr><td id=${rec['drill_id']}>${rec['drill__name']}</td>${rowstr}`;
			$('#tblRank').append(pstr);
		})
		
		$.each(data['ds'],function(ndx,rec){
			let loc=getPosition(rec['student_id'],rec['drill__name'])
			if(loc['student']==-1 || loc['drill']==-1) return true;
			
			$(`#tblRank tr:eq(${loc['drill']}) td:eq(${loc['student']})`).text(rec['updated'])
		})
	},'json')
	return false;
})

function getPosition(student,drill){
	console.log('student ['+student+'] drill ['+drill+']')
	let pos={'student':-1,'drill':-1};
	$('#tblRank tr:eq(0) td').each(function(){
		if($(this).attr('id')==student){
			pos['student']=$(this).index()
			return false;
		}
	})
	$('#tblRank tr').each(function(){
		if($(this).find('td:eq(0)').text()==drill){
			pos['drill']=$(this).index()
			return false;
		}
	})
	console.log('pos',pos)
	return pos;
}