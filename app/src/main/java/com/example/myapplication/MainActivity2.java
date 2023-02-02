package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    TextView enteredName,enteredEmail,enteredPassword,enteredGender;
    SharedPreferences sharedpreferences;
    Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setUserUi();
        displayInputText();
        userLogOut();
    }

    private void setUserUi() {
        enteredName = findViewById(R.id.uiTvEnteredName);
        enteredEmail = findViewById(R.id.uiTvEnteredEmail);
        enteredPassword = findViewById(R.id.uiTvEnteredPassword);
        enteredGender = findViewById(R.id.uiTvEnteredGender);
        sharedpreferences = getSharedPreferences(MainActivity.sharedPrefName,MODE_PRIVATE);
        logOut = findViewById(R.id.uiButtonLogout);
    }

    private void displayInputText() {
        String name = sharedpreferences.getString("keyName","");
        String email = sharedpreferences.getString(MainActivity.keyEmail,null);
        String password = sharedpreferences.getString(MainActivity.keyPassword,null);
        String gender = sharedpreferences.getString(MainActivity.keyGender, null);

        if(name != null || password != null || email != null || gender != null) {
            enteredName.setText(name);
            enteredEmail.setText(email);
            enteredPassword.setText(password);
            enteredGender.setText(gender);
        }
    }

    private void userLogOut() {
        logOut.setOnClickListener(view -> {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.apply();
            navigateToNextScreen();
        });
    }
    private void navigateToNextScreen() {
        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
        startActivity(intent);
    }
}