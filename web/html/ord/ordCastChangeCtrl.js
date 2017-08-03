function changeTabCallback(){
	var appElement = document.querySelector('[ng-controller=ordCastChangeCtrl]');
	var scope=angular.element(appElement).scope();
	scope.doQuery();
	scope.$apply();
}

var ordCastChangeApp = angular.module("ordCastChangeApp", ['commonApp']);
ordCastChangeApp.controller("ordCastChangeCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	var ord ={
		init:function(){
			this.userData();
			this.bindEvent();
			this.queryOrg();
			this.doQuery();
			this.queryQuestionOrg();
			var returnKeyEventDomEleIds = ["beginTime","endTime","id1","id2","id3","id4"];
			commonService.registerKeyEventForDoms(returnKeyEventDomEleIds, "keydown", "return", $scope.doQuery);
		},
		bindEvent:function(){
			$scope.clear = this.clear;
			$scope.selectOne = this.selectOne;
			$scope.doQuery = this.doQuery;
			$scope.query = this.query;
			$scope.selectOne = this.selectOne;
			$scope.selectAll = this.selectAll;
			$scope.toDeal = this.toDeal;
			$scope.toView = this.toView;
			$scope.toView2 = this.toView2;
			$scope.paramsExport = "{}";
			$scope.isTrue = false;
			$scope.commonExport = this.commonExport;
		},
		toView2:function(obj){
			window.open("/ord/ordBillingDetail.html?view=1&trackingNum="+obj.trackingNum+"&type=3&ver=${ver}");
		},
		userData:function(){
			$scope.currOrgId=userInfo.orgId;
			$scope.currOrgName=userInfo.orgName;
			ord.query.dutyOrgId = userInfo.orgId;
		},
		query:{
			page:1,
			rows:10,
    questionType:1,
		},
		/**登记网点查询*/
		queryOrg:function(){
			var url = "staticDataBO.ajax?cmd=selectOrg";
			commonService.postUrl(url,"isParent=1",function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.orgInfo=data;
					$scope.orgInfo.unshift({orgId:-1,orgName:'全部'});
					$scope.query.orgId = -1;
					$scope.query.dutyOrgId= -1;
	 	    	}
			});
			
		},
		
		/**责任网点查询*/
		queryQuestionOrg:function(){
			/*var url = "staticDataBO.ajax?cmd=selectOrgByRole";
			commonService.postUrl(url,"",function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.questionOrg=data;
					$scope.questionOrg.unshift({orgId:-1,orgName:'全部'});
					$scope.query.dutyOrgId= -1;
	 	    	}
			});*/
		},
		/**订单列表查询*/
		doQuery:function(){
			var url = "ordCastChangeBO.ajax?cmd=doQuery";
			$scope.query.endTime = document.getElementById("endTime").value;
			$scope.query.beginTime = document.getElementById("beginTime").value;
			$scope.query.inputEndTime = document.getElementById("inputEndTime").value;
			$scope.query.inputBeginTime = document.getElementById("inputBeginTime").value;
			$scope.query.page = 1;
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:$scope.query,
							callBack:"$scope.tableCallBack"
						});
			},500);
			$scope.tableCallBack = function(){
				//commonService.refreshPageContentHeight($scope.page.data.items.length, 643, 270);
				//$scope.paramsExport = JSON.stringify($scope.query);
			};
			
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
			var excelLables = "异动编号,运单号,登记网点,登记人,问题类型,登记时间,核销网点,减款金额,增加金额,问题描述";
			var excelKeys = "id,trackingNum*,orgIdName,opIdName,typeName,createDateString,dutyOrgIdName,rebatesExport@,addAmountExport@,notes";
			var params = $scope.paramsExport;
			var queryUrl = "ordCastChangeBO.ajax?cmd=doQuery";
			commonService.downloadExcelFile(queryUrl,eval("("+params+")"),excelLables,excelKeys);
			
			 $scope.isTrue = true;
			$("#exportId").html("导出中...");
			//导出倒计时
			$timeout(function() {
				 $scope.isTrue = false;
				$("#exportId").html("导出");
			},3600);
			
		},
		/**清空查询条件*/
		clear:function(){
			$scope.query.beginTime='';
			$scope.query.endTime='';
			document.getElementById("beginTime").value='';
			document.getElementById("endTime").value='';
			$scope.query.inputBeginTime="";
			$scope.query.inputEndTime="";
			$scope.query.trackingNum="";
			$scope.query.status="-1";
			$scope.query.dutyOrgId=-1;
			$scope.query.orgId=-1;
			$scope.query.consignorName="";
			$scope.query.consigneeName="";
			$scope.query.consigneeBill="";
		},
		/**选择一行**/
		selectOne : function(orderId){
			if(document.getElementById("checkbox"+orderId).checked && document.getElementById("checkbox"+orderId) != undefined){
				document.getElementById("checkbox"+orderId).checked=false;
			}else{
				document.getElementById("checkbox"+orderId).checked=true;
			}
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
		toDeal:function(){
			var id='';
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条信息!");
				return false;
			}
			if($("input[name='check-1']:checked").length>1){
				commonService.alert("只能选择一条信息!");
				return false;
			}
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					id=data.id;
				}
			});
			commonService.openTab(id+"111","问题处理","/ord/ordQuestionIn.html?edit=1&id="+id);
		},
		toView:function(variable){
			// 10是修改,其他是新增
 			if(variable==0){
				var arrays = $scope.page.getSelectData();
				if(arrays.length>0){
				var id = arrays[0].trackingNum;
					commonService.openTab("11122233","异动修改","ord/ordCostChangeTo.html?id="+id);
				}else{
					commonService.alert("请选择要修改的信息");
				}
			}else{
				commonService.openTab("11122234","异动新增","ord/ordCostChangeTo.html?id=");
			}
		}	
	};
	ord.init();
}]);