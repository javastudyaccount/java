﻿//エラーメッセージ
var SESSION_ERROR_STATUS = "ErrorPageStatus";
var SESSION_ERROR_MESSAGE = "ErrorPageMessage";
var ANIMATE_SPEED = 300;

// validate日本語対応
$.extend( $.validator.messages, {
    required: "入力してください。",
    remote: "このフィールドを修正してください。",
    email: "有効なEメールアドレスを入力してください。",
    url: "有効なURLを入力してください。",
    date: "有効な日付を入力してください。",
    dateISO: "有効な日付（ISO）を入力してください。",
    number: "有効な数字を入力してください。",
    digits: "数字のみを入力してください。",
    creditcard: "有効なクレジットカード番号を入力してください。",
    equalTo: "同じ値をもう一度入力してください。",
    extension: "有効な拡張子を含む値を入力してください。",
    maxlength: $.validator.format( "{0} 文字以内で入力してください。" ),
    minlength: $.validator.format( "{0} 文字以上で入力してください。" ),
    rangelength: $.validator.format( "{0} 文字から {1} 文字までの値を入力してください。" ),
    range: $.validator.format( "{0} から {1} までの値を入力してください。" ),
    step: $.validator.format( "{0} の倍数を入力してください。" ),
    max: $.validator.format( "{0} 以下の値を入力してください。" ),
    min: $.validator.format( "{0} 以上の値を入力してください。" )
});
$.validator.addMethod('titleLength', function(value, element,len) {
     if(value.length <= 20){
        return true;
     }
     else{
        return false;
     }
    },'キャンペーン名は20文字以内入力してください。');
$.validator.addMethod('customDate', function(value, element) {
     var PTN_YYYYMMDD = /^\d{4}\/\d{2}\/\d{2}$/;
     var DATE_DELIMITER = '/';
     var date = new Date(value);
     var optional = this.optional(element);
     if (optional) {
       return optional;
     }
     // invalidな日付または、フォーマット通りに入力されていない場合はNGとなる
     if (/Invalid|NaN/.test(date.toString()) || !PTN_YYYYMMDD.test(value)) {
       return false;
     }
     var m = '0' + (date.getMonth() + 1);
     var d = '0' + date.getDate();
     var newDateStr = date.getFullYear() + DATE_DELIMITER + m.slice(-2) + DATE_DELIMITER + d.slice(-2);
     return newDateStr === value;
   }, '有効な日付を入力してください。');
