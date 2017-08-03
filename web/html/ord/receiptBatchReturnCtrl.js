var batchApp = angular.module("batchApp", ['commonApp']);
batchApp.controller("batchCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var batch = {
		init:function(){
			this.userData();
			this.bindEvent();
			this.doQueryUser();
			this.getPayData();
			//发货人弹窗
			$scope.showPcum = false;
			$scope.list=new Array();
			$scope.orderInfo=new Array();
			this.registerKeyEvent(); //键盘绑定事件
			$scope.isTrue = false;
			$scope.allCount = 0;
			$scope.selectCount = 0;
			this.doQuery();
		},
		bindEvent:function(){
			$scope.param=this.param;
			$scope.doQuery=this.doQuery;
		    $scope.print=this.print;
		    $scope.doSave=this.doSave;
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
		    $scope.selectData=this.selectData;
		    $scope.checkData=this.checkData;
		    $scope.isCheckData=this.isCheckData;
		    $scope.removeClear=this.removeClear;
		    $scope.addClear=this.addClear;
		    $scope.chechHight=this.chechHight;
		    $scope.vehicleChange=this.vehicleChange;
		    $scope.query = this.query;
		},
		getPayData : function(){
			//获取付款方式
			commonService.postUrl("staticDataBO.ajax?cmd=selectPaymentType","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.paymentTypeData = data;
					if(data.items != null && data.items != undefined && data.items != ""){
						$scope.paymentTypeData.items.unshift({codeVaule:-1,codeName:'全部'});
					}
				}
			});
		},
		params:{
			totalPage:1,
			vehicleState:1
		},
		query:{
			cashSts    : "-1",
			_ALLEXPORT : 1,
			opId : -1
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
		doQueryUser : function(){
			var param = {};
			var url = "webCmUserInfoBO.ajax?cmd=doQueryOrgUser";
			commonService.postUrl(url,param,function(data){
				$scope.userOpData = data.items;
				$scope.userOpData.unshift({userId:-1,userName:'所有'});
			});
		},
		//键盘绑定事
		registerKeyEvent :function(){
			//input 光标必须在文本框中
			commonService.registerKeyEventForDoms(['trackingNumEnterA'], 'keydown', 'return' , function(evt) {
				$scope.checkData();
			});
			
			commonService.registerKeyEventForDoms(['trackingNumEnterB'], 'keydown', 'return' , function(evt) {
				$scope.isCheckData();
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
			batch.form.driverName = obj.name;
			batch.form.driverBill = obj.bill;
			batch.form.vehicleId = obj.vehicleId;
			batch.form.vehicleTypeName = obj.vehicleTypeName;
			$scope.form.vehicleType= obj.vehicleType+"";
			batch.form.weight= obj.actualWeight;
			batch.form.voluem= obj.actualVolume;
			batch.form.plateNumber = obj.plateNumber;
			batch.showToFalse();
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
			batch.remove(data);
			$scope.list.push(data);
		
			batch.addClear(data);
		},
		remove : function(data){
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
		
		
		/**快找**/
		checkData : function(obj){
			if(obj == 1 || obj == "1"){
				//没有值时回复数据
				if($scope.selectData.trackingNums != undefined && $scope.selectData.trackingNums != null && $scope.selectData.trackingNums !=""){
					return false;
				}
			}
			$scope.order.data.items = $scope.orderInfo;
			var itemValue=new Array();
			var r= /^[1-9]?[0-9]*\.[0-9]*$/;
			var c = 1;
			for(var j = 0;j < $scope.orderInfo.length;j++){
				   if(($scope.orderInfo[j].trackingNum+"").indexOf($scope.selectData.trackingNums) >= 0 ){
					  var isFind = false;
					  if($scope.orderInfoList!=null && $scope.orderInfoList!=undefined && $scope.orderInfoList!=""){
						for(var g = 0; g < $scope.orderInfoList.length; g++){
							if($scope.orderInfo[j].orderId == $scope.orderInfoList[g].orderId){
								isFind = true;
								break;
							}
						}
					  }
						if (!isFind)
							itemValue.push($scope.orderInfo[j]);
					}
				}
				$scope.order.data.items = itemValue;
				
				if($scope.order.data.items.length > $scope.list.length){
					$scope.chechHight($scope.order.data.items);
				}else{
					$scope.chechHight($scope.list);
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
		isCheckData:function(obj){
			if(obj == 1 || obj == "1"){
				//没有值时回复数据（离开回复）
				if($scope.selectData.geTrackingNums != undefined && $scope.selectData.geTrackingNums != null && $scope.selectData.geTrackingNums !=""){
					return false;
				}
			}
			var itemValue=new Array();
			var r= /^[1-9]?[0-9]*\.[0-9]*$/;
	//		console.log($scope.orderInfoList);
			if($scope.orderInfoList != undefined && $scope.orderInfoList != null && $scope.orderInfoList != ""){
				for(var j=0;j<$scope.orderInfoList.length;j++){
					   if((($scope.orderInfoList[j].trackingNum+"").indexOf($scope.selectData.geTrackingNums)) >= 0 ){
							itemValue.push($scope.orderInfoList[j]);
						}
				}
			}
			$scope.list = itemValue;
//			console.log($scope.list);
			if($scope.order.data.items.length > $scope.list.length){
				$scope.chechHight($scope.order.data.items);
			}else{
				$scope.chechHight($scope.list);
			}
			
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
		/**全选*/
		selectAll:function(data){
			if($scope.list==null||$scope.list==undefined||$scope.list.length==0){
				$scope.list=new Array();
			}
			for(var i=0;i<data.items.length;i++){
				$scope.list.push(data.items[i]);
			}
			$scope.order.data.items=new Array();
			$scope.orderInfoList= batch.deepCopy($scope.list);
			$scope.orderInfoList.length = $scope.list.length;
			$scope.order.total == 0;
		},
		/**全选清除*/
		selectClear:function(){
			for(var i=0;i<$scope.list.length;i++){
				batch.add($scope.list[i]);
			}
			
			for(var j=0;j<$scope.list.length;j++){
				batch.removeClear($scope.list[j]);
			}
			$scope.list=new Array();
			if($scope.page !=undefined && $scope.page.total != undefined &&$scope.page.total == 0){
				$scope.order.total = $scope.order.data.items.length;
			}
			if($scope.page !=undefined && $scope.page.isLoad==false){
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
			batch.removeClear(data);
			batch.add(data);
			if($scope.order.total == 0){
				$scope.order.total = $scope.order.data.items.length;
			}
			if($scope.order.isLoad=false){
				$scope.order.isLoad=true;
			}
		},
		showTotrue:function(){
			if($scope.showPcum==false){
				batch.doQueryVehile();
			}
			$scope.showPcum = true;
		},
		/**返厂列表查询*/
		doQuery:function(){
			if($scope.isTrue){
				return false;
			}
			$scope.isTrue = true;
			$scope.query.receiptType = 3;
			$scope.query.receiptSatate = 13; //未返厂
			$scope.query.consignorName = $scope.query.consignorName;
			var num =[8,9,13];//定义一个数组    
			$scope.query.repeictNumber = num;
			var url = "orderInfoBO.ajax?cmd=doReceiptQuery";
			$timeout(function(){
				$scope.order.load({
					url:url,
					params:$scope.query,
					callBack:"$scope.callBack"
				 });
			},200);
			$scope.callBack = function(data){
//						console.log(data);
				$scope.orderInfo = batch.deepCopy(data.items);
				$scope.orderInfo.length = data.items.length;
				$scope.allCount = data.items.length;
				for(var i = 0;i < $scope.list.length;i++){
					$scope.remove($scope.list[i]);	
				}
				
				if($scope.list != null && $scope.list != undefined && $scope.list.length>0){
//							console.log($scope.list);
					$scope.orderInfoDataList = batch.deepCopy(data.items);
					$scope.orderInfoDataList.length = data.items.length;
					var arrays = new Array();
					for(var y=0;y < $scope.orderInfoDataList.length;y++){
						arrays.push($scope.orderInfoDataList[y]);
					}
					for(var j=0;j< $scope.list.length;j++){
						arrays.push($scope.list[j]);
					}
					$scope.orderInfo = batch.deepCopy(arrays);
					$scope.orderInfo.length = arrays.length;
				}
				$timeout(function(){
					setContentHegthDelay(20);
				},500);
			};
			
			//如果后台报错（自动还原）
			$timeout(function(){
				$scope.isTrue = false;
			},8000);

		},
		doSave:function(){
			if($scope.list.length == 0){
				commonService.alert("请选择未返厂信息到返厂列表!");
				return false ;
			}
			var data = $scope.list;
			var pay4 = 0; //回单付笔数
			
			var pay5 = 0; //其他付笔数 :
			var payMoney = 0;
			var tip = "";
			var sureText="";
			var cancer="";
			var param = {};
			var orderIds = "";
			var isFlag = true;
			param.receiptStat = 9;
			param.isPay = 2; //未付
			for(var i=0;i<data.length;i++){
				if(data[i].paymentType == 4){
					pay4 = pay4 +1;
					payMoney = payMoney + data[i].receiptPayment;
				}else if(data[i].paymentType == 5){
					if(data[i].receiptPayment > 0){
						pay4 = pay4 +1;
						payMoney = payMoney + data[i].receiptPayment;
					}else{
						pay5 = pay5 + 1;
					}
				}else{
					pay5 = pay5 + 1;
				}
				if(i != data.length -1){
					orderIds = orderIds + data[i].orderId+",";
				}else{
					orderIds = orderIds + data[i].orderId;
				}
				
			}
			param.orderIds = orderIds; //未付
			if(payMoney > 0){
				tips="回单共"+pay4+"笔,需收取人民币"+payMoney/100+"元";
				if(pay5 > 0){
				   tips= tips +	",其他"+pay5+"笔。";
				}
				sureText="确认已收取";
				cancer="暂不收取";
			}else{
				tips = "总共返厂"+pay5+"笔，确认返厂。";
				isFlag = false;
			}
			var url = "orderInfoBO.ajax?cmd=receiptPalautus";
			commonService.confirm(tips,function(){
				param.isPay = 1;//已收
				commonService.postUrl(url,param,function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
						    commonService.alert("批量返厂成功!");
							$scope.list=new Array();
							$scope.orderInfoList = []; //清除过滤 已选缓存
							batch.doQuery();
		 	    	}
				});
			},function(){
				if(isFlag){
					//成功执行
					commonService.postUrl(url,param,function(data){
						if(data!=null && data!=undefined && data!=""){
							    commonService.alert("批量返厂成功!");
								$scope.list=new Array();
								$scope.orderInfoList = []; //清除过滤 已选缓存
								batch.doQuery();
			 	    	}
					});
				}

			},sureText,cancer);
			
		},
		close:function(){
    		commonService.closeToParentTab(true);
    	},

	};
	
	batch.init();
}]);
