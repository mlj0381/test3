/**
 * Created with JetBrains PhpStorm.
 * User: xuheng
 * Date: 12-9-26
 * Time: 涓嫔崃1:06
 * To change this template use File | Settings | File Templates.
 */
/**
 * tab镣瑰向澶勭悊浜嬩欢
 * @param tabHeads
 * @param tabBodys
 * @param obj
 */
function clickHandler( tabHeads,tabBodys,obj ) {
    //head镙峰纺镟存敼
    for ( var k = 0, len = tabHeads.length; k < len; k++ ) {
        tabHeads[k].className = "";
    }
    obj.className = "focus";
    //body鏄鹃殣
    var tabSrc = obj.getAttribute( "tabSrc" );
    for ( var j = 0, length = tabBodys.length; j < length; j++ ) {
        var body = tabBodys[j],
            id = body.getAttribute( "id" );
        body.onclick = function(){
            this.style.zoom = 1;
        };
        if ( id != tabSrc ) {
            body.style.zIndex = 1;
        } else {
            body.style.zIndex = 200;
        }
    }

}

/**
 * TAB鍒囨崲
 * @param tabParentId  tab镄勭埗鑺傜偣ID鎴栬€呭璞℃湰韬?
 */
function switchTab( tabParentId ) {
    var tabElements = $G( tabParentId ).children,
        tabHeads = tabElements[0].children,
        tabBodys = tabElements[1].children;

    for ( var i = 0, length = tabHeads.length; i < length; i++ ) {
        var head = tabHeads[i];
        if ( head.className === "focus" )clickHandler(tabHeads,tabBodys, head );
        head.onclick = function () {
            clickHandler(tabHeads,tabBodys,this);
        }
    }
}
switchTab("helptab");

document.getElementById('version').innerHTML = parent.UE.version;