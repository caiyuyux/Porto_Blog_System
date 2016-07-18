/**
 * Created by Tornado on 2016/7/9.
 */
var flag = 0;//控制表单是否可以提交
$('#frmSignIn').submit(function (e) {
    var account = $('#account').val();
    //选中“记住我”
    if($('#rememberme').is(':checked')  && flag==0){
//			alert("创建或更新cookie");
        var password = $('#password').val();
        var csrf_token = "{{csrf-token}}";
        $.ajax({
            type: "GET",
            url:"/cookies-pass",
            dataType:"json",
            data: "account="+account+"&password="+password+"&__anti-forgery-token="+csrf_token,
            success:function(msg){
//					alert(msg);
//					alert(msg.token);
                if(msg.token==null){
//						alert("移除");
                    $.removeCookie(account);
                }else {
                    if($.cookie(account)==null){
//							alert("创建");
                        $.cookie(account, msg.token, {expires: 7});
                    } else{
//							alert("更新");
                        $.cookie(account, msg.token);
                    }
                    $('#password').val(msg.token);
                }

                flag = 1;
                $('#frmSignIn').submit();
            }
        });
        e.preventDefault();
    }
    //取消选中“记住我”
    if(!$('#rememberme').is(':checked') && $.cookie(account)!=null){
//			alert("取消选中，cookie删除");
        $.removeCookie(account);
    }
});

//密码改变，“记住我”取消选中
$('#password').bind('input propertychange', function() {
    var account = $('#account').val();
    $('#rememberme').attr('checked',false);
    $.removeCookie(account);
});

//用户名存在cookie，填充密码
$('#password').focus(function () {
    var account = $('#account').val();
    if(account.length>0 && $.cookie(account)!=null){
        alert("存在cookie，值为"+$.cookie(account));
        $('#rememberme').attr('checked',true);
        //密码填充
        var tmp = '8'
        for(var i=0; i<50; i++){
            tmp += '8';
        }
        $('#password').val(tmp);
    }
})