package android.example.com.split.data.entity;

import java.io.Serializable;

public class Expense implements Serializable {

    /**
     * Expense ID
     */
    @com.google.firebase.firestore.Exclude
    private String id;
    /**
     * User who paid for the expense
     */
    private String payerId;

    private String payerName;
    /**
     * The amount of the payment made for the expense
     */
    private double paymentAmount;

    /**
     * Expense tittle
     */
    private String tittle;

   @com.google.firebase.firestore.Exclude
    private User user;

    public Expense() {

    }

    public Expense(String id, String payerId, double paymentAmount, String tittle , String payerName) {

        this.id = id;
        this.payerId = payerId;
        this.paymentAmount = paymentAmount;
        this.tittle = tittle;
        this.payerName = payerName;
    }

    // for test only
    public Expense( String payerId, double paymentAmount, String tittle , String payerName) {

        this.payerId = payerId;
        this.paymentAmount = paymentAmount;
        this.tittle = tittle;
        this.payerName = payerName;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    @com.google.firebase.firestore.Exclude
    public User getUser() {
        return user;
    }
    @com.google.firebase.firestore.Exclude
    public void setUser(User user) {
        this.user = user;
    }
}
