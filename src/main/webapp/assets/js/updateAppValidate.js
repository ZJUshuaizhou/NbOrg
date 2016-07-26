'use strict';

function assignment(){
	var tip = "注意：";
	var msgvalue = tip;
	msgvalue = msgvalue + $("#surlmsg").text();
	msgvalue = msgvalue + $("#slogmsg").text();
	msgvalue = msgvalue + $("#ourlmsg").text();
	if(tip==msgvalue)
		msgvalue="";
	$("#tip").text(msgvalue);
}

function validateurl(ele, msgid) {
	var pattenUrl = /^(https?|ftp|file):\/\/[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]/;
	var url = ele.value;
	msgid = "#"+msgid;
	if (pattenUrl.test(url)){
		$(msgid).text("");
	}else{
		if(ele.name=="saml.url"){
			$(msgid).text(" saml的回调url填写错误; ");
		}else if(ele.name=="saml.logoutUrl"){
			$(msgid).text(" saml的登出url填写错误; ");
		}else if(ele.name=="oauth.url"){
			$(msgid).text("OAuth的回调url填写错误; ");
		}
	}
	assignment();
}
function mouseOver(){

	validateurl(document.getElementsByName("saml.url")[0],'surlmsg');
	validateurl(document.getElementsByName("saml.logoutUrl")[0],'slogmsg');
	validateurl(document.getElementsByName("oauth.url")[0],'ourlmsg');
}


/*	function validateAndSubmit() {
var pattenUrl = /^(https?|ftp|file):\/\/[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]/;
var sUrl = document.getElementsByName("saml.url")[0].value;
var slUrl = document.getElementsByName("saml.logoutUrl")[0].value;
var oUrl = document.getElementsByName("oauth.url")[0].value;
if (pattenUrl.test(sUrl) && pattenUrl.test(slUrl)
&& pattenUrl.test(oUrl)) {
document.getElementById("edit_form").submit();
} else if (!pattenUrl.test(oUrl)) {
$.confirm({
keyboardEnabled : true,
title : '提示',
content : 'OAuth的URL地址不合法',
confirmButtonClass : 'btn-info',
autoClose : 'confirm|3000'
});
} else if (!(pattenUrl.test(sUrl) && pattenUrl.test(slUrl))) {
$.confirm({
keyboardEnabled : true,
title : '提示',
content : 'SAML的URL地址不合法，应用不能通过此配置正常登陆，确定吗？',
confirmButtonClass : 'btn-info',
autoClose : 'confirm|3000',
confirm : function() {
document.getElementById("edit_form").submit();
}
});
}
}*/