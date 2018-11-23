package android.example.com.split.data.model;

import android.example.com.split.data.entity.Expense;

import java.util.List;

public interface Expenses {

  List<Expense> getExpenses();

  void addExpense(Expense expense);
}
