package com.example.quickcashapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quickcashapp.employeeDashboard.MainActivityEmployee;
import com.example.quickcashapp.employeeDashboard.RegisterActivity;
import com.example.quickcashapp.employerDashboard.MainActivityEmployer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Apply window insets for padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firebase Auth and Database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Get references to the UI elements
        EditText emailEditText = findViewById(R.id.email);
        EditText passwordEditText = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.login_button);
        TextView registerButton = findViewById(R.id.register_link);

        // Set register link click listener
        registerButton.setOnClickListener(v -> navigateToRegistration());

        // Set the login button click listener
        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            // Validate input fields
            if (!isValidEmail(email)) {
                Toast.makeText(LoginActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill in the password field", Toast.LENGTH_SHORT).show();
                return;
            }

            // Log in the user
            loginUser(email, password);
        });
    }

    /**
     * Switch the intent to the Registration on user click
     */
    private void navigateToRegistration() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }


    /**
     * Check is the email is valid
     *
     * @param email The email string that the user provided.
     * @return Returns true of false depending on if the email is a valid email
     */
    private boolean isValidEmail(String email) {
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     *
     * @param email
     * @param password
     */
    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Login successful
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = mAuth.getCurrentUser();

                        // Retrieve user role from database
                        fetchUserRoleAndNavigate(user);
                    } else {
                        // Login failed
                        Toast.makeText(LoginActivity.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Get the users role.
     * @param user Firebase variable to get the users role.
     */
    private void fetchUserRoleAndNavigate(FirebaseUser user) {
        if (user == null) {
            Toast.makeText(LoginActivity.this, "User authentication failed", Toast.LENGTH_SHORT).show();
            return;
        }

        mDatabase.child("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String role = dataSnapshot.child("role").getValue(String.class);
                    navigateBasedOnRole(role);
                } else {
                    Toast.makeText(LoginActivity.this, "User role not found in database", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, "Failed to retrieve user role", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Take the user to the correct Dashboard depending on there role
     * @param role a string value indicating if the user is an employer or an employee.
     */
    private void navigateBasedOnRole(String role) {
        Intent intent;
        if ("employer".equals(role)) {
            intent = new Intent(LoginActivity.this, MainActivityEmployer.class);
        } else if ("employee".equals(role)) {
            intent = new Intent(LoginActivity.this, MainActivityEmployee.class);
        } else {
            // Default case if role is undefined
            Toast.makeText(LoginActivity.this, "User role is undefined, please contact support", Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(intent);
        finish(); // Close the login activity
    }
}
