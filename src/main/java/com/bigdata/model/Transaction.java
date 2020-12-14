package com.bigdata.model;


import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "transactions")
public class Transaction {
    //this is POJO that models the fields of a TX
    //could add in columns
    private int step;
    private String type;
    private BigDecimal amount;
    private String nameOrig;
    private BigDecimal oldBalanceOrg;
    private BigDecimal newBalanceOrig;
    private String nameDest;
    private BigDecimal oldBalanceDest;
    private BigDecimal newBalanceDest;
    private int isFraud;
    private int isFlaggedFraud;
    //this is a test

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
}
