package org.sunso.demo.statemachine.bill.state.normalsettle;

import org.sunso.demo.statemachine.bill.statemachine.common.context.BillRepayPlanContext;

public class BillNormalSettleContext extends BillRepayPlanContext {
    public static BillNormalSettleContext create() {
        return new BillNormalSettleContext();
    }
}
