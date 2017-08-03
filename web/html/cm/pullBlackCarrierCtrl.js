var pullBlackCarrierApp=angular.module("pullBlackCarrierApp",['commonApp','tableCommon']);
pullBlackCarrierApp.controller("pullBlackCarrierCtrl",["$scope","commonService",function($scope,commonService){
	var pullBlackCarrier={
			init:function(){
				this.bindEvent();
			},
			head:[
			      	{
			      		"name":"拉包工",
			      		"code":"carrierName",
			      		"width":"100",
			      		"type":"text"
			      	},
			      	{
			      		"name":"拉包工账号",
			      		"code":"carrierAccount",
			      		"width":"100",
			      		"type":"text"
			      	},
			    	{
			      		"name":"归属公司",
			      		"code":"companyName",
			      		"width":"130"
			      	},
			    	{
			      		"name":"拉黑时间",
			      		"code":"createDate",
			      		"width":"130"
			      	},
			    	{
			      		"name":"创建人",
			      		"code":"creatorName",
			      		"width":"100"
			      	}
			      	,
			    	{
			      		"name":"拉黑备注",
			      		"code":"remark",
			      		"width":"100"
			      	},
			      	{
			      		"name":"拉黑级别",
			      		"code":"pullLevel",
			      		"width":"100"
			      	}
			      ],
			      bindEvent:function(){
			    	$scope.head= pullBlackCarrier.head;
			    	$scope.url="pullBlackCarrierBO.ajax?cmd=doQueryPullBlackCarrier";
			    	$scope.urlParam=pullBlackCarrier.user;
			    	$scope.user=this.user;
			    	$scope.doQuery = this.doQuery;
			    	$scope.clear=this.clear;
			    	$scope.newAdd=this.newAdd;
			    	$scope.doUpdate=this.doUpdate;
			      },
			      user:{
						carrierName:"",
						carrierAccount:""
				 },
				doQuery:function(){
					var url="pullBlackCarrierBO.ajax?cmd=doQueryPullBlackCarrier";	
					$scope.table.load();
					$scope.table.callBack=function(){
						displayTable();
						setContentHeight();
					}
					},
				clear:function(){
					$scope.table.clearInput();
					$scope.user.carrierName="",
					$scope.user.carrierAccount="";
				},
				newAdd:function(){
					commonService.openTab("25","拉黑拉包工","/cm/pullBlackCarrierAdd.html");
				},
				doUpdate:function(){
					var pullId='';
					var selectedData=$scope.table.getSelectData();
					if(selectedData.length==0){
						commonService.alert("请至少选择一条拉黑信息");
						return false;
					}
					if(selectedData.length>1){
						commonService.alert("只能选择一条拉黑信息");
						return false;
					}
					var data=selectedData[0];
					pullId=data.pullId;
					var url="pullBlackCarrierBO.ajax?cmd=doUpdatePullBlackCarrierByAccount";
					var param={"pullId":pullId};
					commonService.postUrl(url,param,function(data){
		        		commonService.alert("取消成功!");
		        		$scope.doQuery();
		        	});
					},
				
	};
	pullBlackCarrier.init();
}]);