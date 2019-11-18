
$(function () {
    var personInfoUrl = '/o2o/local/person_info';

    $.getJSON(personInfoUrl,function (data) {
        if(data.success){
            var person = data.person;
            $('#panel-right-demo .profile-img').attr('src',person.profileImg);
        }
    });

    //点击头像，放大预览，二次点击则还原
    $('#panel-right-demo .profile-img').click(function(){
        var width = $(this).width();
        if(width==100) {
            $(this).width(220);
            $(this).height(220);
        }
        else {
            $(this).width(100);
            $(this).height(100);
        }
    });

});