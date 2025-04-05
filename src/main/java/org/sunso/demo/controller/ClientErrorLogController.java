package org.sunso.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.sunso.demo.model.ResData;
import org.sunso.demo.model.request.ClientErrorLogRequest;


@RestController
@RequestMapping("/v3/client/error-log")
@Slf4j
public class ClientErrorLogController {

    @PostMapping("/push")
    public ResData insert(ClientErrorLogRequest clientErrorLog) {
        //clientErrorLogService.insert(clientErrorLog);
        log.info("clientErrorLog:{}", clientErrorLog);
        return ResData.success();
    }

}