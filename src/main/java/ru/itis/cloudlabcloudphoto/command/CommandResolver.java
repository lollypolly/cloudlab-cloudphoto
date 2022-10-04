package ru.itis.cloudlabcloudphoto.command;

import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;


@Component
@Command(name = "cloudphoto",
        subcommands = {InitCommand.class,
                ListCommand.class,
                UploadCommand.class,
                DeleteCommand.class,
                DownloadCommand.class,
                MkSiteCommand.class
        },description = "Base command for cloudphoto")
public class CommandResolver {

}
