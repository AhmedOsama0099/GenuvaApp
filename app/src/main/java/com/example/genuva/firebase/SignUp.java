package com.example.genuva.firebase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.genuva.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SignUp extends AppCompatActivity {

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    StorageReference storage  = FirebaseStorage.getInstance().getReference();
    FirebaseAuth myAuth   = FirebaseAuth.getInstance();

    EditText userName , userEmail , userPassword , userPhoneNumber;
    Button signUp;
    ImageView circleImage;


    private  Uri uri = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);



        userName  = findViewById(R.id.edt_username);
        userEmail = findViewById(R.id.edt_email);
        userPassword = findViewById(R.id.edt_password);
        userPhoneNumber = findViewById(R.id.edt_phone_number);
        circleImage  = findViewById(R.id.circleImage);

        circleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });

        signUp = findViewById(R.id.btnSignUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createUser();
            }
        });
    }
    private void SelectImage() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK);
        pickPhoto.setType("image/*");
        startActivityForResult(pickPhoto , 10);
    }

    private void createUser() {
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();

        myAuth.createUserWithEmailAndPassword(email , password ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(SignUp.this, "Done", Toast.LENGTH_SHORT).show();
                    String uri = task.getResult().getUser().getUid();
                    uploadProfileImage(uri);

                }
            }
        });
    }

    private void  uploadProfileImage(final String uid)
    {
        if (uri == null)
        {
            Toast.makeText(this, "Please Select Image", Toast.LENGTH_SHORT).show();
            return;
        }


        storage.child("ProfileImage").child(uid).putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful())
                {
                    storage.child("ProfileImage").child(uid).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful())
                            {
                                String url = task.getResult().toString();
                                createUserImage(uid , url);
                            }
                        }
                    });
                }
             }
        });
    }


    private  void createUserImage(String uid ,String url)
    {
        String UserName = userName.getText().toString();
        String UserPhone = userPhoneNumber.getText().toString();

        UserModel userModel = new UserModel(UserName , UserPhone , url);
        myRef.child("UserImage").child(uid).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(SignUp.this, "User Create Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
