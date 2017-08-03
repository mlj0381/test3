function changeTabCallback(){
	var appElement = document.querySelector('[ng-controller=departMainDirectCtrl]');
	var scope=angular.element(appElement).scope();
	scope.doQuery();
	scope.$apply();
}
var departManageApp = angular.module("departMainDirectApp", ['commonApp']);
departManageApp.controller("departMainDirectCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	$scope.sfQueryList=[];
	$scope.currOrgData = [];
	 $scope.query={};
	 $scope.vehicleStateList=[];
	 $scope.params={};
	var departManage={
		init:function(){
			this.bindEvent();
			this.querySfInfo();
			commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType",{"codeType":"VEHICLE_STATE"},function(data){
				$scope.vehicleStateList=data.items;
			});
			this.desOrgData();
		},
		bindEvent:function(){
			$scope.doQuery=this.doQuery;
		    $scope.deleteDepart=this.deleteDepart;
		    $scope.print=this.print;
		    $scope.toView=this.toView;
		    $scope.clear=this.clear;
		    $scope.selectAll=this.selectAll;
		    $scope.doSave=this.doSave;
		    $scope.confirmMatchVehi=this.confirmMatchVehi;
		    $scope.doModify=this.doModify;
		    $scope.selectOne=this.selectOne;
		    $scope.cancelMatchVehi=this.cancelMatchVehi;
		    $scope.querySfInfo=this.querySfInfo;
		    $scope.commonExport=this.commonExport;
		    $scope.isTrue = false;
		    $scope.paramsExport = "{}";
		},
			//导出方法
		commonExport : function(){
				if($scope.isTrue){
					return false;
				}
				/**
				 * queryUrl  格式如：commonExportBO.ajax?cmd=downloadExcelFile
				 * params   请求的参数对象:{"date":"2016-07-12"}
				 * excelLables  excel的列名: 批次号,时间
				 * excelKeys    excel的字段名称:batchNum,date 
				 */
				var excelLables = "发车批次,配载时间,发车时间,车辆状态,总票数,总重量,总体积,发车网点,目的网点,车牌号,司机姓名,司机手机";

				var excelKeys = "batchNum,loadTimeStr*,departTimeStr,stateName,orderNum@,weight@,volume@,sourceOrgIdName,descOrgIdName,plateNumber,driverName,driverBill";

				var params = $scope.paramsExport;
				var queryUrl = "orderInfoBO.ajax?cmd=queryMatchInfo";
				commonService.downloadExcelFile(queryUrl,eval("("+params+")"),excelLables,excelKeys);
				 $scope.isTrue = true;
				$("#exportId").html("数据加载中...");
				//导出倒计时
				$timeout(function() {
					 $scope.isTrue = false;
					$("#exportId").html("导出");
				},3000);
				
			},
			/**
			 * 查询发车网点
			 */
			desOrgData:function(orgType){
				$scope.currOrgId=userInfo.orgId;
				$scope.currOrgName=userInfo.orgName;
				var obj = new Object();
				obj.beginOrgId = $scope.currOrgId;
				obj.beginOrgName = $scope.currOrgName;
				$scope.currOrgData.push(obj);
				$scope.query.startOrgId = $scope.currOrgId;
				$scope.doQuery();
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
		/**列表查询*/
		doQuery:function(){
			$scope.query.page=1;
			$scope.query.flag=1;
			$scope.query.flagSts=2;
			$scope.page.load({
				url:"orderInfoBO.ajax?cmd=queryMatchInfo",
				params:$scope.query,
				callBack:"setContentHegthDelay"
			});
		},
		/**清空查询条件*/
		clear:function(){
			document.getElementById("beginTime").value='';
			document.getElementById("endTime").value='';
			$scope.query.beginTime="";
			$scope.query.endTime="";
			$scope.query.batchNum="";
			$scope.query.vehicleState="-1";
			$scope.query.descOrgId="-1";
		},
		/**选择一行**/
		selectOne : function(batchNum){
			if(document.getElementById("checkbox"+batchNum).checked && document.getElementById("checkbox"+batchNum) != undefined){
				document.getElementById("checkbox"+batchNum).checked=false;
			}else{
				document.getElementById("checkbox"+batchNum).checked=true;
			}
		},
		/**查看**/
		toView:function(){
			var batchNum='';
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条配载信息!");
				return false;
			}
			if($("input[name='check-1']:checked").length>1){
				commonService.alert("只能选择一条配载信息!");
				return false;
			}
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					batchNum=data.batchNum;
				}
			});
			commonService.openTab(batchNum,"配载详情","/ord/depearInfo.html?batchNum="+batchNum);
				},
		/***保存**/
		doSave:function(){
			commonService.openTab("2222222","新增配载信息","/ord/addDepartDirect.html");
		},
		/***修改**/
		doModify:function(){
			var batchNum='';
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条配载信息!");
				return false;
			}
			if($("input[name='check-1']:checked").length>1){
				commonService.alert("只能选择一条配载信息!");
				return false;
			}
			var flag=false;
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					if(data.state!=1){
						flag=true;
						commonService.alert("批次["+data.batchNum+"]状态为"+data.stateName+",不可以操作[修改]!");
						return false;
					}
					batchNum=data.batchNum;
					setContentHeigth();
				}
			});
			if(flag){
				return false;
			}
			commonService.openTab(batchNum,"修改配载信息","/ord/addDepartDirect.html?batchNum="+batchNum);
		},
		/**打印*/
		print:function(){
		},
		/**删除*/
		deleteDepart:function(){
			var batchNum='';
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条配载信息!");
				return false;
			}
			var flag=false;
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					if(data.state!=1){
						flag=true;
						commonService.alert("批次["+data.batchNum+"]状态为"+data.stateName+",不可以操作!");
						return false;
					}
					if(batchNum==''){
						batchNum=data.batchNum;
					}else{
						batchNum=batchNum+","+data.batchNum;
					}
				}
			});
			if(flag){
				return false;
			}
			var param = {"batchNum":batchNum};
			var url = "orderInfoBO.ajax?cmd=deleteDepartInfo";
			commonService.confirm("是否删除配载?",function(){
				commonService.postUrl(url,param,function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
							commonService.alert("删除成功!");
							$scope.doQuery();
		 	    	}
				});
			});
		},
		querySfInfo:function(){
			//获取安装师傅
			commonService.postUrl("cmSfUserInfoBO.ajax?cmd=queryUserInfoList","",function(data){
				$scope.sfQueryList = data;
			});
		},
		/**发车确认*/
		confirmMatchVehi:function(){
			var batchNum='';
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条配载信息!");
				return false;
			}
			if($("input[name='check-1']:checked").length>1){
				commonService.alert("只能选择一条配载信息!");
				return false;
			}
			var flag=false;
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					if(data.state!=1){
						flag=true;
						commonService.alert("批次["+data.batchNum+"]状态为"+data.stateName+",不可以操作[发车确认]!");
						return false;
					}
					batchNum=data.batchNum;
				}
			});
			if(flag){
				return false;
			}
			var param = {"batchNum":batchNum};
			var url = "orderInfoBO.ajax?cmd=confirmMatchVehi";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
						commonService.alert("操作成功!");
						 $scope.query.currOrgId=parseInt(userInfo.orgId);
							$scope.query.vehicleState="-1";
						$scope.doQuery();
	 	    	}
			});
		},
		cancelMatchVehi:function(){
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条配载信息!");
				return false;
			}
			if($("input[name='check-1']:checked").length>1){
				commonService.alert("只能选择一条配载信息!");
				return false;
			}
			var flag=false;
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					if(data.state!=2){
						flag=true;
						commonService.alert("批次["+data.batchNum+"]状态为"+data.stateName+",不可以操作[取消发车]!");
						return false;
					}
					batchNum=data.batchNum;
				}
			});
			if(flag){
				return false;
			}
			var param = {"batchNum":batchNum};
			var url = "orderInfoBO.ajax?cmd=cancelMatchVehi";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
						commonService.alert("操作成功!");
						$scope.query.currOrgId=parseInt(userInfo.orgId);
						$scope.query.vehicleState="-1";
						$scope.doQuery();
	 	    	}
			});
		}
	};
	 $scope.$watch('$viewContentLoaded', function() {
		 departManage.init();
	 });
}]);
