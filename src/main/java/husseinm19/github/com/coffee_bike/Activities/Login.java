package husseinm19.github.com.coffee_bike.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import husseinm19.github.com.coffee_bike.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Hussein on 24-03-2020
 */

public class Login extends AppCompatActivity {
    EditText emailId, password;
    Button btnSignIn;
    TextView tvSignUp;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private ProgressDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDialog= new ProgressDialog(this);
        emailId = findViewById(R.id.text_email);
        password = findViewById(R.id.edit_text_password);
        btnSignIn = findViewById(R.id.button_sign_in);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if( mFirebaseUser != null ){
                    //Toast.makeText(Login.this,"You are logged in",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Login.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    //Toast.makeText(Login.this,"Please Login",Toast.LENGTH_SHORT).show();
                }
            }
        };

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String pass = password.getText().toString();
                if(email.isEmpty()){
                    emailId.setError("Please enter email id");
                    emailId.requestFocus();
                }else  if(pass.isEmpty()){
                    password.setError("Please enter your password");
                    password.requestFocus();
                }else  if(email.isEmpty() && pass.isEmpty()){
                    Toast.makeText(Login.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();
                }else  if(!(email.isEmpty() && pass.isEmpty())){
                    mDialog.setMessage("Processing...");
                    mDialog.show();
                    mFirebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(Login.this,
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(!task.isSuccessful()){
                                        mDialog.dismiss();
                                        Toast.makeText(Login.this,"Email Or Password Dont Match",Toast.LENGTH_SHORT).show();
                                    }else{
                                        mDialog.dismiss();
                                        Intent intToHome = new Intent(Login.this,MainActivity.class);
                                        startActivity(intToHome);
                                        finish();
                                    }
                                }
                            });


                }
                else{
                    Toast.makeText(Login.this,"Error Occurred!",Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    public void signup(View view) {
        Intent intent = new Intent(Login.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
