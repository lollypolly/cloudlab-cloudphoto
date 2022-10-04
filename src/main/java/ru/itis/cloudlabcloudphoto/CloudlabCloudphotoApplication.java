package ru.itis.cloudlabcloudphoto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import picocli.CommandLine;
import ru.itis.cloudlabcloudphoto.command.CommandResolver;

import java.util.Arrays;

@Slf4j
@SpringBootApplication
public class CloudlabCloudphotoApplication implements CommandLineRunner {

    @Autowired
    private CommandLine.IFactory factory;
    @Autowired
    private CommandResolver commandResolver;

    @Override
    public void run(String... args) {
        System.out.println(Arrays.toString(args));
        int exitCode = new CommandLine(commandResolver,factory).execute(args);
        System.out.println(exitCode);
    }
    public static void main(String[] args) {
        SpringApplication.run(CloudlabCloudphotoApplication.class, args);
    }

}
