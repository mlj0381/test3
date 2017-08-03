var orgManageApp = angular.module("orgManageApp", ['commonApp']);
orgManageApp.controller("orgManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var orgManage={
		init:function(){
			this.doQuery();
			this.bindEvent();
			this.selectProvince();
		},
		bindEvent:function(){
			$scope.param=this.param;
			$scope.doQuery=this.doQuery;
		    $scope.deleteDepart=this.deleteDepart;
		    $scope.print=this.print;
		    $scope.toView=this.toView;
		    $scope.query=this.query;
		    $scope.clear=this.clear;
		    $scope.selectAll=this.selectAll;
		    $scope.selectCity=this.selectCity;
		    $scope.selectProvince=this.selectProvince;
		    $scope.selectDistrict=this.selectDistrict;
		    $scope.streetCreat = this.streetCreat;
		    $scope.doSave=this.doSave;
		    $scope.doModify=this.doModify;
		    $scope.selectOne=this.selectOne;
		    $scope.query=this.query;
		    $scope.close=this.close;
		    $scope.recharge=this.recharge;
		    $scope.toUpdate=this.toUpdate;
		    $scope.toDel = this.toDel;
		},
		params:{
			
		},
		query:{
		},
		/** 全选 */
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
		/** 列表查询 */
		doQuery:function(){
			
			orgManage.params.provinceId=orgManage.query.provinceId;
			orgManage.params.regionId=orgManage.query.regionId;
			orgManage.params.countyId=orgManage.query.countyId;
			orgManage.params.streetId=orgManage.query.streetId;
			orgManage.params.streetName = orgManage.query.streetName;
			orgManage.params.address = orgManage.query.address;
			orgManage.params.orgType=orgManage.query.orgType;
			orgManage.params.businessType=orgManage.query.businessType;
			orgManage.params.orgName=orgManage.query.orgName;
			orgManage.params.orgPrincipalPhone=orgManage.query.orgPrincipalPhone;
			orgManage.params.orgPrincipal=orgManage.query.orgPrincipal;
			orgManage.params.page=1;
			var url = "organizationBO.ajax?cmd=doQuery";
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:orgManage.params,
							callBack:'setContentHegthDelay'
						});
			},500);
		},
		/** 清空查询条件 */
		clear:function(){
			orgManage.query.provinceId=-1;
			orgManage.query.regionId="";
			orgManage.query.countyId="";
			orgManage.query.streetId="";
			$scope.districtData={};
			$scope.cityData={};
			$scope.streetData = {};
			orgManage.query.orgType='-1';
			orgManage.query.businessType='-1';
			orgManage.query.orgName='';
			orgManage.query.orgPrincipalPhone='';
			orgManage.query.orgPrincipal='';
			
		},
		/** 选择一行* */
		selectOne : function(orgId){
			if(document.getElementById("checkbox"+orgId).checked && document.getElementById("checkbox"+orgId) != undefined){
				document.getElementById("checkbox"+orgId).checked=false;
			}else{
				document.getElementById("checkbox"+orgId).checked=true;
			}
		},
		/** 查看* */
		toView:function(){
			var orgId='';
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条网点信息!");
				return false;
			}
			if($("input[name='check-1']:checked").length>1){
				commonService.alert("只能选择一条网点信息!");
				return false;
			}
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					orgId=data.orgId;
				}
			});
			commonService.openTab(orgId,"配载详情","/ord/depearInfo.html?orgId="+orgId);
				},
		/** *保存* */
		doSave:function(){
			commonService.openTab("324234","新增网点信息","/org/addOrgInfo.html");
		},
		/** *修改* */
		doModify:function(){
			var orgId='';
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条网点信息!");
				return false;
			}
			if($("input[name='check-1']:checked").length>1){
				commonService.alert("只能选择一条网点信息!");
				return false;
			}
			var flag=false;
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					if(data.state!=1){
						flag=true;
						commonService.alert("批次["+data.orgId+"]状态为"+data.stateName+",不可以操作[修改]!");
						return false;
					}
					orgId=data.orgId;
				}
			});
			if(flag){
				return false;
			}
			commonService.openTab(orgId,"修改网点信息","/ord/addDepart.html?orgId="+orgId);
		},
		/** 打印 */
		print:function(){
		},
		/** 删除 */
		deleteDepart:function(){
			var orgId='';
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条网点信息!");
				return false;
			}
			var flag=false;
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					if(data.state!=1){
						flag=true;
						commonService.alert("批次["+data.orgId+"]状态为"+data.stateName+",不可以操作!");
						return false;
					}
					if(orgId==''){
						orgId=data.orgId;
					}else{
						orgId=orgId+","+data.orgId;
					}
				}
			});
			if(flag){
				return false;
			}
			var param = {"orgId":orgId};
			var url = "orderInfoBO.ajax?cmd=deleteDepartInfo";
			commonService.postUrl(url,param,function(data){
				// 成功执行
				if(data!=null && data!=undefined && data!=""){
						commonService.alert("删除成功!");
						$scope.doQuery();
	 	    	}
			});
		},
		toUpdate:function(){
			var selectedDate = $scope.page.getSelectData();
			if(selectedDate == undefined || selectedDate.length == 0){
				commonService.alert("请选择一条数据！");
				return false;
			}
			commonService.openTab(selectedDate[0].orgId+"4","修改网点信息","/org/addOrgInfo.html?orgId="+selectedDate[0].orgId);
		},
		selectProvince:function(){
			var param = {"isAll":"Y"};
			var url = "staticDataBO.ajax?cmd=selectProvince";
			commonService.postUrl(url,param,function(data){
				// 成功执行
				if(data!=null && data!=undefined && data!=""){
						$scope.provinceData=data;
						orgManage.query.provinceId=data.items[0].id;
	 	    	}
			});
		},
		selectCity:function(provinceId){
			if(parseInt(provinceId)>0){
				var param = {"isAll":"Y","provinceId":provinceId};
				var url = "staticDataBO.ajax?cmd=selectCity";
				commonService.postUrl(url,param,function(data){
					// 成功执行
					if(data!=null && data!=undefined && data!=""){
							$scope.cityData=data;
							orgManage.query.regionId=data.items[0].id;
		 	    	}
				});
			}
		},
		selectDistrict:function(cityId){
			if(parseInt(cityId)>0){
				var param = {"isAll":"Y","cityId":cityId};
				var url = "staticDataBO.ajax?cmd=selectDistrict";
				commonService.postUrl(url,param,function(data){
					// 成功执行
					if(data!=null && data!=undefined && data!=""){
							$scope.districtData=data;
							orgManage.query.countyId=data.items[0].id;
		 	    	}
				});
			}
		},
		streetCreat : function (countyId){
			if(parseInt(countyId)>0){
				var param = {"isAll":"Y","districtId":countyId};
				var url = "staticDataBO.ajax?cmd=selectStreet";
				commonService.postUrl(url,param,function(data){
					// 成功执行
					if(data!=null && data!=undefined && data!=""){
							$scope.streetData=data;
							orgManage.query.streetId=data.items[0].id;
		 	    	}
				});
			}
		},
		recharge:function(){
			var orgId='';
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请选择一条网点信息!");
				return false;
			}
			if($("input[name='check-1']:checked").length>1){
				commonService.alert("只能选择一条网点信息!");
				return false;
			}
			var flag=false;
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
						orgId=data.orgId;
				}
			});
			var param = {"orgId":orgId,"money":$scope.money};
			var url = "organizationBO.ajax?cmd=recharge";
			commonService.postUrl(url,param,function(data){
				// 成功执行
				if(data!=null && data!=undefined && data!=""){
					commonService.alert("充值成功!");
					$scope.close();
	 	    	}
			});
		},
		close:function(){
			document.getElementById('popup2').style.display='none';
			document.getElementById('fade1_xz').style.display='none';
			$scope.money='';
		},
		toDel:function(){
			var selectedDate = $scope.page.getSelectData();
			if(selectedDate == undefined || selectedDate.length == 0){
				commonService.alert("请选择一条数据！");
				return false;
			}
			var isDel = false;
			var name = "";
			commonService.postUrl("organizationBO.ajax?cmd=chackOrgByUser",{"orgId":selectedDate[0].orgId},function(data){
				if (data != null && data != "") {
					isDel = true;
					name = data;
				}else{
					isDel = false;
				}
			});
			$timeout(function(){
				if (isDel) {
					commonService.confirm("删除该网点有用户【"+name+"】，确认还继续删除！",function(){
						commonService.confirm("删除该网点，同时会将相关的线路，该网点配置了区域，配置的线路价格等删除掉，确认操作!",function(){
							$timeout(function(){
								commonService.postUrl("organizationBO.ajax?cmd=delOrganization",{"orgId":selectedDate[0].orgId},function(data){
									if (data == "Y") {
										commonService.alert("删除成功！");
										$scope.doQuery();
									}
								});
							},10);
						});
					});
				}else{
					commonService.confirm("删除该网点，同时会将相关的线路，该网点配置了区域，配置的线路价格等删除掉，确认操作!",function(){
						commonService.postUrl("organizationBO.ajax?cmd=delOrganization",{"orgId":selectedDate[0].orgId},function(data){
							if (data == "Y") {
								commonService.alert("删除成功！");
								$scope.doQuery();
							}
						});
					});
				}
				
			},100);
			
		}
	}
	orgManage.init();
}]);
