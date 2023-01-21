package com.coursework.socksapp.services.impl;

import com.coursework.socksapp.services.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileServiceImpl implements FileService {
    @Value("${path.to.storage.json}")
    private String storagePath;
    @Value("${name.of.storage.json}")
    private String storageName;
    @Value("${path.to.history.json}")
    private String historyPath;
    @Value("${name.of.history.json}")
    private String historyName;

    @Override
    public void cleanStorageFile(){
        try {
            Path path = Path.of(storagePath, storageName);
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveToStorageFile(String data){
        try {
            cleanStorageFile();
            Files.writeString(Path.of(storagePath, storageName), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String readFromStorageFile(){
        try {
            return Files.readString(Path.of(storagePath, storageName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public File getStorageFile(){
        return new File(storagePath +"/"+ storageName);
    }


    @Override
    public void cleanHistoryFile(){
        try {
            Path path = Path.of(historyPath, historyName);
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveToHistoryFile(String data){
        try {
            cleanHistoryFile();
            Files.writeString(Path.of(historyPath, historyName), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String readFromHistoryFile(){
        try {
            return Files.readString(Path.of(historyPath, historyName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public File getHistoryFile(){
        return new File(historyPath +"/"+ historyName);
    }
}
