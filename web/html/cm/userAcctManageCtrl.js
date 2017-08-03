var myApp = angular.module("userManageApp", ['commonApp']);
myApp.controller("userManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var myManage={
		init:function(){
			this.bindEvent();	
			this.initData();
			$scope.doQuery();
			$scope.showAddAcct=false;
			
		},
		bindEvent:function(){
			$scope.cmUser={};
			$scope.doQuery = this.doQuery;
			$scope.queryCity = this.queryCity;
			$scope.clear = this.clear;
			$scope.toAddSfUser = this.toAddSfUser;
			$scope.doClose = this.doClose;
			$scope.toModify = this.toModify;
			$scope.selectAll = this.selectAll;
			$scope.selectOne = this.selectOne;
			$scope.doSave = this.doSave;
			$scope.checkSaveData = this.checkSaveData;
			$scope.query={};
		},
		//获取静态数据
		initData:function(){
			commonService.postUrl("staticDataBO.ajax?cmd=selectProvince","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.provinces = data.items;
				}
			});
			var param={codeType:"ACCT_RECE_TYPE"};
			commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType",param,function(data){
				if(data != null && data != undefined && data != ""){
					$scope.receTypes = data.items;
				}
			});
			param={codeType:"ACCT_BANKS"};
			commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType",param,function(data){
				if(data != null && data != undefined && data != ""){
					$scope.acctBanks = data.items;
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
							url:'cmSfUserInfoBO.ajax?cmd=querySFUserAcctInfos',
							params:$scope.query
						});
			},500);
		},
		
		toAddSfUser:function(){
			$scope.showAddAcct=true;
		},
		//保存
		doSave:function(){
			/*if(!$scope.checkSaveData()){
				return;
			}*/
			$timeout(function(){
				commonService.postUrl("cmSfUserInfoBO.ajax?cmd=modSFUserAcctInfo",$scope.cmUser,function(data){
					if(data != null && data != undefined && data != ""){
						if(data == 'Y'){
							commonService.alert("你已成功变更账户资料");
							$scope.showAddAcct=true;
						}
					}
				},null,null,'Y');
			},500);
		},
		doClose:function(){
			$scope.showAddAcct=false;
		},
		checkSaveData:function(){
			if($scope.cmUser.receiType==''){
				commonService.alert('账户类型');
				return false;
			}
			if($scope.cmUser.bankName==''){
				commonService.alert('请选择银行');
				return false;
			}
			if($scope.cmUser.accountName==''){
				commonService.alert('请输入持卡人名称');
				return false;
			}
			if($scope.cmUser.bankAccount==''){
				commonService.alert('请输入银行账户');
				return false;
			}
			return true;
		},
		toDelSfUser:function(){
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
			
			var param = {"sfUserIds":sfUserIdArray.join(",")};
			commonService.postUrl("cmSfUserInfoBO.ajax?cmd=deleteUserInfo",param,function(data){
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
			$scope.query.bankName='';
			$scope.query.receiType='-1';
			$scope.query.name='';
			$scope.query.phone=''
		},
		toModify:function(){
			if($("input[name='check-1']:checked").length==0){
				alert("请选择一条数据!");
				return false;
			}
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=$(this).val();
					var obj = eval("("+data+")");
					$scope.cmUser.phone=obj.phone;
					$scope.cmUser.sfUserId=obj.sfUserId;
					$scope.cmUser.name=obj.name;
					$scope.cmUser.bankName=obj.bankName;
					$scope.cmUser.accountName=obj.accountName;
					$scope.cmUser.bankAccount=obj.bankAccount;
					$scope.cmUser.receiType=obj.receiType;
					$scope.showAddAcct=true;
				}
			});
		}
	};
	myManage.init();
}]);
