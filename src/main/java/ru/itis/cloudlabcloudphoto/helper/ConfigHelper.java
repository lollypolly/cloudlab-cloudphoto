package ru.itis.cloudlabcloudphoto.helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.ini4j.Ini;
import org.springframework.stereotype.Component;
import ru.itis.cloudlabcloudphoto.entity.dto.ConfigMDTO;

import java.io.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConfigHelper {

    private static final String CONFIG_PATH = System.getProperty("user.home") + "/.config/cloudphoto/cloudphotorc";
    private Ini ini;

    public void updateConfigFile(ConfigMDTO configMDTO){
        //todo create bucket with given name if not exists
        try {
            FileUtils.forceMkdir(new File(CONFIG_PATH));
            File file =new File(CONFIG_PATH+"/config.ini");
            file.createNewFile();
            ini = new Ini(file);
            ini.put("AWS","accessKey",configMDTO.getAccessKey());
            ini.put("AWS","secretKey",configMDTO.getSecretKey());
            ini.put("AWS","bucketName",configMDTO.getBucketName());
            ini.store(new FileOutputStream(CONFIG_PATH+"/config.ini"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }
    public String getParamFromIniAWSSection(String optionName){
        return getParamFromIni("AWS",optionName);
    }

    public String getParamFromIni(String sectionName,String optionName){
        try(InputStream inputStream = new FileInputStream(CONFIG_PATH+"/config.ini")){
            return new Ini(inputStream).get(sectionName,optionName);
        }catch (IOException e){
            throw new IllegalStateException("конфиг не обнаружен. Выполните команду *cloudphoto init*",e);
        }
    }


}
