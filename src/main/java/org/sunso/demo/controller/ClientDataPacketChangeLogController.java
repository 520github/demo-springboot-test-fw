package org.sunso.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.sunso.demo.model.ResData;
import org.sunso.demo.model.request.FetchDataPacketChangeLogRequest;
import org.sunso.demo.model.response.OblistDataPacketChangeLogResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunso520
 * @Title:ClientDataPacketChangeLogController
 * @Description: <br>
 * @Created on 2025/7/16 08:58
 */
@RestController
@RequestMapping("/v3/client/data-packet-change-log")
public class ClientDataPacketChangeLogController{

    @RequestMapping(path = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public ResData findChangeLogList(FetchDataPacketChangeLogRequest request) {
        return ResData.success(getChangeLogList(request));
    }

    private List<OblistDataPacketChangeLogResponse> getChangeLogList(FetchDataPacketChangeLogRequest request) {
        List<OblistDataPacketChangeLogResponse> dataList = new ArrayList<>();
        dataList.add(getChangeLog(request));
        return dataList;
    }

    private OblistDataPacketChangeLogResponse getChangeLog(FetchDataPacketChangeLogRequest request) {
        OblistDataPacketChangeLogResponse response = new OblistDataPacketChangeLogResponse();
        response.setId(10L);
        response.setDataDt("2025-07-20");
        response.setDataEnv("prod");
        response.setListDataType("DD_OBLIST");
        //response.setListDataType("EDD_DM_OBLIST");
        //response.setListType("F");
        response.setListType("D");
        response.setDataVersion("v3");
        response.setDataPkType("zip");
        response.setSplitType("count");
        response.setEncryptType("aes");
        response.setDataPkPathMd5("all");
        response.setBeforeDataPkSize(2000L);
        response.setBeforeDataPkCount(3000L);
        response.setAfterDataPkSize(4000L);
        response.setAfterDataPkCount(5000L);
        response.setRemark("数据包发生变更");
        response.setStatus("init");
        return response;
    }
}
