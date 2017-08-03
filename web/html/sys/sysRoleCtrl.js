var sysRoleApp = angular.module("sysRoleApp", ['commonApp']);
sysRoleApp.controller("sysRoleCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	var userId= getQueryString("userId");
	var ord ={
		init:function(){
			this.userData();
			this.bindEvent();
			this.selectOrg();
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
			$scope.saveUserRole = this.saveUserRole;
			$scope.saveUrl = this.saveUrl;
			$scope.saveMenu = this.saveMenu;
			$scope.delSysRole = this.delSysRole;
			$scope.toUpdate = this.toUpdate;
			
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
			var url = "sysRoleBO.ajax?cmd=doQuerySysRole";
			ord.data.page=1;
			if(userId!=null && userId!=undefined && userId!=""){
				$scope.data.isShow=true;
			}else{
				$scope.data.isShow=false;
			}
			
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:ord.data,
							callBack:"$scope.setData"
						});
			},500);
			$scope.setData=function(){
				if(userId!=null && userId!=undefined && userId!=""){
					$scope.params.userId=userId;
					 commonService.postUrl("sysRoleBO.ajax?cmd=queryUserRole",$scope.params,function(data){
							if(data != null && data != undefined && data != ""){
								if(data.items.length>0){
									for(var i=0;i<data.items.length;i++){
										document.getElementById("checkbox"+data.items[i].roleId).checked=true;
									}
								}
							}
						});
				}
				
			};
		},
		
		//查询所有网点
		selectOrg:function(){
			commonService.postUrl("staticDataBO.ajax?cmd=queryOrganization","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.orgInfo = data;
					$scope.orgInfo.unshift({"orgId":-1,"orgName":"全部"});
					$scope.data.orgId = data[0].orgId;
				}
			});
		},
		/**选择一行**/
		selectOne : function(roleId){
			if(document.getElementById("checkbox"+roleId).checked && document.getElementById("checkbox"+roleId) != undefined){
				document.getElementById("checkbox"+roleId).checked=false;
			}else{
				document.getElementById("checkbox"+roleId).checked=true;
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
			commonService.openTab("11233","角色新增","/sys/addSysRole.html");
			//location.href="addSysRole.html";
		},
		toUpdate:function(roleId){
			commonService.openTab(roleId,"角色修改","/sys/addSysRole.html?roleId="+roleId);
		},
		close:function(){
			commonService.closeToParentTab(true);
		},
		/**保存用户角色**/
		saveUserRole:function(){
		     var chk_value =[];//定义一个数组    
	         $('input[name="check-1"]:checked').each(function(){//遍历每一个名字为interest的复选框，其中选中的执行函数    
	        	 chk_value.push($(this).val());//将选中的值添加到数组chk_value中    
	         });
	         if(chk_value.length==0){
	        	commonService.alert("请选择数据");
					return;
	         } 
	         if(userId!=null && $scope.params!=undefined && $scope.params!=""){
	        	 $scope.params.userId=userId;
	         }
	         $scope.params.roleIds=chk_value;
	         commonService.postUrl("sysRoleBO.ajax?cmd=saveUserRole",$scope.params,function(data){
					if(data != null && data != undefined && data != ""){
						commonService.alert("保存完成");
					}
				});
		},
		saveMenu:function(){
			 var chk_value =[];//定义一个数组    
	         $('input[name="check-1"]:checked').each(function(){//遍历每一个名字为interest的复选框，其中选中的执行函数    
	        	 chk_value.push($(this).val());//将选中的值添加到数组chk_value中    
	         });
	         if(chk_value.length==0){
	        	commonService.alert("请选择数据");
					return;
	         } 
	         if(chk_value.length>1){
	        	 commonService.alert("只能选择一行数据");
			     return;
	         }
	         var userId = chk_value[0];
	         commonService.openTab(userId,"角色查询","/sys/sysRole.html?userId="+userId);  
		},
		/**给角色添加菜单**/
		saveMenu:function(){
			 var chk_value =[];//定义一个数组    
	         $('input[name="check-1"]:checked').each(function(){//遍历每一个名字为interest的复选框，其中选中的执行函数    
	        	 chk_value.push($(this).val());//将选中的值添加到数组chk_value中    
	         });
	         if(chk_value.length==0){
	        	commonService.alert("请选择数据");
					return;
	         } 
	         if(chk_value.length>1){
	        	 commonService.alert("只能选择一行数据");
			     return;
	         }
	         var roleId = chk_value[0];
	         commonService.openTab(roleId,"添加菜单","/cm/MenuInfo.html?roleId="+roleId);  
		},
		/**给角色添加URL**/
		saveUrl:function(){
			 var chk_value =[];//定义一个数组    
	         $('input[name="check-1"]:checked').each(function(){//遍历每一个名字为interest的复选框，其中选中的执行函数    
	        	 chk_value.push($(this).val());//将选中的值添加到数组chk_value中    
	         });
	         if(chk_value.length==0){
	        	commonService.alert("请选择数据");
					return;
	         } 
	         if(chk_value.length>1){
	        	 commonService.alert("只能选择一行数据");
			     return;
	         }
	         var roleId = chk_value[0];
	         commonService.openTab(roleId+"1","添加URL","/sys/sysOperUrl.html?roleId="+roleId);  
		},
		delSysRole:function(){
			   var chk_value =[];//定义一个数组    
		         $('input[name="check-1"]:checked').each(function(){//遍历每一个名字为interest的复选框，其中选中的执行函数    
		        	 chk_value.push($(this).val());//将选中的值添加到数组chk_value中    
		         });
		         if(chk_value.length==0){
		        	commonService.alert("请选择数据");
						return;
		         } 
		         $scope.params.roleIds=chk_value;
		         commonService.confirm("确认取消数据?",function(){
			         commonService.postUrl("sysRoleBO.ajax?cmd=delSysRole",$scope.params,function(data){
							if(data != null && data != undefined && data != ""){
								$scope.page.reLoad(-1);
								commonService.alert("操作完成");
							}
						});
		         });
		},
	};
	ord.init();
}]);