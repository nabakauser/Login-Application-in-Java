package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText userName, userEmail, userPassword;
    Spinner userGender;
    Button submit;
    SharedPreferences sharedpreferences;

    public static final String sharedPrefName = "MyPrefs";
    public static final String keyName = "keyName";
    public static final String keyEmail = "keyEmail";
    public static final String keyGender = "keyGender";
    public static final String keyPassword = "keyPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpUi();
        setUpUserGender();
        checkPreviousFieldSavedInDb();
        setUpListeners();
    }

    private void checkPreviousFieldSavedInDb() {
        String name = sharedpreferences.getString(keyName, null);
        if (name != null) {
            navigateToNextScreen();
            finish();
        }
    }

    private void setUpUi() {
        userName = findViewById(R.id.uiEtUserName);
        Log.d("username", "setUpUi: " + userName.getText().toString());
        userEmail = findViewById(R.id.uiEtUserEmail);
        userPassword = findViewById(R.id.uiEtPassword);
        userGender = findViewById(R.id.uiSpinnerGender);
        submit = findViewById(R.id.uiButtonSubmit);
        sharedpreferences = getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
    }

    private void setUpUserGender() {
        Spinner spinner = findViewById(R.id.uiSpinnerGender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.genders, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(null);

    }

    private void setUpListeners() {
        submit.setOnClickListener(v -> actionSubmit());
        Log.d("nameValidation", "my name is: " +submit );

    }

    private void actionSubmit() {
        Log.d("nameValidation", "my issue is: " +submit );

        if (validateName() && validateEmail() && validatePassword()) {
            Log.d("validateEmail", "validateEmail" + validateEmail());
                saveInputFieldsToDb();
                navigateToNextScreen();

        }
    }

    private void saveInputFieldsToDb() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(keyName, userName.getText().toString());
        editor.putString(keyEmail, userEmail.getText().toString());
        editor.putString(keyPassword, userPassword.getText().toString());
        editor.putString(keyGender, userGender.getSelectedItem().toString());
        editor.apply();
    }

    private void navigateToNextScreen() {
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(intent);
    }

    private boolean validateName() {
        String nameToString = userName.getText().toString();
        Log.d("validateName", ":beforeValidation ");
        if (nameToString.trim().length() == 0) {
            Log.d("validateName", ":isEmptyValidation ");
            userName.setError("Username is not entered");
            userName.requestFocus();
            return false;
        }else {
            Log.d("validateName", "afterValidation ");
            userName.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {
        String emailToString = userEmail.getText().toString();
        Log.d("validateEmail", ":beforeValidation ");
        if (emailToString.isEmpty()) {
            Log.d("validateEmail", "isEmptyValidation ");
            userEmail.setError("Email is not entered");
            userEmail.requestFocus();
            return false;
        }else if (emailToString.trim().length() != 0 && !Patterns.EMAIL_ADDRESS.matcher(emailToString).matches()) {
            userEmail.setError("Email format is incorrect");
            Log.d("validateEmail", "formatValidation ");
            userEmail.requestFocus();
                return false;
        } else {
            Log.d("validateEmail", "afterValidation ");
            userEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordToString = userPassword.getText().toString();
        if (passwordToString.isEmpty()) {
            userPassword.setError("Password is not entered");
            userPassword.requestFocus();
            return false;
        }
        if (passwordToString.trim().length() != 0) {
            if (passwordToString.length() < 7) {
                userPassword.setError("Password is weak");
                userPassword.requestFocus();
                return false;
            }else {
                userPassword.setError(null);
                return true;
            }
        }else {
            userPassword.setError("Password is weak");
            return false;
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}