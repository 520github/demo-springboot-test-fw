package org.sunso.demo.statemachine.bill.statemachine;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.sunso.demo.statemachine.bill.state.billcreate.BillCreateContext;
import org.sunso.demo.statemachine.bill.state.closed.BillClosedContext;
import org.sunso.demo.statemachine.bill.statemachine.common.BillActions;
import org.sunso.demo.statemachine.bill.statemachine.common.BillChecks;
import org.sunso.demo.statemachine.bill.statemachine.common.BillPrepares;
import org.sunso.demo.statemachine.bill.state.extendsettle.BillExtendSettleContext;
import org.sunso.demo.statemachine.bill.state.lendfail.BillLendFailContext;
import org.sunso.demo.statemachine.bill.state.lending.BillLendContext;
import org.sunso.demo.statemachine.bill.state.lendsuccess.BillLendSuccessContext;
import org.sunso.demo.statemachine.bill.state.normalsettle.BillNormalSettleContext;
import org.sunso.demo.statemachine.bill.state.overdue.BillOverdueContext;
import org.sunso.demo.statemachine.bill.state.overduesettle.BillOverdueSettleContext;
import org.sunso.demo.statemachine.bill.state.repay.BillRepayContext;
import org.sunso.statemachine.StateMachine;
import org.sunso.statemachine.bootstrap.StateMachineBootstrap;
import org.sunso.statemachine.repository.StateMachineRepositoryFactory;
import org.sunso.statemachine.response.Response;

@Service
public class BillStateMachineService implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        //状态机流程定义
        StateMachineBootstrap builder = StateMachineBootstrap.create();
        // 创建账单
        builder.newTransfer()
                .source(BillState.CREATE_BILL)
                .target(BillState.CREATE_BILL)
                .event(BillEvent.CREATE_BILL_EVENT)
                .check(BillChecks.getBillCreateCheck())
                .prepare(BillPrepares.getBillCreatePrepareList())
                .action(BillActions.getBillCreateAction())
                .builder();

        // 放款动作
        builder.newTransfer()
                .source(BillState.CREATE_BILL)
                .target(BillState.LENDING)
                .event(BillEvent.LEND_EVENT)
                .check(BillChecks.getBillLendCheck())
                .prepare(BillPrepares.getBillLendPrepareList())
                .action(BillActions.getBillLendAction())
                .builder();

        // 放款失败
        builder.newTransfer()
                .source(BillState.LENDING)
                .target(BillState.LENDING_FAIL)
                .event(BillEvent.LEND_FAIL_EVENT)
                .check(BillChecks.getBillLendFailCheck())
                .prepare(BillPrepares.getBillLendFailPrepareList())
                .action(BillActions.getBillLendFailAction())
                .builder();

        // 放款成功
        builder.newTransfer()
                .source(BillState.LENDING)
                .target(BillState.REPAY_PROCESS)
                .event(BillEvent.LEND_SUCCESS_EVENT)
                .check(BillChecks.getBillLendSuccessCheck())
                .prepare(BillPrepares.getBillLendSuccessPrepareList())
                .action(BillActions.getBillLendSuccessAction())
                .builder();

        // 还款
        builder.newTransfers()
                .source(BillState.repay())
                .target(BillState.REPAY_PROCESS)
                .event(BillEvent.REPAY_EVENT)
                .check(BillChecks.getBillRepayCheck())
                .prepare(BillPrepares.getBillRepayPrepareList())
                .action(BillActions.getBillRepayAction())
                .builder();

        // 逾期
        builder.newTransfers()
                .source(BillState.repay())
                .target(BillState.OVERDUE)
                .event(BillEvent.OVERDUE_EVENT)
                .check(BillChecks.getBillOverdueCheck())
                .prepare(BillPrepares.getBillOverduePrepareList())
                .action(BillActions.getBillOverdueAction())
                .builder();

        // 正常结清
        builder.newTransfer()
                .source(BillState.REPAY_PROCESS)
                .target(BillState.NORMAL_SETTLE)
                .event(BillEvent.NORMAL_SETTLE_EVENT)
                .check(BillChecks.getBillNormalSettleCheck())
                .prepare(BillPrepares.getBillNormalSettlePrepareList())
                .action(BillActions.getBillNormalSettleAction())
                .builder();

        // 逾期结清
        builder.newTransfer()
                .source(BillState.OVERDUE)
                .target(BillState.OVERDUE_SETTLE)
                .event(BillEvent.OVERDUE_SETTLE_EVENT)
                .check(BillChecks.getBillOverdueSettleCheck())
                .prepare(BillPrepares.getBillOverdueSettlePrepareList())
                .action(BillActions.getBillOverdueSettleAction())
                .builder();

        // 展期结清
        builder.newTransfers()
                .source(BillState.extendSettle())
                .target(BillState.EXTENSION_SETTLE)
                .event(BillEvent.EXTENSION_SETTLE_EVENT)
                .check(BillChecks.getBillExtendSettleCheck())
                .prepare(BillPrepares.getBillExtendSettlePrepareList())
                .action(BillActions.getBillExtendSettleAction())
                .builder();

        // 账单关闭
        builder.newTransfers()
                .source(BillState.values()) // BillState.notContainClosed()
                .target(BillState.CLOSED)
                .event(BillEvent.CLOSED_EVENT)
                .check(BillChecks.getBillClosedCheck())
                .prepare(BillPrepares.getBillClosedPrepareList()).
                action(BillActions.getBillClosedAction())
                .builder();

        builder.builder(BillStateMachineId.billStateMachine);
    }

    public Response fireBillCreate(BillCreateContext context) {
        return getStateMachine().fireEvent(BillState.CREATE_BILL, BillEvent.CREATE_BILL_EVENT, context);
    }

    public Response fireBillLend(BillLendContext context) {
        return getStateMachine().fireEvent(BillState.CREATE_BILL, BillEvent.LEND_EVENT, context);
    }

    public Response fireBillLendFail(BillLendFailContext context) {
        return getStateMachine().fireEvent(BillState.LENDING, BillEvent.LEND_FAIL_EVENT, context);
    }

    public Response fireBillLendSuccess(BillLendSuccessContext context) {
        return getStateMachine().fireEvent(BillState.LENDING, BillEvent.LEND_SUCCESS_EVENT, context);
    }

    public Response fireBillRepay(BillRepayContext context) {
        return getStateMachine().fireEvent(context.getCurrentState(), BillEvent.REPAY_EVENT, context);
    }

    public Response fireBillOverdue(BillOverdueContext context) {
        return getStateMachine().fireEvent(context.getCurrentState(), BillEvent.OVERDUE_EVENT, context);
    }

    public Response fireBillNormalSettle(BillNormalSettleContext context) {
        return getStateMachine().fireEvent(BillState.REPAY_PROCESS, BillEvent.NORMAL_SETTLE_EVENT, context);
    }

    public Response fireBillOverdueSettle(BillOverdueSettleContext context) {
        return getStateMachine().fireEvent(BillState.OVERDUE, BillEvent.OVERDUE_SETTLE_EVENT, context);
    }

    public Response fireBillExtendSettle(BillExtendSettleContext context) {
        return getStateMachine().fireEvent(context.getCurrentState(), BillEvent.EXTENSION_SETTLE_EVENT, context);
    }

    public Response fireBillClosed(BillClosedContext context) {
        return getStateMachine().fireEvent(context.getCurrentState(), BillEvent.CLOSED_EVENT, context);
    }

    private StateMachine getStateMachine() {
        return StateMachineRepositoryFactory.get(BillStateMachineId.billStateMachine);
    }

}
