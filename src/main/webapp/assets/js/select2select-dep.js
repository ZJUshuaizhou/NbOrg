$(function(){
    var leftSel = $("#selectDepL");
	var rightSel = $("#selectDepR");
	$("#toDepRight").bind("click",function(){		
		leftSel.find("option:selected").each(function(){
			$(this).remove().appendTo(rightSel);
		});
	});
	$("#toDepLeft").bind("click",function(){		
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
//			selVal.push(this.id+" "+this.value);
//		});
//		selVals = selVal.join(",");
//		//selVals = rightSel.val();
//		if(selVals==""){
//		}else{
//			alert(selVals);
//		}
//	});
});