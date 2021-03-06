var cashTotalApp = angular.module("cashTotalApp", ['commonApp']);
cashTotalApp.controller("cashTotalCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	Date.prototype.Format = function(fmt) { // author: meizz
		var o = {
			"M+" : this.getMonth() + 1, // 月份
			"d+" : this.getDate(), // 日
			"h+" : this.getHours(), // 小时
			"m+" : this.getMinutes(), // 分
			"s+" : this.getSeconds(), // 秒
			"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
			"S" : this.getMilliseconds()
		// 毫秒
		};
		if (/(y+)/.test(fmt))
			fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		for ( var k in o)
			if (new RegExp("(" + k + ")").test(fmt))
				fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		return fmt;
	}
	var billIngManage={
		init:function(){
			$scope.isShow = false;
			this.bindEvent();
			this.initOrg();
			this.getBusinessType();
			this.doQuery();
			this.showLatitudeInfo();
		},
		bindEvent:function(){
			$scope.param=this.param;
			$scope.query=this.query;
			$scope.doQuery=this.doQuery;
			$scope.loadControl={};
			$scope.showView=this.showView;
			$scope.changeDate=this.changeDate;
			$scope.closeView=this.closeView;
			$scope.changeSelectedLatitudeId=this.changeSelectedLatitudeId;
		    $scope.isTrue = false;
		    $scope.selectedLatitudeType=false;
		    $scope.commonExport = this.commonExport;
		    $scope.paramsExport = "{}";
		    $scope.paramsTotalExport = "{}";
		    $scope.commonTotalExport = this.commonTotalExport;
		},
		param:{},
		query:{},
		doQuery:function(){
			$timeout(function(){
				if($scope.orgSelect!=null && $scope.orgSelect!=undefined && $scope.orgSelect!=""){
				    $scope.param.orgId=$scope.orgSelect;
				}
				if($scope.param.selectDate==1){
					var selectedLatitudeTypeDate2 = document.getElementById("selectedLatitudeTypeDate2").value;
					if(selectedLatitudeTypeDate2 == undefined || "" == selectedLatitudeTypeDate2){
						commonService.alert('请选择需要统计的[月份]');
						document.getElementById("selectedLatitudeTypeDate2").focus();
						return false;
					}
					$scope.param.checkDate=selectedLatitudeTypeDate2;
				}else if($scope.param.selectDate==2 || $scope.param.selectDate==3){
					    var selectedLatitudeTypeDate4 = document.getElementById("selectedLatitudeTypeDate4").value;
					    var selectedLatitudeTypeDate5 = document.getElementById("selectedLatitudeTypeDate5").value;
						if(selectedLatitudeTypeDate4 == undefined || "" == selectedLatitudeTypeDate4){
							commonService.alert('请选择需要统计的[开始日期]');
							document.getElementById("selectedLatitudeTypeDate4").focus();
							return false;
						}
						if(selectedLatitudeTypeDate5 == undefined || "" == selectedLatitudeTypeDate5){
							commonService.alert('请选择需要统计的[结束日期]');
							document.getElementById("selectedLatitudeTypeDate5").focus();
							return false;
						}
						$scope.param.checkDate=selectedLatitudeTypeDate4;
						$scope.param.endDate=selectedLatitudeTypeDate5;
				}
				var url="statisticBO.ajax?cmd=doQueryOrgCash";
				commonService.postUrl(url,$scope.param,function(data){
					$scope.IncomeList =data.list;
					$scope.paramsTotalExport = JSON.stringify($scope.param);
					$scope.showData=true;
					commonService.refreshPageContentHeight(data.list.length,550, 270);
				});
			},1000);
		},
		//改变时间格式
		changeSelectedLatitudeId:function(){// 纬度选择
			if($scope.param.selectDate==2 || $scope.param.selectDate==3){
				$scope.selectedLatitudeType=true;
				var currentDate = new Date();
				var beforeDate = new Date();
				beforeDate.setTime(currentDate.getTime() - 3600 * 24 * 30 * 1000);
				document.getElementById("selectedLatitudeTypeDate4").value = beforeDate.Format('yyyy年MM月dd日');
				document.getElementById("selectedLatitudeTypeDate5").value = currentDate.Format('yyyy年MM月dd日');
				if($scope.param.selectDate==3){
					$scope.param.checkSts="1";
					$scope.isAble=true;
				}else{
					$scope.isAble=false;
				}
			}else{
				$scope.selectedLatitudeType=false;
				$scope.isAble=false;
			}
		},
		initOrg:function(){
			var url = "statisticBO.ajax?cmd=getCurrentUserPriOrgInfo";
			commonService.postUrl(url,"",function(data){
				if (data.orgs != undefined) {
					$scope.orgInfo = data.orgs;
					if(data.orgs.length > 0) {
						$scope.orgSelect = data.orgs[0].orgId;
						$scope.orgName = data.orgs[0].orgName;
					}
				}
				$scope.param.selectDate='1';
				var currentDate = new Date();
				document.getElementById("selectedLatitudeTypeDate2").value = currentDate.Format('yyyy年MM月');
			});
		},
		changeDate:function(orgName){
			$scope.orgName=orgName;
		},
		showLatitudeInfo:function(){
			var url="statisticBO.ajax?cmd=loadStatisticChartLatitude";
			commonService.postUrl(url,"",function(data){
				if(data.resultCode == '1'){
					$scope.loadControl.associateLatitudeInfo = data.associateLatitudeInfo;
				}
				if(data.resultCode == '2'){
					commonService.alert("登录会话超时");
					window.location.href="/index.html";
				}
				if(data.resultCode == '0'){
				}
			});
		},
		//导出方法
		commonExport : function(){
			if($scope.isTrue){
				return false;
			}
			if($scope.query.feeType!=24){
				var excelLables = "运单号,收支类型,金额,开单日期,核销状态,现金状态,发货方,发货人,发货手机,品名,件数,重量,体积";
				var excelKeys = "trackingNum*,inoutStsName,incomeAmount@,createDateString,checkStsName,cashStsName,consignorName,consignorLinkmanName,consignorBill," +
						"goodsName,count@,weightString@,volumeString@";
			}else{
				var excelLables = "批次号,收支类型,金额,发车日期,核销状态,现金状态,发车网点,到达网点,车牌号,司机名称,司机手机";
				var excelKeys = "batchNum*,inoutStsName,incomeAmount@,createDateString,checkStsName,cashStsName,sourceOrgName,descOrgName,plateNumber," +
						"driverName,driverBill";
			}
			var params = $scope.paramsExport;
			var queryUrl = "statisticBO.ajax?cmd=queryDayTotalCash";
			commonService.downloadExcelFile(queryUrl,eval("("+params+")"),excelLables,excelKeys);
			$scope.isTrue = true;
			$("#exportId").html("导出中。");
			//导出倒计时
			$timeout(function() {
				 $scope.isTrue = false;
				$("#exportId").html("导出");
			},3600);
			
		},
		//导出方法
		commonTotalExport : function(){
			if($scope.isTrue){
				return false;
			}
			var excelLables = "方向,来源,项目,本期发生";
			var excelKeys = "typeName*,comName,feeName,incomeAmount";
			var params = $scope.paramsTotalExport;
			var queryUrl = "statisticBO.ajax?cmd=doQueryOrgCash";
			commonService.downloadExcelFile(queryUrl,eval("("+params+")"),excelLables,excelKeys,'list');
			$scope.isTrue = true;
			$("#selectId").html("导出中。");
			//导出倒计时
			$timeout(function() {
				 $scope.isTrue = false;
				$("#selectId").html("导出");
			},3600);
			
		},
		showView:function(feeType,feeName,inoutSts){
			$scope.titleMessage=feeName+"收支明细";
			var url="statisticBO.ajax?cmd=queryDayTotalCash";
			if($scope.orgSelect!=null && $scope.orgSelect!=undefined && $scope.orgSelect!=""){
			    $scope.query.orgId=$scope.orgSelect;
			}
			if($scope.param.selectDate==1){
				var selectedLatitudeTypeDate2 = document.getElementById("selectedLatitudeTypeDate2").value;
				if(selectedLatitudeTypeDate2 == undefined || "" == selectedLatitudeTypeDate2){
					commonService.alert('请选择需要统计的[月份]');
					document.getElementById("selectedLatitudeTypeDate2").focus();
					return false;
				}
				$scope.query.checkDate=selectedLatitudeTypeDate2;
			}else if($scope.param.selectDate==2 || $scope.param.selectDate==3){
				    var selectedLatitudeTypeDate4 = document.getElementById("selectedLatitudeTypeDate4").value;
				    var selectedLatitudeTypeDate5 = document.getElementById("selectedLatitudeTypeDate5").value;
					if(selectedLatitudeTypeDate4 == undefined || "" == selectedLatitudeTypeDate4){
						commonService.alert('请选择需要统计的[开始日期]');
						document.getElementById("selectedLatitudeTypeDate4").focus();
						return false;
					}
					if(selectedLatitudeTypeDate5 == undefined || "" == selectedLatitudeTypeDate5){
						commonService.alert('请选择需要统计的[结束日期]');
						document.getElementById("selectedLatitudeTypeDate5").focus();
						return false;
					}
					$scope.query.checkDate=selectedLatitudeTypeDate4;
					$scope.query.endDate=selectedLatitudeTypeDate5;
			}
			$scope.query.selectDate=$scope.param.selectDate;
			$scope.query.checkSts=$scope.param.checkSts;
			$scope.query.feeType=feeType;
			$scope.query.inoutSts=inoutSts;
			$scope.query.page = 1;
		    if(feeType!=95 && feeType!=96 && feeType!=97){
				$timeout(function(){
					$scope.page.load({
								url:url,
								params:$scope.query,
								callBack:"$scope.tableCallBack"
							});
				},1000);
				 $scope.tableCallBack=function(){
						commonService.refreshPageContentHeight($scope.page.data.items.length,550,270);
						$scope.isShow=true;
					    $scope.showData=false;
						$scope.paramsExport = JSON.stringify($scope.query);
					};
		    }
			
		},
		/**核销状态**/
		getBusinessType:function(){
       	 	var param = "codeType=AC_CASH_PROVE@CHECK_STS";
			var url = "orderInfoBO.ajax?cmd=queryState";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!="" && data!=undefined){
					$scope.businessData=data;
					$scope.businessData.items.unshift({codeValue:-1,codeName:'全部'});
					$scope.param.checkSts="1";
				};
			}); 
        },
		closeView:function(){
			$scope.isShow=false;
		    $scope.showData=true;
		    commonService.refreshPageContentHeight(null,750, 270);
		}
	};
	
	
	billIngManage.init();
}]);
