package android.example.com.split.data.entity;

import com.alaskalany.lib.model.IExpense;

import java.io.Serializable;

public class Expense implements Serializable, IExpense {

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


    public Expense() {

    }




    public Expense(String id, String payeeId, double paymentAmount, String tittle , String payeeName) {

        this.id = id;
        this.payeeId = payeeId;
        this.paymentAmount = paymentAmount;
        this.tittle = tittle;
        this.payeeName = payeeName;
    }


    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getPayeeId() {
        return payeeId;
    }

    @Override
    public void setPayeeId(String payeeId) {
        this.payeeId = payeeId;
    }

    @Override
    public String getPayeeName() {
        return payeeName;
    }

    @Override
    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    @Override
    public double getPaymentAmount() {
        return paymentAmount;
    }

    @Override
    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    @Override
    public String getTittle() {
        return tittle;
    }

    @Override
    public void setTittle(String tittle) {
        this.tittle = tittle;
    }
}
