// ページ読み込み時に実行
$().ready( function(){
    // 日付入力項目にdatepickerをつける
    $('.has_datepicker').datepicker({
            language: 'ja',
            format: "yyyy/mm/dd",
            autoclose: true,
            startDate: new Date(),
            forceParse: false
        }).keydown(function(event) {
            return false;
        });
    // 時刻入力項目にtimepickerをつける
    $('.has_timepicker').timepicker({
        show2400: true,
        step: 15,
        timeFormat: 'H:i',
        useSelect: false
    });
    // アップロードモッド
    if (viewMod === VIEWMOD_UPDATE) {
        var name = curruntCampaignName;
        html = 'キャンペーン【' + name + '】を更新しました。';
        $.alert(html, function () {
        $.dialogClose($('#campaign_dialog'));
        });
    }
    // 登録モッド
    if (viewMod === VIEWMOD_CONFIRM) {
        var name = curruntCampaignName;
        html = 'キャンペーン【' + name + '】を登録しました。';
        $.alert(html, function () {
        $.dialogClose($('#campaign_dialog'));
         });
    }
       // 削除モッド
    if (viewMod === VIEWMOD_DELETE) {
        var name = curruntCampaignName;
        var html = 'キャンペーン【' + name + '】削除しました。';
        $.alert(html, function () {
        $.dialogClose($('#campaign_dialog'));
         });
    }
    // エラーモッド
    if (viewMod ==='registerError') {
       $('#reg_or_edi').val("register");
       create_campaign();
    }
    // エラーモッド
    if (viewMod ==='updateError') {
       $('#reg_or_edi').val("edit");
        edit_campaign($('#del_or_down_campaign_id').val());
    }

    $('.has_datepicker').on('show', function () {
        previousDate = $(this).val();
    });    
    $('.has_datepicker').on('changeDate', function(e) {
        clearErrorMessage();
        if ($(this).val() === '' || $(this).val() === null) {
        $(this).val(previousDate).datepicker('update');
    }
        $(this).valid();
        var entr_s = ($('#entr_s_date').val() + ' ' + $('#entr_s_time').val()).trim();
        var entr_e = ($('#entr_e_date').val() + ' ' + $('#entr_e_time').val()).trim();
        var impl_s = ($('#impl_s_date').val() + ' ' + $('#impl_s_time').val()).trim();
        var impl_e = ($('#impl_e_date').val() + ' ' + $('#impl_e_time').val()).trim();
        var disp_s = ($('#disp_s_date').val() + ' ' + $('#disp_s_time').val()).trim();
        var disp_e = ($('#disp_e_date').val() + ' ' + $('#disp_e_time').val()).trim();
        setDatepickerMinMax(entr_s, entr_e, impl_s, impl_e, disp_s, disp_e);
    });
    $('.has_timepicker').change(function (e) {
        clearErrorMessage();
        $(this).valid();
        var entr_s = ($('#entr_s_date').val() + ' ' + $('#entr_s_time').val()).trim();
        var entr_e = ($('#entr_e_date').val() + ' ' + $('#entr_e_time').val()).trim();
        var impl_s = ($('#impl_s_date').val() + ' ' + $('#impl_s_time').val()).trim();
        var impl_e = ($('#impl_e_date').val() + ' ' + $('#impl_e_time').val()).trim();
        var disp_s = ($('#disp_s_date').val() + ' ' + $('#disp_s_time').val()).trim();
        var disp_e = ($('#disp_e_date').val() + ' ' + $('#disp_e_time').val()).trim();
        setDatepickerMinMax(entr_s, entr_e, impl_s, impl_e, disp_s, disp_e);
    });
    
    $('#team_number_min').attr('min', 2);
    $('#team_number_max').attr('max',30);
    $('#team_number_min').attr('max',30);
    $('#team_number_max').attr('min',2);
    
    $('#team_number_max').change(function (e) {
        $(this).valid();
        if ($(this).val() && $(this).val() > 0) {
            if($(this).val() >=2 && $(this).val() <= 30){
                $('#team_number_min').attr('max', $(this).val());
            }
            else{
                $('#team_number_min').attr('min', 2);
                $('#team_number_min').attr('max', 30);
            }
        }else{
            $('#team_number_min').attr('min', 2);
            $('#team_number_min').attr('max', 30);
        }
    });
    $('#team_number_min').change(function (e) {
        $(this).valid();
        if ($(this).val() && $(this).val() > 0) {
            if($(this).val() == 1){
                $('#team_number_max').attr('min',2);
            }
            if($(this).val() > 30){
               $('#team_number_max').attr('min',30);
            }
            else{
                $('#team_number_max').attr('min',$(this).val());
            }
        }else{
            $('#team_number_max').attr('min', 2);
            $('#team_number_max').attr('max', 30);
        }
    });

    $('#name').change(function (e) {
       $(this).valid();
    });

    // validateチェック
    $('form.validate').each(function (index) {
        var rules = {};
        $(this).find('input, textarea, select').each(function () {
            $(this).wrap('<label class="relative_label">');
        });
        $(this).find('[number]').each(function () {
            var name = $(this).attr('name');
            if (!rules[name]) {
                rules[name] = {};
            }
            rules[name]['number'] = true;
        });
        $(this).find('.has_datepicker').each(function () {
            var name = $(this).attr('name');
            if (!rules[name]) {
                rules[name] = {};
            }
            rules[name]['date'] = true;
        });
        $(this).find('[bansingle]').each(function () {
            var name = $(this).attr('name');
            if (!rules[name]) {
                rules[name] = {};
            }
            rules[name]['bansingle'] = true;
        });
         $(this).find('[time]').each(function () {
            var name = $(this).attr('name');
            if (!rules[name]) {
                rules[name] = {};
            }
            rules[name]['time'] = true;
        });
        $(this).find('[customDate]').each(function () {
            var name = $(this).attr('name');
            if (!rules[name]) {
                rules[name] = {};
            }
            rules[name]['customDate'] = true;
        });
        $(this).find('[dateTimeRange]').each(function () {
            var name = $(this).attr('name');
            if (!rules[name]) {
                rules[name] = {};
            }
            rules[name]['dateTimeRange'] = true;
        });
        $(this).find('[titleLength]').each(function () {
            var name = $(this).attr('name');
            if (!rules[name]) {
                rules[name] = {};
            }
            rules[name]['titleLength'] = true;
        });
        $(this).validate({ rules: rules });
    });
    // 入力項目からフォーカスをはずしたときにチェック
    $('form.validate input, form.validate textarea, form.validate select').blur(function (event) {
        $(this).valid();
    });

    // 新規登録ボタン押下
    $('#create_campaign').click(function (e) {
        clearForm();
        resetReadOnly();
        $('#reg_or_edi').val("register");
        //実施未終了のキャンペーン数が2件より多いか判断
        var isImplSumOverTwo = checkImplCamp();
        if(isImplSumOverTwo){
            $('#regi_btn_error').css('visibility', 'visible');
            $('#create_campaign').prop('disabled', true);
        }else{
            create_campaign();
        }
    });
    // 詳細・実績ボタン
    $(document).on('click', '[id^="view_campaign_"]', function (e) {
        var id = $(e.target).attr('data-id');
        view_campaign(id);

    });
    // 編集ボタン
    $(document).on('click', '[id^="edit_campaign_"]', function (e) {
        resetReadOnly();
        var id = $(e.target).attr('data-id');
        $('#reg_or_edi').val("edit");
        edit_campaign(id);
    });
    // 削除ボタン
    $(document).on('click', '[id^="delete_campaign_"]', function (e) {
        var id = $(e.target).attr('data-id');
        var nameid = '#'+'campaign_name_' + id;
        var name = $(nameid).val();
        delete_campaign(id,name);
    });
    // キャンペーン登録ダイアログ　登録ボタン押下
    $('#regist_campaign').click(function () {
        regist_campaign();
    });
});
//被るチェック
function checkDateRange(){
    if($('#impl_s_time').val() && $('#impl_s_date').val() && $('#impl_e_time').val() && $('#impl_e_date').val()){
    impl_s = ($('#impl_s_date').val() + ' ' + $('#impl_s_time').val()).trim();
    impl_e = ($('#impl_e_date').val() + ' ' + $('#impl_e_time').val()).trim();
    var campaign = null;
    var duplSum = 0;
    var dateSelecteds = new Date(impl_s);
    var dateSelectede = new Date(impl_e);
    for (let i = 0; i < campaignList.length; i++) {        
        campaign = campaignList[i].campaign;
        if($('#del_or_down_campaign_id').val() !=campaign.campaignId){
            var impl_s_dt = getNewDate(campaign.implementationStartDate,campaign.implementationStartTime);
            var impl_e_dt = getNewDate(campaign.implementationEndDate,campaign.implementationEndTime);
            isDupl = isDatetimeOverlap(impl_s_dt,impl_e_dt,dateSelecteds,dateSelectede);
            if(isDupl && $('#del_or_down_campaign_id').val() !== campaign.campaignId){
                duplSum += 1;
            }
        }
     }
     if(duplSum >= 1){
         $('#regi_period_error').css('visibility', 'visible');
         $('#regi_period_error').text('すでに登録済みのキャンペーンと実施期間が重複しているため登録できません。');
         return false;
         }
         else{
            return true;
         }
    }
    else{
       return true;
    }
}
//エラーメッセージをクリア
function clearErrorMessage(){
    if ($('#regi_period_error').css('visibility', 'visible')) {
         $('#regi_period_error').css('visibility', 'hidden');
         $('#regi_period_error').text('');
    }
}
//フォームリセット
function clearForm(){
    $('#campaign_form')
    .find("textarea,:text,input[type=number]")
    .not("input[type=hidden],input[type=radio]")
    .val("")
    .end();
    $('#campaign_form').find(":checked")
    .prop("checked", false);
    
    $("select option:selected").each(function(){
      $(this).removeAttr("selected");
    });
}
//エラーメッセージ表示をクリア
function resetReadOnly() {
	clearErrorMessage();
    $("label.error").hide();
    $("#campaignPeriodError-error").contents().filter('*').remove();
    $("#teamMemberError-error").contents().filter('*').remove();        
    $(".error").removeClass("error");
    //input属性のものを一括で取得する
    var inputItem = document.getElementsByTagName("input");
    //ループしながら全てに処理を行う
    for (var i=0; i<inputItem.length; i++){
      //項目がreadonlyの場合のみ処理
      if (inputItem[i].readOnly){
        inputItem[i].readOnly = false;
      }
    }
}
// 日付入力の上限下限を設定
function setDatepickerMinMax(entr_s, entr_e, impl_s, impl_e, disp_s, disp_e) {
    // 　　 ｴﾝﾄﾘｰ　 実施
    // 　├───┼───┤
    // ├─────────┤
    // 　　　　 表示
    // 時系列
    // ↓表示開始
    // ↓エントリー開始
    // ↓エントリー終了
    // ↓実施開始
    // ↓実施終了
    // ↓表示終了

    //本日
    var currentDateSt = getCurrentDate() + ' ' + "00:00";

    // 各期間上限設定  
    if (!$('#disp_s_date').prop('disabled')) {
    	setMax($('#disp_s_date'), $('#disp_s_time'), minValue(entr_s, entr_e, impl_s, impl_e, disp_e));
    }
    if (!$('#entr_s_date').prop('disabled')) {
    	setMax($('#entr_s_date'), $('#entr_s_time'), minValue(impl_s, impl_e, disp_e));
    }
    if (!$('#entr_e_date').prop('disabled')) {
    	setMax($('#entr_e_date'), $('#entr_e_time'), minValue(impl_s, impl_e, disp_e));
    }
    if (!$('#impl_s_date').prop('disabled')) {
    	setMax($('#impl_s_date'), $('#impl_s_time'), minValue(disp_e));
    }
    if (!$('#impl_e_date').prop('disabled')) {
    	setMax($('#impl_e_date'), $('#impl_e_time'), minValue(disp_e));
    }


    // 上限設定後に各期間の値を取得
    entr_s = ($('#entr_s_date').val() + ' ' + $('#entr_s_time').val()).trim();
    entr_e = ($('#entr_e_date').val() + ' ' + $('#entr_e_time').val()).trim();
    impl_s = ($('#impl_s_date').val() + ' ' + $('#impl_s_time').val()).trim();
    impl_e = ($('#impl_e_date').val() + ' ' + $('#impl_e_time').val()).trim();
    disp_s = ($('#disp_s_date').val() + ' ' + $('#disp_s_time').val()).trim();
    disp_e = ($('#disp_e_date').val() + ' ' + $('#disp_e_time').val()).trim();

    // 各期間下限設定
    if (!$('#entr_s_date').prop('disabled')) {
        setMin($('#entr_s_date'), $('#entr_s_time'), maxValue(disp_s, currentDateSt));
    }
    if (!$('#entr_e_date').prop('disabled')) {
        setMin($('#entr_e_date'), $('#entr_e_time'), maxValue(disp_s, entr_s, currentDateSt));
    }
    if (!$('#impl_s_date').prop('disabled')) {
        setMin($('#impl_s_date'), $('#impl_s_time'), maxValue(disp_s, entr_s, entr_e, currentDateSt));
    }
    if (!$('#impl_e_date').prop('disabled')) {
        setMin($('#impl_e_date'), $('#impl_e_time'), maxValue(disp_s, entr_s, entr_e, impl_s, currentDateSt));
    }
    if (!$('#disp_s_date').prop('disabled')) {
        setMin($('#disp_s_date'), $('#disp_s_time'), maxValue(currentDateSt));
    }
    if (!$('#disp_e_date').prop('disabled')) {
        setMin($('#disp_e_date'), $('#disp_e_time'), maxValue(disp_s, entr_s, entr_e, impl_s, impl_e, currentDateSt));
    }

}
// 日時入力の上限を設定
function setMax(date, time, max) {
    // 日付の上限を設定
    date.datepicker('setEndDate', getSplit(max, ' ', 0));
    // 設定時にすでに下限より大きい場合は上限の値に変更
    if (date.val() && date.val() > getSplit(max, ' ', 0)) {
        date.val(getSplit(max, ' ', 0));
    }
}
// 日時入力の下限を設定
function setMin(date, time, min) {
    // 日付の下限を設定
    date.datepicker('setStartDate', getSplit(min, ' ', 0));
    // 設定時にすでに下限より小さい場合は下限の値に変更
    if (date.val() && date.val() < getSplit(min, ' ', 0)) {
        date.val(getSplit(min, ' ', 0));
    }
}
// 新規登録ボタン押下時動作
function create_campaign() {
    new_campaign();
}
// 詳細・実績ボタン押下時動作
function view_campaign(id) {
    var actionPath = "";
    var MYHL05001_DOWNLOAD = getUri(URI_BASE_ENTRYWALKING, URI_MYHL05001, URI_DOWNLOAD);
    actionPath = MYHL05001_DOWNLOAD;
    $('form').attr('action', actionPath);
    $('form').serialize();
    $('#del_or_down_campaign_id').val(id);
    $('form').submit();
}
// キャンペーン新規動作
function new_campaign() {
    if(viewMod ==='registerError'){
        $('#campaign_dialog_title').text('キャンペーン登録');
        $('#regist_campaign').val('  登録  ');
        $.dialog('#campaign_dialog', true);
    }
    else{    
        $('#del_or_down_campaign_id').val('');
        $('#old_name').val('');
        $('#name').val('');
        $('#entr_s_date').val('');
        $('#entr_s_time').val('');
        $('#entr_e_date').val('');
        $('#entr_e_time').val('');
        $('#impl_s_date').val('');
        $('#impl_s_time').val('');
        $('#impl_e_date').val('');
        $('#impl_e_time').val('');
        $('#disp_s_date').val('');
        $('#disp_s_time').val('');
        $('#disp_e_date').val('');
        $('#disp_e_time').val('');
        $('#team_number_min').val('');
        $('#team_number_max').val('');
        $('#campaign_dialog_title').text('キャンペーン登録');
        $('#regist_campaign').val('  登録  ');
        $.dialog('#campaign_dialog', true);
    
        // disableを解除する
        $('#entr_s_date').prop('disabled', false);
        $('#entr_s_time').prop('disabled', false);
        $('#entr_e_date').prop('disabled', false);
        $('#entr_e_time').prop('disabled', false);
        $('#impl_s_date').prop('disabled', false);
        $('#impl_s_time').prop('disabled', false);
        $('#impl_e_date').prop('disabled', false);
        $('#impl_e_time').prop('disabled', false);
        $('#disp_s_date').prop('disabled', false);
        $('#disp_s_time').prop('disabled', false);
        $('#disp_e_date').prop('disabled', false);
        $('#disp_e_time').prop('disabled', false);
        $('#team_number_min').prop('disabled', false);
        $('#team_number_max').prop('disabled', false);
        $('#regist_campaign').prop('disabled', false);
        $('#name').prop('disabled', false);
    
        setDatepickerMinMax('', '', '', '', '', '', '', '', '', '', '', '');
        // チーム人数上限下限の設定
        $('#team_number_max').prop('max', $('#setting_team_member_max').val());
        $('#team_number_min').prop('min', $('#setting_team_member_min').val());
    }    
}
// 編集ボタン押下時動作
function edit_campaign(id) {
    if(viewMod ==='updateError'){
        var entr_s_date = $('#entr_s_date').val();
        var entr_s_time = $('#entr_s_time').val();
        var entr_e_date = $('#entr_e_date').val();
        var entr_e_time = $('#entr_e_time').val();
        var impl_s_date = $('#impl_s_date').val();
        var impl_s_time = $('#impl_s_time').val();
        var impl_e_date = $('#impl_e_date').val();
        var impl_e_time = $('#impl_e_time').val();
        var disp_s_date = $('#disp_s_date').val();
        var disp_s_time = $('#disp_s_time').val();
        var disp_e_date = $('#disp_e_date').val();
        var disp_e_time = $('#disp_e_time').val();

        $('#campaign_dialog_title').text('キャンペーン更新');
        $('#regist_campaign').val('  更新  ');
    
        // 編集不可項目をdisableにする
        // 現在日時より過去の項目は編集不可
        var now = new Date();
        var dateES = getNewDate(entr_s_date,entr_s_time);
        $('#entr_s_date').prop('disabled', dateES <= now);
        $('#entr_s_time').prop('disabled', dateES <= now);
    
        var dateEE = getNewDate(entr_e_date,entr_e_time);
        $('#entr_e_date').prop('disabled', dateEE <= now);
        $('#entr_e_time').prop('disabled', dateEE <= now);
    
        var dateIS = getNewDate(impl_s_date,impl_s_time);
        $('#impl_s_date').prop('disabled', dateIS <= now);
        $('#impl_s_time').prop('disabled', dateIS <= now);
        // 実施開始後はキャンペーン名編集不可
        $('#name').prop('disabled', dateIS <= now);
    
        var dateIE = getNewDate(impl_e_date,impl_e_time);
        $('#impl_e_date').prop('disabled', dateIE <= now);
        $('#impl_e_time').prop('disabled', dateIE <= now);
    
        var dateDS = getNewDate(disp_s_date,disp_s_time);
        $('#disp_s_date').prop('disabled', dateDS <= now);
        $('#disp_s_time').prop('disabled', dateDS <= now);
    
        var dateDE = getNewDate(disp_e_date,disp_e_time);
        $('#disp_e_date').prop('disabled', dateDE <= now);
        $('#disp_e_time').prop('disabled', dateDE <= now);
    
        // エントリー開始後はチーム人数編集不可
        $('#team_number_min').prop('disabled', dateES <= now);
        $('#team_number_max').prop('disabled', dateES <= now);
    
        // 実施終了後は更新ボタン編集不可
        $('#regist_campaign').prop('disabled', dateIE <= now);
    
        $.dialog('#campaign_dialog', true);

        }
     else{
        var campaign = null;
        for (let i = 0; i < campaignList.length; i++) {
            var campid = campaignList[i].campaign.campaignId;
            if (campid == id) {
                campaign = campaignList[i].campaign;
            }
         }
    
        // 各日時を日付と時刻に分割
        var entr_s_date = campaign.entryStartDate;
        var entr_s_time = campaign.entryStartTime;
        var entr_e_date = campaign.entryEndDate;
        var entr_e_time = campaign.entryEndTime;
        var impl_s_date = campaign.implementationStartDate;
        var impl_s_time = campaign.implementationStartTime;
        var impl_e_date = campaign.implementationEndDate;
        var impl_e_time = campaign.implementationEndTime;
        var disp_s_date = campaign.indicatesStartDate;
        var disp_s_time = campaign.indicatesStartTime;
        var disp_e_date = campaign.indicatesEndDate;
        var disp_e_time = campaign.indicatesEndTime;
    
        setDatepickerMinMax(entr_s_date + ' ' + entr_s_time, 
                            entr_e_date + ' ' + entr_e_time, 
                            impl_s_date + ' ' + impl_s_time,
                            impl_e_date + ' ' + impl_e_time,
                            disp_s_date + ' ' + disp_s_time,
                            disp_e_date + ' ' + disp_e_time);
    
        // 時刻23:59を24:00として表示する
        entr_s_time = entr_s_time == '23:59' ? '24:00' : entr_s_time;
        entr_e_time = entr_e_time == '23:59' ? '24:00' : entr_e_time;
        impl_s_time = impl_s_time == '23:59' ? '24:00' : impl_s_time;
        impl_e_time = impl_e_time == '23:59' ? '24:00' : impl_e_time;
        disp_s_time = disp_s_time == '23:59' ? '24:00' : disp_s_time;
        disp_e_time = disp_e_time == '23:59' ? '24:00' : disp_e_time;
    
        $('#del_or_down_campaign_id').val(id);
        $('#old_name').val(campaign.campaignName);
        $('#name').val(campaign.campaignName);
        $('#entr_s_date').val(entr_s_date);
        $('#entr_s_time').val(entr_s_time);
        $('#entr_e_date').val(entr_e_date);
        $('#entr_e_time').val(entr_e_time);
        $('#impl_s_date').val(impl_s_date);
        $('#impl_s_time').val(impl_s_time);
        $('#impl_e_date').val(impl_e_date);
        $('#impl_e_time').val(impl_e_time);
        $('#disp_s_date').val(disp_s_date);
        $('#disp_s_time').val(disp_s_time);
        $('#disp_e_date').val(disp_e_date);
        $('#disp_e_time').val(disp_e_time);
        $('#team_number_min').val(campaign.memberMin);
        $('#team_number_max').val(campaign.memberMax);
        $('#campaign_dialog_title').text('キャンペーン更新');
        $('#regist_campaign').val('  更新  ');
    
        // 編集不可項目をdisableにする
        // 現在日時より過去の項目は編集不可
        var now = new Date();
        var dateES = getNewDate(entr_s_date,entr_s_time);
        $('#entr_s_date').prop('disabled', dateES <= now);
        $('#entr_s_time').prop('disabled', dateES <= now);
    
        var dateEE = getNewDate(entr_e_date,entr_e_time);
        $('#entr_e_date').prop('disabled', dateEE <= now);
        $('#entr_e_time').prop('disabled', dateEE <= now);
    
        var dateIS = getNewDate(impl_s_date,impl_s_time);
        $('#impl_s_date').prop('disabled', dateIS <= now);
        $('#impl_s_time').prop('disabled', dateIS <= now);
        // 実施開始後はキャンペーン名、更新ボタン編集不可
        $('#name').prop('disabled', dateIS <= now);
    
        var dateIE = getNewDate(impl_e_date,impl_e_time);
        $('#impl_e_date').prop('disabled', dateIE <= now);
        $('#impl_e_time').prop('disabled', dateIE <= now);
    
        var dateDS = getNewDate(disp_s_date,disp_s_time);
        $('#disp_s_date').prop('disabled', dateDS <= now);
        $('#disp_s_time').prop('disabled', dateDS <= now);
    
        var dateDE = getNewDate(disp_e_date,disp_e_time);
        $('#disp_e_date').prop('disabled', dateDE <= now);
        $('#disp_e_time').prop('disabled', dateDE <= now);
    
        // エントリー開始後はチーム人数編集不可
        $('#team_number_min').prop('disabled', dateES <= now);
        $('#team_number_max').prop('disabled', dateES <= now);
    
        // 実施終了後は更新ボタン編集不可
        $('#regist_campaign').prop('disabled', dateIE <= now);
    
        $.dialog('#campaign_dialog', true);
     }
 }

