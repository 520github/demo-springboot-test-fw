package org.sunso.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.sunso.demo.model.ResData;
import org.sunso.demo.model.request.ClientBizLogRequest;

import java.util.List;

@RestController
@RequestMapping("/v3/client/biz-log")
@Slf4j
public class ClientBizLogController {

    @PostMapping("/push")
    public ResData insert(ClientBizLogRequest clientBizLog) {
        //clientBizLogService.insert(clientBizLog);
        log.info("clientBizLog:{}", clientBizLog);
        return ResData.success();
    }

}