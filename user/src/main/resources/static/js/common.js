$(".header").load("/user/head");
var _common = {
    //用户登录
    userLogin: function () {
        var password = $.md5($("#login_password").val());
        var username = $("#login_username").val();
        if (username == "" || password == "") {
            $("#loginModal").modal("show");
            return;
        }
        var obj = {username: username, password: password};
        $.ajax({
            url: "/user/login",
            method: "POST",
            data: JSON.stringify(obj),
            dataType: "json",
            contentType: 'application/json',
            success: function (json) {
                if (json.code == 0) {
                    window.location.reload();
                } else {
                    $("#loginModal").modal("show");
                    layer.alert(json.message);
                }
            }
        })
    },
    //用户注册
    register: function () {
        var userMobile = $("#register_userMobile").val();
        var userEmail = $("#register_user_email").val();
        var password = $("#register_password").val();
        var vercode = $("#register_vercode").val();
        if (userMobile == "" || userEmail == "" || password == "" || vercode == "") {
            layer. alert("参数不能为空");
            return;
        }
        var obj = {userMobile: userMobile, userEmail: userEmail, password: $.md5(password), vercode: vercode};
        $.ajax({
            url: "/user/register",
            method: "POST",
            data: JSON.stringify(obj),
            dataType: "json",
            contentType: 'application/json',
            success: function (json) {
                if (json.code == 0) {
                    $("#registerModal").modal("hide");
                    $("#loginModal").modal("show");
                    layer.alert('注册成功,请登陆');
                } else {
                    layer.alert(json.message);
                }
            }
        });
    },
    //停课
    stopCourse: function () {
        var obj = {start: $("#stop_course_startDate").val(), end: $("#stop_course_endDate").val()};
        $.ajax({
            url: "/user/stop",
            method: "POST",
            data: JSON.stringify(obj),
            dataType: "json",
            contentType: 'application/json',
            success: function (json) {
                if (json.code == 0) {
                    layer.alert('停课成功');
                    $("#stopCourseModal").modal("hide");
                } else {
                    layer.alert(json.message);
                }
            }
        });
    }
}