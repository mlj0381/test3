var myApp = angular.module("GeneralApp", ['commonApp']);
myApp.controller("GeneralCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var myManage={
		init:function(){
			$scope.query = {};
			this.bindEvent();
			setContentHeight();
		},
		bindEvent:function(){
			$scope.param = this.param;
			$scope.doQuery = this.doQuery;
			$scope.clear = this.clear;
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
		params:{
		},
		/**列表查询*/
		doQuery:function(){
			myManage.params.doObjName=$scope.query.doObjName;
			myManage.params.doTel=$scope.query.doTel;
			myManage.params.state=$scope.query.state;
			myManage.params.signTimeBegin=$scope.query.signTimeBegin;
			myManage.params.signTimeEnd=$scope.query.signTimeEnd;
			myManage.doQuerySf();
		},
		/**列表查询*/
		doQuerySf:function(){
			var loginAcct = $scope.query.doTel;
			var param = {};
			var url = "cashManageBO.ajax?cmd=doQueryCom";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.sf = data;
					var url = "cashManageBO.ajax?cmd=doBusiAccountTotalQuery";
					$timeout(function(){
						$scope.page.load({
									url:url,
									params:myManage.params,
									callBack:"authHeight"
								});
					},500);
				}
			});
		},
		/**清空查询条件*/
		clear:function(){
			$scope.query = {};
			$scope.query.state="-1";
		}
	};
	myManage.init();
}]);

//table 列表头部随滚动条滚动
window.onload=function(){
        function $(id){return document.getElementById(id)}
        var hDiv=$('hDiv'),dDiv=$('dDiv'),tb0=$('tb0'),tb1=$('tb1');
        if(hDiv!=null&&hDiv!=undefined){
		         dDiv.onscroll=function(){
		           tb0.style.left = dDiv.scrollLeft*-1 +'px'
		       };
        }
        setContentHeight(500);
    
}
function authHeight(){
	setContentHeight(500);
}
