/**
 * 首页设置的标签
 * 
 */
var indexLabel=angular.module("indexLabel", ['highcharts-ng']);


/**
 * 首页的标签
 * 
 */
indexLabel.directive('myIndexLabel',['commonService','$compile',function(commonService,$compile){
	var myIndexLabel = {};
	myIndexLabel.restrict="AE";
	myIndexLabel.scope={configList:'='};
	myIndexLabel.compile=function(element,attrs,$scope){
		return function($scope,element,attrs){
			commonService.postUrl("indexBusiBO.ajax?cmd=getIndexElement","",function(data){
				var configList=data.items;
				var template="";
				for(index in configList){
					template=template+'<li  class="fl_left" style="width:'+configList[index].width+'%" '+configList[index].label+'='+configList[index].title+' params='+configList[index].params+'></li>';
				}
				element.html(template);
				$compile(element.contents())($scope);
			});
			
		};
	};
	return myIndexLabel;
}]);



/**
 * 报表标签
 */
indexLabel.directive('myHighChart',['commonService','$compile',function(commonService,$compile){
	var myHighChart = {};
	myHighChart.restrict="AE";
//	myHighChart.scope={};
	myHighChart.compile=function(element,attrs,$scope){
		return function($scope,element,attrs){
			var title=attrs.myHighChart;//整块元素的标题
			var charId=attrs.params;
			
			
			
			var chart={
					buildStatisticChartYAxis : function(chartInfo,userChartInfo){// 构建统计图表Y轴信息
						chartInfo.yAxis = {
				            title: {
				                text: userChartInfo.chartYaxisTitle
				            },
				            plotLines: [{
				                value: 0,
				                width: 1,
				                color: '#808080'
				            }]
				        };
					},
					buildStatisticChartXAxis :function(chartInfo,userChartInfo){// 构建统计图表X轴信息
						chartInfo.xAxis = {};
						chartInfo.xAxis.categories = userChartInfo.categories;
					},
					buildStatisticChart:function(data){
						
						if(data.checkInfo.resultCode == '1') {
							var userChartInfo = data.chartInfo;
							if(userChartInfo.chartType == 6){ // 6:用户存活率图表(此类型图表特殊，后续考虑可否公用)
								var tempCategories = userChartInfo.categories;
								var userRetentionChartInfosCategories = new Array();
								for(var i = 0; i < tempCategories.length; i++){
									var tempObj = {
										value : tempCategories[i]
									}
									userRetentionChartInfosCategories.push(tempObj);
								}
								this.userRetentionChartInfosCategories = userRetentionChartInfosCategories;
								this.userRetentionSeriesInfos = data.seriesInfo;
								return;
							} else if (userChartInfo.chartType == 7){// table 类型
								this.tableChartInfo.total = data.tableInfo.tableDataTotal;
								this.tableChartInfo.results = data.tableInfo.tableResultData;
								var tempCategories = userChartInfo.categories;
								var categories = new Array();
								for(var i = 0; i < tempCategories.length; i++){
									var tempObj = {
										value : tempCategories[i]
									}
									categories.push(tempObj);
								}
								this.tableChartInfo.head = categories;
								this.tableChartInfo.chartInfo = data.chartInfo;
								this.exportTableDataInfo = data.exportTableDataInfo;
								return;
							}
							
							var chartInfo = {};
							this.buildStatisticChartCommonInfo(chartInfo,userChartInfo);// 构建Title和SubTitle等公用信息
							
							// '图表类型，枚举如下：\n1:曲线图\n2:3D柱状图\n3:饼图\n4:双饼图'
							if (1 == userChartInfo.chartType) {// 曲线图
								this.buildStatisticChartXAxis(chartInfo,userChartInfo);// 构建X轴
								this.buildStatisticChartYAxis(chartInfo,userChartInfo);// 构建Y轴
								chartInfo.plotOptions = {
						            series: {
						                cursor: 'pointer',
						                events: {
						                    click: function (event) {
						                    	var xIndex = event.point.index;// 确定点击的是哪个X轴
						                        var pointUserOptions = this.userOptions.userOptions;
												if(pointUserOptions != undefined && 'click' == pointUserOptions.eventName){
//													eval("$scope." + pointUserOptions.eventMethodName + "(pointUserOptions, xIndex)");
												}
						                    }
						                }
						            }
						        }
							} else if (2 == userChartInfo.chartType) {// 3D柱状图
								this.buildStatisticChartXAxis(chartInfo,userChartInfo);// 构建X轴
								this.buildStatisticChartYAxis(chartInfo,userChartInfo);// 构建Y轴
								chartInfo.chart = {
						            type: 'column',
						            margin: 75,
						            options3d: {
						                enabled: true,
						                alpha: 5,
						                beta: 15,
						                depth: 70
						            }
						        };
							} else if (3 == userChartInfo.chartType || 5 == userChartInfo.chartType) {// 饼图
								if(5 == userChartInfo.chartType){
									chartInfo.chart = {
										type: 'pie',
										options3d: {
											enabled: true,
											alpha: 45,
											beta: 0
										}
									};
								} else {
									chartInfo.chart = {
							            plotBackgroundColor: null,
							            plotBorderWidth: null,
							            plotShadow: false
							        };
								}
								chartInfo.plotOptions = {
						            pie: {
						                allowPointSelect: true, // 是否允许选择扇区
						                cursor: 'pointer',
						                dataLabels: {
						                    enabled: true, // 控制是否显示dataLables部分
						                    color: '#000000',// 扇区提示字体颜色
						                    connectorColor: '#000000'// 扇区连接线颜色
						                },
										events: {
											click: function (event) {// click事件
												var pointUserOptions = event.point.options.userOptions;
												if(pointUserOptions != undefined && 'click' == pointUserOptions.eventName){
//													eval("$scope." + pointUserOptions.eventMethodName + "(pointUserOptions)");
												}
											}
										}
						            }
						        };
								
								if(undefined != userChartInfo.chartTooltipValuePointFormat && null != userChartInfo.chartTooltipValuePointFormat 
										&& 'null' != userChartInfo.chartTooltipValuePointFormat && "" != userChartInfo.chartTooltipValuePointFormat) {
									chartInfo.plotOptions.pie.dataLabels.format = userChartInfo.chartTooltipValuePointFormat;
							    }else{
							    	chartInfo.plotOptions.pie.dataLabels.format = '<b>{point.name}</b>: {point.percentage:.1f} %'
							    }
								
								if(userChartInfo.showLegend){
									chartInfo.plotOptions.pie.showInLegend = true;
									chartInfo.legend.layout = userChartInfo.legendLayout;
									chartInfo.legend.align = userChartInfo.legendAlign;
									chartInfo.legend.verticalAlign = userChartInfo.legendVerticalAlign;
									chartInfo.legend.borderWidth = 0;
								}
							} else if (4 == userChartInfo.chartType) {// 双饼图
								this.buildStatisticChartYAxis(chartInfo,userChartInfo);// 构建Y轴
								chartInfo.chart = {
						            type: 'pie'
						        };
								chartInfo.plotOptions = {
						            pie: {
						                shadow: false,
						                center: ['50%', '50%']
						            }
						        };
								this.buildDoublePieSeriesInfo(data.seriesInfo);
							} else {// 默认直方图
								chartInfo.chart = {
						            type: 'column',
						            margin: 75
						        };
							}
							if(userChartInfo.chartType != 6){
								chartInfo.series = data.seriesInfo;
								
								// 只显示图表中的第一条线，其他线隐藏
								if (1 == userChartInfo.chartType || 2 == userChartInfo.chartType) {
									var chartSeriesLength = chart.series.length;
									if(chartSeriesLength > 1){
										for(var i = 1; i < chartSeriesLength; i++){
											if(chart.series[i].name != '累计总数')
												chart.series[i].hide();
										}
									}
								}
							}
							
							return chartInfo;
						} else {
							if(undefined != data.checkInfo.resultMessage){
								alert(data.checkInfo.resultMessage);
							} else {
								alert('无法获取对应运营统计数据');
							}
						}
					},
					buildStatisticChartCommonInfo : function(chartInfo,userChartInfo){// 构建统计图表通用信息
						chartInfo.exporting = {
							enabled : false
						};
						chartInfo.credits = {
							enabled : false	
						};
						chartInfo.title = {
				            text: userChartInfo.chartTitle,
				            x: -20 //center
				        };
						chartInfo.subtitle = {
				            text: userChartInfo.chartSubtitle,
				            x: -20
				        };
						chartInfo.tooltip = {};
						if(undefined != userChartInfo.chartTooltipValueSuffix && null != userChartInfo.chartTooltipValueSuffix 
							&& 'null' != userChartInfo.chartTooltipValueSuffix && "" != userChartInfo.chartTooltipValueSuffix) {
							chartInfo.tooltip.valueSuffix = userChartInfo.chartTooltipValueSuffix;
				        }
						if(undefined != userChartInfo.chartTooltipValuePointFormat && null != userChartInfo.chartTooltipValuePointFormat 
								&& 'null' != userChartInfo.chartTooltipValuePointFormat && "" != userChartInfo.chartTooltipValuePointFormat) {
							chartInfo.tooltip.pointFormat = userChartInfo.chartTooltipValuePointFormat;
					    }
						if(userChartInfo.showLegend){
							chartInfo.legend = {};
							chartInfo.legend.layout = userChartInfo.legendLayout;
							chartInfo.legend.align = userChartInfo.legendAlign;
							chartInfo.legend.verticalAlign = userChartInfo.legendVerticalAlign;
							chartInfo.legend.borderWidth = 0;
						}
					}
			}
			
			
			var params={"chartId":charId};
			
			commonService.postUrl("statisticBO.ajax?cmd=showStatisticInfo",params,function(data){
				var chartInfo=chart.buildStatisticChart(data.resultData);
				$scope.charts=chartInfo;
				var template='<div style="width:100%">'
						+'<div class="main_yybb border_r1">'
						+'   <div class="title clearfix"><i class="m_jt fl"></i><h2>'+title+'</h2></div>'
						+'   <i class="function_icon icon"><a href="#" class="function_icon_li"></a></i>'
						+'   <div class="main_data"><highchart title="charts.title" series="charts.series" options="charts.options"></highchart></div>'
						+' </div>'
						+' </div>';
					element.html(template);
					
					$compile(element.contents())($scope);
			});
			
		};
	};
	return myHighChart;
}]);


