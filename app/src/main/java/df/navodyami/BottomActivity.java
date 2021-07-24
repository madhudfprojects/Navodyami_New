package df.navodyami;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import df.navodyami.adapter.CalendarAdapter;

public class BottomActivity extends AppCompatActivity {

    public CalendarAdapter cal_adapter1;
    public GregorianCalendar cal_month, cal_month_copy;

    Class_InternetDectector internetDectector;
    Boolean isInternetPresent = false;

    String id, status;
     String SyncSlno="0",SyncSlnoSector="0",SyncSlnoQualification="0",SyncSlnoSocialCategory="0",SyncSlnoKnowNavodyami="0",SyncSlnoOwnership="0",SyncSlnoInvestment="0";
    private HashMap<String,Integer> hashProjTypeId = new HashMap<String,Integer>();
    List<Class_StateListDetails> stateList;
    List<Class_DistrictListDetails> districtList;
    List<Class_TalukListDetails> talukList;
    List<Class_VillageListDetails> villageList;
    int x;
    int enquiry_page,event_page,dashboard_page,page;
    Fragment selectedFragment = null;
    String frgToLoad;
    Fragment fragment = null;
    FragmentTransaction fragmentTransaction;
    BottomNavigationView bottomNavigationView;

    SharedPreferences sharedpreferencebook_usercredential_Obj;
    SharedPreferences.Editor editor_obj;
    String str_UserId;
    public static final String sharedpreferencebook_usercredential = "sharedpreferencebook_usercredential";
    public static final String KeyValue_employeeid = "KeyValue_employeeid";

    private HashMap<String, Integer> hashYearId = new HashMap<String, Integer>();
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
    String BusinessSource;
    String LastLoan;
    String Investment;
    String Knowledge;
    String AppliedAmt;
    String SanctionedAmt;
    String InterestRate;
    String AppliedAt;
    String Repaymentperiod;
    String ApplicationFees;
    String VerifiedDate;
    String Remark;
    String Manual_Receipt_No,Payment_Mode,TempId_asyncTask;

    String Application_SlnoNew,personalEnquiry_Id,personalApplicationSlno,personalTempId,AllDatatempId,AllDataApplicationSlno,AllDataEnquiry_Id;

