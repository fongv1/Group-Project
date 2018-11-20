package android.example.com.split.data.repository;

import android.example.com.split.data.entity.Group;
import android.util.Log;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class GroupDataRepository {

    private static final String TAG = "DataRepository";


    public void createNewGroup(Group group) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("groups").add(group).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "onSuccess: " + documentReference.getId());
            }
        });
    }
}
