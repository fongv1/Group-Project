package android.example.com.split.data.entity;

import android.example.com.split.data.model.Payment;

import java.io.Serializable;

/**
 * Share
 */
class Share implements Payment, Serializable, com.alaskalany.lib.model.Share {

  /**
   * True if the share is paid
   */
  private boolean paid;
  /* Share amount */
  private double shareAmount;
  /**
   * The {@link User} ID associated to this share
   */
  private String userId;

  /**
   * Share
   */
  public Share() {
  }

  /**
   * Check if share is paid
   *
   * @return boolean True if the share is paid
   */
  @Override
  public boolean isPaid() {
    return paid;
  }

  /**
   * Set share is paid
   *
   * @param paid True to set the share as paid
   */
  @Override
  public void setPaid(boolean paid) {
    this.paid = paid;
  }

  /**
   * Get share amount
   *
   * @return double the amount of the share
   */
  @Override
  public double getAmount() {
    return shareAmount;
  }

  /**
   * Set share amount
   *
   * @param shareAmount Set the share amount
   */
  @Override
  public void setAmount(double shareAmount) {
    this.shareAmount = shareAmount;
  }

  /**
   * Get user ID
   *
   * @return Get the {@link User} the ID
   */
  @Override
  public String getUserId() {
    return userId;
  }

  /**
   * Set user ID
   *
   * @param userId Set the {@link User} ID
   */
  @Override
  public void setUserId(String userId) {
    this.userId = userId;
  }
}
