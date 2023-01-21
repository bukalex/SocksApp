package com.coursework.socksapp.controllers;

import com.coursework.socksapp.services.FileService;
import com.coursework.socksapp.services.SocksService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;
    @Autowired
    private SocksService socksService;

    @GetMapping("/export_storage")
    public ResponseEntity<InputStreamResource> downloadStorageFile() throws FileNotFoundException {
        File file = fileService.getStorageFile();
        if (file.exists()){
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Storage.json\"")
                    .body(resource);
        } else{
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "/import_storage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadStorageFile(@RequestParam MultipartFile file){
        fileService.cleanStorageFile();
        File recipeFile = fileService.getStorageFile();
        try(FileOutputStream fos = new FileOutputStream(recipeFile)){
            IOUtils.copy(file.getInputStream(), fos);
            socksService.readFromStorageFile();
            return ResponseEntity.ok().build();
        } catch (IOException e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/export_history")
    public ResponseEntity<InputStreamResource> downloadHistoryFile() throws FileNotFoundException {
        File file = fileService.getHistoryFile();
        if (file.exists()){
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"History.json\"")
                    .body(resource);
        } else{
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "/import_history", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadHistoryFile(@RequestParam MultipartFile file){
        fileService.cleanHistoryFile();
        File recipeFile = fileService.getHistoryFile();
        try(FileOutputStream fos = new FileOutputStream(recipeFile)){
            IOUtils.copy(file.getInputStream(), fos);
            socksService.readFromHistoryFile();
            return ResponseEntity.ok().build();
        } catch (IOException e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
