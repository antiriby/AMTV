package com.example.amtvregistrationdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private TextView  banner, registerUser;
    private EditText editTextFirstName, editTextLastName, editTextEmail, editTextPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.txtBanner);
        banner.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.btnRegister);
        registerUser.setOnClickListener(this);

        editTextFirstName = (EditText) findViewById(R.id.txtFirstName);
        editTextLastName = (EditText) findViewById(R.id.txtLastName);
        editTextEmail = (EditText) findViewById(R.id.txtEmail);
        editTextPassword= (EditText) findViewById(R.id.txtPassword);

        progressBar = (ProgressBar) findViewById(R.id.registrationProgressBar);



    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.txtBanner:
                finish();
                break;

            case R.id.btnRegister:
                registerUser();
                break;

        }

    }

    private void registerUser() {
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (firstName.isEmpty()){
            editTextFirstName.setError("First name is required!");
            editTextFirstName.requestFocus();
            return;
        }

        if (lastName.isEmpty()){
            editTextLastName.setError("Last name is required!");
            editTextLastName.requestFocus();
            return;
        }

        if (email.isEmpty()){
            editTextEmail.setError("Email Address is required!");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please provide a valid email address (i.e. @gmail.com");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6){
            editTextPassword.setError("Minimum password length is 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        //Create a household
        Household household = new Household(
                new ArrayList<User>() {
                    {
                        add(new User("Briana", "Antiri", "luvu@gmail.com"));
                    }
                });


        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(firstName, lastName, email, household);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(RegisterActivity.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);

                                                //redirect to Login layout by closing this activity
                                                finish();


                                            } else {
                                                Toast.makeText(RegisterActivity.this, "Failed to register user. Try Again!", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });

                        } else {
                            Toast.makeText(RegisterActivity.this, "Failed to register user. Try Again!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}