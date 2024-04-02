package com.hassaan.netflixclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgetPassword extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText ed1 ;
    EditText ed2;
    Button btn,btn2;
    String id;
    boolean isFound = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ed1 = (EditText) findViewById(R.id.ed1);
        ed2 = (EditText) findViewById(R.id.ed2);
        btn = (Button) findViewById(R.id.btn);
        btn2 = (Button) findViewById(R.id.btn2);
        myDb = new DatabaseHelper(this);
    }
    public void main(View view){
        Drawable dr = getResources().getDrawable(R.drawable.baseline_key_24);
        final String username = ed1.getText().toString();
        Cursor resultSet = myDb.getData();
        boolean userExists = false;


        while (resultSet.moveToNext()) {
            if (resultSet.getString(1).equals(username)) {
                userExists = true;
                id = resultSet.getString(0);
            }

        }

        if (!userExists) {
            Toast.makeText(getApplicationContext(), "Cant find user", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(),"Found the user!",Toast.LENGTH_SHORT).show();
            ed1.setHint("New Passoword");
            ed1.setText("");
            ed1.setCompoundDrawablesWithIntrinsicBounds(dr,null,null,null); // change drawable left
            ed1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            ed2.setHint("Confirm Password");
            ed2.setVisibility(View.VISIBLE);
            btn.setVisibility(View.INVISIBLE);
            btn2.setVisibility(View.VISIBLE);
        }

    }
    public void change(View view){
                Boolean updated = myDb.updateData2(id,ed2.getText().toString());
                if (updated)
                    Toast.makeText(getApplicationContext(), "Data Updated Successfully!!", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "Unable to Update", Toast.LENGTH_LONG).show();

}
public void prev(View view){
        Intent intent = new Intent(this, loginActivity.class);
        startActivity(intent);
}
}