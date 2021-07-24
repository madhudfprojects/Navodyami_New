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
 * Created by Madhu on 28-11-2019.
 */

public class EnquiryListAdapter extends BaseAdapter {

    public ArrayList<EnquiryListModel> feesPaidList;
    Activity activity;

    public EnquiryListAdapter(Activity activity, ArrayList<EnquiryListModel> feesPaidList) {
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
    public EnquiryListModel getItem(int position) {

        //return projList.get(position);
        return feesPaidList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        TextView mstudName;
        TextView mEnquiryId;
        TextView mRegistrationDate;
        TextView mCollegeName;
        TextView mPhoneNumber;
        TextView mYearCode;
        TextView mtxt_tempId;
        ImageView mupdate_bt;
        CheckBox check_status;
        CardView card_view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final EnquiryListAdapter.ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fees_unpaid_listrow, null);
            holder = new EnquiryListAdapter.ViewHolder();

            holder.mstudName = (TextView) convertView.findViewById(R.id.txt_fname);
            holder.mEnquiryId = (TextView) convertView.findViewById(R.id.txt_enquiryId);
            holder.mRegistrationDate = (TextView) convertView.findViewById(R.id.txt_mobno);
            holder.mCollegeName = (TextView) convertView.findViewById(R.id.txt_bussname);
            holder.mPhoneNumber = (TextView) convertView.findViewById(R.id.txt_emailid);
            holder.mupdate_bt = (ImageView) convertView.findViewById(R.id.update_bt);
            holder.check_status = (CheckBox) convertView.findViewById(R.id.check_status);
            holder.mYearCode = (TextView) convertView.findViewById(R.id.txt_yearCode);
            holder.mtxt_tempId = (TextView) convertView.findViewById(R.id.txt_tempId);
            holder.card_view=(CardView)convertView.findViewById(R.id.card_view);

            convertView.setTag(holder);
        } else {
            holder = (EnquiryListAdapter.ViewHolder) convertView.getTag();
        }

        EnquiryListModel item = feesPaidList.get(position);

        if(item.getFname()!=null) {
            holder.mstudName.setText(item.getFname().toString());
        }
        if(item.getEmailId()!=null) {
            holder.mPhoneNumber.setText(item.getEmailId().toString());
        }
        if(!item.getBussinessName().equals(null)) {
            holder.mCollegeName.setText(item.getBussinessName().toString());
        }
        if(item.getEnquiry_id()!=null) {
            holder.mEnquiryId.setText(item.getEnquiry_id().toString());
        }
        if(item.getTempId()!=null) {
            holder.mtxt_tempId.setText(item.getTempId().toString());
        }
        if(item.getMobileNo()!=null) {
            holder.mRegistrationDate.setText(item.getMobileNo().toString());
        }
        if(item.getYearCode()!=null) {
         //   Log.e("tag","item.getYearCode()="+item.getYearCode());
            holder.mYearCode.setText(item.getYearCode().toString());
        }

        if(item.getDataSyncStatus()!=null) {
            if (item.getDataSyncStatus().equalsIgnoreCase("offline")) {
                holder.card_view.setBackgroundResource(R.color.gray);
            }
        }
        if(item.getMobileNo()!=null) {
            holder.mRegistrationDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + holder.mRegistrationDate.getText().toString()));
                    v.getContext().startActivity(intent);
                }
            });
        }

        holder.mupdate_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1  = new Intent (activity, EditEnquiryActivity.class);
                intent1.putExtra("EnquiryId",holder.mEnquiryId.getText());
                intent1.putExtra("TempId",holder.mtxt_tempId.getText());
                intent1.putExtra("isApplicant","0");
                intent1.putExtra("YearCode",holder.mYearCode.getText());
                activity.startActivity(intent1);
            }
        });

        holder.check_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(holder.check_status.isChecked())
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                    dialog.setCancelable(false);
                    dialog.setTitle("Alert");
                    dialog.setMessage("Convert Enquiry to Application?" );

                    dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id)
                        {
                            Intent i = new Intent(activity, AddApplicationOneActivity.class);
                            i.putExtra("EnquiryId",holder.mEnquiryId.getText());
                            i.putExtra("isApplicant","0");
                            i.putExtra("EnquiryTemptId",holder.mtxt_tempId.getText());
                            activity.startActivity(i);
                           // finish();
                        }
                    })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Action for "Cancel".
                                    dialog.dismiss();
                                    holder.check_status.setChecked(false);
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
                }/*else{
                    impactproject_againtstide_et.setVisibility(View.GONE);
                    impactproject_againtstide_et.setText("");
                    str_validation_againtstide="no";
                }
*/
            }
        });

        return convertView;
    }


    public void filter(String charText, ArrayList<EnquiryListModel> feesList, String selectedCollegeName) {
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
                    for (EnquiryListModel wp : feesList) {
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
                            if ((wp.getBussinessName()!=null && wp.getBussinessName().toLowerCase().contains(charText.toLowerCase())) || ( wp.getEnquiry_id()!=null && wp.getEnquiry_id().toLowerCase().contains(charText.toLowerCase())) || ( wp.getMobileNo()!=null && wp.getMobileNo().toLowerCase().contains(charText.toLowerCase())) || ( wp.getFname()!=null && wp.getFname().toLowerCase().contains(charText.toLowerCase()) ) || ( wp.getMobileNo()!=null && wp.getMobileNo().toLowerCase().contains(charText.toLowerCase()))) {
                                this.feesPaidList.add(wp);
                            }
                        }else{
                            if ((wp.getBussinessName()!=null && wp.getBussinessName().equals(selectedCollegeName)) && ((wp.getBussinessName()!=null && wp.getBussinessName().toLowerCase().contains(charText.toLowerCase())) || (wp.getEnquiry_id()!=null && wp.getEnquiry_id().toLowerCase().contains(charText.toLowerCase())) || (wp.getMobileNo()!=null && wp.getMobileNo().toLowerCase().contains(charText.toLowerCase())) || (wp.getFname()!=null && wp.getFname().toLowerCase().contains(charText.toLowerCase())) || (wp.getMobileNo()!=null && wp.getMobileNo().toLowerCase().contains(charText.toLowerCase())))) {
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
