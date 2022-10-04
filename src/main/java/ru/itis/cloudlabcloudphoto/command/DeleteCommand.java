package ru.itis.cloudlabcloudphoto.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import ru.itis.cloudlabcloudphoto.service.AlbumService;
import ru.itis.cloudlabcloudphoto.service.PhotoService;

import java.util.Objects;

@Component
@CommandLine.Command(name = "delete", description = "cloudphoto команда удаления")
@RequiredArgsConstructor
@Slf4j
public class DeleteCommand implements Runnable {

    @Getter
    @Setter
    @CommandLine.Option(names = "--album", description = "имя альбома, который хотите удалить или в котором хотите удалить какую-либо фотографию", required = true)
    private String albumName;
    @Getter
    @Setter
    @CommandLine.Option(names = "--path", description = "укажите директорию")
    private String photoPath;

    private final AlbumService albumService;
    private final PhotoService photoService;

    @Override
    public void run() {
        if (Objects.isNull(photoPath)) {
            albumService.deleteAlbum(albumName);
        } else {
            photoService.deletePhotoInAlbum(albumName, photoPath);
        }
    }
}
