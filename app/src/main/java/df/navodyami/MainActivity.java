package df.navodyami;

// Created by Madhu 02/11/2019

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.Vector;

import df.navodyami.R;

public class MainActivity extends AppCompatActivity
{


    public static final int RequestPermissionCode = 7;

    private static final int RC_SIGN_IN = 234;////a constant for detecting the login intent result
    private static final String TAG = "dffarmpond";
    GoogleSignInClient googlesigninclient_obj;
    FirebaseAuth firebaseauth_obj;
    SignInButton google_signin_bt;

    public final static String COLOR = "#1565C0";


    GoogleSignInAccount account;

    String str_googletokenid;


    EditText username_et;
    Button normallogin_bt;
    String str_gmailid;

    Context context_obj;


    public static final String sharedpreferencebook_usercredential = "sharedpreferencebook_usercredential";
    public static final String KeyValue_employeeid = "KeyValue_employeeid";
    public static final String KeyValue_employeename = "KeyValue_employeename";
    public static final String KeyValue_employee_mailid = "KeyValue_employee_mailid";
    public static final String KeyValue_employeemobileno = "KeyValue_employeemobileno";
 //   public static final String KeyValue_employeesandbox = "KeyValue_employeesandbox";
    public static final String Key_username = "name_googlelogin";
    public static final String key_userimage = "profileimg_googlelogin";

    SharedPreferences sharedpreferencebook_usercredential_Obj;
    SharedPreferences.Editor editor_obj;
    String str_token;
    String str_tokenfromprefrence;

  /*  public static final String sharedpreferenc_username = "googlelogin_name";
    public static final String Key_username = "name_googlelogin";
    SharedPreferences sharedpref_userimage_Obj;
    public static final String sharedpreferenc_userimage = "googlelogin_img";
    public static final String key_userimage = "profileimg_googlelogin";
    SharedPreferences sharedpref_username_Obj;*/

    String str_loginusername,str_profileimage;
    String str_UserId;

