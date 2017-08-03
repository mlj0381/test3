        var shipDepeatInfoApp = angular.module("shipDepeatInfoApp", ['commonApp']);
        shipDepeatInfoApp.controller("shipDepeatInfoCtrl", ["$scope","commonService","$timeout","$sce",function($scope,commonService,$timeout,$sce) {
        	var exportExcel = false;
        	var shipDepeatInfo={
        			init:function(){
        				
        				this.bindEvent();
        				$scope.freightCollect=0;
						$scope.collectingMoney=0;
						$scope.flag=getQueryString("flag");
						$scope.printTime = new Date();
						$scope.companyName=userInfo.tenantName;
						this.getPrintTeantId();
        			},
        			printTotalInfo :  {
        				
        			},
        			
        			bindEvent:function(){
        			    $scope.toView=this.toView;
        			    $scope.close=this.close;
        			    $scope.selectAll=this.selectAll;
        			    $scope.selectOne=this.selectOne;
        			    $scope.confirmGoodVehi=this.confirmGoodVehi;
        			    $scope.printTable=this.printTable;
        			    $scope.toDetailAllInfo = this.toDetailAllInfo;
        			    $scope.customParseDouble = this.customParseDouble;
        			    $scope.exportOrd = this.exportOrd;
        			    $scope.getLableName = this.getLableName;
        			    $scope.getDealDatas = this.getDealDatas;
//        			    $scope.printHtmlTableDiv = this.printHtmlTableDiv;
        			    
        			},
        			//获取可打印表头和 页码的租户信息
        			getPrintTeantId: function(){
        				var showData={
        						maxPageSize : 28,
        						minPageSize : 24,
        						showLable :  false,
        				};
        				$scope.showData = {};
        				if(window.top.tenantConfig != undefined ){
        					var showDataA  =  window.top.tenantConfig.showDataFunction;
                            if(showDataA != undefined){
                            	showData = showDataA;
                            	if(showData.maxPageSize != null && showData.maxPageSize != "" && showData.maxPageSize != undefined ){
                					$scope.showData.maxPageSize = parseInt(showData.maxPageSize);
                				}
                				if(showData.minPageSize != null && showData.minPageSize != "" && showData.minPageSize != undefined ){
                					$scope.showData.minPageSize = parseInt(showData.minPageSize);
                				}
        					}
                            
        				}
        				$scope.showData = showData;
        				$scope.toView();
        			},
        			//导出方法
        			exportOrd : function(){
        				if(exportExcel){
        					return false;
        				}
        				setContentHeigth();
        				var batchNum = $scope.departDetail.batchNum;
//        				console.log(batchNum);
        				var url = "ordShippingInfoBO.ajax?cmd=getDataByBacthInfo";
        				var toUrl = signUrl(url+"&batchNum="+batchNum+"");
        				var iframe = document.createElement("iframe");
        			    iframe.id = "frameDownloading";
        			    iframe.src = toUrl;
        			    iframe.style.display = "none";
        			    document.body.appendChild(iframe);
        			    
        			    var selectExport = document.getElementById("selectExport");
        				exportExcel = true;
        				selectExport.innerHTML = "导出中。";
        				//导出倒计时
        				$timeout(function() {
        					exportExcel = false;
        				    selectExport.innerHTML = "导出";
        				},3000);
        				
        			},
        			toDetailAllInfo: function(d){
        				commonService.openTab(d.orderId,"运单详情","/ord/ordBillingDetail.html?view=1&orderId="+d.orderId+"&trackingNum="+d.trackingNum+"&type=3");
        			},
        			printTable: function(){
        				$scope.printTime = new Date();
        				printTableInfo("printDiv", "联运汇_发车到货_配载详情");
        			},
        			close:function(){
                		commonService.closeToParentTab(true);
                	},
                	customParseDouble: function(obj){
            			if (obj == undefined || obj == null) {
            				return 0;
            			}
            			var ret = Math.round(obj * 100) / 100;
            			if(isNaN(ret)){
            				return 0;
            			}
            			return ret;
            		},
                	toView:function(batchId){
                		$scope.printTotalInfo = {// 打印合计信息
            					number: 0,
            					weight: 0,
            					volume: 0,
            					consigneePay: 0,// 发货人付
            					freightCollect: 0,// 到付
            					collectingMoney: 0,// 代收款
            					discount: 0,// 回扣
            					count: 0,// 件数
            					freight: 0,// 运费
            					cashMoney: 0,// 费用2
            					cashMoney2: 0,// 费用1
            					installCosts: 0,// 配安费
            					pickingCosts: 0// 提货费
            			};
                		var batchNum='';
                		if(batchId!=null&&batchId!=undefined&&batchId!=''){
                			batchNum=batchId;
                		}else{
                			batchNum=getQueryString("batchNum");
                		}
                		if(batchNum==null||batchNum==undefined|| batchNum==''){
                			commonService.alert("批次号为空!");
                			return false;
                		}
                		var queryString="batchNum="+batchNum;
                		var url="ordShippingInfoBO.ajax?cmd=getByBacthInfo";
                		window.top.showLoad();
						commonService.postUrl(url,queryString,function(data){
							var collectingMoney=0;
							var freightCollect=0;
							// add by chenjun Start
							$scope.printTotalInfo.number = data.list.length;
							$scope.departDetail= data;
							var arrs = new Array();
							for (var i = 0; i < data.list.length; i++) {
								var o = data.list[i];
								
								if (o.consigneeName == undefined || '' == o.consigneeName) {
									o.consigneeName = o.consigneeLinkmanName;
								}
								if(o.collectingMoney != null && o.collectingMoney != undefined &&o.collectingMoney != ''){
									collectingMoney = collectingMoney + o.collectingMoney;
								}
								if(o.freightCollect != null && o.freightCollect != undefined &&o.freightCollect != ''){
									freightCollect = freightCollect+ o.freightCollect;
								}
								if(o.weight != null && o.weight != undefined &&o.weight != ''){
									$scope.printTotalInfo.weight += $scope.customParseDouble(o.weight);
								}
								
								if(o.volume != null && o.volume != undefined &&o.volume != ''){
									$scope.printTotalInfo.volume += $scope.customParseDouble(o.volume);
								}
								
								
								
								// 发货方付
								var consigneePay = (o.receiptPayment != "" ? o.receiptPayment / 100 : 0) 
									+ (o.cashPayment != "" ? o.cashPayment / 100 : 0)
									+ (o.monthlyPayment != "" ? o.monthlyPayment / 100 : 0);
								$scope.printTotalInfo.consigneePay += consigneePay;
								// 到付
								if(o.freightCollect != undefined && o.freightCollect != "" && o.freightCollect != null)
									$scope.printTotalInfo.freightCollect += o.freightCollect / 100;
								// 代收款
								if(o.collectingMoney != undefined && o.collectingMoney != "" && o.collectingMoney != null)
									$scope.printTotalInfo.collectingMoney += o.collectingMoney / 100;
								// 户口回扣
								if(o.discount != undefined && o.discount != "" && o.discount != null)
									$scope.printTotalInfo.discount += o.discount / 100;
								
								// 运费
								if(o.freight != undefined && o.freight != "" && o.freight != null)
									$scope.printTotalInfo.freight += o.freight / 100;
								
								// 配安费
								if(o.installCosts != undefined && o.installCosts != "" && o.installCosts != null)
									$scope.printTotalInfo.installCosts += o.installCosts / 100;
								
								// 付款1费用
								if(o.cashMoney != undefined && o.cashMoney != "" && o.cashMoney != null)
									$scope.printTotalInfo.cashMoney += o.cashMoney / 100;
								
								// 付款2费用
								if(o.cashMoney2 != undefined && o.cashMoney2 != "" && o.cashMoney2 != null)
									$scope.printTotalInfo.cashMoney2 += o.cashMoney2 / 100;
								// 件数
								if(o.count != undefined && o.count != "")
									$scope.printTotalInfo.count += o.count;
							     
								//
								// 提货费
								if(o.pickingCosts != undefined && o.pickingCosts != "" && o.pickingCosts != null)
									$scope.printTotalInfo.pickingCosts += o.pickingCosts  / 100;
								
								if($scope.departDetail.state == 4){
									o.stateNameAlias = "已到货";
								}
								if($scope.departDetail.state == 5){
									if(o.orderState == 4){
										o.stateNameAlias = "未到货";
									}else{
										o.stateNameAlias = "已到货";
									}
								}
								if($scope.departDetail.state != 4 && $scope.departDetail.state != 5){
									o.stateNameAlias = "未到货";
								}
								
								arrs.push(o);
							}
						
							
							$scope.freightCollect=freightCollect;
							$scope.collectingMoney=collectingMoney;
							$scope.pageData.clearAllCheckbox();
							$scope.pageData.loadData(arrs);
							window.top.hideLoad();
						    setContentHegthDelay();
						    
	                        // add by chenjun End
//							if (undefined != $scope.printTotalInfo.weight && null != $scope.printTotalInfo.weight && '' != $scope.printTotalInfo.weight)
//								$scope.printTotalInfo.weight = $scope.printTotalInfo.weight.toFixed(2);
//							if (undefined != $scope.printTotalInfo.volume && null != $scope.printTotalInfo.volume && '' != $scope.printTotalInfo.volume) {
//								$scope.printTotalInfo.volume = $scope.printTotalInfo.volume.toFixed(2);
//							}
						    
						    $scope.departDetail= data;
						    
						    $scope.departDetailPrint =  [];
						    var retData = $scope.getDealDatas(data.list);
						    $scope.departDetailPrint = retData.data;
						    var insertPage_ = retData.insertPage_;
						    var noNum = retData.noNum;
							    
						});
						$timeout(function(){
							//失败12秒隐藏
							window.top.hideLoad();
						},12000);
						
						
                	},
  
                	getDealDatas : function(data){
                		var arrs = new Array();
//                		var minPageSize = 24; //第1页 24条 数据          （运单数据） + 表头
//                		var maxPageSize = 28; //第2页 开始 28条 数据  （运单数据） + 表头 + 分页

                		var minPageSize = $scope.showData.minPageSize; //第1页 24条 数据          （运单数据） + 表头
                 		var maxPageSize = $scope.showData.maxPageSize; //第2页 开始 28条 数据  （运单数据） + 表头 + 分页
                		var length = data.length; //
                		var mlable = $scope.getLableName();
                		var insertPage_ = "insertPage_";
                		mlable.isTrue =  true;
                		if($scope.showData.showLable == true || $scope.showData.showLable == "true"  ){
                			arrs.push(mlable);
                		}
                		if(length <= minPageSize){
                			//只存在 1页数据
                			for(var i =0;i<data.length;i++){
                				var arr = data[i];
                				arr.isTrue =  true;
                				arr.indexNo = i + 1;
                				arrs.push(arr);
                			}
                		}else{
                    		//存在 2 以上的数据
                			var j = 0;
                			for(var i =0;i<data.length;i++){
                				var arr = data[i];
                				arr.isTrue =  true;
                				arr.indexNo = i + 1;
                				arrs.push(arr);
                				if(i == ((minPageSize - 1) + j * maxPageSize)){
                					j++;
                					//每页表头 
                					var m = {} ;
                					var ms = angular.toJson(mlable);
                					m = angular.fromJson(ms); 
                					m.noNum = insertPage_+j;
                					m.isTrue =  true;
                					var mPage = {} ;
                					mPage.isTrue =  false;
                					if($scope.showData.showLable == true || $scope.showData.showLable == "true"  ){
                						arrs.push(mPage);
                						arrs.push(m);
                					}
                				}
                			} 
                		}
                		var ret = {};
                		ret.noNum = j;
                		ret.insertPage_ = insertPage_;
                		ret.data = arrs;
                		return ret;
                	},
                	getLableName  : function(){
                		var codeNames = "序号,目的城市,运单号,发货方,货名,件数,重量,体积,收货人," +
                				           "收货人电话,运费,配安费,提货费,到付,代收货款,回扣,交货方式,付款方式,备注";
                		var codeValues = "indexNo,destCityName,trackingNum," +
                				             "consignorName,goodsNames,count,weight,volume," +
                				               "consigneeName,consigneeBill,freightString," +
                				                   "installCostsString,pickingCostsString,freightCollectString,collectingMoneyString," +
                				                     "discountString,deliveryTypeName,paymentTypeName,remarks";
                		
                		var ns = codeNames.split(",");
                		var cs = codeValues.split(",");
                		var m = "";
                		m = m + "{";
                		for(var i = 0;i<ns.length;i++){
                			 if(i != ns.length - 1){
                				 m = m + "\""+cs[i]+"\":\""+ns[i] +"\",";
                			 }else{
                				 m = m + "\""+cs[i]+"\":\""+ns[i] +"\"";
                			 }
                			
                		}
                		m = m + "} ";
//                		console.log(m);
                		return angular.fromJson(m); 
                	},
            		/**到货确认*/
            		confirmGoodVehi:function(){
            			var orderId='';
            			var flag=false;
            			var selectDatas =  $scope.pageData.getSelectData();
            			if(selectDatas.length == 0){
            				commonService.alert("请至少选择一条配载信息!");
            				return false;
            			}
            			for(var i = 0;i < selectDatas.length;i++){
            				var data = selectDatas[i];
            				if(data.orderState != 4){//4 待到货状态
        						flag=true;
        						commonService.alert("运单["+data.trackingNum+"]状态为已到货,不可以再次操作[到货确认]!");
        						return false;
        					 }
            				orderId=orderId + data.orderId+',';
            			}
               			if(flag){
            				return false;
            			}
               			var queryString={"orderId":orderId,"batchNum":$scope.departDetail.batchNum};
                		var url="ordShippingInfoBO.ajax?cmd=arriveGoods";
						commonService.postUrl(url,queryString,function(data){
							commonService.alert("操作成功!");
							shipDepeatInfo.toView($scope.departDetail.batchNum);
						});
            		}
        	};
        	shipDepeatInfo.init();
        }]);
      //get URL加密
        function signUrl(orgiUrl) {
        	var paramArray = new Array();
        	var name,value,paramStr; 
        	if (orgiUrl != undefined) {
        		 var url = orgiUrl.substring(orgiUrl.lastIndexOf("/")+1);
        		 if ((idx = url.indexOf("&")) > 0) {
        			 paramStr = url.substring(0, idx);
        			 var params = url.substring(idx+1).split("&");
        			 for (var i in params) {
        				 if (params[i].split("=")[1] !== "null" && params[i].split("=")[1] !== "") {
        					 paramArray.push(params[i]);
        				 }
        			 }
        		 } else {
        			 paramStr = url;
        		 }
        	}
            if (paramArray.length > 0)
            	paramStr += "&"+ paramArray.sort().join("&");
                paramStr += "&sign=" +md5(paramStr+getCookie("token"));
            return paramStr;
        }
