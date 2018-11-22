package android.example.com.split.data.entity;

import com.alaskalany.lib.model.IExpense;
import com.alaskalany.lib.model.IGroup;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Group
 */
@IgnoreExtraProperties
public class Group implements Serializable, IGroup {

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
    private List<String> members;

    /**
     * {@link Expense}s in the group
     */
    private List<IExpense> expens;


    public Group() {

        // Initialise members and expenses lists
        members = new ArrayList<>();
        expens = new ArrayList<>();
    }

    public Group(String groupId, String name) {
        this.groupId = groupId;
        this.name = name;
    }

    @Override
    @Exclude
    public String getGroupId() {
        return groupId;
    }

    @Override
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    // return the list of group members

    @Override
    public List<String> getMembers() {
        return members;
    }


    // return the list of group expenses
    @Override
    public List<IExpense> getExpenses() {
        return expens;
    }


    @Override
    public void addExpense(IExpense IExpense) {
        expens.add(IExpense);
    }

    /**
     * Add a user as member to the group
     */

    @Override
    public void addMember(String memberId) {
        members.add(memberId);
    }
}
