package com.example.genuva;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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


public class Upload_Image extends AppCompatActivity {

    Button btn_upload;
    ImageView imageViewUpload;

    DatabaseReference realTimeRef = FirebaseDatabase.getInstance().getReference();
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload__image);

        btn_upload = findViewById(R.id.uploadButton);
        imageViewUpload = findViewById(R.id.uploadImage);


        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });
    }

    private void SelectImage() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK);
        pickPhoto.setType("image/*");
        startActivityForResult(pickPhoto , 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10 && resultCode == RESULT_OK && data  != null)
        {
            Uri uri = data.getData();
            imageViewUpload.setImageURI(uri);
            uploadImage(uri);
        }
    }

    private  void uploadImage(Uri uri)
    {
        final String randomName = realTimeRef.push().getKey();
        storageRef.child("images").child(randomName).putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(Upload_Image.this, "Done Successful", Toast.LENGTH_SHORT).show();
                    storageRef.child("images").child(randomName).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            String imageUrl = task.getResult().toString();
                            realTimeRef.child("images").child(randomName).setValue(imageUrl);
                        }
                    });

                }else
                    {
                        Toast.makeText(Upload_Image.this, "Done UnSuccessful", Toast.LENGTH_SHORT).show();
                    }
            }
        });

    }
}