    AlertDialog alertdialog;
    String EnquiryIdoff,Enquiry_tempIdoff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bottom);
        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_n_actionbar);
        setSupportActionBar(toolbar);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final TextView title= (TextView) toolbar.findViewById(R.id.title_name);
        getSupportActionBar().setTitle("");
      //  title.setText("Home");

        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

        stateList = new ArrayList<>();
        districtList = new ArrayList<>();
        talukList = new ArrayList<>();
        villageList = new ArrayList<>();

        sharedpreferencebook_usercredential_Obj = this.getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_UserId = sharedpreferencebook_usercredential_Obj.getString(KeyValue_employeeid, "").trim();

        if(isInternetPresent){
            offlineData();
        }

      //  GetSyncSlno_SectorTable();
     //   toolbar.setBackgroundResource(R.drawable.gradiant);
        Intent intent = getIntent();
        if(getIntent().getExtras()!=null) {
            frgToLoad = intent.getStringExtra("frgToLoad");
            page=Integer.valueOf(frgToLoad);
            /*if(page==0){
                selectedFragment = EnquiryMain_Fragment.newInstance();
                title.setText("Entrepreneur Details");
                switchFragment(selectedFragment);
            }else*/ if(page==1){
                selectedFragment = Dashboard_Fragment.newInstance();
                title.setText("Home");
                switchFragment(selectedFragment);
            }else if(page==2){
                selectedFragment = EventMain_Fragment.newInstance();
                title.setText("Schedules");
                switchFragment(selectedFragment);
            }else{
                selectedFragment = Dashboard_Fragment.newInstance();
                title.setText("Home");
                switchFragment(selectedFragment);
            }
        }


        if (isInternetPresent) {

            GetSyncSlno_VillageTable_B4insertion();
            GetSectorlistCount();
            GetQualificationlistCount();
            Get_Social_CategoryCount();
            Get_KnowNavodyamiCount();
            Get_OwnershipCount();
            Get_InvestmentCount();

        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.action_item1:
                                selectedFragment = EnquiryMain_Fragment.newInstance();
                                title.setText("Entrepreneur Details");
                                switchFragment(selectedFragment);
                                break;

                            case R.id.action_item2:
                                selectedFragment = Dashboard_Fragment.newInstance();
                                title.setText("Home");
                                switchFragment(selectedFragment);
                                break;

                            case R.id.action_item3:
                                selectedFragment = EventMain_Fragment.newInstance();
                                title.setText("Schedules");
                                switchFragment(selectedFragment);
                                break;
                        }
                       /* if(item.getItemId()==R.id.action_item1){
                            page=0;
                        }else if(item.getItemId()==R.id.action_item2){
                            page=1;
                        }else if(item.getItemId()==R.id.action_item3){
                            page=3;
                        }else {
                            page=1;
                        }
                        switch (page) {
                            case 0:
                                selectedFragment = EnquiryMain_Fragment.newInstance();
                                title.setText("Entrepreneur Details");
                                break;
                            case 1:
                                selectedFragment = Dashboard_Fragment.newInstance();
                                title.setText("Dashboard");
                                break;
                            case 3:
                                selectedFragment = EventMain_Fragment.newInstance();
                                title.setText("Schedules");
                                break;*/
                            /*case R.id.action_item4:
                                selectedFragment = ItemOneFragment.newInstance();
                                break;
                            case R.id.action_item5:
                                selectedFragment = ItemTwoFragment.newInstance();
                                break;*/

                       /* FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();*/
                        return true;
                    }
                });


            //Manually displaying the first fragment - one time only
      /*  FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, Dashboard_Fragment.newInstance());
        transaction.commit();*/

        //Used to select an item programmatically
     //   bottomNavigationView.getMenu().getItem(page).setChecked(true);
    }

    private void switchFragment(Fragment selectedFragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, selectedFragment);
        fragmentTransaction.commit();
        bottomNavigationView.getMenu().getItem(page).setChecked(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.help){
            Intent i = new Intent(getApplicationContext(), Activity_UserManual_DownloadPDF.class);
            startActivity(i);
            finish();
        }
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
        if (id == R.id.Sync) {
            internetDectector = new Class_InternetDectector(getApplicationContext());
            isInternetPresent = internetDectector.isConnectingToInternet();
            if (isInternetPresent) {

               /* GetMasterDetails getMasterDetails = new GetMasterDetails(BottomActivity.this);
                getMasterDetails.execute();

                GetSectorDetails getSectorDetails = new GetSectorDetails(BottomActivity.this);
                getSectorDetails.execute();

                GetQualificationDetails getQualificationDetails =new GetQualificationDetails(BottomActivity.this);
                getQualificationDetails.execute();

                GetSocialCategoryDetails getSocialCategoryDetails=new GetSocialCategoryDetails(BottomActivity.this);
                getSocialCategoryDetails.execute();

                GetKnowNavodyamiDetails getKnowNavodyamiDetails=new GetKnowNavodyamiDetails(BottomActivity.this);
                getKnowNavodyamiDetails.execute();

                GetOwnershipDetails getOwnershipDetails=new GetOwnershipDetails(BottomActivity.this);
                getOwnershipDetails.execute();

                GetInitialInvestmentDetails getInitialInvestmentDetails=new GetInitialInvestmentDetails(BottomActivity.this);
                getInitialInvestmentDetails.execute();*/

                deleteYearTable_B4insertion();
                AcademicYear academicYear = new AcademicYear(this);
                academicYear.execute();
               // }

            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            }


        }
        return super.onOptionsItemSelected(item);
    }
    public void deleteYearTable_B4insertion() {

        SQLiteDatabase db_yearlist_delete = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_yearlist_delete.execSQL("CREATE TABLE IF NOT EXISTS YearList(YearID VARCHAR,YearName VARCHAR,Display_Year VARCHAR);");
        Cursor cursor = db_yearlist_delete.rawQuery("SELECT * FROM YearList", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_yearlist_delete.delete("YearList", null, null);

        }
        db_yearlist_delete.close();
    }

    public class AcademicYear extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;

        private ProgressBar progressBar;
        private ProgressDialog progressDialog;

        AcademicYear (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params)
        {
            //String versionCode = (String) params[2];
            String METHOD_NAME = "GetAcademicYearList";
            String SOAP_ACTION1 = "http://mis.navodyami.org/GetAcademicYearList";
            String NAMESPACE = "http://mis.navodyami.org/";


            try{

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

                envelope.dotNet = true;
                //Set output SOAP object
                envelope.setOutputSoapObject(request);
                //Create HTTP call object
                //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
                HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Login.toString().trim());
                //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

                try
                {
                    androidHttpTransport.call(SOAP_ACTION1, envelope);
                    SoapObject response = (SoapObject) envelope.getResponse();
                    Log.d("soap response year",response.toString());

                    return response;

                }catch(OutOfMemoryError ex){
                  runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                catch (Exception t) {
                    Log.e("request fail", "> " + t.getMessage().toString());

                    final String exceptionStr = t.getMessage().toString();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(context,"Web Service Error", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            catch(OutOfMemoryError ex){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception t) {
                Log.d("exception outside",t.getMessage().toString());
                final String exceptionStr = t.getMessage().toString();
               runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context,"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;


            //Log.d("Soap response is",response.toString());

            //return response;
        }

        @Override
        protected void onPreExecute() {
    /*        progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/

            progressDialog.setMessage("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(SoapObject result) {
            //progressBar.setVisibility(View.GONE);

            progressDialog.dismiss();


            String finalResult = result.toString();
            String finals = finalResult.replace("anyType","");
            Log.d("Finals is",finals);

            ArrayList<String> yearList = new ArrayList<String>();

            Log.d("finalResultsssss",finalResult);

            for(int i=0;i < result.getPropertyCount();i++)
            {
                SoapObject list = (SoapObject) result.getProperty(i);
                SoapPrimitive S_slno,S_AcademicCode,S_Status,S_Display_Year;
                Object O_slno,O_AcademicCode,O_Status,O_Display_Year;

                int int_slno=0;

                String str_slno=null,str_AcademicCode=null,str_status=null,str_Display_Year=null;

                O_slno = list.getProperty("slno");
                if(!O_slno.toString().equals("anyType") && !O_slno.toString().equals(null) && !O_slno.toString().equals("anyType{}")){
                    S_slno = (SoapPrimitive) list.getProperty("slno");
                    Log.d("slno",S_slno.toString());
                    str_slno = O_slno.toString();
                    int_slno = Integer.valueOf(str_slno);
                }

                O_AcademicCode = list.getProperty("AcademicCode");
                if(!O_AcademicCode.toString().equals("anyType") && !O_AcademicCode.toString().equals(null) && !O_AcademicCode.toString().equals("anyType{}"))
                {
                    S_AcademicCode = (SoapPrimitive) list.getProperty("AcademicCode");
                    Log.d("O_AcademicCode Name",S_AcademicCode.toString());
                    str_AcademicCode = O_AcademicCode.toString();

                    yearList.add(str_AcademicCode);

                    hashYearId.put(str_AcademicCode,int_slno);

                }
                O_Display_Year = list.getProperty("Display_Year");
                if(!O_Display_Year.toString().equals("anyType") && !O_Display_Year.toString().equals(null) && !O_Display_Year.toString().equals("anyType{}"))
                {
                    S_Display_Year = (SoapPrimitive) list.getProperty("Display_Year");
                    Log.d("O_AcademicCode Name",S_Display_Year.toString());
                    str_Display_Year = O_Display_Year.toString();

                }
                DBCreate_Yeardetails_insert_2SQLiteDB(str_slno,str_AcademicCode,str_Display_Year);

            }

           /* ArrayAdapter dataAdapter3 = new ArrayAdapter(getActivity(), R.layout.simple_spinner_items, yearList);
            dataAdapter3.setDropDownViewResource(R.layout.spinnercustomstyle);
            // attaching data adapter to spinner
            spin_year.setAdapter(dataAdapter3);*/
         //   uploadfromDB_Yearlist();



            //setProjectTypeSpinner(projectTypeList);

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
    public void DBCreate_Yeardetails_insert_2SQLiteDB(String str_yearID, String str_yearname,String str_display_Year) {
        SQLiteDatabase db_yearlist = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_yearlist.execSQL("CREATE TABLE IF NOT EXISTS YearList(YearID VARCHAR,YearName VARCHAR,Display_Year VARCHAR);");


        String SQLiteQuery = "INSERT INTO YearList (YearID, YearName, Display_Year)" +
                " VALUES ('" + str_yearID + "','" + str_yearname + "','" + str_display_Year + "');";
        db_yearlist.execSQL(SQLiteQuery);


        //Log.e("str_yearID DB", str_yearID);
        // Log.e("str_yearname DB", str_yearname);
        db_yearlist.close();
    }

    public void GetSectorlistCount() {
        SQLiteDatabase db_sector = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_sector.execSQL("CREATE TABLE IF NOT EXISTS SectorList(SectorId VARCHAR,SectorName VARCHAR);");
     //   Cursor cursor = db_sector.rawQuery("SELECT DISTINCT * FROM SectorList WHERE SectorId = (SELECT MAX(SectorId)  FROM SectorList)", null);
        Cursor cursor = db_sector.rawQuery("SELECT DISTINCT * FROM SectorList", null);

        x = cursor.getCount();
        Log.d("cursor count sector", Integer.toString(x));
       if(x>0) {
           cursor.moveToLast();
           SyncSlnoSector = cursor.getString(cursor.getColumnIndex("SectorId"));
       }
       Log.e("tag","SyncSlnoSector=="+SyncSlnoSector);
    }
    public void GetQualificationlistCount() {
        SQLiteDatabase db_Qualification = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_Qualification.execSQL("CREATE TABLE IF NOT EXISTS QualificationList(QualificationId VARCHAR,QualificationName VARCHAR);");
       // Cursor cursor = db_sector.rawQuery("SELECT DISTINCT * FROM QualificationList WHERE QualificationId = (SELECT MAX(QualificationId)  FROM QualificationList)", null);
        Cursor cursor = db_Qualification.rawQuery("SELECT DISTINCT * FROM QualificationList", null);
        x = cursor.getCount();
        Log.d("tag","cursor count Qualification="+Integer.toString(x));
        if(x>0) {
            cursor.moveToLast();
            SyncSlnoQualification = cursor.getString(cursor.getColumnIndex("QualificationId"));
        }
        Log.e("tag","SyncSlnoQualification=="+SyncSlnoQualification);
    }
    public void Get_Social_CategoryCount() {
        SQLiteDatabase db_SocialCategory = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_SocialCategory.execSQL("CREATE TABLE IF NOT EXISTS SocialCategoryList(SocialCategoryId VARCHAR,SocialCategoryName VARCHAR);");
        Cursor cursor = db_SocialCategory.rawQuery("SELECT DISTINCT * FROM SocialCategoryList", null);
        x = cursor.getCount();
        Log.d("tag","cursor count SocialCategory="+Integer.toString(x));
        if(x>0) {
            cursor.moveToLast();
            SyncSlnoSocialCategory = cursor.getString(cursor.getColumnIndex("SocialCategoryId"));
        }
        Log.e("tag","SyncSlnoSocialCategory=="+SyncSlnoSocialCategory);
    }
    public void Get_KnowNavodyamiCount() {
        SQLiteDatabase db_SocialCategory = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_SocialCategory.execSQL("CREATE TABLE IF NOT EXISTS KnowNavodyamiList(KnowNavodyamiId VARCHAR,KnowNavodyamiName VARCHAR);");
        Cursor cursor = db_SocialCategory.rawQuery("SELECT DISTINCT * FROM KnowNavodyamiList", null);
        x = cursor.getCount();
        Log.d("tag","cursor count KnowNavodyami="+Integer.toString(x));
        if(x>0) {
            cursor.moveToLast();
            SyncSlnoKnowNavodyami = cursor.getString(cursor.getColumnIndex("KnowNavodyamiId"));
        }
        Log.e("tag","SyncSlnoKnowNavodyami=="+SyncSlnoKnowNavodyami);
    }
    public void Get_OwnershipCount() {
        SQLiteDatabase db_Ownership = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_Ownership.execSQL("CREATE TABLE IF NOT EXISTS OwnershipList(OwnershipId VARCHAR,OwnershipName VARCHAR);");
        Cursor cursor = db_Ownership.rawQuery("SELECT DISTINCT * FROM OwnershipList", null);
        x = cursor.getCount();
        Log.d("tag","cursor count Ownership="+Integer.toString(x));
        if(x>0) {
            cursor.moveToLast();
            SyncSlnoOwnership = cursor.getString(cursor.getColumnIndex("OwnershipId"));
        }
        Log.e("tag","SyncSlnoOwnership=="+SyncSlnoOwnership);
    }
    public void Get_InvestmentCount() {
        SQLiteDatabase db_Investment = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_Investment.execSQL("CREATE TABLE IF NOT EXISTS InvestmentList(InvestmentId VARCHAR,InvestmentName VARCHAR);");
        Cursor cursor = db_Investment.rawQuery("SELECT DISTINCT * FROM InvestmentList", null);
        x = cursor.getCount();
        Log.d("tag","cursor count Ownership="+Integer.toString(x));
        if(x>0) {
            cursor.moveToLast();
            SyncSlnoInvestment = cursor.getString(cursor.getColumnIndex("InvestmentId"));
        }
        Log.e("tag","SyncSlnoInvestment=="+SyncSlnoInvestment);
    }

    public void GetSyncSlno_VillageTable_B4insertion() {

        SQLiteDatabase db_villagelist_syncslno = openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_villagelist_syncslno.execSQL("CREATE TABLE IF NOT EXISTS VillageList(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR,SyncSlno VARCHAR);");
        Cursor cursor1 = db_villagelist_syncslno.rawQuery("SELECT SyncSlno FROM VillageList WHERE VillageID=570686", null);
        int x = cursor1.getCount();

        if (cursor1.moveToFirst()) {

            do {

                SyncSlno = cursor1.getString(cursor1.getColumnIndex("SyncSlno"));

                Log.e("tag","SyncSlno=="+SyncSlno);
            } while (cursor1.moveToNext());
        }
        db_villagelist_syncslno.close();

    }
  /*  public void GetSyncSlno_SectorTable() {

        SQLiteDatabase db_Sectorlist_syncslno = openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_Sectorlist_syncslno.execSQL("CREATE TABLE IF NOT EXISTS SectorList(SectorId VARCHAR,SectorName VARCHAR);");
        Cursor cursor1 = db_Sectorlist_syncslno.rawQuery("SELECT SectorId FROM SectorList", null);
        int x = cursor1.getCount();

        if (cursor1.moveToFirst()) {

            do {

                SyncSlnoSector = cursor1.getString(cursor1.getColumnIndex("SyncSlno"));

                Log.e("tag","SyncSlnoSector=="+SyncSlnoSector);
            } while (cursor1.moveToNext());
        }
        db_Sectorlist_syncslno.close();

    }*/

    public void DBCreate_Sector_insert_2SQLiteDB(ArrayList<String> str_SectorId, ArrayList<String> str_SectorName) {
        SQLiteDatabase db_sectorlist = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_sectorlist.execSQL("CREATE TABLE IF NOT EXISTS SectorList(SectorId VARCHAR,SectorName VARCHAR);");

        Cursor cursor1 = db_sectorlist.rawQuery("SELECT * FROM SectorList WHERE SectorId='"+SyncSlnoSector+"'", null);
        int x = cursor1.getCount();
Log.e("tag","SyncSlnoSector count="+x);

        for(int s=0;s<str_SectorId.size();s++) {
            Log.e("tag","str_SectorId="+str_SectorId.get(s)+"str_SectorName="+str_SectorName.get(s));
            String SQLiteQuery = "INSERT INTO SectorList (SectorId, SectorName)" +
                    " VALUES ('" + str_SectorId.get(s) + "','" + str_SectorName.get(s) + "');";
            db_sectorlist.execSQL(SQLiteQuery);
        }


        //Log.e("str_yearID DB", str_yearID);
        // Log.e("str_yearname DB", str_yearname);
        db_sectorlist.close();
    }
    public void DBCreate_Qualification_insert_2SQLiteDB(ArrayList<String> str_QualificationId, ArrayList<String> str_QualificationName) {
        SQLiteDatabase db_Qualificationlist = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_Qualificationlist.execSQL("CREATE TABLE IF NOT EXISTS QualificationList(QualificationId VARCHAR,QualificationName VARCHAR);");

        Cursor cursor1 = db_Qualificationlist.rawQuery("SELECT * FROM QualificationList WHERE QualificationId='"+SyncSlnoSector+"'", null);
        int x = cursor1.getCount();
        Log.e("tag","SyncSlnoQualification count="+x);

        for(int s=0;s<str_QualificationId.size();s++) {
            Log.e("tag","str_QualificationId="+str_QualificationId.get(s)+"str_QualificationName="+str_QualificationName.get(s));
            String SQLiteQuery = "INSERT INTO QualificationList (QualificationId, QualificationName)" +
                    " VALUES ('" + str_QualificationId.get(s) + "','" + str_QualificationName.get(s) + "');";
            db_Qualificationlist.execSQL(SQLiteQuery);
        }
        db_Qualificationlist.close();
    }
    public void DBCreate_SocialCategory_insert_2SQLiteDB(ArrayList<String> str_SocialCategoryId, ArrayList<String> str_SocialCategoryName) {
        SQLiteDatabase db_SocialCategorylist = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_SocialCategorylist.execSQL("CREATE TABLE IF NOT EXISTS SocialCategoryList(SocialCategoryId VARCHAR,SocialCategoryName VARCHAR);");

        Cursor cursor1 = db_SocialCategorylist.rawQuery("SELECT * FROM SocialCategoryList WHERE SocialCategoryId='"+SyncSlnoSocialCategory+"'", null);
        int x = cursor1.getCount();
        Log.e("tag","SyncSlnoQualification count="+x);

        for(int s=0;s<str_SocialCategoryId.size();s++) {
            Log.e("tag","str_SocialCategoryId="+str_SocialCategoryId.get(s)+"str_SocialCategoryName="+str_SocialCategoryName.get(s));
            String SQLiteQuery = "INSERT INTO SocialCategoryList (SocialCategoryId, SocialCategoryName)" +
                    " VALUES ('" + str_SocialCategoryId.get(s) + "','" + str_SocialCategoryName.get(s) + "');";
            db_SocialCategorylist.execSQL(SQLiteQuery);
        }
        db_SocialCategorylist.close();
    }
    public void DBCreate_KnowNavodyami_insert_2SQLiteDB(ArrayList<String> str_Id, ArrayList<String> str_Name) {
        SQLiteDatabase db_KnowNavodyamilist = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_KnowNavodyamilist.execSQL("CREATE TABLE IF NOT EXISTS KnowNavodyamiList(KnowNavodyamiId VARCHAR,KnowNavodyamiName VARCHAR);");

        Cursor cursor1 = db_KnowNavodyamilist.rawQuery("SELECT * FROM KnowNavodyamiList WHERE KnowNavodyamiId='"+SyncSlnoKnowNavodyami+"'", null);
        int x = cursor1.getCount();
        Log.e("tag","SyncKnowNavodyami count="+x);

        for(int s=0;s<str_Id.size();s++) {
            Log.e("tag","str_KnowNavodyamiId="+str_Id.get(s)+"str_KnowNavodyamiName="+str_Name.get(s));
            String SQLiteQuery = "INSERT INTO KnowNavodyamiList (KnowNavodyamiId, KnowNavodyamiName)" +
                    " VALUES ('" + str_Id.get(s) + "','" + str_Name.get(s) + "');";
            db_KnowNavodyamilist.execSQL(SQLiteQuery);
        }
        db_KnowNavodyamilist.close();
    }
    public void DBCreate_Ownership_insert_2SQLiteDB(ArrayList<String> str_Id, ArrayList<String> str_Name) {
        SQLiteDatabase db_Ownership = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_Ownership.execSQL("CREATE TABLE IF NOT EXISTS OwnershipList(OwnershipId VARCHAR,OwnershipName VARCHAR);");

        Cursor cursor1 = db_Ownership.rawQuery("SELECT * FROM OwnershipList WHERE OwnershipId='"+SyncSlnoOwnership+"'", null);
        int x = cursor1.getCount();
        Log.e("tag","SyncOwnership count="+x);

        for(int s=0;s<str_Id.size();s++) {
            Log.e("tag","str_OwnershipId="+str_Id.get(s)+"str_OwnershipName="+str_Name.get(s));
            String SQLiteQuery = "INSERT INTO OwnershipList (OwnershipId, OwnershipName)" +
                    " VALUES ('" + str_Id.get(s) + "','" + str_Name.get(s) + "');";
            db_Ownership.execSQL(SQLiteQuery);
        }
        db_Ownership.close();
    }

    public void DBCreate_Investment_insert_2SQLiteDB(ArrayList<String> str_Id, ArrayList<String> str_Name){
        SQLiteDatabase db_Investment = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_Investment.execSQL("CREATE TABLE IF NOT EXISTS InvestmentList(InvestmentId VARCHAR,InvestmentName VARCHAR);");

        Cursor cursor1 = db_Investment.rawQuery("SELECT * FROM InvestmentList WHERE InvestmentId='"+SyncSlnoInvestment+"'", null);
        int x = cursor1.getCount();
        Log.e("tag","SyncInvestment count="+x);

        for(int s=0;s<str_Id.size();s++) {
            Log.e("tag","str_InvestmentId="+str_Id.get(s)+"str_InvestmentName="+str_Name.get(s));
            String SQLiteQuery = "INSERT INTO InvestmentList (InvestmentId, InvestmentName)" +
                    " VALUES ('" + str_Id.get(s) + "','" + str_Name.get(s) + "');";
            db_Investment.execSQL(SQLiteQuery);
        }
        db_Investment.close();
    }

    public class GetInitialInvestmentDetails extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;
       // ProgressDialog progressDialog;

        //  private ProgressBar progressBar;

        GetInitialInvestmentDetails (Context ctx){
            context = ctx;
       //     progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];
            String METHOD_NAME = "Get_Initial_Investment";
            String SOAP_ACTION1 = "http://mis.navodyami.org/Get_Initial_Investment";
            String NAMESPACE = "http://mis.navodyami.org/";

            try{

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("Sync_Slno",SyncSlnoInvestment);
                Log.d("tag","request GetInvestment:"+request.toString());

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

                envelope.dotNet = true;
                //Set output SOAP object
                envelope.setOutputSoapObject(request);
                //Create HTTP call object
                //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
                HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Login.toString().trim());
                //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

                try
                {
                    androidHttpTransport.call(SOAP_ACTION1, envelope);
                    SoapObject response = (SoapObject) envelope.getResponse();
                    Log.d("tag","GetInvestment response:"+response.toString());

                    return response;

                }

                catch(OutOfMemoryError ex){
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(BottomActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                catch (Exception t) {
                    Log.e("request fail", "> " + t.getMessage().toString());

                    t.printStackTrace();
                    final String exceptionStr = t.getMessage().toString();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(BottomActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            catch(OutOfMemoryError ex){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(BottomActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }

            catch (Exception t) {
                Log.d("exception outside",t.getMessage().toString());

                t.printStackTrace();
                final String exceptionStr = t.getMessage().toString();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(BottomActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;


            //Log.d("Soap response is",response.toString());

            //return response;
        }

        @Override
        protected void onPreExecute() {
           /* progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/

           /* progressDialog.setMessage("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();*/
        }

        @Override
        protected void onPostExecute(SoapObject result) {
            /* progressBar.setVisibility(View.GONE);*/
           // progressDialog.dismiss();

            if(result!=null) {
                String finalResult = result.toString();
                String finals = finalResult.replace("anyType", "");
                Log.d("Finals is", finals);

                ArrayList<String> NameList = new ArrayList<String>();
                ArrayList<String> IdList = new ArrayList<String>();
                // sectorList.add("Select Sector");

                Log.d("final Investment", finalResult);

                for (int i = 0; i < result.getPropertyCount(); i++) {
                    SoapObject list = (SoapObject) result.getProperty(i);
                    SoapPrimitive S_ThemeId, S_ThemeName, S_Status;
                    Object O_ThemeId, O_ThemeName, O_Status;

                    int projectTypeId = 0;

                    String str_ProjectTypeId = null, str_ProjectType = null, str_status = null;

                    if(list.getProperty("Status").toString().equals("Success")) {
                        O_ThemeId = list.getProperty("Id");
                        if (!O_ThemeId.toString().equals("anyType") && !O_ThemeId.toString().equals(null) && !O_ThemeId.toString().equals("anyType{}")) {
                            S_ThemeId = (SoapPrimitive) list.getProperty("Id");
                            Log.d("Investment Id", S_ThemeId.toString());
                            str_ProjectTypeId = O_ThemeId.toString();
                            projectTypeId = Integer.valueOf(str_ProjectTypeId);
                            IdList.add(str_ProjectTypeId);
                        }

                        O_ThemeName = list.getProperty("Name");
                        if (!O_ThemeName.toString().equals("anyType") && !O_ThemeName.toString().equals(null) && !O_ThemeName.toString().equals("anyType{}")) {
                            S_ThemeName = (SoapPrimitive) list.getProperty("Name");
                            Log.d("Investment Name", S_ThemeName.toString());
                            str_ProjectType = O_ThemeName.toString();

                            NameList.add(str_ProjectType);

                            // hashProjTypeId.put(str_ProjectType, projectTypeId);
                        }
                    }


                }

                DBCreate_Investment_insert_2SQLiteDB(IdList,NameList);
                //  uploadfromDB_Sectorlist();
                // setSectorSpinner(sectorList);

            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    public class GetOwnershipDetails extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;
//        ProgressDialog progressDialog;

        //  private ProgressBar progressBar;

        GetOwnershipDetails (Context ctx){
            context = ctx;
       //     progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];
            String METHOD_NAME = "Get_Ownership";
            String SOAP_ACTION1 = "http://mis.navodyami.org/Get_Ownership";
            String NAMESPACE = "http://mis.navodyami.org/";

            try{

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("Sync_Slno",SyncSlnoOwnership);
                Log.d("tag","request Get_Ownership:"+request.toString());

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

                envelope.dotNet = true;
                //Set output SOAP object
                envelope.setOutputSoapObject(request);
                //Create HTTP call object
                //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
                HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Login.toString().trim());
                //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

                try
                {
                    androidHttpTransport.call(SOAP_ACTION1, envelope);
                    SoapObject response = (SoapObject) envelope.getResponse();
                    Log.d("tag","Get_Ownership response:"+response.toString());

                    return response;

                }

                catch(OutOfMemoryError ex){
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(BottomActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                catch (Exception t) {
                    Log.e("request fail", "> " + t.getMessage().toString());

                    t.printStackTrace();
                    final String exceptionStr = t.getMessage().toString();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(BottomActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            catch(OutOfMemoryError ex){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(BottomActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }

            catch (Exception t) {
                Log.d("exception outside",t.getMessage().toString());

                t.printStackTrace();
                final String exceptionStr = t.getMessage().toString();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(BottomActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;


            //Log.d("Soap response is",response.toString());

            //return response;
        }

        @Override
        protected void onPreExecute() {
           /* progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/

           /* progressDialog.setMessage("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();*/
        }

        @Override
        protected void onPostExecute(SoapObject result) {
            /* progressBar.setVisibility(View.GONE);*/
         //   progressDialog.dismiss();

            if(result!=null) {
                String finalResult = result.toString();
                String finals = finalResult.replace("anyType", "");
                Log.d("Finals is", finals);

                ArrayList<String> NameList = new ArrayList<String>();
                ArrayList<String> IdList = new ArrayList<String>();
                // sectorList.add("Select Sector");

                Log.d("final KnowNavodyami", finalResult);

                for (int i = 0; i < result.getPropertyCount(); i++) {
                    SoapObject list = (SoapObject) result.getProperty(i);
                    SoapPrimitive S_ThemeId, S_ThemeName, S_Status;
                    Object O_ThemeId, O_ThemeName, O_Status;

                    int projectTypeId = 0;

                    String str_ProjectTypeId = null, str_ProjectType = null, str_status = null;

                    if(list.getProperty("Status").toString().equals("Success")) {
                        O_ThemeId = list.getProperty("Id");
                        if (!O_ThemeId.toString().equals("anyType") && !O_ThemeId.toString().equals(null) && !O_ThemeId.toString().equals("anyType{}")) {
                            S_ThemeId = (SoapPrimitive) list.getProperty("Id");
                            Log.d("Ownership Id", S_ThemeId.toString());
                            str_ProjectTypeId = O_ThemeId.toString();
                            projectTypeId = Integer.valueOf(str_ProjectTypeId);
                            IdList.add(str_ProjectTypeId);
                        }

                        O_ThemeName = list.getProperty("Name");
                        if (!O_ThemeName.toString().equals("anyType") && !O_ThemeName.toString().equals(null) && !O_ThemeName.toString().equals("anyType{}")) {
                            S_ThemeName = (SoapPrimitive) list.getProperty("Name");
                            Log.d("Ownership Name", S_ThemeName.toString());
                            str_ProjectType = O_ThemeName.toString();

                            NameList.add(str_ProjectType);

                            // hashProjTypeId.put(str_ProjectType, projectTypeId);
                        }
                    }


                }

                DBCreate_Ownership_insert_2SQLiteDB(IdList,NameList);
                //  uploadfromDB_Sectorlist();
                // setSectorSpinner(sectorList);

            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    public class GetKnowNavodyamiDetails extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;
        ProgressDialog progressDialog;

        //  private ProgressBar progressBar;

        GetKnowNavodyamiDetails (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];
            String METHOD_NAME = "Get_Know_Navodyami";
            String SOAP_ACTION1 = "http://mis.navodyami.org/Get_Know_Navodyami";
            String NAMESPACE = "http://mis.navodyami.org/";

            try{

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("Sync_Slno",SyncSlnoKnowNavodyami);
                Log.d("tag","request Get_Know_Navodyami:"+request.toString());

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

                envelope.dotNet = true;
                //Set output SOAP object
                envelope.setOutputSoapObject(request);
                //Create HTTP call object
                //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
                HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Login.toString().trim());
                //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

                try
                {
                    androidHttpTransport.call(SOAP_ACTION1, envelope);
                    SoapObject response = (SoapObject) envelope.getResponse();
                    Log.d("tag","Get_Know_Navodyami response:"+response.toString());

                    return response;

                }

                catch(OutOfMemoryError ex){
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(BottomActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                catch (Exception t) {
                    Log.e("request fail", "> " + t.getMessage().toString());

                    t.printStackTrace();
                    final String exceptionStr = t.getMessage().toString();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(BottomActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            catch(OutOfMemoryError ex){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(BottomActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }

            catch (Exception t) {
                Log.d("exception outside",t.getMessage().toString());

                t.printStackTrace();
                final String exceptionStr = t.getMessage().toString();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(BottomActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;


            //Log.d("Soap response is",response.toString());

            //return response;
        }

        @Override
        protected void onPreExecute() {
           /* progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/

            progressDialog.setMessage("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(SoapObject result) {
            /* progressBar.setVisibility(View.GONE);*/
            progressDialog.dismiss();

            if(result!=null) {
                String finalResult = result.toString();
                String finals = finalResult.replace("anyType", "");
                Log.d("Finals is", finals);

                ArrayList<String> NameList = new ArrayList<String>();
                ArrayList<String> IdList = new ArrayList<String>();
                // sectorList.add("Select Sector");

                Log.d("final KnowNavodyami", finalResult);

                for (int i = 0; i < result.getPropertyCount(); i++) {
                    SoapObject list = (SoapObject) result.getProperty(i);
                    SoapPrimitive S_ThemeId, S_ThemeName, S_Status;
                    Object O_ThemeId, O_ThemeName, O_Status;

                    int projectTypeId = 0;

                    String str_ProjectTypeId = null, str_ProjectType = null, str_status = null;

                    if(list.getProperty("Status").toString().equals("Success")) {
                        O_ThemeId = list.getProperty("Id");
                        if (!O_ThemeId.toString().equals("anyType") && !O_ThemeId.toString().equals(null) && !O_ThemeId.toString().equals("anyType{}")) {
                            S_ThemeId = (SoapPrimitive) list.getProperty("Id");
                            Log.d("Get_Social_CategoryId", S_ThemeId.toString());
                            str_ProjectTypeId = O_ThemeId.toString();
                            projectTypeId = Integer.valueOf(str_ProjectTypeId);
                            IdList.add(str_ProjectTypeId);
                        }

                        O_ThemeName = list.getProperty("Name");
                        if (!O_ThemeName.toString().equals("anyType") && !O_ThemeName.toString().equals(null) && !O_ThemeName.toString().equals("anyType{}")) {
                            S_ThemeName = (SoapPrimitive) list.getProperty("Name");
                            Log.d("Social_Category Name", S_ThemeName.toString());
                            str_ProjectType = O_ThemeName.toString();

                            NameList.add(str_ProjectType);

                            // hashProjTypeId.put(str_ProjectType, projectTypeId);
                        }
                    }


                }

                DBCreate_KnowNavodyami_insert_2SQLiteDB(IdList,NameList);
                //  uploadfromDB_Sectorlist();
                // setSectorSpinner(sectorList);

            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    public class GetSocialCategoryDetails extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;
        ProgressDialog progressDialog;

        //  private ProgressBar progressBar;

        GetSocialCategoryDetails (Context ctx){
            context = ctx;
          //  progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];
            String METHOD_NAME = "Get_Social_Category";
            String SOAP_ACTION1 = "http://mis.navodyami.org/Get_Social_Category";
            String NAMESPACE = "http://mis.navodyami.org/";

            try{

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("Sync_Slno",SyncSlnoSocialCategory);
                Log.d("tag","request Get_SocialCategory:"+request.toString());

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

                envelope.dotNet = true;
                //Set output SOAP object
                envelope.setOutputSoapObject(request);
                //Create HTTP call object
                //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
                HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Login.toString().trim());
                //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

                try
                {
                    androidHttpTransport.call(SOAP_ACTION1, envelope);
                    SoapObject response = (SoapObject) envelope.getResponse();
                    Log.d("tag","Get_SocialCategory response:"+response.toString());

                    return response;

                }

                catch(OutOfMemoryError ex){
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(BottomActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                catch (Exception t) {
                    Log.e("request fail", "> " + t.getMessage().toString());

                    t.printStackTrace();
                    final String exceptionStr = t.getMessage().toString();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(BottomActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            catch(OutOfMemoryError ex){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(BottomActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }

            catch (Exception t) {
                Log.d("exception outside",t.getMessage().toString());

                t.printStackTrace();
                final String exceptionStr = t.getMessage().toString();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(BottomActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;


            //Log.d("Soap response is",response.toString());

            //return response;
        }

        @Override
        protected void onPreExecute() {
           /* progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/

          /*  progressDialog.setMessage("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();*/
        }

        @Override
        protected void onPostExecute(SoapObject result) {
            /* progressBar.setVisibility(View.GONE);*/
        //    progressDialog.dismiss();

            if(result!=null) {
                String finalResult = result.toString();
                String finals = finalResult.replace("anyType", "");
                Log.d("Finals is", finals);

                ArrayList<String> SocialCategoryNameList = new ArrayList<String>();
                ArrayList<String> SocialCategoryIdList = new ArrayList<String>();
                // sectorList.add("Select Sector");

                Log.d("finalResultsssss", finalResult);

                for (int i = 0; i < result.getPropertyCount(); i++) {
                    SoapObject list = (SoapObject) result.getProperty(i);
                    SoapPrimitive S_ThemeId, S_ThemeName, S_Status;
                    Object O_ThemeId, O_ThemeName, O_Status;

                    int projectTypeId = 0;

                    String str_ProjectTypeId = null, str_ProjectType = null, str_status = null;

                    if(list.getProperty("Status").toString().equals("Success")) {
                        O_ThemeId = list.getProperty("Id");
                        if (!O_ThemeId.toString().equals("anyType") && !O_ThemeId.toString().equals(null) && !O_ThemeId.toString().equals("anyType{}")) {
                            S_ThemeId = (SoapPrimitive) list.getProperty("Id");
                            Log.d("Get_Social_CategoryId", S_ThemeId.toString());
                            str_ProjectTypeId = O_ThemeId.toString();
                            projectTypeId = Integer.valueOf(str_ProjectTypeId);
                            SocialCategoryIdList.add(str_ProjectTypeId);
                        }

                        O_ThemeName = list.getProperty("Name");
                        if (!O_ThemeName.toString().equals("anyType") && !O_ThemeName.toString().equals(null) && !O_ThemeName.toString().equals("anyType{}")) {
                            S_ThemeName = (SoapPrimitive) list.getProperty("Name");
                            Log.d("Social_Category Name", S_ThemeName.toString());
                            str_ProjectType = O_ThemeName.toString();

                            SocialCategoryNameList.add(str_ProjectType);

                            // hashProjTypeId.put(str_ProjectType, projectTypeId);
                        }
                    }


                }

                DBCreate_SocialCategory_insert_2SQLiteDB(SocialCategoryIdList,SocialCategoryNameList);
                //  uploadfromDB_Sectorlist();
                // setSectorSpinner(sectorList);

            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
    public class GetQualificationDetails extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;
  //      ProgressDialog progressDialog;

        //  private ProgressBar progressBar;

        GetQualificationDetails (Context ctx){
            context = ctx;
       //     progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];
            String METHOD_NAME = "Get_Qualification";
            String SOAP_ACTION1 = "http://mis.navodyami.org/Get_Qualification";
            String NAMESPACE = "http://mis.navodyami.org/";

            try{

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("Sync_Slno",SyncSlnoQualification);
                Log.d("tag","request Get_Qualification:"+request.toString());

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

                envelope.dotNet = true;
                //Set output SOAP object
                envelope.setOutputSoapObject(request);
                //Create HTTP call object
                //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
                HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Login.toString().trim());
                //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

                try
                {
                    androidHttpTransport.call(SOAP_ACTION1, envelope);
                    SoapObject response = (SoapObject) envelope.getResponse();
                    Log.d("tag","Get_Qualification response:"+response.toString());

                    return response;

                }

                catch(OutOfMemoryError ex){
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(BottomActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                catch (Exception t) {
                    Log.e("request fail", "> " + t.getMessage().toString());

                    t.printStackTrace();
                    final String exceptionStr = t.getMessage().toString();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(BottomActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            catch(OutOfMemoryError ex){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(BottomActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }

            catch (Exception t) {
                Log.d("exception outside",t.getMessage().toString());

                t.printStackTrace();
                final String exceptionStr = t.getMessage().toString();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(BottomActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;


            //Log.d("Soap response is",response.toString());

            //return response;
        }

        @Override
        protected void onPreExecute() {
           /* progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/

         /*   progressDialog.setMessage("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();*/
        }

        @Override
        protected void onPostExecute(SoapObject result) {
            /* progressBar.setVisibility(View.GONE);*/
          //  progressDialog.dismiss();

            if(result!=null) {
                String finalResult = result.toString();
                String finals = finalResult.replace("anyType", "");
                Log.d("Finals is", finals);

                ArrayList<String> QualificaitonNameList = new ArrayList<String>();
                ArrayList<String> QualificaitonIdList = new ArrayList<String>();
                // sectorList.add("Select Sector");

                Log.d("finalResultsssss", finalResult);

                for (int i = 0; i < result.getPropertyCount(); i++) {
                    SoapObject list = (SoapObject) result.getProperty(i);
                    SoapPrimitive S_ThemeId, S_ThemeName, S_Status;
                    Object O_ThemeId, O_ThemeName, O_Status;

                    int projectTypeId = 0;

                    String str_ProjectTypeId = null, str_ProjectType = null, str_status = null;

                    if(list.getProperty("Status").toString().equals("Success")) {
                        O_ThemeId = list.getProperty("Qualificaiton_Id");
                        if (!O_ThemeId.toString().equals("anyType") && !O_ThemeId.toString().equals(null) && !O_ThemeId.toString().equals("anyType{}")) {
                            S_ThemeId = (SoapPrimitive) list.getProperty("Qualificaiton_Id");
                            Log.d("Qualificaiton_Id", S_ThemeId.toString());
                            str_ProjectTypeId = O_ThemeId.toString();
                            projectTypeId = Integer.valueOf(str_ProjectTypeId);
                            QualificaitonIdList.add(str_ProjectTypeId);
                        }

                        O_ThemeName = list.getProperty("Name");
                        if (!O_ThemeName.toString().equals("anyType") && !O_ThemeName.toString().equals(null) && !O_ThemeName.toString().equals("anyType{}")) {
                            S_ThemeName = (SoapPrimitive) list.getProperty("Name");
                            Log.d("Qualificaiton Name", S_ThemeName.toString());
                            str_ProjectType = O_ThemeName.toString();

                            QualificaitonNameList.add(str_ProjectType);

                           // hashProjTypeId.put(str_ProjectType, projectTypeId);
                        }
                    }


                }

                DBCreate_Qualification_insert_2SQLiteDB(QualificaitonIdList,QualificaitonNameList);
                //  uploadfromDB_Sectorlist();
                // setSectorSpinner(sectorList);

            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
    public class GetSectorDetails extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;
 //       ProgressDialog progressDialog;

        //  private ProgressBar progressBar;

        GetSectorDetails (Context ctx){
            context = ctx;
      //      progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];
            String METHOD_NAME = "Get_Sector";
            String SOAP_ACTION1 = "http://mis.navodyami.org/Get_Sector";
            String NAMESPACE = "http://mis.navodyami.org/";

            try{

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("Sync_Slno",SyncSlnoSector);
                Log.d("request Sector:",request.toString());

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

                envelope.dotNet = true;
                //Set output SOAP object
                envelope.setOutputSoapObject(request);
                //Create HTTP call object
                //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
                HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Login.toString().trim());
                //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

                try
                {
                    androidHttpTransport.call(SOAP_ACTION1, envelope);
                    SoapObject response = (SoapObject) envelope.getResponse();
                    Log.d("GetThemeList:",response.toString());

                    return response;

                }

                catch(OutOfMemoryError ex){
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(BottomActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                catch (Exception t) {
                    Log.e("request fail", "> " + t.getMessage().toString());

                    t.printStackTrace();
                    final String exceptionStr = t.getMessage().toString();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(BottomActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            catch(OutOfMemoryError ex){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(BottomActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }

            catch (Exception t) {
                Log.d("exception outside",t.getMessage().toString());

                t.printStackTrace();
                final String exceptionStr = t.getMessage().toString();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(BottomActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;


            //Log.d("Soap response is",response.toString());

            //return response;
        }

        @Override
        protected void onPreExecute() {
           /* progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/

     /*       progressDialog.setMessage("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();*/
        }

        @Override
        protected void onPostExecute(SoapObject result) {
            /* progressBar.setVisibility(View.GONE);*/
     //       progressDialog.dismiss();

            if(result!=null) {
                String finalResult = result.toString();
                String finals = finalResult.replace("anyType", "");
                Log.d("Finals is", finals);

                ArrayList<String> sectorNameList = new ArrayList<String>();
                ArrayList<String> sectorIdList = new ArrayList<String>();
                // sectorList.add("Select Sector");

                Log.d("finalResultsssss", finalResult);

                for (int i = 0; i < result.getPropertyCount(); i++) {
                    SoapObject list = (SoapObject) result.getProperty(i);
                    SoapPrimitive S_ThemeId, S_ThemeName, S_Status;
                    Object O_ThemeId, O_ThemeName, O_Status;

                    int projectTypeId = 0;

                    String str_ProjectTypeId = null, str_ProjectType = null, str_status = null;

                    if(list.getProperty("Status").toString().equals("Success")) {
                        O_ThemeId = list.getProperty("SectorId");
                        if (!O_ThemeId.toString().equals("anyType") && !O_ThemeId.toString().equals(null) && !O_ThemeId.toString().equals("anyType{}")) {
                            S_ThemeId = (SoapPrimitive) list.getProperty("SectorId");
                            Log.d("SectorId", S_ThemeId.toString());
                            str_ProjectTypeId = O_ThemeId.toString();
                            projectTypeId = Integer.valueOf(str_ProjectTypeId);
                            sectorIdList.add(str_ProjectTypeId);
                        }

                        O_ThemeName = list.getProperty("SectorName");
                        if (!O_ThemeName.toString().equals("anyType") && !O_ThemeName.toString().equals(null) && !O_ThemeName.toString().equals("anyType{}")) {
                            S_ThemeName = (SoapPrimitive) list.getProperty("SectorName");
                            Log.d("SectorName", S_ThemeName.toString());
                            str_ProjectType = O_ThemeName.toString();

                            sectorNameList.add(str_ProjectType);

                            hashProjTypeId.put(str_ProjectType, projectTypeId);
                        }
                    }


                }

                DBCreate_Sector_insert_2SQLiteDB(sectorIdList,sectorNameList);
              //  uploadfromDB_Sectorlist();
                // setSectorSpinner(sectorList);

            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    public class GetMasterDetails extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;
        ProgressDialog progressDialog;

        //private ProgressBar progressBar;

        GetMasterDetails (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];
            String METHOD_NAME = "Sync_Village_SyncSlno";
            String SOAP_ACTION1 = "http://mis.navodyami.org/Sync_Village_SyncSlno";
            String NAMESPACE = "http://mis.navodyami.org/";

            try{

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                // String collgId="302",AcadId="5";

                Log.e("tag","req SyncSlno=="+SyncSlno);
                request.addProperty("Sync_Slno",SyncSlno);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

                envelope.dotNet = true;
                //Set output SOAP object
                Log.e("tag","request Master data="+request);
                envelope.setOutputSoapObject(request);
                //Create HTTP call object
                //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
                HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Login.toString().trim());
                //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

                try {
                    androidHttpTransport.call(SOAP_ACTION1, envelope);
                    SoapObject response = (SoapObject) envelope.getResponse();
                    Log.d("Master list:", response.toString());

                    return response;

                    // }
                    //    else {
                    // Toast.makeText(AddEnquiryActivity.this, "There are no syncing data", Toast.LENGTH_LONG).show();
                    //  }
                    //   adapter_collg.notifyDataSetChanged();

                    //     Log.e("tag","collegeList="+collegeList);

                    //return response;

                }

                catch(OutOfMemoryError ex){
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(BottomActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                catch (Exception t) {
                    Log.e("request fail", "> " + t.getMessage().toString());

                    t.printStackTrace();
                    final String exceptionStr = t.getMessage().toString();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(BottomActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            catch(OutOfMemoryError ex){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(BottomActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }

            catch (Exception t) {
                Log.d("exception outside",t.getMessage().toString());

                t.printStackTrace();
                final String exceptionStr = t.getMessage().toString();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(BottomActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;


            //Log.d("Soap response is",response.toString());

            //return response;
        }

        @Override
        protected void onPreExecute() {
           /* progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/

            progressDialog.setMessage("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(SoapObject result) {
            /* progressBar.setVisibility(View.GONE);*/
            progressDialog.dismiss();

            SoapObject soapObj_Village1 = (SoapObject) result.getProperty("Village");


            SoapObject soapObj_VillageList = (SoapObject) soapObj_Village1.getProperty(0);

            Log.e("tag", "VillageStatus=" + soapObj_VillageList.getProperty("VillageStatus"));

          /*  VillageStatus= soapObj_VillageList.getProperty("VillageStatus");
            Log.e("tag", "VillageStatus=" + VillageStatus);
*/
            if (soapObj_VillageList.getProperty("VillageStatus").toString().equals("Success")) {

                //  if(soapObj_VillageList.getProperty("VillageStatus").equals("Success")){
                SoapObject soapObj = (SoapObject) result.getProperty("State");

                if (!soapObj.toString().equals("anyType{}") && !soapObj.toString().equals(null)) {

                    SoapPrimitive S_State_Id, S_StateName, S_StateStatus;
                    Object O_State_Id, O_StateName, O_StateStatus;
                    String str_State_Id = null, str_StateName = null, str_StateStatus = null;
                    //count2 = soapObj.getPropertyCount();
                    //  collegeList.clear();
                    Log.e("tag", "soapObj.getPropertyCount()==" + soapObj.getPropertyCount());
                    // Log.e("tag","soapObj.getPropertyCount()-1=="+soapObj.getPropertyCount()-1);

                    for (int count1 = 0; count1 < soapObj.getPropertyCount(); count1++) {
                        SoapObject ProjectList = (SoapObject) soapObj.getProperty(count1);
                        Log.d("statelist", ProjectList.toString());
                        Log.d("statelist count", String.valueOf(soapObj.getPropertyCount()));


                        //   if (count1 != 0) {
                        O_State_Id = ProjectList.getProperty("State_Id");
                        Log.e("tag", "State_Id==" + O_State_Id);
                        if (!O_State_Id.toString().equals("anyType{}") && !O_State_Id.toString().equals(null)) {
                            S_State_Id = (SoapPrimitive) ProjectList.getProperty("State_Id");
                            str_State_Id = S_State_Id.toString();
                            Log.e("tag", "str_State_Id==" + str_State_Id);
                        }
                        O_StateName = ProjectList.getProperty("StateName");
                        Log.e("tag", "StateName==" + O_StateName);
                        if (!O_StateName.toString().equals("anyType{}") && !O_StateName.toString().equals(null)) {
                            S_StateName = (SoapPrimitive) ProjectList.getProperty("StateName");
                            str_StateName = S_StateName.toString();
                            Log.e("tag", "str_StateName==" + str_StateName);
                        }
                        O_StateStatus = ProjectList.getProperty("StateStatus");
                        Log.e("tag", "StateStatus==" + O_StateStatus);
                        if (!O_StateStatus.toString().equals("anyType{}") && !O_StateStatus.toString().equals(null)) {
                            S_StateStatus = (SoapPrimitive) ProjectList.getProperty("StateStatus");
                            str_StateStatus = S_StateStatus.toString();
                            Log.e("tag", "str_StateStatus==" + str_StateStatus);
                        }

                        Class_StateListDetails class_stateListDetails = new Class_StateListDetails(str_State_Id, str_StateName, str_StateStatus);
                        stateList.add(class_stateListDetails);
                        DBCreate_Statedetails_insert_2SQLiteDB(str_State_Id, str_StateName, str_StateStatus);
                        //  }
                    }
                }

                SoapObject soapObj_district = (SoapObject) result.getProperty("District");

                if (!soapObj_district.toString().equals("anyType{}") && !soapObj_district.toString().equals(null)) {

                    SoapPrimitive S_DistrictId, S_DistrictName, S_Stateid;
                    Object O_DistrictId, O_DistrictName, O_Stateid;
                    String str_DistrictId = null, str_DistrictName = null, str_Stateid = null;
                    //count2 = soapObj.getPropertyCount();
                    //  collegeList.clear();
                    Log.e("tag", "soapObj_district.getPropertyCount()==" + soapObj_district.getPropertyCount());
                    // Log.e("tag","soapObj.getPropertyCount()-1=="+soapObj.getPropertyCount()-1);

                    for (int count1 = 0; count1 < soapObj_district.getPropertyCount(); count1++) {
                        SoapObject DistrictList = (SoapObject) soapObj_district.getProperty(count1);
                        Log.d("DistrictList", DistrictList.toString());
                        Log.d("DistrictList count", String.valueOf(soapObj_district.getPropertyCount()));


                        //    if (count1 != 0) {
                        O_DistrictId = DistrictList.getProperty("DistrictId");
                        Log.e("tag", "DistrictId==" + O_DistrictId);
                        if (!O_DistrictId.toString().equals("anyType{}") && !O_DistrictId.toString().equals(null)) {
                            S_DistrictId = (SoapPrimitive) DistrictList.getProperty("DistrictId");
                            str_DistrictId = S_DistrictId.toString();
                            Log.e("tag", "str_DistrictId==" + str_DistrictId);
                        }
                        O_DistrictName = DistrictList.getProperty("DistrictName");
                        Log.e("tag", "DistrictName==" + O_DistrictName);
                        if (!O_DistrictName.toString().equals("anyType{}") && !O_DistrictName.toString().equals(null)) {
                            S_DistrictName = (SoapPrimitive) DistrictList.getProperty("DistrictName");
                            str_DistrictName = S_DistrictName.toString();
                            Log.e("tag", "str_DistrictName==" + str_DistrictName);
                        }
                        O_Stateid = DistrictList.getProperty("Stateid");
                        Log.e("tag", "Stateid==" + O_Stateid);
                        if (!O_Stateid.toString().equals("anyType{}") && !O_Stateid.toString().equals(null)) {
                            S_Stateid = (SoapPrimitive) DistrictList.getProperty("Stateid");
                            str_Stateid = S_Stateid.toString();
                            Log.e("tag", "str_Stateid==" + str_Stateid);
                        }

                        Class_DistrictListDetails class_districtListDetails = new Class_DistrictListDetails(str_DistrictId, str_DistrictName, str_Stateid);
                        districtList.add(class_districtListDetails);
                        DBCreate_Districtdetails_insert_2SQLiteDB(str_DistrictId, str_DistrictName, str_Stateid);
                        //   }
                    }
                }
                SoapObject soapObj_Taluka = (SoapObject) result.getProperty("Taluka");

                if (!soapObj_Taluka.toString().equals("anyType{}") && !soapObj_Taluka.toString().equals(null)) {

                    SoapPrimitive S_Taluka_Id, S_Taluk_Name, S_District_Id;
                    Object O_Taluka_Id, O_Taluk_Name, O_District_Id;
                    String str_Taluka_Id = null, str_Taluk_Name = null, str_District_Id = null;
                    //count2 = soapObj.getPropertyCount();
                    //  collegeList.clear();
                    Log.e("tag", "soapObj_Taluka.getPropertyCount()==" + soapObj_Taluka.getPropertyCount());
                    // Log.e("tag","soapObj.getPropertyCount()-1=="+soapObj.getPropertyCount()-1);

                    for (int count1 = 0; count1 < soapObj_Taluka.getPropertyCount(); count1++) {
                        SoapObject TalukaList = (SoapObject) soapObj_Taluka.getProperty(count1);
                        Log.d("TalukaList", TalukaList.toString());
                        Log.d("TalukaList count", String.valueOf(soapObj_Taluka.getPropertyCount()));


                        //    if (count1 != 0) {
                        O_Taluka_Id = TalukaList.getProperty("Taluka_Id");
                        Log.e("tag", "Taluka_Id==" + O_Taluka_Id);
                        if (!O_Taluka_Id.toString().equals("anyType{}") && !O_Taluka_Id.toString().equals(null)) {
                            S_Taluka_Id = (SoapPrimitive) TalukaList.getProperty("Taluka_Id");
                            str_Taluka_Id = S_Taluka_Id.toString();
                            Log.e("tag", "str_Taluka_Id==" + str_Taluka_Id);
                        }
                        O_Taluk_Name = TalukaList.getProperty("Taluk_Name");
                        Log.e("tag", "Taluk_Name==" + O_Taluk_Name);
                        if (!O_Taluk_Name.toString().equals("anyType{}") && !O_Taluk_Name.toString().equals(null)) {
                            S_Taluk_Name = (SoapPrimitive) TalukaList.getProperty("Taluk_Name");
                            str_Taluk_Name = S_Taluk_Name.toString();
                            Log.e("tag", "str_Taluk_Name==" + str_Taluk_Name);
                        }
                        O_District_Id = TalukaList.getProperty("District_Id");
                        Log.e("tag", "District_Id==" + O_District_Id);
                        if (!O_District_Id.toString().equals("anyType{}") && !O_District_Id.toString().equals(null)) {
                            S_District_Id = (SoapPrimitive) TalukaList.getProperty("District_Id");
                            str_District_Id = S_District_Id.toString();
                            Log.e("tag", "str_District_Id==" + str_District_Id);
                        }

                        Class_TalukListDetails class_talukListDetails = new Class_TalukListDetails(str_Taluka_Id, str_Taluk_Name, str_District_Id);
                        talukList.add(class_talukListDetails);
                        DBCreate_Talukdetails_insert_2SQLiteDB(str_Taluka_Id, str_Taluk_Name, str_District_Id);
                        //    }
                    }
                }

                SoapObject soapObj_Village = (SoapObject) result.getProperty("Village");
                if (!soapObj_Village.toString().equals("anyType{}") && !soapObj_Village.toString().equals(null)) {

                    SoapPrimitive S_Village_Id, S_Village_Name, S_Taluka_Id, S_SyncSlno;
                    Object O_Village_Id, O_Village_Name, O_Taluka_Id, O_SyncSlno;
                    String str_Village_Id = null, str_Village_Name = null, str_Taluka_Id = null, str_SyncSlno = null;
                    //count2 = soapObj.getPropertyCount();
                    //  collegeList.clear();
                    Log.e("tag", "soapObj_Village.getPropertyCount()==" + soapObj_Village.getPropertyCount());
                    // Log.e("tag","soapObj.getPropertyCount()-1=="+soapObj.getPropertyCount()-1);

                    for (int count1 = 0; count1 < soapObj_Village.getPropertyCount(); count1++) {
                        SoapObject VillageList = (SoapObject) soapObj_Village.getProperty(count1);
                        Log.d("VillageList", VillageList.toString());
                        Log.d("VillageList count", String.valueOf(soapObj_Village.getPropertyCount()));


                        //   if (count1 != 0) {
                        O_Village_Id = VillageList.getProperty("Village_Id");
                        Log.e("tag", "Village_Id==" + O_Village_Id);
                        if (!O_Village_Id.toString().equals("anyType{}") && !O_Village_Id.toString().equals(null)) {
                            S_Village_Id = (SoapPrimitive) VillageList.getProperty("Village_Id");
                            str_Village_Id = S_Village_Id.toString();
                            Log.e("tag", "str_Village_Id==" + str_Village_Id);
                        }
                        O_Village_Name = VillageList.getProperty("Village_Name");
                        Log.e("tag", "Village_Name==" + O_Village_Name);
                        if (!O_Village_Name.toString().equals("anyType{}") && !O_Village_Name.toString().equals(null)) {
                            S_Village_Name = (SoapPrimitive) VillageList.getProperty("Village_Name");
                            str_Village_Name = S_Village_Name.toString();
                            Log.e("tag", "str_Village_Name==" + str_Village_Name);
                        }
                        O_Taluka_Id = VillageList.getProperty("Taluka_Id");
                        Log.e("tag", "Taluka_Id==" + O_Taluka_Id);
                        if (!O_Taluka_Id.toString().equals("anyType{}") && !O_Taluka_Id.toString().equals(null)) {
                            S_Taluka_Id = (SoapPrimitive) VillageList.getProperty("Taluka_Id");
                            str_Taluka_Id = S_Taluka_Id.toString();
                            Log.e("tag", "str_Taluka_Id==" + str_Taluka_Id);
                        }
                        O_SyncSlno = VillageList.getProperty("SyncSlno");
                        Log.e("tag", "SyncSlno==" + O_SyncSlno);
                        if (!O_SyncSlno.toString().equals("anyType{}") && !O_SyncSlno.toString().equals(null)) {
                            S_SyncSlno = (SoapPrimitive) VillageList.getProperty("SyncSlno");
                            str_SyncSlno = S_SyncSlno.toString();
                            Log.e("tag", "str_SyncSlno==" + str_SyncSlno);
                        }

                        Class_VillageListDetails class_villageListDetails = new Class_VillageListDetails(str_Village_Id, str_Village_Name, str_Taluka_Id, str_SyncSlno);
                        villageList.add(class_villageListDetails);
                        DBCreate_Villagedetails_insert_2SQLiteDB(str_Village_Id, str_Village_Name, str_Taluka_Id, str_SyncSlno);
                        //    }
                    }
                }
            }else{
                Toast.makeText(BottomActivity.this, "There are no syncing data", Toast.LENGTH_LONG).show();
            }
        /*    if (VillageStatus.equals("Success")) {
               // if(result!=null) {
                    String finalResult = result.toString();
                    uploadfromDB_Statelist();
                    uploadfromDB_Districtlist();
                    uploadfromDB_Taluklist();
                    uploadfromDB_Villagelist();
               // }

            }
            else {
                Toast.makeText(AddEnquiryActivity.this, "There are no syncing data", Toast.LENGTH_LONG).show();
            }*/
          /*  uploadfromDB_Statelist();
            uploadfromDB_Districtlist();
            uploadfromDB_Taluklist();
            uploadfromDB_Villagelist();*/
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    public void DBCreate_Statedetails_insert_2SQLiteDB(String str_stateID, String str_statename, String state_status) {
        SQLiteDatabase db_statelist = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_statelist.execSQL("CREATE TABLE IF NOT EXISTS StateList(StateID VARCHAR,StateName VARCHAR,state_status VARCHAR);");

      /*  String Query = "Select * from StateList where StateID = " + "'" + str_stateID + "'";
        Cursor cursor = db_statelist.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            //return false;
        }else {
            String SQLiteQuery = "INSERT INTO StateList (StateID,StateName,state_status)" +
                    " VALUES ('" + str_stateID + "','" + str_statename + "','" + state_status + "');";
            db_statelist.execSQL(SQLiteQuery);
        }
*/
        String SQLiteQuery = "INSERT INTO StateList (StateID, StateName, state_status)"+
                "SELECT * FROM (SELECT '"+str_stateID+"', '"+str_statename+"', '"+state_status+"') AS tmp "+
                "WHERE NOT EXISTS ("+
                "SELECT StateID FROM StateList WHERE StateID = '"+str_stateID+"'"+
                ")";
        db_statelist.execSQL(SQLiteQuery);
        /*insert into tablename (code)
        Select '1448523' Where not exists(select * from tablename where code='1448523')*/

       /* String SQLiteQuery = "INSERT INTO StateList (StateID,StateName,state_status) Select StateID Where not exists(select * from StateList where StateID='"+str_stateID+"')";
        db_statelist.execSQL(SQLiteQuery);*/

       /* String SQLiteQuery = "INSERT INTO StateList (StateID,StateName,state_status)" +
                " VALUES ('" + str_stateID + "','" + str_statename + "','" + state_status + "');";
        db_statelist.execSQL(SQLiteQuery);*/

//        Log.e("str_stateID DB", str_stateID);
//        Log.e("str_statename DB", str_statename);
//        Log.e("str_stateyearid DB", str_yearid);
        db_statelist.close();
    }
    public void DBCreate_Districtdetails_insert_2SQLiteDB(String str_districtID, String str_districtname, String str_stateid) {
        SQLiteDatabase db_districtlist = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_districtlist.execSQL("CREATE TABLE IF NOT EXISTS DistrictList(DistrictID VARCHAR,DistrictName VARCHAR,Distr_Stateid VARCHAR);");


       /* String SQLiteQuery = "INSERT INTO DistrictList (DistrictID, DistrictName,Distr_Stateid)" +
                " VALUES ('" + str_districtID + "','" + str_districtname + "','" + str_stateid + "');";
        db_districtlist.execSQL(SQLiteQuery);*/
        /*Cursor cursor1 = db_districtlist.rawQuery("SELECT DistrictID FROM DistrictList WHERE DistrictID = '"+str_districtID+"'", null);
        int x = cursor1.getCount();
        Log.e("Select statment count=", String.valueOf(x));*/

        String SQLiteQuery = "INSERT INTO DistrictList (DistrictID, DistrictName, Distr_Stateid)"+
                "SELECT * FROM (SELECT '"+str_districtID+"', '"+str_districtname+"', '"+str_stateid+"') AS tmp "+
                "WHERE NOT EXISTS ("+
                "SELECT DistrictID FROM DistrictList WHERE DistrictID = '"+str_districtID+"'"+
                ")";
        db_districtlist.execSQL(SQLiteQuery);



//        Log.e("str_districtID DB", str_districtID);
//        Log.e("str_districtname DB", str_districtname);
//        Log.e("str_yearid DB", str_yearid);
//        Log.e("str_stateid DB", str_stateid);
        db_districtlist.close();
    }
    public void DBCreate_Talukdetails_insert_2SQLiteDB(String str_talukID, String str_talukname, String str_districtid) {

        SQLiteDatabase db_taluklist = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_taluklist.execSQL("CREATE TABLE IF NOT EXISTS TalukList(TalukID VARCHAR,TalukName VARCHAR,Taluk_districtid VARCHAR);");


   /*     String SQLiteQuery = "INSERT INTO TalukList (TalukID, TalukName,Taluk_districtid)" +
                " VALUES ('" + str_talukID + "','" + str_talukname + "','" + str_districtid + "');";
        db_taluklist.execSQL(SQLiteQuery);*/

        String SQLiteQuery = "INSERT INTO TalukList (TalukID, TalukName, Taluk_districtid)"+
                "SELECT * FROM (SELECT '"+str_talukID+"', '"+str_talukname+"', '"+str_districtid+"') AS tmp "+
                "WHERE NOT EXISTS ("+
                "SELECT TalukID FROM TalukList WHERE TalukID = '"+str_talukID+"'"+
                ")";
        db_taluklist.execSQL(SQLiteQuery);
//        Log.e("str_talukID DB", str_talukID);
//        Log.e("str_talukname DB", str_talukname);
//        Log.e("str_districtid DB", str_districtid);
        db_taluklist.close();
    }
    public void DBCreate_Villagedetails_insert_2SQLiteDB(String str_villageID, String str_village, String str_talukid,String str_SyncSlno) {
        SQLiteDatabase db_villagelist = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_villagelist.execSQL("CREATE TABLE IF NOT EXISTS VillageList(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR,SyncSlno VARCHAR);");


        /*String SQLiteQuery = "INSERT INTO VillageList (VillageID, Village,TalukID)" +
                " VALUES ('" + str_villageID + "','" + str_village + "','" + str_talukid + "');";
        db_villagelist.execSQL(SQLiteQuery);*/

        String SQLiteQuery = "INSERT INTO VillageList (VillageID, Village,TalukID,SyncSlno)"+
                "SELECT * FROM (SELECT '"+ str_villageID + "','" + str_village + "','" + str_talukid +"','" + str_SyncSlno +"') AS tmp "+
                "WHERE NOT EXISTS ("+
                "SELECT VillageID FROM VillageList WHERE VillageID = '"+str_villageID+"'"+
                ")";
        db_villagelist.execSQL(SQLiteQuery);
//        Log.e("str_villageID DB", str_villageID);
//        Log.e("str_village DB", str_village);
//        Log.e("str_talukid DB", str_talukid);
        db_villagelist.close();
    }

    //-------------------------------------- Enquiry And Application data sync ---------------------------------------------------------------------------------------------
    public  void offlineData(){
        if(isInternetPresent) {
            int EnquiryOfflineCount = Add_Edit_offlineEnquiryCount();
            int ApplicationOfflineCount = getOfflineAddOREditApplicationCount();

            AlertDialog.Builder builder = new AlertDialog.Builder(BottomActivity.this);
            builder.setTitle(" Please wait syncing offline data ");
            builder.setMessage(" ");
            alertdialog = builder.create();
            /*if(alertdialog.isShowing()){
                alertdialog.dismiss();
            }*/
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
            }
        }
    }
    public void SyncData(int enquiryOfflineCount, int applicationOfflineCount){

        SQLiteDatabase db2 = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS ListEnquiryDetails(tempId INTEGER PRIMARY KEY AUTOINCREMENT,FName VARCHAR,MobileNo VARCHAR,EmailId VARCHAR,BusinessName VARCHAR,EnquiryId VARCHAR,Status VARCHAR,YearCode VARCHAR,MName VARCHAR,LName VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,isApplicant VARCHAR,Gender VARCHAR,dataSyncStatus VARCHAR );");

        Cursor cursor1 = db2.rawQuery("SELECT DISTINCT * FROM ListEnquiryDetails WHERE dataSyncStatus='" +"offline"+"'", null);
        int EnquiryofflineCount = cursor1.getCount();
        Log.d("tag", "cursor Enquiry offline Count sync" + Integer.toString(EnquiryofflineCount));
        if(EnquiryofflineCount>0) {
            getEnquiryOfflineDataSync();
        }

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

        Cursor cursor2 = db1.rawQuery("SELECT DISTINCT * FROM CompliteApplicationDetails WHERE dataSyncStatus='" + "offline" + "'", null);
        int ApplicationOfflineCount = cursor2.getCount();
        Log.d("tag", "cursor offline Application list Count" + Integer.toString(ApplicationOfflineCount));
        if(ApplicationOfflineCount>0){
            getOfflineAddOREditApplication();
        }
        if (EnquiryofflineCount == 0 || ApplicationOfflineCount == 0) {
            offlineData();
        }
      /*  int EnquiryOfflineCount=Add_Edit_offlineEnquiryCount();
        int ApplicationOfflineCount=getOfflineAddOREditApplicationCount();*/

       // }
    }
    public int Add_Edit_offlineEnquiryCount() {

        SQLiteDatabase db2 = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS ListEnquiryDetails(tempId INTEGER PRIMARY KEY AUTOINCREMENT,FName VARCHAR,MobileNo VARCHAR,EmailId VARCHAR,BusinessName VARCHAR,EnquiryId VARCHAR,Status VARCHAR,YearCode VARCHAR,MName VARCHAR,LName VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,isApplicant VARCHAR,Gender VARCHAR,dataSyncStatus VARCHAR );");

        Cursor cursor1 = db2.rawQuery("SELECT DISTINCT * FROM ListEnquiryDetails WHERE dataSyncStatus='" +"offline"+"'", null);
        int EnquiryofflineCount = cursor1.getCount();
        Log.d("tag", "cursor Enquiry offline Count" + Integer.toString(EnquiryofflineCount));
        return EnquiryofflineCount;
       // String EnquiryIdCount = null;
       /* int newCount=0,editCount=0;
        if (x > 0) {
            if (cursor1.moveToFirst()) {

                do {
                    EnquiryIdCount = cursor1.getString(cursor1.getColumnIndex("EnquiryId"));

                    if(EnquiryIdCount.equalsIgnoreCase("0")){
                        newCount=newCount+1;
                    }else{
                        editCount=editCount+1;
                    }
                } while (cursor1.moveToNext());
            }

            Log.d("tag", "cursor offline Enquiry list Count newCount" + newCount);
            if (newCount != 0) {
                String x_str = String.valueOf(newCount);
            }
            Log.d("tag", "cursor offline Enquiry list Count editCount" + editCount);
            if (editCount != 0) {
                String x_str = String.valueOf(editCount);
            }
        }*/
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
                offlineData();
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
               offlineData();
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

/////////////////////////////////

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
                offlineData();
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

    public void SubmitAddNewApplication(){
        String URL = Class_URL.URL_Login.toString().trim();
        String METHOD_NAME = "Save_Application_Details";
        String Namespace = "http://mis.navodyami.org/", SOAPACTION = "http://mis.navodyami.org/Save_Application_Details";

        try {



            SoapObject request = new SoapObject(Namespace, METHOD_NAME);

       /* ------   API Data  ---------
       <First_Name>string</First_Name>
      <Middle_Name>string</Middle_Name>
      <Last_Name>string</Last_Name>
      <Mobile_No>string</Mobile_No>
      <Email_Id>string</Email_Id>
      <WhatApp_No>string</WhatApp_No>
      <Aadhar_No>string</Aadhar_No>
      <State_Id>string</State_Id>
      <District_Id>string</District_Id>
      <Taluka_Id>string</Taluka_Id>
      <Village_Id>string</Village_Id>
      <Gender>string</Gender>
      <DOB>string</DOB>
      <Qualification_Id>string</Qualification_Id>
      <Category_Id>string</Category_Id>
      <Economic_Status>string</Economic_Status>
      <Sector_Id>string</Sector_Id>
      <Business_Name>string</Business_Name>
      <Business_Address>string</Business_Address>
      <Years_In_Business>string</Years_In_Business>
      <Years_In_Current_Business>string</Years_In_Current_Business>
      <Earlier_Sector>string</Earlier_Sector>
      <Know_Navodyami>string</Know_Navodyami>
      <isNavodyami_Member_Before>string</isNavodyami_Member_Before>
      <Year_in_Navodyami>string</Year_in_Navodyami>
      <isUNDP_Member_Before>string</isUNDP_Member_Before>
      <Year_in_UNDP>string</Year_in_UNDP>
      <isManufacture>string</isManufacture>
      <isHave_License>string</isHave_License>
      <Which_License>string</Which_License>
      <Product1>string</Product1>
      <Product2>string</Product2>
      <Product3>string</Product3>
      <Business_Started_Year>string</Business_Started_Year>
      <Ownership>string</Ownership>
      <Which_Labour_Hire>string</Which_Labour_Hire>
      <IsHave_Labour_Outside_Family>string</IsHave_Labour_Outside_Family>
      <Which_Machine_Use>string</Which_Machine_Use>
      <Which_Machine_Have>string</Which_Machine_Have>
      <Where_Sell_Products>string</Where_Sell_Products>
      <Earn_Most_Channel>string</Earn_Most_Channel>
      <Permanent_Employees>string</Permanent_Employees>
      <TurnOver>string</TurnOver>
      <Profit>string</Profit>
      <Total_Employees>string</Total_Employees>
      <Source_Of_Business>string</Source_Of_Business>
      <Initial_Investment>string</Initial_Investment>
      <isKnowledge_Loan_Process>string</isKnowledge_Loan_Process>
      <Applied_Loan_Amount>string</Applied_Loan_Amount>
      <Sanctioned_Loan_Amount>string</Sanctioned_Loan_Amount>
      <Interest_Rate>double</Interest_Rate>
      <Repayment_Period>string</Repayment_Period>
      <Applied_At>string</Applied_At>
      <Loan_For_Product_Improvement>string</Loan_For_Product_Improvement>
      <Loan_For_Working_Expenses>string</Loan_For_Working_Expenses>
      <Loan_For_Land>string</Loan_For_Land>
      <Loan_For_Equipment>string</Loan_For_Equipment>
      <Loan_For_Finance_Trading>string</Loan_For_Finance_Trading>
      <User_Id>string</User_Id>
      <Application_Fees>int</Application_Fees>
      <Receipt_Date>string</Receipt_Date>
      <Remark>string</Remark>
      <Manual_Receipt_No>string</Manual_Receipt_No>
      <Payment_Mode>string</Payment_Mode>
      <Application_Date>string</Application_Date>*/

//--------------------------------------------Personal_Details------------------------------------------------------

            // request.addProperty("Enquiry_Id",Integer.valueOf(EnquiryId));
            //   request.addProperty("Application_Slno",Integer.valueOf(ApplicationSlno));
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


            } catch (Throwable t) {

                Log.e("request fail", "> " + t.getMessage());

            }
        } catch (Throwable t) {

            Log.e("UnRegister Error", "> " + t.getMessage());
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

    /*public void Update_DBmadeOnlineApplication(String tempId){
        SQLiteDatabase db_addEnquiry = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);

        db_addEnquiry.execSQL("CREATE TABLE IF NOT EXISTS CompliteApplicationDetails(tempId INTEGER PRIMARY KEY AUTOINCREMENT,EnquiryId VARCHAR,Application_Slno VARCHAR,Academic_Year VARCHAR,FName VARCHAR,MName VARCHAR,LName VARCHAR,MobileNo VARCHAR," +
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
                "AppliedAt VARCHAR,Repaymentperiod VARCHAR,ApplicationFees VARCHAR,VerifiedDate VARCHAR,Remark VARCHAR,Manual_Receipt_No VARCHAR,Payment_Mode VARCHAR,dataSyncStatus VARCHAR);");

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
*/

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

    public void Update(){           //String FName,String MName,String LName,String MobileNo,String EmailId,String BusinessName,String sp_strstate_ID,String sp_strdistrict_ID,String sp_strTaluk_ID,String sp_strVillage_ID,String sp_strsector_ID,String str_UserId,String Gender){
        String URL = Class_URL.URL_Login.toString().trim();
        String METHOD_NAME = "Save_Application_Personal_Details";
        String Namespace = "http://mis.navodyami.org/", SOAPACTION = "http://mis.navodyami.org/Save_Application_Personal_Details";

        try {



            SoapObject request = new SoapObject(Namespace, METHOD_NAME);

       /* ------   API Data  ---------
        <Enquiry_Id>string</Enquiry_Id><Application_Slno>string</Application_Slno><First_Name>string</First_Name><Middle_Name>string</Middle_Name>
      <Last_Name>string</Last_Name><Mobile_No>string</Mobile_No><Email_Id>string</Email_Id><WhatApp_No>string</WhatApp_No>
      <Aadhar_No>string</Aadhar_No><State_Id>string</State_Id><District_Id>string</District_Id><Taluka_Id>string</Taluka_Id>
      <Village_Id>string</Village_Id><Gender>string</Gender><DOB>string</DOB><Qualification_Id>string</Qualification_Id><Category_Id>string</Category_Id>
      <Economic_Status>string</Economic_Status><Sector_Id>string</Sector_Id><Business_Name>string</Business_Name><Business_Address>string</Business_Address>
      <Years_In_Business>string</Years_In_Business><Years_In_Current_Business>string</Years_In_Current_Business><Earlier_Sector>string</Earlier_Sector>
      <Know_Navodyami>string</Know_Navodyami><isNavodyami_Member_Before>string</isNavodyami_Member_Before><Year_in_Navodyami>string</Year_in_Navodyami>
      <isUNDP_Member_Before>string</isUNDP_Member_Before><Year_in_UNDP>string</Year_in_UNDP><User_Id>string</User_Id>*/


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

/* <Enquiry_Id>string</Enquiry_Id>
      <Application_Slno>string</Application_Slno>
      <First_Name>string</First_Name>
      <Middle_Name>string</Middle_Name>
      <Last_Name>string</Last_Name>
      <Mobile_No>string</Mobile_No>
      <Email_Id>string</Email_Id>
      <WhatApp_No>string</WhatApp_No>
      <Aadhar_No>string</Aadhar_No>
      <State_Id>string</State_Id>
      <District_Id>string</District_Id>
      <Taluka_Id>string</Taluka_Id>
      <Village_Id>string</Village_Id>
      <Gender>string</Gender>
      <DOB>string</DOB>
      <Qualification_Id>string</Qualification_Id>
      <Category_Id>string</Category_Id>
      <Economic_Status>string</Economic_Status>
      <Sector_Id>string</Sector_Id>
      <Business_Name>string</Business_Name>
      <Business_Address>string</Business_Address>
      <Years_In_Business>string</Years_In_Business>
      <Years_In_Current_Business>string</Years_In_Current_Business>
      <Earlier_Sector>string</Earlier_Sector>
      <Know_Navodyami>string</Know_Navodyami>
      <isNavodyami_Member_Before>string</isNavodyami_Member_Before>
      <Year_in_Navodyami>string</Year_in_Navodyami>
      <isUNDP_Member_Before>string</isUNDP_Member_Before>
      <Year_in_UNDP>string</Year_in_UNDP>
      <User_Id>string</User_Id>*/
            /*request.addProperty("Enquiry_Id",10);
            request.addProperty("Application_Slno",0);
            request.addProperty("First_Name", "madhu");
            request.addProperty("Middle_Name", "test");
            request.addProperty("Last_Name", "sdfn");
            request.addProperty("Mobile_No", "23456789");
            request.addProperty("Email_Id", "madhu@dfmail.org");
            request.addProperty("WhatApp_No", "8904674048");
            request.addProperty("Aadhar_No", "123456789");
            request.addProperty("State_Id", 29);
            request.addProperty("District_Id", 527);
            request.addProperty("Taluka_Id",5470);
            request.addProperty("Village_Id",602014);
            request.addProperty("Gender","M");
            request.addProperty("DOB","2000-6-12");
            request.addProperty("Qualification_Id",1);
            request.addProperty("Category_Id",1);
            request.addProperty("Economic_Status","APL");
            request.addProperty("Sector_Id",11);
            request.addProperty("Business_Name","ncjkasd");
            request.addProperty("Business_Address","sanj");
            request.addProperty("Years_In_Business",5);
            request.addProperty("Years_In_Current_Business",3);
            request.addProperty("Earlier_Sector","sacsdv");
            request.addProperty("Know_Navodyami","asd");
            request.addProperty("isNavodyami_Member_Before",1);
            request.addProperty("Year_in_Navodyami",1);
            request.addProperty("isUNDP_Member_Before",1);
            request.addProperty("Year_in_UNDP",1);
            request.addProperty("User_Id",1);*/

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

    public void SubmitBusiness(){
        String URL = Class_URL.URL_Login.toString().trim();
        String METHOD_NAME = "Save_Application_Business_Details";
        String Namespace = "http://mis.navodyami.org/", SOAPACTION = "http://mis.navodyami.org/Save_Application_Business_Details";

        try {



            SoapObject request = new SoapObject(Namespace, METHOD_NAME);

       /* ------   API Data  ---------
      <Save_Application_Business_Details xmlns="http://mis.navodyami.org/">
      <Enquiry_Id>string</Enquiry_Id>
      <Application_Slno>string</Application_Slno>
      <isManufacture>string</isManufacture>
      <isHave_License>string</isHave_License>
      <Which_License>string</Which_License>
      <Product1>string</Product1>
      <Product2>string</Product2>
      <Product3>string</Product3>
      <Business_Started_Year>string</Business_Started_Year>
      <Ownership>string</Ownership>
      <Which_Labour_Hire>string</Which_Labour_Hire>
      <IsHave_Labour_Outside_Family>string</IsHave_Labour_Outside_Family>
      <Which_Machine_Use>string</Which_Machine_Use>
      <Which_Machine_Have>string</Which_Machine_Have>
      <Where_Sell_Products>string</Where_Sell_Products>
      <Earn_Most_Channel>string</Earn_Most_Channel>
      <User_Id>string</User_Id>
      <Permanent_Employees>string</Permanent_Employees>
      <TurnOver>string</TurnOver>
      <Profit>string</Profit>
    </Save_Application_Business_Details>*/

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
                offlineData();
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

    public void SubmitCredit(){
        String URL = Class_URL.URL_Login.toString().trim();
        String METHOD_NAME = "Save_Application_Credit_Details";
        String Namespace = "http://mis.navodyami.org/", SOAPACTION = "http://mis.navodyami.org/Save_Application_Credit_Details";

        try {



            SoapObject request = new SoapObject(Namespace, METHOD_NAME);

       /* ------   API Data  ---------
      <Application_Slno>string</Application_Slno>
      <Source_Of_Business>string</Source_Of_Business>
      <Initial_Investment>string</Initial_Investment>
      <isKnowledge_Loan_Process>string</isKnowledge_Loan_Process>
      <Applied_Loan_Amount>string</Applied_Loan_Amount>
      <Sanctioned_Loan_Amount>string</Sanctioned_Loan_Amount>
      <Interest_Rate>double</Interest_Rate>
      <Repayment_Period>string</Repayment_Period>
      <Applied_At>string</Applied_At>
      <Loan_For_Product_Improvement>string</Loan_For_Product_Improvement>
      <Loan_For_Working_Expenses>string</Loan_For_Working_Expenses>
      <Loan_For_Land>string</Loan_For_Land>
      <Loan_For_Equipment>string</Loan_For_Equipment>
      <Loan_For_Finance_Trading>string</Loan_For_Finance_Trading>
      <Where_Sell_Products>string</Where_Sell_Products>
      <Earn_Most_Channel>string</Earn_Most_Channel>
      <User_Id>string</User_Id>
      <Permanent_Employees>string</Permanent_Employees>
      <TurnOver>string</TurnOver>
      <Profit>string</Profit>
    </Save_Application_Credit_Details>*/

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
    }


    @Override
    public void onBackPressed() {

        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

}
