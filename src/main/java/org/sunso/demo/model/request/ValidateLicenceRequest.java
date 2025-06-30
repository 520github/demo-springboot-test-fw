package org.sunso.demo.model.request;

import lombok.Data;

/**
 * @author sunso520
 * @Title:ValidateLicence
 * @Description: <br>
 * @Created on 2025/4/24 09:01
 */
@Data
public class ValidateLicenceRequest {
    private String organKey;
    private Long operatorId;
    private String macAddress;
    private String licence;
}
