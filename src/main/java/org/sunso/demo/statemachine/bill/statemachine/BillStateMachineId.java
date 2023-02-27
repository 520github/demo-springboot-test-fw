package org.sunso.demo.statemachine.bill.statemachine;

import org.sunso.statemachine.StateMachineID;

public enum BillStateMachineId implements StateMachineID {
    billStateMachine("billStateMachine", "billStateMachine");

    String id;
    String name;

    BillStateMachineId(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
