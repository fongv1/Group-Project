package com.alaskalany.lib.model;

import java.util.List;

public interface Group {

    String getGroupId();

    void setGroupId(String groupId);

    String getName();

    void setName(String name);

    List<String> getMembers();

    // return the list of group expenses
    List<Expense> getExpenses();

    void addExpense(Expense Expense);

    void addMember(String memberId);
}
