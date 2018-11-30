package android.example.com.split.data.repository;

import android.example.com.split.data.entity.User;
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

public class UserDataRepository {

  public static final String SUCCESS = "success";
  public static final String CONTACT_LIST = "contact_list";
  private static final String TAG = "UserDataRepository";
  public OnUserId listener;
  private FirebaseFirestore db;

  // create new Auth user
  public void createNewUser(User user, String userAuthId, final OnUserCreated listener) {
    db = FirebaseFirestore.getInstance();
    if (userAuthId != null) {
      db.collection("users").document(userAuthId).set(user)
        .addOnSuccessListener(new OnSuccessListener<Void>() {
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
    } else {
      db.collection("users").add(user)
        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
          @Override
          public void onSuccess(DocumentReference documentReference) {
            listener.onUserCreated(true);
          }
        }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
          listener.onUserCreated(false);
        }
      });
    }

  }

  // create new user if not exist and add it to Auth user's contact list
/*  public void addNewContact(final User user, final String userAuthId, final OnContactCreated
      listener) {
    db = FirebaseFirestore.getInstance();

    // adding a new user to user collection (if we want to add join group)
          db.collection("users").add(user)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
              @Override
              public void onSuccess(DocumentReference documentReference) {
                final String documentReferenceId = documentReference.getId();
                // get current user
                db.collection("users").document(userAuthId).get()
                  .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                      if (task.isSuccessful()) {
                        DocumentSnapshot snapshot = task.getResult();
                        User myUser = snapshot.toObject(User.class);
                        myUser.addToContactList(documentReferenceId);
                        // update the current user by adding the created user to contact list
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

  }*/


  // get the document is of the current auth user
  public void getUserDocument(String user_auth_id, final OnUserId listener) {
    db = FirebaseFirestore.getInstance();
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


  public void isUserExist(String id, final IsUserExist listener) {
    db = FirebaseFirestore.getInstance();
    db.collection("users").document(id).get()
      .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
        // if got document or connected to collection ref but document not exists
        @Override
        public void onSuccess(DocumentSnapshot documentSnapshot) {
          if (documentSnapshot.exists())
            listener.isUserExist(true);
          else
            listener.isUserExist(false);
        }
      })
      // if fail to connect or get collection ref
      .addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
          listener.isUserExist(false);
        }
      });
  }


  // get the contact list for the currenrt user

/*  public void getContactlist(final String userAuthId, final OnGetContact listener) {
    db = FirebaseFirestore.getInstance();
    db.collection("users").document(userAuthId).get()
      .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
        @Override
        public void onSuccess(DocumentSnapshot documentSnapshot) {

          if (documentSnapshot.exists()) {
            final List<User> contactUserList = new ArrayList<>();
            User user = documentSnapshot.toObject(User.class);

            // list of users document id
            List<String> contactList = user.getContactsUsers();

            for (int i = 0; i <= contactList.size() - 1; i++) {
              db.collection("users").document(contactList.get(i)).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

                  @Override
                  public void onSuccess(DocumentSnapshot documentSnapshot) {
                    // list of contact usersList<User> contactUserList;


                    User contactUser = documentSnapshot.toObject(User.class);

                    contactUserList.add(contactUser);
                    listener.onGetContact(contactUserList);


                  }
                });

            }

           // listener.onGetContact(contactUserList);
          }

        }
      });
  }*/

  // get user details

  public void getUserDetail(final String userAuthId, final OnUserDetails listener) {
    db = FirebaseFirestore.getInstance();
    db.collection("users").document(userAuthId).get()
      .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
        @Override
        public void onSuccess(DocumentSnapshot documentSnapshot) {
          if (documentSnapshot.exists()) {
            User user = documentSnapshot.toObject(User.class);
            listener.onUserDetails(user);
          }

        }
      }).addOnFailureListener(new OnFailureListener() {
      @Override
      public void onFailure(@NonNull Exception e) {

      }
    });
  }


  // create new user if not exist and add it to Auth user's contact list
  public void addNewContact(final User user, final String userAuthId, final Handler.Callback
      listener) {
    db = FirebaseFirestore.getInstance();

    // adding a new user to user collection (if we want to add join group)
    db.collection("users").add(user)
      .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
        @Override
        public void onSuccess(DocumentReference documentReference) {
          final String documentReferenceId = documentReference.getId();
          db.collection("users").document(documentReferenceId).update("id", documentReferenceId)
            .addOnCompleteListener(

                new OnCompleteListener<Void>() {
                  @Override
                  public void onComplete(@NonNull Task<Void> task) {
                    User contact = new User();
                    contact.setId(documentReferenceId);
                    contact.setFirstName(user.getFirstName());
                    contact.setLastName(user.getLastName());

                    db.collection("users").document(userAuthId)
                      .update("contacts", FieldValue.arrayUnion(contact.getId()))
                      .addOnCompleteListener(

                          new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                              if (task.isSuccessful()) {
                                db.collection("users").document(documentReferenceId)
                                  .update("contacts", FieldValue.arrayUnion(userAuthId))
                                  .addOnCompleteListener(

                                      new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                          Message message = new Message();
                                          Bundle data = new Bundle();
                                          if (task.isSuccessful()) {
                                            data.putBoolean(SUCCESS, true);
                                            message.setData(data);
                                            listener.handleMessage(message);
                                          } else {
                                            data.putBoolean(SUCCESS, false);
                                            message.setData(data);
                                            listener.handleMessage(message);
                                          }
                                        }
                                      });
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
                });
          // get current user
        }
      });

  }

  // get the contact list collection for the currenrt user
  public void getContactlist(final String userAuthId, final Handler.Callback listener) {
    db = FirebaseFirestore.getInstance();
    final List<User> contactUserList = new ArrayList<>();

    db.collection("users").document(userAuthId).collection("contacts").get()
      .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
        @Override
        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
          for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
            User user = documentSnapshot.toObject(User.class);
            contactUserList.add(user);
          }

          Message message = new Message();
          Bundle data = new Bundle();
          data.putBoolean(SUCCESS, false);
          data.putSerializable(CONTACT_LIST, (Serializable) contactUserList);
          message.setData(data);
          listener.handleMessage(message);
        }
      });

  }

  // remove a contact from conatct collection (tested)

  public void removeContact(final String userId, String contactUserId, final Handler.Callback
      listener) {
    db = FirebaseFirestore.getInstance();
    db.collection("users").document(userId).collection("contacts").whereEqualTo("id", contactUserId)
      .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
      @Override
      public void onComplete(@NonNull Task<QuerySnapshot> task) {
        Message message = new Message();
        Bundle data = new Bundle();
        if (task.isSuccessful()) {
          for (DocumentSnapshot document : task.getResult()) {
            db.collection("users").document(userId).collection("contacts")
              .document(document.getId()).delete();
          }
          data.putBoolean(SUCCESS, true);
          message.setData(data);
          listener.handleMessage(message);
        } else {
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


  public interface OnUserId {

    void onUserId(String userId);


  }

  public interface OnUserCreated {

    void onUserCreated(Boolean userCreated);
  }

  public interface OnContactCreated {

    void onContactCreated(Boolean contactCreated);
  }

  public interface OnGetContact {

    void onGetContact(List<User> contactUser);

    void onGetId(String id);
  }

  public interface IsUserExist {

    void isUserExist(Boolean userExist);
  }

  public interface OnUserDetails {

    void onUserDetails(User user);
  }


  public interface OnItemRemoved {

    void onItemRemoved(boolean isItemRemoved);
  }

}
