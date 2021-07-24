package df.navodyami.FeesModule;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import java.util.Date;
import java.util.GregorianCalendar;

import df.navodyami.BottomActivity;
import df.navodyami.Class_InternetDectector;
import df.navodyami.Class_URL;
import df.navodyami.EventListActivity;
import df.navodyami.Feedback_AttendanceActivity;
import df.navodyami.MainActivity;
import df.navodyami.R;
import df.navodyami.SaveSharedPreference;
import df.navodyami.adapter.CalendarAdapter;
import df.navodyami.util.UserInfo;


public class FeesActivity extends AppCompatActivity {

    ListView lv_Feeslist;
    private ArrayList<FeesListModel> feesList;
    private FeesAdapter adapter;

    String userId,scheduleId,eventName,isAdmin,isLocation,isInchage;
    TextView allotedAmt_tv,eventName_tv;
    public GregorianCalendar cal_month, cal_month_copy;
    public CalendarAdapter cal_adapter1;

    String Event_Date;

    SharedPreferences sharedpreferencebook_usercredential_Obj;
    SharedPreferences.Editor editor_obj;
    String str_UserId;
    public static final String sharedpreferencebook_usercredential = "sharedpreferencebook_usercredential";
    public static final String KeyValue_employeeid = "KeyValue_employeeid";

    Class_InternetDectector internetDectector;
    Boolean isInternetPresent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_n_actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final TextView title= (TextView) toolbar.findViewById(R.id.title_name);
        getSupportActionBar().setTitle("");
        title.setText("Fees Payment List");

        sharedpreferencebook_usercredential_Obj=getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_UserId = sharedpreferencebook_usercredential_Obj.getString(KeyValue_employeeid, "").trim();

      /*  internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();*/

        eventName_tv=(TextView) findViewById(R.id.eventName_tv);
        allotedAmt_tv=(TextView) findViewById(R.id.allotedAmt_tv);
        lv_Feeslist= (ListView) findViewById(R.id.lv_Feeslist);


        cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
        cal_month_copy = (GregorianCalendar) cal_month.clone();
        cal_adapter1 = new CalendarAdapter(this, cal_month, UserInfo.user_info_arr);

        Intent intent = getIntent();
        if(getIntent().getExtras()!=null) {
            userId = intent.getStringExtra("userId");
            scheduleId = intent.getStringExtra("scheduleId");
            eventName = intent.getStringExtra("eventName");
            isAdmin = intent.getStringExtra("isAdmin");
            isLocation = intent.getStringExtra("isLocation");
            isInchage = intent.getStringExtra("isInchage");
            Event_Date = intent.getStringExtra("Event_Date");
            Log.e("tag","fees intent data= "+"Schedule_Id="+scheduleId+"str_UserId="+userId+"Event_Name="+eventName);
            Log.e("tag","IsLocation="+isLocation+"IsAdmin="+isAdmin+"isInchage="+isInchage+"Event_Date="+Event_Date);
        }

        Log.e("tag","scheduleId"+scheduleId+"eventName="+eventName+"");
        feesList = new ArrayList<FeesListModel>();
        adapter = new FeesAdapter(this, FeesListModel.feesDetails_info_arr);

        eventName_tv.setText(eventName);
        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

        if(isInternetPresent){
            GetFeesDetails getFeesDetails=new GetFeesDetails(FeesActivity.this);
            getFeesDetails.execute();
        }else{
            //Toast.makeText(this,"There is no internet connection", Toast.LENGTH_SHORT).show();
            Toast toast= Toast.makeText(FeesActivity.this, "  There is no internet connection  " , Toast.LENGTH_SHORT);
            View view =toast.getView();
            view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
            toast.show();
        }

