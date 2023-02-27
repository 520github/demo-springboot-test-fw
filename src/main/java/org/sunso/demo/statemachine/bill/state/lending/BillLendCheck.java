package org.sunso.demo.statemachine.bill.state.lending;

import org.springframework.stereotype.Component;
import org.sunso.demo.statemachine.bill.statemachine.common.check.AbstractBillCheck;
import org.sunso.statemachine.event.Event;
import org.sunso.statemachine.response.Response;
import org.sunso.statemachine.state.State;

@Component
public class BillLendCheck extends AbstractBillCheck<BillLendContext> {
    @Override
    public Response check(State state, State target, Event event, BillLendContext context) {
        Response response = checkBillStatus(context, target);
        if (response.isFail()) {
            return response;
        }
        response = checkBillTrade(context, target);
        if (response.isFail()) {
            return response;
        }
        return success(target);
    }

    /**
     * 检查账单状态
     * 
     * @param context
     * @return
     */
    private Response checkBillStatus(BillLendContext context, State target) {
        return success(target);
    }

    /**
     * 检查是否已存在交易中的流水号
     * 
     * @param context
     * @return
     */
    private Response checkBillTrade(BillLendContext context, State target) {
        return success(target);
    }
}
