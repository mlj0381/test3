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
			$scope.toAddSfUser = this.toAddSfUser;
			$scope.initData = this.initData;
			$scope.toDelSfUser = this.toDelSfUser;
			$scope.toModify = this.toModify;
			$scope.selectAll = this.selectAll;
			$scope.selectOne = this.selectOne;
			$scope.authorSfInfo=this.authorSfInfo;
			$scope.query={};
			$scope.close=this.close;
			$scope.selectPlatSf=this.selectPlatSf;
		},
		//获取静态数据
		initData:function(){
			commonService.postUrl("staticDataBO.ajax?cmd=selectProvince","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.provinces = data.items;
				}
			});
		},
		selectPlatSf:function(){
			commonService.openTab("seleteTab","选择平台师傅","/cm/platSfManage.html");
		},
		queryCity:function(provinceId){
			var param={provinceId:provinceId};
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
							url:'cmSfUserInfoBO.ajax?cmd=queryUserInfo',
							params:$scope.query
						});
			},500);
		},
		
		toAddSfUser:function(){
			commonService.openTab("sfTab","新增师傅资料","/cm/addInstallUser.html");
		},
		authorSfInfo:function(){
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请选择一条数据!");
				return false;
			}
			if($("input[name='check-1']:checked").length>1){
				commonService.alert("最多选择一条数据!");
				return false;
			}
			var isTrue=true;
			var sfName=null;
			var sfUserId='';
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					sfUserId=data.sfUserId;
					if(data.auditState==2||data.auditState==4){
						isTrue=false;
						sfName=data.name;
					}
				}
			});
			if(!isTrue){
				commonService.alert(sfName+"等师傅信息已操作过审核动作，无需再操作!");
				return false;
			}
			commonService.openTab(sfUserId,"认证师傅资料","/cm/auditInstallUser.html?userId="+sfUserId);
		},
		toDelSfUser:function(){
			var sfUserIdArray =new Array();
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请选择一条数据!");
				return false;
			}
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					sfUserIdArray.push(data.sfUserId);
				}
			});
			var param = {"sfUserIds":sfUserIdArray.join(",")};
			commonService.postUrl("cmSfUserInfoBO.ajax?cmd=deleteUserInfo",param,function(data){
				if(data =="Y"){
					commonService.alert("删除成功");
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
			$scope.query.sfUserName='';
			$scope.query.sfUserAcct='';
			$scope.query.cooperationType='-1';
			$scope.query.auditState='-1';
		},
		close:function(){
    		commonService.closeTab();
		},
		toModify:function(flag){
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请选择一条数据!");
				return false;
			}
			if($("input[name='check-1']:checked").length>1){
				commonService.alert("最多选择一条数据!");
				return false;
			}
			var tips="修改";
			if(flag==1){
				tips="查看";
			}
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					commonService.openTab(data.sfUserId,tips+"师傅资料","/cm/addInstallUser.html?userId="+data.sfUserId+"&isView="+flag);
				}
			});
		}
	};
	myManage.init();
}]);