    String[] permissions= new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};
    public static final int MULTIPLE_PERMISSIONS = 10; // code you want.

    Class_InternetDectector internetDectector, internetDectector2;
    Boolean isInternetPresent = false;
    String str_gmail_ID,str_username,str_photoUrl, str_pwd, login_userStatus="", login_userid, login_userEmail="",login_Username,login_MobileNo;
    TelephonyManager tm1=null;
    String  myVersion, deviceBRAND, deviceHARDWARE, devicePRODUCT, deviceUSER, deviceModelName, deviceId, tmDevice, tmSerial, androidId, simOperatorName, sdkver, mobileNumber;
    int sdkVersion, Measuredwidth = 0, Measuredheight = 0, update_flage = 0;
    String regId ="navodyami",Str_FCMName;

    private String versioncode;
    String Str_status,o_versionresponse,o_versioncode;
    float soapprimitive_versionfloat,versionCodes;
    int soapprimitive_versionInteger,versionCodes_int;

    ////////////////////////////////////////
    AlertDialog alertdialog;
    String EnquiryIdoff,Enquiry_tempIdoff;
    String Application_SlnoNew,personalEnquiry_Id,personalApplicationSlno,personalTempId,AllDatatempId,AllDataApplicationSlno,AllDataEnquiry_Id;

    //----------------Sync Data----------
    String FName,LName,MName,MobileNo,EmailId,BusinessName,stateId,districtId,talukId,villegeId,sectorId,deviceType,userId,Enquiry_Id,Gender;
    String str_StateId,str_DistrictId,str_TalukaId,str_VillageId,str_SectorId;
    String Economic,whatsappNo,DOB,education, socialCatgary, businessAddress,yearInBusiness,yearInCurrentBusiness,sectorBusiness,gottoknow,UNDP,yearUNDP,navodyami,yearNavodyami,aadhar,Application_Date,street;
    Class_AddApplicationDetails[] arrayObj_Class_ApplicationDetails;
    Class_AddApplicationTwoDetails[] arrayObj_Class_ApplicationDetailsTwo;
    Class_AddApplicationThreeDetails[] arrayObj_Class_ApplicationDetailsThree;
    String manufacture, licence, whichLicence, productOne, productTwo, productThree, businessYear, ownership;
    String str_yearOne = null, female_year1, male_year1, str_yearTwo = null, female_year2, male_year2, str_yearThree=null, female_year3;
    String male_year3, labour, outsidefamilyLabour, trunover_year1, trunover_year2, trunover_year3, profit_year1;
    String profit_year2, profit_year3, machine, sell_products, last_quarter,enquiryId,total_Employee,Application_Slno,Which_Machine;
    ArrayList<String> listYears = new ArrayList<String>();
    ArrayList<String> listFemalePermentEmplyee = new ArrayList<String>();
    ArrayList<String> listMalePermentEmplyee = new ArrayList<String>();
    ArrayList<String> listTurnOver = new ArrayList<String>();
    ArrayList<String> listProfit = new ArrayList<String>();
    List<String> LastLoanList= new ArrayList<String>();
    //   String str_BusinessSource,int_knowledge="0",str_LastLoan,int_LastLoan,str_investment,str_AppliedAt,str_AppliedAmt,str_SanctionAmt,str_InterestRate,EnquiryId,str_Repaymentperiod;

    String int_Product_Improvement,int_Working_Expenses,int_Land,int_Equipment,int_Finance_Trading,str_verfiedDate,str_ApplAmtRemark,str_Manual_Receipt_No,str_ApplFees,str_Payment_Mode;
    String BusinessSource,LastLoan,Investment,Knowledge,AppliedAmt,SanctionedAmt,InterestRate,AppliedAt,Repaymentperiod,ApplicationFees,VerifiedDate;
    String Remark,Manual_Receipt_No,Payment_Mode,TempId_asyncTask;
    String id, status;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* sharedpref_username_Obj=getSharedPreferences(sharedpreferenc_username, Context.MODE_PRIVATE);
        str_loginusername = sharedpref_username_Obj.getString(Key_username, "").trim();
        sharedpref_userimage_Obj=getSharedPreferences(sharedpreferenc_userimage, Context.MODE_PRIVATE);
        str_profileimage = sharedpref_userimage_Obj.getString(key_userimage, "").trim();*/

        sharedpreferencebook_usercredential_Obj=this.getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_loginusername = sharedpreferencebook_usercredential_Obj.getString(Key_username, "").trim();
        str_profileimage = sharedpreferencebook_usercredential_Obj.getString(key_userimage, "").trim();
        str_UserId = sharedpreferencebook_usercredential_Obj.getString(KeyValue_employeeid, "").trim();

        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

        try {
            versionCodes = Float.parseFloat(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        try {
            versionCodes_int = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (isInternetPresent)
        {
            VersionCheckAsyncCallWS task = new VersionCheckAsyncCallWS(MainActivity.this);
            task.execute();
        }
        else {
            if (SaveSharedPreference.getUserName(MainActivity.this).length() == 0) {
                // call Login Activity
            } else {
                Intent i = new Intent(MainActivity.this, BottomActivity.class);
                i.putExtra("frgToLoad", "1");
                startActivity(i);
                finish();

                // Stay at the current activity.
            }
        }
        /////////////////////////////////////////


        normallogin_bt =(Button)findViewById(R.id.normallogin_bt);
        username_et =(EditText) findViewById(R.id.username_et);
        google_signin_bt =(SignInButton)findViewById(R.id.google_signin_bt);
        google_signin_bt.setColorScheme(SignInButton.COLOR_DARK);
        setGooglePlusButtonText(google_signin_bt,"  Sign in with DF mail  ");

        context_obj=this.getApplicationContext();

        //


        //Google Sign initializing
        firebaseauth_obj = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_ID_type3))
                .requestEmail()
                .build();
        googlesigninclient_obj = GoogleSignIn.getClient(this, gso);
        //Google Sign initializing

        if(CheckingPermissionIsEnabledOrNot())
        {
            // Toast.makeText(LoginActivity.this, "All Permissions Granted Successfully", Toast.LENGTH_LONG).show();
        }
        // If, If permission is not enabled then else condition will execute.
        else {

            //Calling method to enable permission.
            //Toast.makeText(LoginActivity.this, "Requesting", Toast.LENGTH_LONG).show();
            RequestMultiplePermission();

        }

        try {
            versioncode = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (checkPermissions())
        {
            //  permissions  granted.
        }

        // Signout function

        Intent myIntent = getIntent();

        if(myIntent!=null)
        {

            String logout="no";
            logout = myIntent.getStringExtra("Key_Logout");
            if(logout!=null && (logout.equalsIgnoreCase("yes")))
            {
                if(isInternetPresent){
                    offlineData("logout");
                }

            }
        }

        // Signout function


        google_signin_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                internetDectector = new Class_InternetDectector(getApplicationContext());
                isInternetPresent = internetDectector.isConnectingToInternet();
                if (isInternetPresent)
                {
                    google_sign();
                }else {
                    Toast.makeText(getApplicationContext()," Internet is not Connected ",Toast.LENGTH_SHORT).show();
                }

            }
        });

        //normal login comment while releasing apk

        normallogin_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                str_gmailid=username_et.getText().toString();
             //   AsyncTask_loginverify();
            }
        });

        //normal login comment while releasing apk

    }// end of onCreate()

    private void google_sign() {
        //getting the google signin intent
        Intent signInIntent = googlesigninclient_obj.getSignInIntent();
        //starting the activity for result
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        //if the requestCode is the Google Sign In code that we defined at starting
        if (requestCode == RC_SIGN_IN) {
            //Getting the GoogleSignIn Task
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //Google Sign In was successful, authenticate with Firebase
                account = task.getResult(ApiException.class);
                //authenticating with firebase
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                Toast.makeText(MainActivity.this, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct)
    {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        //getting the auth credential
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        str_gmailid=acct.getEmail().toString();

        //Now using firebase we are signing in the user here
        firebaseauth_obj.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseauth_obj.getCurrentUser();

                            str_gmail_ID=user.getEmail().toString();
                            str_username=acct.getDisplayName().toString();
                            str_photoUrl=acct.getPhotoUrl().toString();
                         //   googleApiClient.clearDefaultAccountAndReconnect();

                            if (isInternetPresent) {
                                AsyncCallWS_ValidateUser asyncCallWS_validateUser = new AsyncCallWS_ValidateUser(MainActivity.this);
                                asyncCallWS_validateUser.execute();
                                //Toast.makeText(getApplicationContext(),"net"+isInternetPresent.toString(),Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"No Internet Connected",Toast.LENGTH_SHORT).show();
                            }



                            Toast.makeText(MainActivity.this, "User Signed In:"+str_gmailid, Toast.LENGTH_SHORT).show();

                           // AsyncTask_loginverify();
                         /*try {
                                postRequest();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }*/

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private class VersionCheckAsyncCallWS extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;

        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");
            /*dialog.setMessage("Please wait,State Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
*/
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //Log.i(TAG, "onProgressUpdate---tab2");
        }


        @Override
        protected Void doInBackground(String... params) {
            Log.i("df", "doInBackground");

            GetAppReleasedDetails();  // get the App Release Details
            return null;
        }

        public VersionCheckAsyncCallWS(MainActivity activity) {
            context = activity;
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPostExecute(Void result) {

       /* if ((this.dialog != null) && this.dialog.isShowing()) {
            dialog.dismiss();

        }*/
            dialog.dismiss();

            //if(result!=null) {

            if (Str_status.equalsIgnoreCase("Success")) {

                if (soapprimitive_versionInteger == 555) {
                    alerts_dialogMaintenance();
                } else {


                    if (versionCodes_int >= soapprimitive_versionInteger) //9>=10
                    {
                       if (SaveSharedPreference.getUserName(MainActivity.this).length() == 0) {
                            // call Login Activity
                       } else {
                            Intent i = new Intent(MainActivity.this, BottomActivity.class);
                            i.putExtra("frgToLoad", "1");
                            startActivity(i);
                            finish();

                            // Stay at the current activity.
                       }
                    } else {
                        //alerts();

                        if(isInternetPresent){
                            offlineData("version");
                        }

                    }


                }
            }else{
                Toast toast= Toast.makeText(MainActivity.this, "  Webservice Error"+Str_status+"  " , Toast.LENGTH_LONG);
                View view =toast.getView();
                view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
                toast.show();
            }

            //}

        }

        public void GetAppReleasedDetails()
        {
            String URL = Class_URL.URL_Login.toString().trim();
            String METHOD_NAME = "GetAppReleasedDetails";
            String Namespace = "http://mis.navodyami.org/", SOAPACTION = "http://mis.navodyami.org/GetAppReleasedDetails";

            try {

                SoapObject request = new SoapObject(Namespace, METHOD_NAME);
                // request.addProperty("stateid", "0");//<stateid>long</stateid>

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                //Set output SOAP object
                envelope.setOutputSoapObject(request);
                //Create HTTP call object
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);


                /*<AppVersion>string</AppVersion>
        <versionCode>string</versionCode>
        <Status>string</Status>*/

                try {

                    androidHttpTransport.call(SOAPACTION, envelope);
                    //  Log.i(TAG, "GetAllLoginDetails is running");

                    SoapObject response = (SoapObject) envelope.getResponse();
                    Log.e("response",response.toString());

                    o_versionresponse = response.getProperty("AppVersion").toString();
                    o_versioncode = response.getProperty("versionCode").toString();
                    //   str_misscallno=response.getProperty("MissCallNumber").toString();
                    Str_status = response.getProperty("Status").toString(); //Success

                    soapprimitive_versionfloat = Float.parseFloat(o_versionresponse);
                    soapprimitive_versionInteger = Integer.parseInt(o_versioncode);


                    /*editor_generalbook=shardprefgeneralbook_obj.edit();
                    editor_generalbook.putString(PREFBook_generalbook, str_misscallno);
                    editor_generalbook.commit();*/

//AppVersion
                    Log.e("version", o_versioncode.toString());


                } catch (Throwable t) {
                    //Toast.makeText(context, "Request failed: " + t.toString(),
                    //    Toast.LENGTH_LONG).show();
                    Log.e("request fail", "> " + t.getMessage());
                    //internet_issue = "slow internet";
                }
            } catch (Throwable t) {
                //Toast.makeText(context, "UnRegister Receiver Error " + t.toString(),
                //    Toast.LENGTH_LONG).show();
                Log.e("UnRegister Error", "> " + t.getMessage());
            }


        }
    }

    //-------------------------------------- Enquiry And Application data sync ---------------------------------------------------------------------------------------------
    public  void offlineData(String where){
        if(isInternetPresent) {
            int EnquiryOfflineCount = Add_Edit_offlineEnquiryCount();
            int ApplicationOfflineCount = getOfflineAddOREditApplicationCount();

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(" Please wait syncing offline data ");
         //   builder.setMessage(" ");
            alertdialog = builder.create();
           if(alertdialog.isShowing()){
               alertdialog.dismiss();
           }
            if (EnquiryOfflineCount != 0) {
                if (alertdialog.isShowing()) {
                    getEnquiryOfflineDataSync();
                }else{
                    alertdialog.show();
                    alertdialog.setCancelable(false);
                    getEnquiryOfflineDataSync();
                }
                //   SyncData(EnquiryOfflineCount, ApplicationOfflineCount);
            }
            if(ApplicationOfflineCount != 0){
                if (alertdialog.isShowing()) {
                    getOfflineAddOREditApplication();
                }else{
                    alertdialog.show();
                    alertdialog.setCancelable(false);
                    getOfflineAddOREditApplication();
                }
            }
            if (EnquiryOfflineCount == 0 || ApplicationOfflineCount == 0) {
                // dialogoffline.dismiss();
                if (alertdialog.isShowing()) {
                    alertdialog.dismiss();
                    Toast toast = Toast.makeText(getApplicationContext(), " Successfully data synced ", Toast.LENGTH_SHORT);
                    View view = toast.getView();
                    view.setBackgroundColor(Color.parseColor("#009900")); //any color your want
                    toast.show();
                }
                if(where.equalsIgnoreCase("logout")){
                    signOut();
                }else if(where.equalsIgnoreCase("version")){
                    alerts_dialog();
                    alertdialog.dismiss();
                }

            }
        }
    }

    public int Add_Edit_offlineEnquiryCount() {

        SQLiteDatabase db2 = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS ListEnquiryDetails(tempId INTEGER PRIMARY KEY AUTOINCREMENT,FName VARCHAR,MobileNo VARCHAR,EmailId VARCHAR,BusinessName VARCHAR,EnquiryId VARCHAR,Status VARCHAR,YearCode VARCHAR,MName VARCHAR,LName VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,isApplicant VARCHAR,Gender VARCHAR,dataSyncStatus VARCHAR );");

        Cursor cursor1 = db2.rawQuery("SELECT DISTINCT * FROM ListEnquiryDetails WHERE dataSyncStatus='" +"offline"+"'", null);
        int EnquiryofflineCount = cursor1.getCount();
        Log.d("tag", "cursor Enquiry offline Count" + Integer.toString(EnquiryofflineCount));
        return EnquiryofflineCount;
    }
    public int getOfflineAddOREditApplicationCount() {
        SQLiteDatabase db1 = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS CompliteApplicationDetails(tempId INTEGER PRIMARY KEY AUTOINCREMENT,EnquiryId VARCHAR,Application_Slno VARCHAR,Academic_Year VARCHAR,dataSyncStatus VARCHAR,FName VARCHAR,MName VARCHAR,LName VARCHAR,MobileNo VARCHAR," +
                "EmailId VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,BusinessName VARCHAR," +
                "DeviceType VARCHAR,UserId VARCHAR,Gender VARCHAR,Economic VARCHAR,whatsappNo VARCHAR,DOB VARCHAR,education VARCHAR, " +
                "socialCatgary VARCHAR, businessAddress VARCHAR,yearInBusiness VARCHAR,yearInCurrentBusiness VARCHAR,sectorBusiness VARCHAR," +
                "gottoknow VARCHAR,UNDP VARCHAR,yearUNDP VARCHAR,navodyami VARCHAR,yearNavodyami VARCHAR,aadhar VARCHAR,street VARCHAR,Application_Date VARCHAR," +
                "Manufacture VARCHAR,Licence VARCHAR,WhichLicence VARCHAR,ProductOne VARCHAR," +
                "ProductTwo VARCHAR,ProductThree VARCHAR,BusinessYear VARCHAR,Ownership VARCHAR,YearOne VARCHAR,Female_year1 VARCHAR,Male_year1 VARCHAR," +
                "YearTwo VARCHAR,Female_year2 VARCHAR,Male_year2 VARCHAR,YearThree VARCHAR,Female_year3 VARCHAR,Male_year3 VARCHAR,Labour VARCHAR,OutsidefamilyLabour VARCHAR," +
                "Trunover_year1 VARCHAR,Trunover_year2 VARCHAR,Trunover_year3 VARCHAR,Profit_year1 VARCHAR,Profit_year2 VARCHAR,Profit_year3 VARCHAR,Which_Machine VARCHAR,Machine VARCHAR," +
                "Sell_products VARCHAR,Last_quarter VARCHAR,Total_Employee VARCHAR," +
                "BusinessSource VARCHAR,LastLoan VARCHAR,Investment VARCHAR,Knowledge VARCHAR,AppliedAmt VARCHAR,SanctionedAmt VARCHAR,InterestRate VARCHAR," +
                "AppliedAt VARCHAR,Repaymentperiod VARCHAR,ApplicationFees VARCHAR,VerifiedDate VARCHAR,Remark VARCHAR,Manual_Receipt_No VARCHAR,Payment_Mode VARCHAR,IsAccountVerified VARCHAR);");

        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM CompliteApplicationDetails WHERE dataSyncStatus='" + "offline" + "'", null);
        int ApplicationOfflineCount = cursor1.getCount();
        Log.d("tag", "cursor offline Application list Count" + Integer.toString(ApplicationOfflineCount));
        return ApplicationOfflineCount;

    }
    public void getOfflineAddOREditApplication(){
        SQLiteDatabase db1 = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS CompliteApplicationDetails(tempId INTEGER PRIMARY KEY AUTOINCREMENT,EnquiryId VARCHAR,Application_Slno VARCHAR,Academic_Year VARCHAR,dataSyncStatus VARCHAR,FName VARCHAR,MName VARCHAR,LName VARCHAR,MobileNo VARCHAR," +
                "EmailId VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,BusinessName VARCHAR," +
                "DeviceType VARCHAR,UserId VARCHAR,Gender VARCHAR,Economic VARCHAR,whatsappNo VARCHAR,DOB VARCHAR,education VARCHAR, " +
                "socialCatgary VARCHAR, businessAddress VARCHAR,yearInBusiness VARCHAR,yearInCurrentBusiness VARCHAR,sectorBusiness VARCHAR," +
                "gottoknow VARCHAR,UNDP VARCHAR,yearUNDP VARCHAR,navodyami VARCHAR,yearNavodyami VARCHAR,aadhar VARCHAR,street VARCHAR,Application_Date VARCHAR," +
                "Manufacture VARCHAR,Licence VARCHAR,WhichLicence VARCHAR,ProductOne VARCHAR," +
                "ProductTwo VARCHAR,ProductThree VARCHAR,BusinessYear VARCHAR,Ownership VARCHAR,YearOne VARCHAR,Female_year1 VARCHAR,Male_year1 VARCHAR," +
                "YearTwo VARCHAR,Female_year2 VARCHAR,Male_year2 VARCHAR,YearThree VARCHAR,Female_year3 VARCHAR,Male_year3 VARCHAR,Labour VARCHAR,OutsidefamilyLabour VARCHAR," +
                "Trunover_year1 VARCHAR,Trunover_year2 VARCHAR,Trunover_year3 VARCHAR,Profit_year1 VARCHAR,Profit_year2 VARCHAR,Profit_year3 VARCHAR,Which_Machine VARCHAR,Machine VARCHAR," +
                "Sell_products VARCHAR,Last_quarter VARCHAR,Total_Employee VARCHAR," +
                "BusinessSource VARCHAR,LastLoan VARCHAR,Investment VARCHAR,Knowledge VARCHAR,AppliedAmt VARCHAR,SanctionedAmt VARCHAR,InterestRate VARCHAR," +
                "AppliedAt VARCHAR,Repaymentperiod VARCHAR,ApplicationFees VARCHAR,VerifiedDate VARCHAR,Remark VARCHAR,Manual_Receipt_No VARCHAR,Payment_Mode VARCHAR,IsAccountVerified VARCHAR);");

        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM CompliteApplicationDetails WHERE dataSyncStatus='" + "offline" + "'", null);
        int x = cursor1.getCount();
        Log.d("tag","cursor offline Application list Count"+ Integer.toString(x));
        if(x>0) {
            if (isInternetPresent) {
                //  if (cursor1.moveToFirst()) {

                //   do {
                cursor1.moveToFirst();
                String Enquiry_Id = cursor1.getString(cursor1.getColumnIndex("EnquiryId"));
                String Application_Slno = cursor1.getString(cursor1.getColumnIndex("Application_Slno"));
                String tempId = cursor1.getString(cursor1.getColumnIndex("tempId"));

                if (Enquiry_Id.equalsIgnoreCase("0") && Application_Slno.equalsIgnoreCase("0")) {
                         /*   GetDataFromDB_AddApplicationDetails(Enquiry_Id, Application_Slno);
                            GetDatafromDB_AddApplicationTwoDetails(Enquiry_Id, Application_Slno);
                            getDatefromDB_AddApplicationThreeDetails(Enquiry_Id, Application_Slno);*/

                    AllDataEnquiry_Id=Enquiry_Id;
                    AllDataApplicationSlno=Application_Slno;
                    AllDatatempId=tempId;

                    GetDataFromDB_CompliteApplicationDetails(AllDataEnquiry_Id, AllDataApplicationSlno,AllDatatempId);
                    AddNewApplicationDetails addNewApplicationDetails = new AddNewApplicationDetails(getApplicationContext());
                    addNewApplicationDetails.execute();
                } else {
                    //if (!Enquiry_Id.equalsIgnoreCase("0") && Application_Slno.equalsIgnoreCase("0")) {

                    personalEnquiry_Id = Enquiry_Id;
                    personalApplicationSlno = Application_Slno;
                    personalTempId=tempId;
                    // GetDataFromDB_AddApplicationDetails(personalEnquiry_Id, personalApplicationSlno);
                    GetDataFromDB_CompliteApplicationDetails(personalEnquiry_Id, personalApplicationSlno,personalTempId);
                    UpdateApplicationDetails updateApplicationDetails = new UpdateApplicationDetails(getApplicationContext());
                    updateApplicationDetails.execute();
                }
                //  }while (cursor1.moveToNext());
                // }
            }
        }
    }
    public void GetDataFromDB_CompliteApplicationDetails(String passEnquiry_Id,String passApplication_Slno,String passTempId){

        listYears.clear();
        listProfit.clear();
        listTurnOver.clear();
        listFemalePermentEmplyee.clear();
        listMalePermentEmplyee.clear();

        // LastLoan.clear();

        SQLiteDatabase db_addEnquiry = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);

        db_addEnquiry.execSQL("CREATE TABLE IF NOT EXISTS CompliteApplicationDetails(tempId INTEGER PRIMARY KEY AUTOINCREMENT,EnquiryId VARCHAR,Application_Slno VARCHAR,Academic_Year VARCHAR,dataSyncStatus VARCHAR,FName VARCHAR,MName VARCHAR,LName VARCHAR,MobileNo VARCHAR," +
                "EmailId VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,BusinessName VARCHAR," +
                "DeviceType VARCHAR,UserId VARCHAR,Gender VARCHAR,Economic VARCHAR,whatsappNo VARCHAR,DOB VARCHAR,education VARCHAR, " +
                "socialCatgary VARCHAR, businessAddress VARCHAR,yearInBusiness VARCHAR,yearInCurrentBusiness VARCHAR,sectorBusiness VARCHAR," +
                "gottoknow VARCHAR,UNDP VARCHAR,yearUNDP VARCHAR,navodyami VARCHAR,yearNavodyami VARCHAR,aadhar VARCHAR,street VARCHAR,Application_Date VARCHAR," +
                "Manufacture VARCHAR,Licence VARCHAR,WhichLicence VARCHAR,ProductOne VARCHAR," +
                "ProductTwo VARCHAR,ProductThree VARCHAR,BusinessYear VARCHAR,Ownership VARCHAR,YearOne VARCHAR,Female_year1 VARCHAR,Male_year1 VARCHAR," +
                "YearTwo VARCHAR,Female_year2 VARCHAR,Male_year2 VARCHAR,YearThree VARCHAR,Female_year3 VARCHAR,Male_year3 VARCHAR,Labour VARCHAR,OutsidefamilyLabour VARCHAR," +
                "Trunover_year1 VARCHAR,Trunover_year2 VARCHAR,Trunover_year3 VARCHAR,Profit_year1 VARCHAR,Profit_year2 VARCHAR,Profit_year3 VARCHAR,Which_Machine VARCHAR,Machine VARCHAR," +
                "Sell_products VARCHAR,Last_quarter VARCHAR,Total_Employee VARCHAR," +
                "BusinessSource VARCHAR,LastLoan VARCHAR,Investment VARCHAR,Knowledge VARCHAR,AppliedAmt VARCHAR,SanctionedAmt VARCHAR,InterestRate VARCHAR," +
                "AppliedAt VARCHAR,Repaymentperiod VARCHAR,ApplicationFees VARCHAR,VerifiedDate VARCHAR,Remark VARCHAR,Manual_Receipt_No VARCHAR,Payment_Mode VARCHAR,IsAccountVerified VARCHAR);");

        Cursor cursor = db_addEnquiry.rawQuery("SELECT DISTINCT * FROM CompliteApplicationDetails WHERE EnquiryId ='"+passEnquiry_Id+"' AND Application_Slno='"+passApplication_Slno +"' AND tempId='"+passTempId+"'", null);
        int x = cursor.getCount();
        Log.d("tag","cursor count compliteApplicationDetails="+ Integer.toString(x));

        int i = 0;
        arrayObj_Class_ApplicationDetails = new Class_AddApplicationDetails[x];
        cursor.moveToFirst();
     /*   if (cursor.moveToFirst()) {

            do {*/
        // Enquiry_Id = cursor.getString(cursor.getColumnIndex("EnquiryId"));

        FName = cursor.getString(cursor.getColumnIndex("FName"));
        MName = cursor.getString(cursor.getColumnIndex("MName"));
        LName = cursor.getString(cursor.getColumnIndex("LName"));
        MobileNo = cursor.getString(cursor.getColumnIndex("MobileNo"));
        EmailId = cursor.getString(cursor.getColumnIndex("EmailId"));
        str_StateId = cursor.getString(cursor.getColumnIndex("StateId"));
        str_DistrictId = cursor.getString(cursor.getColumnIndex("DistrictId"));
        str_TalukaId = cursor.getString(cursor.getColumnIndex("TalukaId"));
        str_VillageId = cursor.getString(cursor.getColumnIndex("VillageId"));
        str_SectorId = cursor.getString(cursor.getColumnIndex("SectorId"));
        BusinessName = cursor.getString(cursor.getColumnIndex("BusinessName"));
        deviceType = cursor.getString(cursor.getColumnIndex("DeviceType"));
        userId = cursor.getString(cursor.getColumnIndex("UserId"));
        Gender = cursor.getString(cursor.getColumnIndex("Gender"));
        Economic = cursor.getString(cursor.getColumnIndex("Economic"));
        whatsappNo = cursor.getString(cursor.getColumnIndex("whatsappNo"));
        DOB = cursor.getString(cursor.getColumnIndex("DOB"));
        education = cursor.getString(cursor.getColumnIndex("education"));
        socialCatgary = cursor.getString(cursor.getColumnIndex("socialCatgary"));
        businessAddress = cursor.getString(cursor.getColumnIndex("businessAddress"));
        yearInBusiness = cursor.getString(cursor.getColumnIndex("yearInBusiness"));
        yearInCurrentBusiness = cursor.getString(cursor.getColumnIndex("yearInCurrentBusiness"));
        sectorBusiness = cursor.getString(cursor.getColumnIndex("sectorBusiness"));
        gottoknow = cursor.getString(cursor.getColumnIndex("gottoknow"));
        UNDP = cursor.getString(cursor.getColumnIndex("UNDP"));
        yearUNDP = cursor.getString(cursor.getColumnIndex("yearUNDP"));
        navodyami = cursor.getString(cursor.getColumnIndex("navodyami"));
        yearNavodyami = cursor.getString(cursor.getColumnIndex("yearNavodyami"));
        aadhar = cursor.getString(cursor.getColumnIndex("aadhar"));
        street = cursor.getString(cursor.getColumnIndex("street"));
        Application_Date = cursor.getString(cursor.getColumnIndex("Application_Date"));

        manufacture= cursor.getString(cursor.getColumnIndex("Manufacture"));
        licence=cursor.getString(cursor.getColumnIndex("Licence"));
        whichLicence=cursor.getString(cursor.getColumnIndex("WhichLicence"));
        productOne=cursor.getString(cursor.getColumnIndex("ProductOne"));
        productTwo=cursor.getString(cursor.getColumnIndex("ProductTwo"));
        productThree=cursor.getString(cursor.getColumnIndex("ProductThree"));
        businessYear=cursor.getString(cursor.getColumnIndex("BusinessYear"));
        ownership=cursor.getString(cursor.getColumnIndex("Ownership"));
        str_yearOne=cursor.getString(cursor.getColumnIndex("YearOne"));
        female_year1=cursor.getString(cursor.getColumnIndex("Female_year1"));
        male_year1=cursor.getString(cursor.getColumnIndex("Male_year1"));
        str_yearTwo=cursor.getString(cursor.getColumnIndex("YearTwo"));
        female_year2=cursor.getString(cursor.getColumnIndex("Female_year2"));
        male_year2=cursor.getString(cursor.getColumnIndex("Male_year2"));
        str_yearThree=cursor.getString(cursor.getColumnIndex("YearThree"));
        male_year3=cursor.getString(cursor.getColumnIndex("Male_year3"));
        female_year3=cursor.getString(cursor.getColumnIndex("Female_year3"));
        labour=cursor.getString(cursor.getColumnIndex("Labour"));
        outsidefamilyLabour=cursor.getString(cursor.getColumnIndex("OutsidefamilyLabour"));
        trunover_year1=cursor.getString(cursor.getColumnIndex("Trunover_year1"));
        trunover_year2=cursor.getString(cursor.getColumnIndex("Trunover_year2"));
        trunover_year3=cursor.getString(cursor.getColumnIndex("Trunover_year3"));
        profit_year1=cursor.getString(cursor.getColumnIndex("Profit_year1"));
        profit_year2=cursor.getString(cursor.getColumnIndex("Profit_year2"));
        profit_year3=cursor.getString(cursor.getColumnIndex("Profit_year3"));
        Which_Machine = cursor.getString(cursor.getColumnIndex("Which_Machine"));
        machine=cursor.getString(cursor.getColumnIndex("Machine"));
        sell_products=cursor.getString(cursor.getColumnIndex("Sell_products"));
        Log.e("tag","comp Sell_products="+sell_products);
        if(sell_products==null||sell_products.equalsIgnoreCase("")){
            sell_products="";
        }
        last_quarter=cursor.getString(cursor.getColumnIndex("Last_quarter"));
        Log.e("tag","comp last_quarter="+last_quarter);
        if(last_quarter==null||last_quarter.equalsIgnoreCase("")){
            last_quarter="";
        }
        total_Employee=cursor.getString(cursor.getColumnIndex("Total_Employee"));

        BusinessSource= cursor.getString(cursor.getColumnIndex("BusinessSource"));
        LastLoan=cursor.getString(cursor.getColumnIndex("LastLoan"));
        if(LastLoan!=null) {
            LastLoanList = Arrays.asList(LastLoan.split(","));
            for (int i1 = 0; i1 < LastLoanList.size(); i1++) {
                if (LastLoanList.get(0).equalsIgnoreCase("1")) {
                    int_Product_Improvement="1";
                }else{
                    int_Product_Improvement="0";
                }
                if (LastLoanList.get(1).equalsIgnoreCase("1")) {
                    int_Working_Expenses="1";
                }else{
                    int_Working_Expenses="0";
                }
                if (LastLoanList.get(2).equalsIgnoreCase("1")) {
                    int_Land="1";
                }else{
                    int_Land="0";

                }
                if (LastLoanList.get(3).equalsIgnoreCase("1")) {
                    int_Finance_Trading="1";
                }else {
                    int_Finance_Trading="0";
                }
                if (LastLoanList.get(4).equalsIgnoreCase("1")) {
                    int_Equipment="1";
                }else{
                    int_Equipment="0";
                }
            }
        }
        Investment=cursor.getString(cursor.getColumnIndex("Investment"));
        Knowledge=cursor.getString(cursor.getColumnIndex("Knowledge"));
        AppliedAmt=cursor.getString(cursor.getColumnIndex("AppliedAmt"));
        SanctionedAmt=cursor.getString(cursor.getColumnIndex("SanctionedAmt"));
        InterestRate=cursor.getString(cursor.getColumnIndex("InterestRate"));
        AppliedAt=cursor.getString(cursor.getColumnIndex("AppliedAt"));
        Repaymentperiod=cursor.getString(cursor.getColumnIndex("Repaymentperiod"));
        //    ApplicationSlnoNew=cursor.getString(cursor.getColumnIndex("Application_Slno"));
        ApplicationFees=cursor.getString(cursor.getColumnIndex("ApplicationFees"));
        VerifiedDate=cursor.getString(cursor.getColumnIndex("VerifiedDate"));
        Remark=cursor.getString(cursor.getColumnIndex("Remark"));
        Manual_Receipt_No=cursor.getString(cursor.getColumnIndex("Manual_Receipt_No"));
        Payment_Mode=cursor.getString(cursor.getColumnIndex("Payment_Mode"));

        //  } while (cursor.moveToNext());


        listYears.add(str_yearOne);
        listYears.add(str_yearTwo);
        listYears.add(str_yearThree);

        listFemalePermentEmplyee.add(female_year1);
        listFemalePermentEmplyee.add(female_year2);
        listFemalePermentEmplyee.add(female_year3);

        listMalePermentEmplyee.add(male_year1);
        listMalePermentEmplyee.add(male_year2);
        listMalePermentEmplyee.add(male_year3);

        listTurnOver.add(trunover_year1);
        listTurnOver.add(trunover_year2);
        listTurnOver.add(trunover_year3);

        listProfit.add(profit_year1);
        listProfit.add(profit_year2);
        listProfit.add(profit_year3);
        TempId_asyncTask = cursor.getString(cursor.getColumnIndex("tempId"));
        // }
        cursor.close();
        db_addEnquiry.close();
    }
    private class AddNewApplicationDetails extends AsyncTask<String, Void, Void>
    {
        //  ProgressDialog dialog;

        Context context;

        //  public UpdateManagerDetails(GetManagerDetails getManagerDetails) {
        //  }

        @Override
        protected void onPreExecute()
        {
            Log.i("TAG", "onPreExecute---tab2");
          /*  dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();*/

        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            Log.i("TAG", "onProgressUpdate---tab2");
        }

        public AddNewApplicationDetails(Context activity) {
            context = activity;
            //   dialog = new ProgressDialog(context);
        }
        @Override
        protected Void doInBackground(String... params)
        {
            Log.i("TAG", "doInBackground");
            //SubmitAddNewApplication();
            String URL = Class_URL.URL_Login.toString().trim();
            String METHOD_NAME = "Save_Application_Details";
            String Namespace = "http://mis.navodyami.org/", SOAPACTION = "http://mis.navodyami.org/Save_Application_Details";

            try {

                SoapObject request = new SoapObject(Namespace, METHOD_NAME);
                request.addProperty("First_Name", FName);
                request.addProperty("Middle_Name", MName);
                request.addProperty("Last_Name", LName);
                request.addProperty("Mobile_No", MobileNo);
                request.addProperty("Email_Id", EmailId);
                request.addProperty("WhatApp_No", whatsappNo);
                request.addProperty("Aadhar_No", aadhar);
                request.addProperty("State_Id", Integer.valueOf(str_StateId));
                request.addProperty("District_Id", Integer.valueOf(str_DistrictId));
                request.addProperty("Taluka_Id",Integer.valueOf(str_TalukaId));
                request.addProperty("Village_Id",Integer.valueOf(str_VillageId));
                request.addProperty("Gender",Gender);
                request.addProperty("DOB",DOB);
                request.addProperty("Qualification_Id",Integer.valueOf(education));
                request.addProperty("Category_Id",Integer.valueOf(socialCatgary));
                request.addProperty("Economic_Status",Economic);
                request.addProperty("Sector_Id",Integer.valueOf(str_SectorId));
                request.addProperty("Business_Name",BusinessName);
                request.addProperty("Business_Address",businessAddress);
                request.addProperty("Years_In_Business",Integer.valueOf(yearInBusiness));
                request.addProperty("Years_In_Current_Business",Integer.valueOf(yearInCurrentBusiness));
                request.addProperty("Earlier_Sector",sectorBusiness);
                request.addProperty("Know_Navodyami",gottoknow);
                request.addProperty("isNavodyami_Member_Before",Integer.valueOf(navodyami));
                request.addProperty("Year_in_Navodyami",Integer.valueOf(yearNavodyami));
                request.addProperty("isUNDP_Member_Before",Integer.valueOf(UNDP));
                request.addProperty("Year_in_UNDP",Integer.valueOf(yearUNDP));


                //--------------------------------------------Business_Details------------------------------------------------------

                //  request.addProperty("Enquiry_Id",Integer.valueOf(EnquiryId));
                //  request.addProperty("Application_Slno", Integer.valueOf(ApplicationSlnoNew));
                request.addProperty("isManufacture", Integer.valueOf(manufacture));
                request.addProperty("isHave_License", Integer.valueOf(licence));
                request.addProperty("Which_License", whichLicence);
                request.addProperty("Product1", productOne);
                request.addProperty("Product2", productTwo);
                request.addProperty("Product3",productThree);
                if(businessYear.equalsIgnoreCase("")||businessYear==null){
                    businessYear="0";
                }
                request.addProperty("Business_Started_Year",Integer.valueOf(businessYear));
                request.addProperty("Ownership",ownership);
                request.addProperty("Which_Labour_Hire",Integer.valueOf(labour));
                request.addProperty("IsHave_Labour_Outside_Family",Integer.valueOf(outsidefamilyLabour));
                request.addProperty("Which_Machine_Use",Which_Machine);
                request.addProperty("Which_Machine_Have",Integer.valueOf(machine));
                request.addProperty("Where_Sell_Products",sell_products);
                request.addProperty("Earn_Most_Channel",last_quarter);
                //  request.addProperty("User_Id",Integer.valueOf(str_UserId));


                JSONArray array=new JSONArray();
                ArrayList<String> json_arrayList=new ArrayList<>();
                String str_json = null,str_jsonfinal;
                str_jsonfinal="[";
                for(int i=0;i<listYears.size();i++){
                    JSONObject obj=new JSONObject();
                    try {
                        obj.put("Year",listYears.get(i));
                        obj.put("TotalMale",listMalePermentEmplyee.get(i));
                        obj.put("TotalFemale",listFemalePermentEmplyee.get(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    array.put(obj);
                    if(str_json==null){
                        str_json =(obj.toString() + ",");
                    }else {
                        str_json = str_json + (obj.toString() + ",");
                    }
               /* if(i==2) {
                    str_json += obj.toString();
                }else{
                    str_json += obj.toString() + ",";
                }*/
                    //json_arrayList.add(str_json.trim);
                    // str_json += str_json+obj.toString()+",";
                }
                int lastCommaIndex = str_json.lastIndexOf(",");
                str_jsonfinal += str_json.substring(0, lastCommaIndex -0);

                // str_json.trim();
                // String str_jsonFinal=removeLastChar(str_json);
                str_jsonfinal+="]";


                Log.e("tag","PermentEmplyee json_arrayList=="+json_arrayList);
                Log.e("tag","PermentEmplyee array=="+array);
                Log.e("tag","PermentEmplyee str_jsonFinal=="+str_jsonfinal);
                request.addProperty("Permanent_Employees",str_jsonfinal);
                JSONObject jsonObjectTrunover = new JSONObject();
                for(int k=0;k<listYears.size() && k<listTurnOver.size();k++)
                {
                    jsonObjectTrunover.put(listYears.get(k),listTurnOver.get(k));
                }
                Log.e("tag","jsonObjectTrunover="+jsonObjectTrunover);
                request.addProperty("TurnOver",jsonObjectTrunover.toString());

                JSONObject jsonObjectProfit = new JSONObject();
                for(int k=0;k<listYears.size() && k<listProfit.size();k++)
                {
                    jsonObjectProfit.put(listYears.get(k),listProfit.get(k));
                }
                Log.e("tag","jsonObjectProfit="+jsonObjectProfit);
                request.addProperty("Profit",jsonObjectProfit.toString());
                request.addProperty("Total_Employees",Integer.valueOf(total_Employee));

                //--------------------------------------------Credit_Details------------------------------------------------------

                // request.addProperty("Application_Slno", Integer.parseInt(ApplicationSlnoNew));
                request.addProperty("Source_Of_Business", BusinessSource);
                request.addProperty("Initial_Investment", Investment);
                request.addProperty("isKnowledge_Loan_Process", Integer.parseInt(Knowledge));
                request.addProperty("Applied_Loan_Amount", Integer.parseInt(AppliedAmt));
                request.addProperty("Sanctioned_Loan_Amount", Integer.parseInt(SanctionedAmt));
                request.addProperty("Interest_Rate", InterestRate);
                request.addProperty("Repayment_Period",Repaymentperiod);
                request.addProperty("Applied_At",AppliedAt);
                request.addProperty("Loan_For_Product_Improvement",Integer.parseInt(int_Product_Improvement));
                request.addProperty("Loan_For_Working_Expenses",Integer.parseInt(int_Working_Expenses));
                request.addProperty("Loan_For_Land",Integer.parseInt(int_Land));
                request.addProperty("Loan_For_Equipment",Integer.parseInt(int_Equipment));
                request.addProperty("Loan_For_Finance_Trading",Integer.parseInt(int_Finance_Trading));
                request.addProperty("User_Id",str_UserId);
                Log.e("tag","str_ApplFees="+str_ApplFees);
                request.addProperty("Application_Fees",Integer.parseInt(ApplicationFees));
                request.addProperty("Receipt_Date",VerifiedDate);
                request.addProperty("Remark",Remark);
                request.addProperty("Manual_Receipt_No",Manual_Receipt_No);
                request.addProperty("Payment_Mode",Payment_Mode);
                request.addProperty("Application_Date",Application_Date);
                request.addProperty("Business_Street",street);

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
                    // SoapObject response = (SoapObject) envelope.getResponse();
                    //Log.e("tag","soap response Updateresponse"+ response.toString());

                    status = response.toString();
                    Log.e("tag","response new application =="+response.toString());
                    Log.e("tag","status =="+status);
                    if(status.equalsIgnoreCase("success")) {
                        Delete_DatafromDBTempID(TempId_asyncTask);
                    }


                } catch (Throwable t) {

                    Log.e("request fail", "> " + t.getMessage());

                }
            } catch (Throwable t) {

                Log.e("UnRegister Error", "> " + t.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            Log.i("TAG", "onPostExecute");

            Log.d("ServiceResponseisss: ",status.toString());

            //    dialog.dismiss();

            if(status.equalsIgnoreCase("success")) {
                offlineData("");
              /*  Toast toast= Toast.makeText(getApplicationContext(), "  Application Added Successfully " , Toast.LENGTH_LONG);
                // Toast toast=   Toast.makeText(YOUR ACTIVITY NAME ,Toast.LENGTH_SHORT);
                View view =toast.getView();
                view.setBackgroundColor( Color.parseColor("#009900")); //any color your want
                toast.show();
                Intent intent1 = new Intent(getApplicationContext(), BottomActivity.class);
                intent1.putExtra("frgToLoad", "0");
                startActivity(intent1);*/

                //offline_count.setVisibility(View.GONE);
                // Delete_DatafromDBTempID();
               /* AddApplicationTwoActivity addApplicationTwoActivity=new AddApplicationTwoActivity();
                addApplicationTwoActivity.clear_fields();*/
            }
            else{
                //  Toast toast= Toast.makeText(getContext(), "  Updated Failed  " , Toast.LENGTH_LONG);
                if (alertdialog.isShowing()) {
                    alertdialog.dismiss();
                }

                Toast toast= Toast.makeText(context, "  "+status.toString()+"  " , Toast.LENGTH_LONG);
                View view =toast.getView();
                view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
                toast.show();
            }
        }
    }

    public void Delete_DatafromDBTempID(String tempId){
        SQLiteDatabase db_addEnquiry = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);

        db_addEnquiry.execSQL("CREATE TABLE IF NOT EXISTS CompliteApplicationDetails(tempId INTEGER PRIMARY KEY AUTOINCREMENT,EnquiryId VARCHAR,Application_Slno VARCHAR,Academic_Year VARCHAR,dataSyncStatus VARCHAR,FName VARCHAR,MName VARCHAR,LName VARCHAR,MobileNo VARCHAR," +
                "EmailId VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,BusinessName VARCHAR," +
                "DeviceType VARCHAR,UserId VARCHAR,Gender VARCHAR,Economic VARCHAR,whatsappNo VARCHAR,DOB VARCHAR,education VARCHAR, " +
                "socialCatgary VARCHAR, businessAddress VARCHAR,yearInBusiness VARCHAR,yearInCurrentBusiness VARCHAR,sectorBusiness VARCHAR," +
                "gottoknow VARCHAR,UNDP VARCHAR,yearUNDP VARCHAR,navodyami VARCHAR,yearNavodyami VARCHAR,aadhar VARCHAR,street VARCHAR,Application_Date VARCHAR," +
                "Manufacture VARCHAR,Licence VARCHAR,WhichLicence VARCHAR,ProductOne VARCHAR," +
                "ProductTwo VARCHAR,ProductThree VARCHAR,BusinessYear VARCHAR,Ownership VARCHAR,YearOne VARCHAR,Female_year1 VARCHAR,Male_year1 VARCHAR," +
                "YearTwo VARCHAR,Female_year2 VARCHAR,Male_year2 VARCHAR,YearThree VARCHAR,Female_year3 VARCHAR,Male_year3 VARCHAR,Labour VARCHAR,OutsidefamilyLabour VARCHAR," +
                "Trunover_year1 VARCHAR,Trunover_year2 VARCHAR,Trunover_year3 VARCHAR,Profit_year1 VARCHAR,Profit_year2 VARCHAR,Profit_year3 VARCHAR,Which_Machine VARCHAR,Machine VARCHAR," +
                "Sell_products VARCHAR,Last_quarter VARCHAR,Total_Employee VARCHAR," +
                "BusinessSource VARCHAR,LastLoan VARCHAR,Investment VARCHAR,Knowledge VARCHAR,AppliedAmt VARCHAR,SanctionedAmt VARCHAR,InterestRate VARCHAR," +
                "AppliedAt VARCHAR,Repaymentperiod VARCHAR,ApplicationFees VARCHAR,VerifiedDate VARCHAR,Remark VARCHAR,Manual_Receipt_No VARCHAR,Payment_Mode VARCHAR,IsAccountVerified VARCHAR);");

        Cursor cursor = db_addEnquiry.rawQuery("SELECT DISTINCT * FROM CompliteApplicationDetails WHERE tempId='"+tempId+"'", null);
        int x = cursor.getCount();
        Log.d("tag","cursor delete count compliteApplicationDetails="+ Integer.toString(x));
        if (x > 0) {
            String where="tempId=?";
            //  int result = db_addEnquiry.rawQuery(" Delete from CompliteApplicationDetails where tempId = tempId", null);
            //  int numberOFEntriesDeleted= db_addEnquiry.delete(CompliteApplicationDetails, where, new String[]{tempId}) ;
            int numberOFEntriesDeleted=db_addEnquiry.delete("CompliteApplicationDetails", where, new String[]{tempId});
            Log.e("tag","numberOFEntriesDeleted="+numberOFEntriesDeleted);
        }
    }

    private class UpdateApplicationDetails extends AsyncTask<String, Void, Void>
    {
        //  ProgressDialog dialog;

        Context context;

        //  public UpdateManagerDetails(GetManagerDetails getManagerDetails) {
        //  }

        @Override
        protected void onPreExecute()
        {
            Log.i("TAG", "onPreExecute---tab2");
         /*   dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();*/

        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            Log.i("TAG", "onProgressUpdate---tab2");
        }

        public UpdateApplicationDetails(Context activity) {
            context = activity;
            //  dialog = new ProgressDialog(context);
        }
        @Override
        protected Void doInBackground(String... params)
        {
            Log.i("TAG", "doInBackground");
            //Update();// params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9] ,params[10],params[11],params[12]);
            String URL = Class_URL.URL_Login.toString().trim();
            String METHOD_NAME = "Save_Application_Personal_Details";
            String Namespace = "http://mis.navodyami.org/", SOAPACTION = "http://mis.navodyami.org/Save_Application_Personal_Details";

            try {



                SoapObject request = new SoapObject(Namespace, METHOD_NAME);
                request.addProperty("Enquiry_Id",Integer.valueOf(personalEnquiry_Id));
                request.addProperty("Application_Slno",Integer.valueOf(personalApplicationSlno));
                request.addProperty("First_Name", FName);
                request.addProperty("Middle_Name", MName);
                request.addProperty("Last_Name", LName);
                request.addProperty("Mobile_No", MobileNo);
                request.addProperty("Email_Id", EmailId);
                request.addProperty("WhatApp_No", whatsappNo);
                request.addProperty("Aadhar_No", aadhar);
                request.addProperty("State_Id", Integer.valueOf(str_StateId));
                request.addProperty("District_Id", Integer.valueOf(str_DistrictId));
                request.addProperty("Taluka_Id",Integer.valueOf(str_TalukaId));
                request.addProperty("Village_Id",Integer.valueOf(str_VillageId));
                request.addProperty("Gender",Gender);
                request.addProperty("DOB",DOB);
                request.addProperty("Qualification_Id",Integer.valueOf(education));
                request.addProperty("Category_Id",Integer.valueOf(socialCatgary));
                request.addProperty("Economic_Status",Economic);
                request.addProperty("Sector_Id",Integer.valueOf(str_SectorId));
                request.addProperty("Business_Name",BusinessName);
                request.addProperty("Business_Address",businessAddress);
                request.addProperty("Years_In_Business",Integer.valueOf(yearInBusiness));
                request.addProperty("Years_In_Current_Business",Integer.valueOf(yearInCurrentBusiness));
                request.addProperty("Earlier_Sector",sectorBusiness);
                request.addProperty("Know_Navodyami",gottoknow);
                request.addProperty("isNavodyami_Member_Before",Integer.valueOf(navodyami));
                request.addProperty("Year_in_Navodyami",Integer.valueOf(yearNavodyami));
                request.addProperty("isUNDP_Member_Before",Integer.valueOf(UNDP));
                request.addProperty("Year_in_UNDP",Integer.valueOf(yearUNDP));
                request.addProperty("User_Id",Integer.valueOf(str_UserId));
                request.addProperty("Application_Date",Application_Date);
                request.addProperty("Business_Street",street);

                Log.e("tag","request=="+request.toString());

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                //Set output SOAP object
                envelope.setOutputSoapObject(request);
                //Create HTTP call object
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

                try {

                    androidHttpTransport.call(SOAPACTION, envelope);

                    // SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                    SoapObject response = (SoapObject) envelope.getResponse();

                    //Log.e("tag","soap response Updateresponse"+ response.toString());
                    Object Application_Slno = response.getProperty("New_Application_Id");
                    Object Status = response.getProperty("Status");
                    Application_SlnoNew=Application_Slno.toString();
                    // status = response.toString();
                    status = Status.toString();
                    Log.e("tag","response application=="+response.toString());
                    Log.e("tag","status application =="+status+"Application_SlnoNew="+Application_SlnoNew);


                } catch (Throwable t) {

                    Log.e("request fail", "> " + t.getMessage());

                }
            } catch (Throwable t) {

                Log.e("UnRegister Error", "> " + t.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            Log.i("TAG", "onPostExecute");

            Log.d("tag","Application Responseisss: "+status.toString());

            //   dialog.dismiss();

            if(status.equalsIgnoreCase("success")) {
                Log.e("tag","Application_SlnoNew response1=="+Application_SlnoNew);
                Log.e("tag","personalApplicationSlno response=="+personalApplicationSlno);

                if(Application_SlnoNew.equalsIgnoreCase("0")){
                    Application_SlnoNew=personalApplicationSlno;

                }
                else {
                    //DBUpdate_ApplicationNo(Application_SlnoNew,EnquiryId);
                }
                Log.e("tag","Application_SlnoNew response2=="+Application_SlnoNew);
                //    Toast toast= Toast.makeText(getContext(), " Application Updated Successfully ",Toast.LENGTH_SHORT);
               /* Intent intent1  = new Intent (getContext(), AddApplicationTwoActivity.class);
                intent1.putExtra("Application_SlnoNew",Application_SlnoNew);
                intent1.putExtra("EnquiryId",EnquiryId);
                intent1.putExtra("isApplicant",isApplicant);
                startActivity(intent1);*/
                //   Update_ApplicationNo_AddApplicationTwoDetails(Application_SlnoNew);
                //    Update_ApplicationNo_AddApplicationThreeDetails(Application_SlnoNew);
                Update_ApplicationNo_CompliteApplicationDetails(Application_SlnoNew);
                personalApplicationSlno=Application_SlnoNew;
                //  GetDatafromDB_AddApplicationTwoDetails(personalEnquiry_Id, personalApplicationSlno);
                AddApplicationTwoDetails addApplicationTwoDetails = new AddApplicationTwoDetails(getApplicationContext());
                addApplicationTwoDetails.execute();
              /*  View view =toast.getView();
                view.setBackgroundColor( Color.parseColor("#009900")); //any color your want
                toast.show();*/
            }
            else{
                //  Toast.makeText(AddApplicationOneActivity.this, "Update Request Failed " , Toast.LENGTH_LONG).show();
                //  Toast toast= Toast.makeText(getContext(), "  Update Request Failed  " , Toast.LENGTH_LONG);
                if (alertdialog.isShowing()) {
                    alertdialog.dismiss();
                }
                Toast toast= Toast.makeText(context, "  "+status.toString()+"  " , Toast.LENGTH_LONG);
                View view =toast.getView();
                view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
                toast.show();
            }
        }
    }
    public void Update_ApplicationNo_CompliteApplicationDetails(String Application_SlnoNew){
        SQLiteDatabase db_addEnquiry = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);

        db_addEnquiry.execSQL("CREATE TABLE IF NOT EXISTS CompliteApplicationDetails(tempId INTEGER PRIMARY KEY AUTOINCREMENT,EnquiryId VARCHAR,Application_Slno VARCHAR,Academic_Year VARCHAR,dataSyncStatus VARCHAR,FName VARCHAR,MName VARCHAR,LName VARCHAR,MobileNo VARCHAR," +
                "EmailId VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,BusinessName VARCHAR," +
                "DeviceType VARCHAR,UserId VARCHAR,Gender VARCHAR,Economic VARCHAR,whatsappNo VARCHAR,DOB VARCHAR,education VARCHAR, " +
                "socialCatgary VARCHAR, businessAddress VARCHAR,yearInBusiness VARCHAR,yearInCurrentBusiness VARCHAR,sectorBusiness VARCHAR," +
                "gottoknow VARCHAR,UNDP VARCHAR,yearUNDP VARCHAR,navodyami VARCHAR,yearNavodyami VARCHAR,aadhar VARCHAR,street VARCHAR,Application_Date VARCHAR," +
                "Manufacture VARCHAR,Licence VARCHAR,WhichLicence VARCHAR,ProductOne VARCHAR," +
                "ProductTwo VARCHAR,ProductThree VARCHAR,BusinessYear VARCHAR,Ownership VARCHAR,YearOne VARCHAR,Female_year1 VARCHAR,Male_year1 VARCHAR," +
                "YearTwo VARCHAR,Female_year2 VARCHAR,Male_year2 VARCHAR,YearThree VARCHAR,Female_year3 VARCHAR,Male_year3 VARCHAR,Labour VARCHAR,OutsidefamilyLabour VARCHAR," +
                "Trunover_year1 VARCHAR,Trunover_year2 VARCHAR,Trunover_year3 VARCHAR,Profit_year1 VARCHAR,Profit_year2 VARCHAR,Profit_year3 VARCHAR,Which_Machine VARCHAR,Machine VARCHAR," +
                "Sell_products VARCHAR,Last_quarter VARCHAR,Total_Employee VARCHAR," +
                "BusinessSource VARCHAR,LastLoan VARCHAR,Investment VARCHAR,Knowledge VARCHAR,AppliedAmt VARCHAR,SanctionedAmt VARCHAR,InterestRate VARCHAR," +
                "AppliedAt VARCHAR,Repaymentperiod VARCHAR,ApplicationFees VARCHAR,VerifiedDate VARCHAR,Remark VARCHAR,Manual_Receipt_No VARCHAR,Payment_Mode VARCHAR,IsAccountVerified VARCHAR);");

        ContentValues cv = new ContentValues();

        cv.put("Application_Slno",Application_SlnoNew);
        cv.put("dataSyncStatus","online");
        db_addEnquiry.update("CompliteApplicationDetails", cv, "EnquiryId = ?", new String[]{personalEnquiry_Id});

    }
    private class AddApplicationTwoDetails extends AsyncTask<String, Void, Void>
    {
        //    ProgressDialog dialog;

        Context context;

        //  public UpdateManagerDetails(GetManagerDetails getManagerDetails) {
        //  }

        @Override
        protected void onPreExecute()
        {
            Log.i("TAG", "onPreExecute---tab2");
           /* dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();*/

        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            Log.i("TAG", "onProgressUpdate---tab2");
        }

        public AddApplicationTwoDetails(Context activity) {
            context = activity;
            //  dialog = new ProgressDialog(context);
        }
        @Override
        protected Void doInBackground(String... params)
        {
            Log.i("TAG", "doInBackground");
            //  SubmitBusiness();
            String URL = Class_URL.URL_Login.toString().trim();
            String METHOD_NAME = "Save_Application_Business_Details";
            String Namespace = "http://mis.navodyami.org/", SOAPACTION = "http://mis.navodyami.org/Save_Application_Business_Details";

            try {



                SoapObject request = new SoapObject(Namespace, METHOD_NAME);
                request.addProperty("Enquiry_Id",Integer.valueOf(personalEnquiry_Id));
                request.addProperty("Application_Slno", Integer.valueOf(personalApplicationSlno));
                request.addProperty("isManufacture", Integer.valueOf(manufacture));
                request.addProperty("isHave_License", Integer.valueOf(licence));
                request.addProperty("Which_License", whichLicence);
                request.addProperty("Product1", productOne);
                request.addProperty("Product2", productTwo);
                request.addProperty("Product3",productThree);
                if(businessYear.equalsIgnoreCase("")||businessYear==null){
                    businessYear="0";
                }
                request.addProperty("Business_Started_Year",Integer.valueOf(businessYear));
                request.addProperty("Ownership",ownership);
                request.addProperty("Which_Labour_Hire",Integer.valueOf(labour));
                request.addProperty("IsHave_Labour_Outside_Family",Integer.valueOf(outsidefamilyLabour));
                request.addProperty("Which_Machine_Use",Which_Machine);
                request.addProperty("Which_Machine_Have",Integer.valueOf(machine));
                request.addProperty("Where_Sell_Products",sell_products);
                request.addProperty("Earn_Most_Channel",last_quarter);
                request.addProperty("User_Id",Integer.valueOf(str_UserId));


                JSONArray array=new JSONArray();
                ArrayList<String> json_arrayList=new ArrayList<>();
                String str_json = null,str_jsonfinal;
                str_jsonfinal="[";
                for(int i=0;i<listYears.size();i++){
                    JSONObject obj=new JSONObject();
                    try {
                        obj.put("Year",listYears.get(i));
                        obj.put("TotalMale",listMalePermentEmplyee.get(i));
                        obj.put("TotalFemale",listFemalePermentEmplyee.get(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    array.put(obj);
                    if(str_json==null){
                        str_json =(obj.toString() + ",");
                    }else {
                        str_json = str_json + (obj.toString() + ",");
                    }
               /* if(i==2) {
                    str_json += obj.toString();
                }else{
                    str_json += obj.toString() + ",";
                }*/
                    //json_arrayList.add(str_json.trim);
                    // str_json += str_json+obj.toString()+",";
                }
                int lastCommaIndex = str_json.lastIndexOf(",");
                str_jsonfinal += str_json.substring(0, lastCommaIndex -0);

                // str_json.trim();
                // String str_jsonFinal=removeLastChar(str_json);
                str_jsonfinal+="]";


                Log.e("tag","PermentEmplyee json_arrayList=="+json_arrayList);
                Log.e("tag","PermentEmplyee array=="+array);
                Log.e("tag","PermentEmplyee str_jsonFinal=="+str_jsonfinal);
                request.addProperty("Permanent_Employees",str_jsonfinal);
                JSONObject jsonObjectTrunover = new JSONObject();
                for(int k=0;k<listYears.size() && k<listTurnOver.size();k++)
                {
                    jsonObjectTrunover.put(listYears.get(k),listTurnOver.get(k));
                }
                Log.e("tag","jsonObjectTrunover="+jsonObjectTrunover);
                request.addProperty("TurnOver",jsonObjectTrunover.toString());

                JSONObject jsonObjectProfit = new JSONObject();
                for(int k=0;k<listYears.size() && k<listProfit.size();k++)
                {
                    jsonObjectProfit.put(listYears.get(k),listProfit.get(k));
                }
                Log.e("tag","jsonObjectProfit="+jsonObjectProfit);
                request.addProperty("Profit",jsonObjectProfit.toString());
                request.addProperty("Total_Employees",Integer.valueOf(total_Employee));

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


            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            Log.i("TAG", "onPostExecute");

            Log.d("ServiceResponseisss: ",status.toString());

            //     dialog.dismiss();


            if(status.equalsIgnoreCase("success")) {
                /*Toast toast= Toast.makeText(AddApplicationTwoActivity.this, " Application Updated Successfully " , Toast.LENGTH_LONG);
                // Toast toast=   Toast.makeText(YOUR ACTIVITY NAME ,Toast.LENGTH_SHORT);
                View view =toast.getView();
                view.setBackgroundColor( Color.parseColor("#009900")); //any color your want
                toast.show();*/
               /* Intent intent1 = new Intent(AddApplicationTwoActivity.this, AddApplicationThreeActivity.class);
                intent1.putExtra("EnquiryId",EnquiryId);
                intent1.putExtra("Application_SlnoNew",ApplicationSlnoNew);
                intent1.putExtra("isApplicant",isApplicant);
                startActivity(intent1);*/
                //  getDatefromDB_AddApplicationThreeDetails(personalEnquiry_Id,personalApplicationSlno);
                AddApplicationThreeDetails addApplicationThreeDetails=new AddApplicationThreeDetails(getApplicationContext());
                addApplicationThreeDetails.execute();
            }
            else{
                // Toast toast= Toast.makeText(getActivity(), "  Updated Failed  " , Toast.LENGTH_LONG);
                if (alertdialog.isShowing()) {
                    alertdialog.dismiss();
                }
                Toast toast= Toast.makeText(context, "  "+status.toString()+"  " , Toast.LENGTH_LONG);
                View view =toast.getView();
                view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
                toast.show();
            }
        }
    }
    private class AddApplicationThreeDetails extends AsyncTask<String, Void, Void>
    {
        //     ProgressDialog dialog;

        Context context;

        //  public UpdateManagerDetails(GetManagerDetails getManagerDetails) {
        //  }

        @Override
        protected void onPreExecute()
        {
            Log.i("TAG", "onPreExecute---tab2");
         /*   dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();*/

        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            Log.i("TAG", "onProgressUpdate---tab2");
        }

        public AddApplicationThreeDetails(Context activity) {
            context = activity;
            //  dialog = new ProgressDialog(context);
        }
        @Override
        protected Void doInBackground(String... params)
        {
            Log.i("TAG", "doInBackground");
            //    SubmitCredit();
            String URL = Class_URL.URL_Login.toString().trim();
            String METHOD_NAME = "Save_Application_Credit_Details";
            String Namespace = "http://mis.navodyami.org/", SOAPACTION = "http://mis.navodyami.org/Save_Application_Credit_Details";

            try {
                SoapObject request = new SoapObject(Namespace, METHOD_NAME);
                request.addProperty("Application_Slno", Integer.parseInt(personalApplicationSlno));
                request.addProperty("Source_Of_Business", BusinessSource);
                request.addProperty("Initial_Investment", Investment);
                request.addProperty("isKnowledge_Loan_Process", Integer.parseInt(Knowledge));
                request.addProperty("Applied_Loan_Amount", Integer.parseInt(AppliedAmt));
                request.addProperty("Sanctioned_Loan_Amount", Integer.parseInt(SanctionedAmt));
                request.addProperty("Interest_Rate", InterestRate);
                request.addProperty("Repayment_Period",Repaymentperiod);
                request.addProperty("Applied_At",AppliedAt);
                request.addProperty("Loan_For_Product_Improvement",Integer.parseInt(int_Product_Improvement));
                request.addProperty("Loan_For_Working_Expenses",Integer.parseInt(int_Working_Expenses));
                request.addProperty("Loan_For_Land",Integer.parseInt(int_Land));
                request.addProperty("Loan_For_Equipment",Integer.parseInt(int_Equipment));
                request.addProperty("Loan_For_Finance_Trading",Integer.parseInt(int_Finance_Trading));
                request.addProperty("User_Id",Integer.valueOf(str_UserId));
                request.addProperty("Application_Fees",Integer.parseInt(ApplicationFees));
                request.addProperty("Receipt_Date",VerifiedDate);
                request.addProperty("Remark",Remark);
                request.addProperty("Manual_Receipt_No",Manual_Receipt_No);
                request.addProperty("Payment_Mode",Payment_Mode);

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
                    Log.e("tag","response three =="+response.toString());
                    Log.e("tag","status =="+status);


                } catch (Throwable t) {

                    Log.e("request fail", "> " + t.getMessage());

                }
            } catch (Throwable t) {

                Log.e("UnRegister Error", "> " + t.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            Log.i("TAG", "onPostExecute");

            Log.d("ServiceResponseisss: ",status.toString());

            //    dialog.dismiss();

            if(status.equalsIgnoreCase("success")) {
                offlineData("");
               /* Toast toast= Toast.makeText(getApplicationContext(), "  Application Updated Successfully " , Toast.LENGTH_LONG);
                // Toast toast=   Toast.makeText(YOUR ACTIVITY NAME ,Toast.LENGTH_SHORT);
                View view =toast.getView();
                view.setBackgroundColor( Color.parseColor("#009900")); //any color your want
                toast.show();*/
                // offline_count.setVisibility(View.GONE);
              /*  Intent intent1 = new Intent(getContext(), BottomActivity.class);
                intent1.putExtra("frgToLoad", "0");
                startActivity(intent1);*/
                /*AddApplicationTwoActivity addApplicationTwoActivity=new AddApplicationTwoActivity();
                addApplicationTwoActivity.clear_fields();*/
            }
            else{
                // Toast toast= Toast.makeText(getContext(), "  Updated Failed  " , Toast.LENGTH_LONG);
                if (alertdialog.isShowing()) {
                    alertdialog.dismiss();
                }
                Toast toast= Toast.makeText(context, "  "+status.toString()+"  " , Toast.LENGTH_LONG);
                View view =toast.getView();
                view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
                toast.show();
            }
        }
    }
    public void getEnquiryOfflineDataSync(){
        SQLiteDatabase db2 = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS ListEnquiryDetails(tempId INTEGER PRIMARY KEY AUTOINCREMENT,FName VARCHAR,MobileNo VARCHAR,EmailId VARCHAR,BusinessName VARCHAR,EnquiryId VARCHAR,Status VARCHAR,YearCode VARCHAR,MName VARCHAR,LName VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,isApplicant VARCHAR,Gender VARCHAR,dataSyncStatus VARCHAR );");
        Cursor cursor1 = db2.rawQuery("SELECT DISTINCT * FROM ListEnquiryDetails WHERE dataSyncStatus='" +"offline"+"'", null);
        int x = cursor1.getCount();

        if(x>0){
            // if (cursor1.moveToFirst()) {
            cursor1.moveToFirst();
            //    do {
            EnquiryIdoff = cursor1.getString(cursor1.getColumnIndex("EnquiryId"));
            Enquiry_tempIdoff = cursor1.getString(cursor1.getColumnIndex("tempId"));
            if(EnquiryIdoff.equalsIgnoreCase("0")){

                String FName=cursor1.getString(cursor1.getColumnIndex("FName"));
                String MName=cursor1.getString(cursor1.getColumnIndex("MName"));
                String LName=cursor1.getString(cursor1.getColumnIndex("LName"));
                String MobileNo=cursor1.getString(cursor1.getColumnIndex("MobileNo"));
                String EmailId=cursor1.getString(cursor1.getColumnIndex("EmailId"));
                String sp_strstate_ID=cursor1.getString(cursor1.getColumnIndex("StateId"));
                String sp_strdistrict_ID=cursor1.getString(cursor1.getColumnIndex("DistrictId"));
                String sp_strTaluk_ID=cursor1.getString(cursor1.getColumnIndex("TalukaId"));
                String sp_strVillage_ID=cursor1.getString(cursor1.getColumnIndex("VillageId"));
                String sp_strsector_ID=cursor1.getString(cursor1.getColumnIndex("SectorId"));
                String BusinessName=cursor1.getString(cursor1.getColumnIndex("BusinessName"));
                // cursor1.getString(cursor1.getColumnIndex("DeviceType"));
                //   String str_UserId= cursor1.getString(cursor1.getColumnIndex("UserId"));
                String str_Gender= cursor1.getString(cursor1.getColumnIndex("Gender"));
                //cursor1.getString(cursor1.getColumnIndex("EnquiryId"));

                AddEnquiryDetails addEnquiryDetails = new AddEnquiryDetails(getApplicationContext());
                addEnquiryDetails.execute(FName,MName,LName,MobileNo,EmailId,BusinessName,sp_strstate_ID,sp_strdistrict_ID,sp_strTaluk_ID,sp_strVillage_ID,sp_strsector_ID,str_UserId,str_Gender);


            }else{
                String FName = cursor1.getString(cursor1.getColumnIndex("FName"));
                String MName = cursor1.getString(cursor1.getColumnIndex("MName"));
                String LName = cursor1.getString(cursor1.getColumnIndex("LName"));
                String MobileNo = cursor1.getString(cursor1.getColumnIndex("MobileNo"));
                String EmailId = cursor1.getString(cursor1.getColumnIndex("EmailId"));
                String BusinessName = cursor1.getString(cursor1.getColumnIndex("BusinessName"));
                String EnquiryId = cursor1.getString(cursor1.getColumnIndex("EnquiryId"));
                String Status = cursor1.getString(cursor1.getColumnIndex("Status"));
                String YearCode = cursor1.getString(cursor1.getColumnIndex("YearCode"));
                String sp_strstate_ID = cursor1.getString(cursor1.getColumnIndex("StateId"));
                String sp_strdistrict_ID = cursor1.getString(cursor1.getColumnIndex("DistrictId"));
                String sp_strTaluk_ID = cursor1.getString(cursor1.getColumnIndex("TalukaId"));
                String sp_strVillage_ID = cursor1.getString(cursor1.getColumnIndex("VillageId"));
                String sp_strsector_ID = cursor1.getString(cursor1.getColumnIndex("SectorId"));
                // cursor1.getString(cursor1.getColumnIndex("DeviceType"));
//                String str_UserId= cursor1.getString(cursor1.getColumnIndex("UserId"));
                String str_Gender = cursor1.getString(cursor1.getColumnIndex("Gender"));


                UpdateEnquiryDetails updateEnquiryDetails = new UpdateEnquiryDetails(getApplicationContext());
                updateEnquiryDetails.execute(FName, MName, LName, MobileNo, EmailId, BusinessName, sp_strstate_ID, sp_strdistrict_ID, sp_strTaluk_ID, sp_strVillage_ID, sp_strsector_ID, str_UserId, str_Gender, EnquiryId);

            }
            // } while (cursor1.moveToNext());
            // }
        }

    }
    public void DBUpdate_EnquiryDetails_made_online(String EnquiryId) {
        SQLiteDatabase db_editEnquiry = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_editEnquiry.execSQL("CREATE TABLE IF NOT EXISTS ListEnquiryDetails(tempId INTEGER PRIMARY KEY AUTOINCREMENT,FName VARCHAR,MobileNo VARCHAR,EmailId VARCHAR,BusinessName VARCHAR,EnquiryId VARCHAR,Status VARCHAR,YearCode VARCHAR,MName VARCHAR,LName VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,isApplicant VARCHAR,Gender VARCHAR,dataSyncStatus VARCHAR );");

        String FName,LName,MName,MobileNo,EmailId,BusinessName,stateId,districtId,talukId,villegeId,sectorId,deviceType,userId,Gender;

       /* for(int s=0;s<str_editenquirys.size();s++) {
            Log.e("tag","BusinessName="+str_editenquirys.get(s).getBussinessName()+"Fname="+str_editenquirys.get(s).getFname());

            FName=str_editenquirys.get(s).getFname();
            MName=str_editenquirys.get(s).getMName();
            LName=str_editenquirys.get(s).getLName();
            MobileNo=str_editenquirys.get(s).getMobileNo();
            EmailId=str_editenquirys.get(s).getEmailId();
            BusinessName=str_editenquirys.get(s).getBussinessName();
            stateId=str_editenquirys.get(s).getStateId();
            districtId=str_editenquirys.get(s).getDistrictId();
            talukId=str_editenquirys.get(s).getTalukaI();
            villegeId=str_editenquirys.get(s).getVillageId();
            sectorId=str_editenquirys.get(s).getSectorId();
            EnquiryId=str_editenquirys.get(s).getEnquiry_id();
            Gender = str_editenquirys.get(s).getGender();*/

        //  dataSyncStatus="offline";
        ContentValues cv = new ContentValues();

        cv.put("dataSyncStatus","online");


        long result =  db_editEnquiry.update("ListEnquiryDetails", cv, "EnquiryId = ? AND tempId = ?", new String[]{EnquiryId,Enquiry_tempIdoff});

        Log.e("tag","result update offline to online Enquiry="+result);
        //  if(result != -1){
              /*  editorEnquiry_obj = sharedpreferencebook_OfflineEnquiryData_Obj.edit();
                editorEnquiry_obj.putString(KeyValue_EnquiryEdited, "1");
                editorEnquiry_obj.apply();

                Toast toast= Toast.makeText(EditEnquiryActivity.this, " Enquiry Updated Successfully " , Toast.LENGTH_LONG);
                View view =toast.getView();
                view.setBackgroundColor( Color.parseColor("#009900")); //any color your want
                toast.show();
               *//* FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, EventMain_Fragment.newInstance());
                transaction.commit();*//*
                Intent i = new Intent(EditEnquiryActivity.this, BottomActivity.class);
                i.putExtra("frgToLoad", "0");
                startActivity(i);
              *//*  FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, EventMain_Fragment.newInstance());
                transaction.commit();*//*
                finish();

                SQLiteDatabase db_editEnquiryId = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
                db_editEnquiryId.execSQL("CREATE TABLE IF NOT EXISTS ListEditEnquiryId(EnquiryId VARCHAR,tempId VARCHAR);");

                String SQLiteQuery = "INSERT INTO ListEditEnquiryId (EnquiryId,tempId)" +
                        " VALUES ('" + EnquiryId + "','" + TempId + "');";
                db_editEnquiryId.execSQL(SQLiteQuery);
                db_editEnquiryId.close();*/

        //    }
            /*else{

                //  Toast.makeText(context, "Update Request Failed " , Toast.LENGTH_LONG).show();
                Toast toast= Toast.makeText(EditEnquiryActivity.this, "  Update Request Failed  " , Toast.LENGTH_LONG);
                View view =toast.getView();
                view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
                toast.show();
            }*/

        // }

        db_editEnquiry.close();
    }

    public class AddEnquiryDetails extends AsyncTask<String, Void, Void>
    {
        //   ProgressDialog dialog;

        Context context;

        //  public UpdateManagerDetails(GetManagerDetails getManagerDetails) {
        //  }

        @Override
        protected void onPreExecute()
        {
            Log.i("TAG", "onPreExecute---tab2");
         /*   dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();*/

        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            Log.i("TAG", "onProgressUpdate---tab2");
        }

        public AddEnquiryDetails(Context activity) {
            context = activity;
            // dialog = new ProgressDialog(context);
        }
        @Override
        protected Void doInBackground(String... params)
        {
            Log.i("TAG", "doInBackground");
            //  Submit( params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9] ,params[10],params[11],params[12]);
            String FName=(String) params[0];
            String MName=(String) params[1];
            String LName=(String) params[2];
            String MobileNo=(String) params[3];
            String EmailId=(String) params[4];
            String BusinessName=(String) params[5];
            String sp_strstate_ID=(String) params[6];
            String sp_strdistrict_ID=(String) params[7];
            String sp_strTaluk_ID=(String) params[8];
            String sp_strVillage_ID=(String) params[9];
            String sp_strsector_ID=(String) params[10];
            String str_UserId=(String) params[11];
            String Gender=(String) params[12];
            //  String EnquiryID=(String) params[13];

            String URL = Class_URL.URL_Login.toString().trim();
            String METHOD_NAME = "Enquiry_Adding";
            String Namespace = "http://mis.navodyami.org/", SOAPACTION = "http://mis.navodyami.org/Enquiry_Adding";

            try {
                SoapObject request = new SoapObject(Namespace, METHOD_NAME);

                request.addProperty("FirstName", FName);
                request.addProperty("MiddleName", MName);
                request.addProperty("LastName", LName);
                request.addProperty("MobileNo", MobileNo);
                request.addProperty("EmailId", EmailId);
                request.addProperty("StateId", sp_strstate_ID);
                request.addProperty("DistrictId", sp_strdistrict_ID);
                request.addProperty("TalukaId",sp_strTaluk_ID);
                request.addProperty("VillageId",sp_strVillage_ID);
                //   request.addProperty("panchayat_Id","0");
                request.addProperty("SectorId",sp_strsector_ID);
                request.addProperty("BusinessName",BusinessName);
                request.addProperty("DeviceType","MOB");
                request.addProperty("UserId",str_UserId);
                request.addProperty("EnquiryId","0");
                request.addProperty("Gender",Gender);

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
                    if(status.equalsIgnoreCase("success")){
                        DBUpdate_EnquiryDetails_made_online("0");
                    }


                } catch (Throwable t) {

                    Log.e("request fail", "> " + t.getMessage());

                }
            } catch (Throwable t) {

                Log.e("UnRegister Error", "> " + t.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            Log.i("TAG", "onPostExecute");

            Log.d("ServiceResponseisss: ",status.toString());

            //   dialog.dismiss();

            if(status.equalsIgnoreCase("success")) {
                offlineData("");
            /*    Toast toast= Toast.makeText(context, "  Enquiry inserted Successfully " , Toast.LENGTH_LONG);
                // Toast toast=   Toast.makeText(YOUR ACTIVITY NAME ,Toast.LENGTH_SHORT);
                View view =toast.getView();
                view.setBackgroundColor( Color.parseColor("#009900")); //any color your want
                toast.show();*/
            }
            else{
                // Toast toast= Toast.makeText(context, "  Enquiry insertion Failed  " , Toast.LENGTH_LONG);
                Toast toast= Toast.makeText(context, "  "+status.toString()+"  " , Toast.LENGTH_LONG);
                View view =toast.getView();
                view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
                toast.show();
            }
        }
    }
    public void Submit(String FName,String MName,String LName,String MobileNo,String EmailId,String BusinessName,String sp_strstate_ID,String sp_strdistrict_ID,String sp_strTaluk_ID,String sp_strVillage_ID,String sp_strsector_ID,String str_UserId,String Gender){
        String URL = Class_URL.URL_Login.toString().trim();
        String METHOD_NAME = "Enquiry_Adding";
        String Namespace = "http://mis.navodyami.org/", SOAPACTION = "http://mis.navodyami.org/Enquiry_Adding";

        try {



            SoapObject request = new SoapObject(Namespace, METHOD_NAME);

       /* ------   API Data  ---------
       <Enquiry_Adding xmlns="http://mis.navodyami.org/">
      <FirstName>string</FirstName>
      <MiddleName>string</MiddleName>
      <LastName>string</LastName>
      <MobileNo>string</MobileNo>
      <EmailId>string</EmailId>
      <StateId>long</StateId>
      <DistrictId>long</DistrictId>
      <TalukaId>long</TalukaId>
      <VillageId>long</VillageId>
      <panchayat_Id>long</panchayat_Id>
      <SectorId>long</SectorId>
      <BusinessName>string</BusinessName>
      <DeviceType>string</DeviceType>
      <UserId>long</UserId>
      <EnquiryId>long</EnquiryId>
    </Enquiry_Adding>*/
            request.addProperty("FirstName", FName);
            request.addProperty("MiddleName", MName);
            request.addProperty("LastName", LName);
            request.addProperty("MobileNo", MobileNo);
            request.addProperty("EmailId", EmailId);
            request.addProperty("StateId", sp_strstate_ID);
            request.addProperty("DistrictId", sp_strdistrict_ID);
            request.addProperty("TalukaId",sp_strTaluk_ID);
            request.addProperty("VillageId",sp_strVillage_ID);
            //   request.addProperty("panchayat_Id","0");
            request.addProperty("SectorId",sp_strsector_ID);
            request.addProperty("BusinessName",BusinessName);
            request.addProperty("DeviceType","MOB");
            request.addProperty("UserId",str_UserId);
            request.addProperty("EnquiryId","0");
            request.addProperty("Gender",Gender);

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
                if(status.equalsIgnoreCase("success")){
                    DBUpdate_EnquiryDetails_made_online("0");
                }


            } catch (Throwable t) {

                Log.e("request fail", "> " + t.getMessage());

            }
        } catch (Throwable t) {

            Log.e("UnRegister Error", "> " + t.getMessage());
        }
    }

    public class UpdateEnquiryDetails extends AsyncTask<String, Void, Void>
    {
        //   ProgressDialog dialog;

        Context context;

        //  public UpdateManagerDetails(GetManagerDetails getManagerDetails) {
        //  }

        @Override
        protected void onPreExecute()
        {
            Log.i("TAG", "onPreExecute---tab2");
          /*  dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
*/
        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            Log.i("TAG", "onProgressUpdate---tab2");
        }

        public UpdateEnquiryDetails(Context activity) {
            context = activity;
            //  dialog = new ProgressDialog(context);
        }
        @Override
        protected Void doInBackground(String... params)
        {
            Log.i("TAG", "doInBackground");
            //  Update( params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9] ,params[10],params[11],params[12],params[13]);
            String FName=(String) params[0];
            String MName=(String) params[1];
            String LName=(String) params[2];
            String MobileNo=(String) params[3];
            String EmailId=(String) params[4];
            String BusinessName=(String) params[5];
            String sp_strstate_ID=(String) params[6];
            String sp_strdistrict_ID=(String) params[7];
            String sp_strTaluk_ID=(String) params[8];
            String sp_strVillage_ID=(String) params[9];
            String sp_strsector_ID=(String) params[10];
            String str_UserId=(String) params[11];
            String Gender=(String) params[12];
            String EnquiryID=(String) params[13];

            String URL = Class_URL.URL_Login.toString().trim();
            String METHOD_NAME = "Enquiry_Adding";
            String Namespace = "http://mis.navodyami.org/", SOAPACTION = "http://mis.navodyami.org/Enquiry_Adding";

            try {
                SoapObject request = new SoapObject(Namespace, METHOD_NAME);
                request.addProperty("FirstName", FName);
                request.addProperty("MiddleName", MName);
                request.addProperty("LastName", LName);
                request.addProperty("MobileNo", MobileNo);
                request.addProperty("EmailId", EmailId);
                request.addProperty("StateId", sp_strstate_ID);
                request.addProperty("DistrictId", sp_strdistrict_ID);
                request.addProperty("TalukaId",sp_strTaluk_ID);
                request.addProperty("VillageId",sp_strVillage_ID);
                //   request.addProperty("panchayat_Id","0");
                request.addProperty("SectorId",sp_strsector_ID);
                request.addProperty("BusinessName",BusinessName);
                request.addProperty("DeviceType","MOB");
                request.addProperty("UserId",str_UserId);
                request.addProperty("EnquiryId",Long.parseLong(EnquiryID));
                request.addProperty("Gender",Gender);

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

                    if(status.equals("success")) {
                        DBUpdate_EnquiryDetails_made_online(EnquiryID);
                    }

                } catch (Throwable t) {

                    Log.e("request fail", "> " + t.getMessage());

                }
            } catch (Throwable t) {

                Log.e("UnRegister Error", "> " + t.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            Log.i("TAG", "onPostExecute");

            Log.d("Enquiry Responseisss: ",status.toString());

            //    dialog.dismiss();

            if(status.equalsIgnoreCase("success")) {
                offlineData("");
              /*  Toast toast= Toast.makeText(context, " Enquiry Updated Successfully " , Toast.LENGTH_LONG);
                View view =toast.getView();
                view.setBackgroundColor( Color.parseColor("#009900")); //any color your want
                toast.show();

               *//* FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, EventMain_Fragment.newInstance());
                transaction.commit();*/

            }
            else{
                // Toast.makeText(context, "Update Request Failed " , Toast.LENGTH_LONG).show();
                if (alertdialog.isShowing()) {
                    alertdialog.dismiss();
                }
                Toast toast= Toast.makeText(context, "  "+status.toString()+"  " , Toast.LENGTH_LONG);
                View view =toast.getView();
                view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
                toast.show();
            }
        }
    }
    public void Update(String FName,String MName,String LName,String MobileNo,String EmailId,String BusinessName,String sp_strstate_ID,String sp_strdistrict_ID,String sp_strTaluk_ID,String sp_strVillage_ID,String sp_strsector_ID,String str_UserId,String Gender,String EnquiryID){
        String URL = Class_URL.URL_Login.toString().trim();
        String METHOD_NAME = "Enquiry_Adding";
        String Namespace = "http://mis.navodyami.org/", SOAPACTION = "http://mis.navodyami.org/Enquiry_Adding";

        try {



            SoapObject request = new SoapObject(Namespace, METHOD_NAME);

       /* ------   API Data  ---------
       <Enquiry_Adding xmlns="http://mis.navodyami.org/">
      <FirstName>string</FirstName>
      <MiddleName>string</MiddleName>
      <LastName>string</LastName>
      <MobileNo>string</MobileNo>
      <EmailId>string</EmailId>
      <StateId>long</StateId>
      <DistrictId>long</DistrictId>
      <TalukaId>long</TalukaId>
      <VillageId>long</VillageId>
      <panchayat_Id>long</panchayat_Id>
      <SectorId>long</SectorId>
      <BusinessName>string</BusinessName>
      <DeviceType>string</DeviceType>
      <UserId>long</UserId>
      <EnquiryId>long</EnquiryId>
    </Enquiry_Adding>*/
            request.addProperty("FirstName", FName);
            request.addProperty("MiddleName", MName);
            request.addProperty("LastName", LName);
            request.addProperty("MobileNo", MobileNo);
            request.addProperty("EmailId", EmailId);
            request.addProperty("StateId", sp_strstate_ID);
            request.addProperty("DistrictId", sp_strdistrict_ID);
            request.addProperty("TalukaId",sp_strTaluk_ID);
            request.addProperty("VillageId",sp_strVillage_ID);
            //   request.addProperty("panchayat_Id","0");
            request.addProperty("SectorId",sp_strsector_ID);
            request.addProperty("BusinessName",BusinessName);
            request.addProperty("DeviceType","MOB");
            request.addProperty("UserId",str_UserId);
            request.addProperty("EnquiryId",Long.parseLong(EnquiryID));
            request.addProperty("Gender",Gender);

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

                if(status.equals("success")) {
                    DBUpdate_EnquiryDetails_made_online(EnquiryID);
                }

            } catch (Throwable t) {

                Log.e("request fail", "> " + t.getMessage());

            }
        } catch (Throwable t) {

            Log.e("UnRegister Error", "> " + t.getMessage());
        }
    }

    //-------------------------------------------------------------------------------------------------------------------------------------
    public  void alerts_dialog()
    {

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("Alert");
      //  dialog.setMessage("Kindly update from playstore");
        dialog.setMessage("Kindly contact Tech team for new version");
        /*dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
               *//* Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.leadcampusapp"));
                startActivity(intent);*//*
                finish();
            }
        });*/


        final AlertDialog alert = dialog.create();
        alert.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
            }
        });
        alert.show();

    }

    public  void alerts_dialogMaintenance()
    {

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("Alert");
        dialog.setMessage("Sorry for inconvenience Application is under maintenance!");
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                finish();
            }
        });


        final AlertDialog alert = dialog.create();
        alert.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
            }
        });
        alert.show();


    }
    private class AsyncCallWS_ValidateUser extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;
        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");
            dialog.setMessage("Please wait...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //Log.i(TAG, "onProgressUpdate---tab2");
        }


        @Override
        protected Void doInBackground(String... params) {
            Log.i("df", "doInBackground");

            Login_Verify(str_gmail_ID);
            return null;
        }

        public AsyncCallWS_ValidateUser(MainActivity activity) {
            context = activity;
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPostExecute(Void result) {


            dialog.dismiss();
            if(!login_userStatus.equals("")){
                if (login_userStatus.equals("Success")) {

                    AsyncCallFCM task = new AsyncCallFCM(MainActivity.this);
                    task.execute();

                    /*SharedPreferences.Editor myprefs_Username = sharedpref_username_Obj.edit();
                    myprefs_Username.putString(Key_username, str_username);
                    myprefs_Username.apply();
                    SaveSharedPreference.setUserName(MainActivity.this,account.getDisplayName());

                    SharedPreferences.Editor myprefs_UserImg = sharedpref_userimage_Obj.edit();
                    myprefs_UserImg.putString(key_userimage, str_photoUrl);
                    myprefs_UserImg.apply();*/
                    editor_obj = sharedpreferencebook_usercredential_Obj.edit();
                    editor_obj.putString(Key_username, str_username);
                    editor_obj.putString(key_userimage, str_photoUrl);
                    editor_obj.commit();
                    SaveSharedPreference.setUserName(MainActivity.this,account.getDisplayName());


                    Intent intent = new Intent(MainActivity.this,BottomActivity.class);
                    intent.putExtra("frgToLoad", "1");
                    startActivity(intent);

                } else if (login_userStatus.equals("Invalid Email Id")){
//                    Toast.makeText(MainActivity.this, "No Records Found", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, "Invalid Email Id", Toast.LENGTH_SHORT).show();
                    //signOut();
                    googlesigninclient_obj.signOut();
                }
            }else{
                Toast.makeText(MainActivity.this, "Access Denied", Toast.LENGTH_SHORT).show();

            }

        }//end of onPostExecute
    }// end Async task

    public void Login_Verify(String username1) {
        Vector<SoapObject> result1 = null;

        String METHOD_NAME = "ValidateLogin";
        String Namespace = "http://mis.navodyami.org/", SOAPACTION = "http://mis.navodyami.org/ValidateLogin";

        try {

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
            request.addProperty("EmailId", username1);
            //request.addProperty("User_Password", password1);

            Log.i("request", request.toString());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Login.toString().trim());

            try {

                androidHttpTransport.call(SOAPACTION, envelope);
                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("value at response", response.toString());
             /*   SoapObject obj2 =(SoapObject) response.getProperty(0);
                Log.e("obj2", obj2.toString());*/
                login_userStatus=response.getProperty("Status").toString();
                if (response.getProperty("Status").toString().equals("Success")) {

                        login_userid= response.getProperty("LoginId").toString();
                        login_userEmail=response.getProperty("MailId").toString();
                        login_Username=response.getProperty("Username").toString();
                        login_MobileNo=response.getProperty("MobileNo").toString();


                        Log.e("login_userid", login_userid);
                        Log.e("login_userEmail", login_userEmail);
                        Log.e("login_userStatus", login_userStatus);

                    editor_obj = sharedpreferencebook_usercredential_Obj.edit();
                    editor_obj.putString(KeyValue_employeeid, login_userid);
                    editor_obj.putString(KeyValue_employeename, login_Username);
                    editor_obj.putString(KeyValue_employee_mailid, login_userEmail);
                    editor_obj.putString(KeyValue_employeemobileno, login_MobileNo);
                    editor_obj.commit();


                }

            } catch (Throwable t) {
                Log.e("request fail", "> " + t.getMessage());
                login_userStatus = "slow internet";

            }
        } catch (Throwable t) {
            Log.e("UnRegister Receiver ", "> " + t.getMessage());

        }

    }


    private class AsyncCallFCM extends AsyncTask<String, Void, Void> {

        ProgressDialog dialog;

        Context context;
        boolean versionval;
        @Override
        protected void onPreExecute() {
            Log.i("Navodyamimis", "onPreExecute---tab2");
           /* dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();*/

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Log.i("Navodyamimis", "onProgressUpdate---tab2");
        }

        public AsyncCallFCM(MainActivity activity) {
            context = activity;
            // dialog = new ProgressDialog(activity);
        }

        @Override
        protected Void doInBackground(String... params) {
            Log.i("Navodyamimis", "doInBackground");






            setGCM1();
            setGCM1();


            //  versionval =	appversioncheck(versioncodeInString);


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            /*if (dialog.isShowing()) {
                dialog.dismiss();

            }*/
            // Log.i(TAG, "onPostExecute");

           /* if(versionval)
            {	}else{alerts();}*/



        }
    }//end of AsynTask



    @SuppressLint("MissingPermission")
    public void setGCM1()
    {



//

        // Fetch Device info

       /* final TelephonyManager tm = (TelephonyManager) getBaseContext()
                .getSystemService(Context.TELEPHONY_SERVICE);*/

        tm1 = (TelephonyManager) getBaseContext()
                .getSystemService(Context.TELEPHONY_SERVICE);

        //   final String tmDevice, tmSerial, androidId;
        String NetworkType;
        //TelephonyManager telephonyManager = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));
        simOperatorName = tm1.getSimOperatorName();
        Log.v("Operator", "" + simOperatorName);
        NetworkType = "GPRS";



        int simSpeed = tm1.getNetworkType();
        if (simSpeed == 1)
            NetworkType = "Gprs";
        else if (simSpeed == 4)
            NetworkType = "Edge";
        else if (simSpeed == 8)
            NetworkType = "HSDPA";
        else if (simSpeed == 13)
            NetworkType = "LTE";
        else if (simSpeed == 3)
            NetworkType = "UMTS";
        else
            NetworkType = "Unknown";

        Log.v("SIM_INTERNET_SPEED", "" + NetworkType);
        tmDevice = "" + tm1.getDeviceId();
        Log.v("DeviceIMEI", "" + tmDevice);
        mobileNumber = "" + tm1.getLine1Number();
        Log.v("getLine1Number value", "" + mobileNumber);

        String mobileNumber1 = "" + tm1.getPhoneType();
        Log.v("getPhoneType value", "" + mobileNumber1);
        tmSerial = "" + tm1.getSimSerialNumber();
        //  Log.v("GSM devices Serial Number[simcard] ", "" + tmSerial);
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        Log.v("androidId CDMA devices", "" + androidId);
        UUID deviceUuid = new UUID(androidId.hashCode(),
                ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        deviceId = deviceUuid.toString();
        //  Log.v("deviceIdUUID universally unique identifier", "" + deviceId);


        deviceModelName = Build.MODEL;
        Log.v("Model Name", "" + deviceModelName);
        deviceUSER = Build.USER;
        Log.v("Name USER", "" + deviceUSER);
        devicePRODUCT = Build.PRODUCT;
        Log.v("PRODUCT", "" + devicePRODUCT);
        deviceHARDWARE = Build.HARDWARE;
        Log.v("HARDWARE", "" + deviceHARDWARE);
        deviceBRAND = Build.BRAND;
        Log.v("BRAND", "" + deviceBRAND);
        myVersion = Build.VERSION.RELEASE;
        Log.v("VERSION.RELEASE", "" + myVersion);
        sdkVersion = Build.VERSION.SDK_INT;
        Log.v("VERSION.SDK_INT", "" + sdkVersion);
        sdkver = Integer.toString(sdkVersion);
        // Get display details

        Measuredwidth = 0;
        Measuredheight = 0;
        Point size = new Point();
        WindowManager w = getWindowManager();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            //   w.getDefaultDisplay().getSize(size);
            Measuredwidth = w.getDefaultDisplay().getWidth();//size.x;
            Measuredheight = w.getDefaultDisplay().getHeight();//size.y;
        } else {
            Display d = w.getDefaultDisplay();
            Measuredwidth = d.getWidth();
            Measuredheight = d.getHeight();
        }

        Log.v("SCREEN_Width", "" + Measuredwidth);
        Log.v("SCREEN_Height", "" + Measuredheight);


        regId = FirebaseInstanceId.getInstance().getToken();



        Log.e("regId_DeviceID", "" + regId);

/*<username>string</username>
      <DeviceId>string</DeviceId>
      <OSVersion>string</OSVersion>
      <Manufacturer>string</Manufacturer>
      <ModelNo>string</ModelNo>
      <SDKVersion>string</SDKVersion>
      <DeviceSrlNo>string</DeviceSrlNo>
      <ServiceProvider>string</ServiceProvider>
      <SIMSrlNo>string</SIMSrlNo>
      <DeviceWidth>string</DeviceWidth>
      <DeviceHeight>string</DeviceHeight>
      <AppVersion>string</AppVersion>*/

        //if (!regId.equals("")){
        if (2>1){
            // String WEBSERVICE_NAME = "http://dfhrms.cloudapp.net/PMSservice.asmx?WSDL";
            String SOAP_ACTION1 = "http://mis.navodyami.org/SaveDeviceDetails";
            String METHOD_NAME1 = "SaveDeviceDetails";
            String MAIN_NAMESPACE = "http://mis.navodyami.org/";
            String URI = Class_URL.URL_Login.toString().trim();


            SoapObject request = new SoapObject(MAIN_NAMESPACE, METHOD_NAME1);

            //	request.addProperty("LeadId", Password1);
            request.addProperty("username",login_userid);

            request.addProperty("DeviceId", regId);
            request.addProperty("OSVersion", myVersion);
            request.addProperty("Manufacturer", deviceBRAND);
            request.addProperty("ModelNo", deviceModelName);
            request.addProperty("SDKVersion", sdkver);
            request.addProperty("DeviceSrlNo", tmDevice);
            request.addProperty("ServiceProvider", simOperatorName);
            request.addProperty("SIMSrlNo", tmSerial);
            request.addProperty("DeviceWidth", Measuredwidth);
            request.addProperty("DeviceHeight", Measuredheight);
            request.addProperty("AppVersion", versioncode);
            //request.addProperty("AppVersion","4.0");


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            // Set output SOAP object
            envelope.setOutputSoapObject(request);
            Log.e("deviceDetails Request","deviceDetail"+request.toString());
            // Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URI);

            try {
                androidHttpTransport.call(SOAP_ACTION1, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

                System.out.println("Device Res"+response);

                Log.i("sending device detail", response.toString());

            } catch (Exception e) {
                e.printStackTrace();
                Log.i("err",e.toString());
            }
        }






    }//end of GCM()


    public boolean CheckingPermissionIsEnabledOrNot() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int ThirdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET);
        int FourthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE);
        int FifthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_NETWORK_STATE);
        int SixthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE);
       // int SeventhPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WAKE_LOCK);
        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED && ThirdPermissionResult == PackageManager.PERMISSION_GRANTED && FourthPermissionResult == PackageManager.PERMISSION_GRANTED && FifthPermissionResult == PackageManager.PERMISSION_GRANTED && SixthPermissionResult == PackageManager.PERMISSION_GRANTED ;  //&& SeventhPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    private void RequestMultiplePermission() {

        // Creating String Array with Permissions.
        ActivityCompat.requestPermissions(MainActivity.this, new String[]
                {
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.CALL_PHONE
                }, RequestPermissionCode);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case RequestPermissionCode:

                if (grantResults.length > 0) {

                    boolean WriteExternalStoragePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadExternalStoragePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean InternetPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadPhonePermission = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean AccessNetworkPermission = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                    boolean CallPhonePermission = grantResults[5] == PackageManager.PERMISSION_GRANTED;

                    if (WriteExternalStoragePermission && ReadExternalStoragePermission && InternetPermission && ReadPhonePermission && AccessNetworkPermission && CallPhonePermission) {

                        //Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this,"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }

                break;
        }

    }

    private void signOut() {
        googlesigninclient_obj.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                     //   Toast.makeText(MainActivity.this,"Sigined Out Successfully",Toast.LENGTH_SHORT).show();
                        Toast toast = Toast.makeText(getApplicationContext(), "Signed out Successfully", Toast.LENGTH_SHORT);
                        View view = toast.getView();
                        view.setBackgroundColor(Color.parseColor("#009900")); //any color your want
                        toast.show();
                        if(alertdialog.isShowing()){
                            alertdialog.dismiss();
                        }
                    }
                });
    }




  /*  private void signOut_InvalidUser() {
        googlesigninclient_obj.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        Toast.makeText(MainActivity.this,"Sigined Out: InValid User",Toast.LENGTH_SHORT).show();
                    }
                });
    }*/





    private  boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(this,p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MULTIPLE_PERMISSIONS );
            return false;
        }
        return true;
    }

    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                // tv.setBackgroundColor(Color.CYAN);
                tv.setBackgroundDrawable(
                        new ColorDrawable(Color.parseColor(COLOR)));
                tv.setTextColor(Color.WHITE);
              //  tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/laouibold.ttf"));
                return;
            }
        }
    }

    @Override
    public void onBackPressed() {

        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

}// end of class
