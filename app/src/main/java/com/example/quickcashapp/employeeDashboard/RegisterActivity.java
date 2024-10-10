package com.example.quickcashapp.employeeDashboard;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quickcashapp.LoginValidator;
import com.example.quickcashapp.R;
import com.example.quickcashapp.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private RadioGroup roleRadioGroup;
    private RadioButton selectedRoleButton;
    public LoginValidator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firebase Auth and Database Reference
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        validator = new LoginValidator();

        // Get references to the UI elements
        EditText nameEditText = findViewById(R.id.name);
        EditText emailEditText = findViewById(R.id.email);
        EditText passwordEditText = findViewById(R.id.password);
        EditText creditCardEditText = findViewById(R.id.credit_card);
        roleRadioGroup = findViewById(R.id.role_radio_group);

        Button registerButton = findViewById(R.id.register_button);


        registerButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString();
            String creditCard = creditCardEditText.getText().toString().trim();

            // Validate input fields
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || creditCard.isEmpty()) {
                setStatusMessage("Please fill in all fields",Color.RED);
                return;
            }

            // Get selected role
            int selectedRoleId = roleRadioGroup.getCheckedRadioButtonId();
            if (selectedRoleId == -1) {
                // No role selected
                setStatusMessage("Please select a role",Color.RED);
                return;
            } else {
                selectedRoleButton = findViewById(selectedRoleId);
            }

            String selectedRole = selectedRoleButton.getText().toString().toLowerCase();

            // Register the user with the selected role
            if(!validator.isValidPassword(password)){
                setStatusMessage(password, Color.RED);
                return;
            }else if(!validator.isValidEmail(email)){
                setStatusMessage("Invalid Email", Color.RED);
                return;
            }else if(!validator.isValidCreditCard(creditCard)){
                setStatusMessage("Invalid Credit Card", Color.RED);
                return;
            }else {
                setStatusMessage("Registering", Color.GREEN);
                registerUser(name, email, password, creditCard, selectedRole);
            }
        });
    }
    public void setStatusMessage(String message, int colour){
        TextView statusMessage = findViewById(R.id.statusMessage);
        statusMessage.setText(message);
        statusMessage.setTextColor(colour);
    }
    private void registerUser(String name, String email, String password, String creditCard, String role) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Registration successful
                        FirebaseUser user = mAuth.getCurrentUser();
                        User newUser = new User(name, email, password, creditCard, role);
                        mDatabase.child("users").child(user.getUid()).setValue(newUser)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        setStatusMessage("Registration successful",Color.GREEN);
                                    } else {
                                        setStatusMessage("Failed to save user data",Color.RED);
                                    }
                                });
                    } else {
                        // Registration failed
                        setStatusMessage("Registration Failed",Color.RED);
                    }
                });
    }
}
