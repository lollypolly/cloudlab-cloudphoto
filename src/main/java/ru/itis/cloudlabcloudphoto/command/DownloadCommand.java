package ru.itis.cloudlabcloudphoto.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import ru.itis.cloudlabcloudphoto.service.PhotoService;

import java.nio.file.Path;

@Component
@CommandLine.Command(name = "download", description = "cloudphoto команда выгрузки с облака")
@RequiredArgsConstructor
@Slf4j
public class DownloadCommand implements Runnable {

    private final PhotoService photoService;
    @Getter
    @Setter
    @CommandLine.Option(names = "--album", description = "имя альбома, с которого хотите скачать фотографии", required = true)
    private String albumName;
    @Getter
    @Setter
    @CommandLine.Option(names = "--path", description = "путь, куда скачать, или же фотографии будут скачаны в домашнюю директорию")
    private String photosPath;

    @Override
    public void run() {
        photosPath = photosPath == null ? System.getProperty("user.dir") : photosPath;
        photoService.downloadPhotoListByAlbumName(albumName, Path.of(photosPath).normalize().toAbsolutePath().toFile());
    }
}
