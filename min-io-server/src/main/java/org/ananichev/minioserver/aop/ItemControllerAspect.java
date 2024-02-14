package org.ananichev.minioserver.aop;

import lombok.AllArgsConstructor;
import org.ananichev.minioserver.service.DownloadRequestService;
import org.ananichev.minioserver.service.SearchRequestService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@AllArgsConstructor
public class ItemControllerAspect {
    private final DownloadRequestService downloadRequestService;
    private final SearchRequestService searchRequestService;

    @After("execution(* org.ananichev.minioserver.controller.ItemController.getObjects(..))")
    public void searchRequest(JoinPoint p) {
        searchRequestService.save((String) p.getArgs()[0]);
    }

    @After("execution(* org.ananichev.minioserver.controller.ItemController.downloadFile(..))")
    public void downloadRequest(JoinPoint p) {
        downloadRequestService.save((String) p.getArgs()[0]);
    }
}
