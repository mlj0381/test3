var commonFileUploadApp = angular.module('commonFileUploadApp', ['angularFileUpload']);
/**
 * 鏂囦欢涓娄紶鍜屾枃浠跺睍绀烘爣绛?image-upload),灞炴€у涓嬶细
 * outter-scope-object  : 鏆撮湶缁椤閮ㄧ殑瀵硅薄锛岃繖涓繖涓璞′负钬滃睘镐у€?Control钬?
 * width 				: 锲剧墖妗嗙殑瀹藉害
 * height 				: 锲剧墖妗嗙殑楂桦害
 * image-src			: 锲剧墖婧?链変竴浜涚ず渚嫔浘鐗囧彲浠ョ洿鎺ラ€氲绷灞炴€ц缃?
 * is-can-change-file	: 鏄惁鍙互涓娄紶鏂囦欢(链変竴浜涚ず渚嫔浘鐗囷紝镞犻渶涓娄紶锛屽彲浠ュ皢杩欎釜灞炴€т箣璁剧疆涓篺alse)
 * is锛峚mplify锛峣mage	: 鏄惁闇€瑕佹斁澶у浘鐗?
 * rotate-image			: 鏄惁闇€瑕佹棆杞浘鐗?
 * 
 * outterEvents瀵硅薄涓嬬殑鏂规硶鏄彁渚涚粰澶栭儴璋幂敤镄?
 */
