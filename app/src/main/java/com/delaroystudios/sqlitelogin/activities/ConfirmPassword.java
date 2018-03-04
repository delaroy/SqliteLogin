package com.delaroystudios.sqlitelogin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Toast;

import com.delaroystudios.sqlitelogin.R;
import com.delaroystudios.sqlitelogin.helper.InputValidation;
import com.delaroystudios.sqlitelogin.model.User;
import com.delaroystudios.sqlitelogin.sql.DatabaseHelper;

/**
 * Created by delaroy on 3/3/18.
 */

public class ConfirmPassword extends AppCompatActivity {
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextConfirmPassword;

    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private NestedScrollView nestedScrollView;
    private AppCompatButton appCompatButtonReset;

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmpassword);

        inputValidation = new InputValidation(this);
        databaseHelper = new DatabaseHelper(this);

        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputpass);
        textInputEditTextConfirmPassword = (TextInputEditText) findViewById(R.id.textInputConfirmPassword);

        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        textInputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.textInputLayoutConfirmPassword);
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        appCompatButtonReset = (AppCompatButton) findViewById(R.id.appCompatButtonReset);

        Intent intent = getIntent();
        email = intent.getStringExtra("EMAIL");

        setTitle("Reset password");

        appCompatButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePassword();
            }
        });
    }


    private void updatePassword() {

        String value1 = textInputEditTextPassword.getText().toString().trim();
        String value2 = textInputEditTextConfirmPassword.getText().toString().trim();

        if (value1.isEmpty() && value2.isEmpty()){
            Toast.makeText(this, "fill all fields ", Toast.LENGTH_LONG).show();
            return;
        }

        if (!value1.contentEquals(value2)){
            Toast.makeText(this, "password doesn't match", Toast.LENGTH_LONG).show();
            return;
        }

        if (!databaseHelper.checkUser(email)) {

            Snackbar.make(nestedScrollView, "email doesn't exist", Snackbar.LENGTH_LONG).show();
            return;

        } else {
            databaseHelper.updatePassword(email, value1);

            Toast.makeText(this, "password reset successfully", Toast.LENGTH_SHORT).show();
            emptyInputEditText();

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void emptyInputEditText()
    {
        textInputEditTextPassword.setText("");
        textInputEditTextConfirmPassword.setText("");
    }
}
