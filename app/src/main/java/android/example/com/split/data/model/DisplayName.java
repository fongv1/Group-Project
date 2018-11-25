package android.example.com.split.data.model;

import android.support.annotation.Nullable;

public interface DisplayName {

  @Nullable
  String getFirstName() throws Exception;

  void setFirstName(String firstName) throws IllegalArgumentException;

  String getLastName();

  void setLastName(String lastName);
}
