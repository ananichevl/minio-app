package org.ananichev.minioserver.service.repository;

import org.ananichev.minioserver.model.SearchRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchRequestRepository extends JpaRepository<SearchRequest, Long> {
}
