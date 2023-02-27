package org.sunso.demo.statemachine.bill.state.extendsettle;

import org.springframework.stereotype.Component;
import org.sunso.demo.statemachine.bill.statemachine.BillState;
import org.sunso.demo.statemachine.bill.statemachine.common.action.AbstractBillAction;
import org.sunso.statemachine.event.Event;
import org.sunso.statemachine.state.State;

@Component
public class BillExtendSettleAction extends AbstractBillAction<BillExtendSettleContext> {
    @Override
    public void execute(State state, State target, Event event, BillExtendSettleContext context) {
        updateBill(context);
        updateRepayPlan(context);
        createNewBill(context);
        createNewRepayPlan(context);
    }

    /**
     * 更新账单
     * 
     * @param context
     */
    private void updateBill(BillExtendSettleContext context) {
        context.getBill().setStatus(BillState.EXTENSION_SETTLE.getKey());
    }

    /**
     * 更新还款计划
     * 
     * @param context
     */
    private void updateRepayPlan(BillExtendSettleContext context) {

    }

    /**
     * 创建新账单
     * 
     * @param context
     */
    private void createNewBill(BillExtendSettleContext context) {

    }

    /**
     * 创建新还款计划
     * 
     * @param context
     */
    private void createNewRepayPlan(BillExtendSettleContext context) {

    }
}
