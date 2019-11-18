
$(function () {
    // 从地址栏中获取productId
    var productId = getQueryString('productId');
    // 获取商品信息的Url
    var productDetailUrl = '/o2o/frontend/product_detail_info?productId=' + productId;


    showProductDetail();

    // 访问后端获取信息，并在前端进行渲染
    function showProductDetail() {
        /**
         * $表示jquery对象，例如$.post()、$.get()、$.ajax()、$.getJSON()、$.confirm()等这些都是jquery这个对象的方法
         * 其中$.getJSON()方法用于向后端发送请求，并接收后端返回的json数据
         */
        $.getJSON(productDetailUrl,function (data) {
            if(data.success){
                // 获取商品信息
                var product = data.product;
                // 商品名称
                $('.product-name').text(product.productName);
                // 商品缩略图
                $('.product-img').attr('src',product.imgAddress);
                // 最新更新时间
                $('.product-update-time').text(product.lastEditTime);
                // 商品简介
                $('.product-desc').text(product.productDesc);

                // 商品价格展示逻辑
                if(product.normalPrice != undefined && product.promotionPrice != undefined){
                    // 如果原价现价都不为空，则都进行展示
                    // 原价
                    $('.normal-price').html('<del>' + '￥' + product.normalPrice + '</del>');
                    $('.normal-price').attr('class','del');
                    // 现价
                    $('.promotion-price').html('￥' + product.promotionPrice);
                    $('.promotion-price').attr('class','show');
                }else if(product.normalPrice != undefined && product.promotionPrice == undefined){
                    // 如果原价不为空，而现价为空，则只展示原价
                    $('.normal-price').html('￥' + product.normalPrice);
                    $('.normal-price').attr('class','show');
                }else if(product.normalPrice == undefined && product.promotionPrice != undefined){
                    // 如果现价不为空，而原价为空，则只展示现价
                    $('.promotion-price').html('￥' + product.promotionPrice);
                    $('.promotion-price').attr('class','show');
                }

                // 获取商品详情图列表信息
                var productImgList = product.productImgList;
                if(productImgList.length <= 2){
                    // 如果后端返回的商品详情图列表长度<=5，则删除加载提示符（否则加载提示符一直在显示，影响美观）
                    $('.infinite-scroll-preloader').remove();
                }
               if(productImgList[0].productImgId == null){
                   var noDetailImgHtml = '<div class="no-img">宝宝什么都没有😫😫</div>';
                   $('.demo-card-header-pic').after(noDetailImgHtml);
                   return;
               }
                var html = '';
                // 遍历商品详情图列表，并生成批量的img标签
                productImgList.map(function (item, index) {
                    html += '<div><img src="'+ item.productDetailImg +'"></div>'
                });
                $('.list-div').html(html);
            }
        });
    }

});