var trunkApp = angular.module("trunkApp", ['commonApp']);
trunkApp.controller("trunkCtrl", ['$http',"$scope","commonService","$timeout",function($http,$scope,commonService,$timeout){ 
	 var trunk = {
			 init:function(){
				 this.bindEvent();
				 this.selectProvince();
				 this.selectMerchant();
				 this.selectZxLine();
				 this.doQuery();
				 this.initPaymentType();
				 this.cityAll();
			 },
			 bindEvent:function(){
				 $scope.query=this.query;
				 $scope.update=this.update;
				 $scope.clear=this.clear;
				 $scope.add=this.add;
				 $scope.doQuery=this.doQuery;
				 $scope.doDel=this.doDel;
				 $scope.openUpdateDialog=this.openUpdateDialog;
				 $scope.update=this.update;
				 $scope.selectCity=this.selectCity;
			     $scope.selectDistrict=this.selectDistrict;
			     $scope.closeUpdateDialog=this.closeUpdateDialog;
			 },
			 query:{
				page: 1,
				rows: 10
			 },
			 update:{
				 url:"",
				 costId:"",
				 costDetailId:"",
				 paymentType:"",
				 volumePriceDouble:"",
				 weightPriceDouble:"",
				 countPriceDouble:""
			 },
			 add:function(){
				 commonService.openTab("666","新增费用配置","/cm/trunkCostAdd.html");
			 },
			 doQuery:function(){
					var url = "cmTrunkCostBO.ajax?cmd=doQueryCmTrunkCost";
					$timeout(function(){
						$scope.page.load({
									url:url,
									params:$scope.query,
									callBack:'setContentHegthDelay'
								});
					},500);
			 },
			 doDel:function(){
					var selectedDate = $scope.page.getSelectData();
					if(selectedDate == undefined || selectedDate.length == 0){
						commonService.alert("请选择一条数据！");
						return false;
					}
					commonService.confirm("确认要删除该条费用配置!",function(){
						var costDetailId=selectedDate[0].costDetailId;
						commonService.postUrl("cmTrunkCostBO.ajax?cmd=doDelCmTrunkCostDetail",{costDetailId:costDetailId},function(data){
							commonService.alert("删除成功！");
							$scope.doQuery();
						});
					});
			 },
			 openUpdateDialog:function(type){
					var selectedDate = $scope.page.getSelectData();
					if(selectedDate == undefined || selectedDate.length == 0){
						commonService.alert("请选择一条数据！");
						return false;
					}
					$("#fade1_xz").css("display","block");
					if(type=="all"){
						$("#update").css("width","790px");
						$("#update").css("margin-left","-320px");
						$("#paymentType").attr("disabled","disabled");
						$scope.visible="";
						trunk.update.costDetailId=selectedDate[0].costDetailId;
						trunk.update.orgId=selectedDate[0].orgId;
						trunk.update.businessOrgId=selectedDate[0].businessOrgId;
						trunk.update.paymentType=selectedDate[0].paymentType;
						trunk.update.volumePriceDouble=selectedDate[0].volumePriceDouble;
						trunk.update.weightPriceDouble=selectedDate[0].weightPriceDouble;
						trunk.update.countPriceDouble=selectedDate[0].countPriceDouble;
						trunk.update.url="cmTrunkCostBO.ajax?cmd=updateCmTrunkCostDetail";
					}else{
						$("#update").css("width","670px");
						$("#update").css("margin-left","-260px");
						$("#paymentType").removeAttr("disabled");
						$scope.visible="none";
						trunk.update.costId=selectedDate[0].costId;
						trunk.update.orgId=selectedDate[0].orgId;
						trunk.update.businessOrgId=selectedDate[0].businessOrgId;
						trunk.update.paymentType=selectedDate[0].paymentType;
						trunk.update.url="cmTrunkCostBO.ajax?cmd=updatePaymentType";
					}
					$("#update").css("display","block");
			 },
			 update:function(){
					commonService.postUrl(trunk.update.url,trunk.update,function(data){
						$("#update").css("display","none");
						trunk.closeUpdateDialog();
						$scope.doQuery();
					});
			 },
			 clear:function(){
					$scope.query.province=-1;
					$scope.query.businessOrgId="";
					$scope.query.orgId="";
					$scope.query.city=-1;
					$scope.cityData={};
					
			 },
			 initPaymentType:function(){
					commonService.postUrl("staticDataBO.ajax?cmd=selectPaymentType","",function(data){
						if(data != null && data != undefined && data != ""){
							$scope.paymentTypeData = data;
						}
					});
			 },
			 selectProvince:function(){
					var param = {"isAll":"Y"};
					var url = "staticDataBO.ajax?cmd=selectProvince";
					commonService.postUrl(url,param,function(data){
						if(data!=null && data!=undefined && data!=""){
								$scope.provinceData=data;
								trunk.query.province=data.items[0].id;
			 	    	}
					});
				},
			 selectCity:function(provinceId){
					if(parseInt(provinceId)>0){
						var param = {"isAll":"Y","provinceId":provinceId};
						var url = "staticDataBO.ajax?cmd=selectCity";
						commonService.postUrl(url,param,function(data){
							if(data!=null && data!=undefined && data!=""){
									$scope.cityData=data;
									trunk.query.city=data.items[0].id;
				 	    	}
						});
					}
				},
				selectMerchant:function(){
						var url = "cmTrunkCostBO.ajax?cmd=doQueryMerchant";
						var param = {};
						param._ALLEXPORT = 1;
						commonService.postUrl(url,param,function(data){
							if(data!=null && data!=undefined && data!=""){
									$scope.businessData=data;
//									trunk.query.businessOrgId=data.items[0].orgId;
				 	    	}
						});
				},
				selectZxLine:function(){
					var url = "cmTrunkCostBO.ajax?cmd=doQueryZxLine";
					var param = {};
					commonService.postUrl(url,param,function(data){
						if(data!=null && data!=undefined && data!=""){
								$scope.orgData=data;
								if(data.items.length == 1){
									trunk.query.orgId=data.items[0].orgId;
								}else{
									
								}
//								trunk.query.orgId=data.items[0].orgId;
			 	    	}
					});
				},
				cityAll : function(){
					 $scope.cityData = {};
					 $scope.items = [];
					 $scope.obj = {};
					 $scope.obj.id= "-1";
					 $scope.obj.name= "全部";
					 $scope.items.push($scope.obj);
					 $scope.query.city = "-1";
					 
					 $scope.cityData.items = $scope.items;
				 },
				closeUpdateDialog:function(){
					$("#update").css("display","none");
					$("#fade1_xz").css("display","none");
				}
	 };
	 trunk.init();
 }]);