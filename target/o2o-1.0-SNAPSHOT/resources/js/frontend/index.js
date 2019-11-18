
$(function () {
    //定义访问后台，获取头条列表和以及店铺分类列表的url
    var url = '/o2o/frontend/main_page_info_list';

    //访问后台，获取信息
    $.getJSON(url,function (data) { //data用于接收访问成功后，后台返回的json数据
        if(data.success){
            //获取后台返回的头条列表信息
            var headLineList = data.headLineList;
            var swiperHtml = '';
            headLineList.map(function (item, index) {
                swiperHtml += '<div class="swiper-slide img-wrap">'
                    + '<a href="' + item.lineLink + '" external>'
                    + '<img class="banner-img" src="'+ item.lineImg +'" alt="'+ item.lineName +'" </a></div>'
            });
            //将轮播图组赋值给前端HTML控件
            $('.swiper-wrapper').html(swiperHtml);
            $('.swiper-container').swiper({
                //设定轮播图切换时间为3秒
                autoplay: 3000,
                //用户对轮播图进行操作时，停止autoplay自动播放
                aotuplayDisableOnInteraction: true
            });

            //获取后台返回的一级分类店铺信息
            var shopCategoryList = data.shopCategoryList;
            var categoryHtml = '';
            shopCategoryList.map(function (item, index) {
                categoryHtml += '<div class="col-50 shop-classify" data-category="'+ item.shopCategoryId +'">'
                    + '<div class="word">'
                        + '<p class="shop-title" >'+ item.shopCategoryName + '</p>'
                        + '<p class="shop-desc" >'+ item.shopCategoryDesc + '</p>'
                    + '</div>'
                    + '<div class="shop-classify-img-warp">'
                        + '<img class="shop-img" src="'+ item.shopCategoryImg +'" />'
                    + '</div> '
                    + '</div>'
            });
            //将拼接好的一级店铺分类赋值给前端HTML控件进行展示
            $('.row').html(categoryHtml);
        }
    });

    // 点击跳转到当前店铺分类下的店铺列表页面
    $('.row').on('click','.shop-classify',function (e) {
        //获取当前单击元素的数据属性值shopCategoryId
        var shopCategoryId = e.currentTarget.dataset.category;
        var shopListUrl = '/o2o/frontend/to_shop_list?parentId=' + shopCategoryId;
        window.location.href = shopListUrl;
    });


});