      //  CardsAdapter adapter = new CardsAdapter(this, ListviewEvents.listview_info_arr);

    }

    public class GetFeesDetails extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        ProgressDialog progressDialog;
        //private ProgressBar progressBar;

        GetFeesDetails (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {

            SoapObject response = getFeesListing();

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
             <Slno>int</Slno>
          <Schedule_Id>int</Schedule_Id>
          <Entreprenuer_Slno>int</Entreprenuer_Slno>
          <Entreprenuer_Id>string</Entreprenuer_Id>
          <Mobile_No>string</Mobile_No>
          <Email_Id>string</Email_Id>
          <Sector_Name>string</Sector_Name>
          <Allocated_Fees>int</Allocated_Fees>
          <Collected_Fees>int</Collected_Fees>
          <Discount_Amount>int</Discount_Amount>
          <Discount_Remark>string</Discount_Remark>
          <Balance>int</Balance>
          <Stall_No>int</Stall_No>
          <Name>string</Name>
          <UserName>string</UserName>
          <Payment_Status>int</Payment_Status>
          <Submit_Count>int</Submit_Count>
          <Verified_Count>int</Verified_Count>
          <Status>string</Status>
             */
            if(result != null) {

                // SoapObject result = (SoapObject) result.getProperty("vmEvent_Schedule_Feedback_Attendance");
                //    for(int i=0; i<result.getPropertyCount();i++) {
                for (int j = 0; j < result.getPropertyCount(); j++) {
                    SoapPrimitive S_Slno,S_Schedule_Id,S_Entreprenuer_Slno,S_Entreprenuer_Id,S_Name,S_Mobile_No,S_Email_Id,S_Attendance,S_Sector_Name,S_Allocated_Fees,S_Collected_Fees,S_Discount_Amount,S_Discount_Remark,S_Balance,S_Stall_No,S_UserName,S_Payment_Status,S_Submit_Count,S_Verified_Count, S_Status,S_Discount_Date;
                    Object O_Slno,O_Schedule_Id,O_Entreprenuer_Slno,O_Entreprenuer_Id,O_Name,O_Mobile_No,O_Email_Id,O_Attendance,O_Sector_Name,O_Allocated_Fees,O_Collected_Fees,O_Discount_Amount,O_Discount_Remark,O_Balance,O_Stall_No,O_UserName,O_Payment_Status,O_Submit_Count,O_Verified_Count, O_Status,O_Discount_Date;
                    String str_Slno = null, str_Status = null,str_Entreprenuer_Id=null,str_Schedule_Id=null,str_Entreprenuer_Slno=null,str_Sector_Name=null,str_Name=null,str_Mobile_No=null,str_Email_Id=null,str_Allocated_Fees=null,str_Attendance=null,str_Collected_Fees=null,str_Discount_Amount=null,str_Discount_Remark=null,str_Balance=null,str_Stall_No=null,str_UserName=null,str_Payment_Status=null,str_Submit_Count=null,str_Verified_Count=null,str_Application_Slno=null,str_First_Name,str_FeedBack,str_Discount_Date=null;

                    SoapObject materialList = (SoapObject) result.getProperty(j);
                    Log.e(" materialList=", materialList.toString());
                    //SoapObject vmPermanent_Employee = (SoapObject) materialList.getProperty(j);
                    String status = materialList.getProperty("Status").toString();
                    if (status.equalsIgnoreCase("Success")) {

                        O_Slno = materialList.getProperty("Slno");
                        if (!O_Slno.toString().equals("anyType{}") && !O_Slno.toString().equals(null)) {
                            S_Slno = (SoapPrimitive) materialList.getProperty("Slno");
                            Log.d("Slno new=", S_Slno.toString());
                            str_Slno = S_Slno.toString();
                        }
                        O_Schedule_Id = materialList.getProperty("Schedule_Id");
                        if (!O_Schedule_Id.toString().equals("anyType{}") && !O_Schedule_Id.toString().equals(null)) {
                            S_Schedule_Id = (SoapPrimitive) materialList.getProperty("Schedule_Id");
                            Log.d("Schedule_Id new=", S_Schedule_Id.toString());
                            str_Schedule_Id = S_Schedule_Id.toString();
                        }
                        O_Entreprenuer_Slno = materialList.getProperty("Entreprenuer_Slno");
                        if (!O_Entreprenuer_Slno.toString().equals("anyType{}") && !O_Entreprenuer_Slno.toString().equals(null)) {
                            S_Entreprenuer_Slno = (SoapPrimitive) materialList.getProperty("Entreprenuer_Slno");
                            Log.d("Entreprenuer_Slno new=", S_Entreprenuer_Slno.toString());
                            str_Entreprenuer_Slno = S_Entreprenuer_Slno.toString();
                        }
                        O_Entreprenuer_Id = materialList.getProperty("Entreprenuer_Id");
                        if (!O_Entreprenuer_Id.toString().equals("anyType{}") && !O_Entreprenuer_Id.toString().equals(null)) {
                            S_Entreprenuer_Id = (SoapPrimitive) materialList.getProperty("Entreprenuer_Id");
                            Log.d("Entreprenuer_Id new=", S_Entreprenuer_Id.toString());
                            str_Entreprenuer_Id = S_Entreprenuer_Id.toString();
                        }
                        O_Name = materialList.getProperty("Name");
                        if (!O_Name.toString().equals("anyType{}") && !O_Name.toString().equals(null)) {
                            S_Name = (SoapPrimitive) materialList.getProperty("Name");
                            Log.d("Name new=", S_Name.toString());
                            str_Name = S_Name.toString();
                        }
                        O_Mobile_No = materialList.getProperty("Mobile_No");
                        if (!O_Mobile_No.toString().equals("anyType{}") && !O_Mobile_No.toString().equals(null)) {
                            S_Mobile_No = (SoapPrimitive) materialList.getProperty("Mobile_No");
                            Log.d("Mobile_No new=", S_Mobile_No.toString());
                            str_Mobile_No = S_Mobile_No.toString();
                        }
                        O_Sector_Name = materialList.getProperty("Sector_Name");
                        if (!O_Sector_Name.toString().equals("anyType{}") && !O_Sector_Name.toString().equals(null)) {
                            S_Sector_Name = (SoapPrimitive) materialList.getProperty("Sector_Name");
                            Log.d("Sector_Name new=", S_Sector_Name.toString());
                            str_Sector_Name = S_Sector_Name.toString();
                        }
                        O_Email_Id = materialList.getProperty("Email_Id");
                        if (!O_Email_Id.toString().equals("anyType{}") && !O_Email_Id.toString().equals(null)) {
                            S_Email_Id = (SoapPrimitive) materialList.getProperty("Email_Id");
                            Log.d("Email_Id new=", S_Email_Id.toString());
                            str_Email_Id = S_Email_Id.toString();
                        }
                        O_Allocated_Fees = materialList.getProperty("Allocated_Fees");
                        if (!O_Allocated_Fees.toString().equals("anyType{}") && !O_Allocated_Fees.toString().equals(null)) {
                            S_Allocated_Fees = (SoapPrimitive) materialList.getProperty("Allocated_Fees");
                            Log.d("Allocated_Fees new=", S_Allocated_Fees.toString());
                            str_Allocated_Fees = S_Allocated_Fees.toString();
                        }
                        O_Collected_Fees = materialList.getProperty("Collected_Fees");
                        if (!O_Collected_Fees.toString().equals("anyType{}") && !O_Collected_Fees.toString().equals(null)) {
                            S_Collected_Fees = (SoapPrimitive) materialList.getProperty("Collected_Fees");
                            Log.d("Collected_Fees new=", S_Collected_Fees.toString());
                            str_Collected_Fees = S_Collected_Fees.toString();
                        }
                        O_Discount_Amount = materialList.getProperty("Discount_Amount");
                        if (!O_Discount_Amount.toString().equals("anyType{}") && !O_Discount_Amount.toString().equals(null)) {
                            S_Discount_Amount = (SoapPrimitive) materialList.getProperty("Discount_Amount");
                            Log.d("Discount_Amount new=", S_Discount_Amount.toString());
                            str_Discount_Amount = S_Discount_Amount.toString();
                        }
                        O_Discount_Remark = materialList.getProperty("Discount_Remark");
                        if (!O_Discount_Remark.toString().equals("anyType{}") && !O_Discount_Remark.toString().equals(null)) {
                            S_Discount_Remark = (SoapPrimitive) materialList.getProperty("Discount_Remark");
                            Log.d("Discount_Remark new=", S_Discount_Remark.toString());
                            str_Discount_Remark = S_Discount_Remark.toString();
                        }
                        O_Discount_Date = materialList.getProperty("Discount_Date");
                        if (!O_Discount_Date.toString().equals("anyType{}") && !O_Discount_Date.toString().equals(null)) {
                            S_Discount_Date = (SoapPrimitive) materialList.getProperty("Discount_Date");
                            Log.d("Discount_Date new=", S_Discount_Date.toString());
                            str_Discount_Date= S_Discount_Date.toString();
                        }
                        O_Balance = materialList.getProperty("Balance");
                        if (!O_Balance.toString().equals("anyType{}") && !O_Balance.toString().equals(null)) {
                            S_Balance = (SoapPrimitive) materialList.getProperty("Balance");
                            Log.d("Balance new=", S_Balance.toString());
                            str_Balance = S_Balance.toString();
                        }
                        O_Stall_No = materialList.getProperty("Stall_No");
                        if (!O_Stall_No.toString().equals("anyType{}") && !O_Stall_No.toString().equals(null)) {
                            S_Stall_No = (SoapPrimitive) materialList.getProperty("Stall_No");
                            Log.d("Stall_No new=", S_Stall_No.toString());
                            str_Stall_No = S_Stall_No.toString();
                        }
                        O_UserName = materialList.getProperty("UserName");
                        if (!O_UserName.toString().equals("anyType{}") && !O_UserName.toString().equals(null)) {
                            S_UserName = (SoapPrimitive) materialList.getProperty("UserName");
                            Log.d("UserName new=", S_UserName.toString());
                            str_UserName = S_UserName.toString();
                        }
                        O_Payment_Status = materialList.getProperty("Payment_Status");
                        if (!O_Payment_Status.toString().equals("anyType{}") && !O_Payment_Status.toString().equals(null)) {
                            S_Payment_Status = (SoapPrimitive) materialList.getProperty("Payment_Status");
                            Log.d("Payment_Status new=", S_Payment_Status.toString());
                            str_Payment_Status = S_Payment_Status.toString();
                        }
                        O_Submit_Count = materialList.getProperty("Submit_Count");
                        if (!O_Submit_Count.toString().equals("anyType{}") && !O_Submit_Count.toString().equals(null)) {
                            S_Submit_Count = (SoapPrimitive) materialList.getProperty("Submit_Count");
                            Log.d("Submit_Count new=", S_Submit_Count.toString());
                            str_Submit_Count = S_Submit_Count.toString();
                        }
                        O_Verified_Count = materialList.getProperty("Verified_Count");
                        if (!O_Verified_Count.toString().equals("anyType{}") && !O_Verified_Count.toString().equals(null)) {
                            S_Verified_Count = (SoapPrimitive) materialList.getProperty("Verified_Count");
                            Log.d("Verified_Count new=", S_Verified_Count.toString());
                            str_Verified_Count = S_Verified_Count.toString();
                        }

                        feesList.clear();
                        FeesListModel.feesDetails_info_arr.clear();
                        FeesListModel item = null;
                        item = new FeesListModel(str_Slno,str_Schedule_Id,str_Entreprenuer_Slno,str_Entreprenuer_Id,str_Mobile_No,str_Email_Id,str_Sector_Name,str_Allocated_Fees,str_Collected_Fees,str_Discount_Amount,str_Discount_Remark,str_Discount_Date,str_Balance,str_Stall_No,str_Name,str_UserName,str_Payment_Status,str_Submit_Count,str_Verified_Count,userId,isAdmin,isInchage,isLocation,Event_Date,eventName);
                        feesList.add(item);

                        FeesListModel.feesDetails_info_arr.add(new FeesListModel(str_Slno,str_Schedule_Id,str_Entreprenuer_Slno,str_Entreprenuer_Id,str_Mobile_No,str_Email_Id,str_Sector_Name,str_Allocated_Fees,str_Collected_Fees,str_Discount_Amount,str_Discount_Remark,str_Discount_Date,str_Balance,str_Stall_No,str_Name,str_UserName,str_Payment_Status,str_Submit_Count,str_Verified_Count,userId,isAdmin,isInchage,isLocation,Event_Date,eventName) );
                        adapter.add(new FeesListModel(str_Slno,str_Schedule_Id,str_Entreprenuer_Slno,str_Entreprenuer_Id,str_Mobile_No,str_Email_Id,str_Sector_Name,str_Allocated_Fees,str_Collected_Fees,str_Discount_Amount,str_Discount_Remark,str_Discount_Date,str_Balance,str_Stall_No,str_Name,str_UserName,str_Payment_Status,str_Submit_Count,str_Verified_Count,userId,isAdmin,isInchage,isLocation,Event_Date,eventName));
                        allotedAmt_tv.setText(feesList.get(0).getAllocated_Fees());

                        lv_Feeslist.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                   else{
                        feesList.clear();
                        FeesListModel.feesDetails_info_arr.clear();
                        lv_Feeslist.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(FeesActivity.this,status, Toast.LENGTH_SHORT).show();

                    }

                }



                progressDialog.dismiss();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapObject getFeesListing() {
        String METHOD_NAME = "Get_Fees_List";
        String SOAP_ACTION1 = "http://mis.navodyami.org/Get_Fees_List";
        String NAMESPACE = "http://mis.navodyami.org/";

        try{
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            request.addProperty("User_Id",str_UserId);
            request.addProperty("Schedule_Id",scheduleId);
            request.addProperty("isIncharge",Boolean.valueOf(isInchage));
            request.addProperty("isLocation",Boolean.valueOf(isLocation));
            request.addProperty("isAdmin",Boolean.valueOf(isAdmin));

            Log.e("tag","soap request="+request.toString());
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
                Log.d("tag","soapresponse Fees List="+envelope.getResponse().toString());


                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("tag","soapresponse Fees details="+response.toString());

                //return null;

                return response;

            }
            catch(OutOfMemoryError ex){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(FeesActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());
                final String exceptionStr = t.getMessage().toString();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(FeesActivity.this,"Web Service Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        catch(OutOfMemoryError ex){
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(FeesActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());
            final String exceptionStr = t.getMessage().toString();
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(FeesActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;

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
            /*Date date = new Date();
            Log.i("Tag_time", "date1=" + date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String PresentDayStr = sdf.format(date);
            Log.i("Tag_time", "PresentDayStr=" + PresentDayStr);

            cal_adapter1.getPositionList(PresentDayStr, FeesActivity.this);*/
            Date date = new Date();
            Log.i("Tag_time", "date1=" + date);
            Log.i("Tag_time", "Event_Date=" + Event_Date);
            try {
                Date initDate = new SimpleDateFormat("dd-MM-yyyy").parse(Event_Date);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String PresentDayStr = sdf.format(initDate);
                Log.i("Tag_time", "PresentDayStr=" + PresentDayStr);
                cal_adapter1.getPositionList(PresentDayStr, FeesActivity.this);
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
        try {
            Date initDate = new SimpleDateFormat("dd-MM-yyyy").parse(Event_Date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String PresentDayStr = sdf.format(initDate);
            Log.i("Tag_time", "PresentDayStr=" + PresentDayStr);
            cal_adapter1.getPositionList(PresentDayStr, FeesActivity.this);
            finish();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