commonFileUploadApp.directive('imageUpload',['$http', '$upload', function($http, $upload){// 寮瑰嚭妗?
	var directiveInner = {};
	directiveInner.restrict = "E";
	directiveInner.replace = true;
	directiveInner.compile = function(element, attrs) {
		var outterScopeObject = 'imageUpload';// 鏆撮湶缁椤閮ㄧ殑瀵硅薄
		if(undefined != attrs.outterScopeObject) {
			outterScopeObject = attrs.outterScopeObject;
		}
		var width = 180;// 瀹?
		if(undefined != attrs.width){
			width = attrs.width;
		}
		
		var height = 140;// 楂?
		if(undefined != attrs.height){
			height = attrs.height;
		}
		
		var imageSrc = '/image/zb/man.png';// 锲剧墖婧?
		if(undefined != attrs.imageSrc){
			imageSrc = attrs.imageSrc;
		}
		
		var isCanChangeFile = true;
		if(undefined != attrs.isCanChangeFile){
			isCanChangeFile = 'true' == attrs.isCanChangeFile ? true : false;
		}
		
		var isAmplifyImage = false;
		if(undefined != attrs.isAmplifyImage){
			isAmplifyImage = 'true' == attrs.isAmplifyImage ? true : false;
		}
		
		var rotateImage = false;
		if(undefined != attrs.rotateImage){// 鏄惁镞嬭浆锲剧墖
			rotateImage = 'true' == attrs.rotateImage ? true : false;
		}
		
		var innerObject = {
			objectName: outterScopeObject,
			imageSrc: imageSrc,// 锲剧墖婧?
			defaultImageSrc: imageSrc,
			file: undefined,// 闇€瑕佷笂浼犵殑锲剧墖鏂囦欢
			changed: true,
			width: width,// 瀹?
			height: height,// 楂?
			showUploadTip: false,// 鏄惁鏄剧ず钬沧棆杞浘鐗団€漝iv
			fileFlowId: -1,
			isCanChangeFile: isCanChangeFile,// 鏄惁鍙互阃夋嫨涓娄紶鏂囦欢
			anglePoint: 0,
			rotateImage: rotateImage,// 鏄惁镞嬭浆锲剧墖锛屽彧链夊湪鏀惧ぇ镄勬椂链欓噰鐢ㄥ皬锛屼篃灏辨槸isAmplifyImage涓篓rue镄勬椂链?
			imageAmplify: {// 锲剧墖鏀惧ぇ
				isAmplifyImage: isAmplifyImage,// 鏄惁鏀惧ぇ锲剧墖(榛樿涓篺alse)
				id: 'toolTipLayer',
				toolTipSTYLE: '',
				endaction: false,
				rT: true,
				bT: true,
				tw: 50,
				ns4: document.layers,
				ns6: document.getElementById && !document.all,
				ie4: document.all,
				offsetX: 10,
				offsetY: 20,
				toolTipSTYLE: "",
				bigImgChangeDomId: outterScopeObject + 'BigImgChangeDomId',
				init: function(){
					if(!innerObject.imageAmplify.isAmplifyImage){// 涓嶉渶瑕佹斁澶х殑璇濓紝灏辩洿鎺ヨ繑锲?
	        			return;
	        		}
					var tempDiv = document.createElement("div");
	        	 	tempDiv.id = innerObject.imageAmplify.id;
	        	 	tempDiv.style.position = "absolute";
	        	 	tempDiv.style.display = "none";
	        	 	document.body.appendChild(tempDiv);
	        	 	if (innerObject.imageAmplify.ns4 || innerObject.imageAmplify.ns6 || innerObject.imageAmplify.ie4) {
						if (innerObject.imageAmplify.ns4)
							innerObject.imageAmplify.toolTipSTYLE = document.toolTipLayer;
						else if (innerObject.imageAmplify.ns6)
							innerObject.imageAmplify.toolTipSTYLE = document.getElementById(innerObject.imageAmplify.id).style;
						else if (innerObject.imageAmplify.ie4)
							innerObject.imageAmplify.toolTipSTYLE = document.all.toolTipLayer.style;
						
						if (innerObject.imageAmplify.ns4)
							document.captureEvents(Event.MOUSEMOVE);
						else {
							innerObject.imageAmplify.toolTipSTYLE.visibility = "visible";
							innerObject.imageAmplify.toolTipSTYLE.display = "none";
						}
						document.onmousemove = innerObject.imageAmplify.moveToMouseLoc;
					}
				},
				moveToMouseLoc: function(e){
					var scrollTop = innerObject.imageAmplify.getScrollTop();
					var scrollLeft = innerObject.imageAmplify.getScrollLeft();
					if(innerObject.imageAmplify.ns4 || innerObject.imageAmplify.ns6) {
						x = e.pageX + scrollLeft;
						y = e.pageY - scrollTop;
					} else {
						x = event.clientX + scrollLeft;
						y = event.clientY;
					}
	        	   
					if (x - scrollLeft > innerObject.imageAmplify.getViewportWidth() / 2) {
						x = x - document.getElementById(innerObject.imageAmplify.id).offsetWidth - 2 * innerObject.imageAmplify.offsetX;
					}
	        	   
					if ((y + document.getElementById(innerObject.imageAmplify.id).offsetHeight + innerObject.imageAmplify.offsetY) > innerObject.imageAmplify.getViewportHeight()) {
						y = innerObject.imageAmplify.getViewportHeight() - document.getElementById(innerObject.imageAmplify.id).offsetHeight - innerObject.imageAmplify.offsetY;
					}
					innerObject.imageAmplify.toolTipSTYLE.left = (x + innerObject.imageAmplify.offsetX) + 'px';
					innerObject.imageAmplify.toolTipSTYLE.top = (y + innerObject.imageAmplify.offsetY + scrollTop) + 'px';
					return true;
				},
				getScrollTop: function() {
	        		if (self.pageYOffset){ // all except Explorer
	        			return self.pageYOffset;
	        		} else if (document.documentElement && document.documentElement.scrollTop){// Explorer 6 Strict
	        			return document.documentElement.scrollTop;
	        		} else if (document.body){ // all other Explorers
	        			return document.body.scrollTop;
	        		}
	        	},
	        	getScrollLeft: function() {
	        		if (self.pageXOffset){ // all except Explorer
	        			return self.pageXOffset;
	        		} else if (document.documentElement && document.documentElement.scrollLeft){// Explorer 6 Strict
	        			return document.documentElement.scrollLeft;
	        		} else if (document.body){ // all other Explorers
	        			return document.body.scrollLeft;
	        		}
	        	},
	        	getViewportHeight: function(){
	        		if (window.innerHeight!=window.undefined) return window.innerHeight;
	        		if (document.compatMode=='CSS1Compat') return document.documentElement.clientHeight;
	        		if (document.body) return document.body.clientHeight; 
	        		return window.undefined; 
	        	},
	        	getViewportWidth: function() {
	        		if (window.innerWidth!=window.undefined) return window.innerWidth;
	        		if (document.compatMode=='CSS1Compat') return document.documentElement.clientWidth;
	        		if (document.body) return document.body.clientWidth; 
	        		return window.undefined; 
	        	},
	        	toggleShow: function(isShow) {
	        		if(!innerObject.imageAmplify.isAmplifyImage){// 涓嶉渶瑕佹斁澶х殑璇濓紝灏辩洿鎺ヨ繑锲?
	        			return;
	        		}
	        	 	try {
	        	 		if(!isShow || innerObject.imageSrc == innerObject.defaultImageSrc){ // 闅愯棌
	        	 			if(innerObject.imageAmplifyns4) {
	        	 				innerObject.imageAmplify.toolTipSTYLE.visibility = "hidden";
	        	 			} else {
	        	 				if (!innerObject.imageAmplify.endaction) {
	        	 					innerObject.imageAmplify.toolTipSTYLE.display = "none";
	        	 				}
	        	 				
	        	 				if (innerObject.imageAmplify.rT) document.all("msg1").filters[1].Apply();
	        	 				if (innerObject.imageAmplify.bT) document.all("msg1").filters[2].Apply();
	        	 				document.all("msg1").filters[0].opacity=0;
	        	 				if (innerObject.imageAmplify.rT) document.all("msg1").filters[1].Play();
	        	 				if (innerObject.imageAmplify.bT) document.all("msg1").filters[2].Play();
	        	 				if (innerObject.imageAmplify.rT){ 
	        	 					if (document.all("msg1").filters[1].status==1 || document.all("msg1").filters[1].status==0){  
	        	 						innerObject.imageAmplify.toolTipSTYLE.display = "none";
	        	 					}
	        	 				}
	        	 				if (innerObject.imageAmplify.bT){
	        	 					if (document.all("msg1").filters[2].status==1 || document.all("msg1").filters[2].status==0){  
	        	 						innerObject.imageAmplify.toolTipSTYLE.display = "none";
	        	 					}
	        	 				}
	        	 				if (!innerObject.imageAmplify.rT && !innerObject.imageAmplify.bT){
	        	 					innerObject.imageAmplify.toolTipSTYLE.display = "none";
	        	 				}
	        	 			}
	        	 		} else { // show
	        	 			if(!innerObject.imageAmplify.fg) innerObject.imageAmplify.fg = "#777777";
	        	 			if(!innerObject.imageAmplify.bg) innerObject.imageAmplify.bg = "#eeeeee";
	        	 			var content = '<table id="msg1" name="msg1" border="0" cellspacing="0" cellpadding="1" bgcolor="' + innerObject.imageAmplify.fg + '" class="trans_msg"><td>' 
	        	 						+ '<table border="0" cellspacing="2" cellpadding="3" bgcolor="' + innerObject.imageAmplify.bg +  '"><td><font face="Arial" color="' + innerObject.imageAmplify.fg 
	        	 						+ '" size="-2">' + "<img src=" + (innerObject.imageSrc).replace("/img/man.png","''").replace(/\.(\w*)$/,"_big.$1") + " id=" + innerObject.imageAmplify.bigImgChangeDomId + ">" + '</font></td></table></td></table>';
	        	 			if(innerObject.imageAmplify.ns4) {
	        	 				innerObject.imageAmplify.toolTipSTYLE.document.write(content);
	        	 				innerObject.imageAmplify.toolTipSTYLE.document.close();
	        	 				innerObject.imageAmplify.toolTipSTYLE.visibility = "visible";
	        	 			}
	        	 			if(innerObject.imageAmplify.ns6) {
	        	 				document.getElementById("toolTipLayer").innerHTML = content;
	        	 				innerObject.imageAmplify.toolTipSTYLE.display='block'
	        	 			}
	        	 			if(innerObject.imageAmplify.ie4) {
	        	 				document.all("toolTipLayer").innerHTML=content;
	        	 				innerObject.imageAmplify.toolTipSTYLE.display='block';
	        	 				var cssopaction=document.all("msg1").filters[0].opacity
	        	 				document.all("msg1").filters[0].opacity=0;
	        	 				if (innerObject.imageAmplify.rT) document.all("msg1").filters[1].Apply();
	        	 				if (innerObject.imageAmplify.bT) document.all("msg1").filters[2].Apply();
	        	 				document.all("msg1").filters[0].opacity=cssopaction;
	        	 				if (innerObject.imageAmplify.rT) document.all("msg1").filters[1].Play();
	        	 				if (innerObject.imageAmplify.bT) document.all("msg1").filters[2].Play();
	        	 			}
	        	 		}
	        	 	} catch(e) {}
	        	 }
			},
			innerEvents: {
				mouseleave: function(){
					innerObject.anglePoint = 0;
					if(innerObject.isCanChangeFile){// 濡傛灉鍙互涓娄紶鏂囦欢
						innerObject.showUploadTip = false;
					}
					innerObject.imageAmplify.toggleShow(false);
				},
				mouseenter: function(){
					innerObject.anglePoint = 0;
					if(innerObject.isCanChangeFile){// 濡傛灉鍙互涓娄紶鏂囦欢
						innerObject.showUploadTip = true;
					}
					innerObject.imageAmplify.toggleShow(true);
				},
				rotateBigImage: function(){
					if(!innerObject.imageAmplify.isAmplifyImage || !rotateImage){// 涓嶉渶瑕佹斁澶х殑璇濓紝灏辩洿鎺ヨ繑锲?
	        			return;
	        		}
					innerObject.anglePoint = innerObject.anglePoint + 90;
	        		 if(innerObject.anglePoint == 360 ){
	        			 innerObject.anglePoint = 0;
	        		 }
	        		 $('#' + innerObject.imageAmplify.bigImgChangeDomId).rotate({angle : innerObject.anglePoint});
	        	 },
				init: function(){
					innerObject.imageAmplify.init();
				},
				changeFile: function(file, result){
					innerObject.file = file;
	        		innerObject.imageSrc = result;
	        		innerObject.changed = true;
	        		innerObject.isShowBigImg = true; 
	        		// 鏄惁闇€瑕佷笂浼?
	        		file.upload = $upload.upload({
	        			 url: 'attach.ajax?cmd=doUpload',
	        			 method: 'POST',
	        			 headers: {
	        				 'my-header' : 'my-header-value'
	        			 },
	        			 fields: {},
	        			 file: file,
	        			 fileFormDataName: "file"
	        		});
	        		file.upload.then(function(response) {
	        			innerObject.fileFlowId = response.data;
	        		 });
				}
			},
			outterEvents: {
				initData: function(imageSrc){
					innerObject.imageSrc = imageSrc;
					innerObject.changed = false;
					innerObject.fileFlowId = -1;
					innerObject.anglePoint = 0;
				},
				cleanData: function(){
					innerObject.outterEvents.initData(innerObject.defaultImageSrc);
				},
				getFlowId: function(){
					return innerObject.fileFlowId;
				}
			}
		}
		var html='<div class="controls lh26 card_submit">'
					+ '<div class="container_id_card first_id_container" style="width: {{'+ outterScopeObject +'Control.width}}px; height: {{'+ outterScopeObject +'Control.height}}px;" ng-mouseleave="'+outterScopeObject+'Control.innerEvents.mouseleave();" ng-mouseenter="'+outterScopeObject+'Control.innerEvents.mouseenter();" ng-click="'+outterScopeObject+'Control.innerEvents.rotateBigImage();">'
						+ '<img style="cursor: pointer; width: {{'+ outterScopeObject +'Control.width - 2}}px; height: {{'+ outterScopeObject +'Control.height - 2}}px; padding:1px;" ng-src="{{'+outterScopeObject+'Control.imageSrc}}" />'
						+ '<div class="a12" style="width:  {{'+ outterScopeObject +'Control.width - 2}}px;" ng-show="' + outterScopeObject + 'Control.showUploadTip">'
							+ '<a class="tx" href="javascript:void(0);" style="width: {{'+ outterScopeObject +'Control.width - 2}}px;">阃夋嫨锲剧墖</a>'
							+ '<input type="file" class="tx" file-model="' + outterScopeObject + '" style="width: {{'+ outterScopeObject +'Control.width}}px; opacity: 0; cursor: pointer;" accept="*">'
						+ '</div>'
					+ '</div>'
				+'</div>'
		element.html(html);
		return function($scope, element, attrs){
			eval("$scope." + outterScopeObject + "Control=innerObject");
			eval("$scope." + outterScopeObject + "Control.innerEvents.init()");
		};
	};
	return directiveInner;
}]);
/**
 * table镙囩
 * 鍖呮嫭濡备笅灞炴€?
 * outter-scope-object 					: 灏佽姝ゆ爣绛炬墍链変俊鎭殑angular瀵硅薄鍚嶏紝榛樿涓?scope.outterScopeObject
 * load-data-url						: 蹇呭～灞炴€э紝锷犺浇table鏁版嵁镄剈rl
 * init-callback						: 鍒濆鍖栨爣绛惧悗镄勫洖璋冩柟娉?
 * 											阃氩父闇€瑕佸湪姝ゆ柟娉曚腑璋幂敤$scope.outterScopeObject.setTableHead(tableHead);鏂规硶璁剧疆table镄勬爣棰樿
 * 											闄や简杩欎釜鏂规硶涔嫔锛屼篃鍙互璋幂敤鍏朵粬outterEvents灞炴€т腑灏佽镄勫澶栨柟娉?
 * outterEvents							: 灏佽浜嗛拡瀵箃able杩涜鎿崭綔镄勭浉鍏虫柟娉曪紝阍埚璋幂敤钥呮彁渚涚殑鏂规硶
 */
