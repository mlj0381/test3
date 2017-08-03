var CreatedOKLodop7766 = null;
var LODOP; // 声明为全局变量
function getLodop(oOBJECT, oEMBED) {
	/***************************************************************************
	 * 本函数根据浏览器类型决定采用哪个页面元素作为Lodop对象： IE系列、IE内核系列的浏览器采用oOBJECT，
	 * 其它浏览器(Firefox系列、Chrome系列、Opera系列、Safari系列等)采用oEMBED,
	 * 如果页面没有相关对象元素，则新建一个或使用上次那个,避免重复生成。 64位浏览器指向64位的安装程序install_lodop64.exe。
	 **************************************************************************/

	var strHtmInstall = "<br><font color='#FF00FF'>打印控件未安装!点击这里<a href='../../jsp/print/install_lodop32.exe' target='_self' style='color:red;font-weight: bold;'>执行安装</a>,安装后请刷新页面或重新进入。</font>";
	var strHtmUpdate = "<br><font color='#FF00FF'>打印控件需要升级!点击这里<a href='../../jsp/print/install_lodop32.exe' target='_self' style='color:red;font-weight: bold;'>执行升级</a>,升级后请重新进入。</font>";
	var strHtm64_Install = "<br><font color='#FF00FF'>打印控件未安装!点击这里<a href='../../jsp/print/install_lodop64.exe' target='_self' style='color:red;font-weight: bold;'>执行安装</a>,安装后请刷新页面或重新进入。</font>";
	var strHtm64_Update = "<br><font color='#FF00FF'>打印控件需要升级!点击这里<a href='../../jsp/print/install_lodop64.exe' target='_self' style='color:red;font-weight: bold;'>执行升级</a>,升级后请重新进入。</font>";
	var strHtmFireFox = "<br><br><font color='#FF00FF'>（注意：如曾安装过Lodop旧版附件npActiveXPLugin,请在【工具】->【附加组件】->【扩展】中先卸它）</font>";
	var strHtmChrome = "<br><br><font color='#FF00FF'>(如果此前正常，仅因浏览器升级或重安装而出问题，需重新执行以上安装）</font>";

	var LODOP;
	try {
		// =====判断浏览器类型:===============
		var isIE = (navigator.userAgent.indexOf('MSIE') >= 0)
				|| (navigator.userAgent.indexOf('Trident') >= 0);
		var is64IE = isIE && (navigator.userAgent.indexOf('x64') >= 0);
		// =====如果页面有Lodop就直接使用，没有则新建:==========
		if (oOBJECT != undefined || oEMBED != undefined) {
			if (isIE)
				LODOP = oOBJECT;
			else
				LODOP = oEMBED;
		} else {
			if (CreatedOKLodop7766 == null) {
				LODOP = document.createElement("object");
				LODOP.setAttribute("width", 0);
				LODOP.setAttribute("height", 0);
				LODOP
						.setAttribute("style",
								"position:absolute;left:0px;top:-100px;width:0px;height:0px;");
				if (isIE)
					LODOP.setAttribute("classid",
							"clsid:2105C259-1E0C-4534-8141-A753534CB4CA");
				else
					LODOP.setAttribute("type", "application/x-print-lodop");
				document.documentElement.appendChild(LODOP);
				CreatedOKLodop7766 = LODOP;
			} else
				LODOP = CreatedOKLodop7766;
		}
		;
		// =====判断Lodop插件是否安装过，没有安装或版本过低就提示下载安装:==========
		if ((LODOP == null) || (typeof (LODOP.VERSION) == "undefined")) {
			if (navigator.userAgent.indexOf('Chrome') >= 0)
				document.documentElement.innerHTML = strHtmChrome
						+ document.documentElement.innerHTML;
			if (navigator.userAgent.indexOf('Firefox') >= 0)
				document.documentElement.innerHTML = strHtmFireFox
						+ document.documentElement.innerHTML;
			if (is64IE)
				document.write(strHtm64_Install);
			else if (isIE)
				document.write(strHtmInstall);
			else
				document.documentElement.innerHTML = strHtmInstall
						+ document.documentElement.innerHTML;
			return LODOP;
		} else if (LODOP.VERSION < "6.1.9.8") {
			if (is64IE)
				document.write(strHtm64_Update);
			else if (isIE)
				document.write(strHtmUpdate);
			else
				document.documentElement.innerHTML = strHtmUpdate
						+ document.documentElement.innerHTML;
			return LODOP;
		}
		;
		// =====如下空白位置适合调用统一功能(如注册码、语言选择等):====

		LODOP.SET_LICENSES("", "128170832841C6D2DC03B40159673C41",
				"C94CEE276DB2187AE6B65D56B3FC2848",
				"A98059EB2341CD527B4BF6DD39357B4E");
		// ============================================================
		return LODOP;
	} catch (err) {
		if (is64IE)
			document.documentElement.innerHTML = "Error:" + strHtm64_Install
					+ document.documentElement.innerHTML;
		else
			document.documentElement.innerHTML = "Error:" + strHtmInstall
					+ document.documentElement.innerHTML;
		return LODOP;
	}
}

