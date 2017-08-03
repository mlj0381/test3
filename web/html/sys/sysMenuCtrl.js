var sysMenuApp = angular.module("sysMenuApp", ['commonApp']);
sysMenuApp.controller("sysMenuCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	var userId= getQueryString("userId");
	var ord ={
		init:function(){
			this.userData();
			this.bindEvent();
			//this.selectOrg();
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
			var url = "sysRoleBO.ajax?cmd=doQuerySysMenu";
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
		/**选择一行**/
		selectOne : function(menuId){
			if(document.getElementById("checkbox"+menuId).checked && document.getElementById("checkbox"+menuId) != undefined){
				document.getElementById("checkbox"+menuId).checked=false;
			}else{
				document.getElementById("checkbox"+menuId).checked=true;
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
			commonService.openTab("11229","新增菜单","/sys/addSysMenu.html");
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
		toUpdate:function(menuId){
			commonService.openTab(menuId,"修改菜单","/sys/addSysMenu.html?menuId="+menuId);
		},
		/**删除**/
		toDel:function(){
			var chk_value =[];//定义一个数组    
	        $('input[name="check-1"]:checked').each(function(){//遍历每一个名字为interest的复选框，其中选中的执行函数    
	       	 chk_value.push($(this).val());//将选中的值添加到数组chk_value中    
	        });
	        if(chk_value.length==0){
	       	commonService.alert("请选择数据");
					return;
	        } 
	        $scope.params.menuIds=chk_value;
			  commonService.confirm("确认取消菜单?",function(){
			  commonService.postUrl("sysRoleBO.ajax?cmd=doDelMenu",$scope.params,function(data){
				  $scope.page.reLoad(-1);
				  commonService.alert("操作完成");
			 });
			});
		},
	};
	ord.init();
}]);