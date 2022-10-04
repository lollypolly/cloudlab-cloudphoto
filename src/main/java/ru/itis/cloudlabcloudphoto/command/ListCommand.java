package ru.itis.cloudlabcloudphoto.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import ru.itis.cloudlabcloudphoto.entity.model.Album;
import ru.itis.cloudlabcloudphoto.service.AlbumService;
import ru.itis.cloudlabcloudphoto.service.PhotoService;

import java.util.List;
import java.util.Optional;

@Component
@Command(name = "list", description = "cloudphoto команда показывает содержимое альбома")
@RequiredArgsConstructor
public class ListCommand implements Runnable {

    private final AlbumService albumService;
    private final PhotoService photoService;
    @Getter
    @Setter
    @Option(names = "--album", description = "имя альбома")
    private String album;

    @Override
    public void run() {
        Optional.ofNullable(album)
                .ifPresentOrElse(
                        albumName -> {
                            List<String> photoKeys = photoService.getPhotoKeyListByAlbumName(albumService.getOrThrow(albumName).getName());
                            if (photoKeys.isEmpty()) {
                                throw new IllegalStateException("Нет фотографий в альбоме");
                            }
                            photoKeys.forEach(System.out::println);
                        },
                        () -> {
                            List<Album> albumList = albumService.getAllAlbumsDto().getAlbumList();
                            if (albumList.isEmpty()) {
                                throw new IllegalStateException("Нет такого альбома");
                            }
                            albumList.forEach(System.out::println);
                        });

    }
}
