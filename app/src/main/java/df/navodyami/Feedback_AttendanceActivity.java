package df.navodyami;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import df.navodyami.adapter.CalendarAdapter;
import df.navodyami.util.UserInfo;

public class Feedback_AttendanceActivity extends AppCompatActivity {

    private ListView lview;
    EditText etSearch;
    ArrayList<Class_FeedbackAttendenceDetails> feedbackList=new ArrayList<>();
    ArrayList<Class_FeedbackAttendenceDetails> feedbackoriginalList=new ArrayList<>();
    private ArrayList<Class_FeedbackAttendenceDetails> originalList = null;
    private ArrayList<Class_FeedbackAttendenceDetails> feesList;

    FeedbackAttendence_CardAdapter adapter;
    Button btn_submit;
    ArrayList<String> arrLstAttendes;
    private String str_AttendesOverall="";
    ArrayList<String> arrLstSlno;
    private String str_SlnoOverall="";
    private int counts=0;
    private int counter=0;
    private ArrayList<String> arrayAttendes;
    private ArrayList<String> arrayFeedback;
    private ArrayList<String> arrayFeedbackSlno;
    SharedPreferences sharedpreferencebook_usercredential_Obj;
    SharedPreferences.Editor editor_obj;
    String str_UserId,str_scheduleId,status,Event_Date,isEventClosed,inchargeId;
    public static final String sharedpreferencebook_usercredential = "sharedpreferencebook_usercredential";
    public static final String KeyValue_employeeid = "KeyValue_employeeid";
    Class_InternetDectector internetDectector;
    Boolean isInternetPresent = false;

