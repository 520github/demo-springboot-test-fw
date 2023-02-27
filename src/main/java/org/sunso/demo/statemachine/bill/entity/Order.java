package org.sunso.demo.statemachine.bill.entity;

public class Order {
    private String uid;
    private String productCode;
    private String orderId;
    private String mobile;
    private String status;

    public String getUid() {
        return uid;
    }

    public Order setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getProductCode() {
        return productCode;
    }

    public Order setProductCode(String productCode) {
        this.productCode = productCode;
        return this;
    }

    public String getOrderId() {
        return orderId;
    }

    public Order setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public Order setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Order setStatus(String status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "Order{" + "uid='" + uid + '\'' + ", productCode='" + productCode + '\'' + ", orderId='" + orderId + '\''
                + ", mobile='" + mobile + '\'' + ", status='" + status + '\'' + '}';
    }
}
