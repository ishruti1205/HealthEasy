package com.example.healtheasy;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class IntroActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private IntroPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        viewPager = findViewById(R.id.viewPager);
        adapter = new IntroPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    // Method to navigate to the next fragment in the ViewPager
    public void navigateToNextFragment(Fragment fragment) {
        int currentItem = viewPager.getCurrentItem();
        int nextItem = currentItem + 1;

        if (nextItem < adapter.getCount()) {
            viewPager.setCurrentItem(nextItem);
        } else {
            // If the nextItem exceeds the adapter's count, start LoginActivity
            startActivity(new Intent(IntroActivity.this, LoginActivity.class));
            finish(); // Finish the IntroActivity
        }
    }
}
