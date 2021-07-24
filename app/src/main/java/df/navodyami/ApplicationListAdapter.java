package df.navodyami;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Madhu.
 */

public class ApplicationListAdapter extends BaseAdapter {

    public ArrayList<ApplicationListModel> feesPaidList;
    Activity activity;

    public ApplicationListAdapter(Activity activity, ArrayList<ApplicationListModel> feesPaidList) {
        super();
        this.activity = activity;
        this.feesPaidList = feesPaidList;
    }

    @Override
    public int getCount() {
        //return projList.size();
        return feesPaidList.size();
    }

    @Override
    public ApplicationListModel getItem(int position) {

        //return projList.get(position);
        return feesPaidList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        TextView mFirstName;
        TextView mMobileNo;
        TextView mApplFees;
        TextView mBusinessName;
        TextView mApplicationSlno;
        ImageView mupdate_bt;
        TextView mtempID;
        CardView card_view;
      //  CheckBox check_status;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ApplicationListAdapter.ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.application_listrow, null);
            holder = new ApplicationListAdapter.ViewHolder();

            holder.mFirstName = (TextView) convertView.findViewById(R.id.txt_fname);
            /*holder.mEnquiryId = (TextView) convertView.findViewById(R.id.txt_enquiryId);*/
            holder.mMobileNo = (TextView) convertView.findViewById(R.id.txt_mobno);
            holder.mBusinessName = (TextView) convertView.findViewById(R.id.txt_bussname);
            holder.mApplicationSlno = (TextView) convertView.findViewById(R.id.txt_applicationSlno);
            holder.mupdate_bt = (ImageView) convertView.findViewById(R.id.update_bt);
            holder.mApplFees = (TextView) convertView.findViewById(R.id.txt_applfees);
            holder.mtempID = (TextView) convertView.findViewById(R.id.txt_tempID);
            holder.card_view=(CardView)convertView.findViewById(R.id.card_view);
           // holder.check_status = (CheckBox) convertView.findViewById(R.id.check_status);

            convertView.setTag(holder);
        } else {
            holder = (ApplicationListAdapter.ViewHolder) convertView.getTag();
        }

        ApplicationListModel item = feesPaidList.get(position);

        if(item.getFirst_Name()!=null) {
            holder.mFirstName.setText(item.getFirst_Name().toString());
        }
        if(item.getMobile_No()!=null) {
            holder.mMobileNo.setText(item.getMobile_No().toString());
        }
        if(item.getBusiness_Name()!=null) {
            holder.mBusinessName.setText(item.getBusiness_Name().toString());
        }
        if(item.getApplication_Slno()!=null) {
            holder.mApplicationSlno.setText(item.getApplication_Slno().toString());
        }
       // Log.e("tag","getApplFees=="+item.getApplFees());
        if(item.getApplFees().equals("0")) {
            holder.mApplFees.setTextColor(Color.RED);
            holder.mApplFees.setText(item.getApplFees().toString());
        }else {
            holder.mApplFees.setTextColor(Color.GREEN);
            holder.mApplFees.setText(item.getApplFees().toString());
        }
      /*  if(item.getEnquiry_id()!=null) {
            holder.mEnquiryId.setText(item.getEnquiry_id().toString());
        }
        if(item.getMobileNo()!=null) {
            holder.mRegistrationDate.setText(item.getMobileNo().toString());
        }*/

        if(item.getDataSyncStatus()!=null) {
            if (item.getDataSyncStatus().equalsIgnoreCase("offline")) {
                holder.card_view.setBackgroundResource(R.color.gray);
            }
        }
        if(item.getTempId()!=null) {
            holder.mtempID.setText(item.getTempId().toString());
        }

