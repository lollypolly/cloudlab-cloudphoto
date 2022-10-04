package ru.itis.cloudlabcloudphoto.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import ru.itis.cloudlabcloudphoto.service.AlbumService;
import ru.itis.cloudlabcloudphoto.service.PhotoService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@CommandLine.Command(name = "upload", description = "cloudphoto команда загрузки на облако")
@RequiredArgsConstructor
@Slf4j
public class UploadCommand implements Runnable {

    private final PhotoService photoService;
    private final AlbumService albumService;
    private final Tika tika = new Tika();
    @Getter
    @Setter
    @CommandLine.Option(names = "--album", description = "имя альбома, куда загрузить", required = true)
    private String albumName;
    @Getter
    @Setter
    @CommandLine.Option(names = "--path", description = "путь откуда взять фотографии")
    private String photosPath;

    @Override
    public void run() {

        photosPath = photosPath == null ? System.getProperty("user.dir") : photosPath;
        List<File> fileList = Arrays.stream(Objects.requireNonNull(getAlbumDirectory(photosPath).listFiles())).toList();
        albumService.saveAlbum(albumName);
        photoService.savePhotoList(fileList, albumName);
    }

    public File getAlbumDirectory(String path) {
        File albumDirectory = new File(path);
        validateAlbumPath(albumDirectory);
        return albumDirectory;
    }

    private void validateAlbumPath(File photosDirectorty) {
        if (!photosDirectorty.isDirectory()) {
            throw new IllegalStateException("Путь до фотографий должен быть директорией");
        }
        if (Objects.isNull(photosDirectorty.listFiles())) {
            throw new IllegalStateException("это не директория с файлами");
        }
        if (Objects.requireNonNull(photosDirectorty.listFiles()).length == 0) {
            throw new IllegalStateException("директория пуста");
        }
        Arrays.stream(Objects.requireNonNull(photosDirectorty.listFiles())).forEach(photoFile -> {
            if (!isImageMimeType(photoFile)) {
                throw new IllegalStateException("некоторые файлы - не формата jpeg/jpg, пожалуйста, удалите лишние");
            }
        });

    }

    public boolean isImageMimeType(File src) {
        try (FileInputStream fis = new FileInputStream(src)) {
            String mime = tika.detect(fis, src.getName());
            return mime.contains("/")
                    && mime.split("/")[0].equalsIgnoreCase("image");
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
