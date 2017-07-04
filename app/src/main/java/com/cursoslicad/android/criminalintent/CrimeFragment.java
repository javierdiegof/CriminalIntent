package com.cursoslicad.android.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

/**
 * Created by javier on 6/26/17.
 */

public class CrimeFragment extends Fragment{
    private static final String ARG_CRIME_ID = "crime_id";
    public static final String TAG = "CrimeFragment";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    // Falso constructor al que se le pasa el ID del crimen desde el Intent
    public static CrimeFragment newInstance(UUID crimeId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
        Log.d(TAG, "llamada a onCreate()");
    }

    @Override
    public void onPause(){
        super.onPause();
        CrimeLab.get(getActivity()).updateCrime(mCrime);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_crime, container, false);
        Log.d(TAG, "llamada a onCreateView()");

        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // Se ignora, no tenemos algo que hacer cuando no se hace click
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // Qué hacer cuando el texto ha cambiado
                        mCrime.setTitle(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        // No nos interesa hacer algo después de que se haga click
                    }
                }
        );

        mDateButton = (Button) v.findViewById(R.id.crime_date);
        updateDate();
        mDateButton.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        FragmentManager manager = getFragmentManager();
                        // Le pasamos la fecha actual al DatePickerFragment
                        DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                        // Le indicamos al datePickerFragment que el resultado se debe de mandar a
                        // CrimeFragment
                        dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                        dialog.show(manager, DIALOG_DATE);
                    }
                }
        );

        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        // coloca el modelo con el estado del CheckBox
                        mCrime.setSolved(isChecked);
                    }
                }
        );

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_DATE){
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate();
        }
    }

    private void updateDate() {
        mDateButton.setText(mCrime.getDate().toString());
    }

}
