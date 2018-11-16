package android.example.com.split.data;

import android.example.com.split.data.exception.StringArgumentLengthException;
import android.support.annotation.Nullable;

interface DisplayName {
@Nullable
    String getFirstName() throws Exception;

    void setFirstName(String firstName) throws StringArgumentLengthException;

    String getLastName();

    void setLastName(String lastName);
}
