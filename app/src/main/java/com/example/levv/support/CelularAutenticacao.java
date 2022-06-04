package com.example.levv.support;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.levv.model.DAO.ConfiguracaoFirebase;
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

public class CelularAutenticacao {

    private FirebaseAuth mAuth;
    /**      * TESTE      */
    private final String TAG_LOGIN = "LOGIN";
    private final String TAG_mCallBacks = "LOGIN";
    private final String phoneNumber = "+5548991577468";
//    private String mVerificationId = "";
  //  private Task<GetTokenResult> mResendToken;
    private PhoneAuthCredential credential;
    private String code;
    private Context contexto;


    public CelularAutenticacao(Context contexto) {
        this.mAuth = ConfiguracaoFirebase.getFirebaseAuth();
        //this.mVerificationId = mAuth.getInstance().getUid();
        //this.mResendToken = mAuth.getAccessToken(true);
        //String verificationId =
        String code = "123456";
        this.contexto = contexto;
    }


    //3 - Enviar um código de verificação ao telefone do usuário
    public void verificarCredencial(String phoneNumber) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity((Activity) contexto)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks())          // OnVerificationStateChangedCallbacks
                        .build();

        verificarNumero(options);
    }

    private void verificarNumero(PhoneAuthOptions options){
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks() {

        criarPhoneAuthCredential(mAuth.getInstance().getUid(), code);


        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG_mCallBacks, "onVerificationCompleted:" + credential);

                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG_mCallBacks, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

                // Show a message and update the UI
            }

             @Override public void onCodeSent(@NonNull String verificationId,
             @NonNull PhoneAuthProvider.ForceResendingToken token) {
             // The SMS verification code has been sent to the provided phone number, we
             // now need to ask the user to enter the code and then construct a credential
             // by combining the code with a verification ID.
             Log.d(TAG_mCallBacks, "onCodeSent:" + verificationId);

             // Save verification ID and resending token so we can use them later
             //mVerificationId = verificationId;
             //mResendToken = token;
             }
        };
        return mCallbacks;
    }

    //4 - Criar um objeto PhoneAuthCredential
    private void criarPhoneAuthCredential(String verificationId, String code) {
        credential = PhoneAuthProvider.getCredential(verificationId, code);

    }

    //5 - Fazer o login do usuário
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) contexto, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG_LOGIN, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG_LOGIN, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

}
