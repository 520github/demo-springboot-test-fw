package org.sunso.demo.statemachine.bill.statemachine.common.check;

import org.springframework.stereotype.Component;
import org.sunso.demo.statemachine.bill.statemachine.common.context.BillContext;
import org.sunso.statemachine.event.Event;
import org.sunso.statemachine.response.DefaultResponse;
import org.sunso.statemachine.response.Response;
import org.sunso.statemachine.response.SystemBizCode;
import org.sunso.statemachine.state.State;

@Component
public class BillStatusCheck extends AbstractBillCheck<BillContext> {
    @Override
    public Response check(State source, State target, Event event, BillContext billContext) {
        if (!checkState(event, source)) {
            return DefaultResponse.fail(SystemBizCode.currentStateNotAllow);
        }
        return success(target);
    }

    protected boolean checkState(Event event, State source) {
        if (event.allowState() == null) {
            return true;
        }
        if (event.allowState().contains(source)) {
            return true;
        }
        return false;
    }
}
