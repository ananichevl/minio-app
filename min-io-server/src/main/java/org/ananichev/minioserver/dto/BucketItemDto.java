package org.ananichev.minioserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BucketItemDto {
    private String objectName;
    private OwnerDto owner;
    private String etag;
    private String lastModified;
    private long size;


}
