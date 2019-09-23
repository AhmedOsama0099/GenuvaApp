package com.example.genuva;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormatSymbols;
import java.util.Calendar;

public class CreateParty extends AppCompatActivity implements View.OnClickListener {
    EditText partyName, firstclass, secondclass, thirdclass;
    TextView partyTime;
    Button select_img, creat_new_party;
    ImageView party_img_view;
    RadioGroup radgroup;
    RadioButton sakia, opera;
    Uri imgpath;
    StorageReference mStorage;
    ProgressDialog mProgress;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    int yearr,dayy;
    String monthh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_party);
        mProgress = new ProgressDialog(this);
        mStorage = FirebaseStorage.getInstance().getReference();
        party_img_view = findViewById(R.id.party_img_view_selected);
        partyName = findViewById(R.id.party_name);
        partyTime = findViewById(R.id.party_time);
        partyTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c=Calendar.getInstance();
                int this_year=c.get(Calendar.YEAR);
                int this_month=c.get(Calendar.MONTH);
                int this_date=c.get(Calendar.DATE);
                DatePickerDialog datePickerDialog=new DatePickerDialog(CreateParty.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        yearr=year;
                        monthh=new DateFormatSymbols().getMonths()[month-1];
                        dayy=dayOfMonth;


                        int hour=c.get(Calendar.HOUR);
                        int mint=c.get(Calendar.MINUTE);
                        TimePickerDialog timePickerDialog=new TimePickerDialog(CreateParty.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                partyTime.setText(hourOfDay+":"+minute+","+dayy+" "+ monthh+" "+yearr);
                            }
                        },hour,mint,true);
                        timePickerDialog.show();

                    }
                },this_year,this_month,this_date);
                datePickerDialog.show();

            }
        });
        firstclass = findViewById(R.id.first_class_p);
        secondclass = findViewById(R.id.second_class_p);
        thirdclass = findViewById(R.id.third_class_p);
        radgroup = findViewById(R.id.party_place_group);
        sakia = findViewById(R.id.party_place_sakia_ElSawy);
        opera = findViewById(R.id.party_place_operaHouse);
        select_img = findViewById(R.id.Image_Selector);
        creat_new_party = findViewById(R.id.creat_new_party);
        select_img.setOnClickListener(this);
        creat_new_party.setOnClickListener(this);
    }

    public void Selectimg() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 10);
    }

    public void uploadimg() {
        mProgress.setMessage("Uploading..");
        mProgress.show();
        mStorage.child("partyImage").child(imgpath.getLastPathSegment()).putFile(imgpath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    mStorage.child("partyImage").child(imgpath.getLastPathSegment()).getDownloadUrl()
                            .addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        String imgurl = task.getResult().toString();
                                        createParty(imgurl);
                                    }
                                    mProgress.dismiss();
                                }
                            });
                }
            }
        });

    }

    public void createParty(String imgurl) {
        String partyname = partyName.getText().toString();
        String partytime = partyTime.getText().toString();
        String first = firstclass.getText().toString();
        String second = secondclass.getText().toString();
        String third = thirdclass.getText().toString();
        int selectedradio = radgroup.getCheckedRadioButtonId();
        RadioButton selected_bot = findViewById(selectedradio);
        String place = selected_bot.getText().toString();
        String id = myRef.push().getKey();
        PartyModel model = new PartyModel(imgurl, first, second, third, partyname, partytime,id);
        myRef.child(place).child(id).setValue(model);

        for (int i = 1; i <= 30; i++) {
            if (i <= 10)
                myRef.child(place).child(id).child("Seats").child(Integer.toString(i)).setValue(new SeatsModel(Integer.toString(i), first, false));
            else if (i > 10 && i <= 20)
                myRef.child(place).child(id).child("Seats").child(Integer.toString(i)).setValue(new SeatsModel(Integer.toString(i), second, false));
            else
                myRef.child(place).child(id).child("Seats").child(Integer.toString(i)).setValue(new SeatsModel(Integer.toString(i), third, false));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK) {
            imgpath = data.getData();
            party_img_view.setImageURI(imgpath);


        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == creat_new_party.getId()) {
            uploadimg();
        } else if (view.getId() == select_img.getId()) {
            Selectimg();
        }
    }


}
