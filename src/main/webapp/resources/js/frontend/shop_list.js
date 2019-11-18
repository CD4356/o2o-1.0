
$(function () {
    // false表示当前是非正在加载状态
    var loading = false;
    //页码（1即从第一页开始）
    var pageIndex = 1;
    //限制一页返回的最大条数
    var pageSize = 10;
    // 后台返回的所有店铺数
    var maxItems = 0;
    // 每页加载的卡片数
    var total = 0;
    //获取店铺列表的Url
    var shopListUrl = '/o2o/frontend/shop_list';
    //从地址栏中获取ShopCategory的parentId
    var parentId = getQueryString('parentId');
    if(parentId != null){
        var shopPageInfoListUrl = '/o2o/frontend/shop_page_info_list?parentId=' + parentId;
    }else {
        var shopPageInfoListUrl = '/o2o/frontend/shop_page_info_list';
    }
    var areaId = '';
    var shopCategoryId = '';
    var shopName = '';

    // 渲染出店铺类别列表以及区域列表以供搜索
    getSearchData();


    /**
     * 获取店铺分类列表和区域列表信息
     */
    function getSearchData() {
        $.getJSON(shopPageInfoListUrl,function (data) {
            if(data.success){
                //获取后台返回过来的店铺类别列表
                var shopCategoryList = data.shopCategoryList;
                var html = '';
                html += '<a href="#" class="button" data-category-id=""> 全部类别  </a>';
                // 遍历店铺类别列表，拼接出a标签集
                shopCategoryList.map(function (item, index) {
                    html += '<a href="#" class="button" data-category-id="'+ item.shopCategoryId +'">'
                            + item.shopCategoryName +'</a>';
                });
                // 将拼接好的店铺类别标签嵌入前台的html组件里
                $('#shoplist-search-div').html(html);

                // 获取后台返回的区域列表
                var areaList = data.areaList;
                var selectOptions = '<option value="">全部街道</option>';
                // 遍历区域信息列表，拼接出option标签集
                areaList.map(function (item, index) {
                    selectOptions += '<option value="'+ item.areaId +'">'
                        + item.areaName +'</option>';
                });
                // 将拼接好的区域标签集嵌入前台的html组件里
                $('#area-search').html(selectOptions);
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
        var url = shopListUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
            + pageSize + '&parentId=' + parentId + '&areaId=' + areaId
            + '&shopCategoryId=' + shopCategoryId + '&shopName=' + shopName;
        // 访问后台,获取相应查询条件下的店铺列表
        $.getJSON(url,function (data) {
            if(data.success){
                // 获取后台返回过来当前查询条件下的店铺总数，超过次数则禁止访问后台
                maxItems = data.count;
                if(maxItems <= pageSize){
                    // 如果后端返回的数据<=10条（一页），则删除加载提示符（否则加载提示符一直在显示，影响美观）
                    $('.infinite-scroll-preloader').remove();
                }
                // 获取后台返回过来的店铺类别列表
                var shopList = data.shopList;
                if(shopList.length == 0){
                    var noShopHtml = '<div class="no-shop">宝宝什么都没有😫😫</div>';
                    $('.list-div').append(noShopHtml);
                }
                var html = '';
                // 遍历店铺列表，拼接出卡片集合
                shopList.map(function (item, index) {
                    html += '<div class="card" data-shop-id="'+ item.shopId +'">'
                        + '<div class="card-header">'+ item.shopName +'</div>'
                        + '<div class="card-content">'
                            + '<div class="list-block media-list">'
                                + '<ul>'
                                    + '<li class="item-content">'
                                        + '<div class="item-media">'
                                            + '<img src="'+ item.shopImg +'" width="44">'
                                        + '</div>'
                                        + '<div class="item-inner">'
                                            + '<div class="item-title">'+ item.shopDesc +'</div>'
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
            }
        });
    }

    //预先加载10条
    addItems(pageIndex, pageSize);

    // 下滑屏幕自动进行分页搜索
    $(document).on('infinite', '.infinite-scroll-bottom', function() {
        // 如果正在加载，则退出
        if (loading){
            return;
        }
        // 设置flag
        loading = true;
        // 否则页码加1，继续load出新的店铺
        pageIndex += 1;
        // 获取目前为止已显示的卡片总数，包含之前已经加载的
        total = $('.list-div .card').length;
        // 若总数达到跟按照此查询条件列出来的总数一致，则停止后台的加载
        if (total >= maxItems) {
            // 删除加载提示符
            $('.infinite-scroll-preloader').remove();
            return;
        }
        addItems(pageIndex, pageSize);
        // 加载结束，可以再次加载了
        loading = false;
        // 刷新页面，显示新加载的店铺
        $.refreshScroller();
    });


    // 点击店铺的卡片进入该店铺的详情页
    $('.shop-list').on('click', '.card', function(e) {
        var shopId = e.currentTarget.dataset.shopId;
        window.location.href = '/o2o/frontend/shop_detail?shopId=' + shopId;
    });


    // 选择新的店铺类别之后，重置页码，清空原先的店铺列表，按照新的类别去查询
    $('#shoplist-search-div').on('click', '.button', function(e) {
        if (parentId) { // 如果传递过来的是一个父类下的子类
            shopCategoryId = e.target.dataset.categoryId;
            if ($(e.target).hasClass('button-fill')) {
                // 若之前已经选中了该Category，再次点击，则移除该Category的选定效果，并设置shopCategoryId为空
                $(e.target).removeClass('button-fill');
                shopCategoryId = '';
            } else {
                // 若之前没有选定或已选定了别的category,则移除其选定效果，改成选定新的category
                $(e.target).addClass('button-fill').siblings().removeClass('button-fill');
            }
            // 由于查询条件改变，清空店铺列表再进行查询
            $('.list-div').empty();
            // 重置页码
            pageIndex = 1;
            addItems(pageIndex, pageSize);
        } else {// 如果传递过来的父类为空，则按照父类查询
            parentId = e.target.dataset.categoryId;
            if ($(e.target).hasClass('button-fill')) {
                $(e.target).removeClass('button-fill');
                parentId = '';
            } else {
                $(e.target).addClass('button-fill').siblings()
                    .removeClass('button-fill');
            }
            // 由于查询条件改变，清空店铺列表再进行查询
            $('.list-div').empty();
            // 重置页码
            pageIndex = 1;
            addItems(pageIndex, pageSize);
            parentId = '';
        }
    });


    // 需要查询的店铺名字发生变化后，重置页码，清空原先的店铺列表，按照新的名字去查询
    $('#search').on('change',function () {
        //获取输入框内容，并赋给shopName
        shopName =$('#search').val();
        // 清空原先的店铺列表
        $('.list-div').empty();
        // 重置页码
        pageIndex = 1;
        // 调用addItems()刷新店铺列表展示信息
        addItems(pageIndex, pageSize);
    });


    // 区域信息发生变化后，重置页码，清空原先的店铺列表，按照新的区域去查询
    $('#area-search').on('change',function (e) {
        areaId = $('#area-search').val();
        // 删除id为list-div元素的所有子元素，即将当前展示的店铺卡片全部删除
        $('.list-div').empty();
        // 重置页码
        pageIndex = 1;
        // 调用addItems()刷新店铺列表展示信息
        addItems(pageIndex, pageSize);
    });


    // 点击后打开右侧栏
    $('#me').click(function() {
        $.openPanel('#panel-right-demo');
    });

    // 初始化页面
    $.init();

});