
$(function () {
    var shopId = getQueryString('shopId');
    var shopDetailInfoUrl = '/o2o/frontend/shop_detail_info?shopId='+shopId;
    var productListUrl = '/o2o/frontend/product_list_by_shop';
    var loading = false;
    var maxItems = 0;
    var pageIndex = 1;
    var pageSize = 10;
    var total = 0;
    var productCategoryId = '';
    var productName = '';

    shopDetail();
    //预先加载pageSize条
    addItems(pageIndex, pageSize);

    function shopDetail(){
        $.getJSON(shopDetailInfoUrl,function (data) {
            if(data.success){
                var shop = data.shop;
                $('.shop-name').html('<b>'+ shop.shopName +'</b>');
                $('.shop-img').attr('src',shop.shopImg);
                $('.shop-update-time').text(shop.lastEditTime);
                $('.shop-desc').text(shop.shopDesc);
                $('.shop-address').text(shop.shopAddress);
                $('.shop-phone').text(phoneFormat(shop.shopPhone));

                var productCategoryList = data.productCategoryList;
                var html = '';
                // 遍历商品列表，生成可以点击搜索相应商品类别下的商品的a标签
                productCategoryList.map(function (item, index) {
                    html += '<a href="#"  class="button" data-product-category-id="'+ item.productCategoryId +'">'
                            + item.productCategoryName +'</a>'
                });
                // 将商品类别a标签绑定到相应的HTML组件中
                $('.product-category-button').html(html);
            }
        });
    }


    /**
     * 获取分页展示的店铺列表信息
     *
     * @param pageIndex
     * @param pageSize
     * @returns
     */
    function addItems(pageIndex, pageSize) {
        // 拼接出查询的URL，赋空值默认就去掉这个条件的限制，有值就代表按这个条件去查询
        var url = productListUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize=' + pageSize
            + '&shopId=' + shopId + '&productName=' + productName + '&productCategoryId=' + productCategoryId;
        // 设置flag
        loading = true;
        // 访问后台,获取相应查询条件下的商品列表
        $.getJSON(url,function (data) {
            if(data.success){
                // 获取后台返回过来当前查询条件下的商品总数，超过次数则禁止访问后台
                maxItems = data.count;
                if(maxItems <= pageSize){
                    // 如果后端返回的数据<=10条（一页），则删除加载提示符（否则加载提示符一直在显示，影响美观）
                    $('.infinite-scroll-preloader').remove();
                }
                // 获取后台返回过来的商品类别列表
                var productList = data.productList;
                // 如果返回的商品列表长度为0，即该店铺还没有商品，则显示无商品提示信息
                if(productList.length == 0){
                    var noProductHtml = '<div class="no-product">宝宝什么都没有😫😫</div>';
                    $('.list-div').append(noProductHtml);
                }
                var html = '';
                // 遍历商品列表，拼接出卡片集合
                productList.map(function (item, index) {
                    html  += '<div class="card" data-product-id="'+ item.productId +'">'
                        + '<div class="card-header">'+ item.productName +'</div>'
                        + '<div class="card-content">'
                            + '<div class="list-block media-list">'
                                + '<ul>'
                                    + '<li class="item-content">'
                                        + '<div class="item-media">'
                                            + '<img src="'+ item.imgAddress +'" width="44">'
                                        + '</div>'
                                        + '<div class="item-inner">'
                                            + '<div class="item-title">'+ item.productDesc +'</div>'
                                        + '</div>'
                                    + '</li>'
                                + '</ul>'
                            + '</div>'
                        + '</div>'
                        + '<div class="card-footer">'
                            + '<p class="color-gray">'+ item.lastEditTime +'更新</p>'
                            + '<span> 点击查看 </span>'
                        + '</div>'
                    + '</div>';
                });
                // 将卡片集合添加到目标HTML组件里
                $('.list-div').append(html);
                // 加载结束，可以再次加载了
                loading = false;
            }
        });
    }


    // 下滑屏幕自动进行分页搜索
    $(document).on('infinite', '.infinite-scroll-bottom', function() {
        // 如果正在加载，则退出
        if (loading){
            return;
        }
        // 否则页码加1，继续加载出新的商品
        pageIndex += 1;
        // 获取目前为止已显示的卡片总数，包含之前已经加载的
        total = $('.list-div .card').length;
        // 若总数达到跟按照此查询条件列出来的总数一致，则停止后台的加载
        if (total >= maxItems) {
            // 加载完毕，删除加载提示符，以防不必要的加载
            $('.infinite-scroll-preloader').remove();
            return;
        }
        // 调用addItems方法刷新商品列表
        addItems(pageIndex, pageSize);
        // 刷新页面，显示新加载的商品
        $.refreshScroller();
    });


    // 搜索框商品名称后，重置页码，清空原先的店铺列表，按照新的名字去查询
    $('#search').on('change',function () {
        // 获取输入框输入的商品名称，并赋给productName
        productName = $('#search').val();
        // 清空原先的商品列表
        $('.list-div').empty();
        // 重置页码
        pageIndex = 1;
        // 调用addItems方法刷新商品列表
        addItems(pageIndex,pageSize);
    });

    // 选择新的商品类别之后，重置页码，清空原先的商品列表，按照新的类别去查询
    $('.product-category-button').on('click','.button',function (e) {
        productCategoryId = e.target.dataset.productCategoryId;
        if(productCategoryId){
            // 若之前已选定了别的category,则移除其选定效果，改成选定新的
            if($(e.target).hasClass('button-fill')){
                // 若之前已经选中了该Category，再次点击，则移除该Category的选定效果，并设置productCategoryId为空
                $(e.target).removeClass('button-fill');
                productCategoryId = '';
            }else {
                // 若之前没有选定或已选定了别的category,则移除其选定效果，改成选定新的category
                $(e.target).addClass('button-fill').siblings().removeClass('button-fill');
            }
            // 清空原先的商品列表
            $('.list-div').empty();
            // 重置页码
            pageIndex = 1;
            // 调用addItems方法获取商品列表
            addItems(pageIndex,pageSize);
        }
    });


    // 点击商品卡片进入商品详情页
    $('.list-div').on('click','.card',function (e) {
        var productId = e.currentTarget.dataset.productId;
        window.location.href = '/o2o/frontend/product_detail?productId=' + productId;
    });


    /**
     * 格式化电话号码显示，比如：12312312312 格式化后变为 123-123-123-12
     *
     * 参考：https://cloud.tencent.com/developer/ask/50895
     * @param phone
     * @returns {string}
     */
    function phoneFormat(phone) {
        var phoneFormat = phone.substr(0, 3)+ '-' +phone.substr(3, 3)+ '-' +phone.substr(6,5);
        return phoneFormat;
    }


    // 点击后打开右侧栏
    $('#me').click(function() {
        $.openPanel('#panel-right-demo');
    });

});
