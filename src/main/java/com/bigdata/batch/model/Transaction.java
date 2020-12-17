package com.bigdata.batch.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "transactions")
public class Transaction {
    //this is POJO that models the fields of a TX
    //could add in columns
    @Column(name="step")
    private int step;
    @Column(name="type")
    private String type;
    @Column(name="amount")
    private BigDecimal amount;
    @Column(name="nameorig")
    private String nameOrig;
    @Column(name="oldbalanceorg")
    private BigDecimal oldBalanceOrg;
    @Column(name="newbalanceorig")
    private BigDecimal newBalanceOrig;
    @Column(name="namedest")
    private String nameDest;
    @Column(name="oldbalancedest")
    private BigDecimal oldBalanceDest;
    @Column(name="newbalancedest")
    private BigDecimal newBalanceDest;
    @Column(name="isfraud")
    private int isFraud;
    @Column(name="isflaggedfraud")
    private int isFlaggedFraud;
    @Id
    @GeneratedValue
    public Long id;

    public Transaction() {}

    public Transaction(String data) {
        String[] txData = data.split(",");
        this.step = Integer.parseInt(txData[0]);
        this.type = txData[1];
        this.amount = new BigDecimal(txData[2]);
        this.nameOrig = txData[3];
        this.oldBalanceOrg = new BigDecimal(txData[4]);
        this.newBalanceOrig = new BigDecimal(txData[5]);
        this.nameDest = txData[6];
        this.oldBalanceDest = new BigDecimal(txData[7]);
        this.newBalanceDest = new BigDecimal(txData[8]);
        this.isFraud = Integer.parseInt(txData[9]);
        this.isFlaggedFraud = Integer.parseInt(txData[10]);
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

    public int getIsFraud() {
        return isFraud;
    }

    public void setIsFraud(int isFraud) {
        this.isFraud = isFraud;
    }

    public int getIsFlaggedFraud() {
        return isFlaggedFraud;
    }

    public void setIsFlaggedFraud(int isFlaggedFraud) {
        this.isFlaggedFraud = isFlaggedFraud;
    }
}
