package org.sunso.demo.statemachine.bill.statemachine;

import org.sunso.statemachine.response.BizCode;

public enum BillBizCode implements BizCode {
    ;

    String code;
    String message;

    BillBizCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