commonApp.directive('logbiTable',['$http','commonService', function($http, commonService){
	var directiveInner = {};
	directiveInner.restrict = "E";
	directiveInner.replace = true;
	directiveInner.compile = function(element, attrs) {
		var outterScopeObject = 'outterScopeObject';// 鏆撮湶缁椤閮ㄧ殑瀵硅薄
		if(undefined != attrs.outterScopeObject){
			outterScopeObject = attrs.outterScopeObject;
		}
		var loadDataUrl = attrs.loadDataUrl;// 锷犺浇鏁版嵁镄剈rl
		var isVisitinfo='';//鍖哄埆鏄惁瀹㈡埛锲炶绠＄悊鐣岄溃
		if(undefined != attrs.isVisitinfo){
			isVisitinfo=attrs.isVisitinfo;
		}
		var logbiTableInnerObject = {
			objectName : outterScopeObject,
			tableHead : [],// table镙囬锛宼ableHead鍖呮嫭镄勫璞￠渶瑕佸寘鎷琻ame銆亀idth銆乧olName灞炴€?
			dbClickEventFunc : undefined,// 阃氲绷灞炴€т紶鍏?
			tableSelect : {// table涓嬮溃镄勯€夋嫨阃夐」
				showTableSelectItem : true,// table
				tableSelectType : 'radio',
				tableSelectName : 'tableSelectName',
				tableSelectIndex : undefined
			},
			loadDataUrl : loadDataUrl,
			noDataTip : '娌℃湁绗﹀悎鏉′欢镄勬暟鎹?,
			outterSuccessFunc : undefined,// 璇锋眰澶勭悊鎴愬姛锛屽皢锲炶皟璇锋眰鍙戣捣钥呰缃殑锲炶皟鍑芥暟
			outterErrFunc : undefined,// 璇锋眰澶勭悊澶辫触锛屽皢锲炶皟璇锋眰鍙戣捣钥呰缃殑锲炶皟鍑芥暟
			outterRequestData : {// 澶栭儴璇锋眰镄勭紦瀛樻暟鎹?
				requestData : undefined,
				timeout : 100,
				loadTip : {
					loadTipTypeObj : outterScopeObject + 'Div',
					loadTipInfo : '淇℃伅锷犺浇涓?..',
					loadTipRelative : true
				}
			},
			responseData : {// 鍝嶅簲鏁版嵁
				originalDatas : [], // 铡熷璇锋眰鏁版嵁
				afterProcessedDatas : []// 琚鐞嗗悗镄勬暟鎹紝鐢变簬angular镄勭壒镐э紝闇€瑕佸鐞嗗搷搴旇繑锲炵殑缁撴灉鏁版嵁
			},
			paging : {// 鍖呰鍒嗛〉鐩稿叧镄勬暟鎹?
				total : 0,
				_GRID_TYPE : "jq",
				pageSize : 10,// 姣忛〉鏄剧ず镄勭粨鏋滆鏁?
				pages : [],// 瀛樻斁褰揿墠闇€瑕佹樉绀虹殑鍒嗛〉淇℃伅
				showPageSizes : true,// 鏄惁鏄剧ず钬濇疮椤垫樉绀鸿鏁扳€滈€夐」
				pageSizes : [10, 20, 30],// 姣忛〉钬沧樉绀鸿鏁伴€夐」钬?
				currentPage : 1,// 褰揿墠镄勯〉镰?
				needInitPage : true,// 鏄惁闇€瑕侀吨鏂拌绠楀垎椤垫暟鎹紝濡傛灉闇€瑕侊紝鍒欎细璋幂敤initPage鏂规硶
				totalPage : 0,// 鍏辨湁澶氩皯椤?
				showRecentPageAmount : 1,// 鏄剧ず褰揿墠椤电爜(currentPage)宸﹀彸涓や晶镄勯〉镰侊紝鍏朵粬镄勫叏閮ㄧ敤..浠ｆ浛
				initCurrentPage : function(){
					this.currentPage = 1;
				},
				initPage : function(total){
					if(total == undefined)
						total = 0;
					this.total = total;
					this.currentPage = 1;
					this.pages = [];
					this.totalPage = Math.floor(total / this.pageSize) + (total % this.pageSize == 0 ? 0 : 1);
					this.initPages();// 閲嶆柊璁＄畻
				},
				initPages : function(){
					var preHidePageObject = undefined;
					var nextHidePageObject = undefined;
					var array = new Array();
					for(var i = 1; i <= this.totalPage; i++){
						if(i == 1 || i == this.totalPage || i == this.currentPage){// 绗竴椤碉紝链€鍚庯紝鎴栬€呭綋鍓嶉〉閮介渶瑕佹樉绀?
							array.push({ value : i , canClick : true});
						}else if(i < this.currentPage && i + this.showRecentPageAmount < this.currentPage){
							if(undefined == preHidePageObject){
								preHidePageObject = { value : '...' , canClick : false};
								array.push(preHidePageObject);
							}
						}else if (i > this.currentPage && i > this.showRecentPageAmount + this.currentPage){
							if(undefined == nextHidePageObject){
								nextHidePageObject = { value : '...' , canClick : false};
								array.push(nextHidePageObject);
							}
						}else{
							array.push({ value : i , canClick : true});
						}
					}
					this.pages = array;
				},
				selectPrevious : function(){
					if(this.pages.length == 0 || this.currentPage == 1)
						return;
					this.currentPage = this.currentPage -1;
					this.initPages();
					this.loadPageData();
				},
				selectNext : function(){
					if(this.pages.length == 0 || this.currentPage == this.pages[this.pages.length -1].value)
						return;
					this.currentPage = this.currentPage + 1;
					this.initPages();
					this.loadPageData();
				},
				selectPage : function(page){
					if(!page.canClick){
						return;
					}
					if(this.currentPage == page.value)
						return;
					this.currentPage = page.value;
					this.initPages();
					this.loadPageData();
				},
				isActive : function(page){
					return this.currentPage == page;
				},
				noPrevious : function(){
					return this.currentPage == 1;
				},
				noNext : function(){
					return this.pages.length == 0 ? true : this.currentPage == this.pages[this.pages.length -1].value;
				},
				loadPageData : function(){
					logbiTableInnerObject.innerEvents.innerLoadData(logbiTableInnerObject.outterRequestData.requestData,false);
				},
				isHoverPage : function(innerPageSize){
					return this.pageSize == innerPageSize;
				},
				resizePageSize : function(innerPageSize){
					if(innerPageSize != this.pageSize){
						this.pageSize = innerPageSize;
						this.currentPage = 1;
						logbiTableInnerObject.innerEvents.innerLoadData(logbiTableInnerObject.outterRequestData.requestData,true);
					}
				}
			},
			innerEvents : {// 鍖呰鍐呴儴澶勭悊浜嬩欢
				innerDbClickEvent : function(index){// 鍙屽向琛ㄦ牸镄勫洖璋冨嚱鏁?
					if(undefined != logbiTableInnerObject.dbClickEventFunc){
						var rowData = logbiTableInnerObject.responseData.originalDatas[index];
						logbiTableInnerObject.dbClickEventFunc(rowData);
					}
				},
				innerResetCheckbox : function(name){
					var checkbox=document.getElementsByName(name); 
					for(var i = 0; i < checkbox.length; i++){
						checkbox[i].checked = false;
					}
				},
				innerToggleClickboxHead : function(){
					var checkbox = document.getElementsByName(logbiTableInnerObject.tableSelect.tableSelectName + 'Head');
					if(undefined != checkbox && checkbox.length == 1){
						var checked = checkbox[0].checked;
						var subCheckbox=document.getElementsByName(logbiTableInnerObject.tableSelect.tableSelectName); 
						for(var i = 0; i < subCheckbox.length; i++){
							subCheckbox[i].checked = checked;
						}
					}
				},
				innerSuccessFunc : function(responseData){
					if(logbiTableInnerObject.paging.needInitPage)
						logbiTableInnerObject.paging.initPage(responseData.total);
					logbiTableInnerObject.tableSelect.tableSelectIndex = undefined;
					// 濡傛灉鏄痗heckbox,闇€瑕佹竻绌烘潈闄恈heckbox 
					if(logbiTableInnerObject.tableSelect.showTableSelectItem && logbiTableInnerObject.tableSelect.tableSelectType == 'checkbox'){
						logbiTableInnerObject.innerEvents.innerResetCheckbox(logbiTableInnerObject.tableSelect.tableSelectName + 'Head');
					}
					logbiTableInnerObject.innerEvents.innerDealResponseData(responseData.rows);
					// 鍒ゆ柇鏄惁链夊閮ㄥ洖璋冨嚱鏁?
					if(undefined != logbiTableInnerObject.outterSuccessFunc){
						logbiTableInnerObject.outterSuccessFunc(responseData);
					}
				},
				innerErrFunc : function(responseData){
					if('-1' == responseData){// 瓒呮椂
						alert("鐧诲綍浼氲瘽瓒呮椂锛岃閲嶆柊鐧诲綍");
						window.location="/index.html";
						return;
					}
					// 鍒ゆ柇鏄惁链夊閮ㄥ洖璋冨嚱鏁?
					if(undefined != logbiTableInnerObject.outterErrFunc){
						logbiTableInnerObject.outterErrFunc(responseData);
					}
				},
				innerLoadData : function(requestData, needInitPage){
					// 澶囦唤璇锋眰鏁版嵁 Start
					logbiTableInnerObject.outterRequestData.requestData = requestData;
					logbiTableInnerObject.paging.needInitPage = needInitPage;// 鏄惁闇€瑕侀吨鏂板姞杞藉垎椤垫暟鎹?
					// 澶囦唤璇锋眰鏁版嵁 End
					
					requestData._GRID_TYPE = logbiTableInnerObject.paging._GRID_TYPE;
					requestData.rows = logbiTableInnerObject.paging.pageSize;
					requestData.page = logbiTableInnerObject.paging.currentPage;
					var queryString = requestData;
					var queryObject = {
			    		method  : 'POST',
				    	data	: queryString,
				        url     : logbiTableInnerObject.loadDataUrl
				    };
					commonService.postUrl(logbiTableInnerObject.loadDataUrl,queryObject, this.innerSuccessFunc, this.innerErrFunc);
//				    commonService.postObjectWithLoadTip($http, queryObject, this.innerSuccessFunc, this.innerErrFunc, logbiTableInnerObject.outterRequestData.timeout, logbiTableInnerObject.outterRequestData.loadTip);
				},
				innerDealResponseData : function(datas){// 澶勭悊鍝嶅簲鏁版嵁
					logbiTableInnerObject.responseData.originalDatas = datas;
					var rows = new Array();
					for(var i = 0 ; undefined != datas && i < datas.length; i++){
						var originalRow = datas[i];
						var rowColDatas = new Array();
						for(var j = 0; undefined != logbiTableInnerObject.tableHead && j < logbiTableInnerObject.tableHead.length; j++){
							var headCol = logbiTableInnerObject.tableHead[j];
							rowColDatas.push({value: this.innerGetObjectAttrValue(originalRow,headCol.colName)});
						}
						rows.push({cols:rowColDatas});
					}
					logbiTableInnerObject.responseData.afterProcessedDatas = rows;
				},
				innerGetObjectAttrValue : function(object,attr){
					if(object == undefined || attr == undefined){
						return "";
					}
					return object[attr];
				},
				innerSetSomeRequestInfo : function(loadTip, timeout){
					if(undefined != loadTip && undefined != loadTip.loadTipTypeObj){
						logbiTableInnerObject.outterRequestData.loadTip.loadTipTypeObj = loadTip.loadTipTypeObj;
					}
					if(undefined != loadTip && undefined != loadTip.loadTipInfo){
						logbiTableInnerObject.outterRequestData.loadTip.loadTipInfo = loadTip.loadTipInfo;					
					}
					if(undefined != loadTip && undefined != loadTip.loadTipRelative){
						logbiTableInnerObject.outterRequestData.loadTip.loadTipRelative = loadTip.loadTipRelative;
					}
					if(undefined != timeout){
						logbiTableInnerObject.outterRequestData.timeout = timeout;
					}
				}
			},
			outterEvents : {// 鏆撮湶缁椤闱㈣皟鐢ㄧ殑鏂规硶
				setTableHead : function(tableHead){// 璁剧疆琛ㄦ牸镄勭粨鏋滆镙囬锛屽湪钬榠nit-callback钬椤睘镐у€煎搴旗殑鏂规硶涓皟鐢ㄦ鏂规硶璁剧疆table镄勬爣棰?
					logbiTableInnerObject.tableHead = tableHead;
				},
				loadData : function(requestData, outterSuccessFunc, outterErrFunc, timeout, loadTip){// 锷犺浇鏁版嵁
					logbiTableInnerObject.outterSuccessFunc = outterSuccessFunc;
					logbiTableInnerObject.outterErrFunc = outterErrFunc;
					logbiTableInnerObject.innerEvents.innerSetSomeRequestInfo(loadTip, timeout);
					logbiTableInnerObject.paging.currentPage = 1;
					logbiTableInnerObject.innerEvents.innerLoadData(requestData, true);
				},
				reloadData : function(){// 閲嶆柊锷犺浇鏁版嵁
					logbiTableInnerObject.paging.currentPage = 1;
					logbiTableInnerObject.innerEvents.innerLoadData(logbiTableInnerObject.outterRequestData.requestData, true);
				},
				reloadDataForCurrentPage : function(){// 閲嶆柊锷犺浇褰揿墠椤垫暟鎹?
					logbiTableInnerObject.innerEvents.innerLoadData(logbiTableInnerObject.outterRequestData.requestData, false);
				},
				reloadDataForNextPage : function(subSuccessFunc){// 锷犺浇涓嬩竴椤电殑鏁版嵁
					logbiTableInnerObject.paging.currentPage = logbiTableInnerObject.paging.currentPage + 1;
					if(subSuccessFunc != undefined){
						logbiTableInnerObject.outterSuccessFunc = subSuccessFunc;
					}
					logbiTableInnerObject.innerEvents.innerLoadData(logbiTableInnerObject.outterRequestData.requestData, false);
				},
				setOutterSuccessFunc: function(outterSuccessFunc){
					logbiTableInnerObject.outterSuccessFunc = outterSuccessFunc;
				},
				setShowPageSizes : function(showPageSizes){// 鏄惁鏄剧ず钬濇疮椤垫樉绀鸿鏁扳€滈€夐」
					logbiTableInnerObject.paging.showPageSizes = showPageSizes;
				},
				setDbClickEventFunc : function(dbClickEventFunc){// 璁剧疆鍙屽向琛ㄦ牸琛屽搴旗殑锲炶皟鍑芥暟
					logbiTableInnerObject.dbClickEventFunc = dbClickEventFunc;
				},
				setTableSelect : function(tableSelect){// 璁剧疆琛ㄦ牸鍗曢€夎缃?鍖呮嫭checkbox鍜宺adio)
					if(undefined != tableSelect && undefined != tableSelect.showTableSelectItem){
						logbiTableInnerObject.tableSelect.showTableSelectItem = tableSelect.showTableSelectItem;
					}
					if(undefined != tableSelect && undefined != tableSelect.tableSelectType){
						logbiTableInnerObject.tableSelect.tableSelectType = tableSelect.tableSelectType;
					}
					if(undefined != tableSelect && undefined != tableSelect.tableSelectName){
						logbiTableInnerObject.tableSelect.tableSelectName = tableSelect.tableSelectName;
					}
				},
				setPageSize : function(pageSize){// 璁剧疆姣忛〉鏁版嵁镄勫ぇ灏?
					logbiTableInnerObject.paging.pageSize = pageSize;
					logbiTableInnerObject.paging.pageSizes.push(pageSize);
					logbiTableInnerObject.paging.pageSizes.sort(function(a,b) {
						return a - b
					});
				},
				getSelectedDatas : function(){// 銮峰彇table阃変腑镄勮鏁版嵁
					if(logbiTableInnerObject.tableSelect.showTableSelectItem){
						if('radio' == logbiTableInnerObject.tableSelect.tableSelectType){
							return logbiTableInnerObject.responseData.originalDatas[logbiTableInnerObject.tableSelect.tableSelectIndex];
						}else if('checkbox' == logbiTableInnerObject.tableSelect.tableSelectType){
							var checkbox=document.getElementsByName(logbiTableInnerObject.tableSelect.tableSelectName); 
							var array = Array();
							for(var i=0;i<checkbox.length;i++){
								if(checkbox[i].checked){
									array.push(logbiTableInnerObject.responseData.originalDatas[i]);
								}
							}
							return array;
						}
					}
					return null;
				}, 
				getSelectedIndex : function(){
					return logbiTableInnerObject.tableSelect.tableSelectIndex;
				},
				setSelectedIndex : function(index){
					logbiTableInnerObject.tableSelect.tableSelectIndex = index;
				},
				getPageSize : function(){
					return logbiTableInnerObject.paging.pageSize;
				},
				getCurrentPage: function(){
					return logbiTableInnerObject.paging.currentPage;
				},
				getCurrentPageDataSize: function(){
					return logbiTableInnerObject.responseData.originalDatas == undefined ? 0 : logbiTableInnerObject.responseData.originalDatas.length;
				},
				getTotalPage: function(){
					return logbiTableInnerObject.paging.totalPage;
				},
				cleanTableDatas : function(){// 娓呯┖table鐩稿叧镄勬暟鎹?
					logbiTableInnerObject.paging.pages = new Array();
					logbiTableInnerObject.paging.currentPage = 1;
					logbiTableInnerObject.paging.totalPage = 0;
					logbiTableInnerObject.paging.total = 0;
					logbiTableInnerObject.responseData.originalDatas = new Array();
					logbiTableInnerObject.responseData.afterProcessedDatas = new Array();
					logbiTableInnerObject.innerEvents.innerResetCheckbox(logbiTableInnerObject.tableSelect.tableSelectName + 'Head');
				},
				getPageCheckbox : function(){// 鍙湁褰撴椂checkbox镄勬椂链欐墠链夊€?
					if('checkbox' == logbiTableInnerObject.tableSelect.tableSelectType){
						var checkbox=document.getElementsByName(logbiTableInnerObject.tableSelect.tableSelectName);
						return checkbox;
					}
					return null;
				},
				getPageOriginalDatas : function(){
					return logbiTableInnerObject.responseData.originalDatas;
				}
			}
		};
		var html = ''
		+ '<div id="{{'+outterScopeObject+'.objectName + \'Div\' }}"><div class="table-products p_25" style="padding:0 5px;">'
			+ '<div class="row">'
				+ '<table class="table table-hover ng-cloak" ng-cloak>'
					+ '<thead>'
						+ '<tr>'
							+ '<th width="2%" ng-if="' + outterScopeObject + '.tableSelect.showTableSelectItem"><input type="checkbox" ng-click="' + outterScopeObject + '.innerEvents.innerToggleClickboxHead();" ng-if="' + outterScopeObject + '.tableSelect.tableSelectType == \'checkbox\'" name="{{' + outterScopeObject + '.tableSelect.tableSelectName + \'Head\'}}"></th>'
							+ '<th ng-repeat="headCol in ' + outterScopeObject + '.tableHead" width="{{headCol.width}}">{{headCol.name}}</>'
						+ '</tr>'
					+ '</thead>'
					+ '<tbody>'
						+ '<tr class="first" ng-if="'+outterScopeObject+'.paging.total == 0">'
							+ '<td colspan="10"><div style="text-align: center; line-height: 50px; color: #ff7800;">{{' + outterScopeObject + '.noDataTip}}</div></td>'
						+ '</tr>';
		
		if(isVisitinfo=="0"){
			html = html+''
				+ '<tr ng-repeat="dataRow in '+ outterScopeObject + '.responseData.afterProcessedDatas" ng-dblclick="modifyControlObject.toModify($index);">';
		}else{
			html = html+''
				+ '<tr ng-repeat="dataRow in '+ outterScopeObject + '.responseData.afterProcessedDatas" ng-dblclick="' + outterScopeObject + '.innerEvents.innerDbClickEvent($index);" >';
		}
		html = html+''
		+ '<td ng-if="' + outterScopeObject + '.tableSelect.showTableSelectItem && ' + outterScopeObject + '.tableSelect.tableSelectType == \'radio\'"><input id="radio{{$index}}" type="{{' + outterScopeObject + '.tableSelect.tableSelectType}}" name="{{' + outterScopeObject + '.tableSelect.tableSelectName}}" value="{{$index}}" ng-model="' + outterScopeObject + '.tableSelect.tableSelectIndex" /></td>'
							+ '<td ng-if="' + outterScopeObject + '.tableSelect.showTableSelectItem && ' + outterScopeObject + '.tableSelect.tableSelectType == \'checkbox\'"><input type="{{' + outterScopeObject + '.tableSelect.tableSelectType}}" name="{{' + outterScopeObject + '.tableSelect.tableSelectName}}" value="{{$index}}"/></td>'
							+ '<td class="description" ng-repeat="col in dataRow.cols">{{col.value }}</td>'
						+ '</tr>'
					+ '</tbody>'
				+ '</table>'
			+ '</div>'
		+ '</div>'
		+ '<div id="{{'+outterScopeObject+'.objectName + \'PageDiv\' }}" class="chey_b ng-cloak" style="margin:0 20px;">'
			+'<div class="chey_xs" ng-if="' + outterScopeObject + '.paging.total > 0">'
				+'<span ng-if="' + outterScopeObject + '.paging.showPageSizes">鏄剧ず</span>'
					+ '<a ng-if="' + outterScopeObject + '.paging.showPageSizes" ng-repeat="innerPageSize in ' + outterScopeObject + '.paging.pageSizes" ng-class="{hover : ' + outterScopeObject + '.paging.isHoverPage(innerPageSize)}" href="javascript:void(0)" ng-click="' + outterScopeObject+ '.paging.resizePageSize(innerPageSize);">{{innerPageSize}}</a>'
				+'<span ng-if="' + outterScopeObject + '.paging.showPageSizes">鏉?/span>'
				+'<span style="color:#ff7800;">&nbsp;&nbsp;鍏眦{' + outterScopeObject + '.paging.total}}鏉℃暟鎹?/span>'
			+'</div>'
			+ '<ul class="pagination pull-right">'
				+ '<li ng-class="{disabled: ' + outterScopeObject + '.paging.noPrevious()}"><a ng-click="' + outterScopeObject + '.paging.selectPrevious()">&laquo;</a>'
				+ '<li ng-repeat="page in ' + outterScopeObject + '.paging.pages" ng-class="{active: ' + outterScopeObject + '.paging.isActive(page.value)}"><a ng-click="' + outterScopeObject + '.paging.selectPage(page)">{{page.value}}</a>'
				+ '<li ng-class="{disabled: ' + outterScopeObject + '.paging.noNext()}"><a ng-click="' + outterScopeObject + '.paging.selectNext()">&raquo;</a>'
			+ '</ul>'
		+ '</div></div>';
		element.html(html);
		return function($scope, element, attrs){
			eval("$scope." + outterScopeObject + "=logbiTableInnerObject");
			if(undefined != attrs.initCallback){// 鍒濆鍖栨爣绛句箣鍚庯紝闇€瑕佸洖璋冭缃竴浜涘弬鏁?
				eval('$scope.' + attrs.initCallback + '();');
			}
		};
	};
	return directiveInner;
}]);


