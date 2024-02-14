package org.ananichev.minioserver.service;

import io.minio.GetObjectArgs;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.Result;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import org.ananichev.minioserver.dto.BucketItemDto;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MinIOService {

    private final MinioClient minioClient;

    private final String bucketName;

    public MinIOService(
            @Value("${minio.endpoint}") String endpoint,
            @Value("${minio.credentials.user}") String user,
            @Value("${minio.credentials.password}") String password,
            @Value("${minio.bucket}") String bucketName
    ) {
        this.minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(user, password)
                .build();
        this.bucketName = bucketName;
    }

    public List<String> getBucketList() {
        List<String> bucketNameList = new ArrayList<>();
        List<Bucket> bucketList = new ArrayList<>();
        try {
            bucketList = minioClient.listBuckets();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return bucketList.stream().map(Bucket::name).collect(Collectors.toList());
    }

    public List<BucketItemDto> getBucketItems(String objectName, String sortField, String sortDirection) {
        List<BucketItemDto> bucketItemDtos = new ArrayList<>();

        ListObjectsArgs listObjectsArgs = ListObjectsArgs.builder()
                .bucket(bucketName)
                .build();

        Iterable<Result<Item>> results = minioClient.listObjects(listObjectsArgs);

        for (Result<Item> result : results) {
            BucketItemDto bucketItemDto = new BucketItemDto();
            try {
                Item item = result.get();
                bucketItemDto.setObjectName(item.objectName());
                bucketItemDto.setSize(item.size());
                bucketItemDto.setEtag(item.etag());
                bucketItemDto.setLastModified(item.lastModified().toString());

                bucketItemDtos.add(bucketItemDto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return bucketItemDtos.stream()
                .filter(item -> item.getObjectName().contains(objectName))
                .sorted((first, second) -> {
                    try {
                        Field field = BucketItemDto.class.getDeclaredField(sortField);
                        field.setAccessible(true);
                        Object a = field.get(first);
                        Object b = field.get(second);

                        if (a instanceof String) {
                            return ((String) a).compareTo((String) b);
                        }
                        if (a instanceof Long) {
                            return ((Long) a).compareTo((Long) b);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return -1;
                })
                .collect(Collectors.toList());
    }

    public byte[] downloadFile(String objectName) {

        try (InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build())) {

            return IOUtils.toByteArray(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void uploadFile(MultipartFile file) {
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(file.getOriginalFilename())
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();

            minioClient.putObject(putObjectArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
