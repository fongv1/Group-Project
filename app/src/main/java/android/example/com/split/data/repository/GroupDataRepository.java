package android.example.com.split.data.repository;

import android.example.com.split.data.entity.Group;
import android.util.Log;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
public class GroupDataRepository {

    private static final String TAG = "DataRepository";
    public void getUser() {

    }



    public void createNewGroup(Group group){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("groups").add(group).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "onSuccess: " + documentReference.getId());
            }
        });
    }





    //               FirebaseAuth auth = FirebaseAuth.getInstance();
    //        auth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    //            @Override
    //            public void onComplete(@NonNull Task<AuthResult> task) {
    //                if (task.isSuccessful()) {
    //
    //                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    //
    //                    User myUser = new User();
    //                    myUser.setId("12345678");
    //                    myUser.setEmail("saleh@ibra.com");
    //                    myUser.setFirstName("Saleh");
    //                    myUser.setLastName("Ibrahim");
    //                    myUser.setPhoneNumber("0732222222");
    //
    //                    DataRepository dataRepository = new DataRepository();
    //                    dataRepository.createNewUser(myUser);
    //                    Toast.makeText(HomeActivity.this,"HELL",Toast.LENGTH_LONG).show();
    //                } else {
    //                    Toast.makeText(HomeActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
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
