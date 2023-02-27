package org.sunso.demo.statemachine.bill.statemachine.common;

import org.sunso.demo.holder.SpringHolder;
import org.sunso.statemachine.action.Action;
import org.sunso.statemachine.check.Check;

public class BillActions {

    public static Action getBillLendAction() {
        return getAction("billLendAction");
    }

    public static Action getBillCreateAction() {
        return getAction("billCreateAction");
    }

    public static Action getBillLendFailAction() {
        return getAction("billLendFailAction");
    }

    public static Action getBillLendSuccessAction() {
        return getAction("billLendSuccessAction");
    }

    public static Action getBillRepayAction() {
        return getAction("billRepayAction");
    }

    public static Action getBillOverdueAction() {
        return getAction("billOverdueAction");
    }

    public static Action getBillNormalSettleAction() {
        return getAction("billNormalSettleAction");
    }

    public static Action getBillOverdueSettleAction() {
        return getAction("billOverdueSettleAction");
    }

    public static Action getBillExtendSettleAction() {
        return getAction("billExtendSettleAction");
    }

    public static Action getBillClosedAction() {
        return getAction("billClosedAction");
    }

    private static Action getAction(String name) {
        return SpringHolder.getBean(name, Action.class);
    }
}
