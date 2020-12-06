package com.bigdata.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Transaction {
    //this transaction data is whats going to be going into our database so we'll have to map it out
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer step;

    @Column(length = 10)
    private String type;
    private BigDecimal amount;

    @Column(length=32)
    private String nameOrig;

    private BigDecimal oldBalanceOrig;
    private BigDecimal newBalanceOrig;

    @Column(length=32)
    private String nameDestination;
    private BigDecimal oldBalanceDestination;
    private BigDecimal newBalanceDestination;
    private boolean isFraud;
    private boolean isFlaggedFraud;

    public Transaction() {}

    public Transaction(Integer step, String type, BigDecimal amount, String nameOrig, BigDecimal oldBalanceOrig, BigDecimal newBalanceOrig, String nameDestination, BigDecimal oldBalanceDestination, BigDecimal newBalanceDestination, boolean isFraud, boolean isFlaggedFraud) {
        super();
        this.step = step;
        this.type = type;
        this.amount = amount;
        this.nameOrig = nameOrig;
        this.oldBalanceOrig = oldBalanceOrig;
        this.newBalanceOrig = newBalanceOrig;
        this.nameDestination = nameDestination;
        this.oldBalanceDestination = oldBalanceDestination;
        this.newBalanceDestination = newBalanceDestination;
        this.isFraud = isFraud;
        this.isFlaggedFraud = isFlaggedFraud;
    }
}
