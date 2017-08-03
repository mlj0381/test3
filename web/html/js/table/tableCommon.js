document.write('<script src=\'/js/ngDraggable.js\'><\/script>');
var tableCommon = angular.module("tableCommon", ["ngDraggable"]);

/**qlfeng 城市控件
 * gain 对外提供方法
 * inputValue 双向绑定数据
 * */
tableCommon.directive('tableCity',[function(){
	var myCity = {
			restrict:"E",
			templateUrl : function(tElement,tAttrs){
				return '/js/table/province.html?ver='+tAttrs.ver;
			},
			scope : {
				"inputValue":"=",
				"gain":"=",
				"tableClass":"@",
				"tableDisabled":"=",
				"isCounty":"@"
			},
			compile : function(element, attrs){
				
				/*return function(scope){
					if(scope.tableClass != undefined && scope.tableClass != null && scope.tableClass != ""){
						console.log(scope.tableClass);
					}
					//console.log(scope.inputValue);
				}*/
			},
			controller : [ "$scope", "commonService",function($scope, commonService) {
				/*console.log($scope.inputValue);*/
				var myCity  = {
					init:function(){
						this.bindEvent();
						//this.provinceData();
						this.bindOut();
						
					},
					bindEvent:function(){
						$scope.provinceData=this.provinceData;
						$scope.cityData = this.cityData;
						$scope.countyData = this.countyData;
						$scope.provinceClick = this.provinceClick;
						$scope.cityClick = this.cityClick;
						$scope.countyClick = this.countyClick;
						$scope.changeTab = this.changeTab;
						$scope.inputClick = this.inputClick;
						$scope.close = this.close;
						$scope.clear = this.clear;
						$scope.changeValue = this.changeValue;
						$scope.mouseOut = this.mouseOut;
						$scope.blurOut = this.blurOut;
						$scope.mouseIn = this.mouseIn;
						$scope.confirm = this.confirm;
					},
					//对外提供方法
					bindOut:function(){
						$scope.gain={};
						$scope.gain.initData = this.initData;
						$scope.gain.inputValue ={};
						$scope.gain.clear = this.clear;
						$scope.gain.auto = this.auto;
					},
					//获取省份数据
					provinceData:function(){
						commonService.postUrl("staticDataBO.ajax?cmd=selectProvince",{"time":new Date() + $scope.$id},function(data){
							$scope.province = data.items;
						});
					},
					//获取城市数据
					cityData:function(provinceId){
						commonService.postUrl("staticDataBO.ajax?cmd=selectCity",{"provinceId":provinceId,"time":new Date() + $scope.$id},function(data){
							$scope.city = data.items;
						});
					},
					//获取区域数据
					countyData:function(cityId){
						commonService.postUrl("staticDataBO.ajax?cmd=selectDistrict",{"cityId":cityId,"time":new Date() + $scope.$id},function(data){
							$scope.county = data.items;
						});
					},
					provinceClick:function(provinceId,provinceName){
						$scope.clear();
						if(undefined != provinceId && null != provinceId && provinceId != ""){
							$scope.cityData(provinceId);
							$scope.chooseProvinceId = provinceId;
							$scope.chooseProvinceName = provinceName;
							$scope.changeTab(2);
							$scope.value = $scope.chooseProvinceName;
							$scope.changeValue();
						}
					},
					cityClick:function(cityId,cityName){
						if(undefined != cityId && null != cityId && cityId != ""){
							$scope.countyData(cityId);
							$scope.chooseCityId = cityId;
							$scope.chooseCityName = cityName;
							if ($scope.isCounty == false || $scope.isCounty == undefined) {
								$scope.isShow=false;
							}else{
								$scope.changeTab(3);
							}
							$scope.value = $scope.chooseProvinceName + $scope.chooseCityName;
							$scope.changeValue();
							//$scope.changeTab(2);
						}
					},
					countyClick:function(countyId,countyName){
						$scope.chooseCountyId = countyId;
						$scope.chooseCountyName = countyName;
						$scope.value = $scope.chooseProvinceName + $scope.chooseCityName+$scope.chooseCountyName;
						$scope.isShow=false;
						$scope.changeValue();
					},
					changeTab:function(obj){
						$scope.tabShowIndex = obj;
					},
					inputClick:function(){
						//$scope.clear();
						$scope.isShow=true;
						if($scope.province == undefined || $scope.province == null || $scope.province == ""){
							$scope.provinceData();
						}
						$scope.changeTab(1);
					},
					close:function(){
						$scope.isShow=false;
					},
					clear:function(){
						$scope.chooseProvinceName = "";
						$scope.chooseCityName = "";
						$scope.chooseCountyName="";
						$scope.chooseProvinceId = "";
						$scope.chooseCityId="";
						$scope.chooseCountyId = "";
						$scope.city={};
						$scope.county={};
						$scope.value="";
						$scope.changeTab(1);
						$scope.gain.inputValue = {};
						$scope.inputValue = {};
					},
					changeValue:function(){
						$scope.inputValue = {
							"provinceId":$scope.chooseProvinceId,
							"cityId":$scope.chooseCityId,
							"countyId":$scope.chooseCountyId,
							"provinceName":$scope.chooseProvinceName,
							"cityName":$scope.chooseCityName,
							"countyName":$scope.chooseCountyName
						};
						$scope.gain.inputValue = {
								"provinceId":$scope.chooseProvinceId,
								"cityId":$scope.chooseCityId,
								"countyId":$scope.chooseCountyId,
								"provinceName":$scope.chooseProvinceName,
								"cityName":$scope.chooseCityName,
								"countyName":$scope.chooseCountyName
							};
					},
					initData:function(provinceId,provinceName,cityId,cityName,countyId,countyName){
						if(undefined != $scope.inputValue && null != $scope.inputValue && $scope.inputValue != ""){
							provinceId = $scope.inputValue.provinceId; 
							provinceName = $scope.inputValue.provinceName; 
							cityId = $scope.inputValue.cityId; 
							cityName = $scope.inputValue.cityName; 
							countyId = $scope.inputValue.countyId; 
							countyName = $scope.inputValue.countyName; 
						}
						if(undefined != provinceId && null != provinceId && provinceId != ""){
							$scope.provinceData();
							$scope.provinceClick(provinceId,provinceName);
							//$scope.chooseProvinceId = provinceId;
							//$scope.chooseProvinceName = provinceName;
							
						}
						if(undefined != cityId && null != cityId && cityId != ""){
							$scope.cityClick(cityId,cityName);
							//$scope.cityData(provinceId);
							//$scope.chooseCityId = cityId;
							//$scope.chooseCityName = cityName;
						}
						if(undefined != countyId && null != countyId && countyId != ""){
							$scope.countyClick(countyId,countyName);
							//$scope.countyData(cityId);
							//$scope.chooseCountyId = countyId;
							//$scope.chooseCountyName = countyName;
						}
						$scope.changeValue();
					},
					mouseIn:function(){
						$scope.mouseenterDiv = true;
					},
					blurOut:function(){
						if(!$scope.mouseenterDiv){
							$scope.close();
						}
					},
					confirm:function(){
						$scope.close();
					},
					mouseOut:function(){
						$scope.mouseenterDiv = false;
						//$scope.blurOut();
					},
					auto:function(cityName,districtName){
						commonService.postUrl("staticDataBO.ajax?cmd=getIdByName",{"cityName":cityName,"districtName":districtName},function(data){
							if (data.cityId != null&&data.districtId !=null) {
								$scope.gain.initData($scope.chooseProvinceId,$scope.chooseProvinceName,data.cityId,cityName,data.districtId,districtName);
							}
						});
					}
				};
				myCity.init();
			}]
	};
	return myCity;
}]);


