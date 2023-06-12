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

    private static final String KEY_PRICE = "price";
    private static final String KEY_COMPANY = "company";
    private static final String KEY_SEATS = "seats";
    private static final String KEY_DATEFROM = "date_from";
    private static final String KEY_DATETO = "date_to";
    private static final String KEY_TIMEFROM = "time_from";
    private static final String KEY_TIMETO = "time_to";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.information, container, false);

        Bundle args = getArguments();
        String city1 = args.getString("city1");
        String city2 = args.getString("city2");
        String date_from = args.getString(KEY_DATEFROM);
        String date_to = args.getString(KEY_DATETO);
        String time_from = args.getString(KEY_TIMEFROM);
        String time_to = args.getString(KEY_TIMETO);
        String price = args.getString(KEY_PRICE);
        String company = args.getString(KEY_COMPANY);
        Integer seats = args.getInt(KEY_SEATS);

        // обновляем TextView со значениями цены, компании, типа и количества свободных мест
        TextView textView22 = rootView.findViewById(R.id.textView22);
        textView22.setText(city1);
        TextView textView23 = rootView.findViewById(R.id.textView23);
        textView23.setText(city2);
        TextView priceTextView = rootView.findViewById(R.id.textView21);
        priceTextView.setText(price);
        TextView companyTextView = rootView.findViewById(R.id.textView27);
        companyTextView.setText(company);
        TextView seatsTextView = rootView.findViewById(R.id.textView35);
        seatsTextView.setText(""+seats);

        TextView dateFromTextView = rootView.findViewById(R.id.textView42);
        dateFromTextView.setText(date_from);
        TextView dateToTextView = rootView.findViewById(R.id.textView43);
        dateToTextView.setText(date_to);
        TextView timeFromTextView = rootView.findViewById(R.id.textView38);
        timeFromTextView.setText(time_from);
        TextView timeToTextView = rootView.findViewById(R.id.textView41);
        timeToTextView.setText(time_to);

        Button button8 = rootView.findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BookingFragment bookingFragment = new BookingFragment();
                bookingFragment.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, bookingFragment)
                        .commit();
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
        // получаем значения выбранных городов, цены, компании, типа и количества свободных мест

        return rootView;
    }
}