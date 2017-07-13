package io.feellix.restvideodemo.file;

import java.io.InputStream;

public class DevNullFileStorageRepository implements FileStorageRepository {

    public void storeFile(String filenameWithPath, InputStream io) {
        //nothing simply do not store file
    }
}
