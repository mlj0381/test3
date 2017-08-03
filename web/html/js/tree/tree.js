
var treeApp=angular.module("treeApp", []);

treeApp.directive('myTree',[function(){
	
	var myTree=
			{
			  restrict: 'E',
			  templateUrl: '/js/tree/treeView.html',
			  scope: {
				  treeData: '=',//绑定对应的树形数据
				  canChecked: '=',//是否显示check
				  textField: '@',//显示的文本名称
				  itemClicked: '&',//点击节点的回调函数
				  itemCheckedChanged: '&',//点击check的回调函数
				  itemTemplateUrl: '@'
			  },
			 itemExpended:function(item, $event){
				 //点击展开节点
				 item.$$isExpend = ! item.$$isExpend;
				 $event.stopPropagation();
			 },
			 getItemIcon : function(item){
				 //获取节点前面的图标
				 var isLeaf = myTree.isLeaf(item);
				 if(isLeaf){
					 return 'fa fa-leaf';
				 }
				 return item.$$isExpend ? 'fa fa-minus': 'fa fa-plus';	 
			 },
			 isLeaf:function(item){
				 //判断是否叶子节点
				return !item.childMenus || !item.childMenus.length; 
			 },
			 changeParentAndChild:function(treeData,item){
				 //如果是选中的，需要把该节点的父节点全部选中；需要把该节点的子节点全部选中
				 //如果是取消，需要判断父节点下面的子几点是否都取消了，如果都取消，需要把父节点去掉；需要把该节点的子节点都去掉
				 
				this.setChildCheck(item,item.$$isChecked);
				var parentItem=this.getParentItem(treeData,item);
				if(parentItem!=""){
					this.setParentCheck(treeData,parentItem,item.$$isChecked);
				}
				
			 },
			 setChildCheck:function(item,value){
				 //设置单前的节点的子节点
				 item.$$isChecked=value;
				 if(item.childMenus!=undefined && item.childMenus.length>0){
					 for(var index in item.childMenus){
						 this.setChildCheck(item.childMenus[index],value);
					 }
				 }
			 },
			 isChildCheck:function(item){
				 //判断子节点是否有选中的数据
				 if(item.childMenus!=undefined && item.childMenus.length>0){
					 for(var index in item.childMenus){
						 if(item.childMenus[index].$$isChecked==true){
							 return true;
						 }
						if( this.isChildCheck(item.childMenus[index])){
							return true;
						}
					 }
					 return false;
				 }
				 
			 },
			 setParentCheck:function(treeData,item,value){
				 //设置当前节点的父节点数据
				 if(value){
					 //如果子节点是选中效果
					 item.$$isChecked=value;
					 
				 }else{
					 //如果子节点是取消效果
//					 if(!this.isChildCheck(item)){
//						 item.$$isChecked=value;
//					 }
				 }
				 var parentItem=this.getParentItem(treeData,item);
				 if(parentItem!=""){
					 this.setParentCheck(treeData,parentItem,value);
				 }
			 },
			 getParentItem:function(treeData,currItem){
				 //获取当前节点的父级节点
				 if(treeData.childMenus!=undefined && treeData.childMenus.length>0){
					 for(var index in treeData.childMenus){
						 if(treeData.childMenus[index].menuId==currItem.menuId){
							 return treeData;
						 }else{
							var retVl =this.getParentItem(treeData.childMenus[index],currItem);
							if(retVl!=""){
								return retVl;
							}
						 }
					 }
				 }
				 return "";
			 },
			 controller:['$scope',function($scope){
				 $scope.itemExpended = myTree.itemExpended;
				 $scope.getItemIcon = myTree.getItemIcon;
				 $scope.isLeaf = myTree.isLeaf;
				 $scope.warpCallback =function(callback, item, $event){
					 if(callback=="itemCheckedChanged"){
						 myTree.changeParentAndChild($scope.treeData,item);
					 }
					 //回调函数
					  ($scope[callback] || angular.noop)({
						 $item:item,
						 $event:$event
					 });
				 };
				 
			 }]
		 };
	 return myTree;
}]);
