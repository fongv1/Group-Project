package android.example.com.split.data.entity;

import android.example.com.split.data.model.Expenses;
import android.example.com.split.data.model.Id;
import android.example.com.split.data.model.Members;
import android.example.com.split.data.model.Name;

import java.util.ArrayList;
import java.util.List;

/**
 * Group
 */
public class Group implements Id, Name, Members, Expenses {

    /**
     * Group ID
     */
    private String id;
    /**
     * Group's name
     */
    private String name;
    /**
     * Members ({@link User}s) in the group
     */
    private List<Id> members;
    /**
     * {@link Expense}s in the group
     */
    private List<Expense> expenses;

    /**
     * Group
     */
    public Group() {
        // Initialise members and expenses lists
        members = new ArrayList<>();
        expenses = new ArrayList<>();
    }

    /**
     * Get all member users in the group
     *
     * @return {@link List<User>} list of member users in the group
     */
    @Override
    public List<Id> getMembers() {
        return members;
    }

    /**
     * Get all expenses in the group
     *
     * @return {@link List<Expense>} list of expenses in the group
     */
    @Override
    public List<Expense> getExpenses() {
        return expenses;
    }

    /**
     * Get the group ID
     *
     * @return Group ID
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Set the group ID
     *
     * @param id Group ID
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the name of the group
     *
     * @return Group name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Set the name of the group
     *
     * @param name Group name
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Add a group expense
     *
     * @param expense Group expense
     */
    @Override
    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    /**
     * Add a user as member to the group
     *
     * @param id User to become member of the group
     */
    @Override
    public void addMember(Id id) {
        members.add(id);
    }
}
