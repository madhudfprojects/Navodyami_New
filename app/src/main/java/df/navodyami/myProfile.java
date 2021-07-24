package df.navodyami;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import df.navodyami.adapter.postAdapter;

/**
 * for more visit http://materialuiux.com
 */
public class myProfile extends AppCompatActivity {

    private RecyclerView _recyclerView;
    private postAdapter _mAdapter;
    private RecyclerView.LayoutManager _layoutManager;
    RelativeLayout r1;
    ImageView _pickImage, _changeCover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        /////*     initialize view   */////
        _recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        r1 = (RelativeLayout) findViewById(R.id.id_r1);
        _changeCover = (ImageView) findViewById(R.id.id_changeCover_ImageView);
        _pickImage = (ImageView) findViewById(R.id.id_pickImage_ImageView);
        _layoutManager = new LinearLayoutManager(this);
        _recyclerView.setFocusable(false);

        //create dummy data to show in list
        List<String> list = new ArrayList();

        list.add("It is better to fail in originality than to succeed in imitation -- Herman Melville");
        list.add(" The road to success and the road to failure are almost exactly the same -- Colin R. Davis");
        list.add("Success usually comes to those who are too busy to be looking for it.-- Henry David Thoreau");
        list.add("Opportunities don't happen. You create them.-- Chris Grosser");
        list.add("Don't be afraid to give up the good to go for the great.--John D. Rockefeller");
        list.add("I find that the harder I work, the more luck I seem to have.-- Thomas Jefferson");
        list.add("There are two types of people who will tell you that you cannot make a difference in this world: those who are afraid to try and those who are afraid you will succeed.-- Ray Goforth");
        list.add("Successful people do what unsuccessful people are not willing to do. Don't wish it were easier; wish you were better. -- Jim Rohn");
        list.add("Try not to become a man of success. Rather become a man of value.-- Albert Einstein");
        list.add("Never give in except to convictions of honor and good sense.-- Winston Churchill");
        list.add("Stop chasing the money and start chasing the passion.-- Tony Hsieh");
        list.add("Success is walking from failure to failure with no loss of enthusiasm.-- Winston Churchill");
        list.add("I owe my success to having listened respectfully to the very best advice, and then going away and doing the exact opposite.-- G. K. Chesterton");
        list.add("If you are not willing to risk the usual, you will have to settle for the ordinary.- Jim Rohn");

        _recyclerView.setHasFixedSize(true);
        _recyclerView.setLayoutManager(_layoutManager);

        ///  add items to the adapter
     //   _mAdapter = new postAdapter(list);
        ///  set Adapter to RecyclerView
        _recyclerView.setAdapter(_mAdapter);

        /////*      add Drawable      */////
        r1.setBackground(AppCompatResources.getDrawable(this, R.drawable.ic_shape_background));
        _pickImage.setImageResource(R.drawable.ic_photo);
        _pickImage.setPadding(8, 8, 8, 8);
        _changeCover.setImageResource(R.drawable.ic_photo);
        _pickImage.setPadding(8, 2, 8, 2);

    }
}
