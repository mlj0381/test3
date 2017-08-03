var productManageApp = angular.module("productManageApp", ['commonApp']);
productManageApp.controller("productManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var productManage={
		init:function(){
			this.bindEvent();
			this.doQuery();
			this.selectCategory();
		},
		bindEvent:function(){
			$scope.param = this.param;
			$scope.query = this.query;
			$scope.doQuery = this.doQuery;
		    $scope.clear = this.clear;
		    $scope.selectAll = this.selectAll;
		    $scope.selectOne = this.selectOne;
		    $scope.doSave = this.doSave;
		    $scope.selectCategory=this.selectCategory;
		    $scope.selectChildCategory=this.selectChildCategory;
		    $scope.modify=this.modify;
		    $scope.cancer=this.cancer;
		    $scope.fixNumber=this.fixNumber;
		},
		params:{
		},
		query:{
			provinceId:-1
		},
		/**全选*/
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
		/**列表查询*/
		doQuery:function(){
			/*此处可以写业务限制规则*/
			var url = "productBO.ajax?cmd=queryProductPage";
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:$scope.query
						});
			},500);
		},
		/**清空查询条件*/
		clear:function(){
			$scope.query.secondCatalogId="";
			$scope.query.prodName="";
			$scope.query.rootCatalogId="";
			$scope.childCategory={};
		},
		/**选择一行**/
		selectOne : function(prodId){
			if(document.getElementById("checkbox"+prodId).checked && document.getElementById("checkbox"+prodId) != undefined){
				document.getElementById("checkbox"+prodId).checked=false;
			}else{
				document.getElementById("checkbox"+prodId).checked=true;
			}
		},
		//新增
		doSave:function(){
			var param = {"prodId":$scope.prodId,"installPrice":document.getElementById($scope.prodId).value*100};
			var url = "productBO.ajax?cmd=updateProductInfo";
			commonService.postUrl(url,param,function(data){
				// 成功执行
					//commonService.alert("操作成功!");
					$scope.prodId=null;
					$scope.doQuery();
			});
		},
		selectCategory:function(){
			var param = {};
			var url = "productBO.ajax?cmd=queryProductCatalogByLevel";
			commonService.postUrl(url,param,function(data){
				// 成功执行
				if(data!=null && data!=undefined && data!=""){
						$scope.categoryData=data;
						productManage.query.rootCatalogId=data.items[0].id;
	 	    	}
			});
		},
		selectChildCategory:function(catalogId){
			if(parseInt(catalogId)>0){
				var param = {"catalogId":catalogId};
				var url = "productBO.ajax?cmd=queryProductCatalog";
				commonService.postUrl(url,param,function(data){
					// 成功执行
					if(data!=null && data!=undefined && data!=""){
							$scope.childCategory=data;
							productManage.query.secondCatalogId=data.items[0].id;
		 	    	}
				});
			}
		},
		modify:function(prodId){
			$scope.prodId=prodId;
		},
		cancer:function(){
			$scope.prodId=null;
		},
		fixNumber:function (prodId){
			var value=document.getElementById(prodId).value;
			value = value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
			value = value.replace(/^\./g,"");  //验证第一个字符是数字而不是. 
			value = value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.   
			value = value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
			document.getElementById(prodId).value=value;
		},
	};
	productManage.init();
}]);
