

$(function() {

    var productCategoryManagementUrl = '/o2o/shop_admin/get_product_category_list';
    var batchAddProductCategoryUrl = '/o2o/shop_admin/batch_add_product_category';
    var removeProductCategoryUrl = '/o2o/shop_admin/remove_product_category';

    getProductCategoryList();

    /**
     * 向后端发送请求，获取后端从数据库中查询到的商品分类信息，然后动态显示在前端页面中
     */
    function getProductCategoryList() {
        $.getJSON(productCategoryManagementUrl,function(data) {
            if(data.success){
                // 将商品分类信息渲染到页面之前，先将原有的清空，防止重复显示
                $('category-wrap').html('');
                var dataList = data.productCategoryList;
                var tempHtml = '';
                dataList.map(function (item, index) {
                    tempHtml += ''
                        + '<div class="row row-product-category now">'
                            + '<div class="col-33 product-category-name">'
                                + item.productCategoryName
                            + '</div>'
                            + '<div class="col-33">'
                                + item.priority
                            + '</div>'
                            + '<div class="col-33">'
                                +'<a href="#" class="button delete" data-id="' + item.productCategoryId + '">删除</a>'
                            +'</div>'
                        + '</div>';
                });
                $('.category-wrap').html(tempHtml); //html()表示替换元素内容
            }
        });
    }

    /**
     * 点击新增按钮时，新增input输入框来输入新增商品分类信息
     */
    $('#new').click(function() {
        var tempHtml = '<div class="row row-product-category temp">'
                    + '<div class="col-33"><input class="category-input category" type="text" placeholder="分类名"></div>'
                    + '<div class="col-33"><input class="category-input priority" type="number" placeholder="优先级"></div>'
                    + '<div class="col-33"><a href="#" class="button delete">删除</a></div>'
                    + '</div>';
        $('.category-wrap').append(tempHtml); //append()表示在元素内容后插入（注意：是元素内容后，不是元素后）
    });


    /**
     * 点击提交按钮时，将新增的商品分类信息提交到后端指定的路由方法中
     */
    $('#submit').click(function() {
        var tempArr = $('.temp');
        var productCategoryList = [];
        tempArr.map(function(index, item) {
            var tempObj = {};
            tempObj.productCategoryName = $(item).find('.category').val();
            tempObj.priority = $(item).find('.priority').val();
            if (tempObj.productCategoryName && tempObj.priority) {
                //将tempObj对象push进productCategoryList数组中
                productCategoryList.push(tempObj);
            }
        });

        $.ajax({
            url: batchAddProductCategoryUrl,            /*请求地址*/
            type: 'POST',                               /*请求类型*/
            data: JSON.stringify(productCategoryList),  /*请求参数，json字符串*/
            dataType: 'json',                           /*告诉服务器，我想从服务器获取什么类型的数据*/
            /*添加contentType: 'application/json'后，向后台发送的数据必须为json字符串类型，不能是json对象类型*/
            contentType: 'application/json; charset=utf-8',  /*告诉服务器，我想向服务器发送什么类型的数据*/
            success: function(data) {  //请求成功时执行的函数
                if(data.success) {
                    $.toast('提交成功！');
                    //刷新商品分类列表
                    getProductCategoryList();
                }else {
                    $.toast('提交失败！');
                }
            }
        });
    });


    /**
     * 给.category-wrap标签下的.row-product-category.temp .delete元素绑定单击事件，移除新增的行
     */
    $('.category-wrap').on('click', '.row-product-category.temp .delete', function() {
        //删除当前点击元素的上上级元素
        $(this).parent().parent().remove();
    });


    /**
     * 移除非新增的行，并到后端删除数据库指定的数据，然后调用getProductCategoryList()函数刷新页面
     */
    $('.category-wrap').on('click', '.row-product-category.now .delete', function (e) {
        //获取当前目标元素
        var target = e.currentTarget;
        //弹出消息提示框，询问是否进行某某操作
        $.confirm('是否删除该数据？',function () {
            $.ajax({
                url: removeProductCategoryUrl,
                type: 'POST',
                data: {productCategoryId: target.dataset.id},  //获取当前目标元素的id值
                dataType: 'json',
                success:function (data) {
                    if(data.success){
                        //toast是一种轻量的提示，在页面中间显示，并且会在2秒(默认值，可修改)之后自动消失。
                        $.toast('删除成功！');
                        //刷新商品分类列表
                        getProductCategoryList();
                    }else {
                        $.toast('删除失败!');
                    }
                }
            });
        });
    });

    $.init();

});