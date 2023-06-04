package com.m4xvel.aeroreserve.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.m4xvel.aeroreserve.R;
import com.m4xvel.aeroreserve.information;

public class HomeFragment extends Fragment {

    InformationFragment informationFragment = new InformationFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);

        AutoCompleteTextView autoCompleteTextView1 = rootView.findViewById(R.id.autoCompleteTextView6);
        AutoCompleteTextView autoCompleteTextView2 = rootView.findViewById(R.id.autoCompleteTextView7);

        autoCompleteTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] cities = getResources().getStringArray(R.array.cities);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, cities);
                autoCompleteTextView1.setAdapter(adapter);
                autoCompleteTextView1.showDropDown();
            }
        });
        autoCompleteTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] cities = getResources().getStringArray(R.array.cities);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, cities);
                autoCompleteTextView2.setAdapter(adapter);
                autoCompleteTextView2.showDropDown();
            }
        });

        Button button2 = rootView.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, informationFragment).commit();
            }
        });

        return rootView;
    }
}