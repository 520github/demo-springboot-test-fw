package org.sunso.demo.statemachine.bill.statemachine.common;

import org.sunso.demo.holder.SpringHolder;
import org.sunso.statemachine.check.Check;

public class BillChecks {

    public static Check getBillCreateCheck() {
        return getCheck("billCreateCheck");
    }

    public static Check getBillLendCheck() {
        return getCheck("billLendCheck");
    }

    public static Check getBillLendFailCheck() {
        return getCheck("billLendFailCheck");
    }

    public static Check getBillLendSuccessCheck() {
        return getCheck("billLendSuccessCheck");
    }

    public static Check getBillRepayCheck() {
        return getCheck("billRepayCheck");
    }

    public static Check getBillOverdueCheck() {
        return getCheck("billOverdueCheck");
    }

    public static Check getBillNormalSettleCheck() {
        return getCheck("billNormalSettleCheck");
    }

    public static Check getBillOverdueSettleCheck() {
        return getCheck("billOverdueSettleCheck");
    }

    public static Check getBillExtendSettleCheck() {
        return getCheck("billExtendSettleCheck");
    }

    public static Check getBillClosedCheck() {
        return getCheck("billClosedCheck");
    }

    public static Check getBaseBillStatusCheck() {
        return getCheck("billStatusCheck");
    }

    private static Check getCheck(String name) {
        return SpringHolder.getBean(name, Check.class);
    }
}
