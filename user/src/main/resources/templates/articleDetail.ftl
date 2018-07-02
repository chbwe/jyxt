<div class="container">
    <div class="col-md-3 article">
        <section class="slider_1">
            <div class="flexslider">
                <ul class="slides">
                    <li>
                        <div class="article_box">
                            <div class="article_img">
                                <img src="${teacher.userImg!}" class="img-responsive" alt=""/>
                            </div>
                            <h3>${teacher.userNickname}</h3>
                            <p>${teacher.userContent}</p>
                            <div class="clearfix"></div>
                        </div>
                    </li>
                </ul>
            </div>
        </section>
    </div>
    <div class="col-md-9 column-15">
        <h6>${teacher.userNickname}</h6>
        <img src="${teacher.userImg!}" class="img-responsive" alt="">
        <div class="article_desc">${teacher.userContent}</p></div>
        <div class="clearfix"></div>
        <table class="table mb30">
            <thead>
            <tr>
                <th>星期</th>
                <th>第一节课</th>
                <th>第二节课</th>
                <th>第三节课</th>
                <th>第四节课</th>
                <th>第五节课</th>
                <th>第六节课</th>
                <th>第七节课</th>
                <th>第八节课</th>
            </tr>
            </thead>
            <tbody>
            <#list item as data>
            <tr>
                <td>${data.weekDay!}</td>
                <td>${data.time1!}</td>
                <td>${data.time2!}</td>
                <td>${data.time3!}</td>
                <td>${data.time4!}</td>
                <td>${data.time5!}</td>
                <td>${data.time6!}</td>
                <td>${data.time7!}</td>
                <td>${data.time8!}</td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>
</div>