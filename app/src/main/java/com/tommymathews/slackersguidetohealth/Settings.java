package com.tommymathews.slackersguidetohealth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.util.regex.Pattern;

/**
 * Created by Ashley on 4/29/17.
 */

public class Settings extends AppCompatActivity {

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
    private UserService userService;
    private Button backButton;
    private Button saveSettingsButton;
    private int age;
    private int weight;
    private int height;

    private SharedPreferences sharedPreferences;
    private String email;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences=getSharedPreferences(DbSchema.LOGIN, 0);

        userService = DependencyFactory.getUserService(getApplicationContext());
        email = sharedPreferences.getString(DbSchema.EMAIL,null);
        user = userService.getUserByEmail(email);

        nameEditText = (EditText) findViewById(R.id.name);
        nameEditText.setText(user.getName());

        genderRadioGroup = (RadioGroup) findViewById(R.id.gender_radio_group);
        if(user.getGender() == User.Gender.MALE) {
            genderRadioGroup.check(R.id.male_button);
        } else {
            genderRadioGroup.check(R.id.female_button);
        }

        // Set up the login form.
        emailEditText = (EditText) findViewById(R.id.email);
        emailEditText.setText(user.getEmail());
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
        passwordEditText.setText(user.getPassword());
        confirmPasswordEditText = (EditText) findViewById(R.id.pass_confirm);

        age = user.getAge();
        ageTextView = (TextView) findViewById(R.id.txt_age);
        ageSeekBar = (SeekBar) findViewById(R.id.seekbar_age);
        ageSeekBar.setProgress(age-16);
        ageTextView.setText(Integer.toString(age));
        ageSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ageTextView.setText((i + 16) + "");
                age = 16 + i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        weight = user.getWeight();
        weightTextView = (TextView) findViewById(R.id.weight_display_text);
        weightSeekBar = (SeekBar) findViewById(R.id.weight_bar);
        weightTextView.setText(Integer.toString(weight)+ "lbs.");
        weightSeekBar.setProgress(weight-50);
        weightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                weightTextView.setText((i + 50) + "lbs.");
                weight = 50 + i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        height = user.getHeight();
        heightTextView = (TextView) findViewById(R.id.txt_height);
        heightSeekBar = (SeekBar) findViewById(R.id.seekbar_height);
        heightTextView.setText(user.convertHeight(height));
        heightSeekBar.setProgress(height-12*4);
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

        backButton = (Button) this.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        saveSettingsButton = (Button) findViewById(R.id.btn_save_settings);
        saveSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!attemptSave()) {

                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString(DbSchema.EMAIL, emailEditText.getText().toString());
                    editor.commit();

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

//                    UserService userService = DependencyFactory.getUserService(getApplication());
                    userService.addUser(user);

                    startActivity(new Intent(Settings.this, MainActivity.class));
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
    private boolean attemptSave() {
        // Store values at the time of the save attempt.

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
        return VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr).find();
    }

}


