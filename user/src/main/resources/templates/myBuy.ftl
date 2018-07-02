<div class="container">
    <div class="col-md-3 column-1">
        <div class="list-group">
            <a href="#" class="list-group-item active">我的购买</a>
        </div>
    </div>
    <div class="col-md-9 column-2">
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>购买时间</th>
                <th>套餐类型</th>
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
                    <td>1/${data.packageType}（ 已上课${data.studentIndex}节/总共${data.packageIndex}节）</td>
                    <td>${data.teacherName!}</td>
                    <td>18668170126</td>
                    <td>
                        <#if  data.status == -1>已关闭</#if>
                        <#if  data.status == 0>未付款</#if>
                        <#if  data.status == 1>付款成功</#if>
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