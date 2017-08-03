var AIscheTaskApp = angular.module("AIscheTaskApp", ['commonApp','tableCommon']);
AIscheTaskApp.controller("AIscheTaskCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var AIscheTask={
		init:function(){
			var wayBillId=getQueryString("wayBillId");
			$scope.des={};
			this.bindEvent();		
			this.statisticsTask();
			if(wayBillId!=null&&wayBillId!=undefined&&wayBillId!=''){
				$scope.query.wayBillId=wayBillId;
			}
		},
		head:[
		      {
		    	  "name":"运单号",
			      "code":"wayBillId",
			      "type":"text",
			      "width":"120"
		      },
		      {
		    	  "name":"服务类型",
			      "code":"serverType",
			      "width":"120"
		      },
		      {
		    	  "name":"发货人",
			      "code":"clientName",
			      "type":"text",
			      "width":"120"
		      },
		      {
		    	  "name":"收货人",
			      "code":"receiverName",
			      "type":"text",
			      "width":"120"
		      },
		      {
		    	  "name":"货品",
			      "code":"products",
			      "width":"120"
		      },
		      {
		    	  "name":"安装件数",
			      "code":"count",
			      "isSum": "true",
			      "width":"120"
		      },
		      {
		    	  "name":"配安费(元)",
			      "code":"branchAndInstall",
			      "isSum": "true",
			      "number": "2",
			      "itemType":"text",
			      "width":"120"
		      },
		      {
		    	  "name":"开单日期",
			      "code":"inputOrdTime",
			      "width":"120"
		      },
		      {
		    	  "name":"目的市",
			      "code":"destCity",
			      "width":"120"
		      },
		      {
		    	  "name":"目的区域",
			      "code":"destCounty",
			      "width":"120"
		      },
		      {
		    	  "name":"体积",
			      "code":"volumes",
			      "isSum": "true",
			      "number": "2",
			      "width":"120"
		      },
		      {
		    	  "name":"重量",
			      "code":"weight",
			      "isSum": "true",
			     "number": "2",
			      "width":"120"
		      },
		      {
		    	  "name":"代收货款(元)",
			      "code":"collectingMoney",
			      "isSum": "true",
			      "number": "2",
			      "width":"120"
		      },
		      {
		    	  "name":"到付(元)",
			      "code":"freightCollect",
			      "isSum": "true",
			      "number": "2",
			      "width":"120"
		      },
		      {
		    	  "name":"干线状态",
			      "code":"gxState",
			      "width":"120"
		      },
		      {
		    	  "name":"干线结束时间",
			      "code":"gxTime",
			      "width":"120"
		      },
		      {
		    	  "name":"到货状态",
			      "code":"dhState",
			      "width":"120"
		      },
		      {
		    	  "name":"到货时间",
			      "code":"gxEndTime",
			      "width":"120"  
		      },
		      {
		    	  "name":"干线结束时间",
			      "code":"gxTime",
			      "width":"120"
		      },
		      {
		    	  "name":"预约上门时间",
			      "code":"yyTime",
			      "width":"120"
		      },
		      {
		    	  "name":"提货地址",
			      "code":"pickAddr",
			      "width":"120"
		      }
		      ],
		bindEvent:function(){
			$scope.head=AIscheTask.head;
			$scope.url="scheTaskBO.ajax?cmd=doAIQuery";
			$scope.param = this.param;
			$scope.query = this.query;
			$scope.doQuery = this.doQuery;
			$scope.clear = this.clear;
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
		    $scope.showGoodsDetail=this.showGoodsDetail;
		    $scope.showOrClose=this.showOrClose;
		    $scope.fixNumber=this.fixNumber;
		    $scope.openWayDetail=this.openWayDetail;
		    $scope.openExc=this.openExc;
		    $scope.arriveGoods=this.arriveGoods;
		    $scope.closeYy=this.closeYy;
		    $scope.openYy=this.openYy;
		    $scope.yy=this.yy;
		    $scope.closeMap=this.closeMap;
		},
		//关闭地图
		closeMap:function(){
			$scope.orderMap=false;
			document.getElementById("fade1_xz").style.display="none";
		},
		//到货
		arriveGoods:function(){
			var taskId="";
			var orderId="";
			var orderIdArr=$scope.table.getSelectData();
			if(orderIdArr.length==0){
				commonService.alert("请至少选择一条任务信息");
				return false;
			}
			var arriveGoodsTime='';
			var trackingNum='';
			for(var i=0;i<orderIdArr.length;i++){
				var data=orderIdArr[i];
				taskId+=data.taskId+",";
				orderId+=data.orderId+",";
				if(arriveGoodsTime==''){
					arriveGoodsTime=data.gxEndTime;
					trackingNum=data.wayBillId;
				}
			}
			if(arriveGoodsTime!=''){
				commonService.alert("运单["+trackingNum+"]到货状态为[已到货]，无需再操作!");
				return false;
			}
			var url="scheTaskBO.ajax?cmd=arriveGoods";
			var param={"orderId":orderId,"taskId":taskId};
			commonService.postUrl(url,param,function(data){
				commonService.alert("操作成功!");
				$scope.doQuery();
			});
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
		//查看详情
		openWayDetail:function(){
			var orderId='';
			var taskId='';
			var ordArray = new Array();
            ordArray=$scope.table.getSelectData();
            if(ordArray.length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			if(ordArray.length>1){
				commonService.alert("只能选择一条任务信息!");
				return false;
			}
			var data= ordArray[0];
			orderId=data.orderId;
			taskId=data.taskId;
			commonService.openTab(taskId+orderId,"任务详情","/sche/task/waybill_details.html?orderId="+orderId+"&taskId="+taskId);

		},
		map:null,
		sfInfo:{
			name:'',
			phone:'',
			taskId:'',
			orderId:'',
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
			isTmall:"-1",
			isGxEnd:"-1",
			isArriveGoods:"-1"
		},
		fixNumber:function (id,name){
			var value=document.getElementById(id+name).value;
			value =  value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
			value =  value.replace(/^\./g,"");  //验证第一个字符是数字而不是. 
			value =  value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.   
			value =  value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
			value=value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');//只能输入两个小数 
			document.getElementById(id+name).value=value;
		},
		//订单流程信息
		showGoodsDetail:function(orderId){
			var url="scheTaskBO.ajax?cmd=goodsDetail";
			var param="orderId="+orderId;
			commonService.postUrl(url,param,function(data){
				$scope.goodsInfo=data.goodsInfo;
			    $(".cplx").hover(function(){
                	 $(this).find("span").show();
                },function(){
            	     $(this).find("span").hide();
                });

			});
		},
		//补录提货信息
		openGxEnd:function(){
			var orderArray=$scope.table.getSelectData();
			var cityName='';
			if(orderArray.length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			for(var i=0;i<orderArray.length;i++){
				var data=orderArray[i];
				if(cityName!=''){
					if(cityName!=data.cityName){
						commonService.alert("所选任务，目的城市不一样,请重新选择!");
						return false;
					}
				}
				cityName=data.cityName;
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
		//分配师傅
		shceDisSf:function(sfInfo){
			var taskId='';
			var orderId='';
			var branchAndInstallFee='';
			var tips="请确认所选择的任务";
			$scope.sfInfo=sfInfo;
			var orderArray=$scope.table.getSelectData();
			if(orderArray.length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			for(var i=0;i<orderArray.length;i++){
				var data=orderArray[i];
				taskId=taskId+data.taskId+",";
				orderId=data.orderId;
				var install=0.0;
				if(data.branchAndInstall!=null&&data.branchAndInstall!=undefined&&data.branchAndInstall!=''){
					install=data.branchAndInstall;
				}
				branchAndInstallFee=branchAndInstallFee+install+",";
				tips+=data.wayBillId;
			}
			tips+="等,是否指派给"+$scope.sfInfo.name+"师傅,费用是否合理！是否确认分配？";
			var param = {"taskId":taskId,"orderId":orderId,"branchAndInstallFee":branchAndInstallFee,"sfUserId":$scope.sfInfo.sfUserId,"userType":$scope.sfInfo.userType};
			commonService.confirm(tips,function(){
			var url = "scheTaskBO.ajax?cmd=disSf";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
					$scope.doQuery();
					$scope.statisticsTask();
					$scope.orderMap=false;
					document.getElementById("fade1_xz").style.display="none";
					commonService.alert("分配成功!");
	 	    	}
			});
		});
		},
		//补录提货信息保存
		gxEnd:function(){
			var orderIdArr =new Array();
			var taskIdArr =new Array();
			var pickUpAddr=$scope.query.pickUpAddr;
			var pickUpPhone=$scope.query.pickUpPhone;
			var pickCode=$scope.query.pickCode;
			var ordArray=$scope.table.getSelectData();
			if(ordArray.length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			for(var i=0;i<ordArray.length;i++){
				var data=ordArray[i];
				orderIdArr.push(data.orderId);
				taskIdArr.push(data.taskId);
			}
			 var param={"orderId":orderIdArr.join(","),"taskId":taskIdArr.join(","),"pickUpPhone":pickUpPhone,"pickUpAddr":pickUpAddr,"pickCode":pickCode};
				var url = "scheTaskBO.ajax?cmd=savePickInfo";
				commonService.postUrl(url,param,function(data){
					if(data!=null && data!=undefined && data!=""){
						commonService.alert("操作成功!");
						$scope.doQuery();
						$scope.closeGxEnd();
		 	    	}
				});
		},
		//查找师傅
		queryMatchSf:function(){
			var taskId='';
			var orderId="";
			var cityName='';
			var isAddress=true;
			var ordArray =$scope.table.getSelectData();
			if(ordArray.length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			$scope.orderMap=true;
			var flag=true;
			for(var i=0;i<ordArray.length;i++){
				var data=ordArray[i];
				taskId=data.taskId;
				orderId+=data.orderId+",";
				if(cityName!=''){
				if(cityName!=data.cityName){
					commonService.alert("所选任务，目的城市不一样,请重新选择!");
					return false;
				}
				}
				cityName=data.cityName;
				if(data.orderState!=2){
					commonService.alert("运单号["+orderId+"]已经分配，无需再分配师傅!");
					return false;
				}
			}
			$scope.orderId=orderId;
			$scope.moreRecommend(taskId,orderId,cityName);
		},
		doQuery:function(){
			$scope.query.provinceId = $scope.des.chooseCityId;
			$scope.query.cityId = $scope.des.chooseRegionId;
			$scope.query.countyId = $scope.des.chooseCountyId;
			var gxEndTime=document.getElementById('gxEndTime').value;
			if(gxEndTime!=null&&gxEndTime!=undefined&&gxEndTime!=''){
				$scope.query.gxEndTime=gxEndTime;
			}
			$scope.query.endTime=document.getElementById('endTime').value;
			$scope.query.beginTime=document.getElementById('beginTime').value;
			var url = "scheTaskBO.ajax?cmd=doAIQuery";
			$scope.table.load();
			$scope.table.callBack=function(){
				displayTable();
				setContentHeight();
			}
			document.getElementById('dhl_map').style.display='none';
			document.getElementById("fade1_xz").style.display="none";
			
		},
		clear:function(){
			$scope.des.clear();
			$scope.query.wayBillId="";
			$scope.query.receiveName='';
			$scope.query.isTmall="-1";
			$scope.query.gxEndTime="";
			document.getElementById('gxEndTime').value="";
			$scope.query.isArriveGoods="-1";
			$scope.query.isGxEnd="-1";
			$scope.query.endTime="";
			$scope.query.beginTime="";
			document.getElementById('endTime').value="";
			document.getElementById('beginTime').value="";
			$scope.table.clearInput();
		},
		//更多推荐
		moreRecommend:function(taskId,orderId,cityName){
			var ff = $("#"+taskId);
			document.getElementById("dhl_map").style.display="block";
			document.getElementById("fade1_xz").style.display="block";
			AIscheTask.mapInit(cityName,taskId);
		},
		mapInit:function(cityName,taskId){
			map = new BMap.Map(allmap);
			map.enableScrollWheelZoom();
			var that=this;
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
					try 
					{ 
						map.setCenter(cityName);
						var myGeo = new BMap.Geocoder();
						myGeo.getPoint(cityName, function(point){
							if (point) {
								that.queryAroundInfo(point.lng,point.lat);
							}
						});
					} 
					catch (e) 
					{ 
					  console.error("地图加载出错:"+cityName);
					} finally{
						map.setCenter(result.name);
						that.queryAroundInfo(result.center.lng,result.center.lat);
					}
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
			setContentHegthDelay();
			var that=this;
			var sfPhone="";
			sfPhone=document.getElementById("sfPhone").value;
			var url="scheTaskBO.ajax?cmd=querySfAndCompany";
			var param = {"orderId":$scope.orderId,"sfPhone":sfPhone};
			commonService.postUrl(url,param,function(data){
				setContentHeigth();
				var receiveInfo = data.receiveInfo;
				var pickInfo = data.pickInfo;
				var sfList=data.sfList;
				$scope.sfList=data.sfList;
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
				for(var i=0;i<pickInfo.length;i++){
					var reqObj=pickInfo[i];
					var pt = new BMap.Point(reqObj.eand, reqObj.nand);
					var marker = new BMap.Marker(pt,{icon:goodsIcon});
					markersGoods[i]=marker;
					map.addOverlay(marker);
					(function(){
				        var html='<div class="map_wztis">'+
		                '<div class="map_wzl">'+
		              //  '	<p class="fl" style="margin: 8px 18px 0 8px;"><img src="'+reqObj.userPicture+'" style=" border-radius:50%;width: 80px;height: 80px;" alt=""/></p>'+
		                '	<p class="fl map_w">'+
		                '		联系手机：'+reqObj.pickPhone+'<br>'+
		                '		货品名称：'+reqObj.product+'<br>'+
		                '		提货地址：'+reqObj.pickAddr+
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
				var sfIcon = new BMap.Icon("../image/$tenantId$/map_tb.png", new BMap.Size(73, 62), {  
					 offset: new BMap.Size(33, 42),
                    imageOffset: new BMap.Size(-170,0)
                });
				var sf = [];
				for(var i=0;i<sfList.length;i++){
					var reqObj=sfList[i];
					var pt = new BMap.Point(reqObj.eand, reqObj.nand);
					var marker = new BMap.Marker(pt,{icon:sfIcon});
					sf[i]=marker;
					map.addOverlay(marker);
					(function(){
				        var html='<div class="map_wztis" style="width:360px;">'+
		                '<div class="map_wzl">'+
		                '	<p class="fl" style="margin: 8px 18px 0 8px;"><img src="'+reqObj.userPicture+'" style=" border-radius:50%;width: 80px;height: 80px;" alt=""/></p>'+
		                '	<div class="fl map_w" style="width:230px;white-space: inherit;">'+
		                '		<p>姓名:'+reqObj.name+
		                '		</p><p>手机:'+reqObj.phone+
		                '		</p><p>服务能力:'+reqObj.serviceType+'</p>'+
		                '	</div>'+
		                '</div>'+
		                '<div style="position: absolute; z-index: 3; left: 152px; top: 116px;">'+
		                '	<div style="position: absolute; overflow: hidden; width: 45px; height: 27px;">'+
		                '		<img src="../image/$tenantId$/1.png" width="45" height="27" style="width: 45px; height: 27px; position: absolute; left: 0px; top: 0px; z-index: 0; border: 0px;">'+
		                '	</div>'+
		            	'</div>'+
				        '</div>';
						var labelgps = new BMap.Label(html,{offset:new BMap.Size(-140,-145),position:pt});
						var objMrkers = sf[i];
						sf[i].addEventListener('mouseover', function(){
				        	objMrkers.setLabel(labelgps);
				        });
						sf[i].addEventListener("mouseout",function(e){
							map.removeOverlay(labelgps);
						});
				    })();
				}
				
			});
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
		//预约
		openYy:function(){
			var ordArray=$scope.table.getSelectData();
			if(ordArray.length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			var trackingNum='';
			var isAddress=true;
			var cityName='';
			var isYy=true;
			for(var i=0;i<ordArray.length;i++){
				var data=ordArray[i];
				if(data.yyTime!=null&&data.yyTime!=undefined&&data.yyTime!=''){
					trackingNum=data.wayBillId;
					commonService.alert("["+trackingNum+"]等运单已操作过预约，无需再操作!");
					return false;
				}
				if(cityName!=''){
					if(cityName!=data.cityName){
						commonService.alert("所选任务，目的城市不一样,请重新选择!");
						return false;
					}
				}
				cityName=data.cityName;
			}
			document.getElementById('vehicle_yy').style.display='block';
			document.getElementById('fade1_xz').style.display='block';
		},
		closeYy:function(){
			document.getElementById('vehicle_yy').style.display='none';
			document.getElementById('fade1_xz').style.display='none';
			document.getElementById('yyTime').value='';
		},
		//预约保存
		yy:function(){
			var orderIdArr =new Array();
			var taskIdArr =new Array();
			var ordArray=$scope.table.getSelectData();
			if(ordArray.length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			for(var i=0;i<ordArray.length;i++){
				var data=ordArray[i];
				orderIdArr.push(data.orderId);
				taskIdArr.push(data.taskId);
				
			}
			var yyTime=document.getElementById('yyTime').value;
			if(yyTime==null||yyTime==undefined||yyTime==''){
				commonService.alert("请选择上门时间!");
				return false;
			}
			var param = {"orderId":orderIdArr.join(","),"taskId":taskIdArr.join(","),"yyTime":yyTime,"ipFixTime":document.getElementById('ipFixTime').value};
			var url = "scheTaskBO.ajax?cmd=yy";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
					$scope.closeYy();
					commonService.alert("预约成功!");
					$scope.doQuery();
					$scope.statisticsTask();
	 	    	}
			});
		},
	};
	AIscheTask.init();
}]);
