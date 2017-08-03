var addDepartOtherApp = angular.module("addDepartOtherApp", ['commonApp']);
addDepartOtherApp.controller("addDepartOtherCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	$scope.vehicleTypeList=[];
	$scope.printTime = new Date();
	var addDepart={
		init:function(){
			setContentHegthDelay();
			this.userData();
			var batchNum=getQueryString("batchNum");
			this.bindEvent();
			$scope.list=new Array();
			$scope.orderInfo=new Array();
			this.registerKeyEvent(); //键盘绑定事件
			this.getEndOrg();
			
			$scope.initObject();
			//发货人弹窗
			$scope.showPcum = false;
			$scope.nextShow = true;
			$scope.showCarr = false;
			$scope.tabShow = true;
			$scope.carShow=false;
			$scope.nextButton = true;
			$scope.editShow = false;//修改联系人
			
			$scope.isComit = true;
			//车辆信息默认不显示
			$scope.showPcum = false;
			$scope.gatherValue = 0;
			this.toView();
		},
		bindEvent:function(){
			$scope.param=this.param;
			$scope.doQuery=this.doQuery;
		    $scope.form=this.form;
		    $scope.clear=this.clear;
		    $scope.select=this.select;
		    $scope.selectAll=this.selectAll;
		    $scope.showToFalse=this.showToFalse;
		    $scope.selectVehicle=this.selectVehicle;
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
		    //新增查询条件
		    $scope.query=this.query;
		    $scope.next=this.next;
		    $scope.clearQuery=this.clearQuery;
		    $scope.clearSelect=this.clearSelect;
		    //承运商弹窗
		    $scope.onCloseCarr = this.onCloseCarr;
		    $scope.openCarr = this.openCarr;
		    $scope.queryZx = this.queryZx;
		    $scope.setTab = this.setTab;
		    $scope.queryCarr = this.queryCarr;
		    $scope.selectCarr = this.selectCarr;
		    $scope.selectOne = this.selectOne;
		    $scope.saveTransfer = this.saveTransfer;
		    //新增车辆
		    $scope.addCar = this.addCar;
		    $scope.onCloseCarTab = this.onCloseCarTab;
		    $scope.initCarData = this.initCarData;
		    $scope.getDriverName = this.getDriverName;
		    //修改发货人
		    $scope.editTab = this.editTab;
		    $scope.closeEditTab = this.closeEditTab;
		    $scope.comitEdit = this.comitEdit;
		    
		    //车辆管理－
		    $scope.doAddViechInfo = this.doAddViechInfo;//新增车辆信息
		    $scope.doAddTransfer = this.doAddTransfer; //新增承运商
		    $scope.showTotrue = this.showTotrue;//新增车辆信息
		    $scope.sumOfTransfer = this.sumOfTransfer;
		    $scope.initObject = this.initObject;
		    $scope.transferInfo = this.transferInfo;
		    $scope.selectCheck=this.selectCheck;
		    
		    //导出
		    $scope.exportOrd = this.exportOrd;
		    //打印
		    $scope.printTable = this.printTable;
		},
		selectCheck:function(id){
			if(document.getElementById(id).checked==true){
				document.getElementById(id).checked=false;
			}else{
				document.getElementById(id).checked=true;
			}
		},
		transferInfo:{
			"name":"",
			"csPhones":"",
			"linkPhone":"",
			"address":"",
			"orgId":""
		},
		initObject:function(){
			//初始化数据
			$scope.ordTransferCost = {
					"stevedoringPayState":2,
					"payState":2,
					//到付
					"freightCollect":0,
					//应付
					"collectingMoney":0,
					//中转费
					"transferValue":0,
					//票数
					"transferNum":0,
					"actualWeight":0,
					"actualVolume":0,
			};
			$scope.transferInfo = {
				"name":"",
				"csPhones":"",
				"linkPhone":"",
				"departmentAddress":"",
				"orgId":""
			};
			addDepart.form.driverName = "";
			addDepart.form.driverBill = "";
			addDepart.form.vehicleId = "";
			addDepart.form.vehicleTypeName = "";
			$scope.form.vehicleType= -1+"";
			addDepart.form.weight= "";
			addDepart.form.volume= "";
			addDepart.form.businessType="-1";
			addDepart.form.plateNumber = "";
			document.getElementById("isGet").checked=false;
			document.getElementById("isPay").checked=false;
			document.getElementById("freight").checked=false;
			document.getElementById("stevedoring").checked=false;
			
			
		},
		//初始化 到付，代收货款，中转费用
		initFeeInfo:function(){
			var view = getQueryString("view");
			if( view==1 || view==2){
    			$scope.freightCollect=$scope.ordTransferCost.freightCollect;
    			$scope.collectingMoney=$scope.ordTransferCost.collectingMoney;
    			console.log($scope.ordTransferCost.transferValue);
    			$scope.transferMoney=$scope.ordTransferCost.transferValue;
    			
    			var value = 0;
        		if($scope.freightCollect != null){
        			value = value + Number($scope.freightCollect);
        		}
        		if($scope.collectingMoney != null){
        			value = value + Number($scope.collectingMoney);
        		}
        		value = value - $scope.transferMoney;
        		if(value >=0){
        			$scope.gatherShow = true;
        		}else{
        			$scope.gatherShow = false;
        		}
        		$scope.gatherValueNumber = value.toFixed(2);
        		$scope.gatherValue = Math.abs(value.toFixed(2));
    		}
		},
		
		editTab:function(){
			if($scope.diy.getSelectData().length==0){
				commonService.alert("请选择需要修改的运单信息");
				return false;
			}
			$scope.editShow = true;
			$scope.showCarr = true;
		},
		comitEdit:function(){
			var list = $scope.diy.getSelectData();
			if(list.length>0){
				for(var i=0;i<list.length;i++){
					if($scope.linkTel != null && $scope.linkTel != ""){
						if(!validatemobile($scope.linkTel)){
							commonService.alert("手机号码不正确");
							return false;
						}
						list[i].consignorBill = $scope.linkTel;
					}
					if($scope.linkName != null && $scope.linkName != "" ){
						list[i].consignorName = $scope.linkName;
					}
				}
				if($scope.linkTel != null && $scope.linkTel != "" && $scope.linkName != null && $scope.linkName != "" ){
					commonService.alert("修改完成");
				}else{
					commonService.alert("没有修改数据");
				}
				$scope.closeEditTab();
			}else{
				commonService.alert("请选择需要修改的运单信息");
				return false;
			}
		},
		//关闭窗口
		closeEditTab:function(){
			$scope.editShow = false;
			$scope.showCarr = false;
			$scope.linkName = "";
			$scope.linkTel = "";
		},
		//隐藏div
		showToFalse:function(){
			$timeout(function(){
				$scope.showPcum = false;
				$scope.showRcum = false;
			},200);
		},
		/**车辆列表查询*/
		doQueryVehile:function(plateNumber){
				addDepart.params.plateNumber=plateNumber;
				addDepart.params.descOrgId=addDepart.form.descOrgId;
				addDepart.params.orgId=$scope.currOrgId;
				var url = "vehicleInfoBO.ajax?cmd=doQuery";
				$timeout(function(){
					$scope.page.load({
								url:url,
								params:addDepart.params
							});
				},500);
				$scope.showPcum = true;
		},
		doAddViechInfo:function(){
			commonService.openTab("10001212","新增车辆信息","/cm/cmVehicleInfoAdd.html");
		},
		doAddTransfer:function(){
			commonService.openTab("10201212","新增承运商","/cm/addCmCarrierCompany.html?flag=2");
		},
		showTotrue:function(){
			if($scope.showPcum==false){
				addDepart.doQueryVehile();
			}
			$scope.showPcum = true;
		},
		onCloseCarTab:function(){
			$scope.carShow = false;
			$scope.showCarr = false;
		},
		addCar:function(){
			$scope.carShow = true;
			$scope.showCarr = true;
			$scope.initCarData();
		},
		/**检索主驾驶员名称**/
		getDriverName:function(type,value){
			var param="";
			if(type==1){
				param=$("#ex2_value").val();
			}else if(type==2){
				param=$("#ex1_value").val();
			}
			if(param==null || param==undefined || param==""){
				$scope.isDisabled=false;
			}
			var url = "vehicleInfoBO.ajax?cmd=doQueryVehicleDriver";
			$scope.form.name=param;
			commonService.postUrl(url,$scope.form,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					var aa=new Array();
					for(var i=0;i<data.content.length;i++){
						aa.push(data.content[i]);
					}
					$scope.countries =aa;
				}
			});
		},
		//车辆管理
		initCarData:function(){
			//获取计费方式
			commonService.postUrl("staticDataBO.ajax?cmd=selectExceptionType","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.exceptionTypeData = data;
				}
			});
			/**创建网点查询*/
			var url = "staticDataBO.ajax?cmd=selectOrgByRole";
			commonService.postUrl(url,"",function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.orgInfo=data;
					$scope.data.orgId = data[0].orgId;
	 	    	}
			});
		
		},
		//保存逻辑－选择承运商－是否有修改运单中的 中转费、发货联系人、电话－车辆信息－装卸费（可选）－费用是否已付
		saveTransfer:function(){
			if(!$scope.isComit){
				commonService.alert("提交中...请稍等");
				return false;
			}
			if($scope.list == null || $scope.list.length <=0){
				commonService.alert("至少选择一条运单信息");
				return false;
			}
			var orderList = new Array();
			for(var i=0;i<$scope.list.length;i++){
				var obj = {};
				obj.trackingNum = $scope.list[i].trackingNum;
				obj.transferValue = $scope.list[i].transferValue;
				obj.deliveryTypeName = $scope.list[i].deliveryTypeName;
				obj.deliveryType = $scope.list[i].deliveryType;
				obj.consigneeLinkmanName = $scope.list[i].consigneeLinkmanName;
				obj.consigneeTelephone = $scope.list[i].consigneeTelephone;
				obj.consignorName = $scope.list[i].consignorName;
				obj.consignorBill = $scope.list[i].consignorBill;
				obj.orderId = $scope.list[i].orderId;
				obj.outgoingTrackingNum = $scope.list[i].outgoingTrackingNum;
				orderList.push(obj);
			}
			if($scope.transferInfo.orgId == null || $scope.transferInfo.orgId == undefined || $scope.transferInfo.orgId == ''){
				commonService.alert("请选择承运商!");
				return false;
			}
			if($scope.ordTransferCost.stevedoring != null && $scope.ordTransferCost.stevedoring != undefined && $scope.ordTransferCost.stevedoring > 0){
				if($scope.ordTransferCost.stevedoringPayDot == null  || $scope.ordTransferCost.stevedoringPayDot == undefined || $scope.ordTransferCost.stevedoringPayDot == -1 || $scope.ordTransferCost.stevedoringPayDot == ''){
					commonService.alert("请选择装卸费付款方");
					return false;
				}
			}
			if($scope.ordTransferCost.freight != null && $scope.ordTransferCost.freight != undefined && $scope.ordTransferCost.freight > 0){
				if($scope.ordTransferCost.freightPayDot == null || $scope.ordTransferCost.freightPayDot == undefined || $scope.ordTransferCost.freightPayDot == -1 || $scope.ordTransferCost.freightPayDot == '' ){
					commonService.alert("请选择运费付款方");
					return false;
				}
			}
			var param ={};
			param.batchNum = $scope.batchNum;
			param.orgId = $scope.transferInfo.orgId;
			param.orderList = orderList;
			param.vehicleInfo = addDepart.form;
			param.vehicleInfo.id = addDepart.form.vehicleId;
			param.vehicleInfo.name = addDepart.form.driverName;
			param.vehicleInfo.bill = addDepart.form.driverBill;
			param.ordTransferCost = $scope.ordTransferCost;
			//装卸费是否支付 
			if(document.getElementById("stevedoring").checked){
				param.ordTransferCost.stevedoringPayState = 1;
			}else{
				param.ordTransferCost.stevedoringPayState = 2;
			}
			//运费
			if(document.getElementById("freight").checked){
				param.ordTransferCost.payState = 1;
			}else{
				param.ordTransferCost.payState = 2;
			}
			param.ordTransferCost.transferNum = $scope.list.length;
			param.ordTransferCost.freightCollect = $scope.freightCollect;
			param.ordTransferCost.collectingMoney = $scope.collectingMoney;
			param.ordTransferCost.transferValue = $scope.transferMoney;//中转费
			param.ordTransferCost.actualWeight = $scope.actualWeight;
			param.ordTransferCost.actualVolume = $scope.actualVolume;
			param.ordTransferCost.gatherValue = $scope.gatherValueNumber;
			if(document.getElementById("isGet").checked){
				//未收
				param.ordTransferCost.isGet = 0;
			}else{
				//已收
				param.ordTransferCost.isGet = 1;
			}
			if(document.getElementById("isPay").checked){
				//未付
				param.ordTransferCost.isPay = 0;
			}else{
				//已付
				param.ordTransferCost.isPay = 1;
			}
			$scope.isComit = false;
			//应收－应付
			param.num = $scope.list.length;
			var url = "transferBO.ajax?cmd=doSave";
			commonService.postUrl(url,param,function(data){
				if(data != null && data != undefined && data != ""){
					commonService.alert("保存成功");
					$scope.selectClear();
					$scope.initObject();
					$scope.diy.totalValue.transferValue = 0;
					$scope.gatherValue = 0;
					$scope.isComit = true;
					$scope.close();
				}
			},function(data){
				commonService.alert(data.message);
				$scope.isComit = true;
			});
		},
		//单选
		selectOne : function(orderId,i){
			if(i==1){
				//专线
				if(document.getElementById("checkbox_1_"+orderId).checked && document.getElementById("checkbox_1_"+orderId) != undefined){
					document.getElementById("checkbox_1_"+orderId).checked=false;
				}else{
					document.getElementById("checkbox_1_"+orderId).checked=true;
				}
			}else{
				//合作商
				if(document.getElementById("checkbox_2_"+orderId).checked && document.getElementById("checkbox_2_"+orderId) != undefined){
					document.getElementById("checkbox_2_"+orderId).checked=false;
				}else{
					document.getElementById("checkbox_2_"+orderId).checked=true;
				}
				
			}
		},
		//选择外发用户
		selectCarr:function(){
			$scope.twoOrg = [{"orgId":userInfo.orgId+"","orgName":userInfo.orgName}];
			if(!$scope.tabShow){
				//选择的是专线
				if($scope.zx.getSelectData().length == 0 ){
					commonService.alert("请选择一条专线信息!");
					return false;
				}
				$scope.transferInfo = $scope.zx.getSelectData()[0];
			}else{
				//选择的是合作商
				if($scope.hz.getSelectData().length == 0){
					commonService.alert("请选择一条承运商信息!");
					return false;
				}
				var data=$scope.hz.getSelectData()[0];
				$scope.transferInfo.name = data.orgName;
				$scope.transferInfo.csPhones = data.carryLinkPhone;
				$scope.transferInfo.linkPhone = data.orgPrincipalPhone;
				$scope.transferInfo.departmentAddress = data.departmentAddress;
				$scope.transferInfo.orgId = data.orgId;
			}
			$scope.twoOrg.push({"orgId":$scope.transferInfo.orgId+"","orgName":$scope.transferInfo.name});
			$scope.onCloseCarr();
		},
		//专线查询
		queryZx:function(){
			if($scope.zx.getFilterData() == null || $scope.zx.getFilterData() == undefined){
				var url = "organizationBO.ajax?cmd=queryOrgListByPage";
				commonService.postUrl(url,"_ALLEXPORT=1&myself=0",function(data){
					if(data != null && data != undefined && data != ""){
						$scope.zx.loadData(data.items);
					}
				});
			}
		},
		//合作商查询
		queryCarr:function(){
			if($scope.hz.getFilterData() == null || $scope.hz.getFilterData() == undefined){
				var url = "organizationBO.ajax?cmd=getCarri";
				commonService.postUrl(url,"",function(data){
					if(data != null && data != undefined && data != ""){
						$scope.hz.loadData(data.items);
					}
				});
			}
		},
		//切换tab页
		setTab:function(i){
			$scope.transferInfo = {};
			if(i==1){
				$scope.zx.clearAllCheckbox();
				$scope.tabShow = false;
				$scope.queryZx();
			}else{
				$scope.hz.clearAllCheckbox();
				$scope.tabShow = true;
				$scope.ordTransferCost.outgoingTrackingNum = "";
				$scope.ordTransferCost.transportContract = "";
				$scope.queryCarr();
			}
		},
		//关闭弹窗
		onCloseCarr:function(){
			$scope.showCarr = false;
//			$('#alertMsgIsShow', parent.document).css({"display":"none"});
			$scope.carShow = false;
			$('body', parent.document).css({"overflow":"inherit"});
			
		},
		
		//打开弹窗
		openCarr:function(){
			$scope.transferInfo.orgId = null;
			//下拉框重新选择－设为－1
			$scope.twoOrg = [{"orgId":userInfo.orgId+"","orgName":userInfo.orgName}];
			$scope.ordTransferCost.freightPayDot = null;
			$scope.ordTransferCost.stevedoringPayDot = null;
			$scope.carShow = true;
			$scope.showCarr = true;
//			$('#alertMsgIsShow', parent.document).css({"display":"block","z-index":"1000"});
//			$('#cys_1').css({"display":"block","z-index":"10001"});
			
			$scope.setTab(2);
			$('body', parent.document).css({"overflow":"hidden"});
			
			
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
			commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType",{"codeType":"VEHICLE_TYPE"},function(data){
				$scope.vehicleTypeList=data.items;
			});
			commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType",{"codeType":"BUSINESS_TYPE"},function(data){
				$scope.businessTypeList=data.items;
			});
			commonService.postUrl("organizationBO.ajax?cmd=getOrgInfo","orgId="+userInfo.orgId,function(data){
				$scope.userInfoOrg = data.organization;
			});
			$scope.twoOrg = [{"orgId":userInfo.orgId+"","orgName":userInfo.orgName}];
		},
		getEndOrg:function(){
//			commonService.postUrl("routeBO.ajax?cmd=queryRoateTowards","orgType=0",function(data){
//				//成功执行
//				if(data!=null && data!=undefined && data!=""){
//					$scope.desOrgData=data.items;
//					$scope.desOrgData.unshift({endOrgId:-1,endOrgName:'所有'});
//				}
//			});
		},
		next:function(i){
			if(i == 1){
				if($scope.list.length<=0){
					commonService.alert("至少选择一条中转信息");
				}else{
					$scope.diy.loadData($scope.list);
					$scope.nextShow = false;
					setContentHegthDelay();
				}
			}else{
				$scope.nextShow = true;
				setContentHegthDelay();
			}
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
			if(obj != null){
				addDepart.form.driverName = obj.name;
				addDepart.form.driverBill = obj.bill;
				addDepart.form.vehicleId = obj.vehicleId;
				addDepart.form.vehicleTypeName = obj.vehicleTypeName;
				$scope.form.vehicleType= obj.vehicleType+"";
				addDepart.form.weight= obj.actualWeight;
				addDepart.form.volume= obj.actualVolume;
				addDepart.form.businessType=obj.businessType;
				addDepart.form.plateNumber = obj.plateNumber;
			}
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
			for(var i = 0; i<$scope.list.length ;i++ ){
				if($scope.list[i].trackingNum == data.trackingNum){
					commonService.alert("添加的运单号["+data.trackingNum+"]重复");
					return;
				}
			}
			
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
				if(data.trackingNum==value[x].trackingNum){
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
			$scope.freightCollect = 0;//到付
			$scope.collectingMoney = 0;//代收货款
			$scope.actualVolume = 0;//体积
			$scope.actualWeight = 0;//重量
			$scope.transferMoney = 0;//中转费
			$scope.totalCount = 0;//件数
			$scope.totalAllFee = 0;//费用合计
			$scope.totalAllDiscount = 0; //回扣
		
			for(var i=0;i<$scope.list.length;i++){
				if($scope.list[i].orderState!=5){
					$scope.actualVolume = $scope.actualVolume+$scope.list[i].volume;
					$scope.actualWeight = $scope.actualWeight+$scope.list[i].weight;
					
					//如果订单是有配安任务的，代收，到付的钱不是给到承运商
					//11 是服务方式为“无”的数据
					
					//if($scope.list[i].taskId==null){
						$scope.freightCollect = $scope.freightCollect+($scope.list[i].freightCollect/100);
						$scope.collectingMoney = $scope.collectingMoney+($scope.list[i].collectingMoney/100);
					//}
					if($scope.list[i].transferValue != null && $scope.list[i].transferValue!=undefined){
						$scope.transferMoney = $scope.transferMoney+Number($scope.list[i].transferValue);
					}
					$scope.totalCount = $scope.totalCount + $scope.list[i].count;
					if($scope.list[i].totalFeeString != null && $scope.list[i].totalFeeString != undefined){
						$scope.totalAllFee = $scope.totalAllFee + Number($scope.list[i].totalFeeString)/100;
					}
					if($scope.list[i].discountString != null && $scope.list[i].discountString != undefined){
						$scope.totalAllDiscount = $scope.totalAllDiscount + Number($scope.list[i].discountString)/100;
					}
			   }
			}
			if($scope.totalAllFee != 0){
				$scope.totalAllFee = $scope.totalAllFee.toFixed(2);
			}
			if($scope.totalAllDiscount !=0 ){
				$scope.totalAllDiscount = $scope.totalAllDiscount.toFixed(2);
			}
			if($scope.actualVolume == 0){
			}else{
				$scope.actualVolume = $scope.actualVolume.toFixed(2);
			}
			if($scope.actualWeight == 0){
			}else{
				$scope.actualWeight = $scope.actualWeight.toFixed(0);
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
				if($scope.freightCollectWait != 0){
					$scope.freightCollectWait = $scope.freightCollectWait.toFixed(2);
				}
				if($scope.collectingMoneyWait != 0){
					$scope.collectingMoneyWait = $scope.collectingMoneyWait.toFixed(2);
				}
				if($scope.actualVolumeWait != 0){
					$scope.actualVolumeWait = $scope.actualVolumeWait.toFixed(2);
				}
				if($scope.actualWeightWait != 0){
					$scope.actualWeightWait = $scope.actualWeightWait.toFixed(1);
				}
			}
			var value = 0;
    		if($scope.freightCollect != null){
    			value = value + Number($scope.freightCollect);
    		}
    		if($scope.collectingMoney != null){
    			value = value + Number($scope.collectingMoney);
    		}
    		value = value - $scope.transferMoney;
    		if(value >=0){
    			$scope.gatherShow = true;
    		}else{
    			$scope.gatherShow = false;
    		}
    		$scope.gatherValueNumber = value.toFixed(2);
    		$scope.gatherValue = Math.abs(value.toFixed(2));
    		//查看页面的需要设置一下
    		
		},
		/**全选*/
		selectAll:function(id,isAll){
			var data = $scope.matchListTable.getFilterData();
			if(id=="all"){
				if(data != null && data != undefined){
					if($scope.list==null||$scope.list==undefined||$scope.list.length==0){
						$scope.list=new Array();
					}
					for(var i=0;i<data.length;i++){
						$scope.list.push(data[i]);
					}
					$scope.order.data.items=new Array();
					addDepart.calculate();
					$scope.orderInfoList= addDepart.deepCopy($scope.list);
					$scope.orderInfoList.length=$scope.list.length;
					$scope.order.total==0;
					$scope.matchListTable.loadData($scope.order.data.items);
					$scope.selectMatchListTable.loadData($scope.list);
				}
			}else{
				if(data != null && data != undefined){
					for(var i=0;i<data.length;i++){
						if(data[i].trackingNum == id){
							$scope.select(data[i]);
						}
					}
				}
				$scope.matchListTable.loadData($scope.order.data.items);
				$scope.selectMatchListTable.loadData($scope.list);
			}
		},
		/**全选清除*/
		selectClear:function(id,isAll){
			var data = $scope.selectMatchListTable.getFilterData();
			if(id=="all"){
				if(data != null && data != undefined){
					for(var i=0;i<$scope.list.length;i++){
						addDepart.add($scope.list[i]);
					}
					
					for(var j=0;j<$scope.list.length;j++){
						addDepart.removeClear($scope.list[j]);
					}
					$scope.list=new Array();
					$scope.diy.loadData($scope.list);
					addDepart.calculate();
					if($scope.diy.total==0){
						$scope.order.total=$scope.order.data.items.length;
					}
					if($scope.diy.isLoad=false){
						$scope.order.isLoad=true;
					}
					$scope.matchListTable.loadData($scope.order.data.items);
					$scope.selectMatchListTable.loadData($scope.list);
				}
			}else{
				if(data != null && data != undefined){
					for(var i=0;i<data.length;i++){
						if(data[i].trackingNum == id){
							$scope.clear(data[i]);
						}
					}
				}
				$scope.matchListTable.loadData($scope.order.data.items);
				$scope.selectMatchListTable.loadData($scope.list);
			}
			
		},
		/**清楚选中的*/
		clearSelect:function(){
			var data = $scope.diy.getSelectData();
			for(var i=0;i<data.length;i++){
				$scope.clear(data[i]);
			}
			$scope.diy.clearAllCheckbox();
			addDepart.updateTransferValue();
		},
		updateTransferValue:function(){
			$scope.diy.setTransferValue("transferValue");
		},
		/**单选清除*/
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
		/**查询清除**/
		clearQuery:function(data){
			$scope.query.beginTime = "";
			$scope.query.endTime = "";
			$scope.query.descOrgId = -1;
		},
		/**订单列表查询*/
		doQuery : function(){
				addDepart.query._ALLEXPORT=1;
				addDepart.query.page=1;
				addDepart.query.type = 2;
//				if($scope.order !=null && $scope.order !=undefined && $scope.order!=""){
//					$scope.order.total = 0;
//					$scope.list.length = 0;
//				}
				
				$scope.query.beginDate = $("#beginDate").val();
				$scope.query.endDate = $("#endDate").val();
				
				$scope.query.beginDateArrive = $("#beginDateArrive").val();
				$scope.query.endDateArrive = $("#endDateArrive").val();
				
				$scope.query.proviceId=$scope.des.chooseCityId;//省份
				$scope.query.cityId=$scope.des.chooseRegionId;//市
				$scope.query.countyId=$scope.des.chooseCountyId;//区域
				var url = "transferBO.ajax?cmd=queryOther";
				commonService.postUrl(url,addDepart.query,function(data){
					$scope.order = {};
					$scope.order.data = data;
					$scope.orderInfo= addDepart.deepCopy(data.items);
					$scope.orderInfo.length=data.items.length;
					
					for(var i=0;i<$scope.list.length;i++){
//						$scope.orderInfo[i].transferValue = 0;
						$scope.remove($scope.list[i]);	
					}
					$scope.matchListTable.loadData($scope.order.data.items);
					
					addDepart.calculate(); //计算待发货统计
					setContentHegthDelay();
						$scope.selectData.trackingNums="";
						$scope.selectData.gtWeight="";
						$scope.selectData.gtVolume="";
						$scope.selectData.gtCount="";
						$scope.selectData.geTrackingNums="";
						$scope.selectData.geWeight="";
						$scope.selectData.geVolume="";
						$scope.selectData.geCount="";
				});
		},
		printTable:function(){
			printTableInfo("printDiv", "联运会_中转详情");
		},
		exportOrd:function(){
			if($scope.isTrue){
				return false;
			}
			var batchNum = $scope.batchNum;
			var url = "transferBO.ajax?cmd=getByBacthInfoExport";
			var toUrl = signUrl(url+"&batchNum="+batchNum);
			var iframe = document.createElement("iframe");
		    iframe.id = "frameDownloading";
		    iframe.src = toUrl;
		    iframe.style.display = "none";
		    document.body.appendChild(iframe);
		    
		    var selectExport = document.getElementById("exportId");
			$scope.isTrue = true;
			selectExport.innerHTML = "导出中。";
			//导出倒计时
			$timeout(function() {
				$scope.isTrue = false;
			    selectExport.innerHTML = "导出";
			},3000);
		},
		close:function(){
    		commonService.closeToParentTab(true);
    	},
    	toView:function(){
    		var that = this;
    		var batchNum=getQueryString("batchNum");
    		$scope.batchNum = batchNum;
    		var view = getQueryString("view");
    		if(batchNum!=null&&batchNum!=undefined&& batchNum!=''){
    			//直接显示详情第二页
    			$scope.nextShow = false;
    			var queryString={
    					"batchNum":batchNum
    			}
    			var url="transferBO.ajax?cmd=getByBacthInfoOther";
    			commonService.postUrl(url,queryString,function(data){
    				setContentHegthDelay();
    				if(view == 1){
    					//查看
    					$scope.view = true;
    				}else{
    					//修改
    					$scope.view = false;
    					$scope.nextButton = false;
    				}
    				if(data.organization != null && data.organization != undefined && data.organization !=""){
    					var obj = data.organization;
    					$scope.transferInfo.orgName = obj.orgName;
    					$scope.transferInfo.name = obj.orgName;
    					$scope.transferInfo.linkMan = obj.orgPrincipal;
    					$scope.transferInfo.provinceName = obj.provinceName;
    					$scope.transferInfo.regionName = obj.regionName;
    					$scope.transferInfo.countyName = obj.countyName;
    					$scope.transferInfo.orgId = obj.orgId;
						if(obj.orgType==5){
							//--- 测试
							$scope.tabShow = true;
						}else{
							$scope.tabShow = false;
						}
						$scope.twoOrg = [{"orgId":userInfo.orgId+"","orgName":userInfo.orgName}];
						$scope.twoOrg.push({"orgId":$scope.transferInfo.orgId+"","orgName":$scope.transferInfo.orgName});
    				}
    				if(data.ordTransferCost != null && data.ordTransferCost != undefined && data.ordTransferCost != ""){
    					$scope.ordTransferCost = data.ordTransferCost;
    					addDepart.initFeeInfo();
    					if(data.ordTransferCost.isGet == 0){
    						document.getElementById("isGet").checked=true;
    					}else{
    						document.getElementById("isGet").checked=false;
    					}
    					if(data.ordTransferCost.isPay == 0){
    						document.getElementById("isPay").checked=true;
    					}else{
    						document.getElementById("isPay").checked=false;
    					}
    					if(data.ordTransferCost.payState == 1){
    						document.getElementById("freight").checked=true;
    					}else{
    						document.getElementById("freight").checked=false;
    					}
    					if(data.ordTransferCost.stevedoringPayState == 1){
    						document.getElementById("stevedoring").checked=true;
    					}else{
    						document.getElementById("stevedoring").checked=false;
    					}
    					if(data.ordTransferCost.stevedoringPayDot != null){
    						$scope.ordTransferCost.stevedoringPayDot = data.ordTransferCost.stevedoringPayDot+"" ;
    					}
    					if(data.ordTransferCost.freightPayDot != null){
    						$scope.ordTransferCost.freightPayDot = data.ordTransferCost.freightPayDot+"";
    					}
    					$scope.transferInfo.linkPhone = data.ordTransferCost.linkPhone;
    					$scope.transferInfo.departmentAddress = data.ordTransferCost.departmentAddress;
    					//提货电话
    					$scope.transferInfo.csPhones = data.ordTransferCost.csPhones;
    				}
    				$scope.selectVehicle(data.vehicleInfo);
    				if(data.orderInfoList != null && data.orderInfoList != undefined && data.orderInfoList != ""){
    					$scope.orderInfoList = new Array();
    					$scope.order = {};
    					$scope.order.data = {
    							"items": new Array(),
    					};
    					var list = data.orderInfoList;
    					$scope.list = list;
    					$scope.diy.loadData(list);
    					setContentHegthDelay();
    					if(view!=1 && view!=2){
    						addDepart.calculate();
    					}
	    				
    				}
    			});
    		}else{
    			//初始化不执行写
//    			$scope.doQuery();
    		}
    	},
    	changeDescOrg:function(descOrgId){
    		addDepart.params.descOrgId=descOrgId;
    		
    		addDepart.form.descOrgId=descOrgId;
    		addDepart.doQuery();
    		$scope.list=[];
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
    	},
    	sumOfTransfer:function(){
    		addDepart.calculate();
    	}
		
	};
	
	addDepart.init();
}]);
