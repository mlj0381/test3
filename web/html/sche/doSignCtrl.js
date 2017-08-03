var doSignApp = angular.module("doSignApp", ['commonApp','tableCommon']);
doSignApp.controller("doSignCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var doSign={
		init:function(){
			$scope.des={};
			this.bindEvent();
			this.statisticsTask();
		},
		head:[
		      {
		    	  "name":"运单号",
		    	  "code":"wayBillId",
		    	  "width":"100",
		    	  "type":"text"
		      },
		      {
		    	  "name":"服务类型",
		    	  "code":"serverType",
		    	  "width":"100"
		      },
		      {
		    	  "name":"发货人",
		    	  "code":"clientName",
		    	  "width":"100",
		    	  "type":"text"
		      },
		      {
		    	  "name":"收货人",
		    	  "code":"receiverName",
		    	  "width":"100",
		    	  "type":"text"
		      },
		      {
		    	  "name":"货品",
		    	  "code":"products",
		    	  "width":"100"
		      },
		      {
		    	  "name":"安装件数",
		    	  "code":"count",
		    	  "width":"100",
		    	  "isSum":"true"
		  
		      },
		      {
		    	  "name":"体积",
		    	  "code":"volumes",
		    	  "width":"100",
		    	  "isSum":"true",
		    	  "number":"2"
		      },
		      
		      {
		    	  "name":"重量",
		    	  "code":"weight",
		    	  "width":"100",
		    	  "isSum":"true",
		    	  "number":"2"
		      },
		      {
		    	  "name":"开单日期",
		    	  "code":"inputOrdTime",
		    	  "width":"120"
		      },
		      {
		    	  "name":"目的市",
		    	  "code":"destCity",
		    	  "width":"100"
		      },
		      {
		    	  "name":"目的区域",
		    	  "code":"destCounty",
		    	  "width":"100"
		      },
		      {
		    	  "name":"详细地址",
		    	  "code":"receAddr",
		    	  "width":"200"
		      },
		      {
		    	  "name":"师傅",
		    	  "code":"sfName",
		    	  "width":"100",
		    	  "type":"text"
		      },
		      {
		    	  "name":"师傅帐号",
		    	  "code":"sfPhone",
		    	  "width":"100",
		    	  "type":"text"
		      },
		      {
		    	  "name":"配安费(元)",
		    	  "code":"branchAndInstall",
		    	  "width":"100",
		    	  "isSum":"true",
		    	  "number":"2"
		      },
		      {
		    	  "name":"签收状态",
		    	  "code":"signStateName",
		    	  "width":"100"
		      },
		      {
		    	  "name":"代收货款(元)",
		    	  "code":"collectingMoney",
		    	  "width":"100",
		    	  "isSum":"true",
		    	  "number":"2"
		      },
		      {
		    	  "name":"到付(元)",
		    	  "code":"freightCollect",
		    	  "width":"100",
		    	  "isSum":"true",
		    	  "number":"2"
		      },
		      {
		    	  "name":"到货时间",
		    	  "code":"gxEndTime",
		    	  "width":"130"
		      },
		      {
		    	  "name":"接单时间",
		    	  "code":"acceptTime",
		    	  "width":"130"
		      },
		      {
		    	  "name":"提货时间",
		    	  "code":"pickTime",
		    	  "width":"130"
		      },
		      {
		    	  "name":"预约时间",
		    	  "code":"upFixTime",
		    	  "width":"130"
		      },
		      {
		    	  "name":"上门时间",
		    	  "code":"yyTime",
		    	  "width":"130"
		      },
		      {
		    	  "name":"签收时间",
		    	  "code":"signTime",
		    	  "width":"130"
		      }
		      ],
		bindEvent:function(){
			$scope.head=doSign.head;
			$scope.url="scheTaskBO.ajax?cmd=doTaskQuery";
			$scope.param = this.param;
			$scope.query = this.query;
			$scope.doQuery = this.doQuery;
		    $scope.clear = this.clear;
		    $scope.statisticsTask=this.statisticsTask;
		    $scope.closeSign=this.closeSign;
		    $scope.openSign=this.openSign;
		    $scope.showCredit=this.showCredit;
		    $scope.showGoodsDetail=this.showGoodsDetail;
		    $scope.starOn=this.starOn;
		    $scope.showOrClose=this.showOrClose;
		    $scope.openWayDetail=this.openWayDetail;
		    $scope.doSign=this.doSign;
		    $scope.moreRecommend=this.moreRecommend;
		    $scope.mapInit=this.mapInit;
		    $scope.setMapCity=this.setMapCity;
		    $scope.queryAroundInfo=this.queryAroundInfo;
		    $scope.shceDisSf=this.shceDisSf;
		    $scope.sfInfo=this.sfInfo;
		    $scope.queryMatchSf=this.queryMatchSf;
		    $scope.mouseClick=this.mouseClick;
		    $scope.map=this.map;
		    $scope.updateSfAndFee=this.updateSfAndFee;
		    $scope.fixNumber=this.fixNumber;
		    $scope.closeMap=this.closeMap;
		},
		//关闭地图
		closeMap:function(){
			$scope.orderMap=false;
			document.getElementById("fade1_xz").style.display="none";
		},
		mao:{},
		fixNumber:function (id){
			var value=document.getElementById(id).value;
			value =  value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
			value =  value.replace(/^\./g,"");  //验证第一个字符是数字而不是. 
			value =  value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.   
			value =  value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
			value=value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');//只能输入两个小数 
			document.getElementById(id).value=value;
		},
		sfInfo:{
			name:'',
			phone:'',
			taskId:'',
			orderId:'',
			sfUserId:'',
			installFee:'',
			branchFee:''
		},
		updateSfAndFee:function(){
			var taskId='';
			var orderId='';
			var trackingNum='';
			var branchAndInstallFee='';
			var sfName='';
			var ordArray=$scope.table.getSelectData();
			if(ordArray.length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			for(var i=0;i<ordArray.length;i++){
				var data=ordArray[i];
				taskId=taskId+data.taskId;
				orderId=data.orderId;
				trackingNum=data.wayBillId;
				branchAndInstallFee=data.branchAndInstall;
				sfName=data.sfName;
			}
			var tips="是否将运单号["+trackingNum+"]的配安任务，原师傅["+sfName+"]更改为[";
			tips+=$scope.sfInfo.name+"]师傅,是否确认更改？";
			var param = {"taskId":taskId,"orderId":orderId,"sfUserId":$scope.sfInfo.sfUserId,"remark":$scope.sfInfo.remark};
				commonService.confirm(tips,function(){
				var url = "scheTaskBO.ajax?cmd=doModifySfAndFee";
				commonService.postUrl(url,param,function(data){
					if(data!=null && data!=undefined && data!=""){
						$scope.doQuery();
						document.getElementById("dhl_map").style.display="none";
						document.getElementById("updateSf").style.display="none";
						document.getElementById("fade1_xz").style.display="none";
						commonService.alert("更改成功!");
		 	    	}
				});
			});
		},
		//变更师傅
		queryMatchSf:function(){
			var ordArray=$scope.table.getSelectData();
			if(ordArray.length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			if(ordArray.length>1){
				commonService.alert("只能选择一条记录!");
				return false;
			}
			$scope.orderMap=true;
			var taskId='';
			var orderId='';
			var cityName='';
			var data=ordArray[0];
			taskId=data.taskId;
			$scope.sfUserId=data.sfUserId;
			orderId=data.orderId;
			$scope.branchFee=data.branchAndInstall;
			$scope.orderId=orderId;
			$scope.moreRecommend(taskId,orderId,cityName);
		},
		shceDisSf:function(sfInfo){
			$scope.sfInfo=sfInfo;
			$scope.sfInfo.fee=$scope.branchFee;
			document.getElementById("updateSf").style.display="block";
			document.getElementById("fade1_xz").style.display="block";
		},
		//查看详情
		openWayDetail:function(){
			var orderId='';
			var taskId='';
			var ordArray=$scope.table.getSelectData();
			if(ordArray.length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			if(ordArray.length>1){
				commonService.alert("只能选择一条任务信息!");
				return false;
			}
			var data=ordArray[0];
			orderId=data.orderId;
			taskId=data.taskId;
			commonService.openTab(taskId+orderId,"任务详情","/sche/task/waybill_details.html?orderId="+orderId+"&taskId="+taskId);
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
			taskState:8
		},
		query:{
			provinceId:-1,
			taskState:8,
			isTmall:"-1"
		},
		//订单流程信息
		showGoodsDetail:function(orderId){
			var url="scheTaskBO.ajax?cmd=goodsDetail";
			var param="orderId="+orderId;
			var html="";
			commonService.postUrl(url,param,function(data){
				setContentHeigth();
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
		openSign:function(){
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			if($("input[name='check-1']:checked").length>1){
				commonService.alert("只能选择一条任务信息!");
				return false;
			}
			var isOk=true;
			var taskStateName='';
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					if(data.orderState!=7){
						isOk=false;
						taskStateName=data.orderStateName;
					}
				}
			});
			if(!isOk){
				commonService.alert("任务状态为"+taskStateName+",不允许操作签收!");
				return false;
			}
			document.getElementById('vehicle_qs').style.display='block';
			document.getElementById('fade1_xz').style.display='block';
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
		doQuery:function(){
			$scope.query.provinceId = $scope.des.chooseCityId;
			$scope.query.cityId = $scope.des.chooseRegionId;
			$scope.query.countyId = $scope.des.chooseCountyId;
			if($scope.query.orderState>0){
				$scope.query.taskState=$scope.query.orderState;
			}
			$scope.query.endTime=document.getElementById('endTime').value;
			$scope.query.beginTime=document.getElementById('beginTime').value;
			var url = "scheTaskBO.ajax?cmd=doTaskQuery";
			$scope.table.load();
			$scope.table.callBack=function(){
				displayTable();
				setContentHeight();
			} 
			document.getElementById('dhl_map').style.display='none';
			document.getElementById("fade1_xz").style.display="none";
		},
		clear:function(){
			$scope.query.sfPhone="";
			$scope.query.sfName="";
			$scope.query.receiveName="";
			$scope.query.clientName="";
			$scope.query.signState="-1";
			$scope.query.wayBillId="";
			$scope.query.isTmall="-1";
			$scope.des.clear();
			document.getElementById('endTime').value="";
			document.getElementById('beginTime').value="";
			$scope.table.clearInput();
			
		},
		//更多推荐
		moreRecommend:function(taskId,orderId,cityName){
					var ff = $("#"+taskId);
					document.getElementById("dhl_map").style.display="block";
					document.getElementById("fade1_xz").style.display="block";
					doSign.mapInit(cityName,taskId);
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
		}
	};
	doSign.init();
}]);
