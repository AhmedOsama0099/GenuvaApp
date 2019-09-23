package com.example.genuva;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UsersPartiesRecycleViewAddapter extends RecyclerView.Adapter<UsersPartiesRecycleViewAddapter.ViewHolder> {
    ArrayList<UserPartiesModel>arr;
    Context context;

    public UsersPartiesRecycleViewAddapter(ArrayList<UserPartiesModel> arr, Context context) {
        this.arr = arr;
        this.context = context;
    }

    @NonNull
    @Override
    public UsersPartiesRecycleViewAddapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_parties_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersPartiesRecycleViewAddapter.ViewHolder holder, int position) {
        holder.partyName.setText(arr.get(position).getPartyName());
        holder.patyTime.setText(arr.get(position).getPartyTime());
        holder.partyPrice.setText(arr.get(position).getTotalPartyPrice());
        holder.seatsNumber.setText(arr.get(position).getSeatsNumbers());
        holder.seatsCount.setText(Integer.toString(arr.get(position).getSeatsCount()));
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView partyName,patyTime,partyPrice,seatsNumber,seatsCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            partyName=itemView.findViewById(R.id.user_partyName);
            patyTime=itemView.findViewById(R.id.user_partyTime);
            partyPrice=itemView.findViewById(R.id.user_partyPrice);
            seatsNumber=itemView.findViewById(R.id.user_seatsNumbers);
            seatsCount=itemView.findViewById(R.id.user_seatsCount);
        }
    }
}
