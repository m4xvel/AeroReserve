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
import android.widget.Button;
import android.widget.TextView;

import com.m4xvel.aeroreserve.MainActivity;
import com.m4xvel.aeroreserve.R;
import com.m4xvel.aeroreserve.booking;
import com.m4xvel.aeroreserve.error;

public class InformationFragment extends Fragment {
    BookingFragment bookingFragment = new BookingFragment();
    error error = new error();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.information, container, false);

        Button button8 = rootView.findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, bookingFragment).commit();
            }
        });
        Button button7 = rootView.findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), error.getClass());
                startActivity(intent);
            }
        });
        return rootView;
    }
}