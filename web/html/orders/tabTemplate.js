
var tabhead_order=[
	{state:1,two:'two1',url:'disOrders.html',name:'未分配',value:'waitDis'},
	{state:8,two:'two2',url:'timeOutOrders.html',name:'超时',value:'timeOutDis'},
	{state:2,two:'two3',url:'waitPickOrders.html',name:'提货',value:'waitPickup'},
	{state:3,two:'two4',url:'carryPackingOrders.html',name:'拉包中',value:'carryPackaging'},
	{state:10,two:'two5',url:'waitDepartOrders.html',name:'待发车',value:'waitDepart'},
	{state:4,two:'two6',url:'inTransitOrders.html',name:'运输中',value:'inTransit'},
	{state:5,two:'two7',url:'waitDeliveryOrders.html',name:'待配送',value:'waitDelivery'},
	{state:6,two:'two8',url:'waitSignOrders.html',name:'待签收',value:'waitSign'},
	{state:7,two:'two9',url:'doSignOrders.html',name:'已签收',value:'doSign'},
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
//      templateUrl: 'ordDetail/costShow.html',
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
