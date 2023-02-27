package org.sunso.demo.statemachine.bill.statemachine.common.action;

import org.sunso.demo.statemachine.bill.statemachine.common.context.BillContext;
import org.sunso.statemachine.action.Action;

public abstract class AbstractBillAction<C extends BillContext> implements Action<C> {

    protected void print(Object obj) {
        if (obj == null) {
            return;
        }
        System.out.println(obj);
    }
}
