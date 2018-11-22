package android.example.com.split.data.entity;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Group
 */
@IgnoreExtraProperties
public class Group implements Serializable {

    /**
     * Group ID
     */
    private String groupId;
    /**
     * Group's name
     */
    private String name;
    /**
     * Members ({@link User}s) in the group
     */
    private List<User> members;

    /**
     * {@link Expense}s in the group
     */
    private List<Expense> expenses;


    public Group() {

        // Initialise members and expenses lists
        members = new ArrayList<>();
        expenses = new ArrayList<>();
    }

    public Group(String groupId, String name) {
        this.groupId = groupId;
        this.name = name;
    }

    @Exclude
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // return the list of group members

    public List<User> getMembers() {
        return members;
    }


    // return the list of group expenses
    public List<Expense> getExpenses() {
        return expenses;
    }


    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    /**
     * Add a user as member to the group
     */

    public void addMember(User member) {
        members.add(member);
    }
}
