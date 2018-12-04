package android.example.com.split.data.model;

public interface Payment {


  void setSetteled(boolean setteled);

  void setPaidAmount(double paidAmount);

  double getPaidAmount();

  double getTotalShare();

  void setTotalShare(double shareAmount);
}
