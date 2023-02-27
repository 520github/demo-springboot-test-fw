package org.sunso.demo.statemachine.bill.state.overdue;

import org.sunso.demo.statemachine.bill.statemachine.common.context.BillRepayPlanContext;
import org.sunso.statemachine.state.State;

public class BillOverdueContext extends BillRepayPlanContext {
    private State currentState;

    public static BillOverdueContext create() {
        return new BillOverdueContext();
    }

    public State getCurrentState() {
        return currentState;
    }

    public BillOverdueContext setCurrentState(State currentState) {
        this.currentState = currentState;
        return this;
    }
}
