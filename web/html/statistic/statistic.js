var specialChartId = getQueryString("chartId");// 支持菜单配置
var reportPageSize = getQueryString("pagesize");
var condition = getQueryString("condition")==undefined?"":getQueryString("condition");
if(reportPageSize==undefined||reportPageSize==""){
	reportPageSize = 12;
}
var MyApp = angular.module('MyApp', [ 'commonApp' ]);
Date.prototype.Format = function(fmt) { // author: meizz
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}

$(function(){
	$(".cplx").hover(function(){
		$(this).find("span").show();
	},function(){
		$(this).find("span").hide();
	});
});

function GetRequest(fromUrl) {   
	   var url = location.search; //获取url中"?"符后的字串   
	   if(fromUrl!=undefined && fromUrl!=""){
		   url = fromUrl.substring(fromUrl.indexOf("?"));
	   }
	   var theRequest = new Object();   
	   if (url.indexOf("?") != -1) {   
	      var str = url.substr(1);   
	      strs = str.split("&");   
	      for(var i = 0; i < strs.length; i ++) {   
	         theRequest[strs[i].split("=")[0]]= unescape(strs[i].split("=")[1]);   
	      }   
	   }   
	   return theRequest;   
	}  

function parseParam(param, key){
    var paramStr="";
    if(param instanceof String||param instanceof Number||param instanceof Boolean){
        paramStr+="&"+key+"="+escape(param);
    }else{
        $.each(param,function(i){
            var k=key==null?i:key+(param instanceof Array?"["+i+"]":"."+i);
            paramStr+='&'+parseParam(this, k);
        });
    }
    return paramStr.substr(1);
};

