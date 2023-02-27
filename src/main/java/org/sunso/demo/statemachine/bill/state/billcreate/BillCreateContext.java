package org.sunso.demo.statemachine.bill.state.billcreate;

import org.sunso.demo.statemachine.bill.statemachine.common.context.BillContext;

import java.math.BigDecimal;

public class BillCreateContext extends BillContext {
    private String orderId;
    private String uid;
    private BigDecimal contractAmount;

    public static BillCreateContext create() {
        return new BillCreateContext();
    }

    public String getOrderId() {
        return orderId;
    }

    public BillCreateContext setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public BillCreateContext setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    public BillCreateContext setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
        return this;
    }

    @Override
    public String toString() {
        return "CreateBillContext{" + "orderId='" + orderId + '\'' + ", uid='" + uid + '\'' + ", contractAmount="
                + contractAmount + '}';
    }
}
