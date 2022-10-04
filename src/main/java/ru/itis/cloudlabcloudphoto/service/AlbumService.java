package ru.itis.cloudlabcloudphoto.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.itis.cloudlabcloudphoto.entity.dto.AlbumDtoList;
import ru.itis.cloudlabcloudphoto.entity.model.Album;
import ru.itis.cloudlabcloudphoto.helper.ConfigHelper;
import ru.itis.cloudlabcloudphoto.util.ObjectMapperUtil;

import java.util.HashSet;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AlbumService {
    private final AmazonS3 yandexS3;
    private final ObjectMapperUtil objectMapperUtil;

    private final PhotoService photoService;

    private final ConfigHelper configHelper;


    public Album saveAlbum(String name) {
        AlbumDtoList albumDtoList = getAllAlbumsDto();
        Album newAlbum = Album.builder().name(name).build();
        albumDtoList.getAlbumList().add(newAlbum);
        albumDtoList.setAlbumList(new HashSet<>(albumDtoList.getAlbumList()).stream().toList());
        yandexS3.putObject(configHelper.getParamFromIniAWSSection("bucketName"), "albums.json", objectMapperUtil.writeValueAsString(albumDtoList));
        return newAlbum;
    }

    public Album getOrThrow(String albumName) {
        return getAllAlbumsDto().getAlbumList().stream()
                .filter(album -> album.getName().equals(albumName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Альбома с таким названием не найдено"));
    }

    public AlbumDtoList getAllAlbumsDto() {
        AlbumDtoList albumDtoList = AlbumDtoList.builder().build();
        try {
            S3Object s3object = yandexS3.getObject(configHelper.getParamFromIniAWSSection("bucketName"), "albums.json");
            S3ObjectInputStream inputStream = s3object.getObjectContent();
            albumDtoList = objectMapperUtil.readValue(inputStream, AlbumDtoList.class);
            log.info("all albums {}", albumDtoList);
            return albumDtoList;
        } catch (AmazonS3Exception e) {
            yandexS3.putObject(configHelper.getParamFromIniAWSSection("bucketName"), "albums.json", objectMapperUtil.writeValueAsString(albumDtoList));
            return albumDtoList;
        }
    }

    public void deleteAlbum(String name) {
        photoService.deleteAllPhotosInAlbum(name);
        AlbumDtoList albumDtoList = getAllAlbumsDto();
        Album newAlbum = Album.builder().name(name).build();
        albumDtoList.getAlbumList().remove(newAlbum);
        yandexS3.putObject(configHelper.getParamFromIniAWSSection("bucketName"), "albums.json", objectMapperUtil.writeValueAsString(albumDtoList));
    }
}
