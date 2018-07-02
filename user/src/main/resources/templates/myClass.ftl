<table class="table table-bordered">
    <thead>
    <tr>
        <th>日期</th>
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
    <#if item??>
        <#list item as data>
        <tr>
            <td>${data.weekDay!}</td>
            <td>${data.week!}</td>
            <td>
            ${data.key1!}
                <#if data.key1!?index_of("未上课")!=-1>
                    <a href="javascript:;" onclick="leave(${data.time1!})"><font color="red">请假</font></a>
                </#if>
            </td>
            <td>${data.key2!}
                <#if data.key2!?index_of("未上课")!=-1>
                    <a href="javascript:;" onclick="leave(${data.time2!})"><font color="red">请假</font></a>
                </#if>
            </td>
            <td>${data.key3!}
                <#if data.key3!?index_of("未上课")!=-1>
                    <a href="javascript:;" onclick="leave(${data.time3!})"><font color="red">请假</font></a>
                </#if>
            </td>
            <td>${data.key4!}
                <#if data.key4!?index_of("未上课")!=-1>
                    <a href="javascript:;" onclick="leave(${data.time4!})"><font color="red">请假</font></a>
                </#if>
            </td>
            <td>${data.key5!}
                <#if data.key5!?index_of("未上课")!=-1>
                    <a href="javascript:;" onclick="leave(${data.time5!})"><font color="red">请假</font></a>
                </#if>
            </td>
            <td>
            ${data.key6!}
                <#if data.key6!?index_of("未上课")!=-1>
                    <a href="javascript:;" onclick="leave(${data.time6!})"><font color="red">请假</font></a>
                </#if>
            </td>
            <td>${data.key7!}
                <#if data.key7!?index_of("未上课")!=-1>
                    <a href="javascript:;" onclick="leave(${data.time7!})">
                        <fnt color="red">请假</fnt>
                    </a>
                </#if>

            <td>${data.key8!}
                <#if data.key8!?index_of("未上课")!=-1>
                    <a href="javasript:;" onclick="leave(${data.time8!})"><font color="red">请假</font></a>
                </#if>
            </td>
        </tr>
        </#list>
    <#else >
    <tr>
        <td colspan="10">${message}</td>
    </tr>
    </#if>
    </tbody>
</table>