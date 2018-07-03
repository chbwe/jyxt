<div class="container">
    <div class="col-md-3 article">
        <section class="slider_1">
            <div class="flexslider">
                <ul class="slides">
                <#list item as c>
                    <li>
                        <div class="article_box">
                            <div class="article_img">
                                <img src="${c.userImg!}" class="img-responsive" alt=""/>
                            </div>
                            <h3>${c.userNickname!}</h3>
                            <p>${c.userContent!}</p>
                            <div class="clearfix"></div>
                        </div>
                    </li>
                </#list>
                </ul>
            </div>
        </section>
    </div>

    <div class="col-md-9 column-15">
        <div class="article_box1">

        <#list item as data>
            <article class="article_box2">
                <div class="col-md-6 post-content_box">
                    <div class="post_format_content">
                        <span class="post-meta-likes"></span>
                        <img src="${data.userImg!}" class="img-responsive" alt=""/>
                    </div>
                </div>
                <div class="col-md-6 post-content_box1">
                    <h3 class="post-title_1">${data.userNickname!}</h3>
                    <div class="post-content clearfix">
                        <p>${data.userContent!}</p>
                    </div>
                    <a class="post_content_readmore" href="javascript:;" onclick="loadDetail(${data.id})">详情<i
                            class="fa fa-chevron-right"></i></a>
                    <div class="clearfix"></div>
                </div>
                <div class="clearfix"></div>
            </article>
        </#list>
        </div>

        <!-- 自定义分页-->
        <div class="c-pagination">
            <section class="pagination_1">
                <ul class="pagination m_7 pull-left">
                <#if 1 != currentPage>
                    <li><a class="next page-numbers" href="javascript:;" onclick="load(${currentPage -1})">上一页</a></li>
                </#if>
                <#list page as item>
                    <li>
                        <a href="javascript:;" onclick="load(${item})">
                            <span class="page-numbers <#if item == currentPage>current</#if>">${item}</span>
                        </a>
                    </li>
                </#list>
                <#if totalPage != currentPage>
                    <li><a class="next page-numbers" href="javascript:;" onclick="load(${currentPage +1})">下一页</a></li>
                </#if>
                </ul>
                <div class="clearfix"></div>
            </section>
        </div>
    </div>
    <div class="clearfix"></div>
</div>
<script type="text/javascript">
    function loadDetail(teacherId) {
        $(".about_top").load("/teacher/detail?teacherId=" + teacherId, function () {
            $('.flexslider').flexslider({
                animation: "slide",
                start: function (slider) {
                    $('body').removeClass('loading');
                }
            });
        });
    }
</script>