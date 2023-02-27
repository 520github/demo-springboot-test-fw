package org.sunso.demo.statemachine.bill.statemachine;

import org.sunso.statemachine.event.Event;
import org.sunso.statemachine.state.State;

import java.util.Arrays;
import java.util.List;

public enum BillEvent implements Event {
    CREATE_BILL_EVENT("CREATE_BILL_EVENT", "建账事件", Arrays.asList(BillState.CREATE_BILL)), LEND_EVENT("LEND_EVENT",
            "放款事件", Arrays.asList(BillState.CREATE_BILL)), LEND_SUCCESS_EVENT("LEND_SUCCESS_EVENT", "放款成功事件",
                    Arrays.asList(BillState.LENDING)), LEND_FAIL_EVENT("LEND_FAIL_EVENT", "放款失败事件",
                            Arrays.asList(BillState.LENDING)), REPAY_EVENT("REPAY_EVENT", "还款事件",
                                    BillState.repay()), OVERDUE_EVENT("OVERDUE_EVENT", "逾期事件",
                                            BillState.repay()), NORMAL_SETTLE_EVENT("NORMAL_SETTLE_EVENT", "正常结清事件",
                                                    Arrays.asList(BillState.REPAY_PROCESS)), OVERDUE_SETTLE_EVENT(
                                                            "OVERDUE_SETTLE_EVENT", "逾期结清事件",
                                                            Arrays.asList(BillState.OVERDUE)), EXTENSION_SETTLE_EVENT(
                                                                    "EXTENSION_SETTLE_EVENT", "展期结清事件",
                                                                    BillState.extendSettle()), CLOSED_EVENT(
                                                                            "CLOSED_EVENT", "关闭事件",
                                                                            BillState.notContainClosed());

    String key;
    String name;
    List<State> allowState;

    BillEvent(String key, String name, List<State> allowState) {
        this.key = key;
        this.name = name;
        this.allowState = allowState;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<State> allowState() {
        return allowState;
    }
}
