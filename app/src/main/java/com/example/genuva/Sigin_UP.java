package com.example.genuva;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Sigin_UP extends AppCompatActivity implements View.OnClickListener {

    TextView textViewSiginLinkAlreadyAccount;
    EditText emailSignUp , passwordSignUp , re_passwordSignUp;
    Button  btnSignUp;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigin__up);

        //Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance();

        //Intialaization
        emailSignUp = findViewById(R.id.email_SiginUP);
        passwordSignUp = findViewById(R.id.password_sigin_UP);
        re_passwordSignUp = findViewById(R.id.Re_password_sigin_UP);
        btnSignUp  = findViewById(R.id.btn_sign_UP);



        // link to Move to Login Activity when you Owen Account ;
        textViewSiginLinkAlreadyAccount = findViewById(R.id.text_already_have_Account);
        textViewSiginLinkAlreadyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Sigin_UP.this  , LoginActivity.class);
                startActivity(intent);
            }
        });

        btnSignUp.setOnClickListener(this);
        //Bush data on fire base and intent login Activity
    }

    private void SignUp(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Toast.makeText(Sigin_UP.this,"asdasd",Toast.LENGTH_SHORT).show();
                if (task.isSuccessful())
                {
                    Toast.makeText(Sigin_UP.this , "Sign Up Successful ^ _ ^" , Toast.LENGTH_SHORT).show();
                }else
                    {
                        Toast.makeText(Sigin_UP.this , "Sign Up UnSuccessful ^ ~ ^" , Toast.LENGTH_SHORT).show();

                    }
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_sign_UP:
                if (!emailSignUp.getText().toString().isEmpty() && !passwordSignUp.getText().toString().isEmpty()
                        && !re_passwordSignUp.getText().toString().isEmpty())
                        {
                            if ( passwordSignUp.getText().toString().equals(re_passwordSignUp.getText().toString()))
                            {
                                SignUp(emailSignUp.getText().toString(),passwordSignUp.getText().toString());
                            }else
                                {
                                    Toast.makeText(Sigin_UP.this , "Wrong Data ^ ~ ^" , Toast.LENGTH_SHORT).show();
                                }

                        }
                break;

        }
    }
}