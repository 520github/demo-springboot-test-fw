package org.sunso.demo.statemachine.bill.state.closed;

import org.springframework.stereotype.Component;
import org.sunso.demo.statemachine.bill.statemachine.BillState;
import org.sunso.demo.statemachine.bill.statemachine.common.action.AbstractBillAction;
import org.sunso.statemachine.event.Event;
import org.sunso.statemachine.state.State;

@Component
public class BillClosedAction extends AbstractBillAction<BillClosedContext> {
    @Override
    public void execute(State state, State target, Event event, BillClosedContext context) {
        updateBill(context);
        updateRepayPlan(context);
    }

    /**
     * 更新账单
     * 
     * @param context
     */
    private void updateBill(BillClosedContext context) {
        context.getBill().setStatus(BillState.CLOSED.getKey());
    }

    /**
     * 更新还款计划
     * 
     * @param context
     */
    private void updateRepayPlan(BillClosedContext context) {

    }
}
