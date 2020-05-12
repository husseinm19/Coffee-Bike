package husseinm19.github.com.coffee_bike.fregments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import husseinm19.github.com.coffee_bike.Activities.Login;
import husseinm19.github.com.coffee_bike.Activities.RegisterActivity;
import husseinm19.github.com.coffee_bike.R;

public class AccountFregment extends Fragment {

    //Firebase
    FirebaseAuth mAuth;
    DatabaseReference myRef;
    FirebaseDatabase mDatabase;
    EditText mName, mEmail, mPhone, mAddress;
    ImageView profilePic;
    private StorageReference mStorageRef;
    private String uID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_fregment, container, false);


        mName = view.findViewById(R.id.text_nameAcc);
        mEmail = view.findViewById(R.id.text_emailAcc);
        mPhone = view.findViewById(R.id.edit_text_phoneACC);
        mAddress = view.findViewById(R.id.edit_text_addressACC);
        profilePic = view.findViewById(R.id.photo_profileACC);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();

        uID = mUser.getUid();
        mStorageRef = FirebaseStorage.getInstance().getReference();

       //StorageReference imageRef = mStorageRef.child("images").child(uID+".jpg");

        mStorageRef.child("images").child(uID+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .dontAnimate()
                        //.placeholder(R.drawable.camera)
                        .error(R.drawable.ic_boy)
                        .priority(Priority.HIGH);

                Glide.with(AccountFregment.this)
                        .load(uri)

                        .apply(options)
                        .into(profilePic);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

//
//        String logURl=imageRef.toString();
//
//
//
//
//       // Log.d(logURl,"Pics");


        mDatabase = FirebaseDatabase.getInstance();
        myRef = mDatabase.getInstance().getReference().child("Users").child(uID);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                String phone = dataSnapshot.child("phone").getValue().toString();
                String address = dataSnapshot.child("address").getValue().toString();

                mName.setText(name);
                mEmail.setText(email);
                mPhone.setText(phone);
                mAddress.setText(address);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // mName.setText(uID);


        Button logout = (Button) view.findViewById(R.id.button_sign_out);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                getActivity().finish();

            }
        });


        return view;
    }

}