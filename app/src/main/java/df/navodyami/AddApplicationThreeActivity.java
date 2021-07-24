package df.navodyami;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import df.navodyami.FeesModule.FeesPaymentActivity;

public class AddApplicationThreeActivity extends AppCompatActivity {

    CheckBox checkbox_Selffinanced,checkbox_Relative,checkbox_BanksMFIs,checkbox_Govtscheme,checkbox_Moneylenders,checkbox_SHG,checkbox_private;
    EditText edt_otherSOB,edt_Appliedloan,edt_sanctionedloan,edt_Interestrate,edt_Appliedat,edt_repaymentperiod,edt_verfiedDate,verfiedDateseterror_tv,edt_ApplAmtRemark,edt_ManualReceiptNo;
    String str_BusinessSource,int_knowledge="0",str_LastLoan,int_LastLoan,str_investment,str_AppliedAt,str_AppliedAmt,str_SanctionAmt,str_InterestRate,EnquiryId,str_Repaymentperiod;
    Spinner investment_sp;
    EditText ApplFees_et;
    ArrayList<String> BusinessSource_array;

    ArrayAdapter dataAdapter_Investment;
    CheckBox checkbox_productimprove,checkbox_expenses,checkbox_buildings,checkbox_equipment,checkbox_activities;
    ArrayList<Class_AddApplicationThreeDetails> addApplicationThreeList;
    Class_AddApplicationThreeDetails[] arrayObj_Class_ApplicationDetailsThree;
    Button btn_Submit;
    RadioGroup knowledge_radiogroup;
    ImageView Prev_iv;

    SharedPreferences sharedpreferencebook_usercredential_Obj;
    SharedPreferences.Editor editor_obj;
    public static final String sharedpreferencebook_usercredential = "sharedpreferencebook_usercredential";
    public static final String KeyValue_employeeid = "KeyValue_employeeid";
    String str_UserId,ApplicationSlnoNew,isApplicant,tempId;

    String status;
    String int_Product_Improvement="0";
    String int_Working_Expenses="0";
    String int_Land="0";
    String int_Equipment="0";
    String int_Finance_Trading="0",str_verfiedDate,str_ApplAmtRemark,str_Manual_Receipt_No,str_ApplFees,str_Payment_Mode;
    private int mYear, mMonth, mDay;
    private int cYear, cMonth, cDay;

    List<String> LastLoanList= new ArrayList<String>();
    List<String> BusinessSourceList = new ArrayList<String>();
    Boolean isInternetPresent = false;
    Class_InternetDectector internetDectector;

    Spinner paymentMode_sp;
    ArrayAdapter dataAdapter_paymentMode;


    String FName,LName,MName,MobileNo,EmailId,BusinessName,stateId,districtId,talukId,villegeId,sectorId,deviceType,userId,Enquiry_Id,Gender;
    String str_StateId,str_DistrictId,str_TalukaId,str_VillageId,str_SectorId;
    String Economic,whatsappNo,DOB,education, socialCatgary, businessAddress,yearInBusiness,yearInCurrentBusiness,sectorBusiness,gottoknow,UNDP,yearUNDP,navodyami,yearNavodyami,aadhar,Application_Date,street;
    Class_AddApplicationDetails[] arrayObj_Class_ApplicationDetails;
    Class_AddApplicationTwoDetails[] arrayObj_Class_ApplicationDetailsTwo;
    String manufacture, licence, whichLicence, productOne, productTwo, productThree, businessYear, ownership;
    String str_yearOne = null, female_year1, male_year1, str_yearTwo = null, female_year2, male_year2, str_yearThree=null, female_year3;
    String male_year3, labour, outsidefamilyLabour, trunover_year1, trunover_year2, trunover_year3, profit_year1;
    String profit_year2, profit_year3, machine, sell_products, last_quarter,enquiryId,total_Employee,Application_Slno,Which_Machine;
    ArrayList<String> listYears = new ArrayList<String>();
    ArrayList<String> listFemalePermentEmplyee = new ArrayList<String>();
    ArrayList<String> listMalePermentEmplyee = new ArrayList<String>();
    ArrayList<String> listTurnOver = new ArrayList<String>();
    ArrayList<String> listProfit = new ArrayList<String>();

    String isAccountVerified;
    Class_OwnershipListDetails[] arrayObj_Class_InvestmentDetails2;
    final ArrayList listInvestment = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_application_three);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_n_actionbar);
        setSupportActionBar(toolbar);
        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView title= (TextView) toolbar.findViewById(R.id.title_name);
        title.setText("Application Form");
        getSupportActionBar().setTitle("");

        sharedpreferencebook_usercredential_Obj=getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_UserId = sharedpreferencebook_usercredential_Obj.getString(KeyValue_employeeid, "").trim();

        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

        checkbox_Selffinanced = (CheckBox) findViewById(R.id.checkbox_Selffinanced);
        checkbox_Relative = (CheckBox) findViewById(R.id.checkbox_Relative);
        checkbox_BanksMFIs = (CheckBox) findViewById(R.id.checkbox_BanksMFIs);
        checkbox_Govtscheme = (CheckBox) findViewById(R.id.checkbox_Govtscheme);
        checkbox_Moneylenders = (CheckBox) findViewById(R.id.checkbox_Moneylenders);
        checkbox_SHG = (CheckBox) findViewById(R.id.checkbox_SHG);
        checkbox_private = (CheckBox) findViewById(R.id.checkbox_private);

        checkbox_productimprove = (CheckBox) findViewById(R.id.checkbox_productimprove);
        checkbox_expenses = (CheckBox) findViewById(R.id.checkbox_expenses);
        checkbox_equipment = (CheckBox) findViewById(R.id.checkbox_equipment);
        checkbox_buildings = (CheckBox) findViewById(R.id.checkbox_buildings);
        checkbox_activities = (CheckBox) findViewById(R.id.checkbox_activities);

        edt_otherSOB = (EditText) findViewById(R.id.edt_otherSOB);
        edt_Appliedat = (EditText) findViewById(R.id.edt_Appliedat);
        edt_Appliedloan = (EditText) findViewById(R.id.edt_Appliedloan);
        edt_Interestrate = (EditText) findViewById(R.id.edt_Interestrate);
        edt_sanctionedloan = (EditText) findViewById(R.id.edt_sanctionedloan);
        edt_repaymentperiod = (EditText) findViewById(R.id.edt_repaymentperiod);

        edt_verfiedDate = (EditText) findViewById(R.id.edt_verfiedDate);
        verfiedDateseterror_tv =(EditText)findViewById(R.id.verfiedDateseterror_tv);
        edt_ApplAmtRemark = (EditText) findViewById(R.id.edt_ApplAmtRemark);
        edt_ManualReceiptNo = (EditText) findViewById(R.id.edt_ManualReceiptNo);
        ApplFees_et = (EditText) findViewById(R.id.ApplFees_et);

        investment_sp = (Spinner) findViewById(R.id.investment_sp);
        btn_Submit =(Button) findViewById(R.id.btn_submit);
        knowledge_radiogroup = (RadioGroup) findViewById(R.id.knowledge_radiogroup);
        Prev_iv = (ImageView) findViewById(R.id.Prev_iv);

        paymentMode_sp = (Spinner) findViewById(R.id.paymentMode_sp);

        Intent intent = getIntent();
        EnquiryId=intent.getStringExtra("EnquiryId");
        ApplicationSlnoNew = intent.getStringExtra("Application_SlnoNew");
        isApplicant=intent.getStringExtra("isApplicant");
        tempId=intent.getStringExtra("tempId");

        Log.e("tag","ApplicationSlnoNew three="+ApplicationSlnoNew);
        Log.e("tag","EnquiryId three="+EnquiryId);
        Log.e("tag","tempId three="+tempId);

        addApplicationThreeList = new ArrayList<>();
        BusinessSource_array = new ArrayList<>();

        uploadfromDB_InvestmentList();
        setInvestmentSpinner();
        setPaymentModeSpinner();

        Date date = new Date();
        Log.i("Tag_time", "date1=" + date);
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormatDisplay= new SimpleDateFormat("dd-MM-yyyy");
        try {
            // Date d=dateFormat.parse(date);
            // String PresentDayStr = dateFormat.format(date);
            System.out.println("Formated"+dateFormat.format(date));
            edt_verfiedDate.setText(dateFormatDisplay.format(date).toString());
            str_verfiedDate=dateFormat.format(date);

        }
        catch(Exception e) {
            //java.text.ParseException: Unparseable date: Geting error
            System.out.println("Excep"+e);
        }

       /* Intent intent = getIntent();
        EnquiryId=intent.getStringExtra("EnquiryId");*/

        investment_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                str_investment = investment_sp.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        BusinessSourceList.clear();
        LastLoanList.clear();
        //  uploadfromDB_AddApplicationThreeDetails();
        uploadfromDB_CompliteApplicationThreeDetails();
/*
        str_AppliedAmt=edt_Appliedloan.getText().toString();
        if(str_AppliedAmt!=null||edt_Appliedloan.getText().toString().length() != 0) {
            edt_sanctionedloan.setFilters(new InputFilter[]{new InputFilterMinMax("1", str_AppliedAmt)});
        }*/

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   Intent intent1 = new Intent(AddApplicationThreeActivity.this, AddApplicationOneActivity.class);
                //   intent1.putExtra("EnquiryId",holder.mEnquiryId.getText());
                startActivity(intent1);
