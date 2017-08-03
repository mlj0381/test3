var contactGSTaskManageApp = angular.module("contactGSTaskManageApp", ['commonApp']);
contactGSTaskManageApp.controller("contactGSTaskManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var contactGSTaskManage={
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
		    $scope.openWayDetail=this.openWayDetail;
		    $scope.openExc=this.openExc;
		    $scope.cancerDis=this.cancerDis;
		    $scope.toUpdate = this.toUpdate;
		    $scope.updateYM = this.updateYM;
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
		openWayDetail:function(){
			var orderId='';
			var taskId='';
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
					taskId=data.taskId;
					}
			});
			commonService.openTab(taskId+orderId,"任务详情","/sche/task/waybill_details.html?orderId="+orderId+"&taskId="+taskId);

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
				$scope.query.branchAndInstall=data.branchAndInstall;
			    $(".cplx").hover(function(){
                	 $(this).find("span").show();
                },function(){
            	     $(this).find("span").hide();
                });

			});
		},
		updateYM:function(){
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					$scope.query.totalMoney = data.branchAndInstall;
				}
			});
		},
		toUpdate:function(){
			var taskId='';
			var totalMoney = "";
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
				commonService.alert("运单状态为["+taskStateName+"],不能修改配安费!");
				return false;
			}
			totalMoney = $scope.query.totalMoney;
			commonService.confirm("运单号["+wayBillId+"],是否确认修改配安费？",function(){
				var param = {"taskId":taskId,"totalMoney":totalMoney};
				var url = "scheTaskBO.ajax?cmd=doUpdateMoney";
				commonService.postUrl(url,param,function(data){
					if(data!=null && data!=undefined && data!=""){
						document.getElementById('vehicle_1').style.display='none';
						document.getElementById('fade1_xz').style.display='none';
						commonService.alert("修改配安费成功!");
						$scope.doQuery();
		 	    	}
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
			$scope.query.endTime=document.getElementById('endTime').value;
			$scope.query.beginTime=document.getElementById('beginTime').value;
			var url = "scheTaskBO.ajax?cmd=taskTrace";
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:$scope.query,
							callBack:"setContentHegthDelay"
						});
			},500);
		},
		clear:function(){
			//$scope.query.receivePhone="";
			$scope.query.receiveName="";
			$scope.query.clientName="";
			$scope.query.taskState="-1";
			$scope.query.wayBillId="";
			$scope.query.signState="-1";
			$scope.query.isTmall="-1";
			$scope.query.provinceId=-1;
			$scope.query.cityId=-1;
			$scope.query.countyId=-1;
			$scope.cityData={};
			$scope.districtData={};
			document.getElementById('endTime').value="";
			document.getElementById('beginTime').value="";
			
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
						contactGSTaskManage.query.provinceId=data.items[0].id;
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
							contactGSTaskManage.query.cityId=data.items[0].id;
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
							contactGSTaskManage.query.countyId=data.items[0].id;
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
							contactGSTaskManage.query.townId=data.items[0].id;
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
					if(data.orderState!=4){
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
	contactGSTaskManage.init();
}]);
