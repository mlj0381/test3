        var departApp = angular.module("departApp", ['commonApp']);
        departApp.controller("departCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
        	var exportExcel = false;
        	var depart={
        			init:function(){
//        				$scope.printTotalInfo = {// 打印合计信息
//        					number: 0,
//        					weight: 0,
//        					volume: 0,
//        					consigneePay: 0,// 发货人付
//        					freightCollect: 0,// 到付
//        					collectingMoney: 0,// 代收款
//        					discount: 0// 回扣
//        				};
        				this.toView();
        				this.bindEvent();
        				$scope.freightCollect=0;
						$scope.collectingMoney=0;
						$scope.flag=getQueryString("flag");
						$scope.printTime = new Date();
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
        			},
        			//导出方法
        			exportOrd : function(){
        				if(exportExcel){
        					return false;
        				}
        				var batchNum = $scope.departDetail.batchNum;
//        				console.log(batchNum);
        				var url = "orderInfoBO.ajax?cmd=getDataByBacthInfo";
        				var toUrl = signUrl(url+"&batchNum="+batchNum+"&_ALLEXPORT=1");
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
        			toDetailAllInfo: function(trackingNum){
        				commonService.openTab(trackingNum,"运单详情","/ord/ordBillingDetail.html?isShowReturn=false&view=1&type=3&trackingNum="+trackingNum+"&ver=${ver}");
        			},
        			printTable: function(){
        				$scope.printTime = new Date();
        				printTableInfo("printDiv", "联运会_发车到货_配载详情");
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
                		var url="orderInfoBO.ajax?cmd=getByBacthInfo";
						commonService.postUrl(url,queryString,function(data){
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
	            					installCosts: 0// 配安费
	            			};
							var collectingMoney=0;
							var freightCollect=0;
							// add by chenjun Start
							$scope.printTotalInfo.number = data.list.length;
							for (var i = 0; i < data.list.length; i++) {
								var o = data.list[i];
								
								if (o.consigneeName == undefined || '' == o.consigneeName) {
									o.consigneeName = o.consigneeLinkmanName;
								}
								collectingMoney = o.collectingMoney;
								freightCollect = o.freightCollect;
								$scope.printTotalInfo.weight += $scope.customParseDouble(o.weight);
								$scope.printTotalInfo.volume += $scope.customParseDouble(o.volume);
								
								// 发货方付
								var consigneePay = (o.receiptPayment != "" ? o.receiptPayment / 100 : 0) 
									+ (o.cashPayment != "" ? o.cashPayment / 100 : 0)
									+ (o.monthlyPayment != "" ? o.monthlyPayment / 100 : 0);
								$scope.printTotalInfo.consigneePay += consigneePay;
								// 到付
								if(o.freightCollect != undefined && o.freightCollect != "")
									$scope.printTotalInfo.freightCollect += o.freightCollect / 100;
								// 代收款
								if(o.collectingMoney != undefined && o.collectingMoney != "")
									$scope.printTotalInfo.collectingMoney += o.collectingMoney / 100;
								// 户口回扣
								if(o.discount != undefined && o.discount != "")
									$scope.printTotalInfo.discount += o.discount / 100;
								
								// 配安费
								if(o.installCosts != undefined && o.installCosts != "")
									$scope.printTotalInfo.installCosts += o.installCosts / 100;
								
								// 付款1费用
								if(o.cashMoney != undefined && o.cashMoney != "")
									$scope.printTotalInfo.cashMoney += o.cashMoney / 100;
								
								// 付款2费用
								if(o.cashMoney2 != undefined && o.cashMoney2 != "")
									$scope.printTotalInfo.cashMoney2 += o.cashMoney2 / 100;
								// 件数
								if(o.count != undefined && o.count != "")
									$scope.printTotalInfo.count += o.count;
							}
							// add by chenjun End
							$scope.freightCollect=freightCollect;
							$scope.collectingMoney=collectingMoney;
							$scope.departDetail= data;
							setContentHegthDelay();
//							if (undefined != $scope.printTotalInfo.weight)
//								$scope.printTotalInfo.weight = $scope.printTotalInfo.weight.toFixed(2);
//							if (undefined != $scope.printTotalInfo.volume) {
//								$scope.printTotalInfo.volume = $scope.printTotalInfo.volume.toFixed(2);
//							}
						});
                	},
                	/**全选*/
            		selectAll : function() {
            			var checkbox = document.getElementsByName("check");
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
            		/**选择一行**/
            		selectOne : function(orderId){
            			if(document.getElementById("checkbox"+orderId).checked && document.getElementById("checkbox"+orderId) != undefined){
            				document.getElementById("checkbox"+orderId).checked=false;
            			}else{
            				document.getElementById("checkbox"+orderId).checked=true;
            			}
            		},
            		/**到货确认*/
            		confirmGoodVehi:function(){
            			var orderIdArr=new Array();
            			if($("input[name='check']:checked").length==0){
            				commonService.alert("请至少选择一条配载信息!");
            				return false;
            			}
            			var flag=false;
               			$("input[name='check']:checked").each(function(){
            				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
            					var data=eval("("+$(this).val()+")");
            					if(data.orderState != 4){//4 待到货状态
            						flag=true;
            						commonService.alert("运单["+data.trackingNum+"]状态为已到货,不可以再次操作[到货确认]!");
            						return false;
            					}
            					orderIdArr.push(data.orderId);
            				}
            			});
               			if(flag){
            				return false;
            			}
               			var queryString={"orderId":orderIdArr.join(","),"batchNum":$scope.departDetail.batchNum};
                		var url="orderInfoBO.ajax?cmd=gxArriveGood";
						commonService.postUrl(url,queryString,function(data){
							commonService.alert("操作成功!");
							depart.toView($scope.departDetail.batchNum);
						});
            		}
        	};
                	depart.init();
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
