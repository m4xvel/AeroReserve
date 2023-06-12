package com.m4xvel.aeroreserve;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.m4xvel.aeroreserve.fragments.Booking;

import java.util.ArrayList;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.AdminViewHolder> {
    Context context;
    ArrayList<Booking> list;

    public AdminAdapter(Context context, ArrayList<Booking> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdminAdapter.AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_panel_item, parent, false);
        return new AdminAdapter.AdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminAdapter.AdminViewHolder holder, int position) {
        Booking booking = list.get(position);
        holder.fullName.setText(booking.getFullName());
        holder.phoneNumber.setText(booking.getPhoneNumber());
        holder.booking_id_text.setText(booking.getBookingId());
        holder.received_FIO.setText(booking.getFullName());
        holder.received_number_phone.setText(booking.getPhoneNumber());

        DatabaseReference bookingRef = FirebaseDatabase.getInstance().getReference("booking");

        holder.inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookingId = holder.booking_id_text.getText().toString();
                bookingRef.child(bookingId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Booking booking = snapshot.getValue(Booking.class);
                        // Add the booking to the confirmed collection
                        DatabaseReference confirmedRef = FirebaseDatabase.getInstance().getReference("confirmed");
                        confirmedRef.child(bookingId).setValue(booking);
                        // Remove the booking from the booking collection
                        bookingRef.child(bookingId).removeValue();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });

        holder.rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookingId = holder.booking_id_text.getText().toString();
                bookingRef.child(bookingId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // Remove the booking from the booking collection
                        bookingRef.child(bookingId).removeValue();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AdminViewHolder extends RecyclerView.ViewHolder {
        TextView fromTextView;
        TextView toTextView;
        TextView received_FIO;
        TextView received_number_phone;
        TextView booking_id_text;
        Button inputButton;
        Button rejectButton;
        TextView city1, city2, fullName, phoneNumber, bookingId;

        public AdminViewHolder(@NonNull View itemView) {
            super(itemView);
            received_FIO = itemView.findViewById(R.id.received_FIO);
            received_number_phone = itemView.findViewById(R.id.received_number_phone);
            booking_id_text = itemView.findViewById(R.id.booking_id_text);
            inputButton = itemView.findViewById(R.id.input_button);
            rejectButton = itemView.findViewById(R.id.reject_button);
            fullName = itemView.findViewById(R.id.received_FIO);
            phoneNumber = itemView.findViewById(R.id.received_number_phone);
        }
    }
}