/**
 * 查询订单的搜索框
 */
indexLabel.directive('mySearch',['commonService',function(commonService){
	var mySearch = {};
	mySearch.restrict="AE";
//	mySearch.scope={};
	mySearch.compile=function(element,attrs,$scope){
		var template='<div class="index_search clearfix">'
        			+'<div class="search_form clearfix"><input type="text" ng-model="searchOrderId" class="text fl" name="fname" placeholder="请输入运单号" ng-keydown="($event.which === 13)?search():0" /><button class="button fl" type="button" ng-click="search()"><i class="search_icon icon"></i></button>'
        			+'<div class="search_advance fl ml_20"><a  style="cursor:pointer" ng-click="searchCond()">多条件查询</a></div></div>'
//        			+'<ul class="search_nav clearfix">'
//        			+'<li class="sea_navli"><a href="#"><i class="icon icon_notice"></i><span class="noft-red img_circle">9</span><p>放货通知</p></a></li>'
//        			+' <li class="sea_navli"><a href="#"><i class="icon icon_problem"></i><p>问题件</p></a></li>'
//        			+'<li class="sea_navli"><a href="#"><i class="icon icon_sign"></i><p>签收录入</p></a></li>'
//        			+'<li class="sea_navli"><a href="#"><i class="icon icon_dbdh"></i><span class="noft-red img_circle">99+</span><p>短驳到货</p></a></li>'
//        			+'<li class="sea_navli"><a href="#"><i class="icon icon_gxdh"></i><p>干线到货</p></a></li>'
        			+' </ul>'
        			+'</div>';
		
		element.html(template);
		return function($scope,element,attrs){
			$scope.search=function(){
				var orderId=$scope.searchOrderId;
				var param={"trackingNum":orderId};
				var datailUrl="orderInfoBO.ajax?cmd=queryOrderDetail";
				commonService.postUrl(datailUrl,param,function(data){
				  if(data==""){
					  commonService.alert("找不到该运单的信息"); 
					  return;
				  }else{
					    window.open("/ord/billingDetail.html?isShowReturn=false&view=1&orderId="+orderId+"&type=3");
				  }
				});
				//window.open("/ord/billingDetail.html?view=1&orderId="+orderId+"&type=3");
			};
			//多条件查询
			$scope.searchCond=function(){
		           $("#isShowSearch").show();
		           $("#alertMsgIsShow").show();
		           
			};
		
		};
	};
	return mySearch;
}]);

