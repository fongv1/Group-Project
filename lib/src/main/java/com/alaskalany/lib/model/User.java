package com.alaskalany.lib.model;

import java.util.List;

public interface User {

  List<String> getContacts();

  void setContacts(List<String> contacts);

  String getId();

  void setId(String id);

  String getAuthId();

  String getFirstName();

  void setFirstName(String firstName);

  String getLastName();

  void setLastName(String lastName);

  String getEmail();

  void setEmail(String email);

  String getPhoneNumber();

  void setPhoneNumber(String phoneNumber);

  void addToContactList(String newUser);
}
