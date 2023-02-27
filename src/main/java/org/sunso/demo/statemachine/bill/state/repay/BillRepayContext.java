package org.sunso.demo.statemachine.bill.state.repay;

import org.sunso.demo.statemachine.bill.statemachine.common.context.BillRepayPlanContext;
import org.sunso.statemachine.state.State;

import java.math.BigDecimal;

public class BillRepayContext extends BillRepayPlanContext {
    private State currentState;
    private BigDecimal repayAmount;

    public static BillRepayContext create() {
        return new BillRepayContext();
    }

    public State getCurrentState() {
        return currentState;
    }

    public BillRepayContext setCurrentState(State currentState) {
        this.currentState = currentState;
        return this;
    }

    public BigDecimal getRepayAmount() {
        return repayAmount;
    }

    public BillRepayContext setRepayAmount(BigDecimal repayAmount) {
        this.repayAmount = repayAmount;
        return this;
    }
}
