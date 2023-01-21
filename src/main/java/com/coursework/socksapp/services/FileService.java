package com.coursework.socksapp.services;

import java.io.File;

public interface FileService {
    void cleanStorageFile();

    void saveToStorageFile(String data);

    String readFromStorageFile();

    File getStorageFile();

    void cleanHistoryFile();

    void saveToHistoryFile(String data);

    String readFromHistoryFile();

    File getHistoryFile();
}
