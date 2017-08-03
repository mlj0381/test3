var sysUrlApp = angular.module("sysUrlApp", ['commonApp']);
sysUrlApp.controller("sysUrlCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	var roleId= getQueryString("roleId");
	var ord ={
		init:function(){
			this.userData();
			this.bindEvent();
			this.doQuery();
		},
		bindEvent:function(){
			$scope.selectOne = this.selectOne;
			$scope.selectAll = this.selectAll;
			$scope.toSave = this.toSave;
			$scope.doQuery = this.doQuery;
			$scope.params = this.params;
		
			$scope.doSave = this.doSave;
			$scope.close = this.close;
			$scope.data = this.data;
			$scope.close = this.close;
			$scope.saveRoleUrl = this.saveRoleUrl;
			$scope.toUpdate = this.toUpdate;
			$scope.toDel = this.toDel;
		},
		userData:function(){
			$scope.currOrgId=userInfo.orgId;
			$scope.currOrgName=userInfo.orgName;
		},
		data:{
			isShow:false
		},
		params:{},
		//查询事件
		doQuery:function(){
			var url = "sysRoleBO.ajax?cmd=querySysOperUrl";
			ord.data.page=1;
			if(roleId!=null && roleId!=undefined && roleId!=""){
				$scope.data.isShow=true;
			}else{
				$scope.data.isShow=false;
			}
			 commonService.postUrl(url,ord.data,function(data){
				 $scope.sysUrl=data;
				 if(roleId!=null && roleId!=undefined && roleId!=""){
						$scope.params.roleId=roleId;
						 commonService.postUrl("sysRoleBO.ajax?cmd=queryUrlByRoleId",$scope.params,function(data){
								if(data != null && data != undefined && data != ""){
									if(data.items.length>0){
										for(var i=0;i<data.items.length;i++){
											var urlIparm = document.getElementById("checkbox"+data.items[i].id);
											if(urlIparm!=null && urlIparm!=undefined && urlIparm!=""){
												document.getElementById("checkbox"+data.items[i].id).checked=true;
											}
											
										}
									}
								}
							});
					}
			 });
		},
		
		
		/**选择一行**/
		selectOne : function(urlId){
			if(document.getElementById("checkbox"+urlId).checked && document.getElementById("checkbox"+urlId) != undefined){
				document.getElementById("checkbox"+urlId).checked=false;
			}else{
				document.getElementById("checkbox"+urlId).checked=true;
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
			commonService.openTab("11228","URL新增","/sys/addSysUrl.html");
		},
		close:function(){
			commonService.closeToParentTab(true);
		},
		saveRoleUrl:function(){
		     var chk_value =[];//定义一个数组    
	         $('input[name="check-1"]:checked').each(function(){//遍历每一个名字为interest的复选框，其中选中的执行函数    
	        	 chk_value.push($(this).val());//将选中的值添加到数组chk_value中    
	         });
	         if(chk_value.length==0){
	        	commonService.alert("请选择数据");
					return;
	         } 
	         if(roleId!=null && $scope.params!=undefined && $scope.params!=""){
	        	 $scope.params.roleId=roleId;
	         }
	         $scope.params.urlIds=chk_value;
	         commonService.postUrl("sysRoleBO.ajax?cmd=saveRoleUrl",$scope.params,function(data){
					if(data != null && data != undefined && data != ""){
						commonService.alert("保存完成");
					}
				});
		},
		toUpdate:function(id){
			commonService.openTab(id,"URL修改","/sys/addSysUrl.html?id="+id);
		},
		toDel:function(){
			var chk_value =[];//定义一个数组    
	         $('input[name="check-1"]:checked').each(function(){//遍历每一个名字为interest的复选框，其中选中的执行函数    
	        	 chk_value.push($(this).val());//将选中的值添加到数组chk_value中    
	         });
	         if(chk_value.length==0){
	        	commonService.alert("请选择数据");
					return;
	         } 
	         $scope.params.urlIds=chk_value;
			  commonService.confirm("确认取消url?",function(){
			  commonService.postUrl("sysRoleBO.ajax?cmd=doDelUrl",$scope.params,function(data){
				   $scope.doQuery();
				   commonService.alert("操作完成");
			  });
			});
		},
	};
	ord.init();
}]);