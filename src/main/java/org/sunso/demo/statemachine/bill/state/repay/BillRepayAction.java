package org.sunso.demo.statemachine.bill.state.repay;

import org.springframework.stereotype.Component;
import org.sunso.demo.statemachine.bill.statemachine.common.action.AbstractBillAction;
import org.sunso.statemachine.event.Event;
import org.sunso.statemachine.state.State;

@Component
public class BillRepayAction extends AbstractBillAction<BillRepayContext> {
    @Override
    public void execute(State state, State target, Event event, BillRepayContext context) {
        updateBill(context);
        updateRepayPlan(context);
    }

    /**
     * 更新账单金额
     * 
     * @param context
     */
    private void updateBill(BillRepayContext context) {
        context.getBill()
                .setAlreadyRepayAmount(context.getBill().getAlreadyRepayAmount().add(context.getRepayAmount()));
    }

    /**
     * 更新还款计划
     * 
     * @param context
     */
    private void updateRepayPlan(BillRepayContext context) {

    }
}
