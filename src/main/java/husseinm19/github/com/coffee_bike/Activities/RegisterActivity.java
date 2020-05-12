package husseinm19.github.com.coffee_bike.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.ByteArrayOutputStream;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import husseinm19.github.com.coffee_bike.R;
import husseinm19.github.com.coffee_bike.logic.User;

public class RegisterActivity extends AppCompatActivity implements IPickResult {

    //Firebase
    FirebaseAuth mAuth;
    //for upload the image
    Bitmap image;
    EditText name, email, password, phone, address;
    //signup button
    Button signup;
    String imageURL;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    //init for the image upload
    private ImageView take_photo_profile, favorImage;
    private ProgressDialog mDialog;
    // private String image_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // New child entries
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mStorageRef = FirebaseStorage.getInstance().getReference();

        //Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);

        //to get the image from the gallery
        initView();


        name = findViewById(R.id.text_name);
        email = findViewById(R.id.text_emailR);
        password = findViewById(R.id.edit_text_passwordR);
        phone = findViewById(R.id.edit_text_phoneR);
        address = findViewById(R.id.edit_text_addressR);

        signup = findViewById(R.id.button_registerR);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String mEmail = email.getText().toString().trim();
                final String mPass = password.getText().toString().trim();
                final String mName = name.getText().toString().trim();
                final String mPhone = phone.getText().toString().trim();
                final String mAddress = address.getText().toString().trim();

                if (TextUtils.isEmpty(mEmail) && (TextUtils.isEmpty(mPass) && TextUtils.isEmpty(mName)
                        && TextUtils.isEmpty(mPhone) && TextUtils.isEmpty(mAddress))) {
                    email.setError("Required Filed");
                    password.setError("Required Filed");
                    name.setError("Required Filed");
                    phone.setError("Required Filed");
                    address.setError("Required Filed");
                    return;

                }
                if (TextUtils.isEmpty(mEmail)) {
                    email.setError("Required Filed");
                    return;

                }

                if (TextUtils.isEmpty(mPass)) {
                    password.setError("Required Filed");
                    return;

                }

                if (TextUtils.isEmpty(mName)) {
                    name.setError("Required Filed");
                    return;

                }

                if (TextUtils.isEmpty(mPhone)) {
                    phone.setError("Required Filed");
                    return;

                }

                if (TextUtils.isEmpty(mAddress)) {
                    address.setError("Required Filed");
                    return;

                }
                if (image == null) {
                    Toast.makeText(RegisterActivity.this, "Please Upload Photo", Toast.LENGTH_LONG).show();
                    return;
                }
                mDialog.setMessage("Processing...");
                mDialog.show();
                mAuth.createUserWithEmailAndPassword(mEmail, mPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Register", Toast.LENGTH_SHORT).show();
                            FirebaseUser mUser = mAuth.getCurrentUser();

                            String uID = mUser.getUid();

                            //String id = mDatabase.push().getKey();

                            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uID);
                            final User user = new User(mName, mEmail, mPass, mAddress, mPhone);
                            mDatabase.setValue(user);

                            //upload the image
                            StorageReference tripsRef = mStorageRef.child("images/" + uID + ".jpg");
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] data = baos.toByteArray();

                            UploadTask uploadTask = tripsRef.putBytes(data);

                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                    Toast.makeText(RegisterActivity.this, "Upload Image Failure", Toast.LENGTH_LONG).show();

                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    String image_url = String.valueOf(taskSnapshot.getStorage().getDownloadUrl());
                                    //  String  image_url= taskSnapshot.getResult().getMetadata().getReference().getDownloadUrl().toString();


                                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                    Toast.makeText(RegisterActivity.this, "Upload Image Success", Toast.LENGTH_SHORT).show();


                                }
                            });

                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            mDialog.dismiss();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        }

                    }
                });
            }
        });


    }

    @Override
    public void onPickResult(PickResult pickResult) {
        if (pickResult.getError() == null) {
            //If you want the Uri.
            //Mandatory to refresh image from Uri.
            //getImageView().setImageURI(null);

            //Setting the real returned image.
            //getImageView().setImageURI(r.getUri());

            //If you want the Bitmap.
            favorImage.setImageBitmap(pickResult.getBitmap());
            image = pickResult.getBitmap();
            take_photo_profile.setVisibility(View.GONE);

        } else {
            //Handle possible errors
            Toast.makeText(getApplicationContext(), pickResult.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    private void initView() {
        favorImage = findViewById(R.id.favorImage);
        take_photo_profile = findViewById(R.id.take_photo_profile);
        FrameLayout imageParent = findViewById(R.id.imageParent);


        //to get photo from the gallery or camera
        imageParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PickImageDialog.build(new PickSetup()).show(RegisterActivity.this);

            }
        });
    }


    public void SignInTwo(View view) {
        Intent intent = new Intent(RegisterActivity.this, Login.class);
        startActivity(intent);
        finish();
    }
}
