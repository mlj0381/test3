var acFeeConfigApp = angular.module("acFeeConfigApp", ['commonApp']);
acFeeConfigApp.controller("acFeeConfigCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	var ord ={
		init:function(){
			this.userData();
			this.bindEvent();
			this.selecUserInfo();
			this.doQuery();
		},
		bindEvent:function(){
			$scope.selectOne = this.selectOne;
			$scope.selectAll = this.selectAll;
			$scope.toSave = this.toSave;
			$scope.doQuery = this.doQuery;
			
			$scope.doSave = this.doSave;
			$scope.close = this.close;
			$scope.data = this.data;
		},
		userData:function(){
			$scope.currOrgId=userInfo.orgId;
			$scope.currOrgName=userInfo.orgName;
		},
		data:{},
		//查询事件
		doQuery:function(){
			var url = "acFeeConfigBO.ajax?cmd=doQueryFeeConfig";
			ord.data.page=1;
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:ord.data
						});
			},500);
		},
		//查询所有网点
		selecUserInfo:function(){
			commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType","codeType=USER_TYPE",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.userData = data;
					$scope.userData.items.unshift({"codeValue":"-1","codeName":"全部"});
					$scope.data.userType = data.items[0].codeValue;
				}
			});
		},
		/**选择一行**/
		selectOne : function(feeId){
			if(document.getElementById("checkbox"+feeId).checked && document.getElementById("checkbox"+feeId) != undefined){
				document.getElementById("checkbox"+feeId).checked=false;
			}else{
				document.getElementById("checkbox"+feeId).checked=true;
			}
		},
		/**全选*/
		selectAll : function() {
			var checkbox = document.getElementsByName("check-1");
			if (document.getElementById("checkbox").checked) {
				for (var i = 0; i < checkbox.length; i++) {
					checkbox[i].checked = true;
				}
			} else {
				for (var i = 0; i < checkbox.length; i++) {
					checkbox[i].checked = false;
				}
			}
		},
		toSave:function(){
			commonService.openTab("112433","科目新增","/ac/addAcFeeConfig.html");
			//location.href="addAcFeeConfig.html";
		}
	};
	ord.init();
}]);