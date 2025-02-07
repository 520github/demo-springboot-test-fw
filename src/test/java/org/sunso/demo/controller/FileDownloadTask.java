package org.sunso.demo.controller;


import org.sunso.demo.model.request.FileDownloadRequest;

import java.util.concurrent.Callable;

/**
 * @author sunso520
 * @Title:FileDownloadTask
 * @Description: <br>
 * @Created on 2025/1/23 15:11
 */
public class FileDownloadTask implements Callable<FileDownloadResponse> {
    private final FileDownloadRequest request;

    public FileDownloadTask(FileDownloadRequest request) {
        this.request = request;
    }

    @Override
    public FileDownloadResponse call() throws Exception {

        return null;
    }
}
