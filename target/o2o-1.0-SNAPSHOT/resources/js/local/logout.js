
$(function() {

    //点击登出按钮，去后端清除session，并跳转回首页
    $('#logout').click(function() {
        // 清除session
        $.ajax({
            url : "/o2o/local/logout",
            type : "POST",
            dataType : 'json', //dataType，指定接收后端返回的数据类型
            success : function(data) {
                if (data.success) {
                    // 清除成功后退出到登录界面
                    window.location.href = "/o2o/frontend/index";
                }else {
                    alert("失败！");
                }
            }
        });
    });


});