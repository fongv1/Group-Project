package android.example.com.split.data.repository;

import android.example.com.split.data.entity.User;
import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.*;

import javax.annotation.Nullable;

public class UserDataRepository {

    public OnUserId listener;

    private static final String TAG = "UserDataRepository";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    public void createNewUser(User user, String userAuthId, final OnUserCreated listener) {

        db.collection("users").document(userAuthId).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onUserCreated(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onUserCreated(false);
            }
        });

    }

    public void addNewContact(final User user, final String userAuthId, final OnContactCreated listener) {

        db.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                final String userId = documentReference.getId();
                db.collection("users").document(userAuthId).get()
                  .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                      @Override
                      public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                          if (task.isSuccessful()) {
                              DocumentSnapshot snapshot = task.getResult();
                              User myUser = snapshot.toObject(User.class);
                              myUser.addToContactList(userId);
                              db.collection("users").document(userAuthId).set(myUser)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            listener.onContactCreated(true);
                                        } else {
                                            listener.onContactCreated(false);
                                        }
                                    }
                                });

                          }
                      }
                  });
            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onContactCreated(false);

            }
        });

    }

    private void addNewUserToContactList(String userAuthId, String userId) {

    }


    public void getDocumentId(String user_auth_id, final OnUserId listener) {
        final String[] data = {""};
        db.collection("users").limit(1).whereEqualTo("id", user_auth_id).get()

          .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
              @Override
              public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                  for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

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


    public void getUserById(String id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

            }
        });
    }

    public interface OnUserId {

        void onUserId(String userId);
    }

    public interface OnUserCreated {

        void onUserCreated(Boolean userCreated);
    }

    public interface OnContactCreated {

        void onContactCreated(Boolean contactCreated);
    }

}
