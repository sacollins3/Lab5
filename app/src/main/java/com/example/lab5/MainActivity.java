package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public void login(View view) {

        EditText usernameText = (EditText) findViewById(R.id.username);
        EditText passwordText = (EditText) findViewById(R.id.password);

        String userName = usernameText.getText().toString();
        String passText = passwordText.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username", userName).apply();

        if (userName.length() > 0 && passText.length() > 0){
            Log.i("username",userName);
            goToActivity2(userName);
        }

    }

    public void goToActivity2(String username){
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("username", username);
        Log.i("username", username);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String usernameKey = "";

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5", Context.MODE_PRIVATE);

        if (!sharedPreferences.getString(usernameKey, "").equals("")) {
            goToActivity2(sharedPreferences.getString(usernameKey, ""));
        } else {
        setContentView(R.layout.activity_main);
        }

    }
}