package com.q3.firebaselogindemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.q3.firebaselogindemo.model.User;

public class MainActivity extends AppCompatActivity implements FirebaseDB.DbChangeListener{

    private static final String TAG = MainActivity.class.getName();
    private FirebaseDB firebaseDB;

    //views declaration
    private EditText etName,etEmail;
    private Button btnSubmit;
    private TextView tvMessage;
    private Button btnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create instance of FirebaseDb for db operations
        firebaseDB=new FirebaseDB(this);
        firebaseDB.setDbChangeListener(this);  //to get updated list of users

        btnSubmit=(Button)findViewById(R.id.btnSubmit);
         btnList = (Button) findViewById(R.id.btnList);

        //initialize views
        etName=(EditText) findViewById(R.id.etName);
        etEmail=(EditText) findViewById(R.id.etEmail);
        tvMessage=(TextView)findViewById(R.id.tvMessage);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etName.getText().toString().isEmpty() || etEmail.getText().toString().isEmpty())
                    return;
                firebaseDB.insert(new User(etName.getText().toString(),
                        etEmail.getText().toString()));
            }
        });

        //to get list of users on button click use following code
       /* btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication myapplication= (MyApplication) getApplicationContext();
                for(User user:myapplication.users){
                    tvMessage.append("\n\n"+user.getName()+" : "+user.getEmail());
                }
            }
        });*/

    }

    @Override
    public void onDbChange(DataSnapshot dataSnapshot) {
        tvMessage.setText("List of Current users :\n");
        for(DataSnapshot snapshot:dataSnapshot.getChildren()){

            User user=snapshot.getValue(User.class);
            tvMessage.append("\n"+user.getName()+" : "+user.getEmail());
        }
    }
}