/***
 * 滚动条的标签
 * 
 */
tableCommon.directive('myScrolled', function() { 
    return {
	    restrict:"A",
	    link:function(scope, element, attr){
	    	 	var raw = element[0];
	    	 	scope._scrollObj=raw;
	    	 	element.bind('scroll', function() { 
		    	 	scope._top=raw.scrollTop+1;
		    	 	scope._bottom=-(raw.scrollTop+1);
		    	 	if (raw.scrollTop+raw.offsetHeight+100 >= raw.scrollHeight) { 
		    	 		scope.$apply(attr.myScrolled); 
		    	 	}else{
		    	 		scope.$apply();
		    	 	}
	         }); 
	    }
  };
  });

/**
 * 回车 触发 绑定的属性的ngclick事件
 */
tableCommon.directive("myInputEnter",[function(){
	return {
	    restrict:"A",
	    link:function(scope, element, attr){
	    	element.bind("keydown", function (event) {
	        	if(event.which == 13) {
	        		 scope.$apply(attr.myInputEnter); 
	            }
	        });
	    }
  };
}]);

/**
 * 静态数据下拉框标签
 * 
 * type  静态数据的code值
 * selectValue 选择后的值
 */
tableCommon.directive("mySelectStatic", function() {
	var mySelectStatic = {
		restrict : 'E',
		templateUrl : function(tElement, tAttrs) {
			return '/js/table/select.html?ver='+tAttrs.ver;
		},
		scope : {
			"type":"@",
			"selectValue":"=",
			"exclude":"@"
		},
		compile : function(element, attrs) {
		},
		controller : [ "$scope", "commonService", 
				function($scope, commonService) {
			
				var excludeList=new Array();
				if($scope.exclude!=null && $scope.exclude!=undefined && $scope.exclude!=''){
					excludeList=$scope.exclude.split(",");
				}
					var mySelectStatic = {
						init : function() {
							this.bindEvent();
							this.watchEvent();
						},
						bindEvent : function() {
							$scope.load=this.load;
							$scope.changeValue=this.changeValue;
						},
						watchEvent:function(){
							$scope.$watch("selectValue",function(nv,ov){
								if(nv==$scope.value){
									return;
								}
								$scope.value=nv;
				            },true);
						},
						changeValue:function(){
							$scope.selectValue=$scope.value;
						},
						load:function(){
							if($scope.items!=undefined && $scope.items.length>0){
								return;
							}
							var	url = "staticDataBO.ajax?cmd=selectSysStaticDataByCodeType";
							var param={"codeType":$scope.type};
							commonService.postUrl(url,param,function(data){
								//成功执行
								$scope.items=data.items;
								for(var i=$scope.items.length-1;i>=0;i--){
									for(var j=0;j<excludeList.length;j++){
										var value=excludeList[j];
										if(value==$scope.items[i].codeName){
											$scope.items.splice(i,1);
											break;
										}
									}
								}
								
								var item={"codeId":"-1","codeName":""};
								$scope.items.unshift(item);
							})
						}
					};
					mySelectStatic.init();
				}]
	};
	return mySelectStatic;
});


