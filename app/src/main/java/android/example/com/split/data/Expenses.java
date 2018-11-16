package android.example.com.split.data;

import java.util.List;

interface Expenses {

    List<Expense> getExpenses();

    void addExpense(Expense expense);
}
