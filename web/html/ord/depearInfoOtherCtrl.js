        var departOtherApp = angular.module("departOtherApp", ['commonApp']);
        departOtherApp.controller("departOtherCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
        	var exportExcel = false;
        	var depart={
        			init:function(){
        				$scope.printTotalInfo = {// 打印合计信息
        					number: 0,
        					weight: 0,
        					volume: 0,
        					consigneePay: 0,// 发货人付
        					freightCollect: 0,// 到付
        					collectingMoney: 0,// 代收款
        					discount: 0// 回扣
        				};
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
        			    $scope.printOperateList=this.printOperateList;
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
        				var url = "orderInfoBO.ajax?cmd=getDataByBacthInfoOther";
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
        				window.open("/ord/ordBillingDetail.html?view=1&trackingNum="+trackingNum+"&type=3");
        			},
        			printTable: function(){
        				$scope.printTime = new Date();
        				printTableInfo("printDiv", "众邦物流_发车到货_送货详情");
        			},
        			printOperateList: function(){
        				$scope.printTime = new Date();
        				printTableInfo("printOperateListDiv", "众邦物流_发车到货_送货详情");
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
                		var url="orderInfoBO.ajax?cmd=getByBacthInfoOther";
						commonService.postUrl(url,queryString,function(data){
							var collectingMoney=0;
							var freightCollect=0;
							// add by chenjun Start
							$scope.printTotalInfo.number = data.list.length;
	//						console.log(data);
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
							}
							// add by chenjun End
							
							if (undefined != $scope.printTotalInfo.weight)
								$scope.printTotalInfo.weight = $scope.printTotalInfo.weight.toFixed(2);
							if (undefined != $scope.printTotalInfo.volume) {
								$scope.printTotalInfo.volume = $scope.printTotalInfo.volume.toFixed(2);
							}
							
							$scope.freightCollect=freightCollect;
							$scope.collectingMoney=collectingMoney;
							$scope.departDetail= data;
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
//            			if(document.getElementById("checkbox"+orderId).checked && document.getElementById("checkbox"+orderId) != undefined){
//            				document.getElementById("checkbox"+orderId).checked=false;
//            			}else{
//            				document.getElementById("checkbox"+orderId).checked=true;
//            			}
            		},
            		/**到货确认*/
            		confirmGoodVehi:function(){
            			var orderId='';
            			if($("input[name='check']:checked").length==0){
            				commonService.alert("请至少选择一条送货信息!");
            				return false;
            			}
            			var flag=false;
               			$("input[name='check']:checked").each(function(){
            				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
            					var data=eval("("+$(this).val()+")");
            					if(data.orderState!=4&&data.orderState!=5){//4 待到货状态
            						flag=true;
            						commonService.alert("订单["+data.orderId+"]状态为"+data.orderStateName+",不可以操作[到货确认]!");
            						return false;
            					}
            					orderId=orderId+data.orderId+',';
            				}
            			});
               			if(flag){
            				return false;
            			}
               			var queryString={"orderId":orderId,"batchNum":$scope.departDetail.batchNum};
                		var url="orderInfoBO.ajax?cmd=ordArriveGood";
						commonService.postUrl(url,queryString,function(data){
							commonService.alert("操作成功!");
							depart.toView($scope.departDetail.batchNum);
						});
            		}
        	};
                	depart.init();
        }]);
 