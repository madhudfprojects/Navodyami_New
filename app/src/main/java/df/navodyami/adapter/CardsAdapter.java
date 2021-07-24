package df.navodyami.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import df.navodyami.Class_InternetDectector;
import df.navodyami.EventListActivity;
import df.navodyami.Feedback_AttendanceActivity;

import df.navodyami.R;
import df.navodyami.util.ListviewEvents;


/**
 * @author Alhaytham Alfeel on 10/10/2016.
 */

public class CardsAdapter extends ArrayAdapter<ListviewEvents> {
    private Context context1;
    String schedule_id,datestr,stime,etime,cohortstr,classroomstr,modulestr,bookIdstr,fellowershipsrt,statusStr,userId;
    String scheduleid_holder,book_holder,startTime_holder,endTime_holder,date_holder,cohort_holder,fellowership_holder,module_holder,attandence_holder;
    private Date date;
    private Date dateCompareOne;
    private Date dateCompareTwo;
    String scheduleId,sFdate,sEdate,sEventDiscription,sEventFees,sEventName,sTotalStalls,sEventStatus,sEventType,sAttendenceTaken,sParticipants,sInchageId,sIsEventClosed;

    Class_InternetDectector internetDectector;
    Boolean isInternetPresent = false;

    public  ArrayList<ListviewEvents> listview_info_arr=new ArrayList<>();

    int dmin,dHour,dsec;

    public CardsAdapter(Context context, ArrayList<ListviewEvents> listview_info_arr) {
        super(context, R.layout.card_item);
        this.context1=context;
        this.listview_info_arr=listview_info_arr;
    }

    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
            convertView = inflater.inflate(R.layout.card_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();

        }

        ListviewEvents model = getItem(position);
        int mm=getCount();

        Log.i("TAG","mm="+mm+" getItemId(position)="+ getItemId(position)+"getItem="+getItem(position));

        schedule_id=model.getSchedule_id();
        sFdate=model.getFromDate();
        sEdate=model.getToDate();
        sEventDiscription=model.getEvent_Description();
        sEventFees=model.getEvent_Fees();
        sEventName=model.getEvent_Name();
        sEventStatus=model.getEventStatus();
        sTotalStalls=model.getTotal_Stalls();
        sEventType=model.getEvent_Type();
        sAttendenceTaken=model.getIsAttendance_Taken();
        sInchageId=model.getIncharge_Id();
        sParticipants=model.getParticipants_Count();
        statusStr=model.getStatus();
        userId=model.getUser_Id();
        sIsEventClosed = model.getIsEventClosed();

        listview_info_arr.get(position);

