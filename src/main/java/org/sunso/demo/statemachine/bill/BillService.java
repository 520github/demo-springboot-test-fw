package org.sunso.demo.statemachine.bill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sunso.demo.statemachine.bill.state.billcreate.BillCreateContext;
import org.sunso.demo.statemachine.bill.state.closed.BillClosedContext;
import org.sunso.demo.statemachine.bill.entity.Bill;
import org.sunso.demo.statemachine.bill.state.extendsettle.BillExtendSettleContext;
import org.sunso.demo.statemachine.bill.state.lendfail.BillLendFailContext;
import org.sunso.demo.statemachine.bill.state.lending.BillLendContext;
import org.sunso.demo.statemachine.bill.state.lendsuccess.BillLendSuccessContext;
import org.sunso.demo.statemachine.bill.state.normalsettle.BillNormalSettleContext;
import org.sunso.demo.statemachine.bill.state.overdue.BillOverdueContext;
import org.sunso.demo.statemachine.bill.state.overduesettle.BillOverdueSettleContext;
import org.sunso.demo.statemachine.bill.state.repay.BillRepayContext;
import org.sunso.demo.statemachine.bill.statemachine.BillState;
import org.sunso.demo.statemachine.bill.statemachine.BillStateMachineService;
import org.sunso.statemachine.response.Response;
import org.sunso.statemachine.state.State;

import java.math.BigDecimal;

@Service
public class BillService {
    @Autowired
    private BillStateMachineService billStateMachineService;

    public Response createBill() {
        BillCreateContext context = BillCreateContext.create().setOrderId("orderId").setUid("uid");
        return billStateMachineService.fireBillCreate(context);
    }

    public Response billLend() {
        BillLendContext context = BillLendContext.create();
        context.setBill(newBill(BillState.CREATE_BILL));
        return billStateMachineService.fireBillLend(context);
    }

    public Response billLendFail() {
        BillLendFailContext context = BillLendFailContext.create();
        context.setBill(newBill(BillState.LENDING));
        return billStateMachineService.fireBillLendFail(context);
    }

    public Response billLendSuccess() {
        BillLendSuccessContext context = BillLendSuccessContext.create();
        context.setBill(newBill(BillState.LENDING));
        return billStateMachineService.fireBillLendSuccess(context);
    }

    public Response billRepay(State state) {
        BillRepayContext context = BillRepayContext.create();
        context.setCurrentState(state);
        context.setRepayAmount(BigDecimal.valueOf(50));
        context.setBill(newBill(BillState.REPAY_PROCESS).setAlreadyRepayAmount(BigDecimal.valueOf(100)));
        return billStateMachineService.fireBillRepay(context);
    }

    public Response billOverdue(State state) {
        BillOverdueContext context = BillOverdueContext.create();
        context.setCurrentState(state);
        context.setBill(newBill(BillState.REPAY_PROCESS).setAlreadyRepayAmount(BigDecimal.valueOf(100)));
        return billStateMachineService.fireBillOverdue(context);
    }

    public Response billNormalSettle() {
        BillNormalSettleContext context = BillNormalSettleContext.create();
        context.setBill(newBill(BillState.REPAY_PROCESS));
        return billStateMachineService.fireBillNormalSettle(context);
    }

    public Response billOverdueSettle() {
        BillOverdueSettleContext context = BillOverdueSettleContext.create();
        context.setBill(newBill(BillState.OVERDUE));
        return billStateMachineService.fireBillOverdueSettle(context);
    }

    public Response billExtendSettle(State state) {
        BillExtendSettleContext context = BillExtendSettleContext.create();
        context.setCurrentState(state);
        context.setBill(newBill(BillState.OVERDUE));
        return billStateMachineService.fireBillExtendSettle(context);
    }

    public Response billClosed(State state) {
        BillClosedContext context = BillClosedContext.create();
        context.setCurrentState(state);
        context.setBill(newBill(BillState.OVERDUE));
        return billStateMachineService.fireBillClosed(context);
    }

    private Bill newBill(BillState state) {
        return Bill.create().setBillId("billId").setStatus(state.getKey());
    }
}
