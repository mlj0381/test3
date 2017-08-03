 var menuApp = angular.module("menuApp", ['commonApp','treeApp']);
 menuApp.controller("menuCtrl", ['$http',"$scope","commonService",function($http,$scope,commonService){ 
	 var roleId= commonService.getQueryString("roleId");
	 var roleName= commonService.getQueryString("roleName");
	 var tenantId=commonService.getQueryString("tenantId");
	 var menuTypeInput=commonService.getQueryString("menuType");
	 var vm = {
			 init:function(){
				 this.bindEvent();
				 this.initDate();
				 this.queryMenuType();
			 },
			 bindEvent:function(){
				 $scope.toClose=this.toClose;
				 $scope.toSave=this.toSave;
				 if(roleName!=null && roleName!=""){
					 this.roleName=roleName;
				 }
				 $scope.roleName=this.roleName;
				 $scope.menuTypeChange=this.menuTypeChange;
			 },
			 roleName:"",
			 menuTypeChange:function(){
				 vm.initDate();
			 },
			 queryMenuType:function(){
					var that=this;
					//查询公司管理员角色
					commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType",{hasAll:"false",codeType:"MENU_TYPE"},function(data){
						if(data != null && data != undefined && data != ""){
							$scope.menuTypeInfo = data.items;
							if(menuTypeInput!=undefined && menuTypeInput!=""){
								$scope.menuType=menuTypeInput;
							}else{
								$scope.menuType=$scope.menuTypeInfo[0].codeValue;
							}
						}
					});
				},
			 toClose:function(){
					commonService.closeToParentTab(true);
			},
			
			toSave:function(){
				var entityIds=new Array();
				vm.getSelectEntityId($scope.treeData,entityIds);
				var params={
						roleName:$scope.roleName,
						menuType:$scope.menuType,
						entityId:entityIds
				};
				if(roleId>0){
					params.roleId=roleId;
					params.tenantId=tenantId;
				}
				
				commonService.postUrl("sysRoleBO.ajax?cmd=saveUserRole",params,function(data){
					commonService.alert("添加成功！",function(){
						vm.toClose();
					});
				});
			},
			getSelectEntityId:function(treeData,entityIds){
				for(var index in treeData){
					var menu=treeData[index];
					if(menu.$$isChecked){
						entityIds.push(menu.entityId);
						if(menu.childMenus!=undefined && menu.childMenus.length>0){
							this.getSelectEntityId(menu.childMenus,entityIds);
						}
					}
				}
			},
			initDate:function(){
				var params={};
				if($scope.menuType=="" || $scope.menuType==undefined){
					if(menuTypeInput!=undefined && menuTypeInput!=""){
						params.menuType=menuTypeInput;
					}else{
						params.menuType=1;
					}
				}else{
					params.menuType=$scope.menuType;
				}
				if(roleId>0){
					params.roleId=roleId;
				}
				 commonService.postUrl("portalBusiBO.ajax?cmd=getSelectMenu",params,function(data){
						if(data != null && data != undefined && data != ""){
							$scope.treeData=data.childMenus;
							for(var index in $scope.treeData){
								$scope.treeData[index].$$isExpend=true;//默认展开
							}
							setContentHegthDelay();
						}
					});
			 }
	 };
	 
	vm.init();
    
 }]);
