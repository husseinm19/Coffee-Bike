package husseinm19.github.com.coffee_bike.fregments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import husseinm19.github.com.coffee_bike.R;
import husseinm19.github.com.coffee_bike.logic.Order;

public class OrdersFregment extends Fragment {

    //firebase
    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orders_fregment, container, false);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uID = mUser.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Orders").child(uID);
        mDatabase.keepSynced(true);

        recyclerView = view.findViewById(R.id.recycleViewOrders);
        //Recycle View Read Data
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Order, MyViewHolder> adapter = new FirebaseRecyclerAdapter<Order, MyViewHolder>(
                Order.class,
                R.layout.order_item,
                MyViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(MyViewHolder myViewHolder, final Order order, final int position) {
                myViewHolder.setTitle(order.getTitle());
                myViewHolder.setQuan(order.getQuan());
                myViewHolder.setDate(order.getDate());
                myViewHolder.setPrice(order.getPrice());
            }
        };
        recyclerView.setAdapter(adapter);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        View myView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myView = itemView;
        }

        public void setTitle(String title) {
            TextView mTitle = myView.findViewById(R.id.titleOrder);
            mTitle.setText(title);
        }

        public void setDate(String date) {
            TextView mDate = myView.findViewById(R.id.txt_date);
            mDate.setText(date);
        }

        public void setPrice(int price) {
            TextView mPrice = myView.findViewById(R.id.txt_price);

            mPrice.setText("" + price + " EGP");
        }

        public void setQuan(int quan) {
            TextView mQuan = myView.findViewById(R.id.titleQuan);
            mQuan.setText(" " + quan + " ");
        }

    }
}

