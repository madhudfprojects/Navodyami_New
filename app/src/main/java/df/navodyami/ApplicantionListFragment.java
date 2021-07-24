package df.navodyami; /**
 * Created by Madhu.
 */

import android.app.AlertDialog;
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
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;

public class ApplicantionListFragment extends Fragment {

    private ListView lview;
    private ArrayList<ApplicationListModel> feesList;
    private ApplicationListAdapter adapter;

    private LinkedHashSet<String> collegeNameLst;
    private ArrayList<String> collegeNameArrLst;
    // private AppCompatSpinner spin_college;
    private ArrayList<ApplicationListModel> originalList = null;
    private ProgressDialog progressDialog;
    private EditText etSearch;
    private String EnquiryID;
    private int counter = 0;
    private Spinner spin_year;
    private HashMap<String, Integer> hashYearId = new HashMap<String, Integer>();
    int acadamicCode;
    String Last_Sync_Date="0";

    Class_InternetDectector internetDectector, internetDectector2;
    Boolean isInternetPresent = false;

    Class_YearListDetails[] arrayObj_Class_yearDetails2;
    Class_YearListDetails Obj_Class_yearDetails;
    int sel_yearsp = 0;
    String selected_year, sp_stryear_ID;

    SharedPreferences sharedpreferencebook_usercredential_Obj;
    SharedPreferences.Editor editor_obj;
    String str_UserId;
    public static final String sharedpreferencebook_usercredential = "sharedpreferencebook_usercredential";
    public static final String KeyValue_employeeid = "KeyValue_employeeid";

    SharedPreferences sharedpreferencebook_SyncDate_Obj;
    SharedPreferences.Editor editorSync_obj;
    public static final String sharedpreferencebook_SyncDate = "sharedpreferencebook_SyncDate";
    public static final String KeyValue_SyncDate = "KeyValue_SyncDate";

    ArrayList<Class_AddApplicationDetails> addApplicationList;
    ArrayList<Class_AddApplicationTwoDetails> addApplicationTwoList;
    ArrayList<Class_AddApplicationThreeDetails> addApplicationThreeList;

    LinearLayout offline_count;
    TextView edit_applicationCount,new_applicationCount;

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
    String status;
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
    String Manual_Receipt_No,Payment_Mode;

