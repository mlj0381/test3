var myApp = angular.module("installUserManageApp", ['commonApp']);
myApp.controller("installUserManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var myManage={
		init:function(){
			this.bindEvent();			
			this.initData();
			$scope.doQuery();
		},
		bindEvent:function(){
			$scope.doQuery = this.doQuery;
			$scope.queryCity = this.queryCity;
			$scope.clear = this.clear;
			$scope.toAdd = this.toAdd;
			$scope.toDel = this.toDel;
			$scope.toModify = this.toModify;
			$scope.selectAll = this.selectAll;
			$scope.selectOne = this.selectOne;
			$scope.query={};
		},
		//获取静态数据
		initData:function(){
			commonService.postUrl("staticDataBO.ajax?cmd=selectProvince","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.provinces = data.items;
				}
			});
			///货物总类
			commonService.postUrl("productBO.ajax?cmd=queryProductCatalog","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.childServiceItmes = data.items;
				}
			});
		},
		queryCity:function(){
			
			var param={provinceId:$scope.query.provinceId};
			commonService.postUrl("staticDataBO.ajax?cmd=selectCity",param,function(data){
				if(data != null && data != undefined && data != ""){
					$scope.citys = data.items;
				}
			});
		},
		selectCity:function(){
			if($scope.query.provinceId==null){
				alert("请先选择查询省份");
				return ;
			}
		},
		doQuery:function(){
			$timeout(function(){
				$scope.page.load({
					url:'cmCompanyBO.ajax?cmd=queryCompanyListByPage',
					params:$scope.query
						});
			},500);
		},
		
		toAdd:function(){
			commonService.openTab("sfTab","新增合同公司资料","/cm/addContractCompany.html");
		},
		toDel:function(){
			var sfUserIdArray =new Array();
			if($("input[name='check-1']:checked").length==0){
				alert("请选择一条数据!");
				return false;
			}
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=$(this).val();
					sfUserIdArray.push(data);
				}
			});
			
			var param = {"contractIds":sfUserIdArray.join(",")};
			commonService.postUrl("cmCtrCompanyInfoBO.ajax?cmd=delContractCompany",param,function(data){
				if(data =="Y"){
					alert("删除成功");
					$scope.doQuery();
				}
			});
		},
		selectOne : function(batchNum,strId){
			if(document.getElementById(strId+batchNum).checked && document.getElementById(strId+batchNum) != undefined){
				document.getElementById(strId+batchNum).checked=false;
			}else{
				document.getElementById(strId+batchNum).checked=true;
			}
		},
		/**全选*/
		selectAll : function(id,id1) {
			var checkbox = document.getElementsByName(id);
			if (document.getElementById(id1).checked) {
				for (var i = 0; i < checkbox.length; i++) {
					checkbox[i].checked = true;
				}
			} else {
				for (var i = 0; i < checkbox.length; i++) {
					checkbox[i].checked = false;
				}
			}
		},
		clear:function(){
			$scope.query.provinceId='-1';
			$scope.query.cityId='-1';
			$scope.query.commonServiceId='-1';
			$scope.query.valueServiceId='-1';
			$scope.query.companyName='';
			$scope.query.tenantCode=''
			$scope.query.commonChildServiceId='-1'
		},
		toModify:function(){
			if($("input[name='check-1']:checked").length==0){
				alert("请选择一条数据!");
				return false;
			}
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=$(this).val();
					var param = {"contractId":data};
					commonService.openTab("sfModifyTab","修改合同公司资料","/cm/toModifyContractCompany.html?contractId="+data);
				}
			});
		}
	};
	myManage.init();
}]);
