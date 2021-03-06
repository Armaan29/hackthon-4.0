package com.example.meg2tron.digitalentrypass;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;

import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import utils.Constraints;


public class Login extends AppCompatActivity {



    private ProgressBar spinner;
    FirebaseUser firebaseUser;
    SignInButton btn;
    FirebaseAuth firebaseAuth;
    GoogleApiClient mGoogleSignInClient;
    private final static int RC_SIGN_IN= 1;
    FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login  );
        spinner = (ProgressBar)findViewById(R.id.progressBar1);

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth1) {
                if(firebaseAuth1.getCurrentUser()!=null)
                {
                    startActivity(new Intent(Login.this,second.class));
                    spinner.setVisibility(View.GONE);
                }

            }
        };
        if(firebaseAuth!=null)
        {
            firebaseUser = firebaseAuth.getCurrentUser();
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        btn = (SignInButton)findViewById(R.id.signinbtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                	signIn();


                spinner.setVisibility(View.VISIBLE);
            }
        });
        mGoogleSignInClient = new GoogleApiClient.Builder(this).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(Login.this,"Sorry something went wrong",Toast.LENGTH_LONG).show();
            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

    }

    private void signIn() {
        Intent signInIntent =Auth.GoogleSignInApi.getSignInIntent(mGoogleSignInClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess())
            {
                GoogleSignInAccount account =result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
            else {
                Toast.makeText(Login.this,"Auth went wrong",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount account) {

        Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            firebaseUser = firebaseAuth.getCurrentUser();
                            String photoUri = null;
                            if (account.getPhotoUrl()!=null)
                            {
                                photoUri = account.getPhotoUrl().toString();
                            }
                            User user = new User(account.getDisplayName()+" "+account.getFamilyName(),account.getEmail(),photoUri,FirebaseAuth.getInstance().getCurrentUser().getUid());
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference userRef = database.getReference(Constraints.USER_KEY);
                            // ...
                            userRef.child(account.getEmail().replace(".",","))
                                    .setValue(user, new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                            finish();
                                        }
                                    });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }


                    }
                });


}
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        firebaseAuth.addAuthStateListener(authStateListener);

    }


}
