package org.sunso.demo.parser.entity;

/**
 * @author sunso520
 * @Title:GenericEntity
 * @Description: <br>
 * @Created on 2025/4/5 22:44
 */
import java.util.HashMap;
import java.util.Map;

public class GenericEntity {
    private String objId;
    private Map<String, Object> data = new HashMap<>();

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
