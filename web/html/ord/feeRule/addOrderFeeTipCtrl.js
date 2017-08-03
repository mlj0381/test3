var addOrderFeeTipApp = angular.module("addOrderFeeTipApp", ['commonApp',"tableCommon"]);
addOrderFeeTipApp.controller("addOrderFeeTipCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	//小费规则类型
	$scope.checkboxList = [{id:1,name:"按首重递增",checked:true},{id:2,name:"按重量范围",checked:false},{id:3,name:"按运费占比",checked:false}];
	$scope.selected = "";
	//小费区间
	$scope.orderTipRule = [{minWieght:"",maxWieght:"",feeString:""},{minWieght:"",maxWieght:"",feeString:""}];
	$scope.save ={};
	var tipId = commonService.getQueryString("tipId");
	var isSee = commonService.getQueryString("isSee");
	var fee={
			init:function(){
				this.bindEvent();
				this.checkboxChange();
				this.selectCompany();//获取公司数据
				if(tipId != undefined && tipId != null && tipId != ""){
					if(isSee != undefined && isSee != null && isSee != ""){
						$scope.isSee = isSee;
					}
					this.getOrderFeeRuleData(tipId);
				}
				this.queryCompany();
			},
			bindEvent:function(){
				$scope.checkboxChange = this.checkboxChange;
				$scope.doSave=this.doSave;
				$scope.addOrDelFeeRule = this.addOrDelFeeRule;
				$scope.close = this.close;
				$scope.selectCompany = this.selectCompany;//获取公司数据
				$scope.queryCompany = this.queryCompany;
				$scope.getOrderFeeRuleData = this.getOrderFeeRuleData;
				$scope.isCurrencyChange = this.isCurrencyChange;
				$scope.isCurrencyClike = this.isCurrencyClike;
			},
			checkboxChange:function(item){
				for(var i=0;i<$scope.checkboxList.length;i++){
					if($scope.checkboxList[i].checked){
						$scope.checkboxList[i].checked=false;
					}
					if(item != undefined && item != null && item != ""){
						if($scope.checkboxList[i].id == item.id){
							$scope.checkboxList[i].checked=true;
							$scope.selected = item;
							$scope.checkboxList[i].id == 1 ? $scope.isFirst = true : $scope.isFirst = false;
							$scope.checkboxList[i].id == 2 ? $scope.isWeight = true : $scope.isWeight = false;
							$scope.checkboxList[i].id == 3 ? $scope.isFreight = true : $scope.isFreight = false;
						}
					}else{
						$scope.checkboxList[0].checked=true;
						$scope.isFirst = true;
					}
					
				}
			},
			selectCompany:function(){
				if(userInfo.userType == 1){
					var url = "organizationBO.ajax?cmd=selectOrgByLink";
					commonService.postUrl(url,"",function(data){
						$scope.curOrgData = data;
						$scope.save.tenantId = data.items[0].tenantId+"";
						//$scope.queryCompany($scope.save.tenantId);
					});
				}else{
					var url = "staticDataBO.ajax?cmd=selectCurTenantId";
					commonService.postUrl(url,"",function(data){
						$scope.curOrgData = data;
						$scope.save.tenantId = data.items[0].tenantId+"";
					});
				}
			},
			queryCompany:function(tenantId,beginOrgId,endOrgId){
				var that=this;
				//查询公司下面的所有的网点
				
				$scope.save.beginOrgId = "";
				
				$scope.save.endOrgId = "";
				commonService.postUrl("organizationBO.ajax?cmd=queryOrgList",{"tenantId":tenantId},function(data){
					if(data != null && data != undefined && data != ""){
						$scope.orgInfo = data;
						if(beginOrgId!=undefined){
							$scope.save.beginOrgId= parseInt(beginOrgId);
						}
						if(endOrgId!=undefined){
							$scope.save.endOrgId=parseInt(endOrgId);
						}
					}
				});
				
			},
			doSave:function(){
				$scope.isCurrency == true ? $scope.save.isCurrency = 1 : $scope.save.isCurrency =  0;
				if($scope.save.ruleName == undefined || $scope.save.ruleName == null || $scope.save.ruleName == ""){
					commonService.alert("请输入计费规则名称！");
					return ;
				}
				if($scope.save.tenantId == undefined || $scope.save.tenantId == null || $scope.save.tenantId == ""){
					commonService.alert("请选择归属专线！");
					return;
				}
				if($scope.save.staFeeString == undefined || $scope.save.staFeeString == null || $scope.save.staFeeString == ""){
					commonService.alert("请输入起价！");
					return;
				}
				if($scope.save.topFeeString == undefined || $scope.save.topFeeString == null || $scope.save.topFeeString == ""){
					commonService.alert("请输入封价！");
					return;
				}
				if(parseFloat($scope.save.staFeeString) > parseFloat($scope.save.topFeeString)){
					commonService.alert("封顶价不能小于起价！");
					return;
				}
				if($scope.save.beginOrgId == undefined || $scope.save.beginOrgId == null || $scope.save.beginOrgId == ""){
					commonService.alert("请选择起始网点！");
					return;
				}
				if($scope.save.endOrgId == undefined || $scope.save.endOrgId == null || $scope.save.endOrgId == ""){
					commonService.alert("请选择目的网点！");
					return;
				}
				
				if($scope.isFirst){
					$scope.save.tipType = 1;
					if($scope.save.first == undefined || $scope.save.first == null || $scope.save.first == ""){
						commonService.alert("请输入首重！");
						return;
					}
					if($scope.save.addWeightString == undefined || $scope.save.addWeightString ==  null || $scope.save.addWeightString == ""){
						commonService.alert("请输入续重计费价！");
						return;
					}
				}
				if($scope.isWeight){
					$scope.save.tipType = 2;
					for(var i=0;i<$scope.orderTipRule.length;i++){
						
						if($scope.orderTipRule[i].minWieght == ""){
							commonService.alert("请输入第"+(i+1)+"区间最小重量,不需要的区间可以删除");
							return;
						}
						if($scope.orderTipRule[i].maxWieght == ""){
							commonService.alert("请输入第"+(i+1)+"区间最小重量,不需要的区间可以删除");
							return;
						}
						if($scope.orderTipRule[i].feeString == ""){
							commonService.alert("请输入第"+(i+1)+"区间费用,不需要的区间可以删除");
							return;
						}
						var flagOne = "";
						var flagTwo = "";
						var flagThree = "";
						var minR = parseFloat($scope.orderTipRule[i].minWieght);//i最小
						var maxR = parseFloat($scope.orderTipRule[i].maxWieght);//i最大
						flagOne =  Math.abs(maxR - minR);//i最大-i最小
						if(minR >= maxR){
							commonService.alert("第"+(i+1)+"上限不能小于下限");
							return;
						}
						for(var j=(i+1);j<=$scope.orderTipRule.length-1;j++){
							
							if(minRJ == ""){
								commonService.alert("请输入第"+(j+1)+"区间最小重量,不需要的区间可以删除");
								return;
							}
							if(maxRJ == ""){
								commonService.alert("请输入第"+(j+1)+"区间最小重量,不需要的区间可以删除");
								return;
							}
							if($scope.orderTipRule[j].feeString == ""){
								commonService.alert("请输入第"+(j+1)+"区间费用,不需要的区间可以删除");
								return;
							}
							var maxRJ = parseFloat($scope.orderTipRule[j].maxWieght);
							var minRJ = parseFloat($scope.orderTipRule[j].minWieght);
							if(minRJ >= maxRJ){
								commonService.alert("第"+(j+1)+"上限不能小于下限");
								return;
							}
							flagTwo = Math.abs(maxRJ - minRJ);//j最大-j最小
							//flagThree = Math.abs((maxRJ - minR)+(minRJ-maxR));//j的最大-j的最小
							flagThree = Math.abs((maxRJ - maxR)+(minRJ-minR))
							if(flagThree < flagTwo +flagOne){
								commonService.alert("第"+(i+1)+"区间与第"+(j+1)+"区间重叠了！");
								return;
							}else{
								break;
							}
						}
						eval("$scope.save.orderTipRule_"+i+"_minWieght=$scope.orderTipRule["+i+"].minWieght");
						eval("$scope.save.orderTipRule_"+i+"_maxWieght=$scope.orderTipRule["+i+"].maxWieght");
						eval("$scope.save.orderTipRule_"+i+"_feeString=$scope.orderTipRule["+i+"].feeString");
						$scope.save.num = i;
					}
					//$scope.save.orderTipRule =  $scope.orderTipRule.stringify();
				}
				if($scope.isFreight){
					$scope.save.tipType = 3;
					if($scope.save.percentage == undefined || $scope.save.percentage == null||$scope.save.percentage == ""){
						commonService.alert("请输入运费占比率！");
						return;
					}
				}
				if($scope.save.tipType == undefined || $scope.save.tipType == null || $scope.save.tipType == ""){
					commonService.alert("请选择小费计费方式！");
					return;
				}
				var url = "orderFeeRuleBO.ajax?cmd=doSaveTipFee";
				commonService.postUrl(url,$scope.save,function(data){
					if(data == "Y"){
						commonService.alert("保存成功！",function(){
							$scope.close();
						});
					}
				});
			},
			addOrDelFeeRule:function(num,obj){
				if(num == 1){
					if($scope.orderTipRule.length == 10){
						commonService.alert("最多只能新增十条，不能新增！");
						return;
					}
					$scope.orderTipRule.push({minWieght:"",maxWieght:"",fee:""});
					
				}
				if(num == 2){
					if($scope.orderTipRule.length <= 1){
						commonService.alert("至少保留一条，不能删除！");
						return;
					}
					$scope.orderTipRule.shift(obj);
					
				}
			},
			close:function(){
				commonService.closeToParentTab(true);
			},
			getOrderFeeRuleData:function(id){
				var url = "orderFeeRuleBO.ajax?cmd=getOrderFeeTipOut";
				commonService.postUrl(url,{"id":id},function(data){
					
					var feeRule = data.feeRule;
					$scope.save = feeRule;
					$scope.save.tenantId = feeRule.tenantId+"";
					
					$scope.queryCompany($scope.save.tenantId,feeRule.beginOrgId,feeRule.endOrgId);
					
					
					$scope.save.beginOrgId = parseInt(feeRule.beginOrgId);
					$scope.save.endOrgId = parseInt(feeRule.endOrgId);
					$scope.save.tipType = feeRule.tipType;
					
					
					$scope.save.staFeeString = feeRule.staFeeString;
					$scope.save.topFeeString = feeRule.topFeeString;
					$scope.save.tipType == 1 ? $scope.isFirst = true : $scope.isFirst = false;
					$scope.save.tipType == 2 ? $scope.isWeight = true : $scope.isWeight = false;
					$scope.save.tipType == 3 ? $scope.isFreight = true : $scope.isFreight = false;
					if(data.tipFee != undefined && data.tipFee != null && data.tipFee.length > 0){
						$scope.orderTipRule = data.tipFee;
					}
					if(feeRule.tipType != undefined || feeRule.tipType != null || feeRule.tipType != ""){
						for(var i=0;i<$scope.checkboxList.length;i++){
							if(parseInt($scope.checkboxList[i].id) == parseInt(feeRule.tipType)){
								$scope.checkboxList[i].checked = true;
							}else{
								$scope.checkboxList[i].checked = false;
							}
						}
					}
				});
			},
			isCurrencyChange:function(isCurrency){
				if(isCurrency){
					commonService.alert("设置成通用模板后，其他的通用模板就会改变成普通！");
				}
			},
			isCurrencyClike:function(){
				var url = "orderFeeRuleBO.ajax?cmd=isCurrencyRule";
				commonService.postUrl(url,{"tenantId":$scope.save.tenantId},function(data){
					var feeRule = data.feeRule;
					$scope.save.tipType = feeRule.tipType;
					$scope.isCurrency
					feeRule.isCurrency 
					$scope.save.tenantId = feeRule.tenantId+"";
					$scope.queryCompany($scope.save.tenantId,feeRule.beginOrgId,feeRule.endOrgId);
					$scope.save.staFeeString = feeRule.staFeeString;
					$scope.save.topFeeString = feeRule.topFeeString;
					$scope.save.tipType == 1 ? $scope.isFirst = true : $scope.isFirst = false;
					$scope.save.tipType == 2 ? $scope.isWeight = true : $scope.isWeight = false;
					$scope.save.tipType == 3 ? $scope.isFreight = true : $scope.isFreight = false;
					if(data.tipFee != undefined && data.tipFee != null && data.tipFee != ""){
						$scope.orderTipRule = data.tipFee;
					}
					$scope.save.percentage = feeRule.percentage;
					$scope.save.first = feeRule.first;
					if(feeRule.tipType != undefined || feeRule.tipType != null || feeRule.tipType != ""){
						for(var i=0;i<$scope.checkboxList.length;i++){
							if(parseInt($scope.checkboxList[i].id) == parseInt(feeRule.tipType)){
								$scope.checkboxList[i].checked = true;
								$scope.selected = "";
								$scope.checkboxChange($scope.checkboxList[i]);
							}else{
								$scope.checkboxList[i].checked = false;
							}
						}
					}
					$scope.save.addWeightString = feeRule.addWeightString;
					$scope.sta.initData(feeRule.staProvince,feeRule.staProvinceName,feeRule.staCity,feeRule.staCityName,"","");
				});
			}
	};	
	fee.init();
}]);
