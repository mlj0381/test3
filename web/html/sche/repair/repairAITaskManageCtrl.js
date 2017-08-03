var repairAITaskManageApp = angular.module("repairAITaskManageApp", ['commonApp']);
repairAITaskManageApp.controller("repairAITaskManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var repairAITaskManage={
			init:function(){
				$scope.des={};
				this.bindEvent();
				this.doQuery();
				this.statisticsTask();
			},
			bindEvent:function(){
				$scope.param = this.param;
				$scope.query = this.query;
				$scope.doQuery = this.doQuery;
			    $scope.clear = this.clear;
			    $scope.selectAll = this.selectAll;
			    $scope.selectOne = this.selectOne;
			    $scope.moreRecommend=this.moreRecommend;
			    $scope.mapInit=this.mapInit;
			    $scope.setMapCity=this.setMapCity;
			    $scope.queryAroundInfo=this.queryAroundInfo;
			    $scope.mouseClick=this.mouseClick;
			    $scope.queryMatchSf=this.queryMatchSf;
			    $scope.statisticsTask=this.statisticsTask;
			    $scope.shceDisSf=this.shceDisSf;
			    $scope.map=this.map;
			    $scope.sfInfo=this.sfInfo;
			    $scope.showGoodsDetail=this.showGoodsDetail;
			    $scope.showOrClose=this.showOrClose;
			    $scope.fixNumber=this.fixNumber;
			    $scope.openExc=this.openExc;
			    $scope.dealExc=this.dealExc;
			    
			},
			dealExc:function(){
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
				commonService.openTab("exc"+excId,"任务详情","/sche/exc/excRegister.html?isShow=2"+"&excId="+excId+"&isView=1");
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
				queryType:2,
				isTmall:"-1"
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
			statisticsTask:function(){
				var param = {};
				var url = "repairScheTaskBO.ajax?cmd=statisticsTaskCount";
				commonService.postUrl(url,param,function(data){
					if(data!=null && data!=undefined && data!=""){
							$scope.statisticsTaskCount=data;
		 	    	}
				});
			},
			shceDisSf:function(sfInfo){
				var taskId='';
				var wayBillId='';
				var repairFee='';
				var orderId='';
				var tips="请确认所选择的维修任务[";
				$scope.sfInfo=sfInfo;
				if($("input[name='check-1']:checked").length==0){
					commonService.alert("请至少选择一条任务信息!");
					return false;
				}
				$("input[name='check-1']:checked").each(function(){
					if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
						var data=eval("("+$(this).val()+")");
						taskId=taskId+data.taskId+",";
						wayBillId=data.wayBillId;
						orderId=data.orderId;
						repairFee=repairFee+document.getElementById(data.taskId+"repair").value+",";
						tips+=wayBillId;
					}
				});
				tips+="]等任务,是否指派给"+$scope.sfInfo.name+"师傅,确认费用是否合理！是否确认分配？";
				var param = {"taskId":taskId,"orderId":orderId,"repairFee":repairFee,"sfUserId":$scope.sfInfo.sfUserId,"userType":$scope.sfInfo.userType,"scheType":1};
					commonService.confirm(tips,function(){
					var url = "scheTaskBO.ajax?cmd=disSf";
					commonService.postUrl(url,param,function(data){
						if(data!=null && data!=undefined && data!=""){
							$scope.doQuery();
							$scope.statisticsTask();
							commonService.alert("分配成功!");
							document.getElementById("dhl_map").style.display="none";
			 	    	}
					});
				});
			},
			queryMatchSf:function(){
				var taskId='';
				var wayBillId='';
				var orderId='';
				var cityName='';
				var isAddress=true;
				if($("input[name='check-1']:checked").length==0){
					commonService.alert("请至少选择一条任务信息!");
					return false;
				}
				var flag=true;
				$("input[name='check-1']:checked").each(function(){
					if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
						var data=eval("("+$(this).val()+")");
						taskId=data.taskId;
						orderId=orderId+data.orderId+",";
						wayBillId=wayBillId+data.wayBillId+",";
						if(cityName!=''){
							if(cityName!=data.cityName){
								isAddress=false;
								return false;
							}
						}
						cityName=data.cityName;
						if(data.taskState!=2){
							flag=false;
							return false;
						}
					}
				});
//				if(!isAddress){
//					commonService.alert("所选任务，目的城市不一样,请重新选择!");
//					return false;
//				}
				if(!flag){
					commonService.alert("运单号["+wayBillId+"]已经分配，无需再分配师傅!");
					return false;
				}
				$scope.orderId=orderId;
				$scope.moreRecommend(taskId,wayBillId,cityName);
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
				var url = "repairScheTaskBO.ajax?cmd=doTaskQuery";
				$timeout(function(){
					$scope.page.load({
								url:url,
								params:$scope.query,								
								callBack:'setContentHeigth'
							});
				},500);
				document.getElementById('dhl_map').style.display='none';
			},
			clear:function(){
				//$scope.query.receivePhone="";
				$scope.query.receiveName="";
				$scope.query.clientName="";
				$scope.query.wayBillId="";
				$scope.query.isTmall="-1";
				$scope.des.clear();
				
			},
			selectOne : function(taskId){
				if(document.getElementById("checkbox"+taskId).checked && document.getElementById("checkbox"+taskId) != undefined){
					document.getElementById("checkbox"+taskId).checked=false;
				}else{
					document.getElementById("checkbox"+taskId).checked=true;
				}
			},
			//更多推荐
			moreRecommend:function(taskId,wayBillId,cityName){
					if(document.getElementById("dhl_map").style.display=='none'){
						var ff = $("#"+taskId);
						var hh = ff.position().top+20+"px";		
						$("#dhl_map").css("position","absolute");
						$("#dhl_map").css("top",hh);
						document.getElementById("dhl_map").style.display="block";
					}
					repairAITaskManage.mapInit(cityName,taskId);
			},
			mapInit:function(cityName,taskId){
				map = new BMap.Map(allmap);
				map.enableScrollWheelZoom();
				var that=this;
				//地图加载完毕
//				map.addEventListener("tilesloaded",function(){
//					that.loadClose();
//				});
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
				var that=this;
				var url="scheTaskBO.ajax?cmd=querySfAndCompany";
				var sfPhone="";
				sfPhone=document.getElementById("sfPhone").value;
				var param = {"isExceSche":1,"orderId":$scope.orderId,"sfPhone":sfPhone};
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
	repairAITaskManage.init();
}]);
