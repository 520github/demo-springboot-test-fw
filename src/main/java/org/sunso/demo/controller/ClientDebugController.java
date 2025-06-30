package org.sunso.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.sunso.demo.model.ResData;
import org.sunso.demo.model.request.ValidateLicenceRequest;

/**
 * @author sunso520
 * @Title:ClientDebugController
 * @Description: <br>
 * @Created on 2025/3/25 13:01
 */
@RestController
@RequestMapping("/v3/client/debug")
public class ClientDebugController  {

    @RequestMapping(path = "/validate-licence", method = {RequestMethod.GET, RequestMethod.POST})
    public ResData validateLicence(ValidateLicenceRequest request
            , @RequestHeader(value = "organkey", required = false) String organKey) {
        request.setOrganKey(organKey);
        return ResData.success();
    }


}
