package ru.itis.cloudlabcloudphoto.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import ru.itis.cloudlabcloudphoto.entity.dto.ConfigMDTO;
import ru.itis.cloudlabcloudphoto.helper.ConfigHelper;

import java.util.Scanner;

@Component
@Command(name = "init", description = "cloudphoto команда инициализации конфига")
@Slf4j
public class InitCommand implements Runnable {

    @Autowired
    private ConfigHelper configHelper;


    @Override
    public void run() {
        System.out.println("Введите ключ доступа: ");
        System.out.println();
        Scanner sc = new Scanner(System.in);
        String accessKey = sc.nextLine();
        System.out.println();
        System.out.println("Введите секретный ключ доступа: ");
        String secretKey = sc.nextLine();
        System.out.println();
        System.out.println("Введите имя бакета: ");
        String bucketName = sc.nextLine();
        configHelper.updateConfigFile(ConfigMDTO
                .builder()
                .accessKey(accessKey)
                .secretKey(secretKey)
                .bucketName(bucketName)
                .build());
        System.out.println("Готово");

    }
}
