package com.example.genuva;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Login_phone extends AppCompatActivity {

    EditText enterPhoneNumber , codeConfirm;
    Button  getVerificationCode , signinPhoneNumber;
    FirebaseAuth mAuth;

    String codeSend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_login);

        mAuth =FirebaseAuth.getInstance();

        enterPhoneNumber = findViewById(R.id.edt_phone_number);
        codeConfirm = findViewById(R.id.Code_sigin);
        getVerificationCode = findViewById(R.id.btn_Getverification_Code);
        signinPhoneNumber  = findViewById(R.id.confirmation_code);


        enterPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendVerificationCode();
            }
        });


        signinPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifySignInCode();
            }
        });
    }

    private void verifySignInCode() {
        String code = codeConfirm.getText().toString();
        PhoneAuthCredential credential =  PhoneAuthProvider.getCredential(codeSend , code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Here Can Open new Activity
                            Toast.makeText(Login_phone.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        } else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                            {
                                Toast.makeText(Login_phone.this, "Incorrect Verification Code", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                });
    }

    private void sendVerificationCode() {

        String phone = enterPhoneNumber.getText().toString();

        if (phone.isEmpty())
        {
            enterPhoneNumber.setError("Phone Number is Required");
            enterPhoneNumber.requestFocus();
            return;
        }
           if (phone.length() < 11)
                 {
                    enterPhoneNumber.setError("Phone Number is not a Valid");
                    enterPhoneNumber.requestFocus();
                    return;
                 }



        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks  mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            codeSend = s ;

        }
    };
}
