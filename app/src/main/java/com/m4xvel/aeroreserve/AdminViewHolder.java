package com.m4xvel.aeroreserve;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.m4xvel.aeroreserve.fragments.Booking;

public class AdminViewHolder extends RecyclerView.ViewHolder {
    private TextView fromTextView;
    private TextView toTextView;
    private TextView received_FIO;
    private TextView received_number_phone;
    private TextView booking_id_text;
    private Button inputButton;
    private Button rejectButton;

    public AdminViewHolder(@NonNull View itemView) {
        super(itemView);
        received_FIO = itemView.findViewById(R.id.received_FIO);
        received_number_phone = itemView.findViewById(R.id.received_number_phone);
        booking_id_text = itemView.findViewById(R.id.booking_id_text);
        inputButton = itemView.findViewById(R.id.input_button);
        rejectButton = itemView.findViewById(R.id.reject_button);
    }

    public void bind(Booking booking, View itemView) {
        fromTextView.setText(booking.getCity1());
        toTextView.setText(booking.getCity2());
        booking_id_text.setText(booking.getBookingId());
        received_FIO.setText(booking.getFullName());
        received_number_phone.setText(booking.getPhoneNumber());

    }
}
