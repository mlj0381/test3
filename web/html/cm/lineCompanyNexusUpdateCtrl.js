var lineCompanyNexusUpdateApp=angular.module("lineCompanyNexusUpdateApp",['commonApp']);
lineCompanyNexusUpdateApp.controller("lineCompanyNexusUpdateCtrl",["$scope","commonService","$timeout",function($scope,commonService,$timeout){
	var relId=commonService.getQueryString("relId");
	var lineCompanyNexusUpdate={
			init:function(){
				this.bindEvent();
				this.relByIdQuery();
				this.selectOrgLink();
				this.selectOrgCompany();
			},
			bindEvent:function(){
				$scope.close = this.close;	 
				$scope.user=this.user;
				$scope.doUpdate=this.doUpdate;
			},
			relByIdQuery:function(){
				var url="specialLineCompanyNexusBO.ajax?cmd=queryByIdSpecialLineCompanyNexus";
				if(relId!=null&&relId!=undefined &&relId!=''){
					var data={};
					data.relId=relId;
					commonService.postUrl(url,data,function(data){
						$scope.user.companyId=parseInt(data.companyId);
						$scope.user.lineId=parseInt(data.lineId);
					});
				}
			},
			user:{
				companyId:null,
				lineId:null
			},
			//查询所有专线
			selectOrgLink:function(){
				var param = {"isAll":"Y"};
				var url = "organizationBO.ajax?cmd=queryByAllLink";
				commonService.postUrl(url,param,function(data){
					// 成功执行
					if(data!=null && data!=undefined && data != ""){
						$scope.orgLine=data;
		 	    	}
				});
			},
			//查询所有拉包公司
			selectOrgCompany:function(){
				var param = {"isAll":"Y"};
				var url = "organizationBO.ajax?cmd=queryByCompany";
				commonService.postUrl(url,param,function(data){
					// 成功执行
					if(data!=null && data!=undefined && data != ""){
						$scope.companyData=data;
		 	    	}
				});
			},
			close:function(){
				commonService.closeToParentTab(true);
			}, 
			doUpdate:function(){
				var url="specialLineCompanyNexusBO.ajax?cmd=doUpdateSpecialLineCompanyNexus";
				if(relId!=null&&relId!=undefined &&relId!=''){
					var data={};
					data.relId=relId;	
					data.companyId=$scope.user.companyId;
					data.lineId=$scope.user.lineId;
					commonService.postUrl(url,data,function(data){
						commonService.alert("更新完成!",function(){
						commonService.closeToParentTab(true);
						});
				});
			}
			}
	};
	lineCompanyNexusUpdate.init();
}]);



