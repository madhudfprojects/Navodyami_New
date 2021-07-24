package df.navodyami;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import df.navodyami.FeesModule.FeesActivity;
import df.navodyami.FeesModule.FeesListModel;
import df.navodyami.adapter.AndroidListAdapter;
import df.navodyami.adapter.CalendarAdapter;
import df.navodyami.adapter.CardsAdapter;
import df.navodyami.util.Eventlist;
import df.navodyami.util.ListviewEvents;
import df.navodyami.util.UserInfo;


public class EventListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    UserInfo[] eventListModulessarr;
    String date;
    private ListView lv_android;
    private AndroidListAdapter list_adapter;
    ArrayList<Eventlist> eventlists;
    Eventlist eventlist;
  //  private static EventlistAdapter adapter;
   // String ,datestr,stime,etime,cohortstr,classroomstr,modulestr,bookIdstr,fellowershipsrt,statusStr,attandenceStr;
    //  Boolean eventUpdate;
    private SwipeRefreshLayout swipeLayout;
    public CalendarAdapter cal_adapter1;
    String bookid;
    String cohartName;
    String fellowshipName;
    String eventdate;
    String start_time;
    static String userId;
    String status;
    String end_time;
    String module_name;
    String attendance;
    String Schedule_Status,Schedule_ID,Lavel_ID,Schedule_Date,End_Time,Start_Time,Schedule_Session,Subject_Name,Leason_Name,Lavel_Name,Cluster_Name,Institute_Name;

    ArrayList<UserInfo> arrayList = new ArrayList<UserInfo>();

    UserInfo[] userInfosarr;
    public GregorianCalendar cal_month, cal_month_copy;
    String u1;
    Class_InternetDectector internetDectector;
    Boolean isInternetPresent = false;
    //  Button logout_bt;
    private Toolbar mTopToolbar;
    String str_loginuserID, str_scheduleId;
    String scheduleId,sFdate,sEdate,sEventDiscription,sEventFees,sEventName,sEventStatus,sEventType,sTotalStalls,sAttendenceDate,sAttendenceTaken,sParticipants,sInchageId,sIsEventClosed;

    SharedPreferences sharedpref_loginuserid_Obj;
    SharedPreferences sharedpref_schedulerid_Obj;
    String finalMonth,finalYear,monthYear;
    int date_int;
    int month_int;
    int year_int;
    SharedPreferences sharedpreferencebook_usercredential_Obj;
    SharedPreferences.Editor editor_obj;
    String str_UserId;
    public static final String sharedpreferencebook_usercredential = "sharedpreferencebook_usercredential";
    public static final String KeyValue_employeeid = "KeyValue_employeeid";

    static String str_inchageIdnew,str_eventNamenew,str_scheduleIdnew,isLocation,isAdmin,Event_Date,isInchage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_n_actionbar);
        setSupportActionBar(toolbar);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView title= (TextView) toolbar.findViewById(R.id.title_name);


      /*  mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
*/
//        SharedPreferences myprefs = this.getSharedPreferences("user", Context.MODE_PRIVATE);
//        str_loginuserID = myprefs.getString("login_userid", "nothing");

      /*  sharedpref_loginuserid_Obj=getSharedPreferences(sharedpreferenc_loginuserid, Context.MODE_PRIVATE);
        str_loginuserID = sharedpref_loginuserid_Obj.getString(key_loginuserid, "").trim();

//        SharedPreferences myprefs_scheduleId = this.getSharedPreferences("scheduleId", Context.MODE_PRIVATE);
//        str_scheduleId = myprefs_scheduleId.getString("scheduleId", "nothing");
		sharedpref_schedulerid_Obj=getSharedPreferences(sharedpreferenc_schedulerid, Context.MODE_PRIVATE);
		str_scheduleId = sharedpref_schedulerid_Obj.getString(key_schedulerid, "").trim();*/
        sharedpreferencebook_usercredential_Obj=getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_UserId = sharedpreferencebook_usercredential_Obj.getString(KeyValue_employeeid, "").trim();

        userId=str_UserId;
        cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
        cal_month_copy = (GregorianCalendar) cal_month.clone();

        cal_adapter1 = new CalendarAdapter(this, cal_month, UserInfo.user_info_arr);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);

        Intent intent = getIntent();
        ListView lv_eventlist = (ListView) findViewById(R.id.lv_eventlist);
        //  TextView today_date = (TextView) findViewById(R.id.today_date);
        //   ImageView month_btn=(ImageView) findViewById(R.id.monthview);
        //    logout_bt=(Button) findViewById(R.id.logout);

       /* SharedPreferences myprefs = getSharedPreferences("user", MODE_PRIVATE);
        //Toast.makeText(Instant.this,myprefs.getAll().toString(),Toast.LENGTH_LONG).show();
        String prefName = myprefs.getString("user1", "");

        String prefName2 = myprefs.getString("pwd", "");

        u1 = prefName.toString();*/

       // List<String> arr_scheduleId = intent.getStringArrayListExtra("arr_scheduleId");

        List<String> arr_Fdate = intent.getStringArrayListExtra("arr_Fdate");
        List<String> arr_Edate = intent.getStringArrayListExtra("arr_Edate");
        List<String> arr_eventName = intent.getStringArrayListExtra("arr_eventName");
        List<String> arr_eventFees = intent.getStringArrayListExtra("arr_eventFees");
        List<String> arr_eventStatus = intent.getStringArrayListExtra("arr_eventStatus");
        List<String> arr_eventDiscription = intent.getStringArrayListExtra("arr_eventDiscription"); //Leason_Name
        List<String> arr_eventType = intent.getStringArrayListExtra("arr_eventType");
        List<String> arr_scheduleId = intent.getStringArrayListExtra("arr_scheduleId");
        List<String> arr_totalStalls = intent.getStringArrayListExtra("arr_totalStalls");
        List<String> arr_Attendance_Date = intent.getStringArrayListExtra("arr_Attendance_Date");
        List<String> arr_Attendance_Taken = intent.getStringArrayListExtra("arr_Attendance_Taken");
        List<String> arr_Participants_Count = intent.getStringArrayListExtra("arr_Participants_Count");
        List<String> arr_Incharge_Id = intent.getStringArrayListExtra("arr_Incharge_Id");
        List<String> arr_IsEventClosed = intent.getStringArrayListExtra("arr_IsEventClosed");


      /*  List<String> arr_status = intent.getStringArrayListExtra("arr_status");
        List<String> arr_attandence=intent.getStringArrayListExtra("arr_attandence");*/

        // boolean[] arr_eventUpdate = intent.getBooleanArrayExtra("arr_eventUpdate");

       /* Log.i("Tag", "Main-date=" + arr_date + "arr_module=" + arr_module);
        //  Log.i("Tagg", "arr_eventUpdate=" + arr_eventUpdate[0]);
        Log.i("tag", "Main-event=" + arr_stime  + "arr_bookId=" + arr_bookId + "arr_cohort=" + arr_cohort );
*/
        String Schedule_Status,Schedule_ID,Lavel_ID,Schedule_Date,End_Time,Start_Time,Schedule_Session,Subject_Name;
        ArrayList<UserInfo> arrayList = new ArrayList<UserInfo>();
        UserInfo[] userInfosarr;

        CardsAdapter adapter = new CardsAdapter(this, ListviewEvents.listview_info_arr);

        for (int i = 0; i < arr_Fdate.size(); i++) {
            scheduleId = arr_scheduleId.get(i);
            sFdate = arr_Fdate.get(i);
            sEdate = arr_Edate.get(i);
            sEventDiscription = arr_eventDiscription.get(i);
            sEventFees = arr_eventFees.get(i);
            sEventName = arr_eventName.get(i);
            sEventStatus = arr_eventStatus.get(i);
            sEventType = arr_eventType.get(i);
            sTotalStalls = arr_totalStalls.get(i);
            sAttendenceTaken = arr_Attendance_Taken.get(i);
            sParticipants = arr_Participants_Count.get(i);
            sInchageId = arr_Incharge_Id.get(i);
            sAttendenceDate = arr_Attendance_Date.get(i);
            sIsEventClosed = arr_IsEventClosed.get(i);
            Log.e("Tag", "arr_totalStalls.get(i)=" + arr_totalStalls.get(i));

            ListviewEvents.listview_info_arr.add(new ListviewEvents(scheduleId,sFdate, sEdate,sEventDiscription,sEventFees,sEventName,sEventStatus,sEventType,sTotalStalls,sAttendenceDate,sAttendenceTaken,sParticipants,sInchageId,str_UserId,sIsEventClosed));
            adapter.add(new ListviewEvents(scheduleId,sFdate, sEdate,sEventDiscription,sEventFees,sEventName,sEventStatus,sEventType,sTotalStalls,sAttendenceDate,sAttendenceTaken,sParticipants,sInchageId,str_UserId,sIsEventClosed));

            //    Log.i("Tag", "eventUpdate" + arr_eventUpdate.length);
            Log.i("Tag", "arr_stime.get(i).length()" + arr_Fdate.get(i).length());
         /*   if(arr_eventUpdate[i]==true){
                Log.i("arr_eventUpdate","arr_eventUpdate true=="+arr_eventUpdate[i]);
                lv_eventlist.setBackgroundColor(Color.parseColor("#A9F5D0"));
            }else if(arr_eventUpdate[i]==false){
                Log.i("arr_eventUpdate","arr_eventUpdate false=="+arr_eventUpdate[i]);
                lv_eventlist.setBackgroundColor(Color.parseColor("#F5A9A9"));
            }*/
            // date_tv.setText(date_str);
            //   event_tv.setText(event_msg_str);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            //  SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");

            try {
                Date today = sdf1.parse(sFdate);
                String today_dateStr = sdf.format(today);
                Log.i("Date","today_dateStr"+today_dateStr);
          //      getSupportActionBar().setTitle(today_dateStr);
                /// today_date.setText(today_dateStr);
                title.setText(today_dateStr);
                getSupportActionBar().setTitle("");

            } catch (ParseException e) {
                e.printStackTrace();
            }

            lv_eventlist.setAdapter(adapter);


        }
        Log.i("Tag","arr_Fdate="+arr_Fdate);
        //arr_scheduleId.clear();
        arr_Fdate.clear();
        arr_Edate.clear();
        arr_eventDiscription.clear();
        arr_eventFees.clear();
        arr_eventName.clear();
        arr_eventStatus.clear();
        arr_eventType.clear();
        arr_Attendance_Date.clear();
        arr_Attendance_Taken.clear();
        arr_Participants_Count.clear();
        arr_Incharge_Id.clear();
        arr_IsEventClosed.clear();

      /*  arr_status.clear();
        arr_attandence.clear();*/
        Log.i("Tag","arr_Fdate1="+arr_Fdate);
     /*   lv_eventlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //   String item = ((TextView)view).getText().toString();
               /* Intent intent = new Intent(MainActivity.this, EventUpdateActivity.class);
                intent.putExtra("cohortstr",cohortstr);
                intent.putExtra("classroomstr",classroomstr);
                intent.putExtra("modulestr",modulestr);
                startActivity(intent);*/

            /*    Intent i = new Intent(EventListActivity.this, Remarks.class);
               /* i.putExtra("EventDiscription", "23623|test test|TEST-DSF");
                i.putExtra("EventId", "23623|test test|TEST-DSF");
                i.putExtra("EventDate", "Monday, April 27, 9:30 – 11:30 AM  GMT+05:30");*/
               /*
                i.putExtra("EventDiscription", "23625|Orientation|HB17DSF001");
                i.putExtra("EventId", "23625|Orientation|HB17DSF001");
                i.putExtra("EventDate", "Friday, April 27, 6:00 – 7:00 AM  GMT+05:30");*/

            /*    i.putExtra("EventDiscription",bookIdstr);
                i.putExtra("EventId",bookIdstr);
                i.putExtra("EventDate",stime);
                i.putExtra("FellowshipName",fellowershipsrt);
                i.putExtra("CohortName",cohortstr);
                startActivity(i);
                Toast.makeText(getBaseContext(), "abc", Toast.LENGTH_LONG).show();
            }
        });*/


       /* month_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i  = new Intent (getApplicationContext(),CalenderActivity.class);
                startActivity(i);
            }
        });*/
    }

    /* public void logout(View view) {
         internetDectector = new ConnectionDetector(getApplicationContext());
         isInternetPresent = internetDectector.isConnectingToInternet();

         if (isInternetPresent) {


             Intent i = new Intent(getApplicationContext(), MainActivity.class);
             i.putExtra("logout_key1", "yes");
             startActivity(i);
             finish();
             //}
         }
         else{
             Toast.makeText(getApplicationContext(),"No Internet", Toast.LENGTH_SHORT).show();
         }


     }*/
    /*@Override
    public void onBackPressed() {
        // your code.
        Intent i  = new Intent (getApplicationContext(),BottomActivity.class);
        startActivity(i);
        //   super.onBackPressed();
    }*/

    @Override
    public void onRefresh() {

        Calendar now = Calendar.getInstance();
        //
        System.out.println("Current Year is : " + now.get(Calendar.YEAR));
        // month start from 0 to 11
        System.out.println("Current Month is : " + (now.get(Calendar.MONTH) + 1));
        System.out.println("Current Date is : " + now.get(Calendar.DATE));
        date_int = now.get(Calendar.DATE);
        month_int = now.get(Calendar.MONTH) + 1;
        year_int = now.get(Calendar.YEAR);

        monthYear = String.valueOf(month_int) + "/" + String.valueOf(year_int);


        int finalmonth = 0;
//monthYear=String.valueOf(month_int)+"/"+String.valueOf(year_int);
        finalmonth = now.get(Calendar.MONTH) + 1;

        if (finalmonth == 0) {
            monthYear = "0" + finalmonth + "/" + String.valueOf(year_int);
            finalMonth ="0" + finalmonth;
            finalYear = String.valueOf(year_int);
        } else if (finalmonth <= 9) {
            monthYear = "0" + finalmonth + "/" + String.valueOf(year_int);
            finalMonth ="0" + finalmonth;
            finalYear = String.valueOf(year_int);
        } else {
            monthYear = finalmonth + "/" + String.valueOf(year_int);
            finalMonth = String.valueOf(finalmonth);
            finalYear = String.valueOf(year_int);
        }

        AsyncCallWS2 task=new AsyncCallWS2(EventListActivity.this);
        task.execute();
        swipeLayout.setRefreshing(false);
    }

    private class AsyncCallWS2 extends AsyncTask<String, Void, Void>
    {
        ProgressDialog dialog;

        Context context;

        @Override
        protected void onPreExecute()
        {
            Log.i("tag", "onPreExecute---tab2");
            dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            Log.i("tag", "onProgressUpdate---tab2");
        }

        public AsyncCallWS2(EventListActivity activity) {
            context =  activity;
            dialog = new ProgressDialog(activity);
        }
        @Override
        protected Void doInBackground(String... params)
        {
            Log.i("tag", "doInBackground");


            fetch_all_info();
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            dialog.dismiss();
            Log.i("tag", "onPostExecute");

            // startActivity(i);
            Date date = new Date();
            Log.i("Tag_time","date1="+date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //  SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String PresentDayStr=sdf.format(date);
            Log.i("Tag_time","PresentDayStr="+PresentDayStr);

            cal_adapter1.getPositionList(PresentDayStr, EventListActivity.this);
            finish();
        }
    }

 /*   public void fetch_all_info(String email) {
        String METHOD_NAME = "GetTrainerScheduleList";
        String SOAP_ACTION1 = "http://mis.detedu.org/GetTrainerScheduleList";

        try {

            SoapObject request = new SoapObject("http://mis.detedu.org/", METHOD_NAME);

            Log.i("email=", email);
            request.addProperty("Email", email);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
            HttpTransportSE androidHttpTransport = new HttpTransportSE("http://mis.detedu.org/DETServices.asmx?WSDL");
            //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

            try {
                androidHttpTransport.call(SOAP_ACTION1, envelope);
                Log.d("soap responseyyyyyyy", envelope.getResponse().toString());
                SoapObject response = (SoapObject) envelope.getResponse();
                Log.d("soap responseyyyyyyy", response.toString());
                //	for (SoapObject cs : result1)
                if (response.getPropertyCount() > 0) {
                    int Count = response.getPropertyCount();

                    for (int i = 0; i < Count; i++) {
                        // sizearray=result1.size();
                        //  Log.i("Tag","sizearray="+sizearray);
                        SoapObject list = (SoapObject) response.getProperty(i);
                        status = list.getProperty("status").toString();

                        String a = list.getProperty("notes").toString().trim();
                        bookid = list.getProperty("booking_id").toString();
                        cohartName = list.getProperty("cohortname").toString();
                        fellowshipName = list.getProperty("fellowshipname").toString();
                        eventdate = list.getProperty("date").toString();
                        start_time = list.getProperty("start_time").toString();
                        userId = list.getProperty("user_id").toString();
                        end_time = list.getProperty("end_time").toString();

                        module_name = list.getProperty("module_name").toString();
                        attendance = list.getProperty("attendance").toString();
                        eventUpdate = true;

                        //   State cat = new State(c.getString("state_id"),c.getString("state_name"));
                        UserInfo userInfo = new UserInfo(bookid, userId, eventdate, start_time, cohartName, fellowshipName, eventUpdate, end_time, module_name, status, attendance);
                        arrayList.add(userInfo);

                        Log.e("TAG", "bookid=" + bookid + "module_name=" + module_name + "cohartName=" + cohartName + "fellowshipName=" + fellowshipName + "eventdate=" + eventdate + "start_time" + start_time);

                    }

                    final String[] items = new String[Count];
                    userInfosarr = new UserInfo[Count];
                    UserInfo obj = new UserInfo();

                    UserInfo.user_info_arr.clear();
                    for (int i = 0; i < response.getPropertyCount(); i++) {
                        bookid = arrayList.get(i).booking_id;
                        cohartName = arrayList.get(i).cohortname;
                        userId = arrayList.get(i).user_id;
                        eventUpdate = arrayList.get(i).eventUpdate;
                        start_time = arrayList.get(i).start_time;
                        fellowshipName = arrayList.get(i).fellowshipname;
                        eventdate = arrayList.get(i).date1;
                        end_time = arrayList.get(i).end_time;
                        status = arrayList.get(i).event_status;
                        module_name = arrayList.get(i).module_name;
                        attendance = arrayList.get(i).attendance;
                        obj.setBooking_id(bookid);
                        obj.setCohortname(cohartName);
                        obj.setDate1(eventdate);
                        obj.setFellowshipname(fellowshipName);
                        obj.setStart_time(start_time);
                        obj.setUser_id(userId);
                        obj.setEnd_time(end_time);
                        obj.setEvent_status(status);
                        obj.setModule_name(module_name);
                        obj.setAttendance(attendance);
                        userInfosarr[i] = obj;
                        //   UserInfo.user_info_arr =new ArrayList<UserInfo>() ;

                        UserInfo.user_info_arr.add(new UserInfo(bookid, userId, eventdate, start_time, cohartName, fellowshipName, eventUpdate, end_time, module_name, status, attendance));

                        Log.i("Tag", "items aa=" + arrayList.get(i).booking_id);

                    }

                    Log.i("Tag", "items=" + items.length);
                }

            } catch (Throwable t) {
                //Toast.makeText(MainActivity.this, "Request failed: " + t.toString(),
                //		Toast.LENGTH_LONG).show();
                Log.e("request fail 5", "> " + t.getMessage());
            }
        } catch (Throwable t) {
            Log.e("Tag", "UnRegister Receiver Error" + " > " + t.getMessage());

        }

    }*/

    public void fetch_all_info() {
        String METHOD_NAME = "GetEventList";
        String SOAP_ACTION1 = "http://mis.navodyami.org/GetEventList";

        try {

            SoapObject request = new SoapObject("http://mis.navodyami.org/", METHOD_NAME);

            // Log.i("id=", Emp_id);


            //   Log.i("tag","monthyear="+monthYear);
            /*request.addProperty("p_Month", 01);
            request.addProperty("p_Year", 2020);*/
            request.addProperty("p_Month", Integer.parseInt(finalMonth));
            request.addProperty("p_Year", Integer.parseInt(finalYear));

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Login);
            //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

            try {
                androidHttpTransport.call(SOAP_ACTION1, envelope);
                Log.d("soap responseyyyyyyy", envelope.getResponse().toString());
                SoapObject response = (SoapObject) envelope.getResponse();
                Log.d("soap responseyyyyyyy", response.toString());
                // for (SoapObject cs : result1)
                if (response.getPropertyCount() > 0) {
                    int Count = response.getPropertyCount();

                    String schedule_id, event_Description, event_TypeSlno, event_Type, event_Id, event_Name, event_Fees, total_Stalls=null, fromDate, toDate;
                    String Participants_Count=null,Incharge_Id=null, eventStatus, isEventClosed, eventClosed_Date = null, isChecklistCompleted, isExpenseUpdated, isAccountantVerified, verified_Date, user_Id=null, username = null, email_id, status,isAttendance_Taken = null,Attendance_Date;

                    for (int i = 0; i < Count; i++) {
                        // sizearray=result1.size();
                        //  Log.i("Tag","sizearray="+sizearray);
                        SoapObject O_eventClosed_Date;
                        SoapPrimitive S_eventClosed_Date;
                        SoapObject list = (SoapObject) response.getProperty(i);
                        status = list.getProperty("Status").toString();

                        //   String a = list.getProperty("notes").toString().trim();
                        schedule_id = list.getProperty("schedule_id").toString();
                        event_Description = list.getProperty("Event_Description").toString();
                        event_TypeSlno = list.getProperty("Event_TypeSlno").toString();
                        event_Type = list.getProperty("Event_Type").toString();
                        event_Id = list.getProperty("Event_Id").toString();
                        event_Name = list.getProperty("Event_Name").toString();
                        event_Fees = list.getProperty("Event_Fees").toString();
                        total_Stalls = list.getProperty("Total_Stalls").toString();
                        fromDate = list.getProperty("FromDate").toString();
                        toDate = list.getProperty("ToDate").toString();
                        eventStatus = list.getProperty("EventStatus").toString();
                        isEventClosed = list.getProperty("isEventClosed").toString();
                        /*if(isEventClosed.equalsIgnoreCase("1")){
                            {
                                eventClosed_Date = list.getProperty("eventClosed_Date").toString();
                            }*/
                        isChecklistCompleted = list.getProperty("isChecklistCompleted").toString();
                        isExpenseUpdated = list.getProperty("isExpenseUpdated").toString();
                        isAccountantVerified = list.getProperty("isAccountantVerified").toString();
                        verified_Date = list.getProperty("Verified_Date").toString();
                        user_Id = list.getProperty("User_Id").toString();
                        username = list.getProperty("username").toString();
                        email_id = list.getProperty("email_id").toString();

                        isAttendance_Taken = list.getProperty("isAttendance_Taken").toString();
                        Attendance_Date = list.getProperty("Attendance_Date").toString();
                        eventClosed_Date = list.getProperty("EventClosed_Date").toString();
                        Participants_Count = list.getProperty("Participants_Count").toString();
                        Incharge_Id = list.getProperty("Incharge_Id").toString();

                       /* O_eventClosed_Date = (SoapObject) list.getProperty("EventClosed_Date");
                        if (!O_eventClosed_Date.toString().equals("anyType{}") && !O_eventClosed_Date.toString().equals(null)) {
                            S_eventClosed_Date = (SoapPrimitive) list.getProperty("EventClosed_Date");
                            eventClosed_Date = S_eventClosed_Date.toString();
                            Log.d("eventClosed_Date", eventClosed_Date);
                        }*/

                        String date1=list.getProperty("FromDate").toString();
                        Date initDate = new SimpleDateFormat("dd-MM-yyyy").parse(date1);
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        date = formatter.format(initDate);
                        System.out.println(date);
                        // date=sdf.format(date1);
                        Log.i("Tag_time","date="+date);
                        //   State cat = new State(c.getString("state_id"),c.getString("state_name"));
                        UserInfo userInfo = new UserInfo(schedule_id,event_Description,event_TypeSlno,event_Type,event_Id,event_Name,event_Fees,total_Stalls,fromDate,toDate,eventStatus,isEventClosed,eventClosed_Date,isChecklistCompleted,isExpenseUpdated,isAccountantVerified,verified_Date,user_Id,username,email_id,status,Attendance_Date,isAttendance_Taken,Participants_Count,Incharge_Id);
                        arrayList.add(userInfo);

                    }

                    final String[] items = new String[Count];
                    eventListModulessarr = new UserInfo[Count];
                    UserInfo obj = new UserInfo();

                    UserInfo.user_info_arr.clear();
                    for (int i = 0; i < response.getPropertyCount(); i++) {
                        schedule_id = arrayList.get(i).schedule_id;
                        event_Description = arrayList.get(i).Event_Description;
                        eventClosed_Date = arrayList.get(i).EventClosed_Date;
                        event_Fees = arrayList.get(i).Event_Fees;
                        event_Id = arrayList.get(i).Event_Id;
                        event_Name = arrayList.get(i).Event_Name;
                        event_Type = arrayList.get(i).Event_Type;
                        event_TypeSlno = arrayList.get(i).Event_TypeSlno;
                        eventStatus= arrayList.get(i).EventStatus;
                        fromDate = arrayList.get(i).FromDate;
                        toDate = arrayList.get(i).ToDate;
                        verified_Date = arrayList.get(i).Verified_Date;
                        email_id = arrayList.get(i).email_id;
                        isEventClosed = arrayList.get(i).isEventClosed;
                        isAccountantVerified = arrayList.get(i).isAccountantVerified;
                        isChecklistCompleted = arrayList.get(i).isChecklistCompleted;
                        isExpenseUpdated = arrayList.get(i).isExpenseUpdated;
                        email_id = arrayList.get(i).email_id;

                        Attendance_Date =arrayList.get(i).Attendance_Date;
                        isAttendance_Taken= arrayList.get(i).isAttendance_Taken;
                        Participants_Count=arrayList.get(i).Participants_Count;
                        Incharge_Id=arrayList.get(i).Incharge_Id;
                        status = arrayList.get(i).Status;

                        obj.setSchedule_id(schedule_id);
                        obj.setEvent_Description(event_Description);
                        obj.setEventClosed_Date(eventClosed_Date);
                        obj.setEvent_Description(event_Description);
                        obj.setEvent_Fees(event_Fees);
                        obj.setEvent_TypeSlno(event_TypeSlno);
                        obj.setEvent_Type(event_Type);
                        obj.setEmail_id(email_id);
                        obj.setFromDate(fromDate);
                        obj.setToDate(toDate);
                        obj.setIsAccountantVerified(isAccountantVerified);
                        obj.setIsChecklistCompleted(isChecklistCompleted);
                        obj.setIsExpenseUpdated(isExpenseUpdated);
                        obj.setIsEventClosed(isEventClosed);
                        obj.setVerified_Date(verified_Date);
                        obj.setEventStatus(eventStatus);
                        obj.setStatus(status);
                        obj.setAttendance_Date(Attendance_Date);
                        obj.setIsAttendance_Taken(isAttendance_Taken);
                        obj.setParticipants_Count(Participants_Count);
                        obj.setIncharge_Id(Incharge_Id);
                        eventListModulessarr[i] = obj;
                        //   UserInfo.user_info_arr =new ArrayList<UserInfo>() ;

                        UserInfo.user_info_arr.add(new UserInfo(schedule_id, event_Description, event_TypeSlno, event_Type, event_Id, event_Name, event_Fees, total_Stalls, fromDate, toDate, eventStatus, isEventClosed,eventClosed_Date, isChecklistCompleted, isExpenseUpdated, isAccountantVerified, verified_Date, user_Id, username, email_id, status,Attendance_Date,isAttendance_Taken,Participants_Count,Incharge_Id) );

                        Log.i("Tag", "items aa=" + arrayList.get(i).schedule_id);

                    }

                    Log.i("Tag", "items=" + items.length);
                }

            } catch (Throwable t) {
                //Toast.makeText(MainActivity.this, "Request failed: " + t.toString(),
                //    Toast.LENGTH_LONG).show();
                Log.e("request fail 5", "> " + t.getMessage());
            }
        } catch (Throwable t) {
            Log.e("Tag", "UnRegister Receiver Error" + " > " + t.getMessage());

        }

    }

    public static class GetCheck_AccessDetails extends AsyncTask<String, Void, SoapObject> {

        Context context;
        ProgressDialog progressDialog;
        //private ProgressBar progressBar;

        public GetCheck_AccessDetails(Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(String... params) {

            str_scheduleIdnew = (String) params[0];
             str_inchageIdnew=(String) params[1];
             str_eventNamenew=(String) params[2];
            Event_Date=(String) params[3];
            SoapObject response = getCheck_Access(context);

            //Log.d("Soap response is",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
      /*      progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/
            progressDialog.setMessage("Loading");
            progressDialog.setProgressStyle(android.R.attr.progressBarStyleSmall);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(SoapObject result) {

            /*
             <Check_AccessResult>
        <isLocation>boolean</isLocation>
        <isAdmin>boolean</isAdmin>
      </Check_AccessResult>
             */
            if(result != null) {

                // SoapObject result = (SoapObject) result.getProperty("vmEvent_Schedule_Feedback_Attendance");
                //    for(int i=0; i<result.getPropertyCount();i++) {
                for (int j = 0; j < result.getPropertyCount(); j++) {

                  //  SoapPrimitive materialList = (SoapPrimitive) result.getProperty(j);
                    //SoapObject vmPermanent_Employee = (SoapObject) materialList.getProperty(j);
                    String status=result.getProperty("Status").toString();
                    if(status.equalsIgnoreCase("Success")) {
                        isLocation = result.getProperty("isLocation").toString();
                        isAdmin = result.getProperty("isAdmin").toString();
                    }else{
                        Toast.makeText(context,"User Not Exists", Toast.LENGTH_LONG).show();
                    }
                    Log.e("tag","isAdmin="+isAdmin+",isLocation="+isLocation);

                }
                if(!str_inchageIdnew.equalsIgnoreCase(userId)){
                    isInchage="false";
                }else{
                    isInchage="true";
                }
                Log.e("tag","str_inchageIdnew="+str_inchageIdnew);
                if(!str_inchageIdnew.equalsIgnoreCase(userId)&&isLocation.equalsIgnoreCase("false")&&isAdmin.equalsIgnoreCase("false")){
                    Toast.makeText(context,"You have limited access", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent i = new Intent(context, FeesActivity.class);
                    i.putExtra("scheduleId", str_scheduleIdnew);
                    i.putExtra("userId", userId);
                    i.putExtra("eventName", str_eventNamenew);
                    i.putExtra("isLocation", isLocation);
                    i.putExtra("isAdmin", isAdmin);
                    i.putExtra("isInchage",isInchage);
                    i.putExtra("Event_Date",Event_Date);
                    context.startActivity(i);
                }

                progressDialog.dismiss();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private static SoapObject getCheck_Access(Context context) {
        String METHOD_NAME = "Check_Access";
        String SOAP_ACTION1 = "http://mis.navodyami.org/Check_Access";
        String NAMESPACE = "http://mis.navodyami.org/";

        try{
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            request.addProperty("User_Id",userId);

            Log.e("tag","soap request check="+request.toString());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Login.toString().trim());

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                //SoapPrimitive response1 = (SoapPrimitive) envelope.getResponse();
                Log.d("tag","soapresponse check access="+envelope.getResponse().toString());


                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("tag","soapresponse check access="+response.toString());

                //return null;

                return response;

            }
            catch(OutOfMemoryError ex){

                        Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());
                final String exceptionStr = t.getMessage().toString();

                        Toast.makeText(context,"Web Service Error", Toast.LENGTH_LONG).show();

            }
        }
        catch(OutOfMemoryError ex){
                    Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
        }
        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());
            final String exceptionStr = t.getMessage().toString();

                    Toast.makeText(context,"Web Service Error", Toast.LENGTH_LONG).show();
        }
        return null;

    }

    @Override
    public void onBackPressed() {


    /*    Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }*/

        Intent i = new Intent(EventListActivity.this, BottomActivity.class);
        i.putExtra("frgToLoad", "2");
        startActivity(i);

        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        MenuItem action_Sync = menu.findItem(R.id.Sync);
        action_Sync.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.logout) {
            // Toast.makeText(CalenderActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
            internetDectector = new Class_InternetDectector(getApplicationContext());
            isInternetPresent = internetDectector.isConnectingToInternet();

            if (isInternetPresent) {

                SaveSharedPreference.setUserName(getApplicationContext(),"");

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("Key_Logout", "yes");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();

            }
            else{
                Toast.makeText(getApplicationContext(),"No Internet", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        if(id== android.R.id.home) {
            //  Toast.makeText(getApplicationContext(),"Back button clicked", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(EventListActivity.this, BottomActivity.class);
            i.putExtra("frgToLoad", "2");
            startActivity(i);
            finish();
            return true;
        }
        // break;
        return super.onOptionsItemSelected(item);
    }

}
