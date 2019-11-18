$(function () {
    // 从URL里获取productId参数的值
    var productId = getQueryString('productId');
    var isEdit = false;

    //获取当前店铺的商品类别列表的url
    var getProductCategoryUrl = '/o2o/shop_admin/get_product_category_list';
    //获取商品信息的url
    var getProductByIdUrl = '/o2o/shop_admin/get_product_by_id?productId=' + productId;

    var productPostUrl = '';

    //判断是添加商品还是编辑商品
    if (productId) {
        // 若有productId则为编辑操作
        getProductInfo();
        isEdit = true;
        //更新商品信息的url
        productPostUrl = '/o2o/shop_admin/modify_product';
    } else {
        getProductCategory();
        //添加商品的url
        productPostUrl = '/o2o/shop_admin/add_product';
    }


    /**
     * 根据productId从后端获取需要修改的店铺信息，并填充进表单里，方便修改操作，提高用户体验效果
     */
    function getProductInfo() {
        $.getJSON(getProductByIdUrl, function (data) {
            if (data.success) {
                var product = data.product;
                $('#product-name').val(product.productName);
                $('#priority').val(product.priority);
                $('#normal-price').val(product.normalPrice);
                $('#promotion-price').val(product.promotionPrice);
                $('#product-desc').val(product.productDesc);
                var optionHtml = '';
                data.productCategoryList.map(function (item, index) {
                    optionHtml += '<option data-id=' + item.productCategoryId + ' selected>'
                        + item.productCategoryName + '</option>';
                });
                $('#product-category').html(optionHtml);
            }
        });
    }


    /**
     * 向后端发送请求，后端将数据返回的response响应中，前端通过ajax处理响应，响应中的数据被data接收。
     *
     * 后端返回是json数据，根据json能直接转化成js对象的特性，所以可以直接通过data.key方式来访问
     */
    function getProductCategory() {
        $.getJSON(getProductCategoryUrl, function (data) {
            if (data.success) {
                var optionHtml = '';
                data.productCategoryList.map(function (item, index) {
                    optionHtml += '<option data-value='
                        + item.productCategoryId + '>'
                        + item.productCategoryName + '</option>';
                });
                $('#product-category').html(optionHtml);
            }
        });
    }


    /**
     * 若控件总数未达到6个，则点击就会生成新的一个文件上传控件
     */
    $('#detail-img').change(function () {
        if ($('.detail-img').length < 6) {
            $('#detail-img').append('<input type="file" accept="image/*" class="detail-img">');
        }
    });


    /**
     * 为submit控件绑定单击事件，先获取表单数据，然后提交给后端
     */
    $('#submit').click(function () {
        // 生成表单对象，用于接收表单数据并传递给后台
        var formData = new FormData();

        // 创建商品json对象
        var product = {};
        if (isEdit) {
            //若属于编辑，则为productId赋值
            product.productId = productId;
        }
        // 获取表单里的数据,并填充进商品json对象对应的属性里
        product.productName = $('#product-name').val();
        // 获取选定的商品类别值，保存productCategory对象里
        product.productCategory = {
            productCategoryId: $('#product-category option:checked').data('value')
        };
        product.priority = $('#priority').val();
        product.normalPrice = $('#normal-price').val();
        product.promotionPrice = $('#promotion-price').val();
        product.productDesc = $('#product-desc').val();
        // 将商品json对象转化成json字符串(字符流)保存到formData表单对象中key名为productStr的键值对里
        formData.append('productStr', JSON.stringify(product));

        // 获取缩略图文件流
        var thumbnail = $('#small-img')[0].files[0];
        // 将缩略图文件流添加进表单对象里
        formData.append('thumbnail', thumbnail);

        // 遍历商品详情图控件，获取里面的文件流
        $('.detail-img').map(function (index, item) {
            // 判断商品详情图控件是否选择了文件
            if ($('.detail-img')[index].files.length > 0) {
                // 将第i个文件流保存到formData表单对象中key为productImgi的键值对里
                formData.append('productImg' + index, $('.detail-img')[index].files[0]);
            }
        });

        // 获取表单里输入的验证码
        var verifyCode = $('#j_captcha').val();
        // 判断输入的验证码是否为空
        if (!verifyCode) {
            $.toast('请输入验证码!');
            return;
        }
        formData.append('verifyCode', verifyCode);

        /**
         *  将数据提交至后台处理相关操作
         */
        $.ajax({
            url: productPostUrl,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    //toast是一种轻量的提示，在页面中间显示，并且会在2秒(默认值，可修改)之后自动消失。
                    $.toast('提交成功!');
                    window.location.href = '/o2o/shop_admin/product_management';
                } else {
                    $.toast('提交失败！' + data.errMsg);
                }
                //进行提交后,不管成功还是失败,都对验证码进行更新
                $('#captcha_img').click();
            }
        });

    });


});