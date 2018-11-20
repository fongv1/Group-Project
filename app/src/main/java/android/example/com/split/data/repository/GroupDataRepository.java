package android.example.com.split.data.repository;

import android.example.com.split.data.entity.Group;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class GroupDataRepository extends Repository<Group> {

    private static final String TAG = "DataRepository";

    @Override
    void createItem(Group group, final Handler.Callback listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("groups").add(group).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    DocumentReference documentReference = task.getResult();
                    String documentId = documentReference.getId();
                    Message message = new Message();
                    final Bundle data = new Bundle();
                    data.putBoolean("success", false);
                    data.putString("group-id", documentId);
                    message.setData(data);
                    listener.handleMessage(message);
                } else {
                    Message message = new Message();
                    final Bundle data = new Bundle();
                    data.putBoolean("success", false);
                    message.setData(data);
                    listener.handleMessage(message);
                }
            }
        });
    }

    @Override
    void getItem(String itemId) {

    }

    @Override
    void updateItem(String itemId) {

    }
}
