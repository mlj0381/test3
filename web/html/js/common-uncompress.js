var urlManager={};
var userInfo={
		orgId:getCookie("orgId"),//orgId 加密
		orgName:getCookie("orgName"),
		userId:getCookie("userId"),//userId 加密
		userName:getCookie("userName"),
		cityName:getCookie("cityName")
};
var commonApp=angular.module("commonApp", []);
/**
 * 设置iframe里面的高度
 */
function setContentHeight(height) {
	var id=window.top.getCurrentOpenId();
	var hg=document.body.scrollHeight;
	if(height!=undefined){
		hg=hg+height;
	}
	window.top.document.getElementById('tab_id_'+id).style.height =hg+"px";
}


/**
 * 头部菜单
 * 
 * 标签的属性值表示第几个菜单被选中
 */

commonApp.directive('myTopMenu',['commonService',function(commonService){
	var myTopMenu = {};
	myTopMenu.restrict="AE";
	myTopMenu.compile=function(element,attrs,$scope){
		var objName=attrs.name;
		if(objName == undefined){
			objName="myTopMenu";
		}
		var menu = {
				menuData:{},
				getMenu:function(){
					commonService.postUrl("portalBusiBO.ajax?cmd=getTopMenu","",function(data){
						menu.menuData = data;
					});
				}
				
		};


		 var template='<li  ng-repeat="obj in '+objName+'.menuData.childMenus" class="nav_li"><a href="#" ng-mouseover="'+objName+'.show(obj.menuId)"; class="nav_li_a"><i class="block"><img width="34" height="34" alt="" src="image/$tenantId$/nav_icon.png"></i><span>{{obj.menuName}}<i class="icon_down icon"></i></span></a>'
			+'<div class="submenu clearfix"  id="{{obj.menuId}}">'
			+'<div style="width:1000px; margin:0 auto">'
			+' <dl class="submenu1 fl" ng-repeat="childObj in obj.childMenus">'
			+'   <dt>{{childObj.menuName}}</dt>'
			+'    <dd ng-repeat="menu in childObj.childMenus" ><a href="javascript:void(0)"  ng-click="'+objName+'.menuClick(menu.menuId,menu.menuName,menu.menuPath,obj.menuId)">{{menu.menuName}}</a></dd>'
			+'</dl>'
			+'</div>'
			+'</div>'
			+'</li>';
		
		element.html(template);
		return function($scope,element,attrs){
			menu.getMenu();
			menu.menuClick=function(id,name,url,menuId){
				$scope.myTab.open(id,name,url);
				$scope.isShowIndex=false;
				$scope.isShow=false;
				$("#"+menuId).hide();
			},
			menu.show=function(menuId){
				$("#"+menuId).show();
			},
			eval("$scope."+objName+"=menu");
		};
	};
	return myTopMenu;
}]);


/**
 * tab页面
 * 
 * 标签的属性值表示第几个菜单被选中
 */

commonApp.directive('myTab',['commonService',function(commonService){
	var myTab = {};
	myTab.restrict="AE";

	myTab.compile=function(element,attrs,$scope){
		var objName=attrs.name;
		var callBack=attrs.callback;
		if(objName == undefined){
			objName="myTab";
		}
		var tab = {
				list:[],
				firstOpen:true,
				getCurrentOpenId:function(){
					var currentTab=this.getCurrentTab();
					if(currentTab!=undefined){
						return currentTab.id;
					}
				},
				getCurrentTab:function(){
					for(var index=0;index<this.list.length;index++ ){
						  if(this.list[index].type=="show"){
							  return this.list[index];
						  }
					}
				},
				//打开一个tab,如果该tab已经存在，就显示，如果没有，新开一个
				open:function(id,name,url){
					if(this.firstOpen){
						this.firstOpen=false;
						eval(callBack+"()");
					}
					if(!this.changeTab(id)){
						var elm={};
						elm.id=id;
						elm.parentId="";
						elm.name=name;
						elm.url=url;
						elm.type="show";
						this.list.push(elm);
					}
					
				},
				/***
				 * id  新开页面的id
				 * name 新开页面的名称
				 * url   新开页面的地址
				 * params 表示要传入新开页面的参数
				 */
				openWithParent:function(id,name,url){
					var parentId=this.getCurrentOpenId();
					var childId=parentId+"_"+id;
					if(!this.changeTab(childId)){
						var elm={};
						elm.id=childId;
						elm.parentId=parentId;
						elm.name=name;
						elm.url=url;
						elm.type="show";
						this.list.push(elm);
					}
					
				},
				//把所有tab页面变成不可显示
				hideAll:function(){
					for(var index=0;index<this.list.length;index++ ){
						  this.list[index].type="hide";
					}
				},
				//切换tab页面
				changeTab:function(id){
					this.hideAll();
					for(var index=0;index<this.list.length;index++ ){
						  if(this.list[index].id==id){
							  this.list[index].type="show";
							  return true;
						  }
					}
					return false;
				},
				cleanAll:function(){
					this.list=[];
				},
				refCurrentTab:function(){
					var currentTab=this.getCurrentTab();
					if(currentTab.url.indexOf("?_refCurrentTab=")!=-1){
						currentTab.url=currentTab.url.substr(0,currentTab.url.indexOf("?_refCurrentTab="));
					}
					currentTab.url=currentTab.url+"?_refCurrentTab="+ (new Date()).getTime();
				},
				closeToParentTab:function(isReload){
					var currentTab=this.getCurrentTab();
					if(currentTab!=undefined){
						//关闭当前的页面
						for(var index=0;index<this.list.length;index++ ){
							  if(this.list[index].id==currentTab.id){
								  this.list.splice(index,1);
								  break;
							  }
						}
						//切换到父页面
						this.changeTab(currentTab.parentId);
						if(isReload!=undefined && isReload==true){
							this.refCurrentTab();
						}
					}
				}
		};
		 var template=
			 '<ul id="tags" class="subnav" ng-show="'+objName+'.list.length>0">'
         	+'<li id="dh1" ng-class="{\'hover\':obj.type==\'show\'}" ng-repeat="obj in '+objName+'.list" ><a href="javascript:void(0)" ng-click="'+objName+'.changeTab(obj.id)" class="a1">{{obj.name}}</a><i class="subnav_icon icon"><a ng-click="'+objName+'.close(obj.id)" href="javascript:void(0)" class="gone"></a></i></li>'
         	+'</ul>'
         	+'<div ng-repeat="obj in '+objName+'.list" ng-show="obj.type==\'show\'"  ><iframe frameborder="0" style="height:100%;visibility:inherit; width:100%;z-index:1;" ng-src="{{obj.url}}" scrolling="yes"  id="tab_id_{{obj.id}}" ></iframe></div>';
		
		element.html(template);
		return function($scope,element,attrs){
			tab.close=function(id){
				var index=0;
				for(;index<this.list.length;index++ ){
					  if(this.list[index].id==id){
						  this.list.splice(index,1);
						  break;
					  }
				}
				if(this.list.length==0){
					$scope.loadIndex();
				}else{
					var showTabIndex=0;
					if(index-1>0){
						showTabIndex=index-1;
					}
					this.list[showTabIndex].type="show";
				}
				
			};
			eval("$scope."+objName+"=tab");
		};
	};
	return myTab;
}]);

/**
 * 上传指令
 * 
 */
