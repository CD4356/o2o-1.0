/**
 * jquery使用ajax
 */

$(function () {
    // 从URL里获取shopId参数的值
    var shopId = getQueryString('shopId');
    var isEdit = shopId?true:false;
    var getShopInfoUrl = '/o2o/shop_admin/get_shop_info';
    var registerShopUrl = '/o2o/shop_admin/register_shop';
    var getByShopIdUrl = "/o2o/shop_admin/get_shop_by_id?shopId=" + shopId;
    var modifyShopUrl = '/o2o/shop_admin/modify_shop';

    if (!isEdit){
        getShopInitInfo();
    } else {
        getShopInfo(shopId);
    }


    /**
     * 取得所有二级店铺类别以及区域信息，并分别填充到店铺类别以及区域列表中，方便进行店铺注册
     */
    function getShopInitInfo() {
        // $表示jquery对象，例如$.post()、$.get()、$.ajax()、$.getJSON()、$.confirm()等这些都是jquery这个对象的方法
        $.getJSON(getShopInfoUrl,function (data) { //这里的getShopInfoUrl是发送请求的地址，data是指后台响应请求后返回的数据
            // 判断是否成功获取待后台返回数据
            if(data.success){
                var tempShopCategoryHtml = '';
                var tempAreaHtml = '';

                data.shopCategoryList.map(function(item,index) {
                    tempShopCategoryHtml += '<option data-id="' + item.shopCategoryId + '">'
                        + item.shopCategoryName + '</option>';
                });

                data.areaList.map(function(item,index) {
                    tempAreaHtml += '<option data-id="' + item.areaId + '">'
                        + item.areaName + '</option>';
                });

                $('#shop-category').html(tempShopCategoryHtml);
                $('#area').html(tempAreaHtml);
            }
        });

    }


    /**
     * 通过店铺Id从后端获取店铺信息，然后渲染到前端页面中，方便对店铺信息进行修改
     * @param shopId
     */
    function getShopInfo(shopId) {
        $.getJSON(getByShopIdUrl, function(data) {
            if (data.success) {
                // 若访问成功，则依据后台传递过来的店铺信息为表单元素赋值
                var shop = data.shop;
                $('#shop-name').val(shop.shopName);
                $('#shop-address').val(shop.shopAddress);
                $('#shop-phone').val(shop.shopPhone);
                $('#shop-desc').val(shop.shopDesc);
                // 给店铺类别选定原先的店铺类别值
                var shopCategory = '<option data-id="'
                    + shop.shopCategory.shopCategoryId + '" selected>'
                    + shop.shopCategory.shopCategoryName + '</option>';
                var tempAreaHtml = '';
                // 初始化区域列表
                data.areaList.map(function(item, index) {
                    tempAreaHtml += '<option data-id="' + item.areaId + '">'
                        + item.areaName + '</option>';
                });
                $('#shop-category').html(shopCategory);
                // 不允许选择店铺类别
                $('#shop-category').attr('disabled', 'disabled');
                $('#area').html(tempAreaHtml);
                // 给店铺选定原先的所属的区域
                $("#area option[data-id='" + shop.area.areaId + "']").attr("selected", "selected");
            }
        });
    }


    /**
     * 绑定提交按钮单击事件,分别对店铺注册和编辑操作做不同响应
     */
    $('#submit').click(function () {
        var shop = {};
        if (isEdit) {
            // 若属于编辑，则给shopId赋值
            shop.shopId = shopId;
        }
        // 获取表单里的数据并填充进对应的店铺属性中
        shop.shopName = $('#shop-name').val();
        shop.shopAddress = $('#shop-address').val();
        shop.shopPhone = $('#shop-phone').val();
        shop.shopDesc = $('#shop-desc').val();

        //选择选定好的店铺类别
        shop.shopCategory = {
            shopCategoryId : $('#shop-category option:checked').data('id')
        };
        // 选择选定好的区域信息
        shop.area = {
            //$(selector).not(function)是用来遍历元素集合的函数，并返回要被删除的一个元素
            areaId : $('#area').find('option').not(function(){
                //排除没有被选中的 （this指的是当前的DOM元素）
                return !this.selected;
            }).data('id')
        };

        // 获取上传的图片文件流
        var shopImg = $('#shop-img')[0].files[0];

        // 生成表单对象，用于接收参数并传递给后台
        var formData = new FormData();
        // 将图片流添加进表单对象里
        formData.append('shopImg',shopImg);
        // 将shop json对象转成字符流保存至表单对象key为shopStr的的键值对里
        formData.append('shopStr',JSON.stringify(shop)); //JSON.stringify()方法用于将json对象转换为字符串

        // 获取表单里输入的验证码
        var verifyCode = $('#j_captcha').val();
        // 判断验证码是否为空
        if(!verifyCode){
            $.toast('请输入验证码');
            return;
        }
        // 将验证码添加进表单对象里
        formData.append('verifyCode',verifyCode);

        /**
         * 发送ajax，将数据提交至后台处理相关操作
         */
        $.ajax({
            url : (isEdit ? modifyShopUrl : registerShopUrl),
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            //判断是否成功返回数据(即是否成功将数据提交给后台)
            success: function(data){
                if(data.success){
                    window.location.href = '/o2o/shop_admin/shop_list';
                    //toast是一种轻量的提示，在页面中间显示，并且会在2秒(默认值，可修改)之后自动消失。
                    $.toast('提交成功！');
                }else{
                    $.toast('提交失败！'+data.errMsg);
                }
                //进行提交后,不管成功还是失败,都对验证码进行更新
                $('#captcha_img').click();
            }
        });

    });

});