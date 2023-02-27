package org.sunso.demo.statemachine.bill.state.billcreate;

import org.springframework.stereotype.Component;
import org.sunso.demo.statemachine.bill.statemachine.common.check.AbstractBillCheck;
import org.sunso.statemachine.event.Event;
import org.sunso.statemachine.response.Response;
import org.sunso.statemachine.state.State;

@Component
public class BillCreateCheck extends AbstractBillCheck<BillCreateContext> {
    @Override
    public Response check(State state, State target, Event event, BillCreateContext createBillContext) {
        Response response = isExistBill(createBillContext, target);
        if (response.isFail()) {
            return response;
        }
        return success(target);
    }

    /**
     * 根据orderId和uid查询是否存在对应bill
     * 
     * @param createBillContext
     * @return
     */
    private Response isExistBill(BillCreateContext createBillContext, State target) {
        return success(target);
    }
}