// 削除ボタン押下時動作
function delete_campaign(id,name) {
    var name = $('#campaign_name_' + id).val();
    var html = 'キャンペーン【' + name + '】を削除しますか？';
    $.confirm(html, function () {
        //$.call_api('../CampaignManage/DeleteCampaign', params, delete_campaign_success, get_campaign_list);
        var actionPath = "";
        var MYHL05001_DELETE = getUri(URI_BASE_ENTRYWALKING, URI_MYHL05001, URI_DELETE);
        actionPath = MYHL05001_DELETE;
        $('form').attr('action', actionPath);
        $('form').serialize();
        $('#del_or_down_campaign_id').val(id);
        $('#name').val(name);
        $('form').submit();
    });
}

// 登録ボタン押下時動作
function regist_campaign() {
    if (!$('form.validate').valid()) {
        return;
    }
    //相関チェック
    if(!checkTimeRange()){
        return;
    }
    if(!checkDateRange()){
        return;
    }
    var html;
    if ($('#reg_or_edi').val().trim() === 'edit'.trim()) {
        html = '現在の内容でキャンペーンを更新しますか？';
        //URI_UPDATE
       $.confirm(html, function () {
       // disableを解除する
        $('#entr_s_date').prop('disabled', false);
        $('#entr_s_time').prop('disabled', false);
        $('#entr_e_date').prop('disabled', false);
        $('#entr_e_time').prop('disabled', false);
        $('#impl_s_date').prop('disabled', false);
        $('#impl_s_time').prop('disabled', false);
        $('#impl_e_date').prop('disabled', false);
        $('#impl_e_time').prop('disabled', false);
        $('#disp_s_date').prop('disabled', false);
        $('#disp_s_time').prop('disabled', false);
        $('#disp_e_date').prop('disabled', false);
        $('#disp_e_time').prop('disabled', false);
        $('#team_number_min').prop('disabled', false);
        $('#team_number_max').prop('disabled', false);
        $('#regist_campaign').prop('disabled', false);
        $('#name').prop('disabled', false);
        resetTwentyFourHour();
        var actionPath = "";
        var MYHL05001_UPLOAD = getUri(URI_BASE_ENTRYWALKING, URI_MYHL05001, URI_UPDATE);
        actionPath = MYHL05001_UPLOAD;
        $('form').attr('action', actionPath);
        $('form').serialize();
        $('form').submit();
        });
    } if($('#reg_or_edi').val().trim() === 'register'.trim()) {
        html = '現在の内容でキャンペーンを新規登録しますか？';
        //URI_ADD
           $.confirm(html, function () {
            resetTwentyFourHour();
            var actionPath = "";
            var MYHL05001_UPLOAD = getUri(URI_BASE_ENTRYWALKING, URI_MYHL05001, URI_ADD);
            actionPath = MYHL05001_UPLOAD;
            $('form').attr('action', actionPath);
            $('form').serialize();
            $('form').submit();
            });
            }
}
//時刻24:00を23:59とする
function resetTwentyFourHour() {
    var params = $('#campaign_form').serializeObject();
    // 時刻24:00を23:59とする
    entryStartTime = params.entryStartTime == '24:00' ? '23:59' : params.entryStartTime;
    $('#entr_s_time').val(entryStartTime);
    entryEndTime = params.entryEndTime == '24:00' ? '23:59' : params.entryEndTime;
    $('#entr_e_time').val(entryEndTime);
    implementationStartTime = params.implementationStartTime == '24:00' ? '23:59' : params.implementationStartTime;
    $('#impl_s_time').val(implementationStartTime);
    implementationEndTime = params.implementationEndTime == '24:00' ? '23:59' : params.implementationEndTime;
    $('#impl_e_time').val(implementationEndTime);
    indicatesStartTime = params.indicatesStartTime == '24:00' ? '23:59' : params.indicatesStartTime;
    $('#disp_s_time').val(indicatesStartTime);
    indicatesEndTime = params.indicatesEndTime == '24:00' ? '23:59' : params.indicatesEndTime;
    $('#disp_e_time').val(indicatesEndTime);
    
}
//実施未終了のキャンペーン数2件より多いかチェック
function checkImplCamp(){
        var campaign = null;
        var implSum = 0;
        var isAfter;
        var now = getCurrentTime();
        for (let i = 0; i < campaignList.length; i++) {        
            campaign = campaignList[i].campaign;
                var impl_s_dt = getNewDate(campaign.implementationStartDate,campaign.implementationStartTime);
                isAfter = isArterNow(impl_s_dt,now);
                if(isAfter){
                    implSum += 1;
                }
         }
         if(implSum >= 2){
            return true;
            }
         else{
            return false;
         }
}
//相関チェック
function checkTimeRange(){

    // 各期間を取得
    var entr_s_dt = getNewDate($('#entr_s_date').val(),$('#entr_s_time').val());
    var entr_e_dt = getNewDate($('#entr_e_date').val(),$('#entr_e_time').val());
    var impl_s_dt = getNewDate($('#impl_s_date').val(),$('#impl_s_time').val());
    var impl_e_dt = getNewDate($('#impl_e_date').val(),$('#impl_e_time').val());
    var disp_s_dt = getNewDate($('#disp_s_date').val(),$('#disp_s_time').val());
    var disp_e_dt = getNewDate($('#disp_e_date').val(),$('#disp_e_time').val());
        
    //現在時間
    var currentDate = getCurrentDate();
    var now = getCurrentTime();
    var flg = true;
    
    //　エントリー開始時間
    if($('#entr_s_time').prop('disabled') == false){
        if($('#entr_s_date').val() == currentDate){
            isAfter = isArterNow(entr_s_dt,now);
            if(!isAfter){
                $('#regi_period_error').css('visibility', 'visible');
                $('#regi_period_error').text('エントリー開始日時は現在の日時以降で指定してください。');
                return false;
            }else{
                flg = true;
            }
        }
    }
    //　エントリー終了時間
    if($('#entr_e_time').prop('disabled') == false){
        if($('#entr_e_date').val() == currentDate){
            isAfter = isArterNow(entr_e_dt,now);
            if(!isAfter){
                $('#regi_period_error').css('visibility', 'visible');
                $('#regi_period_error').text('エントリー終了日時は現在の日時以降で指定してください。');
                return false;
            }else{
                flg = true;
            }
        }
    }
    //　実施開始時間
    if($('#impl_s_time').prop('disabled') == false){
        if($('#impl_s_date').val() == currentDate){
            isAfter = isArterNow(impl_s_dt,now);
            if(!isAfter){
                $('#regi_period_error').css('visibility', 'visible');
                $('#regi_period_error').text('実施開始日時は現在の日時以降で指定してください。');
                return false;
            }else{
                flg = true;
            }
        }
    }
    //　実施終了時間
    if($('#impl_e_time').prop('disabled') == false){
        if($('#impl_e_date').val() == currentDate){
            isAfter = isArterNow(impl_e_dt,now);
            if(!isAfter){
                $('#regi_period_error').css('visibility', 'visible');
                $('#regi_period_error').text('実施終了日時は現在の日時以降で指定してください。');
                return false;
            }else{
                flg = true;
            }
        }
    }
    //　表示開始時間
    if($('#disp_s_time').prop('disabled') == false){
        if($('#disp_s_date').val() == currentDate){
            isAfter = isArterNow(disp_s_dt,now);
            if(!isAfter){
                $('#regi_period_error').css('visibility', 'visible');
                $('#regi_period_error').text('表示開始日時は現在の日時以降で指定してください。');
                return false;
            }else{
                flg = true;
            }
        }
    }
    //　表示終了時間
    if($('#disp_e_time').prop('disabled') == false){
        if($('#disp_e_date').val() == currentDate){
            isAfter = isArterNow(disp_e_dt,now);
            if(!isAfter){
                $('#regi_period_error').css('visibility', 'visible');
                $('#regi_period_error').text('表示終了日時は現在の日時以降で指定してください。');
                return false;
            }else{
                flg = true;
            }
        }
    }
    //　エントリー終了日（時刻）とエントリー開始日（時刻）
    if(!isAfterCheck(entr_e_dt,entr_s_dt)){
        $('#regi_period_error').css('visibility', 'visible');
        $('#regi_period_error').text('エントリー終了日時は、エントリー開始日時以降となるように指定してください。');
        return false;
    }
    //　実施終了日（時刻）と実施開始日（時刻）
    if(!isAfterCheck(impl_e_dt,impl_s_dt)){
        $('#regi_period_error').css('visibility', 'visible');
        $('#regi_period_error').text('実施終了日時は、実施開始日時以降となるように指定してください。');
        return false;
    }
    //　表示終了日（時刻）と表示開始日（時刻）
    if(!isAfterCheck(disp_e_dt,disp_s_dt)){
        $('#regi_period_error').css('visibility', 'visible');
        $('#regi_period_error').text('表示終了日時は、表示開始日時以降となるように指定してください。');
        return false;
    }
    //　実施開始日（時刻）とエントリー終了日（時刻）
    if(!isAfterCheck(impl_s_dt,entr_e_dt)){
        $('#regi_period_error').css('visibility', 'visible');
        $('#regi_period_error').text('実施開始日時は、エントリー終了日時以降となるように指定してください。');
        return false;
    }
    //　エントリー開始日（時刻）と表示開始日（時刻）
    if(!isAfterCheck(entr_s_dt,disp_s_dt)){
        $('#regi_period_error').css('visibility', 'visible');
        $('#regi_period_error').text('エントリー開始日時は、表示開始日時以降となるように指定してください。');
        return false;
    }
    //　表示終了日（時刻）と実施終了日（時刻）
    if(!isAfterCheck(disp_e_dt,impl_e_dt)){
        $('#regi_period_error').css('visibility', 'visible');
        $('#regi_period_error').text('表示終了日時は、実施終了日時以降となるように指定してください。');
        return false;
    }
    
    return flg;
}
//本日の日付取得
function getCurrentDate(){
    var current = new Date();
    var currentYYYY = current.getFullYear();
    var currentMM = ('0' + (current.getMonth() + 1)).slice(-2);
    var currentDD = ('0' + current.getDate()).slice(-2);
    var currentDate = currentYYYY.toString() + "/" + currentMM.toString() + "/" + currentDD.toString()

    return currentDate;
}
function isAfterCheck(DateEnd,DateStart){
    return DateEnd >= DateStart;
}