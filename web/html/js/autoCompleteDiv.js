var $autocompletedom = function(id) {
	return "string" == typeof id ? document.getElementById(id) : id;
}
var Bind = function(object, fun) {
	return function() {
		return fun.apply(object, arguments);
	}
}

function AutoComplete(obj, autoObj, arr,provinceId, hasPowerArg, cachedAllDataArg, roobackFuncArg, noOffset) {
	this.objId = obj;
	this.autoObjId = autoObj;
	this.obj = $autocompletedom(obj); // 输入框
	this.autoObj = $autocompletedom(autoObj);// DIV的根节点
	this.hasPower = hasPowerArg;// 是否拥有查询所有权限
	this.cachedAllData = true;// 是否已经缓存了所有数据
	this.value_arr = arr; // 不要包含重复值
	this.index = -1; // 当前选中的DIV的索引
	this.search_value = ""; // 保存当前搜索的字符
//	this.roobackFunc = roobackFuncArg;
	this.noOffset = false;
	this.provinceId = provinceId;  //省份
	if(noOffset != undefined && noOffset){
		this.noOffset = true;
	}
}
AutoComplete.prototype = {
	// 初始化DIV的位置
	init : function() {
		if(!this.noOffset){
			this.autoObj.style.left = this.obj.offsetLeft + "px";
			this.autoObj.style.top = this.obj.offsetTop + this.obj.offsetHeight + "px";
		}
		this.autoObj.style.width = this.obj.offsetWidth - 2 + "px";// 减去边框的长度2px
	},
	// 删除自动完成需要的所有DIV
	deleteDIV : function() {
		while (this.autoObj.hasChildNodes()) {
			this.autoObj.removeChild(this.autoObj.firstChild);
		}
		this.autoObj.className = "auto_hidden";
	},
	// 设置值
	setValue : function(_this) {
		return function() {
			_this.obj.value = _this.seq;
			_this.autoObj.className = "auto_hidden";
//			if(undefined != _this.roobackFunc){
//				_this.roobackFunc(_this.row);
//			}
			_this.obj.focus(); 
		}
	},
	// 模拟鼠标移动至DIV时，DIV高亮
	autoOnmouseover : function(_this, _div_index) {
		return function() {
			_this.index = _div_index;
			var length = _this.autoObj.children.length;
			for (var j = 0; j < length; j++) {
				if (j != _this.index) {
					_this.autoObj.childNodes[j].className = 'auto_onmouseout';
				} else {
					_this.autoObj.childNodes[j].className = 'auto_onmouseover';
				}
			}
		}
	},
	// 更改classname
	changeClassname : function(length) {
		for (var i = 0; i < length; i++) {
			if (i != this.index) {
				this.autoObj.childNodes[i].className = 'auto_onmouseout';
			} else {
				this.autoObj.childNodes[i].className = 'auto_onmouseover';
				this.obj.value = this.autoObj.childNodes[i].getAttribute("seq");
			}
		}
	},
	// 响应键盘
	pressKey : function(event) {
		var length = this.autoObj.children.length;
		// 光标键"↓"
		if (event.keyCode == 40) {
			++this.index;
			if (this.index > length) {
				this.index = 0;
			} else if (this.index == length) {
				this.obj.value = this.search_value;
			}
			this.changeClassname(length);
		}
		// 光标键"↑"
		else if (event.keyCode == 38) {
			this.index--;
			if (this.index < -1) {
				this.index = length - 1;
			} else if (this.index == -1) {
				this.obj.value = this.search_value;
			}
			this.changeClassname(length);
		}
		// 回车键
		else if (event.keyCode == 13) {
			this.autoObj.className = "auto_hidden";
			this.index = -1;
		} else {
			this.index = -1;
		}
	},
	// 程序入口
	start : function(event,showAll) {
		if(showAll!=undefined){
			this.init();
			this.deleteDIV();
			this.search_value = this.obj.value;
			var valueArr = this.value_arr; // 结果列表
			try {
				var reg = new RegExp("(" + this.obj.value + ")", "i");
			} catch (e) {
				return;
			}
			var div_index = 0;// 记录创建的DIV的索引
			for (var i = 0; i < valueArr.length; i++) {
				if (reg.test(valueArr[i].key1)) {
					if(valueArr[i].key1!=null){
					var div = document.createElement("div");
					div.className = "auto_onmouseout";
					div.seq = valueArr[i].key1;
					div.row = valueArr[i];
					div.onclick = this.setValue(this);
					div.onmouseover = this.autoOnmouseover(this, div_index);
					div.innerHTML = valueArr[i].key1.replace(reg, "<strong>$1</strong>");// 搜索到的字符粗体显示
					this.autoObj.appendChild(div);
					this.autoObj.className = "auto_show";
//					div_index++;
					}
				}
			}
			return;
		}
		if (event.keyCode != 13 && event.keyCode != 38 && event.keyCode != 40) {
			this.init();
			this.deleteDIV();
			this.search_value = this.obj.value;
			if(this.search_value!=undefined && this.search_value.length>=1){ //多少位  2
				var valueArr = ""; // 结果列表
				var obj_this = this;
				var province = document.getElementById("provinceName").value
				//var province = pro.split("·")[0];
				if(province != null && province != undefined && "" != province){
					$.ajax({
						url: 'cmSfUserInfoBO.ajax?cmd=getSuggestion',
						type: 'post',
						data: "query="+this.search_value+"&region="+province,
						dataType: 'json',
						success: function (data) {
							if(data.totalNum > 0) {
								var div = '';
								for (var i = 0; i < data.items.length; i++) {
									obj_this.autoObj.innerHTML = "";
									div += '<div seq="'+data.items[i]+'" row="'+data.items[i]+'" class="auto_hidden" onclick="document.getElementById(\''+obj_this.objId+'\').value=\''+data.items[i].name+'\';document.getElementById(\'storeEand\').value=\''+data.items[i].lng+'\';document.getElementById(\'storeNand\').value=\''+data.items[i].lat+'\';document.getElementById(\''+obj_this.autoObjId+'\').style.display=\'none\'';
									div += '">'+data.items[i].name+'</div>';
								}
								obj_this.autoObj.innerHTML = div;
								obj_this.autoObj.className = "auto_show";
							}
						},
						error:function(data){
							console.info("请求数据失败");
						}
					});
				}
				
				try {
					var reg = new RegExp("(" + this.obj.value + ")", "i");
				} catch (e) {
					return;
				}
			}
		}
		//键盘事件暂时去掉
//		this.pressKey(event);
		window.onresize = Bind(this, function() {
			this.init();
		});
	}
}