package com.bsib.demo.fileUploader.controller;

import com.bsib.demo.fileUploader.exception.FileException;
import com.bsib.demo.fileUploader.model.FileInfo;
import com.bsib.demo.fileUploader.service.FileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false")
@RestController
@RequestMapping("/")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping(value = "upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
                                                      MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<FileInfo> saveFile(@RequestParam("file")MultipartFile multipartFile, @RequestParam("info") String fileInfo) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            FileInfo fileJson = objectMapper.readValue(fileInfo, FileInfo.class);
            fileService.saveFile(multipartFile, fileJson);
            return new ResponseEntity<>(fileJson, HttpStatus.OK);
        } catch (JsonProcessingException | FileSizeLimitExceededException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
