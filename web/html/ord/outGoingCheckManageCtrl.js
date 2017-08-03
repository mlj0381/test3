function changeTabCallback(){
	var appElement = document.querySelector('[ng-controller=AcProveManageCtrl]');
	var scope=angular.element(appElement).scope();
	scope.doQuery();
	scope.$apply();
}

var proveManageApp = angular.module("AcProveManageApp", ['commonApp']);
proveManageApp.controller("AcProveManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var proveManage={
		init:function(){
			$scope.query = {};
			this.bindEvent();
			this.doQuery();
			$scope.isTrue = false;
			var returnKeyEventDomEleIds = ["beginTime","endTime","id1","id2","id3"];
			commonService.registerKeyEventForDoms(returnKeyEventDomEleIds, "keydown", "return", $scope.doQuery);
		},
		bindEvent:function(){
			$scope.param = this.param;
			$scope.doQuery = this.doQuery;
		    $scope.deal = this.deal;
		    $scope.undeal = this.undeal;
		    $scope.clear = this.clear;
		    $scope.selectAll = this.selectAll;
		    $scope.selectOne = this.selectOne;
		    $scope.upNum = this.upNum;
		    $scope.toPostTrue = this.toPostTrue;
		    $scope.commonExport = this.commonExport;
		    $scope.toView=this.toView;
		    $scope.toAllAudit = this.toAllAudit;
		    $scope.mapCount = this.mapCount;
		},
		mapCount : {
			count : 0,
			receiptCount : 0,
			weight : 0,
			volume : 0,
			checkFee : 0,
			freightCollect : 0,
			outgoingFee : 0
		},
		toAllAudit : function(){
			 commonService.openTab(13,"中转批量核销","/ac/acGoingBatchAudit.html?type="+13);
		},
		toView:function(trackingNum){
			window.open("/ord/ordBillingDetail.html?view=1&trackingNum="+trackingNum+"&type=3&ver=${ver}");
		},
		params:{
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
		
		upNum:function(valueName){
			var value = eval("$scope."+valueName).replace(/[^\d]/g, '');
			eval("$scope."+valueName+"=value");
		},
		/**列表查询*/
		doQuery:function(){
			$timeout(function(){
					proveManage.params.inoutSts= $scope.query.inoutSts;
					proveManage.params.checkSts= $scope.query.checkSts;
					proveManage.params.trackingNum=$scope.query.trackingNum;
					$scope.query.beginTime = document.getElementById("beginTime").value;
					$scope.query.endTime = document.getElementById("endTime").value;
					$scope.query.page=1;
					var url = "orderInfoBO.ajax?cmd=queryOutGoingCheck";
					$scope.tableCallBack=function(data){
						setContentHegthDelay();
						//数据统计展示
						var dataList = data.items;
						var arrays = new Array();
						var count = 0; //总件数
						var receiptCount = 0; //总回单票数
						var weight = 0; //总重量
						var volume = 0; //总体积
						var checkFee = 0; //核销金额
						var freightCollect = 0; //运费
						var outgoingFee = 0; //中转费
						var collectingMoney = 0;//代收货款
						for(var i = 0;i < dataList.length; i++){
							arrays.push(dataList[i]);
							count = count + dataList[i].count;
							receiptCount = receiptCount + dataList[i].receiptCount;
							weight = weight + dataList[i].weight;
							volume = volume + dataList[i].volume;
							checkFee = checkFee + dataList[i].checkFee;
							freightCollect = freightCollect + dataList[i].freightCollect;
							outgoingFee = outgoingFee + dataList[i].outgoingFee;
							collectingMoney = collectingMoney + dataList[i].collectingMoney;
						}
						$scope.mapCount.count = count;
						$scope.mapCount.receiptCount = receiptCount;
						$scope.mapCount.weight = weight;
						$scope.mapCount.volume = volume;
						$scope.mapCount.checkFee = checkFee;
						$scope.mapCount.freightCollect = freightCollect;
						$scope.mapCount.outgoingFee = outgoingFee;
						$scope.mapCount.collectingMoney = collectingMoney;
					};
					$timeout(function(){
						$scope.page.load({url:url, params:$scope.query, callBack:"$scope.tableCallBack" });
					},500);
			},500);
		},
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
			var excelLables = "运单号,当前网点,核销状态,核销金额,收支类型,到付款,代收货款,中转费,核销时间,承运商,中转时间";
			var excelKeys = "trackingNum,currentOrgIdName,checkStsName,checkFee,inoutStsName,freightCollect,collectingMoney,outgoingFee,checkDate,carrierCompanyName,createDate";
			var params = $scope.paramsExport;
			var queryUrl = "orderInfoBO.ajax?cmd=queryOutGoingCheck";
			commonService.downloadExcelFile(queryUrl,eval("("+params+")"),excelLables,excelKeys);
			 $scope.isTrue = true;
			$("#exportId").html("导出中。");
			//导出倒计时
			$timeout(function() {
				 $scope.isTrue = false;
				$("#exportId").html("导出");
			},3600);
			
		},
		/**清空查询条件*/
		clear:function(){
			document.getElementById("beginTime").value='';
			document.getElementById("endTime").value='';
			$scope.query.trackingNum='';
			$scope.query.carrierCompanyName='';
			$scope.query.inoutSts="-1";// 收支类型
			$scope.query.checkSts="-1";// 核销类型
		},
		/**选择一行**/
		selectOne : function(batchNum){
			if(document.getElementById("checkbox"+batchNum).checked && document.getElementById("checkbox"+batchNum) != undefined){
				document.getElementById("checkbox"+batchNum).checked=false;
			}else{
				document.getElementById("checkbox"+batchNum).checked=true;
			}
		},
		/**处理*/
		deal:function(){
			$scope.batchNum=new Array();
			var isCheckStsOk = false;
			var isPayStsOk = false;
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条核销数据!");
				return false;
			}
			
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					if(data.checkSts==1){
						isCheckStsOk = true;
						return false;
					}
					$scope.batchNum.push(data.orderId);
				}
			});
			if(isCheckStsOk){
				commonService.alert("核销状态为'已核销’的数据,不可以操作!");
				return false;
			}
			$scope.toPostTrue();
		},
		//核销请求
		toPostTrue : function(){
			var param = {"orderIds": $scope.batchNum.join(","), "type": 1};
			var url = "orderInfoBO.ajax?cmd=doSaveOutGoingCheck";
			commonService.postUrl(url,param,function(data){
				if (data.resultCode == '1') {
					commonService.alert("核销成功!");	
					$scope.doQuery();
				} else {
					commonService.alert(data.resultMessage);
				}
			});
		},
		/**处理*/
		undeal:function(){
			$scope.batchNum=new Array();
			var isCheckStsOk = false;
			var isPayStsOk = false;
			var isSysDo = false;
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条反核销数据!");
				return false;
			}
			
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					if(data.checkSts==2){
						isCheckStsOk = true;
						return false;
					}
					$scope.batchNum.push(data.orderId);
				}
			});
			if(isCheckStsOk){
				commonService.alert("核销状态为'未核销’的数据,不可以操作!");
				return false;
			}
			var param = {"orderIds":$scope.batchNum.join(","), type:2};
			var url = "orderInfoBO.ajax?cmd=doSaveOutGoingCheck";
			commonService.postUrl(url,param,function(data){
				if (data.resultCode == '1') {
					commonService.alert("反核销成功!");
					$scope.doQuery();
				} else {
					commonService.alert(data.resultMessage);
				}
			});
		}
	};
	proveManage.init();
}]);
