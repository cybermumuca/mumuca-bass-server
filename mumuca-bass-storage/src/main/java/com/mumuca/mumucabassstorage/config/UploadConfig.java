package com.mumuca.mumucabassstorage.config;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@ConfigurationProperties(prefix = "upload")
// TODO: fix private properties
public class UploadConfig {
    private String directory;
    private String tmpDirectory;

    @PostConstruct
    public void init() {
        createDirectoryIfNotExists(directory);
        createDirectoryIfNotExists(tmpDirectory);
    }

    private void createDirectoryIfNotExists(String path) {
        File dir = new File(path);

        if (!dir.exists()) {
            boolean created = dir.mkdirs();

            if (!created) {
                throw new RuntimeException("Could not create directory: " + path);
            }
        }
    }

    public String getUploadDir() {
        return directory;
    }

    public String getTmpDirectory() {
        return tmpDirectory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public void setTmpDirectory(String tmpDirectory) {
        this.tmpDirectory = tmpDirectory;
    }
}
