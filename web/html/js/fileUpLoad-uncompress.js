var fileId;
var fileUrl;
var fileImg;
var fileColse;
var definedImg;
var fileJdt;
var callbackUrl;
//点击弹出选择图片框
function onClick(fileId,fileUrl,fileImg,fileColse,definedImg,fileJdt,callbackUrl) {
	this.fileId=fileId;
	this.fileUrl=fileUrl;
	this.fileImg=fileImg;
	this.fileColse=fileColse;
	this.definedImg=definedImg;
	this.fileJdt=fileJdt;
	this.callbackUrl=callbackUrl;
	var file = document.getElementById("file");
	if (window.ActiveXObject) {
		file.click();
	} else {
		var evt = document.createEvent("MouseEvents");
		evt.initEvent("click", true, true);
		file.dispatchEvent(evt);
	}
}
//选择完图片执行方法
function operation() {
	var priceValue = document.getElementById("file").value;
	if (priceValue != null && priceValue != undefined && priceValue != "") {
		openJdt();
		var shopEditFrom = document.getElementById("uploadFrom");
		var photoExt = priceValue.substr(priceValue.lastIndexOf(".")).toLowerCase();
		if ('.gif,.jpg,.png,.jpeg,.ico,'.indexOf(photoExt + ',') < 0) {
			closeJdt();
			alert("上传图片格式不正确！");
			return false;
		}
		if (checkProperty(document.getElementById("file"))) {
			/*if (fileId != null && fileId != undefined && fileId != "") {
				//有图片id就删除
				var v=window.parent.document.getElementById(fileId).value;
				if(v!=null && v!=undefined && v!=""){
					doPost("attach.ajax?cmd=doDel&flowId="+v, function(res) {
						
					}, false);
				}
			}*/
			//开始上传
			getFromObj().action = "attach.ajax?cmd=doUpload";
			postForm(getFromObj(), function(res) {
				closeJdt();
				window.parent.document.getElementById(fileId).value=res;
				queryFile(res,this.fileId,this.fileUrl,this.fileImg,this.fileColse,this.definedImg);
			}, false);
			return true;
		} else {
			return false;
		}
	} else {
		return false;
	}
}
function delFile(code,fileId,fileUrl,fileImg,fileColse,definedImg,fileJdt){
	//删除
	var v=code;
	if(v!=null && v!=undefined && v!=""){
		openJdt();
		doPost("attach.ajax?cmd=doDel&flowId="+v, function(res) {
			if(definedImg!=null && definedImg!=undefined && definedImg!="" && definedImg!="null" && definedImg!="undefined"){
				window.parent.document.getElementById(fileImg).src=getRootPath()+definedImg;
			}else{
				window.parent.document.getElementById(fileImg).src=getRootPath()+"/image/fa.jpg";
			}
			window.parent.document.getElementById(fileId).value="";
			window.parent.document.getElementById(fileUrl).value="";
			closeJdt();
		}, false);
	}
}

function queryFile(code,fileId,fileUrl,fileImg,fileColse,definedImg,fileJdt){
	this.fileId=fileId;
	this.fileUrl=fileUrl;
	this.fileImg=fileImg;
	this.fileColse=fileColse;
	this.definedImg=definedImg;
	if(code!=null && code!=undefined && code!=""){
		openJdt();
		var url="attach.ajax?cmd=doQuery&_GRID_TYPE=jq&flowIds="+code;
		var tht=this;
		doPost(url,function(data){
			var json=JSON.parse(data);
			if(json.total>0){
				window.parent.document.getElementById(fileId).value=code;
				window.parent.document.getElementById(fileImg).src=json.rows[0].fullPathUrl;
				window.parent.document.getElementById(fileUrl).value=json.rows[0].storePath;
				if(tht.callbackUrl!=null && tht.callbackUrl!=undefined && tht.callbackUrl!=""){
					var callUrl=tht.callbackUrl+"&flowIds="+code;
					doPost(callUrl,null, false);
				}
			}else{
				window.parent.document.getElementById(fileId).value="";
				window.parent.document.getElementById(fileUrl).value="";
			}
			closeJdt();
		},false);
	}else{
		window.parent.document.getElementById(fileColse).style.display="none";
		window.parent.document.getElementById(fileId).value="";
		window.parent.document.getElementById(fileUrl).value="";
	}
}

function openJdt(){
	if(this.fileJdt!=null && this.fileJdt!=undefined && this.fileJdt!=""){
		window.parent.document.getElementById(this.fileJdt).style.display="block";
	}
}
function closeJdt(){
	if(this.fileJdt!=null && this.fileJdt!=undefined && this.fileJdt!=""){
		window.parent.document.getElementById(this.fileJdt).style.display="none";
	}
}

function getFromObj() {
	return document.getElementById("uploadFrom");
}

function checkProperty(obj) {
	var fileSize = 0;
	var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
	try {
		if (isIE && !obj.files) {
			var filePath = obj.value;
			var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
			var file = fileSystem.GetFile(filePath);
			fileSize = file.Size;
		} else {
			fileSize = obj.files[0].size;
		}
		fileSize = Math.round(fileSize / 1024 * 100) / 100; // 单位为KB
		fileSize = Math.ceil(fileSize);
		if (fileSize > 1024) {
			closeJdt();
			alert("上传图片为" + fileSize + "KB，图片最大为1024KB，请重新上传!");
			return false;
		}
	} catch(e) {
	}
	return true;
}


