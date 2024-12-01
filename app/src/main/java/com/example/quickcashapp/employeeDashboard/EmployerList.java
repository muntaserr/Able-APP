package com.example.quickcashapp.employeeDashboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcashapp.R;
import com.example.quickcashapp.User;
import com.example.quickcashapp.UserListing;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EmployerList extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> employerList;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_list);

        listView = findViewById(R.id.listView);
        employerList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, employerList);
        listView.setAdapter(adapter);

        firebaseAuth = FirebaseAuth.getInstance();
        String userId = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Favorites")
                .child(userId)
                .child("PreferredEmployers");


        loadEmployerList();
    }

    private void loadEmployerList() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                employerList.clear();  // 清除旧数据
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String employerName = snapshot.getValue(String.class);  // 获取雇主的名字
                    employerList.add(employerName);  // 添加到列表中
                }
                adapter.notifyDataSetChanged();  // 通知数据改变，更新UI
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(EmployerList.this, "Failed to load employer names", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
