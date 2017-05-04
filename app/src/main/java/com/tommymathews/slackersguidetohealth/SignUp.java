package com.tommymathews.slackersguidetohealth;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tommymathews.slackersguidetohealth.model.User;
import com.tommymathews.slackersguidetohealth.service.UserService;
import com.tommymathews.slackersguidetohealth.service.impl.DbSchema;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.tommymathews.slackersguidetohealth.R.id.email;

public class SignUp extends Activity {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    // UI references.
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private RadioGroup genderRadioGroup;
    private SeekBar ageSeekBar;
    private TextView ageTextView;
    private SeekBar weightSeekBar;
    private TextView weightTextView;
    private SeekBar heightSeekBar;
    private TextView heightTextView;
    private Spinner goalSpinner;
    private Button signUpButton;
    private int age;
    private int weight;
    private int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameEditText = (EditText) findViewById(R.id.name);
        genderRadioGroup = (RadioGroup) findViewById(R.id.gender_radio_group);
        genderRadioGroup.check(R.id.male_button);

        // Set up the login form.
        emailEditText = (EditText) findViewById(email);
        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!isEmailValid(emailEditText.getText().toString()))
                    emailEditText.setTextColor(Color.RED);
                else
                    emailEditText.setTextColor(Color.BLACK);
            }
        });

        passwordEditText = (EditText) findViewById(R.id.pass_one);
        confirmPasswordEditText = (EditText) findViewById(R.id.pass_confirm);
/*        confirmPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!confirmPasswordEditText.getText().toString().equals(passwordEditText.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Password must match", Toast.LENGTH_SHORT).show();
            }
        });*/

        ageTextView = (TextView) findViewById(R.id.txt_age);
        ageSeekBar = (SeekBar) findViewById(R.id.seekbar_age);
        ageTextView.setText("16");
        ageSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ageTextView.setText((i + 16) + "");
                age = i + 16;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        weightTextView = (TextView) findViewById(R.id.weight_display_text);
        weightTextView.setText("50 lbs.");
        weightSeekBar = (SeekBar) findViewById(R.id.weight_bar);
        weightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                weightTextView.setText((i + 50) + "lbs.");
                weight = i + 50;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        heightTextView = (TextView) findViewById(R.id.txt_height);
        heightSeekBar = (SeekBar) findViewById(R.id.seekbar_height);
        heightTextView.setText("4' 0");
        heightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                heightTextView.setText( (i / 12 + 4) + "' " + (i % 12) + "\"");
                height = i + 4 * 12;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        goalSpinner = (Spinner) findViewById(R.id.goal_spinner);
        ArrayAdapter<CharSequence> goalAdapter = ArrayAdapter.createFromResource(this, R.array.goals_array, android.R.layout.simple_spinner_item);
        goalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        goalSpinner.setAdapter(goalAdapter); // still need to fill this out

        signUpButton = (Button) findViewById(R.id.btn_sign_up);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!attemptLogin()) {
                    SharedPreferences sharedPreferences=getSharedPreferences(DbSchema.LOGIN, 0);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString(DbSchema.EMAIL, emailEditText.getText().toString());
                    editor.commit();

                    //put things in sqlite
                    User user = new User();
                    user.setName(nameEditText.getText().toString());
                    user.setEmail(emailEditText.getText().toString());
                    user.setPassword(passwordEditText.getText().toString());
                    switch (genderRadioGroup.getCheckedRadioButtonId()) {
                        case (R.id.female_button):
                            user.setGender(User.Gender.FEMALE);
                            break;
                        default:
                            user.setGender(User.Gender.MALE);
                    }
                    user.setAge(age);
                    user.setHeight(height);
                    user.setWeight(weight);
                    user.setFitnessGoal(goalSpinner.getSelectedItemPosition());
                    user.setFitnessProgress(0);
                    user.setFoodProgress(0);
                    user.setEventsProgress(0);

                    UserService userService = DependencyFactory.getUserService(getApplication());
                    userService.addUser(user);

                    startActivity(new Intent(SignUp.this, MainActivity.class));
                    finish();
                    //store everything in shared preferences for now
                }
            }
        });
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private boolean attemptLogin() {
        // Store values at the time of the login attempt.

        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();
        boolean cancel = false;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            passwordEditText.setHint("Password required");
            cancel = true;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            confirmPasswordEditText.setHint("Confirm Password required");
            cancel = true;
        }

        if (!password.equals(confirmPassword)){
            Toast.makeText(getApplicationContext(), "Password must match", Toast.LENGTH_LONG).show();
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailEditText.setHint("Email required");
            cancel = true;
        } else if (!isEmailValid(email)) {
            cancel = true;
        }

        if (TextUtils.isEmpty(name)) {
            nameEditText.setHint("Name required");
            cancel = true;
        }

        return cancel;
    }

    public static boolean isEmailValid(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

}

