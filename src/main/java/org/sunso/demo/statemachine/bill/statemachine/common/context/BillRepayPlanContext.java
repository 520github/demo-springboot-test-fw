package org.sunso.demo.statemachine.bill.statemachine.common.context;

import org.sunso.demo.statemachine.bill.entity.RepayPlan;

import java.util.List;

public class BillRepayPlanContext extends BillContext {
    private List<RepayPlan> repayPlanList;

    public List<RepayPlan> getRepayPlanList() {
        return repayPlanList;
    }

    public BillRepayPlanContext setRepayPlanList(List<RepayPlan> repayPlanList) {
        this.repayPlanList = repayPlanList;
        return this;
    }
}
