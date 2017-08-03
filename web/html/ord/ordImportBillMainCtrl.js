
var departManageApp = angular.module("departManageApp", [ 'ngFileUpload','commonApp']);
departManageApp.controller("departManageCtrl", ["$scope","$upload","commonService","$timeout",function($scope,$upload,commonService,$timeout) {
	 
	 var departManage={
		init:function(){
			this.bindEvent();
			$scope.urlManager = undefined;
			this.doQuery();
		},
		bindEvent:function(){
			$scope.commit = this.commit;
			$scope.uploadUsing$upload = this.uploadUsing$upload;
			$scope.doQuery = this.doQuery;
			$scope.query = this.query;
			$scope.leand = this.leand;
			$scope.clear = this.clear;
			$scope.deal = this.deal;
			$scope.close = this.close;
			$scope.pickUp = this.pickUp;
		},
		pickUp:function(){
			var selectedDate = $scope.page.getSelectData();
			if(selectedDate == undefined || selectedDate.length == 0){
				commonService.alert("请选择一条数据！");
				return false;
			}
			if(selectedDate.length != 1){
				commonService.alert("每次只能取出一条数据！");
				return false;
			}
			if(selectedDate[0].state == 1){
				commonService.alert("数据已经提取，不能重复提取！");
				return false;
			}
			commonService.openTab("100100102","运单录入","/ord/billing.html?imports=5&ids="+selectedDate[0].id);
		},
		close:function(){
			commonService.closeToParentTab(true);
		},
		deal:function(){
			var selectedDate = $scope.page.getSelectData();
			if(selectedDate == undefined || selectedDate.length == 0){
				commonService.alert("请选择一条数据！");
				return false;
			}
			var arr = "";
			for(var i=0;i<selectedDate.length;i++){
				arr = selectedDate[i].id+"," + arr;
			}
			commonService.confirm("确认要删除该数据!",function(){
				commonService.postUrl("ordImportBillBO.ajax?cmd=dealOrdImportBill",{ids:arr},function(data){
					commonService.alert("删除成功！");
					$scope.doQuery();
				});
			});
		},
		clear:function(){
			document.getElementById("beginTime").value='';
			document.getElementById("endTime").value='';
			$scope.query.beginTime='';
			$scope.query.endTime='';
			$scope.query.state = "";
			$scope.query.fhdh="";
		},
		leand:function(){
			commonService.openTab("100100100","导入数据","/ord/ordImportBillAdd.html");
		},
		commit: function(tennatDataConfigFile) {
			$scope.urlManager = undefined;
			if($scope.urlManager == undefined){
				$scope.urlManager = {}; // 控制频繁提交的问题
				setTimeout(function(){
					if (!!tennatDataConfigFile){
						if(!!tennatDataConfigFile[0].name){
							if(!(tennatDataConfigFile[0].name.indexOf('xlsx') > 0 || tennatDataConfigFile[0].name.indexOf('XLSX') > 0)) {
								commonService.alert("订单数据文件格式不正确，请上传Excel文件");
								$scope.urlManager = undefined;
								return false;
							}
							
							$scope.formUpload = true;
							commonService.alert("订单数据文件已经提交，数据处理中，请稍等...");
							$scope.uploadUsing$upload(tennatDataConfigFile[0], function(response) {
								var news = response.data.success;
								if(response.data.errorNews != "" && response.data.errorNews != undefined && response.data.errorNews != null){
									news =news +"，录入错误的有："+ response.data.errorNews;
								}
								if (response != undefined && response.data != undefined) {
									if ('1' != response.data.resultCode) {
										if(undefined != response.data.resultMessage) {
											commonService.alert(response.data.resultMessage);
											
										} else {
											commonService.alert('处理文件数据失败，请重新上传');
										}
									} else {
										commonService.alert(news,function(){
											commonService.closeToParentTab(true);
										});
										
									}
								} else {
									commonService.alert('处理文件数据失败，请重新上传');
								}
								$scope.urlManager = undefined;
							});
							return true;
						} else {
							$scope.urlManager = undefined;
							commonService.alert('请选择[导入文件]');
							return false;
						}
					} else {
						$scope.urlManager = undefined;
						commonService.alert('请选择[导入文件]');
						return false;
					}
				}, 500);
			}
		},
		uploadUsing$upload: function(file, callback){
			var code = "" + parseInt(Math.random() * 9999999999 + 1);
			file.upload = $upload.upload({
				url: signUrl('tennatDataConfigBO.ajax?cmd=dealTennatDataConfig&code=' + code),
				method: 'POST',
				headers: {
					'my-header' : 'my-header-value'
				},
				file: file,
				fileFormDataName: 'myFile'
			});
			file.upload.then(function(response) {
				callback(response);
			}, function(response) {
				callback(response);
			});
			file.upload.progress(function(evt) {
				file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
			});
			file.upload.xhr(function(xhr) {
			});
		},
		query:{},
		colse:function(){
			commonService.closeToParentTab(true);
		},
		
		doQuery:function(){
			var url = "ordImportBillBO.ajax?cmd=doQuery";
			$scope.query.page = 1;
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:$scope.query,
							callBack:"setContentHegthDelay"
						});
			},500);
		}
	}
	departManage.init();
}]);
FileAPI = {
		debug: true,
	};
