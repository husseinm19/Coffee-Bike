package husseinm19.github.com.coffee_bike.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import husseinm19.github.com.coffee_bike.R;
import husseinm19.github.com.coffee_bike.fregments.AboutFregment;
import husseinm19.github.com.coffee_bike.fregments.AccountFregment;
import husseinm19.github.com.coffee_bike.fregments.HomeFrement;
import husseinm19.github.com.coffee_bike.fregments.OrdersFregment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.shrikanthravi.customnavigationdrawer2.data.MenuItem;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hussein on 24-03-2020
 */


public class MainActivity extends AppCompatActivity {

    SNavigationDrawer sNavigationDrawer;
    Class fragmentClass;
    public static Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sNavigationDrawer = findViewById(R.id.navigation);

        List<MenuItem> menuItems = new ArrayList<>();

        menuItems.add(new MenuItem("Home",R.drawable.back));
        menuItems.add(new MenuItem("Orders",R.drawable.back));
        menuItems.add(new MenuItem("Account",R.drawable.back));
        menuItems.add(new MenuItem("About",R.drawable.back));

        sNavigationDrawer.setMenuItemList(menuItems);

        fragmentClass= HomeFrement.class;

        try {
            fragment =(Fragment) fragmentClass.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        if (fragment!=null){
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out).replace(R.id.frame,fragment).commit();
        }

        sNavigationDrawer.setOnMenuItemClickListener(new SNavigationDrawer.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClicked(int position) {
                System.out.println("Position "+position);

                switch (position){

                    case 0:{
                        fragmentClass = HomeFrement.class;

                        break;
                    }
                    case 1:{
                        fragmentClass = OrdersFregment.class;

                        break;
                    }
                    case 2:{
                        fragmentClass = AccountFregment.class;

                        break;
                    }
                    case 3:{
                        fragmentClass = AboutFregment.class;

                        break;
                    }
                }
            }
        });

        sNavigationDrawer.setDrawerListener(new SNavigationDrawer.DrawerListener() {
            @Override
            public void onDrawerOpening() {

            }

            @Override
            public void onDrawerClosing() {
                try {
                    fragment =(Fragment) fragmentClass.newInstance();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }

                if (fragment!=null){
                    FragmentManager fragmentManager = getSupportFragmentManager();

                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).replace(R.id.frame,fragment).commit();
                }
            }

            @Override
            public void onDrawerOpened() {

            }

            @Override
            public void onDrawerClosed() {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }
}