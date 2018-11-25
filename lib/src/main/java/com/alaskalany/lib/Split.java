package com.alaskalany.lib;


import com.alaskalany.lib.model.Group;
import com.alaskalany.lib.model.User;

import java.util.List;

class Split {

  private static Split ourInstance;
  private User user;
  private List<User> contacts;
  private List<Group> groups;

  private Split(User user, List<User> contacts, List<Group> groups) {
    this.user = user;
    this.contacts = contacts;
    this.groups = groups;
  }

  static Split getInstance(User user, List<User> contacts, List<Group> groups) {
    if (ourInstance == null) {
      ourInstance = new Split(user, contacts, groups);
    }
    return ourInstance;
  }
}
