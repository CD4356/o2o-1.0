
$(function () {

    $('#portal').click(function () {
        //通过ajax获取后台传入数据
        $.ajax({
            url: "/o2o/local/portal",
            type: "POST",
            dataType: "json",
            success:function (data) {  // data是后台返回的数据
                if(data.success){
                    if(data.flag == 3){
                        window.location.href = '/o2o/shop_admin/shop_list';
                    }else if(data.flag == 2){
                        window.location.href = '/o2o/local/to_account_bind';
                    }else if(data.flag == 1){
                        window.location.href = '/o2o/local/to_login';
                    }
                }
            }
        });

    });

});