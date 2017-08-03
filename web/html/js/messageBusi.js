
 /**
  * 发送短信
  * templateUrl 模版文件地址
  * url 弹出窗口地址
  * detailData 请求的数据返回的对象
  * name 弹窗的对象
  * 
  * 对外提供 show 方法，入参为请求地址的参数
  * 
  * 相对 myDetailWin 多加了一个收藏功能，主要用于车源详情
  * 
  */
 commonApp.directive('sendMessage',['commonService',function(commonService){
		var sendMesDetail={};
		sendMesDetail.restrict="E";
		sendMesDetail.replace=true;
		sendMesDetail.templateUrl=function(element, attrs) {
			return attrs.templateurl;
		};
		
		sendMesDetail.compile=function(element, attrs){
			var url=attrs.url;
			var detailObj=attrs.detaildata;
			var winObj=attrs.name;
			var contactNumber = ""; //创建电话号码存储变量
			return function($scope, element, attrs){
				var win={
						rootView:true,// 当前本页面默认显示
						childView:false, //公共页面默认不显示
						postParam:{},
						smsContent:[], //短信内容数组
						getPhones:function(){
							contactNumber = "";
							vehicleCode = "";
							var chk_value =[];//定义一个数组    
		        	         $('input[name="checkbox2"]:checked').each(function(){//遍历每一个名字为interest的复选框，其中选中的执行函数    
		        	        	 chk_value.push($(this).val());//将选中的值添加到数组chk_value中    
		        	         });
		        	         if(chk_value.length==0){
		        	        	 commonService.alert("请选择数据");
									return;
		        	         }
		        	         for(var i=0;i<chk_value.length;i++){
		        	        	 contactNumber += eval('(' + chk_value[i] + ').contactNumber')+";";
		        	        	 vehicleCode += eval('(' + chk_value[i] + ').vehicleCode')+";";
		        	         }
		        	  	     var param = 'contactNumber='+contactNumber+"&vehicleCode="+vehicleCode;
							var url = "logisticsTrack.ajax?cmd=vehicleTrackSendMess";
							commonService.postUrl(url,param,function(data){
								//成功执行
					 	    	if(data!=null && data!=undefined && data!=""){
					 	    		eval("$scope."+detailObj+ "= data");
					 	    	}
							});
							// 显示发送短信页面
							win.rootView = false;
							win.childView = true;
						},
						addContent:function(){
							//获取信息填充
							var desAddress = $scope.des.inputValue;
							var sourceAddress=$scope.res.inputValue;
							var companyName = eval("$scope."+detailObj+ ".companyName");
							var goodsLoad = eval("$scope."+detailObj+ ".goodsLoad");
							var goodsName = eval("$scope."+detailObj+ ".goodsName");
							var linkMan = eval("$scope."+detailObj+ ".linkman");
							var mobilePhone = eval("$scope."+detailObj+ ".mobilePhone");
							if(sourceAddress==null || sourceAddress==""){
								commonService.alert("始发地不能为空");
								return;
							}
							if(desAddress==null || desAddress==""){
								commonService.alert("目的地不能为空");
								return;
							}
							if($scope.res.chooseCountyId==0){
								commonService.alert("请选择装货省市区/县!");
								$scope.res.focus();
								return ;
							}
							if($scope.des.chooseCountyId==0){
								commonService.alert("请选择卸货省市区/县!");
								$scope.res.focus();
								return ;
							}
							if(goodsLoad==null || goodsLoad==""){
								commonService.alert("货物重量不能为空");
								return;
							}
							if(goodsName==null || goodsName==""){
								commonService.alert("货物名不能为空");
								return;
							}
							if(goodsLoad==null || goodsLoad=="" || goodsLoad==0){
								commonService.alert("请输入货物重量");
								return false;
							}
							if(!validateNumber(goodsLoad)){
								commonService.alert("请输入正确的吨位数");
								return false;
							}
							var content = $("#sc_wby_x");
							win.smsContent.push({"sourceAddress":sourceAddress,"desAddress":desAddress,"goodsName":goodsName,"goodsLoad":goodsLoad});
						},
						deles:function(index){
							win.smsContent.splice(index, 1);
						},
						send:function(param){
							//获取信息填充
							var desAddress = $scope.des.inputValue;
							var sourceAddress=$scope.res.inputValue;
							var companyName = eval("$scope."+detailObj+ ".companyName");
							var goodsLoad = eval("$scope."+detailObj+ ".goodsLoad");
							var goodsName = eval("$scope."+detailObj+ ".goodsName");
							var linkMan = eval("$scope."+detailObj+ ".linkman");
							var mobilePhone = eval("$scope."+detailObj+ ".mobilePhone");
							var vehicleCode = eval("$scope."+detailObj+ ".vehicleCode");
							if(sourceAddress==null || sourceAddress==""){
								commonService.alert("始发地不能为空");
								return;
							}
							if(desAddress==null || desAddress==""){
								commonService.alert("目的地不能为空");
								return;
							}
							if($scope.res.chooseCountyId==0){
								commonService.alert("请选择装货省市区/县!");
								$scope.res.focus();
								return ;
							}
							if($scope.des.chooseCountyId==0){
								commonService.alert("请选择卸货省市区/县!");
								$scope.res.focus();
								return ;
							}
							if(goodsLoad==null || goodsLoad==""){
								commonService.alert("货物重量不能为空");
								return;
							}
							if(goodsName==null || goodsName==""){
								commonService.alert("货物名不能为空");
								return;
							}
							if(goodsLoad==null || goodsLoad=="" || goodsLoad==0){
								commonService.alert("请输入货物重量");
								return false;
							}
							if(!validateNumber(goodsLoad)){
								commonService.alert("请输入正确的吨位数");
								return false;
							}
							var content ="";
							for(var i=0;i<win.smsContent.length;i++){
								content += win.smsContent[i].sourceAddress+"到"+win.smsContent[i].desAddress+","+win.smsContent[i].goodsName+","+win.smsContent[i].goodsLoad+"吨;";
							}
							if(content == null || content =="" || content == undefined || win.smsContent.length == 0){
								commonService.alert("请添加短信内容");
								return false;
							}
							if(content>500){
								commonService.alert("短信内容太长，无法发送短信");
								return ;
							}
							win.postParam.billId=contactNumber;
							win.postParam.companyName=companyName;
							win.postParam.linkMan=linkMan;
							win.postParam.contactNumber=mobilePhone;
							win.postParam.doSendContent=content;
							win.postParam.vehicleCode = vehicleCode;
							var param = win.postParam;
							commonService.postUrl(url,param,function(data){
								//成功执行
								win.postParam={};
								win.smsContent=[];
								eval("$scope."+detailObj+ ".goodsLoad=''"); 
								eval("$scope."+detailObj+ ".goodsName=''"); 
								eval("$scope."+detailObj+ ".doSendContent=''"); 
								eval("$scope."+detailObj+ ".contactNumber=''");
								eval("$scope."+detailObj+ ".vehicleCode=''"); 
								//需要接收的手机号码可以不清空
//								contactNumber ＝ "";
								 $scope.des.clear();
								 $scope.res.clear();
								 commonService.alert("发送完成");
								// 显示发送短信页面
								win.rootView = true;
								win.childView = false;
							});
						}
				};
				eval("$scope."+winObj+ "= win");
			};
		};
		return sendMesDetail;
}]);
 
 