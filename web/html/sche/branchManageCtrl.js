var branchManageApp = angular.module("branchManageApp", ['commonApp']);
branchManageApp.controller("branchManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var branchManage={
		init:function(){
			this.bindEvent();
			this.doQuery();
			this.selectProvince();
			$scope.query = {};
		},
		bindEvent:function(){
			$scope.param = this.param;
			$scope.query = this.query;
			$scope.doQuery = this.doQuery;
		    $scope.clear = this.clear;
		    $scope.selectAll = this.selectAll;
		    $scope.selectOne = this.selectOne;
		    $scope.doSave = this.doSave;
		    $scope.toView = this.toView;
		    $scope.doDelete = this.doDelete;
		    $scope.selectCity=this.selectCity;
		    $scope.selectProvince=this.selectProvince;
		    $scope.selectDistrict=this.selectDistrict;
		    $scope.selectStreet=this.selectStreet;
		},
		params:{
		},
		query:{
			provinceId:-1,
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
			var url = "acBranchFeeBO.ajax?cmd=doQuery";
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:$scope.query
						});
			},500);
		},
		/**清空查询条件*/
		clear:function(){
			$scope.query.provinceId=-1;
			$scope.query.regionId=-1;
			$scope.query.countyId=-1;
			$scope.query.townId=-1;
		},
		/**选择一行**/
		selectOne : function(branchId){
			if(document.getElementById("checkbox"+branchId).checked && document.getElementById("checkbox"+branchId) != undefined){
				document.getElementById("checkbox"+branchId).checked=false;
			}else{
				document.getElementById("checkbox"+branchId).checked=true;
			}
		},
		//新增
		doSave:function(){
			commonService.openTab("228","支线费用新增","/sche/branchFee.html");
		},
		//新增
		toView:function(flag){
			var branchId='';
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
					branchId=data.branchId;
				}
			});
			commonService.openTab(branchId,"支线费用","/sche/branchFee.html?flag="+flag+"&branchId="+branchId);
		},
		doDelete:function(){
			var branchId='';
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条配载信息!");
				return false;
			}
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					branchId=branchId+data.branchId+",";
				}
			});
			commonService.confirm("编号为["+branchId+"]是否删除!",function(){
				var param = {"branchId":branchId};
				var url = "acBranchFeeBO.ajax?cmd=doDelete";
				commonService.postUrl(url,param,function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
						commonService.alert("操作成功!");
						$scope.clear();
						$scope.doQuery();
		 	    	}
				});
			});
		},
		selectProvince:function(){
			var param = {"isAll":"Y"};
			var url = "staticDataBO.ajax?cmd=selectProvince";
			commonService.postUrl(url,param,function(data){
				// 成功执行
				if(data!=null && data!=undefined && data!=""){
						$scope.provinceData=data;
						branchManage.query.provinceId=data.items[0].id;
	 	    	}
			});
		},
		selectCity:function(provinceId){
			if(parseInt(provinceId)>0){
				var param = {"isAll":"Y","provinceId":provinceId};
				var url = "staticDataBO.ajax?cmd=selectCity";
				commonService.postUrl(url,param,function(data){
					// 成功执行
					if(data!=null && data!=undefined && data!=""){
							$scope.cityData=data;
							branchManage.query.regionId=data.items[0].id;
		 	    	}
				});
			}
		},
		selectDistrict:function(cityId){
			if(parseInt(cityId)>0){
				var param = {"isAll":"Y","cityId":cityId};
				var url = "staticDataBO.ajax?cmd=selectDistrict";
				commonService.postUrl(url,param,function(data){
					// 成功执行
					if(data!=null && data!=undefined && data!=""){
							$scope.districtData=data;
							branchManage.query.countyId=data.items[0].id;
		 	    	}
				});
			}
		},
		selectStreet:function(districtId){
			if(parseInt(districtId)>0){
				var param = {"isAll":"Y","districtId":districtId};
				var url = "staticDataBO.ajax?cmd=selectStreet";
				commonService.postUrl(url,param,function(data){
					// 成功执行
					if(data!=null && data!=undefined && data!=""){
							$scope.streetData=data;
							branchManage.query.townId=data.items[0].id;
		 	    	}
				});
			}
		},
	};
	branchManage.init();
}]);
