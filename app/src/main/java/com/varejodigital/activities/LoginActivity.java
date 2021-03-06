package com.varejodigital.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.varejodigital.MainActivity;
import com.varejodigital.R;
import com.varejodigital.model.User;
import com.varejodigital.repository.RestClient;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LoginActivity extends BaseActivity {

    protected EditText usernameEditText;
    protected EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usernameEditText = (EditText)findViewById(R.id.usernameField);
        passwordEditText = (EditText)findViewById(R.id.passwordField);
    }

    @Override
    public void onStart() {
        super.onStart();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    public void loginUser(View view) {
        final String username = usernameEditText.getText().toString();
        final String password = passwordEditText.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this,"Infome usário e senha", Toast.LENGTH_LONG).show();
        }
        else {
            showProgress();
            RestClient.setEmailAccount(username);
            RestClient.setPasswordAccount(password);

            RestClient client = new RestClient();
            client.getApiService().login(new Callback<User>() {
                @Override
                public void success(User user, Response response) {
                    user.setEmail(username);
                    user.setPassword(password);
                    User.saveCurrentUser(user);

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(LoginActivity.this,"ERRO: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

//    public void loginUser(View view) {
//        String username = usernameEditText.getText().toString();
//        String password = passwordEditText.getText().toString();
//
//        if (username.isEmpty() || password.isEmpty()) {
//            Toast.makeText(LoginActivity.this,"Infome usário e senha", Toast.LENGTH_LONG).show();
//        }
//        else {
//            showProgress();
//            ParseUser.logInInBackground(username, password, new LogInCallback() {
//                @Override
//                public void done(ParseUser user, ParseException e) {
//                if (e == null) { // Success!
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                } else {//Fail
//                    Toast.makeText(LoginActivity.this,"ERRO!" + e.getMessage(), Toast.LENGTH_LONG).show();
//                }
//
//                hideProgress();
//                }
//            });
//        }
//    }

    public void signUpUser(View view) {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
}
