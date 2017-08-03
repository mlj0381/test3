// Copyright (c) 2009, Baidu Inc. All rights reserved.
// 
// Licensed under the BSD License
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//      http:// tangram.baidu.com/license.html
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS-IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
 /**
 * @namespace T Tangram涓冨阀鏉? * @name T
 * @version 1.6.0
*/

/**
 * 澹版槑baidu鍖? * @author: allstar, erik, meizz, berg
 */
var T,
    baidu = T = baidu || {version: "1.5.0"};
baidu.guid = "$BAIDU$";
baidu.$$ = window[baidu.guid] = window[baidu.guid] || {global:{}};

/**
 * 浣跨敤flash璧勬簮灏佽镄勪竴浜涘姛鑳? * @namespace baidu.flash
 */
baidu.flash = baidu.flash || {};

/**
 * 鎿崭綔dom镄勬柟娉? * @namespace baidu.dom 
 */
baidu.dom = baidu.dom || {};


/**
 * 浠庢枃妗ｄ腑銮峰彇鎸囧畾镄凞OM鍏幂礌
 * @name baidu.dom.g
 * @function
 * @grammar baidu.dom.g(id)
 * @param {string|HTMLElement} id 鍏幂礌镄刬d鎴朌OM鍏幂礌.
 * @shortcut g,T.G
 * @meta standard
 * @see baidu.dom.q
 *
 * @return {HTMLElement|null} 銮峰彇镄勫厓绱狅紝镆ユ垒涓嶅埌镞惰繑锲瀗ull,濡傛灉鍙傛暟涓嶅悎娉曪紝鐩存帴杩斿洖鍙傛暟.
 */
baidu.dom.g = function(id) {
    if (!id) return null;
    if ('string' == typeof id || id instanceof String) {
        return document.getElementById(id);
    } else if (id.nodeName && (id.nodeType == 1 || id.nodeType == 9)) {
        return id;
    }
    return null;
};
baidu.g = baidu.G = baidu.dom.g;


/**
 * 鎿崭綔鏁扮粍镄勬柟娉? * @namespace baidu.array
 */

baidu.array = baidu.array || {};


/**
 * 阆嶅巻鏁扮粍涓墍链夊厓绱? * @name baidu.array.each
 * @function
 * @grammar baidu.array.each(source, iterator[, thisObject])
 * @param {Array} source 闇€瑕侀亶铡嗙殑鏁扮粍
 * @param {Function} iterator 瀵规疮涓暟缁勫厓绱犺繘琛岃皟鐢ㄧ殑鍑芥暟锛岃鍑芥暟链変袱涓弬鏁帮紝绗竴涓负鏁扮粍鍏幂礌锛岀浜屼釜涓烘暟缁勭储寮曞€硷紝function (item, index)銆? * @param {Object} [thisObject] 鍑芥暟璋幂敤镞剁殑this鎸囬拡锛屽鏋沧病链夋鍙傛暟锛岄粯璁ゆ槸褰揿墠阆嶅巻镄勬暟缁? * @remark
 * each鏂规硶涓嶆敮鎸佸Object镄勯亶铡?瀵筄bject镄勯亶铡嗕娇鐢╞aidu.object.each 銆? * @shortcut each
 * @meta standard
 *             
 * @returns {Array} 阆嶅巻镄勬暟缁? */
 
baidu.each = baidu.array.forEach = baidu.array.each = function (source, iterator, thisObject) {
    var returnValue, item, i, len = source.length;
    
    if ('function' == typeof iterator) {
        for (i = 0; i < len; i++) {
            item = source[i];
            returnValue = iterator.call(thisObject || source, item, i);
    
            if (returnValue === false) {
                break;
            }
        }
    }
    return source;
};

/**
 * 瀵硅瑷€灞傞溃镄勫皝瑁咃紝鍖呮嫭绫诲瀷鍒ゆ柇銆佹ā鍧楁墿灞曘€佺户镓垮熀绫讳互鍙婂璞¤嚜瀹氢箟浜嬩欢镄勬敮鎸并€? * @namespace baidu.lang
 */
baidu.lang = baidu.lang || {};


/**
 * 鍒ゆ柇鐩爣鍙傛暟鏄惁涓篺unction鎴朏unction瀹炰緥
 * @name baidu.lang.isFunction
 * @function
 * @grammar baidu.lang.isFunction(source)
 * @param {Any} source 鐩爣鍙傛暟
 * @version 1.2
 * @see baidu.lang.isString,baidu.lang.isObject,baidu.lang.isNumber,baidu.lang.isArray,baidu.lang.isElement,baidu.lang.isBoolean,baidu.lang.isDate
 * @meta standard
 * @returns {boolean} 绫诲瀷鍒ゆ柇缁撴灉
 */
baidu.lang.isFunction = function (source) {
    return '[object Function]' == Object.prototype.toString.call(source);
};

/**
 * 鍒ゆ柇鐩爣鍙傛暟鏄惁string绫诲瀷鎴朣tring瀵硅薄
 * @name baidu.lang.isString
 * @function
 * @grammar baidu.lang.isString(source)
 * @param {Any} source 鐩爣鍙傛暟
 * @shortcut isString
 * @meta standard
 * @see baidu.lang.isObject,baidu.lang.isNumber,baidu.lang.isArray,baidu.lang.isElement,baidu.lang.isBoolean,baidu.lang.isDate
 *             
 * @returns {boolean} 绫诲瀷鍒ゆ柇缁撴灉
 */
baidu.lang.isString = function (source) {
    return '[object String]' == Object.prototype.toString.call(source);
};
baidu.isString = baidu.lang.isString;


/**
 * 鍒ゆ柇娴忚鍣ㄧ被鍨嫔拰鐗规€х殑灞炴€? * @namespace baidu.browser
 */
baidu.browser = baidu.browser || {};


/**
 * 鍒ゆ柇鏄惁涓簅pera娴忚鍣? * @property opera opera鐗堟湰鍙? * @grammar baidu.browser.opera
 * @meta standard
 * @see baidu.browser.ie,baidu.browser.firefox,baidu.browser.safari,baidu.browser.chrome
 * @returns {Number} opera鐗堟湰鍙? */

/**
 * opera 浠?0寮€濮嬩笉鏄敤opera鍚庨溃镄勫瓧绗︿覆杩涜鐗堟湰镄勫垽鏂? * 鍦˙rowser identification链€鍚庢坊锷燰ersion + 鏁板瓧杩涜鐗堟湰镙囱瘑
 * opera鍚庨溃镄勬暟瀛椾缭鎸佸湪9.80涓嶅彉
 */
baidu.browser.opera = /opera(\/| )(\d+(\.\d+)?)(.+?(version\/(\d+(\.\d+)?)))?/i.test(navigator.userAgent) ?  + ( RegExp["\x246"] || RegExp["\x242"] ) : undefined;


/**
 * 鍦ㄧ洰镙囧厓绱犵殑鎸囧畾浣岖疆鎻掑叆HTML浠ｇ爜
 * @name baidu.dom.insertHTML
 * @function
 * @grammar baidu.dom.insertHTML(element, position, html)
 * @param {HTMLElement|string} element 鐩爣鍏幂礌鎴栫洰镙囧厓绱犵殑id
 * @param {string} position 鎻掑叆html镄勪綅缃俊鎭紝鍙栧€间负beforeBegin,afterBegin,beforeEnd,afterEnd
 * @param {string} html 瑕佹彃鍏ョ殑html
 * @remark
 * 
 * 瀵逛簬position鍙傛暟锛屽ぇ灏忓啓涓嶆晱镒?br>
 * 鍙傛暟镄勬剰镐濓细beforeBegin&lt;span&gt;afterBegin   this is span! beforeEnd&lt;/span&gt; afterEnd <br />
 * 姝ゅ锛屽鏋滀娇鐢ㄦ湰鍑芥暟鎻掑叆甯︽湁script镙囩镄凥TML瀛楃涓诧紝script镙囩瀵瑰簲镄勮剼链皢涓崭细琚墽琛屻€? * 
 * @shortcut insertHTML
 * @meta standard
 *             
 * @returns {HTMLElement} 鐩爣鍏幂礌
 */
