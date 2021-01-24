package com.bigdata.batch.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
//@Table(name = "transactions")
public class Transaction {
    //this is POJO that models the fields of a TX
    //could add in columns

    @Column(name="step")
    private Integer step;
    @Column(length = 10, name="type")
    private String type;
    @Column(name="amount")
    private BigDecimal amount;
    @Column(length = 32, name = "nameorig")
    private String nameOrig;
    @Column(name="oldbalanceorg")
    private BigDecimal oldBalanceOrg;
    @Column(name="newbalanceorig")
    private BigDecimal newBalanceOrig;
    @Column(length = 32, name = "namedest")
    private String nameDest;
    @Column(name="oldbalancedest")
    private BigDecimal oldBalanceDest;
    @Column(name="newbalancedest")
    private BigDecimal newBalanceDest;
    @Column(name="isfraud")
    private Integer isFraud;
    @Column(name="isflaggedfraud")
    private Integer isFlaggedFraud;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public Transaction() {}

    public Transaction(Integer step, String type, BigDecimal amount, String nameOrig,
                   BigDecimal oldBalanceOrig, BigDecimal newBalanceOrig, String nameDest, BigDecimal oldBalanceDest,
                   BigDecimal newBalanceDest, Integer isFraud, Integer isFlaggedFraud) {
                super();
                this.step = step;
                this.type = type;
                this.amount = amount;
                this.nameOrig = nameOrig;
                this.oldBalanceOrg = oldBalanceOrig;
                this.newBalanceOrig = newBalanceOrig;
                this.nameDest = nameDest;
                this.oldBalanceDest = oldBalanceDest;
                this.newBalanceDest = newBalanceDest;
                this.isFraud = isFraud;
                this.isFlaggedFraud = isFlaggedFraud;

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
