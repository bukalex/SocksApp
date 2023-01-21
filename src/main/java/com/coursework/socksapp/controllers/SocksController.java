package com.coursework.socksapp.controllers;

import com.coursework.socksapp.model.Color;
import com.coursework.socksapp.model.PostRequest;
import com.coursework.socksapp.model.Size;
import com.coursework.socksapp.services.SocksService;
import org.springframework.beans.factory.annotation.Autowired;
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
        socksService.addSocks(newSocks);
        return ResponseEntity.ok("Носки добавлены.");
    }

    @PutMapping
    public ResponseEntity<String> editSocks(@RequestBody PostRequest editedSocks){
        return ResponseEntity.ok(socksService.editSocks(editedSocks));
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
        return ResponseEntity.ok(socksService.deleteSocks(deletedSocks));
    }
}