baidu.dom.insertHTML = function (element, position, html) {
    element = baidu.dom.g(element);
    var range,begin;
    if (element.insertAdjacentHTML && !baidu.browser.opera) {
        element.insertAdjacentHTML(position, html);
    } else {
        range = element.ownerDocument.createRange();
        position = position.toUpperCase();
        if (position == 'AFTERBEGIN' || position == 'BEFOREEND') {
            range.selectNodeContents(element);
            range.collapse(position == 'AFTERBEGIN');
        } else {
            begin = position == 'BEFOREBEGIN';
            range[begin ? 'setStartBefore' : 'setEndAfter'](element);
            range.collapse(begin);
        }
        range.insertNode(range.createContextualFragment(html));
    }
    return element;
};

baidu.insertHTML = baidu.dom.insertHTML;

/**
 * 鎿崭綔flash瀵硅薄镄勬柟娉曪紝鍖呮嫭鍒涘缓flash瀵硅薄銆佽幏鍙杅lash瀵硅薄浠ュ强鍒ゆ柇flash鎻掍欢镄勭増链佛
 * @namespace baidu.swf
 */
baidu.swf = baidu.swf || {};


/**
 * 娴忚鍣ㄦ敮鎸佺殑flash鎻掍欢鐗堟湰
 * @property version 娴忚鍣ㄦ敮鎸佺殑flash鎻掍欢鐗堟湰
 * @grammar baidu.swf.version
 * @return {String} 鐗堟湰鍙? * @meta standard
 */
baidu.swf.version = (function () {
    var n = navigator;
    if (n.plugins && n.mimeTypes.length) {
        var plugin = n.plugins["Shockwave Flash"];
        if (plugin && plugin.description) {
            return plugin.description
                    .replace(/([a-zA-Z]|\s)+/, "")
                    .replace(/(\s)+r/, ".") + ".0";
        }
    } else if (window.ActiveXObject && !window.opera) {
        for (var i = 12; i >= 2; i--) {
            try {
                var c = new ActiveXObject('ShockwaveFlash.ShockwaveFlash.' + i);
                if (c) {
                    var version = c.GetVariable("$version");
                    return version.replace(/WIN/g,'').replace(/,/g,'.');
                }
            } catch(e) {}
        }
    }
})();

/**
 * 鎿崭綔瀛楃涓茬殑鏂规硶
 * @namespace baidu.string
 */
baidu.string = baidu.string || {};


/**
 * 瀵圭洰镙囧瓧绗︿覆杩涜html缂栫爜
 * @name baidu.string.encodeHTML
 * @function
 * @grammar baidu.string.encodeHTML(source)
 * @param {string} source 鐩爣瀛楃涓? * @remark
 * 缂栫爜瀛楃链?涓细&<>"'
 * @shortcut encodeHTML
 * @meta standard
 * @see baidu.string.decodeHTML
 *             
 * @returns {string} html缂栫爜鍚庣殑瀛楃涓? */
