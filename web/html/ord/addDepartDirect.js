var addDepartApp = angular.module("addDepartDirectApp", ['commonApp']);
addDepartApp.controller("addDepartDirectCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	$scope.payDot=[];//配送网点
	$scope.query={};//查询条件
	$scope.matchList=[];//查询结果列表
	$scope.matchListTmp=[];//统计查询结果临时列表
	$scope.freightCollectWait=0;//统计查询结果的到付
	$scope.collectingMoneyWait=0;//统计查询结果的货付
	$scope.actualVolumeWait=0;//统计查询结果的体积
	$scope.actualWeightWait=0;//统计查询结果的重量
	$scope.matchCount=0;//统计查询结果的票数
	$scope.selectData={};//统计查询结果检索条件
	$scope.selectMatchList=[];//查询结果列表
	$scope.selectMatchListTmp=[];//查询结果列表
	$scope.freightCollect=0;//选择后的到付
	$scope.collectingMoney=0;//选择后的货付
	$scope.actualVolume=0;//选择后的体积
	$scope.actualWeight=0;//选择后的重量
	$scope.matchCountSel=0;//选择后的票数
	$scope.yeFlag=true;
	$scope.dispatchOrg=[];//配送网点
	$scope.dispatchOrgList=[];// 付款方
	$scope.form={};//提交数据
	$scope.directQuery={};//服务商查询条件
	$scope.directCustFlag=false;
	$scope.sfQueryList=[];//服务商列表
	
	$scope.vehicleTypeList=[];
	$scope.businessTypeList=[];
	
	var addDepart={
		init:function(){
			this.bindEvent();
			this.toView();
			this.doQuery();
			//this.queryDispatchOrg();
			commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType",{"codeType":"VEHICLE_TYPE"},function(data){
				$scope.vehicleTypeList=data.items;
			});
			commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType",{"codeType":"BUSINESS_TYPE"},function(data){
				$scope.businessTypeList=data.items;
			});
		},
		bindEvent:function(){
			$scope.doQuery=this.doQuery;
			$scope.statisticsMatch=this.statisticsMatch;
			$scope.cleanStatisticsMatch=this.cleanStatisticsMatch;
			$scope.clearQuery=this.clearQuery;
			$scope.retrievalMatch=this.retrievalMatch;
			$scope.delMatchList=this.delMatchList;
			$scope.select=this.select;
			$scope.selectAll=this.selectAll;
			$scope.retrievalMatchSel=this.retrievalMatchSel;
			$scope.statisticsMatchSel=this.statisticsMatchSel;
			$scope.cleanStatisticsMatchSel=this.cleanStatisticsMatchSel;
			$scope.selectSel=this.selectSel;
			$scope.delMatchListSel=this.delMatchListSel;
			$scope.selectAllSel=this.selectAllSel;
			$scope.next=this.next;
			$scope.queryDispatchOrg=this.queryDispatchOrg;
			$scope.selectDispatchOrg=this.selectDispatchOrg;
			$scope.selectVehicle=this.selectVehicle;
			$scope.clearSelect=this.clearSelect;
			$scope.doSave=this.doSave;
			$scope.openDirect=this.openDirect;
			$scope.cosleDirect=this.cosleDirect;
			$scope.querySfInfo=this.querySfInfo;
			$scope.selectSf=this.selectSf;
			
			$scope.param=this.param;
		    $scope.print=this.print;
		    $scope.doQueryVehile=this.doQueryVehile;
		    $scope.form=this.form;
		    $scope.clear=this.clear;
		    $scope.showToFalse=this.showToFalse;
		    $scope.showTotrue=this.showTotrue;
		    $scope.selectClear=this.selectClear;
		    $scope.close=this.close;
		    $scope.fixNumber=this.fixNumber;
		    $scope.remove=this.remove;
		    $scope.add=this.add;
		    $scope.changeDescOrg=this.changeDescOrg;
		    $scope.selectData=this.selectData;
		    $scope.checkData=this.checkData;
		    $scope.isCheckData=this.isCheckData;
		    $scope.removeClear=this.removeClear;
		    $scope.addClear=this.addClear;
		    $scope.chechHight=this.chechHight;
		    $scope.vehicleChange=this.vehicleChange;
		    $scope.doAddViechInfo=this.doAddViechInfo;
		},
		queryOrg:function(){
			/**查询配送网点*/
			var param = {"collectType":3,"addType":1};
			var url = "routeBO.ajax?cmd=queryRoateRuting";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
					if(data!=null && data!=undefined && data.items!=null && data.items!=undefined && data.items.length>0){
						$scope.payDot=data.items;
						$scope.query.descOrgId=data.items[0].endOrgId;
						$scope.doQuery();
					}
	 	    	}
			});
		},
		doQuery:function(){
			/**订单列表查询*/
			var url = "orderInfoBO.ajax?cmd=queryMatchCurrentOrg";
			commonService.postUrl(url,$scope.query,function(data){
				$scope.matchList = data.items;
				$scope.matchListTmp = data.items;
				$scope.selectData.trackingNums="";
				$scope.selectData.gtWeight="";
				$scope.selectData.gtVolume="";
				$scope.selectData.gtCount="";
				$scope.selectData.geTrackingNums="";
				$scope.selectData.geWeight="";
				$scope.selectData.geVolume="";
				$scope.selectData.geCount="";
				//修改时不需要清空
				var batchNum=getQueryString("batchNum");
				if(batchNum==null || batchNum==undefined || batchNum==""){
					$scope.selectMatchList=[];
					$scope.selectMatchListTmp=[];
				}
				setContentHegthDelay();
				$scope.statisticsMatch();
				$scope.statisticsMatchSel();
			});					
		},
		statisticsMatch:function(){
			$scope.cleanStatisticsMatch();
			if($scope.matchList!=null && $scope.matchList.length>0){
				$scope.matchCount=$scope.matchList.length;
				for(var i=0;i<$scope.matchList.length;i++){
					$scope.freightCollectWait+=(Number($scope.matchList[i].freightCollect)/100);
					$scope.collectingMoneyWait+=(Number($scope.matchList[i].collectingMoney)/100);
					$scope.actualVolumeWait+=$scope.matchList[i].volume;
					$scope.actualWeightWait+=$scope.matchList[i].weight;
				}
			}
		},
		cleanStatisticsMatch:function(){
			$scope.freightCollectWait=0;//统计查询结果的到付
			$scope.collectingMoneyWait=0;//统计查询结果的货付
			$scope.actualVolumeWait=0;//统计查询结果的体积
			$scope.actualWeightWait=0;//统计查询结果的重量
			$scope.matchCount=0;//统计查询结果的票数
		},
		clearQuery:function(){
			$scope.query={};//查询条件
			$scope.query.descOrgId=$scope.payDot[0].endOrgId;
			document.getElementById("beginTime").value="";
			document.getElementById("endTime").value="";
		},
		retrievalMatch:function(){
			if($scope.matchListTmp!=null && $scope.matchListTmp!=undefined && $scope.matchListTmp.length>0 ){
				$scope.matchList=[];
				var matchListNew=[];
				var str="";
				if($scope.selectData.trackingNums!=null  && $scope.selectData.trackingNums!=undefined && $scope.selectData.trackingNums!=""){
					if(str==""){
						str+=" trackingNum.indexOf("+$scope.selectData.trackingNums+")>-1";
					}else{
						str+=" && trackingNum.indexOf("+$scope.selectData.trackingNums+")>-1";
					}
				}
				if($scope.selectData.gtWeight!=null  && $scope.selectData.gtWeight!=undefined && $scope.selectData.gtWeight!=""){
					if(str==""){
						str+=" weight>"+$scope.selectData.gtWeight;
					}else{
						str+=" && weight>"+$scope.selectData.gtWeight;
					}
				}
				if($scope.selectData.gtVolume!=null  && $scope.selectData.gtVolume!=undefined && $scope.selectData.gtVolume!=""){
					if(str==""){
						str+=" volume>"+$scope.selectData.gtVolume;
					}else{
						str+=" && volume>"+$scope.selectData.gtVolume;
					}
				}
				if($scope.selectData.gtCount!=null  && $scope.selectData.gtCount!=undefined && $scope.selectData.gtCount!=""){
					if(str==""){
						str+=" count>"+$scope.selectData.gtCount;
					}else{
						str+=" && count>"+$scope.selectData.gtCount;
					}
				}
				if(str!=""){
					for(var i=0;i<$scope.matchListTmp.length;i++){
						var trackingNum = $scope.matchListTmp[i].trackingNum;
						var weight = $scope.matchListTmp[i].weight;
						var volume = $scope.matchListTmp[i].volume;
						var count = $scope.matchListTmp[i].count;
						var pjStr="if("+str+") matchListNew.push($scope.matchListTmp[i])";
						eval(pjStr);
					}
					$scope.matchList=matchListNew;
				}else{
					$scope.matchList=$scope.matchListTmp;
				}
				$scope.statisticsMatch();
			}
		},
		/**单选*/
		select:function(data) {
			$scope.selectMatchListTmp.push(data);
			$scope.selectMatchList=$scope.selectMatchListTmp;
			$scope.delMatchList(data);
		},
		delMatchList:function(data){
			var listTmp = [];
			for(var j=0;j<$scope.matchListTmp.length;j++){
				if(data.trackingNum!=$scope.matchListTmp[j].trackingNum){
					listTmp.push($scope.matchListTmp[j]);
				}
			}
			$scope.matchListTmp=listTmp;
			var list = [];
			for(var j=0;j<$scope.matchList.length;j++){
				if(data.trackingNum!=$scope.matchList[j].trackingNum){
					list.push($scope.matchList[j]);
				}
			}
			$scope.matchList=list;
			$scope.selectData.geTrackingNums="";
			$scope.selectData.geWeight="";
			$scope.selectData.geVolume="";
			$scope.selectData.geCount="";
			$scope.statisticsMatch();
			$scope.statisticsMatchSel();
		},
		selectAll:function(){
			if($scope.matchListTmp!=null && $scope.matchListTmp!=undefined){
				var objList = $scope.matchList;
				if(objList.length != $scope.matchListTmp.length){
					if(objList.length<=0){
						return false;
					}
					for(var k=0;k<objList.length;k++){
						$scope.select(objList[k]);
					}
					return false;
				}
				for(var j=0;j<$scope.matchListTmp.length;j++){;
					$scope.selectMatchListTmp.push($scope.matchListTmp[j]);
				}
				$scope.selectMatchList=$scope.selectMatchListTmp;
				$scope.matchList=[];
				$scope.matchListTmp=[];
				$scope.selectData.geTrackingNums="";
				$scope.selectData.geWeight="";
				$scope.selectData.geVolume="";
				$scope.selectData.geCount="";
				$scope.statisticsMatch();
				$scope.statisticsMatchSel();
			}
		},
		retrievalMatchSel:function(){
			if($scope.selectMatchListTmp!=null && $scope.selectMatchListTmp!=undefined && $scope.selectMatchListTmp.length>0 ){
				$scope.selectMatchList=[];
				var matchListNew=[];
				var str="";
				if($scope.selectData.geTrackingNums!=null  && $scope.selectData.geTrackingNums!=undefined && $scope.selectData.geTrackingNums!=""){
					if(str==""){
						str+=" trackingNum.indexOf("+$scope.selectData.geTrackingNums+")>-1";
					}else{
						str+=" && trackingNum.indexOf("+$scope.selectData.geTrackingNums+")>-1";
					}
				}
				if($scope.selectData.geWeight!=null  && $scope.selectData.geWeight!=undefined && $scope.selectData.geWeight!=""){
					if(str==""){
						str+=" weight>"+$scope.selectData.geWeight;
					}else{
						str+=" && weight>"+$scope.selectData.geWeight;
					}
				}
				if($scope.selectData.geVolume!=null  && $scope.selectData.geVolume!=undefined && $scope.selectData.geVolume!=""){
					if(str==""){
						str+=" volume>"+$scope.selectData.geVolume;
					}else{
						str+=" && volume>"+$scope.selectData.geVolume;
					}
				}
				if($scope.selectData.geCount!=null  && $scope.selectData.geCount!=undefined && $scope.selectData.geCount!=""){
					if(str==""){
						str+=" count>"+$scope.selectData.geCount;
					}else{
						str+=" && count>"+$scope.selectData.geCount;
					}
				}
				if(str!=""){
					for(var i=0;i<$scope.selectMatchListTmp.length;i++){
						var trackingNum = $scope.selectMatchListTmp[i].trackingNum;
						var weight = $scope.selectMatchListTmp[i].weight;
						var volume = $scope.selectMatchListTmp[i].volume;
						var count = $scope.selectMatchListTmp[i].count;
						var pjStr="if("+str+") matchListNew.push($scope.selectMatchListTmp[i])";
						eval(pjStr);
					}
					$scope.selectMatchList=matchListNew;
				}else{
					$scope.selectMatchList=$scope.selectMatchListTmp;
				}
				$scope.statisticsMatchSel();
			}
		},
		statisticsMatchSel:function(){
			$scope.cleanStatisticsMatchSel();
			if($scope.selectMatchList!=null && $scope.selectMatchList.length>0){
				$scope.matchCountSel=$scope.selectMatchList.length;
				for(var i=0;i<$scope.selectMatchList.length;i++){
					$scope.freightCollect+=(Number($scope.selectMatchList[i].freightCollect)/100);
					$scope.collectingMoney+=(Number($scope.selectMatchList[i].collectingMoney)/100);
					$scope.actualVolume+=$scope.selectMatchList[i].volume;
					$scope.actualWeight+=$scope.selectMatchList[i].weight;
				}
			}
		},
		cleanStatisticsMatchSel:function(){
			$scope.freightCollect=0;//选择后的到付
			$scope.collectingMoney=0;//选择后的货付
			$scope.actualVolume=0;//选择后的体积
			$scope.actualWeight=0;//选择后的重量
			$scope.matchCountSel=0;//选择后的票数
		},
		/**单选*/
		selectSel:function(data) {
			if($scope.matchListTmp==null || $scope.matchListTmp==undefined){
				$scope.matchListTmp=[];
			}
			$scope.matchListTmp.push(data);
			$scope.matchList=$scope.matchListTmp;
			$scope.delMatchListSel(data);
		},
		delMatchListSel:function(data){
			var listTmp = [];
			for(var j=0;j<$scope.selectMatchListTmp.length;j++){
				if(data.trackingNum!=$scope.selectMatchListTmp[j].trackingNum){
					listTmp.push($scope.selectMatchListTmp[j]);
				}
			}
			$scope.selectMatchListTmp=listTmp;
			var list = [];
			for(var j=0;j<$scope.selectMatchList.length;j++){
				if(data.trackingNum!=$scope.selectMatchList[j].trackingNum){
					list.push($scope.selectMatchList[j]);
				}
			}
			$scope.selectMatchList=list;
			$scope.selectData.trackingNums="";
			$scope.selectData.gtWeight="";
			$scope.selectData.gtVolume="";
			$scope.selectData.gtCount="";
			$scope.statisticsMatch();
			$scope.statisticsMatchSel();
		},
		selectAllSel:function(){
			var objList = $scope.selectMatchList;
			if(objList.length != $scope.selectMatchListTmp.length){
				if(objList.length<=0){
					return false;
				}
				for(var k=0;k<objList.length;k++){
					$scope.selectSel(objList[k]);
				}
				return false;
			}
			if($scope.matchListTmp==null || $scope.matchListTmp==undefined){
				$scope.matchListTmp=[];
			}
			for(var j=0;j<$scope.selectMatchListTmp.length;j++){;
				$scope.matchListTmp.push($scope.selectMatchListTmp[j]);
			}
			$scope.matchList=$scope.matchListTmp;
			$scope.selectMatchList=[];
			$scope.selectMatchListTmp=[];
			$scope.selectData.trackingNums="";
			$scope.selectData.gtWeight="";
			$scope.selectData.gtVolume="";
			$scope.selectData.gtCount="";
			$scope.statisticsMatch();
			$scope.statisticsMatchSel();
		},
		next:function(n){
    		if(n==1){
    			if($scope.selectMatchListTmp.length<=0){
    				 commonService.alert("请选择配载的运单!");
    	    		  return false ;
    			}
    			$scope.yeFlag=false;
    			$scope.diy.loadData($scope.selectMatchListTmp);
    		}else{
    			$scope.yeFlag=true;
    		}
    		setContentHegthDelay();
    		this.querySfInfo();
    	},
    	queryDispatchOrg:function(){
			commonService.postUrl("routeBO.ajax?cmd=queryRoateTowards","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.dispatchOrg = data;
				}
			});
		},
		selectDispatchOrg:function(stevedoringPayDot,freightPayDot){
			var descOrgId =  $scope.form.descOrgId;
			commonService.postUrl("organizationBO.ajax?cmd=getOrganization",{"orgId":descOrgId},function(data){
				if(data != null && data != undefined && data != ""){
					$scope.form.destOrgPhone=data.orgPrincipalPhone;
					$scope.form.destOrgAdder=data.departmentAddress;
					$scope.form.descOrgIdName=data.orgName;
					var obj = new Object();
					obj.phone=data.orgPrincipalPhone;
					obj.address=data.departmentAddress;
					obj.name=data.orgName;
					obj.descOrgId=data.orgId;
					$scope.selectSf(obj,stevedoringPayDot,freightPayDot);
				}
			});
		},
		selectVehicle:function(obj){
			$scope.form.driverId = obj.id;
			$scope.form.driverName = obj.name;
			$scope.form.driverBill = obj.bill;
			$scope.form.volume= obj.actualVolume;
			$scope.form.weight= obj.actualWeight;
			$scope.form.vehicleType= obj.vehicleType;
			$scope.form.vehicleTypeName = obj.vehicleTypeName;
			$scope.form.businessType=obj.businessType;
			$scope.form.vehicleId = obj.vehicleId;
			$scope.form.plateNumber = obj.plateNumber;
			$scope.showToFalse();
		},
		showToFalse:function(){
			$scope.showPcum = false;
			$scope.showRcum = false;
		},
		showTotrue:function(){
			$scope.doQueryVehile();
			$scope.showPcum = true;
		},
		clearSelect:function(){
			var selectedDate=$scope.diy.getSelectData();
			if(selectedDate.length>0){
				for(var i=0;i<selectedDate.length;i++){
					selectedDate[i].tootalMoneyString=(selectedDate[i].tootalMoney/100).toFixed(1);
					$scope.selectSel(selectedDate[i]);
				}
				$scope.diy.loadData($scope.selectMatchListTmp);
			}else{
				commonService.alert("请选择配载的运单!");
			}
		},
		doSave:function(){
			if($scope.form.descOrgId==null||$scope.form.descOrgId==undefined || $scope.form.descOrgId=='' || $scope.form.descOrgId<=0){
				commonService.alert("请输选择到达网点!");
				return false ;
			}
			if($scope.form.vehicleId==null||$scope.form.vehicleId==undefined ||$scope.form.vehicleId==''){
				commonService.alert("请输入车牌号查询车辆!");
				return false ;
			}
			if($scope.form.plateNumber==null||$scope.form.plateNumber==undefined ||$scope.form.plateNumber==''){
				commonService.alert("请输入车牌号!");
				return false ;
			}
			if($scope.form.driverName==null||$scope.form.driverName==undefined ||$scope.form.driverName==''){
				commonService.alert("请输入司机姓名!");
				return false ;
			}
			if($scope.form.driverBill==null||$scope.form.driverBill==undefined ||$scope.form.driverBill==''){
				commonService.alert("请输入司机手机!");
				return false ;
			}
			if($scope.form.vehicleType==null||$scope.form.vehicleType==undefined ||$scope.form.vehicleType==''){
				commonService.alert("请选择车辆类型!");
				return false ;
			}
			if($scope.form.weight==null||$scope.form.weight==undefined ||$scope.form.weight==''){
				commonService.alert("请输入可载重量!");
				return false ;
			}
    	  	if($scope.form.volume==null||$scope.form.volume==undefined ||$scope.form.volume==''){
				commonService.alert("请输入可载体积!");
				return false ;
			}
	        if($scope.form.freight!=null && $scope.form.freight!=undefined && Number($scope.form.freight)>0){
	        	if($scope.form.freightPayDot==null || $scope.form.freightPayDot==undefined || $scope.form.freightPayDot=='' || $scope.form.freightPayDot<=0){
	    			commonService.alert("请选择运费的付款方!");
	    			return false ;
	    		}
//				if($scope.form.payState==null || $scope.form.payState==undefined || $scope.form.payState==''){
//					commonService.alert("请选择运费的支付状态!");
//					return false ;
//				}
	        	if($scope.form.payStateFlag){
	        		$scope.form.payState = 1; //未付
	        	}else{
	        		$scope.form.payState = 2; //已付
	        	}
//	        	console.log("$scope.form.payStateFlag"+$scope.form.payStateFlag);
				$scope.form.freightFee=$scope.form.freight*100;
			}
			if($scope.form.stevedoring!=null && $scope.form.stevedoring!=undefined && Number($scope.form.stevedoring)>0){
				if($scope.form.stevedoringPayDot==null || $scope.form.stevedoringPayDot==undefined || $scope.form.stevedoringPayDot=='' || $scope.form.stevedoringPayDot<=0){
					commonService.alert("请选择装卸费的付款方!");
					return false ;
				}
//				if($scope.form.stevedoringPayState==null || $scope.form.stevedoringPayState==undefined || $scope.form.stevedoringPayState==''){
//					commonService.alert("请选择装卸费的支付状态!");
//					return false ;
//				}
				if($scope.form.stevedoringPayStateFlag){
					$scope.form.stevedoringPayState = 1;//未付
				}else{
					$scope.form.stevedoringPayState = 2;//已付
				}
//				console.log("$scope.form.stevedoringPayStateFlag"+$scope.form.stevedoringPayStateFlag);
				$scope.form.stevedoringFee=$scope.form.stevedoring*100;
			}
			if($scope.selectMatchList.length==0){
				commonService.alert("请选择待发货订单到配载列表!");
				return false ;
			}
			var orderId='';
			var tootalMoneys="";
	        for(var i=0;i<$scope.diy.data.items.length;i++){
	        	if(i==0){
	        		orderId=$scope.diy.data.items[i].orderId;
	        		tootalMoneys+=$scope.diy.data.items[i].orderId+":"+$scope.diy.data.items[i].tootalMoneyString;
	        	}else{
	        		orderId=orderId+","+$scope.diy.data.items[i].orderId;
	        		tootalMoneys+=","+$scope.diy.data.items[i].orderId+":"+$scope.diy.data.items[i].tootalMoneyString;
	        	}
	        }
	        $scope.form.tootalMoneys=tootalMoneys;
			$scope.form.orderId=orderId;
			var tips="所选车辆["+$scope.form.plateNumber+"]，可配置重量为["+$scope.form.weight+"]吨，当前配置信息中，货物重量为["+($scope.actualWeight/1000).toFixed(2)+"]吨，";
			if( parseFloat($scope.form.weight)< parseFloat($scope.actualWeight/1000)){
				tips=tips+"已经超出可配载范围，确定要继续 保存配载信息？";
			}else{
				tips=tips+"确定要继续 保存配载信息？";
			}
			commonService.confirm(tips,function(){
				var param = $scope.form;
				var url = "orderInfoBO.ajax?cmd=matchVehicle";
				commonService.postUrl(url,param,function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
						//如果是修改就不清空。
						var batchNum=getQueryString("batchNum");
			    		if(batchNum!=null && batchNum!=undefined && batchNum!=''){
			    			commonService.alert("操作成功!");
			    			commonService.closeToParentTab(false);
			    			return false;
			    		}
						$scope.form.descOrgId="-1";
						$scope.form.descOrgIdName="";
						$scope.form.destOrgPhone="";
						$scope.form.destOrgAdder="";
						$scope.form.driverName = '';
						$scope.form.driverBill = '';
						$scope.form.vehicleId = '';
						$scope.form.vehicleTypeName = '';
						$scope.form.vehicleType= '-1';
						$scope.form.businessType= '-1';
						$scope.form.weight= '';
						$scope.form.volume= '';
						$scope.form.plateNumber ='';
						$scope.form.freight="";
						$scope.form.freightPayDot= '-1';
						$scope.form.payState= '';
						$scope.form.stevedoring= '';
						$scope.form.stevedoringPayDot= '-1';
						$scope.form.stevedoringPayState= '';
						$scope.form.driverId="";
						$scope.form.remarks="";
						$scope.selectData={};
						$scope.selectMatchList=[];
						$scope.selectMatchListTmp=[];
						$scope.diy.loadData($scope.selectMatchListTmp);
						$scope.doQuery();
						commonService.confirm("操作成功，是否继续配载？",function(){
							$scope.doQuery();
							$scope.next(2);
						},function(){
							commonService.closeToParentTab(false);
						});
		 	    	}
				});
			});
		},
		toView:function(){
			/**根据运单号查询*/
    		var batchNum=getQueryString("batchNum");
    		if(batchNum!=null && batchNum!=undefined && batchNum!=''){
    			var queryString={"batchNum":batchNum};
    			var url="orderInfoBO.ajax?cmd=getByBacthInfo";
    			commonService.postUrl(url,queryString,function(data){
    				if(data==null || data==undefined){
    					return false;
    				}
    				for (var i = 0; i < data.list.length; i++) {
    					$scope.selectMatchList.push(data.list[i]);
    					$scope.selectMatchListTmp.push(data.list[i]);
    					$scope.collectingMoney=data.list[i].collectingMoney;
    					$scope.freightCollect=data.list[i].freightCollect;
    				}
    				/**拷贝配载的数据**/
    				$scope.form.weight=data.weight;
    				$scope.form.batchNum=data.batchNum;
    				$scope.form.driverBill=data.driverBill;
    				$scope.form.driverName=data.driverName;
    				$scope.form.volume=data.volume;
    				$scope.form.vehicleId=data.vehicleId;
    				$scope.form.businessType=data.businessType;
    				$scope.form.vehicleType=data.vehicleType;
    				$scope.form.batchNum=data.batchNum;
    				$scope.form.currOrgId=data.sourceOrgId;
    				$scope.form.currOrgName=data.sourceOrgIdName;
    				$scope.form.descOrgId=data.descOrgId;
    				$scope.form.plateNumber=data.plateNumber;
    				if(data.freight>-1){
    					$scope.form.freight=data.freight/100;
    				}
    				$scope.form.freightPayDot=data.freightPayDot;
    				$scope.form.payState=data.payState;
    				if(data.stevedoring>-1){
    					$scope.form.stevedoring=data.stevedoring/100;
    				}
    				//1未付、2已付
    				if(data.payState == 1 || data.payState == "1"){
    					$scope.form.payStateFlag = true;
    				}else{
    					$scope.form.payStateFlag = false;
    				}
    				
    				$scope.form.stevedoringPayDot=data.stevedoringPayDot;
    				$scope.form.stevedoringPayState=data.stevedoringPayState;
    				
    				//1未付、2已付
    				if(data.stevedoringPayState == 1 || data.stevedoringPayState =="1"){
    					$scope.form.stevedoringPayStateFlag = true;
    				}else{
    					$scope.form.stevedoringPayStateFlag = false;
    				}
    		 
    				$scope.form.driverId=data.driverId;
    				$scope.form.remarks=data.remarks;
    				$scope.form.transportContract=data.transportContract;
    				$scope.selectDispatchOrg(data.stevedoringPayDot,data.freightPayDot);
    				$scope.statisticsMatchSel();
    				setContentHegthDelay();
    			});
    		}
    	},
    	openDirect:function(){
    		$scope.directCustFlag=true;
    	},
    	cosleDirect:function(){
    		$scope.directCustFlag=false;
    	},
    	querySfInfo:function(){
			var sfQueryValue = $scope.directQuery.sfQueryValue;
			var sfQueryType = $scope.directQuery.sfQueryType;
			var sfUserName = "";
			var sfUserAcct = "";
			if(sfQueryType!=null && sfQueryType!=undefined && sfQueryType=="1"){
				sfUserName = sfQueryValue;
			}
			if(sfQueryType!=null && sfQueryType!=undefined && sfQueryType=="2"){
				sfUserAcct = sfQueryValue;
			}
			//获取安装师傅
			commonService.postUrl("cmSfUserInfoBO.ajax?cmd=queryUserInfoList",{"sfUserName":sfUserName,"sfUserAcct":sfUserAcct},function(data){
				$scope.sfQueryList = data;
			});
		},
		selectSf:function(data,stevedoringPayDot,freightPayDot){
			$scope.form.destOrgPhone=data.phone;
			$scope.form.destOrgAdder=data.address;
			$scope.form.descOrgIdName=data.name;
			$scope.form.descOrgId=data.descOrgId;
			this.cosleDirect();
			$scope.dispatchOrgList=[];
			$scope.form.stevedoringPayDot="-1";
			$scope.form.freightPayDot="-1";
			if(stevedoringPayDot!=null && stevedoringPayDot!=undefined && stevedoringPayDot!=""){
				$scope.form.stevedoringPayDot=stevedoringPayDot;
			}
			if(freightPayDot!=null && freightPayDot!=undefined && freightPayDot!=""){
				$scope.form.freightPayDot=freightPayDot;
			}
			var obj = new Object();
			obj.endOrgId=userInfo.orgId;
			obj.endOwnerName=userInfo.orgName;
			$scope.dispatchOrgList.push(obj);
			var obj1 = new Object();
			obj1.endOrgId=data.descOrgId;
			obj1.endOwnerName=data.name;
			$scope.dispatchOrgList.push(obj1);
		},
    	
    	
    	
    	deepCopy:function (obj) {
			//拷贝数组
		    if (typeof obj != "object") {
		        return obj;
		    }
		    var valueList=new Array();
		    var result={};
		    for (var key in obj) {
		        if (undefined != obj[key] && null != obj[key] && typeof obj[key] == "object") {
		        	result[key] = this.deepCopy(obj[key]);
		        } else {
		        	result[key] = obj[key];
		        }
		    }
		    return result;
		},
		params:{
			totalPage:1,
			vehicleState:1
		},
		selectData:{
			trackingNums:"",
			gtWeight:"",
			gtVolume:"",
			gtCount:"",
			geTrackingNums:"",
			geWeight:"",
			geVolume:"",
			geCount:"",
		},
		form:{
			batchNum:'',
			freight:'',
			descOrgId:'',
			plateNumber:'',
			vehicleId:'',
			driverBill:'',
			driverName:'',
			vehicleType:'',
			weight:'',
			volume:'',
			vehicleType:'',
			vehicleTypeName:'',
			currOrgId:'',
			currOrgName:''
		},
		fixNumber:function (){
			$scope.form.freight =  $scope.form.freight.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
			$scope.form.freight =  $scope.form.freight.replace(/^\./g,"");  //验证第一个字符是数字而不是. 
			$scope.form.freight =  $scope.form.freight.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.   
			$scope.form.freight =  $scope.form.freight.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
		},
		//设置发货方
		doAddViechInfo:function(){
			commonService.openTab("10001212","新增车辆信息","/cm/cmVehicleInfoAdd.html");
		},
		
		remove:function(data){
			for(var i=0;i<$scope.order.data.items.length;i++){
				if(data.orderId==$scope.order.data.items[i].orderId){
					$scope.order.data.items.splice(i,1);
					break;
				}
			}
		},
		
		removeClear:function(data){
			var value = new Array();
			for(var j=0;j<$scope.orderInfoList.length;j++){
				value.push($scope.orderInfoList[j]);
			}
			for(var x=0;x<value.length;x++){
				if(data.orderId==value[x].orderId){
					value.splice(x,1);
					break;
				}
			}
			$scope.orderInfoList= value;
			
			
		},
		//经营方式改变
		vehicleChange:function(businessType){
		   	if(businessType==3){
		   		$scope.form.driverName = "";
	    		$scope.form.driverBill ="";
	    		$scope.form.vehicleId = "";
	    		$scope.form.vehicleType= "1";
	    		$scope.form.weight= "";
	    		$scope.form.volume= "";
	    		$scope.form.plateNumber = "";
		   	}
		},
		
		
		/**快找 待配载**/
		checkData:function(){
			$scope.order.data.items = $scope.orderInfo;
			var itemValue=new Array();
			var r= /^[1-9]?[0-9]*\.[0-9]*$/;
			if($scope.selectData.gtWeight=="" ||isNaN($scope.selectData.gtWeight)){
				$scope.selectData.gtWeight=0;	
			}else{
				if(r.test($scope.selectData.gtWeight)){
					//是浮点数
					$scope.selectData.gtWeight=parseFloat($scope.selectData.gtWeight);
					$scope.selectData.gtWeight=parseFloat(0);
				}else{
					$scope.selectData.gtWeight=parseInt($scope.selectData.gtWeight);
				}
			}
			if($scope.selectData.gtVolume==""||isNaN($scope.selectData.gtVolume)){
				$scope.selectData.gtVolume=0;	
				
			}else{
				if(r.test($scope.selectData.gtVolume)){
					//是浮点数
					$scope.selectData.gtVolume=parseFloat($scope.selectData.gtVolume);
					$scope.selectData.gtVolume=parseFloat(0);
				}else{
					$scope.selectData.gtVolume=parseInt($scope.selectData.gtVolume);
				}
			}
			if($scope.selectData.gtCount==""||isNaN($scope.selectData.gtCount)){
				$scope.selectData.gtCount=0;	
			}else{
				if(r.test($scope.selectData.gtCount)){
					//是浮点数
					$scope.selectData.gtCount=parseFloat($scope.selectData.gtCount);
					$scope.selectData.gtCount=parseFloat(0);
				}else{
					$scope.selectData.gtCount=parseInt($scope.selectData.gtCount);
				}
			}
				for(var j=0;j<$scope.orderInfo.length;j++){
				   if(($scope.orderInfo[j].trackingNum.indexOf($scope.selectData.trackingNums)) >= 0 
						   && $scope.orderInfo[j].weight > $scope.selectData.gtWeight 
						   && $scope.orderInfo[j].volume > $scope.selectData.gtVolume 
						   && $scope.orderInfo[j].count > $scope.selectData.gtCount){
					   var isFind = false;
					  if($scope.orderInfoList!=null && $scope.orderInfoList!=undefined && $scope.orderInfoList!=""){
						for(var g=0; g<$scope.orderInfoList.length;g++){
							if($scope.orderInfo[j].orderId==$scope.orderInfoList[g].orderId){
								isFind = true;
								break;
							}
						}
					  }
						if (!isFind)
							itemValue.push($scope.orderInfo[j]);
					}
				}
				$scope.order.data.items=itemValue;
				addDepart.calculate();
				if($scope.order.data.items.length>$scope.list.length){
					$scope.chechHight($scope.order.data.items);
				}else{
					$scope.chechHight($scope.list);
				}
				
				if($scope.selectData.gtWeight<=0){
					$scope.selectData.gtWeight="";
				}
				if($scope.selectData.gtVolume<=0){
					$scope.selectData.gtVolume="";
				}
				if($scope.selectData.gtCount<=0){
					$scope.selectData.gtCount="";
				}
				
		},
		
		chechHight:function(obj){
			if (obj == undefined ||obj.length == 0) {// 没有数据，指定一个默认高度
				setContentHeightWithSpecialHeight(683);
			} else {// 有数据，则根据数据行计算高度
				var height = (683 - 270) +obj.length * 31 ;
				height = height <= 683 ? 683 : height;
				setContentHeightWithSpecialHeight(height);
			}
		},
		
		/**快找 配载**/
		isCheckData:function(){
			var itemValue=new Array();
			var r= /^[1-9]?[0-9]*\.[0-9]*$/;
			if($scope.selectData.geWeight=="" ||isNaN($scope.selectData.geWeight)){
				$scope.selectData.geWeight=0;	
			}else{
				if(r.test($scope.selectData.geWeight)){
					//是浮点数
					$scope.selectData.geWeight=parseFloat($scope.selectData.geWeight);
					$scope.selectData.geWeight=parseFloat(0);
				}else{
					$scope.selectData.geWeight=parseInt($scope.selectData.geWeight);
				}
			}
			if($scope.selectData.geVolume==""||isNaN($scope.selectData.geVolume)){
				$scope.selectData.geVolume=0;	
				
			}else{
				if(r.test($scope.selectData.geVolume)){
					//是浮点数
					$scope.selectData.geVolume=parseFloat($scope.selectData.geVolume);
					$scope.selectData.geVolume=parseFloat(0);
				}else{
					$scope.selectData.geVolume=parseInt($scope.selectData.geVolume);
				}
			}
			if($scope.selectData.geCount==""||isNaN($scope.selectData.geCount)){
				$scope.selectData.geCount=0;	
			}else{
				if(r.test($scope.selectData.geCount)){
					//是浮点数
					$scope.selectData.geCount=parseFloat($scope.selectData.geCount);
					$scope.selectData.geCount=parseFloat(0);
				}else{
					$scope.selectData.geCount=parseInt($scope.selectData.geCount);
				}
			}
				for(var j=0;j<$scope.orderInfoList.length;j++){
				   if(($scope.orderInfoList[j].trackingNum.indexOf($scope.selectData.geTrackingNums)) >= 0 
						   && $scope.orderInfoList[j].weight > $scope.selectData.geWeight 
						   && $scope.orderInfoList[j].volume > $scope.selectData.geVolume 
						   && $scope.orderInfoList[j].count > $scope.selectData.geCount){
						itemValue.push($scope.orderInfoList[j]);
					}
				}
				$scope.list=itemValue;
				addDepart.calculate();
				if($scope.order.data.items.length>$scope.list.length){
					$scope.chechHight($scope.order.data.items);
				}else{
					$scope.chechHight($scope.list);
				}
				if($scope.selectData.geWeight<=0){
					$scope.selectData.geWeight="";
				}
				if($scope.selectData.geVolume<=0){
					$scope.selectData.geVolume="";
				}
				if($scope.selectData.geCount<=0){
					$scope.selectData.geCount="";
				}
		},
		add:function(data){
			$scope.order.data.items.push(data);
			
		
		},
		
		/**
		 *配载发车全选调用
		 * @param data
		 */
		addClear:function(data){
			var value = new Array();
			if($scope.orderInfoList!=null && $scope.orderInfoList!=undefined){
				for(var j=0;j<$scope.orderInfoList.length;j++){
					value.push($scope.orderInfoList[j]);
				}
			}
			value.push(data);
			$scope.orderInfoList= value;
		},
		
		
		
		
		calculate:function(){
			//这是配在统计
			$scope.freightCollect = 0;
			$scope.collectingMoney = 0;
			$scope.actualVolume = 0;
			$scope.actualWeight = 0;
			for(var i=0;i<$scope.list.length;i++){
				if($scope.list[i].orderState!=5){
					$scope.actualVolume = $scope.actualVolume+$scope.list[i].volume;
					$scope.actualWeight = $scope.actualWeight+$scope.list[i].weight;
					$scope.freightCollect = $scope.freightCollect+($scope.list[i].freightCollect/100);
					$scope.collectingMoney = $scope.collectingMoney+($scope.list[i].collectingMoney/100);
			   }
			}
			if($scope.freightCollect == 0){
				$scope.freightCollect = null;
			}
			if($scope.collectingMoney == 0){
				$scope.collectingMoney = null;
			}
			if($scope.actualVolume == 0){
				$scope.actualVolume = null;
			}else{
				$scope.actualVolume = $scope.actualVolume.toFixed(2);
			}
			if($scope.actualWeight == 0){
				$scope.actualWeight = null;
			}else{
				$scope.actualWeight = $scope.actualWeight.toFixed(1);
			}
			if($scope.order.data!=null && $scope.order.data!=undefined && $scope.order.data!=""){
				var orderData = $scope.order.data.items;
				$scope.freightCollectWait = 0;
				$scope.collectingMoneyWait = 0;
				$scope.actualVolumeWait = 0;
				$scope.actualWeightWait = 0;
				for(var i=0;i<orderData.length;i++){
					if(orderData[i].orderState != 5){
						$scope.actualVolumeWait = $scope.actualVolumeWait+orderData[i].volume;
						$scope.actualWeightWait = $scope.actualWeightWait+orderData[i].weight;
						$scope.freightCollectWait = $scope.freightCollectWait+(orderData[i].freightCollect/100);
						$scope.collectingMoneyWait = $scope.collectingMoneyWait+(orderData[i].collectingMoney/100);
				   };
				};
				if($scope.freightCollectWait == 0){
					$scope.freightCollectWait = null;
				}
				if($scope.collectingMoneyWait == 0){
					$scope.collectingMoneyWait = null;
				}
				if($scope.actualVolumeWait == 0){
					$scope.actualVolumeWait = null;
				}else{
					$scope.actualVolumeWait = $scope.actualVolumeWait.toFixed(2);
				}
				if($scope.actualWeightWait == 0){
					$scope.actualWeightWait = null;
				}else{
					$scope.actualWeightWait = $scope.actualWeightWait.toFixed(1);
				}
			}
			
		},
		/**全选清除*/
		selectClear:function(){
			for(var i=0;i<$scope.list.length;i++){
				addDepart.add($scope.list[i]);
			}
			
			for(var j=0;j<$scope.list.length;j++){
				addDepart.removeClear($scope.list[j]);
			}
	
			/*$scope.orderInfoList= addDepart.deepCopy($scope.list);
			$scope.orderInfoList.length=$scope.list.length;*/
			$scope.list=new Array();
			addDepart.calculate();
			if($scope.page.total==0){
				$scope.order.total=$scope.order.data.items.length;
			}
			if($scope.page.isLoad=false){
				$scope.order.isLoad=true;
			}
		},
		/**单选清除**/
		clear:function(data){
			for(var i=0;i<$scope.list.length;i++){
				if(data.orderId==$scope.list[i].orderId){
					$scope.list.splice(i,1);
					break;
				}
			}
			addDepart.removeClear(data);
		/*	$scope.orderInfoList = addDepart.deepCopy($scope.list);
			$scope.orderInfoList.length=$scope.list.length;*/
			addDepart.add(data);
			addDepart.calculate();
			if($scope.order.total==0){
				$scope.order.total=$scope.order.data.items.length;
			}
			if($scope.order.isLoad=false){
				$scope.order.isLoad=true;
			}
		},
		/**车辆列表查询*/
		doQueryVehile:function(plateNumber){
				addDepart.params.plateNumber=plateNumber;
				addDepart.params.orgId=userInfo.orgId;
				var url = "vehicleInfoBO.ajax?cmd=doQuery";
				$scope.page.load({
					url:url,
					params:addDepart.params
				});
				$scope.showPcum = true;
		},
		close:function(){
    		commonService.closeToParentTab(true);
    	},
    	changeDescOrg:function(descOrgId){
    		addDepart.params.descOrgId=descOrgId;
    		
    		addDepart.form.descOrgId=descOrgId;
    		addDepart.doQuery();
    		$scope.list=[];
    		$scope.doQueryVehile();
    		$scope.form.driverName = "";
    		$scope.form.driverBill ="";
    		$scope.form.vehicleId = "";
    		$scope.form.vehicleTypeName = "";
    		$scope.form.vehicleType= "";
    		$scope.form.weight= "";
    		$scope.form.volume= "";
    		$scope.form.plateNumber = "";
    		$scope.freightCollect=0;
		    $scope.collectingMoney=0;
		    $scope.actualVolume=0;
		    $scope.actualWeight=0;
    	}

		
	};
	
	addDepart.init();
}]);