var myApp = angular.module("ApplicationApp", ['commonApp']);
myApp.controller("ApplicationCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	$scope.complaintInfoState=[];
	$scope.complaintInfoSourceType=[];
	var myManage={
		init:function(){
			$scope.query = {};
			$scope.add = {};
			$scope.audit = {};
			$scope.itemsSf=[];
			this.bindEvent();
			this.initQuery();
			setContentHeight();
			this.initSetXing();
			this.getComplaintInfoState();
			this.getComplaintInfoSourceType();
		},
		bindEvent:function(){
			$scope.param = this.param;
		    $scope.doSave = this.doSave;
			$scope.close = this.close;
			$scope.starOn = this.starOn;
			$scope.starClick = this.starClick;
		},
		getComplaintInfoState:function(){
			var url = "staticDataBO.ajax?cmd=getComplaintInfoState";
			commonService.postUrl(url,"",function(data){
				if(data!=null && data!=undefined && data!=""){
					$scope.complaintInfoState=data.items;
	 	    	}
			});
		},
		getComplaintInfoSourceType:function(){
			var url = "staticDataBO.ajax?cmd=getComplaintInfoSourceType";
			commonService.postUrl(url,"",function(data){
				if(data!=null && data!=undefined && data!=""){
					$scope.complaintInfoSourceType=data.items;
	 	    	}
			});
		},
		initSetXing:function(){
			var stepW = 25;
		   // var description = new Array("一般差，回去再练练","真的是差，都不忍心说你了","很差","","差");
		    var stars = $("#star > li");
		    var descriptionTemp;
		    $("#showb").css("width",0);
		    stars.each(function(i){
		        $(stars[i]).click(function(e){
		            var n = i+1;
		            $("#showb").css({"width":stepW*n});
		        //    descriptionTemp = description[i];
		        	selectStar = $(this).find('a').html();
		            $(this).find('a').blur();
		            $scope.starOn(n);
		            if(e && e.preventDefault)
				           e.preventDefault();
				    else
				           window.event.returnValue = false;
				    return false;
		        });
		    });
		},
		close:function(){
			commonService.closeToParentTab();
		},
		starClick:function(){
			alert('a');
		},
		params:{
		},
		initQuery:function(){
    		var belongObjId = getQueryString("belongObjId");
    		var belongObjName = getQueryString("belongObjName");
    		var id = getQueryString("id");
    		var sourceType = getQueryString("sourceType");
    		var sfUserId = getQueryString("sfUserId");
    		var state = getQueryString("state");
    		if(belongObjName==null || belongObjName==undefined || belongObjName=="" || belongObjName=="null"){
    			belongObjName="";
    		}
    		$scope.add.companyName = belongObjName;
    		//修改初始值
			$scope.add.sourceType = sourceType;
			$scope.add.state = state;
			var param = {"tenantId":belongObjId};
			var url = "cmSfUserInfoBO.ajax?cmd=queryUserInfo";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.itemsSf = data.items;
					$timeout(function(){
						$scope.add.bcSfUserId = sfUserId;
					},1000);
				}
			});
			var param1 = {"id":id,"flag":2};
			var url = "serveBO.ajax?cmd=doQuery";
			commonService.postUrl(url,param1,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.add.complaintContent = data.items[0].complaintContent;
					$scope.add.dealResult = data.items[0].dealResult;
					selectStar=data.items[0].level;
					$scope.starOn(data.items[0].level);
					$timeout(function(){
						$scope.add.sourceType = data.items[0].sourceType;
						$scope.add.state = data.items[0].state;
					},1000);
				}
			});
		},
		starOn:function(level){
			$("#one-star").removeClass("on");
			$("#two-stars").removeClass("on");
			$("#three-stars").removeClass("on");
			$("#four-stars").removeClass("on");
			$("#five-stars").removeClass("on");
			if(level==1){
				$(".one-star").addClass("on");
			}
			if(level==2){
				$(".one-star").addClass("on");
				$(".two-stars").addClass("on");
			}
			if(level==3){
				$(".one-star").addClass("on");
				$(".two-stars").addClass("on");
				$(".three-stars").addClass("on");
			}
			if(level==4){
				$(".one-star").addClass("on");
				$(".two-stars").addClass("on");
				$(".three-stars").addClass("on");
				$(".four-stars").addClass("on");
			}
			if(level==5){
				$(".one-star").addClass("on");
				$(".two-stars").addClass("on");
				$(".three-stars").addClass("on");
				$(".four-stars").addClass("on");
				$(".five-stars").addClass("on");
			}
		},
		doSave:function(){
    		var batchNum=getQueryString("batchNum");
    		if(batchNum==null||batchNum==undefined|| batchNum==''){
    			commonService.alert("运单号为空!");
    			return false;
    		}
    		myManage.params.relateOrder = getQueryString("wayBillId");
    		myManage.params.id = getQueryString("id");
			myManage.params.level = selectStar;
			myManage.params.sourceType = $scope.add.sourceType;
			myManage.params.complaintContent = $scope.add.complaintContent;
			myManage.params.bcSfUserId = $scope.add.bcSfUserId;
			myManage.params.state = $scope.add.state;
			myManage.params.dealResult = $scope.add.dealResult;
			var url = "serveBO.ajax?cmd=doSave";
			commonService.postUrl(url,myManage.params,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					commonService.alert("处理成功!");
					$scope.close();
				}
			});
		}
	};
	myManage.init();
}]);
