package com.cursoslicad.android.criminalintent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

public class CrimeActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment(){
        return new CrimeFragment();
    }
}
