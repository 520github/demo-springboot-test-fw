package org.sunso.demo.statemachine.bill.entity;

import java.math.BigDecimal;
import java.util.Date;

public class RepayPlan {
    private int planId;
    private BigDecimal shouldRepayAmount;
    private BigDecimal alreadyRepayAmount;
    private Date actualRepayDate;
    private Date shouldRepayDate;
    private String repayStatus;

    public static RepayPlan create() {
        return new RepayPlan();
    }

    public int getPlanId() {
        return planId;
    }

    public RepayPlan setPlanId(int planId) {
        this.planId = planId;
        return this;
    }

    public BigDecimal getShouldRepayAmount() {
        return shouldRepayAmount;
    }

    public RepayPlan setShouldRepayAmount(BigDecimal shouldRepayAmount) {
        this.shouldRepayAmount = shouldRepayAmount;
        return this;
    }

    public BigDecimal getAlreadyRepayAmount() {
        return alreadyRepayAmount;
    }

    public RepayPlan setAlreadyRepayAmount(BigDecimal alreadyRepayAmount) {
        this.alreadyRepayAmount = alreadyRepayAmount;
        return this;
    }

    public Date getActualRepayDate() {
        return actualRepayDate;
    }

    public RepayPlan setActualRepayDate(Date actualRepayDate) {
        this.actualRepayDate = actualRepayDate;
        return this;
    }

    public Date getShouldRepayDate() {
        return shouldRepayDate;
    }

    public RepayPlan setShouldRepayDate(Date shouldRepayDate) {
        this.shouldRepayDate = shouldRepayDate;
        return this;
    }

    public String getRepayStatus() {
        return repayStatus;
    }

    public RepayPlan setRepayStatus(String repayStatus) {
        this.repayStatus = repayStatus;
        return this;
    }
}