// 表格标签
MyApp.directive('reportTable',['$http','commonService', function($http, commonService){
	var directiveInner = {};
	directiveInner.restrict = "E";
	directiveInner.replace = true;
	directiveInner.compile = function(element, attrs) {
		var outterScopeObject = 'outterScopeObject';// 暴露给外部的对象
		if(undefined != attrs.outterScopeObject){
			outterScopeObject = attrs.outterScopeObject;
		}
		var innerObject = {
			isShow : false,
			tableInfo: {
				title: '',
				heads: [],// 表头
				results: [],// 数据
				foots : [],// 脚注
				exportTableDataInfo: undefined, // 获得此表格的相关条件，这些对应可用于分页展示和导出数据
				dbClickFunc : undefined,
				userOptions : undefined, // 用户选项数据
				id: '',
				colWidth: 0,// 列宽
				rowWidth: 0,// 行宽
			},
			pagination : {
				pageSize : reportPageSize,
				pages : [],
				currentPage : 1,
				total : 200,
				totalpage : 0,
				initCurrentPage : function(){
					this.currentPage = 1;
				},
				initPage : function(total){
					this.total = total;
					this.totalpage = Math.floor(this.total / this.pageSize) + (this.total % this.pageSize == 0 ? 0 : 1);
					this.refreshPage();
				},
				refreshPage : function(){
					var preHidePageObject = undefined;
					var nextHidePageObject = undefined;
					var array = new Array();
					var showRecentPageAmount = 2;
					for(var i = 1; i <= this.totalpage; i++){
						if(i == 1 || i == this.totalpage || i == this.currentPage){// 第一页，最后，或者当前页都需要显示
							array.push({ value : i , canClick : true});
						}else if(i < this.currentPage && i + showRecentPageAmount < this.currentPage){
							if(undefined == preHidePageObject){
								preHidePageObject = { value : '...' , canClick : false};
								array.push(preHidePageObject);
							}
						}else if (i > this.currentPage && i > showRecentPageAmount + this.currentPage){
							if(undefined == nextHidePageObject){
								nextHidePageObject = { value : '...' , canClick : false};
								array.push(nextHidePageObject);
							}
						}else{
							array.push({ value : i , canClick : true});
						}
					}
					this.pages = array;
				},
				selectPrevious : function(isBatchQueryFlag){
					if(this.pages.length == 0 || this.currentPage == 1)
						return;
					this.currentPage = this.currentPage -1;
					this.refreshPage();
					this.loadPageData(isBatchQueryFlag);
				},
				selectNext : function(isBatchQueryFlag){
					if(this.pages.length == 0 || this.currentPage == this.pages[this.pages.length -1].value)
						return;
					this.currentPage = this.currentPage + 1;
					this.refreshPage();
					this.loadPageData(isBatchQueryFlag);
				},
				selectPage : function(page,isBatchQueryFlag){
					if(!page.canClick){
						return;
					}
					if(this.currentPage == page.value)
						return;
					this.currentPage = page.value;
					this.refreshPage();
					this.loadPageData(isBatchQueryFlag);
				},
				isActive : function(page){
					return this.currentPage == page.value;
				},
				noPrevious : function(){
					return this.currentPage == 1;
				},
				noNext : function(){
					return this.pages.length == 0 ? true : this.currentPage == this.pages[this.pages.length -1].value;
				},
				loadPageData : function(isBatchQueryFlag){
					if(isBatchQueryFlag==1){
						var exportTableDataInfo = innerObject.tableInfo.exportTableDataInfo;
						exportTableDataInfo.pageSize = this.pageSize;
						exportTableDataInfo.currentPage = this.currentPage;
						var queryObject = {
							method  : 'POST',
							data 	: exportTableDataInfo,
							url  	: 'statisticBO.ajax?cmd=showSubStatisticInfoBatchQuery'
						};
						var successFun = function(data) {
							if(data.resultCode == '1') {
								innerObject.innerEvents.refreshTableInfoBatch(data.resultData);
							}
						}
						var successFunClean = function(data) {
							if(data.resultCode == '1') {
								innerObject.innerEvents.refreshTableInfoBatch(data.resultData,1);
							}
						}
						//reportPageSize每页数据   item.currentPage当前是哪一页
						var batchQuery = Math.ceil(parseInt(this.pageSize)/50);
						for (var i=1;i<=batchQuery;i++){
							exportTableDataInfo.currentBatchPage = i;
							if(i==1){
								commonService.postUrl('statisticBO.ajax?cmd=showSubStatisticInfoBatchQuery', exportTableDataInfo, successFunClean);
							}
							else
							{
								commonService.postUrl('statisticBO.ajax?cmd=showSubStatisticInfoBatchQuery', exportTableDataInfo, successFun);
							}
						};
					}
					else
					{
						var exportTableDataInfo = innerObject.tableInfo.exportTableDataInfo;
						exportTableDataInfo.pageSize = this.pageSize;
						exportTableDataInfo.currentPage = this.currentPage;
						var queryObject = {
							method  : 'POST',
							data 	: exportTableDataInfo,
							url  	: 'statisticBO.ajax?cmd=showSubStatisticInfo'
						};
						var successFun = function(data) {
							if(data.resultCode == '1') {
								innerObject.innerEvents.refreshTableInfo(data.resultData);
							}
							if(data.resultCode == '2'){
								alert("登录会话超时");
								window.location.href="/index.html";
							}
							if(data.resultCode == '0'){
								alert(data.resultMessage);
							}
						}
						commonService.postUrl('statisticBO.ajax?cmd=showSubStatisticInfo', exportTableDataInfo, successFun);
					}
				}
			},
			innerEvents: {
				exportTableData: function(){
					if(innerObject.pagination.total <= 0){
						alert('没有数据可以导出');
						return false;
					}
					var url = "statisticBO.ajax?cmd=exportTableData";
			        jQuery.each(innerObject.tableInfo.exportTableDataInfo, function(key, val){
			        	if('macroVariablesParams' == key || 'pointUserOptions' == key || 'sqlQueryParams' == key){
			        		jQuery.each(val, function(key1, val1){
			        			if(typeof(val1) == 'string' && val1.indexOf("/")>=0){
			        				val1 = encodeURIComponent(val1);
				        		}
			        			url += '&' + key +'['+ key1 +']=' +  val1;
			        		});
			        	}else{
			        		if(typeof(val) == 'string' && val.indexOf("/")>=0){
			        			val = encodeURIComponent(val);
			        		}
			        		url += ('&' + key +'=' +  val);
			        	}
			        });   
			        var toUrl = signUrl(url);
			        var iframe = document.createElement("iframe");
				    iframe.id = "statisticFrameDownloading";
				    iframe.src = encodeURI(toUrl);
				    iframe.style.display = "none";
				    document.body.appendChild(iframe);
				},
				convertNormalArrayToAngularFomartArray: function(tempCategories){
					var categories = new Array();
					for(var i = 0; undefined != tempCategories && i < tempCategories.length; i++){
						var tempObj = {
							value : tempCategories[i]
						}
						categories.push(tempObj);
					}
					return categories;
				},
				refreshTableInfo: function(data){
					// 处理报个的行数
					var heads = innerObject.innerEvents.convertNormalArrayToAngularFomartArray(data.chartInfo.categories);
					innerObject.tableInfo.userOptions = data.tableInfo.userOptions;// 原始数据
					innerObject.tableInfo.title = data.chartInfo.chartTitle;// 图表标题
					// 表格头信息
					innerObject.tableInfo.heads = heads;// 标题
					// 表格页脚信息
					innerObject.tableInfo.foots = innerObject.innerEvents.convertNormalArrayToAngularFomartArray(data.tableInfo.tableFoots);// 页脚
					// 表格结果行
					innerObject.tableInfo.results = data.tableInfo.tableResultData;// 数据
					// 分页数据
					innerObject.pagination.initPage(data.tableInfo.tableDataTotal);// 分页处理
					innerObject.tableInfo.exportTableDataInfo = data.exportTableDataInfo;// 获得此表格的相关条件，这些对应可用于分页展示和导出数据
//					var id = window.top.getCurrentOpenId();
//					// iframe的宽度
//					var iframeWidth = window.top.document.getElementById('tab_id_'+id).scrollWidth;
//					var colWidth = iframeWidth / heads.length;
//					if (colWidth < 140) {// 煤
//						colWidth = 140;
//						iframeWidth = heads.length * colWidth;
//					}
//					innerObject.tableInfo.colWidth = colWidth;
//					innerObject.tableInfo.rowWidth = iframeWidth
				},
				refreshTableInfoBatch: function(data,cleanFlag){
					if(innerObject.tableInfo.results!=undefined){
						if(cleanFlag==1){
							innerObject.tableInfo.results = data.tableInfo.tableResultData;// 数据
						}
						else
						{
							innerObject.tableInfo.results = innerObject.tableInfo.results.concat(data.tableInfo.tableResultData);// 数据
						}
					}
					else
					{
						innerObject.tableInfo.results = data.tableInfo.tableResultData;// 数据
					}
				},
				dbClickTableRow: function($index) {
					if(undefined == innerObject.tableInfo.userOptions || 'dbclick' != innerObject.tableInfo.userOptions.eventName) {
						return;
					}
					var associateChartIds = innerObject.tableInfo.userOptions.associateChartIds;
					if (undefined == associateChartIds || null == associateChartIds) {
						return;
					}
					
					if (innerObject.tableInfo.dbClickFunc != undefined && typeof innerObject.tableInfo.dbClickFunc == 'function')
						innerObject.tableInfo.dbClickFunc(innerObject, $index);
				}
			},
			outterEvents: {
				refreshTableInfo: function(data, dbClickFunc){
					innerObject.pagination.currentPage = 1;
					innerObject.innerEvents.refreshTableInfo(data);
					innerObject.isShow = true;
					innerObject.tableInfo.dbClickFunc = dbClickFunc;
				},
				refreshTableInfoBatch: function(data, dbClickFunc,cleanFlag){
					innerObject.innerEvents.refreshTableInfoBatch(data,cleanFlag);
				},
				closeTable: function(){
					innerObject.isShow = false;
					innerObject.pagination.currentPage = 1;
				},
				convertRowDataToArrayWithIndex: function(index){
					var rowData = innerObject.tableInfo.results[index].row;
					var newRowData = new Array();
					if(rowData == undefined || rowData == null)
						return newRowData;
					for (var i = 0; i < rowData.length; i++) {
						newRowData.push(rowData[i].value);
					}
					return newRowData;
				}
			}
		};
		var html = ''
			+ '<div ng-show="' + outterScopeObject + '.isShow" id="{{' + outterScopeObject + '.tableInfo.id}}">'
				+ '<div style="margin: 25px 0px; position:relative;">'
					+ '<h2 style="text-align: center;color:#333333;font-size:18px; line-height:30px; font-family: \'Lucida Grande\',\'Lucida Sans Unicode\',Arial,Helvetica,sans-serif ">{{' + outterScopeObject + '.tableInfo.title}}</h2>'
					+ '<a class="btn btn-sm btn-success" style="position:absolute; right:16px; top:0px;" href="javascript:void(0)" ng-click="' + outterScopeObject + '.innerEvents.exportTableData();">导出结果</a>'
				+ '</div>'
				+ '<div style=" min-height: 318px; position: relative;border:1px solid #e0e5e9; overflow: auto;">'
					+ '<div style=" width:100%;position:relative; background:#f4f7f8;">'
						+ '<table class="search_lista" border="0" cellspacing="0" cellpadding="0" style=" /*display:block;*/width: 100%;table-layout: auto;">'
							+ '<thead style="width:{{'+outterScopeObject+'.tableInfo.rowWidth}}px;/*display: block;*/">'
								+ '<tr>'
									+ '<th  style="/*float:left;*/" ng-repeat="head in ' + outterScopeObject + '.tableInfo.heads">{{head.value}}</th>'
								+ '</tr>'
							+ '</thead>'
						
	              			+ '<tbody style="width:{{'+outterScopeObject+'.tableInfo.rowWidth}}px;/*display: block;*/">'
	              				+ '<tr ng-class="{\'search_list_tr\':$index%2==1}" class="ng-cloak" ng-repeat="o in ' + outterScopeObject + '.tableInfo.results" ng-dblclick="' + outterScopeObject + '.innerEvents.dbClickTableRow($index)">'
	              					+ '<td style="/*float:left;*/"  class="hideString" ng-repeat="col in o.row" title="{{col.name}}"><a style="color: #0024ff"; href="#" ng-show="col.link" ng-click="openTab(col.name,col.linkName,col.link)">{{col.name}}</a><span ng-show="!col.link">{{col.name}}</span></td>'
	              				+ '</tr>'
	              				+ '<tr>'
	              					+ '<td style="/*float:left;*/"  class="hideString" ng-repeat="foot in ' + outterScopeObject + '.tableInfo.foots" title="{{foot.value}}"><span class="text_list hideString">{{foot.value}}</span></td>'
	              				+ '</tr>'
	              			+ '</tbody>'
	              		+ '</table>'
	              	+ '</div>'
	           + '</div>'
	           + '<div class="chey_b_1 list_fye clearfix">'
	           		+ '<div class="chey_xs fl ng-binding">'
	           			+ '<span style=" color:#ff7800;">&nbsp;&nbsp;共{{' + outterScopeObject + '.pagination.total}}条数据</span>'
	           		+ '</div>'
	           		+ '<ul class="pagination fr" ng-show="' + outterScopeObject + '.pagination.total > 0">'
	           			+ '<li ng-class="{disabled: ' + outterScopeObject + '.pagination.noPrevious()}"><a ng-click="' + outterScopeObject + '.pagination.selectPrevious(isBatchQueryFlag)">&laquo;</a>'
	           			+ '<li ng-repeat="page in ' + outterScopeObject + '.pagination.pages" ng-class="{active: ' + outterScopeObject + '.pagination.isActive(page)}"><a ng-click="' + outterScopeObject + '.pagination.selectPage(page,isBatchQueryFlag)">{{page.value}}</a>'
	           			+ '<li ng-class="{disabled: ' + outterScopeObject + '.pagination.noNext()}"><a ng-click="' + outterScopeObject + '.pagination.selectNext(isBatchQueryFlag)">&raquo;</a>'
	           		+ '</ul>'
	           + '</div>'
           + '</div>'
          /**
			+ '<div class="chart" ng-show="' + outterScopeObject + '.isShow">'
				+ '<div style="margin: 25px 0px; position:relative;">'
					+ '<h2 style="text-align: center;color:#333333;font-size:18px; line-height:30px; font-family: \'Lucida Grande\',\'Lucida Sans Unicode\',Arial,Helvetica,sans-serif ">{{' + outterScopeObject + '.tableInfo.title}}</h2>'
					+ '<a class="btn btn-sm btn-success" style="position:absolute; right:16px; top:0px;" href="javascript:void(0)" ng-click="' + outterScopeObject + '.innerEvents.exportTableData();">导出结果</a>'
				+ '</div>'
				+ '<div class="table-products p_25" >'
					+ '<div class="row">'
						+ '<table class="table table-hover">'
							+ '<thead>'
								+ '<tr>'
									+ '<th ng-repeat="head in ' + outterScopeObject + '.tableInfo.heads">{{head.value}}</th>'
								+ '</tr>'
							+ '</thead>'
							+ '<tbody>'
								+ '<tr ng-repeat="o in ' + outterScopeObject + '.tableInfo.results" ng-cloak class="ng-cloak" ng-dblclick="' + outterScopeObject + '.innerEvents.dbClickTableRow($index)">'
									+ '<td class="description" ng-repeat="col in o.row">{{ col.name }}</td>'
								+ '</tr>'
							+ '</tbody>'
							+ '<tfoot ng-show="' + outterScopeObject + '.tableInfo.foots != undefined">'
								+ '<tr>'
									+ '<th style="border-top: 0px;" ng-repeat="foot in ' + outterScopeObject + '.tableInfo.foots">{{foot.value}}</th>'
								+ '</tr>'
							+ '</tfoot>'
						+ '</table>'
					+ '</div>'
				+ '</div>'
				+ '<div class="chey_b ng-cloak" style="margin: 10px 15px 10px 10px;">'
					+ '<div class="chey_xs"><span style=" color:#ff7800;">&nbsp;&nbsp;共{{' + outterScopeObject + '.pagination.total}}条数据</span></div>'
	        		+ '<ul class="pagination pull-right">'
	            		+ '<li ng-class="{disabled: ' + outterScopeObject + '.pagination.noPrevious()}"><a ng-click="' + outterScopeObject + '.pagination.selectPrevious()">&laquo;</a>'
	            		+ '<li ng-repeat="page in ' + outterScopeObject + '.pagination.pages" ng-class="{active: ' + outterScopeObject + '.pagination.isActive(page)}"><a ng-click="' + outterScopeObject + '.pagination.selectPage(page)">{{page.value}}</a>'
	            		+ '<li ng-class="{disabled: ' + outterScopeObject + '.pagination.noNext()}"><a ng-click="' + outterScopeObject + '.pagination.selectNext()">&raquo;</a>'
	            	+ '</ul>'
	            + '</div>'
            + '</div>'
            */
		element.html(html);
		return function($scope, element, attrs){
			innerObject.tableInfo.id = (new Date()).valueOf();
			eval("$scope." + outterScopeObject + "=innerObject");
		};
	};
	return directiveInner;
}]);
// 选项卡标签
MyApp.directive('navTabs',['$http','commonService', function($http, commonService){// 弹出框
	var directiveInner = {};
	directiveInner.restrict = "E";
	directiveInner.replace = true;
	directiveInner.compile = function(element, attrs) {
		var outterScopeObject = 'navTabs';// 暴露给外部的对象
		if(undefined != attrs.outterScopeObject) {
			outterScopeObject = attrs.outterScopeObject;
		}
		var innerObject = {
			objectName: outterScopeObject,
			originalItem: undefined,
			tabs: [],// 标签页数据
			defaultTab: null, // 默认标签页数据
			currentActiveTab: null,
			successLoadTabRelatChartCallback: undefined,// 成功加载tab关联的图表回调方法
			preLoadTabRelatChartCallback: undefined,// 家在前的判断
			innerEvents: {
				onClickTab: function(tab){// 点击标签页事件
					if(undefined != innerObject.currentActiveTab && null != innerObject.currentActiveTab && innerObject.currentActiveTab.tabValue == tab.tabValue){
						return;
					}
					innerObject.innerEvents.clickTab(tab);
				},
				clickTab: function(tab){
					if(tab.tabValue == innerObject.currentActiveTab.tabValue) {
						return;
					}
					innerObject.innerEvents.loadSubStatisticInfo(tab, function(successTab){
						for(var i = 0; undefined != successTab && undefined != innerObject.tabs && i < innerObject.tabs.length; i++){
							if(successTab.tabValue == innerObject.tabs[i].tabValue){
								innerObject.tabs[i].tabActive = 'active';
								innerObject.currentActiveTab = innerObject.tabs[i];
							}else{
								innerObject.tabs[i].tabActive = '';
							}
						}
					});
				},
				setTabs: function(tabs, item, successLoadTabRelatChartCallback, preLoadTabRelatChartCallback){
					innerObject.tabs = tabs;
					innerObject.defaultTab = tabs[0];
					innerObject.currentActiveTab = tabs[0];
					innerObject.originalItem = item;
					innerObject.successLoadTabRelatChartCallback = successLoadTabRelatChartCallback;
					innerObject.preLoadTabRelatChartCallback = preLoadTabRelatChartCallback; 
					innerObject.innerEvents.loadSubStatisticInfo(tabs[0], function(successTab){
					});
				},
				loadSubStatisticInfo: function(tab, callback) {
					if(innerObject.preLoadTabRelatChartCallback(tab)){
						callback(tab);
						return;
					};
					var item = innerObject.originalItem;
					item.chartId = tab.tabValue;
					var queryObject = {
						method  : 'POST',
						data 	: item,
					};
					var successFun = function(data) {
						if(data.resultCode == '0'){
							alert(data.resultMessage);
							return;
						}
						if (data.resultData.checkInfo != undefined && data.resultData.checkInfo.resultCode != '1'){
							alert(data.resultData.checkInfo.resultMessage);
							return;
						}
						if(data.resultCode == '1' && innerObject.successLoadTabRelatChartCallback != undefined) {
							innerObject.successLoadTabRelatChartCallback(data.resultData, tab);
							if(callback != undefined)
								callback(tab);// 设置点击的选项卡栏为活动状态
						}
						if(data.resultCode == '2'){
							alert("登录会话超时");
							window.location.href="/index.html";
						}
					}
					commonService.postUrl('statisticBO.ajax?cmd=showSubStatisticInfo', item, successFun);
				}
			},
			outterEvents : {
				setTabs : function(tabs, item, successLoadTabRelatChartCallback, preLoadTabRelatChartCallback){// 设置标签页数据
					innerObject.innerEvents.setTabs(tabs, item, successLoadTabRelatChartCallback, preLoadTabRelatChartCallback);
				}
			}
		}
		var html = ''
			+ '<ul class="nav nav-tabs">'
				+ '<li role="presentation" ng-repeat="tab in ' + outterScopeObject + '.tabs" ng-class="tab.tabActive" ng-click="' + outterScopeObject + '.innerEvents.onClickTab(tab)"><a href="javascript:void(0);" style="outline:none">{{tab.tabName}}</a></li>'
			+ '</ul>';
		element.html(html);
		return function($scope, element, attrs){
			eval("$scope." + outterScopeObject + "=innerObject");
		};
	};
	return directiveInner;
}]);

