package org.sunso.demo.statemachine.bill.statemachine;

import org.sunso.statemachine.state.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum BillState implements State {
    CREATE_BILL("CREATE_BILL", "建帐"), LENDING("LENDING", "放款中"), LENDING_FAIL("LENDING_FAIL", "放款失败"), REPAY_PROCESS(
            "REPAY_PROCESS", "还款中"), OVERDUE("OVERDUE", "已逾期"), NORMAL_SETTLE("NORMAL_SETTLE", "正常结清"), OVERDUE_SETTLE(
                    "OVERDUE_SETTLE", "逾期结清"), EXTENSION_SETTLE("EXTENSION_SETTLE", "展期结清"), CLOSED("CLOSED", "已关闭"),;

    String key;
    String name;

    BillState(String key, String name) {
        this.key = key;
        this.name = name;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getName() {
        return name;
    }

    public static List<State> notContainClosed() {
        List<State> list = new ArrayList<>();
        for (State state : values()) {
            if (BillState.CLOSED != state) {
                list.add(state);
            }
        }
        return list;
    }

    public static List<State> extendSettle() {
        return Arrays.asList(BillState.REPAY_PROCESS, BillState.OVERDUE);
    }

    public static List<State> repay() {
        return Arrays.asList(BillState.REPAY_PROCESS, BillState.OVERDUE);
    }

}
