package org.sunso.demo.controller;

import cn.hutool.core.lang.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.sunso.demo.model.ResData;
import org.sunso.demo.model.request.GetTokenRequest;
import org.sunso.demo.model.request.ValidateLicenceRequest;
import org.sunso.demo.model.response.GetTokenResponse;

/**
 * @author sunso520
 * @Title:ClientDebugController
 * @Description: <br>
 * @Created on 2025/3/25 13:01
 */
@RestController
@RequestMapping("/v3/client/oauth2")
@Slf4j
public class Oauth2Controller {

    @RequestMapping(path = "/token", method = {RequestMethod.GET, RequestMethod.POST})
    public GetTokenResponse getToken(@RequestBody GetTokenRequest request) {
        log.info("request [{}]", request);
        return GetTokenResponse.ofSuccess(UUID.fastUUID().toString());
    }


}
