package org.sunso.demo.statemachine.bill.state.billcreate.prepare;

import org.springframework.stereotype.Component;
import org.sunso.demo.statemachine.bill.state.billcreate.BillCreateContext;
import org.sunso.demo.statemachine.bill.entity.Order;
import org.sunso.statemachine.event.Event;
import org.sunso.statemachine.prepare.Prepare;
import org.sunso.statemachine.state.State;

@Component
public class BillCreateGetOrderPrepare implements Prepare<BillCreateContext> {
    @Override
    public void prepare(State state, State state1, Event event, BillCreateContext context) {
        context.setOrder(getOrder(context));
    }

    private Order getOrder(BillCreateContext context) {
        return new Order().setMobile("mobile").setProductCode("productCode").setUid("uid").setStatus("status");
    }
}
