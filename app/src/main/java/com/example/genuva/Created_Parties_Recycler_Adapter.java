package com.example.genuva;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Created_Parties_Recycler_Adapter extends RecyclerView.Adapter<Created_Parties_Recycler_Adapter.ViewHolder> {
    ArrayList<PartyModel> arr;
    Context context;
    String place;

    public Created_Parties_Recycler_Adapter(ArrayList<PartyModel> arr, Context context,String place) {
        this.arr = arr;
        this.context = context;
        this.place=place;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.parties_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PartyModel partyModel = arr.get(position);
        Picasso.get().load(partyModel.getPartyCoverImage()).into(holder.img);
        holder.partyname.setText(partyModel.getPartyName());
        holder.parytime.setText(partyModel.getPartyTime());
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView img;
        public TextView partyname;
        public TextView parytime;
        public CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.party_img_created);
            partyname = itemView.findViewById(R.id.party_name_created);
            parytime = itemView.findViewById(R.id.party_time_created);
            cardView = itemView.findViewById(R.id.party_card);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getLayoutPosition();
            Intent intent = new Intent(context, BookActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("partyKey",arr.get(pos).getPartyKey());
            intent.putExtra("partyPlace",place);
            context.startActivity(intent);
        }
    }
}
