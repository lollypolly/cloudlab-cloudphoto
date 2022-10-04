package ru.itis.cloudlabcloudphoto.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.MultipleFileDownload;
import com.amazonaws.services.s3.transfer.TransferManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.itis.cloudlabcloudphoto.helper.ConfigHelper;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class PhotoService {
    private final AmazonS3 yandexS3;
    private final ConfigHelper configHelper;
    private final TransferManager transferManager;

    public void savePhotoList(List<File> fileList, String albumName) {
        fileList.forEach(file -> {
            try {
                transferManager.upload(configHelper.getParamFromIniAWSSection("bucketName"), albumName + "/" + file.getName(), file)
                        .waitForCompletion();
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        });
    }


    public List<String> getPhotoKeyListByAlbumName(String albumName) {
        return yandexS3.listObjects(configHelper.getParamFromIniAWSSection("bucketName"), albumName).getObjectSummaries().stream()
                .map(S3ObjectSummary::getKey).toList();
    }

    public void downloadPhotoListByAlbumName(String albumName, File destinationDir) {
        MultipleFileDownload download =
                transferManager.downloadDirectory(configHelper
                        .getParamFromIniAWSSection("bucketName"), albumName + "/", destinationDir);
        try {
            download.waitForCompletion();
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    public void deleteAllPhotosInAlbum(String albumName) {
        yandexS3.deleteObjects(new DeleteObjectsRequest(configHelper.getParamFromIniAWSSection("bucketName"))
                .withKeys(getPhotoKeyListByAlbumName(albumName).toArray(new String[1])));
    }

    public void deletePhotoInAlbum(String albumName,String photoName){
        String photoKey =getPhotoKeyListByAlbumName(albumName).stream()
                .filter(photoKeys->photoKeys.equals(albumName+"/"+photoName))
                .findAny()
                .orElseThrow(()->new IllegalStateException("нет фотографий"));
        yandexS3.deleteObject("album-bucket",photoKey);
    }
}
