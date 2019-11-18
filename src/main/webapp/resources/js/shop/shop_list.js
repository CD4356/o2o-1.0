/**
 * 店铺列表
 */

$(function() {

    getlist();

    function getlist() {
        //通过ajax获取后台传入数据
        $.ajax({
            url: "/o2o/shop_admin/get_shop_list",
            type: "get",
            dataType: "json",
            success:function (data) {  // data是后台返回的数据
                if(data.success){
                    //调用handleUser(data)方法获取处理人信息（商家），并渲染到前端
                    handleUser(data.user);
                    //调用handleList(data)方法去处理店铺
                    handleList(data.shopList);
                }
            }
        });
    }


    //获取处理人信息（商家），并渲染到前端
    function handleUser(data) {
        $('#user-name').text(data.name);
    }


    //处理店铺列表
    function handleList(data) {
        var html = '';
        data.map(function(item, index) {
            html += '<div class="row row-shop"><div class="col-40">'
                + item.shopName + '</div><div class="col-40">'
                + shopStatus(item.enableStatus)
                + '</div><div class="col-20">'
                + goShop(item.enableStatus, item.shopId) + '</div></div>';

        });
        $('.shop-wrap').html(html);
    }


    //将店铺状态所代表的数字，转化成对应的中文，并渲染到前端
    function shopStatus(status) {
        if(status == 0){
            return '审核中';
        }else if(status == 1){
            return '审核通过';
        }else if(status == -1) {
            return '非法店铺';
        }
    }


    //进入店铺管理页（只有通过审核的店铺才会显示该选项）
    function goShop(status,id) {
        if(status == 1){
            return '<a href="/o2o/shop_admin/shop_management?shopId=' + id + '">进入</a>';
        }else {
            return '';
        }
    }

    
});