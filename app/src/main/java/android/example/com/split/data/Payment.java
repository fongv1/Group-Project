package android.example.com.split.data;

interface Payment {

    boolean isPaid();

    void setPaid(boolean paid);

    double getAmount();

    void setAmount(double shareAmount);
}
