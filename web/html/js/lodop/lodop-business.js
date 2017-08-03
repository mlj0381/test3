// 对Date的扩展，将 Date 转化为指定格式的String   
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，   
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)   
// 例子：   
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423   
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18   
Date.prototype.format = function(fmt) { //author: meizz   
  var o = {   
    "M+" : this.getMonth()+1,                 //月份   
    "d+" : this.getDate(),                    //日   
    "h+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
};

/**
 * 获取默认的值，如果数据无效
 */
function getDefaultValue(vl){
	if(vl==undefined || vl==null ){
		return "";
	}
	return vl;
}

/*****联运汇***开始***/

/**
 * 发货人信息
 * stationName  始发站
 * stationPhone 始发站电话
 * sendName     发货人名称
 * sendPhone    发货人电话
 * sendAddr     发货人地址
 * sourceStaffPhones 始发站客服电话集合
 */
function sourceStationInfo(stationName,stationPhone,sendName,sendPhone,sendAddr,sourceStaffPhones){
	this.stationName=getDefaultValue(stationName);
	this.stationPhone=getDefaultValue(stationPhone);
	this.sendName=getDefaultValue(sendName);
	this.sendPhone=getDefaultValue(sendPhone);
	this.sendAddr=getDefaultValue(sendAddr);
	//支持多客服打印
	if(sourceStaffPhones.length == 1){
		this.sourceStaffPhone1 = getDefaultValue(sourceStaffPhones[0]); //打在上面
	}else if(sourceStaffPhones.length == 2){
		this.sourceStaffPhone1 = getDefaultValue(sourceStaffPhones[0]); //打在上面
		this.sourceStaffPhone2 = getDefaultValue(sourceStaffPhones[1]); //打在下面
	}
}
/**
 * 收货人信息
 * @param stationName 达到站名称
 * @param stationPhone 达到站电话
 * @param receiveName   收货人
 * @param receivePhone  收货人电话
 * @param receiveAddr   收货人地址
 * @param destCityName 目的城市
 * @param destCounty 到达站区域
 * @param streetName 到达街道
 * @param destCountyStreetAddr 区域＋街道＋地址
 * @param rNameNotPhone  类似这样的数据： 师傅合作商1-转:李奕野(15920117244)，后面的号码不需要显示：师傅合作商1-转:李奕野
 * @param standbyPhone 备用电话
 * @param tenantId  租户
 * @param descStaffPhones 目的站客服电话集合
 * @param desStationNameHjwl 鸿吉 打印地址 鸿吉打印 到最后选中的地址级别 （选择到市 打印市选择到区就打印区、选择到镇就打印到镇）
 * 
 */
function destStationInfo(stationName,stationPhone,receiveName,receivePhone,receiveAddr,destCityName,destCounty,streetName,destCountyStreetAddr,rNameNotPhone,standbyPhone,tenantId,descStaffPhones,desStationNameHjwl){
	this.stationName=getDefaultValue(stationName);
	this.stationPhone=getDefaultValue(stationPhone);
	this.receiveName=getDefaultValue(receiveName);
	this.receivePhone=getDefaultValue(receivePhone);
	this.receiveAddr=getDefaultValue(receiveAddr);
	this.destCityName=getDefaultValue(destCityName);
	this.destCounty=getDefaultValue(destCounty)=="" ? this.destCityName :getDefaultValue(destCounty);
	this.destCountyStreetAddr=getDefaultValue(destCountyStreetAddr);
	this.rNameNotPhone=getDefaultValue(rNameNotPhone);
	this.standbyPhone=getDefaultValue(standbyPhone);
	//this.streetAddr=streetName+receiveAddr;
	this.receiveAllPhone=this.receivePhone;
	if(this.standbyPhone!=undefined && this.standbyPhone!=""){
		//收货人号码，备用号码 合一起
		this.receiveAllPhone=this.receiveAllPhone+","+this.standbyPhone;
	}
	//东邦的需求：如果有县就只是打印县，如果没有县就打印市 
	//1、东邦物流地址为河北廊坊市霸州市胜芳镇时只打印胜芳镇；
	//2、东邦地址河北保定市高碑店市白沟镇时只打印白沟镇。
	
	this.stationNameOfDB=this.destCityName;
	if(this.destCounty!=undefined && this.destCounty!=""){
		this.stationNameOfDB=this.destCounty;
	}
	if(tenantId == 220 || tenantId == "220"){
		if(streetName == "胜芳镇" || streetName == "白沟镇"){
			this.stationNameOfDB = streetName;
		}
	}

	
	//南哥旧版面单（收货人打印拆分）20161111
	var receiveAllPhones = this.receiveAllPhone.split(",");
	if(receiveAllPhones.length == 1){
		this.receivePhoneNg1 = receiveAllPhones[0];
	}else{
		if(receiveAllPhones[0] == "" ){
			this.receivePhoneNg2 = receiveAllPhones[1];
		}else{
			this.receivePhoneNg2 = receiveAllPhones[0];
			this.receivePhoneNg3 = receiveAllPhones[1];
		}
	}
	

	//支持多客服打印
	if(descStaffPhones.length == 1){
		this.desStaffPhone1 = getDefaultValue(descStaffPhones[0]); //打在上面
	}else if(descStaffPhones.length == 2){
		this.desStaffPhone1 = getDefaultValue(descStaffPhones[0]); //打在上面
		this.desStaffPhone2 = getDefaultValue(descStaffPhones[1]); //打在下面
	}
	
	//打印地址 鸿吉打印 到最后选中的地址级别 （选择到市 打印市选择到区就打印区、选择到镇就打印到镇）(20161129)
	this.desStationNameHjwl = desStationNameHjwl;
		
}
/**
 * 货物信息
 * @param goodsName  货物名称
 * @param packageType 包装
 * @param packageNum  件数
 * @param weight      重量
 * @param volume      体积
 * @param declaredValue 申明价值
 * @param premiumCost 保险费
 * @param installCost 配安费
 * @param freight     运费
 * @param collectingMoney 代收货款
 * @param isInsuranceName  已投保 （远行需打印） 
 */
function goodsDetailInfoDtl(goodsName,packageType,packageNum,weight,volume,declaredValue,premiumCost,installCost,freight,collectingMoney,isInsuranceName){
	this.goodsName=getDefaultValue(goodsName);
	this.packageType=getDefaultValue(packageType);
	this.packageNum=getDefaultValue(packageNum);
	this.weight=getDefaultValue(weight);
	this.volume=getDefaultValue(volume);
	this.declaredValue=getDefaultValue(declaredValue==0?"":declaredValue);
	
	this.premiumCost=getDefaultValue(premiumCost==0?"":premiumCost);
	this.installCost=getDefaultValue(installCost==0?"":installCost);
	this.freight=getDefaultValue(freight==0?"":freight);
	this.collectingMoney=getDefaultValue(collectingMoney==0?"":collectingMoney);
	this.weightAndVolume=this.weight+"/"+this.volume;
	this.isInsuranceName = isInsuranceName;
}
/**
 * 费用信息
 * @param freight          运费
 * @param installCost      配安费用
 * @param premiumCost      保险费
 * @param pickingCost      提货费
 * @param otherCost        其他费用
 * @param collectingMoney  代收货款
 * @param totalCost        总费用
 * @param discountTotal    回扣费用
 * @param discountTotal    回扣费用
 * @param upstairsFeeTotal 上楼费
 */
function feeInfo(freight,installCost,premiumCost,pickingCost,otherCost,collectingMoney,totalCost,discountTotal,upstairsFeeTotal){
	
	this.freight=(isNaN(freight)==true || freight==0?"":freight);
	this.installCost=(isNaN(installCost)==true || installCost==0 ?"":installCost);
	this.premiumCost=(isNaN(premiumCost)==true || premiumCost==0 ?"":premiumCost);
	this.pickingCost=(isNaN(pickingCost)==true || pickingCost==0?"":pickingCost);
	this.collectingMoney=(isNaN(collectingMoney)==true || collectingMoney==0?"":collectingMoney);
	this.totalCost=(isNaN(totalCost)==true || totalCost==0 ?"":totalCost);
	this.totalCostChinese=isNaN(totalCost)==true || totalCost==0?"":transformChargeToChinese(totalCost);
	
	
	var moneyList=tranChineseToList(this.totalCostChinese);
	if(moneyList.length>0)
		this.totalMoneyYuan=moneyList[4];
	if(moneyList.length>1)
		this.totalMoneyShi=moneyList[3];
	if(moneyList.length>2)
		this.totalMoneyBai=moneyList[2];
	if(moneyList.length>3)
		this.totalMoneyQian=moneyList[1];
	if(moneyList.length>4)
		this.totalMoneyWang=moneyList[0];
	
	
	this.collectingMoneyChinese=isNaN(collectingMoney)==true || collectingMoney==0 ?"":transformChargeToChinese(collectingMoney);
	
	this.discountTotal=discountTotal;
	
	this.discountTotalOfYH="H:"+discountTotal;
	this.discountTotalOfAZ="NO:"+discountTotal;
	
	//2016111新增 dxb
	this.upstairsFeeTotal = (isNaN(upstairsFeeTotal)==true || upstairsFeeTotal==0 ?"":upstairsFeeTotal);;
	if(collectingMoney != ""){
		//代收货款拆分
		var moneyCollectingList=tranChineseToList(this.collectingMoneyChinese);
		if(moneyCollectingList.length>0)
			this.collectingMoneyYuan=moneyCollectingList[4];
		if(moneyCollectingList.length>1)
			this.collectingMoneyShi=moneyCollectingList[3];
		if(moneyCollectingList.length>2)
			this.collectingMoneyBai=moneyCollectingList[2];
		if(moneyCollectingList.length>3)
			this.collectingMoneyQian=moneyCollectingList[1];
		if(moneyCollectingList.length>4)
			this.collectingMoneyWang=moneyCollectingList[0];
	}
		
	
	
	
	 
}
/**
 * 其他信息
 * @param trackingNum  	运单号
 * @param deliveryType  交货方式
 * @param isVerification 是否核销
 * @param isReceipt      是否签回单
 * @param paymentType     付款方式 (存在一种付款方式存在值，存在2种付款方式空)
 * @param paymentType2    付款方式 (存在2种付款付款1 存在一种付款方式空)
 * @param paymentType3    付款方式 (存在2种付款付款2 存在一种付款方式空) 
 * @param createDate     制单时间
 * @param creater        制单人
 * @param remarks        备注
 * @param serviceType    家装服务
 * @param consignee  仓管员
 * @param custOrder  第三方运单
 * @param isSeaTransport 是否海运（吉航特殊要求）
 * @param goodsNumberName 货号
 */
function otherInfo(trackingNum,deliveryType,isVerification,isReceipt,paymentType,paymentType2,paymentType3,
		createDate,creater,remarks,serviceType,consignee,custOrder,isSeaTransport,goodsNumberName){
	this.trackingNum=getDefaultValue(trackingNum);
	this.deliveryType=getDefaultValue(deliveryType);
	this.isVerification=isVerification==true ?"是":"否";
	this.isReceipt=isReceipt==true ?"是":"否";
	this.paymentType=getDefaultValue(paymentType);
	this.paymentType2 = getDefaultValue(paymentType2);
	this.paymentType3 = getDefaultValue(paymentType3);
	this.createDate=getDefaultValue(createDate);
	this.creater=getDefaultValue(creater);
	this.remarks=getDefaultValue(remarks);
	this.serviceType=getDefaultValue(serviceType);
	this.deliveryAndserviceType=this.deliveryType+"+"+this.serviceType;
	this.consignee=getDefaultValue(consignee);
	this.custOrder=getDefaultValue(custOrder);
	this.isSeaTransportJH = isSeaTransport == true ? "海运" : "汽运"
	
	var dateList=tranDateToList(this.createDate);
	if(dateList.size!=3){
		this.dateYear=getDefaultValue(dateList[0]);
		this.dateMonth=getDefaultValue(dateList[1]);
		this.dateDay=getDefaultValue(dateList[2]);
	}
	//旭阳升的备注：家装服务＋备注
	this.remarksOfXYS=this.serviceType+"  "+this.remarks + "（"+this.trackingNum+"）";
	
//	this.dateYear="2016";
	
	//2016111新增 dxb
	//南哥特殊处理(旧面单)
	//自提
	this.deliveryTypeNg1 = deliveryType == "自提" ? "√" : "";
	//送货
	this.deliveryTypeNg2 = deliveryType == "配送到楼下" ? "√" : "";
	//送货上楼
	this.deliveryTypeNg3 = deliveryType == "配送上楼" ? "√" : "";
	var paymentTypeNg1 = "";
	var paymentTypeNg2 = "";
	var paymentTypeNg3 = "";
	if(paymentType == null || paymentType == ""){
		paymentTypeNg1 = (paymentType2 != null && paymentType2 != undefined && paymentType2.indexOf("到付")> -1) ? "√" : "";
		if(paymentTypeNg1 == ""){
			paymentTypeNg1 = (paymentType3 != null && paymentType3 != undefined &&  paymentType3.indexOf("到付")> -1) ? "√" : "";
		}
		
		paymentTypeNg2 =  (paymentType2 != null && paymentType2 != undefined && paymentType2.indexOf("现付") >-1) ? "√" : "";
		if(paymentTypeNg2 == ""){
			paymentTypeNg2 = (paymentType3 != null && paymentType3 != undefined && paymentType3.indexOf("现付")> -1) ? "√" : "";
		}
		
		paymentTypeNg3 =  (paymentType2 != null && paymentType2 != undefined && paymentType2.indexOf("月结")> -1) ? "√" : "";
		if(paymentTypeNg3 == ""){
			paymentTypeNg3 = (paymentType3 != null && paymentType3 != undefined && paymentType3.indexOf("月结")> -1) ? "√" : "";
		}
	}else{
		
		paymentTypeNg1 =paymentType == "到付" ? "√" : "";
		paymentTypeNg2 = paymentType == "现付" ? "√" : "";	
		paymentTypeNg3 = paymentType == "月结" ? "√" : "";	
		
		
	}
	
	this.paymentTypeNg1 = paymentTypeNg1;
	this.paymentTypeNg2 = paymentTypeNg2;
	this.paymentTypeNg3 = paymentTypeNg3;
	
	this.goodsNumberName=goodsNumberName;
}

/**
 * 亿航特殊信息
 * goodsName  品名
 * packageNum 包装件数
 * weight     重量
 * declaredValue    声明价格
 * volume 总体积
 */
function goodsDetailInfoDtlOfYH(goodsName,packageNum,weight,declaredValue,volume){
	this.goodsName=getDefaultValue(goodsName);
	this.packageNum=getDefaultValue(packageNum);
	this.weight=getDefaultValue(weight);
	this.declaredValue=getDefaultValue(declaredValue);
	this.volume=getDefaultValue(volume);
}

/**
 * 20161110新增
 * 南哥特殊物品信息(包括收件地址信息)
 * goodsName  品名
 * packages 包装
 * weight     总重量
 * count      总件数
 * volume     总体积
 * desProvince    省
 * desCity     市
 * desCounty   区
 * desDtl    详细地址
 */
function goodsDetailInfoDtlOfNG(goodsName,packages,weight,count,volume,desProvince,desCity,desCounty,desDtl){
	this.goodsNameNg = getDefaultValue(goodsName);
	this.packagesNg = getDefaultValue(packages);
	this.weightNg = getDefaultValue(weight == 0 ? "" :weight );
	this.volumeNg = getDefaultValue(volume == 0 ? "" : volume);
	this.countNg = getDefaultValue(count == 0 ? "" : count);
	this.desProvinceNg = getDefaultValue(desProvince);
	this.desCityNg = getDefaultValue(desCity);
	this.desCountyNg = getDefaultValue(desCounty);
	this.desDtlNg = getDefaultValue(desDtl);
}


/**
 * 亿航罗浮宫特殊信息
 * goodsType 1.常规家具 m³，2.玻璃 ㎡，3.大理石 ㎡，4.工艺品 m³
 * 
 */
function goodsDetailInfoDtlOfYHLFG(goodsType){
	this.goodsType = goodsType;
//	for(var i = 0;i<goodsType.length;i++){
//		eval("this.goodsTypes"+ i + "=" + goodsType[i]);
//	}
	this.goodsTypes0=goodsType[0];
	this.goodsTypes1=goodsType[1];
	this.goodsTypes2=goodsType[2];
	this.goodsTypes3=goodsType[3];
}

/**
 * 亿航罗浮宫特殊信息
 * @param 总物流配送费 deliveryCostsTotal
 * @param 货价声明 goodsPriceTotal
 * @param 保价费 offerTotal
 * @param 总费用  totalFee
 * @param 总运费 freightTotal
 * @param 总费用中文 totalFeeChinese
 * @param 是否投保 isDover
 */
function feeInfoDtlOfYHLFG(deliveryCostsTotal,goodsPriceTotal,offerTotal,totalFee,freightTotal){
	this.deliveryCostsTotal =  (isNaN(deliveryCostsTotal) == true || deliveryCostsTotal == 0 ? "" : deliveryCostsTotal);
	this.goodsPriceTotal = (isNaN(goodsPriceTotal) == true || goodsPriceTotal == 0 ? "" : goodsPriceTotal);
	this.offerTotal =  (isNaN(offerTotal) == true || offerTotal == 0 ? "" : offerTotal);
	this.totalFeeChinese = (isNaN(totalFee) == true || totalFee == 0 ? "" : transformChargeToChinese(totalFee));
	this.freightTotal = (isNaN(freightTotal) == true || freightTotal == 0 ? "" : freightTotal);
	this.totalFee = (isNaN(totalFee) == true || totalFee == 0 ? "" : totalFee);
	var moneyList=tranChineseToList(this.totalFeeChinese);
	if(moneyList.length>0)
		this.totalMoneyYuan=moneyList[4];
	if(moneyList.length>1)
		this.totalMoneyShi=moneyList[3];
	if(moneyList.length>2)
		this.totalMoneyBai=moneyList[2];
	if(moneyList.length>3)
		this.totalMoneyQian=moneyList[1];
	if(moneyList.length>4)
		this.totalMoneyWang=moneyList[0];
	this.isDover = this.offerTotal > 0 ? "√" : "";
}

/**
 * 亿航罗浮宫特殊信息
 * @param 付款方式 paymentType,paymentType2
 * @param 自提 deliveryTypeNg1
 * @param 直送 isDirectLFG
 * @param 总件数 packageCountsTotal
 * @param 总货品名 goodsNameTotal
 * @param 运单号 trackingNum
 * @param 创建时间 createDate
 * @param 创建人 creater
 * @param 备注 remarks
 * @param 收货人详细地址 destAddress
 * 
 */
function otherInfoDtlOfYHLFG(paymentType,paymentType2,deliveryType,isDirect,packageCountsTotal,goodsNameTotal,
		trackingNum,createDate,creater,remarks,destAddress){
	this.paymentType = getDefaultValue(paymentType);
	this.paymentType2 = getDefaultValue(paymentType2);
	this.deliveryTypeLFG1 = deliveryType == "1" ? "√" : "";
	this.isDirectLFG = isDirect == true ? "√" : "";
	this.packageCountsTotal = getDefaultValue(packageCountsTotal);
	this.goodsNameTotal = getDefaultValue(goodsNameTotal);
	this.trackingNum = getDefaultValue(trackingNum);
	this.createDate=getDefaultValue(createDate);
	this.creater=getDefaultValue(creater);
	this.remarks=getDefaultValue(remarks);
	this.destAddress = getDefaultValue(destAddress);
	var dateList=tranDateToList(this.createDate);
	if(dateList.size!=3){
		this.dateYear=getDefaultValue(dateList[0].substring(dateList[0].length-1,dateList[0].length));
		this.dateMonth=getDefaultValue(dateList[1]);
		this.dateDay=getDefaultValue(dateList[2]);
	}
}
/**
 * @param sourceStationInfo 始发站信息
 * @param destStationInfo   目的站信息
 * @param otherInfoDtlOfYHLFG
 * @param feeInfoDtlOfYHLFG
 * @param goodsDetailInfoDtlOfYHLFG
 */
function ExpressBeanByYHLFG(sourceStationInfo,destStationInfo,otherInfoDtlOfYHLFG,feeInfoDtlOfYHLFG,goodsDetailInfoDtlOfYHLFG){
	this.sourceStationInfo =sourceStationInfo;
	this.destStationInfo = destStationInfo;
	this.otherInfoDtlOfYHLFG = otherInfoDtlOfYHLFG;
	this.feeInfoDtlOfYHLFG = feeInfoDtlOfYHLFG;
	this.goodsDetailInfoDtlOfYHLFG=goodsDetailInfoDtlOfYHLFG;
}

/****
 * 打印的逻辑方法(面单)
 * @param sourceStationInfo 始发站信息
 * @param destStationInfo   目的站信息
 * @param goodsDetailInfos  货物信息列表
 * @param feeInfo           费用信息
 * @param otherInfo         其他信息
 * @param goodsDetailInfo 亿航货品信息
 * @param goodsDetailInfoNg 南哥旧面单处理
 * @param otherInfoDtlOfYHLFG
 * @param feeInfoDtlOfYHLFG
 * @param goodsDetailInfoDtlOfYHLFG
 */
function ExpressBean(sourceStationInfo,destStationInfo,goodsDetailInfos,feeInfo,otherInfo,goodsDetailInfoDtl
		,goodsDetailInfoNg,otherInfoDtlOfYHLFG,feeInfoDtlOfYHLFG,goodsDetailInfoDtlOfYHLFG) {
	this.sourceStationInfo=sourceStationInfo;
	this.destStationInfo=destStationInfo;
	this.goodsDetailInfos=goodsDetailInfos;
	this.goodsDetailInfoDtl=goodsDetailInfoDtl;
	this.feeInfo=feeInfo;
	this.otherInfo=otherInfo;
	this.goodsDetailInfoNg = goodsDetailInfoNg;
	this.otherInfoDtlOfYHLFG = otherInfoDtlOfYHLFG;
	this.feeInfoDtlOfYHLFG = feeInfoDtlOfYHLFG;
	this.goodsDetailInfoDtlOfYHLFG=goodsDetailInfoDtlOfYHLFG;
	
	
	/***下面的是测试CASE 现在没用了 */
	this.print=function(){
		this.printSourceStationInfo(sourceStationInfo);
		this.printDestStationInfo(destStationInfo);
		this.printFeeInfo(feeInfo);
		this.printGoodsDetailInfo(goodsDetailInfos);
		this.printOtherInfo(otherInfo);
	};
	//打印始发站信息
	this.printSourceStationInfo=function(sourceStationInfo){
			LODOP.SET_PRINT_STYLE('FontSize', '13');
			LODOP.SET_PRINT_STYLE('Bold', 4);// 粗体
			LODOP.ADD_PRINT_TEXT('20mm', '10.5mm', '53mm', '9mm' ,sourceStationInfo.stationName);
			LODOP.ADD_PRINT_TEXT('20mm', '52.5mm', '72mm', '9mm' ,sourceStationInfo.stationPhone);
			LODOP.ADD_PRINT_TEXT('31mm', '18.5mm', '51mm', '9mm' ,sourceStationInfo.sendName);
			LODOP.ADD_PRINT_TEXT('40mm', '14.5mm', '51mm', '9mm' ,sourceStationInfo.sendPhone);
			LODOP.ADD_PRINT_TEXT('47mm', '14.5mm', '51mm', '9mm' ,sourceStationInfo.sendAddr);
	};
	//打印始发站信息
	this.printDestStationInfo=function(destStationInfo){
		
			LODOP.SET_PRINT_STYLE('FontSize', '14');
			LODOP.SET_PRINT_STYLE('Bold', 4);// 粗体
			LODOP.ADD_PRINT_TEXT('20mm', '94.5mm', '53mm', '9mm' ,destStationInfo.stationName);
			LODOP.ADD_PRINT_TEXT('20mm', '138.5mm', '72mm', '9mm' ,destStationInfo.stationPhone);
			LODOP.ADD_PRINT_TEXT('31mm', '103.5mm', '51mm', '9mm' ,destStationInfo.receiveName);
			LODOP.ADD_PRINT_TEXT('40mm', '99.5mm', '51mm', '9mm' ,destStationInfo.receivePhone);
			LODOP.ADD_PRINT_TEXT('47mm', '99.5mm', '51mm', '9mm' ,destStationInfo.receiveAddr);
	};
	
	//打印费用信息
	this.printFeeInfo=function(feeInfo){
		
			LODOP.SET_PRINT_STYLE('FontSize', '14');
			LODOP.SET_PRINT_STYLE('Bold', 4);// 粗体
			LODOP.ADD_PRINT_TEXT('21mm', '187.5mm', '53mm', '9mm' ,feeInfo.freight);
			LODOP.ADD_PRINT_TEXT('28mm', '187.5mm', '72mm', '9mm' ,feeInfo.installCost);
			LODOP.ADD_PRINT_TEXT('34mm', '187.5mm', '51mm', '9mm' ,feeInfo.premiumCost);
			LODOP.ADD_PRINT_TEXT('40mm', '187.5mm', '51mm', '9mm' ,feeInfo.pickingCost);
			LODOP.ADD_PRINT_TEXT('66mm', '171.5mm', '51mm', '9mm' ,feeInfo.totalCost);
			LODOP.ADD_PRINT_TEXT('74mm', '171.5mm', '51mm', '9mm' ,feeInfo.totalCostChinese);
			LODOP.ADD_PRINT_TEXT('91mm', '171.5mm', '51mm', '9mm' ,feeInfo.collectingMoney);
			LODOP.ADD_PRINT_TEXT('99mm', '171.5mm', '51mm', '9mm' ,feeInfo.collectingMoneyChinese);
			
			
	};
	
	//打印费用信息
	this.printGoodsDetailInfo=function(goodsDetailInfos){
			LODOP.SET_PRINT_STYLE('FontSize', '14');
			LODOP.SET_PRINT_STYLE('Bold', 4);// 粗体
			var left=65;
			for(var i=0;i<goodsDetailInfos.length;i++){
				left=left+7*i;
				var goods=goodsDetailInfos[i];
				LODOP.ADD_PRINT_TEXT(left+'mm', '7.5mm', '53mm', '9mm' ,goods.goodsName);
				LODOP.ADD_PRINT_TEXT(left+'mm', '41.5mm', '72mm', '9mm' ,goods.packageType);
				LODOP.ADD_PRINT_TEXT(left+'mm', '63.5mm', '51mm', '9mm' ,goods.packageNum);
				LODOP.ADD_PRINT_TEXT(left+'mm', '84.5mm', '51mm', '9mm' ,goods.weight);
				LODOP.ADD_PRINT_TEXT(left+'mm', '106.5mm', '51mm', '9mm' ,goods.volume);
				LODOP.ADD_PRINT_TEXT(left+'mm', '124.5mm', '51mm', '9mm' ,goods.declaredValue);
			}
	};
	
	
	//打印其他信息
	this.printOtherInfo=function(otherInfo){
			LODOP.SET_PRINT_STYLE('FontSize', '14');
			LODOP.SET_PRINT_STYLE('Bold', 4);// 粗体
			
			LODOP.ADD_PRINT_TEXT('8mm', '174.5mm', '53mm', '9mm' ,otherInfo.trackingNum);
			LODOP.SET_PRINT_STYLE('FontSize', '11');
			LODOP.SET_PRINT_STYLE('Bold', 4);// 粗体
			LODOP.ADD_PRINT_TEXT('90mm', '15mm', '72mm', '9mm' ,otherInfo.deliveryType+"+"+otherInfo.serviceType);
			
			LODOP.SET_PRINT_STYLE('FontSize', '18');
			LODOP.SET_PRINT_STYLE('Bold', 5);// 粗体
			
			LODOP.ADD_PRINT_TEXT('90mm', '56.5mm', '51mm', '9mm' ,otherInfo.isVerification);
			
			LODOP.SET_PRINT_STYLE('FontSize', '13');
			LODOP.SET_PRINT_STYLE('Bold', 4);// 粗体
			
			LODOP.ADD_PRINT_TEXT('90mm', '94.5mm', '51mm', '9mm' ,otherInfo.isReceipt);
			LODOP.ADD_PRINT_TEXT('90mm', '140.5mm', '51mm', '9mm' ,otherInfo.paymentType);
			LODOP.ADD_PRINT_TEXT('16mm', '180.5mm', '51mm', '9mm' ,otherInfo.createDate);
			LODOP.ADD_PRINT_TEXT('124mm', '174.5mm', '51mm', '9mm' ,otherInfo.creater);
			LODOP.ADD_PRINT_TEXT('99mm', '70.5mm', '71mm', '9mm' ,otherInfo.remarks);
//			LODOP.ADD_PRINT_TEXT('94mm', '2mm', '50mm', '9mm' ,"家装服务："+otherInfo.serviceType);
			
	};
}


function getRootPath(){
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

/**
 * 打印快递单信息
 * @param expressBean : 快递单bean(见ExpressBean)
 * @param taskName : 任务名称(可为空)
 * lodop备注 : 打印任务名，字符型参数，由开发者自主设定，未限制长度，字符要求符合Windows文件起名规则，Lodop会根据该名记忆相关的打印设置、打印维护信息。
 * 若strTaskName空，控件则不保存本地化信息，打印全部由页面程序控制。
 * 系统备注 : 格式要求为 --> 子系统_模块名_任务名
 */
var LODOP; // 全局变量，外层不能覆盖此变量
function printExpressInfo(expressBean, taskName) {
	if (undefined == expressBean || expressBean == null) {
		alert('无法获取需要打印的快递单信息');
		return false;
	}
	if (undefined == taskName || taskName == null) {
		taskName = '';
	}
	LODOP = getLodop();
	// PRINT_INITA(Top,Left,Width,Height,strPrintName)
	LODOP.PRINT_INITA('0mm', '0mm', '253.89mm', "148mm", taskName);
	LODOP.ADD_PRINT_SETUP_BKIMG('<img border="0" src="' + getRootPath()+'/js/lodop/110.jpg?ver='+new Date()+'">');
//	LODOP.ADD_PRINT_SETUP_BKIMG("<img border='0' src='http://localhost:14002/js/lodop/110.jpg'>");
	LODOP.SET_SHOW_MODE('BKIMG_TOP', 0);
	// SET_PRINT_PAGESIZE(intOrient, PageWidth,PageHeight,strPageName)
	LODOP.SET_PRINT_PAGESIZE(0, "250mm", "140mm", "LodopCustomPage");
	LODOP.SET_SHOW_MODE("NP_NO_RESULT", true);// 解决谷歌浏览器长时间无反应是提示弹出框的问题
	LODOP.SET_SHOW_MODE('BKIMG_IN_PREVIEW',1);
	LODOP.SET_SHOW_MODE("BKIMG_PRINT",0);
	
	LODOP.SET_SHOW_MODE("BKIMG_WIDTH",'203mm');
	LODOP.SET_SHOW_MODE("BKIMG_HEIGHT",'140mm');
	LODOP.SET_SHOW_MODE("BKIMG_TOP",'-4.4mm');	
	LODOP.SET_SHOW_MODE("BKIMG_LEFT",'-1.0mm');

	
	/******************将业务信息加入打印******************/
	
	expressBean.print();
	


//	 “BKIMG_LEFT”：设置背景图位置X值
//	 “BKIMG_TOP”：设置背景图位置Y值
//	 “BKIMG_WIDTH”：设置背景图宽度
//	 “BKIMG_HEIGHT”：设置背景图高度
	LODOP.PREVIEW();
}

/**
 * 把日期格式：2016-10-13 的格式拆分 成：
 *       2016,10,13 
 */
function tranDateToList(date){
	var dateList=date.split("-");
	return dateList;
}


/**
 * 只是支持到万
 * 
 * 把中文的金额转换成列表：
 * 
 *  一万三千七百二十三元
 * 
 * @chinese 金额中文  
 */
function tranChineseToList(chinese){
	var retList=new Array();
  
	retList[0]='零';//万
  retList[1]='零';//千
  retList[2]='零';//百
  retList[3]='零';//十
  retList[4]='零';//元
  
	if(chinese==undefined || chinese=="" || chinese==null){
		return retList;
	}
	
  var wang=chinese.split('万');
  var qianStr="";
  if(wang.length>1 && wang[0]!=""){
    retList[0]=wang[0].replace("零","");
    qianStr=wang[1];
  }else{
    qianStr=wang[0];
  }
  
  var qian=qianStr.split('仟');
  var baiStr="";
  if(qian.length>1 && qian[0]!=""){
    retList[1]=qian[0].replace("零","");
    baiStr=qian[1];
  }else{
    baiStr=qian[0];
  }
  
  var bai=baiStr.split('佰');
  var shiStr="";
  if(bai.length>1 && bai[0]!=""){
    retList[2]=bai[0].replace("零","");
    shiStr=bai[1];
  }else{
    shiStr=bai[0];
  }
  
  var shi=shiStr.split('拾');
  var yuanStr="";
  if(shi.length>1 && shi[0]!=""){
    retList[3]=shi[0].replace("零","");
    yuanStr=shi[1];
  }else{
    yuanStr=shi[0];
  }
  
  var yuan=yuanStr.split('元');
  
  if(yuan.length>1 && yuan[0]!=""){
    retList[4]=yuan[0].replace("零","");
  }
  
  
	return retList;
}



/**
 * 将费用转换为大写的字符串
 */
function transformChargeToChinese(charge) {
	charge += ''; 
	for (i = charge.length - 1; i >= 0; i--) {
		charge = charge.replace(",", "")// 替换tomoney()中的“,”
		charge = charge.replace(" ", "")// 替换tomoney()中的空格
	}
	charge = charge.replace("￥", "")// 替换掉可能出现的￥字符
	if (isNaN(charge)) { // 验证输入的字符是否为数字
		alert("请检查小写金额是否正??");
		return;
	}
	// 字符处理完毕后开始转换，采用前后两部分分别转换
	part = String(charge).split(".");
	newchar = "";
	// 小数点前进行转化
	for (i = part[0].length - 1; i >= 0; i--) {
		if (part[0].length > 10) {
			alert("位数过大，无法计算");
			return "";
		}// 若数量超过拾亿单位，提示
		tmpnewchar = ""
		perchar = part[0].charAt(i);
		switch (perchar) {
			case "0":
				tmpnewchar = "零" + tmpnewchar;
				break;
			case "1":
				tmpnewchar = "壹" + tmpnewchar;
				break;
			case "2":
				tmpnewchar = "贰" + tmpnewchar;
				break;
			case "3":
				tmpnewchar = "叁" + tmpnewchar;
				break;
			case "4":
				tmpnewchar = "肆" + tmpnewchar;
				break;
			case "5":
				tmpnewchar = "伍" + tmpnewchar;
				break;
			case "6":
				tmpnewchar = "陆" + tmpnewchar;
				break;
			case "7":
				tmpnewchar = "柒" + tmpnewchar;
				break;
			case "8":
				tmpnewchar = "捌" + tmpnewchar;
				break;
			case "9":
				tmpnewchar = "玖" + tmpnewchar;
				break;
		}
		switch (part[0].length - i - 1) {
			case 0:
				tmpnewchar = tmpnewchar + "元";
				break;
			case 1:
				if (perchar != 0)
					tmpnewchar = tmpnewchar + "拾";
				break;
			case 2:
				if (perchar != 0)
					tmpnewchar = tmpnewchar + "佰";
				break;
			case 3:
				if (perchar != 0)
					tmpnewchar = tmpnewchar + "仟";
				break;
			case 4:
				tmpnewchar = tmpnewchar + "万";
				break;
			case 5:
				if (perchar != 0)
					tmpnewchar = tmpnewchar + "拾";
				break;
			case 6:
				if (perchar != 0)
					tmpnewchar = tmpnewchar + "佰";
				break;
			case 7:
				if (perchar != 0)
					tmpnewchar = tmpnewchar + "仟";
				break;
			case 8:
				tmpnewchar = tmpnewchar + "亿";
				break;
			case 9:
				tmpnewchar = tmpnewchar + "拾";
				break;
		}
		newchar = tmpnewchar + newchar;
	}
	//小数点之后进行转化
	if (charge.indexOf(".") != -1) {
		if (part[1].length > 2) {
			// alert("小数点之后只能保留两位,系统将自动截断");
			part[1] = part[1].substr(0, 2)
		}
		for (i = 0; i < part[1].length; i++) {
			tmpnewchar = ""
			perchar = part[1].charAt(i)
			switch (perchar) {
			case "0":
				tmpnewchar = "零" + tmpnewchar;
				break;
			case "1":
				tmpnewchar = "壹" + tmpnewchar;
				break;
			case "2":
				tmpnewchar = "贰" + tmpnewchar;
				break;
			case "3":
				tmpnewchar = "叁" + tmpnewchar;
				break;
			case "4":
				tmpnewchar = "肆" + tmpnewchar;
				break;
			case "5":
				tmpnewchar = "伍" + tmpnewchar;
				break;
			case "6":
				tmpnewchar = "陆" + tmpnewchar;
				break;
			case "7":
				tmpnewchar = "柒" + tmpnewchar;
				break;
			case "8":
				tmpnewchar = "捌" + tmpnewchar;
				break;
			case "9":
				tmpnewchar = "玖" + tmpnewchar;
				break;
			}
			if (i == 0)
				tmpnewchar = tmpnewchar + "角";
			if (i == 1)
				tmpnewchar = tmpnewchar + "分";
			newchar = newchar + tmpnewchar;
		}
	}
	//替换所有无用汉字
	while (newchar.search("零零") != -1)
		newchar = newchar.replace("零零", "零");
	newchar = newchar.replace("零亿", "亿");
	newchar = newchar.replace("亿万", "亿");
	newchar = newchar.replace("零万", "万");
	newchar = newchar.replace("零元", "元");
	newchar = newchar.replace("零角", "");
	newchar = newchar.replace("零分", "");
	if (newchar.charAt(newchar.length - 1) == "元" || newchar.charAt(newchar.length - 1) == "角")
		newchar = newchar + "整";
	return newchar;
}

/**
 * 将费用转换为大写的数组
 */
function transformChargeToChineseWithArray(charge) {
	if (undefined == charge)
		return null;
	charge += ''; 
	for (i = charge.length - 1; i >= 0; i--) {
		charge = charge.replace(",", "")// 替换tomoney()中的“,”
		charge = charge.replace(" ", "")// 替换tomoney()中的空格
	}
	charge = charge.replace("￥", "")// 替换掉可能出现的￥字符
	if (isNaN(charge)) { // 验证输入的字符是否为数字
		alert("请检查小写金额是否正确");
		return null;
	}
	
	var index = charge.indexOf('.');
	var intStr = ''; // 整数部分
	var decimalStr = '';// 小数部分
	if (index > 0) {// 既有整数，又有小数
		intStr = charge.substr(0, index);
		decimalStr = charge.substr(index + 1);
	} else if (index == 0) {// 只有小数部分
		decimalStr = charge.substr(index + 1);
	} else {// 只有整数部分
		intStr = charge;
	}
	
	var wan = parseInt(parseInt(intStr) / 10000);
	var qian = parseInt((parseInt(intStr) % 10000) / 1000);
	var bai = parseInt((parseInt(intStr) % 1000) / 100);
	var shi = parseInt((parseInt(intStr) % 100) / 10);
	var ge = parseInt(parseInt(intStr) % 10);
	
	var result = new Array();
	if (wan > 0) {
		result.push(swithWanToChinese(wan));
	} else {
		result.push('零');
	}
	result.push(swithNumberToChinessNumber(qian));
	result.push(swithNumberToChinessNumber(bai));
	result.push(swithNumberToChinessNumber(shi));
	result.push(swithNumberToChinessNumber(ge));
	
	// 处理小数点部分
	var decimalResult = '';
	for (var i = 0; i < decimalStr.length; i++) {
		var decimal = parseInt(decimalStr.charAt(i));
		if (i == 0) {
			decimalResult += (this.swithNumberToChinessNumber(decimal) + '角');
		} else if (i == 1) {
			decimalResult += (this.swithNumberToChinessNumber(decimal) + '分');
		}
	}
	if (decimalResult != '')
		result.push(decimalResult);
	return result;
}

function swithWanToChinese(number, unit) {
	if(undefined == unit || null == unit) {		
		unit = '';
	}
	var wan = parseInt(number / 10000);
	var qian = parseInt((number % 10000) / 1000);
	var bai = parseInt((number % 1000) / 100);
	var shi = parseInt((number % 100) / 10);
	var ge = parseInt(number % 10);
	var result = '';
	if (wan > 0) {
		result += swithWanToChinese(wan, '亿');
	}
	if(qian > 0) {
		result += (swithNumberToChinessNumber(qian) + '仟');
	}
	if(bai > 0) {
		result += (swithNumberToChinessNumber(bai) + '佰');
	}
	if(shi > 0) {
		result += (swithNumberToChinessNumber(shi) + '拾');
	}
	if(ge > 0) {
		result += swithNumberToChinessNumber(ge);
	}
	return result + unit;
}

function swithNumberToChinessNumber(number) {
	switch (number) {
		case 0:
			return "零";
		case 1:
			return "壹";
		case 2:
			return "贰";
		case 3:
			return "叁";
		case 4:
			return "肆";
		case 5:
			return "伍";
		case 6:
			return "陆";
		case 7:
			return "柒";
		case 8:
			return "捌";
		case 9:
			return "玖";
	}
	return "-"
}

/****************************************打印标签开始（按租户打印）**************************************/
/**
 * 标签信息
 * @param createDate 开单时间
 * @param tenantName: 租户名称(例如“众邦物流”)
 * @param sourceStaffPhones 始发地电话集合
 * @param desStaffPhones  目的地电话集合
 * @param sourceCityName : 开单网点市(例如“佛山”)
 * @param desCityName : 配送网点和配送区域(揭阳/揭阳区)
 * @param deliveryTypeName: 配送方式
 * @param goodsName: 品名(例如：威远药，多个用“/”分隔)
 * @param consignee : 收货人
 * @param goodsNo : 货物单号(运单号加货物数量，例如“28003278-30”)
 * @param detailAddress: 详细地址
 * @param sourceStation  始发站
 * @param desStation     目的站
 * @param trackingNum    运单号
 * @param totalPackageNum    件数
 * 
 */
function StickerInfo(trackingNum,totalPackageNum,tenantName, sourceStaffPhones, desStaffPhones, sourceCityName, desCityName, deliveryTypeName, goodsName, consignee, goodsNo, detailAddress,sourceStation,desStation) {
	this.tenantName = getDefaultValue(tenantName);
	this.sourceCityName = getDefaultValue(sourceCityName);
	this.desCityName = getDefaultValue(desCityName);
	this.deliveryTypeName = getDefaultValue(deliveryTypeName);
	this.goodsNo = getDefaultValue(goodsNo);
	//拼接电话(始发地)
	this.sourceStaffPhonesAlias = "";
	for( var i  in sourceStaffPhones){
		eval("this.sourceStaffPhonesOne"+i+"="+sourceStaffPhones[i]);
		if(i+ 1 != sourceStaffPhones.length){
			this.sourceStaffPhonesAlias = this.sourceStaffPhonesAlias + +sourceStaffPhones[i] + "|";
		}else{
			this.sourceStaffPhonesAlias = this.sourceStaffPhonesAlias + sourceStaffPhones[i];
		}
	}
	//拼接电话（目的站）
	this.desStaffPhonesAlias = "";
	for( var i  in desStaffPhones){
		eval("this.desStaffPhonesOne"+i+"="+desStaffPhones[i]);
		if(i+ 1 != desStaffPhones.length){
			this.desStaffPhonesAlias = this.desStaffPhonesAlias +  desStaffPhones[i] + "|";
		}else{
			this.desStaffPhonesAlias = this.desStaffPhonesAlias + desStaffPhones[i];
		}
	}
	this.goodsNameAlias = "货物品名："+getDefaultValue(goodsName);
	this.consigneeAlias = "客户名："+ getDefaultValue(consignee);
	this.detailAddressAlias = "地址："+getDefaultValue(detailAddress);
	this.sourceStationAlias = "始发站："+getDefaultValue(sourceStation);
	this.desStationAlias = "目的站："+getDefaultValue(desStation);
	this.trackingNumAlias = "运单号："+getDefaultValue(trackingNum);
	this.totalPackageNumAlias = "件数："+getDefaultValue(totalPackageNum);
	
	//原数据
	this.goodsNameOriginal = getDefaultValue(goodsName);
	this.consigneeOriginal = getDefaultValue(consignee);
	this.detailAddressOriginal = getDefaultValue(detailAddress);
	this.sourceStationOriginal = getDefaultValue(sourceStation);
	this.desStationOriginal = getDefaultValue(desStation);
	this.trackingNumOriginal =  getDefaultValue(trackingNum);
	this.totalPackageNumOriginal = getDefaultValue(totalPackageNum);
	
}

/**
 * 标签信息
 * @param createDate 开单时间
 * @param trackingNum票号
 * @param sourceStation 始发站
 * @param goodsNo 货号
 * @param creater 开单员
 * @param desStation 目的站
 * @param isSeaTransport 运输方式
 * @param consignee 收货人
 */
function StickerInfoDtlJH(createDate,trackingNum,sourceStation,goodsNo,creater,desStation,isSeaTransport,consignee) {
	this.createDate = getDefaultValue(createDate);
	this.trackingNum = getDefaultValue(trackingNum);
	this.sourceStation = getDefaultValue(sourceStation);
	this.goodsNo = getDefaultValue(goodsNo);
	this.creater = getDefaultValue(creater);
	this.desStation = getDefaultValue(desStation);
	this.isSeaTransport = isSeaTransport == true ? "普通海运" : "普通汽运";
	this.consignee = getDefaultValue(consignee);
	
	this.sourceStationName = "始站："+this.sourceStation;
	this.goodsNoName = "货号："+this.goodsNo;
	this.trackingNumName = "票号："+this.trackingNum;
	this.desStationName = "到站："+this.desStation;
	this.isSeaTransportName = "运输方式："+this.isSeaTransport;
}


/****
 * 打印的逻辑方法(标签)
 * @param stickerInfo 打印货物标签的信息
 */
function ExpressTagBean(stickerInfo,StickerInfoDtlJH) {
	this.stickerInfo = stickerInfo;
	this.stickerInfoDtlJH = StickerInfoDtlJH;
}

/**
 * 打印标签信息
 * @param stickerInfo : 贴纸bean(见StickerInfo)
 * @param taskName : 任务名称(可为空)
 * lodop备注 : 打印任务名，字符型参数，由开发者自主设定，未限制长度，字符要求符合Windows文件起名规则，Lodop会根据该名记忆相关的打印设置、打印维护信息。
 * 若strTaskName空，控件则不保存本地化信息，打印全部由页面程序控制。
 * 系统备注 : 格式要求为 --> 子系统_模块名_任务名
 */
function printStickerInfo(stickerInfo, taskName, pageNumber){
	if (undefined == stickerInfo || stickerInfo == null) {
		alert('无法获取需要打印的标签信息');
		return false;
	}
	if (undefined == taskName || taskName == null) {
		taskName = '';
	}
	if (undefined == pageNumber || pageNumber <= 0)
		pageNumber = 1;
	LODOP = getLodop();
	// PRINT_INITA(Top,Left,Width,Height,strPrintName)
	LODOP.PRINT_INITA('0mm', '0mm', '79.5mm', "49.5mm", taskName);
	// SET_PRINT_PAGESIZE(intOrient, PageWidth,PageHeight,strPageName)
	LODOP.SET_PRINT_PAGESIZE(0, "79.5mm", "49.5mm", "LodopCustomPage");
	LODOP.SET_PRINT_COPIES(pageNumber);
	LODOP.SET_SHOW_MODE("NP_NO_RESULT", true);// 解决谷歌浏览器长时间无反应是提示弹出框的问题
	LODOP.SET_PRINT_STYLE('Bold', 1);// 粗体
	
	var tenantName = stickerInfo.tenantName;
	LODOP.SET_PRINT_STYLE('FontSize', '16');
	if (undefined != tenantName) {// 租户名字
		// ADD_PRINT_TEXT(Top,Left,Width,Height,strContent)
		LODOP.ADD_PRINT_TEXT('7.0mm', '6.0mm', '30mm', '12mm', tenantName);
	}

	LODOP.SET_PRINT_STYLE('Bold', 0);// 粗体
	var tenantStaffPhone = stickerInfo.tenantStaffPhone;
	if (undefined != tenantStaffPhone) {// 客服电话
		var offsetX = 35;
		if (undefined != tenantName) {
			offsetX = 6.0 + tenantName.length * 5.6 + 4.0;
		}
		LODOP.SET_PRINT_STYLE('FontSize', '12');
		// ADD_PRINT_TEXT(Top,Left,Width,Height,strContent)
		LODOP.ADD_PRINT_TEXT('7.4mm', offsetX + 'mm', '40mm', '12mm', tenantStaffPhone);
	}
	
	var lineName = stickerInfo.lineName;
	if (undefined != lineName) {// 线路名称
		LODOP.SET_PRINT_STYLE('FontSize', '12');
		// ADD_PRINT_TEXT(Top,Left,Width,Height,strContent)
		LODOP.ADD_PRINT_TEXT('13.0mm', '19mm', '60mm', '12mm', lineName);
	}

	LODOP.SET_PRINT_STYLE('Bold', 1);// 粗体
	var source = stickerInfo.source;
	if (undefined != source) {// 开单网点(例如“佛山”)
		LODOP.SET_PRINT_STYLE('FontSize', '13');
		// ADD_PRINT_TEXT(Top,Left,Width,Height,strContent)
		LODOP.ADD_PRINT_TEXT('20mm', '6.0mm', '35mm', '10mm', source);

		var offsetX = 6.0 + source.length * 4.5 + 2.0;
		LODOP.SET_PRINT_STYLE('FontSize', '11');
		LODOP.ADD_PRINT_TEXT('20mm', (offsetX + 3.0) + 'mm', '10mm', '10mm', '至');
		LODOP.ADD_PRINT_TEXT('22mm', offsetX + 'mm', '30mm', '10mm', '——');
	}
	
	var dest = stickerInfo.dest;
	if (undefined != dest) {//  配送网点和配送区域(揭阳/揭阳区)
		LODOP.SET_PRINT_STYLE('FontSize', '13');
		// ADD_PRINT_TEXT(Top,Left,Width,Height,strContent)
		// 开单网点 ＋ 空格 + 至 + 空格
		var offsetX = source.length * 4.5 + 18.0;
		LODOP.ADD_PRINT_TEXT('20mm', offsetX + 'mm', '45mm', '10mm', dest);
	}
	
	var goodsName = stickerInfo.goodsName;
	if(undefined != goodsName) {	
		LODOP.SET_PRINT_STYLE('FontSize', '12');
		// ADD_PRINT_TEXT(Top,Left,Width,Height,strContent)
		LODOP.ADD_PRINT_TEXT('26.5mm', '6.0mm', '60mm', '10mm', '品名：' + goodsName);
	}
	
	var consignee = stickerInfo.consignee;
	if(undefined != consignee) {	
		LODOP.SET_PRINT_STYLE('FontSize', '12');
		// ADD_PRINT_TEXT(Top,Left,Width,Height,strContent)
		var offsetX = 30;
		if (undefined != goodsName) {
			offsetX = 6.0 + (3 + goodsName.length) * 4.2 + 6.5;
		}
		LODOP.ADD_PRINT_TEXT('26.5mm', offsetX + 'mm', '60mm', '10mm', '收货人：' + consignee);
	}
	
	var goodsNo = stickerInfo.goodsNo;
	if(undefined != goodsNo) {	
		LODOP.SET_PRINT_STYLE('Bold', 0);// 粗体
		LODOP.SET_PRINT_STYLE('FontSize', '12');
		// ADD_PRINT_TEXT(Top,Left,Width,Height,strContent)
		LODOP.ADD_PRINT_TEXT('33mm', '6.0mm', '60mm', '10mm', '货号：');

		LODOP.SET_PRINT_STYLE('Bold', 1);// 粗体
		LODOP.SET_PRINT_STYLE('FontSize', '18');
		LODOP.ADD_PRINT_TEXT('32.1mm', '18.0mm', '60mm', '10mm', goodsNo);
	}
	
	var deliveryTypeName = stickerInfo.deliveryTypeName;
	if(undefined != deliveryTypeName) {	// 配送方式
		LODOP.SET_PRINT_STYLE('FontSize', '12');
		// ADD_PRINT_TEXT(Top,Left,Width,Height,strContent)
		LODOP.ADD_PRINT_TEXT('33mm', '67mm', '14mm', '10mm', deliveryTypeName);
	}
	
	var detailAddress = stickerInfo.detailAddress;
	if (undefined != detailAddress) {
		LODOP.SET_PRINT_STYLE('FontSize', '12');
		// ADD_PRINT_TEXT(Top,Left,Width,Height,strContent)
		LODOP.ADD_PRINT_TEXT('39.5mm', '6mm', '67.5mm', '20mm', detailAddress);
	}
	LODOP.PREVIEW();
}

/****************************************打印标签结束**************************************/

function printTableInfo(tableElementId, taskName){
	if (undefined == tableElementId) {
		alert('请选择需要打印的表格信息');
		return false;
	}
	if (undefined == taskName || taskName == null) {
		taskName = '';
	}
	LODOP=getLodop();  
	LODOP.PRINT_INIT(taskName);
	LODOP.SET_PRINT_PAGESIZE(2, 0, 0, "A4");
	LODOP.SET_SHOW_MODE("LANDSCAPE_DEFROTATED",1);
	LODOP.ADD_PRINT_TABLE(5, 2, "99.8%", "95%", document.getElementById(tableElementId).innerHTML);
	LODOP.PREVIEW();	
}

/*************************************按租户打印***************************************************************/

/**
 * config: 对应Sys_Print_Config表对象
 * itemList: 对应Sys_Print_item表对象
 */
function PrintConfigBean(config, itemList) {
	this.config = config;
	this.itemList = itemList;
}

/**
 * fieldName: 域名
 * fieldValue: 域值
 * topOffset: 整页上边距（相对于打印top偏移量）
 * leftOffset: 整页左边距（相对于打印top偏移量）
 * itemWidth: 宽度
 * itemHeight: 高度
 * fontSize: 字体大小
 * fontBold: 是否加粗
 */
function PrintItemBean(fieldName, fieldValue, topOffset, leftOffset, itemWidth, itemHeight, fontSize, fontBold){
	this.fieldName = fieldName;
	this.fieldValue = fieldValue;
	this.topOffset = topOffset;
	this.leftOffset = leftOffset;
	this.itemWidth = itemWidth;
	this.itemHeight = itemHeight;
	this.fontSize = fontSize;
	this.fontBold = fontBold;
}

function isBlankObject(obj) {
	return undefined == obj || null == obj;
}

function getObjectFieldValue(object, field, defaultVale) {
	var fieldValue = undefined;
	if(field.indexOf('.') > 0) {
		var subFields = field.split('.');
		var subObject = undefined;
		for(var i = 0; i < subFields.length - 1; i++) {
			if (i == 0) {
				subObject = object[subFields[i]];
			} else {
				subObject = subObject[subFields[i]];
			}
			if (isBlankObject(subObject) || typeof(subObject) != 'object')
				break;
		}
		if (!isBlankObject(subObject)) 
			fieldValue = subObject[subFields[subFields.length - 1]];
	} else {
		fieldValue = object[field];
	}
	if (isBlankObject(fieldValue) && !isBlankObject(defaultVale))
		return defaultVale;
	return fieldValue;
}
/**
 * 转换打印单位，默认为毫米(mm)
 * @param printField
 * @param unit
 */
function convertPrintUnit(printField, unit) {
	if (isBlankObject(unit))
		unit = 'mm';
	if (isBlankObject(printField))
		printField = 0;
	return printField + unit;
}

function parsePrintItem(itemList, dataSource) {
	var retItems = new Array();
	doParsePrintItem(itemList, dataSource, retItems);
	return retItems;
}

function doParsePrintItem(itemList, dataSource, retItems, specialFieldName) {
	for(var i = 0; undefined != itemList && i < itemList.length; i++) {
		var item = itemList[i];
		var fieldName = item.objectKey;// 域名
		if (isBlankObject(fieldName))// 如果fieldName为空，则不解析
			continue;
		
		if (!isBlankObject(specialFieldName) && fieldName != specialFieldName) // 值解析特定的fieldName
			continue;
		
		if (!isBlankObject(getSpecailFieldPrintItem(retItems,fieldName)))// 已经解析过
			continue;
		
		var fieldValue = item.specialObjectValue;// 域值
		if (isBlankObject(fieldValue) || fieldValue == '') {
			fieldValue = getObjectFieldValue(dataSource, fieldName, '');
		}
		
		// 计算偏移量
		var posType = item.posType;// 位置类型
		var relObjectKey = item.relObjectKey;// 位置相对对象key
		var offsetValue = item.offsetValue;// 位置相对relObjectKey偏移值
		var topOffset = item.topOffset;// top偏移值 
		var leftOffset = item.leftOffset;// left偏移值
		var itemWidth = item.itemWidth;// 宽度
		var itemHeight = item.itemHeight;// 高度
		var fontBold = item.fontBold;// 是否粗体
		var fontSize = item.fontSize;// 字体大小

		if (posType == 2 || posType == 3) {// X轴偏移 或 Y轴偏移
			if (fieldName == relObjectKey && fieldValue instanceof Array) {// 自己跟自己关联
				for (var j = 0; j < fieldValue.length; j++) {
					var subField = fieldValue[j];
					var mm = convertFontSizeToMM(fontSize);
					if (j != 0) {
						if (posType == 2) {// X轴偏移
							leftOffset = leftOffset + mm * fieldValue[j - 1].length + offsetValue;
						} else {// Y轴偏移
							topOffset = topOffset + mm + offsetValue;
						}
					}
					retItems.push(new PrintItemBean(fieldName + '_' + j, subField, topOffset, leftOffset, itemWidth, itemHeight, fontSize, fontBold));
				}
			} else if (!isBlankObject(relObjectKey) && fieldName != relObjectKey) {
				var relObjectItem = getSpecailFieldPrintItem(relObjectKey, retItems);
				if (isBlankObject(relObjectItem)) {// 没有的话，就解析
					doParsePrintItem(itemList, dataSource, retItems, relObjectKey);
					relObjectItem = getSpecailFieldPrintItem(relObjectKey, retItems);
				}
				if (!isBlankObject(relObjectItem)) {
					var mm = convertFontSizeToMM(relObjectItem.fontSize);
					if (posType == 2) {// X轴偏移
						leftOffset = relObjectItem.leftOffset + mm * relObjectItem.fieldValue.length + offsetValue;
						topOffset = relObjectItem.topOffset;
					} else {// Y轴偏移
						leftOffset = relObjectItem.leftOffset;
						topOffset = relObjectItem.topOffset + mm + offsetValue;
					}
					retItems.push(new PrintItemBean(fieldName, fieldValue, topOffset, leftOffset, itemWidth, itemHeight, fontSize, fontBold));
				}
			}// TODO 其他情况忽略
		} else {// 固定位置
			retItems.push(new PrintItemBean(fieldName, fieldValue, topOffset, leftOffset, itemWidth, itemHeight, fontSize, fontBold));
		}
	}
}

/**
 * 获取已经获得解析的打印项
 * 
 * @param fieldName
 * @param retItems
 * @returns
 */
function getSpecailFieldPrintItem(fieldName, retItems) {
	var retItem = undefined;
	for (var i = 0; i < retItems.length; i++) {
		var item = retItems[i];
		if (fieldName == item.fieldName) {
			retItem = item;			
			break;
		}
	}
	return retItem;
}

/**
 * 将字体磅数转换为毫米
 * 
 * @param fontSize
 * @returns
 */
function convertFontSizeToMM(fontSize) {
	if (fontSize == 5) {// 八号字体
		return 1.74;
	} else if (fontSize == 5.5) {// 七号
		return 1.94;
	} else if (fontSize == 6.5) {// 小六
		return 2.29;
	} else if (fontSize == 7.5) {// 六号
		return 2.65;
	} else if (fontSize == 9) {// 小五
		return 3.18;
	} else if (fontSize == 10.5) {// 五号
		return 3.70;
	} else if (fontSize == 12) {// 小四
		return 4.32;
	} else if (fontSize == 13) {
		return 4.63;
	} else if (fontSize == 14) {// 四号
		return 4.94;
	} else if (fontSize == 15) {// 小三
		return 5.29;
	} else if (fontSize == 16) {// 三号
		return 5.64;
	} else if (fontSize == 18) {// 小二
		return 6.35;
	} else if (fontSize == 22) {// 二号
		return 7.76;
	} else if (fontSize == 24) {// 小一
		return 8.47;
	} else if (fontSize == 26) {// 一号
		return 9.17;
	} else if (fontSize == 36) {// 小初
		return 12.70;
	} else if (fontSize == 42) {// 初号
		return 14.82;
	}
	return 0;// 其他字体一律返回0，表示不支持
	
}




/**
 * 通用打印方法(不支持表格打印)
 * 
 * @param printConfigBean: 见
 * @param pageNumber: 打印份数
 */
function commonPrint(printConfigBean, dataSource, pageNumber) {
	var config = printConfigBean.config;// 打印配置信息
	var itemList = printConfigBean.itemList;// 打印项
	if (isBlankObject(printConfigBean) || isBlankObject(config) || isBlankObject(itemList) || itemList.length == 0) {
		alert('无法获取需要打印的信息');
		return;
	}
	var taskName = getObjectFieldValue(config, 'bizName', '打印任务');// 任务名称
	if (undefined == pageNumber || pageNumber <= 0)
		pageNumber = 1;
	// 处理打印项
	var printItmes = parsePrintItem(itemList, dataSource);
	if (isBlankObject(printItmes) || printItmes.length == 0) {
		alert('无法获取需要打印的信息');
		return;
	}
	
	LODOP = getLodop();
	//设置账号
	LODOP.SET_LICENSES("","CA7D06E8F6CBCEF583213ABFCB278404","C94CEE276DB2187AE6B65D56B3FC2848","");
	// PRINT_INITA(Top,Left,Width,Height,strPrintName)
	LODOP.PRINT_INITA(convertPrintUnit(config.topOffset), convertPrintUnit(config.leftOffset), convertPrintUnit(config.editableWidth), convertPrintUnit(config.editableHeight), taskName);
	// SET_PRINT_PAGESIZE(intOrient, PageWidth,PageHeight,strPageName)
	LODOP.SET_PRINT_PAGESIZE(config.intOrient, convertPrintUnit(config.pageWidth), convertPrintUnit(config.pageHeight), config.pageName);
	if (!isBlankObject(config.bkimgName) && !isBlankObject(config.bkimgPrint) && config.bkimgPrint == 1) {// 存在打印预览背景图片
		var imageFullPath = getRootPath() + '/js/lodop/' + config.bkimgName+'?ver='+new Date();
		LODOP.ADD_PRINT_SETUP_BKIMG('<img border="0" src="' + imageFullPath + '">');
		LODOP.SET_SHOW_MODE("BKIMG_WIDTH", convertPrintUnit(config.bkimgWidth));
		LODOP.SET_SHOW_MODE("BKIMG_HEIGHT", convertPrintUnit(config.bkimgHeight));
		LODOP.SET_SHOW_MODE("BKIMG_TOP", convertPrintUnit(config.bkimgTop));	
		LODOP.SET_SHOW_MODE("BKIMG_LEFT", convertPrintUnit(config.bkimgLeft));
		LODOP.SET_SHOW_MODE('BKIMG_IN_PREVIEW', config.bkimgPrint);
		LODOP.SET_SHOW_MODE("BKIMG_PRINT",0);
	}
	LODOP.SET_SHOW_MODE("NP_NO_RESULT", true);// 解决谷歌浏览器长时间无反应是提示弹出框的问题
	LODOP.SET_PRINT_COPIES(pageNumber);// 打印页数
	for (var i = 0; undefined != printItmes && i < printItmes.length; i++) {
		var item = printItmes[i];
		LODOP.SET_PRINT_STYLE('FontSize', item.fontSize);
		LODOP.SET_PRINT_STYLE('Bold', item.fontBold);
		// ADD_PRINT_TEXT(Top,Left,Width,Height,strContent)
		LODOP.ADD_PRINT_TEXT(convertPrintUnit(item.topOffset), convertPrintUnit(item.leftOffset), convertPrintUnit(item.itemWidth), convertPrintUnit(item.itemHeight), item.fieldValue);
	}
	//可以连接的打印机的数量
	/**
	var count=LODOP.GET_PRINTER_COUNT();
	
	if(count>1){
		LODOP.SELECT_PRINTER();
	}
	**/
	//数据库配置增加字段判断是否打印预览（默认不打印）
	if(config.previewTrue == 1){
		LODOP.PREVIEW();
	}else{
		LODOP.PRINT();
	}
	
	
	
}







