package org.sunso.demo.statemachine.bill.state.lendfail;

import org.springframework.stereotype.Component;
import org.sunso.demo.statemachine.bill.statemachine.BillState;
import org.sunso.demo.statemachine.bill.statemachine.common.action.AbstractBillAction;
import org.sunso.statemachine.event.Event;
import org.sunso.statemachine.state.State;

@Component
public class BillLendFailAction extends AbstractBillAction<BillLendFailContext> {
    @Override
    public void execute(State state, State target, Event event, BillLendFailContext context) {
        updateBill(context);
        updateBillTrade(context);
    }

    /**
     * 更新账单状态为放款失败
     * 
     * @param context
     */
    private void updateBill(BillLendFailContext context) {
        context.getBill().setStatus(BillState.LENDING_FAIL.getKey());
    }

    /**
     * 更新交易状态为交易失败
     * 
     * @param context
     */
    private void updateBillTrade(BillLendFailContext context) {

    }
}
