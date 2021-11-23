package com.example.besafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPConfirmation extends AppCompatActivity {
    EditText otp;
    Button register;
    String phoneNumber,otpId;
    FirebaseAuth mAuth;
       @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpconfirmation);
        phoneNumber=getIntent().getStringExtra("mobile").toString();
        otp=(EditText) findViewById(R.id.otp);
        register=(Button) findViewById(R.id.register);
        mAuth= FirebaseAuth.getInstance();
        initiateOtp();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(otp.getText().toString().isEmpty())  //To check whether field is blank or not
                {
                    Toast.makeText(getApplicationContext(),"Empty Field cannot be processed",Toast.LENGTH_LONG).show();
                }
                else if(otp.getText().toString().length()!=6)   //To check OTPs length which should be equal to 6
                {
                    Toast.makeText(getApplicationContext(),"Invalid OTP",Toast.LENGTH_LONG).show();
                }
                else
                {
                    PhoneAuthCredential credential=PhoneAuthProvider.getCredential(otpId,otp.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });
    }

    private void initiateOtp()
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
                {
                    @Override
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken)
                    {
                        otpId=s;
                    }

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential)
                    {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });        // OnVerificationStateChangedCallbacks

    }



    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        SharedPreferences sharedPreferences=this.getSharedPreferences("com.example.besafe", Context.MODE_PRIVATE);

        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {

                           sharedPreferences.edit().putString("status","Active").apply();
                            Intent intentMain = new Intent(OTPConfirmation.this,HomeScreen.class);
                            startActivity(intentMain);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(),"Sign in Code Error",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}