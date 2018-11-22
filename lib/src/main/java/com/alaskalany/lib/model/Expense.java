package com.alaskalany.lib.model;

public interface Expense {

    String getId();

    void setId(String id);

    String getPayeeId();

    void setPayeeId(String payeeId);

    String getPayeeName();

    void setPayeeName(String payeeName);

    double getPaymentAmount();

    void setPaymentAmount(double paymentAmount);

    String getTittle();

    void setTittle(String tittle);
}
