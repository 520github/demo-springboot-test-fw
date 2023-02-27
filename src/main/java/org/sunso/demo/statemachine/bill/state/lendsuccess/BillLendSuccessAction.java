package org.sunso.demo.statemachine.bill.state.lendsuccess;

import org.springframework.stereotype.Component;
import org.sunso.demo.statemachine.bill.statemachine.BillState;
import org.sunso.demo.statemachine.bill.statemachine.common.action.AbstractBillAction;
import org.sunso.demo.statemachine.bill.enums.TradeStatusEnum;
import org.sunso.statemachine.event.Event;
import org.sunso.statemachine.state.State;

@Component
public class BillLendSuccessAction extends AbstractBillAction<BillLendSuccessContext> {
    @Override
    public void execute(State state, State target, Event event, BillLendSuccessContext context) {
        updateBill(context);
        updateBillTrade(context);
    }

    /**
     * 更新账单状态为还款中
     * 
     * @param context
     */
    private void updateBill(BillLendSuccessContext context) {
        context.getBill().setStatus(BillState.REPAY_PROCESS.getKey());
    }

    /**
     * 更新交易状态为交易成功
     * 
     * @param context
     */
    private void updateBillTrade(BillLendSuccessContext context) {
        context.getBillTrade().setStatus(TradeStatusEnum.success.getCode());
    }

    /**
     * 生成还款计划
     * 
     * @param context
     */
    private void createRepayPlan(BillLendSuccessContext context) {

    }
}
