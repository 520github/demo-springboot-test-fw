package org.sunso.demo.statemachine.bill.state.lending;

import org.sunso.demo.statemachine.bill.statemachine.common.context.BillContext;

import java.math.BigDecimal;

public class BillLendContext extends BillContext {
    /**
     * 放款金额
     */
    private BigDecimal payAmount;

    public static BillLendContext create() {
        return new BillLendContext();
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public BillLendContext setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
        return this;
    }

    @Override
    public String toString() {
        return super.toString() + "--BillLendContext{" + "payAmount=" + payAmount + '}';
    }
}
