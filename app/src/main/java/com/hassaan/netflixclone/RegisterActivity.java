package com.hassaan.netflixclone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText ed1 ;
    EditText ed2;
    EditText ed3;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ed1 = (EditText) findViewById(R.id.ed1);
        ed2 = (EditText) findViewById(R.id.ed2);
        ed3 = (EditText)findViewById(R.id.ed3);
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

                if(username.isEmpty() | password.isEmpty() | username.isEmpty() && password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Empty credentials",Toast.LENGTH_SHORT).show();
                } else{if (userExists ) {
                Toast.makeText(getApplicationContext(),"Username already exists. Cannot add",Toast.LENGTH_SHORT).show();
                }  else if (password.length() < 5) {
                    Toast.makeText(getApplicationContext(),"Password must be at least 5 characters long",Toast.LENGTH_SHORT).show();
                } else if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$")) {
                    Toast.makeText(getApplicationContext(),"Password must contain at least one uppercase letter, one lowercase letter, one digit, one special character, and no spaces",Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(ed3.getText().toString())){
                    ed3.setError("Password do match match");
                }
                else {

                    boolean inserted = myDb.insertData(username, password);
                    if (inserted) {
                        Toast.makeText(getApplicationContext(), "Data Inserted Successfully!!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Sorry, Unable to Insert Data!!", Toast.LENGTH_LONG).show();
                    }
                }}



    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void prev(View view){
        Intent intent = new Intent(this, loginActivity.class);
        startActivity(intent);
    }
}