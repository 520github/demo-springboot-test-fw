package org.sunso.demo.statemachine.bill.statemachine.common.context;

import org.sunso.demo.statemachine.bill.entity.BillTrade;

public class BillTradeContext extends BillContext {
    private BillTrade billTrade;

    public BillTrade getBillTrade() {
        return billTrade;
    }

    public BillTradeContext setBillTrade(BillTrade billTrade) {
        this.billTrade = billTrade;
        return this;
    }
}
