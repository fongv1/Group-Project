package com.alaskalany.lib.model;

public interface Share {

  boolean isPaid();

  void setPaid(boolean paid);

  double getAmount();

  void setAmount(double shareAmount);

  String getUserId();

  void setUserId(String userId);
}
