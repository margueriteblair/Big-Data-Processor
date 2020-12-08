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
}