commonApp.directive('myFileModel',['commonService','$parse','$sce','$compile','$rootScope',function(commonService,parse,$sce,$compile,$rootScope) {
	var myFileUp={};
	myFileUp.restrict="A";
	myFileUp.replace=true;
	myFileUp.compile=function(element, attrs){
		return function($scope, element, attrs){
			var objName=attrs.myFileModel;
			var objClose=attrs.isClose;
			var objDefinedImg=attrs.definedImg;
			if(attrs.index!=undefined){
				var parseFun=parse(attrs.index);
				objName=objName+parseFun($scope);
			}
			
			var fileUp={
				isShow:false,
				show:true,
				callbackUrl:"",
				
				initDate: function (flowId){
					if(flowId!=null && flowId!=undefined && flowId!=""){
						var jdt=document.getElementById(objName+"Jdt");
						if(jdt!=null && jdt!=undefined && jdt!=""){
							jdt.style.display="block";
						}
						var url="attach.ajax?cmd=doQuery&_GRID_TYPE=jq&flowIds="+flowId;
						commonService.postUrl(url,"",function(data){
							if(data.total>0){
								document.getElementById(objName+"Id").value=flowId;
								document.getElementById(objName+"Img").src=data.rows[0].fullPathUrl;
								document.getElementById(objName+"Url").value=data.rows[0].storePath;
							}else{
								document.getElementById(objName+"Id").value="";
								document.getElementById(objName+"Url").value="";
							}
							if(jdt!=null && jdt!=undefined && jdt!=""){
								jdt.style.display="none";
							}
						});
					}
				},
				clike: function (){
					this.iframeClick();
				},
				iframeClick:function(){
					eval(objName+'Iframe.onClick("'+objName+'Id","'+objName+'Url","'+objName+'Img","'+objName+'Close","'+objDefinedImg+'","'+objName+'Jdt","'+this.callbackUrl+'")'); 
				},
				del:function (){
					var id=document.getElementById(objName+"Id").value;
					if(id!=null && id!=undefined && id!=""){
						var jdt=document.getElementById(objName+"Jdt");
						if(jdt!=null && jdt!=undefined && jdt!=""){
							jdt.style.display="block";
						}
						var url="attach.ajax?cmd=doDel&flowId="+id;
						commonService.postUrl(url,"", function(res) {
							if(objDefinedImg!=null && objDefinedImg!=undefined && objDefinedImg!="" && objDefinedImg!="null" && objDefinedImg!="undefined"){
								document.getElementById(objName+"Img").src=commonService.getRootPath()+objDefinedImg;
							}else{
								document.getElementById(objName+"Img").src=commonService.getRootPath()+"/image/fa.jpg";
							}
							document.getElementById(objName+"Id").value="";
							document.getElementById(objName+"Url").value="";
							if(jdt!=null && jdt!=undefined && jdt!=""){
								jdt.style.display="none";
							}
						});
					}
				},
				get:function (){
					var id=document.getElementById(objName+"Id").value;
					var url=document.getElementById(objName+"Url").value;
					return {flowId:id,flowUrl:url};
				},
				clean:function (){
					document.getElementById(objName+"Id").value="";
					document.getElementById(objName+"Url").value="";
					if(objDefinedImg!=null && objDefinedImg!=undefined && objDefinedImg!="" && objDefinedImg!="null" && objDefinedImg!="undefined"){
						document.getElementById(objName+"Img").src=commonService.getRootPath()+objDefinedImg;
					}else{
						document.getElementById(objName+"Img").src=commonService.getRootPath()+"/image/fa.jpg";
					}
				},
				isUpShow:function(flag){
					this.show=flag;
				},
				mouseenter:function(){
					var obj = document.getElementById(objName+"Iframe");
					if(obj==null && obj==undefined){
						document.getElementById(objName+"spanIframe").innerHTML="<iframe id='"+objName+"Iframe' name='"+objName+"Iframe' src='"+commonService.getRootPath()+"/js/fileUpLoad.html' style='display: none;'></iframe>";	
					}
					if(this.show){
						this.isShow=true;
					}else{
						this.isShow=false;
					}
				},
				mouseleave:function(){
					this.isShow=false;
				},
				loadCheck:function(){
					
				}
			};
			var html=
				'<div class="controls_pz" id="'+objName+'UpFile" ng-mouseenter="'+objName+'.mouseenter()" ng-mouseleave="'+objName+'.mouseleave()">'+
				'	<div class="controls_pz">'+
				'		<div class="image-tips-wrap">';
				if(objClose!=null && objClose!=undefined && objClose!="" && objClose=="true"){
					html+='	<i class="s_bh" ng-click="'+objName+'.del()" id="'+objName+'Cl" ng_show="'+objName+'.isShow" id="'+objName+'Close" name="'+objName+'Close">x</i>';
				}else{
					html+='	<i id="'+objName+'Cl" style="display: none;" id="'+objName+'Close" name="'+objName+'Close"></i>';
				}
				if(objDefinedImg!=null && objDefinedImg!=undefined && objDefinedImg!=""){
					html+='	<img src="'+commonService.getRootPath()+objDefinedImg+'" id="'+objName+'Img" name="'+objName+'Img" class="image-border" width="180" height="122"/>';
				}else{
					html+='	<img src="'+commonService.getRootPath()+'/image/fa.jpg" id="'+objName+'Img" name="'+objName+'Img" class="image-border" width="180" height="122"/>';
				}
				html+='		<div class="a11" id="'+objName+'Ck" ng_show="'+objName+'.isShow">'+
				'				<a class="tx" href="javascript:void(0);" ng-click="'+objName+'.clike()">修改图片</a>'+
				//'				<iframe name="'+objName+'Iframe" src="'+getRootPath()+'/js/fileUpLoad.html" style="display: none;"></iframe>'+
				'				<span id="'+objName+'spanIframe"></span>'+
				'				<input type="hidden" id="'+objName+'Id" name="'+objName+'Id"/>'+
				'				<input type="hidden" id="'+objName+'Url" name="'+objName+'Url"/>'+
				'			</div>'+
				'		</div>'+
				'	</div>'+
				'	<div class="kaa" style="display: none;" id="'+objName+'Jdt" name="'+objName+'Jdt"><img src="'+commonService.getRootPath()+'/image/$tenantId$/jdt.gif" class="image-border" width="100" height="100"/> </div>'+
				'</div>';
			element.html($sce.trustAsHtml(html));
			$compile(element.contents())($scope);
			eval("$rootScope."+objName+"=fileUp");
		};
	};
	return myFileUp;
}]);


/**
 * @TODO  wangbq
 */

