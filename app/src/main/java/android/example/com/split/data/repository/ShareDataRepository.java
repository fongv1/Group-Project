package android.example.com.split.data.repository;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.example.com.split.data.entity.Share;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShareDataRepository {

  public static final String SUCCESS = "success";
  public static final String SHARE_ID = "share-id";

  public static final String UPDATE_SUCCESS = "update-success";
  public static final String SHARE_LIST = "share-list";
  private FirebaseFirestore db;

  // add share to a group
  public void addShare(final String groupId , Share share , final Handler.Callback listener){
    db = FirebaseFirestore.getInstance();
    db.collection("groups").document(groupId).collection("shares").add(share)
      .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
        @Override
        public void onComplete(@NonNull Task<DocumentReference> task) {
          if (task.isSuccessful()){
            DocumentReference documentReference = task.getResult();
            String documentId = documentReference.getId();

            // save doc id into the share document
            db.collection("groups").document(groupId).collection("shares").document(documentId)
              .update("id",documentId);

            Message message = new Message();
            Bundle data = new Bundle();
            data.putBoolean(SUCCESS,true);
            data.putString(SHARE_ID,documentId);
            message.setData(data);
            listener.handleMessage(message);

          }
          else {
            Message message = new Message();
            Bundle data = new Bundle();
            data.putBoolean(SUCCESS,false);
            message.setData(data);
            listener.handleMessage(message);

          }

        }
      });
  }

  // get the list of shares in a group
  public void getSharesList(String groupId , final Handler.Callback listener){
    db = FirebaseFirestore.getInstance();
    final List<Share> shareList = new ArrayList<>();
    db.collection("groups").document(groupId).collection("share").get()
      .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
        @Override
        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
          Message message = new Message();
          Bundle data = new Bundle();
          if (!queryDocumentSnapshots.isEmpty()){
            for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                Share share = documentSnapshot.toObject(Share.class);
              share.setId(documentSnapshot.getId());
              shareList.add(share);

            }
            data.putBoolean(SUCCESS, true);
            data.putSerializable(SHARE_LIST, (Serializable) shareList);
            message.setData(data);
            listener.handleMessage(message);
          }
          else {
            data.putBoolean(SUCCESS, true);
            message.setData(data);
            listener.handleMessage(message);
          }
        }
      }).addOnFailureListener(new OnFailureListener() {
      @Override
      public void onFailure(@NonNull Exception e) {
        Message message = new Message();
        Bundle data = new Bundle();
        data.putBoolean(SUCCESS, true);
        message.setData(data);
        listener.handleMessage(message);

      }
    });
  }

  // Update share document

  public void updateShares(String groupId , Share share , final Handler.Callback listener){
    db = FirebaseFirestore.getInstance();
    String shareId = share.getId();

    db.collection("groups").document(groupId).collection("shares").document(shareId)
      .set(share).addOnCompleteListener(new OnCompleteListener<Void>() {
      @Override
      public void onComplete(@NonNull Task<Void> task) {
        Message message = new Message();
        final Bundle data = new Bundle();
        if (task.isSuccessful()){
          data.putBoolean(UPDATE_SUCCESS, true);
          message.setData(data);
          listener.handleMessage(message);
        }
        else {
          data.putBoolean(UPDATE_SUCCESS, false);
          message.setData(data);
          listener.handleMessage(message);

        }
      }
    });

  }

  // remove share (member with his shares)

  public void removeShare(String groupId , String shareId , final Handler.Callback listener ){
    db = FirebaseFirestore.getInstance();
    db.collection("groups").document(groupId).collection("shares").document(shareId)
      .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
      @Override
      public void onComplete(@NonNull Task<Void> task) {
        Message message = new Message();
        Bundle data = new Bundle();
        if(task.isSuccessful()) {
          data.putBoolean(SUCCESS, true);
          message.setData(data);
          listener.handleMessage(message);
        }
        else {
          data.putBoolean(SUCCESS, false);
          message.setData(data);
          listener.handleMessage(message);
        }
      }
    }).addOnFailureListener(new OnFailureListener() {
      @Override
      public void onFailure(@NonNull Exception e) {
        Message message = new Message();
        Bundle data = new Bundle();
        data.putBoolean(SUCCESS, false);
        message.setData(data);
        listener.handleMessage(message);
      }
    });

  }

  // delete all shares in a group

  public void deleteAllShares(String groupId  , final Handler.Callback listener ){
    db = FirebaseFirestore.getInstance();
    db.collection("groups").document(groupId).collection("shares").document()
      .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
      @Override
      public void onComplete(@NonNull Task<Void> task) {
        Message message = new Message();
        Bundle data = new Bundle();
        if(task.isSuccessful()) {
          data.putBoolean(SUCCESS, true);
          message.setData(data);
          listener.handleMessage(message);
        }
        else {
          data.putBoolean(SUCCESS, false);
          message.setData(data);
          listener.handleMessage(message);
        }
      }
    }).addOnFailureListener(new OnFailureListener() {
      @Override
      public void onFailure(@NonNull Exception e) {
        Message message = new Message();
        Bundle data = new Bundle();
        data.putBoolean(SUCCESS, false);
        message.setData(data);
        listener.handleMessage(message);
      }
    });

  }


}
