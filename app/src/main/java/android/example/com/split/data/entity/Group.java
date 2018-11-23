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

    // Initialise memberUsers and expenses lists
    /**
     * Members ({@link User}s) in the group
     */
    private List<String> members = new ArrayList<>();
    /**
     * Members ({@link User}s) in the group
     */
    @com.google.firebase.firestore.Exclude
    private List<User> memberUsers = new ArrayList<>();

    /**
     * {@link Expense}s in the group
     */
    @com.google.firebase.firestore.Exclude
    private List<Expense> expenses = new ArrayList<>();

    /**
     * Empty Constructor needed by Firestore
     */
    public Group() {

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

    public List<String> getMembers() {
        return members;
    }

    public void addMember(String memberId) {
        members.add(memberId);
    }

    // return the list of group memberUsers
    @com.google.firebase.firestore.Exclude
    public List<User> getMemberUsers() {
        return memberUsers;
    }


    // return the list of group expenses
    @com.google.firebase.firestore.Exclude
    public List<Expense> getExpenses() {
        return expenses;
    }

    @com.google.firebase.firestore.Exclude
    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    /**
     * Add a user as member to the group
     */
    @com.google.firebase.firestore.Exclude
    public void addMember(User member) {
        addMember(member.getId());
        memberUsers.add(member);
    }
}
