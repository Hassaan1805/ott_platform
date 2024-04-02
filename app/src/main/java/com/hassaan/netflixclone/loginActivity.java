package com.hassaan.netflixclone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class loginActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText ed1 ;
    EditText ed2;
    Button btn;
    boolean login=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ed1 = (EditText) findViewById(R.id.ed1);
        ed2 = (EditText) findViewById(R.id.ed2);
        btn = (Button) findViewById(R.id.btn);
        myDb = new DatabaseHelper(this);
    }
    public void main(View view) {

        final String username = ed1.getText().toString();
        final String password = ed2.getText().toString();

        Cursor resultSet = myDb.getData();
        boolean userExists = false;
        boolean passwordExists = false;

        while (resultSet.moveToNext()) {
            if (resultSet.getString(1).equals(username)) {
                userExists = true;
            }
            if (resultSet.getString(2).equals(password)) {
                passwordExists = true;
            }
        }

        if (userExists && passwordExists) {
            Toast.makeText(getApplicationContext(), "login successfull", Toast.LENGTH_LONG).show();
            login=true;
            SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("username", username);
            editor.apply();
        } else if (!userExists) {
            Toast.makeText(getApplicationContext(), "Username doesnt exists in database.", Toast.LENGTH_LONG).show();
        } else if (!passwordExists) {
            Toast.makeText(getApplicationContext(), "Password doesnt exists in database", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(),"Both username and password deosnt exists in database",Toast.LENGTH_SHORT).show();
        }
        if(login){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    public void register(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
    public void forgot(View view){
        Intent intent = new Intent(this, ForgetPassword.class);
        startActivity(intent);
        finish();
    }

}