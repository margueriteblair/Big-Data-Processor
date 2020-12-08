package com.bigdata.model;

import java.math.BigDecimal;

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
}
