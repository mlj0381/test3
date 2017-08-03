/**
 * 地图定位
 * 
 */
commonApp.directive('myMap',['commonService',function(commonService) {
	var myMap={};
	var bMap=null;
	myMap.restrict="A";
	myMap.replace=true;
	myMap.compile=function(element, attrs){
		var objName=attrs.myMap;
		var isZxInfo = attrs.iszxinfo;  //专线公司专属标签
		isZxInfo=isZxInfo=="true"?true:false;
		var map={
			vehicleCode:"",
			dataList:{},
			num:"",
			statDate:"",
			endDate:"",
			isMonth:0,
			flag:"",
			xStart:"",
			sStart:"",
			palteId:"",
			openMap:function(vehicleCode,flag){
				var oldjs = document.getElementById("mapJs");
				if(oldjs==null || oldjs==undefined){
					var time = (new Date).getTime();
					var scriptObj = document.createElement("script");
					scriptObj.src = "http://api.map.baidu.com/getscript?v=2.0&ak=36gqbKALpcnbkp0utVF7gS30&services=&t="+time;
					scriptObj.type = "text/javascript";
					scriptObj.id = "mapJs";
					document.getElementsByTagName("head")[0].appendChild(scriptObj);
					if (!/*@cc_on!@*/0) { 
							//if not IE
			        //Firefox2、Firefox3、Safari3.1+、Opera9.6+ support js.onload
			        scriptObj.onload = function () {
			        	// map.mapInit();
			        	  bMap = new BMap.Map("allmap");    // 创建Map实例
			        	    bMap.centerAndZoom(new BMap.Point(116.404, 39.915), 11);  // 初始化地图,设置中心点坐标和地图级别
//			        	  /  bMap.addControl(new BMap.MapTypeControl());   //添加地图类型控件
			        	    bMap.setCurrentCity("北京");          // 设置地图显示的城市 此项是必须设置的
			        	    bMap.enableScrollWheelZoom(true); 
			        	    var oldScript = document.getElementById("oldScript");
			        	    if(oldScript==null || oldScript==undefined){
			        	    	 var Objscript = document.createElement("script");
			 					Objscript.src = "../js/LuShu_min.js";
			 					Objscript.type = "text/javascript";
			 					Objscript.id = "oldScript";//开启鼠标滚轮缩放
			 	        	    document.getElementsByTagName("head")[0].appendChild(Objscript);
			 	        	   map.baiDuApiInit(vehicleCode,flag);
			        	    }
			        };
			       
			    } else {
			        //IE6、IE7 support js.onreadystatechange
			        scriptObj.onreadystatechange = function () {
			            if (scriptObj.readyState == 'loaded' || scriptObj.readyState == 'complete') {
			             //map.mapInit();
			            	  bMap = new BMap.Map("allmap");    // 创建Map实例
				        	    bMap.centerAndZoom(new BMap.Point(116.404, 39.915), 11);  // 初始化地图,设置中心点坐标和地图级别
//				        	  /  bMap.addControl(new BMap.MapTypeControl());   //添加地图类型控件
				        	    bMap.setCurrentCity("北京");          // 设置地图显示的城市 此项是必须设置的
				        	    bMap.enableScrollWheelZoom(true); 
				        	    var oldScript = document.getElementById("oldScript");
				        	    if(oldScript==null || oldScript==undefined){
				        	    	 var Objscript = document.createElement("script");
				 					Objscript.src = "../js/LuShu_min.js";
				 					Objscript.type = "text/javascript";
				 					Objscript.id = "oldScript";//开启鼠标滚轮缩放
				 	        	    document.getElementsByTagName("head")[0].appendChild(Objscript);
				 	        	   map.baiDuApiInit(vehicleCode,flag);
				        	    }
			            }
			        }
			    }
		      }else{
		    	  map.baiDuApiInit(vehicleCode,flag);
		      }
				  
			},
			/*mapInit:function(){
	        	    bMap = new BMap.Map("allmap");    // 创建Map实例
	        	    bMap.centerAndZoom(new BMap.Point(116.404, 39.915), 11);  // 初始化地图,设置中心点坐标和地图级别
//	        	  /  bMap.addControl(new BMap.MapTypeControl());   //添加地图类型控件
	        	    bMap.setCurrentCity("北京");          // 设置地图显示的城市 此项是必须设置的
	        	    bMap.enableScrollWheelZoom(true); 
	        	    var oldScript = document.getElementById("oldScript");
	        	    if(oldScript==null || oldScript==undefined){
	        	    	 var Objscript = document.createElement("script");
	 					Objscript.src = "../js/LuShu_min.js";
	 					Objscript.type = "text/javascript";
	 					Objscript.id = "oldScript";//开启鼠标滚轮缩放
	 	        	    document.getElementsByTagName("head")[0].appendChild(Objscript);
	        	    }
	        	   
				},*/
			baiDuApiInit:function(vehicleCode,flag){
				 //   map.baiDuApiInit();
				    
					document.getElementById(objName+"MapDev").style.display="block";
					document.getElementById(objName+"MapBj").style.display="block";
					if(vehicleCode!=null && vehicleCode!=undefined && vehicleCode!=""){
						this.vehicleCode=vehicleCode;
					if(flag!=null && flag!=undefined && flag!="" && flag=="0"){
							var url="logisticsTrack.ajax?cmd=queryCarLogis&vehicleCode="+vehicleCode;
							commonService.postUrl(url,"", function(data) {
								if(data!=null && data!=undefined && data!=""){
										map.getLocation(data.eandw,data.nands,data.plateNumber,data.contactNumber);
								}else{
									map.cleatLocation();
									commonService.alert("没有定位信息！");
								}
							});
							document.getElementById(objName+"Con").style.display="none";
						}else{
							document.getElementById(objName+"Con").style.display="block";
							map.palteId=vehicleCode;
							map.selectDate(objName+"Tag1",1);
						}
					}
					
				
			},
			closeMap:function(){
				document.getElementById(objName+"MapDev").style.display="none";
				document.getElementById(objName+"MapBj").style.display="none";
			},
			/*formatDate : function (date) {
			    var myyear = date.getFullYear();     
			    var mymonth = date.getMonth()+1;     
			    var myweekday = date.getDate();      
			         
			    if(mymonth < 10){     
			        mymonth = "0" + mymonth;     
			    }      
			    if(myweekday < 10){     
			        myweekday = "0" + myweekday;     
			    }     
			    return (myyear+"-"+mymonth + "-" + myweekday);      
			},*/
			dateAdd:function (dd,n){
				var strs= new Array(); 
				strs = dd.split("-");
				var y = strs[0];
				var m = strs[1];
				var d = strs[2];
				var t = new Date(y,m-1,d);
				var str = t.getTime()+n*(1000*60*60*24);
				var newdate = new Date();
				newdate.setTime(str);
				var strYear=newdate.getFullYear();   
				var strDay=newdate.getDate();
				if(strDay < 10){
					strDay = "0"+strDay;
				}   
				var strMonth=newdate.getMonth()+1;
				if(strMonth < 10){   
				    strMonth = "0"+strMonth;   
				}   
				var strdate=strYear+"-"+strMonth+"-"+strDay;   
				return strdate;
			},
			selectDate:function(sobj,value){
				var tag = document.getElementById(objName+"Tags").getElementsByTagName("li");
				var tagLength = tag.length;
				for(var i=0;i<tagLength;i++){
					tag[i].className="";
				}
				document.getElementById(sobj).className="selectTag";
				this.num=value;
				if(value=="1"){
					this.tenDays="dhl_track_default mr10";
					this.oneMonth="dhl_track_default mr10";
					if(this.isSameDay){
						this.isSameDay=false;
						this.sameDay="dhl_track_default mr10";
						//this.num=0;
					}else{
						this.isSameDay=true;
						this.sameDay="dhl_track_current mr10";
					}
					this.isTenDays=false;
					this.isOneMonth=false;
					this.queryLocation();
				}else if(value=="2"){
					this.sameDay="dhl_track_default mr10";
					this.oneMonth="dhl_track_default mr10";
					this.isSameDay=false;
					if(this.isTenDays){
						this.isTenDays=false;
						this.tenDays="dhl_track_default mr10";
						//this.num=0;
					}else{
						this.isTenDays=true;
						this.tenDays="dhl_track_current mr10";
					}
					this.isOneMonth=false;
					this.queryLocation();
				}else if(value=="3"){
					this.sameDay="dhl_track_default mr10";
					this.tenDays="dhl_track_default mr10";
					this.isSameDay=false;
					this.isTenDays=false;
					if(this.isOneMonth){
						this.isOneMonth=false;
						this.oneMonth="dhl_track_default mr10";
						//this.goodsInfo.date=0;
					}else{
						this.isOneMonth=true;
						this.oneMonth="dhl_track_current mr10";
					}
					this.queryLocation();
				}else{
					
				};
			},
			
			 getLocation:function(eandw, nands,plateNumber,contactNumber) {
				    bMap = new BMap.Map("allmap");
					var point = new BMap.Point(eandw,nands);
					//bMap.addControl(new BMap.NavigationControl());
					
					bMap.centerAndZoom(point,15);
					var gc = new BMap.Geocoder();
					gc.getLocation(point, function(rs){
					 	var addComp = rs.addressComponents;
						var markergps = new BMap.Marker(point);
						var values="随车手机："+contactNumber+"<br>车牌号码："+plateNumber+"<br>";
						values+="地址："+addComp.province + addComp.city + addComp.district + addComp.street +  addComp.streetNumber;
						var labelgps = new BMap.Label(values,{offset:new BMap.Size(20,-10),position:point});
						labelgps.setStyle({ //给label设置样式，任意的CSS都是可以的
						    fontSize:"12px",
						    backgroundColor:"#F7BD39",    
						    color:"#47321B",    
						    padding:"5px",
						    border:"0"
						});
						markergps.setLabel(labelgps); //添加GPS标注
						bMap.addOverlay(markergps); //添加GPS标注
					});
				},
				 cleatLocation:function(){
						if(bMap == null){
							bMap = new BMap.Map("allmap");
						}
						bMap.centerAndZoom(new BMap.Point(116.404, 39.915), 11); // 初始化地图,设置中心点坐标和地图级别
						bMap.addControl(new BMap.NavigationControl()); // 添加平移缩放控件
						//bMap.addControl(new BMap.MapTypeControl({mapTypes:[] }));
						
						bMap.clearOverlays();
						bMap = null;
					},
			queryLocation:function(){
				/*bMap = new BMap.Map("allmap");
				bMap.clearOverlays();*/
				var url="monitor.ajax?cmd=queryLocation&vehicleCode="+encodeURI(map.palteId)+"&date="+this.num;
				commonService.postUrl(url,"",function(data){
					if(data.err!=null && data.err!=undefined && data.err!=""){
						commonService.alert(data.err);
						return false;
					}
					
					if(data.date=="" || data.date=="0"){
						//当前位置
						var pt = new BMap.Point(data.LNG, data.LAT);
						bMap.centerAndZoom(pt, 18);
						var myIcon = new BMap.Icon("../image/map_dq.png", new BMap.Size(78,52));
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
								bMap.removeOverlay(labelgps);
							});
							marker.setLabel(labelgps);
						});
						bMap.addOverlay(marker);
						marker.setAnimation(BMAP_ANIMATION_DROP ); //跳动的动画
					}else{
						bMap = new BMap.Map("allmap");
						//轨迹
						var pts = [];
						if(data.reList!=null && data.reList!=undefined && data.reList.length>0){
							for(var i=0;i<data.reList.length;i++){
								pts[i] = new BMap.Point(data.reList[i][0],data.reList[i][1]);
							}
						}
						
						bMap.centerAndZoom(pts[0], 12);
						var myIcon = new BMap.Icon("../image/map_tb.png",new BMap.Size(33, 42),{
							offset: new BMap.Size(33, 42),  
							imageOffset: new BMap.Size(-60,0)  
						});    
						var myIcon2 = new BMap.Icon("../image/map_tb.png",new BMap.Size(33, 42),{
							offset: new BMap.Size(33, 42),  
							imageOffset: new BMap.Size(-30, 0)  
						});
						var marker = new BMap.Marker(pts[0], {icon: myIcon});  //添加起标志
						var marker2 = new BMap.Marker(pts[(pts.length-1)], {icon: myIcon2});  //添加终标志
						bMap.addOverlay(marker);
						bMap.addOverlay(marker2);
				    	var polyline = new BMap.Polyline(pts,{strokeColor:"#48b852",strokeWeight:5, strokeOpacity:1});
				    	bMap.addOverlay(polyline);
				    	var lushu = new BMapLib.LuShu(bMap,pts,{
				            defaultContent:"",
				            autoView:true,//是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
				            icon  : new BMap.Icon('../image/map_car.png', new BMap.Size(56,21),{anchor : new BMap.Size(27, 13)}),
				            speed: 500,
				            enableRotation:true,//是否设置marker随着道路的走向进行旋转
				            landmarkPois: [ ]
				    	});
				    	lushu.start();
					}
					//
//					/$scope.loadClose();
				});
			},
			getDateMonthSub:function (startDate, endDate){
				startDate=new Date(startDate.replace(/-/g,'/'));
				endDate=new Date(endDate.replace(/-/g,'/'));
				var num=0;
				var year=endDate.getFullYear()-startDate.getFullYear();
				num+=year*12;
				var month=endDate.getMonth()-startDate.getMonth();
				num+=month;
				var day=endDate.getDate()-startDate.getDate();
				if(day>0){
				//if(day>15){ num+=1; }
				num+=1;
				}else if(day<0){
				//if(day<-15){num-=1; }
				//num-=1;
				}
				return num;
			},
			queryCarLogis:function (vehicleCode,flag,statDate,endDate,start,isMonth){
				var tht=this;
				var url="logisticsTrack.ajax?cmd=queryCarLogis&vehicleCode="+vehicleCode+"&flag="
				+flag+"&statDate="+statDate+"&endDate="+endDate+"&start="+start+"&isMonth="+isMonth;
				commonService.postUrl(url,"", function(data) {
					if(data.totalNum>0){
						tht.dataList=data.items;
					}else{
						eval(objName+"Iframepage.cleatLocation()");
						alert("没有定位信息！");
						tht.dataList={};
					}
				});
			},
			
			
			
			pointsIntoWindows :function (eandw,nands,titleInfo,address) {
				if(bMap == null){
					bMap = new BMap.Map("allmap");
				}
				var centerPoint = new BMap.Point(eandw,nands); 
				bMap.centerAndZoom(centerPoint, 15);//地图中心 将第一个点放在地图中心
				var opts = {
						width : 200,     // 信息窗口宽度
						height: 50,     // 信息窗口高度
						title : " " , // 信息窗口标题
						enableMessage:true,//设置允许信息窗发送短息
				}
				var point = new BMap.Point(eandw,nands); 
				var marker = new BMap.Marker(point);  // 创建标注
				map.addOverlay(marker);              // 将标注添加到地图中
				var infoWindow = new BMap.InfoWindow(address, opts);  // 创建信息窗口对象 
				marker.addEventListener("click", function(){        
					bMap.openInfoWindow(infoWindow,point); //开启信息窗口
				});
				
				var label = new BMap.Label(titleInfo,{offset:new BMap.Size(20,-10)});
				  label.setStyle({ 
				     display:"none" //给label设置样式，任意的CSS都是可以的
				  });
				  
				  marker.setLabel(label);
				  
				  marker.addEventListener("mouseover", function(){ 
				    label.setStyle({  //给label设置样式，任意的CSS都是可以的
				      display:"block"
				    });
				        
				  }); 
				  marker.addEventListener("mouseout", function(){ 
					  label.setStyle({  //给label设置样式，任意的CSS都是可以的
					    display:"none"
					  }); 
					});
			},
			
			
			
			//var map=null;
			arrayPoint:function (eandw,nands,address,info){
				bMap=null;
				if(bMap == null){
					bMap = new BMap.Map("allmap");
					var centerPoint = new BMap.Point(eandw,nands); 
					bMap.centerAndZoom(centerPoint, 15);//地图中心 将第一个点放在地图中心
				}
				var opts = {
						width : 200,     // 信息窗口宽度
						height: 50,     // 信息窗口高度
						title : " " , // 信息窗口标题
						enableMessage:true,//设置允许信息窗发送短息
				}
				var point = new BMap.Point(eandw,nands); 
				var marker = new BMap.Marker(point);  // 创建标注
				bMap.addOverlay(marker);              // 将标注添加到地图中
				var infoWindow = new BMap.InfoWindow(address, opts);  // 创建信息窗口对象 
				marker.addEventListener("click", function(){        
					bMap.openInfoWindow(infoWindow,point); //开启信息窗口
				});
				

				var label = new BMap.Label(info,{offset:new BMap.Size(20,-10)});
				  label.setStyle({ 
				     display:"none" //给label设置样式，任意的CSS都是可以的
				  });
				  
				  marker.setLabel(label);
				  
				  marker.addEventListener("mouseover", function(){ 
				    label.setStyle({  //给label设置样式，任意的CSS都是可以的
				      display:"block"
				    });
				        
				  }); 
				  marker.addEventListener("mouseout", function(){ 
					  label.setStyle({  //给label设置样式，任意的CSS都是可以的
					    display:"none"
					  }); 
					});
			},
			
			
			zxDrawPoint:function(eandw,nands,address,info){
				console.log(22222);
				map.arrayPoint(eandw,nands,address,info);
			},
			zxCleatLocation:function(){
				map.cleatLocation();
			},
			zxDrawOnePoint:function(eandw,nands,titleInfo,address){
				map.arrayPoint(eandw,nands,titleInfo,address);
			},
		};
		var html=
			'<div id="'+objName+'MapDev" class="white_content_xz8">';
	if(isZxInfo){
		html+= '	<div class="map_cl_zlglx" id="zxDiv" style="overflow: hidden;height: 450px">';
	}else{
		html+=	'	<h2 id="titleName" >'+
		'		<span>地图展示</span>'+
		'		<a href="javascript:void(0)" ng-click="'+objName+'.closeMap()">x</a>'+
		'	</h2>'+
		'	<div class="map_cl_zlglx" id="zxDiv" style="overflow: hidden;">';
	}	
	html+=	' 		<div id="'+objName+'Con" style="display: none;margin-top:10px;left: 620px;" class="map_con">'+
			'         <ul id="'+objName+'Tags" class="map_tags">'+
			'     		<li class="selectTag"  id="'+objName+'Tag1"><a href="javascript:void(0);" ng-class="sameDay" ng-click="'+objName+'.selectDate(\''+objName+'Tag1\',1)">当天</a> </li>'+
			'     		<li  id="'+objName+'Tag2"><a href="javascript:void(0);" ng-class="tenDays" ng-click="'+objName+'.selectDate(\''+objName+'Tag2\',2)">10天</a> </li>'+
			'     		<li  id="'+objName+'Tag3"><a href="javascript:void(0);" ng-class="oneMonth" ng-click="'+objName+'.selectDate(\''+objName+'Tag3\',3)">一个月</a> </li>'+
			'     	  </ul>'+
			' 		</div>'+
			'		<div id="allmap"></div>'+
			//'		class="selectTag" <iframe src="'+getRootPath()+'/logisticsTrack/logisticsTrack.html" frameborder="0" scrolling="no" width="100%" height="100%" id="'+objName+'Iframepage" name="'+objName+'Iframepage" ></iframe>'+
			'	</div>'+
			'</div>'+
			'<div id="'+objName+'MapBj" class="black_overlay_xz"></div>';
		element.html(html);
		
		return function($scope, element, attrs){
			eval("$scope."+objName+"=map");
			
		};
	};
	return myMap;
}]);

