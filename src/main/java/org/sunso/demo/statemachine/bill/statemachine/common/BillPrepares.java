package org.sunso.demo.statemachine.bill.statemachine.common;

import org.sunso.demo.holder.SpringHolder;
import org.sunso.statemachine.prepare.Prepare;

import java.util.Arrays;
import java.util.List;

public class BillPrepares {

    public static List<Prepare> getBillClosedPrepareList() {
        return Arrays.asList(getBaseBillRepayPlanPrepare());
    }

    public static List<Prepare> getBillExtendSettlePrepareList() {
        return Arrays.asList(getBaseBillRepayPlanPrepare());
    }

    public static List<Prepare> getBillOverdueSettlePrepareList() {
        return Arrays.asList(getBaseBillRepayPlanPrepare());
    }

    public static List<Prepare> getBillNormalSettlePrepareList() {
        return Arrays.asList(getBaseBillRepayPlanPrepare());
    }

    public static List<Prepare> getBillOverduePrepareList() {
        return Arrays.asList(getBaseBillRepayPlanPrepare());
    }

    public static List<Prepare> getBillRepayPrepareList() {
        return Arrays.asList(getBaseBillRepayPlanPrepare());
    }

    public static List<Prepare> getBillLendSuccessPrepareList() {
        return Arrays.asList(getBaseBillTradePrepare());
    }

    public static List<Prepare> getBillLendFailPrepareList() {
        return Arrays.asList(getBaseBillTradePrepare());
    }

    public static List<Prepare> getBillLendPrepareList() {
        return Arrays.asList(getPrepare("billLendGetPayAmountPrepare"));
    }

    public static List<Prepare> getBillCreatePrepareList() {
        return Arrays.asList(getPrepare("billCreateGetContractAmountPrepare"), getPrepare("billCreateGetOrderPrepare"));
    }

    private static Prepare getBaseBillTradePrepare() {
        return getPrepare("billTradePrepare");
    }

    private static Prepare getBaseBillRepayPlanPrepare() {
        return getPrepare("billRepayPlanPrepare");
    }

    private static Prepare getPrepare(String name) {
        return SpringHolder.getBean(name, Prepare.class);
    }
}
