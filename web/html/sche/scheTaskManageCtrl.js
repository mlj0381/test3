var scheTaskManageApp = angular.module("scheTaskManageApp", ['commonApp']);
scheTaskManageApp.controller("scheTaskManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var scheTaskManage={
		init:function(){
			this.bindEvent();
			this.doQuery();
			this.selectProvince();
			this.statisticsTask();
		},
		bindEvent:function(){
			$scope.param = this.param;
			$scope.query = this.query;
			$scope.doQuery = this.doQuery;
		    $scope.clear = this.clear;
		    $scope.selectAll = this.selectAll;
		    $scope.selectOne = this.selectOne;
		    $scope.selectCity=this.selectCity;
		    $scope.selectProvince=this.selectProvince;
		    $scope.selectDistrict=this.selectDistrict;
		    $scope.selectStreet=this.selectStreet;
		    $scope.moreRecommend=this.moreRecommend;
		    $scope.mapInit=this.mapInit;
		    $scope.setMapCity=this.setMapCity;
		    $scope.queryAroundInfo=this.queryAroundInfo;
		    $scope.mouseClick=this.mouseClick;
		    $scope.queryMatchSf=this.queryMatchSf;
		    $scope.gxEnd=this.gxEnd;
		    $scope.statisticsTask=this.statisticsTask;
		    $scope.shceDisSf=this.shceDisSf;
		    $scope.closeGxEnd=this.closeGxEnd;
		    $scope.openGxEnd=this.openGxEnd;
		    $scope.map=this.map;
		    $scope.sfInfo=this.sfInfo;
		    $scope.openDisSf=this.openDisSf;
		    $scope.showCredit=this.showCredit;
		    $scope.showGoodsDetail=this.showGoodsDetail;
		    $scope.starOn=this.starOn;
		    $scope.showOrClose=this.showOrClose;
		    $scope.openWayDetail=this.openWayDetail;
		    $scope.openExc=this.openExc;
		},
		openExc:function(){
			var wayBillId='';
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
					wayBillId=data.wayBillId;
					}
			});
			commonService.openTab("exc"+wayBillId,"异常登记","/sche/exc/excRegister.html?isShow=1&wayBillId="+wayBillId);
		},
		openWayDetail:function(){
			var wayBillId='';
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
					wayBillId=data.wayBillId;
					taskId=data.taskId;
					}
			});
			commonService.openTab(taskId+wayBillId,"任务详情","/sche/task/waybill_details.html?wayBillId="+wayBillId+"&taskId="+taskId);

		},
		map:null,
		sfInfo:{
			name:'',
			phone:'',
			taskId:'',
			wayBillId:'',
			sfUserId:'',
			installFee:'',
			branchFee:''
		},
		showOrClose:function(){
			// 收缩展开效果
			if(document.getElementById("ul").style.display=='none'){
				$("#ul").show(1000);
			}else{
				$("#ul").hide(1000);
			}
		},
		params:{
		},
		query:{
			provinceId:-1,
			taskState:2,
			isTmall:"-1"
		},
		//订单流程信息
		showGoodsDetail:function(wayBillId){
			var url="scheTaskBO.ajax?cmd=goodsDetail";
			var param="wayBillId="+wayBillId;
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
		showCredit:function(sfUserId){
    		if(sfUserId==null||sfUserId==undefined|| sfUserId==''){
    			commonService.alert("师傅ID为空!");
    			return false;
    		}
			var param = {"userId":sfUserId};
    		var url="creditManageBO.ajax?cmd=getDtlById";
			commonService.postUrl(url,param,function(data){
				$scope.ca= data.ca;
				$scope.cad= data.cad;
				$scope.starOn(data.ca.creditLevel);
				 $(".cplx").hover(function(){
                	 $(this).find("span").show();
                },function(){
            	     $(this).find("span").hide();
                });
			});
		},
		starOn:function(level){
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
		openGxEnd:function(){
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			if($("input[name='check-1']:checked").length>1){
				commonService.alert("只能选择一条任务信息!");
				return false;
			}
			var gxEndTime='';
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					gxEndTime=data.gxEndTime;
				}
			});
			if(gxEndTime!=null&&gxEndTime!=undefined&&gxEndTime!=''){
				commonService.alert("已操作过干线结束，无需再操作!");
				return false;
			}
			document.getElementById('vehicle_qxjs').style.display='block';
			document.getElementById('fade1_xz').style.display='block';
		},
		closeGxEnd:function(){
			document.getElementById('vehicle_qxjs').style.display='none';
			document.getElementById('fade1_xz').style.display='none';
			$scope.query.pickUpAddr='';
			$scope.query.pickUpPhone='';
			$scope.query.pickCode='';
		},
		statisticsTask:function(){
			var param = {};
			var url = "scheTaskBO.ajax?cmd=statisticsTaskCount";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
						$scope.statisticsTaskCount=data;
	 	    	}
			});
		},
		shceDisSf:function(){
			 commonService.confirm("运单号["+$scope.sfInfo.wayBillId+"],指派给"+$scope.sfInfo.name+"师傅，安装费："+$scope.installFee+"元,支线费："+$scope.branchFee+"。确定分配？",function(){
				var param = {"taskId":$scope.sfInfo.taskId,"wayBillId":$scope.sfInfo.wayBillId,"branchFee":$scope.branchFee,"installFee":$scope.installFee,"sfUserId":$scope.sfInfo.sfUserId,"userType":1};
				var url = "scheTaskBO.ajax?cmd=disSf";
				commonService.postUrl(url,param,function(data){
					if(data!=null && data!=undefined && data!=""){
						commonService.alert("分配成功!");
						document.getElementById('vehicle_fp').style.display='none';
						document.getElementById('fade1_xz').style.display='none';
						$scope.doQuery();
						$scope.statisticsTask();
		 	    	}
				});
			});
		},
		gxEnd:function(){
			var taskId='';
			var wayBillId='';
			var pickUpAddr=$scope.query.pickUpAddr;
			var pickUpPhone=$scope.query.pickUpPhone;
			var pickCode=$scope.query.pickCode;
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
					taskId=data.taskId;
					wayBillId=data.wayBillId;
				}
			});
			if(pickUpAddr==null||pickUpAddr==undefined||pickUpAddr==''){
				commonService.alert("请填写提货地址!");
				return false;
			}
			if(pickUpPhone==null||pickUpPhone==undefined||pickUpPhone==''){
				commonService.alert("请填写提货手机!");
				return false;
			}
			if(pickCode==null||pickCode==undefined||pickCode==''){
				commonService.alert("请填写提货验证码!");
				return false;
			}
			var param = {"taskId":taskId,"wayBillId":wayBillId,"pickUpPhone":pickUpPhone,"pickUpAddr":pickUpAddr,"pickCode":pickCode};
			var url = "scheTaskBO.ajax?cmd=gxEnd";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
					commonService.alert("操作成功!");
					$scope.doQuery();
					$scope.closeGxEnd();
	 	    	}
			});
		},
		queryMatchSf:function(){
//			if($("input[name='check-1']:checked").length==0){
//				commonService.alert("请至少选择一条任务信息!");
//				return false;
//			}
//			if($("input[name='check-1']:checked").length>1){
//				commonService.alert("只能选择一条任务信息!");
//				return false;
//			}
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					taskId=data.taskId;
					wayBillId=data.wayBillId;
				}
			});
			var param = {"taskId":taskId,"wayBillId":wayBillId};
			var url = "scheTaskBO.ajax?cmd=queryMatchSf";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
					$scope.sfList=data.sfList;
					$scope.receiveInfo=data.receiveInfo;
	 	    	}
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
			var url = "scheTaskBO.ajax?cmd=doTaskQuery";
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:$scope.query
						});
			},500);
		},
		clear:function(){
			//$scope.query.receivePhone="";
			//$scope.query.receiveName="";
			$scope.query.clientName="";
			$scope.query.wayBillId="";
			$scope.query.isTmall="-1";
			$scope.query.provinceId=-1;
			$scope.query.cityId=-1;
			$scope.query.countyId=-1;
			$scope.cityData={};
			$scope.districtData={};
			
		},
		selectOne : function(taskId){
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
						scheTaskManage.query.provinceId=data.items[0].id;
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
							scheTaskManage.query.cityId=data.items[0].id;
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
							scheTaskManage.query.countyId=data.items[0].id;
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
							scheTaskManage.query.townId=data.items[0].id;
		 	    	}
				});
			}
		},
		//更多推荐
		moreRecommend:function(taskId,wayBillId,cityName){
				if(document.getElementById("dhl_map").style.display=='none'){
					var ff = $("#"+taskId);
					var hh = ff.position().top+35+"px";		
					$("#dhl_map").css("position","absolute");
					$("#dhl_map").css("top",hh);
					document.getElementById("dhl_map").style.display="block";
					document.getElementById(taskId).innerHTML="关闭推荐";
					scheTaskManage.mapInit(cityName,taskId);
				}else{
						document.getElementById("dhl_map").style.display="none";
						document.getElementById(taskId).innerHTML="更多推荐";
				}
		},
		mapInit:function(cityName,taskId){
			map = new BMap.Map(allmap);
			map.enableScrollWheelZoom();
			var that=this;
			//地图加载完毕
//			map.addEventListener("tilesloaded",function(){
//				that.loadClose();
//			});
			map.addEventListener("click", function(e){
				that.mouseClick(e);
			});
			that.setMapCity(cityName);
		},
		setMapCity:function(cityName){
			var that=this;
			if(cityName!=null && cityName!=undefined && cityName!=""){
				var mapCity = new BMap.LocalCity();
				mapCity.get(function(result){
					var pt = new BMap.Point(result.center.lng, result.center.lat);
					map.centerAndZoom(pt, 8); 
					map.addControl(new BMap.NavigationControl());
					map.setCenter(cityName);
					var myGeo = new BMap.Geocoder();
					myGeo.getPoint(cityName, function(point){
						if (point) {
							that.queryAroundInfo(point.lng,point.lat);
						}
					});
				});
			}else{
				var mapCity = new BMap.LocalCity();
				mapCity.get(function(result){
					var pt = new BMap.Point(result.center.lng, result.center.lat);
					map.centerAndZoom(pt, 8);
					map.addControl(new BMap.NavigationControl());
					map.setCenter(result.name);
					that.queryAroundInfo(result.center.lng,result.center.lat);
				});
			}
		},
		queryAroundInfo:function(lng,lat){
			scheTaskManage.queryMatchSf();
			var that=this;
				var receiveInfo = $scope.receiveInfo;
				var sfList = $scope.sfList;
				var vehicleIcon = new BMap.Icon("../image/$tenantId$/map_tb.png", new BMap.Size(33, 42), {  
                    offset: new BMap.Size(33, 42), // 指定定位位置  
                    imageOffset: new BMap.Size(-103,0) // 设置图片偏移  
                });
				var markersVehicle = [];
				for(var i=0;i<receiveInfo.length;i++){
					var reqObj=receiveInfo[i];
					var pt = new BMap.Point(reqObj.eand, reqObj.nand);
					var marker = new BMap.Marker(pt,{icon:vehicleIcon});
					map.addOverlay(marker);
					markersVehicle[i]=marker;
					map.addOverlay(marker);
					(function(){
				        var html='<div class="map_wztis">'+
		                '<div class="map_wzl">'+
		                '	<p class="fl map_w">'+
		                '		联系名称:'+reqObj.receiveName+'</br>'+
		                '		联系手机：'+reqObj.receivePhone+'<br>'+
		                '		货品名称：'+reqObj.product+'<br>'+
		                '		地址：'+reqObj.receiveAddr+
		                '	</p>'+
		                '</div>'+
		                '<div style="position: absolute; z-index: 3; left: 152px; top: 116px;">'+
		                '	<div style="position: absolute; overflow: hidden; width: 45px; height: 27px;">'+
		                '		<img src="../image/$tenantId$/1.png" width="45" height="27" style="width: 45px; height: 27px; position: absolute; left: 0px; top: 0px; z-index: 0; border: 0px;">'+
		                '	</div>'+
		            	'</div>'+
				        '</div>';
						var labelgps = new BMap.Label(html,{offset:new BMap.Size(-140,-145),position:pt});//增加提示
						labelgps.setStyle({});
						var objMrkers = markersVehicle[i];
						markersVehicle[i].addEventListener('mouseover', function(){
				        	objMrkers.setLabel(labelgps);
				        });
						markersVehicle[i].addEventListener("mouseout",function(e){
							map.removeOverlay(labelgps);
						});
				    })();
				}
				var goodsIcon = new BMap.Icon("../image/$tenantId$/map_tb.png", new BMap.Size(33, 42), {  
                    offset: new BMap.Size(33, 42), // 指定定位位置  
                    imageOffset: new BMap.Size(-136,0) // 设置图片偏移  
                });
				var markersGoods = [];
				for(var i=0;i<sfList.length;i++){
					var reqObj=sfList[i];
					var pt = new BMap.Point(reqObj.storeEand, reqObj.storeNand);
					var marker = new BMap.Marker(pt,{icon:goodsIcon});
					markersGoods[i]=marker;
					map.addOverlay(marker);
					(function(){
				        var html='<div class="map_wztis">'+
		                '<div class="map_wzl">'+
		                '	<p class="fl" style="margin: 8px 18px 0 8px;"><img src="'+reqObj.userPicture+'" style=" border-radius:50%;width: 80px;height: 80px;" alt=""/></p>'+
		                '	<p class="fl map_w">'+
		                '		师傅姓名：'+reqObj.name+
		                '		</br>联系手机：'+reqObj.phone+
		                '		<br>服务能力：'+reqObj.serviceType+'<br>'+
		                '	</p>'+
		                '</div>'+
		                '<div style="position: absolute; z-index: 3; left: 152px; top: 116px;">'+
		                '	<div style="position: absolute; overflow: hidden; width: 45px; height: 27px;">'+
		                '		<img src="../image/$tenantId$/1.png" width="45" height="27" style="width: 45px; height: 27px; position: absolute; left: 0px; top: 0px; z-index: 0; border: 0px;">'+
		                '	</div>'+
		            	'</div>'+
				        '</div>';
						var labelgps = new BMap.Label(html,{offset:new BMap.Size(-140,-145),position:pt});//增加提示
						var objMrkers = markersGoods[i];
						markersGoods[i].addEventListener('mouseover', function(){
				        	objMrkers.setLabel(labelgps);
				        });
						markersGoods[i].addEventListener("mouseout",function(e){
							map.removeOverlay(labelgps);
						});
				    })();
				}
		},
		mouseClick:function(e){
			map.clearOverlays();
			var pt = new BMap.Point(e.point.lng, e.point.lat);
			map.centerAndZoom(pt, 8);
			var myIcon = new BMap.Icon("../image/$tenantId$/z.png", new BMap.Size(30,53));
			var marker = new BMap.Marker(pt,{icon:myIcon});  // 创建标注
			var geoc = new BMap.Geocoder();
			var labelgps = null;
			var that=this;
			geoc.getLocation(pt, function(rs){//查询出地址
				var addComp = rs.addressComponents;
				var adder=addComp.province+addComp.city+addComp.district+addComp.street+addComp.streetNumber;
				labelgps = new BMap.Label(adder,{offset:new BMap.Size(22,-22),position:pt});//增加提示
				labelgps.setStyle({
				    fontSize:"12px",    
				    color:"#47321B",    
				    padding:"5px",
				    border:"0"
				});
				marker.addEventListener("mouseover",function(e){
					if(labelgps!=null && labelgps!=undefined && labelgps!=""){
						marker.setLabel(labelgps);
					}
				});
				marker.addEventListener("mouseout",function(e){
					map.removeOverlay(labelgps);
				});
				marker.setLabel(labelgps);
				that.queryAroundInfo(e.point.lng, e.point.lat);
			});
			map.addOverlay(marker);
		},
		openDisSf:function(sfInfo){
			if(sfInfo==null||sfInfo==undefined||sfInfo==''){
				if($("input[name='check-1']:checked").length==0){
					commonService.alert("请至少选择一条任务信息!");
					return false;
				}
				if($("input[name='check-1']:checked").length>1){
					commonService.alert("只能选择一条任务信息!");
					return false;
				}
			}else{
				$scope.sfInfo=sfInfo;
			}
			var taskState=2;
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					if(sfInfo==null||sfInfo==undefined||sfInfo==''){
						$scope.sfInfo.name=data.sfName;
						$scope.sfInfo.phone=data.sfPhone;
						$scope.sfInfo.branchFee=data.branchFee;
						$scope.sfInfo.installFee=data.installFee;
						$scope.sfInfo.sfUserId=data.sfUserId;
						$scope.sfInfo.taskId=data.taskId;
						$scope.sfInfo.wayBillId=data.wayBillId;
					}else{
						$scope.sfInfo.taskId=data.taskId;
						$scope.sfInfo.wayBillId=data.wayBillId;
					}
					taskState=data.orderState;
				}
			});
			if(taskState!=2){
				commonService.alert("运单已分配，无需再分配师傅!");
				return false;
			}
			$scope.branchFee=$scope.sfInfo.branchFee;
			$scope.installFee=$scope.sfInfo.installFee;
			document.getElementById('vehicle_fp').style.display='block';
			document.getElementById('fade1_xz').style.display='block';
		}
	};
	scheTaskManage.init();
}]);
