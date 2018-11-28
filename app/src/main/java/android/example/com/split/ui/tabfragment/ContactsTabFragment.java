package android.example.com.split.ui.tabfragment;


import android.content.Context;
import android.example.com.split.R;
import android.example.com.split.data.entity.User;
import android.example.com.split.ui.recycleradapter.ContactsRecyclerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsTabFragment extends BaseTabFragment<ContactsRecyclerAdapter, User>
    implements ContactsActions {

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
  }

  @Override
  public void addContact(User user) {

  }

  @Override
  public void getContactDetailFromUI(String firstName, String lastName, String Email) {

  }

  @Override
  public boolean contactExist(List<User> users, String firstName, String lastName) {
    return false;
  }

  @Override
  public User initialiseNewContact(String firstName, String lastName, String email) {
    return null;
  }

  @Override
  public void saveNewContact(User user) {

  }

  @Override
  public void saveNewContactToRemote(User currentUser, User contact) {

  }

  @Override
  public void updateUIWithNewContact(User user) {

  }

  @Override
  public void removeContact(List<User> users, User user) {

  }

  @Override
  public void removeContactFromDB(User currentUser, User contact) {

  }
}
