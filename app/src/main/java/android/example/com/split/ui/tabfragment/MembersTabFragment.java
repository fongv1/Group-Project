package android.example.com.split.ui.tabfragment;

import android.content.Context;
import android.example.com.split.R;
import android.example.com.split.data.entity.Group;
import android.example.com.split.data.entity.User;
import android.example.com.split.ui.recycleradapter.MembersRecyclerAdapter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MembersTabFragment extends BaseTabFragment<MembersRecyclerAdapter, User> implements
    MembersActions {

  private static final String TAG = "MembersTabFragment";
  private Group group;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_tab_members, container, false);
    ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(
        Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
    boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    if (!isConnected) {
      Toast.makeText(getContext(), "Not connected", Toast.LENGTH_SHORT).show();
    } else {
      setupRecyclerView(rootView, R.id.recyclerView_fragment_tab_expenses);
    }
    return rootView;
  }

  @Override
  protected void setupRecyclerView(View rootView, int recyclerViewId) {
    recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView_fragment_tab_members);
    recyclerView.setHasFixedSize(true);
    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
    recyclerView.setLayoutManager(mLayoutManager);
    // Create bundle to get the group passed from the GroupDetailActivity
    Bundle bundle = getArguments();
    group = (Group) bundle.get("group");
    //gets the expenses from the group
    setData(group.getUserMembers());
    setRecyclerAdapter(new MembersRecyclerAdapter(getData()));
    recyclerView.setAdapter(getRecyclerAdapter());
  }

  @Override
  public void addNewMember(User member) {

  }

  @Override
  public List<User> getContactsNotInAGroup(Group group, List<User> contacts) {
    return null;
  }

  @Override
  public User selectContactFromContacts(List<User> contacts, User contact) {
    return null;
  }

  @Override
  public boolean isContactMemberInGroup(User contact, Group group) {
    return false;
  }

  @Override
  public User initializeNewGroupMember(User newMember, Group group) {
    return null;
  }

  @Override
  public void saveNewMemberInGroup(User newMember, Group group) {

  }

  @Override
  public void saveNewMemberInGroupToRemoteDb(final Group group, final User user) {
    if (user.getId() != null) {
      addMemberToGroup(user.getId(), group, new OnSuccessListener<Void>() {

        @Override
        public void onSuccess(Void aVoid) {
          Toast.makeText(getContext(), "New Member added to the group.", Toast.LENGTH_SHORT).show();
        }
      });
    } else {
      createNewUserDocument(group, user, new OnSuccessListener<DocumentReference>() {
        @Override
        public void onSuccess(DocumentReference documentReference) {
          final String userId = documentReference.getId();
          FirebaseFirestore db = FirebaseFirestore.getInstance();
          CollectionReference usersCollection = db.collection("users");
          DocumentReference userDocument = usersCollection.document(userId);
          updateIdInUserDocument(userDocument, userId, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
              addMemberToGroup(userId, group, new OnSuccessListener<Void>() {

                @Override
                public void onSuccess(Void aVoid) {
                  Toast.makeText(getContext(), "New Member added to the group.", Toast.LENGTH_SHORT)
                       .show();
                }
              });
            }
          });
        }
      });
    }
  }

  private void createNewUserDocument(final Group group, User user, final
  OnSuccessListener<DocumentReference> onSuccessListener) {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersCollection = db.collection("users");
    usersCollection.add(user)
                   .addOnSuccessListener(onSuccessListener)
                   .addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {

                     }
                   })
                   .addOnCanceledListener(new OnCanceledListener() {
                     @Override
                     public void onCanceled() {

                     }
                   });
  }

  private void updateIdInUserDocument(DocumentReference userDocument, String userId, final
  OnSuccessListener<Void> onSuccessListener) {
    userDocument.update("id", userId)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(new OnFailureListener() {
                  @Override
                  public void onFailure(@NonNull Exception e) {

                  }
                })
                .addOnCanceledListener(new OnCanceledListener() {
                  @Override
                  public void onCanceled() {

                  }
                });
  }

  private void addMemberToGroup(String userId, Group group, final OnSuccessListener<Void>
      onSuccessListener) {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference groupsCollRef = db.collection("groups");
    DocumentReference groupDocRef = groupsCollRef.document(group.getGroupId());
    groupDocRef.update("members", FieldValue.arrayUnion(userId))
               .addOnSuccessListener(onSuccessListener)
               .addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {
                   Toast.makeText(getContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                 }
               })
               .addOnCanceledListener(new OnCanceledListener() {
                 @Override
                 public void onCanceled() {
                   Toast.makeText(getContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                 }
               });
  }

  @Override
  public void updateUiAfterAddingNewMemberToGroup() {

  }

  @Override
  public void removeGroupMember(User member, Group group) {

  }

  @Override
  public boolean groupMemberHasZeroBalanceInGroup(User user, Group group) {
    return false;
  }

  @Override
  public void removeGroupMemberInRemoteDb(User member, Group group) {

  }

  @Override
  public void updateUiAfterRemovingGroupMember() {

  }
}
