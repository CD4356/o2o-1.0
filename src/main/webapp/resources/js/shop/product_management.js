
$(function () {

    //获取当前店铺的商品列表
    var getProductListUrl = '/o2o/shop_admin/get_product_list?pageIndex=0&pageSize=99';
    //商品下架
    var statusUrl = '/o2o/shop_admin/modify_product';


    getProductList();

    /**
     * 获取当前店铺的商品列表
     */
    function getProductList() {
        $.getJSON(getProductListUrl,function (data) {
            if(data.success){
                var productList = data.productList;
                var tempHtml = '';
                productList.map(function (item, index) {
                    var textOp = "下架";
                    var contraryStatus;
                    if (item.enableStatus == 0) {
                        // 若状态值为0，表明是已下架的商品，操作变为上架(即点击上架按钮上架相关商品)
                        textOp = "上架";
                        contraryStatus = 1;
                    } else {
                        contraryStatus = 0;
                    }
                    // 拼接每件商品的行信息
                    tempHtml += '<div class="row row-product">'
                        + '<div class="col-33 product-name">'
                            + item.productName
                        + '</div>'
                        + '<div class="col-20">'
                            + item.priority
                        + '</div>'
                        + '<div class="col-40">'
                            + '<a href="#" class="edit" data-id=' + item.productId + ' data-status=' + item.enableStatus + '>编辑</a>'
                            + '<a href="#" class="status" data-id=' + item.productId + ' data-status=' + contraryStatus + '>' + textOp + '</a>'
                            + '<a href="#" class="preview" data-id=' + item.productId + ' data-status=' + item.enableStatus + '>预览</a>'
                        + '</div>'
                        + '</div>';
                });
                $('.product-wrap').html(tempHtml);
            }
        });
    }


    //对class为product-wrap里面的a标签绑定点击事件
    $('.product-wrap').on('click', 'a', function(e) {
        //获取当前目标元素
        var target = e.currentTarget;
        /**
         * 亦可通过var target = $(e.currentTarget); target.hasClass('edit');来判断目标元素类名是否为edit
         */
        if(target.className=='edit'){
            //若目标元素的class为edit，则点击就会进入店铺信息编辑界面
            window.location.href = '/o2o/shop_admin/product_operation?productId=' + target.dataset.id;
        }else if (target.className=='status'){
            //若目标元素的class为status，则点击就会调用后台功能完成商品上下架
            changeProductStatus(target.dataset.id,target.dataset.status);
        } else if(target.className=='preview'){
            //若目标元素的class为preview，则点击就会跳转到前台展示中该商品的详情页预览商品详情信息
            window.location.href = '/o2o/frontend/product_detail?productId=' + target.dataset.id;
        }
    });

    function changeProductStatus(id, status) {
        var product = {};
        product.productId = id;
        product.enableStatus = status;
        //弹出消息提示框，询问是否进行某某操作
        $.confirm("您确定吗？",function () {
            //上下架相关商品
            $.ajax({
                url: statusUrl,
                type: 'POST',
                data: {
                    productStr: JSON.stringify(product),
                    statusChange: true
                },
                dataType: 'json',
                //判断是否成功将数据提交给后台
                success:function (data) {
                    if(data.success){
                        //toast是一种轻量的提示，在页面中间显示，并且会在2秒(默认值，可修改)之后自动消失。
                        $.toast("操作成功啦！");
                        //操作成功后，刷新列表，获取列表最新状态
                        getProductList();
                    }else {
                        $.toast("操作失败了哦！");
                    }
                }
            });
        });
    }


});