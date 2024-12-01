package com.example.quickcashapp.employeeDashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcashapp.R;
import com.example.quickcashapp.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StoreFavoritesEmployer extends AppCompatActivity {

    private Button seeEmployerList, addFavoriteButton;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private User selectedEmployer;  // 用于存储当前选定的雇主


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_favorite_employer);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Favorites");

        //User selectedEmployer = new User("Julian McCarty", "test@example4.com", "Password123", "4111111111111111", "employer");

        seeEmployerList = findViewById(R.id.seeEmployerList);
        addFavoriteButton = findViewById(R.id.addFavoriteButton);

        /*addFavoriteButton.setOnClickListener(view -> {
            if (selectedEmployer != null) {
                addEmployerToFavorites(selectedEmployer);
            } else {
                Toast.makeText(StoreFavoritesEmployer.this, "No employer selected!", Toast.LENGTH_SHORT).show();
            }
        });*/

        selectedEmployer = getSelectedEmployer(); // 获取选定的雇主，这里模拟从列表选择

        addFavoriteButton.setOnClickListener(view -> {
            if (selectedEmployer != null) {
                addEmployerToFavorites(selectedEmployer.getName());  // 使用雇主名字添加到收藏
            } else {
                Toast.makeText(StoreFavoritesEmployer.this, "No employer selected!", Toast.LENGTH_SHORT).show();
            }
        });

        seeEmployerList.setOnClickListener(view -> {
            Intent intent = new Intent(StoreFavoritesEmployer.this, EmployerList.class);
            startActivity(intent);
        });


    }

    private User getSelectedEmployer() {
        // 这里应该有一个实际的方法来获取选中的雇主，这里我们假设返回一个用户
        return new User("Julian McCarty", "test@example4.com", "Password123", "4111111111111111", "employer");
    }

    private void addEmployerToFavorites(String name) {
        String userId = firebaseAuth.getCurrentUser().getUid();
        databaseReference.child(userId).child("PreferredEmployers").child(name).setValue(true)
                //push().setValue(name)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(StoreFavoritesEmployer.this, "Added to favorites!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(StoreFavoritesEmployer.this, "Failed to add to favorites!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
