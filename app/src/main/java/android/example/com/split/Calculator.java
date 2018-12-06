package android.example.com.split;

import android.example.com.split.data.entity.Expense;
import android.example.com.split.data.entity.Group;
import android.example.com.split.data.entity.User;

public class Calculator {

  public static double getTotalGroupExpenses(Group group) {
    double totalBalance = 0;
    for (Expense expense : group.getExpenses()) {
      totalBalance = totalBalance + expense.getPaymentAmount();
    }
    return totalBalance;
  }

  public static double getExpensesPerMember(Group group) {
    double memberBalance = getTotalGroupExpenses(group) / group.getMembers().size();
    return memberBalance;
  }

  public static double getMemberBalance(Group group, User user) {
    double expensesPerMember = getExpensesPerMember(group);
    double memberTotalExpense = 0;
    for (Expense expense : group.getExpenses()) {
      if (expense.getPayerId().equals(user.getId())) {
        memberTotalExpense += expense.getPaymentAmount();
      }
    }
    double memberBalance = memberTotalExpense - expensesPerMember;
    return memberBalance;
  }
}
