package org.ananichev.minioserver.controller;

import lombok.AllArgsConstructor;
import org.ananichev.minioserver.dto.BucketItemDto;
import org.ananichev.minioserver.service.MinIOService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
public class ItemController {

    private final MinIOService minIOService;

    @GetMapping
    public ResponseEntity<List<String>> helloWorld() {
        List<String> bucketNames = minIOService.getBucketList();

        return new ResponseEntity<>(bucketNames, HttpStatus.OK);
    }

    @GetMapping(path = "/items")
    public ResponseEntity<List<BucketItemDto>> getObjects(
            @RequestParam(required = false, defaultValue = "") String objectName,
            @RequestParam(required = false, defaultValue = "objectName") String sortField,
            @RequestParam(required = false, defaultValue = "DESC") String sortDirection
    ) {

        return new ResponseEntity<>(minIOService.getBucketItems(objectName, sortField, sortDirection), HttpStatus.OK);
    }

    @GetMapping(value = "/item/{objectName}/download")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String objectName) throws IOException {

        byte[] content = minIOService.downloadFile(objectName);

        return ResponseEntity.ok()
                .contentLength(content.length)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + objectName)
                .body(content);
    }

    @PostMapping(value = "/item/upload")
    public void uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        minIOService.uploadFile(multipartFile);
    }
}
