var busiRepairTaskManageApp = angular.module("busiRepairTaskManageApp", ['commonApp']);
busiRepairTaskManageApp.controller("busiRepairTaskManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var busiRepairTaskManage={
		init:function(){
			this.bindEvent();
			this.doQuery();
			this.selectProvince();
		},
		bindEvent:function(){
			$scope.query = this.query;
			$scope.doQuery = this.doQuery;
		    $scope.clear = this.clear;
		    $scope.selectAll = this.selectAll;
		    $scope.selectOne = this.selectOne;
		    $scope.selectCity=this.selectCity;
		    $scope.selectProvince=this.selectProvince;
		    $scope.selectDistrict=this.selectDistrict;
		    $scope.selectStreet=this.selectStreet;
		    $scope.showGoodsDetail=this.showGoodsDetail;
		    $scope.dealExc=this.dealExc;
		   // $scope.openExc=this.openExc;
		    //$scope.cancerDis=this.cancerDis;
		},
		openExc:function(){
			var orderId='';
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			if($("input[name='check-1']:checked").length>1){
				commonService.alert("只能选择一条任务信息!");
				return false;
			}
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					orderId=data.orderId;
					}
			});
			commonService.openTab("exc"+orderId,"异常登记","/sche/exc/excRegister.html?isShow=1&orderId="+orderId);
		},
		dealExc:function(isView){
			var excId='';
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			if($("input[name='check-1']:checked").length>1){
				commonService.alert("只能选择一条任务信息!");
				return false;
			}
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					excId=data.excId;
					}
			});
			var excState=null;
			if(isView==2){
				excState=3;
			}
			commonService.openTab("exc"+excId,"任务详情","/sche/exc/excRegister.html?isShow=2"+"&excId="+excId+"&isView="+isView+"&excState="+excState);
		},
		query:{
			provinceId:-1,
			isTmall:"-1"
		},
		//订单流程信息
		showGoodsDetail:function(orderId){
			var url="scheTaskBO.ajax?cmd=goodsDetail";
			var param="orderId="+orderId;
			var html="";
			commonService.postUrl(url,param,function(data){
				$scope.goodsInfo=data.goodsInfo;
			    $(".cplx").hover(function(){
                	 $(this).find("span").show();
                },function(){
            	     $(this).find("span").hide();
                });

			});
		},
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
		doQuery:function(){
			$scope.query.isService=1;
			$scope.query.isQuery=1;
			var url = "repairScheTaskBO.ajax?cmd=doTaskQuery";
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:$scope.query
						});
			},500);
		},
		clear:function(){
			//$scope.query.receivePhone="";
			$scope.query.receiveName="";
			$scope.query.clientName="";
			$scope.query.taskState="-1";
			$scope.query.wayBillId="";
			$scope.query.isTmall="-1";
			$scope.query.provinceId=-1;
			$scope.query.cityId=-1;
			$scope.query.countyId=-1;
			$scope.cityData={};
			$scope.districtData={};
			
		},
		selectOne : function(taskId){
			var inputArr=document.getElementsByName("check-1");
			for(var i=0;i<inputArr.length;i++){
				if(eval("("+inputArr[i].value+")").taskId!=taskId){
					inputArr[i].checked=false;
				}
			}
			if(document.getElementById("checkbox"+taskId).checked && document.getElementById("checkbox"+taskId) != undefined){
				document.getElementById("checkbox"+taskId).checked=false;
			}else{
				document.getElementById("checkbox"+taskId).checked=true;
			}
		},
		selectProvince:function(){
			var param = {"isAll":"Y"};
			var url = "staticDataBO.ajax?cmd=selectProvince";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
						$scope.provinceData=data;
						busiRepairTaskManage.query.provinceId=data.items[0].id;
	 	    	}
			});
		},
		selectCity:function(provinceId){
			if(parseInt(provinceId)>0){
				var param = {"isAll":"Y","provinceId":provinceId};
				var url = "staticDataBO.ajax?cmd=selectCity";
				commonService.postUrl(url,param,function(data){
					if(data!=null && data!=undefined && data!=""){
							$scope.cityData=data;
							busiRepairTaskManage.query.cityId=data.items[0].id;
		 	    	}
				});
			}
		},
		selectDistrict:function(cityId){
			if(parseInt(cityId)>0){
				var param = {"isAll":"Y","cityId":cityId};
				var url = "staticDataBO.ajax?cmd=selectDistrict";
				commonService.postUrl(url,param,function(data){
					if(data!=null && data!=undefined && data!=""){
							$scope.districtData=data;
							busiRepairTaskManage.query.countyId=data.items[0].id;
		 	    	}
				});
			}
		},
		selectStreet:function(districtId){
			if(parseInt(districtId)>0){
				var param = {"isAll":"Y","districtId":districtId};
				var url = "staticDataBO.ajax?cmd=selectStreet";
				commonService.postUrl(url,param,function(data){
					if(data!=null && data!=undefined && data!=""){
							$scope.streetData=data;
							busiRepairTaskManage.query.townId=data.items[0].id;
		 	    	}
				});
			}
		},
		cancerDis:function(){
			var taskId='';
			var orderId='';
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			if($("input[name='check-1']:checked").length>1){
				commonService.alert("只能选择一条任务信息!");
				return false;
			}
			var isOK=true;
			var taskStateName='';
			var wayBillId='';
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					taskId=data.taskId;
					orderId=data.orderId;
					taskStateName=data.orderStateName;
					wayBillId=data.wayBillId;
					if(data.orderState==8){
						isOK=false;
					}
				}
			});
			if(!isOK){
				commonService.alert("运单状态为["+taskStateName+"],不能取消分配!");
				return false;
			}
			commonService.confirm("运单号["+wayBillId+"],是否确认取消分配？",function(){
				var param = {"taskId":taskId,"orderId":orderId};
				var url = "scheTaskBO.ajax?cmd=cancerDis";
				commonService.postUrl(url,param,function(data){
					if(data!=null && data!=undefined && data!=""){
						commonService.alert("取消分配成功!");
						$scope.doQuery();
		 	    	}
				});
			});
		}
	}
	busiRepairTaskManage.init();
}]);
