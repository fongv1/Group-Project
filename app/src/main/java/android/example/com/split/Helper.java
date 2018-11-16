package android.example.com.split;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class Helper {
    public static void switchToActivity(AppCompatActivity from, Class<? extends AppCompatActivity> activityClass) {
        Intent intent = new Intent(from, activityClass);
        from.startActivity(intent);
    }
}
