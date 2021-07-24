package df.navodyami.FeesModule;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import df.navodyami.Class_InternetDectector;
import df.navodyami.Class_URL;
import df.navodyami.InputFilterMinMax;
import df.navodyami.MainActivity;
import df.navodyami.R;
import df.navodyami.SaveSharedPreference;

public class FeesDiscountActivity extends AppCompatActivity {

    Class_InternetDectector internetDectector;
    Boolean isInternetPresent = false;

    String Name,BalanceAmt,CollectAmt,CollectedAmt,DiscountAmt,Schedule_Id,Entreprenuer_Slno,Remark,DiscountDate,DiscountRemark,ReceiptNo,PaymentMode,ChoosenDate;
    Integer PaymentModeInt;
    static String status;

    String UserId,IsLocation,IsAdmin,Event_Date,Event_Name,isInchage;

    Button btn_submit;
    EditText edt_remark,edt_receiptNo,dateseterror_TV,edt_Date,edt_DiscountAmt;
    TextView collecteAmt_tv,balanceAmt_tv,discountAmt_tv,name_tv;
    Spinner paymentMode_sp;
    ArrayAdapter dataAdapter_paymentMode;

    private int mYear, mMonth, mDay;
    private int cYear, cMonth, cDay;

    SharedPreferences sharedpreferencebook_usercredential_Obj;
    SharedPreferences.Editor editor_obj;
    String str_UserId;
    public static final String sharedpreferencebook_usercredential = "sharedpreferencebook_usercredential";
    public static final String KeyValue_employeeid = "KeyValue_employeeid";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees_discount);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_n_actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView title= (TextView) toolbar.findViewById(R.id.title_name);
        getSupportActionBar().setTitle("");
        title.setText("Discount in Fees");

        sharedpreferencebook_usercredential_Obj=getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_UserId = sharedpreferencebook_usercredential_Obj.getString(KeyValue_employeeid, "").trim();


        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

        Intent intent = getIntent();
        if(getIntent().getExtras()!=null) {
            Name = intent.getStringExtra("Name");
            BalanceAmt = intent.getStringExtra("BalanceAmt");
            CollectedAmt = intent.getStringExtra("CollectAmt");
            DiscountAmt = intent.getStringExtra("DiscountAmt");
            Schedule_Id = intent.getStringExtra("Schedule_Id");
            Entreprenuer_Slno = intent.getStringExtra("Entreprenuer_Slno");
            DiscountRemark = intent.getStringExtra("DiscountRemark");
            DiscountDate = intent.getStringExtra("DiscountDate");

            isInchage = intent.getStringExtra("isInchage");
            IsAdmin = intent.getStringExtra("IsAdmin");
            IsLocation = intent.getStringExtra("IsLocation");
            UserId = intent.getStringExtra("UserId");
            Event_Date = intent.getStringExtra("Event_Date");
            Event_Name = intent.getStringExtra("Event_Name");
        }

       /* sharedpreferencebook_usercredential_Obj=getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_UserId = sharedpreferencebook_usercredential_Obj.getString(KeyValue_employeeid, "").trim();
*/
        name_tv = (TextView) findViewById(R.id.name_tv);
        discountAmt_tv =(TextView) findViewById(R.id.discountAmt_tv);
        collecteAmt_tv = (TextView) findViewById(R.id.collecteAmt_tv);
        balanceAmt_tv = (TextView) findViewById(R.id.balanceAmt_tv);
        edt_remark = (EditText) findViewById(R.id.edt_remark);
        dateseterror_TV = (EditText) findViewById(R.id.dateseterror_TV);
        edt_Date = (EditText) findViewById(R.id.edt_Date);
        edt_DiscountAmt = (EditText) findViewById(R.id.edt_DiscountAmt);
        btn_submit=(Button) findViewById(R.id.btn_submit);

        collecteAmt_tv.setText(CollectedAmt);
        discountAmt_tv.setText(DiscountAmt);
        balanceAmt_tv.setText(BalanceAmt);
        name_tv.setText(Name);
        edt_DiscountAmt.setFilters(new InputFilter[]{new InputFilterMinMax("1", BalanceAmt)});

        Log.e("tag","DiscountRemark="+DiscountRemark);
        if (!DiscountAmt.equalsIgnoreCase("0")){
            edt_DiscountAmt.setText(DiscountAmt);
        }
        if (DiscountRemark!=null){
            edt_remark.setText(DiscountRemark);
        }
        if(DiscountDate==null){
            Date date = new Date();
            Log.i("Tag_time", "date1=" + date);
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormatDisplay= new SimpleDateFormat("dd-MM-yyyy");
            try {
               // Date d=dateFormat.parse(date);
               // String PresentDayStr = dateFormat.format(date);
                System.out.println("Formated"+dateFormat.format(date));
                edt_Date.setText(dateFormatDisplay.format(date).toString());
                ChoosenDate=dateFormat.format(date);

            }
            catch(Exception e) {
                //java.text.ParseException: Unparseable date: Geting error
                System.out.println("Excep"+e);
            }

        }else{
            //Date date = new Date();

            Log.i("Tag_time", "date1=" + DiscountDate);
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormatDisplay= new SimpleDateFormat("dd-MM-yyyy");
            try {
               //  Date d=dateFormat.parse(DiscountDate);
                // String PresentDayStr = dateFormat.format(date);
                Date d = new SimpleDateFormat("dd-MM-yyyy").parse(DiscountDate);
                 System.out.println("Formated"+dateFormat.format(d));
                edt_Date.setText(dateFormatDisplay.format(d).toString());
                ChoosenDate=dateFormat.format(d);

            }
            catch(Exception e) {
                //java.text.ParseException: Unparseable date: Geting error
                System.out.println("Excep"+e);
            }
        }
        setPaymentDate();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Remark=edt_remark.getText().toString();
              //  ReceiptNo=edt_receiptNo.getText().toString();
                CollectAmt=edt_DiscountAmt.getText().toString();
              //  PaymentModeInt = paymentMode_sp.getSelectedItemPosition();
                if(validation()) {
                    if (isInternetPresent) {
                        SubmitFeesDiscount submitFeesDiscount = new SubmitFeesDiscount(FeesDiscountActivity.this);
                        submitFeesDiscount.execute();
                    } else {
                        //Toast.makeText(this,"There is no internet connection", Toast.LENGTH_SHORT).show();
                        Toast toast = Toast.makeText(FeesDiscountActivity.this, "  There is no internet connection  ", Toast.LENGTH_LONG);
                        View view = toast.getView();
                        view.setBackgroundColor(Color.parseColor("#DC143C")); //any color your want
                        toast.show();
                    }
                }
            }
        });
    }


    private void setPaymentDate() {

        final Calendar c = Calendar.getInstance();

        edt_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(FeesDiscountActivity.this, R.style.DatePickerTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                cDay = dayOfMonth;
                                cMonth = monthOfYear;
                                cYear = year;

                                // String date =dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                String date =year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;


                                // SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
                                SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
                                SimpleDateFormat dateFormatDisplay= new SimpleDateFormat("dd-MM-yyyy");
                                try {
                                    Date d=dateFormat.parse(date);
                                    System.out.println("Formated"+dateFormat.format(d));
                                    dateseterror_TV.setVisibility(View.GONE);
                                    edt_Date.setText(dateFormatDisplay.format(d).toString());
                                    ChoosenDate=dateFormat.format(d);

                                }
                                catch(Exception e) {
                                    //java.text.ParseException: Unparseable date: Geting error
                                    System.out.println("Excep"+e);
                                }
                                //TextView txtExactDate = (TextView) findViewById(R.id.txt_exactDate);


                                //txtDate.edita
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()-(1000 * 60 * 60 * 24 * 365 * 14));
                // - (1000 * 60 * 60 * 24 * 365.25 * 14)
//------

                datePickerDialog.show();
            }
        });


    }

    public boolean validation() {
        Boolean btruefalse=true;

        if (edt_Date.getText().toString().length() == 0 ||edt_Date.getText().toString().length()<=5)
        {
            dateseterror_TV.setVisibility(View.VISIBLE);
            dateseterror_TV.setError("Please Choose the discount Date!");
            btruefalse=false;
        }
        if( edt_DiscountAmt.getText().toString().length() == 0 ){
            edt_DiscountAmt.setError( "Amount is required!" );btruefalse=false;}


        if( edt_remark.getText().toString().length() == 0 ){
            edt_remark.setError( "Remark is required!" );btruefalse=false;}

        if(btruefalse) {
            return true;
        }else{return false;}
    }

    public class SubmitFeesDiscount extends AsyncTask<String, Void, Void>
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

        public SubmitFeesDiscount(Context activity) {
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
                Toast toast= Toast.makeText(context, "  Discount Added Successfully " , Toast.LENGTH_LONG);
                // Toast toast=   Toast.makeText(YOUR ACTIVITY NAME ,Toast.LENGTH_SHORT);
                View view =toast.getView();
                view.setBackgroundColor( Color.parseColor("#009900")); //any color your want
                toast.show();
                Log.e("tag","discount intent data= "+"Schedule_Id="+Schedule_Id+"str_UserId="+str_UserId+"Event_Name="+Event_Name);
                Log.e("tag","IsLocation="+IsLocation+"IsAdmin="+IsAdmin+"isInchage="+isInchage+"Event_Date="+Event_Date);
                Intent i = new Intent(context, FeesActivity.class);
                i.putExtra("scheduleId", Schedule_Id);
                i.putExtra("userId", str_UserId);
                i.putExtra("eventName", Event_Name);
                i.putExtra("isLocation", IsLocation);
                i.putExtra("isAdmin", IsAdmin);
                i.putExtra("isInchage",isInchage);
                i.putExtra("Event_Date",Event_Date);
                context.startActivity(i);
                finish();
            }
            else{
                Toast toast= Toast.makeText(context, "  Discount Failed  " , Toast.LENGTH_LONG);
                View view =toast.getView();
                view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
                toast.show();
            }
        }
    }

    public void Submit(){
        String URL = Class_URL.URL_Login.toString().trim();
        String METHOD_NAME = "Update_Discount_Details";
        String Namespace = "http://mis.navodyami.org/", SOAPACTION = "http://mis.navodyami.org/Update_Discount_Details";

        try {



            SoapObject request = new SoapObject(Namespace, METHOD_NAME);

       /* ------   API Data  ---------
         <User_Id>int</User_Id>
      <EP_Slno>int</EP_Slno>
      <Discount_Amount>int</Discount_Amount>
      <Discount_Date>string</Discount_Date>
      <Discount_Remark>string</Discount_Remark>*/

            request.addProperty("User_Id", str_UserId);
            request.addProperty("Applicant_Id", Integer.parseInt(Entreprenuer_Slno));
            request.addProperty("Discount_Amount", Integer.parseInt(CollectAmt));
            request.addProperty("Discount_Date", ChoosenDate);
            request.addProperty("Discount_Remark", Remark);

           //// request.addProperty("Device_Type","MOB");

            Log.e("tag","request discount=="+request.toString());

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
                Log.e("tag","response discount=="+response.toString());
                Log.e("tag","status =="+status);


            } catch (Throwable t) {

                Log.e("request fail", "> " + t.getMessage());

            }
        } catch (Throwable t) {

            Log.e("UnRegister Error", "> " + t.getMessage());
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
            super.onBackPressed();
            finish();
            return true;
        }
        // break;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
