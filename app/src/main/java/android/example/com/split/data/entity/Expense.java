package android.example.com.split.data.entity;

import android.example.com.split.data.model.Id;
import android.example.com.split.data.model.Payment;

import java.util.ArrayList;
import java.util.List;

/**
 * Expense in a {@link Group}
 */
public class Expense implements Id {

    /**
     * Expense ID
     */
    private String id;
    /**
     * Group ID
     */
    private String groupId;
    /**
     * User who paid for the expense
     */
    private String payee;
    /**
     * The amount of the payment made for the expense
     */
    private double paymentAmount;
    /**
     * The name of the payment
     */
    private String paymentName;
    /**
     * The amount left to be paid
     */
    private double unpaidTotal;
    /**
     * The shares for all {@link User} members in a group
     */
    private List<Payment> shares;

    /**
     * Expense
     */
    public Expense() {
        shares = new ArrayList<>();
    }

    /**
     * Get the expense ID
     *
     * @return Expense ID
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Set the expense ID
     *
     * @param id Expense ID
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the expense's group ID
     *
     * @return {@link String} Group ID
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * Set the expense's group ID
     *
     * @param groupId
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * Get the user ID for the user who paid for the expense
     *
     * @return
     */
    public String getPayee() {
        return payee;
    }

    /**
     * Set the user ID for the user who paid for the group
     *
     * @param payee The user ID for the user who paid for the expense
     */
    public void setPayee(String payee) {
        this.payee = payee;
    }

    /**
     * Get payment amount
     *
     * @return double Payment amount
     */
    public double getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * Set the payment amount
     *
     * @param paymentAmount Payment amount
     */
    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    /**
     * Get the payment name
     *
     * @return Payment name
     */
    public String getPaymentName() {
        return paymentName;
    }

    /**
     * Set the payment name
     *
     * @param paymentName Payment name
     */
    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    /**
     * Get the amount unpaid of the expense
     *
     * @return The amount of the expense still unpaid
     */
    public double getUnpaidTotal() {
        return unpaidTotal;
    }

    /**
     * Set the unpaid amount of the expense
     *
     * @param unpaidTotal The amount of the expense still unpaid
     */
    public void setUnpaidTotal(double unpaidTotal) {
        this.unpaidTotal = unpaidTotal;
    }

    /**
     * Get expense shares
     *
     * @return List of members' shares
     */
    public List<Payment> getShares() {
        return shares;
    }
}
