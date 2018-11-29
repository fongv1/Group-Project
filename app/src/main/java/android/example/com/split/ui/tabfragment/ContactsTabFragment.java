package android.example.com.split.ui.tabfragment;


import android.content.Context;
import android.example.com.split.R;
import android.example.com.split.data.entity.User;
import android.example.com.split.data.repository.UserDataRepository;
import android.example.com.split.ui.recycleradapter.ContactsRecyclerAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsTabFragment extends BaseTabFragment<ContactsRecyclerAdapter, User>
    implements ContactsActions {

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
    getContactsData();
  }

  private void getContactsData() {
    initDataset();
    List<User> loadedContacts = new ArrayList<>();
    //setData(loadedContacts);
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
      savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_tab_contacts, container, false);
    setupRecyclerView(rootView, R.id.recyclerView_fragment_tab_contacts);
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

    setRecyclerAdapter(new ContactsRecyclerAdapter(getData()));
    recyclerView.setAdapter(getRecyclerAdapter());
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    User user = new User();
    if (firebaseUser != null) {
      user.setId(firebaseUser.getUid());
      fetchContactListFromRemoteDb(user);
    }
  }

  @Override
  public void addContact(User user) {



  }

  @Override
  public User getContactDetailFromUI(String firstName, String lastName, String email) {
    User user = initialiseNewContact(firstName,lastName,email);
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
        }
        else {
          Toast.makeText(getContext(), "Failed to add contact to remote", Toast.LENGTH_SHORT).show();
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
    UserDataRepository userDataRepository = new UserDataRepository();
    userDataRepository.removeContact(currentUserId, contactId, new Handler.Callback() {
      @Override
      public boolean handleMessage(Message msg) {
        if (msg.getData().getBoolean(UserDataRepository.SUCCESS, false)) {
          Toast.makeText(getContext(), "Contact removed from remote", Toast.LENGTH_SHORT).show();
        }
        else {
          Toast.makeText(getContext(), "Failed to remove contact from remote", Toast.LENGTH_SHORT).show();
        }
        return false;
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
          List<User> contactList = (List<User>) msg.getData().getSerializable(UserDataRepository.CONTACT_LIST);

          getRecyclerAdapter().getDataset().addAll(contactList);
        }
        getRecyclerAdapter().notifyDataSetChanged();
        return false;
      }
    });
  }


}
