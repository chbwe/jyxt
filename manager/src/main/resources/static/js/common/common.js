/**
 * Created by ah10 on 2018/5/3.
 */
function clearChangePwdModal() {
    $("#edit_password").val("");
    $("#edit_newPassword").val("");
    $("#edit_confirmPassword").val("");
}

$(".headerbar").load('/user/header', function () {
    _common.i18n.init();
    $('.password-cancel').on('click', function (e) {
        clearChangePwdModal();
    });
    //修改密码
    $(".password-save").on("click", function () {
        var password = $("#edit_password").val();
        var newPassword = $("#edit_newPassword").val();
        var confirmPassword = $("#edit_confirmPassword").val();
        if (password == "" || newPassword == "" || confirmPassword == "") {
            alert("请输入");
            return;
        }
        if (newPassword != confirmPassword) {
            alert("新密码跟确认密码不一致");
            return;
        }
        var obj = {
            "password": $.md5(password),
            "newPassword": $.md5(newPassword)
        }
        $.ajax({
            url: "/teacher/password/change",
            method: "POST",
            data: JSON.stringify(obj),
            dataType: "json",
            contentType: 'application/json',
            success: function (json) {
                if (json.code == 0) {
                    clearChangePwdModal();
                    alert("操作成功");
                    $('.edit-password-modal-lg').hide();
                    window.location.reload();
                } else {
                    alert(json.message);
                }
            }
        })
    });
});

$(".leftpanel").load('/user/navigation', function () {
    var page = window.location.pathname;
    $(".leftpanel .children").hide();
    var children = $(".leftpanel .children").find("a[href='" + page + "']").parent();
    children.addClass('active');
    children.parent().show();
    children.parent().parent().addClass("nav-active active");
    //根据语言适配
    _common.i18n.init();
});

$(function () {
    $("#select-teacher-modal").load("/teacher/select");
    //教师选择窗口--搜索
    $(".teacher-select-search").on("click", function () {
        $(".select-teacher-modal").load("/teacher/select");
    });

});

var _common = {
    //选择老师
    selected: function (id, teacherName) {
        $("#search_teacherId").val(id);
        $(".select-teacher").val(teacherName);
        $("#page").val(1);
        $("#queryForm").submit();
    },
    //表单操作
    from: {
        //清空表单
        clear: function (formId) {
            $(formId).find("input[type='text']").val("");
            $(formId).find("input[type='number']").val("");
            $(formId).find("textarea").val("");
        },
        //表单初始化
        load: function (formId, json) {
            if (typeof json === 'undefined') return;
            var target = $("#" + formId);
            var inputs = target.find("input");
            var name = "";
            for (var i = 0, j = inputs.length; i < j; i++) {
                if ($(inputs[i]).attr('type') == "checkbox") continue;
                name = $(inputs[i]).attr("name");
                $(inputs[i]).val(json[name]);
            }

            inputs = target.find("textarea");
            for (var i = 0, j = inputs.length; i < j; i++) {
                name = $(inputs[i]).attr("name");
                $(inputs[i]).val(json[name]);
            }

            inputs = target.find("select");
            for (var i = 0, j = inputs.length; i < j; i++) {
                name = $(inputs[i]).attr("name");
                if (typeof name !== 'undefined' && typeof json[name] !== 'undefined') {
                    $(inputs[i]).find("option[value='" + json[name] + "']").attr("selected", "selected");
                }
            }
        }
    },
    //加载国际化语言
    i18n: {
        //初始化
        init: function () {
            _common.i18n.load(_common.i18n.default());
        },
        //获取默认的语言
        default: function () {
            var language = localStorage.getItem("language");
            if (typeof language == 'undefined' || language == null) language = "zh";
            return language;
        },
        //切换语言
        switch: function () {
            var language = _common.i18n.default();
            if (language == 'zh') {
                language = "us";
            } else {
                language = "zh";
            }
            localStorage.setItem("language", language);
            _common.i18n.load(language);
        },
        //加载语言配置文件
        load: function (type) {
            if (!jQuery.i18n) return;
            jQuery.i18n.properties({
                name: 'jyxt',
                path: '/static/i18n/', //资源文件路径
                mode: 'both', //用Map的方式使用资源文件中的值
                language: type,
                cache: true,
                callback: function () {//加载成功后设置显示内容
                    $("[data-locale]").each(function () {
                        var val = $.i18n.map[$(this).data("locale")];
                        if (val) $(this).html(val);
                    });
                }
            });
        }
    },
    //提示消息
    message: function (json, message, url) {
        if (json.code != 0) {
            layer.alert(json.message);
        } else {
            layer.alert(message ? message : "操作成功", function () {
                layer.closeAll();
                if (url && url != "") window.location.href = url;
            });
        }
    }
}

//分页插件
function loadPaginator(settings, id, url) {
    var options = {
        bootstrapMajorVersion: 3,
        alignment: "center",//居中显示
        pageSize: settings.size,
        currentPage: settings.page,//当前页数
        totalPages: settings.totalPage,//总页数 注意不是总条数
        pageUrl: function (type, page, current) {
            if (page == current) {
                return "javascript:void(0)";
            } else {
                if (typeof url == 'string') {
                    return url + "?size=" + settings.size + "&page=" + page;
                }
                if (typeof url == 'function') {
                    url(page);
                }
                $("#page").val(page);
                $("#queryForm").submit();
            }
        }
    }
    $(id).bootstrapPaginator(options);
}

//全局设置ajax
$.ajaxSetup({cache: false});

$(function () {
    _common.i18n.init();
    //切换
    $(".switch-language").on("click", function () {
        var l = $(this).text();
        var language = "zh";
        if (l == "English") {
            sessionStorage.setItem("language", "us");
        }
        _common.i18n(language);
    });

    //审批
    $(".admin-approval").on("click", function () {
        var id = $(this).parent().attr("node-id");
        var type = $(this).parent().attr("node-type");
        layer.confirm("确认要进行审批吗？", function (index) {
            layer.close(index);
            $.getJSON(type + "/approval?id=" + id+"&status=1", function (json) {
                _common.message(json, "操作成功", type);
            });
        });
    })
});
