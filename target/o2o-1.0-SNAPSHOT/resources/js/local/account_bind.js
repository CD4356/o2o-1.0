
$(function () {

    $('#submit').click(function() {
        // 获取输入的帐号
        var username = $('#username').val();
        // 获取输入的密码
        var password = $('#password').val();
        // 获取表单输入的验证码
        var verifyCode = $('#j_captcha').val();
        // 判断输入的验证码是否为空
        if(!verifyCode){
            $.toast('请输入验证码!');
            return;
        }

        // 访问后台，绑定帐号
        $.ajax({
            url : '/o2o/local/account_bind',
            type : "post", //指定Ajax请求方式
            dataType : 'json', //指定服务器返回的数据类型
            data : {
                username : username,
                password : password,
                verifyCode : verifyCode
            },
            success: function(data) {
                if (data.success) {
                    $.toast('绑定成功！');
                    window.location.href = '/o2o/shop_admin/shop_list';
                } else {
                    $.toast('提交失败！' + data.errMsg);
                    $('#captcha_img').click();
                }
            }
        });
    });

});