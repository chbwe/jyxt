<table class="table table-bordered">
    <thead>
    <tr>
        <th>#</th>
        <th>类型</th>
        <th>时长</th>
        <th>节数</th>
        <th>价格</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <#list item as data>
    <tr>
        <td>${data.id}</td>
        <td>1/${data.type}</td>
        <td>
            <#if data.times == 60>1</#if>
            <#if data.times == 90>1.5</#if>
            <#if data.times == 120>2</#if>
        </td>
        <th>${data.indexs}</th>
        <td>${data.price}</td>
        <td node-id="${data.id}">
            <a href="#" buy='1' class="btn btn-primary btn-sm">购买</a>
        </td>
    </tr>
    </#list>
    </tbody>
</table>
<script type="application/javascript">
    //点击套餐进行购买
    $("a[buy]").click(function () {
        $("#packagesId").val($(this).parent().attr("node-id"));
        _next();
    });
</script>