    String Application_SlnoNew,personalEnquiry_Id,personalApplicationSlno,personalTempId,AllDatatempId,AllDataApplicationSlno,AllDataEnquiry_Id;
    String TempId_asyncTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_application_list, container, false);

        lview = (NonScrollListView) view.findViewById(R.id.listview_applicationlist);

        internetDectector = new Class_InternetDectector(getActivity());
        isInternetPresent = internetDectector.isConnectingToInternet();

        addApplicationList = new ArrayList<>();
        addApplicationTwoList = new ArrayList<>();
        addApplicationThreeList = new ArrayList<>();

        sharedpreferencebook_usercredential_Obj = getActivity().getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_UserId = sharedpreferencebook_usercredential_Obj.getString(KeyValue_employeeid, "").trim();

        sharedpreferencebook_SyncDate_Obj = getActivity().getSharedPreferences(sharedpreferencebook_SyncDate, Context.MODE_PRIVATE);
        Last_Sync_Date = sharedpreferencebook_SyncDate_Obj.getString(KeyValue_SyncDate, "").trim();

        Log.e("tag","Last_Sync_Date="+Last_Sync_Date);
        if(Last_Sync_Date==null||Last_Sync_Date.equalsIgnoreCase(null)||Last_Sync_Date.equalsIgnoreCase("")){
            Last_Sync_Date="0";
        }

        offline_count = (LinearLayout) view.findViewById(R.id.offline_count);
        new_applicationCount = (TextView) view.findViewById(R.id.new_applicationCount);
        edit_applicationCount = (TextView) view.findViewById(R.id.edit_applicationCount);

        spin_year = (Spinner) view.findViewById(R.id.spin_year);
        feesList = new ArrayList<ApplicationListModel>();
        adapter = new ApplicationListAdapter(getActivity(), feesList);
        lview.setAdapter(adapter);

      /*  if (isInternetPresent) {
            deleteYearTable_B4insertion();
            populateAcademicYear();
        } else {*/
            uploadfromDB_Yearlist();
       // }
        offline_count.setVisibility(View.GONE);

        etSearch = (EditText) view.findViewById(R.id.etSearch);

        if(isInternetPresent){
            getOfflineAddOREditApplication();
        }else{
            getOfflineAddOREditApplicationCount();
        }
        spin_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_yearDetails = (Class_YearListDetails) spin_year.getSelectedItem();
                sp_stryear_ID = Obj_Class_yearDetails.getYearID().toString();
                selected_year = spin_year.getSelectedItem().toString();
                int sel_yearsp_new = spin_year.getSelectedItemPosition();
                //  farmerListViewAdapter.notifyDataSetChanged();

                acadamicCode = Integer.valueOf(sp_stryear_ID);
                if (sel_yearsp_new != sel_yearsp) {
                    sel_yearsp = sel_yearsp_new;
                }
                if (isInternetPresent) {
                    // deleteListApplicationDetailsTable_B4insertion();
                    populateList();
                } else {
                    List_offlineApplicationDetails();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            ;

            @Override
            public void afterTextChanged(Editable s) {
                //adapter.getFilter().filter(s.toString());
                String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text, originalList, null);
            }
        });



        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent i = new Intent(getContext(), AddApplicationOneActivity.class);
                i.putExtra("EnquiryId", "0");
                i.putExtra("isApplicant", "0");
                startActivity(i);
            }
        });

        return view;
    }

    /*private void populateAcademicYear() {
        //  if (isInternetPresent) {
        AcademicYear academicYear = new AcademicYear(getActivity());
        academicYear.execute();
        //   }

    }*/

    private void populateList() {

        GetApplicationListing getApplicationListing = new GetApplicationListing(getActivity());
        getApplicationListing.execute();

    }

    public class GetApplicationListing extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;

        //private ProgressBar progressBar;

        GetApplicationListing(Context ctx) {
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapObject response = getApplicationListing();

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

        /* @Override
         protected void onPostExecute(SoapObject result) {

             *//*---------------------------------Soap Response----------------------------
           <vmApplication_Listing>
<Application_Slno>1</Application_Slno>
<First_Name>Mallikarjun</First_Name>
<Mobile_No>9035047002</Mobile_No>
<Business_Name>Agri</Business_Name>
<Status>Success</Status>
</vmApplication_Listing>

             *//*
            if(result != null){

                SoapPrimitive S_Application_Slno,S_First_Name,S_Mobile_No,S_Business_Name,S_Status,S_Application_Fees;
                Object O_Application_Slno,O_First_Name,O_Mobile_No,O_Business_Name,O_Status,O_Application_Fees;
                String str_Application_Slno = null,str_First_Name = null,str_Mobile_No = null,str_Business_Name = null,str_Status = null,str_Application_Fees=null;

            //    deleteListApplicationDetailsTable_B4insertion();
                for (int i = 0; i < result.getPropertyCount(); i++) {
                    SoapObject list = (SoapObject) result.getProperty(i);

                    //Log.d("DistrictResult", list.toString());

                    O_Status = list.getProperty("Status");
                    if (!O_Status.toString().equals("anyType{}") && !O_Status.toString().equals(null)) {
                        S_Status = (SoapPrimitive) list.getProperty("Status");
                        Log.d("Status:", S_Status.toString());
                        str_Status = S_Status.toString();
                    }

                    if (str_Status.equalsIgnoreCase("Success")) {

                        O_Application_Slno = list.getProperty("Application_Slno");
                        if (!O_Application_Slno.toString().equals("anyType{}") && !O_Application_Slno.toString().equals(null)) {
                            S_Application_Slno = (SoapPrimitive) list.getProperty("Application_Slno");
                            str_Application_Slno = S_Application_Slno.toString();
                            Log.d("Application_Slno", str_Application_Slno);
                        }

                        O_First_Name = list.getProperty("First_Name");
                        if (!O_First_Name.toString().equals("anyType{}") && !O_First_Name.toString().equals(null)) {
                            S_First_Name = (SoapPrimitive) list.getProperty("First_Name");
                            str_First_Name = S_First_Name.toString();
                            Log.d("str_First_Name", str_First_Name);
                        }

                        O_Mobile_No = list.getProperty("Mobile_No");
                        if (!O_Mobile_No.toString().equals("anyType{}") && !O_Mobile_No.toString().equals(null)) {
                            S_Mobile_No = (SoapPrimitive) list.getProperty("Mobile_No");
                            Log.d("Mobile_No", S_Mobile_No.toString());
                            str_Mobile_No = S_Mobile_No.toString();
                            Log.d("str_Mobile_No", str_Mobile_No);
                        }

                        O_Business_Name = list.getProperty("Business_Name");
                        if (!O_Business_Name.toString().equals("anyType{}") && !O_Business_Name.toString().equals(null)) {
                            S_Business_Name = (SoapPrimitive) list.getProperty("Business_Name");
                            Log.d("S_Business_Name", S_Business_Name.toString());
                            str_Business_Name = S_Business_Name.toString();
                            Log.d("str_Business_Name", str_Business_Name);

                        }
                        O_Application_Fees = list.getProperty("Application_Fees");
                        if (!O_Application_Fees.toString().equals("anyType{}") && !O_Application_Fees.toString().equals(null)) {
                            S_Application_Fees = (SoapPrimitive) list.getProperty("Application_Fees");
                            Log.d("Application_Fees", S_Application_Fees.toString());
                            str_Application_Fees = S_Application_Fees.toString();
                            Log.d("Application_Fees", str_Application_Fees);
                        }

                        if (O_Business_Name.toString().equals("anyType{}") || O_Business_Name.toString().equals(null)) {
                            str_Business_Name = "";
                        }

                        ApplicationListModel item = null;
                        if (!str_Business_Name.isEmpty()) {
                            // item = new EnquiryListModel(str_studentName, str_leadid, str_registrationDate, str_collegeName, str_MobileNo, str_Status,String.valueOf(acadamicCode));
                            item = new ApplicationListModel(str_Application_Slno,str_First_Name,str_Mobile_No,str_Business_Name,str_Status,String.valueOf(acadamicCode),str_Application_Fees,Enquiry_Id);
                            feesList.add(item);
                        }


                    }else{
                        feesList.clear();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(),"There are no Applications", Toast.LENGTH_LONG).show();
                    }

                }


                originalList = new ArrayList<ApplicationListModel>();
                originalList.addAll(feesList);

                Date date = new Date();
                Log.i("Tag_time", "date1=" + date);
                SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat dateFormatDisplay= new SimpleDateFormat("dd-MM-yyyy");
                try {
                    // Date d=dateFormat.parse(date);
                    // String PresentDayStr = dateFormat.format(date);
                    System.out.println("Formated"+dateFormat.format(date));
                  //  edt_verfiedDate.setText(dateFormatDisplay.format(date).toString());
                   String str_SyncDate=dateFormat.format(date);
                    editorSync_obj = sharedpreferencebook_SyncDate_Obj.edit();
                    editorSync_obj.putString(KeyValue_SyncDate, str_SyncDate);
                    editorSync_obj.apply();


                }
                catch(Exception e) {
                    //java.text.ParseException: Unparseable date: Geting error
                    System.out.println("Excep"+e);
                }

                DBCreate_ListApplicationDetails_2SQLiteDB(originalList);
                adapter.notifyDataSetChanged();
                // initCollegeSpinner();
            }
            progressDialog.dismiss();
        }
*/
        @Override
        protected void onPostExecute(SoapObject result) {

            /*---------------------------------Soap Response----------------------------
<Application_Slno>4</Application_Slno> <User_Id>4</User_Id><Enquiry_Id>7</Enquiry_Id><First_Name>Rahul N</First_Name><Middle_Name>K N</Middle_Name>
<Last_Name>P N</Last_Name><DOB>1991-05-23</DOB><Gender>F</Gender><Qualification>2</Qualification><Social_Category>4</Social_Category>
<Economic_Status>APL</Economic_Status><Mobile_No>1231231234</Mobile_No><Email_Id>vn@dfmail.org1</Email_Id><WhatApp_No>1231231234</WhatApp_No>
<Aadhar_No>909090909000</Aadhar_No><Application_Fees>0</Application_Fees><Receipt_No>4</Receipt_No><Receipt_Date>2019-10-24</Receipt_Date>
<isAccountant_Verified>0</isAccountant_Verified><Verified_By>0</Verified_By><Accounant_Remark/><Business_Address>Test Address - Shop Buiseness - updated data</Business_Address>
<Street>Test - Street - updated data</Street><State_Id>29</State_Id><District_Id>527</District_Id><Taluka_Id>5434</Taluka_Id>
<Village_Id>597271</Village_Id><Years_In_Business>9</Years_In_Business><year_In_Current_Business>9</year_In_Current_Business>
<Early_Sector>9</Early_Sector><Know_Navodyami>Friends</Know_Navodyami><UNDP_Member_Before>0</UNDP_Member_Before><Years_In_UNDP>0</Years_In_UNDP>
<Navodyami_Member_Before>0</Navodyami_Member_Before><Years_In_Navodyami>0</Years_In_Navodyami><Business_Name>VD Test Buiseness</Business_Name>
<Sector_Id>5</Sector_Id>
-------------Business Details--------------------
<isManufacture>1</isManufacture><Sell_Product1>Banana</Sell_Product1><Sell_Product2>Fruit</Sell_Product2><Sell_Product3>Salad</Sell_Product3>
<isHave_License>1</isHave_License><Which_License>DLH</Which_License><Business_Year>0</Business_Year><Total_Employee>0</Total_Employee>
<Permanent_Employee>0</Permanent_Employee><Take_Skilled_Employee>0</Take_Skilled_Employee><Hire_Outside_Family>0</Hire_Outside_Family>
<Own_Rent_Machine>0</Own_Rent_Machine><isAttended_MarketLinkage>0</isAttended_MarketLinkage>
-----------------Credit Details-----------------
<Initial_Investment>0</Initial_Investment>
<isKnowledge_Loan_Process>0</isKnowledge_Loan_Process><Applied_Loan_Amount>0</Applied_Loan_Amount><Sanctioned_Loan_Amount>0</Sanctioned_Loan_Amount>
<Interest_Rate>0</Interest_Rate><Repayment_Period>0</Repayment_Period><Loan_For_Product_Improvement>0</Loan_For_Product_Improvement>
<Loan_For_Working_Expenses>0</Loan_For_Working_Expenses><Loan_For_Land>0</Loan_For_Land><Loan_For_Equipment>0</Loan_For_Equipment>
<Loan_For_Finance_Trading>0</Loan_For_Finance_Trading><Academic_Year>0</Academic_Year><Status>Success</Status>

             */
            if (result != null) {
                SoapPrimitive S_StateId, S_DistrictId, S_TalukaId, S_VillageId, S_SectorId, S_FirstName, S_MiddleName, S_LastName, S_EmailId, S_BusinessName, S_MobileNo, S_isApplicant, S_Status, S_Gender, S_Remark, SApplication_Date;
                Object O_StateId, O_DistrictId, O_TalukaId, O_VillageId, O_SectorId, O_FirstName, O_MiddleName, O_LastName, O_EmailId, O_BusinessName, O_MobileNo, O_isApplicant, O_Status, O_Gender, O_Remark, OApplication_Date;
                String str_gender = null, str_SectorId = null,str_StateId=null,str_DistrictId=null,str_TalukaId=null,str_VillageId=null, str_FirstName = null, str_MiddleName = null, str_LastName = null, str_EmailId = null, str_BusinessName = null, str_MobileNo = null, str_isApplicant = null, str_Status = null, str_Application_Date = null;

                SoapPrimitive S_Which_Machine, S_Earn_Most_Channel, S_Where_Sell_Products, S_TotalAmountProfit, S_TotalAmount, S_TotalFemale, S_TotalMale, S_p_Year, SSocial_Category, SYears_In_Navodyami, SNavodyami_Member_Before, SYears_In_UNDP, SUNDP_Member_Before, SKnow_Navodyami, SEarly_Sector, Syear_In_Current_Business, SYears_In_Business, SStreet, S_Application_Slno, S_User_Id, S_Enquiry_Id, S_DOB, S_Qualification, S_Economic_Status, S_WhatApp_No, SAadhar_No, SApplication_Fees, SReceipt_No, SReceipt_Date, SBusiness_Address;
                Object O_Which_Machine, O_Earn_Most_Channel, O_Where_Sell_Products, O_TotalAmountProfit, O_TotalAmount, O_TotalFemale, O_TotalMale, O_p_Year, OSocial_Category, OYears_In_Navodyami, ONavodyami_Member_Before, OYears_In_UNDP, OUNDP_Member_Before, OKnow_Navodyami, OEarly_Sector, Oyear_In_Current_Business, OYears_In_Business, OStreet, O_Application_Slno, O_User_Id, O_Enquiry_Id, O_DOB, O_Qualification, O_Economic_Status, O_WhatApp_No, OAadhar_No, OApplication_Fees, OReceipt_No, OReceipt_Date, OBusiness_Address;
                String str_Which_Machine = null, str_Earn_Most_Channel = null, str_Where_Sell_Products = null, str_TotalAmountProfit1 = null, str_TotalAmountProfit2 = null, str_TotalAmountProfit3 = null, str_TotalAmountProfit = null, str_TotalAmount3 = null, str_TotalAmount2 = null, str_TotalAmount1 = null, str_TotalAmount = null, str_TotalFemale3 = null, str_TotalFemale2 = null, str_TotalFemale1 = null, str_TotalFemale = null, str_TotalMale3 = null, str_TotalMale2 = null, str_TotalMale1 = null, str_TotalMale = null, str_p_Year3 = null, str_p_Year2 = null, str_str_p_Year1 = null, str_p_Year = null, str_Social_Category = null, str_Years_In_Navodyami=null, str_Navodyami_Member_Before = null, str_Years_In_UNDP = null, str_UNDP_Member_Before=null, str_Know_Navodyami = null, str_Early_Sector = null, str_Application_Slno = null, str_User_Id = null, str_Enquiry_Id = null, str_DOB = null, str_Qualification = null, str_Economic_Status = null, str_Business_Address = null;
                String str_year_In_Current_Business = null, str_Years_In_Business = null, str_Street = null, str_WhatApp_No = null, str_Aadhar_No = null, str_Application_Fees = null, str_Receipt_No = null, str_Receipt_Date = null, str_Remark = null;

                SoapPrimitive SOwnership, SOwn_Rent_Machine, SHire_Outside_Family, STake_Skilled_Employee, SPermanent_Employee, STotal_Employee, SBusiness_Year, SWhich_License, SisHave_License, SSell_Product3, SSell_Product2, SSell_Product1, SisManufacture;
                Object OOwnership, OOwn_Rent_Machine, OHire_Outside_Family, OTake_Skilled_Employee, OPermanent_Employee, OTotal_Employee, OBusiness_Year, OWhich_License, OisHave_License, OSell_Product3, OSell_Product2, OSell_Product1, OisManufacture;
                String str_Ownership = null, str_Own_Rent_Machine = null, str_Hire_Outside_Family = null, str_Take_Skilled_Employee = null, str_Permanent_Employee = null, str_Total_Employee = null, str_Business_Year = null, str_Which_License = null, str_isHave_License = null, str_Sell_Product3 = null, str_Sell_Product2 = null, str_Sell_Product1 = null, str_isManufacture = null;

                SoapPrimitive SApplied_At,SIsAccountVerified,SPayment_Mode, SAcademic_Year, SLoan_For_Finance_Trading, SLoan_For_Equipment, SLoan_For_Land, SLoan_For_Working_Expenses, SLoan_For_Product_Improvement, SRepayment_Period, SInterest_Rate, SSanctioned_Loan_Amount, SApplied_Loan_Amount, SisKnowledge_Loan_Process, SInitial_Investment, SSource_Of_Business;
                Object OApplied_At,OIsAccountVerified,OPayment_Mode, OAcademic_Year, OLoan_For_Finance_Trading, OLoan_For_Equipment, OLoan_For_Land, OLoan_For_Working_Expenses, OLoan_For_Product_Improvement, ORepayment_Period, OInterest_Rate, OSanctioned_Loan_Amount, OApplied_Loan_Amount, OisKnowledge_Loan_Process, OInitial_Investment, OSource_Of_Business;
                String str_Applied_At=null,str_IsAccountVerified=null,str_Payment_Mode = null, str_Academic_Year = null, str_Loan_For_Finance_Trading = null, str_Loan_For_Equipment = null, str_Loan_For_Land = null, str_Loan_For_Working_Expenses = null, str_Loan_For_Product_Improvement = null, str_Repayment_Period = null, str_Interest_Rate = null, str_Sanctioned_Loan_Amount = null, str_Applied_Loan_Amount = null, str_isKnowledge_Loan_Process = null, str_Initial_Investment = null, str_Source_Of_Business = null;

                Log.e("tag", "getPropertyCount=" + result.getPropertyCount());
                for (int i = 0; i < result.getPropertyCount(); i++) {
                    SoapObject list = (SoapObject) result.getProperty(i);

                    //    SoapPrimitive list = (SoapPrimitive)result.getProperty(i);
                    //Log.d("DistrictResult", list.toString());

                    O_Status = list.getProperty("Status");
                    if (!O_Status.toString().equals("anyType{}") && !O_Status.toString().equals(null)) {
                        S_Status = (SoapPrimitive) list.getProperty("Status");
                        Log.d("Status:", S_Status.toString());
                        str_Status = S_Status.toString();
                    }

                    if (str_Status.equalsIgnoreCase("Success")) {


                        O_Application_Slno = list.getProperty("Application_Slno");
                        if (!O_Application_Slno.toString().equals("anyType{}") && !O_Application_Slno.toString().equals(null)) {
                            S_Application_Slno = (SoapPrimitive) list.getProperty("Application_Slno");
                            str_Application_Slno = S_Application_Slno.toString();
                            // ApplicationSlno=str_Application_Slno;
                            Log.d("str_Application_Slno", str_Application_Slno);
                        }
                        O_User_Id = list.getProperty("User_Id");
                        if (!O_User_Id.toString().equals("anyType{}") && !O_User_Id.toString().equals(null)) {
                            S_User_Id = (SoapPrimitive) list.getProperty("User_Id");
                            str_User_Id = S_User_Id.toString();
                            Log.d("str_User_Id", str_User_Id);
                        }
                        O_Enquiry_Id = list.getProperty("Enquiry_Id");
                        if (!O_Enquiry_Id.toString().equals("anyType{}") && !O_Enquiry_Id.toString().equals(null)) {
                            S_Enquiry_Id = (SoapPrimitive) list.getProperty("Enquiry_Id");
                            str_Enquiry_Id = S_Enquiry_Id.toString();
                            //  EnquiryId=str_Enquiry_Id;
                            Log.d("str_EnquiryId", str_Enquiry_Id);
                        }

                        O_FirstName = list.getProperty("First_Name");
                        if (!O_FirstName.toString().equals("anyType{}") && !O_FirstName.toString().equals(null)) {
                            S_FirstName = (SoapPrimitive) list.getProperty("First_Name");
                            str_FirstName = S_FirstName.toString();
                            //  edt_FirstName.setText(str_FirstName);
                            Log.d("FirstName", str_FirstName);
                        }

                        O_MiddleName = list.getProperty("Middle_Name");
                        if (!O_MiddleName.toString().equals("anyType{}") && !O_MiddleName.toString().equals(null)) {
                            S_MiddleName = (SoapPrimitive) list.getProperty("Middle_Name");
                            str_MiddleName = S_MiddleName.toString();
                            //   edt_MiddleName.setText(str_MiddleName);
                            Log.d("MiddleName", str_MiddleName);
                        }

                        O_LastName = list.getProperty("Last_Name");
                        if (!O_LastName.toString().equals("anyType{}") && !O_LastName.toString().equals(null)) {
                            S_LastName = (SoapPrimitive) list.getProperty("Last_Name");
                            str_LastName = S_LastName.toString();
                            //    edt_LastName.setText(str_LastName);
                            Log.d("LastName", str_LastName);
                        }

                        O_MobileNo = list.getProperty("Mobile_No");
                        if (!O_MobileNo.toString().equals("anyType{}") && !O_MobileNo.toString().equals(null)) {
                            S_MobileNo = (SoapPrimitive) list.getProperty("Mobile_No");
                            Log.d("S_MobileNo", S_MobileNo.toString());
                            str_MobileNo = S_MobileNo.toString();
                            //    edt_MobileNo.setText(str_MobileNo);
                        }

                        O_EmailId = list.getProperty("Email_Id");
                        if (!O_EmailId.toString().equals("anyType{}") && !O_EmailId.toString().equals(null)) {
                            S_EmailId = (SoapPrimitive) list.getProperty("Email_Id");
                            Log.d("S_EmailId", S_EmailId.toString());
                            str_EmailId = S_EmailId.toString();
                            Log.d("Str_EmailId", str_EmailId);
                            //    edt_EmailId.setText(str_EmailId);
                        }

                        O_BusinessName = list.getProperty("Business_Name");
                        if (!O_BusinessName.toString().equals("anyType{}") && !O_BusinessName.toString().equals(null)) {
                            S_BusinessName = (SoapPrimitive) list.getProperty("Business_Name");
                            Log.d("S_BusinessName", S_BusinessName.toString());
                            str_BusinessName = S_BusinessName.toString();
                            Log.d("str_BusinessName", str_BusinessName);
                            //  edt_BusinessName.setText(str_BusinessName);
                            /// collegeNameLst.add(str_collegeName);

                        }
                      /*  if (O_BusinessName.toString().equals("anyType{}") || O_BusinessName.toString().equals(null)) {
                            str_BusinessName = "";
                            edt_BusinessName.setText("");

                            //collegeNameLst.add(str_collegeName);
                        }*/



                       /* O_isApplicant = list.getProperty("isApplicant");
                        if (!O_isApplicant.toString().equals("anyType{}") && !O_isApplicant.toString().equals(null)) {
                            S_isApplicant = (SoapPrimitive) list.getProperty("isApplicant");
                            Log.d("S_isApplicant", S_isApplicant.toString());
                            str_isApplicant = S_isApplicant.toString();
                        }*/

                        O_StateId = list.getProperty("State_Id");
                        if (!O_StateId.toString().equals("anyType{}") && !O_StateId.toString().equals(null)) {
                            S_StateId = (SoapPrimitive) list.getProperty("State_Id");
                            Log.d("S_StateId", S_StateId.toString());
                            str_StateId = S_StateId.toString();

                            //  sel_statesp = Integer.parseInt(S_StateId.toString());
                        }
                        O_DistrictId = list.getProperty("District_Id");
                        if (!O_DistrictId.toString().equals("anyType{}") && !O_DistrictId.toString().equals(null)) {
                            S_DistrictId = (SoapPrimitive) list.getProperty("District_Id");
                            Log.d("str_DistrictId", S_DistrictId.toString());
                            str_DistrictId = S_DistrictId.toString();
                            // sel_districtsp = Integer.parseInt(S_DistrictId.toString());
                            //   districtlist_SP.setSelection(sel_districtsp);

                        }
                        O_TalukaId = list.getProperty("Taluka_Id");
                        if (!O_TalukaId.toString().equals("anyType{}") && !O_TalukaId.toString().equals(null)) {
                            S_TalukaId = (SoapPrimitive) list.getProperty("Taluka_Id");
                            Log.d("Str_TalukaId", S_TalukaId.toString());
                            str_TalukaId = S_TalukaId.toString();
                            //     sel_taluksp = Integer.parseInt(S_TalukaId.toString());

                        }
                        O_VillageId = list.getProperty("Village_Id");
                        if (!O_VillageId.toString().equals("anyType{}") && !O_VillageId.toString().equals(null)) {
                            S_VillageId = (SoapPrimitive) list.getProperty("Village_Id");
                            Log.d("str_VillageId", S_VillageId.toString());
                            str_VillageId = S_VillageId.toString();
                            //   sel_villagesp = Integer.parseInt(S_VillageId.toString());
                        }
                        O_SectorId = list.getProperty("Sector_Id");
                        if (!O_SectorId.toString().equals("anyType{}") && !O_SectorId.toString().equals(null)) {
                            S_SectorId = (SoapPrimitive) list.getProperty("Sector_Id");
                            Log.d("Str_SectorId", S_SectorId.toString());
                            str_SectorId = S_SectorId.toString();
                          /* sel_sector =  Integer.parseInt(S_SectorId.toString());
                           spin_Sector.setSelection(sel_sector);*/
                        }
                        O_DOB = list.getProperty("DOB");
                        if (!O_DOB.toString().equals("anyType{}") && !O_DOB.toString().equals(null)) {
                            S_DOB = (SoapPrimitive) list.getProperty("DOB");
                            Log.d("str_DOB new", S_DOB.toString());
                            str_DOB = S_DOB.toString();
                           /* SimpleDateFormat dateFormatDisplay= new SimpleDateFormat("dd-MM-yyyy");
                            Date date= null;
                            try {
                                date = dateFormatDisplay.parse(str_DOB);
                                Log.d("str_DOB date", date.toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }*/
//                           SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
//                           SimpleDateFormat dateFormatDisplay= new SimpleDateFormat("dd-MM-yyyy");
//                           try {
//                               Date d=dateFormat.parse(str_DOB);
//                               System.out.println("Formated"+dateFormat.format(d));
//                               dobseterror_tv.setVisibility(View.GONE);
//                               edt_dob.setText(dateFormatDisplay.format(d).toString());
//                               str_dob=dateFormat.format(d);
//
//                           }
//                           catch(Exception e) {
//                               System.out.println("Excep"+e);
//                           }
                        }
                        O_Qualification = list.getProperty("Qualification");
                        if (!O_Qualification.toString().equals("anyType{}") && !O_Qualification.toString().equals(null)) {
                            S_Qualification = (SoapPrimitive) list.getProperty("Qualification");
                            Log.d("Qualification", S_Qualification.toString());
                            str_Qualification = S_Qualification.toString();
                          /* sel_qulification= Integer.parseInt(str_Qualification);
                           sp_Education.setSelection(sel_qulification);*/
                        }
                        OSocial_Category = list.getProperty("Social_Category");
                        if (!OSocial_Category.toString().equals("anyType{}") && !OSocial_Category.toString().equals(null)) {
                            SSocial_Category = (SoapPrimitive) list.getProperty("Social_Category");
                            Log.d("Social_Category", SSocial_Category.toString());
                            str_Social_Category = SSocial_Category.toString();
                         /*  sel_social_Category= Integer.parseInt(str_Social_Category);
                           Catgary_SP.setSelection(sel_social_Category);
                           str_catgary=SSocial_Category.toString();*/
                        }

                        O_Gender = list.getProperty("Gender");
                        if (!O_Gender.toString().equals("anyType{}") && !O_Gender.toString().equals(null)) {
                            S_Gender = (SoapPrimitive) list.getProperty("Gender");
                            Log.d("Str_Gender", S_Gender.toString());
                            str_gender = S_Gender.toString();
                         /*  if(str_gender.equals("M"))
                           {
                               gender_radiogroup.check(R.id.rdb_male); }
                           else
                           { gender_radiogroup.check(R.id.rdb_female); }*/

                        }
                        O_Economic_Status = list.getProperty("Economic_Status");
                        if (!O_Economic_Status.toString().equals("anyType{}") && !O_Economic_Status.toString().equals(null)) {
                            S_Economic_Status = (SoapPrimitive) list.getProperty("Economic_Status");
                            Log.d("Str_Economic_Status", S_Economic_Status.toString());
                            str_Economic_Status = S_Economic_Status.toString();
                          /* if(str_Economic_Status.equals("APL"))
                           {
                               economic_radiogroup.check(R.id.rdb_apl);
                               str_economic="APL";
                           }
                           else
                           { economic_radiogroup.check(R.id.rdb_bpl);
                               str_economic="BPL";
                           }*/

                        }
                        O_WhatApp_No = list.getProperty("WhatApp_No");
                        if (!O_WhatApp_No.toString().equals("anyType{}") && !O_WhatApp_No.toString().equals(null)) {
                            S_WhatApp_No = (SoapPrimitive) list.getProperty("WhatApp_No");
                            str_WhatApp_No = S_WhatApp_No.toString();
                            Log.d("S_WhatApp_No", str_WhatApp_No);
                            // edt_whatsappMobileNo.setText(str_WhatApp_No);
                        }
                        OAadhar_No = list.getProperty("Aadhar_No");
                        if (!OAadhar_No.toString().equals("anyType{}") && !OAadhar_No.toString().equals(null)) {
                            SAadhar_No = (SoapPrimitive) list.getProperty("Aadhar_No");
                            str_Aadhar_No = SAadhar_No.toString();
                            Log.d("S_Aadhar_No", str_Aadhar_No);
                            //  edt_Aadhar.setText(str_Aadhar_No);
                        }
                        OApplication_Fees = list.getProperty("Application_Fees");
                        if (!OApplication_Fees.toString().equals("anyType{}") && !OApplication_Fees.toString().equals(null)) {
                            SApplication_Fees = (SoapPrimitive) list.getProperty("Application_Fees");
                            str_Application_Fees = SApplication_Fees.toString();
                            Log.d("SApplication_Fees", str_Application_Fees);
                        }
                        OReceipt_No = list.getProperty("Receipt_No");
                        if (!OReceipt_No.toString().equals("anyType{}") && !OReceipt_No.toString().equals(null)) {
                            SReceipt_No = (SoapPrimitive) list.getProperty("Receipt_No");
                            str_Receipt_No = SReceipt_No.toString();
                            Log.d("OReceipt_No", str_Receipt_No);
                        }
                        OReceipt_Date = list.getProperty("Receipt_Date");
                        if (!OReceipt_Date.toString().equals("anyType{}") && !OReceipt_Date.toString().equals(null)) {
                            SReceipt_Date = (SoapPrimitive) list.getProperty("Receipt_Date");
                            str_Receipt_Date = SReceipt_Date.toString();
                            Log.d("Receipt_Date", str_Receipt_Date);
                        }
                        O_Remark = list.getProperty("Remark");
                        if (!O_Remark.toString().equals("anyType{}") && !O_Remark.toString().equals(null)) {
                            S_Remark = (SoapPrimitive) list.getProperty("Remark");
                            str_Remark = S_Remark.toString();
                            Log.d("Remark", str_Remark);
                        }
                        OBusiness_Address = list.getProperty("Business_Address");
                        if (!OBusiness_Address.toString().equals("anyType{}") && !OBusiness_Address.toString().equals(null)) {
                            SBusiness_Address = (SoapPrimitive) list.getProperty("Business_Address");
                            str_Business_Address = SBusiness_Address.toString();
                            Log.d("Business_Address", str_Business_Address);
                            //  edt_BusinessAddress.setText(str_Business_Address);
                        }
                        OStreet = list.getProperty("Street");
                        if (!OStreet.toString().equals("anyType{}") && !OStreet.toString().equals(null)) {
                            SStreet = (SoapPrimitive) list.getProperty("Street");
                            str_Street = SStreet.toString();
                            Log.d("S_Street", str_Street);
                            //  edt_Street.setText(str_Street);
                        }
                        OYears_In_Business = list.getProperty("Years_In_Business");
                        if (!OYears_In_Business.toString().equals("anyType{}") && !OYears_In_Business.toString().equals(null)) {
                            SYears_In_Business = (SoapPrimitive) list.getProperty("Years_In_Business");
                            str_Years_In_Business = SYears_In_Business.toString();
                            Log.d("OYears_In_Business", str_Years_In_Business);
                            //    edt_yearInBusiness.setText(str_Years_In_Business);
                        }
                        Oyear_In_Current_Business = list.getProperty("year_In_Current_Business");
                        if (!Oyear_In_Current_Business.toString().equals("anyType{}") && !Oyear_In_Current_Business.toString().equals(null)) {
                            Syear_In_Current_Business = (SoapPrimitive) list.getProperty("year_In_Current_Business");
                            str_year_In_Current_Business = Syear_In_Current_Business.toString();
                            Log.d("S_Street", str_year_In_Current_Business);
                            //   edt_yearIncurrentBusiness.setText(str_year_In_Current_Business);
                        }
                        OEarly_Sector = list.getProperty("Early_Sector");
                        if (!OEarly_Sector.toString().equals("anyType{}") && !OEarly_Sector.toString().equals(null)) {
                            SEarly_Sector = (SoapPrimitive) list.getProperty("Early_Sector");
                            str_Early_Sector = SEarly_Sector.toString();
                            Log.d("Early_Sector", str_Early_Sector);
                            ///  edt_sectorBusiness.setText(str_Early_Sector);
                        }
                        OKnow_Navodyami = list.getProperty("Know_Navodyami");
                        if (!OKnow_Navodyami.toString().equals("anyType{}") && !OKnow_Navodyami.toString().equals(null)) {
                            SKnow_Navodyami = (SoapPrimitive) list.getProperty("Know_Navodyami");
                            str_Know_Navodyami = SKnow_Navodyami.toString();
                            Log.d("Know_Navodyami", str_Know_Navodyami);
                            //  edt_gottoknow.setText(str_Know_Navodyami);
                         /*  if (str_Know_Navodyami != null) {
                               int spinnerPosition = dataAdapter_gotknow.getPosition(str_Know_Navodyami);
                               gotknow_sp.setSelection(spinnerPosition);
                           }*/
                        }
                        OUNDP_Member_Before = list.getProperty("UNDP_Member_Before");
                        if (!OUNDP_Member_Before.toString().equals("anyType{}") && !OUNDP_Member_Before.toString().equals(null)) {
                            SUNDP_Member_Before = (SoapPrimitive) list.getProperty("UNDP_Member_Before");
                            str_UNDP_Member_Before = SUNDP_Member_Before.toString();
                            Log.d("UNDP_Member_Before", str_UNDP_Member_Before);

                          /* if(str_UNDP_Member_Before.equals("1"))
                           {
                               undp_radiogroup.check(R.id.rdb_UNDPyes);
                               int_UNDP="1";
                               edt_undp.setVisibility(View.VISIBLE);
                           }
                           else
                           { undp_radiogroup.check(R.id.rdb_UNDPno);
                               int_UNDP="0";
                               edt_undp.setVisibility(View.GONE);
                           }*/
                        }
                        OYears_In_UNDP = list.getProperty("Years_In_UNDP");
                        if (!OYears_In_UNDP.toString().equals("anyType{}") && !OYears_In_UNDP.toString().equals(null)) {
                            SYears_In_UNDP = (SoapPrimitive) list.getProperty("Years_In_UNDP");
                            str_Years_In_UNDP = SYears_In_UNDP.toString();
                            Log.d("Years_In_UNDP", str_Years_In_UNDP);
                            // edt_undp.setText(str_Years_In_UNDP);
                            // int_UNDP=str_Years_In_UNDP;
                        }
                        ONavodyami_Member_Before = list.getProperty("Navodyami_Member_Before");
                        if (!ONavodyami_Member_Before.toString().equals("anyType{}") && !ONavodyami_Member_Before.toString().equals(null)) {
                            SNavodyami_Member_Before = (SoapPrimitive) list.getProperty("Navodyami_Member_Before");
                            str_Navodyami_Member_Before = SNavodyami_Member_Before.toString();
                            Log.d("Navodyami_Member_Before", str_Navodyami_Member_Before);

                         /*  if(str_Navodyami_Member_Before.equals("1"))
                           {
                               navodyami_radiogroup.check(R.id.rdb_navoyes);
                               int_navo="1";
                               edt_navodyami.setVisibility(View.VISIBLE);
                           }
                           else
                           { navodyami_radiogroup.check(R.id.rdb_navono);
                               int_navo="0";
                               edt_navodyami.setVisibility(View.GONE);
                           }*/
                        }
                        OYears_In_Navodyami = list.getProperty("Years_In_Navodyami");
                        if (!OYears_In_Navodyami.toString().equals("anyType{}") && !OYears_In_Navodyami.toString().equals(null)) {
                            SYears_In_Navodyami = (SoapPrimitive) list.getProperty("Years_In_Navodyami");
                            str_Years_In_Navodyami = SYears_In_Navodyami.toString();
                            Log.d("Years_In_Navodyami", str_Years_In_Navodyami);
                            //  edt_navodyami.setText(str_Years_In_Navodyami);
                        }
                        OAcademic_Year = list.getProperty("Academic_Year");
                        if (!OAcademic_Year.toString().equals("anyType{}") && !OAcademic_Year.toString().equals(null)) {
                            SAcademic_Year = (SoapPrimitive) list.getProperty("Academic_Year");
                            str_Academic_Year = SAcademic_Year.toString();
                            Log.d("Academic_Year", str_Academic_Year);
                        }
                        OApplication_Date = list.getProperty("Application_Date");
                    /*   if (!OApplication_Date.toString().equals("anyType{}") && !OApplication_Date.toString().equals(null)) {
                           SApplication_Date = (SoapPrimitive) list.getProperty("Application_Date");
                           str_Application_Date = SApplication_Date.toString();
                           Log.d("Application_Date", str_Application_Date);

                           SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
                           SimpleDateFormat dateFormatDisplay= new SimpleDateFormat("dd-MM-yyyy");
                           try {
                               Date d=dateFormat.parse(str_Application_Date);
                               System.out.println("Formated"+dateFormat.format(d));
                               appldate_seterror_TV.setVisibility(View.GONE);
                               edt_appldate.setText(dateFormatDisplay.format(d).toString());
                               str_appldate=dateFormat.format(d);

                           }
                           catch(Exception e) {
                               System.out.println("Excep"+e);
                           }
                       }else{
                           Date date = new Date();
                           Log.i("Tag_time", "date1=" + date);
                           SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
                           SimpleDateFormat dateFormatDisplay= new SimpleDateFormat("dd-MM-yyyy");
                           try {
                               // Date d=dateFormat.parse(date);
                               // String PresentDayStr = dateFormat.format(date);
                               System.out.println("Formated"+dateFormat.format(date));
                               edt_appldate.setText(dateFormatDisplay.format(date).toString());
                               str_appldate=dateFormat.format(date);

                           }
                           catch(Exception e) {
                               //java.text.ParseException: Unparseable date: Geting error
                               System.out.println("Excep"+e);
                           }*/

                        Class_AddApplicationDetails class_addApplicationDetails = new Class_AddApplicationDetails(str_FirstName, str_MiddleName, str_LastName, str_MobileNo, str_EmailId, str_StateId, str_DistrictId, str_TalukaId, str_VillageId, str_SectorId, str_BusinessName, "MOB", str_UserId, str_Enquiry_Id,str_gender,str_Economic_Status,str_WhatApp_No,str_DOB,str_Qualification,str_Social_Category,str_Business_Address,str_Years_In_Business,str_year_In_Current_Business,str_Early_Sector,str_Know_Navodyami,str_UNDP_Member_Before,str_Years_In_UNDP,str_Navodyami_Member_Before,str_Years_In_Navodyami,str_Aadhar_No,str_Street,str_Application_Date,str_Application_Slno,str_Academic_Year);
                        addApplicationList.add(class_addApplicationDetails);
                        //  DBCreate_AddApplicationDetails_insert_2SQLiteDB(addApplicationList);

                        //  ApplicationListModel item = new ApplicationListModel(str_Application_Slno,str_FirstName,str_MobileNo,str_BusinessName,str_Status,str_Academic_Year,str_Application_Fees,str_Enquiry_Id,"online");
                        //  feesList.add(item);
                        //-----------------------------------Business Details----------------------------------------
                        OisManufacture = list.getProperty("isManufacture");
                        if (!OisManufacture.toString().equals("anyType{}") && !OisManufacture.toString().equals(null)) {
                            SisManufacture = (SoapPrimitive) list.getProperty("isManufacture");
                            str_isManufacture = SisManufacture.toString();
                            Log.d("isManufacture", str_isManufacture);
                        }
                        OSell_Product1 = list.getProperty("Sell_Product1");
                        if (!OSell_Product1.toString().equals("anyType{}") && !OSell_Product1.toString().equals(null)) {
                            SSell_Product1 = (SoapPrimitive) list.getProperty("Sell_Product1");
                            str_Sell_Product1 = SSell_Product1.toString();
                            Log.d("Sell_Product1", str_Sell_Product1);
                        }
                        OSell_Product2 = list.getProperty("Sell_Product2");
                        if (!OSell_Product2.toString().equals("anyType{}") && !OSell_Product2.toString().equals(null)) {
                            SSell_Product2 = (SoapPrimitive) list.getProperty("Sell_Product2");
                            str_Sell_Product2 = SSell_Product2.toString();
                            Log.d("Sell_Product2", str_Sell_Product2);
                        }
                        OSell_Product3 = list.getProperty("Sell_Product3");
                        if (!OSell_Product3.toString().equals("anyType{}") && !OSell_Product3.toString().equals(null)) {
                            SSell_Product3 = (SoapPrimitive) list.getProperty("Sell_Product3");
                            str_Sell_Product3 = SSell_Product3.toString();
                            Log.d("Sell_Product3", str_Sell_Product3);
                        }
                        OisHave_License = list.getProperty("isHave_License");
                        if (!OisHave_License.toString().equals("anyType{}") && !OisHave_License.toString().equals(null)) {
                            SisHave_License = (SoapPrimitive) list.getProperty("isHave_License");
                            str_isHave_License = SisHave_License.toString();
                            Log.d("isHave_License", str_isHave_License);
                        }
                        OWhich_License = list.getProperty("Which_License");
                        if (!OWhich_License.toString().equals("anyType{}") && !OWhich_License.toString().equals(null)) {
                            SWhich_License = (SoapPrimitive) list.getProperty("Which_License");
                            str_Which_License = SWhich_License.toString();
                            Log.d("Which_License", str_Which_License);
                        }
                        OTotal_Employee = list.getProperty("Total_Employee");
                        if (!OTotal_Employee.toString().equals("anyType{}") && !OTotal_Employee.toString().equals(null)) {
                            STotal_Employee = (SoapPrimitive) list.getProperty("Total_Employee");
                            str_Total_Employee = STotal_Employee.toString();
                            Log.d("Total_Employee", str_Total_Employee);
                        }
                        OBusiness_Year = list.getProperty("Business_Year");
                        if (!OBusiness_Year.toString().equals("anyType{}") && !OTotal_Employee.toString().equals(null)) {
                            SBusiness_Year = (SoapPrimitive) list.getProperty("Business_Year");
                            str_Business_Year = SBusiness_Year.toString();
                            Log.d("Total_Employee", str_Business_Year);
                        }
                       /* OPermanent_Employee = list.getProperty("Permanent_Employee");
                        if (!OPermanent_Employee.toString().equals("anyType{}") && !OPermanent_Employee.toString().equals(null)) {
                            SPermanent_Employee = (SoapPrimitive) list.getProperty("Permanent_Employee");
                            str_Permanent_Employee = SPermanent_Employee.toString();
                            Log.d("Permanent_Employee", str_Permanent_Employee);
                        }*/
                        OTake_Skilled_Employee = list.getProperty("Take_Skilled_Employee");
                        if (!OTake_Skilled_Employee.toString().equals("anyType{}") && !OTake_Skilled_Employee.toString().equals(null)) {
                            STake_Skilled_Employee = (SoapPrimitive) list.getProperty("Take_Skilled_Employee");
                            str_Take_Skilled_Employee = STake_Skilled_Employee.toString();
                            Log.d("Take_Skilled_Employee", str_Take_Skilled_Employee);
                        }
                        OHire_Outside_Family = list.getProperty("Hire_Outside_Family");
                        if (!OHire_Outside_Family.toString().equals("anyType{}") && !OHire_Outside_Family.toString().equals(null)) {
                            SHire_Outside_Family = (SoapPrimitive) list.getProperty("Hire_Outside_Family");
                            str_Hire_Outside_Family = SHire_Outside_Family.toString();
                            Log.d("Hire_Outside_Family", str_Hire_Outside_Family);
                        }
                        OOwn_Rent_Machine = list.getProperty("Own_Rent_Machine");
                        if (!OOwn_Rent_Machine.toString().equals("anyType{}") && !OOwn_Rent_Machine.toString().equals(null)) {
                            SOwn_Rent_Machine = (SoapPrimitive) list.getProperty("Own_Rent_Machine");
                            str_Own_Rent_Machine = SOwn_Rent_Machine.toString();
                            Log.d("Own_Rent_Machine", str_Own_Rent_Machine);
                        }
                        OOwnership = list.getProperty("Ownership");
                        if (!OOwnership.toString().equals("anyType{}") && !OOwnership.toString().equals(null)) {
                            SOwnership = (SoapPrimitive) list.getProperty("Ownership");
                            str_Ownership = SOwnership.toString();
                            Log.d("str_Ownership", str_Ownership);
                        }
                        O_Where_Sell_Products = list.getProperty("Where_Sell_Products");
                        if (!O_Where_Sell_Products.toString().equals("anyType{}") && !O_Where_Sell_Products.toString().equals(null)) {
                            S_Where_Sell_Products = (SoapPrimitive) list.getProperty("Where_Sell_Products");
                            str_Where_Sell_Products = S_Where_Sell_Products.toString();
                            Log.d("Where_Sell_Products", str_Where_Sell_Products);
                        }
                        O_Earn_Most_Channel = list.getProperty("Earn_Most_Channel");
                        if (!O_Earn_Most_Channel.toString().equals("anyType{}") && !O_Earn_Most_Channel.toString().equals(null)) {
                            S_Earn_Most_Channel = (SoapPrimitive) list.getProperty("Earn_Most_Channel");
                            str_Earn_Most_Channel = S_Earn_Most_Channel.toString();
                            Log.d("Earn_Most_Channel", str_Earn_Most_Channel);
                        }

                        O_Which_Machine = list.getProperty("Which_Machine");
                        if (!O_Which_Machine.toString().equals("anyType{}") && !O_Which_Machine.toString().equals(null)) {
                            S_Which_Machine = (SoapPrimitive) list.getProperty("Which_Machine");
                            str_Which_Machine = S_Which_Machine.toString();
                            Log.d("Which_Machine", str_Which_Machine);
                        }

                        SoapObject materialList = (SoapObject) list.getProperty("p_Permanent_Employee");
                        for (int j = 0; j < materialList.getPropertyCount(); j++) {
                            SoapObject vmPermanent_Employee = (SoapObject) materialList.getProperty(j);
                            String status = vmPermanent_Employee.getProperty("Status").toString();
                            if (status.equalsIgnoreCase("Success")) {
                                //  int icount=j;
                                if (j == 0) {
                                    O_p_Year = vmPermanent_Employee.getProperty("p_Year");
                                    if (!O_p_Year.toString().equals("anyType{}") && !O_p_Year.toString().equals(null)) {
                                        S_p_Year = (SoapPrimitive) vmPermanent_Employee.getProperty("p_Year");
                                        Log.d("p_Year 1", S_p_Year.toString());
                                        str_str_p_Year1 = S_p_Year.toString();
                                    }
                                    O_TotalMale = vmPermanent_Employee.getProperty("TotalMale");
                                    if (!O_TotalMale.toString().equals("anyType") && !O_TotalMale.toString().equals(null) && !O_TotalMale.toString().equals("anyType{}")) {
                                        S_TotalMale = (SoapPrimitive) vmPermanent_Employee.getProperty("TotalMale");
                                        Log.d("TotalMale1", S_TotalMale.toString());
                                        str_TotalMale1 = S_TotalMale.toString();
                                    }
                                    O_TotalFemale = vmPermanent_Employee.getProperty("TotalFemale");
                                    if (!O_TotalFemale.toString().equals("anyType") && !O_TotalFemale.toString().equals(null) && !O_TotalFemale.toString().equals("anyType{}")) {
                                        S_TotalFemale = (SoapPrimitive) vmPermanent_Employee.getProperty("TotalFemale");
                                        Log.d("TotalFemale1", S_TotalFemale.toString());
                                        str_TotalFemale1 = S_TotalFemale.toString();
                                    }
                                }
                                if (j == 1) {
                                    O_p_Year = vmPermanent_Employee.getProperty("p_Year");
                                    if (!O_p_Year.toString().equals("anyType{}") && !O_p_Year.toString().equals(null)) {
                                        S_p_Year = (SoapPrimitive) vmPermanent_Employee.getProperty("p_Year");
                                        Log.d("p_Year 2", S_p_Year.toString());
                                        str_p_Year2 = S_p_Year.toString();
                                    }
                                    O_TotalMale = vmPermanent_Employee.getProperty("TotalMale");
                                    if (!O_TotalMale.toString().equals("anyType") && !O_TotalMale.toString().equals(null) && !O_TotalMale.toString().equals("anyType{}")) {
                                        S_TotalMale = (SoapPrimitive) vmPermanent_Employee.getProperty("TotalMale");
                                        Log.d("TotalMale2", S_TotalMale.toString());
                                        str_TotalMale2 = S_TotalMale.toString();
                                    }
                                    O_TotalFemale = vmPermanent_Employee.getProperty("TotalFemale");
                                    if (!O_TotalFemale.toString().equals("anyType") && !O_TotalFemale.toString().equals(null) && !O_TotalFemale.toString().equals("anyType{}")) {
                                        S_TotalFemale = (SoapPrimitive) vmPermanent_Employee.getProperty("TotalFemale");
                                        Log.d("TotalFemale2", S_TotalFemale.toString());
                                        str_TotalFemale2 = S_TotalFemale.toString();
                                    }
                                }
                                if (j == 2) {
                                    O_p_Year = vmPermanent_Employee.getProperty("p_Year");
                                    if (!O_p_Year.toString().equals("anyType{}") && !O_p_Year.toString().equals(null)) {
                                        S_p_Year = (SoapPrimitive) vmPermanent_Employee.getProperty("p_Year");
                                        Log.d("p_Year 3", S_p_Year.toString());
                                        str_p_Year3 = S_p_Year.toString();
                                    }
                                    O_TotalMale = vmPermanent_Employee.getProperty("TotalMale");
                                    if (!O_TotalMale.toString().equals("anyType") && !O_TotalMale.toString().equals(null) && !O_TotalMale.toString().equals("anyType{}")) {
                                        S_TotalMale = (SoapPrimitive) vmPermanent_Employee.getProperty("TotalMale");
                                        Log.d("TotalMale3", S_TotalMale.toString());
                                        str_TotalMale3 = S_TotalMale.toString();
                                    }
                                    O_TotalFemale = vmPermanent_Employee.getProperty("TotalFemale");
                                    if (!O_TotalFemale.toString().equals("anyType") && !O_TotalFemale.toString().equals(null) && !O_TotalFemale.toString().equals("anyType{}")) {
                                        S_TotalFemale = (SoapPrimitive) vmPermanent_Employee.getProperty("TotalFemale");
                                        Log.d("TotalFemale3", S_TotalFemale.toString());
                                        str_TotalFemale3 = S_TotalFemale.toString();
                                    }
                                }
                                /*O_TotalMale = vmPermanent_Employee.getProperty("TotalMale");
                                if (!O_TotalMale.toString().equals("anyType") && !O_TotalMale.toString().equals(null) && !O_TotalMale.toString().equals("anyType{}")) {
                                    S_TotalMale = (SoapPrimitive) vmPermanent_Employee.getProperty("TotalMale");
                                    Log.d("TotalMale", S_TotalMale.toString());
                                    str_TotalMale = S_TotalMale.toString();
                                }
                                O_TotalFemale = vmPermanent_Employee.getProperty("TotalFemale");
                                if (!O_TotalFemale.toString().equals("anyType") && !O_TotalFemale.toString().equals(null) && !O_TotalFemale.toString().equals("anyType{}")) {
                                    S_TotalFemale = (SoapPrimitive) vmPermanent_Employee.getProperty("TotalFemale");
                                    Log.d("TotalFemale", S_TotalFemale.toString());
                                    str_TotalFemale = S_TotalFemale.toString();
                                }*/
                                Log.e("tag", "TotalFemale=" + str_TotalFemale + "str_TotalMale=" + str_TotalMale + "str_p_Year=" + str_p_Year);
                                /*if (j == 0) {
                                    str_str_p_Year1 = str_p_Year;
                                    str_TotalFemale1 = str_TotalFemale;
                                    str_TotalMale1 = str_TotalMale;
                                }
                                if (j == 1) {
                                    str_p_Year2 = str_p_Year;
                                    str_TotalFemale2 = str_TotalFemale;
                                    str_TotalMale2 = str_TotalMale;
                                }
                                if (j == 2) {
                                    str_p_Year3 = str_p_Year;
                                    str_TotalFemale3 = str_TotalFemale;
                                    str_TotalMale3 = str_TotalMale;
                                }*/
                            }
                        }

                      /*  SoapObject Permanent_Employee = (SoapObject) result.getProperty("p_Permanent_Employee");
                      if(result.toString().contains("p_Permanent_Employee"))
                        {
                           // SoapObject Permanent_EmployeeList = (SoapObject) Permanent_Employee.getProperty(i);

                              SoapObject Permanent_EmployeeList = (SoapObject) Permanent_Employee.getProperty("p_Permanent_Employee");
                            for (int j = 0; j < Permanent_EmployeeList.getPropertyCount(); j++)
                            {
                                SoapObject vmPermanent_Employee = (SoapObject) Permanent_EmployeeList.getProperty(j);

                                O_p_Year = vmPermanent_Employee.getProperty("p_Year");
                                if (!O_p_Year.toString().equals("anyType{}") && !O_p_Year.toString().equals(null)) {
                                    S_p_Year = (SoapPrimitive) vmPermanent_Employee.getProperty("p_Year");
                                    Log.d("p_Year", S_p_Year.toString());
                                    str_p_Year = S_p_Year.toString();
                                }

                                O_TotalMale = vmPermanent_Employee.getProperty("TotalMale");
                                if (!O_TotalMale.toString().equals("anyType") && !O_TotalMale.toString().equals(null) && !O_TotalMale.toString().equals("anyType{}")) {
                                    S_TotalMale = (SoapPrimitive) vmPermanent_Employee.getProperty("TotalMale");
                                    Log.d("TotalMale", S_TotalMale.toString());
                                    str_TotalMale = S_TotalMale.toString();
                                }
                                O_TotalFemale = vmPermanent_Employee.getProperty("TotalFemale");
                                if (!O_TotalFemale.toString().equals("anyType") && !O_TotalFemale.toString().equals(null) && !O_TotalFemale.toString().equals("anyType{}")) {
                                    S_TotalFemale = (SoapPrimitive) vmPermanent_Employee.getProperty("TotalFemale");
                                    Log.d("TotalFemale", S_TotalFemale.toString());
                                    str_TotalFemale = S_TotalFemale.toString();
                                }
                                Log.e("tag","TotalFemale="+str_TotalFemale+"str_TotalMale="+str_TotalMale+"str_p_Year="+str_p_Year);
                                if(j==0){
                                    str_str_p_Year1=str_p_Year;
                                    str_TotalFemale1=str_TotalFemale;
                                    str_TotalMale1=str_TotalMale;
                                }
                                if(j==1){
                                    str_p_Year2=str_p_Year;
                                    str_TotalFemale2=str_TotalFemale;
                                    str_TotalMale2=str_TotalMale;
                                }
                                if(j==2){
                                    str_p_Year3=str_p_Year;
                                    str_TotalFemale3=str_TotalFemale;
                                    str_TotalMale3=str_TotalMale;
                                }
                            }
                        }*/
                        if (list.toString().contains("p_TurnOver")) {
                            SoapObject Permanent_EmployeeList = (SoapObject) list.getProperty("p_TurnOver");
                            for (int j = 0; j < Permanent_EmployeeList.getPropertyCount(); j++) {
                                SoapObject vmPermanent_Employee = (SoapObject) Permanent_EmployeeList.getProperty(j);


                                String status = vmPermanent_Employee.getProperty("Status").toString();

                                if (status.equalsIgnoreCase("Success")) {

                                   /* O_p_Year = vmPermanent_Employee.getProperty("p_Year");
                                    if (!O_p_Year.toString().equals("anyType{}") && !O_p_Year.toString().equals(null)) {
                                        S_p_Year = (SoapPrimitive) vmPermanent_Employee.getProperty("p_Year");
                                        Log.d("p_Year", S_p_Year.toString());
                                        str_p_Year = S_p_Year.toString();
                                    }
                                    O_TotalAmount = vmPermanent_Employee.getProperty("TotalAmount");
                                    if (!O_TotalAmount.toString().equals("anyType") && !O_TotalAmount.toString().equals(null) && !O_TotalAmount.toString().equals("anyType{}")) {
                                        S_TotalAmount = (SoapPrimitive) vmPermanent_Employee.getProperty("TotalAmount");
                                        Log.d("TotalAmount", S_TotalAmount.toString());
                                        str_TotalAmount = S_TotalAmount.toString();
                                    }*/
                                    if (j == 0) {
                                        str_p_Year = null;
                                        O_p_Year = vmPermanent_Employee.getProperty("p_Year");
                                        if (!O_p_Year.toString().equals("anyType{}") && !O_p_Year.toString().equals(null)) {
                                            S_p_Year = (SoapPrimitive) vmPermanent_Employee.getProperty("p_Year");
                                            Log.d("p_Year 1", S_p_Year.toString());
                                            str_p_Year = S_p_Year.toString();
                                        }
                                        O_TotalAmount = vmPermanent_Employee.getProperty("TotalAmount");
                                        if (!O_TotalAmount.toString().equals("anyType") && !O_TotalAmount.toString().equals(null) && !O_TotalAmount.toString().equals("anyType{}")) {
                                            S_TotalAmount = (SoapPrimitive) vmPermanent_Employee.getProperty("TotalAmount");
                                            Log.d("TotalAmount", S_TotalAmount.toString());
                                            str_TotalAmount1 = S_TotalAmount.toString();
                                        }
                                        if (str_str_p_Year1 == null) {
                                            str_str_p_Year1 = str_p_Year;
                                        }

                                        //str_TotalAmount1 = str_TotalAmount;
                                    }
                                    if (j == 1) {
                                        str_p_Year = null;
                                        O_p_Year = vmPermanent_Employee.getProperty("p_Year");
                                        if (!O_p_Year.toString().equals("anyType{}") && !O_p_Year.toString().equals(null)) {
                                            S_p_Year = (SoapPrimitive) vmPermanent_Employee.getProperty("p_Year");
                                            Log.d("p_Year 2", S_p_Year.toString());
                                            str_p_Year = S_p_Year.toString();
                                        }
                                        O_TotalAmount = vmPermanent_Employee.getProperty("TotalAmount");
                                        if (!O_TotalAmount.toString().equals("anyType") && !O_TotalAmount.toString().equals(null) && !O_TotalAmount.toString().equals("anyType{}")) {
                                            S_TotalAmount = (SoapPrimitive) vmPermanent_Employee.getProperty("TotalAmount");
                                            Log.d("TotalAmount2", S_TotalAmount.toString());
                                            str_TotalAmount2 = S_TotalAmount.toString();
                                        }
                                        if (str_p_Year2 == null) {
                                            str_p_Year2 = str_p_Year;
                                        }
                                        // str_TotalAmount2 = str_TotalAmount;
                                    }
                                    if (j == 2) {
                                        str_p_Year = null;
                                        O_p_Year = vmPermanent_Employee.getProperty("p_Year");
                                        if (!O_p_Year.toString().equals("anyType{}") && !O_p_Year.toString().equals(null)) {
                                            S_p_Year = (SoapPrimitive) vmPermanent_Employee.getProperty("p_Year");
                                            Log.d("p_Year 3", S_p_Year.toString());
                                            str_p_Year = S_p_Year.toString();
                                        }
                                        O_TotalAmount = vmPermanent_Employee.getProperty("TotalAmount");
                                        if (!O_TotalAmount.toString().equals("anyType") && !O_TotalAmount.toString().equals(null) && !O_TotalAmount.toString().equals("anyType{}")) {
                                            S_TotalAmount = (SoapPrimitive) vmPermanent_Employee.getProperty("TotalAmount");
                                            Log.d("TotalAmount3", S_TotalAmount.toString());
                                            str_TotalAmount3 = S_TotalAmount.toString();
                                        }
                                        if (str_p_Year3 == null) {
                                            str_p_Year3 = str_p_Year;
                                        }
                                        // str_TotalAmount3 = str_TotalAmount;
                                    }
                                }
                            }
                        }
                        if (list.toString().contains("p_Profit")) {
                            SoapObject Permanent_EmployeeList = (SoapObject) list.getProperty("p_Profit");
                            for (int j = 0; j < Permanent_EmployeeList.getPropertyCount(); j++) {
                                SoapObject vmPermanent_Employee = (SoapObject) Permanent_EmployeeList.getProperty(j);
                                String status = vmPermanent_Employee.getProperty("Status").toString();
                                if (status.equalsIgnoreCase("Success")) {


                                   /* O_p_Year = vmPermanent_Employee.getProperty("p_Year");
                                    if (!O_p_Year.toString().equals("anyType{}") && !O_p_Year.toString().equals(null)) {
                                        S_p_Year = (SoapPrimitive) vmPermanent_Employee.getProperty("p_Year");
                                        Log.d("p_Year", S_p_Year.toString());
                                        str_p_Year = S_p_Year.toString();
                                    }
                                    O_TotalAmountProfit = vmPermanent_Employee.getProperty("TotalAmount");
                                    if (!O_TotalAmountProfit.toString().equals("anyType") && !O_TotalAmountProfit.toString().equals(null) && !O_TotalAmountProfit.toString().equals("anyType{}")) {
                                        S_TotalAmountProfit = (SoapPrimitive) vmPermanent_Employee.getProperty("TotalAmount");
                                        Log.d("TotalAmountProfit", S_TotalAmountProfit.toString());
                                        str_TotalAmountProfit = S_TotalAmountProfit.toString();
                                    }*/
                                    if (j == 0) {
                                        O_p_Year = vmPermanent_Employee.getProperty("p_Year");
                                        if (!O_p_Year.toString().equals("anyType{}") && !O_p_Year.toString().equals(null)) {
                                            S_p_Year = (SoapPrimitive) vmPermanent_Employee.getProperty("p_Year");
                                            Log.d("p_Year 1", S_p_Year.toString());
                                            str_p_Year = S_p_Year.toString();
                                        }
                                        O_TotalAmountProfit = vmPermanent_Employee.getProperty("TotalAmount");
                                        if (!O_TotalAmountProfit.toString().equals("anyType") && !O_TotalAmountProfit.toString().equals(null) && !O_TotalAmountProfit.toString().equals("anyType{}")) {
                                            S_TotalAmountProfit = (SoapPrimitive) vmPermanent_Employee.getProperty("TotalAmount");
                                            Log.d("TotalAmountProfit1", S_TotalAmountProfit.toString());
                                            str_TotalAmountProfit1 = S_TotalAmountProfit.toString();
                                        }
                                        if (str_str_p_Year1 == null) {
                                            str_str_p_Year1 = str_p_Year;
                                        }
                                        // str_TotalAmountProfit1 = str_TotalAmountProfit;
                                    }
                                    if (j == 1) {
                                        O_p_Year = vmPermanent_Employee.getProperty("p_Year");
                                        if (!O_p_Year.toString().equals("anyType{}") && !O_p_Year.toString().equals(null)) {
                                            S_p_Year = (SoapPrimitive) vmPermanent_Employee.getProperty("p_Year");
                                            Log.d("p_Year 2", S_p_Year.toString());
                                            str_p_Year = S_p_Year.toString();
                                        }
                                        O_TotalAmountProfit = vmPermanent_Employee.getProperty("TotalAmount");
                                        if (!O_TotalAmountProfit.toString().equals("anyType") && !O_TotalAmountProfit.toString().equals(null) && !O_TotalAmountProfit.toString().equals("anyType{}")) {
                                            S_TotalAmountProfit = (SoapPrimitive) vmPermanent_Employee.getProperty("TotalAmount");
                                            Log.d("TotalAmountProfit2", S_TotalAmountProfit.toString());
                                            str_TotalAmountProfit2 = S_TotalAmountProfit.toString();
                                        }
                                        if (str_p_Year2 == null) {
                                            str_p_Year2 = str_p_Year;
                                        }
                                        // str_TotalAmountProfit2 = str_TotalAmountProfit;
                                    }
                                    if (j == 2) {

                                        O_p_Year = vmPermanent_Employee.getProperty("p_Year");
                                        if (!O_p_Year.toString().equals("anyType{}") && !O_p_Year.toString().equals(null)) {
                                            S_p_Year = (SoapPrimitive) vmPermanent_Employee.getProperty("p_Year");
                                            Log.d("p_Year 3", S_p_Year.toString());
                                            str_p_Year = S_p_Year.toString();
                                        }
                                        O_TotalAmountProfit = vmPermanent_Employee.getProperty("TotalAmount");
                                        if (!O_TotalAmountProfit.toString().equals("anyType") && !O_TotalAmountProfit.toString().equals(null) && !O_TotalAmountProfit.toString().equals("anyType{}")) {
                                            S_TotalAmountProfit = (SoapPrimitive) vmPermanent_Employee.getProperty("TotalAmount");
                                            Log.d("TotalAmountProfit3", S_TotalAmountProfit.toString());
                                            str_TotalAmountProfit3 = S_TotalAmountProfit.toString();
                                        }
                                        if (str_p_Year2 == null) {
                                            str_p_Year3 = str_p_Year;
                                        }

                                        // str_TotalAmountProfit3 = str_TotalAmountProfit;
                                    }
                                }
                            }
                        }
                        Class_AddApplicationTwoDetails class_addApplicationTwoDetails = new Class_AddApplicationTwoDetails(str_isManufacture, str_isHave_License, str_Which_License, str_Sell_Product1,
                                str_Sell_Product2, str_Sell_Product3, str_Business_Year, str_Ownership, str_str_p_Year1, str_TotalFemale1, str_TotalMale1, str_p_Year2, str_TotalFemale2, str_TotalMale2,
                                str_p_Year3, str_TotalFemale3, str_TotalMale3, str_Take_Skilled_Employee, str_Hire_Outside_Family, str_TotalAmount1, str_TotalAmount2, str_TotalAmount3, str_TotalAmountProfit1,
                                str_TotalAmountProfit2, str_TotalAmountProfit3, str_Which_Machine, str_Own_Rent_Machine, str_Where_Sell_Products, str_Earn_Most_Channel, str_Enquiry_Id, str_Total_Employee, str_Application_Slno);
                        addApplicationTwoList.add(class_addApplicationTwoDetails);
                        //DBCreate_AddApplicationTwoDetails_insert_2SQLiteDB(addApplicationTwoList);

                        //----------------------------Credit Details--------------------------------------
                        OSource_Of_Business = list.getProperty("Source_Of_Business");
                        if (!OSource_Of_Business.toString().equals("anyType{}") && !OSource_Of_Business.toString().equals(null)) {
                            SSource_Of_Business = (SoapPrimitive) list.getProperty("Source_Of_Business");
                            str_Source_Of_Business = SSource_Of_Business.toString();
                            Log.d("Source_Of_Business", str_Source_Of_Business);
                        }
                        OInitial_Investment = list.getProperty("Initial_Investment");
                        if (!OInitial_Investment.toString().equals("anyType{}") && !OInitial_Investment.toString().equals(null)) {
                            SInitial_Investment = (SoapPrimitive) list.getProperty("Initial_Investment");
                            str_Initial_Investment = SInitial_Investment.toString();
                            Log.d("Initial_Investment", str_Initial_Investment);
                        }
                        OisKnowledge_Loan_Process = list.getProperty("isKnowledge_Loan_Process");
                        if (!OisKnowledge_Loan_Process.toString().equals("anyType{}") && !OisKnowledge_Loan_Process.toString().equals(null)) {
                            SisKnowledge_Loan_Process = (SoapPrimitive) list.getProperty("isKnowledge_Loan_Process");
                            str_isKnowledge_Loan_Process = SisKnowledge_Loan_Process.toString();
                            Log.d("tag", "isKnowledge_Loan_Process" + str_isKnowledge_Loan_Process);
                        }
                        OApplied_Loan_Amount = list.getProperty("Applied_Loan_Amount");
                        if (!OApplied_Loan_Amount.toString().equals("anyType{}") && !OApplied_Loan_Amount.toString().equals(null)) {
                            SApplied_Loan_Amount = (SoapPrimitive) list.getProperty("Applied_Loan_Amount");
                            str_Applied_Loan_Amount = SApplied_Loan_Amount.toString();
                            Log.d("Initial_Investment", str_Applied_Loan_Amount);
                        }
                        OSanctioned_Loan_Amount = list.getProperty("Sanctioned_Loan_Amount");
                        if (!OSanctioned_Loan_Amount.toString().equals("anyType{}") && !OSanctioned_Loan_Amount.toString().equals(null)) {
                            SSanctioned_Loan_Amount = (SoapPrimitive) list.getProperty("Sanctioned_Loan_Amount");
                            str_Sanctioned_Loan_Amount = SSanctioned_Loan_Amount.toString();
                            Log.d("Sanctioned_Loan_Amount", str_Sanctioned_Loan_Amount);
                        }
                        OInterest_Rate = list.getProperty("Interest_Rate");
                        if (!OInterest_Rate.toString().equals("anyType{}") && !OInterest_Rate.toString().equals(null)) {
                            SInterest_Rate = (SoapPrimitive) list.getProperty("Interest_Rate");
                            str_Interest_Rate = SInterest_Rate.toString();
                            Log.d("Interest_Rate", str_Interest_Rate);
                        }
                        ORepayment_Period = list.getProperty("Repayment_Period");
                        if (!ORepayment_Period.toString().equals("anyType{}") && !ORepayment_Period.toString().equals(null)) {
                            SRepayment_Period = (SoapPrimitive) list.getProperty("Repayment_Period");
                            str_Repayment_Period = SRepayment_Period.toString();
                            Log.d("Repayment_Period", str_Repayment_Period);
                        }
                        OApplied_At = list.getProperty("Applied_At");
                        if (!OApplied_At.toString().equals("anyType{}") && !OApplied_At.toString().equals(null)) {
                            SApplied_At = (SoapPrimitive) list.getProperty("Applied_At");
                            str_Applied_At = SApplied_At.toString();
                            Log.d("Applied_At", str_Applied_At);
                        }
                        OPayment_Mode = list.getProperty("Payment_Mode");
                        if (!OPayment_Mode.toString().equals("anyType{}") && !OPayment_Mode.toString().equals(null)) {
                            SPayment_Mode = (SoapPrimitive) list.getProperty("Payment_Mode");
                            str_Payment_Mode = SPayment_Mode.toString();
                            Log.d("Payment_Mode", str_Payment_Mode);
                        }
                        OLoan_For_Product_Improvement = list.getProperty("Loan_For_Product_Improvement");
                        if (!OLoan_For_Product_Improvement.toString().equals("anyType{}") && !OLoan_For_Product_Improvement.toString().equals(null)) {
                            SLoan_For_Product_Improvement = (SoapPrimitive) list.getProperty("Loan_For_Product_Improvement");
                            str_Loan_For_Product_Improvement = SLoan_For_Product_Improvement.toString();
                            Log.d("tag", "Loan_For_Product_Improvement" + str_Loan_For_Product_Improvement);
                        }
                        OSanctioned_Loan_Amount = list.getProperty("Sanctioned_Loan_Amount");
                        if (!OSanctioned_Loan_Amount.toString().equals("anyType{}") && !OSanctioned_Loan_Amount.toString().equals(null)) {
                            SSanctioned_Loan_Amount = (SoapPrimitive) list.getProperty("Sanctioned_Loan_Amount");
                            str_Sanctioned_Loan_Amount = SSanctioned_Loan_Amount.toString();
                            Log.d("Sanctioned_Loan_Amount", str_Sanctioned_Loan_Amount);
                        }
                        OLoan_For_Equipment = list.getProperty("Loan_For_Equipment");
                        if (!OLoan_For_Equipment.toString().equals("anyType{}") && !OLoan_For_Equipment.toString().equals(null)) {
                            SLoan_For_Equipment = (SoapPrimitive) list.getProperty("Loan_For_Equipment");
                            str_Loan_For_Equipment = SLoan_For_Equipment.toString();
                            Log.d("Loan_For_Equipment", str_Loan_For_Equipment);
                        }
                        OLoan_For_Working_Expenses = list.getProperty("Loan_For_Working_Expenses");
                        if (!OLoan_For_Working_Expenses.toString().equals("anyType{}") && !OLoan_For_Working_Expenses.toString().equals(null)) {
                            SLoan_For_Working_Expenses = (SoapPrimitive) list.getProperty("Loan_For_Working_Expenses");
                            str_Loan_For_Working_Expenses = SLoan_For_Working_Expenses.toString();
                            Log.d("tag", "Loan_For_Working_Expenses" + str_Loan_For_Working_Expenses);
                        }
                        OLoan_For_Land = list.getProperty("Loan_For_Land");
                        if (!OLoan_For_Land.toString().equals("anyType{}") && !OLoan_For_Land.toString().equals(null)) {
                            SLoan_For_Land = (SoapPrimitive) list.getProperty("Loan_For_Land");
                            str_Loan_For_Land = SLoan_For_Land.toString();
                            Log.d("Sanctioned_Loan_Amount", str_Loan_For_Land);
                        }
                        OLoan_For_Finance_Trading = list.getProperty("Loan_For_Finance_Trading");
                        if (!OLoan_For_Finance_Trading.toString().equals("anyType{}") && !OLoan_For_Finance_Trading.toString().equals(null)) {
                            SLoan_For_Finance_Trading = (SoapPrimitive) list.getProperty("Loan_For_Finance_Trading");
                            str_Loan_For_Finance_Trading = SLoan_For_Finance_Trading.toString();
                            Log.d("tag", "Loan_For_Finance_Trading" + str_Loan_For_Finance_Trading);
                        }
                        OIsAccountVerified = list.getProperty("IsAccountVerified");
                        if (!OIsAccountVerified.toString().equals("anyType{}") && !OIsAccountVerified.toString().equals(null)) {
                            SIsAccountVerified = (SoapPrimitive) list.getProperty("IsAccountVerified");
                            str_IsAccountVerified = SIsAccountVerified.toString();
                            Log.d("tag", "IsAccountVerified" + str_IsAccountVerified);
                        }

                        String str_LastLoan = str_Loan_For_Product_Improvement + "," + str_Loan_For_Working_Expenses + "," + str_Loan_For_Land + "," + str_Loan_For_Finance_Trading + "," + str_Loan_For_Equipment;
                        //  String str_BusinessSource="1";
                        Log.e("tag", "str_LastLoan=" + str_LastLoan);
                        Class_AddApplicationThreeDetails class_addApplicationThreeDetails = new Class_AddApplicationThreeDetails(str_Source_Of_Business, str_LastLoan, str_Initial_Investment, str_isKnowledge_Loan_Process, str_Applied_Loan_Amount, str_Sanctioned_Loan_Amount, str_Interest_Rate, str_Applied_At, str_Enquiry_Id, str_Repayment_Period, str_Loan_For_Product_Improvement, str_Loan_For_Working_Expenses, str_Loan_For_Land, str_Loan_For_Equipment, str_Loan_For_Finance_Trading, str_Application_Slno, str_Application_Fees, str_Receipt_Date, str_Remark, str_Receipt_No, str_Payment_Mode,"online",str_IsAccountVerified);
                        addApplicationThreeList.add(class_addApplicationThreeDetails);

                        //DBCreate_AddApplicationThreeDetails_insert_2SQLiteDB(addApplicationThreeList);
                        DBCreate_CompliteApplicationDetails_insert_2SQLiteDB(addApplicationList,addApplicationTwoList,addApplicationThreeList);
                    }
                    else{
                        List_offlineApplicationDetails();
                    }

                }
          //      originalList = new ArrayList<ApplicationListModel>();
            //    originalList.addAll(feesList);
                //DBCreate_ListApplicationDetails_2SQLiteDB(originalList);

                Date date = new Date();
                Log.i("Tag_time", "date1=" + date);
                SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat dateFormatDisplay= new SimpleDateFormat("dd-MM-yyyy");
                try {
                    // Date d=dateFormat.parse(date);
                    // String PresentDayStr = dateFormat.format(date);
                    System.out.println("Formated"+dateFormat.format(date));
                    //  edt_verfiedDate.setText(dateFormatDisplay.format(date).toString());
                    String str_SyncDate=dateFormat.format(date);
                    editorSync_obj = sharedpreferencebook_SyncDate_Obj.edit();
                    editorSync_obj.putString(KeyValue_SyncDate, str_SyncDate);
                    editorSync_obj.apply();


                }
                catch(Exception e) {
                    //java.text.ParseException: Unparseable date: Geting error
                    System.out.println("Excep"+e);
                }

                //adapter.notifyDataSetChanged();

                /*originalList = new ArrayList<EnquiryListModel>();
                originalList.addAll(feesList);

                adapter.notifyDataSetChanged();
                initCollegeSpinner();*/
                progressDialog.dismiss();


                //     uploadfromDB_Statelist();

        /* uploadfromDB_Districtlist();
         uploadfromDB_Taluklist();
         uploadfromDB_Villagelist();*/
                //      uploadfromDB_Sectorlist();
            }

        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapObject getApplicationListing() {
        String METHOD_NAME = "Get_Application_Details_Sync";
        String SOAP_ACTION1 = "http://mis.navodyami.org/Get_Application_Details_Sync";
        String NAMESPACE = "http://mis.navodyami.org/";

        try{

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");

            request.addProperty("AcademicCode",acadamicCode);
            request.addProperty("User_Id",str_UserId);
            request.addProperty("Last_Sync_Date",Last_Sync_Date);

            Log.e("tag","request appl Details="+request);
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
                Log.d("tag","soapresponse Application List="+envelope.getResponse().toString());


                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("tag","soapresponse Application="+response.toString());

                //return null;

                return response;

            }
            catch(OutOfMemoryError ex){
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());
                final String exceptionStr = t.getMessage().toString();
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        catch(OutOfMemoryError ex){
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());
            final String exceptionStr = t.getMessage().toString();
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(),"Web Service Error", Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;

    }

   /* public class AcademicYear extends AsyncTask<Void, Void, SoapObject> {

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
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                catch (Exception t) {
                    Log.e("request fail", "> " + t.getMessage().toString());

                    final String exceptionStr = t.getMessage().toString();
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(context,"Web Service Error", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            catch(OutOfMemoryError ex){
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception t) {
                Log.d("exception outside",t.getMessage().toString());
                final String exceptionStr = t.getMessage().toString();
                getActivity().runOnUiThread(new Runnable() {
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
    *//*        progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*//*

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

           *//* ArrayAdapter dataAdapter3 = new ArrayAdapter(getActivity(), R.layout.simple_spinner_items, yearList);
            dataAdapter3.setDropDownViewResource(R.layout.spinnercustomstyle);
            // attaching data adapter to spinner
            spin_year.setAdapter(dataAdapter3);*//*
            uploadfromDB_Yearlist();



            //setProjectTypeSpinner(projectTypeList);

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
*/
    public void uploadfromDB_Yearlist() {

        SQLiteDatabase db_year = getActivity().openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_year.execSQL("CREATE TABLE IF NOT EXISTS YearList(YearID VARCHAR,YearName VARCHAR,Display_Year VARCHAR);");
        Cursor cursor = db_year.rawQuery("SELECT DISTINCT * FROM YearList", null);
        int x = cursor.getCount();
        Log.d("cursor count year", Integer.toString(x));

        int i = 0;
        arrayObj_Class_yearDetails2 = new Class_YearListDetails[x];
        if (cursor.moveToFirst()) {

            do {
                Class_YearListDetails innerObj_Class_yearList = new Class_YearListDetails();
                innerObj_Class_yearList.setYearID(cursor.getString(cursor.getColumnIndex("YearID")));
                innerObj_Class_yearList.setYear(cursor.getString(cursor.getColumnIndex("YearName")));


                arrayObj_Class_yearDetails2[i] = innerObj_Class_yearList;
                i++;

            } while (cursor.moveToNext());


        }//if ends

        db_year.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getContext(), R.layout.spinnercustomstyle, arrayObj_Class_yearDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercustomstyle);
            spin_year.setAdapter(dataAdapter);
            if(x>sel_yearsp) {
                spin_year.setSelection(sel_yearsp);
            }
        }

    }

    public void DBCreate_Yeardetails_insert_2SQLiteDB(String str_yearID, String str_yearname,String str_display_Year) {
        SQLiteDatabase db_yearlist = getActivity().openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_yearlist.execSQL("CREATE TABLE IF NOT EXISTS YearList(YearID VARCHAR,YearName VARCHAR,Display_Year VARCHAR);");


        String SQLiteQuery = "INSERT INTO YearList (YearID, YearName, Display_Year)" +
                " VALUES ('" + str_yearID + "','" + str_yearname + "','" + str_display_Year + "');";
        db_yearlist.execSQL(SQLiteQuery);


        //Log.e("str_yearID DB", str_yearID);
        // Log.e("str_yearname DB", str_yearname);
        db_yearlist.close();
    }

    public void deleteYearTable_B4insertion() {

        SQLiteDatabase db_yearlist_delete = getActivity().openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_yearlist_delete.execSQL("CREATE TABLE IF NOT EXISTS YearList(YearID VARCHAR,YearName VARCHAR,Display_Year VARCHAR);");
        Cursor cursor = db_yearlist_delete.rawQuery("SELECT * FROM YearList", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_yearlist_delete.delete("YearList", null, null);

        }
        db_yearlist_delete.close();
    }

    public void List_offlineApplicationDetails() {

        SQLiteDatabase db1 = getActivity().openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
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

        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM CompliteApplicationDetails ORDER BY tempId DESC", null);

        int x = cursor1.getCount();
        Log.d("tag","cursor Application list list Count="+ Integer.toString(x));

        int i = 0;
        // arrayObj_Class_GrampanchayatListDetails2 = new Class_GrampanchayatListDetails[x];

        feesList.clear();
        if (cursor1.moveToFirst()) {
            String YearCode = cursor1.getString(cursor1.getColumnIndex("Academic_Year"));
            if(YearCode.equalsIgnoreCase(String.valueOf(acadamicCode))||YearCode.equalsIgnoreCase("0")) {

                do {
                    String Business_Name,Application_Slno,First_Name,Mobile_No,Status,Application_Fees,Enquiry_Id,dataSyncStatus,tempId;

                    Application_Slno = cursor1.getString(cursor1.getColumnIndex("Application_Slno"));
                    First_Name = cursor1.getString(cursor1.getColumnIndex("FName"));
                    Business_Name = cursor1.getString(cursor1.getColumnIndex("BusinessName"));
                    Mobile_No = cursor1.getString(cursor1.getColumnIndex("MobileNo"));
                   // Status = cursor1.getString(cursor1.getColumnIndex("Status"));
                    Application_Fees = cursor1.getString(cursor1.getColumnIndex("ApplicationFees"));
                    Enquiry_Id=cursor1.getString(cursor1.getColumnIndex("EnquiryId"));
                    dataSyncStatus=cursor1.getString(cursor1.getColumnIndex("dataSyncStatus"));
                    tempId=cursor1.getString(cursor1.getColumnIndex("tempId"));
                    //   if (Status.equalsIgnoreCase("Success")) {
                    ApplicationListModel item = null;
                    item = new ApplicationListModel(Application_Slno,First_Name,Mobile_No, Business_Name,"success",YearCode,Application_Fees,Enquiry_Id,dataSyncStatus,tempId);
                    feesList.add(item);

                /*    } else {
                        feesList.clear();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "There are no Applications", Toast.LENGTH_LONG).show();
                    }*/
                    i++;


                } while (cursor1.moveToNext());
            }else {
                feesList.clear();
                adapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "There are no Applications", Toast.LENGTH_LONG).show();
            }


        }//if ends



        originalList = new ArrayList<ApplicationListModel>();
        originalList.addAll(feesList);
        adapter.notifyDataSetChanged();
        cursor1.close();
        db1.close();

    }

    //---------------------------------
    public void getOfflineAddOREditApplicationCount() {
        SQLiteDatabase db1 = getActivity().openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
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
        Log.d("tag", "cursor offline Application list Count" + Integer.toString(x));
        String Application_SlnoCount=null,EnquiryIdCount = null;
        int newCount=0,editCount=0;
        if (x > 0) {
            if (cursor1.moveToFirst()) {

                do {
                    EnquiryIdCount = cursor1.getString(cursor1.getColumnIndex("EnquiryId"));
                    Application_SlnoCount = cursor1.getString(cursor1.getColumnIndex("Application_Slno"));
                    if(Application_SlnoCount.equalsIgnoreCase("0")){
                        newCount=newCount+1;
                    }/*else if(!EnquiryIdCount.equalsIgnoreCase("0")&&Application_SlnoCount.equalsIgnoreCase("0")){
                        newCount=newCount+1;
                    }*/else{
                        editCount=editCount+1;
                    }
                } while (cursor1.moveToNext());
            }

          /*  Cursor cursor2 = db1.rawQuery("SELECT DISTINCT * FROM CompliteApplicationDetails WHERE dataSyncStatus='" + "offline" + "' AND EnquiryId='" + 0 + "' AND Application_Slno='" + 0 + "'", null);
            int x2 = cursor2.getCount();*/
            Log.d("tag", "cursor offline Application list Count newCount" + newCount);
            if (newCount != 0) {
                String x_str = String.valueOf(newCount);
                offline_count.setVisibility(View.VISIBLE);
                new_applicationCount.setText(x_str);
            }

         //   Cursor cursor3 = db1.rawQuery("SELECT DISTINCT * FROM CompliteApplicationDetails WHERE dataSyncStatus='" + "offline" + "' AND EnquiryId <> '" + "0" + "' AND Application_Slno <> '" + "0" + "'", null);
            /*Cursor findNormalItems = db1.query("items","dataSyncStatus", "type != ?",
                    new String[] { "onSale" });
          //  String where="tempId !=?";
            String isZero="0";
          String where="EnquiryId !=?";
            Cursor count=db1.rawQuery("CompliteApplicationDetails", where, new String[]{isZero});
*/
           // int x3 = cursor3.getCount();
            Log.d("tag", "cursor offline Application list Count editCount" + editCount);
            if (editCount != 0) {
                String x_str = String.valueOf(editCount);
                offline_count.setVisibility(View.VISIBLE);
                edit_applicationCount.setText(x_str);
            }
        }
    }
    public void getOfflineAddOREditApplication(){
        SQLiteDatabase db1 = getActivity().openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
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

        Cursor cursor11 = db1.rawQuery("SELECT DISTINCT * FROM CompliteApplicationDetails", null);
        int totalcount = cursor11.getCount();
        Log.d("tag","cursor offline Application list totalcount"+ Integer.toString(totalcount));
        Cursor cursor = db1.rawQuery("SELECT DISTINCT * FROM CompliteApplicationDetails WHERE dataSyncStatus='" + "offline" + "'", null);
        int x = cursor.getCount();
        Log.d("tag","cursor offline Application list Count"+ Integer.toString(x));
        if(x!=0) {
           cursor.moveToFirst();
           // for(int i=0;i<=1;i++){
           // for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                String Enquiry_Id = cursor.getString(cursor.getColumnIndex("EnquiryId"));
                String Application_Slno = cursor.getString(cursor.getColumnIndex("Application_Slno"));
                String tempId = cursor.getString(cursor.getColumnIndex("tempId"));
                if (Enquiry_Id.equalsIgnoreCase("0") && Application_Slno.equalsIgnoreCase("0")) {
                         /*   GetDataFromDB_AddApplicationDetails(Enquiry_Id, Application_Slno);
                            GetDatafromDB_AddApplicationTwoDetails(Enquiry_Id, Application_Slno);
                            getDatefromDB_AddApplicationThreeDetails(Enquiry_Id, Application_Slno);*/

                    AllDataEnquiry_Id=Enquiry_Id;
                    AllDataApplicationSlno=Application_Slno;
                    AllDatatempId=tempId;

                    GetDataFromDB_CompliteApplicationDetails(AllDataEnquiry_Id, AllDataApplicationSlno,AllDatatempId);
                    AddNewApplicationDetails addNewApplicationDetails1 = new AddNewApplicationDetails(getActivity());
                    addNewApplicationDetails1.execute(TempId_asyncTask);


                } else {
                    //if (!Enquiry_Id.equalsIgnoreCase("0") && Application_Slno.equalsIgnoreCase("0")) {

                    personalEnquiry_Id = Enquiry_Id;
                    personalApplicationSlno = Application_Slno;
                    personalTempId=tempId;
                    // GetDataFromDB_AddApplicationDetails(personalEnquiry_Id, personalApplicationSlno);
                    GetDataFromDB_CompliteApplicationDetails(personalEnquiry_Id, personalApplicationSlno,personalTempId);
                    UpdateApplicationDetails updateApplicationDetails = new UpdateApplicationDetails(getActivity());
                    updateApplicationDetails.execute();
                }
           // }
            cursor.close();
            offline_count.setVisibility(View.GONE);
            //populateList();
        }
        db1.close();
    }

    public void DBCreate_CompliteApplicationDetails_insert_2SQLiteDB(ArrayList<Class_AddApplicationDetails> str_addenquirys,ArrayList<Class_AddApplicationTwoDetails> str_addenquirystwo,ArrayList<Class_AddApplicationThreeDetails> str_addapplicationthree) {
        SQLiteDatabase db_addEnquiry = getActivity().openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);

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

        String Application_Slno,EnquiryId,FName,LName,MName,MobileNo,EmailId,BusinessName,stateId,districtId,talukId,villegeId,sectorId,deviceType,userId,Gender,Application_Date,Academic_Year;
        String Economic,whatsappNo,DOB,education, socialCatgary, businessAddress,yearInBusiness,yearInCurrentBusiness,sectorBusiness,gottoknow,UNDP,yearUNDP,navodyami,yearNavodyami,aadhar,street;

        String manufacture, licence, whichLicence, productOne, productTwo, productThree, businessYear, ownership;
        String yearOne, female_year1, male_year1, yearTwo, female_year2, male_year2, yearThree, female_year3;
        String male_year3, labour, outsidefamilyLabour, trunover_year1, trunover_year2, trunover_year3, profit_year1;
        String profit_year2, profit_year3, machine, sell_products, last_quarter,total_Employee,Which_Machine;

        String BusinessSource,LastLoan,Investment,Knowledge,AppliedAmt,SanctionedAmt,InterestRate,AppliedAt;
        String Repaymentperiod,ApplicationFees,VerifiedDate,Remark,Manual_Receipt_No,Payment_Mode,dataSyncStatus,IsAccountVerified;

        Log.e("tag","str_addenquirys.size()="+str_addenquirys.size());
        for(int s=0;s<str_addenquirys.size();s++) {
            Log.e("tag", "new BusinessName=" + str_addenquirys.get(s).getBusinessName() + "Fname=" + str_addenquirys.get(s).getFName());

            EnquiryId = str_addenquirys.get(s).getEnquiryId();
            Application_Slno = str_addenquirys.get(s).getApplication_Slno();

            FName = str_addenquirys.get(s).getFName();
            MName = str_addenquirys.get(s).getMName();
            LName = str_addenquirys.get(s).getLName();
            MobileNo = str_addenquirys.get(s).getMobileNo();
            EmailId = str_addenquirys.get(s).getEmailId();
            BusinessName = str_addenquirys.get(s).getBusinessName();
            stateId = str_addenquirys.get(s).getStateId();
            districtId = str_addenquirys.get(s).getDistrictId();
            talukId = str_addenquirys.get(s).getTalukaI();
            villegeId = str_addenquirys.get(s).getVillageId();
            sectorId = str_addenquirys.get(s).getSectorId();
            deviceType = str_addenquirys.get(s).getDeviceType();
            userId = str_addenquirys.get(s).getUserId();
            Gender = str_addenquirys.get(s).getGender();
            Economic = str_addenquirys.get(s).getEconomic();
            whatsappNo = str_addenquirys.get(s).getWhatsappNo();
            DOB = str_addenquirys.get(s).getDOB();
            education = str_addenquirys.get(s).getEducation();
            socialCatgary = str_addenquirys.get(s).getSocialCatgary();
            businessAddress = str_addenquirys.get(s).getBusinessAddress();
            yearInBusiness = str_addenquirys.get(s).getYearInBusiness();
            yearInCurrentBusiness = str_addenquirys.get(s).getYearInCurrentBusiness();
            sectorBusiness = str_addenquirys.get(s).getSectorBusiness();
            gottoknow = str_addenquirys.get(s).getGottoknow();
            UNDP = str_addenquirys.get(s).getUNDP();
            yearUNDP = str_addenquirys.get(s).getYearUNDP();
            navodyami = str_addenquirys.get(s).getNavodyami();
            yearNavodyami = str_addenquirys.get(s).getYearNavodyami();
            aadhar = str_addenquirys.get(s).getAadhar();
            street = str_addenquirys.get(s).getStreet();
            Application_Date = str_addenquirys.get(s).getApplication_Date();
            Academic_Year = str_addenquirys.get(s).getAcademic_Year();

            manufacture=str_addenquirystwo.get(s).getManufacture();
            licence=str_addenquirystwo.get(s).getLicence();
            whichLicence=str_addenquirystwo.get(s).getWhichLicence();
            productOne=str_addenquirystwo.get(s).getProductOne();
            productTwo=str_addenquirystwo.get(s).getProductTwo();
            productThree=str_addenquirystwo.get(s).getProductThree();
            businessYear=str_addenquirystwo.get(s).getBusinessYear();
            total_Employee=str_addenquirystwo.get(s).getTotal_Employee();
            ownership=str_addenquirystwo.get(s).getOwnership();
            yearOne=str_addenquirystwo.get(s).getYearOne();
            female_year1=str_addenquirystwo.get(s).getFemale_year1();
            male_year1=str_addenquirystwo.get(s).getMale_year1();
            yearTwo=str_addenquirystwo.get(s).getYearTwo();
            female_year2=str_addenquirystwo.get(s).getFemale_year2();
            male_year2=str_addenquirystwo.get(s).getMale_year2();
            yearThree = str_addenquirystwo.get(s).getYearThree();
            female_year3 = str_addenquirystwo.get(s).getFemale_year3();
            male_year3 = str_addenquirystwo.get(s).getMale_year3();
            labour = str_addenquirystwo.get(s).getLabour();
            outsidefamilyLabour = str_addenquirystwo.get(s).getOutsidefamilyLabour();
            trunover_year1 = str_addenquirystwo.get(s).getTrunover_year1();
            trunover_year2 = str_addenquirystwo.get(s).getTrunover_year2();
            trunover_year3 = str_addenquirystwo.get(s).getTrunover_year3();
            profit_year1 = str_addenquirystwo.get(s).getProfit_year1();
            profit_year2 = str_addenquirystwo.get(s).getProfit_year2();
            profit_year3 = str_addenquirystwo.get(s).getProfit_year3();
            machine = str_addenquirystwo.get(s).getMachine();
            sell_products =str_addenquirystwo.get(s).getSell_products();
            last_quarter=str_addenquirystwo.get(s).getLast_quarter();
            //   enquiryId = str_addenquirystwo.get(s).getEnquiryId();
            //   Application_Slno = str_addenquirystwo.get(s).getApplication_Slno();
            Which_Machine = str_addenquirystwo.get(s).getWhichmachine();

            BusinessSource=str_addapplicationthree.get(s).getBusinessSource();
            LastLoan=str_addapplicationthree.get(s).getLastLoan();
            Investment=str_addapplicationthree.get(s).getInvestment();
            Knowledge=str_addapplicationthree.get(s).getKnowledge();
            AppliedAmt=str_addapplicationthree.get(s).getAppliedAmt();
            SanctionedAmt=str_addapplicationthree.get(s).getSanctionedAmt();
            InterestRate=str_addapplicationthree.get(s).getInterestRate();
            AppliedAt=str_addapplicationthree.get(s).getAppliedAt();
            // EnquiryId = str_addapplicationthree.get(s).getEnquiryId();
            Repaymentperiod = str_addapplicationthree.get(s).getRepaymentperiod();
            //  Application_Slno = str_addapplicationthree.get(s).getApplication_Slno();
            ApplicationFees = str_addapplicationthree.get(s).getApplicationFees();
            VerifiedDate = str_addapplicationthree.get(s).getVerifiedDate();
            Remark = str_addapplicationthree.get(s).getRemark();
            Manual_Receipt_No=str_addapplicationthree.get(s).getManual_Receipt_No();
            Payment_Mode = str_addapplicationthree.get(s).getPayment_Mode();
            dataSyncStatus=str_addapplicationthree.get(s).getDataSyncStatus();
            IsAccountVerified=str_addapplicationthree.get(s).getIsAccountVerified();

            Cursor cursor1 = db_addEnquiry.rawQuery("SELECT DISTINCT * FROM CompliteApplicationDetails WHERE EnquiryId='"+EnquiryId+"'", null);
            int x = cursor1.getCount();
            Log.d("tag","cursor complite Application Count"+ Integer.toString(x));
            if(x==0) {
                String SQLiteQuery = "INSERT INTO CompliteApplicationDetails (EnquiryId,Application_Slno,Academic_Year,dataSyncStatus,FName, MName,LName,MobileNo,EmailId,StateId,DistrictId,TalukaId,VillageId," +
                        "SectorId,BusinessName,DeviceType,UserId,Gender,Economic,whatsappNo,DOB,education, socialCatgary, businessAddress," +
                        "yearInBusiness,yearInCurrentBusiness,sectorBusiness,gottoknow,UNDP,yearUNDP,navodyami,yearNavodyami,aadhar,street,Application_Date," +
                        "Manufacture, Licence,WhichLicence,ProductOne,ProductTwo,ProductThree,BusinessYear,Ownership,YearOne,Female_year1,Male_year1,YearTwo," +
                        "Female_year2,Male_year2,YearThree,Female_year3,Male_year3,Labour,OutsidefamilyLabour, Trunover_year1, Trunover_year2," +
                        "Trunover_year3,Profit_year1,Profit_year2,Profit_year3,Which_Machine,Machine,Sell_products,Last_quarter,Total_Employee," +
                        "BusinessSource, LastLoan,Investment,Knowledge,AppliedAmt,SanctionedAmt,InterestRate,AppliedAt,Repaymentperiod,ApplicationFees,VerifiedDate,Remark,Manual_Receipt_No,Payment_Mode,IsAccountVerified)" +
                        " VALUES ('" + EnquiryId + "','"+ Application_Slno+"','" +Academic_Year+"','" + dataSyncStatus+"','"+ FName + "','" + MName + "','" + LName + "','" + MobileNo + "','" + EmailId + "','" + stateId + "','" +
                        districtId + "','" + talukId + "','" + villegeId + "','" + sectorId + "','" + BusinessName + "','" + deviceType + "','" + userId  + "','" + Gender + "','" + Economic + "','" +
                        whatsappNo + "','" + DOB + "','" + education + "','" + socialCatgary + "','" + businessAddress + "','" + yearInBusiness + "','" + yearInCurrentBusiness + "','" +
                        sectorBusiness + "','" + gottoknow + "','" + UNDP + "','" + yearUNDP + "','" + navodyami + "','" + yearNavodyami + "','" + aadhar + "','" + street + "','" + Application_Date + "','" + manufacture + "','" + licence + "','" + whichLicence + "','" + productOne + "','" + productTwo + "','" + productThree + "','" +
                        businessYear + "','" + ownership + "','" + yearOne + "','" + female_year1 + "','" + male_year1 + "','" + yearTwo + "','" +
                        female_year2 + "','" + male_year2 + "','" + yearThree + "','" + female_year3 + "','" + male_year3 + "','" + labour + "','" + outsidefamilyLabour + "','" +
                        trunover_year1 + "','" + trunover_year2 + "','" + trunover_year3 + "','" + profit_year1 + "','" +
                        profit_year2 + "','" + profit_year3 +"','" + Which_Machine+ "','" + machine + "','" + sell_products + "','" + last_quarter + "','"+ total_Employee+"','"+
                        BusinessSource + "','" + LastLoan + "','" + Investment + "','" + Knowledge + "','" + AppliedAmt + "','" + SanctionedAmt + "','" +
                        InterestRate + "','" + AppliedAt  + "','" + Repaymentperiod +"','" + ApplicationFees+"','" + VerifiedDate+"','" + Remark+"','" + Manual_Receipt_No+"','" + Payment_Mode+"','" + IsAccountVerified+"');";
                db_addEnquiry.execSQL(SQLiteQuery);
            }else{

                String fname=FName;
                String str_talukid=talukId;

                ContentValues cv = new ContentValues();
                cv.put("EnquiryId",EnquiryId);
                cv.put("Application_Slno",Application_Slno);
                cv.put("Academic_Year",Academic_Year);
                cv.put("dataSyncStatus",dataSyncStatus);
                cv.put("FName",FName);
                cv.put("MName",MName);
                cv.put("LName",LName);
                cv.put("MobileNo",MobileNo);
                cv.put("EmailId",EmailId);
                cv.put("StateId",stateId);
                cv.put("DistrictId",districtId);
                cv.put("TalukaId",talukId);
                cv.put("VillageId",villegeId);
                cv.put("SectorId",sectorId);
                cv.put("BusinessName",BusinessName);
                cv.put("DeviceType",deviceType);
                cv.put("UserId",userId);
                cv.put("Gender",Gender);
                cv.put("Economic",Economic);
                cv.put("whatsappNo",whatsappNo);
                cv.put("DOB",DOB);
                cv.put("socialCatgary",socialCatgary);
                cv.put("businessAddress",businessAddress);
                cv.put("yearInBusiness",yearInBusiness);
                cv.put("yearInCurrentBusiness",yearInCurrentBusiness);
                cv.put("sectorBusiness",sectorBusiness);
                cv.put("gottoknow",gottoknow);
                cv.put("UNDP",UNDP);
                cv.put("yearUNDP",yearUNDP);
                cv.put("navodyami",navodyami);
                cv.put("yearNavodyami",yearNavodyami);
                cv.put("aadhar",aadhar);
                cv.put("street",street);
                cv.put("Application_Date",Application_Date);

                cv.put("Manufacture",manufacture);
                cv.put("Licence",licence);
                cv.put("WhichLicence",whichLicence);
                cv.put("ProductOne",productOne);
                cv.put("ProductTwo",productTwo);
                cv.put("ProductThree",productThree);
                cv.put("BusinessYear",businessYear);
                cv.put("Ownership",ownership);
                cv.put("YearOne",yearOne);
                cv.put("Female_year1",female_year1);
                cv.put("Male_year1",male_year1);
                cv.put("YearTwo",yearTwo);
                cv.put("Female_year2",female_year2);
                cv.put("Male_year2",male_year2);
                cv.put("YearThree",yearThree);
                cv.put("Female_year3",female_year3);
                cv.put("Male_year3",male_year3);
                cv.put("Labour",labour);
                cv.put("OutsidefamilyLabour",outsidefamilyLabour);
                cv.put("Trunover_year1",trunover_year1);
                cv.put("Trunover_year2",trunover_year2);
                cv.put("Trunover_year3",trunover_year3);
                cv.put("Profit_year1",profit_year1);
                cv.put("Profit_year2",profit_year2);
                cv.put("Profit_year3",profit_year3);
                cv.put("Which_Machine",Which_Machine);
                cv.put("Machine",machine);
                cv.put("Sell_products",sell_products);
                cv.put("Last_quarter",last_quarter);
                cv.put("Total_Employee",total_Employee);

                cv.put("BusinessSource",BusinessSource);
                cv.put("LastLoan",LastLoan);
                cv.put("Investment",Investment);
                cv.put("Knowledge",Knowledge);
                cv.put("AppliedAmt",AppliedAmt);
                cv.put("SanctionedAmt",SanctionedAmt);
                cv.put("InterestRate",InterestRate);
                cv.put("AppliedAt",AppliedAt);
                cv.put("Repaymentperiod",Repaymentperiod);
                cv.put("ApplicationFees",ApplicationFees);
                cv.put("VerifiedDate",VerifiedDate);
                cv.put("Remark",Remark);
                cv.put("Manual_Receipt_No",Manual_Receipt_No);
                cv.put("Payment_Mode",Payment_Mode);
                cv.put("IsAccountVerified",IsAccountVerified);

                Log.e("tag","IsAccountVerified update="+IsAccountVerified);
                db_addEnquiry.update("CompliteApplicationDetails", cv, "EnquiryId = ?", new String[]{EnquiryId});

            }

        }
        db_addEnquiry.close();
        List_offlineApplicationDetails();
    }
    //--------------------------Add---------
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

        public AddNewApplicationDetails(Context activity) {
            context = activity;
            dialog = new ProgressDialog(context);
        }
        @Override
        protected Void doInBackground(String... params)
        {
            Log.i("TAG", "doInBackground");
            TempId_asyncTask=(String) params[0];
            Log.e("TAG", "EnquiryId_asyncTask="+TempId_asyncTask);
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

            dialog.dismiss();

            if(status.equalsIgnoreCase("success")) {
               /* Toast toast= Toast.makeText(getContext(), "  Application Added Successfully " , Toast.LENGTH_LONG);
                // Toast toast=   Toast.makeText(YOUR ACTIVITY NAME ,Toast.LENGTH_SHORT);
                View view =toast.getView();
                view.setBackgroundColor( Color.parseColor("#009900")); //any color your want
                toast.show();*/
                getOfflineAddOREditApplication();
                /*Intent intent1 = new Intent(getContext(), BottomActivity.class);
                intent1.putExtra("frgToLoad", "0");
                startActivity(intent1);*/

               // offline_count.setVisibility(View.GONE);
               // Delete_DatafromDBTempID();
               /* AddApplicationTwoActivity addApplicationTwoActivity=new AddApplicationTwoActivity();
                addApplicationTwoActivity.clear_fields();*/
            }
            else{
              //  Toast toast= Toast.makeText(getContext(), "  Updated Failed  " , Toast.LENGTH_LONG);
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
        SQLiteDatabase db_addEnquiry = getActivity().openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);

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

  /*  public void Update_DataSynStatus_DatafromDBTempID(String tempId){
        SQLiteDatabase db_addEnquiry = getActivity().openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);

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
        Log.d("tag","cursor count compliteApplicationDetails="+ Integer.toString(x));
        if (x > 0) {
            db_addEnquiry.rawQuery(" Delete from CompliteApplicationDetails where tempId = tempId", null);

        }
    }
*/
    public void GetDataFromDB_CompliteApplicationDetails(String passEnquiry_Id,String passApplication_Slno,String passTempId){

        listYears.clear();
        listProfit.clear();
        listTurnOver.clear();
        listFemalePermentEmplyee.clear();
        listMalePermentEmplyee.clear();

       // LastLoan.clear();

        SQLiteDatabase db_addEnquiry = getActivity().openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);

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

       // Cursor cursor = db_addEnquiry.rawQuery("SELECT DISTINCT * FROM CompliteApplicationDetails WHERE EnquiryId ='"+passEnquiry_Id+"' AND Application_Slno='"+passApplication_Slno +"' AND tempId='"+passTempId+"'", null);
        Cursor cursor = db_addEnquiry.rawQuery("SELECT DISTINCT * FROM CompliteApplicationDetails WHERE tempId='"+passTempId+"'", null);
        int x = cursor.getCount();
        Log.d("tag","cursor count compliteApplicationDetails="+ Integer.toString(x));
        String EnquiryId_get = null;
        int i = 0;
       // arrayObj_Class_ApplicationDetails = new Class_AddApplicationDetails[x];
        cursor.moveToFirst();
    //   for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            FName = cursor.getString(cursor.getColumnIndex("FName"));
            MName = cursor.getString(cursor.getColumnIndex("MName"));
            LName = cursor.getString(cursor.getColumnIndex("LName"));
            MobileNo = cursor.getString(cursor.getColumnIndex("MobileNo"));
            EmailId = cursor.getString(cursor.getColumnIndex("EmailId"));
            Log.e("tag","str_StateId="+str_StateId+"str_DistrictId="+str_DistrictId);
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
                        int_Equipment="1";
                    }else{
                        int_Equipment="0";
                    }
                    if (LastLoanList.get(4).equalsIgnoreCase("1")) {
                        int_Finance_Trading="1";
                    }else {
                        int_Finance_Trading="0";
                    }

                }
            }
            Investment=cursor.getString(cursor.getColumnIndex("Investment"));
            Knowledge=cursor.getString(cursor.getColumnIndex("Knowledge"));
            AppliedAmt=cursor.getString(cursor.getColumnIndex("AppliedAmt"));
            if(AppliedAmt.equals("")||AppliedAmt==null){
                AppliedAmt="0";
            }
            SanctionedAmt=cursor.getString(cursor.getColumnIndex("SanctionedAmt"));
            if(SanctionedAmt==null||SanctionedAmt.equalsIgnoreCase("")){
                SanctionedAmt="0";
            }
            InterestRate=cursor.getString(cursor.getColumnIndex("InterestRate"));
            if(InterestRate==null||InterestRate.equalsIgnoreCase("")){
                InterestRate="0";
            }
            AppliedAt=cursor.getString(cursor.getColumnIndex("AppliedAt"));
            Repaymentperiod=cursor.getString(cursor.getColumnIndex("Repaymentperiod"));
            //    ApplicationSlnoNew=cursor.getString(cursor.getColumnIndex("Application_Slno"));
            ApplicationFees=cursor.getString(cursor.getColumnIndex("ApplicationFees"));
            if(ApplicationFees==null||ApplicationFees.equalsIgnoreCase("")){
                ApplicationFees="0";
            }
            VerifiedDate=cursor.getString(cursor.getColumnIndex("VerifiedDate"));
            Remark=cursor.getString(cursor.getColumnIndex("Remark"));
            Manual_Receipt_No=cursor.getString(cursor.getColumnIndex("Manual_Receipt_No"));
            Payment_Mode=cursor.getString(cursor.getColumnIndex("Payment_Mode"));

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


    ////////////////

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
           /* dialog.setMessage("Please wait..");
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

      //      dialog.dismiss();

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
                AddApplicationTwoDetails addApplicationTwoDetails = new AddApplicationTwoDetails(getActivity());
                addApplicationTwoDetails.execute();
              /*  View view =toast.getView();
                view.setBackgroundColor( Color.parseColor("#009900")); //any color your want
                toast.show();*/
            }
            else{
                //  Toast.makeText(AddApplicationOneActivity.this, "Update Request Failed " , Toast.LENGTH_LONG).show();
              //  Toast toast= Toast.makeText(getContext(), "  Update Request Failed  " , Toast.LENGTH_LONG);
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
        SQLiteDatabase db_addEnquiry = getActivity().openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);

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
   /* public void Update_ApplicationNo_AddApplicationTwoDetails(String Application_SlnoNew) {
        SQLiteDatabase db_addApplicationtwo = getActivity().openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);

        db_addApplicationtwo.execSQL("CREATE TABLE IF NOT EXISTS AddApplicationDetailsTwo(Manufacture VARCHAR,Licence VARCHAR,WhichLicence VARCHAR,ProductOne VARCHAR," +
                "ProductTwo VARCHAR,ProductThree VARCHAR,BusinessYear VARCHAR,Ownership VARCHAR,YearOne VARCHAR,Female_year1 VARCHAR,Male_year1 VARCHAR," +
                "YearTwo VARCHAR,Female_year2 VARCHAR,Male_year2 VARCHAR,YearThree VARCHAR,Female_year3 VARCHAR,Male_year3 VARCHAR,Labour VARCHAR,OutsidefamilyLabour VARCHAR, " +
                "Trunover_year1 VARCHAR,Trunover_year2 VARCHAR,Trunover_year3 VARCHAR,Profit_year1 VARCHAR,Profit_year2 VARCHAR,Profit_year3 VARCHAR,Which_Machine VARCHAR,Machine VARCHAR,Sell_products VARCHAR,Last_quarter VARCHAR,EnquiryId VARCHAR,Total_Employee VARCHAR,Application_Slno VARCHAR);");

        ContentValues cv = new ContentValues();

        cv.put("Application_Slno",Application_SlnoNew);

        db_addApplicationtwo.update("AddApplicationDetailsTwo", cv, "EnquiryId = ?", new String[]{personalApplicationSlno});

    }

    public void Update_ApplicationNo_AddApplicationThreeDetails(String Application_SlnoNew) {
        SQLiteDatabase db_addApplicationthree = getActivity().openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);

        db_addApplicationthree.execSQL("CREATE TABLE IF NOT EXISTS AddApplicationDetailsThree(BusinessSource VARCHAR,LastLoan VARCHAR,Investment VARCHAR,Knowledge VARCHAR," +
                "AppliedAmt VARCHAR,SanctionedAmt VARCHAR,InterestRate VARCHAR,AppliedAt VARCHAR,EnquiryId VARCHAR,Repaymentperiod VARCHAR,Application_Slno VARCHAR,ApplicationFees VARCHAR,VerifiedDate VARCHAR,Remark VARCHAR,Manual_Receipt_No VARCHAR,Payment_Mode VARCHAR);");

        ContentValues cv = new ContentValues();

        cv.put("Application_Slno",Application_SlnoNew);

        db_addApplicationthree.update("AddApplicationDetailsThree", cv, "EnquiryId = ?", new String[]{personalApplicationSlno});

    }*/

    private class AddApplicationTwoDetails extends AsyncTask<String, Void, Void>
    {
      //  ProgressDialog dialog;

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
        //    dialog = new ProgressDialog(context);
        }
        @Override
        protected Void doInBackground(String... params)
        {
            Log.i("TAG", "doInBackground");
         //   SubmitBusiness();
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

        //    dialog.dismiss();


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
                AddApplicationThreeDetails addApplicationThreeDetails=new AddApplicationThreeDetails(getActivity());
                addApplicationThreeDetails.execute();
            }
            else{
               // Toast toast= Toast.makeText(getActivity(), "  Updated Failed  " , Toast.LENGTH_LONG);
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
            dialog.show();*/

        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            Log.i("TAG", "onProgressUpdate---tab2");
        }

        public AddApplicationThreeDetails(Context activity) {
            context = activity;
           // dialog = new ProgressDialog(context);
        }
        @Override
        protected Void doInBackground(String... params)
        {
            Log.i("TAG", "doInBackground");
         //   SubmitCredit();
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

          //  dialog.dismiss();

            if(status.equalsIgnoreCase("success")) {
              /*  Toast toast= Toast.makeText(getContext(), "  Application Updated Successfully " , Toast.LENGTH_LONG);
                // Toast toast=   Toast.makeText(YOUR ACTIVITY NAME ,Toast.LENGTH_SHORT);
                View view =toast.getView();
                view.setBackgroundColor( Color.parseColor("#009900")); //any color your want
                toast.show();*/
                getOfflineAddOREditApplication();
            //    offline_count.setVisibility(View.GONE);
              /*  Intent intent1 = new Intent(getContext(), BottomActivity.class);
                intent1.putExtra("frgToLoad", "0");
                startActivity(intent1);*/
                /*AddApplicationTwoActivity addApplicationTwoActivity=new AddApplicationTwoActivity();
                addApplicationTwoActivity.clear_fields();*/
            }
            else{
               // Toast toast= Toast.makeText(getContext(), "  Updated Failed  " , Toast.LENGTH_LONG);
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
}//end of fragment class
