package org.sunso.demo.statemachine.bill.state.lendfail;

import org.sunso.demo.statemachine.bill.statemachine.common.context.BillTradeContext;

public class BillLendFailContext extends BillTradeContext {

    public static BillLendFailContext create() {
        return new BillLendFailContext();
    }
}
