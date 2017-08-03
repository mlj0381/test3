var myApp = angular.module("GeneralApp", ['commonApp']);
myApp.controller("GeneralCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var myManage={
		init:function(){
			$scope.query = {};
			this.bindEvent();
			this.doQuery();
		},
		bindEvent:function(){
			$scope.param = this.param;
			$scope.doQuery = this.doQuery;
			$scope.clear = this.clear;
			  $scope.downloadExcelFile=this.downloadExcelFile;
		},
		downloadExcelFile:function(){
			commonService.downloadExcelFile("cashManageBO.ajax?cmd=doAccountTotalQuery",
					myManage.params,"公司名称,安装师傅账号,安装师傅,收货人,运单号,目的市,目的区域,品名,体积,金额,科目," +
							"签收时间,提现状态,申请时间,申请人,审批时间,审批人,打款人,核销码,核销金额,提现编号",
					"belongObjName,doTel,doObjName,receiveName,bussId,destCity,destCounty,products,volumes," +
					"totalMoney@,feeName,signTime,stateName,createDate,createName,auditDate,auditMan,verifyPerson,verifyId,verifyMoney,appId","师傅对账数据");
		},
		selectProvince:function(){
			var param = {"isAll":"Y"};
			var url = "staticDataBO.ajax?cmd=selectProvince";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
						$scope.provinceData=data;
						scheTaskManage.query.provinceId=data.items[0].id;
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
							scheTaskManage.query.cityId=data.items[0].id;
		 	    	}
				});
			}
		},
		selectDistrict:function(cityId){
			if(parseInt(cityId)>0){
				var param = {"isAll":"Y","cityId":cityId};
				var url = "staticDataBO.ajax?cmd=selectDistrict";
				commonService.postUrl(url,param,function(data){
					if(data!=null && data!=undefined && data!=""){
							$scope.districtData=data;
							scheTaskManage.query.countyId=data.items[0].id;
		 	    	}
				});
			}
		},
		params:{
		},
		/**列表查询*/
		doQuery:function(){
//			if($scope.query.doTel==""||$scope.query.doTel==undefined){
//				commonService.alert("安装师傅账号必填!");
//				return false;
//			}
			myManage.params.doObjName=$scope.query.doObjName;
			myManage.params.doTel=$scope.query.doTel;
			myManage.params.state=$scope.query.state;
			myManage.params.signTimeBegin=$scope.query.signTimeBegin;
			myManage.params.signTimeEnd=$scope.query.signTimeEnd;
			myManage.params.feeId=$scope.query.feeId;
			myManage.doQuerySf();
		},
		/**列表查询*/
		doQuerySf:function(){
			var loginAcct = $scope.query.doTel;
			var url = "cashManageBO.ajax?cmd=doQuerySf";
			myManage.params.isQuery=1;
			commonService.postUrl(url,myManage.params,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.sf = data;
					var url = "cashManageBO.ajax?cmd=doAccountTotalQuery";
					$timeout(function(){
						$scope.page.load({
									url:url,
									params:myManage.params,
									callBack:'setContentHegthDelay'
								});
					},500);
				}
			});
		},
		/**清空查询条件*/
		clear:function(){
			$scope.query = {};
			$scope.query.state="-1";
			$scope.query.feeId="-1";
		}
	};
	myManage.init();
}]);
