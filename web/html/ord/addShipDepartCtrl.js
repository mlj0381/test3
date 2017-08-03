var addShipDepartApp = angular.module("addShipDepartApp", ['commonApp']);
addShipDepartApp.controller("addShipDepartCtrl", ["$scope","commonService","$timeout","$filter",function($scope,commonService,$timeout,$filter) {
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
	
	$scope.freightCollect=0;//选择后的到付
	$scope.collectingMoney=0;//选择后的货付
	$scope.actualVolume=0;//选择后的体积
	$scope.actualWeight=0;//选择后的重量
	$scope.matchCountSel=0;//选择后的票数
	$scope.yeFlag=true;
	$scope.dispatchOrg=[];//配送网点
	$scope.dispatchOrgList=[];// 付款方
	$scope.form={};//提交数据
	
	
	$scope.vehicleTypeList=[];
	var isShort = getQueryString("isShort"); //1短驳 默认干线
	var addShipDepart={
		filterInput:{},//左边的过滤器
		filterInputRigth:{},//右边的过滤器
		leftDataAll:new Array(),//左边每次请求的加载的数据
		selectMatchListTmp:[],//右边实际选择的数据
		selected:[],//选择框选中存的数据,
		leftSortObj:{},//保存左边的排序的类型
		rigthSortObj:{},//保存右边的排序的类型
		lefSum:{
			count:0,
			volume:0,
			weight:0,
			totalFee:0,
			freightCollect:0,
			collectingMoney:0,
			installCosts:0
			},
		init:function(){
			this.bindEvent();
			this.toView();
			this.queryOrg();
			this.queryDispatchOrg();
			jQuery("#_trackingNum").focus();
		},
		query:function(){
			addShipDepart.isQuery=true;
			this.doQuery();
		},
		isQuery:false,//
		
		bindEvent:function(){
			$scope.doQuery=this.doQuery;
			$scope.cleanStatisticsMatch=this.cleanStatisticsMatch;
			$scope.clearQuery=this.clearQuery;
			$scope.leftBtselect=this.leftBtselect;
			$scope.onLeftBtClick=this.onLeftBtClick;
			$scope.statisticsMatchSel=this.statisticsMatchSel;
			$scope.cleanStatisticsMatchSel=this.cleanStatisticsMatchSel;
			$scope.rigthBtselect=this.rigthBtselect;
			$scope.onRigthBtClick=this.onRigthBtClick;
			$scope.next=this.next;
			$scope.queryDispatchOrg=this.queryDispatchOrg;
			$scope.selectDispatchOrg=this.selectDispatchOrg;
			$scope.selectVehicle=this.selectVehicle;
			$scope.clearSelect=this.clearSelect;
			$scope.doSave=this.doSave;
			$scope.openNext=this.openNext;
			
			$scope.param=this.param;
		    $scope.print=this.print;
		    $scope.form=this.form;
		    $scope.showToFalse=this.showToFalse;
		    $scope.showTotrue=this.showTotrue;
		    $scope.close=this.close;
		    $scope.fixNumber=this.fixNumber;
		    $scope.remove=this.remove;
		    $scope.add=this.add;
		    $scope.changeDescOrg=this.changeDescOrg;
		    $scope.selectData=this.selectData;

		    $scope.removeClear=this.removeClear;
		    $scope.addClear=this.addClear;
		    $scope.chechHight=this.chechHight;
		    $scope.setTrackingNum=this.setTrackingNum;
		    $scope.getSelectDataByTrackingNum=this.getSelectDataByTrackingNum;
		    
		    $scope.query=this.query;
		    $scope.filtSelectdData=this.filtSelectdData;
		    //新增的方法
		    $scope.filterInput=this.filterInput;
		    $scope.filterInputRigth=this.filterInputRigth;
		    $scope.onLeftInputChange=this.onLeftInputChange;
		    $scope.onRigthInputChange=this.onRigthInputChange;
		    
		    $scope.leftSum=this.lefSum;
		    $scope.rigthSum=this.rigthSum;
		    
		    $scope.leftSumFn=this.leftSumFn;
		    $scope.rigthSumFn=this.rigthSumFn;
		    
		    $scope.scrollTopTable=scrollTopTable;
		    
		    $scope.updateSelection=this.updateSelection;
		    $scope.selected=this.selected;
		    $scope.clearAllCheckbox=this.clearAllCheckbox;
		    $scope.selectAllCheckbox=this.selectAllCheckbox;
		    
		    $scope.onLeftSortClick=this.onLeftSortClick;
		    $scope.onRigthSortClick=this.onRigthSortClick;
		    $scope.leftSortObj=this.leftSortObj;
		    $scope.rigthSortObj=this.rigthSortObj;
		},
		
		//左边的汇总所有数据
		rigthSumFn:function(allList){
			//每次调用的时候，需要情况数据
			$scope.rigthSum={
				count:0,
				volume:0,
				weight:0,
				totalFee:0,
				freightCollect:0,
				collectingMoney:0,
				installCosts:0
			};
			if(allList!=undefined && allList.length!=undefined && allList.length>0){
				for(var i in allList){
					var vl=allList[i];
					if(vl.count!=undefined){
						try {
							var count=parseInt(vl.count);
							if(count>0){
								$scope.rigthSum.count=$scope.rigthSum.count+count;
							}
						} catch (e) {
							
						}
						
					}
					if(vl.volume!=undefined ){
						try {
							var volume=parseFloat(vl.volume);
							if(volume>0){
								$scope.rigthSum.volume=$scope.rigthSum.volume+volume;
							}
							
						} catch (e) {
							
						}
					}
					
					if(vl.weight!=undefined){
						try {
							var weight=parseFloat(vl.weight);
							if(weight>0){
								$scope.rigthSum.weight=$scope.rigthSum.weight+weight;
							}
						} catch (e) {
							
						}
						
					}
					
					if(vl.totalFeeString!=undefined){
						try {
							var totalFee=parseFloat(vl.totalFeeString);
							if(totalFee>0){
								$scope.rigthSum.totalFee=$scope.rigthSum.totalFee+totalFee;
							}
						} catch (e) {
							
						}
						
					}
					if(vl.freightCollectString!=undefined){
						
						try {
							var freightCollect=parseFloat(vl.freightCollectString);
							if(freightCollect>0){
								$scope.rigthSum.freightCollect=$scope.rigthSum.freightCollect+freightCollect;
							}
						} catch (e) {
							
						}
						
					}
					if(vl.collectingMoneyString!=undefined ){
						try {
							var collectingMoney=parseFloat(vl.collectingMoneyString);
							if(collectingMoney>0){
								$scope.rigthSum.collectingMoney=$scope.rigthSum.collectingMoney+collectingMoney;
							}
						} catch (e) {
							
						}
						
					}
					if(vl.installCostsString!=undefined){
						try {
							var installCosts=parseFloat(vl.installCostsString);
							if(installCosts>0){
								$scope.rigthSum.installCosts=$scope.rigthSum.installCosts+installCosts;
							}
						} catch (e) {
							
						}
						
					}
				}
			}
			
		},
		
		
		
		//左边的汇总所有数据
		leftSumFn:function(allList){
			//每次调用的时候，需要情况数据
			$scope.leftSum={
				count:0,
				volume:0,
				weight:0,
				totalFee:0,
				freightCollect:0,
				collectingMoney:0,
				installCosts:0
			};
			if(allList!=undefined && allList.length!=undefined && allList.length>0){
				for(var i in allList){
					var vl=allList[i];
					if(vl.count!=undefined){
						try {
							var count=parseInt(vl.count);
							if(count>0){
								$scope.leftSum.count=$scope.leftSum.count+count;
							}
						} catch (e) {
							
						}
						
					}
					if(vl.volume!=undefined ){
						try {
							var volume=parseFloat(vl.volume);
							if(volume>0){
								$scope.leftSum.volume=$scope.leftSum.volume+volume;
							}
							
						} catch (e) {
							
						}
					}
					
					if(vl.weight!=undefined){
						try {
							var weight=parseFloat(vl.weight);
							if(weight>0){
								$scope.leftSum.weight=$scope.leftSum.weight+weight;
							}
						} catch (e) {
							
						}
						
					}
					
					if(vl.totalFeeString!=undefined){
						try {
							var totalFee=parseFloat(vl.totalFeeString);
							if(totalFee>0){
								$scope.leftSum.totalFee=$scope.leftSum.totalFee+totalFee;
							}
						} catch (e) {
							
						}
						
					}
					if(vl.freightCollectString!=undefined){
						
						try {
							var freightCollect=parseFloat(vl.freightCollectString);
							if(freightCollect>0){
								$scope.leftSum.freightCollect=$scope.leftSum.freightCollect+freightCollect;
							}
						} catch (e) {
							
						}
						
					}
					if(vl.collectingMoneyString!=undefined ){
						try {
							var collectingMoney=parseFloat(vl.collectingMoneyString);
							if(collectingMoney>0){
								$scope.leftSum.collectingMoney=$scope.leftSum.collectingMoney+collectingMoney;
							}
						} catch (e) {
							
						}
						
					}
					if(vl.installCostsString!=undefined){
						try {
							var installCosts=parseFloat(vl.installCostsString);
							if(installCosts>0){
								$scope.leftSum.installCosts=$scope.leftSum.installCosts+installCosts;
							}
						} catch (e) {
							
						}
						
					}
				}
			}
			
		},
		//左边的输入框的数据变化的处理
		onLeftInputChange:function(row){
			var filtObj={};
			eval("filtObj."+row+"=$scope.filterInput."+row);
			$scope.matchList=$filter("filter")(addShipDepart.leftDataAll,filtObj);
			$scope.filtSelectdData();
			$scope.leftSumFn($scope.matchList);
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
		
		
		//右边的输入框的数据变化的处理
		onRigthInputChange:function(row){
			var filtObj={};
			eval("filtObj."+row+"=$scope.filterInputRigth."+row);
			$scope.selectMatchList=$filter("filter")(addShipDepart.selectMatchListTmp,filtObj);
//			$scope.filtSelectdData();
			$scope.rigthSumFn($scope.selectMatchList);
			
			//右边的变化事件跟下一页的事件是绑定一起。
			//下一页的表格，如果之前选择了数据，输入过滤后，如果去掉过滤后，原来选中的效果没有，但是js实际已经有选中数据
			if($scope.selected.length>0){
				
				for(var i in $scope.selected){
					var data=$scope.selected[i];//运单号
					$scope.updateSelection("",data);
				}
				
			}
			
		},
		
		queryOrg:function(){
			/**查询配送网点*/
			if(isShort == 1 || isShort == "1"){
				//短驳查询短驳线路
				var url = "routeBO.ajax?cmd=queryRoateRutingIsShort";
				commonService.postUrl(url,{},function(data){
					if(data!=null && data!=undefined && data!=""){
						if(data!=null && data != undefined){
							$scope.payDot = data.routeList;
							$scope.payDot.unshift({endOrgId:-1,endOrgName:"全部"});
     						$scope.query.descOrgId = -1;
						}
		 	    	}
				});
				
			}else{
				//干线
				var param = {};
				var url = "routeBO.ajax?cmd=getCurrRoute";
				commonService.postUrl(url,param,function(data){
					if(data!=null && data!=undefined && data!=""){
						if(data!=null && data!=undefined && data.items!=null && data.items!=undefined && data.items.length>0){
							$scope.payDot=data.items;
							$scope.payDot.unshift({endOrgId:-1,endOrgName:"全部"});
							$scope.query.descOrgId=data.items[0].endOrgId;
						}
		 	    	}
				});
			}
			
		},

		filtSelectdData:function(){
			if($scope.selectMatchList!=undefined && $scope.selectMatchList.length>0){
				for(var i=$scope.matchList.length-1;i>=0;i--){
					var prevl=$scope.matchList[i];
					for(var j=0; j<$scope.selectMatchList.length;j++){
						var vl=$scope.selectMatchList[j];
						if(prevl.trackingNum==vl.trackingNum){
							$scope.matchList.splice(i,1);
							break;
						}
					}
				}
			}
			
		},
		
		doQuery:function(){
			/**订单列表查询*/
			$scope.query.cityId=$scope.des.chooseCityId;//省份
			$scope.query.countyId=$scope.des.chooseCountyId;//区域
			$scope.query.regionId=$scope.des.chooseRegionId;//市
			$scope.query.isShip=1;//市
			$scope.query.beginTime = $("#beginDate").val();//开始时间
			$scope.query.endTime = $("#endDate").val();//结束时间
			$scope.query.rows=50;
			$scope.query.page=1;
			
			window.top.showLoad();
			var url = "orderInfoBO.ajax?cmd=queryMatchCurrentOrg";
			commonService.postUrl(url,$scope.query,function(data){
				$scope.matchList = data.items;
				
				var pages = Math.round((data.totalNum+25)/50);
				var loadCount=pages-1;
	            for (var i=2; i<=pages; i++) {
	                    $scope.query.page=i;
	                    window.top.showLoad();
	                    commonService.postUrl(url,$scope.query,function(dataTemp){
	                    		loadCount=loadCount-1;
	                    		dataTemp.items.forEach(function(e){
	                                    $scope.matchList.push(e);
	                            });
	                    		if(loadCount==0){
	                    			$scope.filtSelectdData();
	                    			angular.copy($scope.matchList,addShipDepart.leftDataAll);
	                    			$scope.leftSumFn($scope.matchList);
	                    		}
	                    		window.top.hideLoad();
	                    });
	            }
	            if(loadCount==1 || loadCount==0){
	            	$scope.filtSelectdData();
	            	angular.copy($scope.matchList,addShipDepart.leftDataAll);
	            	$scope.leftSumFn($scope.matchList);
	            }
				
				$scope.matchListTmp = data.items;
				$scope.selectData.trackingNums="";
				$scope.selectData.gtWeight="";
				$scope.selectData.gtVolume="";
				$scope.selectData.gtCount="";
				$scope.selectData.geTrackingNums="";
				$scope.selectData.geWeight="";
				$scope.selectData.geVolume="";
				$scope.selectData.geCount="";
				//修改时不需要清空,点击了查询按钮后，也不能清除数据
				var batchNum=getQueryString("batchNum");
				if(batchNum==null || batchNum==undefined || batchNum==""){
					if(!addShipDepart.isQuery){
						$scope.selectMatchList=[];
						addShipDepart.selectMatchListTmp=[];
					}
				}
				setContentHegthDelay();
				window.top.hideLoad();
			},function(){
				window.top.hideLoad();
			});					
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
		/**左边的按钮的选择一条数据 去掉左边的表格的选中的记录，把记录添加到右边表格*/
		leftBtselect:function(data) {
			//判断是否重复
			if(data!=undefined && addShipDepart.selectMatchListTmp!=undefined && addShipDepart.selectMatchListTmp.length>0){
				for(var i in addShipDepart.selectMatchListTmp){
					var vl=addShipDepart.selectMatchListTmp[i];
					if(vl.trackingNum==data.trackingNum){
						commonService.alert("添加的运单号["+data.trackingNum+"]重复");
						return;
					}
				}
			}
			
			
			//添加到右边的表格
			addShipDepart.selectMatchListTmp.push(data);
			$scope.selectMatchList=addShipDepart.selectMatchListTmp;
			
			//把左边的选中的数据删除掉
			for(var j=0;j<$scope.matchList.length;j++){
				if($scope.matchList[j]!=null && $scope.matchList[j]!=undefined 
						&& data.trackingNum==$scope.matchList[j].trackingNum){
					$scope.matchList.splice(j,1);
				}
			}
			
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
						addShipDepart.selectMatchListTmp.push($scope.matchList[j]);
					}
					$scope.selectMatchList=addShipDepart.selectMatchListTmp;
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
			$scope.leftSumFn($scope.matchList);
			$scope.rigthSumFn($scope.selectMatchList);
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
					
					if(addShipDepart.selectMatchListTmp!=undefined && addShipDepart.selectMatchListTmp!=null){
						if(addShipDepart.selectMatchListTmp.length==$scope.selectMatchList.length){
							//如果右边的数据是在没有过滤的情况下选择了全部，就直接全部过去，不需要处理
							$scope.selectMatchList=[];
							addShipDepart.selectMatchListTmp=[];
						}else if(addShipDepart.selectMatchListTmp.length>$scope.selectMatchList.length){
							//如果右边的数据是在过滤的情况下，点击全部，只是把过滤后的数据进行移除
							for(var j=0;j< $scope.selectMatchList.length;j++){
								var trackingNum=$scope.selectMatchList[j]["trackingNum"];
								for(var i=0;i<addShipDepart.selectMatchListTmp.length;i++){
									var trackingNumTemp=addShipDepart.selectMatchListTmp[i]["trackingNum"];
									if(trackingNumTemp==trackingNum){
										addShipDepart.selectMatchListTmp.splice(i,1);
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
			$scope.leftSumFn($scope.matchList);
			$scope.rigthSumFn($scope.selectMatchList);
		},
		
		
		
		statisticsMatchSel:function(){
			$scope.cleanStatisticsMatchSel();
			if(addShipDepart.selectMatchListTmp!=null && addShipDepart.selectMatchListTmp.length>0){
				$scope.matchCountSel=addShipDepart.selectMatchListTmp.length;
				for(var i=0;i<addShipDepart.selectMatchListTmp.length;i++){
					if(addShipDepart.selectMatchListTmp != null && addShipDepart.selectMatchListTmp != undefined 
							&& addShipDepart.selectMatchListTmp[i]!=null && addShipDepart.selectMatchListTmp[i]!=undefined ){
						$scope.freightCollect+=(parseInt(addShipDepart.selectMatchListTmp[i].freightCollect)/100);
						$scope.collectingMoney+=(parseInt(addShipDepart.selectMatchListTmp[i].collectingMoney)/100);
						$scope.actualVolume+=addShipDepart.selectMatchListTmp[i].volume;
						$scope.actualWeight+=addShipDepart.selectMatchListTmp[i].weight;
					}
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
		/**右边选择一行的记录：移除右边的一行，并把这一行添加到左边*/
		rigthBtselect:function(data) {
			if($scope.matchListTmp==null || $scope.matchListTmp==undefined){
				$scope.matchListTmp=[];
				return;
			}
			//添加到左边的列表
			$scope.matchList.push(data);
			
			//把右边的选中的数据删除掉，删除的是展示的数据
			for(var j=0;j<$scope.selectMatchList.length;j++){
				if($scope.selectMatchList[j]!=null && $scope.selectMatchList[j]!=undefined 
						&& data.trackingNum==$scope.selectMatchList[j].trackingNum){
					$scope.selectMatchList.splice(j,1);
				}
			}
			
			//把右边的选中的数据删除掉，删除全部的数据
			for(var j=0;j<addShipDepart.selectMatchListTmp.length;j++){
				if(addShipDepart.selectMatchListTmp[j]!=null && addShipDepart.selectMatchListTmp[j]!=undefined 
						&& data.trackingNum==addShipDepart.selectMatchListTmp[j].trackingNum){
					addShipDepart.selectMatchListTmp.splice(j,1);
				}
			}
			
		},
		
		
		next:function(n){
    		if(n==1){
    			if(addShipDepart.selectMatchListTmp.length<=0){
    				 commonService.alert("请选择配载的运单!");
    	    		  return false ;
    			}
    			var tmpArrayList = [];
    			var boolIs = false;
    			for(var i=0;i<addShipDepart.selectMatchListTmp.length;i++){
    				var bool=false;
    				var destCity = addShipDepart.selectMatchListTmp[i].distributionOrgName;
    				addShipDepart.selectMatchListTmp[i].goodsNames=addShipDepart.selectMatchListTmp[i].products;
					tmpArrayList.push(destCity);
					for(var j =0;j<tmpArrayList.length;j++){
						if(tmpArrayList[j]!=destCity){
							bool = true;
							break;
						}
					}
					if(bool){
						boolIs = true;
						break;
					}
    			}
    			if(boolIs){
    				commonService.confirm("选择的配载列表中有不同配送网点，是否继续配载？",function(){
    					$scope.openNext(false);
    				});
    			}else{
    				$scope.openNext(false);
    			}
    		}else{
    			$scope.openNext(true);
    		}
    	},
    	openNext:function(obj){
    		if(obj){
    			document.getElementById("add_id2").style.display="none";
    			document.getElementById("add_id1").style.display="block";
    		}else{
    			document.getElementById("add_id2").style.display="block";
    			document.getElementById("add_id1").style.display="none";
    		}
    		
    		$scope.statisticsMatchSel();
    		
    		setContentHegthDelay();
    	},
    	queryDispatchOrg:function(){
    		/**查询短驳可到达网点*/
			if(isShort == 1 || isShort == "1"){
				//短驳查询短驳线路
				var url = "routeBO.ajax?cmd=queryRoateRutingIsShort";
				commonService.postUrl(url,{"dd":"1"},function(data){
					if(data!=null && data!=undefined && data!=""){
						if(data!=null && data != undefined){

							var ds = new Array();
							for(var i = 0;i<data.routeList.length ;i++){
								var obj = {};
								obj.endOrgId = data.routeList[i].endOrgId;
								obj.endOrgName =  data.routeList[i].endOrgName;
								ds.push(obj);
							}
							$scope.dispatchOrg = ds;
							
						}
		 	    	}
				});
				
			}else{
				commonService.postUrl("routeBO.ajax?cmd=getCurrRoute&v=1","",function(data){
					if(data != null && data != undefined && data != ""){
						$scope.dispatchOrg = data.items;
					}
				});
			}
			
		},
		selectDispatchOrg:function(stevedoringPayDot,freightPayDot){
			var descOrgId =  $scope.form.descOrgId;
			$scope.dispatchOrgList=[];
			$scope.form.stevedoringPayDot="-1";
			$scope.form.freightPayDot="-1";
			if(stevedoringPayDot!=null && stevedoringPayDot!=undefined && stevedoringPayDot!=""){
				$scope.form.stevedoringPayDot=stevedoringPayDot;
			}
			if(freightPayDot!=null && freightPayDot!=undefined && freightPayDot!=""){
				$scope.form.freightPayDot=freightPayDot;
			}
			if(descOrgId!=null && descOrgId!=undefined && descOrgId>0){
				commonService.postUrl("organizationBO.ajax?cmd=getOrganization",{"orgId":descOrgId},function(data){
					if(data != null && data != undefined && data != ""){
						$scope.form.destOrgPhone=data.orgPrincipalPhone;
						$scope.form.destOrgAdder=data.departmentAddress;
						$scope.form.descOrgIdName=data.orgName;
						var obj = new Object();
						obj.endOrgId=userInfo.orgId;
						obj.endOwnerName=userInfo.orgName;
						$scope.dispatchOrgList.push(obj);
						var obj1 = new Object();
						obj1.endOrgId=data.orgId;
						obj1.endOwnerName=data.orgName;
						$scope.dispatchOrgList.push(obj1);
					}
				});
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
			$scope.showPcum = true;
		},
		clearSelect:function(){
			
			if($scope.selected.length>0){
				
				for(var i=0;i<$scope.selected.length;i++){
					var trackingNumTemp=$scope.selected[i];
					//把右边的选中的数据删除掉，删除的是展示的数据
					for(var j=0;j<$scope.selectMatchList.length;j++){
						if($scope.selectMatchList[j]!=null && $scope.selectMatchList[j]!=undefined 
								&& trackingNumTemp==$scope.selectMatchList[j].trackingNum){
							$scope.selectMatchList.splice(j,1);
						}
					}
					
					//把右边的选中的数据删除掉，删除全部的数据
					for(var j=0;j<addShipDepart.selectMatchListTmp.length;j++){
						if(addShipDepart.selectMatchListTmp[j]!=null && addShipDepart.selectMatchListTmp[j]!=undefined 
								&& trackingNumTemp==addShipDepart.selectMatchListTmp[j].trackingNum){
							addShipDepart.selectMatchListTmp.splice(j,1);
						}
					}
				}
				
				$scope.leftSumFn($scope.matchList);
				$scope.rigthSumFn($scope.selectMatchList);
				$scope.statisticsMatchSel();
				
			}else{
				commonService.alert("请选择要移除配载的运单!");
			}

		},
		doSave:function(){
			$scope.form.isShort = isShort;
			if($scope.form.descOrgId==null||$scope.form.descOrgId==undefined || $scope.form.descOrgId=='' || $scope.form.descOrgId<=0){
				commonService.alert("请选择到达网点!");
				return false ;
			}
			if($scope.form.shipName==null||$scope.form.shipName==undefined || $scope.form.shipName==''){
				commonService.alert("请填写船只名称!");
				return false ;
			}
	        if($scope.form.freight!=null && $scope.form.freight!=undefined && Number($scope.form.freight)>0){
	        	if($scope.form.freightPayDot==null || $scope.form.freightPayDot==undefined || $scope.form.freightPayDot=='' || $scope.form.freightPayDot<=0){
	    			commonService.alert("请选择运费的付款方!");
	    			return false ;
	    		}

	        	if($scope.form.payStateFlag){
	        		$scope.form.payState = 1; //未付
	        	}else{
	        		$scope.form.payState = 2; //已付
	        	}
				$scope.form.freightFee=$scope.form.freight*100;
			}
			if($scope.form.stevedoring!=null && $scope.form.stevedoring!=undefined && Number($scope.form.stevedoring)>0){
				if($scope.form.stevedoringPayDot==null || $scope.form.stevedoringPayDot==undefined || $scope.form.stevedoringPayDot=='' || $scope.form.stevedoringPayDot<=0){
					commonService.alert("请选择装卸费的付款方!");
					return false ;
				}

				if($scope.form.stevedoringPayStateFlag){
					$scope.form.stevedoringPayState = 1;//未付
				}else{
					$scope.form.stevedoringPayState = 2;//已付
				}
				$scope.form.stevedoringFee=$scope.form.stevedoring*100;
			}
			if(addShipDepart.selectMatchListTmp.length==0){
				commonService.alert("请选择待发货订单到配载列表!");
				return false ;
			}
			var orderId='';
	        for(var i=0;i<addShipDepart.selectMatchListTmp.length;i++){
	        	if(i==0){
	        		orderId=addShipDepart.selectMatchListTmp[i].orderId;
	        	}else{
	        		orderId=orderId+","+addShipDepart.selectMatchListTmp[i].orderId;
	        	}
	        }
			$scope.form.orderId=orderId;
			$scope.form.freightCollectFee=$scope.freightCollect*100;
			$scope.form.collectingMoneyFee=$scope.collectingMoney*100;
			var tips="运输船只名称为["+$scope.form.shipName+"]，柜号为["+$scope.form.cntrNo
						+"],封条号为["+$scope.form.sealNo+"],运载工具牌号为["+$scope.form.carrierNo+"]，当前配置信息中，货物重量、体积为["+($scope.actualWeight/1000).toFixed(2)+"吨,"+$scope.actualVolume.toFixed(2)+"立方]，";
			tips=tips+"确定要继续 保存配载信息？";
			commonService.confirm(tips,function(){
				var param = $scope.form;
				delete param.list;
				var url = "ordShippingInfoBO.ajax?cmd=doSave";
				commonService.postUrl(url,param,function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
						//如果是修改就不清空。
						var batchNum=getQueryString("batchNum");
			    		if(batchNum!=null && batchNum!=undefined && batchNum!=''){
			    			commonService.alert("操作成功!",function(){
			    				commonService.closeToParentTab(false);
			    			});
			    			return false;
			    		}
			    		$scope.form={};
			    		$scope.form.batchNumAlias="";
						$scope.form.descOrgId="-1";
						$scope.form.descOrgIdName="";
						$scope.form.destOrgPhone="";
						$scope.form.destOrgAdder="";
						$scope.form.weight= '';
						$scope.form.volume= '';
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
						addShipDepart.selectMatchListTmp=[];
//						$scope.diy.loadData(addShipDepart.selectMatchListTmp);
						
						$scope.form.transportContract ="";
						commonService.confirm("操作成功，是否继续配载？",function(){
							addShipDepart.isQuery=false;
							$scope.doQuery();
							$scope.next(2);
						},function(){
							commonService.closeToParentTab(true);
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
    			var url="ordShippingInfoBO.ajax?cmd=getByBacthInfo";
    			commonService.postUrl(url,queryString,function(data){
    				if(data==null || data==undefined){
    					return false;
    				}
    				for (var i = 0; i < data.list.length; i++) {
    					$scope.selectMatchList.push(data.list[i]);
    					addShipDepart.selectMatchListTmp.push(data.list[i]);
    					$scope.collectingMoney=data.list[i].collectingMoney;
    					$scope.freightCollect=data.list[i].freightCollect;
    				}
    				display();
    				/**拷贝配载的数据**/
    				$scope.form=data;
    				$scope.form.weight=data.weight;
    				$scope.form.batchNum=data.batchNum;
    				$scope.form.volume=data.volume;
    				$scope.form.batchNum=data.batchNum;
    				$scope.form.currOrgId=data.sourceOrgId;
    				$scope.form.currOrgName=data.sourceOrgIdName;
    				$scope.form.descOrgId=data.descOrgId;
    				if(data.freight>-1){
    					$scope.form.freight=data.freight/100;
    				}
    				$scope.form.freightPayDot = data.freightPayDot;
    				$scope.form.payState = data.payState;
    				if(data.stevedoring>-1){
    					$scope.form.stevedoring=data.stevedoring/100;
    				}
    				//1未付、2已付
    				if(data.payState == 1 || data.payState == "1"){
    					$scope.form.payStateFlag = true;
    				}else{
    					$scope.form.payStateFlag = false;
    				}
    				$scope.form.stevedoringPayDot = data.stevedoringPayDot; //TODO
    				$scope.form.stevedoringPayState = data.stevedoringPayState;
    				
    				//1未付、2已付
    				if(data.stevedoringPayState == 1 || data.stevedoringPayState =="1"){
    					$scope.form.stevedoringPayStateFlag = true;
    				}else{
    					$scope.form.stevedoringPayStateFlag = false;
    				}
    		 
    				$scope.form.remarks=data.remarks;
    				$scope.form.shipDate=data.shipDateStr;
    				$scope.selectDispatchOrg(data.stevedoringPayDot,data.freightPayDot);
    				$scope.statisticsMatchSel();
    				setContentHegthDelay();
    				
    				$scope.leftSumFn($scope.matchList);
    				$scope.rigthSumFn($scope.selectMatchList);
    			});
    		}
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
				for(var j=0;j<$scope.orderInfoList.length;j++){
					value.push($scope.orderInfoList[j]);
				}
			}
			value.push(data);
			$scope.orderInfoList= value;
		},
		
		updateSelection:function($event,id){
			id=""+id;
			var checkbox = document.getElementById("checkId"+id);
			if(checkbox == undefined){
				return ;
			}
        	var action = (checkbox.checked?'remove':'add');
			
			//全选的操作
			if(id!=undefined && id=="-1"){
				
				if(action == 'add' && $scope.selected.indexOf(id) == -1){
					$scope.selectAllCheckbox();
		         }
		         if(action == 'remove'){
		        	 $scope.clearAllCheckbox();
		         }
				
			}else{
				if(action == 'add'){
					if($scope.selected.indexOf(id) == -1){
						$scope.selected.push(id);
					}
		            checkbox.checked=true;
		         }
		         if(action == 'remove' ){
		        	 if($scope.selected.indexOf(id)!=-1){
		        		 var idx = $scope.selected.indexOf(id);
			             $scope.selected.splice(idx,1);
		        	 }
		            
		             checkbox.checked=false;
		         }
			}
			
		},
		//清除所有的checkbox
		clearAllCheckbox:function(){
			var checkbox = document.getElementsByName("checkName");
			for(var index in checkbox){
				checkbox[index].checked=false;
			}
			$scope.selected=[];
		},
		
		//清除所有的checkbox
		selectAllCheckbox:function(){
			$scope.selected=[];
			var checkbox = document.getElementsByName("checkName");
			for(var index in checkbox){
				checkbox[index].checked=true;
				if(checkbox[index].value!=undefined && checkbox[index].value!=""){
					$scope.selected.push(checkbox[index].value);
				}
			}
		},
		close:function(){
    		commonService.closeToParentTab(true);
    	},
    	changeDescOrg:function(descOrgId){
    		addShipDepart.params.descOrgId=descOrgId;
    		
    		addShipDepart.form.descOrgId=descOrgId;
    		addShipDepart.doQuery();
    		$scope.list=[];
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
    	setTrackingNum:function(ev){
    		if (ev.keyCode !== 13) return;
    		var selectObj = $scope.getSelectDataByTrackingNum($scope.trackingNum);
    		if(selectObj==undefined){
    			commonService.alert("运单号不存在，请核实后扫描!");
    			$scope.trackingNum = "";
    			jQuery("#_trackingNum").focus();
    			return;
    		}
    		
    		
    		$scope.leftBtselect(selectObj);
    		
    		$scope.leftSumFn($scope.matchList);
			$scope.rigthSumFn($scope.selectMatchList);
    		
			$scope.trackingNum = "";
    		jQuery("#_trackingNum").focus();
    	},
		//获取选中的数据
		getSelectDataByTrackingNum:function(trackingNum){
			var selectArray=new Array();
			var list=$scope.matchList;
			if(list!=null && list!=undefined && list.length>0){
				for(var index in list){
					if(list[index]!=null && list[index]!=undefined){
						if(list[index].trackingNum == trackingNum){
							return list[index];
						}
					}
    			}
			}
		}
	};
	
	addShipDepart.init();
}]);



