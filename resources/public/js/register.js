/**
 * Created by Tornado on 2016/7/9.
 */
var code;
function createCode(type){
    code = "";
    var codeLength = 6;//验证码长度
    //获取随机数的数组
    var selectChar = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
        'A', 'B', 'C', 'D', 'E', 'F', 'G',
        'H', 'I', 'J', 'K', 'L', 'M', 'N',
        'O', 'P', 'Q', 'R', 'S', 'T',
        'U', 'V', 'W', 'X', 'Y', 'Z');

    for(var i=0; i<codeLength; i++){
        var charIndex = Math.floor(Math.random()*36);
        code +=selectChar[charIndex];
    }
    var date = new Date();
    date.setTime(date.getTime() + (10 * 60 * 1000));//10分钟后过期
    if(type=="register"){
        $.cookie("code", code, { expires: date});
        register_mail();
    }
    else if(type=="recover"){
        $.cookie("code2", code, { expires: date});
        recover_mail();
    }
    
}
function register_mail(){
    var toMail = $('#inputEmail').val();
    $.ajax({
        type: "GET",
        url:"/register-email",
        data: "code="+code+"&to="+toMail,
        success:function(msg){}
    });
}
function recover_mail(){
    var toMail = $('#inputEmail2').val();
    $.ajax({
        type: "GET",
        url:"/recover-email",
        data: "code="+code+"&to="+toMail,
        success:function(msg){}
    });
}
$('#emailCheck').countdown({
    time: 30,
    text: "重发",
    obj: $('#inputEmail'),
    before: function(){
        var email = $("#inputEmail").val();
        var reg = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
        if (reg.test(email)) {
            createCode("register");
            $.cookie('keepEmail',$('#inputEmail').val());
            return true;
        }else{
            alert("请输入正确的邮箱地址");
            return false;
        }
        return true;
    },
    after: function (star) {
        star();
        return true;
    }
});

$('#emailCheck2').countdown({
    time: 30,
    text: "重发",
    obj: $('#inputEmail2'),
    before: function(){
        var email = $("#inputEmail2").val();
        var reg = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
        if (reg.test(email)) {
            createCode("recover");
            $.cookie('keepEmail2',$('#inputEmail2').val());
            return true;
        }else{
            alert("请输入正确的邮箱地址");
            return false;
        }
        return true;
    },
    after: function (star) {
        star();
        return true;
    }
});