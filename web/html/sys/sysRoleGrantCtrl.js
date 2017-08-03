var sysRoleGrantApp = angular.module('sysRoleGrantApp', ["commonApp"]);
sysRoleGrantApp.controller("sysRoleGrantCtrl",["$scope","commonService","$timeout","$rootScope","$sce", function($scope,commonService,$timeout,$rootScope,$sce) {
	var roleGrant={
			 init:function(){
	   				this.bindEvent();
	   				this.selecSysRole();
	   				this.getEntity();
	   				this.selectcompany();
	   				this.getOrgData();
	   			},
	   		    /**绑定操作事件*/
	   			bindEvent:function(){
	   				$scope.data=this.data;
	   				$scope.doQuery=this.doQuery;
	   				$scope.role=this.role;
	   				$scope.doSave=this.doSave;
	   				$scope.updateRole=this.updateRole;
	   				//$scope.xinZeng=this.xinZeng;
	   				$scope.doQueryGrant=this.doQueryGrant;
	   				$scope.getEntity=this.getEntity;
	   				$scope.RoleGrant=this.RoleGrant;
	   				
	   				$scope.getObject=this.getObject;
	   				$scope.showHide=this.showHide;
                    $scope.selectAll=this.selectAll;
                    $scope.changeOrg=this.changeOrg;
                    $scope.getStsData=this.getStsData;
                    $scope.selectOne=this.selectOne;
                    $scope.selChange=this.selChange;
                    $scope.selecSysRole=this.selecSysRole;
	   			}, 
	   			data:{
	   				_GRID_TYPE:"jq",
					page:1,
					rows:10,
					totalPage:1,
				},
				role:{
					entityRecId:"",
					isCheck:1,
				},
				RoleGrant:{
					
				},
				
				/**获取公司数据*/
				selectcompany:function(){
					commonService.postUrl("cmCompanyBO.ajax?cmd=getCompanyList","",function(data){
					
						if(data != null && data != undefined && data != ""){
							$scope.companyData=data;
							data.tenantId=data.items[0].tenantId;
						}
					});
				},
				
				/*tenChange:function(tenantId){
					
				}*/
				/**获取角色数据*/
				selecSysRole:function(tenantId){
					
					commonService.postUrl("sysRoleBO.ajax?cmd=getRoleByTenantId","tenantId="+tenantId,function(data){
						//console.log(data==null);
						//if(data != null && data != undefined && data != ""){
							$scope.sysRole=data;
						//}
					});
				},
				
				selChange:function(roleId){
					if(roleId!=null && roleId>0){
						$scope.updateRole(roleId);
					}else{
						 $('input[name="checkbox2"]:checked').each(function(){
							 var recId = $(this).attr("id");
							 document.getElementById(recId).checked=false;
						 });
					}
				},
				/**获取组织数据*/
				getOrgData:function(){
					/*var param = "";
					var url = "sysOperator.ajax?cmd=getOrgData";
					commonService.postUrl(url,param,function(data){
						//成功执行
			 	    	if(data!=null && data!=undefined && data!=""){
			 	    		$scope.orgData=data;
			 	    		$scope.data.orgId=$scope.orgData.items[1].organizeId;
			 	    		$scope.role.orgId=$scope.data.orgId;
			 	    		$scope.getStsData();
			 	    	}
					});*/
				},     
				        /**获取菜单**/
						getEntity:function(){
							var param="";
							var url="portalBusiBO.ajax?cmd=getTopMenu";
							var arrayList = new Array();
							var array = new Array();
							commonService.postUrl(url,param,function(data){
								for(var i=0;i< data.childMenus.length;i++){
									if((i+1)%4!=1){
									  arrayList.push(data.childMenus[i]);
									  if(i==data.childMenus.length-1){
										  array.push(arrayList);
									  }
									}else{
										array.push(arrayList);
										arrayList=new Array(); 
										arrayList.push(data.childMenus[i]);
									}
								}
								$scope.RoleGrant=array;
							});
							
						},
						/**根据组织获取角色**/
						changeOrg:function(orgId){
							/*if(orgId>0){
							   $scope.role.orgId=orgId;
							   $scope.getStsData();
							}*/
						},
				/**新增权限**/
				doSave:function(){
					var chk_value =[];//定义一个数组 
					/*if($scope.role.roleId==0){
						 commonService.alert("请选择角色");
						 return;
					}
					*/
					$scope.role.entityRecId="";
					  $('input[name="checkbox2"]:checked').each(function(){//遍历每一个名字为checkbox2的复选框，其中选中的执行函数    
			   	        	    chk_value.push($(this).attr("value"));//将选中的值添加到数组chk_value中    
			   	         });
					  if(chk_value.length==0){
						  commonService.alert("请选择数据");
							return;
					  }
					  $scope.role.entityIds = chk_value;
					    var param = $scope.role;
					    var url="";
					    commonService.postUrl("sysRoleBO.ajax?cmd=doSaveRoleMenu",param,function(data){
			            	commonService.alert("设置成功");
			            });
				},
			/**回显权限**/
			updateRole:function(roleId){
				roleGrant.getEntity();
				$scope.role.roleId=roleId;
				$scope.role.isCheck=2;
				 var param = $scope.role;
				 var url = "sysRoleBO.ajax?cmd=queryMenuByRoleId";
				 $('input[name="checkbox2"]:checked').each(function(){
					 var recId = $(this).attr("id");
					 document.getElementById(recId).checked=false;
				 });
				 commonService.postUrl(url,param,function(data){
					 /**回显权限**/
					 if(data!=null && data!="" && data!=undefined){
						 for(var i=0;i<data.numRows;i++){
							 if( document.getElementById(data.items[i].entityId)!=null &&  document.getElementById(data.items[i].entityId)!=undefined){
							    document.getElementById(data.items[i].entityId).checked=true;
							 }if(document.getElementById("check"+data.items[i].entityId)!=null && document.getElementById("check"+data.items[i].entityId)!=undefined){
								 document.getElementById("check"+data.items[i].entityId).checked=true;
							 }
						 }
					 }
			    });
			},
			 getObject:function(objectId){
				if(document.getElementById && document.getElementById(objectId)){
					return document.getElementById(objectId);
				}else if(document.all && document.all(objectId)){
					return document.all(objectId);
				}else if(document.layers && document.layers[objectId]){
					return document.layers[objectId];
				}else{
					return false;
				}
			},
			 showHide:function(objId){
				var obj = $scope.getObject(objId);
				if(obj.style.display == "none"){
					obj.style.display = "block";
					$("#min"+objId).removeClass("plus");
					$("#min"+objId).addClass("minus");
				}else{
					obj.style.display = "none";
					$("#min"+objId).removeClass("minus");
					$("#min"+objId).addClass("plus");
				}
			},
			
			/**全选**/
			selectAll : function(sysInfo) {
				var checkbox = sysInfo.childMenus;
				if (document.getElementById("check"+sysInfo.entityId).checked) {
					for (var i = 0; i < checkbox.length; i++) {
						document.getElementById(checkbox[i].entityId).checked = true;
					}
				} else {
					for (var i = 0; i < checkbox.length; i++) {
						document.getElementById(checkbox[i].entityId).checked = false;
					}
				}
			},
			/**
			 * 选择一行数据
			 * @param obj
			 */
			selectOne:function(obj){
				document.getElementById("check"+obj.entityId).checked = true;
			},
	 };
	roleGrant.init();
  
  }]);
