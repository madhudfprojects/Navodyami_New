package df.navodyami;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import df.navodyami.util.ListviewEvents;


public class FeedbackAttendence_CardAdapter extends ArrayAdapter<Class_FeedbackAttendenceDetails> {
    private Context context1;
   String Slno,Application_Slno,Entreprenuer_Id,First_Name,Mobile_No,FeedBack,Attendance,Status;

    public  ArrayList<Class_FeedbackAttendenceDetails> listview_info_arr=new ArrayList<>();

    int dmin,dHour,dsec;

 /*   public FeedbackAttendence_CardAdapter(Context context, ArrayList<Class_FeedbackAttendenceDetails> listview_info_arr) {
        super(context, R.layout.feedbackattendce_carditem);
        this.context1=context;
        this.listview_info_arr=listview_info_arr;
    }*/

    public FeedbackAttendence_CardAdapter(Feedback_AttendanceActivity context, ArrayList<Class_FeedbackAttendenceDetails> feesList) {
        super(context, R.layout.feedbackattendce_carditem);
        this.context1=context;
        this.listview_info_arr=feesList;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
            convertView = inflater.inflate(R.layout.feedbackattendce_carditem, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();

        }

        Class_FeedbackAttendenceDetails model = getItem(position);
        int mm=getCount();

        Log.i("TAG","mm="+mm+" getItemId(position)="+ getItemId(position)+"getItem="+getItem(position));


        Entreprenuer_Id=model.getEntreprenuer_Id();
        Application_Slno=model.getApplication_Slno();
        Attendance=model.getAttendance();
        FeedBack=model.getFeedBack();
        First_Name=model.getFirst_Name();
        Mobile_No=model.getMobile_No();
        Slno=model.getSlno();

        holder.tv_Entreprenuer_Id.setText(Entreprenuer_Id);
        holder.tv_First_Name.setText(First_Name);
        holder.tv_Mobile_No.setText(Mobile_No);
        holder.tv_Slno.setText(Slno);
        holder.edt_FeedBack.setText(FeedBack);

        if(Attendance.equalsIgnoreCase("0")){
            holder.btn_PreAbs.setBackgroundResource(R.drawable.buttonboarder_abs);
            holder.btn_PreAbs.setText("Absent");
            holder.txt_PreAbsValue.setText("0");
            holder.btn_PreAbs.setTextColor(Color.parseColor("#ff0000"));
        }else{
            holder.btn_PreAbs.setBackgroundResource(R.drawable.buttonboarder_pre);
            holder.btn_PreAbs.setText("Present");
            holder.txt_PreAbsValue.setText("1");
            holder.btn_PreAbs.setTextColor(Color.parseColor("#006600"));
        }

            holder.btn_PreAbs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (holder.btn_PreAbs.getText().toString().equals("Present")) {
                        holder.btn_PreAbs.setBackgroundResource(R.drawable.buttonboarder_abs);
                        holder.btn_PreAbs.setText("Absent");
                        holder.txt_PreAbsValue.setText("0");
                        listview_info_arr.get(position).setAttendance("0");
                        if (v instanceof Button) {
                            ((Button) v).setTextColor(Color.parseColor("#ff0000"));
                        }
                    }else {
                        holder.btn_PreAbs.setBackgroundResource(R.drawable.buttonboarder_pre);
                        holder.btn_PreAbs.setText("Present");
                        holder.txt_PreAbsValue.setText("1");
                        listview_info_arr.get(position).setAttendance("1");
                        if (v instanceof Button) {
                            ((Button) v).setTextColor(Color.parseColor("#006600"));
                        }
                    }
                }
            });

        if(model.getMobile_No()!=null) {
            holder.tv_Mobile_No.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + holder.tv_Mobile_No.getText().toString()));
                    v.getContext().startActivity(intent);
                }
            });
        }
        listview_info_arr.get(position).setFeedBack(holder.edt_FeedBack.getText().toString());
        listview_info_arr.get(position).setSlno(holder.tv_Slno.getText().toString());
       /* holder.btn_PreAbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str_Slno=holder.tv_Slno.getText().toString();
            }
        });*/



        return convertView;
    }

    static class ViewHolder {
        TextView tv_Entreprenuer_Id;
        TextView tv_First_Name;
        TextView tv_Mobile_No;
        EditText edt_FeedBack;
        TextView tv_Slno;
        Button btn_PreAbs;
        TextView txt_PreAbsValue;
      //  CheckBox cb_Attendance;


        ViewHolder(View view) {
          //  imageView = (ImageView) view.findViewById(R.id.image);
            tv_Entreprenuer_Id = (TextView) view.findViewById(R.id.tv_Entreprenuer_Id);
            tv_First_Name = (TextView) view.findViewById(R.id.tv_First_Name);
            tv_Mobile_No =(TextView) view.findViewById(R.id.tv_Mobile_No);
            edt_FeedBack = (EditText) view.findViewById(R.id.edt_FeedBack);
          //  cb_Attendance = (CheckBox) view.findViewById(R.id.checkbox_Attendance);
            tv_Slno =(TextView) view.findViewById(R.id.tv_Slno);
            btn_PreAbs = (Button) view.findViewById(R.id.btn_PreAbs);
            txt_PreAbsValue = (TextView) view.findViewById(R.id.txt_PreAbsValue);
         //   txt_PreAbsValue.setText("1");
        }
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getCount() {
        //return projList.size();
        Log.e("tag","listview_info_arr.size()="+listview_info_arr.size());

        return listview_info_arr.size();
    }

    @Override
    public Class_FeedbackAttendenceDetails getItem(int position) {

        //return projList.get(position);
        return listview_info_arr.get(position);
    }
    public void filter(String charText, ArrayList<Class_FeedbackAttendenceDetails> feesList, String selectedCollegeName) {

        this.listview_info_arr.clear();

        if(charText!=null) {
            if(feesList!=null) {
                if (charText.isEmpty() || charText.length() == 0) {
                    this.listview_info_arr.addAll(feesList);
                } else {
                    for (Class_FeedbackAttendenceDetails wp : feesList) {

                        if(selectedCollegeName == null) {
                            if ((wp.getFirst_Name()!=null && wp.getFirst_Name().toLowerCase().contains(charText.toLowerCase())) || ( wp.getApplication_Slno()!=null && wp.getApplication_Slno().toLowerCase().contains(charText.toLowerCase())) || ( wp.getMobile_No()!=null && wp.getMobile_No().toLowerCase().contains(charText.toLowerCase())) || ( wp.getFirst_Name()!=null && wp.getFirst_Name().toLowerCase().contains(charText.toLowerCase()) ) || ( wp.getMobile_No()!=null && wp.getMobile_No().toLowerCase().contains(charText.toLowerCase()))) {
                                this.listview_info_arr.add(wp);
                            }
                        }else{
                            if ((wp.getFirst_Name()!=null && wp.getFirst_Name().equals(selectedCollegeName)) && ((wp.getFirst_Name()!=null && wp.getFirst_Name().toLowerCase().contains(charText.toLowerCase())) || (wp.getApplication_Slno()!=null && wp.getApplication_Slno().toLowerCase().contains(charText.toLowerCase())) || (wp.getMobile_No()!=null && wp.getMobile_No().toLowerCase().contains(charText.toLowerCase())) || (wp.getFirst_Name()!=null && wp.getFirst_Name().toLowerCase().contains(charText.toLowerCase())) || (wp.getMobile_No()!=null && wp.getMobile_No().toLowerCase().contains(charText.toLowerCase())))) {
                                this.listview_info_arr.add(wp);
                            }
                        }

                    }
                }
                notifyDataSetChanged();
            }
        }
    }
/*    public void filter(String charText, ArrayList<Class_FeedbackAttendenceDetails> feesList, String selectedCollegeName) {
        //charText = charText.toLowerCase(Locale.getDefault());
        //Log.d("charTextissss",charText);
        this.listview_info_arr.clear();

     *//*   for(FeesUnpaidModel feesUnpaidModel : feesList){
            String  stdName = feesUnpaidModel.getStudent_name().toString();
            //Log.d("StudeNameissssss",stdName);
        }*//*

        if(charText!=null) {
            if(feesList!=null) {
                if (charText.isEmpty() || charText.length() == 0) {
                    this.listview_info_arr.addAll(feesList);
                } else {
                    for (Class_FeedbackAttendenceDetails wp : feesList) {
                        //Log.d("GetCollegeNameissss",wp.getCollege_name().toLowerCase((Locale.getDefault())));
                        if(selectedCollegeName == null) {
                            if ((wp.getEntreprenuer_Id()!=null && wp.getEntreprenuer_Id().toLowerCase().contains(charText.toLowerCase())) || ( wp.getApplication_Slno()!=null && wp.getApplication_Slno().toLowerCase().contains(charText.toLowerCase())) || ( wp.getMobile_No()!=null && wp.getMobile_No().toLowerCase().contains(charText.toLowerCase())) || ( wp.getFirst_Name()!=null && wp.getFirst_Name().toLowerCase().contains(charText.toLowerCase()) ) || ( wp.getMobile_No()!=null && wp.getMobile_No().toLowerCase().contains(charText.toLowerCase()))) {
                                this.listview_info_arr.add(wp);
                            }
                        }else{
                            if ((wp.getEntreprenuer_Id()!=null && wp.getEntreprenuer_Id().equals(selectedCollegeName)) && ((wp.getEntreprenuer_Id()!=null && wp.getEntreprenuer_Id().toLowerCase().contains(charText.toLowerCase())) || (wp.getApplication_Slno()!=null && wp.getApplication_Slno().toLowerCase().contains(charText.toLowerCase())) || (wp.getMobile_No()!=null && wp.getMobile_No().toLowerCase().contains(charText.toLowerCase())) || (wp.getFirst_Name()!=null && wp.getFirst_Name().toLowerCase().contains(charText.toLowerCase())) || (wp.getMobile_No()!=null && wp.getMobile_No().toLowerCase().contains(charText.toLowerCase())))) {
                                this.listview_info_arr.add(wp);
                            }
                        }

                    }
                }
                notifyDataSetChanged();
            }
        }
    }*/
}
