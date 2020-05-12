package husseinm19.github.com.coffee_bike.fregments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import husseinm19.github.com.coffee_bike.Activities.MainActivity;
import husseinm19.github.com.coffee_bike.Activities.RequestOrderActivity;
import husseinm19.github.com.coffee_bike.R;

public class HomeFrement extends Fragment {

    Button turkish,latte,cappuiccino,macchiato;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fregment,container,false);

        turkish=view.findViewById(R.id.card1);
        latte=view.findViewById(R.id.card2);
        cappuiccino=view.findViewById(R.id.card3);
        macchiato=view.findViewById(R.id.card4);


        turkish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getActivity(), "Turkish Coffee", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), RequestOrderActivity.class);
                intent.putExtra("id",1 );
                startActivity(intent);

            }
        });

        latte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getActivity(), "Latte", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), RequestOrderActivity.class);
                intent.putExtra("id",2 );
                startActivity(intent);
                getActivity().finish();
            }
        });

        cappuiccino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getActivity(), "Cappuiccino", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), RequestOrderActivity.class);
                intent.putExtra("id",3 );
                startActivity(intent);
                getActivity().finish();
            }
        });

        macchiato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "Macchiato", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), RequestOrderActivity.class);
                intent.putExtra("id",4 );
                startActivity(intent);
                getActivity().finish();
            }
        });


        return view;
    }

}
