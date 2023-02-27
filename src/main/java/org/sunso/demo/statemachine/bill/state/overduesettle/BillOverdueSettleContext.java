package org.sunso.demo.statemachine.bill.state.overduesettle;

import org.sunso.demo.statemachine.bill.statemachine.common.context.BillRepayPlanContext;

public class BillOverdueSettleContext extends BillRepayPlanContext {
    public static BillOverdueSettleContext create() {
        return new BillOverdueSettleContext();
    }
}