MyApp.directive('statisticDetailInfo',['$http','commonService', '$compile', function($http, commonService, $compile) {
	var directiveInner = {};
	directiveInner.restrict = "E";
	directiveInner.replace = true;
	directiveInner.templateUrl = function(element, attrs) {
		return attrs.templateUrl;
	};
	directiveInner.compile = function(element, attrs){
		var innerObject = {
			isShow : false,
			detailInfo: undefined,
			outterEvents: {
				refreshDetailInfo: function(data){
					innerObject.detailInfo = data.detailInfo; 
				},
				closeTable: function(){
					innerObject.isShow = false;
					innerObject.detailInfo = undefined;
				}
			}
		};
		return function($scope, element, attrs){
			var scopeObject = "scopeObject"
			if(undefined != attrs.scopeObject) {
				scopeObject = attrs.scopeObject;
			}
			eval("$scope." + scopeObject + "=innerObject");
		};
	};
	return directiveInner;
}]);

MyApp.controller('testCtrl', ['$scope', '$http', "commonService",'$sce', function($scope, $http, commonService, $sce) {
	
	$scope.isShowBackButton = true;// 是否现实返回按钮 --> 菜单进入的话，不需要现实返回按钮
	/*********************其它公共方法部分******************************Start*****/
	$scope.getChartTypeFromResponseData = function(response){//从响应数据中获取图表类型
		return response.chartInfo.chartType;
	}
	
	$scope.getSubChartTypeFromResponseData = function(response){//从响应数据详细信息的子类型(订单详情/货源详情)
		return response.chartInfo.subType;
	}
	
	/*********************其它公共方法部分******************************End*******/

	/********************************多级图表展示部分******Start***********************************/
	$scope.seconedLevelPopupWindow = {// 二级弹出框
		window: {// 控制弹出空窗口展示
			displayClass: 'dispaly_none',
			show: function(){
				this.displayClass = 'dispaly_block';
			},
			close: function(){// 关闭窗口
				this.displayClass = 'dispaly_none';
				for(var i = 0; i < $scope.seconedLevelPopupWindow.subChart.chartObjects.length; i++){
					$scope.seconedLevelPopupWindow.subChart.closeChart($scope.seconedLevelPopupWindow.subChart.chartObjects[i]);
					$scope.seconedLevelPopupWindow.subChart.chartObjects[i].state = 0;
					$scope.seconedLevelPopupWindow.subChart.chartObjects[i].chartId = -1;
				}
			}
		},
		subChart: {
			chartObjects: [
			     // state : 0(未加载); 1(成功加载并渲染完成)
			    {type: 'highcharts', chart: $('#seconedLevelHighchartsContainer1'), state: 0, index: 1, chartId:-1},
			    {type: 'highcharts', chart: $('#seconedLevelHighchartsContainer2'), state: 0, index: 2, chartId:-1},
			    {type: 'highcharts', chart: $('#seconedLevelHighchartsContainer3'), state: 0, index: 3, chartId:-1},
			    {type: 'highcharts', chart: $('#seconedLevelHighchartsContainer4'), state: 0, index: 4, chartId:-1},
			    {type: 'highcharts', chart: $('#seconedLevelHighchartsContainer5'), state: 0, index: 5, chartId:-1},
			    {type: 'table', chart: undefined, state: 0, index: 6, chartId:-1},
			    {type: 'table', chart: undefined, state: 0, index: 7, chartId:-1},
			    {type: 'table', chart: undefined, state: 0, index: 8, chartId:-1},
			    {type: 'table', chart: undefined, state: 0, index: 9, chartId:-1},
			    {type: 'table', chart: undefined, state: 0, index: 10, chartId:-1},

			    // TODO 详情(实在没办法才酱紫做) Start
			    {type: 'orderDetailInfo', chart: undefined, state: 0, index: 10, chartId:-1},
			    {type: 'trackingDetailInfo', chart: undefined, state: 0, index: 10, chartId:-1},
			    // TODO 详情(实在没办法才酱紫做) End
			],
			showSubChart: function(resultData, chartType, tab){
				var chartObject = $scope.seconedLevelPopupWindow.subChart.getVailableChartObject(resultData, chartType);// 获取有效的图表对象
				$scope.buildStatisticChartCommonEntry(resultData, chartObject.chart);
				$scope.seconedLevelPopupWindow.subChart.postShowStatisticChart(chartObject, tab);
			},
			getVailableChartObject: function(resultData, chartType) {
				var type = 'highcharts';
				if (chartType == '7'){
					type = 'table';
				} else if (chartType == '8'){
					// TODO 详情(实在没办法才酱紫做) Start
					var subChartType = $scope.getSubChartTypeFromResponseData(resultData);
					if (subChartType == '1') {// 订单
						type = 'orderDetailInfo';
					} else if (subChartType == '2') {// 运单
						type = 'trackingDetailInfo';
					}
					// TODO 详情(实在没办法才酱紫做) End
				}
				
				var retChartObject = undefined;
				for(var i = 0; i < $scope.seconedLevelPopupWindow.subChart.chartObjects.length; i++) {
					var chartObject = $scope.seconedLevelPopupWindow.subChart.chartObjects[i];
					if (type == chartObject.type && chartObject.state == 0){// 获取还没有使用的图表对象
						retChartObject = chartObject;
						if(retChartObject != undefined && retChartObject.type == 'table' && retChartObject.chart == undefined) {
							if (chartObject.index == 6) {// 如果没有数据
								chartObject.chart = $scope.seconedLevelTable1;
							}else if (chartObject.index == 7) {
								chartObject.chart = $scope.seconedLevelTable2;
							}else if (chartObject.index == 8) {
								chartObject.chart = $scope.seconedLevelTable3;
							}else if (chartObject.index == 9) {
								chartObject.chart = $scope.seconedLevelTable4;
							}else if (chartObject.index == 10) {
								chartObject.chart = $scope.seconedLevelTable5;
							}
						}
						
						// TODO 详情(实在没办法才酱紫做) Start
						if(retChartObject != undefined && retChartObject.type == 'orderDetailInfo' && retChartObject.chart == undefined) {
							chartObject.chart = $scope.seconedLevelOrderDetailInfo;
						}
						if(retChartObject != undefined && retChartObject.type == 'trackingDetailInfo' && retChartObject.chart == undefined) {
							chartObject.chart = $scope.seconedLevelTrackingInfo;
						}
						// TODO 详情(实在没办法才酱紫做) End
						break;
					}
				}
				return retChartObject;
			},
			postShowStatisticChart: function(chartObject, tab){// 请求成功响应，并且图表渲染成功之后
				chartObject.chartId = tab.tabValue;
				chartObject.state = 1;// 渲染成功
				$scope.seconedLevelPopupWindow.subChart.hideOtherChart(chartObject);
				$scope.seconedLevelPopupWindow.subChart.showChart(chartObject);
			},
			preShowStatisticChart: function(tab) {// 点击
				var chartObject = undefined;
				for(var i = 0; i < $scope.seconedLevelPopupWindow.subChart.chartObjects.length; i++) {
					var object = $scope.seconedLevelPopupWindow.subChart.chartObjects[i];
					if (object.chartId == tab.tabValue){
						chartObject = object;
						break;
					}
				}
				if (undefined != chartObject && chartObject.state == 1) {// 如果当前选项卡对应的图表已经成功展示，则因此暂时，不再加载
					$scope.seconedLevelPopupWindow.subChart.hideOtherChart(chartObject);
					$scope.seconedLevelPopupWindow.subChart.showChart(chartObject);
					return true;
				}
				return false;
			}, 
			hideOtherChart: function(tab){
				for(var i = 0; i < $scope.seconedLevelPopupWindow.subChart.chartObjects.length; i++) {// 隐藏其它选项卡中的图表
					var object = $scope.seconedLevelPopupWindow.subChart.chartObjects[i];
					if (object.chartId != tab.tabValue){
						$scope.seconedLevelPopupWindow.subChart.hideChart(object);
					}
				}
			},
			showChart: function(chartObject){
				if (chartObject.chart == undefined)
					return;
				// TODO 详情
				if (chartObject.type == 'table' || chartObject.type == 'orderDetailInfo' || chartObject.type == 'trackingDetailInfo') {
					chartObject.chart.isShow = true;
				} else if (chartObject.type == 'highcharts') {
					chartObject.chart.show();
				}
			},
			hideChart: function(chartObject){
				if (chartObject.chart == undefined)
					return;
				// TODO 详情
				if (chartObject.type == 'table' || chartObject.type == 'orderDetailInfo' || chartObject.type == 'trackingDetailInfo') {
					chartObject.chart.isShow = false;
				} else if (chartObject.type == 'highcharts') {
					chartObject.chart.hide();
				}
			},
			closeChart: function(chartObject){
				if (chartObject.chart == undefined)
					return;
				// TODO 详情
				if (chartObject.type == 'table' || chartObject.type == 'orderDetailInfo' || chartObject.type == 'trackingDetailInfo') {
					chartObject.chart.outterEvents.closeTable();
				} else if (chartObject.type == 'highcharts') {
					chartObject.chart.hide();
				}
			}
		},
		successLoadTabRelatChartCallback: function(resultData,tab){// 提供给tab的回调方法
			$scope.seconedLevelPopupWindow.subChart.showSubChart(resultData, $scope.getChartTypeFromResponseData(resultData), tab);
		}
	}
	
	$scope.thirdLevelPopupWindow = {// 三级弹出框
		window: {// 控制弹出空窗口展示
			displayClass: 'dispaly_none',
			show: function(){
				this.displayClass = 'dispaly_block';
			},
			close: function(){// 关闭窗口
				this.displayClass = 'dispaly_none';
				for(var i = 0; i < $scope.thirdLevelPopupWindow.subChart.chartObjects.length; i++){
					$scope.thirdLevelPopupWindow.subChart.closeChart($scope.thirdLevelPopupWindow.subChart.chartObjects[i]);
					$scope.thirdLevelPopupWindow.subChart.chartObjects[i].state = 0;
					$scope.thirdLevelPopupWindow.subChart.chartObjects[i].chartId = -1;
				}
			}
		},
		subChart: {
			chartObjects: [
			     // state : 0(未加载); 1(成功加载并渲染完成)
			    {type: 'highcharts', chart: $('#thirdLevelHighchartsContainer1'), state: 0, index: 1, chartId:-1},
			    {type: 'highcharts', chart: $('#thirdLevelHighchartsContainer2'), state: 0, index: 2, chartId:-1},
			    {type: 'highcharts', chart: $('#thirdLevelHighchartsContainer3'), state: 0, index: 3, chartId:-1},
			    {type: 'highcharts', chart: $('#thirdLevelHighchartsContainer4'), state: 0, index: 4, chartId:-1},
			    {type: 'highcharts', chart: $('#thirdLevelHighchartsContainer5'), state: 0, index: 5, chartId:-1},
			    {type: 'table', chart: undefined, state: 0, index: 6, chartId:-1},
			    {type: 'table', chart: undefined, state: 0, index: 7, chartId:-1},
			    {type: 'table', chart: undefined, state: 0, index: 8, chartId:-1},
			    {type: 'table', chart: undefined, state: 0, index: 9, chartId:-1},
			    {type: 'table', chart: undefined, state: 0, index: 10, chartId:-1},

			    // TODO 详情(实在没办法才酱紫做) Start
			    {type: 'orderDetailInfo', chart: undefined, state: 0, index: 10, chartId:-1},
			    {type: 'trackingDetailInfo', chart: undefined, state: 0, index: 10, chartId:-1},
			    // TODO 详情(实在没办法才酱紫做) End
			],
			showSubChart: function(resultData, chartType, tab){
				var chartObject = $scope.thirdLevelPopupWindow.subChart.getVailableChartObject(resultData, chartType);// 获取有效的图表对象
				$scope.buildStatisticChartCommonEntry(resultData, chartObject.chart);
				$scope.thirdLevelPopupWindow.subChart.postShowStatisticChart(chartObject, tab);
			},
			getVailableChartObject: function(resultData, chartType) {
				var type = 'highcharts';
				if (chartType == '7'){
					type = 'table';
				} else if (chartType == '8'){
					// TODO 详情(实在没办法才酱紫做) Start
					var subChartType = $scope.getSubChartTypeFromResponseData(resultData);
					if (subChartType == '1') {// 订单
						type = 'orderDetailInfo';
					} else if (subChartType == '2') {// 运单
						type = 'trackingDetailInfo';
					}
					// TODO 详情(实在没办法才酱紫做) End
				}
				
				var retChartObject = undefined;
				for(var i = 0; i < $scope.thirdLevelPopupWindow.subChart.chartObjects.length; i++) {
					var chartObject = $scope.thirdLevelPopupWindow.subChart.chartObjects[i];
					if (type == chartObject.type && chartObject.state == 0){// 获取还没有使用的图表对象
						retChartObject = chartObject;
						if(retChartObject != undefined && retChartObject.type == 'table' && retChartObject.chart == undefined) {
							if (chartObject.index == 6) {// 如果没有数据
								chartObject.chart = $scope.thirdLevelTable1;
							}else if (chartObject.index == 7) {
								chartObject.chart = $scope.thirdLevelTable2;
							}else if (chartObject.index == 8) {
								chartObject.chart = $scope.thirdLevelTable3;
							}else if (chartObject.index == 9) {
								chartObject.chart = $scope.thirdLevelTable4;
							}else if (chartObject.index == 10) {
								chartObject.chart = $scope.thirdLevelTable5;
							}
						}
						
						// TODO 详情(实在没办法才酱紫做) Start
						if(retChartObject != undefined && retChartObject.type == 'orderDetailInfo' && retChartObject.chart == undefined) {
							chartObject.chart = $scope.thirdLevelOrderDetailInfo;
						}
						if(retChartObject != undefined && retChartObject.type == 'trackingDetailInfo' && retChartObject.chart == undefined) {
							chartObject.chart = $scope.thirdLevelTrackingInfo;
						}
						// TODO 详情(实在没办法才酱紫做) End
						break;
					}
				}
				return retChartObject;
			},
			postShowStatisticChart: function(chartObject, tab){// 请求成功响应，并且图表渲染成功之后
				chartObject.chartId = tab.tabValue;
				chartObject.state = 1;// 渲染成功
				$scope.thirdLevelPopupWindow.subChart.hideOtherChart(chartObject);
				$scope.thirdLevelPopupWindow.subChart.showChart(chartObject);
			},
			preShowStatisticChart: function(tab) {// 点击
				var chartObject = undefined;
				for(var i = 0; i < $scope.thirdLevelPopupWindow.subChart.chartObjects.length; i++) {
					var object = $scope.thirdLevelPopupWindow.subChart.chartObjects[i];
					if (object.chartId == tab.tabValue){
						chartObject = object;
						break;
					}
				}
				if (undefined != chartObject && chartObject.state == 1) {// 如果当前选项卡对应的图表已经成功展示，则因此暂时，不再加载
					$scope.thirdLevelPopupWindow.subChart.hideOtherChart(chartObject);
					$scope.thirdLevelPopupWindow.subChart.showChart(chartObject);
					return true;
				}
				return false;
			}, 
			hideOtherChart: function(tab){
				for(var i = 0; i < $scope.thirdLevelPopupWindow.subChart.chartObjects.length; i++) {// 隐藏其它选项卡中的图表
					var object = $scope.thirdLevelPopupWindow.subChart.chartObjects[i];
					if (object.chartId != tab.tabValue){
						$scope.thirdLevelPopupWindow.subChart.hideChart(object);
					}
				}
			},
			showChart: function(chartObject){
				if (chartObject.chart == undefined)
					return;
				// TODO 详情
				if (chartObject.type == 'table' || chartObject.type == 'orderDetailInfo' || chartObject.type == 'trackingDetailInfo') {
					chartObject.chart.isShow = true;
				} else if (chartObject.type == 'highcharts') {
					chartObject.chart.show();
				}
			},
			hideChart: function(chartObject){
				if (chartObject.chart == undefined)
					return;
				// TODO 详情
				if (chartObject.type == 'table' || chartObject.type == 'orderDetailInfo' || chartObject.type == 'trackingDetailInfo') {
					chartObject.chart.isShow = false;
				} else if (chartObject.type == 'highcharts') {
					chartObject.chart.hide();
				}
			},
			closeChart: function(chartObject){
				if (chartObject.chart == undefined)
					return;
				// TODO 详情
				if (chartObject.type == 'table' || chartObject.type == 'orderDetailInfo' || chartObject.type == 'trackingDetailInfo') {
					chartObject.chart.outterEvents.closeTable();
				} else if (chartObject.type == 'highcharts') {
					chartObject.chart.hide();
				}
			}
		},
		successLoadTabRelatChartCallback: function(resultData,tab){// 提供给tab的回调方法
			$scope.thirdLevelPopupWindow.subChart.showSubChart(resultData, $scope.getChartTypeFromResponseData(resultData), tab);
		}
	}
	
	$scope.dbClickTableRow = function(tableObj, $index){// 双击table表格
		$scope.showPopupWindow(tableObj.tableInfo.userOptions, $index, function(item){
			item.rowData = tableObj.outterEvents.convertRowDataToArrayWithIndex($index);
			return item;
		});
	}
	
	$scope.clickChartMethod = function(pointUserOptions, xIndex){// 点击highcharts上的点(曲线点、饼图扇区等)
		var associateChartIds = pointUserOptions.associateChartIds;
		if (undefined == associateChartIds || null == associateChartIds) {
			return;
		}
		$scope.showPopupWindow(pointUserOptions, xIndex);
	}

	/** 
	 * 展示弹出框
	 * userOptions : 
	 * 		-> highcharts曲线/柱状图，userOptions代表series元数据
	 * 		-> highcharts饼图，userOptions代表扇区元数据
	 * 		-> table表格，userOptions代表图表元数据
	 */
	$scope.showPopupWindow = function(userOptions, xindex, callback){
		var tabs = $scope.getTabsFormUserOptions(userOptions);
		if (tabs.length == 0) {// 说明没有配置配置关联的图表
			return;
		}
		var item = {// 加载关联图表的参数
			pointUserOptions: userOptions,
			chartId: tabs[0].tabValue,
			pageSize: 12,
			currentPage: 1,
			xIndex: xindex
		};
		if (undefined != callback && typeof callback == 'function') {
			item = callback(item);
		}
		if (item == undefined)
			return;
		if ($scope.seconedLevelPopupWindow.window.displayClass == 'dispaly_none') {// 二级菜单处于隐藏状态，则弹出二级菜单
			$scope.seconedLevelNavTabs.outterEvents.setTabs(tabs, item, $scope.seconedLevelPopupWindow.successLoadTabRelatChartCallback, $scope.seconedLevelPopupWindow.subChart.preShowStatisticChart);
			$scope.seconedLevelPopupWindow.window.show();
		} else if ($scope.thirdLevelPopupWindow.window.displayClass == 'dispaly_none') {
			$scope.thirdLevelNavTabs.outterEvents.setTabs(tabs, item, $scope.thirdLevelPopupWindow.successLoadTabRelatChartCallback, $scope.thirdLevelPopupWindow.subChart.preShowStatisticChart);
			$scope.thirdLevelPopupWindow.window.show();
		}
	}
	
	$scope.getTabsFormUserOptions = function(userOptions){// 从userOptions中获取选项卡数据
		var tabs = new Array();
		var ids = userOptions.associateChartIds;
		var names = userOptions.associateChartNames;
		for (var i = 0; i < ids.length; i++) {
			tabs.push({
				tabValue: ids[i],
				tabName: names[i],
				tabActive: (i == 0 ? 'active' : '')
			});
		}
		return tabs;
	}
	
	/********************************多级图表展示部分******End***********************************/
	$scope.statisticItems = [];// 统计项数组
	$scope.statisticItemRowIndexs;
	$scope.userRetentionChartInfosCategories = [];
	$scope.userRetentionSeriesInfos = [];
	$scope.lastClickChartIndex = '';
	$scope.loadControl = { // 统计图表加载控制对象
		isShowLatitude : false,
		isShowOrgSelect : false,
		isShowTenandSelect : false,
		isShowLatitudeTypeSelect : false,
		currentShowChartId : 0,
		associateLatitudeInfo : [],
		selectedLatitudeId : 0,
		selectedLatitudeType : 0,
		multiMonthStatistic : [],
		multiMonthCompare : [],
		defaultOrgId : undefined,
		theFirstClickItem : true,
		showMain : true
	};
	
	$scope.changeSelectedLatitudeId = function(){// 纬度选择
		for(var i = 0; i < $scope.loadControl.associateLatitudeInfo.length; i++){
			if($scope.loadControl.associateLatitudeInfo[i].latitudeId == $scope.loadControl.selectedLatitudeId){
				$scope.loadControl.selectedLatitudeType = $scope.loadControl.associateLatitudeInfo[i].latitudeType;
				break;
			}
		}
		$scope.clearCheckBoxChecked("multiMonthStatistic");
		$scope.clearCheckBoxChecked("multiMonthCompare");
	}
	
	$scope.analysis = function(latitudeId,consignorName,carrierCompanyName,descOrgId,chartId,isLinked,defaultLatitudeType){// 响应"开始分析"按钮事件
		var selectedLatitudeTypeDate1 = document.getElementById("selectedLatitudeTypeDate1").value;
		var selectedLatitudeTypeDate2 = document.getElementById("selectedLatitudeTypeDate2").value;
		var selectedLatitudeTypeDate3 = document.getElementById("selectedLatitudeTypeDate3").value;
		var selectedLatitudeTypeDate4 = document.getElementById("selectedLatitudeTypeDate4").value;
		var selectedLatitudeTypeDate5 = document.getElementById("selectedLatitudeTypeDate5").value;
		
		var selectedOrg = $scope.orgSelect;
		var selectedTenant = $scope.tenantSelect;
		var param = {
			latitudeId : latitudeId!=undefined?latitudeId:$scope.loadControl.selectedLatitudeId,
			consignorName: consignorName!=undefined?consignorName:$scope.queryParams.consignorName,
			carrierCompanyName: carrierCompanyName!=undefined?carrierCompanyName:$scope.queryParams.carrierCompanyName,
			descOrgId: descOrgId!=undefined?descOrgId:$scope.queryParams.descOrgId,
			chartId:chartId!=undefined?chartId:undefined
		};
		if($scope.loadControl.selectedLatitudeType == 1 || $scope.loadControl.selectedLatitudeType == 2){
			if(selectedLatitudeTypeDate1 == undefined || "" == selectedLatitudeTypeDate1){
				alert('请选择需要统计的年份');
				return false;
			}
			param.queryMonth = selectedLatitudeTypeDate1;
			param.queryMonthType = "1";
		}else if($scope.loadControl.selectedLatitudeType == 3){
			if(selectedLatitudeTypeDate2 == undefined || "" == selectedLatitudeTypeDate2){
				alert('请选择需要统计的月份');
				return false;
			}
			param.queryMonth = selectedLatitudeTypeDate2;
			param.queryMonthType = "2";
		}else if($scope.loadControl.selectedLatitudeType == 4){
			if(selectedLatitudeTypeDate3 == undefined || "" == selectedLatitudeTypeDate3){
				alert('请选择需要统计的日期');
				return false;
			}
			param.queryMonth = selectedLatitudeTypeDate3;
			param.queryMonthType = "3";
		}else if($scope.loadControl.selectedLatitudeType == 7){
			if(selectedLatitudeTypeDate4 == undefined || "" == selectedLatitudeTypeDate4){
				alert('请选择需要统计的[开始日期]');
				document.getElementById("selectedLatitudeTypeDate4").focus();
				return false;
			}
			if(selectedLatitudeTypeDate5 == undefined || "" == selectedLatitudeTypeDate5){
				alert('请选择需要统计的[结束日期]');
				document.getElementById("selectedLatitudeTypeDate5").focus();
				return false;
			}
			param.queryMonthType = "4";
			param.customQueryStartTime = selectedLatitudeTypeDate4;
			param.customQueryEndTime = selectedLatitudeTypeDate5;
		} else if($scope.loadControl.selectedLatitudeType == 8){
			var multiMonthStatistic = $scope.getCheckBoxValue("multiMonthStatistic");
			if(multiMonthStatistic == ""){
				alert("请选择需要统计的月份");
				return false;
			}
			param.multiMonthStatistic=multiMonthStatistic;
		} else if($scope.loadControl.selectedLatitudeType == 9){
			var multiMonthCompare = $scope.getCheckBoxValue("multiMonthCompare");
			if(multiMonthCompare == ""){
				alert("请选择需要分析的月份");
				return false;
			}
			if(multiMonthCompare.split(",").length <= 1){
				alert("至少选择两个月份");
				return false;
			}
			param.multiMonthCompare=multiMonthCompare;
		}
		//动态查询条件设置
		if($scope.chartTableQueryIds!=undefined && $scope.chartTableQueryIds!=""){
			var temp = $scope.chartTableQueryIds;
			var tempValue="";
			for(var i=0;i<temp.length;i++){
				var selectedValue = $("#" + temp[i]).val();
				if(selectedValue==undefined || selectedValue==""){
					selectedValue = $("#" + temp[i]).find("option:selected").attr("value");
				}
				if(selectedValue!="-1" && selectedValue!=undefined){
					tempValue = selectedValue==""?"''":"'"+selectedValue+"'";
					eval("param." + temp[i] + "=" + tempValue);
				}
			}
		}
		var tempCondition ={param:param,selectedOrg:selectedOrg,selectedTenant:selectedTenant,selectedLatitudeType:$scope.loadControl.selectedLatitudeType};
		$scope.tempCondition = tempCondition;
		$scope.showStatisticInfo(param,false,$scope.loadControl.isShowLatitude,selectedOrg,selectedTenant,isLinked,latitudeId,defaultLatitudeType);
	}
	
	$scope.getCheckBoxValue = function(name){// 获取checkBox的值，多个值以“逗号”分隔
		var checkbox=document.getElementsByName(name); 
		var str = "";
		for(var i=0;i<checkbox.length;i++){
	        if(checkbox[i].checked){
	        	if(str.indexOf(checkbox[i].value) == -1){
	        		str += checkbox[i].value + ",";
	        	}
	        }
		}
		return str == "" ? "" : str.substring(0, str.length - 1);
	}
	
	$scope.clearCheckBoxChecked = function(name){// 设置checkBox的值，多个值以“逗号”分隔
		var checkbox=document.getElementsByName(name); 
		for(var i=0;i<checkbox.length;i++){
			checkbox[i].checked = false;
		}
	}
	
	$scope.getStatisticType = function(){ // 加载统计类别
		var queryObject = {
			method  : 'POST',
			data 	: {},
			url  	: 'statisticBO.ajax?cmd=getStatisticType'
		};
		var successFun = function(data) {
			if(data.resultCode == '1'){
				var statisticItemRow = Math.floor(data.resultData.length / 4) +  (data.resultData.length % 4 == 0 ? 0 : 1);
				$scope.statisticItemRowIndexs = new Array();
				for(var i = 0; i < statisticItemRow; i++){
					$scope.statisticItemRowIndexs.push(i);	
				}
				$scope.statisticItems = data.resultData;
			}
			if(data.resultCode == '2'){
				alert("登录会话超时");
				window.location.href="/index.html";
			}
			if(data.resultCode == '0'){
				alert(data.resultMessage);
			}
		}
		commonService.postUrl( 'statisticBO.ajax?cmd=getStatisticType',{},successFun);
	}
	
	$scope.showLatitudeInfo = function(item,latitudeId,defaultLatitudeType){
		var queryObject = {
			method  : 'POST',
			data 	: item,
			url  	: 'statisticBO.ajax?cmd=loadStatisticChartLatitude'
		};
		var successFun = function(data) {
			if(data.resultCode == '1'){
				if(undefined != data.isShowLatitude && data.isShowLatitude){
					$scope.loadControl.isShowLatitude = true;
				} else {
					$scope.loadControl.isShowLatitude = false;	
				}
				if(undefined != data.isShowOrgSelect && data.isShowOrgSelect){
					$scope.loadControl.isShowOrgSelect = true;
				} else {
					$scope.loadControl.isShowOrgSelect = false;	
				}
				if(undefined != data.isShowTenandSelect && data.isShowTenandSelect){
					$scope.loadControl.isShowTenandSelect = true;
				} else {
					$scope.loadControl.isShowTenandSelect = false;	
				}
				if(undefined != data.isShowLatitudeTypeSelect && data.isShowLatitudeTypeSelect){
					$scope.loadControl.isShowLatitudeTypeSelect = true;
				} else {
					$scope.loadControl.isShowLatitudeTypeSelect = false;	
				}
				$scope.loadControl.associateLatitudeInfo = data.associateLatitudeInfo;
				if(latitudeId!=undefined && latitudeId!=""){
					$scope.loadControl.selectedLatitudeId = parseInt(latitudeId);
				}
				else
				{
					$scope.loadControl.selectedLatitudeId = data.defaultLatitudeId;
				}
				if(defaultLatitudeType!=undefined && defaultLatitudeType!=""){
					$scope.loadControl.selectedLatitudeType = defaultLatitudeType
				}
				else
				{
					$scope.loadControl.selectedLatitudeType = data.defaultLatitudeType;
				}
				$scope.loadControl.multiMonthStatistic = data.multiMonthStatistic;
				if($scope.reqInfo.multiMonthStatistic!=undefined && $scope.reqInfo.multiMonthStatistic!=""){
					$scope.loadControl.multiMonthStatistic = $scope.reqInfo.multiMonthStatistic;
				}
				$scope.loadControl.multiMonthCompare = data.multiMonthCompare;
				if($scope.reqInfo.multiMonthCompare!=undefined && $scope.reqInfo.multiMonthCompare!=""){
					$scope.loadControl.multiMonthCompare = $scope.reqInfo.multiMonthCompare;
				}
			}
			if(data.resultCode == '2'){
				alert("登录会话超时");
				window.location.href="/index.html";
			}
			if(data.resultCode == '0'){
			}
		}
		commonService.postUrl(queryObject.url,queryObject.data,successFun);
	}
	
	$scope.currentMainChartInfo = {};
	$scope.showStatisticInfoFromPanel = function(item){
		$scope.currentMainChartInfo = item;
		$scope.currentMainChartInfo.chartDescHtml = $sce.trustAsHtml(item.chartDesc)// 提示信息
		$scope.loadControl.showMain = false;
		$scope.showStatisticInfo(item, true);
	}
	
	$scope.backPanel = function(){
		$scope.loadControl.showMain = true;
		$("#container").hide();
		$scope.outterScopeObject.outterEvents.closeTable();
	}
	
	/**
	 * fromPanelClick : 表明是否来自于主面板点击事件
	 * isShowLatitude : 是否显示”运营分析纬度“
	 * selectedOrg : 组织
	 */
	$scope.showStatisticInfo = function(item, fromPanelClick, isShowLatitude, selectedOrg,selectedTenant,isLined,latitudeId,latitudeType){
		if($scope.loadControl.theFirstClickItem){
			$scope.loadControl.defaultOrgId = $scope.orgSelect;
			$scope.loadControl.theFirstClickItem = false;
		}
		if(fromPanelClick==true || isLined==true){
			for(var i = 0; i < $scope.statisticItems.length; i++){
				if($scope.statisticItems[i].chartId == item.chartId){
					$scope.statisticItems[i].chartActive = "chartActive";
				} else {
					$scope.statisticItems[i].chartActive = "";
				}
			}
		}
		
		if(undefined != isShowLatitude)
			$scope.loadControl.isShowLatitude = isShowLatitude;
		if(undefined != selectedOrg){
			item.selectedOrg = selectedOrg;
		}
		if(undefined != selectedTenant){
			item.selectedTenant = selectedTenant;
		}
		
		item.pageSize = reportPageSize;
		item.currentPage = 1;
		item.isBatchQueryFlag = $scope.isBatchQueryFlag;
		if(fromPanelClick ==true || isLined==true)
			$scope.showLatitudeInfo(item,latitudeId,latitudeType);// 展示“运营分析纬度”
		//动态参数解析
		if($scope.chartTableQueryIds!=undefined && $scope.chartTableQueryIds!=""){
			var chartTableQueryIds = $scope.chartTableQueryIds;
			for(var i = 0;i < chartTableQueryIds.length; i++){
				document.getElementById(chartTableQueryIds[i]).value;
			}
		}
		var queryObject = {
			method  : 'POST',
			data 	: item,
			url  	: 'statisticBO.ajax?cmd=showStatisticInfo'
		};
		var successFun = function(data) {
			if(data.resultCode == '1'){
				if(fromPanelClick){
					$scope.initDomDefaultValue();
					if($scope.loadControl.defaultOrgId != undefined){
						$scope.orgSelect = $scope.loadControl.defaultOrgId;
					}
				}
				$scope.currentMainChartInfo.chartDescHtml = $sce.trustAsHtml(data.resultData.chartInfo.chartDesc)
				
				var chartObject = undefined;
				var charType = $scope.getChartTypeFromResponseData(data.resultData);
				if ('7' == charType) {
					chartObject = $scope.outterScopeObject;
				} else {
					chartObject = $('#container');
					chartObject.show();
				}
				if (data.resultData.checkInfo != undefined && data.resultData.checkInfo.resultCode != '1'){
					alert(data.resultData.checkInfo.resultMessage);
					return;
				}
				$scope.buildStatisticChartCommonEntry(data.resultData, chartObject);
				if($scope.isBatchQueryFlag==1){
					//reportPageSize每页数据   item.currentPage当前是哪一页
					var batchQuery = Math.ceil(parseInt(reportPageSize)/50);
					for (var i=1;i<=batchQuery;i++){
						window.top.showLoad();
						queryObjectBatch.data.currentBatchPage = i;
						commonService.postUrl(queryObjectBatch.url,queryObjectBatch.data,successFunBatch);
					};
				}
			}
			if(data.resultCode == '2'){
				alert("登录会话超时");
				window.location.href="/index.html";
			}
			if(data.resultCode == '0'){
				alert(data.resultMessage);
			}
			window.top.hideLoad();
		}
		var queryObjectBatch = {
				method  : 'POST',
				data 	: item,
				url  	: 'statisticBO.ajax?cmd=showStatisticInfoBatchQuery'
		};
		var successFunBatch = function(dataBatch) {
			if(dataBatch.resultCode == '1'){
				$scope.outterScopeObject.outterEvents.refreshTableInfoBatch(dataBatch.resultData, $scope.dbClickTableRow);
			}
			if(dataBatch.resultCode == '2'){
				alert("登录会话超时");
				window.location.href="/index.html";
			}
			if(dataBatch.resultCode == '0'){
				alert(data.resultMessage);
			}
			window.top.hideLoad();
		}
		window.top.showLoad();
		commonService.postUrl(queryObject.url,queryObject.data,successFun);
	}
	
	/*********************TODO 构建图表部分**********************Start*******/
	/***
	 * 构建统计图表的公共入口，也就是后台返回报文数据，调用这个方法构建图表
	 * 
	 * @param data: 请求返回的响应数据
	 * @param chartObject: 图表对象。如果是highcharts类型图表，chartObject为一个jquery对象；如果是table，则<report-table></report-table>对应的angular对象
	 */
	$scope.buildStatisticChartCommonEntry = function(data, chartObject){
		var userChartInfo = data.chartInfo;
		if (userChartInfo.chartType == 7){// table 类型
			chartObject.outterEvents.refreshTableInfo(data, $scope.dbClickTableRow);
			return;
		} else if (userChartInfo.chartType == 8){// 详情信息
			chartObject.outterEvents.refreshDetailInfo(data);
			return;
		}
		var chartInfo = {};
		$scope.buildStatisticChartCommonInfo(chartInfo,userChartInfo);// 构建Title和SubTitle等公用信息
		// '图表类型，枚举如下：\n1:曲线图\n2:3D柱状图\n3:饼图\n4:双饼图'
		if (1 == userChartInfo.chartType) {// 曲线图
			$scope.buildStatisticChartXAxis(chartInfo,userChartInfo);// 构建X轴
			$scope.buildStatisticChartYAxis(chartInfo,userChartInfo);// 构建Y轴
			chartInfo.plotOptions = {
	            series: {
	                cursor: 'pointer',
	                events: {
	                    click: function (event) {
	                    	var xIndex = event.point.index;// 确定点击的是哪个X轴
	                        var pointUserOptions = this.userOptions.userOptions;
							if(pointUserOptions != undefined && 'click' == pointUserOptions.eventName){
								eval("$scope." + pointUserOptions.eventMethodName + "(pointUserOptions, xIndex)");
							}
	                    }
	                }
	            }
	        }
		} else if (2 == userChartInfo.chartType) {// 3D柱状图
			$scope.buildStatisticChartXAxis(chartInfo,userChartInfo);// 构建X轴
			$scope.buildStatisticChartYAxis(chartInfo,userChartInfo);// 构建Y轴
			chartInfo.chart = {
	            type: 'column',
	            margin: 75,
	            options3d: {
	                enabled: true,
	                alpha: 5,
	                beta: 12,
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
								eval("$scope." + pointUserOptions.eventMethodName + "(pointUserOptions)");
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
		} else if (4 == userChartInfo.chartType) {// 双饼图(废弃)
			$scope.buildStatisticChartYAxis(chartInfo,userChartInfo);// 构建Y轴
			chartInfo.chart = {
	            type: 'pie'
	        };
			chartInfo.plotOptions = {
	            pie: {
	                shadow: false,
	                center: ['50%', '50%']
	            }
	        };
			$scope.buildDoublePieSeriesInfo(data.seriesInfo);
		} else {// 默认直方图
			chartInfo.chart = {
	            type: 'column',
	            margin: 75
	        };
		}
		chartInfo.series = data.seriesInfo;
		chartObject.highcharts(chartInfo);
	}
	
	$scope.buildDoublePieSeriesInfo = function(seriesInfo){
		if(undefined != seriesInfo && seriesInfo instanceof Array){
			for(var i = 0 ; i < seriesInfo.length; i++){
				if(i == 0){
					seriesInfo[i].dataLabels = {
		                formatter: function () {
		                    return this.y > 5 ? this.point.name : null;
		                },
		                color: 'white',
		                distance: -30
		            }
				} else if(i == 1) {
					seriesInfo[i].dataLabels = {
		                formatter: function () {
		                    // display only if larger than 1
		                    return this.y > 1 ? '<b>' + this.point.name + '，占有率:</b> ' + this.y + '%' : null;
		                }
		            }
				}
			}
		}
	}
	
	$scope.buildStatisticChartYAxis = function(chartInfo,userChartInfo){// 构建统计图表Y轴信息
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
	};
	
	$scope.buildStatisticChartXAxis = function(chartInfo,userChartInfo){// 构建统计图表X轴信息
		chartInfo.xAxis = {};
		chartInfo.xAxis.categories = userChartInfo.categories;
	};
	
	$scope.buildStatisticChartCommonInfo = function(chartInfo,userChartInfo){// 构建统计图表通用信息
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
	};
	/*********************TODO 构建图表部分**********************End*******/
	
	/**************************页面初始化部分**************************/
	$scope.init = function(){
		var reqInfo = GetRequest();
		$scope.reqInfo = reqInfo;
		$scope.initDomDefaultValue();
		$scope.initOrg(reqInfo.selectedOrg);
		$scope.initDesOrg(reqInfo.descOrgId);
		$scope.initTenant(reqInfo.selectedTenant);
		$scope.isBatchQueryFlag = 0;
		$scope.queryParams = {
			consignorName: reqInfo.consignorName==undefined?"":reqInfo.consignorName
		};
		if (undefined != specialChartId && null != specialChartId) {
			$scope.specialChartId = specialChartId;
			$scope.isShowBackButton = false;
			commonService.postUrl('statisticBO.ajax?cmd=queryChartInfo', {chartId: specialChartId}, function(data){
				if (data.resultCode == '1') {
					if (data.resultObject != undefined) {
						var item = {
							chartName: data.resultObject.chartName,
							chartId: data.resultObject.chartId,
							chartDesc: data.resultObject.chartDesc
						}
						//分批次查询
						if(data.resultObject.ext1 == "1"){
							$scope.isBatchQueryFlag = 1;
						}
							if(condition!=undefined && condition!=""){
								//解析linkLatitudeId
								var newLatitudeId ;
								if($scope.reqInfo.latitudeId!=undefined && $scope.reqInfo.latitudeId!=""){
									var strlinkLatitudeId = $scope.reqInfo.linkLatitudeId;
									var beforeLatitudeId = $scope.reqInfo.latitudeId;
									var splitStrLink = strlinkLatitudeId.split(",");
									for(var i=0;i<splitStrLink.length;i++){
										var splitKeyAndValue = splitStrLink[i].split("|");
										if(splitKeyAndValue[0]==beforeLatitudeId){
											newLatitudeId = splitKeyAndValue[1];
											break;
										}
									}
								}
								$scope.loadControl.showMain = false;
								$scope.loadControl.selectedLatitudeType = $scope.reqInfo.selectedLatitudeType;
								$scope.loadControl.isShowLatitude = true;
							}
							else
							{
								var tempCondition ={param:item};
								$scope.tempCondition = tempCondition;
								$scope.showStatisticInfoFromPanel(item);	
							}
						$scope.chartTableQueryConditions = data.chartTableQueryConditions;
						$scope.chartTableQueryIds = data.chartTableQueryIds;
						$scope.staticListMap = data.staticListMap;
					 	setTimeout(function(){
					 		if($scope.chartTableQueryIds!=undefined){
						 		for(var i=0;i<$scope.chartTableQueryIds.length;i++){
						 			var selectedOrText = "";
						 			var selectData = $scope.staticListMap[$scope.chartTableQueryIds[i]];
						 			if($scope.reqInfo[$scope.chartTableQueryIds[i]]!=undefined){
						 				selectedOrText = $scope.reqInfo[$scope.chartTableQueryIds[i]];
						 			}
						 			if(selectData!=undefined){
							 			var selectid = jQuery("#" + $scope.chartTableQueryIds[i]);
							 			for(var j=0;j<selectData.length;j++){
							 				var selectedFlag = "";
							 				if(selectedOrText==selectData[j].codeValue){
							 					selectedFlag = "selected='selected'";
							 				}
								 			selectid.append("<option value="+selectData[j].codeValue+" "+selectedFlag+">"+selectData[j].codeName+"</option>");
							 			}
						 			}
						 			else
						 			{
						 				var textid = jQuery("#" + $scope.chartTableQueryIds[i]);
						 				textid.val(selectedOrText);
						 			}
						 		};
					 		}
					 		if(condition!=undefined && condition!=""){
								$scope.analysis(newLatitudeId,$scope.reqInfo.consignorName,$scope.reqInfo.carrierCompanyName,$scope.reqInfo.descOrgId,$scope.reqInfo.chartId,true,$scope.reqInfo.selectedLatitudeType);
					 		}
						},500);
					} else {
						commonService.alert('获取报表信息失败');
					}
				} else {
					commonService.alert(data.resultMessage);
				}
			});
		} else {
			$scope.getStatisticType();
		}
	}
	
	$scope.initDomDefaultValue = function(){
		var currentDate = new Date();
		var beforeDate = new Date();
		beforeDate.setTime(currentDate.getTime() - 3600 * 24 * 30 * 1000);
		document.getElementById("selectedLatitudeTypeDate1").value = currentDate.Format('yyyy年');
		document.getElementById("selectedLatitudeTypeDate2").value = currentDate.Format('yyyy年MM月');
		document.getElementById("selectedLatitudeTypeDate3").value = currentDate.Format('yyyy年MM月dd日');
		document.getElementById("selectedLatitudeTypeDate4").value = beforeDate.Format('yyyy年MM月dd日');
		document.getElementById("selectedLatitudeTypeDate5").value = currentDate.Format('yyyy年MM月dd日');
		if($scope.reqInfo.queryMonthType!=undefined && $scope.reqInfo.queryMonthType!=4){
			document.getElementById("selectedLatitudeTypeDate"+$scope.reqInfo.queryMonthType).value = $scope.reqInfo.queryMonth;
		}
		if($scope.reqInfo.queryMonthType!=undefined && $scope.reqInfo.queryMonthType==4){
			document.getElementById("selectedLatitudeTypeDate4").value = $scope.reqInfo.customQueryStartTime;
			document.getElementById("selectedLatitudeTypeDate5").value = $scope.reqInfo.customQueryEndTime;
		}
		
	}
	
	$scope.initOrg = function(defaultValue){
		var url = "statisticBO.ajax?cmd=getCurrentUserPriOrgInfo";
		commonService.postUrl(url,"",function(data){
			if (data.orgs != undefined) {
				$scope.orgInfo = data.orgs;
				if(data.orgs.length > 0) {
					if(defaultValue!=undefined && defaultValue!="" && defaultValue!=null){
						$scope.orgSelect = parseInt(defaultValue);
					}
					else
					{
						$scope.orgSelect = data.orgs[0].orgId;
					}	
				}
			}
		});
	}
	
	$scope.initDesOrg = function(defaultValue){
		commonService.postUrl("routeBO.ajax?cmd=getCurrRoute","",function(data){
			if(data != null && data != undefined && data != ""){
				$scope.routes = data.items;
				$scope.routes.unshift({endOrgId:-1,endOrgName:"全部"});
				if(defaultValue!=undefined && defaultValue!="" && defaultValue!=null){
					$scope.routes = defaultValue;
				}
			}
		});
	}
	
	$scope.initTenant = function(defaultValue){
		var url = "statisticBO.ajax?cmd=getTenantInfo";
		var all = {'codeId':-1,'codeName':'全部'};
		commonService.postUrl(url,{rows:1000},function(data){
			if (data.items != undefined) {
				$scope.tenants = data.items;
				$scope.tenants.unshift(all);
				if(defaultValue!=undefined && defaultValue!="" && defaultValue!=null){
					$scope.tenants =  parseInt(defaultValue);
				}
			}
		});
	}
	
	$scope.openTab = function(id,name,url){
		var param = parseParam($scope.tempCondition.param) ;
		var param1 = GetRequest(url) ;
		param1 = parseParam(param1);
		//判断重复数据，以param1数据为准。
		if(param1!=undefined && param1!=""){
			var splitParam = param1.split("&");
			for(var i=0;i<splitParam.length;i++){
				var splitPot = splitParam[i];
				var splitEq = splitPot.split("=");
				if(splitEq!=undefined && splitEq!=""){
					//splitEq[0]属性名
					//splitEq[1]属性值
					if(splitEq[0] == 'pagesize'){
						//兼容老代码
						splitEq[0] = 'pageSize';
					}
					if(param.indexOf(splitEq[0])>-1){
						var reg = new RegExp(splitEq[0] + "=[^&]*","");
						param = param.replace(reg,splitEq[0]+"="+splitEq[1]);
					}
				}
			}
		}
		var urlEncode = url.substring(0,url.indexOf("?")+1) + param + "&" + param1 + "&selectedOrg" + $scope.tempCondition.selectedOrg + "&selectedTenant" + $scope.tempCondition.selectedTenant + "&selectedLatitudeType=" + $scope.tempCondition.selectedLatitudeType;
		commonService.openTab(id,name,urlEncode);
	}
	
	$scope.init();
	/**************************页面初始化部分**************************/
}]);

