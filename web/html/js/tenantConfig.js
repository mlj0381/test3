//运单录入的货品列的配置
function OrderGoodsRowConfig(data){
	
	var goodsRow={
			discount:true,
			collectingMoney:true,
			procedureFee:true,
			goodsPrice:true,
			offer:true,
			handingCosts:true,
			packingCosts:true,
			pickingCosts:true,
			deliveryCosts:true,
			upstairsFee:true,
			installCount:true,
			custOrder:true,
			goodsType:true
	};
	if(data!=undefined){
		for(var vl in data){
			var list=data[vl];
			eval("goodsRow."+list.name+"="+list.value);
		}
	}
	
	return {
		discount:goodsRow.discount,
		collectingMoney:goodsRow.collectingMoney,
		procedureFee:goodsRow.procedureFee,
		goodsPrice:goodsRow.goodsPrice,
		goodsType:goodsRow.goodsType,
		offer:goodsRow.offer,
		handingCosts:goodsRow.handingCosts,
		packingCosts:goodsRow.packingCosts,
		pickingCosts:goodsRow.pickingCosts,
		deliveryCosts:goodsRow.deliveryCosts,
		upstairsFee:goodsRow.upstairsFee,
		installCount:goodsRow.installCount,
		custOrder:goodsRow.custOrder
	};
}

//运单录入运输类型
function shipTypeConfig(data){
	
	var shipType={
			isSeaTransport:true
	};
	if(data!=undefined){
		for(var vl in data){
			var list=data[vl];
			eval("shipType."+list.name+"="+list.value);
		}
	}
	return {
		isSeaTransport:shipType.isSeaTransport
	};
}

/**配载打印控制器*/
function showDataFunction(data){
	var showData={
			maxPageSize : 28,
			minPageSize : 24,
			showLable :  false,
	};
	
	if(data!=undefined){
		for(var vl in data){
			var list=data[vl];
			eval("showData."+list.name+"="+list.value);
		}
	}
	return showData;
    
}
function TenantConfig(){
		var _tenantConfig=this;
		this._initObj=function (data){
		//初始化对象
			_tenantConfig.orderGoodsRowConfig=new OrderGoodsRowConfig(data.goodsRow);
			_tenantConfig.shipTypeConfig=new shipTypeConfig(data.shipType);
			_tenantConfig.showDataFunction = new showDataFunction(data.showData);
		};
		this.initData=function (data){
			_tenantConfig._initObj(data);
		};
		
};

var tenantConfig = new TenantConfig();



