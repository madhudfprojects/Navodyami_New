package df.navodyami.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import df.navodyami.Class_DashboardTotalCount;
import df.navodyami.R;

public class postAdapter
        extends RecyclerView.Adapter<postAdapter.ViewHolder> {
    private List<String> mData;
    private List<Class_DashboardTotalCount> mTotalCount;

    /*public postAdapter(List<Class_DashboardTotalCount> Data,List<String> Dataone) {
        mData = Data;
        mDataNew = Dataone;
    }*/
   /* public postAdapter(List<String> Dataone) {
        mData = Dataone;
    }*/
    public postAdapter(ArrayList<Class_DashboardTotalCount> TotalCounts,List<String> Dataone) {
        mTotalCount = TotalCounts;
        mData = Dataone;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        /////*     initialize view   */////
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dashboad_info, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        // set values for each item
        String itam = mData.get(position);
     //   viewHolder.snate_no.setText(itam);
        for(int i=0;i<mTotalCount.size();i++){
            String event_name = mTotalCount.get(i).getEvent_Name();
            if (event_name.equalsIgnoreCase("Market Channel")){
                String total = mTotalCount.get(i).getTotal();
                viewHolder.snate_no.setText(total);
            }
            if (event_name.equalsIgnoreCase("Training")){
                String total = mTotalCount.get(i).getTotal();
                viewHolder.training_no.setText(total);
            }
            if (event_name.equalsIgnoreCase("Mentorship")){
                String total = mTotalCount.get(i).getTotal();
                viewHolder.mentors_no.setText(total);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView _postText;
        TextView snate_no,training_no,mentors_no;
        public ViewHolder(View v) {
            super(v);
            snate_no = v.findViewById(R.id.snate_no);
            training_no = v.findViewById(R.id.training_no);
            mentors_no = v.findViewById(R.id.mentors_no);

        }

    }
}
