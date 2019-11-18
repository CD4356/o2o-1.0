
$(function () {

    var loginUrl = '/o2o/local/login';

    $('#submit').click(function () {
        // 定义表单对象，用于接收前端数据以提交给后端
        var formData = new FormData();

        // 获取表单里的数据
        var username = $('#username').val();
        var pwd = $('#pwd').val();
        formData.append('username',username);
        formData.append('pwd',pwd);

        /**
         * 发送ajax，将数据提交至后台处理相关操作
         */
        $.ajax({
            url : loginUrl,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            //判断是否成功返回数据(即是否成功将数据提交给后台)
            success: function(data){
                if(data.success){
                    //toast是一种轻量的提示，在页面中间显示，并且会在2秒(默认值，可修改)之后自动消失。
                    $.toast('登陆成功！');
                    window.location.href = '/o2o/frontend/index';
                }else{
                    $.toast(data.errorMsg);
                }
                //进行提交后,不管成功还是失败,都对验证码进行更新
                $('#captcha_img').click();
            }
        });
    });

    //点击重置按钮，清空表单内容，参考：https://blog.csdn.net/qq_34578253/article/details/71108155
    $('#clear').click(function () {
        $('input[type=reset]').trigger('click'); //使用trigger方法触发reset按钮
    });

    //跳转到注册页
    $('.to_register').click(function () {
        window.location.href = '/o2o/local/to_register';
    });



});