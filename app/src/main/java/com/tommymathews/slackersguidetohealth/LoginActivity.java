package com.tommymathews.slackersguidetohealth;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.tommymathews.slackersguidetohealth.model.User;
import com.tommymathews.slackersguidetohealth.service.UserService;
import com.tommymathews.slackersguidetohealth.service.impl.DbSchema;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    // UI references.
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton, signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        emailEditText = (EditText) findViewById(R.id.email);

        loginButton = (Button) findViewById(R.id.log_in_button);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!attemptLogin()) {
                    SharedPreferences sharedPreferences=getSharedPreferences(DbSchema.LOGIN, 0);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString(DbSchema.EMAIL, emailEditText.getText().toString());
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                finish();
                }
            }
        });
        signUpButton = (Button) findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUp.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        passwordEditText = (EditText) findViewById(R.id.password);

    }

    private boolean attemptLogin() {
        boolean cancel = false;
        if (!isEmailValid(emailEditText.getText().toString())) {
            new AlertDialog.Builder(this)
                    .setTitle("Invalid Email")
                    .setMessage("Please set a valid email")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return true;
        }

        UserService userService = DependencyFactory.getUserService(getApplication());
        User user = userService.getUserByEmail(emailEditText.getText().toString());
        if (user != null) {
            if (!user.getPassword().equals(passwordEditText.getText().toString())) {
                new AlertDialog.Builder(this)
                    .setTitle("Invalid Password")
                    .setMessage("Password doesn't match user")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

                cancel = true;
            }
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Invalid Email")
                    .setMessage("No such user exists")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            cancel = true;
        }

        return cancel;
    }

    public static boolean isEmailValid(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

}

