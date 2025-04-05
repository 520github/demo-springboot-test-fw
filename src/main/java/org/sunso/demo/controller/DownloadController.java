package org.sunso.demo.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.sunso.demo.model.ResData;
import org.sunso.demo.model.request.DownloadDataPacketRequest;
import org.sunso.demo.model.request.FetchDownloadDataPacketInfoRequest;
import org.sunso.demo.model.response.FetchDownloadDataPacketInfoResponse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sunso520
 * @Title:DownloadController
 * @Description: <br>
 * @Created on 2025/2/18 15:10
 */
@RestController
@RequestMapping("/v3/client/download")
@Slf4j
public class DownloadController {

    @RequestMapping(path = "/fetch-download-data-packet-info", method = {RequestMethod.GET, RequestMethod.POST})
    public ResData<FetchDownloadDataPacketInfoResponse> fetchDownloadDataPacketInfo(FetchDownloadDataPacketInfoRequest request
            , @RequestHeader(value = "organkey", required = false) String organKey                          ) {
        request.setOrganKey(organKey);
        log.info("fetchDownloadDataPacketInfo [{}], organKey[{}]", request, organKey);
        return ResData.success(getResponse(request));
    }

    @RequestMapping(path = "/file", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity downloadFile(DownloadDataPacketRequest request
            , @RequestHeader(value = "Range", required = false) String range
            , @RequestHeader(value = "organkey", required = false) String organKey                          ) {
        request.setRange(range);
        request.setOrganKey(organKey);
        log.info("downloadFile [{}], organKey[{}]", request, organKey);

        return downloadDataPacket(request);
        //return badRequestResponseEntity();
    }

    private ResponseEntity badRequestResponseEntity() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .header(HttpHeaders.CONTENT_TYPE, "application/json;charset=utf-8")
                .body(ResData.success("成功了"))
                ;
    }


