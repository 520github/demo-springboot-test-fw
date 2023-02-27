package org.sunso.demo.statemachine.bill;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.sunso.demo.BaseSpringBootTest;
import org.sunso.demo.statemachine.bill.statemachine.BillState;
import org.sunso.statemachine.response.BizCode;
import org.sunso.statemachine.response.Response;
import org.sunso.statemachine.response.SystemBizCode;
import org.sunso.statemachine.state.State;

public class BillStateMachineServiceTest extends BaseSpringBootTest {
    @Autowired
    private BillService billService;

    @Test
    public void createBillTest() {
        checkSuccessResponse(billService.createBill(), BillState.CREATE_BILL);
    }

    @Test
    public void billLendTest() {
        checkSuccessResponse(billService.billLend(), BillState.LENDING);
    }

    @Test
    public void billLendFailTest() {
        checkSuccessResponse(billService.billLendFail(), BillState.LENDING_FAIL);
    }

    @Test
    public void billLendSuccessTest() {
        checkSuccessResponse(billService.billLendSuccess(), BillState.REPAY_PROCESS);
    }

    @Test
    public void billRepayTest() {
        for (State state : BillState.repay()) {
            checkSuccessResponse(billService.billRepay(state), BillState.REPAY_PROCESS);
        }
    }

    @Test
    public void billOverdueTest() {
        for (State state : BillState.repay()) {
            checkSuccessResponse(billService.billOverdue(state), BillState.OVERDUE);
        }
    }

    @Test
    public void billNormalSettleTest() {
        checkSuccessResponse(billService.billNormalSettle(), BillState.NORMAL_SETTLE);
    }

    @Test
    public void billOverdueSettleTest() {
        checkSuccessResponse(billService.billOverdueSettle(), BillState.OVERDUE_SETTLE);
    }

    @Test
    public void billExtendSettleTest() {
        for (State state : BillState.extendSettle()) {
            checkSuccessResponse(billService.billExtendSettle(state), BillState.EXTENSION_SETTLE);
        }
    }

    @Test
    public void billClosedTest() {
        for (State state : BillState.notContainClosed()) {
            checkSuccessResponse(billService.billClosed(state), BillState.CLOSED);
        }
    }

    @Test
    public void billClosedFailTest() {
        checkFailResponse(billService.billClosed(BillState.CLOSED), SystemBizCode.currentStateNotAllow);
    }

    private void checkFailResponse(Response response, BizCode bizCode) {
        Assert.assertNotNull(response);
        Assert.assertEquals(false, response.isSuccess());
        Assert.assertEquals(bizCode, response.getBizCode());
        print(response);
    }

    private void checkSuccessResponse(Response response, State target) {
        Assert.assertNotNull(response);
        Assert.assertEquals(true, response.isSuccess());
        Assert.assertEquals(target, response.getState());
        print(response);
    }
}
