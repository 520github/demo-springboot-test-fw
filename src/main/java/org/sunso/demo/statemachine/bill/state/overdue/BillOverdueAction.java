package org.sunso.demo.statemachine.bill.state.overdue;

import org.springframework.stereotype.Component;
import org.sunso.demo.statemachine.bill.statemachine.BillState;
import org.sunso.demo.statemachine.bill.statemachine.common.action.AbstractBillAction;
import org.sunso.statemachine.event.Event;
import org.sunso.statemachine.state.State;

@Component
public class BillOverdueAction extends AbstractBillAction<BillOverdueContext> {
    @Override
    public void execute(State state, State target, Event event, BillOverdueContext context) {
        updateBill(context);
        updateRepayPlan(context);
    }

    /**
     * 更新账单
     * 
     * @param context
     */
    private void updateBill(BillOverdueContext context) {
        context.getBill().setStatus(BillState.OVERDUE.getKey());
    }

    /**
     * 更新还款计划
     * 
     * @param context
     */
    private void updateRepayPlan(BillOverdueContext context) {

    }
}