/**
 * 查询帐户的余额
 */
indexLabel.directive('myBalance',['commonService',function(commonService){
	var myBalance = {};
	myBalance.restrict="AE";
	myBalance.scope={};
	myBalance.compile=function(element,attrs){
		
		return function($scope,element,attrs){
			
			commonService.postUrl("indexBusiBO.ajax?cmd=getAmount","",function(data){
				var template='<ul class="cm_left border_r1 common" style="width:100%">'
        			+'<li class="cm_leftli">'
        			+'<div class="ml118">'
        			+'  <i class="cm_purse icon fl"></i>'
        			+'<div class="cm_collection fl">'
        			+'   <h2>代收款项</h2>'
        			+'   <span>'+data.amount+'</span>'
        			+'  </div>'
        			+' </div>'
        			+'</li>'
        			+' <li class="cm_leftli">'
        			+'	<i class="cm_purse icon fl"></i>'
        			+'    <div class="cm_collection fl">'
        			+'        <h2>预付款项</h2>'
        			+'       <span>'+data.prePay+'</span>'
        			+'   </div>'
        			+'  </li>'
        			+'</ul>';
		
				element.html(template);
			});
		};
	};
	return myBalance;
}]);

/**
 * 查询快捷方式
 */
indexLabel.directive('myShortcut',['commonService',function(commonService){
	var myShortcut = {};
	myShortcut.restrict="AE";
	myShortcut.scope={};
	myShortcut.compile=function(element,attrs){
		
		return function($scope,element,attrs){
			commonService.postUrl("indexBusiBO.ajax?cmd=getshortcutList","",function(data){
				data=data.items;
				var bt="";
				for(var index in data){
					bt=bt+'<a class="cm_r_button" href="javascript:void(0);" onclick="openIndex(\''+data[index].url+'\',\''+data[index].name+'\',\''+data[index].url+'\')">'+data[index].name+'</a>'
					
				}
				var template='<div class="cm_right clearfix common" style="width:100%">'
        			+'<h2>常用功能：</h2>'
        			+'<div class="fl cm_list">'
        			+bt
        			+'</div>'
        			+'<i class="function_icon icon" style="top:0;"><a class="function_icon_li" href="#"></a></i>'
        			+' </div>';
		
				element.html(template);
			});
		};
	};
	return myShortcut;
}]);


