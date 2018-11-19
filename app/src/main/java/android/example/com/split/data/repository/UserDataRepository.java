package android.example.com.split.data.repository;

import android.example.com.split.data.entity.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserDataRepository {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void createNewUser(User user) {

        db.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

            }
        });

    }

}
