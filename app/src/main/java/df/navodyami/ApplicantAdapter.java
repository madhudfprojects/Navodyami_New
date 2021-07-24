package df.navodyami;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Madhu.
 */

public class ApplicantAdapter extends BaseAdapter {

    public ArrayList<EnquiryListModel> feesPaidList;
    Activity activity;

    public ApplicantAdapter(Activity activity, ArrayList<EnquiryListModel> feesPaidList) {
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
        TextView mleadId;
        TextView mRegistrationDate;
        TextView mCollegeName;
        TextView mPhoneNumber;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fees_paid_listrow, null);
            holder = new ViewHolder();

            holder.mstudName = (TextView) convertView.findViewById(R.id.txt_studName);
            holder.mleadId = (TextView) convertView.findViewById(R.id.txt_leadId);
            holder.mRegistrationDate = (TextView) convertView.findViewById(R.id.txt_registeredDateTxt);
            holder.mCollegeName = (TextView) convertView.findViewById(R.id.txt_collegeName);
            holder.mPhoneNumber = (TextView) convertView.findViewById(R.id.txt_phoneNumber);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        EnquiryListModel item = feesPaidList.get(position);

        if(item.getFname()!=null) {
            holder.mstudName.setText(item.getFname().toString());
        }
        if(item.getMobileNo()!=null) {
            holder.mPhoneNumber.setText(item.getMobileNo().toString());
        }
        if(!item.getBussinessName().equals(null)) {
            holder.mCollegeName.setText(item.getBussinessName().toString());
        }
        if(item.getEnquiry_id()!=null) {
            holder.mleadId.setText(item.getEnquiry_id().toString());
        }
        if(item.getEmailId()!=null) {
            holder.mRegistrationDate.setText(item.getEnquiry_id().toString());
        }

        if(item.getMobileNo()!=null) {
            holder.mPhoneNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + holder.mPhoneNumber.getText().toString()));
                    v.getContext().startActivity(intent);
                }
            });
        }

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
