<table class="table table-bordered">
    <thead>
    <tr>
    <tr>
        <th>#</th>
        <th>停课开始日期</th>
        <th>停课截至日期</th>
        <th>提交时间</th>
        <th>状态</th>
    </tr>
    </thead>

    <tbody>
    <#if item??>
        <#list item as data>
        <tr>
            <td>${data.id}</td>
            <td>${data.startTime?date}</td>
            <td>${data.endTime?date}</td>
            <td>${data.createTime?date}</td>
            <td>
                <#if  data.status == 0>待审批</#if>
                <#if  data.status == 1>已同意</#if>
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
