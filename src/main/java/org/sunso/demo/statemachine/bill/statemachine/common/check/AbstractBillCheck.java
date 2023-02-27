package org.sunso.demo.statemachine.bill.statemachine.common.check;

import org.sunso.demo.statemachine.bill.statemachine.common.context.BillContext;
import org.sunso.statemachine.check.Check;
import org.sunso.statemachine.response.DefaultResponse;
import org.sunso.statemachine.response.Response;
import org.sunso.statemachine.state.State;

public abstract class AbstractBillCheck<C extends BillContext> implements Check<C> {

    protected Response success(State target) {
        return DefaultResponse.defaultSuccess(target);
    }
}
