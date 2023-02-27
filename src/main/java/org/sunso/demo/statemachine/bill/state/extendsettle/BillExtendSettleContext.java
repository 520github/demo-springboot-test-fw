package org.sunso.demo.statemachine.bill.state.extendsettle;

import org.sunso.demo.statemachine.bill.statemachine.common.context.BillRepayPlanContext;
import org.sunso.statemachine.state.State;

public class BillExtendSettleContext extends BillRepayPlanContext {
    private State currentState;

    public static BillExtendSettleContext create() {
        return new BillExtendSettleContext();
    }

    public State getCurrentState() {
        return currentState;
    }

    public BillExtendSettleContext setCurrentState(State currentState) {
        this.currentState = currentState;
        return this;
    }
}
