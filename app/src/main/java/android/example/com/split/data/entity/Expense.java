package android.example.com.split.data.entity;

import java.io.Serializable;

public class Expense implements Serializable {

    /**
     * Expense ID
     */
    private String id;
    /**
     * User who paid for the expense
     */
    private String payeeId;

    private String payeeName;
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


    public Expense(String id, String payeeId, double paymentAmount, String tittle, String payeeName) {

        this.id = id;
        this.payeeId = payeeId;
        this.paymentAmount = paymentAmount;
        this.tittle = tittle;
        this.payeeName = payeeName;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(String payeeId) {
        this.payeeId = payeeId;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
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
