var addDeliveryApp = angular.module("addDeliveryApp", ['commonApp']);
addDeliveryApp.controller("addDeliveryCtrl", ["$scope","commonService","$timeout","$filter",function($scope,commonService,$timeout,$filter) {
	$scope.payDot=[];//配送网点
	$scope.query={};//查询条件
	$scope.freightCollectWait=0;//统计查询结果的到付
	$scope.collectingMoneyWait=0;//统计查询结果的货付
	$scope.actualVolumeWait=0;//统计查询结果的体积
	$scope.actualWeightWait=0;//统计查询结果的重量
	$scope.matchCount=0;//统计查询结果的票数
	$scope.freightCollect=0;//选择后的到付
	$scope.collectingMoney=0;//选择后的货付
	$scope.actualVolume=0;//选择后的体积
	$scope.actualWeight=0;//选择后的重量
	$scope.matchCountSel=0;//选择后的票数
	$scope.yeFlag=true;
	$scope.form={};//提交数据
	$scope.directQuery={};//服务商查询条件
	$scope.directCustFlag=false;
	$scope.sfQueryList=[];//服务商列表
	$scope.vehicleTypeList=[];
	$scope.businessTypeList=[];
	$scope.matchList=[];
	var batchNum=getQueryString("batchNum");
	var addDelivery={
		init:function(){
			this.bindEvent();
			this.toView();
			$scope.des={};
//			if(batchNum==null||batchNum==undefined||batchNum=='')
//			{
//				this.doQuery();
//			}
			$scope.isClick=true;
			commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType",{"codeType":"VEHICLE_TYPE"},function(data){
				$scope.vehicleTypeList=data.items;
			});
			commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType",{"codeType":"BUSINESS_TYPE"},function(data){
				$scope.businessTypeList=data.items;
			});
			jQuery("#_trackingNum").focus();
		},
		bindEvent:function(){
			$scope.doQuery=this.doQuery;
			$scope.statisticsMatch=this.statisticsMatch;
			$scope.cleanStatisticsMatch=this.cleanStatisticsMatch;
			$scope.clearQuery=this.clearQuery;
			$scope.delMatchList=this.delMatchList;
			$scope.statisticsMatchSel=this.statisticsMatchSel;
			$scope.cleanStatisticsMatchSel=this.cleanStatisticsMatchSel;
			$scope.selectSel=this.selectSel;
			$scope.next=this.next;
			$scope.selectVehicle=this.selectVehicle;
			$scope.clearSelect=this.clearSelect;
			$scope.doSave=this.doSave;
			$scope.param=this.param;
		    $scope.print=this.print;
		    $scope.doQueryVehile=this.doQueryVehile;
		    $scope.form=this.form;
		    $scope.clear=this.clear;
		    $scope.calculate=this.calculate;
		    $scope.showToFalse=this.showToFalse;
		    $scope.showTotrue=this.showTotrue;
		    $scope.selectClear=this.selectClear;
		    $scope.close=this.close;
		    $scope.fixNumber=this.fixNumber;
		    $scope.remove=this.remove;
		    $scope.add=this.add;
		    $scope.colse=this.colse;
		    $scope.changeDescOrg=this.changeDescOrg;
		    $scope.checkData=this.checkData;
		    $scope.isCheckData=this.isCheckData;
		    $scope.addClear=this.addClear;
		    $scope.chechHight=this.chechHight;
		    $scope.vehicleChange=this.vehicleChange;
		    $scope.doAddViechInfo=this.doAddViechInfo;
		    $scope.openDirect=this.openDirect;
			$scope.cosleDirect=this.cosleDirect;
			$scope.querySfInfo=this.querySfInfo;
			$scope.selectSf=this.selectSf;
		    $scope.setTrackingNum=this.setTrackingNum;
		    $scope.getSelectDataByTrackingNum=this.getSelectDataByTrackingNum;
		    
		    //改造
		    $scope.onLeftInputChange=this.onLeftInputChange;
		    $scope.filterInput=this.filterInput;
		    $scope.onLeftBtClick=this.onLeftBtClick;
		    $scope.filterInputRigth=this.filterInputRigth;
		    
		    $scope.leftBtselect=this.leftBtselect;
		    $scope.onRigthInputChange=this.onRigthInputChange;
		    $scope.onRigthBtClick=this.onRigthBtClick;
		    $scope.rigthBtselect=this.rigthBtselect;
		    $scope.selectMatchList=[];//查询结果列表
		    $scope.leftSortObj=this.leftSortObj;
		    $scope.rigthSortObj=this.rigthSortObj;
		    
		    $scope.onLeftSortClick=this.onLeftSortClick;
		    $scope.onRigthSortClick=this.onRigthSortClick;
		},
		
		//改造
		filterInput:{},//左边的过滤器
		leftDataAll:new Array(),//左边每次请求的加载的数据
		filterInputRigth:{},//右边的过滤器
		leftDataAll:new Array(),//左边每次请求的加载的数据
		selectMatchListTmp:[],//右边实际选择的数据
		selectMatchListTmp:[],
		leftSortObj:{},//保存左边的排序的类型
		rigthSortObj:{},//保存右边的排序的类型
		//左边的输入框的数据变化的处理
		onLeftInputChange:function(row){
			var filtObj={};
			eval("filtObj."+row+"=$scope.filterInput."+row);
			$scope.matchList=$filter("filter")(addDelivery.leftDataAll,filtObj);
			$scope.statisticsMatch();
		},
		onLeftSortClick:function(row){
			 var orderBy = $filter('orderBy');
			 var sortObj=$scope.leftSortObj[row];
			 
			 if(sortObj!=undefined){
				 if("+"==sortObj){
					 sortObj="-";
				 }else {
					 sortObj="+";
				 }
			 }else{
				 $scope.leftSortObj[row]="+";
				 sortObj="+";
			 }
			 //判断是生序，还是降序
			 var predicate=sortObj+row;
			 
			 //清空其他列的数据，只是保留这一列数据
			 $scope.leftSortObj={};
			 $scope.leftSortObj[row]=sortObj;
			 
			 $scope.matchList=orderBy($scope.matchList, predicate);
		},
		onRigthSortClick:function(row){
			 var orderBy = $filter('orderBy');
			 var sortObj=$scope.rigthSortObj[row];
			 
			 if(sortObj!=undefined){
				 if("+"==sortObj){
					 sortObj="-";
				 }else {
					 sortObj="+";
				 }
			 }else{
				 $scope.rigthSortObj[row]="+";
				 sortObj="+";
			 }
			 //判断是生序，还是降序
			 var predicate=sortObj+row;
			 
			 //清空其他列的数据，只是保留这一列数据
			 $scope.rigthSortObj={};
			 $scope.rigthSortObj[row]=sortObj;
			 
			 $scope.selectMatchList=orderBy($scope.selectMatchList, predicate);
		},
		
		//点击左边的按钮
		onLeftBtClick:function(f,d){
			if(f=="all"){
				//全选  直接把左边的全部数据移除过去
				var objList = $scope.matchList;
				if(objList==null || objList==undefined){
					return false;
				}
				
				if($scope.matchList!=null && $scope.matchList!=undefined){
					for(var j=0;j<$scope.matchList.length;j++){
						addDelivery.selectMatchListTmp.push($scope.matchList[j]);
					}
					$scope.selectMatchList=addDelivery.selectMatchListTmp;
					
					
					for(var i=0;i<$scope.matchList.length;i++){
						var matchTemp= $scope.matchList[i];
						//把左边的选中的数据删除掉
						for(var j=0;j<addDelivery.leftDataAll.length;j++){
							if(addDelivery.leftDataAll[j]!=null && addDelivery.leftDataAll[j]!=undefined 
									&& matchTemp.trackingNum==addDelivery.leftDataAll[j].trackingNum){
								addDelivery.leftDataAll.splice(j,1);
							}
						}
					}
					$scope.matchList=[];
				}
			}else{
				
				//单选
				if($scope.matchList!=null && $scope.matchList!=undefined && $scope.matchList.length>0){
    				for(var index in $scope.matchList){
    					if($scope.matchList[index]!=null && $scope.matchList[index]!=undefined){
    						var dateId=$scope.matchList[index]["trackingNum"];
	        				if(d==dateId){
	        					$scope.leftBtselect($scope.matchList[index]);
	        					break;
	        				}
    					}
        			}
    			}
			}
			$scope.statisticsMatch();
			$scope.statisticsMatchSel();
		},
		
		//右边的输入框的数据变化的处理
		onRigthInputChange:function(row){
			var filtObj={};
			eval("filtObj."+row+"=$scope.filterInputRigth."+row);
			$scope.selectMatchList=$filter("filter")(addDelivery.selectMatchListTmp,filtObj);
			$scope.statisticsMatchSel();
		},
		
		/**左边的按钮的选择一条数据 去掉左边的表格的选中的记录，把记录添加到右边表格*/
		leftBtselect:function(data) {
			//判断是否重复
			if(data!=undefined && addDelivery.selectMatchListTmp!=undefined && addDelivery.selectMatchListTmp.length>0){
				for(var i in addDelivery.selectMatchListTmp){
					var vl=addDelivery.selectMatchListTmp[i];
					if(vl.trackingNum==data.trackingNum){
						commonService.alert("添加的运单号["+data.trackingNum+"]重复");
						return;
					}
				}
			}
			
			
			//添加到右边的表格
			addDelivery.selectMatchListTmp.push(data);
			$scope.selectMatchList=addDelivery.selectMatchListTmp;
			
			//把左边的选中的数据删除掉
			for(var j=0;j<$scope.matchList.length;j++){
				if($scope.matchList[j]!=null && $scope.matchList[j]!=undefined 
						&& data.trackingNum==$scope.matchList[j].trackingNum){
					$scope.matchList.splice(j,1);
				}
			}
			
			//把左边的选中的数据删除掉
			for(var j=0;j<addDelivery.leftDataAll.length;j++){
				if(addDelivery.leftDataAll[j]!=null && addDelivery.leftDataAll[j]!=undefined 
						&& data.trackingNum==addDelivery.leftDataAll[j].trackingNum){
					addDelivery.leftDataAll.splice(j,1);
				}
			}
			
		},
		//点击右边的按钮
		onRigthBtClick:function(f,d){
			if(f=="all"){
				//全选  直接把右边的全部过滤后的数据移除过去  matchList
				var objList = $scope.selectMatchList;
				if(objList==null || objList==undefined){
					return false;
				}
				if($scope.selectMatchList!=null && $scope.selectMatchList!=undefined){
					for(var j=0;j<$scope.selectMatchList.length;j++){
						$scope.matchList.push($scope.selectMatchList[j]);
					}
					
					if(addDelivery.selectMatchListTmp!=undefined && addDelivery.selectMatchListTmp!=null){
						if(addDelivery.selectMatchListTmp.length==$scope.selectMatchList.length){
							//如果右边的数据是在没有过滤的情况下选择了全部，就直接全部过去，不需要处理
							$scope.selectMatchList=[];
							addDelivery.selectMatchListTmp=[];
						}else if(addDelivery.selectMatchListTmp.length>$scope.selectMatchList.length){
							//如果右边的数据是在过滤的情况下，点击全部，只是把过滤后的数据进行移除
							for(var j=0;j< $scope.selectMatchList.length;j++){
								var trackingNum=$scope.selectMatchList[j]["trackingNum"];
								for(var i=0;i<addDelivery.selectMatchListTmp.length;i++){
									var trackingNumTemp=addDelivery.selectMatchListTmp[i]["trackingNum"];
									if(trackingNumTemp==trackingNum){
										addDelivery.selectMatchListTmp.splice(i,1);
									}
								}
							}
							
							$scope.selectMatchList=[];
						}
					}
					
				}
			}else{
				
				//单选
				if($scope.selectMatchList!=null && $scope.selectMatchList!=undefined && $scope.selectMatchList.length>0){
    				for(var index in $scope.selectMatchList){
    					if($scope.selectMatchList[index]!=null && $scope.selectMatchList[index]!=undefined){
    						var dateId=$scope.selectMatchList[index]["trackingNum"];
	        				if(d==dateId){
	        					$scope.rigthBtselect($scope.selectMatchList[index]);
	        					break;
	        				}
    					}
        			}
    			}
			}
			
			$scope.statisticsMatch();
			$scope.statisticsMatchSel();
		},
		
		/**右边选择一行的记录：移除右边的一行，并把这一行添加到左边*/
		rigthBtselect:function(data) {
			if(addDelivery.leftDataAll==null || addDelivery.leftDataAll==undefined){
				addDelivery.leftDataAll=[];
				return;
			}
			//添加到左边的列表
			$scope.matchList.push(data);
			addDelivery.leftDataAll.push(data);
			
			//把右边的选中的数据删除掉，删除的是展示的数据
			for(var j=0;j<$scope.selectMatchList.length;j++){
				if($scope.selectMatchList[j]!=null && $scope.selectMatchList[j]!=undefined 
						&& data.trackingNum==$scope.selectMatchList[j].trackingNum){
					$scope.selectMatchList.splice(j,1);
				}
			}
			
			//把右边的选中的数据删除掉，删除全部的数据
			for(var j=0;j<addDelivery.selectMatchListTmp.length;j++){
				if(addDelivery.selectMatchListTmp[j]!=null && addDelivery.selectMatchListTmp[j]!=undefined 
						&& data.trackingNum==addDelivery.selectMatchListTmp[j].trackingNum){
					addDelivery.selectMatchListTmp.splice(j,1);
				}
			}
			
		},
		
		
		
		
		//改造end
		doQuery:function(){
			/**订单列表查询*/
			$timeout(function(){

			$scope.query.provinceId = $scope.des.chooseCityId;
			
			if($scope.query.provinceId==undefined || $scope.query.provinceId==""
				 || $scope.query.provinceId==null){
				commonService.alert("请选择一下收货地址，省份没有选择，请重新选择！");
				return;
			}
			
			$scope.query.cityId = $scope.des.chooseRegionId;
			
			if($scope.query.cityId==undefined || $scope.query.cityId==""
				 || $scope.query.cityId==null){
				commonService.alert("请选择一下收货地址，城市没有选择，请重新选择！");
				return;
			}
			
			
			$scope.query.countyId = $scope.des.chooseCountyId;
			$scope.query.streetId = $scope.des.chooseStreetId;
			var url = "deliveryGoodsBO.ajax?cmd=addQuery";
			window.top.showLoad();
			commonService.postUrl(url,$scope.query,function(data){
				var length=data.items.length;
				window.top.hideLoad();
				for(var i=length;i>0;i--){
					for(var j in addDelivery.selectMatchListTmp){
						if(addDelivery.selectMatchListTmp[j]!=undefined){
							if(data.items[i-1].taskId==addDelivery.selectMatchListTmp[j].taskId){
								data.items.splice(i-1,1);
								break;
							}
						}
					}
				}
				addDelivery.leftDataAll = data.items;
				angular.copy(addDelivery.leftDataAll,$scope.matchList);
				setContentHegthDelay();
				$scope.statisticsMatch();
				$scope.statisticsMatchSel();
			},function(){
				window.top.hideLoad();
			});		
			},500);
		},
		statisticsMatch:function(){
			$scope.cleanStatisticsMatch();
			if($scope.matchList!=null && $scope.matchList.length>0){
				$scope.matchCount=$scope.matchList.length;
				for(var i in $scope.matchList){
					$scope.freightCollectWait+=(Number($scope.matchList[i].freightCollect));
					$scope.collectingMoneyWait+=(Number($scope.matchList[i].collectingMoney));
					$scope.actualVolumeWait+=Number($scope.matchList[i].volumes);
					$scope.actualWeightWait+=Number($scope.matchList[i].weight);
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
			$scope.des.clear();
			document.getElementById("beginTime").value="";
			document.getElementById("endTime").value="";
		},

		statisticsMatchSel:function(){
			$scope.cleanStatisticsMatchSel();
			if($scope.selectMatchList!=null && $scope.selectMatchList.length>0){
				$scope.matchCountSel=$scope.selectMatchList.length;
				for(var i in $scope.selectMatchList){
					$scope.freightCollect+=(Number($scope.selectMatchList[i].freightCollect));
					$scope.collectingMoney+=(Number($scope.selectMatchList[i].collectingMoney));
					$scope.actualVolume+=Number($scope.selectMatchList[i].volumes);
					$scope.actualWeight+=Number($scope.selectMatchList[i].weight);
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
		
		next:function(n){
    		if(n==1){
    			if(addDelivery.selectMatchListTmp.length<=0){
    				 commonService.alert("请选择送货的运单!");
    	    		  return false ;
    			}
    			$scope.yeFlag=false;
    			$scope.diy.loadData(addDelivery.selectMatchListTmp);
    			$scope.calculate();
    			setContentHegthDelay();
    			$timeout(function(){
    				$scope.isClick=false;
    			},2000);
    		}else{
    			$scope.yeFlag=true;
    			$scope.isClick=true;
    			
    			$scope.statisticsMatch();
    			$scope.statisticsMatchSel();
    		}
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
			for(var i in selectedDate){
				if(selectedDate[i].taskState==8){
					 commonService.alert("运单["+addDelivery.selectMatchListTmp[i].trackingNum+"]已签收，不可以移除!");
   	    		  return false ;
				}
			}
			if(selectedDate.length>0){
				for(var i in selectedDate){
					for(var j in addDelivery.selectMatchListTmp){
						if(selectedDate[i].taskId==addDelivery.selectMatchListTmp[j].taskId){
							addDelivery.selectMatchListTmp.splice(j,1);
							break;
						}
					}
					
					for(var j in $scope.selectMatchList){
						if(selectedDate[i].taskId==$scope.selectMatchList[j].taskId){
							$scope.selectMatchList.splice(j,1);
							break;
						}
					}
					addDelivery.leftDataAll.push(selectedDate[i]);
					$scope.matchList.push(selectedDate[i]);
				}
				$scope.diy.loadData(addDelivery.selectMatchListTmp);
				
				display();
			}else{
				commonService.alert("请选择配载的运单!");
			}
			$scope.calculate();
			$scope.diy.clearAllCheckbox();
		},
		
		doSave:function(){
			if($scope.form.sfUserId==null||$scope.form.sfUserId==undefined ||$scope.form.sfUserId==''){
				commonService.alert("请选择师傅!");
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
				if($scope.form.payState==null || $scope.form.payState==undefined || $scope.form.payState==''){
					commonService.alert("请选择运费的支付状态!");
					return false ;
				}
				$scope.form.freightFee=$scope.form.freight*100;
			}
	        
			if($scope.form.stevedoring!=null && $scope.form.stevedoring!=undefined && Number($scope.form.stevedoring)>0){
				if($scope.form.stevedoringPayState==null || $scope.form.stevedoringPayState==undefined){
					commonService.alert("请选择装卸费的支付状态!");
					return false ;
				}
				$scope.form.stevedoringFee=$scope.form.stevedoring*100;
			}else{
				$scope.form.stevedoringFee=0;
			}
			if($scope.form.totalGetMoneyFee!=null && $scope.form.totalGetMoneyFee!=undefined && Number($scope.form.totalGetMoneyFee)>0){
				if($scope.form.isGet==null || $scope.form.isGet==undefined){
					commonService.alert("请选择应收款的收取状态!");
					return false ;
				}
				$scope.form.totalGetMoney=parseInt($scope.form.totalGetMoneyFee*100);
			}else{
				$scope.form.totalGetMoney=null;
				$scope.form.isGet=null;
			}
			if($scope.form.totalPayMoneyFee!=null && $scope.form.totalPayMoneyFee!=undefined && Number($scope.form.totalPayMoneyFee)!=0){
				if($scope.form.isPay==null || $scope.form.isPay==undefined){
					commonService.alert("请选择应付款的支付状态!");
					return false ;
				}
				$scope.form.totalPayMoney=parseInt($scope.form.totalPayMoneyFee*100);
			}else{
				$scope.form.totalPayMoney=null;
				$scope.form.isPay=null;
			}
			$scope.form.branchAndInstall=$scope.form.branchAndInstallFee*100;
			$scope.form.freightCollect=$scope.form.freightCollectFee*100;
			$scope.form.collectingMoney=$scope.form.collectingMoneyFee*100;
			if(addDelivery.selectMatchListTmp.length==0){
				commonService.alert("请选择待发货订单到配载列表!");
				return false ;
			}
			var taskId=new Array();
			var orderId=new Array();
			var taskFee=new Array();
	        for(var i in addDelivery.selectMatchListTmp){
	        	taskId.push(addDelivery.selectMatchListTmp[i].taskId);
	        	orderId.push(addDelivery.selectMatchListTmp[i].orderId);
	        	taskFee.push(addDelivery.selectMatchListTmp[i].newBranchAndInstall*100);
	        	if(addDelivery.selectMatchListTmp[i].sfUserId!=null&&addDelivery.selectMatchListTmp[i].sfUserId!=undefined&&addDelivery.selectMatchListTmp[i].sfUserId!=''){
	        		if(addDelivery.selectMatchListTmp[i].sfUserId!=$scope.form.sfUserId){
	        			commonService.alert("运单["+addDelivery.selectMatchListTmp[i].trackingNum+"]原分配的师傅与最新分配师傅不一致，请移除不一致的运单!");
	    				return false ;
	        		}
	        	}
	        }
			$scope.form.orderId=orderId;
			$scope.form.taskId=taskId;
			$scope.form.taskFee=taskFee;
			var tips="所选车辆["+$scope.form.plateNumber+"]，可配置重量、体积为["+$scope.form.weight+"吨,"+$scope.form.volume+"立方]，当前配置信息中，货物重量、体积为["+$scope.actualWeight/1000+"吨,"+$scope.actualVolume+"立方]，";
			if( parseFloat($scope.form.weight)< parseFloat($scope.actualWeight/1000)
					|| parseFloat($scope.form.volume)< parseFloat($scope.actualVolume)){
				tips=tips+"已经超出可配载范围，确定要继续 保存配载信息？";
			}else{
				tips=tips+"确定要继续 保存配载信息？";
			}
			commonService.confirm(tips,function(){
				var param = $scope.form;
				var url ="deliveryGoodsBO.ajax?cmd=doSave";
				window.top.showLoad();
				commonService.postUrl(url,param,function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
						commonService.alert("操作成功!");
						window.top.hideLoad();
						$scope.colse();
						//如果是修改就不清空。
						var batchNum=getQueryString("batchNum");
			    		if(batchNum!=null && batchNum!=undefined && batchNum!=''){
			    			return false;
			    		}
		 	    	}
				},function(){
					window.top.hideLoad();
				});
			});
		},
		colse:function(){
			commonService.closeToParentTab(true);
		},
		toView:function(){
			/**根据运单号查询*/
    		var batchNum=getQueryString("batchNum");
    		if(batchNum!=null && batchNum!=undefined && batchNum!=''){
    			var queryString={"batchNum":batchNum};
    			var url="deliveryGoodsBO.ajax?cmd=toView";
    			commonService.postUrl(url,queryString,function(data){
    				for (var i in data.list) {
    					addDelivery.selectMatchListTmp.push(data.list[i]);
    					$scope.selectMatchList.push(data.list[i]);
    				}
    				/**拷贝配载的数据**/
    				$scope.form=data;
    				$scope.form.weight=data.weight;
    				if(data.list.length>0){
	    				$scope.form.sfUserId=data.list[0].sfUserId;
	    				$scope.form.sfName=data.list[0].sfName;
	    				$scope.form.sfPhone=data.list[0].sfPhone;
    				}
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
    				$scope.form.freight=data.freight/100;
    				$scope.form.freightPayDot=data.freightPayDot;
    				$scope.form.payState=data.payState;
    				$scope.form.stevedoring=data.stevedoring/100;
    				$scope.form.stevedoringPayDot=data.stevedoringPayDot;
    				$scope.form.stevedoringPayState=data.stevedoringPayState;
    				$scope.form.driverId=data.driverId;
    				$scope.form.remarks=data.remarks;
//    				$scope.doQuery();
    				$scope.calculate();
    				
    				$scope.statisticsMatch();
        			$scope.statisticsMatchSel();
    				
    				setContentHegthDelay();
    			});
    		}
    	},
		params:{
			totalPage:1,
			vehicleState:1
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
		chechHight:function(obj){
			if (obj == undefined ||obj.length == 0) {// 没有数据，指定一个默认高度
				setContentHeightWithSpecialHeight(683);
			} else {// 有数据，则根据数据行计算高度
				var height = (683 - 270) +obj.length * 31 ;
				height = height <= 683 ? 683 : height;
				setContentHeightWithSpecialHeight(height);
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
				for(var j in $scope.orderInfoList){
					value.push($scope.orderInfoList[j]);
				}
			}
			value.push(data);
			$scope.orderInfoList= value;
		},
		/**车辆列表查询*/
		doQueryVehile:function(plateNumber){
				addDelivery.params.plateNumber=plateNumber;
				addDelivery.params.orgId=userInfo.orgId;
				var url = "vehicleInfoBO.ajax?cmd=doQuery";
				$scope.page.load({
					url:url,
					params:addDelivery.params
				});
				$scope.showPcum = true;
		},
		close:function(){
    		commonService.closeToParentTab(true);
    	},
    	changeDescOrg:function(descOrgId){
    		addDelivery.params.descOrgId=descOrgId;
    		
    		addDelivery.form.descOrgId=descOrgId;
    		addDelivery.doQuery();
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
			$scope.query.sfUserAcct=sfUserAcct;
			$scope.query.sfUserName=sfUserName;
			$scope.query.isArea=1;
			$timeout(function(){
				$scope.sf.load({
							url:'cmSfUserInfoBO.ajax?cmd=queryUserInfo',
							params:$scope.query
						});
			},500);
		},
		openDirect:function(){
    		$scope.directCustFlag=true;
    		this.querySfInfo();
    	},
    	cosleDirect:function(){
    		$scope.directCustFlag=false;
    	},
    	selectSf:function(data){
			$scope.form.sfUserId=data.sfUserId;
			$scope.form.sfName=data.name;
			$scope.form.sfPhone=data.phone;
			this.cosleDirect();
		},
		calculate:function(){
			$scope.form.freightCollectFee=0;
			$scope.form.collectingMoneyFee=0;
			$scope.form.branchAndInstallFee=0;
			$scope.form.totalGetMoneyFee=0;
			$scope.form.totalGetMoney=0;
			$scope.form.totalPayMoneyFee=0;
			$scope.form.totalPayMoney=0;
			if(addDelivery.selectMatchListTmp!=null && addDelivery.selectMatchListTmp.length>0){
				for(var i in addDelivery.selectMatchListTmp){
					$scope.form.freightCollectFee+=(Number(addDelivery.selectMatchListTmp[i].freightCollect));
					$scope.form.collectingMoneyFee+=(Number(addDelivery.selectMatchListTmp[i].collectingMoney));
					$scope.form.branchAndInstallFee+=(Number(addDelivery.selectMatchListTmp[i].newBranchAndInstall));
				}
			}
			var money=($scope.form.freightCollectFee+$scope.form.collectingMoneyFee)-$scope.form.branchAndInstallFee;
			if(money>0){
					$scope.form.totalGetMoneyFee=money;
			}else{
				$scope.form.totalPayMoneyFee=-money;
			}
		},
	 	setTrackingNum:function(ev){
    		if (ev.keyCode !== 13) return;
    		
    		var selectObj = $scope.getSelectDataByTrackingNum($scope.trackingNum);
    		
    		if(selectObj==undefined){
    			commonService.alert("运单号不存在，请核实后扫描!",function(){
    				$scope.trackingNum = "";
    				jQuery("#_trackingNum").focus();
    			});
    			$scope.trackingNum = "";
    			jQuery("#_trackingNum").focus();
    		}else{
    			$scope.leftBtselect(selectObj);
        		
    			$scope.statisticsMatch();
    			$scope.statisticsMatchSel();
    			
    			$scope.trackingNum = "";
        		jQuery("#_trackingNum").focus();
        		//控制页面enter事件。
        		$scope.preventEnter = true;
    		}
    	},
		//获取选中的数据
		getSelectDataByTrackingNum:function(trackingNum){
			var selectArray=new Array();
			var list=$scope.matchList;
			if(list!=null && list!=undefined && list.length>0){
				for(var index in list){
					var vl=list[index];
					if(vl!=null && vl!=undefined){
						if(vl.trackingNum == trackingNum){
							return vl;
						}
					}
    			}
			}
		}
	};
	
	addDelivery.init();
}]);



function display(){
	 var body=window.parent.document.getElementsByTagName("body");
	 var height=body[0].clientHeight;
	 var Width=body[0].clientWidth;
	 try {
		 var ss=document.getElementsByClassName("search_table")[0].scrollHeight;
		 var cc=document.getElementsByClassName("chey_b_1")[0].scrollHeight;	
		 var table_height=document.getElementsByClassName("table_height");
		 for(var index in table_height ){
			 if(!isNaN(index)){
			 table_height[index].style.height=parseInt(height)-ss-cc-248;
			 }
		 }
		 window.top.resizeTo(Width,height+170);
	 	} catch (e) {
	 		console.log("表格自适应报错");
	 	}
}
