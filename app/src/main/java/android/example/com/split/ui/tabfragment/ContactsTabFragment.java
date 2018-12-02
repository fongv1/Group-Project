package android.example.com.split.ui.tabfragment;


import android.content.Context;
import android.example.com.split.OnDeleteItemListener;
import android.example.com.split.R;
import android.example.com.split.data.entity.User;
import android.example.com.split.data.repository.UserDataRepository;
import android.example.com.split.ui.recycleradapter.ContactsRecyclerAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsTabFragment extends BaseTabFragment<ContactsRecyclerAdapter, User>
    implements ContactsActions {

  public static final String TAG = "ContactsTabFragment";
  private User currentUser;
  private List<User> users = new ArrayList<>();

  public ContactsTabFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Initialize dataset, this data would usually come from a local content provider or remote
    // server.

  }

  private void getContactsData() {
    setData(new ArrayList<User>());
    //initDataset();
    //List<User> loadedContacts = new ArrayList<>();
    //setData(loadedContacts);
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    db.collection("users")
      .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
      .get()
      .addOnCompleteListener(

          new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
              if (task.isSuccessful()) {
                DocumentSnapshot userDocumentSnapshot = task.getResult();
                User currentUser = userDocumentSnapshot.toObject(User.class);
                Log.d(TAG, "onComplete: My User Id is: " + currentUser.getId());
                List<String> myContactsIds = currentUser.getContacts();
                if (myContactsIds != null && !myContactsIds.isEmpty()) {
                  for (String contactId : myContactsIds) {
                    db.collection("users")
                      .document(contactId)
                      .get()
                      .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                          DocumentSnapshot contactUserDocumentSnapshot = task.getResult();
                          if (contactUserDocumentSnapshot.exists()) {
                            User contactUser = contactUserDocumentSnapshot.toObject(User.class);
                            getData().add(contactUser);
                          }

                        }
                      });
                  }
                  setupRecyclerView(getView(), R.id.recyclerView_fragment_tab_contacts);
                }
              }
            }
          });
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
      savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_tab_contacts, container, false);
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = auth.getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    db.collection("users")
      .document(firebaseUser.getUid())
      .addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
        @Override
        public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax
            .annotation.Nullable FirebaseFirestoreException e) {
          Log.d(TAG, "onEvent: ");
          if (e != null) {
            Log.w(TAG, "onEvent: ", e);
            return;
          }
          getContactsData();
        }
      });
    return rootView;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  @Override
  public void onStart() {
    super.onStart();
  }

  // Create dummy data
  private void initDataset() {
    setData(new ArrayList<User>());
    for (int i = 0; i < 4; i++) {
      User user = new User();
      user.setFirstName("Dummy Contact first name " + i);
      user.setLastName("Dummy Contact last name " + i);
      user.setEmail("Dummy Contact email " + i);
      getData().add(user);
    }
  }

  @Override
  protected void setupRecyclerView(View rootView, int recyclerViewId) {
    recyclerView = (RecyclerView) rootView.findViewById(recyclerViewId);
    recyclerView.setHasFixedSize(true);

    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
    recyclerView.setLayoutManager(mLayoutManager);

    setRecyclerAdapter(new ContactsRecyclerAdapter(getData(), new OnDeleteItemListener() {
      @Override
      public void onDelete(int position) {

      }

      @Override
      public void onDelete(String id) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        removeContact(currentUser.getUid(), id);
      }
    }));
    recyclerView.setAdapter(getRecyclerAdapter());
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    User user = new User();
    if (firebaseUser != null) {
      user.setId(firebaseUser.getUid());
      fetchContactListFromRemoteDb(user);
    }
  }

  private void removeContact(String id, String id1) {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = auth.getCurrentUser();
    db.collection("users")
      .document(currentUser.getUid())
      .update("contacts", FieldValue.arrayRemove(id1))
      .addOnCompleteListener(

          new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
              if (task.isSuccessful()) {
                Toast.makeText(getContext(), "Contact removed", Toast.LENGTH_SHORT).show();
              } else {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
              }
            }
          });
  }

  @Override
  public void addContact(User user) {


  }

  @Override
  public User getContactDetailFromUI(String firstName, String lastName, String email) {
    User user = initialiseNewContact(firstName, lastName, email);
    if (contactExist(users, user.getFirstName(), user.getLastName())) {
      Toast.makeText(getActivity(), "user exists", Toast.LENGTH_SHORT).show();
      return null;
    }
    return user;
  }

  @Override
  public boolean contactExist(List<User> users, String firstName, String lastName) {
    for (User user : users) {
      if (user.getFirstName().equals(firstName) && user.getLastName().equals(lastName)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public User initialiseNewContact(String firstName, String lastName, String email) {
    User user = new User();
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setEmail(email);
    return user;
  }

  @Override
  public void saveNewContact(User user) {

  }

  @Override
  public void saveNewContactToRemote(User currentUser, User contact) {
    String currentUserId = currentUser.getId();
    UserDataRepository userDataRepository = new UserDataRepository();
    userDataRepository.addNewContact(contact, currentUserId, new Handler.Callback() {
      @Override
      public boolean handleMessage(Message msg) {
        if (msg.getData().getBoolean(UserDataRepository.SUCCESS, false)) {
          Toast.makeText(getContext(), "Contact added to remote", Toast.LENGTH_SHORT).show();
        } else {
          Toast.makeText(getContext(), "Failed to add contact to remote", Toast.LENGTH_SHORT)
               .show();
        }

        return false;
      }
    });

  }

  @Override
  public void updateUIWithNewContact() {
    getRecyclerAdapter().notifyDataSetChanged();
  }

  @Override
  public void removeContact(List<User> users, User user) {
    if (user != null && users != null) {
      if (users.contains(user)) {
        users.remove(user);
      }
    }
  }

  @Override
  public void removeContactFromDB(User currentUser, User contact) {
    String currentUserId = currentUser.getId();
    String contactId = contact.getId();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    db.collection("users")
      .document(currentUserId)
      .update("contacts", FieldValue.arrayRemove(contactId))
      .addOnCompleteListener(

          new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
              if (task.isSuccessful()) {
                Toast.makeText(getContext(), "Contact removed", Toast.LENGTH_SHORT).show();
              } else {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
              }
            }
          });
  }

  @Override
  public void fetchContactListFromRemoteDb(User currentUser) {
    String userId = currentUser.getId();
    UserDataRepository userDataRepository = new UserDataRepository();
    userDataRepository.getContactlist(userId, new Handler.Callback() {
      @Override
      public boolean handleMessage(Message msg) {
        if (msg.getData().getBoolean(UserDataRepository.SUCCESS, false)) {
          List<User> contactList = (List<User>) msg.getData()
                                                   .getSerializable(
                                                       UserDataRepository.CONTACT_LIST);

          getRecyclerAdapter().getDataset().addAll(contactList);
        }
        getRecyclerAdapter().notifyDataSetChanged();
        return false;
      }
    });
  }


}
