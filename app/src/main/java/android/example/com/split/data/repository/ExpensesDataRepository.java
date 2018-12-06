package android.example.com.split.data.repository;

import android.example.com.split.data.entity.Expense;
import android.example.com.split.data.entity.Group;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExpensesDataRepository {

    public static final String SUCCESS = "success";
    public static final String EXPENSE_ID = "expense-id";
    public static final String UPDATE_SUCCESS = "update-success";
  public static final String EXPENSE_LIST = "expense-list";
  public static final String EXPENSE = "expense";
  private FirebaseFirestore db;

    // add expense to a group
    public void addExpenses(final String groupId , final Expense expense , final Handler.Callback listener){
     db = FirebaseFirestore.getInstance();
     db.collection("groups").document(groupId).collection("expenses").add(expense)
       .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
           @Override
           public void onComplete(@NonNull Task<DocumentReference> task) {
             if (task.isSuccessful()){
                 DocumentReference documentReference = task.getResult();
                 String documentId = documentReference.getId();
                 // save doc id into the share document
                  db.collection("groups").document(groupId).collection("expenses").document(documentId)
                    .update("id",documentId);
                  // update group balance
                 updateGroupBalance(groupId ,expense);
                 // After adding new expense by default a new share will be added !!


                 Message message = new Message();
                 Bundle data = new Bundle();
                 data.putBoolean(SUCCESS,true);
                 data.putString(EXPENSE_ID,documentId);
                 message.setData(data);
                 listener.handleMessage(message);

             }
             else {
                 Message message = new Message();
                 Bundle data = new Bundle();
                 data.putBoolean(SUCCESS,false);
                 message.setData(data);
                 listener.handleMessage(message);

             }

           }
       });
    }

    private void updateGroupBalance(final String groupId , final Expense expense){
      db = FirebaseFirestore.getInstance();
      db.collection("groups").document(groupId).get()
        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
          @Override
          public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if(task.isSuccessful()){
              Group group = task.getResult().toObject(Group.class);
              double balance = group.getGroupBalance();
              db.collection("groups").document(groupId).update("groupBalance",balance + expense.getPaymentAmount());
            }
          }
        });

    }

    // get the list of expenses in a group
    public void getExpensesList(String groupId , final Handler.Callback listener){
        db = FirebaseFirestore.getInstance();
        final List<Expense> expenseList = new ArrayList<>();
        db.collection("groups").document(groupId).collection("expenses").get()
          .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
              @Override
              public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                  Message message = new Message();
                  Bundle data = new Bundle();
                  if (!queryDocumentSnapshots.isEmpty()){
                      for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                          Expense expense = documentSnapshot.toObject(Expense.class);
                          expense.setId(documentSnapshot.getId());
                          expenseList.add(expense);

                      }
                      data.putBoolean(SUCCESS, true);
                      data.putSerializable(EXPENSE_LIST, (Serializable) expenseList);
                      message.setData(data);
                      listener.handleMessage(message);
                  }
                  else {
                      data.putBoolean(SUCCESS, true);
                      message.setData(data);
                      listener.handleMessage(message);
                  }
              }
          }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Message message = new Message();
                Bundle data = new Bundle();
                data.putBoolean(SUCCESS, true);
                message.setData(data);
                listener.handleMessage(message);

            }
        });
    }

    // fetch one expense
    public void fetchExpense(String groupId , String expenseId , final Handler.Callback listener){
      db = FirebaseFirestore.getInstance();
      db.collection("groups").document(groupId).collection("expenses").document(expenseId)
        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
          Message message = new Message();
          final Bundle data = new Bundle();
          if(task.isSuccessful()){
            Expense expense = task.getResult().toObject(Expense.class);
            expense.setId(task.getResult().getId());

            data.putBoolean(SUCCESS, true);
            data.putSerializable(EXPENSE, expense);
            message.setData(data);
            listener.handleMessage(message);
          }
          else {
            data.putBoolean(SUCCESS, false);
            message.setData(data);
            listener.handleMessage(message);
          }
        }
      }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
          Message message = new Message();
          Bundle data = new Bundle();
          data.putBoolean(SUCCESS, false);
          message.setData(data);
          listener.handleMessage(message);
        }
      });
    }

    // update expense in a group expenses list
    public void updateExpenses(String groupId , Expense expense , final Handler.Callback listener){
        db = FirebaseFirestore.getInstance();
        String expenseId = expense.getId();

        db.collection("groups").document(groupId).collection("expenses").document(expenseId)
          .set(expense).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Message message = new Message();
                final Bundle data = new Bundle();
                if (task.isSuccessful()){
                    data.putBoolean(UPDATE_SUCCESS, true);
                    message.setData(data);
                    listener.handleMessage(message);
                }
                else {
                    data.putBoolean(UPDATE_SUCCESS, false);
                    message.setData(data);
                    listener.handleMessage(message);

                }
            }
        });

    }


  // remove expense

  public void removeExpense(String groupId , String expenseId , final Handler.Callback listener ){
    db = FirebaseFirestore.getInstance();
    db.collection("groups").document(groupId).collection("expenses").document(expenseId)
      .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
      @Override
      public void onComplete(@NonNull Task<Void> task) {
        Message message = new Message();
        Bundle data = new Bundle();
        if(task.isSuccessful()) {
          data.putBoolean(SUCCESS, true);
          message.setData(data);
          listener.handleMessage(message);
        }
        else {
          data.putBoolean(SUCCESS, false);
          message.setData(data);
          listener.handleMessage(message);
        }
      }
    }).addOnFailureListener(new OnFailureListener() {
      @Override
      public void onFailure(@NonNull Exception e) {
        Message message = new Message();
        Bundle data = new Bundle();
        data.putBoolean(SUCCESS, false);
        message.setData(data);
        listener.handleMessage(message);
      }
    });

  }

  // delete all the expenses in a group

  public void deleteAllExpenses(String groupId  , final Handler.Callback listener ){
    db = FirebaseFirestore.getInstance();
    db.collection("groups").document(groupId).collection("expenses").document()
      .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
      @Override
      public void onComplete(@NonNull Task<Void> task) {
        Message message = new Message();
        Bundle data = new Bundle();
        if(task.isSuccessful()) {
          data.putBoolean(SUCCESS, true);
          message.setData(data);
          listener.handleMessage(message);
        }
        else {
          data.putBoolean(SUCCESS, false);
          message.setData(data);
          listener.handleMessage(message);
        }
      }
    }).addOnFailureListener(new OnFailureListener() {
      @Override
      public void onFailure(@NonNull Exception e) {
        Message message = new Message();
        Bundle data = new Bundle();
        data.putBoolean(SUCCESS, false);
        message.setData(data);
        listener.handleMessage(message);
      }
    });

  }

}
