package com.example.quickcashapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.quickcashapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements LoginFragment.loadPageListener {

    @Override
    public void loadPage(Fragment frag){
        replaceFrag(frag);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFrag(new LoginFragment());


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            if(item.getItemId() == R.id.postJob) {
                replaceFrag(new PostJobFragment());
            }else if(item.getItemId() == R.id.login) {
                replaceFrag(new LoginFragment());
            }else if(item.getItemId() ==R.id.searchJobs){
                    replaceFrag(new SearchJobFragment());
            }else{
                replaceFrag(new SearchJobFragment());
            }
            return true;
        });




    }


    public void replaceFrag(Fragment frag){
        FragmentManager fragManager = getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragManager.beginTransaction();
        fragTransaction.replace(R.id.frameLayout,frag);
        fragTransaction.commit();
    }


}