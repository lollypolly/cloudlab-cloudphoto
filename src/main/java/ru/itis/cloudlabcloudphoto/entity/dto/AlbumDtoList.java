package ru.itis.cloudlabcloudphoto.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.cloudlabcloudphoto.entity.model.Album;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlbumDtoList {
    @Builder.Default
    List<Album> albumList = new ArrayList<>();
}
