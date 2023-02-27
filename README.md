基于springboot测试基础框架demo
======================

#### 测试多任务并发执行框架
~~~~
package org.sunso.demo.parallel.index;

import org.springframework.stereotype.Service;
import org.sunso.demo.parallel.index.parameter.IndexTaskParameter;
import org.sunso.parallel.bootstrap.FailRetryParallelTaskBootstrap;
import org.sunso.parallel.parameter.BaseParallelResponse;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class IndexService {
    public void runIndexParallelTask(IndexTaskParameter indexTaskParameter) {
        List<BaseParallelResponse> responseList = FailRetryParallelTaskBootstrap.create()
                .setFailRetryNum(3)
                .setExecutor(Executors.newFixedThreadPool(3))
                .setPoolTaskResultTimeout(100)
                .setTimeUnit(TimeUnit.MILLISECONDS).setBizRequest(indexTaskParameter)
                .isSpringTaskBean(true)
                .executeParallelTask(
                        IndexParallelTaskKey.IndexReadMysqlTask, 
                        IndexParallelTaskKey.IndexReadRedisTask,
                        IndexParallelTaskKey.IndexReadS3Task
                );
        System.out.println("size:" + responseList.size());
    }
}
~~~~
~~~~
package org.sunso.demo.parallel.index;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.sunso.demo.BaseSpringBootTest;
import org.sunso.demo.parallel.index.parameter.IndexTaskParameter;

public class IndexServiceTest extends BaseSpringBootTest {
    @Autowired
    private IndexService indexService;

    @Test
    public void runIndexParallelTaskTest() {
        indexService.runIndexParallelTask(IndexTaskParameter.create());
    }
}
~~~~

#### 对java代码进行并发性能测试
~~~~
package org.sunso.demo.perf;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PerfMapService {
    Map<Integer, Integer> map = new ConcurrentHashMap<>();

    public void putMap(int cycleNum, int randomNun) {
        Random random = new Random(randomNun);
        for (int i = 0; i < cycleNum; i++) {
            int value = random.nextInt();
            map.put(value, value);
        }
    }
}
~~~~

~~~~
package org.sunso.demo.perf;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.sunso.demo.BaseSpringBootTest;
import org.sunso.test.perf.bootstrap.AsyncBenchmarkBootstrap;
import org.sunso.test.perf.request.BenchmarkRequest;

import java.util.concurrent.Executors;

public class PerfMapServiceTest extends BaseSpringBootTest {
    @Autowired
    private PerfMapService perfMapService;

    @Test
    public void putMapTest() {
        BenchmarkRequest request = AsyncBenchmarkBootstrap.create()
                .setStatisticsExecuteService(Executors.newFixedThreadPool(1))
                .setTotalProcessingNum(1000)
                .setTotalThreadNum(5)
                .setRemark("put map test")
                .setRunnable(() -> {
                    perfMapService.putMap(100, 10000000);
                }).run();
        Assert.assertNotNull(request.getReportList());
        request.getReportList().forEach(report -> {
            Assert.assertEquals(report.getTotalProcessingNum(), request.getTotalProcessingNum());
            Assert.assertEquals(report.getTotalThreadNum(), request.getTotalThreadNum());
            Assert.assertEquals(report.getTotalStatisticsNum(), request.getTotalProcessingNum());
            Assert.assertEquals(report.getRemark(), request.getRemark());
        });
    }
}
~~~~


#### 基于状态机的信贷账单流转demo
~~~~
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
~~~~

~~~~
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
~~~~

~~~~
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
~~~~