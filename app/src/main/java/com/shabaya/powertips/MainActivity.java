package com.shabaya.powertips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser == null){
            //Toast.makeText(this, "what", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, OnBoardingActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }else {
           // Toast.makeText(this, firebaseUser.getUid(), Toast.LENGTH_SHORT).show();
        }

        BottomNavigationView bottomNav = findViewById(R.id.nav_bottom);

        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AllFragment()).commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()){
                case R.id.nav_all:
                    selectedFragment = new AllFragment();
                    break;
                case R.id.nav_betOfTheDay:
                    selectedFragment = new BetOfTheDayFragment();
                    break;
                case R.id.nav_combos:
                    selectedFragment = new CombosFragment();
                    break;
                case R.id.nav_over_btts:
                    selectedFragment = new OverBttsFragment();
                    break;
                case R.id.nav_singles:
                    selectedFragment = new SinglesFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };

}