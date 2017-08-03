
 /**
  * 配载详情
  * templateUrl 模版文件地址
  * url 弹出窗口地址
  * detailData 请求的数据返回的对象
  * name 弹窗的对象
  * 
  * 对外提供 show 方法，入参为请求地址的参数
  * 
  * 相对 myDetailWin 
  * 
  */
 commonApp.directive('departDetailWin',['commonService',function(commonService){
	   var departDetail={};
	   departDetail.restrict="AE";
	   departDetail.replace=true;
	   departDetail.templateUrl=function(element, attrs) {
			return attrs.templateurl;
		};
		departDetail.compile=function(element, attrs){
			var url=attrs.url;
			var detailObj=attrs.detaildata;
			var winObj=attrs.name;
			var callBack=attrs.callBack;
			return function($scope, element, attrs){
				var win={
						isShow:false,
						collectingMoney:0,
						freightCollect:0,
						show:function(param){
							if(param.batchNum==null||param.batchNum==undefined ||param.batchNum==''){
								commonService.alert("请选择一条配载信息");
								return false;
							}
							/**
							 * 货源编号解密
							 **/
//							var base64 = new Base64();
//							if(!isNaN(param.goodsId)){
//								
//							}else{
//								var pwd = base64.decode(param.goodsId);
//								if(pwd.indexOf("{zx}")>0){
//									pwd=pwd.substring(2, (pwd.length-6));
//									param.goodsId=pwd;
//								}
//							}
							var queryString="batchNum="+param.batchNum;
							commonService.postUrl(url,queryString,function(data){
								var collectingMoney=0;
								var freightCollect=0;
								for (var i = 0; i < data.depart.list.length; i++) {
									collectingMoney=data.depart.list[i].collectingMoney;
									freightCollect=data.depart.list[i].freightCollect
								}
								win.freightCollect=freightCollect;
								win.collectingMoney=collectingMoney;
								//成功执行
								eval("$scope."+detailObj+ "= data");
								win.isShow=true;
							});
						},
						close:function(){
							win.topMenuId=2;
							win.isShow=false;
						},
				};
				eval("$scope."+winObj+ "= win");
			};
		};
		
		return departDetail;
}]);