baidu.string.encodeHTML = function (source) {
    return String(source)
                .replace(/&/g,'&amp;')
                .replace(/</g,'&lt;')
                .replace(/>/g,'&gt;')
                .replace(/"/g, "&quot;")
                .replace(/'/g, "&#39;");
};

baidu.encodeHTML = baidu.string.encodeHTML;

/**
 * 鍒涘缓flash瀵硅薄镄删tml瀛楃涓? * @name baidu.swf.createHTML
 * @function
 * @grammar baidu.swf.createHTML(options)
 * 
 * @param {Object} 	options 					鍒涘缓flash镄勯€夐」鍙傛暟
 * @param {string} 	options.id 					瑕佸垱寤虹殑flash镄勬爣璇? * @param {string} 	options.url 				flash鏂囦欢镄剈rl
 * @param {String} 	options.errorMessage 		链畨瑁协lash player鎴杅lash player鐗堟湰鍙疯绷浣庢椂镄勬彁绀? * @param {string} 	options.ver 				链€浣庨渶瑕佺殑flash player鐗堟湰鍙? * @param {string} 	options.width 				flash镄勫搴? * @param {string} 	options.height 				flash镄勯佩搴? * @param {string} 	options.align 				flash镄勫榻愭柟寮忥紝鍏佽链硷细middle/left/right/top/bottom
 * @param {string} 	options.base 				璁剧疆鐢ㄤ簬瑙ｆ瀽swf鏂囦欢涓殑镓€链夌浉瀵硅矾寰勮鍙ョ殑鍩烘湰鐩綍鎴朥RL
 * @param {string} 	options.bgcolor 			swf鏂囦欢镄勮儗鏅壊
 * @param {string} 	options.salign 				璁剧疆缂╂斁镄剆wf鏂囦欢鍦ㄧ敱width鍜宧eight璁剧疆瀹氢箟镄勫尯鍩熷唴镄勪綅缃€傚厑璁稿€硷细l/r/t/b/tl/tr/bl/br
 * @param {boolean} options.menu 				鏄惁鏄剧ず鍙抽敭凿滃崟锛屽厑璁稿€硷细true/false
 * @param {boolean} options.loop 				鎾斁鍒版渶鍚庝竴甯ф椂鏄惁閲嶆柊鎾斁锛屽厑璁稿€硷细 true/false
 * @param {boolean} options.play 				flash鏄惁鍦ㄦ祻瑙埚櫒锷犺浇镞跺氨寮€濮嬫挱鏀俱€傚厑璁稿€硷细true/false
 * @param {string} 	options.quality 			璁剧疆flash鎾斁镄勭敾璐紝鍏佽链硷细low/medium/high/autolow/autohigh/best
 * @param {string} 	options.scale 				璁剧疆flash鍐呭濡备綍缂╂斁鏉ラ€傚簲璁剧疆镄勫楂朴€傚厑璁稿€硷细showall/noborder/exactfit
 * @param {string} 	options.wmode 				璁剧疆flash镄勬樉绀烘ā寮忋€傚厑璁稿€硷细window/opaque/transparent
 * @param {string} 	options.allowscriptaccess 	璁剧疆flash涓庨〉闱㈢殑阃氢俊鏉冮檺銆傚厑璁稿€硷细always/never/sameDomain
 * @param {string} 	options.allownetworking 	璁剧疆swf鏂囦欢涓厑璁镐娇鐢ㄧ殑缃戠粶API銆傚厑璁稿€硷细all/internal/none
 * @param {boolean} options.allowfullscreen 	鏄惁鍏佽flash鍏ㄥ睆銆傚厑璁稿€硷细true/false
 * @param {boolean} options.seamlesstabbing 	鍏佽璁剧疆镓ц镞犵绅璺虫牸锛屼粠钥屼娇鐢ㄦ埛鑳借烦鍑篺lash搴旗敤绋嫔簭銆傝鍙傛暟鍙兘鍦ㄥ畨瑁匜lash7鍙婃洿楂樼増链殑Windows涓娇鐢ㄣ€傚厑璁稿€硷细true/false
 * @param {boolean} options.devicefont 			璁剧疆闱欐€佹枃链璞℃槸鍚︿互璁惧瀛椾綋锻堢幇銆傚厑璁稿€硷细true/false
 * @param {boolean} options.swliveconnect 		绗竴娆″姞杞絝lash镞舵祻瑙埚櫒鏄惁搴斿惎锷↗ava銆傚厑璁稿€硷细true/false
 * @param {Object} 	options.vars 				瑕佷紶阃掔粰flash镄勫弬鏁帮紝鏀寔JSON鎴杝tring绫诲瀷銆? * 
 * @see baidu.swf.create
 * @meta standard
 * @returns {string} flash瀵硅薄镄删tml瀛楃涓? */
baidu.swf.createHTML = function (options) {
    options = options || {};
    var version = baidu.swf.version, 
        needVersion = options['ver'] || '6.0.0', 
        vUnit1, vUnit2, i, k, len, item, tmpOpt = {},
        encodeHTML = baidu.string.encodeHTML;
    for (k in options) {
        tmpOpt[k] = options[k];
    }
    options = tmpOpt;
    if (version) {
        version = version.split('.');
        needVersion = needVersion.split('.');
        for (i = 0; i < 3; i++) {
            vUnit1 = parseInt(version[i], 10);
            vUnit2 = parseInt(needVersion[i], 10);
            if (vUnit2 < vUnit1) {
                break;
            } else if (vUnit2 > vUnit1) {
                return '';
            }
        }
    } else {
        return '';
    }
    
    var vars = options['vars'],
        objProperties = ['classid', 'codebase', 'id', 'width', 'height', 'align'];
    options['align'] = options['align'] || 'middle';
    options['classid'] = 'clsid:d27cdb6e-ae6d-11cf-96b8-444553540000';
    options['codebase'] = 'http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0';
    options['movie'] = options['url'] || '';
    delete options['vars'];
    delete options['url'];
    if ('string' == typeof vars) {
        options['flashvars'] = vars;
    } else {
        var fvars = [];
        for (k in vars) {
            item = vars[k];
            fvars.push(k + "=" + encodeURIComponent(item));
        }
        options['flashvars'] = fvars.join('&');
    }
    var str = ['<object '];
    for (i = 0, len = objProperties.length; i < len; i++) {
        item = objProperties[i];
        str.push(' ', item, '="', encodeHTML(options[item]), '"');
    }
    str.push('>');
    var params = {
        'wmode'             : 1,
        'scale'             : 1,
        'quality'           : 1,
        'play'              : 1,
        'loop'              : 1,
        'menu'              : 1,
        'salign'            : 1,
        'bgcolor'           : 1,
        'base'              : 1,
        'allowscriptaccess' : 1,
        'allownetworking'   : 1,
        'allowfullscreen'   : 1,
        'seamlesstabbing'   : 1,
        'devicefont'        : 1,
        'swliveconnect'     : 1,
        'flashvars'         : 1,
        'movie'             : 1
    };
    
    for (k in options) {
        item = options[k];
        k = k.toLowerCase();
        if (params[k] && (item || item === false || item === 0)) {
            str.push('<param name="' + k + '" value="' + encodeHTML(item) + '" />');
        }
    }
    options['src']  = options['movie'];
    options['name'] = options['id'];
    delete options['id'];
    delete options['movie'];
    delete options['classid'];
    delete options['codebase'];
    options['type'] = 'application/x-shockwave-flash';
    options['pluginspage'] = 'http://www.macromedia.com/go/getflashplayer';
    str.push('<embed');
    var salign;
    for (k in options) {
        item = options[k];
        if (item || item === false || item === 0) {
            if ((new RegExp("^salign\x24", "i")).test(k)) {
                salign = item;
                continue;
            }
            
            str.push(' ', k, '="', encodeHTML(item), '"');
        }
    }
    
    if (salign) {
        str.push(' salign="', encodeHTML(salign), '"');
    }
    str.push('></embed></object>');
    
    return str.join('');
};


/**
 * 鍦ㄩ〉闱腑鍒涘缓涓€涓猣lash瀵硅薄
 * @name baidu.swf.create
 * @function
 * @grammar baidu.swf.create(options[, container])
 * 
 * @param {Object} 	options 					鍒涘缓flash镄勯€夐」鍙傛暟
 * @param {string} 	options.id 					瑕佸垱寤虹殑flash镄勬爣璇? * @param {string} 	options.url 				flash鏂囦欢镄剈rl
 * @param {String} 	options.errorMessage 		链畨瑁协lash player鎴杅lash player鐗堟湰鍙疯绷浣庢椂镄勬彁绀? * @param {string} 	options.ver 				链€浣庨渶瑕佺殑flash player鐗堟湰鍙? * @param {string} 	options.width 				flash镄勫搴? * @param {string} 	options.height 				flash镄勯佩搴? * @param {string} 	options.align 				flash镄勫榻愭柟寮忥紝鍏佽链硷细middle/left/right/top/bottom
 * @param {string} 	options.base 				璁剧疆鐢ㄤ簬瑙ｆ瀽swf鏂囦欢涓殑镓€链夌浉瀵硅矾寰勮鍙ョ殑鍩烘湰鐩綍鎴朥RL
 * @param {string} 	options.bgcolor 			swf鏂囦欢镄勮儗鏅壊
 * @param {string} 	options.salign 				璁剧疆缂╂斁镄剆wf鏂囦欢鍦ㄧ敱width鍜宧eight璁剧疆瀹氢箟镄勫尯鍩熷唴镄勪綅缃€傚厑璁稿€硷细l/r/t/b/tl/tr/bl/br
 * @param {boolean} options.menu 				鏄惁鏄剧ず鍙抽敭凿滃崟锛屽厑璁稿€硷细true/false
 * @param {boolean} options.loop 				鎾斁鍒版渶鍚庝竴甯ф椂鏄惁閲嶆柊鎾斁锛屽厑璁稿€硷细 true/false
 * @param {boolean} options.play 				flash鏄惁鍦ㄦ祻瑙埚櫒锷犺浇镞跺氨寮€濮嬫挱鏀俱€傚厑璁稿€硷细true/false
 * @param {string} 	options.quality 			璁剧疆flash鎾斁镄勭敾璐紝鍏佽链硷细low/medium/high/autolow/autohigh/best
 * @param {string} 	options.scale 				璁剧疆flash鍐呭濡备綍缂╂斁鏉ラ€傚簲璁剧疆镄勫楂朴€傚厑璁稿€硷细showall/noborder/exactfit
 * @param {string} 	options.wmode 				璁剧疆flash镄勬樉绀烘ā寮忋€傚厑璁稿€硷细window/opaque/transparent
 * @param {string} 	options.allowscriptaccess 	璁剧疆flash涓庨〉闱㈢殑阃氢俊鏉冮檺銆傚厑璁稿€硷细always/never/sameDomain
 * @param {string} 	options.allownetworking 	璁剧疆swf鏂囦欢涓厑璁镐娇鐢ㄧ殑缃戠粶API銆傚厑璁稿€硷细all/internal/none
 * @param {boolean} options.allowfullscreen 	鏄惁鍏佽flash鍏ㄥ睆銆傚厑璁稿€硷细true/false
 * @param {boolean} options.seamlesstabbing 	鍏佽璁剧疆镓ц镞犵绅璺虫牸锛屼粠钥屼娇鐢ㄦ埛鑳借烦鍑篺lash搴旗敤绋嫔簭銆傝鍙傛暟鍙兘鍦ㄥ畨瑁匜lash7鍙婃洿楂樼増链殑Windows涓娇鐢ㄣ€傚厑璁稿€硷细true/false
 * @param {boolean} options.devicefont 			璁剧疆闱欐€佹枃链璞℃槸鍚︿互璁惧瀛椾綋锻堢幇銆傚厑璁稿€硷细true/false
 * @param {boolean} options.swliveconnect 		绗竴娆″姞杞絝lash镞舵祻瑙埚櫒鏄惁搴斿惎锷↗ava銆傚厑璁稿€硷细true/false
 * @param {Object} 	options.vars 				瑕佷紶阃掔粰flash镄勫弬鏁帮紝鏀寔JSON鎴杝tring绫诲瀷銆? * 
 * @param {HTMLElement|string} [container] 		flash瀵硅薄镄勭埗瀹瑰櫒鍏幂礌锛屼笉浼犻€掕鍙傛暟镞跺湪褰揿墠浠ｇ爜浣岖疆鍒涘缓flash瀵硅薄銆? * @meta standard
 * @see baidu.swf.createHTML,baidu.swf.getMovie
 */
baidu.swf.create = function (options, target) {
    options = options || {};
    var html = baidu.swf.createHTML(options) 
               || options['errorMessage'] 
               || '';
                
    if (target && 'string' == typeof target) {
        target = document.getElementById(target);
    }
    baidu.dom.insertHTML( target || document.body ,'beforeEnd',html );
};
/**
 * 鍒ゆ柇鏄惁涓篿e娴忚鍣? * @name baidu.browser.ie
 * @field
 * @grammar baidu.browser.ie
 * @returns {Number} IE鐗堟湰鍙? */
baidu.browser.ie = baidu.ie = /msie (\d+\.\d+)/i.test(navigator.userAgent) ? (document.documentMode || + RegExp['\x241']) : undefined;

/**
 * 绉婚櫎鏁扮粍涓殑椤? * @name baidu.array.remove
 * @function
 * @grammar baidu.array.remove(source, match)
 * @param {Array} source 闇€瑕佺Щ闄ら」镄勬暟缁? * @param {Any} match 瑕佺Щ闄ょ殑椤? * @meta standard
 * @see baidu.array.removeAt
 *             
 * @returns {Array} 绉婚櫎鍚庣殑鏁扮粍
 */
baidu.array.remove = function (source, match) {
    var len = source.length;
        
    while (len--) {
        if (len in source && source[len] === match) {
            source.splice(len, 1);
        }
    }
    return source;
};

/**
 * 鍒ゆ柇鐩爣鍙傛暟鏄惁Array瀵硅薄
 * @name baidu.lang.isArray
 * @function
 * @grammar baidu.lang.isArray(source)
 * @param {Any} source 鐩爣鍙傛暟
 * @meta standard
 * @see baidu.lang.isString,baidu.lang.isObject,baidu.lang.isNumber,baidu.lang.isElement,baidu.lang.isBoolean,baidu.lang.isDate
 *             
 * @returns {boolean} 绫诲瀷鍒ゆ柇缁撴灉
 */
baidu.lang.isArray = function (source) {
    return '[object Array]' == Object.prototype.toString.call(source);
};



/**
 * 灏嗕竴涓彉閲忚浆鎹㈡垚array
 * @name baidu.lang.toArray
 * @function
 * @grammar baidu.lang.toArray(source)
 * @param {mix} source 闇€瑕佽浆鎹㈡垚array镄勫彉閲? * @version 1.3
 * @meta standard
 * @returns {array} 杞崲鍚庣殑array
 */
baidu.lang.toArray = function (source) {
    if (source === null || source === undefined)
        return [];
    if (baidu.lang.isArray(source))
        return source;
    if (typeof source.length !== 'number' || typeof source === 'string' || baidu.lang.isFunction(source)) {
        return [source];
    }
    if (source.item) {
        var l = source.length, array = new Array(l);
        while (l--)
            array[l] = source[l];
        return array;
    }

    return [].slice.call(source);
};

/**
 * 銮峰缑flash瀵硅薄镄勫疄渚? * @name baidu.swf.getMovie
 * @function
 * @grammar baidu.swf.getMovie(name)
 * @param {string} name flash瀵硅薄镄勫悕绉? * @see baidu.swf.create
 * @meta standard
 * @returns {HTMLElement} flash瀵硅薄镄勫疄渚? */
baidu.swf.getMovie = function (name) {
	var movie = document[name], ret;
    return baidu.browser.ie == 9 ?
    	movie && movie.length ? 
    		(ret = baidu.array.remove(baidu.lang.toArray(movie),function(item){
    			return item.tagName.toLowerCase() != "embed";
    		})).length == 1 ? ret[0] : ret
    		: movie
    	: movie || window[name];
};


baidu.flash._Base = (function(){
   
    var prefix = 'bd__flash__';

    /**
     * 鍒涘缓涓€涓殢链虹殑瀛楃涓?     * @private
     * @return {String}
     */
    function _createString(){
        return  prefix + Math.floor(Math.random() * 2147483648).toString(36);
    };
   
    /**
     * 妫€镆lash钟舵€?     * @private
     * @param {Object} target flash瀵硅薄
     * @return {Boolean}
     */
    function _checkReady(target){
        if(typeof target !== 'undefined' && typeof target.flashInit !== 'undefined' && target.flashInit()){
            return true;
        }else{
            return false;
        }
    };

    /**
     * 璋幂敤涔嫔墠杩涜铡嬫爤镄勫嚱鏁?     * @private
     * @param {Array} callQueue 璋幂敤阒熷垪
     * @param {Object} target flash瀵硅薄
     * @return {Null}
     */
    function _callFn(callQueue, target){
        var result = null;
        
        callQueue = callQueue.reverse();
        baidu.each(callQueue, function(item){
            result = target.call(item.fnName, item.params);
            item.callBack(result);
        });
    };

    /**
     * 涓轰紶鍏ョ殑鍖垮悕鍑芥暟鍒涘缓鍑芥暟鍚?     * @private
     * @param {String|Function} fun 浼犲叆镄勫尶鍚嶅嚱鏁版垨钥呭嚱鏁板悕
     * @return {String}
     */
    function _createFunName(fun){
        var name = '';

        if(baidu.lang.isFunction(fun)){
            name = _createString();
            window[name] = function(){
                fun.apply(window, arguments);
            };

            return name;
        }else if(baidu.lang.isString){
            return fun;
        }
    };

    /**
     * 缁桦埗flash
     * @private
     * @param {Object} options 鍒涘缓鍙傛暟
     * @return {Object} 
     */
    function _render(options){
        if(!options.id){
            options.id = _createString();
        }
        
        var container = options.container || '';
        delete(options.container);
        
        baidu.swf.create(options, container);
        
        return baidu.swf.getMovie(options.id);
    };

    return function(options, callBack){
        var me = this,
            autoRender = (typeof options.autoRender !== 'undefined' ? options.autoRender : true),
            createOptions = options.createOptions || {},
            target = null,
            isReady = false,
            callQueue = [],
            timeHandle = null,
            callBack = callBack || [];

        /**
         * 灏唂lash鏂囦欢缁桦埗鍒伴〉闱笂
         * @public
         * @return {Null}
         */
        me.render = function(){
            target = _render(createOptions);
            
            if(callBack.length > 0){
                baidu.each(callBack, function(funName, index){
                    callBack[index] = _createFunName(options[funName] || new Function());
                });    
            }
            me.call('setJSFuncName', [callBack]);
        };

        /**
         * 杩斿洖flash钟舵€?         * @return {Boolean}
         */
        me.isReady = function(){
            return isReady;
        };

        /**
         * 璋幂敤flash鎺ュ彛镄勭粺涓€鍏ュ彛
         * @param {String} fnName 璋幂敤镄勫嚱鏁板悕
         * @param {Array} params 浼犲叆镄勫弬鏁扮粍鎴愮殑鏁扮粍,鑻ヤ笉璁歌鍙傛暟锛岄渶浼犲叆绌烘暟缁?         * @param {Function} [callBack] 寮傛璋幂敤鍚庡皢杩斿洖链间綔涓哄弬鏁扮殑璋幂敤锲炶皟鍑芥暟锛屽镞犺繑锲炲€硷紝鍙互涓崭紶鍏ユ鍙傛暟
         * @return {Null}
        */
        me.call = function(fnName, params, callBack){
            if(!fnName) return null;
            callBack = callBack || new Function();

            var result = null;
    
            if(isReady){
                result = target.call(fnName, params);
                callBack(result);
            }else{
                callQueue.push({
                    fnName: fnName,
                    params: params,
                    callBack: callBack
                });
    
                (!timeHandle) && (timeHandle = setInterval(_check, 200));
            }
        };
    
        /**
         * 涓轰紶鍏ョ殑鍖垮悕鍑芥暟鍒涘缓鍑芥暟鍚?         * @public
         * @param {String|Function} fun 浼犲叆镄勫尶鍚嶅嚱鏁版垨钥呭嚱鏁板悕
         * @return {String}
         */
        me.createFunName = function(fun){
            return _createFunName(fun);    
        };

        /**
         * 妫€镆lash鏄惁ready锛?骞惰繘琛岃皟鐢?         * @private
         * @return {Null}
         */
        function _check(){
            if(_checkReady(target)){
                clearInterval(timeHandle);
                timeHandle = null;
                _call();

                isReady = true;
            }               
        };

        /**
         * 璋幂敤涔嫔墠杩涜铡嬫爤镄勫嚱鏁?         * @private
         * @return {Null}
         */
        function _call(){
            _callFn(callQueue, target);
            callQueue = [];
        }

        autoRender && me.render(); 
    };
})();



/**
 * 鍒涘缓flash based imageUploader
 * @class
 * @grammar baidu.flash.imageUploader(options)
 * @param {Object} createOptions 鍒涘缓flash镞堕渶瑕佺殑鍙傛暟锛岃鍙傜収baidu.swf.create鏂囨。
 * @config {Object} vars 鍒涘缓imageUploader镞舵墍闇€瑕佺殑鍙傛暟
 * @config {Number} vars.gridWidth 姣忎竴涓瑙埚浘鐗囨墍鍗犵殑瀹藉害锛屽簲璇ヤ负flash瀵涚殑鏁撮櫎
 * @config {Number} vars.gridHeight 姣忎竴涓瑙埚浘鐗囨墍鍗犵殑楂桦害锛屽簲璇ヤ负flash楂樼殑鏁撮櫎
 * @config {Number} vars.picWidth 鍗曞紶棰勮锲剧墖镄勫搴? * @config {Number} vars.picHeight 鍗曞紶棰勮锲剧墖镄勯佩搴? * @config {String} vars.uploadDataFieldName POST璇锋眰涓浘鐗囨暟鎹殑key,榛樿链?picdata'
 * @config {String} vars.picDescFieldName POST璇锋眰涓浘鐗囨弿杩扮殑key,榛樿链?picDesc'
 * @config {Number} vars.maxSize 鏂囦欢镄勬渶澶т綋绉?鍗曚綅'MB'
 * @config {Number} vars.compressSize 涓娄紶鍓嶅鏋滃浘鐗囦綋绉秴杩囱链硷紝浼氩厛铡嬬缉
 * @config {Number} vars.maxNum:32 链€澶т笂浼犲灏戜釜鏂囦欢
 * @config {Number} vars.compressLength 鑳芥帴鍙楃殑链€澶ц竟闀匡紝瓒呰绷璇ュ€间细绛夋瘮铡嬬缉
 * @config {String} vars.url 涓娄紶镄剈rl鍦板潃
 * @config {Number} vars.mode mode == 0镞讹紝鏄娇鐢ㄦ粴锷ㄦ浔锛宫ode == 1镞讹紝鎷変几flash, 榛樿链间负0
 * @see baidu.swf.createHTML
 * @param {String} backgroundUrl 鑳屾櫙锲剧墖璺缎
 * @param {String} listBacgroundkUrl 甯冨眬鎺т欢鑳屾櫙
 * @param {String} buttonUrl 鎸夐挳锲剧墖涓嶈儗鏅? * @param {String|Function} selectFileCallback 阃夋嫨鏂囦欢镄勫洖璋? * @param {String|Function} exceedFileCallback鏂囦欢瓒呭嚭闄愬埗镄勬渶澶т綋绉椂镄勫洖璋? * @param {String|Function} deleteFileCallback 鍒犻櫎鏂囦欢镄勫洖璋? * @param {String|Function} startUploadCallback 寮€濮嬩笂浼犳煇涓枃浠舵椂镄勫洖璋? * @param {String|Function} uploadCompleteCallback 镆愪釜鏂囦欢涓娄紶瀹屾垚镄勫洖璋? * @param {String|Function} uploadErrorCallback 镆愪釜鏂囦欢涓娄紶澶辫触镄勫洖璋? * @param {String|Function} allCompleteCallback 鍏ㄩ儴涓娄紶瀹屾垚镞剁殑锲炶皟
 * @param {String|Function} changeFlashHeight 鏀瑰彉Flash镄勯佩搴︼紝mode==1镄勬椂链欐墠链夌敤
 */ 
baidu.flash.imageUploader = baidu.flash.imageUploader || function(options){
   
    var me = this,
        options = options || {},
        _flash = new baidu.flash._Base(options, [
            'selectFileCallback', 
            'exceedFileCallback', 
            'deleteFileCallback', 
            'startUploadCallback',
            'uploadCompleteCallback',
            'uploadErrorCallback',
            'allCompleteCallback',
            'changeFlashHeight'
        ]);
    /**
     * 寮€濮嬫垨锲炲涓娄紶锲剧墖
     * @public
     * @return {Null}
     */
    me.upload = function(){
        _flash.call('upload');
    };

    /**
     * 鏆傚仠涓娄紶锲剧墖
     * @public
     * @return {Null}
     */
    me.pause = function(){
        _flash.call('pause');
    };
    me.addCustomizedParams = function(index,obj){
        _flash.call('addCustomizedParams',[index,obj]);
    }
};

/**
 * 鎿崭綔铡熺敓瀵硅薄镄勬柟娉? * @namespace baidu.object
 */
baidu.object = baidu.object || {};


/**
 * 灏嗘簮瀵硅薄镄勬墍链夊睘镐ф嫹璐濆埌鐩爣瀵硅薄涓? * @author erik
 * @name baidu.object.extend
 * @function
 * @grammar baidu.object.extend(target, source)
 * @param {Object} target 鐩爣瀵硅薄
 * @param {Object} source 婧愬璞? * @see baidu.array.merge
 * @remark
 * 
1.鐩爣瀵硅薄涓紝涓庢簮瀵硅薄key鐩稿悓镄勬垚锻桦皢浼氲瑕嗙洊銆?br>
2.婧愬璞＄殑prototype鎴愬憳涓崭细鎷疯礉銆?		
 * @shortcut extend
 * @meta standard
 *             
 * @returns {Object} 鐩爣瀵硅薄
 */
baidu.extend =
baidu.object.extend = function (target, source) {
    for (var p in source) {
        if (source.hasOwnProperty(p)) {
            target[p] = source[p];
        }
    }
    
    return target;
};





/**
 * 鍒涘缓flash based fileUploader
 * @class
 * @grammar baidu.flash.fileUploader(options)
 * @param {Object} options
 * @config {Object} createOptions 鍒涘缓flash镞堕渶瑕佺殑鍙傛暟锛岃鍙傜収baidu.swf.create鏂囨。
 * @config {String} createOptions.width
 * @config {String} createOptions.height
 * @config {Number} maxNum 链€澶у彲阃夋枃浠舵暟
 * @config {Function|String} selectFile
 * @config {Function|String} exceedMaxSize
 * @config {Function|String} deleteFile
 * @config {Function|String} uploadStart
 * @config {Function|String} uploadComplete
 * @config {Function|String} uploadError
 * @config {Function|String} uploadProgress
 */
baidu.flash.fileUploader = baidu.flash.fileUploader || function(options){
    var me = this,
        options = options || {};
    
    options.createOptions = baidu.extend({
        wmod: 'transparent'
    },options.createOptions || {});
    
    var _flash = new baidu.flash._Base(options, [
        'selectFile',
        'exceedMaxSize',
        'deleteFile',
        'uploadStart',
        'uploadComplete',
        'uploadError', 
        'uploadProgress'
    ]);

    _flash.call('setMaxNum', options.maxNum ? [options.maxNum] : [1]);

    /**
     * 璁剧疆褰挞紶镙囩Щ锷ㄥ埌flash涓婃椂锛屾槸鍚﹀彉鎴愭坠鍨?     * @public
     * @param {Boolean} isCursor
     * @return {Null}
     */
    me.setHandCursor = function(isCursor){
        _flash.call('setHandCursor', [isCursor || false]);
    };

    /**
     * 璁剧疆榧犳爣鐩稿簲鍑芥暟鍚?     * @param {String|Function} fun
     */
    me.setMSFunName = function(fun){
        _flash.call('setMSFunName',[_flash.createFunName(fun)]);
    }; 

    /**
     * 镓ц涓娄紶鎿崭綔
     * @param {String} url 涓娄紶镄剈rl
     * @param {String} fieldName 涓娄紶镄勮〃鍗曞瓧娈靛悕
     * @param {Object} postData 阌€煎锛屼笂浼犵殑POST鏁版嵁
     * @param {Number|Array|null|-1} [index]涓娄紶镄勬枃浠跺簭鍒?     *                            Int链间笂浼犺鏂囦欢
     *                            Array涓€娆′覆琛屼笂浼犺搴忓垪鏂囦欢
     *                            -1/null涓娄紶镓€链夋枃浠?     * @return {Null}
     */
    me.upload = function(url, fieldName, postData, index){

        if(typeof url !== 'string' || typeof fieldName !== 'string') return null;
        if(typeof index === 'undefined') index = -1;

        _flash.call('upload', [url, fieldName, postData, index]);
    };

    /**
     * 鍙栨秷涓娄紶鎿崭綔
     * @public
     * @param {Number|-1} index
     */
    me.cancel = function(index){
        if(typeof index === 'undefined') index = -1;
        _flash.call('cancel', [index]);
    };

    /**
     * 鍒犻櫎鏂囦欢
     * @public
     * @param {Number|Array} [index] 瑕佸垹闄ょ殑index锛屼笉浼犲垯鍏ㄩ儴鍒犻櫎
     * @param {Function} callBack
     * */
    me.deleteFile = function(index, callBack){

        var callBackAll = function(list){
                callBack && callBack(list);
            };

        if(typeof index === 'undefined'){
            _flash.call('deleteFilesAll', [], callBackAll);
            return;
        };
        
        if(typeof index === 'Number') index = [index];
        index.sort(function(a,b){
            return b-a;
        });
        baidu.each(index, function(item){
            _flash.call('deleteFileBy', item, callBackAll);
        });
    };

    /**
     * 娣诲姞鏂囦欢绫诲瀷锛屾敮鎸乵acType
     * @public
     * @param {Object|Array[Object]} type {description:String, extention:String}
     * @return {Null};
     */
    me.addFileType = function(type){
        var type = type || [[]];
        
        if(type instanceof Array) type = [type];
        else type = [[type]];
        _flash.call('addFileTypes', type);
    };
    
    /**
     * 璁剧疆鏂囦欢绫诲瀷锛屾敮鎸乵acType
     * @public
     * @param {Object|Array[Object]} type {description:String, extention:String}
     * @return {Null};
     */
    me.setFileType = function(type){
        var type = type || [[]];
        
        if(type instanceof Array) type = [type];
        else type = [[type]];
        _flash.call('setFileTypes', type);
    };

    /**
     * 璁剧疆鍙€夋枃浠剁殑鏁伴噺闄愬埗
     * @public
     * @param {Number} num
     * @return {Null}
     */
    me.setMaxNum = function(num){
        _flash.call('setMaxNum', [num]);
    };

    /**
     * 璁剧疆鍙€夋枃浠跺ぇ灏忛檺鍒讹紝浠ュ厗M涓哄崟浣?     * @public
     * @param {Number} num,0涓烘棤闄愬埗
     * @return {Null}
     */
    me.setMaxSize = function(num){
        _flash.call('setMaxSize', [num]);
    };

    /**
     * @public
     */
    me.getFileAll = function(callBack){
        _flash.call('getFileAll', [], callBack);
    };

    /**
     * @public
     * @param {Number} index
     * @param {Function} [callBack]
     */
    me.getFileByIndex = function(index, callBack){
        _flash.call('getFileByIndex', [], callBack);
    };

    /**
     * @public
     * @param {Number} index
     * @param {function} [callBack]
     */
    me.getStatusByIndex = function(index, callBack){
        _flash.call('getStatusByIndex', [], callBack);
    };
};

/**
 * 浣跨敤锷ㄦ€乻cript镙囩璇锋眰链嶅姟鍣ㄨ祫婧愶紝鍖呮嫭鐢辨湇锷″櫒绔殑锲炶皟鍜屾祻瑙埚櫒绔殑锲炶皟
 * @namespace baidu.sio
 */
baidu.sio = baidu.sio || {};

/**
 * 
 * @param {HTMLElement} src script鑺傜偣
 * @param {String} url script鑺傜偣镄勫湴鍧€
 * @param {String} [charset] 缂栫爜
 */
baidu.sio._createScriptTag = function(scr, url, charset){
    scr.setAttribute('type', 'text/javascript');
    charset && scr.setAttribute('charset', charset);
    scr.setAttribute('src', url);
    document.getElementsByTagName('head')[0].appendChild(scr);
};

/**
 * 鍒犻櫎script镄勫睘镐э紝鍐嶅垹闄cript镙囩锛屼互瑙ｅ喅淇鍐呭瓨娉勬纺镄勯棶棰? * 
 * @param {HTMLElement} src script鑺傜偣
 */
baidu.sio._removeScriptTag = function(scr){
    if (scr.clearAttributes) {
        scr.clearAttributes();
    } else {
        for (var attr in scr) {
            if (scr.hasOwnProperty(attr)) {
                delete scr[attr];
            }
        }
    }
    if(scr && scr.parentNode){
        scr.parentNode.removeChild(scr);
    }
    scr = null;
};


/**
 * 阃氲绷script镙囩锷犺浇鏁版嵁锛屽姞杞藉畲鎴愮敱娴忚鍣ㄧ瑙﹀彂锲炶皟
 * @name baidu.sio.callByBrowser
 * @function
 * @grammar baidu.sio.callByBrowser(url, opt_callback, opt_options)
 * @param {string} url 锷犺浇鏁版嵁镄剈rl
 * @param {Function|string} opt_callback 鏁版嵁锷犺浇缁撴潫镞惰皟鐢ㄧ殑鍑芥暟鎴栧嚱鏁板悕
 * @param {Object} opt_options 鍏朵粬鍙€夐」
 * @config {String} [charset] script镄勫瓧绗﹂泦
 * @config {Integer} [timeOut] 瓒呮椂镞堕棿锛岃秴杩囱繖涓椂闂村皢涓嶅啀鍝嶅簲链姹傦紝骞惰Е鍙憃nfailure鍑芥暟
 * @config {Function} [onfailure] timeOut璁惧畾鍚庢墠鐢熸晥锛屽埌杈捐秴镞舵椂闂存椂瑙﹀彂链嚱鏁? * @remark
 * 1銆佷笌callByServer涓嶅悓锛宑allback鍙傛暟鍙敮鎸丗unction绫诲瀷锛屼笉鏀寔string銆? * 2銆佸鏋滆姹备简涓€涓笉瀛桦湪镄勯〉闱紝callback鍑芥暟鍦↖E/opera涓嬩篃浼氲璋幂敤锛屽洜姝や娇鐢ㄨ€呴渶瑕佸湪onsuccess鍑芥暟涓垽鏂暟鎹槸鍚︽纭姞杞姐€? * @meta standard
 * @see baidu.sio.callByServer
 */
baidu.sio.callByBrowser = function (url, opt_callback, opt_options) {
    var scr = document.createElement("SCRIPT"),
        scriptLoaded = 0,
        options = opt_options || {},
        charset = options['charset'],
        callback = opt_callback || function(){},
        timeOut = options['timeOut'] || 0,
        timer;
    scr.onload = scr.onreadystatechange = function () {
        if (scriptLoaded) {
            return;
        }
        
        var readyState = scr.readyState;
        if ('undefined' == typeof readyState
            || readyState == "loaded"
            || readyState == "complete") {
            scriptLoaded = 1;
            try {
                callback();
                clearTimeout(timer);
            } finally {
                scr.onload = scr.onreadystatechange = null;
                baidu.sio._removeScriptTag(scr);
            }
        }
    };

    if( timeOut ){
        timer = setTimeout(function(){
            scr.onload = scr.onreadystatechange = null;
            baidu.sio._removeScriptTag(scr);
            options.onfailure && options.onfailure();
        }, timeOut);
    }
    
    baidu.sio._createScriptTag(scr, url, charset);
};

/**
 * 阃氲绷script镙囩锷犺浇鏁版嵁锛屽姞杞藉畲鎴愮敱链嶅姟鍣ㄧ瑙﹀彂锲炶皟
 * @name baidu.sio.callByServer
 * @function
 * @grammar baidu.sio.callByServer(url, callback[, opt_options])
 * @param {string} url 锷犺浇鏁版嵁镄剈rl.
 * @param {Function|string} callback 链嶅姟鍣ㄧ璋幂敤镄勫嚱鏁版垨鍑芥暟鍚嶃€傚鏋沧病链夋寚瀹氭湰鍙傛暟锛屽皢鍦║RL涓镓紧ptions['queryField']锅氢负callback镄勬柟娉曞悕.
 * @param {Object} opt_options 锷犺浇鏁版嵁镞剁殑阃夐」.
 * @config {string} [charset] script镄勫瓧绗﹂泦
 * @config {string} [queryField] 链嶅姟鍣ㄧcallback璇锋眰瀛楁鍚嶏紝榛樿涓篶allback
 * @config {Integer} [timeOut] 瓒呮椂镞堕棿(鍗曚綅锛歮s)锛岃秴杩囱繖涓椂闂村皢涓嶅啀鍝嶅簲链姹傦紝骞惰Е鍙憃nfailure鍑芥暟
 * @config {Function} [onfailure] timeOut璁惧畾鍚庢墠鐢熸晥锛屽埌杈捐秴镞舵椂闂存椂瑙﹀彂链嚱鏁? * @remark
 * 濡傛灉url涓凡缁忓寘鍚珏ey涓衡€涣ptions['queryField']钬濈殑query椤癸紝灏嗕细琚浛鎹㈡垚callback涓弬鏁颁紶阃掓垨镊姩鐢熸垚镄勫嚱鏁板悕銆? * @meta standard
 * @see baidu.sio.callByBrowser
 */
baidu.sio.callByServer = /**@function*/function(url, callback, opt_options) {
    var scr = document.createElement('SCRIPT'),
        prefix = 'bd__cbs__',
        callbackName,
        callbackImpl,
        options = opt_options || {},
        charset = options['charset'],
        queryField = options['queryField'] || 'callback',
        timeOut = options['timeOut'] || 0,
        timer,
        reg = new RegExp('(\\?|&)' + queryField + '=([^&]*)'),
        matches;

    if (baidu.lang.isFunction(callback)) {
        callbackName = prefix + Math.floor(Math.random() * 2147483648).toString(36);
        window[callbackName] = getCallBack(0);
    } else if(baidu.lang.isString(callback)){
        callbackName = callback;
    } else {
        if (matches = reg.exec(url)) {
            callbackName = matches[2];
        }
    }

    if( timeOut ){
        timer = setTimeout(getCallBack(1), timeOut);
    }
    url = url.replace(reg, '\x241' + queryField + '=' + callbackName);
    
    if (url.search(reg) < 0) {
        url += (url.indexOf('?') < 0 ? '?' : '&') + queryField + '=' + callbackName;
    }
    baidu.sio._createScriptTag(scr, url, charset);

    /*
     * 杩斿洖涓€涓嚱鏁帮紝鐢ㄤ簬绔嫔嵆锛堟寕鍦╳indow涓婏级鎴栬€呰秴镞讹纸鎸傚湪setTimeout涓级镞舵墽琛?     */
    function getCallBack(onTimeOut){
        /*global callbackName, callback, scr, options;*/
        return function(){
            try {
                if( onTimeOut ){
                    options.onfailure && options.onfailure();
                }else{
                    callback.apply(window, arguments);
                    clearTimeout(timer);
                }
                window[callbackName] = null;
                delete window[callbackName];
            } catch (exception) {
            } finally {
                baidu.sio._removeScriptTag(scr);
            }
        }
    }
};

/**
 * 阃氲绷璇锋眰涓€涓浘鐗囩殑鏂瑰纺浠ゆ湇锷″櫒瀛桦偍涓€鏉℃棩蹇? * @function
 * @grammar baidu.sio.log(url)
 * @param {string} url 瑕佸彂阃佺殑鍦板潃.
 * @author: int08h,leeight
 */
baidu.sio.log = function(url) {
  var img = new Image(),
      key = 'tangram_sio_log_' + Math.floor(Math.random() *
            2147483648).toString(36);
  window[key] = img;

  img.onload = img.onerror = img.onabort = function() {
    img.onload = img.onerror = img.onabort = null;

    window[key] = null;
    img = null;
  };
  img.src = url;
};



/*
 * Tangram
 * Copyright 2009 Baidu Inc. All rights reserved.
 * 
 * path: baidu/json.js
 * author: erik
 * version: 1.1.0
 * date: 2009/12/02
 */


/**
 * 鎿崭綔json瀵硅薄镄勬柟娉? * @namespace baidu.json
 */
baidu.json = baidu.json || {};
/*
 * Tangram
 * Copyright 2009 Baidu Inc. All rights reserved.
 * 
 * path: baidu/json/parse.js
 * author: erik, berg
 * version: 1.2
 * date: 2009/11/23
 */



/**
 * 灏嗗瓧绗︿覆瑙ｆ瀽鎴恓son瀵硅薄銆傛敞锛氢笉浼氲嚜锷ㄧ闄ょ┖镙? * @name baidu.json.parse
 * @function
 * @grammar baidu.json.parse(data)
 * @param {string} source 闇€瑕佽В鏋愮殑瀛楃涓? * @remark
 * 璇ユ柟娉旷殑瀹炵幇涓巈cma-262绗簲鐗堜腑瑙勫畾镄凧SON.parse涓嶅悓锛屾殏镞跺彧鏀寔浼犲叆涓€涓弬鏁般€傚悗缁细杩涜锷熻兘涓板瘜銆? * @meta standard
 * @see baidu.json.stringify,baidu.json.decode
 *             
 * @returns {JSON} 瑙ｆ瀽缁撴灉json瀵硅薄
 */
baidu.json.parse = function (data) {
    //2010/12/09锛氭洿鏂拌呖涓崭娇鐢ㄥ师鐢焢arse锛屼笉妫€娴嬬敤鎴疯緭鍏ユ槸鍚︽纭?    return (new Function("return (" + data + ")"))();
};
/*
 * Tangram
 * Copyright 2009 Baidu Inc. All rights reserved.
 * 
 * path: baidu/json/decode.js
 * author: erik, cat
 * version: 1.3.4
 * date: 2010/12/23
 */



/**
 * 灏嗗瓧绗︿覆瑙ｆ瀽鎴恓son瀵硅薄锛屼负杩囨椂鎺ュ彛锛屼粖鍚庝细琚玝aidu.json.parse浠ｆ浛
 * @name baidu.json.decode
 * @function
 * @grammar baidu.json.decode(source)
 * @param {string} source 闇€瑕佽В鏋愮殑瀛楃涓? * @meta out
 * @see baidu.json.encode,baidu.json.parse
 *             
 * @returns {JSON} 瑙ｆ瀽缁撴灉json瀵硅薄
 */
baidu.json.decode = baidu.json.parse;
/*
 * Tangram
 * Copyright 2009 Baidu Inc. All rights reserved.
 * 
 * path: baidu/json/stringify.js
 * author: erik
 * version: 1.1.0
 * date: 2010/01/11
 */



/**
 * 灏唈son瀵硅薄搴忓垪鍖? * @name baidu.json.stringify
 * @function
 * @grammar baidu.json.stringify(value)
 * @param {JSON} value 闇€瑕佸簭鍒楀寲镄刯son瀵硅薄
 * @remark
 * 璇ユ柟娉旷殑瀹炵幇涓巈cma-262绗簲鐗堜腑瑙勫畾镄凧SON.stringify涓嶅悓锛屾殏镞跺彧鏀寔浼犲叆涓€涓弬鏁般€傚悗缁细杩涜锷熻兘涓板瘜銆? * @meta standard
 * @see baidu.json.parse,baidu.json.encode
 *             
 * @returns {string} 搴忓垪鍖栧悗镄勫瓧绗︿覆
 */
baidu.json.stringify = (function () {
    /**
     * 瀛楃涓插鐞嗘椂闇€瑕佽浆涔夌殑瀛楃琛?     * @private
     */
    var escapeMap = {
        "\b": '\\b',
        "\t": '\\t',
        "\n": '\\n',
        "\f": '\\f',
        "\r": '\\r',
        '"' : '\\"',
        "\\": '\\\\'
    };
    
    /**
     * 瀛楃涓插簭鍒楀寲
     * @private
     */
    function encodeString(source) {
        if (/["\\\x00-\x1f]/.test(source)) {
            source = source.replace(
                /["\\\x00-\x1f]/g, 
                function (match) {
                    var c = escapeMap[match];
                    if (c) {
                        return c;
                    }
                    c = match.charCodeAt();
                    return "\\u00" 
                            + Math.floor(c / 16).toString(16) 
                            + (c % 16).toString(16);
                });
        }
        return '"' + source + '"';
    }
    
    /**
     * 鏁扮粍搴忓垪鍖?     * @private
     */
    function encodeArray(source) {
        var result = ["["], 
            l = source.length,
            preComma, i, item;
            
        for (i = 0; i < l; i++) {
            item = source[i];
            
            switch (typeof item) {
            case "undefined":
            case "function":
            case "unknown":
                break;
            default:
                if(preComma) {
                    result.push(',');
                }
                result.push(baidu.json.stringify(item));
                preComma = 1;
            }
        }
        result.push("]");
        return result.join("");
    }
    
    /**
     * 澶勭悊镞ユ湡搴忓垪鍖栨椂镄勮ˉ板?     * @private
     */
    function pad(source) {
        return source < 10 ? '0' + source : source;
    }
    
    /**
     * 镞ユ湡搴忓垪鍖?     * @private
     */
    function encodeDate(source){
        return '"' + source.getFullYear() + "-" 
                + pad(source.getMonth() + 1) + "-" 
                + pad(source.getDate()) + "T" 
                + pad(source.getHours()) + ":" 
                + pad(source.getMinutes()) + ":" 
                + pad(source.getSeconds()) + '"';
    }
    
    return function (value) {
        switch (typeof value) {
        case 'undefined':
            return 'undefined';
            
        case 'number':
            return isFinite(value) ? String(value) : "null";
            
        case 'string':
            return encodeString(value);
            
        case 'boolean':
            return String(value);
            
        default:
            if (value === null) {
                return 'null';
            } else if (value instanceof Array) {
                return encodeArray(value);
            } else if (value instanceof Date) {
                return encodeDate(value);
            } else {
                var result = ['{'],
                    encode = baidu.json.stringify,
                    preComma,
                    item;
                    
                for (var key in value) {
                    if (Object.prototype.hasOwnProperty.call(value, key)) {
                        item = value[key];
                        switch (typeof item) {
                        case 'undefined':
                        case 'unknown':
                        case 'function':
                            break;
                        default:
                            if (preComma) {
                                result.push(',');
                            }
                            preComma = 1;
                            result.push(encode(key) + ':' + encode(item));
                        }
                    }
                }
                result.push('}');
                return result.join('');
            }
        }
    };
})();
/*
 * Tangram
 * Copyright 2009 Baidu Inc. All rights reserved.
 * 
 * path: baidu/json/encode.js
 * author: erik, cat
 * version: 1.3.4
 * date: 2010/12/23
 */



/**
 * 灏唈son瀵硅薄搴忓垪鍖栵紝涓鸿绷镞舵帴鍙ｏ紝浠婂悗浼氲baidu.json.stringify浠ｆ浛
 * @name baidu.json.encode
 * @function
 * @grammar baidu.json.encode(value)
 * @param {JSON} value 闇€瑕佸簭鍒楀寲镄刯son瀵硅薄
 * @meta out
 * @see baidu.json.decode,baidu.json.stringify
 *             
 * @returns {string} 搴忓垪鍖栧悗镄勫瓧绗︿覆
 */
baidu.json.encode = baidu.json.stringify;
