package org.sunso.demo.statemachine.bill.state.billcreate;

import org.springframework.stereotype.Component;
import org.sunso.demo.statemachine.bill.statemachine.common.action.AbstractBillAction;
import org.sunso.statemachine.event.Event;
import org.sunso.statemachine.state.State;

@Component
public class BillCreateAction extends AbstractBillAction<BillCreateContext> {
    @Override
    public void execute(State state, State target, Event event, BillCreateContext context) {
        initCreditAndFrozen(context);
        initBill(context);
    }

    // 初始化额度并冻结
    private void initCreditAndFrozen(BillCreateContext context) {
        print("初始化额度并冻结:" + context);
    }

    // 初始化账单
    private void initBill(BillCreateContext context) {
        print("初始化账单:" + context);
    }
}
