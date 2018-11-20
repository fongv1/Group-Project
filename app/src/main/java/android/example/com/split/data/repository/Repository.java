package android.example.com.split.data.repository;

import android.example.com.split.data.entity.Group;
import android.os.Handler;

public abstract class Repository<T> {

    abstract void createItem(Group group, Handler.Callback listener);

    abstract void getItem(String itemId);

    abstract void updateItem(String itemId);
}
