package org.sunso.demo.controller;

import org.sunso.demo.model.request.FileDownloadRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author sunso520
 * @Title:FileDownloadBatch
 * @Description: <br>
 * @Created on 2025/1/23 15:23
 */
public class FileDownloadBatch {

    public void runDownloadBatch(int threadNum) {
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        List<Future<FileDownloadResponse>> futureList = new ArrayList<>();
        for(int i=0; i<10; i++) {
            FileDownloadRequest request = new FileDownloadRequest();
            FileDownloadTask downloadTask = new FileDownloadTask(request);
            Future<FileDownloadResponse> response = executorService.submit(downloadTask);
            futureList.add(response);
        }
        for(Future<FileDownloadResponse> responseFuture: futureList) {
            try {
                FileDownloadResponse response = responseFuture.get();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 关闭线程池
        executorService.shutdown();
    }
}
