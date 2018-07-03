<style>
    .getVcode {
        font-size: 14px;
        font-weight: 300 !important;
        color: #000;
        cursor: pointer;
        outline: none;
        padding: 7px 15px;
        width: 100%;
        margin: 0 auto;
        border: none;
        background: #328cda;
        text-transform: uppercase;
        border: 4px double #328cda;
    }
</style>
<script type="application/javascript" src="/js/jQuery.md5.js"></script>
<div class="container">
    <div class="col-sm-3 logo">
        <h1><a href="index.html"><span class="highlight"></span>艺百汇新教育</a></h1>
    </div>
    <div class="col-sm-9 logo_right">
        <span class="menu"></span>
        <div class="top-menu">
            <ul>
            <#if isLogin>
                <li><a href="myClass.html" class="btn btn-default btn-default_2 pull-left">我的课程</a></li>
                <li><a href="myApoint.html" class="btn btn-default btn-default_2 pull-left">我的预约</a></li>
                <li><a href="myBuy.html" class="btn btn-default btn-default_2 pull-left">我的购买</a></li>
                <li><a href="myStopRecord.html" class="btn btn-default btn-default_2 pull-left">我的停课记录</a></li>
                <li><a href="myLeaveRecord.html" class="btn btn-default btn-default_2 pull-left">我的请假记录</a></li>
                <li><a href="javascript:;" class="btn btn-default btn-default_2 pull-left user-logout">退出</a></li>
            <#else>
                <li><a href="#" class="btn btn-default btn-default_2 pull-left" data-toggle="modal"
                       data-target="#loginModal">登录</a></li>
                <li><a href="#" class="btn btn-default btn-default_2 pull-left" data-toggle="modal"
                       data-target="#registerModal">注册</a></li>
            </#if>
            </ul>
        </div>
        <div class="clearfix"></div>
    </div>
</div>
<!-- 登陆 -->
<div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="applyModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog_2">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
                <h4 class="modal-title" id="myModalLabel">
                    <div class="head_4">
                        <p>登录</p>
                    </div>
                </h4>
            </div>
            <div class="modal-body">
                <form class="register">
                    <div class="section">
                        <label for="username" class="field prepend-icon">
                            <input type="text" name="username" id="login_username" placeholder="请输入注册时填写的手机号码" required>
                            <label for="username" class="field-icon">
                                <i class="fa fa-user"></i>
                            </label>
                        </label>
                    </div>
                    <div class="section">
                        <label for="password" class="field prepend-icon">
                            <input type="password" name="password" id="login_password" placeholder="请输入密码" required>
                            <label for="password" class="field-icon">
                                <i class="fa fa-lock"></i>
                            </label>
                        </label>
                    </div>
                    <div class="section">
                        <div class="submit"><input type="button" onclick="_common.userLogin()" value="登录"></div>
                    </div>
                    <ul class="new">
                        <li class="new_left"><a href="javascript:;">注册</a></li>
                    </ul>
                    <div class="clearfix"></div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- 注册 -->
<div class="modal fade" id="registerModal" tabindex="-1" role="dialog" aria-labelledby="applyModalLabel"
     aria-hidden="true">
    <div class="modal-dialog dialog_3">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
                <h4 class="modal-title" id="myModalLabel">
                    <div class="head_4">
                        <p>注册</p>
                    </div>
                </h4>
            </div>
            <div class="section">
                <label for="username" class="field prepend-icon">
                    <input type="text" name="userEmail" id="register_user_email" placeholder="姓名" value="">
                    <label for="email id" class="field-icon">
                        <i class="fa fa-user"></i>
                    </label>
                </label>
            </div>
            <div class="section">
                <label for="password" class="field prepend-icon">
                    <input type="password" name="password" id="register_password" placeholder="密码" value="">
                    <label for="password" class="field-icon">
                        <i class="fa fa-lock"></i>
                    </label>
                </label>
            </div>
            <div class="section">
                <label for="username" class="field prepend-icon" style="width: 73%; float: left;">
                    <input type="text" name="userMobile" id="register_userMobile" placeholder="手机号" value="">
                    <label for="mobile number" class="field-icon">
                        <i class="fa fa-phone"></i>
                    </label>
                </label>
                <div class="getCode" style="width: 25%; float: left; margin-left: 3px;">
                    <input type="button" class="getVcode load_register_vercode" value="获取验证码">
                </div>
            </div>
            <div style="clear: both;"></div>
            <div class="section" style="margin-top: 20px;">
                <label for="vercode" class="field prepend-icon">
                    <input type="text" name="vercode" id="register_vercode" placeholder="验证码" value="">
                    <label for="vercode" class="field-icon">
                        <i class="fa fa-legal"></i>
                    </label>
                </label>
            </div>
            <div class="section">
                <div class="submit">
                    <input type="button" class="register-button" value="注册">
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    //60S倒计时
    function countdownTime(time) {
        var current = time - 1;
        if (current == 0) {
            $(".load_register_vercode").val("获取验证码");
            $(".load_register_vercode").removeAttr("disabled");
        } else {
            $(".load_register_vercode").attr("disabled", "disabled");
            $(".load_register_vercode").val(current);
            setTimeout(function () {
                countdownTime(current);
            }, 1000);
        }
    }

    //获取手机验证码
    $(".load_register_vercode").on("click", function () {
        $.getJSON("/user/code?userMobile=" + $("#register_userMobile").val(), function (json) {
            if (json.code == 0) {
                countdownTime(60);
            } else {
                alert(json.message);
            }
        });
    });
    //跳转到注册窗口
    $(".new_left").on('click', function () {
        $("#loginModal").modal("hide");
        $("#registerModal").modal("show");
    });
    //注册窗口显示
    $('#registerModal').on('show.bs.modal', function (e) {
        $("#register_userMobile").val("");
        $("#register_user_email").val("");
        $("#register_password").val("");
        $("#register_vercode").val("");
    });

    //注册
    $('.register-button').on('click', function (e) {
        _common.register();
    });

    //退出登陸
    $(".user-logout").on("click", function () {
        $.getJSON("/user/logout", function () {
            window.location.reload();
        })
    })
</script>