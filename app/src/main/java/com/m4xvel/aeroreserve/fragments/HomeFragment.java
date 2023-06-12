package com.m4xvel.aeroreserve.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.m4xvel.aeroreserve.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class HomeFragment extends Fragment {

    InformationFragment informationFragment = new InformationFragment();

    private DatabaseReference mCityRef;

    private static List<String> mCityListAutoCompleteTextView6 = new ArrayList<>();
    private static List<String> mCityListAutoCompleteTextView7 = new ArrayList<>();

    private static final String TAG = "HomeFragment";
    private static final String onCancelled = "nCancelled";
    private static final String from_to = "from_to";
    private static final String FLIGHTS_COLLECTION = "flights";
    private static final String KEY_PRICE = "price";
    private static final String KEY_COMPANY = "company";
    private static final String KEY_SEATS = "seats";
    private static final String KEY_DATEFROM = "date_from";
    private static final String KEY_DATETO = "date_to";
    private static final String KEY_TIMEFROM = "time_from";
    private static final String KEY_TIMETO = "time_to";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_main, container, false);

        // Get a reference to the Firebase Realtime Database
        mCityRef = FirebaseDatabase.getInstance().getReference("cities");

        // Add the default city list to the database
        mCityRef.child("list").setValue(Arrays.asList("Москва", "Пенза", "Самара", "Сочи", "Казань", "Уфа", "Омск", "Тюмень", "Тула", "Калуга"));

        // Set the adapter for the AutoCompleteTextView6
        ArrayAdapter<String> adapter6 = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, mCityListAutoCompleteTextView6);
        AutoCompleteTextView autoCompleteTextView6 = rootView.findViewById(R.id.autoCompleteTextView6);
        autoCompleteTextView6.setAdapter(adapter6);

        // Set the adapter for the AutoCompleteTextView7
        ArrayAdapter<String> adapter7 = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, mCityListAutoCompleteTextView7);
        AutoCompleteTextView autoCompleteTextView7 = rootView.findViewById(R.id.autoCompleteTextView7);
        autoCompleteTextView7.setAdapter(adapter7);

        // Add a listener to update the adapter when new cities are added to the database
        mCityRef.child("list").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mCityListAutoCompleteTextView6.clear();
                mCityListAutoCompleteTextView7.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String city = snapshot.getValue(String.class);
                    mCityListAutoCompleteTextView6.add(city);
                    mCityListAutoCompleteTextView7.add(city);
                }
                adapter6.notifyDataSetChanged();
                adapter7.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
        Button bigButton = rootView.findViewById(R.id.BigButton);
        bigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Получаем выбранные города из AutoCompleteTextView
                String city1 = autoCompleteTextView6.getText().toString().trim();
                String city2 = autoCompleteTextView7.getText().toString().trim();

                // Подключаемся к коллекции flights в Firebase
                // Определяем условия запроса по полям from и to
                Query query = FirebaseDatabase.getInstance().getReference(FLIGHTS_COLLECTION)
                        .orderByChild(from_to)
                        .equalTo(city1 + "_" + city2);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            // Получаем значения полей цены, компании, типа и количества свободных мест из Firebase
                            String date_from = snapshot.child(KEY_DATEFROM).getValue(String.class);
                            String date_to = snapshot.child(KEY_DATETO).getValue(String.class);
                            String time_from = snapshot.child(KEY_TIMEFROM).getValue(String.class);
                            String time_to = snapshot.child(KEY_TIMETO).getValue(String.class);
                            String price = snapshot.child(KEY_PRICE).getValue(String.class);
                            String company = snapshot.child(KEY_COMPANY).getValue(String.class);
                            Integer seats = snapshot.child(KEY_SEATS).getValue(Integer.class);

                            // Создаем Bundle с выбранными городами и значениями цены, компании, типа и количества свободных мест
                            Bundle args = new Bundle();
                            args.putString("city1", city1);
                            args.putString("city2", city2);
                            args.putString(KEY_DATEFROM, date_from);
                            args.putString(KEY_DATETO, date_to);
                            args.putString(KEY_TIMEFROM, time_from);
                            args.putString(KEY_TIMETO, time_to);
                            args.putString(KEY_PRICE, price);
                            args.putString(KEY_COMPANY, company);
                            args.putInt(KEY_SEATS, seats);

                            // Открываем информационный фрагмент, передавая ему Bundle
                            InformationFragment informationFragment = new InformationFragment();
                            informationFragment.setArguments(args);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, informationFragment)
                                    .commit();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, onCancelled, databaseError.toException());
                    }
                });
            }
        });
        Button button2 = rootView.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Получаем выбранные города из AutoCompleteTextView
                String city1 = "Москва";
                String city2 = "Сочи";

                // Подключаемся к коллекции flights в Firebase
                // Определяем условия запроса по полям from и to
                Query query = FirebaseDatabase.getInstance().getReference(FLIGHTS_COLLECTION)
                        .orderByChild(from_to)
                        .equalTo(city1 + "_" + city2);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            // Получаем значения полей цены, компании, типа и количества свободных мест из Firebase
                            String date_from = snapshot.child(KEY_DATEFROM).getValue(String.class);
                            String date_to = snapshot.child(KEY_DATETO).getValue(String.class);
                            String time_from = snapshot.child(KEY_TIMEFROM).getValue(String.class);
                            String time_to = snapshot.child(KEY_TIMETO).getValue(String.class);
                            String price = snapshot.child(KEY_PRICE).getValue(String.class);
                            String company = snapshot.child(KEY_COMPANY).getValue(String.class);
                            Integer seats = snapshot.child(KEY_SEATS).getValue(Integer.class);

                            // Создаем Bundle с выбранными городами и значениями цены, компании, типа и количества свободных мест
                            Bundle args = new Bundle();
                            args.putString("city1", city1);
                            args.putString("city2", city2);
                            args.putString(KEY_DATEFROM, date_from);
                            args.putString(KEY_DATETO, date_to);
                            args.putString(KEY_TIMEFROM, time_from);
                            args.putString(KEY_TIMETO, time_to);
                            args.putString(KEY_PRICE, price);
                            args.putString(KEY_COMPANY, company);
                            args.putInt(KEY_SEATS, seats);

                            // Открываем информационный фрагмент, передавая ему Bundle
                            InformationFragment informationFragment = new InformationFragment();
                            informationFragment.setArguments(args);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, informationFragment)
                                    .commit();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, onCancelled, databaseError.toException());
                    }
                });
            }
        });
        Button button5 = rootView.findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Получаем выбранные города из AutoCompleteTextView
                String city1 = "Уфа";
                String city2 = "Казань";

                // Подключаемся к коллекции flights в Firebase
                // Определяем условия запроса по полям from и to
                Query query = FirebaseDatabase.getInstance().getReference(FLIGHTS_COLLECTION)
                        .orderByChild(from_to)
                        .equalTo(city1 + "_" + city2);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            // Получаем значения полей цены, компании, типа и количества свободных мест из Firebase
                            String date_from = snapshot.child(KEY_DATEFROM).getValue(String.class);
                            String date_to = snapshot.child(KEY_DATETO).getValue(String.class);
                            String time_from = snapshot.child(KEY_TIMEFROM).getValue(String.class);
                            String time_to = snapshot.child(KEY_TIMETO).getValue(String.class);
                            String price = snapshot.child(KEY_PRICE).getValue(String.class);
                            String company = snapshot.child(KEY_COMPANY).getValue(String.class);
                            Integer seats = snapshot.child(KEY_SEATS).getValue(Integer.class);

                            // Создаем Bundle с выбранными городами и значениями цены, компании, типа и количества свободных мест
                            Bundle args = new Bundle();
                            args.putString("city1", city1);
                            args.putString("city2", city2);
                            args.putString(KEY_DATEFROM, date_from);
                            args.putString(KEY_DATETO, date_to);
                            args.putString(KEY_TIMEFROM, time_from);
                            args.putString(KEY_TIMETO, time_to);
                            args.putString(KEY_PRICE, price);
                            args.putString(KEY_COMPANY, company);
                            args.putInt(KEY_SEATS, seats);

                            // Открываем информационный фрагмент, передавая ему Bundle
                            InformationFragment informationFragment = new InformationFragment();
                            informationFragment.setArguments(args);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, informationFragment)
                                    .commit();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, onCancelled, databaseError.toException());
                    }
                });
            }
        });
        Button button4 = rootView.findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Получаем выбранные города из AutoCompleteTextView
                String city1 = "Пенза";
                String city2 = "Москва";

                // Подключаемся к коллекции flights в Firebase
                // Определяем условия запроса по полям from и to
                Query query = FirebaseDatabase.getInstance().getReference(FLIGHTS_COLLECTION)
                        .orderByChild(from_to)
                        .equalTo(city1 + "_" + city2);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            // Получаем значения полей цены, компании, типа и количества свободных мест из Firebase
                            String date_from = snapshot.child(KEY_DATEFROM).getValue(String.class);
                            String date_to = snapshot.child(KEY_DATETO).getValue(String.class);
                            String time_from = snapshot.child(KEY_TIMEFROM).getValue(String.class);
                            String time_to = snapshot.child(KEY_TIMETO).getValue(String.class);
                            String price = snapshot.child(KEY_PRICE).getValue(String.class);
                            String company = snapshot.child(KEY_COMPANY).getValue(String.class);
                            Integer seats = snapshot.child(KEY_SEATS).getValue(Integer.class);

                            // Создаем Bundle с выбранными городами и значениями цены, компании, типа и количества свободных мест
                            Bundle args = new Bundle();
                            args.putString("city1", city1);
                            args.putString("city2", city2);
                            args.putString(KEY_DATEFROM, date_from);
                            args.putString(KEY_DATETO, date_to);
                            args.putString(KEY_TIMEFROM, time_from);
                            args.putString(KEY_TIMETO, time_to);
                            args.putString(KEY_PRICE, price);
                            args.putString(KEY_COMPANY, company);
                            args.putInt(KEY_SEATS, seats);

                            // Открываем информационный фрагмент, передавая ему Bundle
                            InformationFragment informationFragment = new InformationFragment();
                            informationFragment.setArguments(args);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, informationFragment)
                                    .commit();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, onCancelled, databaseError.toException());
                    }
                });
            }
        });
        Button button6 = rootView.findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Получаем выбранные города из AutoCompleteTextView
                String city1 = "Самара";
                String city2 = "Омск";

                // Подключаемся к коллекции flights в Firebase
                // Определяем условия запроса по полям from и to
                Query query = FirebaseDatabase.getInstance().getReference(FLIGHTS_COLLECTION)
                        .orderByChild(from_to)
                        .equalTo(city1 + "_" + city2);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            // Получаем значения полей цены, компании, типа и количества свободных мест из Firebase
                            String date_from = snapshot.child(KEY_DATEFROM).getValue(String.class);
                            String date_to = snapshot.child(KEY_DATETO).getValue(String.class);
                            String time_from = snapshot.child(KEY_TIMEFROM).getValue(String.class);
                            String time_to = snapshot.child(KEY_TIMETO).getValue(String.class);
                            String price = snapshot.child(KEY_PRICE).getValue(String.class);
                            String company = snapshot.child(KEY_COMPANY).getValue(String.class);
                            Integer seats = snapshot.child(KEY_SEATS).getValue(Integer.class);

                            // Создаем Bundle с выбранными городами и значениями цены, компании, типа и количества свободных мест
                            Bundle args = new Bundle();
                            args.putString("city1", city1);
                            args.putString("city2", city2);
                            args.putString(KEY_DATEFROM, date_from);
                            args.putString(KEY_DATETO, date_to);
                            args.putString(KEY_TIMEFROM, time_from);
                            args.putString(KEY_TIMETO, time_to);
                            args.putString(KEY_PRICE, price);
                            args.putString(KEY_COMPANY, company);
                            args.putInt(KEY_SEATS, seats);

                            // Открываем информационный фрагмент, передавая ему Bundle
                            InformationFragment informationFragment = new InformationFragment();
                            informationFragment.setArguments(args);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, informationFragment)
                                    .commit();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, onCancelled, databaseError.toException());
                    }
                });
            }
        });
        return rootView;
    }
}