        if(item.getMobile_No()!=null) {
            holder.mMobileNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + holder.mMobileNo.getText().toString()));
                    v.getContext().startActivity(intent);
                }
            });
        }

        holder.mupdate_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1  = new Intent (activity,AddApplicationOneActivity.class);
                intent1.putExtra("ApplicationSlno",holder.mApplicationSlno.getText());
                intent1.putExtra("tempId",holder.mtempID.getText());
                intent1.putExtra("isApplicant","1");
                activity.startActivity(intent1);
            }
        });

     /*   holder.check_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(holder.check_status.isChecked())
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                    dialog.setCancelable(false);
                    dialog.setTitle("Navodyami");
                    dialog.setMessage("Are you sure want to Make Enquiry to Applicant?" );

                    dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id)
                        {
                            Intent i = new Intent(activity, AddApplicationOneActivity.class);
                            i.putExtra("EnquiryId",holder.mEnquiryId.getText());
                            activity.startActivity(i);
                           // finish();
                        }
                    })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Action for "Cancel".
                                    dialog.dismiss();
                                }
                            });

                    final AlertDialog alert = dialog.create();
                    alert.setOnShowListener( new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface arg0) {
                            alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
                        }
                    });
                    alert.show();
                }*//*else{
                    impactproject_againtstide_et.setVisibility(View.GONE);
                    impactproject_againtstide_et.setText("");
                    str_validation_againtstide="no";
                }
*//*
            }
        });*/

        return convertView;
    }


    public void filter(String charText, ArrayList<ApplicationListModel> feesList, String selectedCollegeName) {
        //charText = charText.toLowerCase(Locale.getDefault());
        //Log.d("charTextissss",charText);
        this.feesPaidList.clear();

     /*   for(FeesUnpaidModel feesUnpaidModel : feesList){
            String  stdName = feesUnpaidModel.getStudent_name().toString();
            //Log.d("StudeNameissssss",stdName);
        }*/

        if(charText!=null) {
            if(feesList!=null) {
                if (charText.isEmpty() || charText.length() == 0) {
                    this.feesPaidList.addAll(feesList);
                } else {
                    for (ApplicationListModel wp : feesList) {
                        //Log.d("GetCollegeNameissss",wp.getCollege_name().toLowerCase((Locale.getDefault())));
                       /* if (wp.getCollege_name().toLowerCase(Locale.getDefault()).contains(charText) || wp.getLead_id().toLowerCase(Locale.getDefault()).contains(charText) || wp.getPhone_number().toLowerCase(Locale.getDefault()).contains(charText) || wp.getStudent_name().toLowerCase(Locale.getDefault()).contains(charText) || wp.getPhone_number().toLowerCase(Locale.getDefault()).contains(charText)) {
                            this.feesUnPaidList.add(wp);
                        }*/
                     /*   if(selectedCollegeName == null) {
                            if (wp.getCollege_name().contains(charText) || wp.getLead_id().contains(charText) || wp.getPhone_number().contains(charText) || wp.getStudent_name().contains(charText) || wp.getPhone_number().contains(charText)) {
                                this.feesPaidList.add(wp);
                            }
                        }else{
                            if (wp.getCollege_name().equals(selectedCollegeName) && (wp.getCollege_name().contains(charText) || wp.getLead_id().contains(charText) || wp.getPhone_number().contains(charText) || wp.getStudent_name().contains(charText) || wp.getPhone_number().contains(charText))) {
                                this.feesPaidList.add(wp);
                            }
                        }*/

                        if(selectedCollegeName == null) {
                            if ((wp.getBusiness_Name()!=null && wp.getBusiness_Name().toLowerCase().contains(charText.toLowerCase())) || ( wp.getApplication_Slno()!=null && wp.getApplication_Slno().toLowerCase().contains(charText.toLowerCase())) || ( wp.getMobile_No()!=null && wp.getMobile_No().toLowerCase().contains(charText.toLowerCase())) || ( wp.getFirst_Name()!=null && wp.getFirst_Name().toLowerCase().contains(charText.toLowerCase()) ) || ( wp.getMobile_No()!=null && wp.getMobile_No().toLowerCase().contains(charText.toLowerCase()))) {
                                this.feesPaidList.add(wp);
                            }
                        }else{
                            if ((wp.getBusiness_Name()!=null && wp.getBusiness_Name().equals(selectedCollegeName)) && ((wp.getBusiness_Name()!=null && wp.getBusiness_Name().toLowerCase().contains(charText.toLowerCase())) || (wp.getApplication_Slno()!=null && wp.getApplication_Slno().toLowerCase().contains(charText.toLowerCase())) || (wp.getMobile_No()!=null && wp.getMobile_No().toLowerCase().contains(charText.toLowerCase())) || (wp.getFirst_Name()!=null && wp.getFirst_Name().toLowerCase().contains(charText.toLowerCase())) || (wp.getMobile_No()!=null && wp.getMobile_No().toLowerCase().contains(charText.toLowerCase())))) {
                                this.feesPaidList.add(wp);
                            }
                        }

                    }
                }
                notifyDataSetChanged();
            }
        }
    }
}
