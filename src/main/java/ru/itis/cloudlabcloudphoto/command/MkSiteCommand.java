package ru.itis.cloudlabcloudphoto.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import ru.itis.cloudlabcloudphoto.service.SiteService;

@Component
@CommandLine.Command(name = "mksite", description = "cloudphoto команда создания сайта со всеми фотографиями")
@RequiredArgsConstructor
public class MkSiteCommand implements Runnable {

    private final SiteService siteService;

    @Override
    public void run() {
        System.out.println(siteService.getAlbumWebsiteUrl());
    }
}
