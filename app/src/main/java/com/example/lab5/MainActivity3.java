package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity3 extends AppCompatActivity {

    int noteid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //get EditText view
        EditText noteEditText = (EditText) findViewById(R.id.noteText);
        //get intent
        Intent intent = getIntent();
        //get value of noteid from intent and initiate noteid
        noteid = intent.getIntExtra("noteid", -1);

        //if no value present for noteid

        if (noteid != -1){
            //display content by retreiving notes arraylist
            Note note = MainActivity2.notes.get(noteid);
            String noteContent = note.getContent();
            noteEditText.setText(noteContent);
        }

    }

    public void save(View view) {
        EditText noteEditText = (EditText) findViewById(R.id.noteText);
        String content = noteEditText.getText().toString();

        //get SQLiteDatabase instance
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        //initiate "notes" class var using readNotes method in DBHelper, use SharedPref username
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        //set username in String username
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(MainActivity.usernameKey, "");
        Log.i("username", username);
        //save to database
        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if (noteid == -1) { //add new note
            title = "NOTE_" + (MainActivity2.notes.size() + 1);
            Log.i("username in add", username);
            dbHelper.saveNotes(username, title, content, date);


        } else { //update note
            title = "NOTE_" + (noteid + 1);
            Log.i("username in update", username);
            dbHelper.updateNote(title, date, content, username);


        }
        sqLiteDatabase.close();
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
}