/** *************************打印js*********************************** */

/**
 * 标签打印
 */
function printLabelTools() {

	LODOP = getLodop();

	var PhysicalAddress = LODOP
			.GET_SYSTEM_INFO('NetworkAdapter.1.PhysicalAddress');// 主网卡MAC地址:
	var iPrinterNO = LODOP.SELECT_PRINTER();// 获取选择的打印机iPrinterNO
	LODOP.SET_PRINTER_INDEX(iPrinterNO);
}

// 标签打印(直接打印)
function createLabelBill() {
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var day = myDate.getDate();
	var bill_dept = $('#bill_dept option:selected').text();// 始发站
	var endCity = $('#endCity').val();// 目的地
	var waybillNo = $('#waybillNo').val();// 目的地
	var endDept = $('#delivery_dept option:selected').text()// 目的网点
	var receiveClientContact = $('#receiveClientContact').val();// 收货人
	var receiveClientName = $('#receiveName').val();// 收货方
	var waybillNo = $('#waybillNo').val();// 运单号
	var goodType = $('#goodType').val();// 托运物品名
	var statementPrice = $('#statementPrice').val();// 物品价值
	var number = $('#number').val();// 件数
	var packType = $('#packType').val();// 包装
	var wgt = $('#wgt').val();// 重量
	var cube = $('#cube').val();// 体积
	var train_route = $('#train_route').val();// 运输路由
	var pickup_address_name = $('#pickup_address_name').val();// 目的区域
	var strlength = train_route.split(',');
	var bqfs = parseInt($('#bqfs').val());
	var waybill_time = $('#waybill_time').val();// 开单时间
	var receive_type = $('#receive_type').val();// 交接方式
	$('#lable_no').val($('#bqfs').val());

	if (bqfs == '' || bqfs < 1) {
		bqfs = 1;
	}
	// LODOP=getLodop();
	var num = 30;
	var k = (bqfs % 30);// 取余数
	var p = parseInt((bqfs / 30));// 取整数
	var o = 0;
	if (k > 0) {
		o = parseInt(p) + 1;
	} else {
		o = p;
	}
	for (i = 1; i <= o; i++) {// 分组打印
		LODOP.SET_SHOW_MODE("NP_NO_RESULT", true);// 解决谷歌浏览器长时间无反应是提示弹出框的问题
		LODOP.PRINT_INITA("-4.23mm", "-15.35mm", "233.89mm", "128.59mm",
				"ZHDLABEL");
		LODOP.SET_PRINT_PAGESIZE(1, 1000, 950, "");
		LODOP.SET_PRINT_MODE("POS_BASEON_PAPER", true);
		LODOP.SET_SHOW_MODE("BKIMG_WIDTH", 480);
		LODOP.SET_SHOW_MODE("BKIMG_HEIGHT", 480);
		LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW", true);
		// var number=$('input[name=number]').val();
		if (i == o && k > 0) {
			num = k;
		}
		for (j = 1; j <= num; j++) {// 每组打印的页数
			LODOP.NewPage();

			var strCode;
			strCode = waybillNo;

			if (i == 1) {
				if (j < 10) {
					strCode = strCode + '0' + '0' + j;
				} else {
					strCode = strCode + '0' + j;
				}
			} else {
				if (i <= 4) {
					if ((j + 30 * (i - 1)) < 100) {
						strCode = strCode + '0' + (j + 30 * (i - 1));
					} else {
						strCode = strCode + (j + 30 * (i - 1));
					}
				} else {
					strCode = strCode + (j + 30 * (i - 1));
				}
			}
			LODOP.SET_PRINT_STYLE("Bold", 0);
			LODOP.SET_PRINT_STYLE("FontSize", 8);
			// LODOP.ADD_PRINT_BARCODE("1.005cm","7.885cm","2.963cm","1.349cm","Code39",strCode);
			// LODOP.ADD_PRINT_BARCODE("1.005cm","5.885cm",209,50,"128A",waybillNo);
			LODOP.ADD_PRINT_BARCODE("1.005cm", "6.585cm", 178, 50, "128A",
					strCode);
			LODOP.SET_PRINT_STYLE("Bold", 1);
			LODOP.SET_PRINT_STYLE("FontSize", 12);
			LODOP.ADD_PRINT_TEXTA("text01", "3.889cm", "2.328cm", "3.413cm",
					"0.529cm", bill_dept.replace('营业部', '').replace('网点', ''));// 始发网点
			var transitAddress = $('#transitAddress').val();
			var str = '';
			if (strlength.length == 3) {
				var str = "(三级分拨)";

			}
			if ($("#is_conversion").val() == 'Y')// 如果是中转，将中转目的地也打印出来
			{
				LODOP.ADD_PRINT_TEXTA("text02", "3.863cm", "6.715cm",
						"5.845cm", "0.529cm", endDept.replace('营业部', '')
								.replace('网点', '')
								+ str + '-' + transitAddress);// 目的网点
			} else {
				LODOP.ADD_PRINT_TEXTA("text03", "3.863cm", "6.715cm",
						"5.845cm", "0.529cm", endDept.replace('营业部', '')
								.replace('网点', '')
								+ str);// 目的网点
			}
			if (receive_type == 1) {
				LODOP.ADD_PRINT_TEXTA("text10", "8.435cm", "3.034cm",
						"2.223cm", "0.529cm", "自提");
			} else if (receive_type == 2) {
				LODOP.ADD_PRINT_TEXTA("text11", "8.435cm", "3.034cm",
						"2.223cm", "0.529cm", "配送");
			} else {
				LODOP.ADD_PRINT_TEXTA("text12", "8.435cm", "3.034cm",
						"2.223cm", "0.529cm", "送货上楼");
			}
			LODOP.ADD_PRINT_TEXTA("text13", "8.435cm", "5.691cm", "5.381cm",
					"0.529cm", waybill_time);
			LODOP.SET_PRINT_STYLE("FontSize", 18);
			LODOP.ADD_PRINT_TEXTA("text04", "5.715cm", "2.778cm", "4.037cm",
					"0.529cm", receiveClientContact);// 收货人
			LODOP.ADD_PRINT_TEXTA("text05", "5.715cm", "6.592cm", "4.066cm",
					"0.529cm", waybillNo);// 运单号
			LODOP.ADD_PRINT_TEXTA("text06", "7.435cm", "3.034cm", "2.223cm",
					"0.529cm", number);// 件数
			LODOP.ADD_PRINT_TEXTA("text07", "7.435cm", "5.691cm", "2.381cm",
					"0.529cm", goodType);// 品名
			LODOP.ADD_PRINT_TEXTA("text08", "7.435cm", "8.508cm", "2.593cm",
					"0.529cm", packType);// 包装类型
			LODOP.SET_PRINT_STYLE("Bold", 2);
			LODOP.SET_PRINT_STYLE("FontSize", 20);
			LODOP.ADD_PRINT_TEXTA("text09", "26.72mm", "80.75mm", "19.31mm",
					"5.29mm", endCity);// 目的地
			// LODOP.SET_PRINT_STYLE("Bold",1);
			// LODOP.SET_PRINT_STYLE("FontSize",15);
		}
		// LODOP.PREVIEW();
		LODOP.PRINT();
	}
}

