package com.example.quickcashapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.quickcashapp.data.model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationFragment extends Fragment {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private RadioGroup roleRadioGroup;
    private RadioButton selectedRoleButton;



    public View onCreateView( LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);


        // Initialize Firebase Auth and Database Reference
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Get references to the UI elements
        EditText nameEditText = view.findViewById(R.id.name);
        EditText emailEditText = view.findViewById(R.id.email);
        EditText passwordEditText = view.findViewById(R.id.password);
        EditText creditCardEditText = view.findViewById(R.id.credit_card);
        roleRadioGroup = view.findViewById(R.id.role_radio_group);
        Button registerButton = view.findViewById(R.id.register_button);

        registerButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String creditCard = creditCardEditText.getText().toString().trim();

            // Validate input fields
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || creditCard.isEmpty()) {
                Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Get selected role
            int selectedRoleId = roleRadioGroup.getCheckedRadioButtonId();
            if (selectedRoleId == -1) {
                // No role selected
                Toast.makeText(getActivity(), "Please select a role", Toast.LENGTH_SHORT).show();
                return;
            } else {
                selectedRoleButton = view.findViewById(selectedRoleId);
            }

            String selectedRole = selectedRoleButton.getText().toString().toLowerCase();

            // Register the user with the selected role
            registerUser(name, email, password, creditCard, selectedRole);
        });

        return view;
    }

    private void registerUser(String name, String email, String password, String creditCard, String role) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Registration successful
                        FirebaseUser user = mAuth.getCurrentUser();
                        Users newUser = new Users(name, email, password, creditCard, role);
                        mDatabase.child("users").child(user.getUid()).setValue(newUser)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(getActivity(), "Registration successful", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getActivity(), "Failed to save user data", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        // Registration failed
                        Toast.makeText(getActivity(), "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