        Date initDate = null;
        try {
            initDate = new SimpleDateFormat("yyyy-MM-dd").parse(sFdate);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            sFdate = formatter.format(initDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

       // datestr_arr=listview_info_arr.get(position);
       /* stime=model.getStrStartTime();
        etime=model.getStrEndTime();
        cohortstr=model.getStrCohort();
        bookIdstr=model.getStrFacultyName();
        fellowershipsrt=model.getStrFellowship();
        modulestr=model.getStrModule();
        statusStr=model.getStrstatus();
*/
      /*  SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sdf_HM_format = new SimpleDateFormat("HH:mm");
        Date HM_format_Stime= null;
        Date HM_format_Etime=null;
        String Stime=null,Etime = null;
        try {
            HM_format_Stime = sdf_HM_format.parse(stime);
            HM_format_Etime=sdf_HM_format.parse(etime);
            Stime=sdf.format(HM_format_Stime);
            Etime=sdf.format(HM_format_Etime);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

     /*   Log.e("Tag_","Model=="+model+"stime="+stime+"bookIdstr="+bookIdstr);
        Log.e("Tag_","Stime="+Stime);*/
       /* Log.i("tag","listview_info_arr"+listview_info_arr.get(position).getStrDate());
        Log.i("tag","listview_info_arr"+listview_info_arr.get(position).getStrFacultyName());
        Log.i("tag","listview_info_arr"+listview_info_arr.get(position).getStrStartTime());
        Log.i("tag","listview_info_arr"+listview_info_arr.get(position).getStrModule());*/
      //  Object o = holder.btUpdate.bringPointIntoView(position);

        //   holder.imageView.setImageResource(model.getImageId());
      /*  holder.tvFaculty.setText( model.getStrModule());
        holder.tvTime.setText(stime);*/
        holder.tv_todate.setText(sEdate);
        holder.tv_fromdate.setText(sFdate);
        holder.tv_totalstalls.setText(sTotalStalls);
        Log.e("tag","sTotalStalls="+sTotalStalls);
        holder.tv_eventFees.setText( sEventFees);        //Leason_Name
        holder.tv_eventDesc.setText( sEventDiscription);
        holder.tv_eventName.setText(sEventName);
        holder.tv_scheduleId.setText(schedule_id);
        holder.tv_participants.setText(sParticipants);
        holder.tv_inchargeId.setText(sInchageId);
        holder.tv_isEventClosed.setText(sIsEventClosed);



       /* if (sAttendenceTaken.equals("1")) {
          //  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          //      holder.bt_attendence.setBackgroundColor(Color.parseColor("#FBB007"));
           // holder.bt_attendence.set(R.style.BuleButton);
            int buttonStyle = R.style.BuleButton;
            holder.bt_attendence = new Button(new ContextThemeWrapper(getContext(), buttonStyle), null, buttonStyle);
            //   }
        }*/
        holder.bt_attendence.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {

                                                        if (holder.tv_participants.getText().equals("0") || holder.tv_participants.getText().equals("") || holder.tv_participants.getText() == null) {
                                                            holder.bt_attendence.setEnabled(false);
                                                            Toast.makeText(context1, "There are no participants", Toast.LENGTH_SHORT).show();
                                                        }
                                                        if (sAttendenceTaken.equals("1")) {
                                                            //  if(sInchageId.equals(userId)) {
                                                            Log.e("tag", "sAttendenceTaken=" + sAttendenceTaken);

                                                                AlertDialog.Builder dialog = new AlertDialog.Builder(context1);
                                                                dialog.setCancelable(false);
                                                                dialog.setTitle("Alert");
                                                                dialog.setMessage(" Attendence already taken. \n Are you sure want to update it?");

                                                                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialog, int id) {
                                                                        Intent i = new Intent(getContext(), Feedback_AttendanceActivity.class);
                                                                        i.putExtra("scheduleId", holder.tv_scheduleId.getText().toString());
                                                                        i.putExtra("Event_Date", holder.tv_fromdate.getText().toString());
                                                                        i.putExtra("isEventClosed", holder.tv_isEventClosed.getText().toString());
                                                                        i.putExtra("inchargeId", holder.tv_inchargeId.getText().toString());
                                                                        context1.startActivity(i);
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
                                                                alert.setOnShowListener(new DialogInterface.OnShowListener() {
                                                                    @Override
                                                                    public void onShow(DialogInterface arg0) {
                                                                        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                                                                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
                                                                    }
                                                                });
                                                                alert.show();
                                                            } else {

                                                                Intent i = new Intent(getContext(), Feedback_AttendanceActivity.class);
                                                                i.putExtra("scheduleId", holder.tv_scheduleId.getText().toString());
                                                                i.putExtra("Event_Date", holder.tv_fromdate.getText().toString());
                                                                i.putExtra("isEventClosed", holder.tv_isEventClosed.getText().toString());
                                                                i.putExtra("inchargeId", holder.tv_inchargeId.getText().toString());
                                                                context1.startActivity(i);
                                                            }
                                                            //}
                                                       /* else {
                                                            Toast toast= Toast.makeText(context1, "  Only Inchage can take attendence  " , Toast.LENGTH_LONG);
                                                            View view =toast.getView();
                                                            view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
                                                            toast.show();
                                                        }*/

                                                    }

        });

        holder.bt_fees.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        internetDectector = new Class_InternetDectector(context1);
                                                        isInternetPresent = internetDectector.isConnectingToInternet();

                                                        String scheduleId=holder.tv_scheduleId.getText().toString();
                                                        String inchageId=holder.tv_inchargeId.getText().toString();
                                                        String eventName=holder.tv_eventName.getText().toString();
                                                        String Event_Date=holder.tv_fromdate.getText().toString();

