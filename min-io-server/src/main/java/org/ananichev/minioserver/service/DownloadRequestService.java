package org.ananichev.minioserver.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.ananichev.minioserver.model.DownloadRequest;
import org.ananichev.minioserver.service.repository.DownloadRequestRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DownloadRequestService {

    private final DownloadRequestRepository downloadRequestRepository;

    @Transactional
    public void save(String objectName) {

        DownloadRequest downloadRequest = new DownloadRequest();
        downloadRequest.setObjectName(objectName);

        downloadRequestRepository.save(downloadRequest);
    }
}
