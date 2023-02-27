package org.sunso.demo.statemachine.bill.statemachine.common.prepare;

import org.springframework.stereotype.Component;
import org.sunso.demo.statemachine.bill.statemachine.common.context.BillTradeContext;
import org.sunso.demo.statemachine.bill.entity.BillTrade;
import org.sunso.statemachine.event.Event;
import org.sunso.statemachine.prepare.Prepare;
import org.sunso.statemachine.state.State;

@Component
public class BillTradePrepare implements Prepare<BillTradeContext> {
    @Override
    public void prepare(State state, State target, Event event, BillTradeContext context) {
        context.setBillTrade(getBillTrade(context));
    }

    private BillTrade getBillTrade(BillTradeContext context) {
        return new BillTrade();
    }
}