/**
 * 侧边栏快捷方式
 */
indexLabel.directive('mySidebarShortcut',['commonService','$compile',function(commonService,$compile){
	var mySidebarShortcut = {};
	mySidebarShortcut.restrict="AE";
	mySidebarShortcut.scope={
			myTab:"=mytab",
			isShowDef:"=isshowdef"
	};
	mySidebarShortcut.compile=function(element,attrs){
		
		return function($scope,element,attrs){
			$scope.addVersion=function(noVerUrl){
				var ver=attrs.ver;
				if(ver == undefined){
					ver="1";
				}
				if(noVerUrl.indexOf("?")!=-1){
					return noVerUrl+"&ver="+ver;
				}else{
					return noVerUrl+"?ver="+ver;
				}
			};
			
			//获取当前页面的id
			$scope.sideBarSearch=function(){
				var orderId=$scope.sideBarSearchId;
				var param={"trackingNum":orderId};
				var datailUrl="orderInfoBO.ajax?cmd=queryOrderDetail";
				commonService.postUrl(datailUrl,param,function(data){
				  if(data==""){
					  commonService.alert("找不到该运单的信息"); 
					  return;
				  }else{
					  console.log("open");
					  window.open($scope.addVersion("/ord/ordBillingDetail.html?isShowReturn=false&view=1&type=3&trackingNum="+data.items[0].orderInfo.trackingNum));
//					  openIndex(data.items[0].orderInfo.orderId,"运单打印详情","/ord/billing.html?view=1&orderId="+data.items[0].orderInfo.orderId+"&trackingNum="+data.items[0].orderInfo.trackingNum);
				  }
				});
			};
			//默认按钮新增
			$scope.isAdd = true;
			//快捷栏所有图标
			$scope.imageUrl=["navicon_1","navicon_2","navicon_3","navicon_4","navicon_5"];
			$scope.addTitle=function(){
				var obj = getCurrentOpenTab();
				if(obj.type = "show"){
					var param = {"url":obj.url,"name":obj.name,"menuId":obj.id};
					param.imageUrl = $scope.imageUrl[Math.floor(Math.random()*$scope.imageUrl.length)]
					commonService.postUrl("indexBusiBO.ajax?cmd=addShortcuts",param,function(data){
						if(data != null &&data != undefined && data !=""){
							if(data.isOk == "Y"){
								$scope.updateTitle();
							}
						}
					});
				}
			};
			$scope.clickShow=function(){
				if($(".form_kz").css("display")=="none"){
					$(".form_kz").show();
					$(".side_search").css({"background":"#e46143", "border-radius":"0 2px 2px 0"});
				}else{
					$(".form_kz").hide();
					$(".side_search").css({"background":""});
				}
			};
			$scope.allTitle=null;//所有的快捷键
			//控制按钮是否为增加 删除
			$scope.isEquels=function(){
				var obj = $scope.myTab.getCurrentTab();
				if($scope.allTitle != null && $scope.allTitle != undefined && $scope.allTitle.length!=undefined && obj != null){
					for(var i=0;i<$scope.allTitle.length;i++){
						if($scope.allTitle[i].menuId = obj.id){
							if(obj.url.indexOf($scope.allTitle[i].url)!=-1){
								return true;
							}
						}
					}
				}
				return false;
			};
			//删除
			$scope.delTitle=function(){
				var obj = getCurrentOpenTab();
				if(obj.type = "show"){
					var param = {"url":obj.url,"name":obj.name,"id":obj.id};
					commonService.postUrl("indexBusiBO.ajax?cmd=delShortcuts",param,function(data){
						if(data != null &&data != undefined && data !=""){
							if(data.isOk == "Y"){
								$scope.updateTitle();
							}
						}
					});
				}
			};
			//默认多条件查询
			if($scope.isShowDef==true){
				$scope.defaultTitle='<li class="li" style="text-align: left;/*7-21*/"><a class="dgnsy tooltip-tip ajax-load tooltipster-disable" type="button" onClick="document.getElementById(\'isShowSearch\').style.display=\'block\';document.getElementById(\'alertMsgIsShow\').style.display=\'block\';" title="多条件查询" ><i class="dtjss navicon_2 icon" style="left: 26px; right: 12px;"></i><span style="display: inline-block; float: none;">多条件查询</span></a></li>';
			}else{
				$scope.defaultTitle='';
			}
			
			$scope.updateTitle=function(){
				commonService.postUrl("indexBusiBO.ajax?cmd=getshortcutList","",function(data){
					data=data.items;
					$scope.allTitle = data;
					var bt='';
					if($scope.isShowDef==true){
						bt='<li class="li" style="text-align: left;/*7-21*/"><a class="dgnsy tooltip-tip ajax-load tooltipster-disable" type="button" onClick="document.getElementById(\'isShowSearch\').style.display=\'block\';document.getElementById(\'alertMsgIsShow\').style.display=\'block\';document.body.style.overflow=\'hidden\';setContentHegth();" title="多条件查询" ><i class="dtjss navicon_2 icon" /*style="left: 26px; right: 12px;"*/></i><span style="display: inline-block; float: none;">多条件查询</span></a></li>';
					}
					
					for(var index in data){
						var newUrl = $scope.addVersion(data[index].url);
						bt=bt+'<li class="li" style="text-align: left;"><a href="javascript:void(0);" onclick="openIndex(\''+data[index].menuId+'\',\''+data[index].name+'\',\''+newUrl+'\')" class="tooltip-tip ajax-load tooltipster-disable" title="'+data[index].name+'"><i class="'+data[index].imageUrl+' icon" /*style="left: 16px; right: 16px;"*/></i><span style="display:none;">'+data[index].name+'</span></a></li>';
					}
					var template='<div class="skin-xx">'
								+'<div id="skin-select" class="sidebar" style="left: -84px;top:25%;">'
								+'<div class="side_top">'
								+'<a id="toggle" class="toggle" href="#" style="display: inline-block; float: none;"><i class="icon_bars icon"></i></a>'
								+'</div>'
//								+'<div class="side_search" style="text-align: center;"><a class="dj_bh" ><input type="text" ng-keydown="($event.which === 13)?sideBarSearch():0" ng-model="sideBarSearchId" placeholder="输入运单号" class="form_control fl"><button type="button" class="btn_inverse" ng-click="sideBarSearch()"><i class="sida_search_icon icon"></i></button></a></div>'
								+'<div class="side_search" style="text-align: center;"><a class="dj_bh" ><input type="text" ng-keydown="($event.which === 13)?sideBarSearch():0" ng-model="sideBarSearchId" placeholder="输入运单号" class="form_control form_kz fl" style="display:none;"><button type="button" class="btn_inverse" ng-click="clickShow()"><i class="sida_search_icon icon"></i></button></a></div>'
								+'<div class="side-bar">'
								+'<ul class="topnav menu-left-nest side_nav">'
								+bt
								//新增or删除
								+'<li class="li" style="text-align: left;" ng-show="!isEquels()"><a id="adel" class="dgnsy tooltip-tip ajax-load tooltipster-disable adel" href="javascript:void(0)" title="新增" ng-click="addTitle()"><i class="navicon_g6 icon"></i><span>新增</span></a></li>'
								+'<li class="li" style="text-align: left;" ng-show="isEquels()"><a id="del" class="dgnsy tooltip-tip ajax-load tooltipster-disable adel" href="javascript:void(0)" title="删除" ng-click="delTitle()"><i class="navicon_g7 icon"></i><span>删除</span></a></li>'
								+'</ul>'
								+'</div>'
								+'</div>'
								+'</div>';
					element.html(template);
					$compile(element.contents())($scope);
					sideBarInit();
				});
			};
			$scope.updateTitle();
		};
	};
	return mySidebarShortcut;
}]);




