package org.sunso.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.sunso.demo.model.request.FileDownloadRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Enumeration;
import java.util.List;

/**
 * @author sunso520
 * @Title:FileDownloadController
 * @Description: <br>
 * @Created on 2025/1/22 14:40
 */
@RestController
@RequestMapping("/download")
@Slf4j
public class FileDownloadController {

    @RequestMapping(path = "/file", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity downloadFile(FileDownloadRequest fileDownloadRequest, @RequestHeader(value = "Range", required = false) String range, HttpServletRequest request) {
        log.info("fileDownloadRequest [{}], method[{}]", fileDownloadRequest, request.getMethod());
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String key = headers.nextElement();
            log.info("header key[{}], value[{}]", key, request.getHeader(key));
        }
        String filePathName = "/Users/sunso520/home/tmp/第一章.docx";
        File file = new File(filePathName);
        long fileSize = file.length();
        System.out.println("fileSize:" + fileSize);
        if (StringUtils.isEmpty(range)) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileSize))
                    .body(new FileSystemResource(file))
                    ;
        }
        else {
            List<HttpRange> rangeList =  HttpRange.parseRanges(range);
            if (rangeList.isEmpty()) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            HttpRange httpRange = rangeList.get(0);
            long start = httpRange.getRangeStart(fileSize);
            long end = httpRange.getRangeEnd(fileSize);
            long contentLength = end - start + 1;
            System.out.println(String.format("start[%d], end[%d], contentLength[%d]", start, end, contentLength));
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                    .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                    .header(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + fileSize)
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(contentLength))
                    .body(new RangeFileResource(file, start, end));
        }
    }

    private static class RangeFileResource extends FileSystemResource {
        private final long start;
        private final long end;

        public RangeFileResource(File file, long start, long end) {
            super(file);
            this.start = start;
            this.end = end;
        }

        @Override
        public long contentLength() {
            return end - start + 1;
        }

        public InputStream getInputStream() throws IOException {
            RandomAccessFile randomAccessFile = new RandomAccessFile(getFile(), "r");
            randomAccessFile.seek(start);
            return new RangeInputStream(randomAccessFile, contentLength());
        }
    }

    private static class RangeInputStream extends InputStream {
        private final RandomAccessFile randomAccessFile;
        private final long length;
        private long position = 0;

        public RangeInputStream(RandomAccessFile randomAccessFile, long length) {
            this.randomAccessFile = randomAccessFile;
            this.length = length;
        }

        public int read() throws IOException {
            if (position >= length) {
                return -1;
            }
            int value = randomAccessFile.read();
            if (value != -1) {
                position++;
            }
            return value;
        }

        public int read(byte[] b, int off, int len) throws IOException {
            if (position >= length) {
                return -1;
            }
            int bytesToRead = (int)Math.min(len, length -  position);
            int byteRead = randomAccessFile.read(b, off, bytesToRead);
            if (byteRead > 0) {
                position+=byteRead;
            }
            return byteRead;
        }
    }
}
