package com.bigdata.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class Transaction {
    //this is POJO that models the fields of a TX
    private int step;
    private String type;
    private BigDecimal amount;
    private String nameOrig;
    private BigDecimal oldBalanceOrg;
    private BigDecimal newBalanceOrig;
    private String nameDest;
    private BigDecimal oldBalanceDest;
    private BigDecimal newBalanceDest;
    private Boolean isFraud;
    private Boolean isFlaggedFraud;
    //this is a test

    public Transaction() {}

    public Transaction(int step, String type, BigDecimal amount, String nameOrig, BigDecimal oldBalanceOrg, BigDecimal newBalanceOrig, String nameDest,
                BigDecimal oldBalanceDest, BigDecimal newBalanceDest, Boolean isFraud, Boolean isFlaggedFraud
    ) {
        this.step = step;
        this.type = type;
        this.amount = amount;
        this.nameOrig = nameOrig;
        this.oldBalanceOrg = oldBalanceOrg;
        this.newBalanceOrig = newBalanceOrig;
        this.nameDest = nameDest;
        this.oldBalanceDest = oldBalanceDest;
        this.newBalanceDest = newBalanceDest;
        this.isFraud = isFraud;
        this.isFlaggedFraud = isFlaggedFraud;
    }


    public Boolean getFlaggedFraud() {
        return isFlaggedFraud;
    }

    public void setFlaggedFraud(Boolean flaggedFraud) {
        isFlaggedFraud = flaggedFraud;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getNameOrig() {
        return nameOrig;
    }

    public void setNameOrig(String nameOrig) {
        this.nameOrig = nameOrig;
    }

    public BigDecimal getOldBalanceOrg() {
        return oldBalanceOrg;
    }

    public void setOldBalanceOrg(BigDecimal oldBalanceOrg) {
        this.oldBalanceOrg = oldBalanceOrg;
    }

    public BigDecimal getNewBalanceOrig() {
        return newBalanceOrig;
    }

    public void setNewBalanceOrig(BigDecimal newBalanceOrig) {
        this.newBalanceOrig = newBalanceOrig;
    }

    public BigDecimal getOldBalanceDest() {
        return oldBalanceDest;
    }

    public void setOldBalanceDest(BigDecimal oldBalanceDest) {
        this.oldBalanceDest = oldBalanceDest;
    }

    public BigDecimal getNewBalanceDest() {
        return newBalanceDest;
    }

    public void setNewBalanceDest(BigDecimal newBalanceDest) {
        this.newBalanceDest = newBalanceDest;
    }

    public Boolean getFraud() {
        return isFraud;
    }

    public void setFraud(Boolean fraud) {
        isFraud = fraud;
    }

    public String getNameDest() {
        return nameDest;
    }

    public void setNameDest(String nameDest) {
        this.nameDest = nameDest;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}
