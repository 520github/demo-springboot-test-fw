package org.sunso.demo.statemachine.bill.statemachine.common.context;

import org.sunso.demo.statemachine.bill.entity.Bill;
import org.sunso.demo.statemachine.bill.entity.Order;
import org.sunso.statemachine.context.Context;

public class BillContext implements Context {
    private Bill bill;
    private Order order;

    public Bill getBill() {
        return bill;
    }

    public BillContext setBill(Bill bill) {
        this.bill = bill;
        return this;
    }

    public Order getOrder() {
        return order;
    }

    public BillContext setOrder(Order order) {
        this.order = order;
        return this;
    }

    @Override
    public String toString() {
        return "BillContext{" + "bill=" + bill + ", order=" + order + '}';
    }
}
