package com.alaskalany.lib.model;

import java.util.List;

public interface IGroup {

    String getGroupId();

    void setGroupId(String groupId);

    String getName();

    void setName(String name);

    List<String> getMembers();

    // return the list of group expenses
    List<IExpense> getExpenses();

    void addExpense(IExpense IExpense);

    void addMember(String memberId);
}