    public GregorianCalendar cal_month, cal_month_copy;
    public CalendarAdapter cal_adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback__attendance);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_n_actionbar);
        setSupportActionBar(toolbar);
          getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView title= (TextView) toolbar.findViewById(R.id.title_name);
        title.setText("Attendance");

        getSupportActionBar().setTitle("");
        etSearch= (EditText) findViewById(R.id.etSearch);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        lview = (NonScrollListView) findViewById(R.id.listview_feedbackattence);
        /*adapter = new FeedbackAttendence_CardAdapter(this, Class_FeedbackAttendenceDetails.feedbackAttendenceDetails_info_arr);
        lview.setAdapter(adapter);*/
        cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
        cal_month_copy = (GregorianCalendar) cal_month.clone();

        cal_adapter1 = new CalendarAdapter(this, cal_month, UserInfo.user_info_arr);
        sharedpreferencebook_usercredential_Obj=getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_UserId = sharedpreferencebook_usercredential_Obj.getString(KeyValue_employeeid, "").trim();

        arrLstAttendes=new ArrayList<>();
        arrayAttendes=new ArrayList<>();
        arrayFeedback=new ArrayList<>();
        arrayFeedbackSlno = new ArrayList<>();

        Intent intent = getIntent();
        str_scheduleId = intent.getStringExtra("scheduleId");
        Event_Date = intent.getStringExtra("Event_Date");
        isEventClosed = intent.getStringExtra("isEventClosed");
        inchargeId = intent.getStringExtra("inchargeId");

        Log.e("tag","isEventClosed="+isEventClosed);
        if(isEventClosed.equalsIgnoreCase("1")||!inchargeId.equalsIgnoreCase(str_UserId)){
            btn_submit.setVisibility(View.GONE);
        }else{
            btn_submit.setVisibility(View.VISIBLE);
        }
        feesList = new ArrayList<Class_FeedbackAttendenceDetails>();

        GetFeedbackAttendenceDetails getFeedbackAttendenceDetails=new GetFeedbackAttendenceDetails(Feedback_AttendanceActivity.this);
        getFeedbackAttendenceDetails.execute();

        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }
            ;
            @Override
            public void afterTextChanged(Editable s) {
                //adapter.getFilter().filter(s.toString());
                String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text,originalList,null);
            }
        });
        adapter = new FeedbackAttendence_CardAdapter(Feedback_AttendanceActivity.this,feesList);
        lview.setAdapter(adapter);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("lview.getCount()iss", String.valueOf(lview.getCount()));
                for (; counts < lview.getCount(); counts++) {

                    Log.e("tag","listFeedback.get(counts).getAttendance()=="+ adapter.listview_info_arr.get(counts).getAttendance());
                    Log.e("tag","listFeedback.get(counts).getFeedBack()=="+ adapter.listview_info_arr.get(counts).getFeedBack());
                    arrayAttendes.add( adapter.listview_info_arr.get(counts).getAttendance());
                    arrayFeedback.add(adapter.listview_info_arr.get(counts).getFeedBack());
                    arrayFeedbackSlno.add(adapter.listview_info_arr.get(counts).getApplication_Slno());

                }
                Log.e("str_AttendesOverall", str_AttendesOverall);
                Log.e("arrayAttendes", arrayAttendes.toString());
                SubmitFeedbackAttendence submitFeedbackAttendence=new SubmitFeedbackAttendence(Feedback_AttendanceActivity.this);
                submitFeedbackAttendence.execute();
            }
        });

    }


    public class GetFeedbackAttendenceDetails extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;
        ProgressDialog progressDialog;
        //private ProgressBar progressBar;

        GetFeedbackAttendenceDetails (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {

            SoapObject response = getEnquiryListing();

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
             <Get_Event_Schedule_Feedback_AttendanceResult>
        <vmEvent_Schedule_Feedback_Attendance>
          <Slno>int</Slno>
          <Application_Slno>long</Application_Slno>
          <Entreprenuer_Id>string</Entreprenuer_Id>
          <First_Name>string</First_Name>
          <Mobile_No>string</Mobile_No>
          <FeedBack>string</FeedBack>
          <Attendance>int</Attendance>
          <Status>string</Status>
        </vmEvent_Schedule_Feedback_Attendance>
        <vmEvent_Schedule_Feedback_Attendance>
          <Slno>int</Slno>
          <Application_Slno>long</Application_Slno>
          <Entreprenuer_Id>string</Entreprenuer_Id>
          <First_Name>string</First_Name>
          <Mobile_No>string</Mobile_No>
          <FeedBack>string</FeedBack>
          <Attendance>int</Attendance>
          <Status>string</Status>
        </vmEvent_Schedule_Feedback_Attendance>
      </Get_Event_Schedule_Feedback_AttendanceResult>

             */
            if(result != null) {

               // SoapObject result = (SoapObject) result.getProperty("vmEvent_Schedule_Feedback_Attendance");
            //    for(int i=0; i<result.getPropertyCount();i++) {
                Class_FeedbackAttendenceDetails.feedbackAttendenceDetails_info_arr.clear();
                    for (int j = 0; j < result.getPropertyCount(); j++) {
                        SoapPrimitive S_Slno,S_Application_Slno,S_Entreprenuer_Id,S_First_Name,S_Mobile_No,S_FeedBack,S_Attendance, S_Status;
                        Object O_Slno,O_Application_Slno,O_Entreprenuer_Id,O_First_Name,O_Mobile_No,O_FeedBack,O_Attendance, O_Status;
                        String str_Slno = null, str_Status = null,str_Application_Slno=null,str_Entreprenuer_Id=null,str_First_Name=null,str_Mobile_No=null,str_FeedBack=null,str_Attendance=null;

                        SoapObject materialList = (SoapObject) result.getProperty(j);
                        Log.e(" Feedback List=", materialList.toString());
                        //SoapObject vmPermanent_Employee = (SoapObject) materialList.getProperty(j);
                        String status = materialList.getProperty("Status").toString();
                        if (status.equalsIgnoreCase("Success")) {

                            O_Slno = materialList.getProperty("Slno");
                            if (!O_Slno.toString().equals("anyType{}") && !O_Slno.toString().equals(null)) {
                                S_Slno = (SoapPrimitive) materialList.getProperty("Slno");
                                Log.d("Slno new=", S_Slno.toString());
                                str_Slno = S_Slno.toString();
                            }
                            O_Application_Slno = materialList.getProperty("Application_Slno");
                            if (!O_Application_Slno.toString().equals("anyType{}") && !O_Application_Slno.toString().equals(null)) {
                                S_Application_Slno = (SoapPrimitive) materialList.getProperty("Application_Slno");
                                Log.d("Application_Slno new=", S_Application_Slno.toString());
                                str_Application_Slno = S_Application_Slno.toString();
                            }
                            O_Entreprenuer_Id = materialList.getProperty("Entreprenuer_Id");
                            if (!O_Entreprenuer_Id.toString().equals("anyType{}") && !O_Entreprenuer_Id.toString().equals(null)) {
                                S_Entreprenuer_Id = (SoapPrimitive) materialList.getProperty("Entreprenuer_Id");
                                Log.d("Entreprenuer_Id new=", S_Entreprenuer_Id.toString());
                                str_Entreprenuer_Id = S_Entreprenuer_Id.toString();
                            }
                            O_First_Name = materialList.getProperty("First_Name");
                            if (!O_First_Name.toString().equals("anyType{}") && !O_First_Name.toString().equals(null)) {
                                S_First_Name = (SoapPrimitive) materialList.getProperty("First_Name");
                                Log.d("First_Name new=", S_First_Name.toString());
                                str_First_Name = S_First_Name.toString();
                            }
                            O_Mobile_No = materialList.getProperty("Mobile_No");
                            if (!O_Mobile_No.toString().equals("anyType{}") && !O_Mobile_No.toString().equals(null)) {
                                S_Mobile_No = (SoapPrimitive) materialList.getProperty("Mobile_No");
                                Log.d("Mobile_No new=", S_Mobile_No.toString());
                                str_Mobile_No = S_Mobile_No.toString();
                            }
                            O_FeedBack = materialList.getProperty("FeedBack");
                            if (!O_FeedBack.toString().equals("anyType{}") && !O_FeedBack.toString().equals(null)) {
                                S_FeedBack = (SoapPrimitive) materialList.getProperty("FeedBack");
                                Log.d("FeedBack new=", S_FeedBack.toString());
                                str_FeedBack = S_FeedBack.toString();
                            }
                            O_Attendance = materialList.getProperty("Attendance");
                            if (!O_Attendance.toString().equals("anyType{}") && !O_Attendance.toString().equals(null)) {
                                S_Attendance = (SoapPrimitive) materialList.getProperty("Attendance");
                                Log.d("Attendance new=", S_Attendance.toString());
                                str_Attendance = S_Attendance.toString();
                            }

                         //   feesList.clear();
                            //   Class_FeedbackAttendenceDetails.feedbackAttendenceDetails_info_arr.clear();
                         /*   Class_FeedbackAttendenceDetails item = null;
                            item = new Class_FeedbackAttendenceDetails(str_Slno, str_Application_Slno, str_Entreprenuer_Id,str_First_Name,str_Mobile_No,str_FeedBack,str_Attendance,str_Status);
                            feedbackList.add(item);

                            Class_FeedbackAttendenceDetails.feedbackAttendenceDetails_info_arr.add(new Class_FeedbackAttendenceDetails(str_Slno, str_Application_Slno, str_Entreprenuer_Id,str_First_Name,str_Mobile_No,str_FeedBack,str_Attendance,str_Status) );
                            adapter.add(new Class_FeedbackAttendenceDetails(str_Slno, str_Application_Slno, str_Entreprenuer_Id,str_First_Name,str_Mobile_No,str_FeedBack,str_Attendance,str_Status));
*/
                            Class_FeedbackAttendenceDetails item = null;
                            item = new Class_FeedbackAttendenceDetails(str_Slno, str_Application_Slno, str_Entreprenuer_Id,str_First_Name,str_Mobile_No,str_FeedBack,str_Attendance,str_Status);
                            feesList.add(item);
                          //  adapter.notifyDataSetChanged();
                        }
                        else{
                            Toast toast= Toast.makeText(Feedback_AttendanceActivity.this, "  "+status+"  " , Toast.LENGTH_LONG);
                            View view =toast.getView();
                            view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
                            toast.show();
                        }


                    }

             /*   Class_FeedbackAttendenceDetails item = null;

                            item = new Class_FeedbackAttendenceDetails(str_Slno, str_Application_Slno, str_Entreprenuer_Id,str_First_Name,str_Mobile_No,str_FeedBack,str_Attendance,str_Status);
                            feedbackList.add(item);*/
                                       //     lview.setAdapter(adapter);
                            /*


                    }

                }
*/
                originalList = new ArrayList<Class_FeedbackAttendenceDetails>();
                originalList.clear();
                originalList.addAll(feesList);

                adapter.notifyDataSetChanged();

                progressDialog.dismiss();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapObject getEnquiryListing() {
        String METHOD_NAME = "Get_Event_Schedule_Feedback_Attendance";
        String SOAP_ACTION1 = "http://mis.navodyami.org/Get_Event_Schedule_Feedback_Attendance";
        String NAMESPACE = "http://mis.navodyami.org/";

        try{

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            // request.addProperty("AcademicCode",0);
            request.addProperty("Schedule_Id",str_scheduleId);
            Log.e("tag","request feedback=="+request.toString());
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
                Log.d("tag","soapresponse Feedback List="+envelope.getResponse().toString());


                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("tag","soapresponse Feedback attendence="+response.toString());

                //return null;

                return response;

            }
            catch(OutOfMemoryError ex){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(Feedback_AttendanceActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());
                final String exceptionStr = t.getMessage().toString();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(Feedback_AttendanceActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        catch(OutOfMemoryError ex){
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(Feedback_AttendanceActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());
            final String exceptionStr = t.getMessage().toString();
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(Feedback_AttendanceActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;

    }

    private class SubmitFeedbackAttendence extends AsyncTask<String, Void, Void>
    {
        ProgressDialog dialog;

        Context context;

        //  public UpdateManagerDetails(GetManagerDetails getManagerDetails) {
        //  }

        @Override
        protected void onPreExecute()
        {
            Log.i("TAG", "onPreExecute---tab2");
            dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            Log.i("TAG", "onProgressUpdate---tab2");
        }

        public SubmitFeedbackAttendence(Feedback_AttendanceActivity activity) {
            context = activity;
            dialog = new ProgressDialog(context);
        }
        @Override
        protected Void doInBackground(String... params)
        {
            Log.i("TAG", "doInBackground");
            Submit();
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            Log.i("TAG", "onPostExecute");

            Log.d("ServiceResponseisss: ",status.toString());

            dialog.dismiss();

            if(status.equalsIgnoreCase("success")) {
                Toast toast= Toast.makeText(Feedback_AttendanceActivity.this, "  Attendence submitted Successfully " , Toast.LENGTH_LONG);
                // Toast toast=   Toast.makeText(YOUR ACTIVITY NAME ,Toast.LENGTH_SHORT);
                View view =toast.getView();
                view.setBackgroundColor( Color.parseColor("#009900")); //any color your want
                toast.show();

                Date date = new Date();
                Log.i("Tag_time", "date1=" + date);
                Log.i("Tag_time", "Event_Date=" + Event_Date);
                try {
                    Date initDate = new SimpleDateFormat("dd-MM-yyyy").parse(Event_Date);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String PresentDayStr = sdf.format(initDate);
                    Log.i("Tag_time", "PresentDayStr=" + PresentDayStr);
                    cal_adapter1.getPositionList(PresentDayStr, Feedback_AttendanceActivity.this);
                    finish();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            else{
                Toast toast= Toast.makeText(Feedback_AttendanceActivity.this, "  Attendence Failed  " , Toast.LENGTH_LONG);
                View view =toast.getView();
                view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
                toast.show();
            }
        }
    }

    public void Submit(){
        String URL = Class_URL.URL_Login.toString().trim();
        String METHOD_NAME = "Save_Event_Feedback_Attendance";
        String Namespace = "http://mis.navodyami.org/", SOAPACTION = "http://mis.navodyami.org/Save_Event_Feedback_Attendance";

        try {



            SoapObject request = new SoapObject(Namespace, METHOD_NAME);

       /* ------   API Data  ---------
       <Schedule_Id>int</Schedule_Id>
      <Feedback_Attendance>string</Feedback_Attendance> ex: [{"slno":"2","Attendance":,"1","Feedback":"Its good to attend"}]
      <User_Id>int</User_Id>*/

            JSONArray array=new JSONArray();
            ArrayList<String> json_arrayList=new ArrayList<>();
            String str_json = null,str_jsonfinal;

            for(int i=0;i<arrayFeedbackSlno.size();i++){
                JSONObject obj=new JSONObject();
                try {
                    obj.put("Slno",arrayFeedbackSlno.get(i));
                    obj.put("Attendance",arrayAttendes.get(i));
                    obj.put("Feedback",arrayFeedback.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                array.put(obj);

                if(str_json==null){
                    str_json =(obj.toString() + ",");
                }else {
                    str_json = str_json + (obj.toString() + ",");
                }
            }
            int lastCommaIndex = str_json.lastIndexOf(",");
            str_jsonfinal ="[";
            str_jsonfinal += str_json.substring(0, lastCommaIndex -0);
            str_jsonfinal +="]";
            Log.e("tag","str_json="+str_json);
            Log.e("tag","str_jsonfinal="+str_jsonfinal);
            request.addProperty("Schedule_Id", str_scheduleId);
            request.addProperty("Feedback_Attendance", str_jsonfinal);
            request.addProperty("User_Id", str_UserId);

            Log.e("tag","request=="+request.toString());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {

                androidHttpTransport.call(SOAPACTION, envelope);

                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                //Log.e("tag","soap response Updateresponse"+ response.toString());

                status = response.toString();
                Log.e("tag","response =="+response.toString());
                Log.e("tag","status =="+status);


            } catch (Throwable t) {

                Log.e("request fail", "> " + t.getMessage());

            }
        } catch (Throwable t) {

            Log.e("UnRegister Error", "> " + t.getMessage());
        }
    }

    @Override
    public void onBackPressed() {


    /*    Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }*/

      /*  Intent i = new Intent(Feedback_AttendanceActivity.this, EventListActivity.class);
      //  i.putExtra("frgToLoad", "2");
        startActivity(i);*/
        Date date = new Date();
        Log.i("Tag_time", "date1=" + date);
        Log.i("Tag_time", "Event_Date=" + Event_Date);
        try {
            Date initDate = new SimpleDateFormat("dd-MM-yyyy").parse(Event_Date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String PresentDayStr = sdf.format(initDate);
            Log.i("Tag_time", "PresentDayStr=" + PresentDayStr);
            cal_adapter1.getPositionList(PresentDayStr, Feedback_AttendanceActivity.this);
            finish();
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
            /*Intent i = new Intent(Feedback_AttendanceActivity.this, EventListActivity.class);
           // i.putExtra("frgToLoad", "2");
            startActivity(i);*/
           /* Date date = new Date();
            Log.i("Tag_time", "date1=" + date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String PresentDayStr = sdf.format(date);
            Log.i("Tag_time", "PresentDayStr=" + PresentDayStr);

            cal_adapter1.getPositionList(PresentDayStr, Feedback_AttendanceActivity.this);
            finish();*/

            Date date = new Date();
            Log.i("Tag_time", "date1=" + date);
            Log.i("Tag_time", "Event_Date=" + Event_Date);

            try {
                Date initDate = new SimpleDateFormat("dd-MM-yyyy").parse(Event_Date);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String PresentDayStr = sdf.format(initDate);
                Log.i("Tag_time", "PresentDayStr=" + PresentDayStr);
                cal_adapter1.getPositionList(PresentDayStr, Feedback_AttendanceActivity.this);
                finish();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            finish();
            return true;
        }
        // break;
        return super.onOptionsItemSelected(item);
    }

}
