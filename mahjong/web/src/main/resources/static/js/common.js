function modalShow(modalId){
	$("[data-remodal-id="+modalId+"]").remodal().open();
}
function modalClose(modalId){
    $("[data-remodal-id="+modalId+"]").remodal().close();
}
/**
 * 共通モーダルの表示
 */
$(function(){
    var contentHeight = getContenterHeight();
    $('#content .container').css("min-height", contentHeight);

});
$(window).resize(function() {
    var contentHeight = getContenterHeight();
    $('#content .container').css("min-height", contentHeight);
});
function getContenterHeight(){
    var screenHeight = $(window).height();
    var headerHeight = $('#banner').outerHeight();
    var contentFooterHeight = $('.content-footer').outerHeight();
    var footerHeight = $('#footer').outerHeight();
    var contentHeight = screenHeight - headerHeight - contentFooterHeight - footerHeight + 'px';
    return contentHeight;
}

function getUri(baseUri, pathUri, actionUri){
    var uri = baseUri + pathUri;
    if (actionUri){
        uri = uri + actionUri;
    }
    return uri;
}

// 拡張子チェック
function checkExt(allowExts, filename)
{
    var ext = getExt(filename).toLowerCase();
    //許可する拡張子の一覧(allow_exts)から対象の拡張子があるか確認する
    if (allowExts.indexOf(ext) === -1) return false;
    return true;
}

// 新しいウィンドウ開く
function openNew(url)
{
	window.open(url);
}

//ファイル名から拡張子を取得する関数
function getExt(filename)
{
    var pos = filename.lastIndexOf('.');
    if (pos === -1) return '';
    return filename.slice(pos + 1);
}

function checkFile(allowExts, file, errorDiv){
    var $errorDiv = document.getElementById(errorDiv);
    if (!file){
        $errorDiv.innerHTML = 'ファイル必須。';
        return false;
    }
    if (!checkExt(allowExts, file.name)) {
        $errorDiv.innerHTML = '拡張子不正。';
        return false;
    }
    if (file.size > 1024 * 1024 ){
        $errorDiv.innerHTML = 'ファイルサイズ１M以内でアップロードしてください。';
        return false;
    }
    return true;
}

// badge button
$(document).on('click', '#button-badge', function () {
    window.location.href="/invited";
});