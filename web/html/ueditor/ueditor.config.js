/**
 * ueditor瀹屾暣閰岖疆椤?
 * 鍙互鍦ㄨ繖閲岄厤缃暣涓紪杈戝櫒镄勭壒镐?
 */
/**************************鎻愮ず********************************
 * 镓€链夎娉ㄩ喷镄勯厤缃」鍧囦负UEditor榛樿链笺€?
 * 淇敼榛樿閰岖疆璇烽鍏堢‘淇濆凡缁忓畲鍏ㄦ槑纭鍙傛暟镄勭湡瀹炵敤阃斻€?
 * 涓昏链変袱绉崭慨鏀规柟妗堬紝涓€绉嶆槸鍙栨秷姝ゅ娉ㄩ喷锛岀劧鍚庝慨鏀规垚瀵瑰簲鍙傛暟锛涘彟涓€绉嶆槸鍦ㄥ疄渚嫔寲缂栬緫鍣ㄦ椂浼犲叆瀵瑰簲鍙傛暟銆?
 * 褰揿崌绾х紪杈戝櫒镞讹紝鍙洿鎺ヤ娇鐢ㄦ棫鐗堥厤缃枃浠舵浛鎹㈡柊鐗堥厤缃枃浠?涓岖敤鎷呭绩镞х増閰岖疆鏂囦欢涓洜缂哄皯鏂板姛鑳芥墍闇€镄勫弬鏁拌€屽镊磋剼链姤阌欍€?
 **************************鎻愮ず********************************/