commonApp.directive('vehicleType',['commonService',function(commonService){
	var myVehicleType={};
	myVehicleType.restrict="AE";
	myVehicleType.replace=true;
	myVehicleType.compile=function(element, attrs){
		var placeholder = attrs.placeholder;
		if(placeholder == undefined || placeholder ==null){
			placeholder = "";
		}
		var vehicleTypeWin={
			isShow:false,//整个弹窗是否显示
			inputValue:"",//输入框的数据
			inputId:"",
			isDisabel:false, // 控制是否显示数据
			vehicleTypeData:{},
			vehicleLen:"",
			initDate:function(inputId){
				if(inputId!=null && inputId!=undefined && inputId!=""){
					var url = "selectContainer.ajax?cmd=doQueryContainer";
					var that=this;
					var param="";
					commonService.postUrl(url,param,function(data){
						that.vehicleTypeData=data.items;
						for(var i=0;i< that.vehicleTypeData.length;i++){
							var obj=that.vehicleTypeData[i];
							if(obj.containerId==inputId){
								that.inputValue=obj.containerName;
								that.inputId=obj.containerId;
							}
						}
	            	});
				}
			},
			setData:function (vehicleLen){
				this.vehicleLen=vehicleLen;
				if(this.vehicleLen!=null && this.vehicleLen!=undefined && this.vehicleLen!=""){
					var that=this;
					var url = "selectContainer.ajax?cmd=doQueryContainer&vehicleLength="+vehicleLen;
					var param="";
					commonService.postUrl(url,param,function(data){
						that.vehicleTypeData=data.items;
	            	});
				}
			},
			disable:function(val){
				this.isDisabel=val;
			},
			inputClick: function (){
				alert(1);
				this.isShow=true;
				if(this.isDisabel){
					this.isShow=false;
				}
				if(this.isShow){
					this.creatData();
				}
				
			},
			
			confirm:function(objName,objId){
				this.inputValue=objName;
				this.inputId=objId;
				this.isShow=false;
				this.executive(this.inputId,this.inputValue);
			},
			clear:function(){
				this.inputValue="";
				this.inputId="";
				this.focus();
			},
			creatData:function(){
				if(this.vehicleLen==null || this.vehicleLen==undefined || this.vehicleLen==""){
					alert(2);
					var url = "selectContainer.ajax?cmd=doQueryContainer";
					var that=this;
					var param="";
					commonService.postUrl(url,param,function(data){
						that.vehicleTypeData=data.items;
	            	});
				}
			},
			close:function(){
				this.isShow=false;
			},
			mouseleave : function(){
				this.focus();
				this.mouseenterDiv = false;
			},
			blur : function(){
				if(!this.mouseenterDiv){
					this.close();
				}
			},
			mouseenter : function(){
				this.mouseenterDiv = true;
			}
		};
		//每个标签的对象不一样
		var objName=attrs.vehicleType;
		var root = attrs.roots;
		var exec=attrs.exec;
//		<div class="list_stock" style="display:">
//    	<h2>请选择车辆类型</h2>
//        <i class="list_xl_icon icon"><a class="gone" href="#dh3"></a></i>
//        <ul class="area_list">
//            	<li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="1" type="checkbox">普通货车</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="15" type="checkbox">平板</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="12" type="checkbox">厢式</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="20" type="checkbox">厢式尾板</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="3" type="checkbox">集装箱</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="5" type="checkbox">高栏</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="21" type="checkbox">冷藏车</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="23" type="checkbox">罐体专用车</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="4" type="checkbox">自卸</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="22" type="checkbox">危险品车</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="1" type="checkbox">普通货车</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="15" type="checkbox">平板</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="12" type="checkbox">厢式</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="20" type="checkbox">厢式尾板</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="3" type="checkbox">集装箱</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="5" type="checkbox">高栏</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="21" type="checkbox">冷藏车</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="23" type="checkbox">罐体专用车</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="4" type="checkbox">自卸</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="22" type="checkbox">危险品车</a></li>
//           </ul>
//           <div class="qc_q"><a class="ui-city-close" href="javascript:void(0);" ng-click="manyVehicleType.clear()">清除</a></div>
//           <div class="qr_q"><a class="ui-city-close" href="javascript:void(0);" ng-click="manyVehicleType.confirm()">确定</a></div>
//    </div>
		var html="<input type=\"text\" placeholder='"+placeholder+"' ng-blur=\"" + objName + ".blur()\"   readonly=\"readonly\" my-Focus=\""+objName+"focus\" ng-click=\""+objName+".inputClick()\" ng-model=\""+objName+".inputValue\" class=\"form_billing\" style=\"width:"+attrs.width+"px;height:"+attrs.height+"px\"></input>";
		html+="<div class=\"list_stock\" ng-mouseenter=\""+ objName + ".mouseenter()\" ng-mouseleave=\""+ objName + ".mouseleave()\" ng-show=\""+objName+".isShow\" style=\"position: absolute;z-index: 999999;width: 390px;\">";
		html+="<h2>选择车辆类型信息</h2>";
		html+="<div data-widget=\"tabs\" class=\"m JD-stock\" id=\"JD-stock\">";
		html+="<input type=\"hidden\"/>";
		html+="<div class=\"mc\" data-area=\"0\" data-widget=\"tab-content\" id=\"stock_province_item\" >";
		html+="<ul class=\"area-list\" >";
		html+="<li ng-click=\""+objName+".confirm(vehicletype.containerName,vehicletype.containerId)\" ng-repeat=\"vehicletype in "+objName+".vehicleTypeData\" style=\"width: 75px;\"><a href=\"javascript:void(0);\">{{vehicletype.containerName}}</a></li>";
		html+="</ul>";
		html+="</div>";
		html+="</div>";
		html+="<div class=\"close\"> <i class=\"list_xl_icon icon\"><a class=\"gone\" ng-click=\""+objName+".close()\" href=\"javascript:void();\"></a></i></div>";
		html+="<div class=\"qc_q\"><a class=\"ui-city-close\" href=\"javascript:void(0);\" ng-click=\""+objName+".clear()\">清除</a></div>";
		html+="<div class=\"qr_q\"><a class=\"ui-city-close\" href=\"javascript:void(0);\" ng-click=\"vehicleType.confirm()\">确定</a></div>";
		html+="</div>";
		element.html(html);
		return function($rootScope,$scope, element, attrs){
//			vehicleTypeWin.executive=function (inputId,inputValue){
//				if(exec!=null && exec!=undefined && exec!=""){
//					eval(exec+"('"+inputId+"','"+inputValue+"')");
//				}
//			};
//			vehicleTypeWin.focus=function(){
//				if(root == null){
//					eval("$scope."+objName+"focus.focus(true)");
//				}else{
//					eval(root+"."+objName+"focus.focus(true)");
//				}
//			};
//			if(root == null || root==undefined){
//				eval("$scope."+objName+"=vehicleTypeWin");
//			}else{
//				
//			}
			eval("$scope."+objName+"=vehicleTypeWin");
			$scope.test=function(){
				alert(1);
			};
		};
	};
	return myVehicleType;
}]);


/**
 * 日期标签
 * 
 *  该属性值为对象，如果不传，按默认格式。 对象属性：dateFmt，autoPickDate
 * 
 */
commonApp.directive("myDatePicker",function($parse){
                    return {
                        restrict:"A",
                        link:function(scope, element, attr){
                        	var obj={};
                        	if(attr.myDatePicker!=undefined  && attr.myDatePicker!="" && attr.myDatePicker!=null){
                        		obj=angular.fromJson(attr.myDatePicker,false);
                        	}
                        	var dateFmt="yyyy-MM-dd";
                        	var autoPickDate=true;
                        	var callBack = "";
                        	var maxDate = "";
                        	var minDate="";
                        	if(obj.dateFmt!=undefined){
                        		dateFmt=obj.dateFmt;
                        	}
                        	if(obj.autoPickDate!=undefined){
                        		autoPickDate=obj.autoPickDate;
                        	}
                        	if(obj.callBack != undefined){
                        		callBack = obj.callBack
                        	}
                        	if(obj.maxDate != undefined){
                        		maxDate = obj.maxDate
                        	}
                        	if(obj.minDate != undefined){
                        		minDate = obj.minDate
                        	}
                            element.bind("click", function () {
                            	WdatePicker({dateFmt:dateFmt, autoPickDate: autoPickDate,minDate:minDate,maxDate:maxDate,onpicked: function(){
                            		$parse(attr['ngModel']).assign(scope, this.value);
                            		if(callBack != null && callBack != undefined && callBack != ""){
                            			eval("scope."+callBack+"(this.value)");
                            		}
                            	}});
                            });
                        }
       };
});


/**
 * 用户需要登录才能操作
 */
commonApp.directive("isCheckLogin",['commonService',function(commonService){
                    return {
                        restrict:"A",
                        link:function(scope, element, attr){
                            element.bind("click", function (event) {
            					var url = "logisticsTrack.ajax?cmd=userDateInfoOfIndex";
            					commonService.postUrl(url,"",function(data){
            						if(data != null && data != undefined && data !=""){
            							//表示已经登录了
            							if(attr.isCheckLogin.indexOf(".html")==-1){
            								var isCheckLogin="scope."+attr.isCheckLogin;
            								eval(isCheckLogin);
            							}else{
            								window.location.href=attr.isCheckLogin;
            							}
            						}else{
            							//表示没有登录，需要提示用户登录
            							commonService.confirm("系统异常，请联系管理员！",function(){
            								var loginLocStr=getRootPath()+'/'+'login.html';
            								if(loginLocStr != null && loginLocStr != undefined && loginLocStr !=""){
            									window.location.href=loginLocStr;
            								}
			                             });
            						}
            					});
                            });
                        }
       };
}]);


