package df.navodyami;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import df.navodyami.Dashboard_Fragment;
import df.navodyami.EnquiryMain_Fragment;
import df.navodyami.EventMain_Fragment;
import df.navodyami.R;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        String menuFragment = getIntent().getStringExtra("messageTitle");

        Log.e("tag","menuFragment=="+menuFragment);
        FragmentManager fragmentManager = getFragmentManager();
       // FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // If menuFragment is defined, then this activity was launched with a fragment selection
        if (menuFragment != null) {

            // Here we can decide what do to -- perhaps load other parameters from the intent extras such as IDs, etc
            if (menuFragment.equalsIgnoreCase("Enquiry")) {
               /* FavoritesFragment favoritesFragment = new FavoritesFragment();
                fragmentTransaction.replace(android.R.id.content, favoritesFragment);*/
                /*FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, EnquiryMain_Fragment.newInstance());
                transaction.commit();*/
                Intent i = new Intent(NotificationActivity.this, BottomActivity.class);
                i.putExtra("frgToLoad", "0");
                startActivity(i);
            }
            else if(menuFragment.equalsIgnoreCase("Application")){
               /* FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, EnquiryMain_Fragment.newInstance());
                transaction.commit();*/
                Intent i = new Intent(NotificationActivity.this, BottomActivity.class);
                i.putExtra("frgToLoad", "0");
                startActivity(i);
            }
            else if(menuFragment.equalsIgnoreCase("Events")){
                /*FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, EventMain_Fragment.newInstance());
                transaction.commit();*/
                Intent i = new Intent(NotificationActivity.this, BottomActivity.class);
                i.putExtra("frgToLoad", "2");
                startActivity(i);
            }
            else if(menuFragment.equalsIgnoreCase("General")){
                /*FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, Dashboard_Fragment.newInstance());
                transaction.commit();*/
                Intent i = new Intent(NotificationActivity.this, BottomActivity.class);
                i.putExtra("frgToLoad", "1");
                startActivity(i);
            }
            else if(menuFragment.equalsIgnoreCase("Alert")){
                /*FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, Dashboard_Fragment.newInstance());
                transaction.commit();*/
                Intent i = new Intent(NotificationActivity.this, BottomActivity.class);
                i.putExtra("frgToLoad", "1");
                startActivity(i);
            }
            else if(menuFragment.equalsIgnoreCase("Announce")){
                /*FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, Dashboard_Fragment.newInstance());
                transaction.commit();*/
                Intent i = new Intent(NotificationActivity.this, BottomActivity.class);
                i.putExtra("frgToLoad", "1");
                startActivity(i);
            }
        } else {
            // Activity was not launched with a menuFragment selected -- continue as if this activity was opened from a launcher (for example)
            /*FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, Dashboard_Fragment.newInstance());
            transaction.commit();*/
            Intent i = new Intent(NotificationActivity.this, BottomActivity.class);
            i.putExtra("frgToLoad", "1");
            startActivity(i);
        }
    }
}