(function () {

    /**
     * 缂栬緫鍣ㄨ祫婧愭枃浠舵抵璺缎銆傚畠镓€琛ㄧず镄勫惈涔夋槸锛氢互缂栬緫鍣ㄥ疄渚嫔寲椤甸溃涓哄綋鍓嶈矾寰勶紝鎸囧悜缂栬緫鍣ㄨ祫婧愭枃浠讹纸鍗砫ialog绛夋枃浠跺す锛夌殑璺缎銆?
     * 閴翠簬寰埚鍚屽鍦ㄤ娇鐢ㄧ紪杈戝櫒镄勬椂链椤嚭鐜扮殑绉岖璺缎闂锛屾澶勫己鐑埚缓璁ぇ瀹朵娇鐢?鐩稿浜庣綉绔欐抵鐩綍镄勭浉瀵硅矾寰?杩涜閰岖疆銆?
     * "鐩稿浜庣綉绔欐抵鐩綍镄勭浉瀵硅矾寰?涔熷氨鏄互鏂沧潬寮€澶寸殑褰㈠"/myProject/ueditor/"杩欐牱镄勮矾寰勩€?
     * 濡傛灉绔欑偣涓湁澶氢釜涓嶅湪鍚屼竴灞傜骇镄勯〉闱㈤渶瑕佸疄渚嫔寲缂栬緫鍣紝涓斿紩鐢ㄤ简鍚屼竴UEditor镄勬椂链欙紝姝ゅ镄刄RL鍙兘涓嶉€傜敤浜庢疮涓〉闱㈢殑缂栬緫鍣ㄣ€?
     * 锲犳锛孶Editor鎻愪緵浜嗛拡瀵逛笉鍚岄〉闱㈢殑缂栬緫鍣ㄥ彲鍗旷嫭閰岖疆镄勬抵璺缎锛屽叿浣撴潵璇达紝鍦ㄩ渶瑕佸疄渚嫔寲缂栬緫鍣ㄧ殑椤甸溃链€椤堕儴鍐欎笂濡备笅浠ｇ爜鍗冲彲銆傚綋铹讹紝闇€瑕佷护姝ゅ镄刄RL绛変簬瀵瑰簲镄勯厤缃€?
     * window.UEDITOR_HOME_URL = "/xxxx/xxxx/";
     */
    var URL = window.UEDITOR_HOME_URL || getUEBasePath();

    /**
     * 閰岖疆椤逛富浣撱€傛敞镒忥紝姝ゅ镓€链夋秹鍙婂埌璺缎镄勯厤缃埆阆楁纺URL鍙橀噺銆?
     */
    window.UEDITOR_CONFIG = {

        //涓虹紪杈戝櫒瀹炰緥娣诲姞涓€涓矾寰勶紝杩欎釜涓嶈兘琚敞閲?
        UEDITOR_HOME_URL: URL

        // 链嶅姟鍣ㄧ粺涓€璇锋眰鎺ュ彛璺缎
        // , serverUrl: URL + "jsp/controller.jsp"
        , serverUrl: '/ueditorFileUpload'
        //宸ュ叿镙忎笂镄勬墍链夌殑锷熻兘鎸夐挳鍜屼笅鎷夋锛屽彲浠ュ湪new缂栬緫鍣ㄧ殑瀹炰緥镞堕€夋嫨镊繁闇€瑕佺殑浠庢柊瀹氢箟
        , toolbars: [[
            // 'fullscreen', 
            'source', '|', 'undo', 'redo', '|',
            'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
            'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
            'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
            'directionalityltr', 'directionalityrtl', 'indent', '|',
            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
            'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
            'simpleupload', 'insertimage', 'emotion', 'insertvideo'
            // , 'scrawl' , 'insertvideo', ,'music', 'attachment'
            , 'map'
            // , 'gmap', 'insertframe'
            , 'insertcode'
            // , 'webapp' 
            , 'pagebreak', 'template', 'background', '|',
            'horizontal', 'date', 'time', 'spechars'
            // , 'snapscreen'
            // , 'wordimage'
            , '|',
            'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
            'print', 'preview', 'searchreplace', 'help', 'drafts'
        ]]
        //褰挞紶镙囨斁鍦ㄥ伐鍏锋爮涓婃椂鏄剧ず镄则ooltip鎻愮ず,鐣欑┖鏀寔镊姩澶氲瑷€閰岖疆锛屽惁鍒欎互閰岖疆链间负鍑?
        //,labelMap:{
        //    'anchor':'', 'undo':''
        //}

        //璇█閰岖疆椤?榛樿鏄痾h-cn銆傛湁闇€瑕佺殑璇濅篃鍙互浣跨敤濡备笅杩欐牱镄勬柟寮忔潵镊姩澶氲瑷€鍒囨崲锛屽綋铹讹紝鍓嶆彁鏉′欢鏄痩ang鏂囦欢澶逛笅瀛桦湪瀵瑰簲镄勮瑷€鏂囦欢锛?
        //lang链间篃鍙互阃氲绷镊姩銮峰彇 (navigator.language||navigator.browserLanguage ||navigator.userLanguage).toLowerCase()
        //,lang:"zh-cn"
        //,langPath:URL +"lang/"

        //涓婚閰岖疆椤?榛樿鏄痉efault銆傛湁闇€瑕佺殑璇濅篃鍙互浣跨敤濡备笅杩欐牱镄勬柟寮忔潵镊姩澶氢富棰桦垏鎹紝褰撶劧锛屽墠鎻愭浔浠舵槸themes鏂囦欢澶逛笅瀛桦湪瀵瑰簲镄勪富棰樻枃浠讹细
        //鐜版湁濡备笅镄偆:default
        //,theme:'default'
        //,themePath:URL +"themes/"

        //,zIndex : 900     //缂栬緫鍣ㄥ眰绾х殑鍩烘暟,榛樿鏄?00

        //阍埚getAllHtml鏂规硶锛屼细鍦ㄥ搴旗殑head镙囩涓锷犺缂栫爜璁剧疆銆?
        //,charset:"utf-8"

        //鑻ュ疄渚嫔寲缂栬緫鍣ㄧ殑椤甸溃镓嫔姩淇敼镄刣omain锛屾澶勯渶瑕佽缃负true
        //,customDomain:false

        //甯哥敤閰岖疆椤圭洰
        //,isShow : true    //榛樿鏄剧ず缂栬緫鍣?

        //,textarea:'editorValue' // 鎻愪氦琛ㄥ崟镞讹紝链嶅姟鍣ㄨ幏鍙栫紪杈戝櫒鎻愪氦鍐呭镄勬墍鐢ㄧ殑鍙傛暟锛屽瀹炰緥镞跺彲浠ョ粰瀹瑰櫒name灞炴€э紝浼氩皢name缁椤畾镄勫€兼渶涓烘疮涓疄渚嬬殑阌€硷紝涓岖敤姣忔瀹炰緥鍖栫殑镞跺€欓兘璁剧疆杩欎釜链?

        //,initialContent:'娆㈣繋浣跨敤ueditor!'    //鍒濆鍖栫紪杈戝櫒镄勫唴瀹?涔熷彲浠ラ€氲绷textarea/script缁椤€硷紝鐪嫔畼缃戜緥瀛?

        //,autoClearinitialContent:true //鏄惁镊姩娓呴櫎缂栬緫鍣ㄥ垵濮嫔唴瀹癸紝娉ㄦ剰锛氩鏋渇ocus灞炴€ц缃负true,杩欎釜涔熶负鐪燂紝闾ｄ箞缂栬緫鍣ㄤ竴涓婃潵灏变细瑙﹀彂瀵艰嚧鍒濆鍖栫殑鍐呭鐪嬩笉鍒颁简

        //,focus:false //鍒濆鍖栨椂锛屾槸鍚﹁缂栬緫鍣ㄨ幏寰楃剑镣箃rue鎴杅alse

        //濡傛灉镊畾涔夛紝链€濂界粰p镙囩濡备笅镄勮楂桡紝瑕佷笉杈揿叆涓枃镞讹紝浼氭湁璺冲姩镒?
        //,initialStyle:'p{line-height:1em}'//缂栬緫鍣ㄥ眰绾х殑鍩烘暟,鍙互鐢ㄦ潵鏀瑰彉瀛椾綋绛?

        //,iframeCssUrl: URL + '/themes/iframe.css' //缁欑紪杈戝櫒鍐呴儴寮曞叆涓€涓猚ss鏂囦欢

        //indentValue
        //棣栬缂╄繘璺濈,榛樿鏄?em
        //,indentValue:'2em'

        //,initialFrameWidth:1000  //鍒濆鍖栫紪杈戝櫒瀹藉害,榛樿1000
        //,initialFrameHeight:320  //鍒濆鍖栫紪杈戝櫒楂桦害,榛樿320

        //,readonly : false //缂栬緫鍣ㄥ垵濮嫔寲缁撴潫鍚?缂栬緫鍖哄烟鏄惁鏄彧璇荤殑锛岄粯璁ゆ槸false

        //,autoClearEmptyNode : true //getContent镞讹紝鏄惁鍒犻櫎绌虹殑inlineElement鑺傜偣锛埚寘鎷祵濂楃殑鎯呭喌锛?

        //鍚敤镊姩淇濆瓨
        //,enableAutoSave: true
        //镊姩淇濆瓨闂撮殧镞堕棿锛?鍗曚綅ms
        //,saveInterval: 500

        //,fullscreen : false //鏄惁寮€鍚垵濮嫔寲镞跺嵆鍏ㄥ睆锛岄粯璁ゅ叧闂?

        //,imagePopup:true      //锲剧墖鎿崭綔镄勬诞灞傚紑鍏筹紝榛樿镓揿紑

        //,autoSyncData:true //镊姩鍚屾缂栬緫鍣ㄨ鎻愪氦镄勬暟鎹?
        //,emotionLocalization:false //鏄惁寮€鍚〃鎯呮湰鍦板寲锛岄粯璁ゅ叧闂€傝嫢瑕佸紑鍚纭缭emotion鏂囦欢澶逛笅鍖呭惈瀹樼綉鎻愪緵镄刬mages琛ㄦ儏鏂囦欢澶?

        //绮樿创鍙缭鐣欐爣绛撅紝铡婚櫎镙囩镓€链夊睘镐?
        //,retainOnlyLabelPasted: false

        //,pasteplain:false  //鏄惁榛樿涓虹函鏂囨湰绮樿创銆俧alse涓轰笉浣跨敤绾枃链矘璐达紝true涓轰娇鐢ㄧ函鏂囨湰绮樿创
        //绾枃链矘璐存ā寮忎笅镄勮绷婊よ鍒?
        //'filterTxtRules' : function(){
        //    function transP(node){
        //        node.tagName = 'p';
        //        node.setStyle();
        //    }
        //    return {
        //        //鐩存帴鍒犻櫎鍙婂叾瀛楄妭镣瑰唴瀹?
        //        '-' : 'script style object iframe embed input select',
        //        'p': {$:{}},
        //        'br':{$:{}},
        //        'div':{'$':{}},
        //        'li':{'$':{}},
        //        'caption':transP,
        //        'th':transP,
        //        'tr':transP,
        //        'h1':transP,'h2':transP,'h3':transP,'h4':transP,'h5':transP,'h6':transP,
        //        'td':function(node){
        //            //娌℃湁鍐呭镄则d鐩存帴鍒犳帀
        //            var txt = !!node.innerText();
        //            if(txt){
        //                node.parentNode.insertAfter(UE.uNode.createText(' &nbsp; &nbsp;'),node);
        //            }
        //            node.parentNode.removeChild(node,node.innerText())
        //        }
        //    }
        //}()

        //,allHtmlEnabled:false //鎻愪氦鍒板悗鍙扮殑鏁版嵁鏄惁鍖呭惈鏁翠釜html瀛楃涓?

        //insertorderedlist
        //链夊簭鍒楄〃镄勪笅鎷夐厤缃?链肩暀绌烘椂鏀寔澶氲瑷€镊姩璇嗗埆锛岃嫢閰岖疆链硷紝鍒欎互姝ゅ€间负鍑?
        //,'insertorderedlist':{
        //      //镊畾镄勬牱寮?
        //        'num':'1,2,3...',
        //        'num1':'1),2),3)...',
        //        'num2':'(1),(2),(3)...',
        //        'cn':'涓€,浜?涓?...',
        //        'cn1':'涓€),浜?,涓?....',
        //        'cn2':'(涓€),(浜?,(涓?....',
        //     //绯荤粺镊甫
        //     'decimal' : '' ,         //'1,2,3...'
        //     'lower-alpha' : '' ,    // 'a,b,c...'
        //     'lower-roman' : '' ,    //'i,ii,iii...'
        //     'upper-alpha' : '' , lang   //'A,B,C'
        //     'upper-roman' : ''      //'I,II,III...'
        //}

        //insertunorderedlist
        //镞犲簭鍒楄〃镄勪笅鎷夐厤缃紝链肩暀绌烘椂鏀寔澶氲瑷€镊姩璇嗗埆锛岃嫢閰岖疆链硷紝鍒欎互姝ゅ€间负鍑?
        //,insertunorderedlist : { //镊畾镄勬牱寮?
        //    'dash' :'钬?镰存姌鍙?, //-镰存姌鍙?
        //    'dot':' 銆?灏忓浑鍦?, //绯荤粺镊甫
        //    'circle' : '',  // '鈼?灏忓浑鍦?
        //    'disc' : '',    // '鈼?灏忓浑镣?
        //    'square' : ''   //'鈻?灏忔柟鍧?
        //}
        //,listDefaultPaddingLeft : '30'//榛樿镄勫乏杈圭缉杩涚殑鍩烘暟链?
        //,listiconpath : 'http://bs.baidu.com/listicon/'//镊畾涔夋爣鍙风殑璺缎
        //,maxListLevel : 3 //闄愬埗鍙互tab镄勭骇鏁? 璁剧疆-1涓轰笉闄愬埗

        //,autoTransWordToList:false  //绂佹word涓矘璐磋繘鏉ョ殑鍒楄〃镊姩鍙樻垚鍒楄〃镙囩

        //fontfamily
        //瀛椾綋璁剧疆 label鐣欑┖鏀寔澶氲瑷€镊姩鍒囨崲锛岃嫢閰岖疆锛屽垯浠ラ厤缃€间负鍑?
        ,'fontfamily':[
            { label:'',name:'songti',val:'瀹嬩綋,SimSun'},
            { label:'',name:'kaiti',val:'妤蜂綋,妤蜂綋_GB2312, SimKai'},
            { label:'',name:'yahei',val:'寰蒋板呴粦,Microsoft YaHei'},
            { label:'',name:'heiti',val:'榛戜綋, SimHei'},
            { label:'',name:'lishu',val:'闅朵功, SimLi'},
            { label:'',name:'andaleMono',val:'andale mono'},
            { label:'',name:'arial',val:'arial, helvetica,sans-serif'},
            { label:'',name:'arialBlack',val:'arial black,avant garde'},
            { label:'',name:'comicSansMs',val:'comic sans ms'},
            { label:'',name:'impact',val:'impact,chicago'},
            { label:'',name:'timesNewRoman',val:'times new roman'},
            { label:'Courier New',name:'Courier New',val:'Courier New'}
        ]

        //fontsize
        //瀛楀佛
        //,'fontsize':[10, 11, 12, 14, 16, 18, 20, 24, 36]

        //paragraph
        //娈佃惤镙煎纺 链肩暀绌烘椂鏀寔澶氲瑷€镊姩璇嗗埆锛岃嫢閰岖疆锛屽垯浠ラ厤缃€间负鍑?
        //,'paragraph':{'p':'', 'h1':'', 'h2':'', 'h3':'', 'h4':'', 'h5':'', 'h6':''}

        //rowspacingtop
        //娈甸棿璺?链煎拰鏄剧ず镄勫悕瀛楃浉鍚?
        //,'rowspacingtop':['5', '10', '15', '20', '25']

        //rowspacingBottom
        //娈甸棿璺?链煎拰鏄剧ず镄勫悕瀛楃浉鍚?
        //,'rowspacingbottom':['5', '10', '15', '20', '25']

        //lineheight
        //琛屽唴闂磋窛 链煎拰鏄剧ず镄勫悕瀛楃浉鍚?
        //,'lineheight':['1', '1.5','1.75','2', '3', '4', '5']

        //customstyle
        //镊畾涔夋牱寮忥紝涓嶆敮鎸佸浗闄呭寲锛屾澶勯厤缃€煎嵆鍙渶鍚庢樉绀哄€?
        //block镄勫厓绱犳槸渚濇嵁璁剧疆娈佃惤镄勯€昏緫璁剧疆镄勶紝inline镄勫厓绱犱緷鎹瓸IU镄勯€昏緫璁剧疆
        //灏介噺浣跨敤涓€浜涘父鐢ㄧ殑镙囩
        //鍙傛暟璇存槑
        //tag 浣跨敤镄勬爣绛惧悕瀛?
        //label 鏄剧ず镄勫悕瀛椾篃鏄敤鏉ユ爣璇嗕笉鍚岀被鍨嬬殑镙囱瘑绗︼紝娉ㄦ剰杩欎釜链兼疮涓涓嶅悓锛?
        //style 娣诲姞镄勬牱寮?
        //姣忎竴涓璞″氨鏄竴涓嚜瀹氢箟镄勬牱寮?
        //,'customstyle':[
        //    {tag:'h1', name:'tc', label:'', style:'border-bottom:#ccc 2px solid;padding:0 4px 0 0;text-align:center;margin:0 0 20px 0;'},
        //    {tag:'h1', name:'tl',label:'', style:'border-bottom:#ccc 2px solid;padding:0 4px 0 0;margin:0 0 10px 0;'},
        //    {tag:'span',name:'im', label:'', style:'font-style:italic;font-weight:bold'},
        //    {tag:'span',name:'hi', label:'', style:'font-style:italic;font-weight:bold;color:rgb(51, 153, 204)'}
        //]

        //镓揿紑鍙抽敭凿滃崟锷熻兘
        //,enableContextMenu: true
        //鍙抽敭凿滃崟镄勫唴瀹癸紝鍙互鍙傝€僷lugins/contextmenu.js閲岃竟镄勯粯璁よ彍鍗旷殑渚嫔瓙锛宭abel鐣欑┖鏀寔锲介台鍖栵紝鍚﹀垯浠ユ閰岖疆涓哄嗳
        //,contextMenu:[
        //    {
        //        label:'',       //鏄剧ず镄勫悕绉?
        //        cmdName:'selectall',//镓ц镄刢ommand锻戒护锛屽綋镣瑰向杩欎釜鍙抽敭凿滃崟镞?
        //        //exec鍙€夛紝链変简exec灏变细鍦ㄧ偣鍑绘椂镓ц杩欎釜function锛屼紭鍏堢骇楂树簬cmdName
        //        exec:function () {
        //            //this鏄綋鍓岖紪杈戝櫒镄勫疄渚?
        //            //this.ui._dialogs['inserttableDialog'].open();
        //        }
        //    }
        //]

        //蹇嵎凿滃崟
        //,shortcutMenu:["fontfamily", "fontsize", "bold", "italic", "underline", "forecolor", "backcolor", "insertorderedlist", "insertunorderedlist"]

        //elementPathEnabled
        //鏄惁鍚敤鍏幂礌璺缎锛岄粯璁ゆ槸鏄剧ず
        //,elementPathEnabled : true

        //wordCount
        //,wordCount:true          //鏄惁寮€鍚瓧鏁扮粺璁?
        //,maximumWords:10000       //鍏佽镄勬渶澶у瓧绗︽暟
        //瀛楁暟缁熻鎻愮ず锛寋#count}浠ｈ〃褰揿墠瀛楁暟锛寋#leave}浠ｈ〃杩桦彲浠ヨ緭鍏ュ灏戝瓧绗︽暟,鐣欑┖鏀寔澶氲瑷€镊姩鍒囨崲锛屽惁鍒欐寜姝ら厤缃樉绀?
        //,wordCountMsg:''   //褰揿墠宸茶緭鍏?{#count} 涓瓧绗︼紝镇ㄨ缮鍙互杈揿叆{#leave} 涓瓧绗?
        //瓒呭嚭瀛楁暟闄愬埗鎻愮ず  鐣欑┖鏀寔澶氲瑷€镊姩鍒囨崲锛屽惁鍒欐寜姝ら厤缃樉绀?
        //,wordOverFlowMsg:''    //<span style="color:red;">浣犺緭鍏ョ殑瀛楃涓暟宸茬粡瓒呭嚭链€澶у厑璁稿€硷紝链嶅姟鍣ㄥ彲鑳戒细鎷掔粷淇濆瓨锛?/span>

        //tab
        //镣瑰向tab阌椂绉诲姩镄勮窛绂?tabSize链嶆暟锛宼abNode浠€涔埚瓧绗﹀仛涓哄崟浣?
        //,tabSize:4
        //,tabNode:'&nbsp;'

        //removeFormat
        //娓呴櫎镙煎纺镞跺彲浠ュ垹闄ょ殑镙囩鍜屽睘镐?
        //removeForamtTags镙囩
        //,removeFormatTags:'b,big,code,del,dfn,em,font,i,ins,kbd,q,samp,small,span,strike,strong,sub,sup,tt,u,var'
        //removeFormatAttributes灞炴€?
        //,removeFormatAttributes:'class,style,lang,width,height,align,hspace,valign'

        //undo
        //鍙互链€澶氩洖阃€镄勬鏁?榛樿20
        //,maxUndoCount:20
        //褰撹緭鍏ョ殑瀛楃鏁拌秴杩囱链兼椂锛屼缭瀛树竴娆＄幇鍦?
        //,maxInputCount:1

        //autoHeightEnabled
        // 鏄惁镊姩闀块佩,榛樿true
        //,autoHeightEnabled:true

        //scaleEnabled
        //鏄惁鍙互鎷変几闀块佩,榛樿true(褰揿紑鍚椂锛岃嚜锷ㄩ昵楂桦け鏁?
        //,scaleEnabled:false
        //,minFrameWidth:800    //缂栬緫鍣ㄦ嫋锷ㄦ椂链€灏忓搴?榛樿800
        //,minFrameHeight:220  //缂栬緫鍣ㄦ嫋锷ㄦ椂链€灏忛佩搴?榛樿220

        //autoFloatEnabled
        //鏄惁淇濇寔toolbar镄勪綅缃笉锷?榛樿true
        //,autoFloatEnabled:true
        //娴姩镞跺伐鍏锋爮璺濈娴忚鍣ㄩ《閮ㄧ殑楂桦害锛岀敤浜庢煇浜涘叿链夊浐瀹氩ご閮ㄧ殑椤甸溃
        //,topOffset:30
        //缂栬緫鍣ㄥ簳閮ㄨ窛绂诲伐鍏锋爮楂桦害(濡傛灉鍙傛暟澶т簬绛変簬缂栬緫鍣ㄩ佩搴︼紝鍒栾缃棤鏁?
        //,toolbarTopOffset:400

        //pageBreakTag
        //鍒嗛〉镙囱瘑绗?榛樿鏄痏ueditor_page_break_tag_
        //,pageBreakTag:'_ueditor_page_break_tag_'

        //autotypeset
        //镊姩鎺掔増鍙傛暟
        //,autotypeset: {
        //    mergeEmptyline: true,           //鍚埚苟绌鸿
        //    removeClass: true,              //铡绘帀鍐椾綑镄刢lass
        //    removeEmptyline: false,         //铡绘帀绌鸿
        //    textAlign:"left",               //娈佃惤镄勬帓鐗堟柟寮忥紝鍙互鏄?left,right,center,justify 铡绘帀杩欎釜灞炴€ц〃绀轰笉镓ц鎺掔増
        //    imageBlockLine: 'center',       //锲剧墖镄勬诞锷ㄦ柟寮忥紝镫崰涓€琛屽墽涓?宸﹀彸娴姩锛岄粯璁? center,left,right,none 铡绘帀杩欎釜灞炴€ц〃绀轰笉镓ц鎺掔増
        //    pasteFilter: false,             //镙规嵁瑙勫垯杩囨护娌′簨绮樿创杩涙潵镄勫唴瀹?
        //    clearFontSize: false,           //铡绘帀镓€链夌殑鍐呭祵瀛楀佛锛屼娇鐢ㄧ紪杈戝櫒榛樿镄勫瓧鍙?
        //    clearFontFamily: false,         //铡绘帀镓€链夌殑鍐呭祵瀛椾綋锛屼娇鐢ㄧ紪杈戝櫒榛樿镄勫瓧浣?
        //    removeEmptyNode: false,         // 铡绘帀绌鸿妭镣?
        //    //鍙互铡绘帀镄勬爣绛?
        //    removeTagNames: {镙囩鍚嶅瓧:1},
        //    indent: false,                  // 琛岄缂╄繘
        //    indentValue : '2em',            //琛岄缂╄繘镄勫ぇ灏?
        //    bdc2sb: false,
        //    tobdc: false
        //}

        //tableDragable
        //琛ㄦ牸鏄惁鍙互鎷栨嫿
        //,tableDragable: true

        //,disabledTableInTable:true  //绂佹琛ㄦ牸宓屽

        //sourceEditor
        //婧愮爜镄勬煡鐪嬫柟寮?codemirror 鏄唬镰侀佩浜紝textarea鏄枃链,榛樿鏄痗odemirror
        //娉ㄦ剰榛樿codemirror鍙兘鍦╥e8+鍜岄潪ie涓娇鐢?
        //,sourceEditor:"codemirror"
        //濡傛灉sourceEditor鏄痗odemirror锛岃缮鐢ㄩ厤缃竴涓嬩袱涓弬鏁?
        //codeMirrorJsUrl js锷犺浇镄勮矾寰勶紝榛樿鏄?URL + "third-party/codemirror/codemirror.js"
        //,codeMirrorJsUrl:URL + "third-party/codemirror/codemirror.js"
        //codeMirrorCssUrl css锷犺浇镄勮矾寰勶紝榛樿鏄?URL + "third-party/codemirror/codemirror.css"
        //,codeMirrorCssUrl:URL + "third-party/codemirror/codemirror.css"
        //缂栬緫鍣ㄥ垵濮嫔寲瀹屾垚鍚庢槸鍚﹁繘鍏ユ簮镰佹ā寮忥紝榛樿涓哄惁銆?
        //,sourceEditorFirst:false

        //iframeUrlMap
        //dialog鍐呭镄勮矾寰?锝炰细琚浛鎹㈡垚URL,鍨揿睘镐т竴镞︽墦寮€锛屽皢瑕嗙洊镓€链夌殑dialog镄勯粯璁よ矾寰?
        //,iframeUrlMap:{
        //    'anchor':'~/dialogs/anchor/anchor.html',
        //}

        //webAppKey 鐧惧害搴旗敤镄凙PIkey锛屾疮涓珯闀垮繀椤婚鍏埚幓鐧惧害瀹樼綉娉ㄥ唽涓€涓猭ey鍚庢柟鑳芥甯镐娇鐢╝pp锷熻兘锛屾敞鍐屼粙缁嶏紝http://app.baidu.com/static/cms/getapikey.html
        //, webAppKey: ""
    };

    function getUEBasePath(docUrl, confUrl) {

        return getBasePath(docUrl || self.document.URL || self.location.href, confUrl || getConfigFilePath());

    }

    function getConfigFilePath() {

        var configPath = document.getElementsByTagName('script');

        return configPath[ configPath.length - 1 ].src;

    }

    function getBasePath(docUrl, confUrl) {

        var basePath = confUrl;


        if (/^(\/|\\\\)/.test(confUrl)) {

            basePath = /^.+?\w(\/|\\\\)/.exec(docUrl)[0] + confUrl.replace(/^(\/|\\\\)/, '');

        } else if (!/^[a-z]+:/i.test(confUrl)) {

            docUrl = docUrl.split("#")[0].split("?")[0].replace(/[^\\\/]+$/, '');

            basePath = docUrl + "" + confUrl;

        }

        return optimizationPath(basePath);

    }

    function optimizationPath(path) {

        var protocol = /^[a-z]+:\/\//.exec(path)[ 0 ],
            tmp = null,
            res = [];

        path = path.replace(protocol, "").split("?")[0].split("#")[0];

        path = path.replace(/\\/g, '/').split(/\//);

        path[ path.length - 1 ] = "";

        while (path.length) {

            if (( tmp = path.shift() ) === "..") {
                res.pop();
            } else if (tmp !== ".") {
                res.push(tmp);
            }

        }

        return protocol + res.join("/");

    }

    window.UE = {
        getUEBasePath: getUEBasePath
    };

})();
