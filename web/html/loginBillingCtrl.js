var loginApp = angular.module("loginApp", ['commonApp']);
loginApp.controller("loginCtrl", ["$scope","commonService","$rootScope",function($scope,commonService,$rootScope) {
	var referrer = '';
	var login={
			codeUrl:"genCode",
			init:function(){
				this.bindEvent();
				this.genCode();
				$scope.consigneeBill = 0;
			},
			bindEvent:function(){
				$scope.genCode = this.genCode;
				$scope.submit = this.submit;
//				$scope.myKeyup = this.myKeyup;
				jQuery("#_vaildCode").bind('keydown', 'return', function (evt){// 为元素绑定事件
					login.submit(jQuery('#_ver').val());
					return true;
				});
			},
			genCode:function(){
				var timestamp = (new Date()).valueOf();
				this.codeUrl="genCode?timestamp=" + timestamp;
			},
			//登陆提交
			submit:function(){
				$scope.trackingNumList = null;
				var trackingNum = $scope.trackingNum;
				var vaildCode = $scope.vaildCode;
				login.selectId = {};
				login.selectId.trackingNum =trackingNum;
				login.selectId.vaildCode = vaildCode;
				login.selectId.consigneeBill = $scope.consigneeBill;
				
				if(trackingNum == null || trackingNum == undefined || jQuery.trim(trackingNum) == ''){
					alert("请输入需要查询信息!");
					return false;
				}
				if(vaildCode == null || vaildCode == undefined || jQuery.trim(vaildCode) == ''){
					alert("请输入验证码!");
					return false;
				}
				/**替换全部**/
				function replaceAll(obj,str1,str2){ 
					var result = obj.replace(eval("/"+str1+"/gi"),str2); 
					return result; 
				}
				//把中文，替换成英文,
				if(trackingNum.indexOf("，") > 0){
					trackingNum = replaceAll(trackingNum,"，",",");
				}
				var trackingNums = [];
				if($scope.consigneeBill != parseInt(1)){
					trackingNums = trackingNum.split(",");
				}else{
					trackingNums[0] = trackingNum;
				}
				
				if(trackingNums.length > 5 && $scope.consigneeBill != parseInt(1)){
					alert("最多只能查5单，超过5单不展示后面单的信息！");
				}
				
				var url = "scheTaskBO.ajax?cmd=queryOrdBillDetailByTrackingNum";
				commonService.postUrl(url,login.selectId,function(data){
					$scope.genCode();
					if(data != null && data != undefined && data != ""){
						$scope.trackingNumList = data.items;
						$scope.vaildCode = "";
						document.getElementById('nrxs').style.display='block';
						document.getElementById('nryc').style.display='none';
					}
				});
			},
			
			//ENTER建操作
//			myKeyup : function(e){
//				 var keycode = window.event?e.keyCode:e.which; 
//			     if(keycode==13){ 
//			        	$scope.login(); 
//			     } 
//			}
	};
	login.init();
}]);