/**
 * 查询新闻信息
 */
indexLabel.directive('myNews',['commonService',function(commonService){
	var myNews = {};
	myNews.restrict="AE";
	myNews.scope={};
	myNews.compile=function(element,attrs,$scope){
		
		
		return function($scope,element,attrs){
			var title=attrs.myNews;
			var params=attrs.params;
			if(params==undefined || params=="null"){
				params="1";
			}
			commonService.postUrl("newsBO.ajax?cmd=queryNews",{"page":"1","rows":"10","type":params},function(data){
				var li="";
				var items=data.rows;
				var typeName="新闻";
				if(params=="2"){
					typeName="公告";
				}
				
				for(var index in items){
					li=li+'<li class="main_list_li"><span class="fl">['+typeName+']</span><a href="javascript:void(0);" onclick="openIndex(\'news_'+items[index].id+'\',\''+typeName+'\',\'/news/newsDetail.html?id='+items[index].id+'\')">'+items[index].title+'</a></li>';
				}
				var template='<div style="width:100%">'
            		+'<div class="main_yybb border_r1">'
            		+'<div class="title clearfix"><i class="m_jt fl"></i><h2>'+title+'</h2></div>'
            		+'<i class="function_icon icon"><a class="function_icon_li" href="#"></a></i>'
            		+'<ul class="main_list ellipsis a_w_b80">'
            		+li
            		+'</ul>'
//            		+'<a class="more fr" href="#">查看更多  &gt;&gt;</a>'
            		+'</div>'
            		+'</div>';
		
				element.html(template);
			});
		};
	};
	return myNews;
}]);
