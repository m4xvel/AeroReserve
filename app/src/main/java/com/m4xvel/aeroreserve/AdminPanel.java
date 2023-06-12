package com.m4xvel.aeroreserve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.m4xvel.aeroreserve.fragments.Booking;
import com.m4xvel.aeroreserve.fragments.HomeFragment;
import com.m4xvel.aeroreserve.fragments.ProfileFragment;

import java.util.ArrayList;
import java.util.Collections;

public class AdminPanel extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView logoutTextView;
    RecyclerView recyclerView;
    ArrayList<Booking> list;
    Query databaseReference;
    AdminAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_panel);

        recyclerView = findViewById(R.id.adminRecyclerView);
        databaseReference = FirebaseDatabase.getInstance().getReference("booking");
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdminAdapter(this, list);
        recyclerView.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Booking booking = dataSnapshot.getValue(Booking.class);
                    list.add(booking);
                }
                Collections.reverse(list);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        mAuth = FirebaseAuth.getInstance();
        logoutTextView = findViewById(R.id.logout);

        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
            }
        });
    }
    @Override
    protected void onStop() {
        AdminPanel.super.onStop();
        mAuth.signOut();
    }
}