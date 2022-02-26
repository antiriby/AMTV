package com.example.amtvregistrationdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterHouseholdActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseUser user;
    private String userId;

    private DatabaseReference dbReference;
    private EditText editTextHouseholdName, editTextFamilyPassword, editTextConfirmPassword;
    private Button registerHousehold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_household);

        user = FirebaseAuth.getInstance().getCurrentUser();
        dbReference = FirebaseDatabase.getInstance().getReference();

        userId = user.getUid();

        registerHousehold = (Button) findViewById(R.id.btnRegistrationNext);
        registerHousehold.setOnClickListener(this);

        editTextHouseholdName = (EditText) findViewById(R.id.editTextHouseholdName);
        editTextFamilyPassword = (EditText) findViewById(R.id.registrationFamilyPassword);
        editTextConfirmPassword = (EditText) findViewById(R.id.registrationConfirmPassword);
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.btnRegistrationNext:
                createHousehold();
                break;
        }
    }

    private void createHousehold() {
        String householdName = editTextHouseholdName.getText().toString().trim();
        String familyPassword = editTextFamilyPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        if(householdName.isEmpty()){
            editTextHouseholdName.setError("Household Name is required!");
            editTextHouseholdName.requestFocus();
            return;
        }

        if(familyPassword.isEmpty()){
            editTextFamilyPassword.setError("Family password is required!");
            editTextFamilyPassword.requestFocus();
            return;
        }

        if(confirmPassword.isEmpty()){
            editTextConfirmPassword.setError("Must confirm password!");
            editTextConfirmPassword.requestFocus();
            return;
        }

        if(!confirmPassword.equals(familyPassword)){
            editTextConfirmPassword.setError("Passwords do not match. Please try again.");
            editTextConfirmPassword.requestFocus();
            return;
        }

        dbReference.child("Users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User currentUser = snapshot.getValue(User.class);
                DatabaseReference householdRef = dbReference.child("Households");
                householdRef.push().setValue(new Household(householdName,familyPassword,currentUser), new DatabaseReference.CompletionListener() {

                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if (error != null) {
                            Toast.makeText(RegisterHouseholdActivity.this, "Failed to create household. Try Again!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(RegisterHouseholdActivity.this, "Successfully created household!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RegisterHouseholdActivity.this, "Something went wrong.", Toast.LENGTH_LONG).show();;
            }
        });

    }
}