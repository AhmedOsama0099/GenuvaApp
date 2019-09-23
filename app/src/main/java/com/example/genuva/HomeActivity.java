package com.example.genuva;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public  class HomeActivity extends AppCompatActivity {

    Button btn_LogOut;
    Button btn_CreateParty;
    Button btn_myTickets;
    private FirebaseAuth mauthlogout;

    ImageView imageViewSaqia , imageViewOperaHouse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mauthlogout  = FirebaseAuth.getInstance();
        btn_CreateParty = findViewById(R.id.createparty);
        imageViewSaqia = findViewById(R.id.image_sakia);
        imageViewOperaHouse = findViewById(R.id.image_opera);
        btn_myTickets = findViewById(R.id.btn_myTickets);


        imageViewSaqia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent party = new Intent(HomeActivity.this , Parties.class);
                party.putExtra("place","Sakia El Sawy");
                startActivity(party);
            }
        });

        imageViewOperaHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent party = new Intent(HomeActivity.this , Parties.class);
                party.putExtra("place","Opera House");
                startActivity(party);
            }
        });

        btn_CreateParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this,CreateParty.class);
                startActivity(intent);
            }
        });

        btn_LogOut = findViewById(R.id.logout);
        btn_LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mauthlogout.signOut();
                finish();
                startActivity(new Intent(HomeActivity.this , LoginActivity.class));
            }
        });
        btn_myTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,BookedSeatsActivity.class);
                startActivity(intent);
            }
        });

    }
}