$.validator.addMethod('time', function(value, element) {
    var preg = /^([01][0-9]|2[0-4]):([0-5][0-9])$/;
    return this.optional(element) || preg.test(value);
}, '全角hh:mmフォーマットで入力してください。');
$.validator.addMethod('bansingle', function (value, element) {
    var preg = /^[^ -~｡-ﾟ]*$/;
    return this.optional(element) || preg.test(value);
}, '全角で入力してください。');
$.validator.addMethod('untisymbol', function (value, element) {
    var preg = /(?=.*[\\\'\"\<\>\&\/\:\*\?\|]).*$/;
    return this.optional(element) || !preg.test(value);
}, '\' " < > & ¥ / : * ? | 以外の文字を入力してください。');
$.validator.addMethod('required4multi', function (value, element) {
    return value || $(element).find('option').length > 0;
}, '設定を追加してください。');
$.validator.addMethod('required4ckeditor', function (value, element) {
    return value.length > 0;
}, $.validator.messages.required);

$.fn.serializeJSON = function () {
    return serialize4Object(this.serializeArray());
};
$.fn.serializeObject = function(){
    var vars = {};
    this.serializeArray().forEach(function(item){
        if(item.name in vars){
            if(! $.isArray(vars[item.name])){
                vars[item.name] = [vars[item.name]];
            }
            vars[item.name].push(item.value);
            return;
        }
        vars[item.name] = item.value;
    });
    return vars;
}
// HTMLエスケープを行う
function html_escape(str) {
    if (!str) return;
    return str.replace(/[<>&"'`]/g, function (match) {
        const escape = {
            '<': '&lt;',
            '>': '&gt;',
            '&': '&amp;',
            '"': '&quot;',
            "'": '&#39;',
            '`': '&#x60;'
        };
        return escape[match];
    });
}

// URLから絶対パスを求める
function get_abolute_path(url) {
    // アンカーにhrefとしてurlを指定すると絶対パスを取得できる。
    return $('<a>').attr('href', url).get(0).href;
}

// オブジェクトのソート
function object_array_sort(data, key, order, fn) {
    //デフォは降順(DESC)
    var num_a = -1;
    var num_b = 1;

    if (order === 'asc') { // 指定があれば昇順(ASC)
        num_a = 1;
        num_b = -1;
    }

    data = data.sort(function (a, b) {
        var x = a[key];
        var y = b[key];
        if (x > y) {
            return num_a;
        }
        if (x < y) {
            return num_b;
        }
        return 0;
    });

    fn(data); // ソート後の配列を返す
}

// 1remのpx数を求める * rem
function rem(rem) {
    return parseFloat($('html').css('font-size')) * rem;
}

var currentScrollY = {};
// スクロール位置の固定・解除
function stopScroll() {
    var target = 'body';
    // ダイアログ表示状態の場合は、ダイアログのスクロールを固定する
    if ($('.dialog:visible').length > 0) {
        target = '.dialog_wrap';
    }

    // スクロール固定
    if (target in currentScrollY) {
        if (target === 'body') {
            currentScrollY[target] = currentScrollY[target] > 0 ? currentScrollY[target] : $(window).scrollTop();
        } else {
            currentScrollY[target] = currentScrollY[target] > 0 ? currentScrollY[target] : $(target).scrollTop();
        }
    } else {
        if (target === 'body') {
            currentScrollY[target] = $(window).scrollTop();
        } else {
            currentScrollY[target] = $(target).scrollTop();
        }
    }

    //console.log(currentScrollY, 'スクロール固定' + target + '追加');
    if (target === 'body') {
        $(target).css({
            position: 'fixed',
            top: -1 * currentScrollY[target]
        });
       if ($(target).height() > $(window).height()) {
            $('html').css({
                overflowY: 'scroll'
            });
        }
    }
}
function restartScroll() {
    var target = 'body';
    // ダイアログの固定中スクロールがある場合はそれを解除
    if ('.dialog_wrap' in currentScrollY) {
        target = '.dialog_wrap';
    }

    // スクロール固定の解除
    $(target).css({
        position: 'relative',
        top: 'auto'
    });
    if (target === 'body') {
        $(window).scrollTop(currentScrollY[target]);
        $('html').css({
            overflowY: 'auto'
        });
    } else {
        $(target).scrollTop(currentScrollY[target]);
    }
    //console.log(currentScrollY, 'スクロール固定' + target + '削除');
    delete currentScrollY[target];
}

// アラート・確認ダイアログ
var alertOkCallback = null;
var alertCloseCallback = null;
function appendAlert() {
    if ($('#alert').length === 0) {
        $('body').append($('<div id="alert">'));
        $('#alert').append($('<div class="alert_main">'));
        $('#alert').append($('<div class="alert_buttons">'));
        $('#alert .alert_buttons').append($('<input type="button" class="ok" value="OK">'));
        $('#alert .alert_buttons').append($('<input type="button" class="cancel" value="CANCEL">'));
        $('body').append($('<div id="alert_backdrop"></div>'));
    }
}
// アラートダイアログ
$.alert = function (message, ok) {
    message = typeof message === 'string' && message.length > 0 ? message : '';
    alertOkCallback = ok;
    alertCloseCallback = ok;
    appendAlert();
    $('#alert .alert_main').html(message);
    $('#alert .alert_buttons input.ok').val('OK');
    $('#alert .alert_buttons input.ok').show();
    $('#alert .alert_buttons input.cancel').hide();

    // ダイアログ表示時に使用可能だった入力項目にdisabledを付与する
    $('#content input:not(:disabled)').addClass('disabled_for_alert');
    $('#content select:not(:disabled)').addClass('disabled_for_alert');
    $('#content button:not(:disabled)').addClass('disabled_for_alert');
    $('#content a:not(:disabled)').addClass('disabled_for_alert');
    $('.dialog input:not(:disabled)').addClass('disabled_for_alert');
    $('.dialog select:not(:disabled)').addClass('disabled_for_alert');
    $('.dialog button:not(:disabled)').addClass('disabled_for_alert');
    $('.dialog a:not(:disabled)').addClass('disabled_for_alert');
    $('.disabled_for_alert').prop('disabled', true);

    // スクロール固定
    stopScroll();

    // 表示
    $('#alert').show('fade', ANIMATE_SPEED);
    $('#alert_backdrop').show('fade', ANIMATE_SPEED);
    $('#alert .alert_buttons input.ok').eq(0).focus();
};

// 確認ダイアログ
$.confirm = function (message, ok, cancel) {
    message = typeof message === 'string' && message.length > 0 ? message : '';
    alertOkCallback = ok;
    alertCloseCallback = cancel;
    appendAlert();
    $('#alert .alert_main').html(message);
    $('#alert .alert_buttons input.ok').val('はい');
    $('#alert .alert_buttons input.ok').show();
    $('#alert .alert_buttons input.cancel').val('いいえ');
    $('#alert .alert_buttons input.cancel').show();

    // ダイアログ表示時に使用可能だった入力項目にdisabledを付与する
    $('#content input:not(:disabled)').addClass('disabled_for_alert');
    $('#content select:not(:disabled)').addClass('disabled_for_alert');
    $('#content button:not(:disabled)').addClass('disabled_for_alert');
    $('#content a:not(:disabled)').addClass('disabled_for_alert');
    $('.dialog input:not(:disabled)').addClass('disabled_for_alert');
    $('.dialog select:not(:disabled)').addClass('disabled_for_alert');
    $('.dialog button:not(:disabled)').addClass('disabled_for_alert');
    $('.dialog a:not(:disabled)').addClass('disabled_for_alert');
    $('.disabled_for_alert').prop('disabled', true);

    // スクロール固定
    stopScroll();

    // 表示
    $('#alert').show('fade', ANIMATE_SPEED);
    $('#alert_backdrop').show('fade', ANIMATE_SPEED);
    $('#alert .alert_buttons input.cancel').eq(0).focus();
};

// アラート・確認ダイアログ内ボタン
$(document).on('click', '#alert input.ok', function () {
    // ダイアログ表示時に付与したdisabledをはずす
    $('.disabled_for_alert').prop('disabled', false);
    $('.disabled_for_alert').removeClass('disabled_for_alert');

    // 非表示
    $('#alert').hide('fade', ANIMATE_SPEED);
    $('#alert_backdrop').hide('fade', ANIMATE_SPEED);
    if (typeof alertOkCallback === 'function') {
        alertOkCallback();
    }

    // スクロール固定解除
    restartScroll();
});
$(document).on('click', '#alert input.cancel, #alert_backdrop', function () {
    // ダイアログ表示時に付与したdisabledをはずす
    $('.disabled_for_alert').prop('disabled', false);
    $('.disabled_for_alert').removeClass('disabled_for_alert');

    // 非表示
    $('#alert').hide('fade', ANIMATE_SPEED);
    $('#alert_backdrop').hide('fade', ANIMATE_SPEED);
    if (typeof alertCloseCallback === 'function') {
        alertCloseCallback();
    }

    // スクロール固定解除
    restartScroll();
});

// ダイアログ
var dialogCloseCallback = null;
function appendDialog(selector) {
    if ($('#dialog_backdrop').length === 0) {
        $('body').append($('<div id="dialog_backdrop"></div>'));
    }
    if ($(selector).find('.dialog_wrap').length === 0) {
        $(selector).wrapInner('<div class="dialog_wrap"></div>');
        $(selector + ' .dialog_wrap').wrapInner('<div class="scroll"></div>');
        $(selector + ' .scroll').css({ position: 'relative' });
        var wrap_margin = 0;
        if ($(selector + ' .title').length > 0) {
            wrap_margin += 3.2;
            $(selector + ' > .dialog_wrap').css('margin-top', '3.2rem');
            $(selector + ' .title').insertBefore(selector + ' > .dialog_wrap');
            if ($(selector).find('a.dialog_close').length === 0) {
                $(selector + ' .title').after($('<a class="dialog_close icomoon-cross"></a>'));
            }
        } else {
            if ($(selector).find('a.dialog_close').length === 0) {
                $(selector + ' > .dialog_wrap').after($('<a class="dialog_close icomoon-cross"></a>'));
            }
        }
        if ($(selector + ' .buttons').length > 0) {
            wrap_margin += 3.2;
            $(selector + ' > .dialog_wrap').css('margin-bottom', '3.2rem');
            $(selector + ' .buttons').insertAfter(selector + ' > .dialog_wrap');
        }
        if (wrap_margin > 0) {
            $(selector + ' > .dialog_wrap').css('max-height', 'calc(80vh - ' + wrap_margin + 'rem');
        }
    }
}
// ダイアログ
$.dialog = function (selector, confirm_close, close, open) {
    dialogCloseCallback = close;
    appendDialog(selector);
    $(selector).addClass('dialog');

    // ダイアログ内にバリデーションを行うフォームがある場合
    if ($(selector).find('form.validate').length) {
        var validres = $(selector).find('form.validate').valid();
    }

    // confirm_closeフラグがtrueの場合、クラスを追加する
    if (confirm_close) {
        $(selector).addClass('confirm_close');
    } else {
        $(selector).removeClass('confirm_close');
    }

    // ダイアログ表示時に使用可能だった入力項目にdisabledを付与する
    $('#content input:not(:disabled)').addClass('disabled_for_dialog');
    $('#content select:not(:disabled)').addClass('disabled_for_dialog');
    $('#content button:not(:disabled)').addClass('disabled_for_dialog');
    $('#content a:not(:disabled)').addClass('disabled_for_dialog');
    $('.disabled_for_dialog').prop('disabled', true);

    // スクロール固定
    stopScroll();

    if (typeof open === 'function') {
        open();
    }

    // 表示
    $(selector).show('fade', ANIMATE_SPEED);
    $('#dialog_backdrop').show('fade', ANIMATE_SPEED);
    $(selector).eq(0).focus();
};

// ダイアログを閉じる
$.dialogClose = function (dialog) {
    setTimeout(function () {
        // ダイアログ表示時に付与したdisabledをはずす
        $('.disabled_for_dialog').prop('disabled', false);
        $('.disabled_for_dialog').removeClass('disabled_for_dialog');

        // 非表示
        dialog.hide('fade', ANIMATE_SPEED);
        $('#dialog_backdrop').hide('fade', ANIMATE_SPEED);
        if (typeof dialogCloseCallback === 'function') {
            dialogCloseCallback();
        }

        // スクロール固定解除
        restartScroll();
    }, 100);
};

$(document).on('click', '.dialog .dialog_close, #dialog_backdrop', function () {
    var close = false;
    var dialog = $('.dialog:visible');
    if (dialog.hasClass('confirm_close')) {
        $.confirm('ダイアログを閉じてよろしいですか？', function () {
            // ダイアログを閉じる
            $.dialogClose(dialog);
        });
    } else {
        // ダイアログを閉じる
        $.dialogClose(dialog);
    }
});

// 時間経過を計測する
getPast = (function () {
    var lastDate = NaN;
    return function () {
        var now = Date.now();
        var past = now - lastDate;
        lastDate = now;
        return past;
    };
})();
// 日付かどうかをチェックする
function valid_date(ymd, dt) {
    var split = [];
    if (ymd.match(/\-/)) {
        ymd.split('-');
    } else if (ymd.match(/\//)) {
        ymd.split('/');
    }
    if (split.length !== 3) {
        return false;
    }
    if (isNaN(parseInt(split[0]))) {
        return false;
    }
    if (isNaN(parseInt(split[1]))) {
        return false;
    }
    if (isNaN(parseInt(split[2]))) {
        return false;
    }
    var y = parseInt(split[0]);
    var m = parseInt(split[1]) - 1;
    var d = parseInt(split[2]);
    dt = new Date(y, m, d);
    return month = dt.getMonth();
}

//オブジェクトのマージ（後勝ち）
function merge() {
    var args = Array.prototype.slice.call(arguments);
    var len = args.length;
    var ret = {};
    var itm;

    for (var i = 0; i < len; i++) {
        var arg = args[i];
        for (itm in arg) {
            if (arg.hasOwnProperty(itm)) {
                ret[itm] = arg[itm];
            }
        }
    }

    return ret;
}
// 引数の中で最も小さいものを返す（不正値は除く）
function minValue() {
    var args = Array.prototype.slice.call(arguments);
    var len = args.length;

    var min = null;
    for (var i = 0; i < len; i++) {
        var arg = args[i];
        if (arg) {
            if (min) {
                min = min > arg ? arg : min;
            } else {
                min = arg;
            }
        }
    }
    return min;
}
// 引数の中で最も小さいものを返す（不正値は除く）
function maxValue() {
    var args = Array.prototype.slice.call(arguments);
    var len = args.length;

    var max = null;
    for (var i = 0; i < len; i++) {
        var arg = args[i];
        if (arg) {
            if (max) {
                max = max < arg ? arg : max;
            } else {
                max = arg;
            }
        }
    }
    return max;
}
/**
 * 2つの期間の重複判定
 * @param Date 期間1の開始
 * @param Date 期間1の終了
 * @param Date 期間2の開始
 * @param Date 期間2の終了
 * @return Boolean
 */
function isDatetimeOverlap(start1, end1, start2, end2){
  return start1 <= end2 && end1 >= start2;
}
// 文字列をスプリットし、指定indexを取得。要素がなければnull
function getSplit(str, separator, index) {
    if (!str) {
        return null;
    }
    var splitStr = str.split(separator);
    if (splitStr.length <= index) {
        return null;
    }
    return splitStr[index].trim();
}
 //日付変更
function getNewDate(date,time) {
    var str = date + ' ' + time + ':00';   
    var date1 = Date.parse(str); // parse()で
    var date2 = new Date(str); // コンストラクターで
    return date2;
}

//現在時刻を返す
function getCurrentTime() {
    var now = new Date();
    var res = "" + now.getFullYear() + "/" + padZero(now.getMonth() + 1) + 
        "/" + padZero(now.getDate()) + " " + padZero(now.getHours()) + ":" + 
        padZero(now.getMinutes()) + ":" + padZero(now.getSeconds());
    var date1 = Date.parse(res); // parse()で
    var date2 = new Date(res); // コンストラクターで
    return date2;
}
//未来か判断
function isArterNow(start,now){
    return start > now;
}

//先頭ゼロ付加
function padZero(num) {
    var result;
    if (num < 10) {
        result = "0" + num;
    } else {
        result = "" + num;
    }
    return result;
}
// カンマ付stringからstring配列を作成し、空文字要素を除去
function getStringArrayWithOutEmpty(strWithComma, separator) {
    if (!strWithComma) {
        return [];
    }
    var splitStr = strWithComma.split(separator);
    var tmp = [];
    $.each(splitStr, function (i, str) {
        if (str) {
            str = str.trim();
            if (str !== '') {
                tmp.push(str);
            }
        }
    });
    return tmp;
}


// ウィンドウを閉じる
function closeWindow() {
    //window.open('about:blank', '_self').close();
    alert('本当はこのタイミングでウィンドウを閉じる');
}

// 文字の繰り返し
String.prototype.repeat = function (num) {
    for (var str = ""; (this.length * num) > str.length; str += this);
    return str;
};


/**
 * カンマ編集（カンマ外し）
 * varBuf     : 対象文字列
 */
function splitComma(varBuf) {
    var buf = varBuf.split(",");
    var retBuf = buf.join("");
    return retBuf;
}

/**
 * カンマ編集（カンマ付与）
 * varBuf     : 対象文字列
 */
function grantComma(varBuf) {
    varBuf += '';

    //うっかり入っていたカンマを消す(=fig2num())
    var Separator = varBuf.indexOf(',', 0);
    while (Separator != -1) {
        varBuf = varBuf.substring(0, Separator) + varBuf.substring(Separator + 1, varBuf.length);
        Separator = varBuf.indexOf(',', 0);
    }

    //小数点を探し、小数点以下と整数部を分割して保持する
    var DecimalPoint = varBuf.lastIndexOf('.');
    if (DecimalPoint == -1) {
        var Decimals = '';
        var Integers = varBuf + '';
    } else {
        var Decimals = varBuf.substring(DecimalPoint, varBuf.length) + '';
        var Integers = varBuf.substring(0, DecimalPoint) + '';
    }
    //整数部の文字列長を3の倍数にする。足りない分は手前に' 'を埋め込む
    Blanks = Integers.length % 3;
    if (Blanks != 0) {
        for (var i = 0; 3 - Blanks > i ; i++) {
            Integers = ' ' + Integers;
        }
    }

    //整数文字列先頭から3文字おきにカンマを挿入する
    //先頭がマイナス符号の時は負数として処理する
    FigureInteger = Integers.substring(0, 3);
    var j = 2;
    if (Integers.charAt(2) == '-') {
        FigureInteger = FigureInteger + Integers.substring(3, 6);
        j = 4;
    }
    for (i = j; Integers.length > i ; i++) {
        if (i % 3 == 0) {
            FigureInteger = FigureInteger + ',' + Integers.substring(i, i + 3);
        }
    }

    //臨時に入れておいた' 'を削除する
    while (FigureInteger.charAt(0) == ' ') {
        FigureInteger = FigureInteger.substring(1, FigureInteger.length);
    }

    //整形済みの整数部と、待避してあった小数部を連結。
    commaNumber = FigureInteger + Decimals;
    return commaNumber;
}