/** 是否有权限访问  */
commonApp.directive("isCanVisit",['commonService',function(commonService){
                    return {
                        restrict:"A",
                        link:function(scope, element, attr){
                        	var entityId = attr.isCanVisit.split("|")[0];
                        	var reg = new RegExp("^[0-9]*$");
                        	if(reg.test(entityId)){
                        		element.bind("click", function (event) {
                        			var url = "logisticsTrack.ajax?cmd=isCanVisit";
                        			commonService.postUrl(url,"entityId="+entityId,function(data){
                        				if(data != null && data != undefined && data !=""){
                        					if(data == "Y"){
                        						//表示已经登录了
                        						if(attr.isCanVisit.indexOf(".html")==-1){
                        							var isCanVisit="scope."+attr.isCanVisit.split("|")[1];
                        							eval(isCanVisit);
                        						}else{
                        							window.location.href=attr.isCanVisit;
                        						}
                        					}else{
                        						commonService.alert("亲，您没有该使用权限！");
                        					}
                        				}else{
                        					//表示没有登录，需要提示用户登录
                        					commonService.alert("亲，您没有该使用权限！");
                        				}
                        			});
                        		});
                        	}else{
                        		element.bind("click", function (event) {
                        			//这个是配置错误 标签填写的不是数字
                					commonService.alert("亲，您没有该使用权限！");
                        		});
                        	}
                        }
       };
}]);

/**
 * 分页表格
 * 
 * 如果没有设置name属性值，默认取page做为该表格的对象，如果name属性有传入值，则以该属性值为对象
 * 
 * 对外提供：
 *      load方法，加载表格数据入参为对象：
 *      		    属性：url 表示请求的地址
 *      			属性：params 表示请求的参数
 *                  属性：callBack 表示数据请求后，调用的回调方法，入参为请求返回的数据对象
 *      reload方法，重新请求当前页的数据。
 * 
 */
commonApp.directive('myTable',['commonService',function(commonService){
	var myTable={};
	myTable.restrict="E";
	myTable.transclude=true;
	myTable.compile=function(element, attrs){
		var objName=attrs.name;
		var isShowTotal=attrs.showtotal;
		isShowTotal=isShowTotal=="false"?false:true;
		if(objName==undefined){
			objName="page";
		}
		var template='<div ng-transclude>'
			+'</div>'
			+'<div ng-show="'+objName+'.total==0 && !'+objName+'.isLoad" class="search_page" style=" text-align:center; line-height:50px; color:#ff7800;">没有符合条件的数据'
			+'</div>'
			+'<div class="chey_b_1 list_fye clearfix ">'
			+'<div class="chey_xs fl">'
			+'<span>显示</span><a ng-click="'+objName+'.changeRows(10);" href="javascript:void(0)" ng-class="{hover:'+objName+'.pageCount==10}" class="hover">10</a><a ng-click="'+objName+'.changeRows(20);" href="javascript:void(0)" ng-class="{hover:'+objName+'.pageCount==20}">20</a><a ng-click="'+objName+'.changeRows(30);" href="javascript:void(0)" ng-class="{hover:'+objName+'.pageCount==30}">30</a><span>条</span>共{{'+objName+'.total}}条数据'
			+'</div>'
			+' <ul class="pagination fr" ng-show="'+objName+'.total>0">'
			+'  <li ng-class="{active:obj.isChoose==true}" ng-repeat="obj in '+objName+'.pageList" ><a ng-if="obj.pageNum==-1" ng-click="'+objName+'.prePage();" title="" href="javascript:void(0)"><</a>'
			+'  <a ng-if="obj.pageNum>=1"  ng-click="'+objName+'.goPage(obj.pageNum);" href="javascript:void(0)">{{obj.pageNum}}</a>'
			+' <div ng-if="obj.isEllipsis==true" class="dhl_break">...</div>'
			+'  <a ng-if="obj.pageNum==-2" ng-click="'+objName+'.nextPage();" title="" href="javascript:void(0)">></a></li>'
			+' </ul>'
			+' </div>';
		
		
		element.html(template);
		return function( $scope, element, attrs ) {

			var page={
	        		pageCount:10,//分页数据的大小
	        		pageNum:1,//当前的分页的第几页
	        		totalNum:1,//总的页数
	        		total:0,//总记录数
	        		params:{},//请求的参数
	        		isLoad:true,
	        		isShowTotal:isShowTotal,
	        		pageList:new Array(),
	        		load:function(pageParms){
	        			var that=this;
//	        			pageParms.params.page=that.pageNum;
	        			that.pageNum=pageParms.params.page;
	        			
	        			if(pageParms.params.rows!=undefined && pageParms.params.rows!=null){
	        				that.pageCount=pageParms.params.rows;
	        			}
	        			
	        			pageParms.params.rows=that.pageCount;
	        			this.params=pageParms;
	        			commonService.postUrl(pageParms.url,pageParms.params,function(data){
	        				that.isLoad=false;
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
	        					eval(pageParms.callBack+"(data)");
	        				}
	        				
	        				
	        				that.initPage();
	        				that.pageList=that.getPageArray(that.totalNum,that.pageNum);
	                	});
	        		},
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
	        			this.params.params.rows=rowsNum;
	        			this.params.params.page=1;
	        			this.load(this.params);
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
	        				this.load(this.params);
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
	        			this.load(this.params);
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
	        			this.load(this.params);
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
            	eval("$scope."+objName+"=page");
            };
	};
	return myTable;
}]);

/**
 * 自定义下拉列表标签
 * 实例：<my-select id="query.inoutSts" code-type="AC_CASH_PROVE@INOUT_STS"  default-value="" url="" url-param='' show-name='' show-value=''/>
 * id：ng-model
 * code-type：基础数据sys_static_data的code-type字段
 * has-all==true：所有下拉项
 * default-value：默认值
 * url：自定义查询方法出参为：codeValue=codeName键值对
 * url-param：string类型json格式数据,给url传参
 * show-name：自定义获取下拉数据名称展示
 * show-value：自定义获取下拉数据名称展示对应的值
 * change：值改变时执行方法
 * PS:程序设置下拉列表选中值的话，直接通过$scope.id = ***即可设置
 */
