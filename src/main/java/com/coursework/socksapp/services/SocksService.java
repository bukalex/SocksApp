package com.coursework.socksapp.services;

import com.coursework.socksapp.model.Color;
import com.coursework.socksapp.model.PostRequest;
import com.coursework.socksapp.model.Size;

import java.util.List;

public interface SocksService {
    void addSocks(List<PostRequest> newSocks);

    String editSocks(PostRequest editedSocks);

    int getQuantity(Color color, Size size, int cottonMin, int cottonMax);

    String deleteSocks(PostRequest deletedSocks);
}
