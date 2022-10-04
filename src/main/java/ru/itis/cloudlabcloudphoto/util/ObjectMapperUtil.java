package ru.itis.cloudlabcloudphoto.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class ObjectMapperUtil {
    private  final ObjectMapper objectMapper;

    public String writeValueAsString(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }
    public <T> T readValue(InputStream inputStream,Class<T> clazz){
        try {
            return objectMapper.readValue(inputStream,clazz);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
