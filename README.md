# CloudPhoto

## Запуск программы

Для запуска программы Вам потребуется *java* 19 версии😊
Если команда *"java --version"* показывает не ту версию, либо выдает ошибку - 
следует установить java версии 19 😋

Чтоб не засорять пространство машины, можно создать специальную директорию для работы
*cloudphoto*.
Пример ниже:
```shell
mkdir /tmp/cloudphoto
cd /tmp/cloudphoto
```
По скольку jar-file у Spring BOOT приложения весит солидно, то гитхаб не может хранить
его в себе, поэтому использовались сторонние хранилки. Можете восползоваться одним из вариантов ниже
 - Скачать jar с [внешнего источника](https://disk.yandex.ru/d/rt_NGRGJyL6Axg)  😎
 - Склонировать репозиторий и собрать jar-file при помощи *maven* 🤥

Следующим пунктом было использование программы при помощи короткого запуска, для этого нам необходимо
зайти в файл при помощи любого удобного редактора *"~/.bashrc"* и в нем прописать следующую строку 
```shell
alias cloudphoto="java -jar /tmp/cloudphoto/cloudphoto-v1.0.jar"
```
После выхода из файла можно 
 - Ребутнуть машину, если вы все делаете на своей\
 - Переподключиться по ssh, если у вас ssh соединение
 - Ну или нормальный вариант:
```shell
source ~/.bashrc
```
Мои поздравления! Программа готова к запуску! Напоминаю, что первым делом нужно запустить *cloudphoto init*
```shell
cloudphoto init
```
Для нормальной работы yandex cloud рекомендуется использовать следующий конфиг
 - access_key= YCAJE6gXg_VEwf-wo-ccXT5GA
 - access_secret_key= YCP68kFWJkgy84jJ71cM5j57UVbxmQ5-MHJtzm7F
 - bucket_name= polypollya-album-bucket
