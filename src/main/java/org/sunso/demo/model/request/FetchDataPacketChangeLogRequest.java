package org.sunso.demo.model.request;

import lombok.Data;

/**
 * @author sunso520
 * @Title:FetchDataPacketChangeLogRequest
 * @Description: <br>
 * @Created on 2025/7/16 09:01
 */
@Data
public class FetchDataPacketChangeLogRequest {
    private Long id;
    private String dataDt;


    public long fetchId() {
        if (id == null) {
            return 0L;
        }
        return id;
    }
}
