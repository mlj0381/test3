var matchManageApp = angular.module("matchManageApp", ['commonApp']);
matchManageApp.controller("matchManageCtrl", ["$scope","commonService","$timeout","$compile",function($scope,commonService,$timeout,$compile) {
	var matchManage={
		init:function(){
			this.bindEvent();
			this.doQuery();
			$scope.isAdd=true;
		},
		bindEvent:function(){
			$scope.param = this.param;
			$scope.query = this.query;
			$scope.doQuery = this.doQuery;
			$scope.doQuerySimilar = this.doQuerySimilar;
		    $scope.selectAll = this.selectAll;
		    $scope.selectOne = this.selectOne;
		    $scope.fixNumber=this.fixNumber;
		    $scope.modify=this.modify;
		    $scope.doSave=this.doSave;
		    $scope.modifyCrefit=this.modifyCrefit;
		    $scope.cancer=this.cancer;
		    $scope.saveCrefit=this.saveCrefit;
		    $scope.addTable=this.addTable;
		    $scope.deleteTable=this.deleteTable;
		    $scope.doSaveCredit=this.doSaveCredit;
		    $scope.piePic=this.piePic;
		},
		params:{
		},
		query:{
		},
		piePic:function(data){
					Highcharts.getOptions().colors = Highcharts.map(Highcharts.getOptions().colors, function(color) {
					    return {
					        radialGradient: { cx: 0.5, cy: 0.3, r: 0.7 },
					        stops: [
					            [0, color],
					            [1, Highcharts.Color(color).brighten(-0.5).get('rgb')] // darken
					        ]
					    };
					});
					    $('#container').highcharts({
					        chart: {
					            plotBackgroundColor: null,
					            plotBorderWidth: null,
					            plotShadow: false
					        },
					        title: {
					            text: '匹配项权重占比饼图'
					        },
					        tooltip: {
					    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
					        },
					        plotOptions: {
					            pie: {
					                allowPointSelect: true,
					                cursor: 'pointer',
					                dataLabels: {
					                    enabled: true,
					                    color: '#000000',
					                    connectorColor: '#000000',
					                    formatter: function() {
					                        return '<b>'+ this.point.name +'</b>: '+ this.percentage +' %';
					                    }
					                },
					                events: {
										click: function (event) {// click事件
											var pointUserOptions = event.point.options;
											$scope.color=pointUserOptions.color;
											if(pointUserOptions != undefined ){
												$scope.similarGroup=pointUserOptions.groupName;
												$scope.name=pointUserOptions.name;
												matchManage.doQuerySimilar(pointUserOptions.groupName);
											}
										}
									}
					            }
					        },
					        series: [{
					            type: 'pie',
					            name: '百分比 ',
					            data: data
					        }]
					    });
				},
		addTable:function(id){
			if($scope.matchSimilar.items.length>=5){
				commonService.alert("最多允许5条数据!");
				return false;
			}
			var table=document.getElementById(id);
			var newTr = table.insertRow(0);
			//添加两列
			var newTd0 = newTr.insertCell();
			newTd0.className="td";
			var input0=document.createElement("input");
			input0.setAttribute("id","min");
			input0.className="input_dhl";
			input0.setAttribute("value",$scope.matchSimilar.items[$scope.matchSimilar.items.length-1].max);
			var newTd1 = newTr.insertCell();
			newTd1.className="td";
			var input1=document.createElement("input");
			input1.setAttribute("id","max");
			input1.className="input_dhl";
			var newTd2 = newTr.insertCell();
			newTd2.className="td";
			var input2=document.createElement("input");
			input2.className="input_dhl";
			input2.setAttribute("id","score");
			var newTd3 = newTr.insertCell();
			newTd3.className="td";
			newTd3.innerHTML="<a   class=\"dhl_tjgd mr10\" ng-click=\"doSaveCredit();\" href=\"javascript:void(0)\" >保存</a>" +
			"<a   class=\"dhl_tjgd mr10\" ng-click=\"deleteTable('table');\" href=\"javascript:void(0)\" >取消</a>";
			newTd0.appendChild(input0);
			newTd1.appendChild(input1);
			newTd2.appendChild(input2);
			//newTd3.appendChild(a);
			$("#table tbody tr:odd").css("background-color", "#f9fbfd");   
			$("#table tbody tr:even").css("background-color","#fff");   
			$("#table tbody tr:odd").addClass("odd");
			$("#table tbody tr:even").addClass("even");
			$scope.isAdd=false;
			$compile(newTd3)($scope);
		},
		deleteTable:function(id){
			var table=document.getElementById(id);
			table.deleteRow(0);
			$scope.isAdd=true;
		},
		fixNumber:function (prodId){
			var value=document.getElementById(prodId).value;
			value = value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
			value = value.replace(/^\./g,"");  //验证第一个字符是数字而不是. 
			value = value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.   
			value = value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
			document.getElementById(prodId).value=value;
		},
		selectAll : function() {
			var checkbox = document.getElementsByName("check-1");
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
		doQuery:function(){
			var param = {};
			var url = "matchWeightBO.ajax?cmd=doQueryItem";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
						$scope.matchInfo=data;
						matchManage.doQuerySimilar(data.items[0].similarGroup);
						$scope.name=data.items[0].chainName;
						$scope.dataArray=new Array();
						$scope.color="#"+data.items[0].color;
						for(var i=0;i<data.items.length;i++){
							if(data.items[i].percent!=null&&data.items[i].percent!=undefined&&data.items[i].percent!=''&&parseInt(data.items[i].percent)>0){
								var dataPie={"name":data.items[i].chainName,"y":data.items[i].percent,"sliced":true,
								"selected":false,"groupName":data.items[i].similarGroup,"color":"#"+data.items[i].color};
								$scope.dataArray.push(dataPie);
							}
						}
						$scope.piePic($scope.dataArray);
//						var head = document.getElementsByTagName('head')[0];
//						var script = document.createElement('script');
//				        script.src = "../../js/jscolor-2.0.4/jscolor.min.js";
//				        script.type = 'text/javascript';
//				        head.appendChild(script);
	 	    	}
			});
		},
		doQuerySimilar:function(similarGroup){
			$scope.id=null;
			$scope.similarGroup=similarGroup;
			var param = {"similarGroup":similarGroup};
			var url = "matchWeightBO.ajax?cmd=doQueryChain";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
						$scope.matchSimilar=data;
	 	    	}
			});
		},
		doSaveCredit:function(){
			var min=document.getElementById("min").value;
			var max=document.getElementById("max").value;
			var score=document.getElementById("score").value;
			if(min==''||min==null||min==undefined){
				commonService.alert("最小值不能为空!");
				return false;
			}
			if(max==''||max==null||max==undefined){
				commonService.alert("最大值不能为空!");
				return false;
			}
			if(score==''||score==null||score==undefined){
				commonService.alert("信用分不能为空!");
				return false;
			}
			var param = {"similarGroup":$scope.similarGroup,"min":min,"max":max,"score":score};
			var url = "matchWeightBO.ajax?cmd=doSave";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
					commonService.alert("操作成功!");
					matchManage.deleteTable("table");
					matchManage.doQuerySimilar($scope.similarGroup);
	 	    	}
			});
		},
		saveCrefit:function(id){
			var min=document.getElementById(id+"_input_min").value;
			var max=document.getElementById(id+"_input_max").value;
			var score=document.getElementById(id+"_input_score").value;
			if(min==''||min==null||min==undefined){
				commonService.alert("最小值不能为空!");
				return false;
			}
			if(max==''||max==null||max==undefined){
				commonService.alert("最大值不能为空!");
				return false;
			}
			if(score==''||score==null||score==undefined){
				commonService.alert("信用分不能为空!");
				return false;
			}
			var param = {"similarGroup":$scope.similarGroup,"id":id,"min":min,"max":max,"score":score};
			var url = "matchWeightBO.ajax?cmd=doSave";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
					commonService.alert("操作成功!");
					$scope.id=null;
					matchManage.doQuerySimilar($scope.similarGroup);
	 	    	}
			});
		},
		modifyCrefit:function(id){
			$scope.id=id;
		},
		selectOne : function(taskId){
			if(document.getElementById("checkbox"+taskId).checked && document.getElementById("checkbox"+taskId) != undefined){
				document.getElementById("checkbox"+taskId).checked=false;
			}else{
				document.getElementById("checkbox"+taskId).checked=true;
			}
		},
		modify:function(){
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条数据!");
				return false;
			}
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					document.getElementById(data.id+"label").style.display='none';
					document.getElementById(data.id+"Input").style.display='block';
					//document.getElementById(data.id+"_label_color").style.display='none';
					//var colorInput=document.getElementById(data.id+"_input_color");
					//colorInput.style.display='block';
					}
			});
		},
		cancer:function(){
			$scope.id=null;
		},
		doSave:function(){
			var percentMax=0;
			var id="";
			var percent="";
			var color="";
			for (var i=0;i<$scope.matchInfo.items.length;i++){
				var percentValue=document.getElementById($scope.matchInfo.items[i].id+"Input").value;
				if(percentValue==''||percentValue==null||percentValue==undefined){
					continue;
				}
				if(document.getElementById($scope.matchInfo.items[i].id+"Input").style.display=='block'){
					percent=percent+percentValue+",";
					id=id+$scope.matchInfo.items[i].id+",";
					
				}
//				var colorValue=document.getElementById($scope.matchInfo.items[i].id+"_input_color").value;
//				if(document.getElementById($scope.matchInfo.items[i].id+"_input_color").style.display=='block'){
//					color=color+colorValue+","
//				}
				percentMax=percentMax+parseInt(percentValue);
			}
			if(percentMax<100||percentMax>100){
				commonService.alert("抱歉!总占比不能低于100%或者大于100%");
				return false;
			}
			var param={"id":id,"percent":percent,"color":color};
			var url = "matchWeightBO.ajax?cmd=modifyPercent";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
					commonService.alert("修改成功!");
					$scope.doQuery();
	 	    	}
			});
		},
		
	};
	matchManage.init();
}]);
