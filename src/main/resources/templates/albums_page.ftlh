<!doctype html>
<html>
    <head>
        <meta charset=utf-8>
        <title>Фотоархив</title>
    </head>
    <body>
        <h1>Фотоархив</h1>
        <ul>
            <#if albums?has_content>
            <#list 0..albums?size-1 as i>
            <li><a href="album${i}.html">${albums[i]}</a></li>
            </#list>
            </#if>
        </ul>
    </body>
</html>