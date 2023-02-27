package org.sunso.demo.statemachine.bill.entity;

import java.math.BigDecimal;

public class Bill {
    private String uid;
    private String productCode;
    private String billId;
    private String orderId;
    private String status;
    private BigDecimal applyAmount;
    private BigDecimal alreadyRepayAmount;

    public static Bill create() {
        return new Bill();
    }

    public String getUid() {
        return uid;
    }

    public Bill setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getProductCode() {
        return productCode;
    }

    public Bill setProductCode(String productCode) {
        this.productCode = productCode;
        return this;
    }

    public String getBillId() {
        return billId;
    }

    public Bill setBillId(String billId) {
        this.billId = billId;
        return this;
    }

    public String getOrderId() {
        return orderId;
    }

    public Bill setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Bill setStatus(String status) {
        this.status = status;
        return this;
    }

    public BigDecimal getApplyAmount() {
        return applyAmount;
    }

    public Bill setApplyAmount(BigDecimal applyAmount) {
        this.applyAmount = applyAmount;
        return this;
    }

    public BigDecimal getAlreadyRepayAmount() {
        return alreadyRepayAmount;
    }

    public Bill setAlreadyRepayAmount(BigDecimal alreadyRepayAmount) {
        this.alreadyRepayAmount = alreadyRepayAmount;
        return this;
    }

    @Override
    public String toString() {
        return "Bill{" + "uid='" + uid + '\'' + ", productCode='" + productCode + '\'' + ", billId='" + billId + '\''
                + ", orderId='" + orderId + '\'' + ", status='" + status + '\'' + ", applyAmount=" + applyAmount
                + ", alreadyRepayAmount=" + alreadyRepayAmount + '}';
    }
}
