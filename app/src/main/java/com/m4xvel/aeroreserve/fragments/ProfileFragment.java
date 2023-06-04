package com.m4xvel.aeroreserve.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
import com.m4xvel.aeroreserve.R;
import com.m4xvel.aeroreserve.authorization;

public class ProfileFragment extends Fragment {

    private TextView textView8;
    private TextView textView9;
    private TextView textView17;
    private FirebaseAuth mAuth;

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
        } else {
            textView8.setVisibility(View.VISIBLE);
            textView17.setVisibility(View.GONE);
        }

        return rootView;
    }
}