var MyApp = angular.module('MyApp', ['commonApp','commonFileUploadApp']);
MyApp.controller('newsCtrl', ['$scope', '$http', '$timeout', '$compile', 'commonService', function($scope, $http, $timeout, $compile, commonService) {
	$scope.ue = undefined;// ueditor瀵硅薄
	$scope.newsInfo = {
		source : '鎻＄墿娴? 
	};
	$scope.formData = {};// 镆ヨ鏉′欢

	$scope.clearUEditorData = function(){// 娓呯┖
		if(confirm('纭疄瑕佹竻绌哄凡缂栬緫镄勬暟鎹悧?')) {
			$scope.cleanData();
		}
	}
	
	$scope.cleanData = function(){
		$scope.newsInfo = {
			source : '鎻＄墿娴?		
		}
		document.getElementById('newsInfoType').value = 1;
		$scope.ue.setContent('');
		$scope.imageUploadControl.outterEvents.initData('/image/zb/man.png');
	}
	
	$scope.reloadPageData = function(){
		$scope.cleanData();
		$scope.newsManageMainWinTable.outterEvents.loadData({} ,function(){
			var element = document.getElementById('newsManageMainWinTablePageDiv');
			element.style.marginTop = '0px';
			element.style.marginRight = '0px';
			element.style.marginBottom = '0px';
			element.style.marginLeft = '0px';
		}, undefined, undefined, {
			loadTipTypeObj : 'newMainDiv',
			loadTipInfo : '鏂伴椈鍏憡锷犺浇涓?..'
		});
	}
	
	$scope.saveUEditorData = function(flag){// 淇濆瓨
		if(flag == undefined){
			flag = 'save';
		}
		$scope.newsInfo.flag = flag;
		$scope.newsInfo.content = $scope.ue.getContent();

		if($scope.newsInfo.title == undefined || '' == $.trim($scope.newsInfo.title)){
			alert('[镙囬]涓嶈兘绌?);
			document.getElementById('newsInfoTitle').focus();
			return;
		}
		$scope.newsInfo.type = $scope.newsInfoType;// 绫诲瀷
		
		if($scope.imageUploadControl != undefined && $scope.imageUploadControl.fileFlowId != undefined){
			$scope.newsInfo.fileFlowId = $scope.imageUploadControl.fileFlowId;
		} 
		
//		if(($scope.newsInfo.fileFlowId == undefined || $scope.newsInfo.fileFlowId == -1)&& $scope.newsInfo.picture == undefined){
//			alert('璇烽€夋嫨锲剧墖');
//			return;
//		}
		
		var queryString = $scope.newsInfo;
		var dest=queryString.description;
		//鍒犻櫎鏀瑰瓧娈靛睘镐э紝鏀瑰睘镐у悕绉板湪鍚庡彴浼氲鎷︽埅鎶ラ敊
		delete queryString.description;
		queryString.dest=dest;
		var queryObject = {
    		method  : 'POST',
	    	data	: queryString,
	        url     : 'newsBO.ajax?cmd=save'
	    };
		var successFun = function(data) {
			if(data.resultCode == "1"){
				alert('涓氩姟鍙楃悊鎴愬姛');
				$scope.reloadPageData();
			} else if(data.resultCode == "-1"){// 鐧诲綍浼氲瘽瓒呮椂
				alert("鐧诲綍浼氲瘽瓒呮椂锛岃閲嶆柊鐧诲綍");
				window.location="/index.html";
			} else {
				if(!!data.resultMessage){
					alert(data.resultMessage);
				}else{
					alert('涓氩姟鍙楃悊澶辫触');
				}
			}
	    };
	    
	    commonService.postUrl("newsBO.ajax?cmd=save",queryObject,successFun);
	    
//	    commonService.postObjectWithLoadTip($http,queryObject,successFun, undefined, 100, {
//			 loadTipTypeObj : 'newMainDiv',
//			 loadTipInfo : (flag == 'auth' ? '鎻愪氦瀹℃牳涓?..' : '淇濆瓨涓?..'),
//			 loadTipRelative : true
//	    });
	}
	
	$scope.submitAuth = function(){// 鎻愪氦瀹℃牳
		$scope.saveUEditorData('auth');
	}
	
	$scope.authContent = function(){// 瀹℃牳阃氲绷
		if($scope.newsInfo == undefined || $scope.newsInfo.id == undefined){
			alert('璇峰湪宸︿晶[鏂伴椈鍏憡鍒楄〃]涓弻鍑婚€夋嫨闇€瑕佸镙哥殑鏂伴椈鍏憡淇℃伅');
			return;
		}
		
		if(confirm('纭畾瀹℃牳阃氲绷姝ゆ柊闂诲叕锻婂悧?')) {
			var queryString = {
				id :  $scope.newsInfo.id	
			}
			
			var queryObject = {
	    		method  : 'POST',
		    	data	: queryString,
		        url     : 'newsBO.ajax?cmd=auth'
		    };
			var successFun = function(data) {
				if(data.resultCode == "1"){
					alert('瀹℃牳阃氲绷涓氩姟鍙楃悊鎴愬姛');
					$scope.reloadPageData();
				} else if(data.resultCode == "-1"){// 鐧诲綍浼氲瘽瓒呮椂
					alert("鐧诲綍浼氲瘽瓒呮椂锛岃閲嶆柊鐧诲綍");
					window.location="/index.html";
				} else {
					if(!!data.resultMessage){
						alert(data.resultMessage);
					}else{
						alert('涓氩姟鍙楃悊澶辫触');
					}
				}
		    };
		    
		    commonService.postUrl("newsBO.ajax?cmd=auth",queryObject,successFun);
		    
//		    commonService.postObjectWithLoadTip($http,queryObject,successFun, undefined, 100, {
//				 loadTipTypeObj : 'newMainDiv',
//				 loadTipInfo : '瀹℃牳阃氲绷涓?..',
//				 loadTipRelative : true
//		    });
		}
	}
	
	$scope.queryNews = function(){
		$scope.formData.type = $scope.formDataType;
		$scope.formData.state =  $scope.formDataState;
		$scope.newsManageMainWinTable.outterEvents.loadData($scope.formData ,undefined, undefined, undefined, {
			loadTipTypeObj : 'newMainDiv',
			loadTipInfo : '鏂伴椈鍏憡锷犺浇涓?..'
		});
	}
	
	$scope.dbClicknewsManageMainWinTableEventFunc = function(rowData){// 鍙屽向琛ㄦ牸
		var queryString = {id:rowData.id};
		var queryObject = {
    		method  : 'POST',
	    	data	: queryString,
	        url     : 'newsBO.ajax?cmd=getNewsDetail'
	    };
		
		var successFun = function(data) {
			if(data.resultCode == "1"){
				$scope.newsInfo = data.resultData;
				document.getElementById('newsInfoType').value = $scope.newsInfo.type;
				$scope.ue.setContent($scope.newsInfo.content);
				$scope.imageUploadControl.outterEvents.initData($scope.newsInfo.picture);
			} else if(data.resultCode == "-1"){// 鐧诲綍浼氲瘽瓒呮椂
				alert("鐧诲綍浼氲瘽瓒呮椂锛岃閲嶆柊鐧诲綍");
				window.location="/index.html";
			} else {
				if(!!data.resultMessage){
					alert(data.resultMessage);
				}else{
					alert('涓氩姟鍙楃悊澶辫触');
				}
			}
	    };
	    
	    commonService.postUrl("newsBO.ajax?cmd=getNewsDetail",queryObject,successFun);
	    
//	    commonService.postObjectWithLoadTip($http,queryObject,successFun, undefined, 100, {
//			 loadTipTypeObj : 'newMainDiv',
//			 loadTipInfo : '鏂伴椈鍏憡锷犺浇涓?..',
//			 loadTipRelative : true
//	    });
	}
	
	$scope.newsManageMainWinTableInitCallback = function(){
		var tableHead = [{
			name : '鏂伴椈鍏憡鍒楄〃(鍙屽向鍙紪杈?',
			width : '100%',
			colName : 'title'
		}];
		$scope.newsManageMainWinTable.outterEvents.setShowPageSizes(false);
		$scope.newsManageMainWinTable.outterEvents.setTableHead(tableHead);
		$scope.newsManageMainWinTable.outterEvents.setTableSelect({showTableSelectItem : false});
		$scope.newsManageMainWinTable.outterEvents.setDbClickEventFunc($scope.dbClicknewsManageMainWinTableEventFunc);
		
		$scope.newsManageMainWinTable.outterEvents.loadData({} ,function(){
			var element = document.getElementById('newsManageMainWinTablePageDiv');
			element.style.marginTop = '0px';
			element.style.marginRight = '0px';
			element.style.marginBottom = '0px';
			element.style.marginLeft = '0px';
		}, undefined, undefined, {
			loadTipTypeObj : 'newMainDiv',
			loadTipInfo : '鏂伴椈鍏憡锷犺浇涓?..'
		});
	}
	
	$scope.initUeditor = function(){
		 $scope.ue = UE.getEditor('container', {
			autoHeightEnabled : false,
			initialContent : '',
			initialFrameWidth : 565,
			initialFrameHeight : 520
		});
	}
	$scope.init = function(){
		$scope.initUeditor();
	}
	$scope.init();
} ]);