    public ResponseEntity downloadDataPacket(DownloadDataPacketRequest request) {
        try {
            String filePath = request.getDataPkPathMd5();
            File file = getFile(filePath);
//            if (!file.exists()) {
//                file = new File("/Users/sunso520/home/tmp/第一章.docx");
//            }
            if (StrUtil.isBlank(request.getRange())) {
                return downloadFile(file);
            }
            return downloadFileFromRange(file, request.getRange());
        }catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("");
        }
    }

    private File getFile(String filePath) {
        log.info("File path: " + filePath);
        if ("D-1".equals(filePath)) {
            return new File("/Users/sunso520/home/tmp/editor.zip");
        }
        if ("D-2".equals(filePath)) {
            return new File("/Users/sunso520/home/tmp/metaMask.zip");
        }
        if ("F-1".equals(filePath)) {
            return new File("/Users/sunso520/home/tmp/yxh.zip");
        }
        if ("F-2".equals(filePath)) {
            return new File("/Users/sunso520/home/tmp/sublime.zip");
        }
        return new File("/Users/sunso520/home/tmp/第一章.docx");
    }

    private ResponseEntity downloadFile(File file) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()))
                .body(new FileSystemResource(file))
                ;
    }

    private ResponseEntity downloadFileFromRange(File file, String range) {
        List<HttpRange> rangeList =  HttpRange.parseRanges(range);
        if (rangeList.isEmpty()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        long fileSize = file.length();
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

    private FetchDownloadDataPacketInfoResponse getResponse(FetchDownloadDataPacketInfoRequest request) {
        FetchDownloadDataPacketInfoResponse response = new FetchDownloadDataPacketInfoResponse();
//        FetchDownloadDataPacketInfoResponse.DownloadDataPackageInfo data = new FetchDownloadDataPacketInfoResponse.DownloadDataPackageInfo();
//        response.setData(data);
        response.setTotal(getTotal(request));
        if ("F".equals(request.getListType())) {
            response.setFileList(getAllFileItemList());
        }
        else if ("D".equals(request.getListType())) {
            response.setFileList(getFileItemList());
        }
        else {
            response.setFileList(getFileItemList());
        }
        return response;
    }

    private FetchDownloadDataPacketInfoResponse.DownloadDataPackageTotal getTotal(FetchDownloadDataPacketInfoRequest request) {
        FetchDownloadDataPacketInfoResponse.DownloadDataPackageTotal total = new FetchDownloadDataPacketInfoResponse.DownloadDataPackageTotal();
        total.setDataEnv(request.getDataEnv());
        total.setDataVersion(request.getDataVersion());
        total.setListDataType(request.getListDataType());
        total.setListType(request.getListType());
        total.setDataDt(request.getDataDt());
        total.setDataPkType(request.getDataPkType());
        total.setSplitType(request.getSplitType());
        total.setEncryptType(request.getEncryptType());
        total.setFileNum(2);

        return total;
    }

    private List<FetchDownloadDataPacketInfoResponse.DownloadDataPackageFileItem> getFileItemList() {
        List<FetchDownloadDataPacketInfoResponse.DownloadDataPackageFileItem> fileItemList = new ArrayList<>();
        fileItemList.add(getFileItem());
        fileItemList.add(getFileItem2());
        return fileItemList;
    }

    private FetchDownloadDataPacketInfoResponse.DownloadDataPackageFileItem getFileItem() {
        FetchDownloadDataPacketInfoResponse.DownloadDataPackageFileItem fileItem = new FetchDownloadDataPacketInfoResponse.DownloadDataPackageFileItem();
        fileItem.setDataPkPathMd5("D-1");
        fileItem.setDataPkName("editor.zip");
        fileItem.setDataPkSize(fileItem.fetchDataPkSize());
        fileItem.setDataPkMd5(fileItem.fetchDataPkMd5());
        fileItem.setDataPkCount(100);
        fileItem.setDataPkSort(100);
        fileItem.setDataRange("1-100");
        return fileItem;
    }

    private FetchDownloadDataPacketInfoResponse.DownloadDataPackageFileItem getFileItem2() {
        FetchDownloadDataPacketInfoResponse.DownloadDataPackageFileItem fileItem = new FetchDownloadDataPacketInfoResponse.DownloadDataPackageFileItem();
        fileItem.setDataPkPathMd5("D-2");
        fileItem.setDataPkName("metaMask.zip");
        fileItem.setDataPkSize(fileItem.fetchDataPkSize());
        fileItem.setDataPkMd5(fileItem.fetchDataPkMd5());
        fileItem.setDataPkCount(200);
        fileItem.setDataPkSort(200);
        fileItem.setDataRange("101-200");
        return fileItem;
    }


    private List<FetchDownloadDataPacketInfoResponse.DownloadDataPackageFileItem> getAllFileItemList() {
        List<FetchDownloadDataPacketInfoResponse.DownloadDataPackageFileItem> fileItemList = new ArrayList<>();
        fileItemList.add(getAllFileItem());
        fileItemList.add(getAllFileItem2());
        return fileItemList;
    }

    private FetchDownloadDataPacketInfoResponse.DownloadDataPackageFileItem getAllFileItem() {
        FetchDownloadDataPacketInfoResponse.DownloadDataPackageFileItem fileItem = new FetchDownloadDataPacketInfoResponse.DownloadDataPackageFileItem();
        fileItem.setDataPkPathMd5("F-1");
        fileItem.setDataPkName("yxh.zip");
        fileItem.setDataPkSize(fileItem.fetchDataPkSize());
        fileItem.setDataPkMd5(fileItem.fetchDataPkMd5());
        fileItem.setDataPkCount(1000);
        fileItem.setDataPkSort(1000);
        fileItem.setDataRange("1-1000");
        return fileItem;
    }

    private FetchDownloadDataPacketInfoResponse.DownloadDataPackageFileItem getAllFileItem2() {
        FetchDownloadDataPacketInfoResponse.DownloadDataPackageFileItem fileItem = new FetchDownloadDataPacketInfoResponse.DownloadDataPackageFileItem();
        fileItem.setDataPkPathMd5("F-2");
        fileItem.setDataPkName("sublime.zip");
        fileItem.setDataPkSize(fileItem.fetchDataPkSize());
        fileItem.setDataPkMd5(fileItem.fetchDataPkMd5());
        fileItem.setDataPkCount(2000);
        fileItem.setDataPkSort(2000);
        fileItem.setDataRange("1001-2000");
        return fileItem;
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
