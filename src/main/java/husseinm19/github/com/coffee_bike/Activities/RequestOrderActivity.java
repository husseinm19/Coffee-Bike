package husseinm19.github.com.coffee_bike.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;
import husseinm19.github.com.coffee_bike.R;
import husseinm19.github.com.coffee_bike.logic.Order;

public class RequestOrderActivity extends AppCompatActivity {

    //inti View
    int quaninty = 1;
    int price;
    TextView quan, priceTxt,desc,name;
    Button btn_plus, btn_minse,order;
    CheckBox WhippedCreamCheckBox, ChoclateCheckBox;
    ImageView image;

    //firebase
    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_order_activity);


        // New child entries
        mDatabase = FirebaseDatabase.getInstance().getReference();



        //Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);

        //price = 5;

        image=findViewById(R.id.header_image);
        desc=findViewById(R.id.textViewdesc);
        name=findViewById(R.id.txt_name_coffee);
        Intent i = getIntent();
        int position = i.getExtras().getInt("id");



        if (position == 1){
            image.setImageResource(R.drawable.turkish);
            desc.setText("Turkish coffee is made by bringing the powdered coffee with water and sugar to the boil in a special pot called cezve.");
            name.setText(" Turkish Coffee ");
            price=7;
        }

        if (position == 2){
            image.setImageResource(R.drawable.latte);
            desc.setText("latte coffee made with espresso and steamed milk. \n");
            name.setText(" Latte ");
            price=8;

        }

        if (position == 3){
            image.setImageResource(R.drawable.cappuccino);
            desc.setText("cappuccino coffee drink composed of a single espresso shot and hot milk, with the surface topped with foamed milk.");
            name.setText(" Cappuccino ");
            price=9;
        }

        if (position == 4){
            image.setImageResource(R.drawable.macchiato);
            desc.setText("Caffè macchiato espresso macchiato, an espresso coffee drink with a small amount of milk, usually foamed.");
            name.setText(" Macchiato ");
            price=10;
        }


        quan = findViewById(R.id.quantextView);
        quan.setText("" + quaninty);
        btn_minse = findViewById(R.id.buttonMinse);
        btn_plus = findViewById(R.id.buttonPlus);
        btn_minse.setEnabled(false);
        order=findViewById(R.id.button_order);
        priceTxt = findViewById(R.id.Price_Text_View);
        WhippedCreamCheckBox = findViewById(R.id.Whipped_Creem);
        ChoclateCheckBox = findViewById(R.id.Chocloate);

        priceTxt.setText(""+quaninty*price);


        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quaninty = quaninty + 1;

                btn_minse.setEnabled(true);
                priceTxt.setText("" + quaninty * price);
                quan.setText("" + quaninty);
                if (quaninty == 5) {
                    btn_plus.setEnabled(false);
                    Toast.makeText(RequestOrderActivity.this, "maximum order", Toast.LENGTH_SHORT).show();

                }
            }
        });

        btn_minse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quaninty = quaninty - 1;


                quan.setText("" + quaninty);
                priceTxt.setText("" + quaninty * price);
                if (quaninty == 1) {
                    btn_minse.setEnabled(false);
                    btn_plus.setEnabled(true);
                    Toast.makeText(RequestOrderActivity.this, "minimum order", Toast.LENGTH_SHORT).show();
                }
            }
        });


        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDialog.setMessage("Processing...");
                mDialog.show();
                FirebaseUser mUser = mAuth.getCurrentUser();


                final String uID = mUser.getUid();

                mDatabase = FirebaseDatabase.getInstance().getReference().child("Orders").child(uID);
                String mDate= DateFormat.getDateInstance().format(new Date());
                String id = mDatabase.push().getKey();
                final int final_price=quaninty*price;

                String title= (String) name.getText();

                Order order=new Order(title,quaninty,final_price,mDate,id);

                mDatabase.child(id).setValue(order);
//                Toast.makeText(RequestOrderActivity.this, "Success", Toast.LENGTH_SHORT).show();
//               // mDialog.dismiss();
//                Intent intent = new Intent(RequestOrderActivity.this,MainActivity.class);
//                startActivity(intent);
//                finish();

                Timer myTimer = new Timer();
                myTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        // If you want to modify a view in your Activity
                        RequestOrderActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                mDialog.dismiss();
                                Intent intent = new Intent(RequestOrderActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(RequestOrderActivity.this, " Success ", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }, 3000);

            }





        });


    }

//    public void SubmitOrder (View view){
////        int NumberOfCoffe=0;
//        int price =5;
//        String priceMessage="Amount Due = " + quaninty*price;
//        display(quaninty);
//        priceMessage = priceMessage + "\nThank You";
//        displayMessage(priceMessage);
//
//        // displayPrice(quaninty*price);
//    }
//
//    private void display(int number) {
//        TextView quanityTextView = (TextView) findViewById(R.id.textView);
//        quanityTextView.setText(""+ number);
//    }
//
//    private void displayPrice(int number){
//        TextView priceTextView = (TextView) findViewById(R.id.Price_Text_View);
//        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
//    }
//
//    private void displayMessage( String message){
//        TextView priceTextView = (TextView) findViewById(R.id.Price_Text_View);
//        priceTextView.setText(message);
//    }
//
//    public void increment(View view) {
//
//        quaninty=quaninty+1;
//        display(quaninty);
////        displayPrice(quaninty*5);
//    }
//
//    public void decrement(View view) {
//
//        quaninty=quaninty-1;
//        display(quaninty);
////        displayPrice(quaninty*5);
//    }
//
//    public void logout(View view) {
//        FirebaseAuth.getInstance().signOut();
//        Intent intent = new Intent(RequestOrderActivity.this,Login.class);
//        startActivity(intent);
//        finish();
//    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        finish();
//    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RequestOrderActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
