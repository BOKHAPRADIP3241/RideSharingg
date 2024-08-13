package com.example.myapplication22;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class login extends AppCompatActivity {
    TextView code_text,number_text,after_text;
    EditText number_edit,OTP_edit;
    Button OTP_button,signIn_button;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    String otpId,OTP;
    PhoneAuthCredential credential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().hide();

        code_text =findViewById(R.id.code_text);
        number_text =findViewById(R.id.number_text);
        after_text =findViewById(R.id.after_text);
        number_edit = findViewById(R.id.number_edit);
        OTP_edit = findViewById(R.id.otp_edit);
        signIn_button = findViewById(R.id.signin_button);
        OTP_button = findViewById(R.id.otp_button);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        OTP_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (number_edit.getText().toString().length()==10){
                    number_text.setText("+91-"+number_edit.getText());
                    after_text.setText(" Please, verify the OTP");
                    sendOTP();
                    }
                    else{
                        Log.d("mobile_number","Check mobile number");
                        number_text.setText("Enter valid number");
                    }
                }
            });

    signIn_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            OTP = OTP_edit.getText().toString();
            credential= PhoneAuthProvider.getCredential(otpId,OTP);
            signInWithPhoneAuthCredential(credential);
        }
    });
    }

    @Override
    protected void onStart() {
        FirebaseUser userId = auth.getCurrentUser();
        super.onStart();
        if(userId!=null){
            Intent i = new Intent(login.this,bottom_navigation.class);
            i.putExtra("uId",auth.getCurrentUser().getUid());
            startActivity(i);
        }
    }

    private void sendOTP() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber("+91 "+number_edit.getText().toString())       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onVerificationCompleted(PhoneAuthCredential credential) {
//                                credential = credential;
                                Log.d("start","start");
                                signInWithPhoneAuthCredential(credential);
                            }

                            @Override
                            public void onVerificationFailed(FirebaseException e) {
                                // This callback is invoked in an invalid request for verification is made,
                                // for instance if the the phone number format is not valid.
                                Log.w("mobile", "onVerificationFailed", e);

                                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                    // Invalid request
                                } else if (e instanceof FirebaseTooManyRequestsException) {
                                    // The SMS quota for the project has been exceeded
                                }

                                // Show a message and update the UI
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId,
                                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                                // The SMS verification code has been sent to the provided phone number, we
                                // now need to ask the user to enter the code and then construct a credential
                                // by combining the code with a verification ID.
                                Log.d("mobile", "onCodeSent:" + verificationId);
                                otpId = verificationId;

                            }
                        }
                        )          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        Log.d("in","in");
            auth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                               Log.d("uid",auth.getCurrentUser().getUid());
                               Intent i = new Intent(login.this,bottom_navigation.class);
                               i.putExtra("mobile_number",number_edit.getText().toString());
                               startActivity(i);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("fail",e.getMessage());
                }
            });
        }
    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }

}