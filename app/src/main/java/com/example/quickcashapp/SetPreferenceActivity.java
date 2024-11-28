package com.example.quickcashapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetPreferenceActivity extends ComponentActivity {
    private EditText editTextJobType;
    private EditText editTextLocation;

    private DatabaseReference databaseRef;  // Firebase 数据库引用

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_preference);
        EdgeToEdge.enable(this);

        // 初始化 Firebase
        databaseRef = FirebaseDatabase.getInstance().getReference();

        editTextJobType = findViewById(R.id.editTextJobType);
        editTextLocation = findViewById(R.id.editTextLocation);
        Button btnSavePreferences = findViewById(R.id.btnSavePreferences);

        btnSavePreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferencesToFirebase(editTextJobType.getText().toString(), editTextLocation.getText().toString());
            }
        });
    }

    private void savePreferencesToFirebase(String jobType, String location) {
        String userId = "some_user_id"; // 这应当是动态获取的用户标识，例如从登录信息或设备信息中获取
        // 存储到 Firebase 的路径
        DatabaseReference userPrefRef = databaseRef.child("userPreferences").child(userId);
        userPrefRef.child("jobType").setValue(jobType);
        userPrefRef.child("location").setValue(location);

        Toast.makeText(this, "Preferences Saved to Firebase!", Toast.LENGTH_SHORT).show();
    }
}