commonApp.directive('mySelect',['commonService',function(commonService){
	var mySelect={};
	mySelect.restrict="E";
	mySelect.transclude=true;
	mySelect.compile=function(element, attrs){
		element.html("");
		var id=attrs.id;
		var showName=attrs.showName;
		var showValue=attrs.showValue;
		var change=attrs.change;
		var filterName=attrs.filter;
		if(!showName){
			showName = "codeName";
		}
		if(!showValue){
			showValue = "codeValue";
		}
		var dataId=attrs.id.replace(".","_") + "_data";
		var objId=attrs.id.replace(".","_") + "_obj";
		var template='';
		if(filterName!=null&&filterName!=undefined&&filterName!=''){
			template="<select ng-change='"+change+"' ng-model='" + id + "' ng-options='" + objId + "." + showValue + " as " + objId + "." + showName + " for " + objId + " in " + dataId + ".items | filter:{"+showName+":\"!"+filterName+"\"}' />";
		}else{
			template="<select ng-change='"+change+"' ng-model='" + id + "' ng-options='" + objId + "." + showValue + " as " + objId + "." + showName + " for " + objId + " in " + dataId + ".items' />";
		}
		element.html(template);
		return function( $scope, element, attrs ) {
			var url = attrs.url;
			var codeType = attrs.codeType;
			var defaultValue = attrs.defaultValue;
			var hasAll = attrs.hasAll;
			var elseValue=attrs.elseValue;
			var valueArr=new Array();
			if(elseValue!=null&&elseValue!=undefined&&elseValue!=''){
				valueArr=elseValue.split("-");
			}
			var param = attrs.urlParam;
			if(param==undefined||param==""){
				param = {"hasAll":hasAll,"codeType":codeType};
			}
			else
			{
				param = eval('(' + param + ')');;
			}
			if(url==undefined||url==""){
				url = "staticDataBO.ajax?cmd=selectSysStaticDataByCodeType&time="+(new Date()).valueOf();
			}
			if(defaultValue==undefined||defaultValue==""||defaultValue==null){
				if(hasAll == "true"||hasAll == "TRUE"){
					defaultValue = "-1";
				}
			}
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					if(valueArr.length>0){
						for(var i=data.items.length-1;i>0;i--){
							for(var j=0;j<valueArr.length;j++){
								var value=eval("data.items["+i+"]."+showValue);
								if(value==valueArr[j]){
									data.items.splice(i,1);
									break;
								}
							}
						}
					}
					eval("$scope."+ dataId + "=data");
					if(defaultValue==undefined||defaultValue==""){
						eval("$scope."+ id + "=data.items[0]."+showValue);
					}
					else
					{
						eval("$scope."+ id + "=defaultValue");
					}
	 	    	}
			})
		};
	};
	return mySelect;
}]);




/*******************************指令 end************************************************************/

/*******************************form 表单 字段验证属性 start************************************************************/

/**
 * 浮点数校验
 * 只是保留小数点两位
 * 在需要校验的输入框加入该属性
 * 
 */
commonApp.directive("myDoubleVal", function ($parse) {
        return {
            require:"ngModel",
            link:function(scope,ele,attrs,ctrl) {
            	var methodName='change';
            	if(attrs.myDoubleVal!=null&&attrs.myDoubleVal!=undefined&&attrs.myDoubleVal!=''){
            		methodName=attrs.myDoubleVal;
            	}
            	ele.bind(methodName,function(){
            		if(ctrl.$viewValue!=undefined){
            			var vl=ctrl.$viewValue.replace(/^\D*(\d*(?:\.\d{0,2})?).*$/g, '$1');
                		$parse(attrs['ngModel']).assign(scope, vl);
            			scope.$apply();
            		}
            	});
            	
            }
        };
 });

/**
 * 正整数校验
 * 
 */
commonApp.directive("myNumVal", function ($parse) {
        return {
            require:"ngModel",
            link:function(scope,ele,attrs,ctrl) {
            	ele.bind('change',function(){
            		if(ctrl.$viewValue!=undefined){
            			var vl=ctrl.$viewValue.replace(/[^\d]/g, '');
                		$parse(attrs['ngModel']).assign(scope, vl);
            			scope.$apply();
            		}
            	});
            	
            }
        };
 });


/**
 * 手机号码校验
 * 
 * 在需要校验的输入框加入该属性
 */
commonApp.directive(
				'myRequired',
				function() {
					return {
					    require: '?ngModel',
					    link: function(scope, elm, attr, ctrl) {
					      if (!ctrl) return;
					      attr.myRequired = true; // force truthy in case we are on non input element

					      var validator = function(value) {
					        if (attr.myRequired && ctrl.$isEmpty(value)) {
					          ctrl.$setValidity('myRequired', false);
					          return;
					        } else {
					          ctrl.$setValidity('myRequired', true);
					          return value;
					        }
					      };

					      ctrl.$formatters.push(validator);
					      ctrl.$parsers.unshift(validator);

					      attr.$observe('myRequired', function() {
					        validator(ctrl.$viewValue);
					      });
					    }
					  };
});

/**
 * 手机号码校验
 * 
 * 在需要校验的输入框加入该属性
 */
commonApp.directive(
				'myMobileVal',
				function() {
					return {
						require : "ngModel",
						link : function(scope, element, attr, ngModel) {
							if (ngModel) {
								var billRegexp =/^0?(13[0-9]|15[0-9]|18[0-9]|14[57]|17[0678])[0-9]{8}$/;
							}

							var customValidator = function(value) {
								var validity = ngModel.$isEmpty(value)
										|| billRegexp.test(value);
								ngModel.$setValidity("myMobileVal", validity);
								return validity ? value : undefined;
							};
							ngModel.$formatters.push(customValidator);
							ngModel.$parsers.push(customValidator);
						}
					};
});
/**
 * 浮点数校验
 * 在需要校验的输入框加入该属性
 * 
 */
commonApp.directive("myFloatVal", function () {
		var FLOAT_REGEXP = /^\-?\d+(?:[.,]\d+)?$/;
        return {
            require:"ngModel",
            link:function(scope,ele,attrs,ctrl) {
            	
            	var customValidator=function(viewValue) {
            		var validity=ctrl.$isEmpty(viewValue)
					|| FLOAT_REGEXP.test(viewValue);
                    if(validity) {
                        ctrl.$setValidity("myFloatVal", true);
                        if(viewValue!=undefined && viewValue.replace!=undefined){
                        	 return parseFloat(viewValue.replace(",", "."));
                        }
                        return undefined;
                    }else {
                        ctrl.$setValidity("myFloatVal", false);
                        return undefined;
                    }
                };
                ctrl.$formatters.push(customValidator);
                ctrl.$parsers.unshift(customValidator);
            }
        };
 });
/**
 * 身份证校验
 * 
 * 
 */
commonApp.directive("myCardnoVal", function () {
	
		var cardVal=function IdentityCodeValid(code) {
			var city = {
					11 : "北京",
					12 : "天津",
					13 : "河北",
					14 : "山西",
					15 : "内蒙古",
					21 : "辽宁",
					22 : "吉林",
					23 : "黑龙江 ",
					31 : "上海",
					32 : "江苏",
					33 : "浙江",
					34 : "安徽",
					35 : "福建",
					36 : "江西",
					37 : "山东",
					41 : "河南",
					42 : "湖北 ",
					43 : "湖南",
					44 : "广东",
					45 : "广西",
					46 : "海南",
					50 : "重庆",
					51 : "四川",
					52 : "贵州",
					53 : "云南",
					54 : "西藏 ",
					61 : "陕西",
					62 : "甘肃",
					63 : "青海",
					64 : "宁夏",
					65 : "新疆",
					71 : "台湾",
					81 : "香港",
					82 : "澳门",
					91 : "国外 "
				};
				var tip = "";
				var pass = true;
				
				if(!(code.length == 18 || code.length == 15)){
					tip = "身份证号码长度不对";
					pass = false;
				}
				
				if (!code
						|| !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i
								.test(code)) {
					tip = "身份证号格式错误";
					pass = false;
				}

				else if (!city[code.substr(0, 2)]) {
					tip = "地址编码错误";
					pass = false;
				} else {
					// 18位身份证需要验证最后一位校验位
					if (code.length == 18) {
						code = code.split('');
						// ∑(ai×Wi)(mod 11)
						// 加权因子
						var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
						// 校验位
						var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
						var sum = 0;
						var ai = 0;
						var wi = 0;
						for (var i = 0; i < 17; i++) {
							ai = code[i];
							wi = factor[i];
							sum += ai * wi;
						}
						var last = parity[sum % 11];
						if (parity[sum % 11] != code[17]) {
							tip = "校验位错误";
							pass = false;
						}
					}
				}
				
				return pass;
			};
        return {
            require:"ngModel",
            link:function(scope,ele,attrs,ctrl) {
            	
            	var customValidator=function(viewValue) {
            		var validity=ctrl.$isEmpty(viewValue)
					|| cardVal(viewValue);
                    if(validity) {
                        ctrl.$setValidity("myCardnoVal", true);
                        return viewValue;
                    }else {
                        ctrl.$setValidity("myCardnoVal", false);
                        return undefined;
                    }
                };
                ctrl.$formatters.push(customValidator);
                ctrl.$parsers.unshift(customValidator);
            }
        };
 });
