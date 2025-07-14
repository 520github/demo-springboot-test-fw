package org.sunso.demo.model.response;

import cn.hutool.json.JSONObject;
import lombok.Data;

/**
 * @author sunso520
 * @Title:GetTokenResponse
 * @Description: <br>
 * @Created on 2025/7/7 08:12
 */
@Data
public class GetTokenResponse {
    private String code;
    private JSONObject result;

    public static GetTokenResponse ofSuccess(String accessToken) {
        GetTokenResponse response = new GetTokenResponse();
        response.code = "0";
        response.result = new JSONObject();
        response.result.set("access_token", accessToken);
        return response;
    }
}
