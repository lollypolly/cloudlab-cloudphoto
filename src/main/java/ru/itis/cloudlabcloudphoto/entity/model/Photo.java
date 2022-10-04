package ru.itis.cloudlabcloudphoto.entity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Photo {
    @NotNull
    private String name;
    @NotNull
    private String albumName;

}
