package org.sunso.demo.statemachine.bill.state.lendsuccess;

import org.sunso.demo.statemachine.bill.statemachine.common.context.BillTradeContext;

public class BillLendSuccessContext extends BillTradeContext {

    public static BillLendSuccessContext create() {
        return new BillLendSuccessContext();
    }
}
