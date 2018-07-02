<table class="table table-bordered">
    <thead>
    <tr>
        <th>时间</th>
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
        <td>
            <#if data.time1!='无'>
                <#if data.show1 ==true>
                    <input type="${inputType}" name="courseId" value="${data.key1}"/>
                </#if>

                <#if data.show1 ==false>
                    <input type="${inputType}" name="courseId" value="${data.key1}" disabled/>
                </#if>
            </#if>
        ${data.time1!}
        </td>
        <td>
            <#if data.time2!='无'>
                <#if data.show2 ==true>
                    <input type="${inputType}" name="courseId" value="${data.key2}"/>
                </#if>
                <#if data.show2 ==false>
                    <input type="${inputType}" name="courseId" value="${data.key2}" disabled/>
                </#if>
            </#if>
        ${data.time2!}
        </td>
        <td>
            <#if data.time3!='无'>

                <#if data.show3 ==true>
                <input type="${inputType}" name="courseId" value="${data.key3}"/>
                </#if>
                <#if data.show3 ==false>
                    <input type="${inputType}" name="courseId" value="${data.key3}" disabled/>
                </#if>
            </#if>
        ${data.time3!}
        </td>
        <td>
            <#if data.time4!='无'>
                <#if data.show4 ==true>
                    <input type="${inputType}" name="courseId" value="${data.key4}"/>
                </#if>
                <#if data.show4 ==false>
                    <input type="${inputType}" name="courseId" value="${data.key4}" disabled/>
                </#if>
            </#if>
        ${data.time4!}
        </td>
        <td>
            <#if data.time5!='无'>
                <#if data.show5 ==true>
                    <input type="${inputType}"  name="courseId" value="${data.key5}"/>
                </#if>
                <#if data.show5 ==false>
                    <input type="${inputType}"  name="courseId" value="${data.key5}" disabled/>
                </#if>
            </#if>
        ${data.time5!}
        </td>
        <td>
            <#if data.time6!='无'>
                <#if data.show6 ==true>
                    <input type="${inputType}" name="courseId" value="${data.key6}"/>
                </#if>
                <#if data.show6 ==false>
                    <input type="${inputType}" name="courseId" value="${data.key6}" disabled/>
                </#if>
            </#if>
        ${data.time6!}
        </td>
        <td>
            <#if data.time7!='无'>
                <#if data.show7 ==true>
                    <input type="${inputType}" name="courseId" value="${data.key7}"/>
                </#if>
                <#if data.show7 ==false>
                    <input type="${inputType}" name="courseId" value="${data.key7}" disabled/>
                </#if>
            </#if>
        ${data.time7!}
        </td>
        <td>
            <#if data.time8!='无'>
                <#if data.show8 ==true>
                    <input type="${inputType}" name="courseId" value="${data.key8}"/>
                </#if>
                <#if data.show8 ==false>
                    <input type="${inputType}" name="courseId" value="${data.key8}" disabled/>
                </#if>
            </#if>
        ${data.time8!}
        </td>
    </tr>
    </#list>
    </tbody>
</table>