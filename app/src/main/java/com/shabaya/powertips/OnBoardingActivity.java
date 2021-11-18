package com.shabaya.powertips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
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

import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;
import static java.security.AccessController.getContext;

public class OnBoardingActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private  FirebaseUser firebaseUser;
    ProgressDialog progressDialog;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    EditText PhoneNumber, OTP;
    Button Submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        PhoneNumber = findViewById(R.id.phone_number);
        OTP = findViewById(R.id.otp);
        Submit = findViewById(R.id.btn_submit);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String phone = PhoneNumber.getText().toString();
               //String OTP.getText().toString();

                if(!phone.isEmpty()){
                    startPhoneNumberVerification(phone);
                }
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser != null){
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
//        user = new User();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                progressDialog.dismiss();
                //OTP.setText("");
                Toast.makeText(OnBoardingActivity.this, "where are?", Toast.LENGTH_SHORT).show();
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);

                AlertDialog alertDialog = new AlertDialog.Builder(OnBoardingActivity.this)
                        .setTitle("Mobile Number Verification")
                        .setMessage("The verification code entered was invalid. Try again?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                        .show();

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                mVerificationId = verificationId;
                mResendToken = forceResendingToken;
                OTP.setVisibility(View.VISIBLE);
                PhoneNumber.setVisibility(View.INVISIBLE);
                Submit.setVisibility(View.INVISIBLE);
                //prefsManager.setString("otp.verificationId", mVerificationId);
                if (progressDialog != null)
                    progressDialog.dismiss();
            }
        };

        OTP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("VERIFY_OTP", " text change : " + count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                String otp = s.toString();
                Log.d("VERIFY_OTP", " text change : " + otp);
                if (otp.length() == 6) {
                    //verify here
                    //String verificationId = prefsManager.getString("verificationId");
                    verifyPhoneNumberWithCode(mVerificationId, otp);
                }
            }
        });
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(OnBoardingActivity.this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();

        progressDialog = new ProgressDialog(OnBoardingActivity.this);
        progressDialog.setMessage("Sending One Time Password");
        progressDialog.show();

        PhoneAuthProvider.verifyPhoneNumber(options);
        // [END start_phone_auth]
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    FirebaseUser firebaseUser = task.getResult().getUser();

//                    user.setMobileNumber(firebaseUser.getPhoneNumber());
//                    user.setUid(firebaseUser.getUid());
                   // prefsManager.setLocalUser(user);
                   // prefsManager.setInt("onBoardingStep", 3);
                   // prefsManager.setBoolean("isUserLoggedIn", true);
                    Intent i = new Intent(OnBoardingActivity.this,MainActivity.class);
                    i.setAction("VIEWPAGER_NEXT_PAGE");
                    startActivity(i);

                } else {
                    progressDialog.dismiss();
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        AlertDialog alertDialog = new AlertDialog.Builder(OnBoardingActivity.this)
                                .setTitle("Mobile Number Verification")
                                .setMessage("The verification code entered was invalid. Try again?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                                .show();
                    }
                }
            }
        });
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Verifying One Time Password");
        progressDialog.show();

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }
}