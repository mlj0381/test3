

var tabhead_order=[
	{state:-1,two:'two1',url:'trackManage.html',name:'所有',value:'trackManage'},
	{state:13,two:'two2',url:'waitDepartTracks.html',name:'待发车',value:'waitDepartTracks'},
	{state:45,two:'two3',url:'inTransitTracks.html',name:'运输中',value:'inTransitTracks'},
	{state:2,two:'two4',url:'waitDeliveryTracks.html',name:'待配送',value:'waitDeliveryTracks'},
	{state:610,two:'two5',url:'waitSignTracks.html',name:'待签收',value:'waitSignTracks'},
	{state:7,two:'two6',url:'doSignTracks.html',name:'已签收',value:'doSignTracks'},
	{state:89,two:'two7',url:'cancelTracks.html',name:'取消',value:'cancelTracks'}
	];

/**
 * 费用
 * 
 * 
 * tabhead:[{state:1,two:two1,url:disOrders.html?v=${ver},name:未分配,value:waitDis}]
 */
commonApp.directive("tabShow",['commonService',"$timeout",function(commonService,$timeout){

	return {
		restrict: 'E',
        scope: {
        	"orderstate":"=orderstate",
        	"isQuery":"=isquery",
        	"urlTab":"@urltab",
        	"headNum":"@headnum"
        },
		templateUrl : function(tElement,tAttrs){
	      	return 'tabTemplate.html?ver='+tAttrs.ver;
	    },
        link: function($scope, elem, attrs) {
        	eval("$scope.tabhead=tabhead_"+$scope.headNum);
        	//console.log($scope.tabhead);
        	$scope.load = function(){
        		if($scope.isQuery==null||$scope.isQuery==undefined||$scope.isQuery==''||$scope.isQuery==1){
        		var url = $scope.urlTab;
	        		commonService.postUrl(url,{"orderstate":$scope.orderstate},function(data){
	        			$scope.statistics=data;
	        		});
        		}
        	};
        	$scope.load($scope.isQuery);
        	$scope.$watch('isQuery',function(oldVal,newVal){
        			$timeout(function(){
        				$scope.load($scope.isQuery);
        			},500);
        	});
        }
	};
}]);
