package com.coursework.socksapp.controllers;

import com.coursework.socksapp.model.Color;
import com.coursework.socksapp.model.PostRequest;
import com.coursework.socksapp.model.Size;
import com.coursework.socksapp.services.SocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/socks")
public class SocksController {
    @Autowired
    private SocksService socksService;

    @PostMapping
    public ResponseEntity<String> addSocks(@RequestBody List<PostRequest> newSocks){
        if(newSocks == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Параметры запроса имеют некорректный формат.");
        }
        return socksService.addSocks(newSocks);
    }

    @PutMapping
    public ResponseEntity<String> editSocks(@RequestBody PostRequest editedSocks){
        if(editedSocks == null || editedSocks.getQuantity() < 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Параметры запроса имеют некорректный формат.");
        }
        return socksService.editSocks(editedSocks);
    }

    @GetMapping
    public ResponseEntity<String> getSocks(@RequestParam(required = false) Color color,
                                           @RequestParam(required = false) Size size,
                                           @RequestParam(defaultValue = "0") int cottonMin,
                                           @RequestParam(defaultValue = "100") int cottonMax){
        return ResponseEntity.ok(String.valueOf(socksService.getQuantity(color, size, cottonMin, cottonMax)));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteSocks(@RequestBody PostRequest deletedSocks){
        if(deletedSocks == null || deletedSocks.getQuantity() < 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Параметры запроса имеют некорректный формат.");
        }
        return socksService.deleteSocks(deletedSocks);
    }
}