/**
 * 网点下拉框标签
 * 
 * selectValue 选择后的值
 */
tableCommon.directive("mySelectOrg", function() {
	var mySelectOrg = {
		restrict : 'E',
		templateUrl : function(tElement, tAttrs) {
			return '/js/table/selectOrg.html?ver='+tAttrs.ver;
		},
		scope : {
			"selectValue":"="
		},
		compile : function(element, attrs) {

		},
		controller : [ "$scope", "commonService", 
				function($scope, commonService) {
					var mySelectOrg = {
						init : function() {
							this.bindEvent();
							this.watchEvent();
						},
						bindEvent : function() {
							$scope.load=this.load;
							$scope.changeValue=this.changeValue;
						},
						changeValue:function(){
							$scope.selectValue=$scope.value;
						},
						watchEvent:function(){
							$scope.$watch("selectValue",function(nv,ov){
								if(nv==$scope.value){
									return;
								}
								$scope.value=nv;
				            },true);
						},
						load:function(){
							if($scope.items!=undefined && $scope.items.length>0){
								return;
							}
							var	url = "organizationBO.ajax?cmd=queryOrgList";
							commonService.postUrl(url,"",function(data){
								//成功执行
								$scope.items=data;
								var item={"orgId":"-1","orgName":""};
								$scope.items.unshift(item);
							})
						}
					};
					mySelectOrg.init();
				}]
	};
	return mySelectOrg;
});



