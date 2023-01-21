package com.coursework.socksapp.services;

import com.coursework.socksapp.model.Color;
import com.coursework.socksapp.model.History;
import com.coursework.socksapp.model.PostRequest;
import com.coursework.socksapp.model.Size;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SocksService {
    ResponseEntity<String> addSocks(List<PostRequest> newSocks);

    ResponseEntity<String> editSocks(PostRequest editedSocks);

    int getQuantity(Color color, Size size, int cottonMin, int cottonMax);

    ResponseEntity<String> deleteSocks(PostRequest deletedSocks);

    void readFromStorageFile();

    void saveToStorageFile();

    void readFromHistoryFile();

    void saveToHistoryFile();
}
