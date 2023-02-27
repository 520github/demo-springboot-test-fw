package org.sunso.demo.statemachine.bill.state.billcreate.prepare;

import org.springframework.stereotype.Component;
import org.sunso.demo.statemachine.bill.state.billcreate.BillCreateContext;
import org.sunso.statemachine.event.Event;
import org.sunso.statemachine.prepare.Prepare;
import org.sunso.statemachine.state.State;

import java.math.BigDecimal;

@Component
public class BillCreateGetContractAmountPrepare implements Prepare<BillCreateContext> {
    @Override
    public void prepare(State state, State state1, Event event, BillCreateContext context) {
        context.setContractAmount(getContractAmount(context));
    }

    private BigDecimal getContractAmount(BillCreateContext context) {
        return BigDecimal.valueOf(1000);
    }
}
