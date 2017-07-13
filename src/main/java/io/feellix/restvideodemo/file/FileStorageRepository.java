package io.feellix.restvideodemo.file;

import java.io.InputStream;

public interface FileStorageRepository {

    void storeFile(String filenameWithPath, InputStream io);
}