/**
 * 表格
 * 
 * 在head 的列表对象里面添加2个属性，
 * 		mouseenter：表示该列是需要触发鼠标进入的事件，对应的值就是回调的方法名称；
 * 		mouseleave：表示该列是需要触发鼠标离开的事件，对应的值就是回调的方法名称；
 * 		需要在对外的tabel的对象里面，绑定回调的方法，例如 head里面的列配置 "mouseenter":"enterCallback"
 *      $scope.table.enterCallback=function(x,y,data){};
 *      回调的方法会回传三个属性，x,y 表示鼠标对应的坐标，data 是整行的数据
 *      clickLocation
 * 
 * 
 */
tableCommon.directive("myTableCm", function() {
	var myTableCm = {
		restrict : 'E',
		templateUrl : function(tElement, tAttrs) {
			return '/js/table/table.html?ver='+tAttrs.ver;
		},
		scope : {
			"head":"=",//头部列表的数据
			"table":"=",//通过这个对象返回表格的方法，属性对外
			"url":"=",//请求的url
			"param":"=",//请求的参数
			"id":"@",//列表的数据的主键
			"tableName":"@",//表格的名称
			"afterInitFn":"&",//标签初始化后，调用该方法
			"isMultiSelect":"@",
			"isSelectData" : "@",//隐藏选择项
			"isFilter" : "@",//条件table条件
			"isShowRow":"@"//是否显示设置按钮
		},
		compile : function(scope,element, attrs) {
			if(element.isFilter == undefined){
				element.isFilter = 'true';
			}
		},
		controller : [ "$scope", "commonService", "$timeout","$interval",
				function($scope, commonService, $timeout,$interval) {
					var table = {
						inputParam:{},//表格的输入框的数据	
						page:1,
						rows:50,
						halfRows:25,
						selected:new Array(),//选中的列表
						init : function() {
							this.bindEvent();
							this.bindOut();
							this.setRow();
							$timeout(function(){  
								if($scope.afterInitFn!=undefined){
									$scope.afterInitFn();
								}
				            },0);
							
						},
						bindEvent : function() {
							if($scope.isFilter == undefined || $scope.isFilter == null || $scope.isFilter == ""){
								$scope.isFilter = 'true';
							}
							//绑定变量
							$scope.headList=$scope.head;
							$scope.filterInput={};
							$scope.inputChange=this.inputChange;
							$scope.inputEnter=this.inputEnter;
							$scope.loadMore=this.loadMore;
							$scope.inputParam=this.inputParam;
							$scope.updateSelection=this.updateSelection;
							$scope.isSelected=this.isSelected;
							$scope.tDCss=this.tDCss;
							$scope.mouseenter=this.mouseenter;
							$scope.mouseleave=this.mouseleave;
							$scope.clickLocation = this.clickLocation;
						},
						bindOut:function(){
							//绑定对外的数据
							$scope.table={};
							$scope.table.load=this.load;
							$scope.table.callBack=function(){};
							$scope.table.getSelectData=this.getSelectData;
							$scope.table.downloadExcelFile=this.downloadExcelFile;
							$scope.table.clearInput=this.clearInput;
						},
						mouseenter:function(even,data,callbackFn){
							if(callbackFn!=undefined){
								eval("$scope.table."+callbackFn+"("+even.clientX+","+even.clientY+","+angular.toJson(data)+")");
							}
						},
						mouseleave:function(even,data,callbackFn){
							if(callbackFn!=undefined){
								eval("$scope.table."+callbackFn+"("+even.clientX+","+even.clientY+","+angular.toJson(data)+")");
							}
						},
						clickLocation:function(data,callbackFn){
							console.log(callbackFn)
							if (callbackFn!=undefined) {
								eval("$scope.table."+callbackFn+"("+angular.toJson(data)+")");
							}
						},
						tDCss:function(data,inputStr){
							if(inputStr==undefined ||  inputStr=="" )
								return "";
							var splitStr=inputStr.split(":");
							if(splitStr.length==1){
								return splitStr[0];
							}else if(splitStr.length==2){
								if(eval("data."+splitStr[0])==true){
									return splitStr[1];
								}else{
									return "";
								}
							}else {
								return "";
							}
						},
						//初始化滚动条的位置
						initScroll:function(){
							$scope._top=0;
							$scope._bottom=0;
							$scope._scrollObj.scrollTop=0;
						},
						//清楚过滤的条件的输入的数据
						clearInput:function(){
							for(var i  in $scope.inputParam){
								for(var j in $scope.head){
									if(i==$scope.head[j].code){
										if("text"==$scope.head[j].type){
											$scope.inputParam[i]="";
											$scope.filterInput[i]="";
										}else if("select"==$scope.head[j].type || "selectOrg"==$scope.head[j].type){
											$scope.inputParam[i]="-1";
											$scope.filterInput[i]="-1";
										}
									}
								}
							}
						},
						//选中一行的checkbox的逻辑
						updateSelection:function(even,id,isAll){
							id=""+id;
							var tableName="";
							if($scope.tableName != undefined){
                                tableName = $scope.tableName;
							}
		        			var checkbox = document.getElementById(tableName+"checkBox_"+id);
		        			if(checkbox == undefined){
		        				return ;
		        			}
				        	var action = (checkbox.checked?'remove':'add');
				        	
		        			if(($scope.isMultiSelect==undefined) || ($scope.isMultiSelect==undefined && $scope.isMultiSelect!="true")){
		        				table.clearAllCheckbox();
		        			}
		        			//全选的操作
		        			if(isAll!=undefined && isAll==-1){
		        				
		        				if(action == 'add' && table.selected.indexOf(id) == -1){
		        					table.selectAllCheckbox();
		    			         }
		    			         if(action == 'remove'){
		    			        	 table.clearAllCheckbox();
		    			         }
		        				
		        			}else{
		        				if(action == 'add'){
		        					if(table.selected.indexOf(id) == -1){
		        						table.selected.push(id);
		        					}
		    			            checkbox.checked=true;
		    			         }
		    			         if(action == 'remove' ){
		    			        	 if(table.selected.indexOf(id)!=-1){
		    			        		 var idx = table.selected.indexOf(id);
		    			        		 table.selected.splice(idx,1);
		    			        	 }
		    			            
		    			             checkbox.checked=false;
		    			         }
		        			}
							
						},
						
						//获取选中的数据
		        		getSelected:function(){
		        			return table.selected;
		        		},
		        		//获取单选方式选中的数据
		        		getOneSelected:function(){
		        			if(table.selected.length==1){
		        				return table.selected[0];
		        			}else{
		        				return "";
		        			}
		        		},
		        		//获取选中的数据
		        		getSelectData:function(){
		        			var selectArray=new Array();
		        			var list=$scope.tableData;
		        			if(list!=null && list!=undefined && list.length>0){
		        				for(var index in list){
		        					if(list[index]!=null && list[index]!=undefined){
		        						var dateId=list[index][$scope.id];
				        				for(var selectIndex in table.selected){
				        					if(table.selected[selectIndex]==dateId){
				        						selectArray.push(list[index]);
				        					}
				        				}
		        					}
			        			}
		        			}
		        			return selectArray;
		        		},
						
						//清除所有的checkbox
		        		clearAllCheckbox:function(){
							var tableName="";
							if($scope.tableName != undefined){
								tableName = $scope.tableName;
							}
		        			var checkbox = document.getElementsByName(tableName+"_checkName");
		        			for(var index in checkbox){
		        				checkbox[index].checked=false;
		        			}
		        			this.selected=[];
		        		},
		        		
		        		//清除所有的checkbox
		        		selectAllCheckbox:function(){
		        			this.selected=[];
							var tableName="";
							if($scope.tableName != undefined){
								tableName = $scope.tableName;
							}
		        			var checkbox = document.getElementsByName(tableName+"_checkName");
		        			for(var index in checkbox){
		        				checkbox[index].checked=true;
		        				if(checkbox[index].value!=undefined && checkbox[index].value!=""){
		        					this.selected.push(checkbox[index].value);
		        				}
		        			}
		        			
		        		},
		        		isSelected:function(inputId){
		        			//判断输入的值是否已经选中了
		        			inputId=""+inputId;
		        			return table.selected.indexOf(inputId)==-1 ?false:true;
		        		},
						//输入框的变化事件
						inputChange:function(row){
							$scope.inputParam[row]=$scope.filterInput[row];
						},
						//输入框的回车事件
						inputEnter:function(){
							table.load();
						},
						//向下拉，加载多一页的数据
						loadMore:function(){
							var pages = Math.round(($scope.totalNum+table.halfRows)/table.rows);
							$scope.param.page=parseInt($scope.param.page)+1;
				            if($scope.param.page>pages){
				            	return;
				            }
				            //0表示不需要返回汇总的信息
				            $scope.param._sum="0";
		                    commonService.postUrl($scope.url,$scope.param,function(dataTemp){
		                    		dataTemp.items.forEach(function(e){
		                                    $scope.tableData.push(e);
		                                    
		                            });
		                    });
		                    $scope.tableListData=$scope.tableData;
		                    table.clearAllCheckbox();
						},
						//第一个请求
						load:function(){
							$scope.param.page=table.page;
							$scope.param.rows=table.rows;
							$scope.param.inputParamJson=angular.toJson($scope.inputParam);
							if($scope.param.inputParamJson=="{}"){
								$scope.param.inputParamJson="";
							}
							table.initScroll();
							window.top.showLoad();
							
							//需要汇总的参数，表示需要返回汇总的数据
							$scope.param._sum="1";
							
							commonService.postUrl($scope.url,$scope.param,function(data){
								$scope.tableData = data.items;
								$scope.sumData=data.sumData;
								$scope.totalNum=data.totalNum;
								$scope.table.callBack(data);
								window.top.hideLoad();
							},function(){
								window.top.hideLoad();
							});	
							table.clearAllCheckbox();
						},
						//导出功能
						downloadExcelFile:function(){
							delete $scope.param._sum;
                            var queryUrl=$scope.url;
		        			var excelKeys=new Array();
		        			var excelLables=new Array();
		        			
		        			var sum = false;
		        			for(var index in $scope.head){
		        				var el=$scope.head[index];
                                var isHide = el.isHide||false;
                                if(isHide){
                                    continue;
                                }
		        				if(!sum){
		        					//对于统计处理
		        					excelKeys.push(el.code+"*");
		        					excelLables.push(el.name);
		        					sum=true;
		        				}else{
		        					excelLables.push(el.name);
			        				if(el.isSum){
			        					//对于统计处理
			        					excelKeys.push(el.code+"@");
			        				}else{
			        					excelKeys.push(el.code);
			        				}
		        				}
		        			}
		        			commonService.downloadExcelFile(queryUrl,$scope.param,excelLables,excelKeys,"",$scope.tableName);
		        		},
		        		signUrl:function(orgiUrl) {
		        			var paramArray = new Array();
		        			var name,value,paramStr; 
		        			if (orgiUrl != undefined) {
		        				 var url = orgiUrl.substring(orgiUrl.lastIndexOf("/")+1);
		        				 if ((idx = url.indexOf("&")) > 0) {
		        					 paramStr = url.substring(0, idx);
		        					 var params = url.substring(idx+1).split("&");
		        					 for (var i in params) {
		        						 if (params[i].split("=")[1] !== "null" && params[i].split("=")[1] !== "") {
		        							 paramArray.push(params[i]);
		        						 }
		        					 }
		        				 } else {
		        					 paramStr = url;
		        				 }
		        			}
		        		    if (paramArray.length > 0)
		        		    	paramStr += "&"+ paramArray.sort().join("&");
		        		        paramStr += "&sign=" +md5(paramStr+getCookie("token"));
		        		    return paramStr;
		        		},
		        		//设置table列表
		        		setRow : function(){
		        			// 请求后台回去列显示信息
		        			var headList = window.top.tableHead[$scope.tableName];
		        			if(typeof headList != "undefined" && headList != null && headList != ""){
		        				for(var i in $scope.headList){
		        					var hd = $scope.headList[i];
		        					$scope.headList[i].isHide = true;
		        					if(headList.hasOwnProperty(hd.name+"#"+hd.code)){
		        						$scope.headList[i].isHide = false;
		        					}
		        				}
		        				var list=[];
		        				for(var i in $scope.headList){
		        					var hd = $scope.headList[i];
	        						list.push(hd);
		        				}
		        				for(var i in list){
		        					var hd = list[i];
		        					if(headList.hasOwnProperty(hd.name+"#"+hd.code)){
		        						var index = parseInt(headList[hd.name+"#"+hd.code]);
		    		                    var otherObj = $scope.headList[index];
		    		                    var otherIndex = $scope.headList.indexOf(hd);
		    		                    $scope.headList[index] = hd;
		    		                    $scope.headList[otherIndex] = otherObj;
		        					}
		        				}
		        			}
		        			// 列表设置显示隐藏
		        			$scope.setTabelShow = false;
							$scope.setTabel = function(){
			                	isSelectAll()
								if($scope.setTabelShow){
									$scope.setTabelShow = false;
								}else{									
									$scope.setTabelShow = true;
								}
							}
							$scope.saveRowSet = function(){
								var headConfig={};
								headConfig.tableName = $scope.tableName;
								headConfig.sysTableHeadConfigList = [];
								$scope.tableHeadValue = {};
								//组装要保存的table，目前保存需要显示的
								for(var i in $scope.headList){
									var hd = $scope.headList[i];
									if(!hd.isHide){
										var tableHeadConfig = {};
										tableHeadConfig.headName = hd.name;
										tableHeadConfig.headCode = hd.code;
										tableHeadConfig.headIndex = i;
										headConfig.sysTableHeadConfigList.push(tableHeadConfig);
										$scope.tableHeadValue[hd.name+"#"+hd.code]=i;
									}
								}
								var param = {};
								param.paramStr = JSON.stringify(headConfig);
								var url = "sysTableHeadConfigBO.ajax?cmd=saveSysTableHeadConfigs";
								commonService.postUrl(url,param,function(data){
								    commonService.alert("保存完成!",function(){
								    	$scope.setTabelShow = false;
								    	//更新头部的参数
								    	window.top.tableHead[$scope.tableName]=$scope.tableHeadValue;
								    	$scope.$apply();
									});
								});
							}
							$scope.cancelRowSet = function(){
								$scope.setTabelShow = false;
							}
							// 显示隐藏列
		        			$scope.myChange = function(index){
				                if($scope.headList[index].isHide){
				                    $scope.headList[index].isHide = false;
				                }else{
				                    $scope.headList[index].isHide = true;
				                }
				                isSelectAll();
				            }
				            // 拖动调整
				            $scope.onDropComplete = function (index, obj, evt) {
			                    var otherObj = $scope.headList[index];
			                    var otherIndex = $scope.headList.indexOf(obj);
			                    $scope.headList.splice(otherIndex,1);
			                    $scope.headList.splice(index,0,obj); 
			                }
			                if($scope.isShowRow){
			                	$scope.showsSetRow = true;
			                }
			                $scope.selectAll = function(){
			                	for(var i in $scope.headList){
			                		$scope.headList[i].isHide = false;
			                	}
			                }
			                $scope.selectBack = function(){
			                	for(var index in $scope.headList){
			                		if($scope.headList[index].isHide){
				                    	$scope.headList[index].isHide = false;
					                }else{
					                    $scope.headList[index].isHide = true;
					                }
			                	}
			                	isSelectAll()
			                }
			                function isSelectAll(){
			                	var isAll = true;
				                for(var i in $scope.headList){
				                	if($scope.headList[i].isHide){
				                		isAll = false;
				                		$("#selectAllBtn").prop({checked:false});
				                	}
				                }
				                if(isAll){
				                	$("#selectAllBtn").prop({checked:true});
				                }
			                }
		        		}
					};
					table.init();
				}]
	};
	return myTableCm;
});


