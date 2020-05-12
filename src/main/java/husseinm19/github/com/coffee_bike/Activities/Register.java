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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Hussein on 24-03-2020
 */

public class Register extends AppCompatActivity {
    //inti the edittext and button
    EditText emailID,password;
    Button btnSnup;
//inti firebase auth.
    FirebaseAuth mFirebaseAuth;

    private ProgressDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        mFirebaseAuth =FirebaseAuth.getInstance();
        mDialog= new ProgressDialog(this);

        emailID =findViewById(R.id.text_emailR);
        password = findViewById(R.id.edit_text_passwordR);
        btnSnup =findViewById(R.id.button_register);

        btnSnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailID.getText().toString();
                String pass = password.getText().toString();
                if (email.isEmpty()){
                    emailID.setError("Please Provide An Email.");
                    emailID.requestFocus();
                }
                else if (pass.isEmpty()){
                    password.setError("Please Choose A Password");
                    password.requestFocus();
                } else if (email.isEmpty() && pass.isEmpty()){
                    Toast.makeText(Register.this, "Please Choose email and Password", Toast.LENGTH_LONG).show();
                } else if ( ! (email.isEmpty() && pass.isEmpty())){
                    mDialog.setMessage("Processing...");
                    mDialog.show();
                        mFirebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(Register.this
                                , new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                                        if(!task.isSuccessful()){
                                           mDialog.dismiss();
                                            Toast.makeText(Register.this,"SignUp Unsuccessful, Please Try Again",Toast.LENGTH_SHORT).show();
                                        }else {
                                            mDialog.dismiss();
                                            startActivity(new Intent(Register.this,MainActivity.class));
                                            finish();
                                        }
                                    }
                                });

                }
                else{
                    Toast.makeText(Register.this,"Error Occurred!",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void SignIn(View view) {
        Intent intent = new Intent(Register.this,Login.class);
        startActivity(intent);
        finish();
    }
}
