package android.example.com.split.data.entity;

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
  private List<String> members;

  @com.google.firebase.firestore.Exclude
  private List<User> userMembers;

  /**
   * {@link Expense}s in the group
   */
  @com.google.firebase.firestore.Exclude
  private List<Expense> expenses;


  public List<Double> getMembersBalance() {
    return membersBalance;
  }

  public void setMembersBalance(List<Double> membersBalance) {
    this.membersBalance = membersBalance;
  }

  private List<Double> membersBalance;


  public Group() {

    // Initialise members and expenses lists
    members = new ArrayList<>();
    membersBalance = new ArrayList<>();
    expenses = new ArrayList<>();
    userMembers = new ArrayList<>();
  }

  public Group(String groupId, String name) {
    this.groupId = groupId;
    this.name = name;
    members = new ArrayList<>();
    membersBalance = new ArrayList<>();
    expenses = new ArrayList<>();
    userMembers = new ArrayList<>();
  }

  @com.google.firebase.firestore.Exclude
  public List<User> getUserMembers() {
    return userMembers;
  }


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
  public List<String> getMembers() {
    return members;
  }

  // return the list of group members


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


  public void addMember(String member) {
    members.add(member);
  }

  @com.google.firebase.firestore.Exclude
  public void addUserMember(User user) {
    userMembers.add(user);
  }

  @com.google.firebase.firestore.Exclude
  public void setExpenses(List<Expense> expenseList) {
    expenses = expenseList;
  }
}
