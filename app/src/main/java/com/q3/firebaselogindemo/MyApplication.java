package com.q3.firebaselogindemo;

import android.app.Application;

import com.q3.firebaselogindemo.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gkaur on 10/11/2017.
 */

public class MyApplication extends Application {
    List<User> users=new ArrayList<>();
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
