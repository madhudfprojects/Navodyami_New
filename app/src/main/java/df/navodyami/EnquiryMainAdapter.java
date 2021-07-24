package df.navodyami;

/**
 * Created by Admin on 22-06-2018.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class EnquiryMainAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public EnquiryMainAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                EnquiryListFragment tab1 = new EnquiryListFragment();
                return tab1;
        /*    case 1:
                ChartStud_CollgCountFragment tab2 = new ChartStud_CollgCountFragment();
                return tab2;*/
            case 1:
                ApplicantionListFragment tab3 = new ApplicantionListFragment();
                return tab3;
    /*        case 2:
                ChartFundAmtCountFragment tab4 = new ChartFundAmtCountFragment();
                return tab4;*/
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}