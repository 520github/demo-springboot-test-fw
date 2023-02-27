package org.sunso.demo.statemachine.bill.state.lendsuccess;

import org.springframework.stereotype.Component;
import org.sunso.demo.statemachine.bill.statemachine.common.BillChecks;
import org.sunso.statemachine.check.Check;
import org.sunso.statemachine.event.Event;
import org.sunso.statemachine.response.Response;
import org.sunso.statemachine.state.State;

@Component
public class BillLendSuccessCheck implements Check<BillLendSuccessContext> {
    @Override
    public Response check(State state, State target, Event event, BillLendSuccessContext context) {
        return BillChecks.getBaseBillStatusCheck().check(state, target, event, context);
    }
}
