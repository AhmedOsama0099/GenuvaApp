package com.example.genuva;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookedSeatsActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRef = database.getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userId = user.getUid();
    ArrayList<UserPartiesModel> userPartiesModelsArr = new ArrayList<>();
    RecyclerView userParties;
    UsersPartiesRecycleViewAddapter addapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_seats);
        userParties=findViewById(R.id.usersPartiesRecycle);
        //RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        userParties.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        addapter=new UsersPartiesRecycleViewAddapter(userPartiesModelsArr,this);
        userParties.setAdapter(addapter);
        //
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> partiesPlace = new ArrayList<>();
                for (DataSnapshot datt : dataSnapshot.child("Users").child(userId).child("place").getChildren()) {
                    partiesPlace.add(datt.getKey());
                }
                for (int i = 0; i < partiesPlace.size(); i++) {
                    for (DataSnapshot datt : dataSnapshot.child("Users").child(userId).child("place").child(partiesPlace.get(i)).getChildren()) {
                        String partyName = dataSnapshot.child(partiesPlace.get(i)).child(datt.getKey()).child("partyName").getValue(String.class);
                        String partyTime = dataSnapshot.child(partiesPlace.get(i)).child(datt.getKey()).child("partyTime").getValue(String.class);
                        String partyPrice=datt.child("TotalPrice").getValue(String.class)+"L.E";
                        String seatsNumbers=new String();
                        for(DataSnapshot datt2:datt.child("Seats").getChildren()){
                            seatsNumbers+=datt2.getKey()+";";
                        }
                        String []count=seatsNumbers.split(";");
                        int seatsCount=count.length;
                        userPartiesModelsArr.add(new UserPartiesModel(partyName,partyTime,partyPrice,seatsNumbers,seatsCount));
                    }
                }
                addapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
