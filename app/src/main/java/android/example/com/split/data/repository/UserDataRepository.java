package android.example.com.split.data.repository;

import android.example.com.split.data.entity.User;
import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.*;

import javax.annotation.Nullable;

public class UserDataRepository {

    public OnUserId listener;

    private static final String TAG = "UserDataRepository";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void createNewUser(User user) {

        db.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    public void getDocumentId(String user_auth_id, final OnUserId listener) {
        final String[] data = {""};
        db.collection("users").limit(1).whereEqualTo("id", user_auth_id).get()

          .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
              @Override
              public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                  for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                      /*User user = documentSnapshot.toObject(User.class);
                      user.setId(documentSnapshot.getId());

                      String documentId = user.getId();
                      String title = user.getFirstName();*/


                      data[0] = documentSnapshot.getId();
                  }
                  listener.onUserId(data[0]);
              }

          });
    }
          /*.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
              @Override
              public void onComplete(@NonNull Task<QuerySnapshot> task) {
                  if (task.isSuccessful()) {
                      DocumentSnapshot asd = task.getResult().getDocuments().get(0);
                      id[0] = asd.getId();


                *//* User user = asd.toObject(User.class);

                   for(QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                     Log.d(TAG, "onComplete: " + documentSnapshot.getId());

                 }*//*
                  }

              }
          });

        return id[0];*/

       public interface OnUserId{
           void onUserId(String userId);

       }


    public void getUserById(String id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

            }
        });
    }

}
