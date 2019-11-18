

$(function() {
    // 从URL里获取shopId参数的值
    var shopId = getQueryString('shopId');
    var shopInfoUrl = '/o2o/shop_admin/get_shop_management_info?shopId=' + shopId;

    $.getJSON(shopInfoUrl,function(data) {
        if(data.redirect){
            window.location.href = data.url;
        }else{
            if(data.shopId !=undefined && data.shopId !=null){
                shopId = data.shopId;
            }

            // 如果后端返回了shopId且值不为空，则
            $('#shopInfo').attr('href','/o2o/shop_admin/shop_operation?shopId=' + shopId);
        }
    });
});