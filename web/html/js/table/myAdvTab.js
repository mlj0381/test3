document.write('<script src=\'/js/ngDraggable.js\'><\/script>');
var tableCommon = angular.module("myAdvTableCommon", ["ngDraggable"]);

/**
 * 表格标签
 * 
 * 对外提供的属性：
 *    @param head :传入头部的标签，格式为数组对象 [{},{}]
 *            对象支持的参数：
 *            	name 表头的名称
 *              code 列表返回的数据对应的字段的key值
 *              isSum 值为：true/false ，表示该列是否需要汇总
 *    @param  isSum 表示是否在列表底部显示汇总行
 *    @param  isFilter 表示是否在表格的头部下展示输入框，用于过滤表格数据
 *    @param  isShowSeq 表示是否在表格的第一列显示一个递增的序号
 *    @param  isShowCheck 表示是否显示checkbox的
 *    @param  isMultiSelect 表示checkbox 是支持单选，还是多选
 *    @param  id 表示每一行的数据的一个组件的字段key值
 *    @param  name 表示这个标签的对象，如果页面只有一个表格，可以不传入该值，默认值为“page”
 *    @param  isShowBt 表示是否显示前面的按钮 ,true 表示显示
 * 
 * 对外提供的方法：
 *    getSelected :返还所有选中的数据的主键
 *    getOneSelected: 如果选择的是单选的，可以直接调用该方法，返还一条数据的主键
 *    getSelectData: 返还选择的数据的全部数据内容
 *    
 *    downloadExcelFile:导出excel方法，请求的bo需要继承baseBo
 * 	  load  发送表格的请求，格式为对象
 *                url 请求的地址
				  params 请求的参数
 *    reload
 *    btclick  按钮的回调 方法，两个入参：第一个是选择的数据的id,第二个如果是等于－1表示是全选的按钮
 *    getFilterData 获取过滤后的数据
 * 
 **/