// 打印预览回单
function printviewReceipt() {
	createReceiptBill();
	LODOP.PREVIEW();
}

// 打印回单
function printReceipt() {
	createReceiptBill();
	LODOP.PRINTA();
}

function createReceiptBill() {
	LODOP = getLodop();
	LODOP.SET_SHOW_MODE("NP_NO_RESULT", true);// 解决谷歌浏览器长时间无反应是提示弹出框的问题
	LODOP.PRINT_INITA("-4.5mm", "-15mm", "233.89mm", "128.59mm", "ZHDRECEIPT");
	LODOP.SET_PRINT_PAGESIZE(1, 2300, 1270, "");
	LODOP.SET_PRINT_MODE("POS_BASEON_PAPER", true);
	// LODOP.ADD_PRINT_SETUP_BKIMG("C:\\Users\\michael\\Desktop\\回单.png");
//	LODOP.ADD_PRINT_SETUP_BKIMG('<img border="0" src="' + getRootPath()
//			+ '/images/receipt.png">');
	LODOP.SET_SHOW_MODE("BKIMG_LEFT", 13);
	LODOP.SET_SHOW_MODE("BKIMG_TOP", 12);
	LODOP.SET_SHOW_MODE("BKIMG_WIDTH", 871);
	LODOP.SET_SHOW_MODE("BKIMG_HEIGHT", 480);
	LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW", true);

//	var endDept = $('#delivery_dept option:selected').text()// 目的网点
//	// var endCity=$('#endCity').val();//目的地
//	var bill_dept = $('#bill_dept option:selected').text();// 始发站
//	var sendClientName = $('#shortName').val();// 发货方
//	var remark = $('#remark').val();// 备注
//	var sendClientContact = $('#sendClientContact').val();// 发货人
//	var sendClientTel = $('#sendClientTel').val();// 发货联系电话
//	var sendClientMobile = $('#sendClientMobile').val();// 发货联系手机
//	var receiveClientName = $('#receiveName').val();// 收货方
//	var receiveClientTel = $('#receiveClientTel').val();// 收货联系电话
//	var receiveClientMobile = $('#receiveClientMobile').val();// 收货联系手机
//	var receive_addr = $('#receive_addr').val();// 收货详细地址
//	var receiveClientContact = $('#receiveClientContact').val();// 收货人
//	var waybill_time = $('#waybill_time').val();// 开单时间
//	var receipt_no = $('#receipt_no').val();// 回单号
//	var receipt_number = $('#receipt_number').val();// 回单份数
//	var year = waybill_time.substring(0, 4);// 年
//	var month = waybill_time.substring(5, 7);// 月
//	var day = waybill_time.substring(8, 10);// 日
//	var waybillNo = $('#waybillNo').val();// 托运单号
//	var salesman = $('#salesman').val();// 业务员
	LODOP.SET_PRINT_STYLE("Bold", 1);
	LODOP.SET_PRINT_STYLE("FontSize", 13);
	LODOP.ADD_PRINT_TEXT("43.13mm", "50.8mm", "32.28mm", "5.29mm",
			"发货公司");// 发货公司
	LODOP.ADD_PRINT_TEXT("43.13mm", "118.27mm", "43.39mm", "5.29mm",
			"收货公司");// 收货公司
	LODOP.ADD_PRINT_TEXT("72.5mm", "124.35mm", "39.69mm", "5.29mm",
			"收货人");// 收货人
	// LODOP.ADD_PRINT_TEXT("54.24mm","45.51mm","46.83mm","5.29mm","深圳市盐田区东海道金港盛世3栋");//发货详细地址
	LODOP.ADD_PRINT_TEXT("53.45mm", "115.09mm", "49.74mm", "5.29mm",
			"详细地址");// 收货详细地址
	LODOP.ADD_PRINT_TEXT("72.23mm", "49.74mm", "41.01mm", "5.29mm",
			"发货人姓名");// 发货人姓名
	LODOP.ADD_PRINT_TEXT("81.76mm", "37.31mm", "53.18mm", "5.29mm",
				"发货手机15920117244");// 发货电话(手机)
	

	LODOP.ADD_PRINT_TEXT("81.76mm", "106.63mm", "55.83mm", "5.29mm",
				"收货电话");// 收货电话
	
	LODOP.ADD_PRINT_TEXT("92.34mm", "131.5mm", "26.46mm", "5.29mm", "业务员");// 业务员
	LODOP.ADD_PRINT_TEXT("48.15mm", "168.01mm", "27.17mm", "5.29mm", "配送网点");// 派件站点
	LODOP.ADD_PRINT_TEXT("48.42mm", "191.29mm", "27.17mm", "5.29mm", "寄件网点");// 寄件站点
	LODOP.ADD_PRINT_TEXT("98.95mm", "170.92mm", "38.36mm", "5.29mm", "备注");
	LODOP.ADD_PRINT_TEXT("105.83mm", "97.9mm", "56.09mm", "5.29mm", "托运单号");// 托运单号
	LODOP.SET_PRINT_STYLE("FontSize", 10);
	LODOP.ADD_PRINT_TEXT("24.87mm", "179.92mm", "14.73mm", "5.29mm", "2016");// 年
	LODOP.ADD_PRINT_TEXT("24.87mm", "192.35mm", "10.09mm", "5.29mm", "03");// 月
	LODOP.ADD_PRINT_TEXT("24.87mm", "202.94mm", "10.82mm", "5.29mm", "17");// 日

}

// js获取项目根路径，如： http://localhost:8083/uimcardprj
function getRootPath() {
	// 获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
	var curWwwPath = window.document.location.href;
	// 获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	// 获取主机地址，如： http://localhost:8083
	var localhostPaht = curWwwPath.substring(0, pos);
	// 获取带"/"的项目名，如：/uimcardprj
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPaht + projectName);
}

function Arabia_to_Chinese(Num) {
	for (i = Num.length - 1; i >= 0; i--) {
		Num = Num.replace(",", "")// 替换tomoney()中的“,”
		Num = Num.replace(" ", "")// 替换tomoney()中的空格
	}
	Num = Num.replace("￥", "")// 替换掉可能出现的￥字符
	if (isNaN(Num)) { // 验证输入的字符是否为数字
		alert("请检查小写金额是否正确");
		return;
	}
	// 字符处理完毕后开始转换，采用前后两部分分别转换
	part = String(Num).split(".");
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
	if (Num.indexOf(".") != -1) {
		if (part[1].length > 2) {
			alert("小数点之后只能保留两位,系统将自动截断");
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
	if (newchar.charAt(newchar.length - 1) == "元"
			|| newchar.charAt(newchar.length - 1) == "角")
		newchar = newchar + "整"
	return newchar;
}