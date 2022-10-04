<!doctype html>
<html>
    <head>
        <meta charset=utf-8>
        <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/galleria/1.6.1/themes/classic/galleria.classic.min.css" />
        <style>
            .galleria{ width: 960px; height: 540px; background: #000 }
        </style>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/galleria/1.6.1/galleria.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/galleria/1.6.1/themes/classic/galleria.classic.min.js"></script>
    </head>
    <body>
        <div class="galleria">
            <#list photo_content as photo>
            <img src="${photo}" data-title="${photo}">
            </#list>
        </div>
        <p>Вернуться на <a href="index.html">главную страницу</a> фотоархива</p>
        <script>
            (function() {
                Galleria.run('.galleria');
            }());
        </script>
    </body>
</html>