commonApp.directive('myAdvTable',['commonService','$filter','$timeout','$interval',function(commonService,$filter,$timeout,$interval){
	var myTable={};
	myTable.restrict="E";
	myTable.transclude=true;
	myTable.compile=function(element, attrs){
		var objName=attrs.name;
		var headList=angular.fromJson(attrs.head);
		var isSum=attrs.issum;
		var isFilter=attrs.isfilter;
		var isShowTotal=attrs.showtotal;
		var isShowSeq=attrs.isshowseq;//是否显示序号
		var filterStr="";//过滤的字符串
		var id=attrs.id;//每一行的数据的主键
		var isMultiSelect=attrs.ismultiselect;//单选框是否可以多选
		var isShowCheck=attrs.isshowcheck;//是否显示checkbox
		var doubleClickCallMethod = attrs.doubleclick; //双击调用方法
		var isAutoHide = attrs.isautohide; //是否随着列表增高自动加高
		var isShowBt=attrs.isshowbt;//是否在最前面显示＋按钮
		var btClickCallMethod=attrs.btclick;
		var isShowPage=attrs.isshowpage;//是否显示分页的样式
		var btCssType=attrs.btcsstype;//按钮的样式，true 表示是加号按钮，false表示是减号按钮
		var btTip=attrs.bttip;//
		var heightSize = "";
		var h2Size = "";
		var diytableName = attrs.tablename;
		if(isAutoHide == "false" || isAutoHide == false){
			heightSize = "";
//			h2Size = "min-height:220px";
		}else{
//			h2Size = "min-height:220px";
		}
		
		
		if(isShowPage==undefined){
			//如果这个值没有传，默认是需要显示分页
			isShowPage="true";
		}
		
		if(doubleClickCallMethod == undefined){
			doubleClickCallMethod = "doubleClickCallMethod";
		}
		isShowTotal=isShowTotal=="false"?false:true;
		if(objName==undefined){
			objName="page";
		}
		var tableWidth = attrs.width;
		if(tableWidth == undefined || tableWidth == null || tableWidth == ""){
			tableWidth = "";
		}
		//拼装过滤的字符串
		if(isFilter=="true"){
			for(var head in headList){
				filterStr+="|filter :{"+headList[head].code+":"+objName+".filterInput."+headList[head].code+"}";
			};
		}
		
		var template='<div style="position: relative;">'
					+'<div style=" '+heightSize+'; position: relative;">'
					+'<div class="table_height" style="/*min-height:270px;*/  '+h2Size+'; width:100%;overflow:auto;" >'
					+'<table class="search_lista" width="100%" border="0" cellspacing="0" cellpadding="0" style="width:'+tableWidth+';">'
					+' <thead class="fixed-thead" style="width:'+tableWidth+';">'
					+'<tr >'
					+'<th valign="top" width="25px" style="padding-left: 14px;" ng-show="'+objName+'.isShowBt==\'true\'" ng-click="'+objName+'.btClick($event,\'all\',-1)"><a ng-click="" style="cursor: pointer;margin-top: 5px;" class="icon" ng-class="{true:\'pcar_j\',false:\'pcar_m\'}['+objName+'.btCssType]"  tips="全部{{'+objName+'.btTip}}"></a></th>'
					+'   <th width="60" class="search_list_border" ng-if="'+objName+'.isShowCheck==\'true\'" ><div class="controls" ng-if="'+objName+'.isShowCheck==\'true\' && '+objName+'.isMultiSelect==\'true\'"><input id="'+objName+'.checkIdall" name="'+objName+'.checkName" class="none"  type="checkbox"><label for="checkbox" ng-click="'+objName+'.updateSelection($event,\'all\',-1)"></label></div></th>'
					
					+'   <th width="60" class="xlh" ng-if="'+objName+'.isShowSeq==\'true\'"><div style="font-family:宋体;font-size: 12px;font-weight: bold;">序号</div></th>'
					
					+'   <th style="text-align: left;" width="{{o.width == undefined ? 140 : o.width}}" ng-repeat="o in '+objName+'.headList" ng-click="'+objName+'.sort(o.code)" ><span style=" padding: 0 5px;">{{o.name}}<i ng-if="'+objName+'.orderFile==\'+\'+o.code"  class="list_top_tp"></i><i ng-if="'+objName+'.orderFile==\'-\'+o.code"  class="list_top_tpx"></i></span></th>'
					+'</tr>'
					
					+'<tr ng-if="'+objName+'.isFilter==\'true\'" class="ng-cloak" >'
					+'<th valign="top" width="25px" style="padding-left: 14px;" ng-if="'+objName+'.isShowBt==\'true\'"></th>'
					+'<th ng-if="'+objName+'.isShowCheck==\'true\'" width="60" class="search_list_border"></th>'
					+'<th ng-if="'+objName+'.isShowSeq==\'true\'" width="60" class="xlh" ></th>'
					+'<th width="{{t.width == undefined ? 140 : t.width}}" valign="top" ng-repeat="t in '+objName+'.headList" >'
					+'<input value=""  class="inp_sr"  type="text" ng-change="$parent.'+objName+'.changeData();" ng-model="$parent.'+objName+'.filterInput[t.code]" onclick="scrollTopTable()">'
					+'</th>'
					+' </tr>'
					
					+'</thead>'

					+'<tbody class="fixed-tbody" ng-class="{true:\'m_true\', false: \'m_false\'}['+objName+'.isFilter]" style="width:'+tableWidth+';">'
					//+'<tbody ng-class="fixed-tbody" style="width:'+tableWidth+';margin-bottom: 21px;margin-top:53px;">'
					
					
					+'<tr class="ng-cloak " ng-dblclick="'+doubleClickCallMethod+'(d)"  ng-class="{\'selected1\': '+objName+'.isSelected(d.'+id+')}" ng-repeat="d in '+objName+'.data.items '+filterStr+' | orderBy:'+objName+'.orderFile"  ng-click="'+objName+'.updateSelection($event,d.'+id+')" >'
					+'<td valign="top" width="25px" style="padding-left: 14px;" ng-if="'+objName+'.isShowBt==\'true\'" ng-click="'+objName+'.btClick($event,d.'+id+')"><a ng-click="" style="cursor: pointer;margin-top: 3px;" class="icon" ng-class="{\'true\':\'pcar_j icon\',\'false\':\'pcar_m icon\'}['+objName+'.btCssType]"  tips="{{'+objName+'.btTip}}"></a></td>'
					+'<td ng-if="'+objName+'.isShowCheck==\'true\'" width="60" class="search_list_border" ><div class="controls"><input  id="{{\''+objName+'.checkId\'+d.'+id+'}}" value="{{d.'+id+'}}" class="none"  type="checkbox" name="'+objName+'.checkName" ><label for="check-1"   ></label></div></td>'
					+'<td ng-if="'+objName+'.isShowSeq==\'true\'" width="60" class="xlh" ><div>{{$index+1}}</div></td>'
					+'<td width="{{t.width == undefined ? 140 : t.width}}" class="{{d.rowColor}}"  style="white-space: nowrap !important;word-break: keep-all;overflow: hidden;text-overflow: ellipsis;"   ng-repeat="t in '+objName+'.headList" >'
					+'<span ng-if="t.isEdit==undefined||t.isEdit==true">'
					+	'<span ng-if="t.number!=undefined"  style="width:{{t.width == undefined ? 140 : t.width}}px;white-space: nowrap !important;word-break: keep-all;overflow: hidden;text-overflow: ellipsis;display: block;text-align:left;padding: 0 5px;">{{(t.isEdit)}}{{$eval("d."+t.code) | filterString | number: t.number }}</span>'
					+	'<span ng-if="t.number==undefined"  style="width:{{t.width == undefined ? 140 : t.width}}px;white-space: nowrap !important;word-break: keep-all;overflow: hidden;text-overflow: ellipsis;display: block;text-align: left;padding: 0 5px;" title="{{$eval(\'d.\'+t.code)}}">{{(t.isEdit)}}{{$eval("d."+t.code)}}</span>'
					+'</span>'
					+'<span ng-if="t.isEdit!=undefined&&!(t.isEdit==true)">'
					+	'<input ng-if="t.number==undefined" class="form_term1"  title="{{$eval(\'d.\'+t.code)}}" value="{{$eval(\'d.\'+t.code)}}" onkeyup="this.value=this.value.replace(/^\\D*(\\d*(?:\\.\\d{0,2})?).*$/g, \'$1\');" ng-blur="'+objName+'.setTransferValue(t.code)" ng-model="d[t.code]"/>'
					+'</span>'
					+'</td>'
					+' </tr>'
//					+'<tr ng-if="'+objName+'.isSum==\'true\'" class="ng-cloak" >'
//					+'<td valign="top" width="25px" style="padding-left: 14px;" ng-if="'+objName+'.isShowBt==\'true\'" ></td>'
//					+'<td width="60" class="" ng-if="'+objName+'.isShowCheck==\'true\'"></td>'
//					+'<td ng-if="'+objName+'.isShowSeq==\'true\'" width="60" class="xlh" ></td>'
//					+'<td width="{{t.width == undefined ? 140 : t.width}}" ng-repeat="t in '+objName+'.headList" >'
//					+'<span class="text_list" ng-if="t.number!=undefined && t.isSum==\'true\'">{{'+objName+'.data.items '+filterStr+' |sumOfItems:t.code |number: t.number}}</span>'
//					+'<span class="text_list" ng-if="t.number==undefined && t.isSum==\'true\'">{{'+objName+'.data.items '+filterStr+' |sumOfItems:t.code }}</span>'
//					+'</td>'
//					+' </tr>'
					+'</tbody>'
					+'<tfoot ng-if="'+objName+'.isSum==\'true\'"  class="fixed-tfoot tfoot" style="width:'+tableWidth+';">'
					+'<tr class="ng-cloak" >'
					+'<td valign="top" width="25px" style="padding-left: 14px;" ng-if="'+objName+'.isShowBt==\'true\'" ></td>'
					+'<td width="60" class="" ng-if="'+objName+'.isShowCheck==\'true\'"></td>'
					+'<td ng-if="'+objName+'.isShowSeq==\'true\'" width="60" class="xlh" ></td>'
					+'<td width="{{t.width == undefined ? 140 : t.width}}" ng-repeat="t in '+objName+'.headList" >'
					+'<span class="text_list" ng-if="t.number!=undefined && t.isSum==\'true\'">{{'+objName+'.data.items '+filterStr+' |sumOfItems:t.code | filterString |number: t.number}}</span>'
					+'<span class="text_list" ng-if="t.number==undefined && t.isSum==\'true\'">{{'+objName+'.data.items '+filterStr+' |sumOfItems:t.code }}</span>'
					+'</td>'
					+' </tr>'
					+'</tfoot>'
					+'</table>'
					+' </div>'
					+' </div>'
					+'<div class="kaa ng-hide"  ng-show="'+objName+'.isLoad" ><img src="'+commonService.getRootPath()+'/image/$tenantId$/jdt.gif" class="image-border" width="100" height="100"/> </div>'
			+'<div ng-show="'+objName+'.total==0 && !'+objName+'.isLoad" class="search_page ng-hide" style=" text-align:center; line-height:50px; color:#ff7800;top:100px;">没有符合条件的数据'
			+'</div>'
			+'<div ng-if="'+objName+'.isShowPage==\'true\'"  class="chey_b_1 list_fye clearfix ">'
			+'<div class="chey_xs fl">'
			+'<span>显示</span><a ng-click="'+objName+'.changeRows(10);" href="javascript:void(0)" ng-class="{hover:'+objName+'.pageCount==10}" class="hover">10</a><a ng-click="'+objName+'.changeRows(20);" href="javascript:void(0)" ng-class="{hover:'+objName+'.pageCount==20}">20</a><a ng-click="'+objName+'.changeRows(50);" href="javascript:void(0)" ng-class="{hover:'+objName+'.pageCount==50}">50</a><span>条</span>共{{'+objName+'.total}}条数据'
			+'</div>'
			+' <ul class="pagination fr" ng-show="'+objName+'.total>0">'
			+'  <li ng-class="{active:obj.isChoose==true}" ng-repeat="obj in '+objName+'.pageList" ><a ng-if="obj.pageNum==-1" ng-click="'+objName+'.prePage();" title="" href="javascript:void(0)"><</a>'
			+'  <a ng-if="obj.pageNum>=1"  ng-click="'+objName+'.goPage(obj.pageNum);" href="javascript:void(0)">{{obj.pageNum}}</a>'
			+' <div ng-if="obj.isEllipsis==true" class="dhl_break">...</div>'
			+'  <a ng-if="obj.pageNum==-2" ng-click="'+objName+'.nextPage();" title="" href="javascript:void(0)">></a></li>'
			+' </ul>'
			+' </div>'
		+'</div>';
		element.html(template);
		
//		$timeout(function(){
//			console.info(window.top.getTablDiyData());
//			console.info(headList);
			if(diytableName != null && diytableName != undefined){
				var obj = window.top.getTablDiyData();
				if(obj!=null && obj != undefined && obj != '' && obj.tableDiy != null && obj.tableDiy != undefined && obj.tableDiy != ''){
					var titleStr = "";
					for(var i=0;i<obj.tableDiy.length;i++){
						if(diytableName == obj.tableDiy[i].tableName){
							titleStr = obj.tableDiy[i].title;
							break;
						}
					}
					var titles = titleStr.split(',');
					var newHeadList = new Array();
					for(var j=0;j<titles.length;j++){
						for(var h=0;h<headList.length;h++){
							if(titles[j] == headList[h].name){
								newHeadList.push(headList[h]);
								break;
							}
						}
					}
					headList = newHeadList;
					if(isFilter=="true"){
						filterStr = "";
						for(var head in headList){
							filterStr+="|filter :{"+headList[head].code+":"+objName+".filterInput."+headList[head].code+"}";
						}
						element.html(template);
					}
				}
			}
//		},2000);
		return function( $scope, element, attrs ) {
			var page={
	        		pageCount:10,//分页数据的大小
	        		pageNum:1,//当前的分页的第几页
	        		totalNum:1,//总的页数
	        		total:0,//总记录数
	        		params:{},//请求的参数
	        		isLoad:false,
	        		isShowTotal:isShowTotal,
	        		pageList:new Array(),
	        		headList:headList,//头部的标题
	        		isPage:true,//是否分页
	        		isSum:isSum,//是否需要显示汇总的行
	        		urlInfo:{},//发送的请求的信息
	        		data:{},//页面的加载的数据
	        		sumList:{},//汇总的数据
	        		isFilter:isFilter,//是否需要过滤数据
	        		filterInput:{},//保存哪些字段需要过滤
	        		orderFile:"",//排序的字段
	        		selected:new Array(),//checkbox选中的数据
	        		isMultiSelect:isMultiSelect,//checkbox 是否可以多选
	        		isShowCheck:isShowCheck,//是否显示checkbox
	        		isShowBt:isShowBt,
	        		isShowPage:isShowPage,
	        		btCssType:btCssType,
	        		btTip:btTip,
	        		id:id,
	        		totalValue:{
	        			"transferValue":0
	        		},
	        		rowColorCallback: undefined,
	        		isShowSeq:isShowSeq,//是否显示序号
	        		
	        		getFilterData:function(){
	        			if(isFilter=="true"){
	        				var originalData=this.data.items;
	        				for(var head in headList){
	        					var inputVl=$scope.$eval(objName+".filterInput."+headList[head].code);
	        					if(inputVl!=undefined){
	        						var filterObj={};
	        						filterObj[headList[head].code]=inputVl;
	        						originalData=$filter('filter')(originalData, filterObj);
	        					}
	        				};
	        				return originalData;
	        			}
	        		},
	        		isSelected:function(inputId){
	        			//判断输入的值是否已经选中了
	        			
	        			inputId=""+inputId;
	        			return this.selected.indexOf(inputId)==-1 ?false:true;
	        		},
	        		setTransferValue:function(code){
	        			var that = this;
	        			var data = that.data.items;
	        			var totalValue = 0;
	        			for(var i=0;i<data.length;i++){
	        				if(eval("data["+i+"]."+code) != null && eval("data["+i+"]."+code) != undefined ){
        						totalValue = totalValue + Number(eval("data["+i+"]."+code));
	        				}
	        			}
        				eval("that.totalValue."+code+"="+totalValue);
	        		},
	        		downloadExcelFile:function(){
	        			delete this.params.params._sum;
	        			var queryUrl=this.params.url;
	        			var queryBo=queryUrl.substr(0,queryUrl.indexOf(".ajax"));
	        			var queryCmd=queryUrl.substr(queryUrl.indexOf("cmd=")+4,queryUrl.length);
	        			
	        			var url = queryBo+".ajax?cmd=downloadExcelFile";
	        			
	        			var excelKeys=new Array();
	        			var excelLables=new Array();
	        			
	        			for(var index in this.headList){
	        				var el=this.headList[index];
	        				if(index == 0){
	        					//对于统计处理
	        					excelKeys.push(el.code+"*");
	        					excelLables.push(el.name);
	        				}else{
	        					excelLables.push(el.name);
		        				if(el.isSum){
		        					//对于统计处理
		        					excelKeys.push(el.code+"@");
		        				}else{
		        					excelKeys.push(el.code);
		        				}
	        				}
	        				
	        					
	        			}
	        			
	        			var queryParamStr="";
	        			for(var key in this.params.params){
	        				if(this.params.params[key]!=undefined){
	        					queryParamStr=queryParamStr+"&"+key+"="+this.params.params[key];
	        				}
	        			}
	        			//isAscTrue == 1 导出顺序 业务代码自己处理（web页面是倒序的）
	        			var toUrl = signUrl(url+"&queryUrl="+queryBo+"|"+queryCmd+"&excelKeys="+excelKeys+"&excelLables="+excelLables+queryParamStr+"&_ALLEXPORT=1&isAscTrue=1");
	        			var iframe = document.createElement("iframe");
	        		    iframe.id = "frameDownloading";
	        		    iframe.src = encodeURI(toUrl);
	        		    iframe.style.display = "none";
	        		    document.body.appendChild(iframe);
	        		    var that=this;
	        		    
	        		    
	        		    var checkIframe = document.createElement("iframe");
	        		    checkIframe.id = "checkIframe";
	        		    checkIframe.style.display = "none";
	        		    document.body.appendChild(checkIframe);
	        		    window.top.showLoad();
	        		    that.inter=$interval(function() {
	        		    	var checkUrl=queryBo+".ajax?cmd=checkFinishDownLoad&time="+(new Date()).getTime();
	        		    	commonService.postUrl(checkUrl,"",function(data){
		        				if(data.result=="true"){
		        					$interval.cancel(page.inter);
		        		        	var frameDownloadExcel = document.createElement("iframe");
		        		        	frameDownloadExcel.id = "frameDownloadExcel";
		        		        	frameDownloadExcel.src = encodeURI(signUrl(queryBo+".ajax?cmd=downloadExcelFileFromSession"));
		        		        	frameDownloadExcel.style.display = "none";
		    	        		    document.body.appendChild(frameDownloadExcel);
		    	        		    window.top.hideLoad();
		        				}
		        				
		                	});
	        		    }, 5000);
	        		},
	        		btClick:function($event,id,isAll){
	        			this.selected=new Array();
	        			if(isAll==-1){
	        				//加载全部数据
	        				var list=this.data.items;
		        			if(list!=null && list!=undefined && list.length>0){
		        				for(var index in list){
		        					if(list[index]!=null && list[index]!=undefined){
		        						var dateId=list[index][this.id];
			        					this.selected.push(dateId);
		        					}
			        			}
			        		}
	        			}else{
	        				this.selected.push(id);
	        			}
	        			
	        			if(btClickCallMethod!=undefined){
	        				this.getFilterData();
	        				eval("$scope."+btClickCallMethod+"('"+id+"',"+isAll+")");
	        			}
	        		},
	        		//更新选中的数据
	        		
	        		//由于原生的input框
	        		updateSelection:function($event,id,isAll){
	        			id=""+id;
	        			var checkbox = document.getElementById(objName+".checkId"+id);
	        			if(checkbox == undefined){
	        				return ;
	        			}
			        	var action = (checkbox.checked?'remove':'add');
			        	
	        			if(this.isMultiSelect!="true"){
	        				this.clearAllCheckbox();
	        			}
	        			//全选的操作
	        			if(isAll!=undefined && isAll==-1){
	        				
	        				if(action == 'add' && this.selected.indexOf(id) == -1){
	        					this.selectAllCheckbox();
	    			         }
	    			         if(action == 'remove'){
	    			        	 this.clearAllCheckbox();
	    			         }
	        				
	        			}else{
	        				if(action == 'add'){
	        					if(this.selected.indexOf(id) == -1){
	        						this.selected.push(id);
	        					}
	    			            checkbox.checked=true;
	    			         }
	    			         if(action == 'remove' ){
	    			        	 if(this.selected.indexOf(id)!=-1){
	    			        		 var idx = this.selected.indexOf(id);
		    			             this.selected.splice(idx,1);
	    			        	 }
	    			            
	    			             checkbox.checked=false;
	    			         }
	        			}
	        			
	        		},
	        		changeData:function(){
	        			this.clearAllCheckbox();
	        		},
	        		//清除所有的checkbox
	        		clearAllCheckbox:function(){
	        			var checkbox = document.getElementsByName(objName+".checkName");
	        			for(var index in checkbox){
	        				checkbox[index].checked=false;
	        			}
	        			this.selected=[];
	        		},
	        		
	        		//清除所有的checkbox
	        		selectAllCheckbox:function(){
	        			this.selected=[];
	        			var checkbox = document.getElementsByName(objName+".checkName");
	        			for(var index in checkbox){
	        				checkbox[index].checked=true;
	        				if(checkbox[index].value!=undefined && checkbox[index].value!=""){
	        					this.selected.push(checkbox[index].value);
	        				}
	        			}
	        			
	        		},
	        		//获取选中的数据
	        		getSelected:function(){
	        			return this.selected;
	        		},
	        		//获取单选方式选中的数据
	        		getOneSelected:function(){
	        			if(this.selected.length==1){
	        				return this.selected[0];
	        			}else{
	        				return "";
	        			}
	        		},
	        		//获取选中的数据
	        		getSelectData:function(){
	        			var selectArray=new Array();
	        			var list=this.data.items;
	        			if(list!=null && list!=undefined && list.length>0){
	        				for(var index in list){
	        					if(list[index]!=null && list[index]!=undefined){
	        						var dateId=list[index][this.id];
			        				for(var selectIndex in this.selected){
			        					if(this.selected[selectIndex]==dateId){
			        						selectArray.push(list[index]);
			        					}
			        				}
	        					}
		        			}
	        			}
	        			return selectArray;
	        		},
	        		
	        		//排序触发的方法
	        		sort:function(code){
	        			var asc="+";
	        			if(this.orderFile!=undefined){
	        				if(this.orderFile.indexOf("+")!=-1){
	        					asc="-";
	        				}
	        			}
	        			this.orderFile=asc+code;
	        		},
	        		//加载数据
	        		load:function(pageParms,type){
	        			var that=this;
	        			that.isLoad=false;
	        			window.top.showLoad();
	        			//bug 当用户选择不是第一页的按钮后，如?俨檠氖焙颍夭坏降谝灰车奈侍?
	        			if(type==undefined){
	        				pageParms.params.page=1;
	        			}	        			
	        			if(pageParms==undefined){
	        				pageParms=this.urlInfo;
	        			}else{
	        				this.urlInfo=pageParms;
	        			}
	        			that.pageNum=pageParms.params.page;
	        			if(pageParms.params.rows!=undefined && pageParms.params.rows!=null){
	        				that.pageCount=pageParms.params.rows;
	        			}
	        			pageParms.params.rows=that.pageCount;
	        			this.params=pageParms;
	        			commonService.postUrl(pageParms.url,pageParms.params,function(data){
	        				that.clearAllCheckbox();
	        				that.isLoad=false;
	        				window.top.hideLoad();
	        				that.data = data;
	        				if(data.total!=undefined && data.total!=null ){
	        					that.total=data.total;
	        					if(that.total==0){
	        						that.pageNum=0;
	        					}
	        				}
	        				if(data.totalNum!=undefined && data.totalNum!=null ){
	        					that.total=data.totalNum;
	        					if(that.total==0){
	        						that.pageNum=0;
	        					}
	        				}
	        				if(data.totalNum!=undefined && data.totalNum!=null ){
	        					that.total=data.totalNum;
	        					if(that.total==0){
	        						that.pageNum=0;
	        					}
	        				}
	        				if(pageParms.callBack!=null && pageParms.callBack!=undefined && pageParms.callBack!=""){
	        					eval(pageParms.callBack+"('"+data+"')");
	        				}
	        				
	        				// data
	        				// add by yangy, 增加回调函数，给外部提供一个回调函数来支持行颜色的变化
	        				var rowColorCallbackIsFunction = that.rowColorCallback != undefined && that.rowColorCallback instanceof Function;
	        				for(var i = 0; undefined != data && undefined != data.items && i < data.items.length; i++) {
	        					if (rowColorCallbackIsFunction) {
	        						var rowColor = that.rowColorCallback(data.items[i]);
	        						if (undefined == rowColor)
	        							rowColor = '';
	        						data.items[i].rowColor = rowColor;
	        					} else {
	        						data.items[i].rowColor = '';
	        					}
	        				}
	        				// add by yangy, 增加回调函数，给外部提供一个回调函数来支持行颜色的变化
	        				
	        				that.initPage();
	        				that.pageList=that.getPageArray(that.totalNum,that.pageNum);
	                	},function(){
	                		window.top.hideLoad();
	                	});
	        		},
	        		loadData:function(data){
	        			var that = this;
	        			that.data.items = data;
	        			that.total=data.length;
	        		},
	        		//重新加载数据
	        		reLoad:function(toChangePage){
	        			var that=this;
	        			if(toChangePage!=undefined && toChangePage!=""){
	        				if(toChangePage<0){
	        					if((this.total+toChangePage)%this.pageCount==0){
	        						that.params.params.page=that.params.params.page-1;
	        					}
	        				}else {
	        					if((this.total+toChangePage)%this.pageCount==1){
	        						that.params.params.page=that.params.params.page+1;
	        					}
	        				}
	        				
	        			}
	        			commonService.postUrl(that.params.url,that.params.params,function(data){
	        				that.data = data;
	        				if(data.total!=undefined && data.total!=null){
	        					that.total=data.total;
	        				}
	        				if(data.totalNum!=undefined && data.totalNum!=null){
	        					that.total=data.totalNum;
	        				}
	        				if(that.params.params.callBack!=undefined ){
	        					eval(that.params.params.callBack+"(data)");
	        				}
	        				that.initPage();
	                	});
	        		},
	        		initPage : function(){
	        			if(this.total%this.pageCount !=0){
	        				this.totalNum = Math.floor(this.total/this.pageCount)+1;
	        			}else{
	        				this.totalNum = Math.floor(this.total/this.pageCount);
	        			}
	        			if(this.pageNum>this.totalNum){
	        				this.pageNum=this.totalNum;
        				}
	        		},
	        		
	        		//更改分页大小
	        		changeRows : function(rowsNum){
	        			this.pageCount = rowsNum;
	        			if(rowsNum==-1){
	        				this.params.params._ALLEXPORT=1;
	        				this.params.params.rows=undefined;
	        			}else{
		        			this.params.params.rows=rowsNum;
		        			this.params.params.page=1;
		        			delete this.params.params._ALLEXPORT;
	        			}
	        			this.load(this.params,1);
	        		},
	        		//指定页
	        		changePage : function(page){
	        			if(this.totalNum==0){
	        				commonService.alert("没有数据");
	        				return;
	        			}
	        			if(page<=1 && this.params.params.page==page){
	        				commonService.alert("已经是第一页了");
	        			}else if(page>this.totalNum ){
	        				commonService.alert("已经是最后一页了");
	        			}else {
	        				this.params.params.page = page;
	        				this.load(this.params,1);
	        			}
	        		},
	        		//下一页
	        		nextPage : function(){
	        			if(this.totalNum==0){
	        				commonService.alert("没有数据");
	        				return;
	        			}
	        			if(this.params.params.page>=this.totalNum){
	        				commonService.alert("超出最大页数");
	        			
	        				return ;
	        			}
	        			this.params.params.page = parseInt(this.params.params.page) +1;
	        			this.load(this.params,1);
	        		},
	        		//上一页
	        		prePage : function(){
	        			if(this.totalNum==0){
	        				commonService.alert("没有数据");
	        				return;
	        			}
	        			if(this.params.params.page<=1){
	        				commonService.alert("已经是第一页了");
	        				return;
	        			}
	        			this.params.params.page = parseInt(this.params.params.page) -1;
	        			this.load(this.params,1);
	        		},
	        		
	        		//跳转到指定页面
	        		goPage : function(num){
	        			this.changePage(num);
	        			eval("$scope."+objName+".govalue=''");
	        		},
	        		//跳转到最后一页
	        		toEndPage : function(){
	        			if(this.params.params.page>=this.totalNum){
	        				commonService.alert("超出最大页数");
	        				return ;
	        			}
	        			page.changePage(this.totalNum);
	        		},
	        		toIndexPage:function(){
	        			if(this.params.params.page<=1){
	        				commonService.alert("已经是第一页了");
	        				return;
	        			}
	        			page.changePage(1);
	        		},
	        		/**
	        		 * 入参为 分页的总页数，当前页数
	        		 * 返回值：返回一个展示分页的列表对象
	        		 *     该对象
	        		 *    ｛
	        		 *    		isChoose:是否选中状态, 当前页的数据
	        		 *          pageNum:第几页：－1 为上一页，－2 为下一页
	        		 *          isEllipsis:是否是省略号
	        		 *     ｝
	        		 */
	        		getPageArray:function(totalNum,pageNum){
	        			var retList=new Array();
	        			var prePage={
		        				isChoose:false,
		        				pageNum:-1,
		        				isEllipsis:false
		        			};
	        			var nextPage={
		        				isChoose:false,
		        				pageNum:-2,
		        				isEllipsis:false
		        			};
		        		retList.push(prePage);
		        		
		        		//省略号
        				var ellipsis={
		        				isChoose:false,
		        				pageNum:0,
		        				isEllipsis:true
		        			};
		        		
	        			//第一种情况：页数没有超过 6页
	        		    // 上一页  1 2 3 4 5 6下一页
	        			if(totalNum<=6){
	        				for(var i=1;i<=totalNum;i++){
	        					var pageTemp={};
	        					pageTemp.pageNum=i;
	        					pageTemp.isEllipsis=false;
	        					if(i==pageNum){
	        						pageTemp.isChoose=true;
	        					}else{
	        						pageTemp.isChoose=false;
	        					}
	        					retList.push(pageTemp);
	        				}
	        			}
	        			//第二种情况，页数超过6页的时候，需要添加省略号
	        			if(totalNum>6){
	        				//如果当前页数小于6的，123456...
	        				if(pageNum<6){
		        				for(var i=1;i<=6;i++){
		        					var pageTemp={};
		        					pageTemp.pageNum=i;
		        					pageTemp.isEllipsis=false;
		        					if(i==pageNum){
		        						pageTemp.isChoose=true;
		        					}else{
		        						pageTemp.isChoose=false;
		        					}
		        					retList.push(pageTemp);
		        				}
		        				
		        				retList.push(ellipsis);
	        				}else{
	        					//计算需要展示的数字，在当前页的前后加起来5个数字
	        					var endNum=pageNum+2;
	        					if(totalNum<endNum){
	        						endNum=totalNum;
	        					}
	        					
	        					var firsPage={
	        							isChoose:false,
				        				pageNum:1,
				        				isEllipsis:false
	        					}
	        					
	        					retList.push(firsPage);
	        					
	        					ellipsis={
	    		        				isChoose:false,
	    		        				pageNum:0,
	    		        				isEllipsis:true
	    		        			};
		        				retList.push(ellipsis);
	        					
		        				//展示的数据
		        				for(var i=endNum-5;i<=endNum;i++){
		        					var pageTemp={};
		        					pageTemp.pageNum=i;
		        					pageTemp.isEllipsis=false;
		        					if(i==pageNum){
		        						pageTemp.isChoose=true;
		        					}else{
		        						pageTemp.isChoose=false;
		        					}
		        					retList.push(pageTemp);
		        				}
		        				//如果展示的数据最大的值比总数小2以上，展示省略号和最后一页
		        				if(totalNum-endNum>=2){
		        					ellipsis={
		    		        				isChoose:false,
		    		        				pageNum:0,
		    		        				isEllipsis:true
		    		        			};
		        					retList.push(ellipsis);
		        					var lastPage={
		        							isChoose:false,
					        				pageNum:totalNum,
					        				isEllipsis:false
		        					};
		        					retList.push(lastPage);
		        				}
		        				
	        				}
	        			}
	        			retList.push(nextPage);
	        			return retList;
	        		}
	        	};
				$scope[objName]=page;
            	//eval($scope+"."+objName+"=page");
            };
	};
	return myTable;
}]);