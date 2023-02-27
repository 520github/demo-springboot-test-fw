package org.sunso.demo.statemachine.bill.statemachine.common.prepare;

import org.springframework.stereotype.Component;
import org.sunso.demo.statemachine.bill.statemachine.common.context.BillRepayPlanContext;
import org.sunso.demo.statemachine.bill.entity.RepayPlan;
import org.sunso.statemachine.event.Event;
import org.sunso.statemachine.prepare.Prepare;
import org.sunso.statemachine.state.State;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class BillRepayPlanPrepare implements Prepare<BillRepayPlanContext> {
    @Override
    public void prepare(State state, State target, Event event, BillRepayPlanContext context) {
        context.setRepayPlanList(getRepayPlanList(context));
    }

    private List<RepayPlan> getRepayPlanList(BillRepayPlanContext context) {
        return Arrays.asList(RepayPlan.create().setPlanId(1).setShouldRepayAmount(BigDecimal.valueOf(100)),
                RepayPlan.create().setPlanId(2).setShouldRepayAmount(BigDecimal.valueOf(150)));
    }
}
