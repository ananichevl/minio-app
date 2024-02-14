--liquibase formatted sql

--changeset liquibase-minio-service:create-search-table
CREATE TABLE search_request
(
    ID        int          NOT NULL,
    object_name  varchar(255) NOT NULL,
    PRIMARY KEY (ID)
);

--changeset liquibase-minio-service:create-download-table
CREATE TABLE download_request
(
    ID        int          NOT NULL,
    object_name  varchar(255) NOT NULL,
    PRIMARY KEY (ID)
);