                                                        if(isInternetPresent){
                                                            EventListActivity.GetCheck_AccessDetails getCheckAccessDetails=new EventListActivity.GetCheck_AccessDetails(context1);
                                                            getCheckAccessDetails.execute(scheduleId,inchageId,eventName,Event_Date);
                                                        }


                                                    }
                                                });
    //    holder.today_date.setText( model.getStrDate());
   /*     holder.tvfellowship.setVisibility(View.VISIBLE);
        holder.tv_attandence.setVisibility(View.VISIBLE);
        holder.tvDate.setVisibility(View.GONE);
        holder.tv_scheduleId.setVisibility(View.GONE);
        holder.tvFaculty.setVisibility(View.VISIBLE);
        Log.i("tag","statusStr="+statusStr);*/
        /*if(statusStr.equals("1") || statusStr.equals("1")){
            //holder.btUpdate.
            holder.btUpdate1.setVisibility(View.GONE);
        }else {

            holder.btUpdate1.setVisibility(View.VISIBLE);
        }*/

        //holder.tvClassRoom.setText(model.getStrClassroom());
      //  holder.tvModule.setText(model.getStrModule());

       /*int stimeLenth = model.getStrStartTime();
        String sstime[] = new String[stimeLenth];
        Log.i("Tag_sstime","stimeLenth"+stimeLenth);
       for(int i=0;i<stimeLenth;i++){

           sstime[i]=model.getStrStartTime();
           Log.i("Tag_sstime","sstime"+sstime[i]);

       }*/

      /*  holder.btIncharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =new Intent(getContext(), InchargeActivity.class);
              /*  i.putExtra("EventDiscription", mTitle.getText().toString());
                i.putExtra("EventId", mTitle.getText());
                i.putExtra("EventDate", mWhenDateTime.getText().toString());*/

         /*       i.putExtra("EventDiscription", "23625|Orientation|HB17DSF001");
                i.putExtra("EventId", "23625|Orientation|HB17DSF001");
                i.putExtra("EventDate", "Friday, April 27, 6:00 – 7:00 AM  GMT+05:30");
                context1.startActivity(i);

            }
        });
        holder.btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getContext(), Remarks.class);
               /* i.putExtra("EventDiscription", "23623|test test|TEST-DSF");
                i.putExtra("EventId", "23623|test test|TEST-DSF");
                i.putExtra("EventDate", "Monday, April 27, 9:30 – 11:30 AM  GMT+05:30");*/
         /*       i.putExtra("EventDiscription", "23625|Orientation|HB17DSF001");
                i.putExtra("EventId", "23625|Orientation|HB17DSF001");
                i.putExtra("EventDate", "Friday, April 27, 6:00 – 7:00 AM  GMT+05:30");
                context1.startActivity(i);
            }
        });*/


      /*  holder.btUpdate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               book_holder=  holder.tvFaculty.getText().toString();
               startTime_holder=holder.tvTime.getText().toString();
               endTime_holder=holder.tvTime2.getText().toString();
               date_holder=holder.tvDate.getText().toString();
               cohort_holder=holder.tvCohort.getText().toString();
               fellowership_holder=holder.tvfellowship.getText().toString();
               module_holder=holder.tvModule.getText().toString();
               attandence_holder=holder.tv_attandence.getText().toString();
               scheduleid_holder=holder.tv_scheduleId.getText().toString();

              Log.i("Tag","book="+book_holder);
                Log.i("Tag","startTime="+startTime_holder);
                Log.i("Tag","endTime="+endTime_holder);
                Log.i("Tag","date="+date_holder);
                Log.i("Tag","fellowership_holder="+fellowership_holder);
                Log.i("Tag","cohort="+cohort_holder);
                Log.e("Tag","scheduleid_holder="+scheduleid_holder);
                try {
                    int id=v.getId();

                    Integer position=(Integer) v.getTag();
                    Log.i("TAG_","position="+position+"ID="+id);
                    String dateandtime = date_holder + " " + startTime_holder;
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());

                    long currentday=calendar.getTimeInMillis();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    SimpleDateFormat daySDF = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                    Date StartTimeToCompare = sdf.parse(stime);
                    Log.i("Tag_time","dateandtime="+dateandtime);
                    Date SelectedDat = daySDF.parse(dateandtime);
                    Date currentdate1=new Date(currentday);
                    String finaldate=daySDF.format(currentdate1);
                    Date eventTime=daySDF.parse(finaldate);

                    Log.i("Tag_time","SelectedDat="+SelectedDat);
                    Log.i("Tag_time","eventTime="+eventTime);
                    // Log.i("Tag_time","finaldate="+finaldate);
                    String formattedTime = sdf.format(StartTimeToCompare);
                    Date StartTimeToCompare1=sdf.parse(formattedTime);
                    dHour=calendar.get(Calendar.HOUR_OF_DAY);
                    dmin=calendar.get(Calendar.MINUTE);
                    dsec=calendar.get(Calendar.SECOND);
                    date = sdf.parse(dHour + ":" + dmin + ":" + dsec);

                    Date date1 = new Date();
                    // Log.i("Tag_time","StartTimeToCompare1="+StartTimeToCompare1);
                    Log.i("Tag_time","datestr="+datestr);
                    Log.i("Tag_time","date1="+date1);
                    Log.i("Tag_time","date="+date);
                    Log.i("Tag_time","StartTimeToCompare="+StartTimeToCompare);

                    String PresentTimeStr=sdf.format(date1);
                    Date PresentTime=sdf.parse(PresentTimeStr);

                    Log.i("Tag_time","PresentTime="+PresentTime);
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.setTimeInMillis(System.currentTimeMillis());
                    calendar1.set(Calendar.HOUR_OF_DAY, 23);
                    calendar1.set(Calendar.MINUTE, 00);
                    calendar1.set(Calendar.SECOND, 00);

                    long mEventEnd=calendar1.getTimeInMillis();
                    Date EventEnddate = new Date(mEventEnd);

                    String EventEndTimeStr = daySDF.format(EventEnddate);
                    Date EventEndTime = daySDF.parse(EventEndTimeStr);
                    Log.i("Tag","EventEndTime="+EventEndTime);
                    String endtimeformat=sdf.format(EventEndTime);
                    Date EndTimeFinal=sdf.parse(endtimeformat);
                    SimpleDateFormat sdf_date = new SimpleDateFormat("MM-dd-yyyy");
                    String formattedTime1 = sdf_date.format(SelectedDat);
                    String formattedTime2 = sdf_date.format(date1);
                    Log.i("Madhu","formattedTime1=="+formattedTime1);
                    Log.i("Madhu","formattedTime2=="+formattedTime2);
                    Date d1=sdf_date.parse(formattedTime1);
                    Date d2=sdf_date.parse(formattedTime2);
                    Log.i("Madhu","d1=="+d1);
                    Log.i("Madhu","d2=="+d2);
                    Log.i("Tag_time","EndTimeFinal=="+EndTimeFinal);
                    Log.i("Tag_time","StartTimeToCompare=="+StartTimeToCompare);
                    if(SelectedDat.compareTo(date1) > 0 )
                    //  if(SelectedDat.compareTo(date1)>0)
                    //if(SelectedDat.compareTo(eventTime) > 0 )
                    {
                        Log.i("Tag_time","Inside if ");
                      //     holder.btUpdate.setEnabled(false);
                        // System.out.println("StartTimeToCompare is Greater than my date1");
                        Toast.makeText(context1, "Update after the class time", Toast.LENGTH_SHORT).show();
                    }
                //    else if(d2.compareTo(d1)>0 || PresentTime.compareTo(StartTimeToCompare)<0){
                    else if(d2.compareTo(d1) > 0 || EndTimeFinal.compareTo(PresentTime) < 0){
                        Toast.makeText(context1, "Allocated time for event has been over", Toast.LENGTH_SHORT).show();
                    }
                    else{
                     //   holder.btUpdate.setEnabled(true);
                       //  holder.btUpdate.setEnabled(true);
                Intent i = new Intent(getContext(), CalenderActivity.class);

                i.putExtra("ScheduleId",scheduleid_holder);
                i.putExtra("Cluster_Name",book_holder);
                i.putExtra("Lavel_Name",module_holder);
              //  i.putExtra("EventDate",stime);
                i.putExtra("Leason_Name",fellowership_holder);
                i.putExtra("Subject_Name",cohort_holder);
                i.putExtra("Institute_Name",attandence_holder);
                context1.startActivity(i);
                    }
                    // Log.i("Tag_time","dateCompareTwo="+dateCompareTwo);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        });*/

                return convertView;
    }

    static class ViewHolder {
     //   ImageView imageView;
        TextView tv_eventName;
        TextView tv_eventDesc;
        TextView tv_fromdate;
        TextView tv_todate;
        TextView tv_eventFees;
        TextView tv_totalstalls;
        Button bt_attendence;
        Button bt_fees;
        TextView tv_scheduleId;
        TextView tv_participants;
        TextView tv_inchargeId;
        TextView tv_isEventClosed;
       // Button btUpdate;


        ViewHolder(View view) {
          //  imageView = (ImageView) view.findViewById(R.id.image);
            tv_eventName = (TextView) view.findViewById(R.id.tv_eventName);
            tv_eventDesc = (TextView) view.findViewById(R.id.tv_eventDesc);
            tv_fromdate =(TextView) view.findViewById(R.id.tv_fromdate);
            tv_todate = (TextView) view.findViewById(R.id.tv_todate);
            tv_totalstalls = (TextView) view.findViewById(R.id.tv_totalstalls);
            tv_eventFees =(TextView) view.findViewById(R.id.tv_Fees);
            bt_attendence =(Button)view.findViewById(R.id.attendence_bt);
            bt_fees = (Button) view.findViewById(R.id.fees_bt);
            tv_scheduleId = (TextView) view.findViewById(R.id.tv_scheduleId);
            tv_participants = (TextView) view.findViewById(R.id.tv_participants);
            tv_inchargeId = (TextView) view.findViewById(R.id.tv_inchargeId);
            tv_isEventClosed = (TextView) view.findViewById(R.id.tv_isEventClosed);
        }
    }
}
