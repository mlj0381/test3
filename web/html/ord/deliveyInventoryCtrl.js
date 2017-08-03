var deliveyApp = angular.module("deliveyApp", ['commonApp']);
var paramData = "{}";
deliveyApp.controller("deliveyCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var delivey = {
		init:function(){
			this.userData();
			//this.queryOrg();
			this.bindEvent();
			//this.queryOrgType();
			this.doQuery();
			
		},
		
		bindEvent:function(){
			$scope.query = this.query;
			$scope.clearModel =  this.clearModel;
			//$scope.doQueryCount = this.doQueryCount;
			$scope.doQuery = this.doQuery;
			$scope.orderCount2 = this.orderCount2;
			$scope.orderCount = this.orderCount;
			$scope.doQueryDa = this.doQueryDa;
			$scope.printTime = new Date();
			$scope.printTable = this.printTable;
			$scope.isTrue = false;
			$scope.commonExport = this.commonExport;
			$scope.paramsExport = "{}";
			
		},
		userData:function(){
			$scope.currOrgId = userInfo.orgId;
			$scope.currOrgName = userInfo.orgName;
		},
		query:{
			page : 1,
			rows : 10,
            beginOrgId : parseInt(userInfo.orgId),
            endOrgId : -1
		},
		orderCount : {
			freightCount :0,
			freightCollectCount :0,
			collectingMoneyCount :0,
			orderCount : 0,
			countW :0,
			countV : 0,
		},
		orderCount2 : {
			freightCount :0,
			freightCollectCount :0,
			collectingMoneyCount :0,
			orderCount : 0,
			countW :0,
			countV : 0,
		},
		printTable: function(){
			if($scope.isTrue){
				return false;
			}
			var param = eval("("+paramData+")")
			$scope.doQueryDa(param);
			var endDate = param.endDate;
			var beginDate = param.beginDate;
			var isE = false;
			var isB = false;
			if(beginDate !=undefined &&  beginDate != null && beginDate != '' ){
				isB = true;
			}
			if(endDate !=undefined && endDate != null && endDate != '' ){
				isE = true;
			}
			
			if(isE && isB){
				$scope.dateTime = "从"+beginDate +"到"+endDate
			}else if(isB){
				$scope.dateTime = "从"+ beginDate +" 到现在"
			}else if(isE){
				$scope.dateTime = "截止"+endDate;
			}else{
				$scope.dateTime = "";
			}
			
			var beginOrgId = $scope.query.beginOrgId;
			var endOrgId = $scope.query.endOrgId;
			var aass = $scope.orgInfo;
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
		/**到货库存打印查询*/
		doQueryDa:function(param){
			/*var url = "orderInfoBO.ajax?cmd=doQueryDeliveyOrArriveOrd";
			$scope.orderDataDa = {};
			var paramsData = {};
			paramsData = param;
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
							printTableInfo("printDiv", "众邦物流_发货库存");
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
				$scope.query.beginDate = null;
				$scope.query.endDate = null;
				$scope.query.endOrgId = -1;
				if(!$scope.orgTypeTrue){
					$scope.query.beginOrgId = -1;
				}
			
		},
		/**收货站点*/
		/*queryOrgType:function(){
			var url = "staticDataBO.ajax?cmd=selectOrgInfo";
			var param = {};
			param.orgId = userInfo.orgId;
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.orgType= data.orgType;
					if($scope.orgType == 1  ||$scope.orgType == "1"){
						$scope.orgTypeTrue = true;
					}else{
						$scope.orgTypeTrue = false;
					}
	 	    	}
			});
		},*/
		/**网点查询*/
		/*queryOrg:function(){
			var url = "staticDataBO.ajax?cmd=selectOrg";
			commonService.postUrl(url,"",function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.orgInfo = data;
					$scope.orgInfo.unshift({orgId:-1,orgName:'全部'});
					$scope.query.orgId = -1;
	 	    	}
			});
		},*/
		
		
		/**发货库存查询*/
		doQuery:function(){
			var param = $scope.query;
			param.endDate = $("#endDate").val();
			param.beginDate = $("#beginDate").val();
			param.page = 1;
			param.typeQuery = 0;
			var url = "orderInfoBO.ajax?cmd=doQueryDelOrArrOrd";
			$scope.tableCallBack=function(){
				setContentHegthDelay();
				//保存打印查询参数使用json字符串保存
				paramData = JSON.stringify(param);
				$scope.paramsExport = JSON.stringify(param);
				
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
			};
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:param,
							callBack:"$scope.tableCallBack"
						});
			},500);
			
			//保存打印查询参数使用json字符串保存
			paramData = JSON.stringify(param);
		},
		
		/**发货库存统计查询*//*
		doQueryCount:function(param){
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
	delivey.init();
}]);