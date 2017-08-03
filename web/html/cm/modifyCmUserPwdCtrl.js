var modifyCmUserInfoPwdApp = angular.module("modifyCmUserInfoPwdApp", ['commonApp']);
modifyCmUserInfoPwdApp.controller("modifyCmUserInfoPwdCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	
	var tow={
			init:function(){
				$scope.user=this.user;
				this.bindEvent();
			},
			bindEvent:function(){
				$scope.doSave=this.doSave;
				$scope.close=this.close;
				$scope.checkPassWord=this.checkPassWord;
			},
			user:{
				password:"",
				confirmPassword:""
			},
			doSave:function(){
				
				if($scope.user.password==null || $scope.user.password==undefined || $scope.user.password==""){
					commonService.alert("密码不能为空");
					return false;
				}
				if($scope.user.confirmPassword==null || $scope.user.confirmPassword==undefined || $scope.user.confirmPassword==""){
					commonService.alert("确认密码不能为空");
					return false;
				}
				if($scope.user.password!=$scope.user.confirmPassword){
					commonService.alert("密码不一致，请重新输入！");
					return false;
				}
				
		
				var first = Math.round(Math.random()*80) + 10;
				var last = Math.round(Math.random()*80) + 10;
				var base64 = new Base64();
				$scope.user.password=base64.encode(first+""+$scope.user.password+""+last+"{zx}");
				$scope.user.confirmPassword=base64.encode(first+""+$scope.user.confirmPassword+""+last+"{zx}");
				commonService.postUrl("webCmUserInfoBO.ajax?cmd=modifyCmUserInfoPwd",$scope.user,function(data){
					
						$scope.user.password=$scope.checkPassWord($scope.user.password);
						$scope.user.confirmPassword=$scope.checkPassWord($scope.user.confirmPassword);
						commonService.alert("修改成功!",function(){
							commonService.closeToParentTab(true);
						});
				},function(data){
					
					$scope.user.password=$scope.checkPassWord($scope.user.password);
					$scope.user.confirmPassword=$scope.checkPassWord($scope.user.confirmPassword);
					window.top.alert(data.message);
					
				});
			},
			close:function(){
				commonService.closeToParentTab(true);
			},
			checkPassWord:function(passWord){
				var base64 = new Base64();
				if(!isNaN(passWord)){
				}else{
					var pwd = base64.decode(passWord);
					if(pwd.indexOf("{zx}")>0){
						pwd=pwd.substring(2, (pwd.length-6));
						passWord=pwd;
					}
				}
				return passWord;
			}
	};
	tow.init();
}]);
