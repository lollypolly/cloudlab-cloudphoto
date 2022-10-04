package ru.itis.cloudlabcloudphoto.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import ru.itis.cloudlabcloudphoto.helper.ConfigHelper;

@Configuration
public class AWSConfig {

  @Lazy
  @Bean
  public AWSCredentials basicAWSCredentials(ConfigHelper configHelper) {
    return new BasicAWSCredentials(configHelper.getParamFromIniAWSSection("accessKey"),
            configHelper.getParamFromIniAWSSection("secretKey"));
  }

  @Lazy
  @Bean
  public AmazonS3 amazonS3(AWSCredentials basicAWSCredentials) {
    return AmazonS3ClientBuilder.standard()
        .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
        .withEndpointConfiguration(
            new AmazonS3ClientBuilder.EndpointConfiguration(
                "storage.yandexcloud.net", "ru-central1"))
        .build();
  }

  @Bean
  public ObjectMapper objectMapper(){
    return new ObjectMapper();
  }

  @Lazy
  @Bean
  public TransferManager transferManager(AmazonS3 amazonS3){
    return TransferManagerBuilder.standard().withS3Client(amazonS3).build();
  }
}
