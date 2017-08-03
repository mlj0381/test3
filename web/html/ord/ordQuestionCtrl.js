var ordQuestionApp = angular.module("ordQuestionApp", ['commonApp']);
ordQuestionApp.controller("ordQuestionCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	var ord ={
		init:function(){
			this.userData();
			this.bindEvent();
			this.queryOrg();
			this.doQuery();
			this.queryQuestionOrg();
			
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
			commonService.postUrl(url,"",function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.orgInfo=data;
					$scope.orgInfo.unshift({orgId:-1,orgName:'全部'});
					$scope.query.orgId = -1;
	 	    	}
			});
			
		},
		
		/**责任网点查询*/
		queryQuestionOrg:function(){
			var url = "staticDataBO.ajax?cmd=selectOrgByRole";
			commonService.postUrl(url,"",function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.questionOrg=data;
					$scope.questionOrg.unshift({orgId:-1,orgName:'全部'});
					$scope.query.dutyOrgId= -1;
	 	    	}
			});
		},
		/**订单列表查询*/
		doQuery:function(){
			var url = "ordQuestionBO.ajax?cmd=doQuery";
			ord.query.page = 1;
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:ord.query,
							callBack:"$scope.tableCallBack"
						});
			},500);
			
			$scope.tableCallBack = function(){
				if ($scope.page.data.items == undefined || $scope.page.data.items.length == 0) {// 没有数据，指定一个默认高度
					setContentHeightWithSpecialHeight(643);
				} else {// 有数据，则根据数据行计算高度
					var height = (643 - 270) + $scope.page.data.items.length * 48 ;
					height = height <= 643 ? 643 : height;
					setContentHeightWithSpecialHeight(height);
				}
			}
			
		},
		/**清空查询条件*/
		clear:function(){
			$scope.query.beginTime='';
			$scope.query.endTime='';
			document.getElementById("beginTime").value='';
			document.getElementById("endTime").value='';
			$scope.query.trackingNum="";
			$scope.query.status="-1";
			$scope.query.orgId=-1;
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
		toView:function(){
			var id='';
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条运单信息!");
				return false;
			}
			if($("input[name='check-1']:checked").length>1){
				commonService.alert("只能选择一条运单信息!");
				return false;
			}
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					id=data.id;
				}
			});
			commonService.openTab(id,"问题详情","/ord/ordQuestionIn.html?view=1&id="+id);
		}
	};
	ord.init();
}]);