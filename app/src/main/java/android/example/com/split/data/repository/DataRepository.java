package android.example.com.split.data.repository;

import android.example.com.split.data.entity.User;
import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class DataRepository {

    public void getUser() {

    }

    public void createNewUser(User user) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(user.getId()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                } else {
                }
            }
        });
    }

    public void getUserById(String id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

            }
        });
    }


    //        FirebaseAuth auth = FirebaseAuth.getInstance();
    //        auth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    //            @Override
    //            public void onComplete(@NonNull Task<AuthResult> task) {
    //                if (task.isSuccessful()) {
    //                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    //
    //                    User myUser = new User();
    //                    myUser.setId(user.getUid());
    //                    myUser.setEmail("ahmed@alaskalany.com");
    //                    myUser.setFirstName("Ahmed");
    //                    myUser.setLastName("AlAskalamny");
    //                    myUser.setPhoneNumber("0734894796");
    //
    //                    DataRepository dataRepository = new DataRepository();
    //                    dataRepository.createNewUser(myUser);
    //                } else {
    //                }
    //            }
    //        });

    //        FirebaseFirestore db = FirebaseFirestore.getInstance();
    //        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
    //            @Override
    //            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
    //
    //            }
    //        });
}
