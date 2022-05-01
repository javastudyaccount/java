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


    var diceFunc = function(){
        $("audio")[0].play();
        jQuery.each([$("#dice1"), $("#dice2")], function(index, dice){
            var left = parseInt($(dice).css("left").replace("px", ""));
            var top = parseInt($(dice).css("top").replace("px", ""));
            $(".wrap1").append("<div id='dice_mask'></div>");//add mask
            dice.attr("class","dice");//After clearing the last points animation
            dice.css('cursor','default');
            var num = Math.floor(Math.random()*6+1);//random num 1-6
            
            dice.animate({left: (left+2)+'px'}, 100,function(){
                dice.addClass("dice_t");
            }).delay(200).animate({top:(top-2)+'px'},100,function(){
                dice.removeClass("dice_t").addClass("dice_s");
            }).delay(200).animate({opacity: 'show'},600,function(){
                dice.removeClass("dice_s").addClass("dice_e");
            }).delay(100).animate({left:(left)+'px',top:(top)+'px'},100,function(){
                dice.removeClass("dice_e").addClass("dice_"+num);
                dice.css('cursor','pointer');
                $("#dice_mask").remove();//remove mask
                $("audio")[0].pause();
                $("audio")[0].currentTime = 0;
            });
        });
    };