/**
 * 车牌号校验
 * 
 */
 commonApp.directive("myPlateVal", function () {
		var plateVal = function validatePlateNumber(plateNumber) {
						var chare1 = /^[\u4e00-\u9fa5]{1}[A-Z_0-9]{6}$/;
						var chare2 = /^[A-Z_0-9]{5}[\u4e00-\u9fa5]{1}$/;
						var chare3 = /^[A-Z]{2}[A-Z_0-9]{7}$/;
						var chare4 = /^[A-Z]{2}[-]{1}[0-9]{5}$/;
						var chare5 = /^[A-Z]{2}[0-9]{5}$/;
						var chare6 = /^[A-Z]{2}[A-Z_0-9]{5}$/;
						var chare7 = /^[A-Z]{2}[\u4e00-\u9fa5]{1}[A-Z_0-9]{4}$/;
						var chare8 = /^[A-Z]{2}[0-9]{8}$/;
						var chare9 = /^[A-Z]{2}[\u4e00-\u9fa5]{1}[A-Z_0-9]{5}$/;
						var chare10 = /^[A-Z]{2}[\u4e00-\u9fa5]{1}[0-9]{5}$/;
						var chare11 = /^[A-Z]{2}[0-9]{5}$/;
						var chare12 = /^[A-Z]{2}[0-9]{7}$/;
						var chare13 = /^[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{3}$/;
						var arr = [ chare1, chare2, chare3, chare4, chare5,
								chare6, chare7, chare8, chare9, chare10,
								chare11, chare12, chare13 ];
						for (var k = 0; k < arr.length; k++) {
							if (plateNumber.match(arr[k])) {
								return true;
							}
						}
						return false;
			};
        return {
            require:"ngModel",
            link:function(scope,ele,attrs,ctrl) {
            	
            	var customValidator=function(viewValue) {
            		var validity=ctrl.$isEmpty(viewValue)
					|| plateVal(viewValue);
                    if(validity) {
                        ctrl.$setValidity("myPlateVal", true);
                        return viewValue;
                    }else {
                        ctrl.$setValidity("myPlateVal", false);
                        return undefined;
                    }
                };
                ctrl.$formatters.push(customValidator);
                ctrl.$parsers.unshift(customValidator);
            }
        };
 });
 /**
  * 正数校验
  * 
  */
 commonApp.directive("myNumberVal", function () {
		var FLOAT_REGEXP = /^\d+(?=\.{0,1}\d+$|$)/;
        return {
            require:"ngModel",
            link:function(scope,ele,attrs,ctrl) {
            	
            	var customValidator=function(viewValue) {
            		var validity=ctrl.$isEmpty(viewValue)
					|| FLOAT_REGEXP.test(viewValue);
                    if(validity) {
                        ctrl.$setValidity("myNumberVal", true);
                        
                        return viewValue;
                    }else {
                        ctrl.$setValidity("myNumberVal", false);
                        return undefined;
                    }
                };
                ctrl.$formatters.push(customValidator);
                ctrl.$parsers.unshift(customValidator);
            }
        };
 });
 
 
/**
 * 密码校验
 * 校验输入的两个密码是否一致
 * 
 * 在需要校验的输入框加入该标签，属性值为另外一个被校验的值的 id
 * 
 */ 
commonApp.directive('myPwdVal', [function () {
	 var pwdObj={
		        require: 'ngModel',
		        compile: function (elem, attrs, ctrl) {
		            var firstPassword = '#' + attrs.myPwdVal;
//		            var first=$(firstPassword);
		            return function(scope,elem, attrs, ctrl){
		            	elem.on('keyup', function () {
			                scope.$apply(function () {
			                    var v = elem.val()===$(firstPassword).val();
			                    ctrl.$setValidity('myPwdVal', v);
			                });
			            });
		            };
		        }
		    };
	    return pwdObj;
	}]);

/**
 * 图片加载失败，加载默认的图片
 * 
 * errSrc 
 *     1 表示车辆的默认图片 小的
 *     2 表示用户的默认图片 小的
 *     3 表示用户的默认图片 大的
 * 
 */
commonApp.directive("errSrc", function() {
	var rt={
			  getImg:function(type){
				  if(type==1){
					  return getRootPath()+"/image/default/default_min.jpg";
				  }
				  if(type==2){
					  return getRootPath()+"/image/default/user_min.jpg";
				  }
				  if(type==3){
					  return getRootPath()+"/image/default/user_big.jpg";
				  }
				  
				  return "";
			  },
		      link: function(scope, element, attrs) {
		      if(attrs.ngSrc=="" || attrs.ngSrc==undefined){
		    	  	 var img=rt.getImg(attrs.errSrc);
		    		 attrs.$set("src", img);
		    	 }
		      element.bind("error", function() {
		        if (attrs.src != attrs.errSrc) {
		        	var img=rt.getImg(attrs.errSrc);
		          attrs.$set("src", img);
		        }
		      });
		    }
		  };
	  return rt
	});




/**
 * @TODO wangbq
 * 
 */
commonApp.directive('plate',function(){
//commonApp.directive('plate',['commonService','$rootScope',function(commonService,parse,$sce,$compile,$rootScope) {
	var myPlate={};
	myPlate.restrict="AE";
	myPlate.replace=true;
	myPlate.truefalse=false;
	myPlate.compile=function(element, attrs){
		var placeholder = attrs.placeholder;
		
		if(placeholder == undefined || placeholder == null){
			placeholder = "";
		}
		var plateWin={
			isShow:false,//整个弹窗是否显示
			inputValue:"",//输入框的数据
			typeface:"",
			letter:"",
			initDate:function(inputValue){
				this.inputValue=inputValue;
			},
			inputClick: function (){
				var readonly = attrs.readonly;
				if(readonly!=null && readonly!=undefined && readonly!=""){	
					this.isShow=false;
				}else{
					this.isShow=true;
				}
				
			},
			confirmT:function(obj){
				this.typeface=obj;
				this.confirm("");
			},
			confirmL:function(obj){
				this.letter=obj;
				this.confirm("");
			},
			clear:function(){
				this.inputValue="";
				this.typeface="";
				this.letter="";
				this.focus();
			},
			close:function(){
				this.isShow=false;
			},
			mouseleave : function(){
				this.focus();
				this.mouseenterDiv = false;
			},
			blur : function(){
			
				if(!this.mouseenterDiv){
					this.close();
				}
			},
			mouseenter : function(){
				this.mouseenterDiv = true;
			}
		};
		//每个标签的对象不一样
		var objName=attrs.plate;
		var readonly = attrs.readonly;
		if(readonly!=null && readonly!=undefined && readonly!=""){	
			readonly="true";
		}else{
			readonly="false";
		}
		
		var objClassName=attrs.className;
		if(objClassName==null || objClassName==undefined || objClassName==""){
			objClassName = "controls_text";
		}
		
		
		var html='<input ng-blur="' + objName + '.blur()" placeholder= "'+placeholder+'"  type="text" my-focus="'+objName+'focus" ng-click="'+objName+'.inputClick()" ng-readonly="{{'+readonly+'}}" ng-model="'+objName+'.inputValue" class="'+objClassName+'" style="width:'+attrs.width+'px;height:'+attrs.height+'px" maxlength="10"></input>';
		html+='<div ng-mouseenter="'+ objName + '.mouseenter()" ng-mouseleave="'+ objName + '.mouseleave()" ng-show="'+objName+'.isShow" style="position: absolute;z-index: 999999;" class="chooseCard ng-hide">';
		html+=' <div class="left_choose">';
		html+='  <ul style="list-style-type: none;">';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'京\')">京</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'津\')">津</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'沪\')">沪</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'渝\')">渝</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'冀\')">冀</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'晋\')">晋</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'辽\')">辽</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'吉\')">吉</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'黑\')">黑</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'苏\')">苏</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'浙\')">浙</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'皖\')">皖</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'闽\')">闽</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'赣\')">赣</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'鲁\')">鲁</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'豫\')">豫</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'鄂\')">鄂</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'湘\')">湘</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'粤\')">粤</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'琼\')">琼</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'川\')">川</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'黔\')">黔</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'滇\')">滇</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'贵\')">贵</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'云\')">云</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'陕\')">陕</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'秦\')">秦</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'甘\')">甘</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'陇\')">陇</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'青\')">青</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'藏\')">藏</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'桂\')">桂</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'蒙\')">蒙</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'宁\')">宁</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'新\')">新</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'蜀\')">蜀</li>';
		html+='  	</ul></div><div class="right_choose">';
		html+='  	<ul style="list-style-type: none;" id="english">';
		html+='  	<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'A\')">A</li>';
		html+='  	<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'B\')">B</li>';
		html+='  	<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'C\')">C</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'D\')">D</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'E\')">E</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'F\')">F</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'G\')">G</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'H\')">H</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'I\')">I</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'J\')">J</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'K\')">K</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'L\')">L</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'M\')">M</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'N\')">N</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'O\')">O</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'P\')">P</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'Q\')">Q</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'R\')">R</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'S\')">S</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'T\')">T</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'U\')">U</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'V\')">V</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'W\')">W</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'X\')">X</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'Y\')">Y</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'Z\')">Z</li>';
		html+='		<li style="width:40px!important" class="ClearCls" ng-click="'+objName+'.clear()">清空</li>';
		html+='		<li style="width:40px!important;height:20px" class="closeT2" ng-click="'+objName+'.close()">关闭</li>';
		html+='	</ul>';
		html+='</div>';
		html+='</div>';
		element.html(html);
		return function($scope, element, attrs){
			plateWin.focus=function(){
				eval("$scope."+objName+"focus.focus(true)");
			};
			plateWin.confirm=function(obj){
				this.inputValue=this.typeface+this.letter+obj;
				if(this.typeface!=null && this.typeface!=undefined && this.typeface!=""
					&& this.letter!=null && this.letter!=undefined && this.letter!=""){
					this.isShow=false;
					eval("$scope."+objName+"focus.focus(true)");
				}
			},
			eval("$scope."+objName+"=plateWin");
		};
	};
	return myPlate;
});

/**
 * 获取焦点的属性
 * 
 * 属性名称做为对象
 * 
 * 对外提供一个方法：focus方法，调用该方法时，该标签获得焦点
 * 
 * 
 */
commonApp.directive('myFocus',function(){
	var myFouce={};
	myFouce.restrict="A";
	myFouce.replace=true;
	myFouce.priority=21001;
	myFouce.compile=function(element, attrs){
		var objName=attrs.myFocus;
		return function($scope, element, attrs){
			var fouceWin={
					focus:function(val){
						if(val==true){
							element[0].focus();
						}
					}
			};
			eval("$scope."+objName+"=fouceWin");
		};
	};
	return myFouce;
	
});
 /*******************************form 表单 字段验证属性 end************************************************************/
 
 /*******************************service start************************************************************/
commonApp.service( 'commonService', ['$http','$rootScope','$sce', function( $http,$rootScope,$sce ) {
	 
	 var urlEncode=function (param, key, encode) {
		 if(param==null) return '';
		 var paramStr = '';
		 var t = typeof (param);
		 if (t == 'string' || t == 'number' || t == 'boolean' ||  param instanceof Array) {
			 if (param !== "") {
				 paramStr = key + '=' + ((encode==null||encode) ? encodeURIComponent(param) : param);
			 }
		 } else {
			 var idx = 0;
			 var paramArray = new Array();
			 if (param.url != undefined) {
				 if ((idx = param.url.indexOf("&")) > 0) {
					 paramStr = param.url.substring(0, idx);
					 var params = param.url.substring(idx+1).split("&");
					 for (var i in params) {
						 if (params[i].split("=")[1] !== "null" && params[i].split("=")[1] !== "") {
							 paramArray.push(params[i]);
						 }
					 }
				 } else {
					 paramStr = param.url;
				 }
			 }
			 if (param.params != undefined) {
				 for (var i in param.params) {
					 if (param.params[i] != null && param.params[i] !== "null" && param.params[i] !== "") {
						 paramArray.push(urlEncode(param.params[i], i, encode));
					 }
				 }
			 }
			 if (paramArray.length > 0)
				 paramStr += "&"+ paramArray.sort().join("&");
		 }
		 return paramStr;
	 };
	 
	var idEncode=function(param) {
		if(param==null) return '';
		var base64 = new Base64();
		return base64.encode(param);
	}
	/**
	 * 用于解析的内容是对象
	 */
	var objToPostObj=function(obj,key,retmap){
		var j=0;
		for(var i in obj){
			if(obj[i] instanceof Array==true){
				objToPostList(obj[i],key+"["+j+"]",retmap);
			}else if(obj[i] instanceof Object==true){
				objToPostObj(obj[i],key+"."+i,retmap);
			}else{
				retmap[key+"."+i]=obj[i];
			}
			j++;
		}
	}
	/**
	 * 用于解析的内容是列表
	 * 
	 * 
	 */
	var objToPostList=function(obj,key,retmap){
		var j=0;
		for(var i in obj){
			if(obj[i] instanceof Array==true){
				objToPostList(obj[i],key+"["+j+"]",retmap);
			}else if(obj[i] instanceof Object==true){
				objToPostObj(obj[i],key+"["+j+"]",retmap);
			}
			j++;
		}
		retmap[key]=obj.toString();
	}
	/**
	 * 对于传入的对象转换成可以post的形式
	 * 
	 * 
	 */
	var objToPostParam=function(obj){
		var map={};
		for(var i in obj){
			if(obj[i] instanceof Array==true){
				objToPostList(obj[i],i,map);
			}else if(obj[i] instanceof Object==true){
				objToPostObj(obj[i],i,map);
			}else{
				map[i]=obj[i];
			}
		}
		return map;
	}
	var postUrl=function postUrl(url,param,successFun,errorFun,type){
		if(type==undefined || type=="" || type==null){
			 type="POST";
		}
		if(url==null || url==undefined || url==""){
			alert("提交URL为空！");
			return ;
		}
		if(param==null || param==undefined ){
			alert("提交参数为空！");
			return ;
		}
		var queryObject;
		if(typeof param == "string"){
			if (param !== "") {
				if(url.indexOf("?")==-1){
					url=url+"?"+param;
				}else{
					url=url+"&"+param;
				}
			}
			queryObject = {
				method : type,
			    url : url
			};
		}else {
			queryObject = {
				method : type,
			    params : objToPostParam(param),
			    url : url
			};
		}
		if (queryObject.params != undefined && queryObject.params.sign != undefined) {
			delete queryObject.params.sign;
		}
		var urlStr=urlEncode(queryObject, null, false);
		var sign=md5(urlStr+getCookie("token"));
		if (queryObject.params != undefined) {
			queryObject.params.sign=sign;
		} else {
			queryObject.url+="&sign="+sign;
		}
		if (type == "POST") {
			//限制重复请求
			if(urlManager[urlStr]==undefined){
				 urlManager[urlStr]="1";
				 httpPost(queryObject, successFun, errorFun, urlStr);
			}
		} else {
			httpPost(queryObject, successFun, errorFun, urlStr);
		}
	};
	
	getCookie = function(name) {
		var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
		if(arr=document.cookie.match(reg))
			return unescape(arr[2]);
		else
			return "";
	}
	
	//发送请求
	var httpPost = function(queryObject, successFun, errorFun, urlStr) {
		//调用发送请求
		 $http(queryObject).success(function(response, status, headers, config) {
				 delete urlManager[urlStr];
		 		 successFun(response, status, headers, config);
		 	}).error(function(response, status, headers, config) {
				delete urlManager[urlStr];
				if (status == 403) {
					window.top.location.href = "/index.html";
				}else if(status==500){
					window.top.alert("系统异常，请联系管理员！");
				}else if(status==501){
					if(typeof errorFun == "function") {
						errorFun(response); 
					} else {
						window.top.alert(response.message);
					}
				}else if(typeof errorFun == "function") {
					errorFun(response); 
				}else if(status==0){
					
				}else if(errorFun== undefined || errorFun=="" || errorFun==null){
					window.top.alert(response.message);
				}
		 });
	}
	
	var tksign = function(queryObject, url) {
		var sign="xxxx";
		if (queryObject.params != undefined) {
			queryObject.params.sign=sign;
		} else {
			queryObject.url+="&sign="+sign;
		}
	}
		
	var getRootPath=function(){
		//获取当前网址
	    var curWwwPath=window.document.location.href;
	    //获取主机地址之后的目录
	    var pathName=window.document.location.pathname;
	    if (pathName == "" || pathName == "/") {
	    	if(curWwwPath.substr(curWwwPath.length-1,curWwwPath.length)=="/"){
	    		return curWwwPath.substr(0,curWwwPath.length-1);
	    	}
	    	return curWwwPath;
	    }
	    var pos=curWwwPath.indexOf(pathName);
	    //获取主机地址，
	    var localhostPath=curWwwPath.substring(0,pos);
	 /*   if(localhostPath.indexOf("localhost")!=-1 || localhostPath.indexOf("127.0.0.1")!=-1){
	    	//获取带"/"的项目名
	        var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	        
	        return(localhostPath+projectName);
	    }else{*/
	    	return localhostPath;
	   // }
	    
	};
	var getQueryString= function(name) {
		   var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		   var r = window.location.search.substr(1).match(reg);
		   if (r != null) return unescape(r[2]); return null;
	   };
	
	 var alertMsg={
			 text:"",
			 isShow:false,
			 callBack:"",
			 alert:function(text,callBack){
				 alertMsg.isShow=true;
				 alertMsg.text=$sce.trustAsHtml(text);
				 if(callBack!=undefined && callBack!="" && callBack!=null){
					 alertMsg.callBack=callBack; 
				 }
			 },
			 sure:function(){
				 alertMsg.isShow=false;
				 alertMsg.text="";
				 if(alertMsg.callBack!=undefined && alertMsg.callBack!="" && alertMsg.callBack!=null){
					 alertMsg.callBack();
				 }
			 },
			 close:function(){
				 alertMsg.isShow=false;
				 alertMsg.text="";
			 }
	 };
	 
	 $rootScope.alertMsg=alertMsg;
	
	 var confirmMsg={
			 text:"",
			 isShow:false,
			 callBack:"",
			 close:function(){
				 confirmMsg.isShow=false;
				 confirmMsg.text="";
			 },
			 cancel:function(){
				 if(confirmMsg.cancelCallBack!=undefined && confirmMsg.cancelCallBack!="" &&
					 confirmMsg.cancelCallBack!=null){
					 confirmMsg.cancelCallBack();
				 }
				confirmMsg.isShow=false;
				confirmMsg.text="";
			 },
			 confirm:function(text,sureCallBack,cancelCallBack,sureText,cancelText){
				 confirmMsg.text=text;
				 confirmMsg.isShow=true;
				 confirmMsg.callBack=sureCallBack;
				 if(cancelCallBack!=undefined){
					 confirmMsg.cancelCallBack=cancelCallBack;
				 }
				 if(sureText!=undefined&&sureText!=""){
					 confirmMsg.sureText=sureText;
				 }else{
					 confirmMsg.sureText="确认";
				 }
				 if(cancelText!=undefined&&cancelText!=""){
					 confirmMsg.cancelText=cancelText;
				 }else{
					 confirmMsg.cancelText="取消";
				 }
			 },
			 sure:function(){
				 confirmMsg.callBack();
				 confirmMsg.isShow=false;
				 confirmMsg.text="";
			 }
			 
	 };
	 $rootScope.confirmMsg=confirmMsg;
	 
	 var time={
			 getNowDate:function(){
					var myDate = new Date();
				    var fullYear=myDate.getFullYear();
				    var month=myDate.getMonth()+1;
				    var months = myDate.getMonth();
				    var date=myDate.getDate();
				    if(month<10){
				  	  month="0"+month;
				    }
				    if(date<10){
				  	  date="0"+date;
				    }
				   var starTime = fullYear+"-"+month+"-"+date;
				   return starTime;
				 }
	 };
	 //打开tab页面的方法
	 var openTab=function(id,name,url){
		 window.top.openTab(id,name,url);
	 };
	//关闭一个页面并回到父页面
	 var closeToParentTab=function(isReload){
		 window.top.closeToParentTab(isReload);
	 };
	//关闭一个页面
	 var closeTab=function(){
		 window.top.close();
	 }
	
	 
    var service = {
   	    "postUrl":postUrl,//发送ajax请求
   	    "getRootPath":getRootPath,//获取网站的根路径
   	    "getQueryString":getQueryString,//获取地址里面某个属性的值
   	    "alert":window.top.alert,
   	    "confirm":window.top.confirm,
   	    "getNowDate":time.getNowDate,// 获取当前时间，格式  yyyy-mm-dd
   	    "idEncode":idEncode,
   	    "openTab":openTab,//打开一个tab页面
   	    "closeToParentTab":closeToParentTab,//关闭一个页面后，回到父页面
   	    "closeTab":closeTab//关闭一个页面
    };
    
    return service;
}]);
 /**这个用于获取url的参数  **/
 function getQueryString(name) {
	   var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	   var r = window.location.search.substr(1).match(reg);
	   if (r != null) return unescape(r[2]); return null;
 } 
 /**设置cookie**/
 function setCookie(name,value)
 {
	 var Days = 30;
	 var exp = new Date();
	 exp.setTime(exp.getTime() + Days*24*60*60*1000);
	 document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
 }
 /**获取cookie**/
 function getCookie(name)
 {
	 var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	 if(arr=document.cookie.match(reg))
	 return unescape(decodeURIComponent(arr[2].replace("\"","").replace("\"","")));
	 else
	 return null;
 }
 
 

 function getTableWidth(){
 		var divWidth=document.getElementsByTagName("table")[0].parentElement.clientWidth;
 		var thArr=document.getElementsByTagName("th");
 		var tdArr=document.getElementsByTagName("td");
 		var firstWidth=thArr[0].clientWidth;
 		var secondWidth=thArr[1].clientWidth;
 		if(divWidth>10+(firstWidth+secondWidth*(thArr.length-1))){
 			for(i=1;i<thArr.length;i++){
 				thArr[i].style.width=divWidth/(thArr.length)+"px";
 				//tdArr[i].style.width=divWidth/(tdArr.length)+"px";
 			}
 			document.getElementsByTagName("table")[0].style.width=divWidth+"px";
 		}else{
 			document.getElementsByTagName("table")[0].style.width=10+(firstWidth+secondWidth*(thArr.length-1))+"px";
 		}
 }