//判断是否渲染完毕
tableCommon.directive('repeatFinish',function(){
    return {
        link: function($scope,element,attr){
            if($scope.$last == true){
                stretch($scope);
            }
        }
    }
});


//表单列宽自由拖动
function stretch($scope){
	var myTAbId = document.getElementById("myTab");
    var tTD; //用来存储当前更改宽度的Table Cell,避免快速移动鼠标的问题   
    var wData = {};
    if (myTAbId!=undefined&&myTAbId!=null) {
		
	
        for (var j = 0; j < myTAbId.rows[0].cells.length; j++) {  
            
            myTAbId.rows[0].cells[j].onmousedown = function (event) {   
                //记录单元格    
                tTD = this;   
                if (event.offsetX > tTD.offsetWidth - 10) {   
                    tTD.mouseDown = true;   
                    tTD.oldX = event.clientX;   
                    tTD.oldWidth = tTD.offsetWidth;   
                }   
                //记录Table宽度   
                //table = tTD; while (table.tagName != ‘TABLE') table = table.parentElement;   
                //tTD.tableWidth = table.offsetWidth;   
            };   
            myTAbId.rows[0].cells[j].onmouseup = function (event) {   
                //结束宽度调整   
                if (tTD == undefined) tTD = this;   
                tTD.mouseDown = false;   
                tTD.style.cursor = 'default';   
            };   
            myTAbId.rows[0].cells[j].onmousemove = function (event) {   
                //更改鼠标样式   
                if (event.offsetX > this.offsetWidth - 10)   
                this.style.cursor = 'col-resize';      
                else   
                this.style.cursor = 'default';   
                //取出暂存的Table Cell   
                if (tTD == undefined) tTD = this;   
                //调整宽度   
                if (tTD.mouseDown != null && tTD.mouseDown == true) { 
                    
                    tTD.style.cursor = 'default';   
                    if (tTD.oldWidth + (event.clientX - tTD.oldX)>0)   
                    tTD.width = tTD.oldWidth + (event.clientX - tTD.oldX);   
                    //调整列宽   
                    tTD.style.width = tTD.width;   
                    tTD.style.cursor = 'col-resize';   
                    // myTAbId.rows[8].cells[j].style.width = tTD.width;   
                    //调整该列中的每个Cell   
                    myTAbId = tTD; while (myTAbId.tagName != 'TABLE') myTAbId = myTAbId.parentElement;   
                    for (var k = 0; k < myTAbId.rows.length; k++) {   
                        myTAbId.rows[k].cells[tTD.cellIndex].width = tTD.width;   
                    }   
                    //调整整个表   
                    //table.width = tTD.tableWidth + (tTD.offsetWidth – tTD.oldWidth);   
                    //table.style.width = table.width;   
                }   
            };   
        }  
    }
}