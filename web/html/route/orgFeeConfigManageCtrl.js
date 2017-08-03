var orgFeeConfigManageApp = angular.module("orgFeeConfigManageApp", ['commonApp']);
orgFeeConfigManageApp.controller("orgFeeConfigManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var orgFeeConfigManage={
		init:function(){
			this.bindEvent();
			this.doQuery();
			//$scope.query = {};
			this.queryOrg();
		},
		bindEvent:function(){
			$scope.param = this.param;
			$scope.doQuery = this.doQuery;
		    $scope.clear = this.clear;
		    $scope.selectAll = this.selectAll;
		    $scope.selectOne = this.selectOne;
		    $scope.toAdd = this.toAdd;
		    $scope.query =this.query;
		    $scope.toDtl = this.toDtl;
		    $scope.selectAll = this.selectAll;
		    
		    
		},
		params:{
		},
		query:{
			page:1
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
			/*此处可以写业务限制规则*/
			var url = "orgFeeConfigManageBO.ajax?cmd=doQuery";
			 $scope.query.page=1;
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:$scope.query
						});
			},500);
		},
		/**清空查询条件*/
		clear:function(){
			$scope.query.orgId = "-1";
			$scope.query.feeId = "-1";
		},
		/**选择一行**/
		selectOne : function(indexId){
			if(document.getElementById("checkbox"+indexId).checked && document.getElementById("checkbox"+indexId) != undefined){
				document.getElementById("checkbox"+indexId).checked=false;
			}else{
				document.getElementById("checkbox"+indexId).checked=true;
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
		/**登记网点查询*/
		queryOrg:function(){
			var url = "staticDataBO.ajax?cmd=selectOrgByRole";
			commonService.postUrl(url,"",function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.orgInfo=data;
					$scope.orgInfo.unshift({orgId:"-1",orgName:'全部'});
					$scope.query.orgId = "-1";
	 	    	}
			});
			
			/**科目费用查询*/
			var url = "staticDataBO.ajax?cmd=queryAcFeeConfig";
			commonService.postUrl(url,"",function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.acFeeConfig=data;
					$scope.acFeeConfig.unshift({feeId:"-1",feeName:'全部'});
					$scope.query.feeId = "-1";
				}
			});
			
		},		
		toAdd:function(){
			commonService.openTab("228","网点费用新增","/route/orgFeeConfigAdd.html");
		},
		toDtl:function(){
			 var chk_value =[];//定义一个数组    
			 var chk_attr =[];//定义一个数组    
	         $('input[name="check-1"]:checked').each(function(){//遍历每一个名字为interest的复选框，其中选中的执行函数    
	        	 chk_value.push($(this).val());//将选中的值添加到数组chk_value中    
	         });
	         if(chk_value.length==0){
	        	commonService.alert("请选择数据");
					return;
	         } 
	         if(chk_value.length>1){
	        	 commonService.alert("只能选择一行数据");
			     return;
	         }
	         $('input[name="check-1"]:checked').each(function(){//遍历每一个名字为interest的复选框，其中选中的执行函数    
	        	 chk_attr.push($(this).attr("attr"));//将选中的值添加到数组chk_value中    
	         });
	         var indexData= chk_attr[0];
	        commonService.openTab(indexData+"228","网点费用详情","/route/orgFeeConfigDtl.html?orgId="+encodeURIComponent(chk_value[0]));
		},
	};
	orgFeeConfigManage.init();
}]);
