package org.sunso.demo.statemachine.bill.state.lending.prepare;

import org.springframework.stereotype.Component;
import org.sunso.demo.statemachine.bill.state.lending.BillLendContext;
import org.sunso.statemachine.event.Event;
import org.sunso.statemachine.prepare.Prepare;
import org.sunso.statemachine.state.State;

import java.math.BigDecimal;

@Component
public class BillLendGetPayAmountPrepare implements Prepare<BillLendContext> {
    @Override
    public void prepare(State state, State target, Event event, BillLendContext context) {
        context.setPayAmount(BigDecimal.valueOf(2000));
    }
}
