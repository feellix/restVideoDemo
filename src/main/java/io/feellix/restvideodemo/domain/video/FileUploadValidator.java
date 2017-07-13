package io.feellix.restvideodemo.domain.video;

import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class FileUploadValidator {

    public boolean isVideo(InputStream inputStream) {
        return false;
    }
}
