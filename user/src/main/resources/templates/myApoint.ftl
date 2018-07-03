<div class="container">
    <div class="col-md-3 column-1">
        <div class="list-group">
            <a href="#" class="list-group-item active">我的预约</a>
        </div>
    </div>
    <div class="col-md-9 column-2">
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>预约时间</th>
                <th>上课时间</th>
                <th>教师名称</th>
                <th>教师联系方式</th>
                <th>状态</th>
            </tr>
            </thead>

            <tbody>
            <#if item??>
                <#list item as data>
                <tr>
                    <td>${data.createTime?date!}</td>
                    <td>${data.courseDate?date}（第${data.courseIndex!}节）</td>
                    <td>${data.teacherName!}</td>
                    <td>18668170126</td>
                    <td>
                        <#if  data.courseStatus == -9>已关闭</#if>
                        <#if  data.courseStatus == -1>未付款</#if>
                        <#if  data.courseStatus == 0>预约</#if>
                        <#if  data.courseStatus == 1>已用</#if>
                        <#if  data.courseStatus == 2>失约</#if>
                    </td>
                </tr>
                </#list>
            <#else >
            <tr>
                <td colspan="5">${message}</td>
            </tr>
            </#if>
            </tbody>
        </table>
    </div>
    <div class="clearfix"></div>
</div>