var addDepartOtherApp = angular.module("addDepartOtherApp", ['commonApp']);
addDepartOtherApp.controller("addDepartOtherCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var addDepart={
		init:function(){
			this.toView();
			this.userData();
			var batchNum=getQueryString("batchNum");
			this.bindEvent();
			//发货人弹窗
			$scope.showPcum = false;
			$scope.list=new Array();
			$scope.orderInfo=new Array();
			this.registerKeyEvent(); //键盘绑定事件
			this.doQuery();
		},
		bindEvent:function(){
			$scope.param=this.param;
			$scope.doQuery=this.doQuery;
		    $scope.print=this.print;
		    $scope.doSave=this.doSave;
		    $scope.doQueryVehile=this.doQueryVehile;
		    $scope.form=this.form;
		    $scope.clear=this.clear;
		    $scope.select=this.select;
		    $scope.selectAll=this.selectAll;
		    $scope.showToFalse=this.showToFalse;
		    $scope.selectVehicle=this.selectVehicle;
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
		    $scope.vehicleChange=this.vehicleChange;
		},
		params:{
			totalPage:1,
			vehicleState:1
		},
		query:{
			orderState:2
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
		userData:function(){
			$scope.currOrgId=userInfo.orgId;
			$scope.currOrgName=userInfo.orgName;
		},
		//键盘绑定事
		registerKeyEvent :function(){
			this.registerKeyEventForDomsGetFocus('keydown', 'return', "trackingNumOneEnter", function(evt){
				if($scope.trackingNumOne != undefined && $scope.trackingNumOne != null && $scope.trackingNumOne != ''){
					addDepart.query.trackingNum = $scope.trackingNumOne;
					addDepart.doQuery(); 
				}else{
					addDepart.query.trackingNum="";
					addDepart.doQuery(); 
				}
				return false;
			});
		},
		//键盘绑定事件 (为某个元素绑定)
		registerKeyEventForDomsGetFocus: function(keyEvent, key, domEleIds, callback) { // 注册键盘事件
			jQuery("#"+domEleIds).bind('keydown', key, function(evt){
				if (undefined != callback)
					callback(evt);
                return false;
            });
		},
		fixNumber:function (){
			$scope.form.freight =  $scope.form.freight.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
			$scope.form.freight =  $scope.form.freight.replace(/^\./g,"");  //验证第一个字符是数字而不是. 
			$scope.form.freight =  $scope.form.freight.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.   
			$scope.form.freight =  $scope.form.freight.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
		},
		//设置发货方
		selectVehicle:function(obj){
			addDepart.form.driverName = obj.name;
			addDepart.form.driverBill = obj.bill;
			addDepart.form.vehicleId = obj.vehicleId;
			addDepart.form.vehicleTypeName = obj.vehicleTypeName;
			$scope.form.vehicleType= obj.vehicleType+"";
			addDepart.form.weight= obj.actualWeight;
			addDepart.form.voluem= obj.actualVolume;
			addDepart.form.plateNumber = obj.plateNumber;
			addDepart.showToFalse();
		},
		//隐藏div
		showToFalse:function(){
			$timeout(function(){
				$scope.showPcum = false;
				$scope.showRcum = false;
			},200);
		},
		/**单选*/
		select:function(data) {
			addDepart.remove(data);
			$scope.list.push(data);
		
			addDepart.addClear(data);
			addDepart.calculate();
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
	    		$scope.form.voluem = "";
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
		},
		/**
		 *拷贝数组
		 * @param obj
		 * @returns
		 */
		deepCopy:function (obj) {
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
		/**全选*/
		selectAll:function(data){
			if($scope.list==null||$scope.list==undefined||$scope.list.length==0){
				$scope.list=new Array();
			}
			for(var i=0;i<data.items.length;i++){
				$scope.list.push(data.items[i]);
			}
			$scope.order.data.items=new Array();
			addDepart.calculate();
			$scope.orderInfoList= addDepart.deepCopy($scope.list);
			$scope.orderInfoList.length=$scope.list.length;
			$scope.order.total==0;
		},
		/**全选清除*/
		selectClear:function(){
			for(var i=0;i<$scope.list.length;i++){
				addDepart.add($scope.list[i]);
			}
			
			for(var j=0;j<$scope.list.length;j++){
				addDepart.removeClear($scope.list[j]);
			}
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
			addDepart.add(data);
			addDepart.calculate();
			if($scope.order.total==0){
				$scope.order.total=$scope.order.data.items.length;
			}
			if($scope.order.isLoad=false){
				$scope.order.isLoad=true;
			}
		},
		showTotrue:function(){
			if($scope.showPcum==false){
				addDepart.doQueryVehile();
			}
			$scope.showPcum = true;
		},
		/**车辆列表查询*/
		doQueryVehile:function(plateNumber){
				addDepart.params.plateNumber=plateNumber;
				addDepart.params.descOrgId=addDepart.form.descOrgId;
				var url = "vehicleInfoBO.ajax?cmd=doQuery";
				$timeout(function(){
					$scope.page.load({
								url:url,
								params:addDepart.params
							});
				},500);
				$scope.showPcum = true;
		},
		/**订单列表查询*/
		doQuery : function(){
				addDepart.query._ALLEXPORT=1;
				addDepart.query.page=1;
				addDepart.query.type = 2;
				var url = "orderInfoBO.ajax?cmd=doQueryOther";
				$timeout(function(){
					$scope.order.load({
								url:url,
								params:addDepart.query,
								callBack:"$scope.callBack"
					});
		         	$scope.callBack=function(data){
						$scope.orderInfo= addDepart.deepCopy(data.items);
						$scope.orderInfo.length=data.items.length;
						for(var i=0;i<$scope.list.length;i++){
							$scope.remove($scope.list[i]);	
						}
						addDepart.calculate(); //计算待发货统计
						setContentHeight();
							$scope.selectData.trackingNums="";
							$scope.selectData.gtWeight="";
							$scope.selectData.gtVolume="";
							$scope.selectData.gtCount="";
							$scope.selectData.geTrackingNums="";
							$scope.selectData.geWeight="";
							$scope.selectData.geVolume="";
							$scope.selectData.geCount="";
					};
				},500);
		},
		doSave:function(){
			 if($scope.form.businessType==3){
	        	  $("input[name='identity']:checked").each(function(){
	  	        	if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
	  					var payState=eval("("+$(this).val()+")");
	  					$scope.form.payState=payState;
	  					//batchNum=data.batchNum;
	  				}
	  			});
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
	        	  if($scope.form.voluem==null||$scope.form.voluem==undefined ||$scope.form.voluem==''){
						commonService.alert("请输入可载体积!");
						return false ;
					}
	        	  
	        	  
	        }else{
	        	$scope.form.payState=0;
	        	if($scope.form.vehicleId==null||$scope.form.vehicleId==undefined ||$scope.form.vehicleId==''){
					commonService.alert("请输入车牌号查询车辆!");
					return false ;
				}
	        }
	      
			
			if($scope.list.length==0){
				commonService.alert("请选择待发货订单到配载列表!");
				return false ;
			}
			var orderId='';
	        for(var i=0;i<$scope.list.length;i++){
	        	if(i==0){
	        		orderId=$scope.list[i].orderId;
	        	}else{
	        		orderId=orderId+","+$scope.list[i].orderId;
	        	}
	        }
	        for(var j=0;j<$scope.list.length;j++){
				addDepart.removeClear($scope.list[j]);
			}
	       
			$scope.form.orderId=orderId;
			var tips="所选车辆["+$scope.form.plateNumber+"]，可配置重量为["+$scope.form.weight+"]吨，当前配置信息中，货物重量为["+$scope.actualWeight/1000+"]吨，";
			if( parseFloat($scope.form.weight)< parseFloat($scope.actualWeight/1000)){
				tips=tips+"已经超出可送货范围，确定要继续 保存送货信息？";
			}else{
				tips=tips+"确定要继续 保存送货信息？";
			}
			commonService.confirm(tips,function(){
				var param = $scope.form;
				var url = "orderInfoBO.ajax?cmd=matchVehicleTransitOther";
				commonService.postUrl(url,param,function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
							commonService.alert("操作成功!");
							$scope.list=new Array();
							addDepart.form.driverName = '';
							addDepart.form.driverBill = '';
							addDepart.form.vehicleId = '';
							addDepart.form.vehicleTypeName = '';
							addDepart.form.vehicleType= '';
							addDepart.form.weight= '';
							addDepart.form.voluem= '';
							addDepart.form.plateNumber ='';
							addDepart.form.freight="";
							addDepart.form_billing="";
							addDepart.form_billing="";
							addDepart.form.freight= '';
							$scope.selectData.trackingNums="";
				    		$scope.selectData.gtWeight="";
				    		$scope.selectData.gtVolume="";
				    		$scope.selectData.gtCount="";
				    		$scope.selectData.geTrackingNums="";
				    		$scope.selectData.geWeight="";
				    		$scope.selectData.geVolume="";
				    		$scope.selectData.geCount="";
							$scope.freightCollect=0;
							$scope.collectingMoney=0;
							$scope.actualVolume=0;
							$scope.actualWeight=0;
							addDepart.doQuery();
		 	    	}
				});
			});
			
		},
		close:function(){
    		commonService.closeToParentTab(true);
    	},
    	toView:function(){
    		var batchNum=getQueryString("batchNum");
    		if(batchNum!=null&&batchNum!=undefined&& batchNum!=''){
    			var queryString="batchNum="+batchNum;
    			var url="orderInfoBO.ajax?cmd=getByBacthInfoOther";
    			commonService.postUrl(url,queryString,function(data){
    				for (var i = 0; i < data.list.length; i++) {
    					$scope.list.push(data.list[i]);
    					collectingMoney=data.list[i].collectingMoney;
    					freightCollect=data.list[i].freightCollect;
    				}
    				/**拷贝配载的数据**/
    				$scope.orderInfoList= addDepart.deepCopy($scope.list);
    				$scope.orderInfoList.length=$scope.list.length;
    				
    				addDepart.form.weight=data.weight;
    				addDepart.form.batchNum=data.batchNum;
    				addDepart.form.driverBill=data.driverBill;
    				addDepart.form.driverName=data.driverName;
    				addDepart.form.voluem=data.volume;
    				addDepart.form.vehicleId=data.vehicleId;
    				$scope.form.businessType=data.businessType+"";
    				$scope.form.vehicleType=data.vehicleType+"";
    			   if(data.payState>0){
    				   document.getElementById("checkBox_"+data.payState).checked=true;
    			    }
    				addDepart.form.batchNum=data.batchNum;
    				addDepart.form.currOrgId=data.sourceOrgId;
    				addDepart.form.currOrgName=data.sourceOrgIdName;
    				addDepart.form.descOrgId=data.descOrgId;
    				addDepart.form.plateNumber=data.plateNumber;
    				addDepart.form.freight=data.freight/100;
    				addDepart.calculate();
    			});
    		}
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
    		$scope.form.voluem= "";
    		$scope.form.plateNumber = "";
    		$scope.freightCollect=0;
		    $scope.collectingMoney=0;
		    $scope.actualVolume=0;
		    $scope.actualWeight=0;
    	}

		
	};
	
	addDepart.init();
}]);
