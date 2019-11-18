
$(function () {
    // 账号注册Url
    var registerUrl = '/o2o/local/register';

    $('#submit').click(function () {

        // 定义表单对象，用于接收前端数据以提交给后端
        var formData = new FormData();

        // 创建person对象
        var person = {};
        // 获取表单里的数据,并填充进person对象对应的属性里
        person.name = $('#username').val();
        person.pwd = $('#pwd').val();
        // 将person对象转化成json字符串，并保存到formDate表单对象中名为personStr的Key里
        formData.append('personStr',JSON.stringify(person));

        // 获取上传的图片文件流
        var uploadFile = $('#upload_file')[0].files[0];
        // 将图片流添加进表单对象里
        formData.append('uploadFile',uploadFile);

        // 获取表单输入的验证码
        var verifyCode = $('#j_captcha').val();
        // 判断输入的验证码是否为空
        if(!verifyCode){
            $.toast('请输入验证码!');
            return;
        }
        // 将验证码保存到ormDate表单对象中名为verifyCode的Key里
        formData.append('verifyCode',verifyCode);

        /**
         * 发送ajax，将数据提交至后台处理相关操作
         */
        $.ajax({
            url : registerUrl,
            type: 'post',
            data: formData,
            dataType: 'json', //指定服务器返回的数据类型
            processData: false, //必须参数(默认为true)，因为data值是FormData对象，告诉jQuery不要将data处理转化为查询字符串(URL参数)
            /*必须参数(默认为true)，data默认以查询字符串形式传输(如id=1&pwd=1)，这种传数据的格式，无法传输复杂的数据(如文件、数组)
              把contentType 改成 false 就会改掉之前默认的数据格式，在上传文件时就不会报错了
             */
            contentType: false,
            cache: false, //不从浏览器中加载请求信息
            //判断是否成功返回数据(即是否成功将数据提交给后台)
            success: function(data){
                if(data.success){
                    //toast是一种轻量的提示，在页面中间显示，并且会在2秒(默认值，可修改)之后自动消失。
                    $.toast('注册成功！');
                    window.location.href = '/o2o/local/to_login';
                }else{
                    $.toast(data.errorMsg);
                }
                //进行提交后,不管成功还是失败,都对验证码进行更新
                $('#captcha_img').click();
            }
        });
    });


    //点击重置按钮，清空表单内容  参考：https://blog.csdn.net/qq_34578253/article/details/71108155
    $('#clear').click(function () {
        $("input[type=reset]").trigger("click");//通过trigger()方法触发reset按钮
    });


});