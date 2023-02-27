package org.sunso.demo.statemachine.bill.enums;

public enum TradeStatusEnum {
    init("init", "初始化"), fail("fail", "失败"), success("success", "成功"),;

    String code;
    String message;

    TradeStatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
