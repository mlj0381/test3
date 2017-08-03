var myApp = angular.module("auditUserManageApp", ['commonApp']);
myApp.controller("auditUserManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
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
			$scope.toAddSfUser = this.toAddSfUser;
			$scope.toAuditSfUser= this.toAuditSfUser;
			$scope.toView = this.toView;
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
				commonService.alert("请先选择查询省份");
				return ;
			}
		},
		doQuery:function(){
			$timeout(function(){
				$scope.page.load({
							url:'cmSfUserInfoBO.ajax?cmd=queryChagServiceUserInfos',
							params:$scope.query
						});
			},500);
		},
		
		toAddSfUser:function(){
			commonService.openTab("sfTab","新增师傅资料","/cm/addInstallUser.html");
		},
		
		toAuditSfUser:function(opType){
			var sfUserIdArray =new Array();
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请选择一条数据!");
				return false;
			}
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=$(this).val();
					sfUserIdArray.push(data);
				}
			});
			var param = {"sfUserIdProdIds":sfUserIdArray.join(","),
					"opType":opType
					};
			commonService.postUrl("cmSfUserInfoBO.ajax?cmd=auditChagUserInfo",param,function(data){
				if(data =="Y"){
					commonService.alert("操作成功");
					$scope.doQuery();
				}
			});
			/*var pass = commonService.confirm("确定审核所选的服务",function(){
				commonService.postUrl("cmSfUserInfoBO.ajax?cmd=auditChagUserInfo",param,function(data){
					if(data =="Y"){
						alert("认证成功");
						$scope.doQuery();
					}
				});
			}
			);*/
			
			
		},
		selectOne : function(batchNum,strId,auditState){
			if(document.getElementById(strId+batchNum).checked && document.getElementById(strId+batchNum) != undefined){
				document.getElementById(strId+batchNum).checked=false;
			}else{
				if(auditState==2){
					commonService.alert("当前用户已审核,请核查");
					return;
				}
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
			$scope.query.sfUserName='';
			$scope.query.sfUserAcct='',
			//$scope.query.auditState='-1',
			$scope.query.commonChildServiceId='-1'
		},
		toView:function(){
			if($("input[name='check-1']:checked").length!=1){
				commonService.alert("请选择一条数据!");
				return false;
			}
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=$(this).val();
					var param = {"sfUserId":data};
					commonService.openTab("sfModifyTab","查看师傅资料","/cm/toModifyInstallUser.html?userId="+data);
				}
			});
		}
		
	};
	myManage.init();
}]);
