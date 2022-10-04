package ru.itis.cloudlabcloudphoto.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfigMDTO {
    private String accessKey;
    private String secretKey;
    private String bucketName;
}
