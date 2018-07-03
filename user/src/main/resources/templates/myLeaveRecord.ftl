<table class="table table-bordered">
    <thead>
    <tr>
    <tr>
        <th>#</th>
        <th>时间</th>
        <th>课时</th>
        <th>状态</th>
    </tr>
    </thead>

    <tbody>
    <#if item??>
        <#list item as data>
        <tr>
            <td>${data.id}</td>
            <td>${data.date?date!}</td>
            <td>${data.index!}</td>
            <td>
                <#if  data.status == 0>待审批</#if>
                <#if  data.status == 1>已同意</#if>
            </td>
        </tr>
        </#list>
    <#else >
    <tr>
        <td colspan="4">${message}</td>
    </tr>
    </#if>
    </tbody>
</table>
