package org.sunso.demo.statemachine.bill.state.closed;

import org.sunso.demo.statemachine.bill.statemachine.common.context.BillRepayPlanContext;
import org.sunso.statemachine.state.State;

public class BillClosedContext extends BillRepayPlanContext {
    private State currentState;

    public static BillClosedContext create() {
        return new BillClosedContext();
    }

    public State getCurrentState() {
        return currentState;
    }

    public BillClosedContext setCurrentState(State currentState) {
        this.currentState = currentState;
        return this;
    }
}
