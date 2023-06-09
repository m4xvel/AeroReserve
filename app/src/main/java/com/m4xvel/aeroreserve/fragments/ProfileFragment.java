package com.m4xvel.aeroreserve.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.m4xvel.aeroreserve.BookingAdapter;
import com.m4xvel.aeroreserve.R;
import com.m4xvel.aeroreserve.authorization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ProfileFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Booking> list;
    Query databaseReference;
    BookingAdapter adapter;

    com.m4xvel.aeroreserve.authorization authorization = new authorization();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile, container, false);

        TextView tv = rootView.findViewById(R.id.textView9);
        String s = (String) tv.getText();
        SpannableString ss = new SpannableString(s);
        ss.setSpan(new UnderlineSpan(), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(ss);

        TextView textView = rootView.findViewById(R.id.textView9);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser == null) {
                    Intent intent = new Intent(getActivity(), authorization.getClass());
                    startActivity(intent);
                } else {
                    mAuth.signOut();
                    Toast.makeText(getActivity(), "Выход выполнен", Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, new ProfileFragment());
                    transaction.commit();
                }
            }
        });

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        TextView userEmail = rootView.findViewById(R.id.textView9);
        TextView textView8 = rootView.findViewById(R.id.textView8);
        TextView textView17 = rootView.findViewById(R.id.textView17);

        if (currentUser != null) {
            String email = currentUser.getEmail();
            userEmail.setText(email);
            textView8.setVisibility(View.GONE);
            textView17.setVisibility(View.VISIBLE);

            recyclerView = rootView.findViewById(R.id.bookingRecyclerView);
            FirebaseUser user = mAuth.getCurrentUser();
            databaseReference = FirebaseDatabase.getInstance().getReference("confirmed").orderByChild("email").equalTo(email);
            list = new ArrayList<>();
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new BookingAdapter(getContext(), list);
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

        } else {
            textView8.setVisibility(View.VISIBLE);
            textView17.setVisibility(View.GONE);
        }

        return rootView;
    }
}