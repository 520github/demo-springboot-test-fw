package org.sunso.demo.statemachine.bill.state.normalsettle;

import org.springframework.stereotype.Component;
import org.sunso.demo.statemachine.bill.statemachine.BillState;
import org.sunso.demo.statemachine.bill.statemachine.common.action.AbstractBillAction;
import org.sunso.statemachine.event.Event;
import org.sunso.statemachine.state.State;

@Component
public class BillNormalSettleAction extends AbstractBillAction<BillNormalSettleContext> {
    @Override
    public void execute(State state, State target, Event event, BillNormalSettleContext context) {
        updateBill(context);
        updateRepayPlan(context);
    }

    /**
     * 更新账单
     * 
     * @param context
     */
    private void updateBill(BillNormalSettleContext context) {
        context.getBill().setStatus(BillState.NORMAL_SETTLE.getKey());
    }

    /**
     * 更新还款计划
     * 
     * @param context
     */
    private void updateRepayPlan(BillNormalSettleContext context) {

    }
}
