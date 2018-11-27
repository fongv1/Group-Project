package android.example.com.split.data.repository;

import android.example.com.split.data.entity.Expense;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExpensesDataRepository {

    public static final String SUCCESS = "success";
    public static final String EXPENSE_ID = "expense-id";
    public static final String UPDATE_SUCCESS = "update-success";
    private FirebaseFirestore db;

    // add expense to a group
    public void addExpenses(final String groupId , Expense expense , final Handler.Callback listener){
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
                      data.putSerializable("expense-list", (Serializable) expenseList);
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

}