*/
                BusinessSource_array.clear();
                str_BusinessSource="";
                checkboxBusinessSource();
                checkboxLastLoan();

                int size=BusinessSource_array.size();
                /*if(size>1) {
                    str_BusinessSource = BusinessSource_array.get(size - 1);
                }*/
                for (int i=0;i<BusinessSource_array.size();i++) {
                    //  i++;
                    str_BusinessSource+=BusinessSource_array.get(i);
                    str_BusinessSource+=",";

                }
                Log.e("tag","str_BusinessSource=="+str_BusinessSource.toString());
                str_AppliedAmt=edt_Appliedloan.getText().toString();
                str_AppliedAt=edt_Appliedat.getText().toString();
                if(str_AppliedAt==null||str_AppliedAt.equalsIgnoreCase("")){
                    str_AppliedAt="0";
                }
                if(str_AppliedAmt==null||str_AppliedAmt.equalsIgnoreCase("")){
                    str_AppliedAmt="0";
                }
                str_SanctionAmt=edt_sanctionedloan.getText().toString();
                if(str_SanctionAmt==null||str_SanctionAmt.equalsIgnoreCase("")){
                    str_SanctionAmt="0";
                }
                str_InterestRate=edt_Interestrate.getText().toString();
                if(str_InterestRate==null||str_InterestRate.equalsIgnoreCase("")){
                    str_InterestRate="0";
                }
                str_Repaymentperiod=edt_repaymentperiod.getText().toString();
                //str_verfiedDate=edt_verfiedDate.getText().toString();
                str_ApplAmtRemark=edt_ApplAmtRemark.getText().toString();
                str_Manual_Receipt_No=edt_ManualReceiptNo.getText().toString();
                str_ApplFees=ApplFees_et.getText().toString();
                if(str_ApplFees==null||str_ApplFees.equalsIgnoreCase("")){
                    str_ApplFees="0";
                }

                str_Payment_Mode= paymentMode_sp.getSelectedItem().toString();
            /* if(edt_Appliedat.getText().length()==0){
                 str_AppliedAmt="0";
             }*/
                if(edt_Appliedloan.getText().length()==0){
                    str_AppliedAmt="0";
                }
                if(edt_sanctionedloan.getText().length()==0){
                    str_SanctionAmt="0";
                }
                if(edt_Interestrate.getText().length()==0){
                    str_InterestRate="0";
                }

                if(validation()) {
                    internetDectector = new Class_InternetDectector(getApplicationContext());
                    isInternetPresent = internetDectector.isConnectingToInternet();

                    if(isInternetPresent) {
                        if(EnquiryId.equalsIgnoreCase("0")){
                            //GetDataFromDB_AddApplicationDetails();
                            // GetDatafromDB_AddApplicationTwoDetails();
                            GetDataFromDB_CompliteApplicationDetails(EnquiryId,ApplicationSlnoNew,tempId);
                            AddNewApplicationDetails addNewApplicationDetails = new AddNewApplicationDetails(AddApplicationThreeActivity.this);
                            addNewApplicationDetails.execute();
                        }else {
                            AddApplicationThreeDetails addApplicationThreeDetails = new AddApplicationThreeDetails(AddApplicationThreeActivity.this);
                            addApplicationThreeDetails.execute();

                            Class_AddApplicationThreeDetails class_addApplicationThreeDetails = new Class_AddApplicationThreeDetails(str_BusinessSource, int_LastLoan, str_investment, int_knowledge, str_AppliedAmt, str_SanctionAmt, str_InterestRate, str_AppliedAt, EnquiryId, str_Repaymentperiod, int_Product_Improvement, int_Working_Expenses, int_Land, int_Equipment, int_Finance_Trading, ApplicationSlnoNew, str_ApplFees, str_verfiedDate, str_ApplAmtRemark, str_Manual_Receipt_No, str_Payment_Mode,"online",isAccountVerified);
                            addApplicationThreeList.add(class_addApplicationThreeDetails);
                            // DBCreate_AddApplicationThreeDetails_insert_2SQLiteDB(addApplicationThreeList);
                            DBCreate_AddCompliteApplicationThreeDetails_insert_2SQLiteDB(addApplicationThreeList);
                        }
                    }else{
                        Class_AddApplicationThreeDetails class_addApplicationThreeDetails = new Class_AddApplicationThreeDetails(str_BusinessSource, int_LastLoan, str_investment, int_knowledge, str_AppliedAmt, str_SanctionAmt, str_InterestRate, str_AppliedAt, EnquiryId, str_Repaymentperiod, int_Product_Improvement, int_Working_Expenses, int_Land, int_Equipment, int_Finance_Trading, ApplicationSlnoNew, str_ApplFees, str_verfiedDate, str_ApplAmtRemark, str_Manual_Receipt_No, str_Payment_Mode,"offline",isAccountVerified);
                        addApplicationThreeList.add(class_addApplicationThreeDetails);
                        //  DBCreate_AddApplicationThreeDetails_insert_2SQLiteDB(addApplicationThreeList);
                        DBCreate_AddCompliteApplicationThreeDetails_insert_2SQLiteDB(addApplicationThreeList);
                    }
                }
            }
        });

        Prev_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(AddApplicationThreeActivity.this, AddApplicationTwoActivity.class);
                intent1.putExtra("EnquiryId",EnquiryId);
                intent1.putExtra("Application_SlnoNew",ApplicationSlnoNew);
                intent1.putExtra("isApplicant",isApplicant);
                intent1.putExtra("tempId",tempId);
                startActivity(intent1);


            }
        });
        setVerifiedDate();

    }

    private void setVerifiedDate() {

        final Calendar c = Calendar.getInstance();

        edt_verfiedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddApplicationThreeActivity.this, R.style.DatePickerTheme,
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
                                    verfiedDateseterror_tv.setVisibility(View.GONE);
                                    edt_verfiedDate.setText(dateFormatDisplay.format(d).toString());
                                    str_verfiedDate=dateFormat.format(d);

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


    public boolean validation(){
        Boolean bownership=true;
        String str_investment1 = investment_sp.getSelectedItem().toString();

        if(str_investment1.contains("Select Investment")){
            str_investment="";
           /* TextView errorText = (TextView)investment_sp.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Please select the Investment");
            // Toast.makeText(AddEnquiryActivity.this,"Please select the Sector",Toast.LENGTH_LONG).show();
            bownership=false;*/
            // return false;
        }
        if(!checkbox_SHG.isChecked() && !checkbox_Selffinanced.isChecked() && !checkbox_Relative.isChecked() && !checkbox_private.isChecked() &&!checkbox_Moneylenders.isChecked() && !checkbox_Govtscheme.isChecked()&&!checkbox_BanksMFIs.isChecked()){
            //do some validation
            Toast toast= Toast.makeText(getApplicationContext(), "  Select Source of business  " , Toast.LENGTH_LONG);
            View view =toast.getView();
            view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
            toast.show();
            bownership=false;
        }

        String str_paymentMode = paymentMode_sp.getSelectedItem().toString();

        if(str_paymentMode.contains("Select Payment Mode")){
            TextView errorText = (TextView)paymentMode_sp.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Please select the payment mode!");
            bownership=false;
        }
        if (edt_verfiedDate.getText().toString().length() == 0 ||edt_verfiedDate.getText().toString().length()<=5)
        {
            verfiedDateseterror_tv.setVisibility(View.VISIBLE);
            verfiedDateseterror_tv.setError("Please Choose the Date!");
            bownership=false;
        }
        if(!str_paymentMode.contains("Cash")) {
            if (edt_ApplAmtRemark.getText().toString().length() == 0) {
                edt_ApplAmtRemark.setError("Remarks is required!");
                bownership = false;
            }
        }
        if( edt_ManualReceiptNo.getText().toString().length() == 0 ){
            edt_ManualReceiptNo.setError( "Receipt No is required!" );bownership=false;}

        if( ApplFees_et.getText().toString().length() == 0 ){
            ApplFees_et.setError( "Amount is required!" );bownership=false;}

        if(bownership) {
            return true;
        }else{
            Toast toast= Toast.makeText(getApplicationContext(), "  Please fill mandatory fields" , Toast.LENGTH_LONG);
            View view =toast.getView();
            view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
            toast.show();
            return false;}

    }
    public void checkboxBusinessSource() {

        //str_BusinessSource=null;
        if (checkbox_Selffinanced.isChecked()) {
            //    str_BusinessSource += "Self-financed" + ",";
            BusinessSource_array.add("Self-financed");
        }
        if (checkbox_Relative.isChecked()) {
            //    str_BusinessSource += "Relative" + ",";
            BusinessSource_array.add("Relative");
        }
        if (checkbox_BanksMFIs.isChecked()) {
            //     str_BusinessSource += "Banks" + ",";
            BusinessSource_array.add("Banks");
        }
        if (checkbox_Govtscheme.isChecked()) {
            //     str_BusinessSource += "Government scheme" + ",";
            BusinessSource_array.add("Government scheme");
        }
        if (checkbox_Moneylenders.isChecked()) {
            //    str_BusinessSource += "Moneylenders" + ",";
            BusinessSource_array.add("Moneylenders");
        }
        if (checkbox_SHG.isChecked()) {
            //   str_BusinessSource += "SHG" + ",";
            BusinessSource_array.add("SHG");
        }
        if (checkbox_private.isChecked()) {
            //   str_BusinessSource += "Loans from private organization" + ",";
            BusinessSource_array.add("Loans from private organization");
        }
        /*if(edt_otherSOB.getText().toString().length() == 0){
            str_BusinessSource +=edt_otherSOB.getText().toString();
            Log.e("tag","str_BusinessSource=="+str_BusinessSource);
        }*/
    }

    public void checkboxLastLoan() {

        if (checkbox_productimprove.isChecked()) {
            str_LastLoan += "Product_Improve" + ",";
            int_LastLoan = "1"+ ",";
            int_Product_Improvement="1";
        }else{
            int_LastLoan ="0"+ ",";
            int_Product_Improvement="0";
        }
        if (checkbox_buildings.isChecked()) {
            str_LastLoan += "For_Buildings" + ",";
            int_LastLoan +="1"+ ",";
            int_Land="1";
        }else{
            int_LastLoan +="0"+ ",";
            int_Land="0";
        }
        if (checkbox_expenses.isChecked()) {
            str_LastLoan += "Office_Expenses" + ",";
            int_LastLoan +="1"+ ",";
            int_Working_Expenses="1";
        }else{
            int_LastLoan +="0"+ ",";
            int_Working_Expenses="0";
        }
        if (checkbox_equipment.isChecked()) {
            str_LastLoan += "For_Equipment" + ",";
            int_LastLoan +="1"+ ",";
            int_Equipment="1";
        }else{
            int_LastLoan +="0"+ ",";
            int_Equipment="0";
        }
        if (checkbox_activities.isChecked()) {
            str_LastLoan += "Financing_Activites" + ",";
            int_LastLoan +="1"+ ",";
            int_Finance_Trading="1";
        }else{
            int_LastLoan +="0"+ ",";
            int_Finance_Trading="0";
        }
    }
    public void set_checkboxLastLoan() {

        for (int i = 0; i < LastLoanList.size(); i++) {
            if (LastLoanList.get(0).equalsIgnoreCase("1")) {
                checkbox_productimprove.setChecked(true);
            }
            if (LastLoanList.get(1).equalsIgnoreCase("1")) {
                checkbox_expenses.setChecked(true);
            }
            if (LastLoanList.get(2).equalsIgnoreCase("1")) {
                checkbox_buildings.setChecked(true);
            }
            if (LastLoanList.get(3).equalsIgnoreCase("1")) {
                checkbox_activities.setChecked(true);
            }
            if (LastLoanList.get(4).equalsIgnoreCase("1")) {
                checkbox_equipment.setChecked(true);
            }
        }
    }

    public void setcheckboxvaluesBusinessSource() {

        for(int i=0;i<BusinessSourceList.size();i++){
            if(BusinessSourceList.get(i).equalsIgnoreCase("Banks")){
                checkbox_BanksMFIs.setChecked(true);
            }
            if(BusinessSourceList.get(i).equalsIgnoreCase("Government scheme")){
                checkbox_Govtscheme.setChecked(true);
            }
            if(BusinessSourceList.get(i).equalsIgnoreCase("Loans from private organization")){
                checkbox_private.setChecked(true);
            }
            if(BusinessSourceList.get(i).equalsIgnoreCase("Moneylenders")) {
                checkbox_Moneylenders.setChecked(true);
            }
            if(BusinessSourceList.get(i).equalsIgnoreCase("Relative")) {
                checkbox_Relative.setChecked(true);
            }
            if(BusinessSourceList.get(i).equalsIgnoreCase("Self-financed")) {
                checkbox_Selffinanced.setChecked(true);
            }
            if(BusinessSourceList.get(i).equalsIgnoreCase("SHG")) {
                checkbox_SHG.setChecked(true);
            }
        }

    }

    private void setInvestmentSpinner() {
       /* final ArrayList listInvestment = new ArrayList();
        listInvestment.add("Select Investment");
        listInvestment.add("1000 – 5000");
        listInvestment.add("5000 – 10000");
        listInvestment.add("10000 – 15000");
        listInvestment.add("15000 – 20000");
        listInvestment.add("20000 – 50000");
        listInvestment.add("Above 50000");*/

        //ArrayAdapter dataAdapterSem = new ArrayAdapter(context, R.layout.simple_spinner_semester, listsemester);
        dataAdapter_Investment = new ArrayAdapter(AddApplicationThreeActivity.this, R.layout.simple_spinner_items, listInvestment);

        // Drop down layout style - list view with radio button
        dataAdapter_Investment.setDropDownViewResource(R.layout.spinnercustomstyle);

        // attaching data adapter to spinner
        investment_sp.setAdapter(dataAdapter_Investment);
        // sp_Education.setSupportBackgroundTintList(ContextCompat.getColorStateList(AddApplicationOneActivity.this, R.color.colorBlack));

    }

    private void setPaymentModeSpinner() {
        final ArrayList listMode = new ArrayList();
        listMode.add("Select Payment Mode");
        listMode.add("Cash");
        listMode.add("Online");
        listMode.add("DD");

        //ArrayAdapter dataAdapterSem = new ArrayAdapter(context, R.layout.simple_spinner_semester, listsemester);
        dataAdapter_paymentMode = new ArrayAdapter(AddApplicationThreeActivity.this, R.layout.simple_spinner_items, listMode);

        // Drop down layout style - list view with radio button
        dataAdapter_paymentMode.setDropDownViewResource(R.layout.spinnercustomstyle);

        // attaching data adapter to spinner
        paymentMode_sp.setAdapter(dataAdapter_paymentMode);
        // sp_Education.setSupportBackgroundTintList(ContextCompat.getColorStateList(AddApplicationOneActivity.this, R.color.colorBlack));

    }

    public void uploadfromDB_InvestmentList() {

        SQLiteDatabase db_Investment = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_Investment.execSQL("CREATE TABLE IF NOT EXISTS InvestmentList(InvestmentId VARCHAR,InvestmentName VARCHAR);");
        Cursor cursor = db_Investment.rawQuery("SELECT DISTINCT * FROM InvestmentList", null);
        int x = cursor.getCount();
        Log.d("tag","cursor count Investment"+ Integer.toString(x));

        int i = 0;
        arrayObj_Class_InvestmentDetails2 = new Class_OwnershipListDetails[x];
        if (cursor.moveToFirst()) {

            do {
                Class_OwnershipListDetails innerObj = new Class_OwnershipListDetails();
                innerObj.setId(cursor.getString(cursor.getColumnIndex("InvestmentId")));
                innerObj.setName(cursor.getString(cursor.getColumnIndex("InvestmentName")));

                arrayObj_Class_InvestmentDetails2[i] = innerObj;
                Log.e("tag","arrayObj_Class_InvestmentDetails2[]="+arrayObj_Class_InvestmentDetails2[i].toString());
                listInvestment.add(cursor.getString(cursor.getColumnIndex("InvestmentName")));
                i++;

            } while (cursor.moveToNext());


        }//if ends

        db_Investment.close();
        //setKnowSpinner();
        /*     if (x > 0) {

         *//*  for(int i1=0;i1<=arrayObj_Class_NavodyamiDetails2.length;i1++){
              listKnow.add(arrayObj_Class_NavodyamiDetails2[i1].getName());
           }*//*
            Log.e("tag","arrayObj_Class_NavodyamiDetails2="+arrayObj_Class_NavodyamiDetails2.toString());
           Log.e("tag","listKnow="+listKnow.toString());
            dataAdapter_gotknow = new ArrayAdapter(this, R.layout.spinnercustomstyle, arrayObj_Class_NavodyamiDetails2);
            dataAdapter_gotknow.setDropDownViewResource(R.layout.spinnercustomstyle);
            // attaching data adapter to spinner
            gotknow_sp.setAdapter(dataAdapter_gotknow);

          //  sel_gottoknow=gotknow_sp.getSelectedItemPosition();

            if(x>sel_gottoknow) {
                gotknow_sp.setSelection(sel_gottoknow);
            }
        }*/

    }

    public void onRadioButtonKnowledgeClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.rdb_knowledgeYes:
                if (checked) {
                    int_knowledge = "1";
                }
                break;
            case R.id.rdb_knowledgeNo:
                if (checked) {
                    int_knowledge = "0";
                }
                break;
            case R.id.rdb_knowledgeNotsure:
                if (checked) {
                    int_knowledge = "2";
                }
                break;
        }
    }

    public void DBCreate_AddApplicationThreeDetails_insert_2SQLiteDB(ArrayList<Class_AddApplicationThreeDetails> str_addapplication)
    {
        SQLiteDatabase db_addApplicationtwo = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);

        db_addApplicationtwo.execSQL("CREATE TABLE IF NOT EXISTS AddApplicationDetailsThree(BusinessSource VARCHAR,LastLoan VARCHAR,Investment VARCHAR,Knowledge VARCHAR," +
                "AppliedAmt VARCHAR,SanctionedAmt VARCHAR,InterestRate VARCHAR,AppliedAt VARCHAR,EnquiryId VARCHAR,Repaymentperiod VARCHAR,Application_Slno VARCHAR,ApplicationFees VARCHAR,VerifiedDate VARCHAR,Remark VARCHAR,Manual_Receipt_No VARCHAR,Payment_Mode VARCHAR);");

        String BusinessSource;
        String LastLoan;
        String Investment;
        String Knowledge;
        String AppliedAmt;
        String SanctionedAmt;
        String InterestRate;
        String AppliedAt;
        String EnquiryId;
        String Repaymentperiod;
        String Application_Slno,ApplicationFees,VerifiedDate,Remark,ManualReceiptNo,Payment_Mode;

        for(int s=0;s<str_addapplication.size();s++) {
            //    Log.e("tag","BusinessName="+str_addenquirys.get(s).getBusinessName()+"Fname="+str_addenquirys.get(s).getFName());
            BusinessSource=str_addapplication.get(s).getBusinessSource();
            LastLoan=str_addapplication.get(s).getLastLoan();

            Investment=str_addapplication.get(s).getInvestment();
            Knowledge=str_addapplication.get(s).getKnowledge();
            AppliedAmt=str_addapplication.get(s).getAppliedAmt();
            SanctionedAmt=str_addapplication.get(s).getSanctionedAmt();
            InterestRate=str_addapplication.get(s).getInterestRate();
            AppliedAt=str_addapplication.get(s).getAppliedAt();
            EnquiryId = str_addapplication.get(s).getEnquiryId();
            Repaymentperiod = str_addapplication.get(s).getRepaymentperiod();
            Application_Slno = str_addapplication.get(s).getApplication_Slno();
            ApplicationFees = str_addapplication.get(s).getApplicationFees();
            VerifiedDate = str_addapplication.get(s).getVerifiedDate();
            Remark = str_addapplication.get(s).getRemark();
            ManualReceiptNo = str_addapplication.get(s).getManual_Receipt_No();
            Payment_Mode = str_addapplication.get(s).getPayment_Mode();

Log.e("tag","AppliedAt="+AppliedAt);
            Cursor cursor1 = db_addApplicationtwo.rawQuery("SELECT DISTINCT * FROM AddApplicationDetailsThree WHERE EnquiryId ='"+EnquiryId+"'", null);
            int x = cursor1.getCount();
            Log.d("tag","cursor Application three Count"+ Integer.toString(x));
            if(x==0) {
                String SQLiteQuery = "INSERT INTO AddApplicationDetailsThree (BusinessSource, LastLoan,Investment,Knowledge,AppliedAmt,SanctionedAmt,InterestRate,AppliedAt,EnquiryId,Repaymentperiod,Application_Slno,ApplicationFees,VerifiedDate,Remark,Manual_Receipt_No,Payment_Mode)" +
                        " VALUES ('" + BusinessSource + "','" + LastLoan + "','" + Investment + "','" + Knowledge + "','" + AppliedAmt + "','" + SanctionedAmt + "','" +
                        InterestRate + "','" + AppliedAt + "','" + EnquiryId  + "','" + Repaymentperiod+ "','" +Application_Slno+"','" + ApplicationFees+"','" + VerifiedDate+"','" + Remark+"','" + ManualReceiptNo+"','" + Payment_Mode+"');";
                db_addApplicationtwo.execSQL(SQLiteQuery);
                //   db_addEnquiry.close();

                ContentValues cv = new ContentValues();
                cv.put("BusinessSource",BusinessSource);
                cv.put("LastLoan",LastLoan);
                cv.put("Investment",Investment);
                cv.put("Knowledge",Knowledge);
                cv.put("AppliedAmt",AppliedAmt);
                cv.put("SanctionedAmt",SanctionedAmt);
                cv.put("InterestRate",InterestRate);
                cv.put("AppliedAt",AppliedAt);
                cv.put("EnquiryId",EnquiryId);
                cv.put("Repaymentperiod",Repaymentperiod);
                cv.put("Application_Slno",Application_Slno);
                cv.put("ApplicationFees",ApplicationFees);
                cv.put("VerifiedDate",VerifiedDate);
                cv.put("Remark",Remark);
                cv.put("Manual_Receipt_No",ManualReceiptNo);
                cv.put("Payment_Mode",Payment_Mode);
                long result=db_addApplicationtwo.insert("AddApplicationDetailsThree",null,cv);
                // Log.e("tag","SQLiteQuery insert=="+SQLiteQuery.toString());
                Log.e("tag","SQLiteQuery insert result="+result);

                if(result==1){
                    Toast toast= Toast.makeText(getApplicationContext(), "  Application inserted offline Successfully " , Toast.LENGTH_SHORT);
                    // Toast toast=   Toast.makeText(YOUR ACTIVITY NAME ,Toast.LENGTH_SHORT);
                    View view =toast.getView();
                    view.setBackgroundColor( Color.parseColor("#009900")); //any color your want
                    toast.show();
                    Intent intent1 = new Intent(AddApplicationThreeActivity.this, BottomActivity.class);
                    intent1.putExtra("frgToLoad", "0");
                    startActivity(intent1);
                }
                else{
                    Toast toast= Toast.makeText(getApplicationContext(), "  Application inserted Failed  " , Toast.LENGTH_SHORT);
                    View view =toast.getView();
                    view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
                    toast.show();
                }

            }else{

                ContentValues cv = new ContentValues();
                cv.put("BusinessSource",BusinessSource);
                cv.put("LastLoan",LastLoan);
                cv.put("Investment",Investment);
                cv.put("Knowledge",Knowledge);
                cv.put("AppliedAmt",AppliedAmt);
                cv.put("SanctionedAmt",SanctionedAmt);
                cv.put("InterestRate",InterestRate);
                cv.put("AppliedAt",AppliedAt);
                cv.put("EnquiryId",EnquiryId);
                cv.put("Repaymentperiod",Repaymentperiod);
                cv.put("Application_Slno",Application_Slno);
                cv.put("ApplicationFees",ApplicationFees);
                cv.put("VerifiedDate",VerifiedDate);
                cv.put("Remark",Remark);
                cv.put("Manual_Receipt_No",ManualReceiptNo);
                cv.put("Payment_Mode",Payment_Mode);

                long result = db_addApplicationtwo.update("AddApplicationDetailsThree", cv, "EnquiryId = ?", new String[]{EnquiryId});
                //     db_addEnquiry.close();
                if(result != -1){
                    Toast toast= Toast.makeText(getApplicationContext(), "  Application updated offline Successfully " , Toast.LENGTH_SHORT);
                    // Toast toast=   Toast.makeText(YOUR ACTIVITY NAME ,Toast.LENGTH_SHORT);
                    View view =toast.getView();
                    view.setBackgroundColor( Color.parseColor("#009900")); //any color your want
                    toast.show();
                }
                else{
                    Toast toast= Toast.makeText(getApplicationContext(), "  Application update Failed  " , Toast.LENGTH_LONG);
                    View view =toast.getView();
                    view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
                    toast.show();
                }
            }
        }

        // ApplicationCount();


        db_addApplicationtwo.close();
    }
    public void DBCreate_AddCompliteApplicationThreeDetails_insert_2SQLiteDB(ArrayList<Class_AddApplicationThreeDetails> str_addapplication)
    {
        SQLiteDatabase db_addApplicationthree = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);

        db_addApplicationthree.execSQL("CREATE TABLE IF NOT EXISTS CompliteApplicationDetails(tempId INTEGER PRIMARY KEY AUTOINCREMENT,EnquiryId VARCHAR,Application_Slno VARCHAR,Academic_Year VARCHAR,dataSyncStatus VARCHAR,FName VARCHAR,MName VARCHAR,LName VARCHAR,MobileNo VARCHAR," +
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

        String BusinessSource;
        String LastLoan;
        String Investment;
        String Knowledge;
        String AppliedAmt;
        String SanctionedAmt;
        String InterestRate;
        String AppliedAt;
        String EnquiryId;
        String Repaymentperiod;
        String Application_Slno,ApplicationFees,VerifiedDate,Remark,ManualReceiptNo,Payment_Mode,dataSyncStatus;
        String int_Product_Improvement, int_Working_Expenses, int_Land, int_Equipment, int_Finance_Trading;

        for(int s=0;s<str_addapplication.size();s++) {
            //    Log.e("tag","BusinessName="+str_addenquirys.get(s).getBusinessName()+"Fname="+str_addenquirys.get(s).getFName());
            BusinessSource=str_addapplication.get(s).getBusinessSource();
            LastLoan=str_addapplication.get(s).getLastLoan();
            int_Product_Improvement=str_addapplication.get(s).getProduct_Improvement();
            int_Working_Expenses=str_addapplication.get(s).getWorking_Expenses();
            int_Land=str_addapplication.get(s).getLand();
            int_Equipment=str_addapplication.get(s).getEquipment();
            int_Finance_Trading=str_addapplication.get(s).getFinance_Trading();
            Investment=str_addapplication.get(s).getInvestment();
            Knowledge=str_addapplication.get(s).getKnowledge();
            AppliedAmt=str_addapplication.get(s).getAppliedAmt();
            SanctionedAmt=str_addapplication.get(s).getSanctionedAmt();
            InterestRate=str_addapplication.get(s).getInterestRate();
            AppliedAt=str_addapplication.get(s).getAppliedAt();
            EnquiryId = str_addapplication.get(s).getEnquiryId();
            Repaymentperiod = str_addapplication.get(s).getRepaymentperiod();
            Application_Slno = str_addapplication.get(s).getApplication_Slno();
            ApplicationFees = str_addapplication.get(s).getApplicationFees();
            VerifiedDate = str_addapplication.get(s).getVerifiedDate();
            Remark = str_addapplication.get(s).getRemark();
            ManualReceiptNo = str_addapplication.get(s).getManual_Receipt_No();
            Payment_Mode = str_addapplication.get(s).getPayment_Mode();
            dataSyncStatus=str_addapplication.get(s).getDataSyncStatus();

            String str_LastLoan = int_Product_Improvement + "," + int_Working_Expenses + "," + int_Land + "," + int_Finance_Trading + "," + int_Equipment;

            Cursor cursor = db_addApplicationthree.rawQuery("SELECT DISTINCT * FROM CompliteApplicationDetails WHERE EnquiryId ='"+EnquiryId+"' AND Application_Slno='"+ApplicationSlnoNew +"' AND tempId='"+tempId+"'", null);
            int x = cursor.getCount();

            Log.d("tag","cursor Application three Count"+ Integer.toString(x));
            Log.e("tag","AppliedAt="+ AppliedAt);

            ContentValues cv = new ContentValues();
            cv.put("BusinessSource",BusinessSource);
            cv.put("LastLoan",str_LastLoan);
            cv.put("Investment",Investment);
            cv.put("Knowledge",Knowledge);
            cv.put("AppliedAmt",AppliedAmt);
            cv.put("SanctionedAmt",SanctionedAmt);
            cv.put("InterestRate",InterestRate);
            cv.put("AppliedAt",AppliedAt);
            cv.put("EnquiryId",EnquiryId);
            cv.put("Repaymentperiod",Repaymentperiod);
            cv.put("Application_Slno",Application_Slno);
            cv.put("ApplicationFees",ApplicationFees);
            cv.put("VerifiedDate",VerifiedDate);
            cv.put("Remark",Remark);
            cv.put("Manual_Receipt_No",ManualReceiptNo);
            cv.put("Payment_Mode",Payment_Mode);
            cv.put("dataSyncStatus",dataSyncStatus);

            long result = db_addApplicationthree.update("CompliteApplicationDetails", cv, "tempId = ?", new String[]{tempId});
            //     db_addEnquiry.close();
            if(result > 0){
                if(isInternetPresent){

                }else{
                Toast toast= Toast.makeText(getApplicationContext(), "  Application updated offline Successfully " , Toast.LENGTH_SHORT);
                // Toast toast=   Toast.makeText(YOUR ACTIVITY NAME ,Toast.LENGTH_SHORT);
                View view =toast.getView();
                view.setBackgroundColor( Color.parseColor("#009900")); //any color your want
                toast.show();
                    Intent intent1 = new Intent(AddApplicationThreeActivity.this, BottomActivity.class);
                    intent1.putExtra("frgToLoad", "0");
                    startActivity(intent1);
                }
            }
            else{
                Toast toast= Toast.makeText(getApplicationContext(), "  Application update Failed  " , Toast.LENGTH_LONG);
                View view =toast.getView();
                view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
                toast.show();
            }
        }

        db_addApplicationthree.close();
    }
   /* public void uploadfromDB_AddApplicationThreeDetails() {

        SQLiteDatabase db_addApplicationthree = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);

        db_addApplicationthree.execSQL("CREATE TABLE IF NOT EXISTS AddApplicationDetailsThree(BusinessSource VARCHAR,LastLoan VARCHAR,Investment VARCHAR,Knowledge VARCHAR," +
                "AppliedAmt VARCHAR,SanctionedAmt VARCHAR,InterestRate VARCHAR,AppliedAt VARCHAR,EnquiryId VARCHAR,Repaymentperiod VARCHAR,Application_Slno VARCHAR,ApplicationFees VARCHAR,VerifiedDate VARCHAR,Remark VARCHAR,Manual_Receipt_No VARCHAR,Payment_Mode VARCHAR);");

        Cursor cursor = db_addApplicationthree.rawQuery("SELECT DISTINCT * FROM AddApplicationDetailsThree WHERE EnquiryId ='"+EnquiryId+"'", null);
        int x = cursor.getCount();
        Log.d("tag","cursor count AddApplicationDetailsThree"+ Integer.toString(x));

        int i = 0;
        arrayObj_Class_ApplicationDetailsThree = new Class_AddApplicationThreeDetails[x];
        String BusinessSource;
        String LastLoan;
        String Investment;
        String Knowledge;
        String AppliedAmt;
        String SanctionedAmt;
        String InterestRate;
        String AppliedAt;
        String EnquiryId;
        String Repaymentperiod;
        String Application_Slno;
        String ApplicationFees;
        String VerifiedDate;
        String Remark;
        String Manual_Receipt_No,Payment_Mode;

        if (cursor.moveToFirst()) {

            do {
                *//*Class_AddApplicationThreeDetails innerObj_Class_AddApplicationThreeDetails = new Class_AddApplicationThreeDetails();
                innerObj_Class_AddApplicationThreeDetails.setBusinessSource(cursor.getString(cursor.getColumnIndex("BusinessSource")));
                innerObj_Class_AddApplicationThreeDetails.setLastLoan(cursor.getString(cursor.getColumnIndex("LastLoan")));
                innerObj_Class_AddApplicationThreeDetails.setInvestment(cursor.getString(cursor.getColumnIndex("Investment")));
                innerObj_Class_AddApplicationThreeDetails.setKnowledge(cursor.getString(cursor.getColumnIndex("Knowledge")));
                innerObj_Class_AddApplicationThreeDetails.setAppliedAmt(cursor.getString(cursor.getColumnIndex("AppliedAmt")));
                innerObj_Class_AddApplicationThreeDetails.setSanctionedAmt(cursor.getString(cursor.getColumnIndex("SanctionedAmt")));
                innerObj_Class_AddApplicationThreeDetails.setInterestRate(cursor.getString(cursor.getColumnIndex("InterestRate")));
                innerObj_Class_AddApplicationThreeDetails.setAppliedAt(cursor.getString(cursor.getColumnIndex("AppliedAt")));
                innerObj_Class_AddApplicationThreeDetails.setRepaymentperiod(cursor.getString(cursor.getColumnIndex("Repaymentperiod")));

                arrayObj_Class_ApplicationDetailsThree[i] = innerObj_Class_AddApplicationThreeDetails;
                i++;
*//*

                BusinessSource= cursor.getString(cursor.getColumnIndex("BusinessSource"));
                Log.e("tag","BusinessSource="+BusinessSource);
                if(BusinessSource!=null) {
                    BusinessSourceList = Arrays.asList(BusinessSource.split(","));
                    Log.e("tag", "BusinessSourceList=" + BusinessSourceList);
                    Log.e("tag", "BusinessSourceList=" + BusinessSourceList.get(0));
                    setcheckboxvaluesBusinessSource();
                }
                LastLoan=cursor.getString(cursor.getColumnIndex("LastLoan"));
                if(LastLoan!=null) {
                    LastLoanList = Arrays.asList(LastLoan.split(","));
                    Log.e("tag", "LastLoan=" + LastLoan);
                    Log.e("tag", "LastLoanList=" + LastLoanList);
                    set_checkboxLastLoan();
                }
                Investment=cursor.getString(cursor.getColumnIndex("Investment"));
                if (Investment != null) {
                    int spinnerPosition = dataAdapter_Investment.getPosition(Investment);
                    investment_sp.setSelection(spinnerPosition);
                }
                Knowledge=cursor.getString(cursor.getColumnIndex("Knowledge"));
                if(Knowledge.equalsIgnoreCase("1"))
                {
                    knowledge_radiogroup.check(R.id.rdb_knowledgeYes);
                    int_knowledge="1";
                }
                else if(Knowledge.equalsIgnoreCase("0"))
                { knowledge_radiogroup.check(R.id.rdb_knowledgeNo);
                    int_knowledge="0";
                }else if(Knowledge.equalsIgnoreCase("2")){
                    knowledge_radiogroup.check(R.id.rdb_knowledgeNotsure);
                    int_knowledge="2";
                }
                AppliedAmt=cursor.getString(cursor.getColumnIndex("AppliedAmt"));
                edt_Appliedloan.setText(AppliedAmt);
                SanctionedAmt=cursor.getString(cursor.getColumnIndex("SanctionedAmt"));
                edt_sanctionedloan.setText(SanctionedAmt);
                InterestRate=cursor.getString(cursor.getColumnIndex("InterestRate"));
                edt_Interestrate.setText(InterestRate);
                AppliedAt=cursor.getString(cursor.getColumnIndex("AppliedAt"));
                edt_Appliedat.setText(AppliedAt);
                Repaymentperiod=cursor.getString(cursor.getColumnIndex("Repaymentperiod"));
                edt_repaymentperiod.setText(Repaymentperiod);
                ApplicationSlnoNew=cursor.getString(cursor.getColumnIndex("Application_Slno"));
                ApplicationFees=cursor.getString(cursor.getColumnIndex("ApplicationFees"));
                *//*if(ApplicationFees.equals("0")){
                    ApplFees_et.setText("0");
                }*//*
                if(ApplicationFees!=null){
                    ApplFees_et.setText(ApplicationFees);
                }
                else {
                    ApplFees_et.setText("100");
                }
                VerifiedDate=cursor.getString(cursor.getColumnIndex("VerifiedDate"));
                SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat dateFormatDisplay= new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date d=dateFormat.parse(VerifiedDate);
                    System.out.println("Formated"+dateFormat.format(d));
                    verfiedDateseterror_tv.setVisibility(View.GONE);
                    edt_verfiedDate.setText(dateFormatDisplay.format(d).toString());
                    str_verfiedDate=dateFormat.format(d);

                }
                catch(Exception e) {
                    System.out.println("Excep"+e);
                }
                Remark=cursor.getString(cursor.getColumnIndex("Remark"));
                if(Remark!=null) {
                    edt_ApplAmtRemark.setText(Remark);
                }
                Manual_Receipt_No=cursor.getString(cursor.getColumnIndex("Manual_Receipt_No"));
                if(Manual_Receipt_No!=null) {
                    edt_ManualReceiptNo.setText(Manual_Receipt_No);
                }
                Payment_Mode=cursor.getString(cursor.getColumnIndex("Payment_Mode"));
                if (Payment_Mode != null) {
                    int spinnerPosition = dataAdapter_paymentMode.getPosition(Payment_Mode);
                    paymentMode_sp.setSelection(spinnerPosition);
                }

            } while (cursor.moveToNext());


        }//if ends

        db_addApplicationthree.close();
        if (x > 0) {

        }

    }
*/
    public void uploadfromDB_CompliteApplicationThreeDetails() {

        SQLiteDatabase db_addApplicationthree = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);

        db_addApplicationthree.execSQL("CREATE TABLE IF NOT EXISTS CompliteApplicationDetails(tempId INTEGER PRIMARY KEY AUTOINCREMENT,EnquiryId VARCHAR,Application_Slno VARCHAR,Academic_Year VARCHAR,dataSyncStatus VARCHAR,FName VARCHAR,MName VARCHAR,LName VARCHAR,MobileNo VARCHAR," +
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

        Cursor cursor = db_addApplicationthree.rawQuery("SELECT DISTINCT * FROM CompliteApplicationDetails WHERE EnquiryId ='"+EnquiryId+"' AND tempId='"+tempId+"'", null);
        int x = cursor.getCount();

        Log.d("tag","cursor count AddApplicationDetailsThree"+ Integer.toString(x));

        int i = 0;
        arrayObj_Class_ApplicationDetailsThree = new Class_AddApplicationThreeDetails[x];
        String BusinessSource;
        String LastLoan;
        String Investment;
        String Knowledge;
        String AppliedAmt;
        String SanctionedAmt;
        String InterestRate;
        String AppliedAt;
        String EnquiryId;
        String Repaymentperiod;
        String Application_Slno;
        String ApplicationFees;
        String VerifiedDate;
        String Remark;
        String Manual_Receipt_No,Payment_Mode,IsAccountVerified;

        if (cursor.moveToFirst()) {

            do {
                /*Class_AddApplicationThreeDetails innerObj_Class_AddApplicationThreeDetails = new Class_AddApplicationThreeDetails();
                innerObj_Class_AddApplicationThreeDetails.setBusinessSource(cursor.getString(cursor.getColumnIndex("BusinessSource")));
                innerObj_Class_AddApplicationThreeDetails.setLastLoan(cursor.getString(cursor.getColumnIndex("LastLoan")));
                innerObj_Class_AddApplicationThreeDetails.setInvestment(cursor.getString(cursor.getColumnIndex("Investment")));
                innerObj_Class_AddApplicationThreeDetails.setKnowledge(cursor.getString(cursor.getColumnIndex("Knowledge")));
                innerObj_Class_AddApplicationThreeDetails.setAppliedAmt(cursor.getString(cursor.getColumnIndex("AppliedAmt")));
                innerObj_Class_AddApplicationThreeDetails.setSanctionedAmt(cursor.getString(cursor.getColumnIndex("SanctionedAmt")));
                innerObj_Class_AddApplicationThreeDetails.setInterestRate(cursor.getString(cursor.getColumnIndex("InterestRate")));
                innerObj_Class_AddApplicationThreeDetails.setAppliedAt(cursor.getString(cursor.getColumnIndex("AppliedAt")));
                innerObj_Class_AddApplicationThreeDetails.setRepaymentperiod(cursor.getString(cursor.getColumnIndex("Repaymentperiod")));

                arrayObj_Class_ApplicationDetailsThree[i] = innerObj_Class_AddApplicationThreeDetails;
                i++;
*/

                BusinessSource= cursor.getString(cursor.getColumnIndex("BusinessSource"));
                Log.e("tag","BusinessSource="+BusinessSource);
                if(BusinessSource!=null) {
                    BusinessSourceList = Arrays.asList(BusinessSource.split(","));
                    Log.e("tag", "BusinessSourceList=" + BusinessSourceList);
                    Log.e("tag", "BusinessSourceList=" + BusinessSourceList.get(0));
                    setcheckboxvaluesBusinessSource();
                }
                LastLoan=cursor.getString(cursor.getColumnIndex("LastLoan"));
                if(LastLoan!=null) {
                    LastLoanList = Arrays.asList(LastLoan.split(","));
                    Log.e("tag", "LastLoan=" + LastLoan);
                    Log.e("tag", "LastLoanList=" + LastLoanList);
                    set_checkboxLastLoan();
                }
                Investment=cursor.getString(cursor.getColumnIndex("Investment"));
                if (Investment != null) {
                    int spinnerPosition = dataAdapter_Investment.getPosition(Investment);
                    investment_sp.setSelection(spinnerPosition);
                }
                Knowledge=cursor.getString(cursor.getColumnIndex("Knowledge"));
                if(Knowledge.equalsIgnoreCase("1"))
                {
                    knowledge_radiogroup.check(R.id.rdb_knowledgeYes);
                    int_knowledge="1";
                }
                else if(Knowledge.equalsIgnoreCase("0"))
                { knowledge_radiogroup.check(R.id.rdb_knowledgeNo);
                    int_knowledge="0";
                }else if(Knowledge.equalsIgnoreCase("2")){
                    knowledge_radiogroup.check(R.id.rdb_knowledgeNotsure);
                    int_knowledge="2";
                }
                AppliedAmt=cursor.getString(cursor.getColumnIndex("AppliedAmt"));
                edt_Appliedloan.setText(AppliedAmt);
                SanctionedAmt=cursor.getString(cursor.getColumnIndex("SanctionedAmt"));
                edt_sanctionedloan.setText(SanctionedAmt);
                InterestRate=cursor.getString(cursor.getColumnIndex("InterestRate"));
                edt_Interestrate.setText(InterestRate);
                AppliedAt=cursor.getString(cursor.getColumnIndex("AppliedAt"));
                Log.e("tag","AppliedAt get="+AppliedAt);
                edt_Appliedat.setText(AppliedAt);
                Repaymentperiod=cursor.getString(cursor.getColumnIndex("Repaymentperiod"));
                edt_repaymentperiod.setText(Repaymentperiod);
//                ApplicationSlnoNew=cursor.getString(cursor.getColumnIndex("Application_Slno"));
                ApplicationFees=cursor.getString(cursor.getColumnIndex("ApplicationFees"));
                if(ApplicationFees.equals("0")){
                    ApplFees_et.setText("100");
                }else{
                    ApplFees_et.setText(ApplicationFees);
                }
                /*if(ApplicationFees!=null){

                }
                else {
                    ApplFees_et.setText("100");
                }*/
                VerifiedDate=cursor.getString(cursor.getColumnIndex("VerifiedDate"));
                SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat dateFormatDisplay= new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date d=dateFormat.parse(VerifiedDate);
                    System.out.println("Formated"+dateFormat.format(d));
                    verfiedDateseterror_tv.setVisibility(View.GONE);
                    edt_verfiedDate.setText(dateFormatDisplay.format(d).toString());
                    str_verfiedDate=dateFormat.format(d);

                }
                catch(Exception e) {
                    System.out.println("Excep"+e);
                }
                Remark=cursor.getString(cursor.getColumnIndex("Remark"));
                if(Remark!=null) {
                    edt_ApplAmtRemark.setText(Remark);
                }
                Manual_Receipt_No=cursor.getString(cursor.getColumnIndex("Manual_Receipt_No"));
                if(Manual_Receipt_No!=null) {
                    edt_ManualReceiptNo.setText(Manual_Receipt_No);
                }
                Payment_Mode=cursor.getString(cursor.getColumnIndex("Payment_Mode"));
                if (Payment_Mode != null) {
                    int spinnerPosition = dataAdapter_paymentMode.getPosition(Payment_Mode);
                    paymentMode_sp.setSelection(spinnerPosition);
                }
                IsAccountVerified=cursor.getString(cursor.getColumnIndex("IsAccountVerified"));
                Log.e("tag","IsAccountVerified="+IsAccountVerified);
               /* if (IsAccountVerified == null||IsAccountVerified.equalsIgnoreCase("")) {
                    IsAccountVerified="0";
                }else */if(IsAccountVerified.equalsIgnoreCase("1")){
                    ApplFees_et.setEnabled(false);
                    ApplFees_et.setFocusable(false);
                    paymentMode_sp.setEnabled(false);
                    paymentMode_sp.setClickable(false);
                    edt_ApplAmtRemark.setEnabled(false);
                    edt_ApplAmtRemark.setFocusable(false);
                    edt_ManualReceiptNo.setEnabled(false);
                    edt_ManualReceiptNo.setFocusable(false);
                    edt_verfiedDate.setFocusable(false);
                    edt_verfiedDate.setEnabled(false);
                }


            } while (cursor.moveToNext());


        }//if ends

        db_addApplicationthree.close();
        if (x > 0) {

        }

    }

    private class AddApplicationThreeDetails extends AsyncTask<String, Void, Void>
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

        public AddApplicationThreeDetails(AddApplicationThreeActivity activity) {
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
                Toast toast= Toast.makeText(AddApplicationThreeActivity.this, "  Application Updated Successfully " , Toast.LENGTH_SHORT);
                // Toast toast=   Toast.makeText(YOUR ACTIVITY NAME ,Toast.LENGTH_SHORT);
                View view =toast.getView();
                view.setBackgroundColor( Color.parseColor("#009900")); //any color your want
                toast.show();
                Intent intent1 = new Intent(AddApplicationThreeActivity.this, BottomActivity.class);
                intent1.putExtra("frgToLoad", "0");
                startActivity(intent1);
                /*AddApplicationTwoActivity addApplicationTwoActivity=new AddApplicationTwoActivity();
                addApplicationTwoActivity.clear_fields();*/
            }
            else{
               // Toast toast= Toast.makeText(AddApplicationThreeActivity.this, "  Updated Failed  " , Toast.LENGTH_LONG);
                Toast toast= Toast.makeText(context, "  "+status.toString()+"  " , Toast.LENGTH_LONG);
                View view =toast.getView();
                view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
                toast.show();
            }
        }
    }

    public void Submit(){
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
Log.e("tag","str_AppliedAt="+str_AppliedAt);
            request.addProperty("Application_Slno", Integer.parseInt(ApplicationSlnoNew));
            request.addProperty("Source_Of_Business", str_BusinessSource);
            request.addProperty("Initial_Investment", str_investment);
            request.addProperty("isKnowledge_Loan_Process", Integer.parseInt(int_knowledge));
            request.addProperty("Applied_Loan_Amount", Integer.parseInt(str_AppliedAmt));
            request.addProperty("Sanctioned_Loan_Amount", Integer.parseInt(str_SanctionAmt));
            request.addProperty("Interest_Rate", str_InterestRate);
            request.addProperty("Repayment_Period",str_Repaymentperiod);
            request.addProperty("Applied_At",str_AppliedAt);
            request.addProperty("Loan_For_Product_Improvement",Integer.parseInt(int_Product_Improvement));
            request.addProperty("Loan_For_Working_Expenses",Integer.parseInt(int_Working_Expenses));
            request.addProperty("Loan_For_Land",Integer.parseInt(int_Land));
            request.addProperty("Loan_For_Equipment",Integer.parseInt(int_Equipment));
            request.addProperty("Loan_For_Finance_Trading",Integer.parseInt(int_Finance_Trading));
            request.addProperty("User_Id",str_UserId);
            request.addProperty("Application_Fees",Integer.parseInt(str_ApplFees));
            request.addProperty("Receipt_Date",str_verfiedDate);
            request.addProperty("Remark",str_ApplAmtRemark);
            request.addProperty("Manual_Receipt_No",str_Manual_Receipt_No);
            request.addProperty("Payment_Mode",str_Payment_Mode);

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

//------------------------------------------Add New Application------------------------------------------------------

    private class AddNewApplicationDetails extends AsyncTask<String, Void, Void>
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

        public AddNewApplicationDetails(AddApplicationThreeActivity activity) {
            context = activity;
            dialog = new ProgressDialog(context);
        }
        @Override
        protected Void doInBackground(String... params)
        {
            Log.i("TAG", "doInBackground");
            SubmitAddNewApplication();
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            Log.i("TAG", "onPostExecute");

            Log.d("ServiceResponseisss: ",status.toString());

            dialog.dismiss();

            if(status.equalsIgnoreCase("success")) {
                Toast toast= Toast.makeText(AddApplicationThreeActivity.this, "  Application Added Successfully " , Toast.LENGTH_SHORT);
                // Toast toast=   Toast.makeText(YOUR ACTIVITY NAME ,Toast.LENGTH_SHORT);
                View view =toast.getView();
                view.setBackgroundColor( Color.parseColor("#009900")); //any color your want
                toast.show();
                Intent intent1 = new Intent(AddApplicationThreeActivity.this, BottomActivity.class);
                intent1.putExtra("frgToLoad", "0");
                startActivity(intent1);
                Delete_DatafromDBTempID(tempId);
               /* AddApplicationTwoActivity addApplicationTwoActivity=new AddApplicationTwoActivity();
                addApplicationTwoActivity.clear_fields();*/
            }
            else{
            //    Toast toast= Toast.makeText(AddApplicationThreeActivity.this, "  Updated Failed  " , Toast.LENGTH_LONG);
                Toast toast= Toast.makeText(context, "  "+status.toString()+"  " , Toast.LENGTH_LONG);
                View view =toast.getView();
                view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
                toast.show();
            }
        }
    }

   /* public void SubmitAddNewApplication(){
        String URL = Class_URL.URL_Login.toString().trim();
        String METHOD_NAME = "Save_Application_Details";
        String Namespace = "http://mis.navodyami.org/", SOAPACTION = "http://mis.navodyami.org/Save_Application_Details";

        try {



            SoapObject request = new SoapObject(Namespace, METHOD_NAME);

       *//* ------   API Data  ---------
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
      <Application_Date>string</Application_Date>*//*

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
            request.addProperty("Which_License", licence);
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
                    obj.put("TotalMale",listFemalePermentEmplyee.get(i));
                    obj.put("TotalFemale",listMalePermentEmplyee.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                array.put(obj);
                if(str_json==null){
                    str_json =(obj.toString() + ",");
                }else {
                    str_json = str_json + (obj.toString() + ",");
                }
               *//* if(i==2) {
                    str_json += obj.toString();
                }else{
                    str_json += obj.toString() + ",";
                }*//*
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
            request.addProperty("Source_Of_Business", str_BusinessSource);
            request.addProperty("Initial_Investment", str_investment);
            request.addProperty("isKnowledge_Loan_Process", Integer.parseInt(int_knowledge));
            request.addProperty("Applied_Loan_Amount", Integer.parseInt(str_AppliedAmt));
            request.addProperty("Sanctioned_Loan_Amount", Integer.parseInt(str_SanctionAmt));
            request.addProperty("Interest_Rate", str_InterestRate);
            request.addProperty("Repayment_Period",str_Repaymentperiod);
            request.addProperty("Applied_At",str_AppliedAt);
            request.addProperty("Loan_For_Product_Improvement",Integer.parseInt(int_Product_Improvement));
            request.addProperty("Loan_For_Working_Expenses",Integer.parseInt(int_Working_Expenses));
            request.addProperty("Loan_For_Land",Integer.parseInt(int_Land));
            request.addProperty("Loan_For_Equipment",Integer.parseInt(int_Equipment));
            request.addProperty("Loan_For_Finance_Trading",Integer.parseInt(int_Finance_Trading));
            request.addProperty("User_Id",str_UserId);
            request.addProperty("Application_Fees",Integer.parseInt(str_ApplFees));
            request.addProperty("Receipt_Date",str_verfiedDate);
            request.addProperty("Remark",str_ApplAmtRemark);
            request.addProperty("Manual_Receipt_No",str_Manual_Receipt_No);
            request.addProperty("Payment_Mode",str_Payment_Mode);
            request.addProperty("Application_Date",Application_Date);
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
    }*/
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
           if(socialCatgary.equals("")||socialCatgary==null){
               socialCatgary="0";
           }
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
           Log.e("tag","Where_Sell_Products frm sqlite="+sell_products);
           request.addProperty("Where_Sell_Products",sell_products);
           request.addProperty("Earn_Most_Channel",last_quarter);
           Log.e("tag","Earn_Most_Channel frm sqlite="+last_quarter);
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
       /*    request.addProperty("Source_Of_Business", str_BusinessSource);
           request.addProperty("Initial_Investment", str_investment);
           request.addProperty("isKnowledge_Loan_Process", Integer.parseInt(int_knowledge));
           request.addProperty("Applied_Loan_Amount", Integer.parseInt(str_AppliedAmt));
           request.addProperty("Sanctioned_Loan_Amount", Integer.parseInt(str_SanctionAmt));
           request.addProperty("Interest_Rate", str_InterestRate);
           request.addProperty("Repayment_Period",str_Repaymentperiod);
           request.addProperty("Applied_At",str_AppliedAt);
           request.addProperty("Loan_For_Product_Improvement",Integer.parseInt(int_Product_Improvement));
           request.addProperty("Loan_For_Working_Expenses",Integer.parseInt(int_Working_Expenses));
           request.addProperty("Loan_For_Land",Integer.parseInt(int_Land));
           request.addProperty("Loan_For_Equipment",Integer.parseInt(int_Equipment));
           request.addProperty("Loan_For_Finance_Trading",Integer.parseInt(int_Finance_Trading));
           request.addProperty("User_Id",str_UserId);
           request.addProperty("Application_Fees",Integer.parseInt(str_ApplFees));
           request.addProperty("Receipt_Date",str_verfiedDate);
           request.addProperty("Remark",str_ApplAmtRemark);
           request.addProperty("Manual_Receipt_No",str_Manual_Receipt_No);
           request.addProperty("Payment_Mode",str_Payment_Mode);*/

       Log.e("tag","str_BusinessSource="+str_BusinessSource);
           Log.e("tag","str_investment="+str_investment);
           Log.e("tag","str_AppliedAt="+str_AppliedAt);
           Log.e("tag","int_knowledge="+int_knowledge+"str_AppliedAmt="+str_AppliedAmt+"str_SanctionAmt="+str_SanctionAmt);
           Log.e("tag","int_Product_Improvement="+int_Product_Improvement+"int_Working_Expenses="+int_Working_Expenses+"int_Land="+int_Land);
           Log.e("tag","int_Equipment="+int_Equipment+"int_Finance_Trading="+int_Finance_Trading+"str_ApplFees="+str_ApplFees);

           request.addProperty("Source_Of_Business", str_BusinessSource);
           request.addProperty("Initial_Investment", str_investment);
           request.addProperty("isKnowledge_Loan_Process", Integer.parseInt(int_knowledge));
           request.addProperty("Applied_Loan_Amount", Integer.parseInt(str_AppliedAmt));
           request.addProperty("Sanctioned_Loan_Amount", Integer.parseInt(str_SanctionAmt));
           request.addProperty("Interest_Rate", str_InterestRate);
           request.addProperty("Repayment_Period",str_Repaymentperiod);
           request.addProperty("Applied_At",str_AppliedAt);
           request.addProperty("Loan_For_Product_Improvement",Integer.parseInt(int_Product_Improvement));
           request.addProperty("Loan_For_Working_Expenses",Integer.parseInt(int_Working_Expenses));
           request.addProperty("Loan_For_Land",Integer.parseInt(int_Land));
           request.addProperty("Loan_For_Equipment",Integer.parseInt(int_Equipment));
           request.addProperty("Loan_For_Finance_Trading",Integer.parseInt(int_Finance_Trading));
           request.addProperty("User_Id",str_UserId);
           request.addProperty("Application_Fees",Integer.parseInt(str_ApplFees));
           request.addProperty("Receipt_Date",str_verfiedDate);
           request.addProperty("Remark",str_ApplAmtRemark);
           request.addProperty("Manual_Receipt_No",str_Manual_Receipt_No);
           request.addProperty("Payment_Mode",str_Payment_Mode);
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
    public void GetDataFromDB_AddApplicationDetails(){
        SQLiteDatabase db_addEnquiry = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);

        db_addEnquiry.execSQL("CREATE TABLE IF NOT EXISTS AddApplicationDetailsNew(FName VARCHAR,MName VARCHAR,LName VARCHAR,MobileNo VARCHAR," +
                "EmailId VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,BusinessName VARCHAR," +
                "DeviceType VARCHAR,UserId VARCHAR,EnquiryId VARCHAR,Gender VARCHAR,Economic VARCHAR,whatsappNo VARCHAR,DOB VARCHAR,education VARCHAR, " +
                "socialCatgary VARCHAR, businessAddress VARCHAR,yearInBusiness VARCHAR,yearInCurrentBusiness VARCHAR,sectorBusiness VARCHAR," +
                "gottoknow VARCHAR,UNDP VARCHAR,yearUNDP VARCHAR,navodyami VARCHAR,yearNavodyami VARCHAR,aadhar VARCHAR,street VARCHAR);");

      /*  String FName,LName,MName,MobileNo,EmailId,BusinessName,stateId,districtId,talukId,villegeId,sectorId,deviceType,userId,Enquiry_Id,Gender;

        String Economic,whatsappNo,DOB,education, socialCatgary, businessAddress,yearInBusiness,yearInCurrentBusiness,sectorBusiness,gottoknow,UNDP,yearUNDP,navodyami,yearNavodyami,aadhar,street;
     */   Cursor cursor = db_addEnquiry.rawQuery("SELECT DISTINCT * FROM AddApplicationDetailsNew WHERE EnquiryId ='"+EnquiryId+"'", null);
        int x = cursor.getCount();
        Log.d("tag","cursor count AddApplicationDetailsTwo"+ Integer.toString(x));

        int i = 0;
        arrayObj_Class_ApplicationDetails = new Class_AddApplicationDetails[x];
        if (cursor.moveToFirst()) {

            do {
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
                Enquiry_Id = cursor.getString(cursor.getColumnIndex("EnquiryId"));
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

            } while (cursor.moveToNext());
        }
    }

   /* public void GetDatafromDB_AddApplicationTwoDetails() {

        listYears.clear();
        listProfit.clear();
        listTurnOver.clear();
        listFemalePermentEmplyee.clear();
        listMalePermentEmplyee.clear();

        SQLiteDatabase db_addApplicationtwo = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);

        db_addApplicationtwo.execSQL("CREATE TABLE IF NOT EXISTS AddApplicationDetailsTwo(Manufacture VARCHAR,Licence VARCHAR,WhichLicence VARCHAR,ProductOne VARCHAR," +
                "ProductTwo VARCHAR,ProductThree VARCHAR,BusinessYear VARCHAR,Ownership VARCHAR,YearOne VARCHAR,Female_year1 VARCHAR,Male_year1 VARCHAR," +
                "YearTwo VARCHAR,Female_year2 VARCHAR,Male_year2 VARCHAR,YearThree VARCHAR,Female_year3 VARCHAR,Male_year3 VARCHAR,Labour VARCHAR,OutsidefamilyLabour VARCHAR, " +
                "Trunover_year1 VARCHAR,Trunover_year2 VARCHAR,Trunover_year3 VARCHAR,Profit_year1 VARCHAR,Profit_year2 VARCHAR,Profit_year3 VARCHAR,Which_Machine VARCHAR,Machine VARCHAR,Sell_products VARCHAR," +
                "Last_quarter VARCHAR,EnquiryId VARCHAR,Total_Employee VARCHAR,Application_Slno VARCHAR);");

        Cursor cursor = db_addApplicationtwo.rawQuery("SELECT DISTINCT * FROM AddApplicationDetailsTwo WHERE EnquiryId ='"+EnquiryId+"'", null);
        int x = cursor.getCount();
        Log.d("tag","cursor count AddApplicationDetailsTwo"+ Integer.toString(x));

        int i = 0;
        arrayObj_Class_ApplicationDetailsTwo = new Class_AddApplicationTwoDetails[x];
        if (cursor.moveToFirst()) {

            do {
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
                Log.e("tag","Sell_products="+sell_products);

                last_quarter=cursor.getString(cursor.getColumnIndex("Last_quarter"));
                Log.e("tag","last_quarter="+last_quarter);

                enquiryId=cursor.getString(cursor.getColumnIndex("EnquiryId"));
                total_Employee=cursor.getString(cursor.getColumnIndex("Total_Employee"));

                Application_Slno=cursor.getString(cursor.getColumnIndex("Application_Slno"));
                *//*if(str_yearOne==null||str_yearTwo==null||str_yearThree==null){
                    //  if(str_yearOne.equalsIgnoreCase(null)||str_yearTwo.equalsIgnoreCase("null")||str_yearThree.equalsIgnoreCase("null")) {
                    uploadfromDB_Yearlist();
                    //  }
                }*//*

            } while (cursor.moveToNext());


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
        }//if ends

        db_addApplicationtwo.close();
    }*/
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
    public void GetDataFromDB_CompliteApplicationDetails(String passEnquiry_Id,String passApplication_Slno,String passTempId){

        listYears.clear();
        listProfit.clear();
        listTurnOver.clear();
        listFemalePermentEmplyee.clear();
        listMalePermentEmplyee.clear();

        //LastLoanList.clear();

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
        if (cursor.moveToFirst()) {

            do {
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
                Log.e("tag","whichLicence three="+whichLicence);
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
                last_quarter=cursor.getString(cursor.getColumnIndex("Last_quarter"));
                Log.e("tag","comp last_quarter="+last_quarter);
                total_Employee=cursor.getString(cursor.getColumnIndex("Total_Employee"));

               /* BusinessSource= cursor.getString(cursor.getColumnIndex("BusinessSource"));
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
                Payment_Mode=cursor.getString(cursor.getColumnIndex("Payment_Mode"));*/

            } while (cursor.moveToNext());


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
        }
    }

//---------------------------------------------------------------------------------------------------------------

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent1 = new Intent(AddApplicationThreeActivity.this, AddApplicationTwoActivity.class);
        intent1.putExtra("EnquiryId",EnquiryId);
        intent1.putExtra("Application_SlnoNew",ApplicationSlnoNew);
        intent1.putExtra("isApplicant",isApplicant);
        intent1.putExtra("tempId",tempId);
        startActivity(intent1);

    }

}
