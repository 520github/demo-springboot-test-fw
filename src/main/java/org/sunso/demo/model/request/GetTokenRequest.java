package org.sunso.demo.model.request;

import lombok.Data;

/**
 * @author sunso520
 * @Title:GetTokenRequest
 * @Description: <br>
 * @Created on 2025/7/7 08:11
 */
@Data
public class GetTokenRequest {
    private String app_key;
    private String app_secret;
}
