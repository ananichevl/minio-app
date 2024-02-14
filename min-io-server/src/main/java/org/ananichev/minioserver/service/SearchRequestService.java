package org.ananichev.minioserver.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.ananichev.minioserver.model.DownloadRequest;
import org.ananichev.minioserver.model.SearchRequest;
import org.ananichev.minioserver.service.repository.SearchRequestRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SearchRequestService {

    private final SearchRequestRepository searchRequestRepository;

    @Transactional
    public void save(String objectName) {

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setObjectName(objectName);

        searchRequestRepository.save(searchRequest);
    }
}
