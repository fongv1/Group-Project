package android.example.com.split.data.repository;

import android.example.com.split.data.entity.Group;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class GroupDataRepository extends Repository<Group> {

    public static final String SUCCESS = "success";
    private FirebaseFirestore db;
    private static final String TAG = "DataRepository";
    public static final String GROUP_ID = "group-id";
    public static final String USERS_LIST = "users-list";

    // create new group
    @Override
    public void createItem(Group group, final Handler.Callback listener) {
        db = FirebaseFirestore.getInstance();
        final String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("groups").add(group).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    DocumentReference documentReference = task.getResult();
                    String documentId = documentReference.getId();

                    // add the created user to the group
                    addCurrentMember(documentId, user);

                    Message message = new Message();
                    final Bundle data = new Bundle();
                    data.putBoolean(SUCCESS, true);
                    data.putString(GROUP_ID, documentId);

                    /*ArrayList<User> users = new ArrayList<>();
                    users.add(new User());
                    data.putSerializable(USERS_LIST, users);*/

                    message.setData(data);
                    listener.handleMessage(message);
                } else {
                    Message message = new Message();
                    final Bundle data = new Bundle();
                    data.putBoolean(SUCCESS, false);
                    message.setData(data);
                    listener.handleMessage(message);
                }
            }
        });
    }

    private void addCurrentMember(String documentId, String user) {
        db.collection("groups").document(documentId)
          .update("members", FieldValue.arrayUnion(user));
    }

    @Override
    public void getItem(String itemId) {

    }

    @Override
    public void updateItem(String itemId) {

    }

    // add a member to a group
    public void addGroupMember(String groupId , String memberId, final Handler.Callback listener){
        db = FirebaseFirestore.getInstance();
        DocumentReference groupDocument = db.collection("groups").document(groupId);
        // add new member to this group
        groupDocument.update("members", FieldValue.arrayUnion(memberId)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
              if (task.isSuccessful()){
                  Message message = new Message();
                  final Bundle data = new Bundle();
                  data.putBoolean(SUCCESS, true);
                  message.setData(data);
                  listener.handleMessage(message);
              } else{
                  Message message = new Message();
                  final Bundle data = new Bundle();
                  data.putBoolean(SUCCESS, false);
                  message.setData(data);
                  listener.handleMessage(message);
              }
            }
        });
    }

    // add expense to a group

    public void addGroupExpense(String groupId , Map<String, Object> expense , final Handler.Callback listener){
        db = FirebaseFirestore.getInstance();
        DocumentReference groupDocument = db.collection("groups").document(groupId);
        // add new expense to this group
        groupDocument.update("expenses",expense).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Message message = new Message();
                    final Bundle data = new Bundle();
                    data.putBoolean(SUCCESS, true);
                    message.setData(data);
                    listener.handleMessage(message);
                } else{
                    Message message = new Message();
                    final Bundle data = new Bundle();
                    data.putBoolean(SUCCESS, false);
                    message.setData(data);
                    listener.handleMessage(message);
                }
            }
        });

    }
     // get the list of user's groups
    public void getGroupList(String userId){
        db = FirebaseFirestore.getInstance();
        db.collection("groups").whereArrayContains("members",userId).get();
    }

}
