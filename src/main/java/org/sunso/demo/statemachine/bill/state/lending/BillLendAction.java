package org.sunso.demo.statemachine.bill.state.lending;

import org.springframework.stereotype.Component;
import org.sunso.demo.statemachine.bill.statemachine.BillState;
import org.sunso.demo.statemachine.bill.statemachine.common.action.AbstractBillAction;
import org.sunso.statemachine.event.Event;
import org.sunso.statemachine.state.State;

@Component
public class BillLendAction extends AbstractBillAction<BillLendContext> {
    @Override
    public void execute(State state, State target, Event event, BillLendContext context) {
        updateBill(context);
        initTrade(context);
        tradePay(context);
        print("context:" + context);
    }

    /**
     * 更新账单状态
     * 
     * @param context
     */
    private void updateBill(BillLendContext context) {
        context.getBill().setStatus(BillState.LENDING.getKey());
        // update data to db
        print("更新账单状态");
    }

    /**
     * 初始化交易
     * 
     * @param context
     */
    private void initTrade(BillLendContext context) {
        print("初始化交易");
    }

    /**
     * 调用支付并处理支付返回结果
     * 
     * @param context
     */
    private void tradePay(BillLendContext context) {
        print("调用支付并处理支付返回结果");
    }

}
