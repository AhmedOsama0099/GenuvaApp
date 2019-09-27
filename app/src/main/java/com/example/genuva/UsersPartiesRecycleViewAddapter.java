package com.example.genuva;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UsersPartiesRecycleViewAddapter extends RecyclerView.Adapter<UsersPartiesRecycleViewAddapter.ViewHolder> {
    ArrayList<UserPartiesModel>arr;
    Context context;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRef = database.getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userId = user.getUid();

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
        public Button btn_del;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            partyName=itemView.findViewById(R.id.user_partyName);
            patyTime=itemView.findViewById(R.id.user_partyTime);
            partyPrice=itemView.findViewById(R.id.user_partyPrice);
            seatsNumber=itemView.findViewById(R.id.user_seatsNumbers);
            seatsCount=itemView.findViewById(R.id.user_seatsCount);
            btn_del = itemView.findViewById(R.id.delete);

            btn_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ArrayList<String> seats = new ArrayList<>();
                            for (DataSnapshot dat : dataSnapshot.child("Users").child(userId).child("place").child(arr.get(getAdapterPosition()).getPartyPlace()).
                                    child(arr.get(getAdapterPosition()).getPartyId()).child("Seats").getChildren()){

                                seats.add(dat.getKey());
                            }

                            mRef.child("Users").child(userId).child("place").child(arr.get(getAdapterPosition()).getPartyPlace()).
                                    child(arr.get(getAdapterPosition()).getPartyId()).removeValue();
                            for (int i = 0; i < seats.size(); i++){

                                mRef.child(arr.get(getAdapterPosition()).getPartyPlace())
                                        .child(arr.get(getAdapterPosition()).getPartyId()).
                                        child("Seats").child(seats.get(i)).child("seat_state").setValue(new Boolean(false));
                            }

                            arr.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });

        }



    }
}
