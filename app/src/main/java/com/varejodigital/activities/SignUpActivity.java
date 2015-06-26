package com.varejodigital.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.varejodigital.MainActivity;
import com.varejodigital.R;


public class SignUpActivity extends BaseActivity {

    protected EditText usernameEditText;
    protected EditText passwordEditText;
    protected EditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        usernameEditText = (EditText)findViewById(R.id.usernameField);
        passwordEditText = (EditText)findViewById(R.id.passwordField);
        emailEditText = (EditText)findViewById(R.id.emailField);
    }

    public void signUp(View view) {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String email = emailEditText.getText().toString();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Informe todos os dados", Toast.LENGTH_LONG).show();
        }
        else {
            showProgress();

            ParseUser newUser = new ParseUser();
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setEmail(email);
            newUser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) { // Success!
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                    else { //Fail
                        Toast.makeText(SignUpActivity.this,"ERRO!" + e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                    hideProgress();
                }
            });
        }
    }
}
