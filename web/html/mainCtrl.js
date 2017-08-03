var tokenId=getCookie("token");
function getTablDiyData(){
	var appElement = document.querySelector('[ng-controller=mainCtrl]');
	var scope=angular.element(appElement).scope();
	return scope.getTableDiyData();
}
var tableHead={};
var mainApp = angular.module("mainApp", ['commonApp','indexLabel','menuCommon']);
mainApp.controller("mainCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	//$scope.myTopMenu = {myTab:function(){}};
	var mainIndex={
		isShowIndex:true,
		showManySearch : false,
		init:function(){
			this.initBusiConfig();
			$scope.isShowIndex=this.isShowIndex;
			$scope.loadIndex=this.loadIndex;
			$scope.userName=userInfo.userName;
			$scope.orgName=userInfo.orgName;
			$scope.tenantName=userInfo.tenantName;
			$scope.logo=userInfo.logo;
			$scope.logout=this.logout;
			$scope.isShowLoad=this.isShowLoad;
			$scope.loadCount=0;
			$scope.rate=0;
			$scope.hideMenu=this.hideMenu;
			$scope.getTableDiyData=this.getTableDiyData;
			$scope.getData=this.getData;
			$scope.des={};
			$scope.modifyPwd=this.modifyPwd;
			$scope.meunTab = this.meunTab;
			this.getCmUserRelat();
			var charts = {
			          options: {
			        	  xAxis: {
			                  categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun','Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
			              },
			              yAxis: {
			                  title: {
			                      text: 'Temperature (°C)'
			                  },
			                  plotLines: [{
			                      value: 0,
			                      width: 1,
			                      color: '#808080'
			                  }]
			              },
			              tooltip: {
			                  valueSuffix: '°C'
			              },
			              legend: {
			                  layout: 'vertical',
			                  align: 'right',
			                  verticalAlign: 'middle',
			                  borderWidth: 0
			              }
			          },
			          series: [{
			              name: 'Tokyo',
			              data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
			          }, {
			              name: 'New York',
			              data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
			          }, {
			              name: 'Berlin',
			              data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]
			          }, {
			              name: 'London',
			              data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
			          }],
			      title: {
		            			text: 'Monthly Average Temperature',
		            			x: -20 //center
			 			}
			     }
			     $scope.charts=charts;
			     $scope.showManySearch = this.showManySearch;
			     
			     $scope.closeView = this.closeView;
			     $scope.clearModel = this.clearModel;
			     $scope.doQueryOrder = this.doQueryOrder;
			     $scope.queryOrg = this.queryOrg;
			     $scope.toDetail = this.toDetail;
			     $scope.query = {};
//			     $scope.doQueryOrder();
			     $scope.getData();
			     this.queryOrg();
			     this.bindEvent();
			   //查找表头配置
			     this.getTableHeadConfig();
		},
		getData:function(){
			var url = "portalBusiBO.ajax?cmd=getTableDiy";
			if(null == $scope.tableDiyData || $scope.tableDiyData == undefined ){
				commonService.postUrl(url,{},function(data){
					if(data != null && data != undefined && data != ""){
						$scope.tableDiyData = data;
					}
				});
			}
		},
		getTableDiyData:function(){
			return $scope.tableDiyData;
		},
		bindEvent:function(){
			$scope.changeDate = this.changeDate;
			$scope.query=this.query;
		},
		toDetail : function(o){
			 window.open("/ord/ordBillingDetail.html?isShowReturn=false&view=1&trackingNum="+o.trackingNum+"&type=3");
		},
		/**网点列表查询*/
		queryOrg:function(){
			var beginOrgId = userInfo.orgId;
			commonService.postUrl("routeBO.ajax?cmd=getCurrRoute","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.orgInfo = data;
					if(data.items != null && data.items != undefined && data.items != ""){
						$scope.orgInfo.items.unshift({endOrgId:-1,endOrgName:'全部'});
					}
					$scope.query.descOrgId = -1;
				}
			});
		},
		/**获取关联的用户**/
		getCmUserRelat:function(){
			var url = "portalBusiBO.ajax?cmd=getCmUserRelat";
			commonService.postUrl(url,"",function(data){
				//成功执行
				$scope.orgData=data.userInfoList;
				if($scope.orgData!=null && $scope.orgData!=undefined &&  $scope.orgData.length>0){
					$scope.showData=true;
				}else{
					$scope.showData=false;
				}
				
			});
		},
		closeView : function(){
			 $("#isShowSearch").hide();
	         $("#alertMsgIsShow").hide();
	         $("body").css('overflow','inherit');
	         $scope.clearModel();
//	         $scope.doQueryOrder();
		},
		clearModel : function(){
		     $scope.query = {};
		     $scope.query.signState="-1";
			 $scope.query.transitOutgoingState="0";
			 $scope.des.clear();
		},
		doQueryOrder : function(){
			$scope.paramsOrder = {};
			$scope.query.provinceId = $scope.des.chooseCityId;
			$scope.query.cityId = $scope.des.chooseRegionId;
			$scope.query.countyId = $scope.des.chooseCountyId;
			$scope.query.streetId = $scope.des.chooseStreetId;
			$scope.paramsOrder =  $scope.query;
			var url = "orderInfoBO.ajax?cmd=queryOrderInfoAndOut";
			$scope.paramsOrder.page=1;
			$scope.paramsOrder.count = 10;
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:$scope.paramsOrder
						});
			},500);
		},
		query:{},
		logout:function(){
			var url = "portalBusiBO.ajax?cmd=doLogout";
			commonService.postUrl(url,{},function(data){
				//成功执行
				if(data != null && data != undefined && data != "" && data=="0"){
					window.location = "/index.html";
				}
			});
		},
		loadIndex:function(){
			this.isShowIndex=true;
//			$scope.myTab.cleanAll();
		},
		hideMenu:function(){
			$scope.myTopMenu.hideAllMenu();
		},
		modifyPwd:function(){
			openIndex("12345555","修改密码","/cm/modifyCmUserInfoPwd.html");
		},
		initBusiConfig:function(){
			var url = "sysBusiConfigBO.ajax?cmd=queryBusiConfig";
			commonService.postUrl(url,"",function(data){
				if(data!="" && data!=null){
					if(data.resultCode=="1"){
						//返回有数据的
						
						window.top.tenantConfig.initData(data.busiConfig);
						
					}
				}
			});
		},
		/**
		 * 切换账户
		 * @param value
		 */
		changeDate:function(obj){
			// $("#org").text(obj.orgName);
			$scope.query.org=$scope.orgName;
			var url = "portalBusiBO.ajax?cmd=LoginUserOut";
			commonService.postUrl(url,obj,function(data){
				//成功执行
				if(data != null && data != undefined && data != "" && data.roles.length>0){
					window.top.location.reload();
				}
			});
		},
		getTableHeadConfig:function(){
			var url = "sysTableHeadConfigBO.ajax?cmd=getSysTableHeadConfigs";
			commonService.postUrl(url,"",function(data){
				tableHead = data;
			});
		},
		meunTab:function(id,url,name,menuId){
			$scope.myTab.open(id,name,url,menuId);
			$scope.isShowIndex=false;
			$scope.isShow=false;
		}
	};
	mainIndex.init();
}]);
