var loginPlatApp = angular.module("loginPlatApp", ['commonApp']);
loginPlatApp.controller("loginPlatCtrl", ["$scope","commonService","$rootScope",function($scope,commonService,$rootScope) {
	var referrer = '';
	var login={
			isBrowserShow:false,
			companyName:"",//公司名称
			deptInfoList:new Array(),//公司下面的网点信息
			companyId:"",
			init:function(){
				this.bindEvent();
			},
			
			bindEvent:function(){
				$scope.login=this.login;
				$scope.submit=this.submit;
				$scope.companyName=this.companyName;
				$scope.deptInfoList=this.deptInfoList;
				$scope.tenantCode=this.tenantCode;
				$scope.queryCompanyInfo=this.queryCompanyInfo;
			},
			//根据公司编码查询公司信息，以及公司下面的用户信息
			queryCompanyInfo:function(){
				var that=this;
				commonService.postUrl("portalBusiBO.ajax?cmd=queryCompanyInfoPlat",{tenantCode:$scope.tenantCode},function(data){
					$scope.companyName=data.name;
					$scope.tenantId=data.tenantId;
				});
			},
			
			//登陆提交
			submit:function(version){
				var that=this;
				var username = $scope.userName;
				var password = $scope.password;
				if(username==null||username==''||username==undefined){
					$rootScope.alertMsg.alert("请输入账号!");
					return false;
				}
				if(password==null||password==''||password==undefined){
					$rootScope.alertMsg.alert("请输入密码!");
					return false;
				}
				if($scope.tenantCode == null || $scope.tenantCode == "" || $scope.tenantCode == undefined){
					$rootScope.alertMsg.alert("请输入公司编码!");
					return false;
				}
				var first = Math.round(Math.random()*80) + 10;
				var last = Math.round(Math.random()*80) + 10;
				var base64 = new Base64();
				var pwd=base64.encode(first+""+password+""+last+"{zx}");
				login.selectId={};
				login.selectId.username=username;
				login.selectId.password=pwd;
				login.selectId.tenantId=$scope.tenantId;
				
				var url = "portalBusiBO.ajax?cmd=doLoginPlat";
				commonService.postUrl(url,login.selectId,function(data){
					//成功执行
					if(data != null && data != undefined && data != "" && data.result=="1"){
						$rootScope.alertMsg.alert("用户名和密码错误!");
						$scope.userName="";
						$scope.password="";
						return;
					}else if(data != null && data != undefined && data != "" && data.result=="0"){
						window.location = "/toMainPlatform.html?ver="+version;
					}else{
						$rootScope.alertMsg.alert("系统异常!");
						that.genCode();
						$scope.password="";
					}
				});
			},
			//ENTER建操作
			myKeyup : function(e){
				 var keycode = window.event?e.keyCode:e.which; 
			     if(keycode==13){ 
			        	$scope.login(); 
			     } 
			}
	};
	login.init();
}]);

/**
	  * 弹出框 
	  */
	 var alert=function(text){
		 var appElement = document.querySelector('[ng-controller=loginPlatCtrl]');
		 var scope=angular.element(appElement).scope();
		 scope.alertMsg.alert(text);
		 scope.$apply();
	 }
	 
	 /**
	  * 确认框 
	  */
	 var confirm=function(text,sureCallBack,cancelCallBack,sureText,cancelText){
		 var appElement = document.querySelector('[ng-controller=loginPlatCtrl]');
		 var scope=angular.element(appElement).scope();
		 scope.confirmMsg.confirm(text,sureCallBack,cancelCallBack,sureText,cancelText);
		 scope.$apply();
	 }