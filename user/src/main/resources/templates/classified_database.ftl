<div class="container">
    <div class="col-md-3 column-11">
    </div>
    <div class="col-md-9 column-15">
        <h6>预约试听</h6>
        <p class="m_5"> 预约试听课程需要100块钱预约费用</p>
        <!-- 列表 -->
        <div class="panel-body_3">
        <#list item as data>
            <div class="pull-left" style="margin:0 5px;">
                <a class="thumbnail">
                    <img src="${data.userImg!}" width="60" height="60">
                </a>
            </div>
            <div class="name">
                <span>${data.userNickname!}</span>
                <p>${data.userSchool!}</p>
            </div>
            <div class="contact_btn" node-id="${data.id!}">
                <a  class="add_btn add_btn1 user_reservation" data-toggle="modal">预约</a>
                <a  class="add_btn add_btn1 user_buy" data-toggle="modal">购买</a>
            </div>
            <div class="clearfix"></div>
            <div style="padding-top: 5px;"><p>${data.userContent!}</p></div>
            <div class="clearfix"></div>
            <hr>
        </#list>
        </div>
        <!-- 列表 -->

        <!-- 自定义分页-->
        <div class="c-pagination">
            <section class="pagination_1">
                <ul class="pagination m_7 pull-left">
                <#if 1 != currentPage>
                    <li><a class="next page-numbers" href="javascript:;" onclick="load(${currentPage -1})">上一页</a>
                    </li>
                </#if>
                <#list page as item>
                    <li>
                        <a href="javascript:;" onclick="load(${item})">
                            <span class="page-numbers <#if item == currentPage>current</#if>">${item}</span>
                        </a>
                    </li>
                </#list>
                <#if totalPage != currentPage>
                    <li><a class="next page-numbers" onclick="load(${currentPage +1})">下一页</a></li>
                </#if>
                </ul>
                <div class="clearfix"></div>
            </section>
            <!-- 自定义分页-->
        </div>
    </div>
</div>
<script>
    //预约
    $(".user_reservation").on("click", function () {
        startReservation("reservation", $(this).parent().attr("node-id"));
    });
    //购买
    $(".user_buy").on("click", function () {
        startReservation("buy", $(this).parent().attr("node-id"));
    });
    //启动预约购买页面
    function startReservation(type, id) {
        $("#packagesId").val("");
        $.getJSON("/user/valid", function (json) {
            if (json.code == 0 && json.data) {
                $("#applayaaa").modal("show");
                $("#reservationType").val(type);
                $("#teacherId").val(id);
                if (type == "buy") {
                    $("#tablelistsetp>li:eq(0)").show();
                    step = 0, minStep = 1;
                } else {
                    $("#tablelistsetp>li:eq(0)").hide();
                    step = 1, minStep = 2;
                }
                _next();
            } else {
                $("#applayaaa").modal("hide");
                $("#loginModal").modal("show");
            }
        });
    }
</script>