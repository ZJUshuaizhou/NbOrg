$(function(){
    var leftSel = $("#selectL");
	var rightSel = $("#selectR");
	$("#toright").bind("click",function(){		
		leftSel.find("option:selected").each(function(){
			$(this).remove().appendTo(rightSel);
		});
	});
	$("#toleft").bind("click",function(){		
		rightSel.find("option:selected").each(function(){
			$(this).remove().appendTo(leftSel);
		});
	});
	leftSel.dblclick(function(){
		$(this).find("option:selected").each(function(){
			$(this).remove().appendTo(rightSel);
		});
	});
	rightSel.dblclick(function(){
		$(this).find("option:selected").each(function(){
			$(this).remove().appendTo(leftSel);
		});
	});
//	$("#sub").click(function(){
//		var selVal = [];
//		rightSel.find("option").each(function(){
//			selVal.push(this.id);
//		});
//		selVals = selVal.join(",");
//		$("#pidList").val(selVals);
//		//selVals = rightSel.val();
//		if(selVals==""){
//			alert("没有选择任何项！");
//		}else{
//			alert(selVals);
//		}
//		$("#updateRole").submit();
//	});
});