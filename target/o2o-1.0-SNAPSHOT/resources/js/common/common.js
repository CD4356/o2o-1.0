
function changeVerifyCode(img) {
    img.src = "../Kaptcha?" + Math.floor(Math.random() * 100);
}


/**
 * 获取浏览器url中的参数（这里用了正则表达式匹配，如果看不懂可以看一下这篇博文https://blog.csdn.net/weixin_42950079/article/details/102763183）
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return decodeURIComponent(r[2]);
    }
    return '';
}
