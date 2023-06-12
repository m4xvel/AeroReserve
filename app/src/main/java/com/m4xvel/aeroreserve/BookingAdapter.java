package com.m4xvel.aeroreserve;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.m4xvel.aeroreserve.fragments.Booking;

import java.util.ArrayList;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {
     Context context;
     ArrayList<Booking> list;

    public BookingAdapter(Context context, ArrayList<Booking> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = list.get(position);
        holder.city1.setText(booking.getCity1());
        holder.city2.setText(booking.getCity2());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView city1, city2;
        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            city1 = itemView.findViewById(R.id.textView19);
            city2 = itemView.findViewById(R.id.textView18);
        }
    }
}