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
}
