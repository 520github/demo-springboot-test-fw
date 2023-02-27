package org.sunso.demo.statemachine.bill.entity;

import java.math.BigDecimal;

public class BillTrade {
    private String billId;
    private String tradeId;
    private BigDecimal tradeAmount;
    private String status;

    public String getBillId() {
        return billId;
    }

    public BillTrade setBillId(String billId) {
        this.billId = billId;
        return this;
    }

    public String getTradeId() {
        return tradeId;
    }

    public BillTrade setTradeId(String tradeId) {
        this.tradeId = tradeId;
        return this;
    }

    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    public BillTrade setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public BillTrade setStatus(String status) {
        this.status = status;
        return this;
    }
}
