package com.q3.firebaselogindemo;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.q3.firebaselogindemo.model.User;


/**
 * Created by gkaur on 10/11/2017.
 */

public class FirebaseDB {

    private final FirebaseDatabase database;
    private final DatabaseReference myRef;
    Context context;

    DbChangeListener dbChangeListener;

    MyApplication myApplication;

    public  FirebaseDB(final Context context){
        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");
        myApplication= (MyApplication) context.getApplicationContext();
        this.context=context;

          myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dbChangeListener!=null){
                    dbChangeListener.onDbChange(dataSnapshot);
                }
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                    User user=snapshot.getValue(User.class);
                    myApplication.users.add(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(FirebaseDB.class.getName(),databaseError.getMessage());
            }
        });
    }

    public void insert(User user){
        myRef.push().setValue(user);
    }

    public void setDbChangeListener(DbChangeListener dbChangeListener){
        this.dbChangeListener=dbChangeListener;

    }
    public interface DbChangeListener {
         void onDbChange(DataSnapshot dataSnapshot);
    }

}
