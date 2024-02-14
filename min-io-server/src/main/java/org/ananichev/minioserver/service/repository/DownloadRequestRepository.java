package org.ananichev.minioserver.service.repository;

import org.ananichev.minioserver.model.DownloadRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DownloadRequestRepository extends JpaRepository<DownloadRequest, Long> {
    List<DownloadRequest> findAll();
}
