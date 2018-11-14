package android.example.com.split;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home
        extends AppCompatActivity {
    
    private FirebaseAuth mAuth;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }
    
    @Override
    public void onStart() {
        
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly
        
        mAuth.signInAnonymously()
             .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                
                     if (task.isSuccessful()) {
                         FirebaseUser user = mAuth.getCurrentUser();
                         updateUI(user);
                         Toast.makeText(getApplicationContext(),
                                        "Authentication Succeed userId:" + user.getUid(),
                                        Toast.LENGTH_SHORT)
                              .show();
                         
                     } else {
                         Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT)
                              .show();
                         updateUI(null);
                     }
                 }
             });
        
        // updateUI(currentUser);
    }
    
    private void updateUI(FirebaseUser user) {
    
    }
}
