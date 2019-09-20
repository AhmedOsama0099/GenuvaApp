package com.example.genuva;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    EditText edt_Email_signIn;
    EditText edt_password_signIn;
    TextView tv_forget_passwird;
    Button btn_signIn_Login;
    Button   btn_signIn_gmail;
    Button   btn_signIn_phone;
    Button   btn_signIn_facebook;
    FirebaseAuth myAuth ;
    TextView textViewsingUpIntentLink;

    // Configure Google Sign In
    private static final int RC_SIGN_IN = 234;
    private static final String TAG = "GoogleSignin";

    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth googleAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Inlization
        edt_Email_signIn = findViewById(R.id.email_sigin);
        edt_password_signIn = findViewById(R.id.password_sigin);
        tv_forget_passwird  = findViewById(R.id.text_forget_pass);
        btn_signIn_Login  = findViewById(R.id.btn_login);
        btn_signIn_gmail  = findViewById(R.id.btn_Sigin_google);
        btn_signIn_phone  = findViewById(R.id.btn_Sigin_PhoneNumber);
        btn_signIn_facebook = findViewById(R.id.btn_Sigin_facebook);
        textViewsingUpIntentLink = findViewById(R.id.intent_SignUp_activity_link);

        myAuth = FirebaseAuth.getInstance();

        // Configure Phone Number Sign In

        btn_signIn_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent phone = new Intent(LoginActivity.this , Login_phone.class);
                startActivity(phone);
            }
        });

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Phone Number.>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // Configure Google Sign In
        googleAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
        btn_signIn_gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInGoogle();
            }
        });

        //<<<<<<<<<>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>><<<<<<<<<<<<>>>>>>>>>>>
        tv_forget_passwird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forget = new Intent(LoginActivity.this , forget_Password.class);
                startActivity(forget);
            }
        });

        textViewsingUpIntentLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sigin = new Intent(LoginActivity.this , Sigin_UP.class);
                startActivity(sigin);
            }
        });

        btn_signIn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               signIn(edt_Email_signIn.getText().toString() , edt_password_signIn.getText().toString());

                Intent home = new Intent(LoginActivity.this , HomeActivity.class);
                startActivity(home);
            }
        });
    }

     //<<<<<<<<<>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<>>>>>>>>>>>
    // Configure Google Sign In
    @Override
    protected void onStart() {
        super.onStart();

        if (googleAuth.getCurrentUser()!= null)
        {
            finish();
            Intent intent = new Intent(LoginActivity.this , HomeActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN)
        {
            Task<GoogleSignInAccount> task  = GoogleSignIn.getSignedInAccountFromIntent(data);
            try
               {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    firebaseAuthWithGoogle(account);
               }catch (ApiException e){
                                         e.printStackTrace();
                                      }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG , "fireBaseAutWithGoogle: " + account.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        googleAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    FirebaseUser user = googleAuth.getCurrentUser();
                    Toast.makeText(LoginActivity.this, "User Successfully Signed in ", Toast.LENGTH_SHORT).show();
                }else
                    {
                        Toast.makeText(LoginActivity.this, "SignIn Failed", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }

    private void signInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent , RC_SIGN_IN);
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<<<><<<<GOOGLE LOGIN<<<<<><><<><><>>>>>>>>>>>>><<<<<<<<<<<<<>>>>>>>>>>>>>>>

    private void signIn(String email, String password) {
        myAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                {
                    Toast.makeText(LoginActivity.this , "Login Successful ^ _ ^" , Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(LoginActivity.this , "login UnSuccessful ^ ~ ^" , Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
}
