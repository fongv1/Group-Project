package android.example.com.split.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Group
 */
public class Group {

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
    public List<User> getMembers() {
        return members;
    }

    /**
     * Get all expenses in the group
     *
     * @return {@link List<Expense>} list of expenses in the group
     */
    public List<Expense> getExpenses() {
        return expenses;
    }

    /**
     * Get the group ID
     *
     * @return Group ID
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * Set the group ID
     *
     * @param groupId Group ID
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * Get the name of the group
     *
     * @return Group name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the group
     *
     * @param name Group name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Add a group expense
     *
     * @param expense Group expense
     */
    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    /**
     * Add a user as member to the group
     *
     * @param user User to become member of the group
     */
    public void addMember(User user) {
        members.add(user);
    }
}
