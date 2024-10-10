package com.example.healtheasy;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class IntroPagerAdapter extends FragmentStatePagerAdapter {

    public IntroPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // Determine which fragment to show based on the position
        switch (position) {
            case 0:
                return new Next1Activity();
            case 1:
                return new Next2Activity();
            case 2:
                return new Next3Activity();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // Total number of pages
        return 3; // 3 pages (Next1, Next2, Next3)
    }
}
