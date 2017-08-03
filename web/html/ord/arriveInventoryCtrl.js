var arriveApp = angular.module("arriveApp", ['commonApp']);
var paramData = "{}";
arriveApp.controller("arriveCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	
	var arrive = {
		init:function(){
			this.bindEvent();
			//this.queryOrg();
			this.userData();
			this.doQuery();
		},
		bindEvent:function(){
			$scope.query = this.query;
			$scope.doQuery = this.doQuery;
			$scope.clearModel = this.clearModel;
			//$scope.doQueryCount = this.doQueryCount;
			$scope.orderCount2 = this.orderCount2;
			$scope.orderCount = this.orderCount;
			$scope.printTime = new Date();
			$scope.printTable = this.printTable;
			$scope.doQueryDa = this.doQueryDa;
			$scope.isTrue = false;
			$scope.exportExcel = false;
			$scope.commonExport = this.commonExport;
			$scope.paramsExport = "{}";
		},
		printTable: function(){
			if($scope.isTrue){
				return false;
			}
			var param = eval("("+paramData+")")
			$scope.doQueryDa(param);
			var arrEndDate = param.arrEndDate;
			var arrBeginDate = param.arrBeginDate;
			var isE = false;
			var isB = false;
			if(arrBeginDate != undefined && arrBeginDate != null && arrBeginDate != '' ){
				isB = true;
			}
			if(arrEndDate != undefined && arrEndDate != null && arrEndDate != '' ){
				isE = true;
			}
			if(isE && isB){
				$scope.dateTime = "从"+arrBeginDate +"到"+arrEndDate
			}else if(isB){
				$scope.dateTime = "从"+ arrBeginDate +" 到现在"
			}else if(isE){
				$scope.dateTime = "截止"+arrEndDate;
			}else{
				$scope.dateTime = "";
			}
			
			var beginOrgId = $scope.query.beginOrgId;
			var endOrgId = $scope.query.endOrgId;
			var aass = $scope.orgInfoBegin;
			$scope.beginOrgName = "";
			$scope.endOrgName = "";
			for(var i=0;i<aass.length;i++){
				if(aass[i].orgId == beginOrgId){
					$scope.beginOrgName = aass[i].orgName;
					continue;
				}
			}
			var aass2 = $scope.orgInfo;
			for(var i=0;i<aass2.length;i++){
				if(aass2[i].orgId == endOrgId){
					$scope.endOrgName = aass2[i].orgName;
					continue;
				}
			}
			
		},
		orderCount2 : {
			freightCount :0,
			freightCollectCount :0,
			collectingMoneyCount :0,
			orderCount : 0,
			countW :0,
			countV : 0,
		},
		orderCount : {
			freightCount :0,
			freightCollectCount :0,
			collectingMoneyCount :0,
			orderCount : 0,
			countW :0,
			countV : 0,
		},
		query:{
			page : 1,
			rows : 10,
            type : 2, //1:发货  2：到货
            beginOrgId : parseInt(userInfo.orgId),
            endOrgId : -1
		},
		userData:function(){
			$scope.currOrgId = userInfo.orgId;
			$scope.currOrgName = userInfo.orgName;
		},
		/**目的站点*/
		/*queryOrg:function(){
			var url = "staticDataBO.ajax?cmd=selectOrg";
			commonService.postUrl(url,"",function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.orgInfo = data;
					$scope.orgInfo.unshift({orgId:-1,orgName:'全部'});
					$scope.orgInfoBegin = $scope.orgInfo;
					if(userInfo.orgId != null && userInfo.orgId != ''){
						$scope.query.beginOrgId = parseInt(userInfo.orgId);
					}
					
	 	    	}
			});
		},*/
		/**到货库存查询*/
		doQuery:function(){
			var param = $scope.query;
			param.arrEndDate = $("#endDate").val();
			param.arrBeginDate = $("#beginDate").val();
			param.page = 1;
			param.typeQuery = 1;
			var url = "orderInfoBO.ajax?cmd=doQueryDelOrArrOrd";
			$scope.tableCallBack=function(){
				setContentHegthDelay();
				//保存打印查询参数使用json字符串保存
				paramData = JSON.stringify(param);
				
				if($scope.page.rowColorCallback == undefined){
					$scope.page.rowColorCallback = function(rowData){
						var time = rowData.stockDuration;
						var day = time.substring(0,time.indexOf("天"));
						if(parseInt(day)===1){
							return "greenColor";
						}
						if(parseInt(day)>=2){
							return "redColor";
						}
						return "";
						
					};
				}
				
			}
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:param,
							callBack:"$scope.tableCallBack"
						});
			},500);
			//保存打印查询参数使用json字符串保存
			paramData = JSON.stringify(param);
			$scope.paramsExport = JSON.stringify(param);
			
		},
		/**到货库存统计查询*/
		/*doQueryCount:function(param){
			var url = "orderInfoBO.ajax?cmd=doQueryDeliveyOrArriveOrdCount";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					if(data.orderCount != 0 || data.orderCount != "0"){
						$scope.orderCount = data;
					} else{
						$scope.orderCount = $scope.orderCount2;
					}
					
	 	    	}else{
					$scope.orderCount = $scope.orderCount2;
				}
			});
		},*/
		/**到货库存打印查询*/
		doQueryDa:function(param){
			/*var url = "orderInfoBO.ajax?cmd=doQueryDeliveyOrArriveOrd";
			$scope.orderDataDa = {};
			var paramsData = {};
			paramsData = param;;
			paramsData.typeQuery = 2;
			$scope.isTrue = true;
			$("#dayin").html("打印中。");
			commonService.postUrl(url,paramsData,function(data){
				paramsData.typeQuery = 1;
				//成功执行
				if(data!=null && data!=undefined && data!=""){
						$scope.orderDataDa = data;
						$timeout(function(){
							$scope.printTime = new Date();
							printTableInfo("printDiv", "众邦物流_到货库存");
							$timeout(function(){
								$scope.isTrue = false;
								$("#dayin").html("打印");
							},3500);
							
						},500);
	 	    	}else{
	 	    		commonService.alert("打印失败");
	 	    		$scope.isTrue = false;
					$("#dayin").html("打印");
	 	    	} 
			},function(response, status, headers, config){
				commonService.alert("打印失败");
				$scope.isTrue = false;
				$("#dayin").html("打印");
			});
			//如果打印失败（未安装打印机）
			$timeout(function(){
				$scope.isTrue = false;
				$("#dayin").html("打印");
			},10000);
			*/
		},
		clearModel : function(){
			$scope.query.arrBeginDate = null;
			$scope.query.arrEndDate = null;
			$scope.query.beginOrgId = -1;
			$scope.query.endOrgId = -1;
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
			var excelLables = "运单号,入库时间,入库网点,在库时长,发货人,发货人电话,收货人省,收货人市,收货人区,收货人,联系电话,货品,总重量(kg),总体积(方),数量,交接方式,付款方式1,金额(元),付款方式2,金额(元),贷款(元),运费(元)";
			var excelKeys = "trackingNum,stockInTime,orgIdName,stockDuration,consignorLinkmanName,consignorBill"+
              ",destProvinceName,destCityName,destCountyName,consigneeLinkmanName,consigneeBill"+
              ",products,weight,volume,count,deliveryTypeName,paymentTypeName,cashMoneyString"+
              ",paymentType2Name,cashMoney2String,collectingMoneyString,freightString";
			var params = $scope.paramsExport;
			var queryUrl = "orderInfoBO.ajax?cmd=doQueryDelOrArrOrd";
			commonService.downloadExcelFile(queryUrl,eval("("+params+")"),excelLables,excelKeys);
			
			$scope.isTrue = true;
			$("#exportId").html("导出中。");
			//导出倒计时
			$timeout(function() {
				 $scope.isTrue = false;
				$("#exportId").html("导出");
			},3600);
			
		},
	};
	
	arrive.init();
}]);

//get URL加密
function signUrl(orgiUrl) {
	var paramArray = new Array();
	var name,value,paramStr; 
	if (orgiUrl != undefined) {
		 var url = orgiUrl.substring(orgiUrl.lastIndexOf("/")+1);
		 if ((idx = url.indexOf("&")) > 0) {
			 paramStr = url.substring(0, idx);
			 var params = url.substring(idx+1).split("&");
			 for (var i in params) {
				 if (params[i].split("=")[1] !== "null" && params[i].split("=")[1] !== "") {
					 paramArray.push(params[i]);
				 }
			 }
		 } else {
			 paramStr = url;
		 }
	}
    if (paramArray.length > 0)
    	paramStr += "&"+ paramArray.sort().join("&");
        paramStr += "&sign=" +md5(paramStr+getCookie("token"));
    return paramStr;
}