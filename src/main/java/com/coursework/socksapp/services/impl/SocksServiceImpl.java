package com.coursework.socksapp.services.impl;

import com.coursework.socksapp.model.*;
import com.coursework.socksapp.services.FileService;
import com.coursework.socksapp.services.SocksService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

@Service
@Data
public class SocksServiceImpl implements SocksService {
    private ObjectMapper objectMapper = new ObjectMapper();

    private Map<Socks, Integer> socksMap = new HashMap<>();
    private List<PostRequest> postRequests = new ArrayList<>();
    private List<History> history = new ArrayList<>();
    @Autowired
    private FileService fileService;

    @PostConstruct
    public void init(){
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        try{
            readFromStorageFile();
            readFromHistoryFile();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public ResponseEntity<String> addSocks(List<PostRequest> newSocks){
        for (PostRequest socks : newSocks){
            if(socks.getQuantity() < 0){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Параметры запроса имеют некорректный формат.");
            }
        }

        for (PostRequest socks : newSocks){
            socksMap.put(socks.getSocks(), socksMap.getOrDefault(socks.getSocks(), 0) + socks.getQuantity());
            history.add(new History(OperationType.POST, LocalDateTime.now(), socks.getQuantity(),
                    socks.getSocks().getSize(), socks.getSocks().getCottonPart(), socks.getSocks().getColor()));
        }
        saveToStorageFile();
        saveToHistoryFile();
        return ResponseEntity.ok("Носки добавлены.");
    }

    @Override
    public ResponseEntity<String> editSocks(PostRequest editedSocks){
        if(socksMap.containsKey(editedSocks.getSocks()) && socksMap.get(editedSocks.getSocks()) > 0){
            if(socksMap.get(editedSocks.getSocks()) >= editedSocks.getQuantity()){
                socksMap.put(editedSocks.getSocks(), socksMap.get(editedSocks.getSocks()) - editedSocks.getQuantity());
                history.add(new History(OperationType.PUT, LocalDateTime.now(), editedSocks.getQuantity(),
                        editedSocks.getSocks().getSize(), editedSocks.getSocks().getCottonPart(), editedSocks.getSocks().getColor()));
                saveToStorageFile();
                saveToHistoryFile();
                return ResponseEntity.ok("Носки ушли со склада.");
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("На складе недостаточно носков такого типа.");
            }
        } else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("На складе нет носков такого типа.");
        }
    }

    @Override
    public int getQuantity(Color color, Size size, int cottonMin, int cottonMax){
        int quantity = 0;
        for(Map.Entry<Socks, Integer> socks : socksMap.entrySet()){
            Socks key = socks.getKey();
            if((color == null || key.getColor() == color) &&
                    (size == null || key.getSize() == size) &&
                    cottonMin <= key.getCottonPart() &&
                    key.getCottonPart() <= cottonMax){
                quantity += socks.getValue();
            }
        }
        return quantity;
    }

    @Override
    public ResponseEntity<String> deleteSocks(PostRequest deletedSocks){
        if(socksMap.containsKey(deletedSocks.getSocks()) && socksMap.get(deletedSocks.getSocks()) > 0){
            if(socksMap.get(deletedSocks.getSocks()) >= deletedSocks.getQuantity()){
                socksMap.put(deletedSocks.getSocks(), socksMap.get(deletedSocks.getSocks()) - deletedSocks.getQuantity());
                history.add(new History(OperationType.DELETE, LocalDateTime.now(), deletedSocks.getQuantity(),
                        deletedSocks.getSocks().getSize(), deletedSocks.getSocks().getCottonPart(), deletedSocks.getSocks().getColor()));
                saveToStorageFile();
                saveToHistoryFile();
                return ResponseEntity.ok("Носки сожжены.");
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("На складе недостаточно носков такого типа.");
            }
        } else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("На складе нет носков такого типа.");
        }
    }


    @Override
    public void readFromStorageFile(){
        String data = fileService.readFromStorageFile();
        try {
            postRequests = objectMapper.readValue(data, new TypeReference<ArrayList<PostRequest>>() {
            });
            for (PostRequest postRequest : postRequests){
                socksMap.put(postRequest.getSocks(), postRequest.getQuantity());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveToStorageFile(){
        try {
            for(Map.Entry<Socks, Integer> socks : socksMap.entrySet()){
                PostRequest postRequest = new PostRequest(socks.getKey(), socks.getValue());
                postRequests.add(postRequest);
            }

            String data = objectMapper.writeValueAsString(postRequests);
            fileService.saveToStorageFile(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void readFromHistoryFile(){

        String data = fileService.readFromHistoryFile();
        try {
            history = objectMapper.readValue(data, new TypeReference<ArrayList<History>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveToHistoryFile(){
        try {
            String data = objectMapper.writeValueAsString(history);
            fileService.saveToHistoryFile(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