function doPost(url,callback, isShowDialog){
	var paramArray = new Array();
	var paramStr = parseUrlParam(url, paramArray);
	if (paramArray.length > 0)
    	paramStr += "&"+ paramArray.sort().join("&");
    url = paramStr + "&sign="+ md5(paramStr+getCookie("token"));
	$.ajax({
		url: url,
		type: 'POST',
		dataType: 'text',
		success: function (response, status){
			if (isShowDialog) {
				alert(response);
			} else if (response.indexOf("javascript:") >= 0) {
				eval(response);
			}
			if(typeof callback == "function") {
				callback(response); 
			} else if(typeof callback == "string") {
				eval(callback);
			}
		},
		error: function (response, status, e){
			var pattern=/<body>(.*)<\/body>/g;
			var errBody = pattern.exec(response.responseText.replace(/\r\n/g, ""));
			alert(errBody[1].trim());
		}
	});
}

function getJson(formId) {
	var obj = {};
	var paramArray = new Array();
	paramArray.push("_GRID_TYPE=jq");
	obj["_GRID_TYPE"] = "jq";
	var conds;
	if (formId instanceof jQuery)
		conds = formId.serialize();
	else 
		conds = $(formId).serialize();
	conds = conds.replace(/\+/g," ");	//解决空格变+号问题   
	var pairs = conds.split('&');
	var name,value,paramStr; 
	paramStr = parseUrlParam(formId.action, paramArray);
    $.each(pairs, function(i, pair) {
    	if (pair !== "") {
    		paramArray.push(pair);
            pair = pair.split('=');
            name = decodeURIComponent(pair[0]);
    		value = decodeURIComponent(pair[1]);
            obj[name] = !obj[name] ? value :obj[name].concat(",").concat(value); //若有多个同名称的参数，则拼接
    	}
    });
    if (paramArray.length > 0)
    	paramStr += "&"+ paramArray.sort().join("&");
    obj["sign"] = md5(paramStr+getCookie("token"));
    return obj;
}
function parseUrlParam(orginUrl, paramArray) {
	var paramStr = "";
	if (orginUrl != undefined) {
		 var url = orginUrl.substring(orginUrl.lastIndexOf("/")+1);
		 if ((idx = url.indexOf("&")) > 0) {
			 paramStr = url.substring(0, idx);
			 var params = url.substring(idx+1).split("&");
			 for (var i in params) {
				 if (params[i].split("=")[1] !== "null" && params[i].split("=")[1] !== "") {
					 paramArray.push(params[i]);
				 }
			 }
		 } else {
			 paramStr = url;
		 }
	}
	return paramStr;
}
function getCookie(name) {
	var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	if(arr=document.cookie.match(reg))
		return unescape(arr[2]);
	else
		return "";
}
function postForm(form_, callback, isShowDialog, title,obj,objValue){
	if (typeof form_ == "string")
		form_ = document.getElementById(form_);
	if (form_ instanceof jQuery) {
		jqForm = form_;
		form_ = jqForm[0];
		form_.action = jqForm.action;
	}
	if (typeof(isShowDialog) == "undefined")
		isShowDialog = true;
	//_getTopDijitById("loadingDailog").show(); //进度提示
   	form_.method = "POST";
   	if (form_.encoding.indexOf("multipart") == -1) {
   		//console.log("upload file Request");
   		$.ajax({
			url: form_.action,
			type: form_.method,
			dataType: 'text',
			data: getJson(form_),
			success: function (response, status)
			{
				setTimeout(function() {
					//_getTopDijitById("loadingDailog").hide();
				}, 200);
				if (isShowDialog) {
					alert(response);
				} else if (response.indexOf("javascript:") >= 0) {
					eval(response);
				}
				if(typeof callback == "function") {
					callback(response); 
				} else if(typeof callback == "string") {
					eval(callback);
				}
			},
			error: function (response, status, e)
			{
				var pattern=/<body>(.*)<\/body>/g;
				var errBody = pattern.exec(response.responseText.replace(/\r\n/g, ""));
				alert(errBody[1].trim());
				/**提交发布货源车源失败（后台报错）*/
				if(obj != null && obj != undefined){
					obj.disabled = "";
					obj.value = objValue;	
				}
				 
			}
		});
   	} else {
   		//提交带附件上传的form表单
   		//console.log("normal Post Request");
	   	$.ajaxFileUpload({
			url: form_.action,
			secureuri:false,
			fileElementId: 'file',
			dataType: 'text',
			data: getJson(form_),
			success: function (response, status)
			{
				response=""+response;
				setTimeout(function() {
					//_getTopDijitById("loadingDailog").hide();
				}, 200);
				if (isShowDialog) {
					alert(response);
				} else if (response.indexOf("javascript:") >= 0) {
					eval(response);
				}
				if(typeof callback == "function") {
					callback(response); 
				} else if(typeof callback == "string") {
					eval(callback);
				}
			},
			error: function (response, status, e)
			{
				alert("文件上传失败!");
			}
		});
   	}
}