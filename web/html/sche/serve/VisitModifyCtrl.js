var myApp = angular.module("ApplicationApp", ['commonApp']);
myApp.controller("ApplicationCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	$scope.visitInfoState=[];
	var myManage={
		init:function(){
			$scope.query = {};
			$scope.add = {};
			$scope.audit = {};
			this.initSetXing();
			this.bindEvent();
			this.initQuery();
			this.getState();
		},
		bindEvent:function(){
			$scope.param = this.param;
		    $scope.doSave = this.doSave;
			$scope.close = this.close;
			$scope.starOn = this.starOn;
		},
		close:function(){
			commonService.closeToParentTab();
		},
		params:{
		},
		getState:function(){
			var url = "staticDataBO.ajax?cmd=getVisitInfoState";
			commonService.postUrl(url,"",function(data){
				if(data!=null && data!=undefined && data!=""){
					$scope.visitInfoState=data.items;
	 	    	}
			});
		},
		initSetXing:function(){
			var stepW = 25;
		   // var description = new Array("一般差，回去再练练","真的是差，都不忍心说你了","很差","","差");
		    var stars = $("#star > li");
		    var descriptionTemp;
		    $("#showb").css("width",0);
		    stars.each(function(i){
		        $(stars[i]).click(function(e){
		            var n = i+1;
		            $("#showb").css({"width":stepW*n});
		        //    descriptionTemp = description[i];
		        	selectStar = $(this).find('a').html();
		            $(this).find('a').blur();
		            $scope.starOn(n);
		            if(e && e.preventDefault)
				           e.preventDefault();
				    else
				           window.event.returnValue = false;
				    return false;
		        });
		    });
		},
		initQuery:function(){
    		var id = getQueryString("id");
    		var flag = getQueryString("flag");
			var param1 = {"id":id,"flag":flag};
			var url = "serveBO.ajax?cmd=doQueryVisit";
			commonService.postUrl(url,param1,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.add.question = data.items[0].QUESTION;
					$scope.starOn(data.items[0].LEVEL);
					$scope.add.state = data.items[0].STATE;
					$scope.add.ext1 = data.items[0].EXT1;
					$scope.add.ext2 = data.items[0].EXT2;
					$scope.add.ext3 = data.items[0].EXT3;
					$scope.add.ext4 = data.items[0].EXT4;
					$scope.add.ext5 = data.items[0].EXT5;
				}
				setContentHeigth();
			});
		},
		starOn:function(level){
			$("#one-star").removeClass("on");
			$("#two-stars").removeClass("on");
			$("#three-stars").removeClass("on");
			$("#four-stars").removeClass("on");
			$("#five-stars").removeClass("on");
			if(level==1){
				$(".one-star").addClass("on");
			}
			if(level==2){
				$(".one-star").addClass("on");
				$(".two-stars").addClass("on");
			}
			if(level==3){
				$(".one-star").addClass("on");
				$(".two-stars").addClass("on");
				$(".three-stars").addClass("on");
			}
			if(level==4){
				$(".one-star").addClass("on");
				$(".two-stars").addClass("on");
				$(".three-stars").addClass("on");
				$(".four-stars").addClass("on");
			}
			if(level==5){
				$(".one-star").addClass("on");
				$(".two-stars").addClass("on");
				$(".three-stars").addClass("on");
				$(".four-stars").addClass("on");
				$(".five-stars").addClass("on");
			}
		},
		doSave:function(){
			var wayBill=getQueryString("wayBill");
    		if(wayBill==null||wayBill==undefined|| wayBill==''){
    			commonService.alert("运单号为空!");
    			return false;
    		}
	    	$scope.add.level = selectStar;
	    	$scope.add.id = getQueryString("id");
	    	$scope.add.wayBill = wayBill;
			var param = $scope.add;
			var url = "serveBO.ajax?cmd=doVisitSave";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					commonService.alert("处理成功!");
					$scope.close();
				}
			});
		}
	};
	myManage.init();
}]);
