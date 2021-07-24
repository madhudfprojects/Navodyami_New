package df.navodyami;

import android.app.AlertDialog;
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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddApplicationOneActivity extends AppCompatActivity {

    private EditText edt_BusinessName,edt_EmailId,edt_MobileNo,edt_LastName,edt_MiddleName,edt_FirstName,edt_whatsappMobileNo,edt_BusinessAddress,edt_yearInBusiness,edt_yearIncurrentBusiness,edt_sectorBusiness,edt_gottoknow,edt_navodyami,edt_undp,edt_Aadhar,edt_Street;
    Button btn_update;
    String FName,LName,MName,MobileNo,EmailId,BusinessName,str_gender,str_economic="APL",str_undp,str_navo,navo_years,undp_years,str_dob,str_appldate;
    String str_Street,str_Aadhar,str_whatsapp,str_businessAddrs,str_yearInBusiness,str_yearInCurrentbusiness,str_sectorBusiness,str_gottoknow,str_yearNavo,str_yearUNDP,str_catgary,str_education;
    String int_navo="0",int_UNDP="0",int_Education;
    ImageView Next_iv;

    Spinner yearlist_SP, statelist_SP, districtlist_SP, taluklist_SP, villagelist_SP,spin_Sector;
    LinearLayout spinnerlayout_ll;
    ImageButton search_ib, downarrow_ib, uparrow_ib;
    TextView viewspinner_tv;

    Class_SectorListDetails[] arrayObj_Class_sectorDetails2;
    Class_SectorListDetails Obj_ClasssectorListDetails;
    Class_QualificationListDetails[] arrayObj_Class_QualificationDetails2;
    Class_QualificationListDetails Obj_ClassQualificationListDetails;
    Class_CatgaryListDetails[] arrayObj_Class_CatgaryDetails2;
    Class_CatgaryListDetails Obj_ClassCatgaryListDetails;
    Class_KnowNavodyamiListDetails[] arrayObj_Class_NavodyamiDetails2;
    Class_KnowNavodyamiListDetails Obj_ClassNavodyamiListDetails;

    Class_YearListDetails[] arrayObj_Class_yearDetails2;
    Class_YearListDetails Obj_Class_yearDetails;
    Class_StateListDetails[] arrayObj_Class_stateDetails2;
    Class_StateListDetails Obj_Class_stateDetails;
    Class_DistrictListDetails[] arrayObj_Class_DistrictListDetails2;
    Class_DistrictListDetails Obj_Class_DistrictDetails;
    Class_TalukListDetails[] arrayObj_Class_TalukListDetails2;
    Class_TalukListDetails Obj_Class_TalukDetails;
    Class_VillageListDetails[] arrayObj_Class_VillageListDetails2;
    Class_VillageListDetails Obj_Class_VillageListDetails;

    String selected_year, sp_stryear_ID, sp_strstate_ID, selected_district, selected_stateName, sp_strdistrict_ID, sp_strdistrict_state_ID, sp_strTaluk_ID, selected_taluk, sp_strVillage_ID, selected_village, sp_strgrampanchayat_ID, selected_grampanchayat, sp_strsector_ID, selected_sector;
    int sel_sector=0,sel_yearsp=0,sel_statesp=0,sel_districtsp=0,sel_taluksp=0,sel_villagesp=0,sel_grampanchayatsp=0,sel_qulification=0,sel_social_Category=0,sel_gottoknow=0;

    String str_StateId,str_DistrictId,str_TalukaId,str_VillageId,str_SectorId;
    String status,Application_SlnoNew="0";
    String EnquiryId,isApplicant,ApplicationSlno,tempId,EnquiryTemptId;
    Boolean isInternetPresent = false;
    Class_InternetDectector internetDectector;

    SharedPreferences sharedpreferencebook_usercredential_Obj;
    SharedPreferences.Editor editor_obj;
    String str_UserId;
    public static final String sharedpreferencebook_usercredential = "sharedpreferencebook_usercredential";
    public static final String KeyValue_employeeid = "KeyValue_employeeid";

    RadioGroup gender_radiogroup,economic_radiogroup,navodyami_radiogroup,undp_radiogroup;
    private ProgressDialog progressDialog;

    EditText edt_dob,dobseterror_tv,edt_appldate,appldate_seterror_TV;
    private int mYear, mMonth, mDay;
    private int cYear, cMonth, cDay;

    ArrayAdapter dataAdapter_education,dataAdapter_catgary,dataAdapter_gotknow;
    Spinner Catgary_SP,sp_Education,gotknow_sp;

    ArrayList<Class_AddApplicationDetails> addApplicationList;
    ArrayList<Class_AddApplicationTwoDetails> addApplicationTwoList;
    ArrayList<Class_AddApplicationThreeDetails> addApplicationThreeList;

    Class_AddApplicationDetails[] arrayObj_Class_ApplicationDetails;
    String TempId,dataSyncStatus;
    ArrayList<ApplicationListModel> addApplicationListModule;

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

    final ArrayList listKnow = new ArrayList();

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
    String Manual_Receipt_No,Payment_Mode,IsAccountVerified;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_one_application);

        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_n_actionbar);
        setSupportActionBar(toolbar);
        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView title= (TextView) toolbar.findViewById(R.id.title_name);
        title.setText("Application Form");
        getSupportActionBar().setTitle("");

        yearlist_SP = (Spinner) findViewById(R.id.yearlist_farmer_SP);
        statelist_SP = (Spinner) findViewById(R.id.statelist_farmer_SP);

        districtlist_SP = (Spinner) findViewById(R.id.districtlist_farmer_SP);
        taluklist_SP = (Spinner) findViewById(R.id.taluklist_farmer_SP);
        villagelist_SP = (Spinner) findViewById(R.id.villagelist_farmer_SP);
        //  grampanchayatlist_SP = (Spinner) findViewById(R.id.grampanchayatlist_farmer_SP);
        spinnerlayout_ll = (LinearLayout) findViewById(R.id.spinnerlayout_LL);
        viewspinner_tv = (TextView) findViewById(R.id.viewspinner_TV);

        downarrow_ib = (ImageButton) findViewById(R.id.downarrow_IB);
        uparrow_ib = (ImageButton) findViewById(R.id.uparrow_IB);

        edt_FirstName = (EditText) findViewById(R.id.edt_FirstName);
        edt_LastName = (EditText) findViewById(R.id.edt_LastName);
        edt_MiddleName = (EditText) findViewById(R.id.edt_MiddleName);
        edt_BusinessName = (EditText) findViewById(R.id.edt_BusinessName);
        edt_EmailId = (EditText) findViewById(R.id.edt_EmailId);
        edt_MobileNo = (EditText) findViewById(R.id.edt_MobileNo);
        spin_Sector = (Spinner) findViewById(R.id.Sector_sp);
        btn_update = (Button) findViewById(R.id.btn_update);
        gender_radiogroup =(RadioGroup)findViewById(R.id.gender_radiogroup);
        economic_radiogroup = (RadioGroup)findViewById(R.id.economic_radiogroup);
        undp_radiogroup = (RadioGroup)findViewById(R.id.undp_radiogroup);
        navodyami_radiogroup = (RadioGroup) findViewById(R.id.navodyami_radiogroup);

        Next_iv = (ImageView) findViewById(R.id.Next_iv);
        edt_dob = (EditText) findViewById(R.id.edt_dob);
        dobseterror_tv =(EditText)findViewById(R.id.dobseterror_TV);

        edt_appldate = (EditText) findViewById(R.id.edt_appldate);
        appldate_seterror_TV =(EditText)findViewById(R.id.appldate_seterror_TV);

        sp_Education = (Spinner) findViewById(R.id.Education_SP);
        Catgary_SP = (Spinner) findViewById(R.id.Catgary_SP);
        gotknow_sp = (Spinner) findViewById(R.id.gotknow_sp);

        edt_BusinessAddress = (EditText) findViewById(R.id.edt_BusinessAddress);
        edt_yearInBusiness = (EditText) findViewById(R.id.edt_yearInBusiness);
        edt_sectorBusiness = (EditText) findViewById(R.id.edt_sectorBusiness);
        edt_yearIncurrentBusiness = (EditText) findViewById(R.id.edt_yearIncurrentBusiness);
        /*  edt_gottoknow = (EditText) findViewById(R.id.edt_gottoknow);*/
        edt_undp = (EditText) findViewById(R.id.edt_undp);
        edt_navodyami=(EditText) findViewById(R.id.edt_navodyami);
        edt_whatsappMobileNo=(EditText) findViewById(R.id.edt_whatsappMobileNo);
        edt_Aadhar=(EditText)findViewById(R.id.edt_Aadhar);
        edt_Street=(EditText)findViewById(R.id.edt_Street);

        addApplicationListModule =new ArrayList<>();
        addApplicationList = new ArrayList<>();
        addApplicationTwoList = new ArrayList<>();
        addApplicationThreeList = new ArrayList<>();

        sharedpreferencebook_usercredential_Obj=getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_UserId = sharedpreferencebook_usercredential_Obj.getString(KeyValue_employeeid, "").trim();

        setDateOfBirth();
        setApplicationDate();
       // setEducationSpinner();
        uploadfromDB_QualificationList();
        uploadfromDB_CatgaryList();
        uploadfromDB_KnowNavodyamiList();
       // setCatgarySpinner();
        setKnowSpinner();

        final Intent intent = getIntent();
        if(getIntent().getExtras()!=null) {
            isApplicant = intent.getStringExtra("isApplicant");
            if (isApplicant.equalsIgnoreCase("1")) {
                ApplicationSlno = intent.getStringExtra("ApplicationSlno");
                Application_SlnoNew=ApplicationSlno;
                tempId = intent.getStringExtra("tempId");
                if(isInternetPresent) {
                    // GetApplicationDetails getApplicationDetails = new GetApplicationDetails(AddApplicationOneActivity.this);
                    //  getApplicationDetails.execute();
                    //UploadFromDB_AddApplicationDetails();
                    UploadFromDB_AddCompliteApplicationOneDetails();
                }else{
                    //UploadFromDB_AddApplicationDetails();
                    UploadFromDB_AddCompliteApplicationOneDetails();
                }
            } else if (isApplicant.equalsIgnoreCase("0")) {
                EnquiryId = intent.getStringExtra("EnquiryId");
                EnquiryTemptId = intent.getStringExtra("EnquiryTemptId");
                ApplicationSlno = "0";
                if(isInternetPresent) {
                    GetEnquiryDetails getEnquiryDetails = new GetEnquiryDetails(AddApplicationOneActivity.this);
                    getEnquiryDetails.execute();
                    if(EnquiryId.equalsIgnoreCase("0")){

                        uploadfromDB_Statelist();
                        uploadfromDB_Sectorlist();
                        //   uploadfromDB_QualificationList();
                    }
                }else {
                    uploadfromDB_Statelist();
                    uploadfromDB_Sectorlist();
                    //  uploadfromDB_QualificationList();
                    List_offlineEnquiryDetails();
                }
            }
        }
        uparrow_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewspinner_tv.setText("Hide");
                spinnerlayout_ll.setVisibility(View.VISIBLE);
                downarrow_ib.setVisibility(View.VISIBLE);
                uparrow_ib.setVisibility(View.GONE);

            }
        });

        downarrow_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewspinner_tv.setText("Show");
                spinnerlayout_ll.setVisibility(View.GONE);
                downarrow_ib.setVisibility(View.GONE);
                uparrow_ib.setVisibility(View.VISIBLE);

            }
        });



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
        }

        sp_Education.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

             //   str_education = String.valueOf(sp_Education.getSelectedItemPosition());
                Obj_ClassQualificationListDetails = (Class_QualificationListDetails) sp_Education.getSelectedItem();
                str_education = Obj_ClassQualificationListDetails.getQualificationId().toString();
              //  selected_sector = Obj_ClasssectorListDetails.getSectorName().toString();
               // str_SectorId = Obj_ClasssectorListDetails.getSectorId().toString();
                int sel_qualifsp_new = spin_Sector.getSelectedItemPosition();
                if(sel_qualifsp_new!=sel_qulification) {
                    sel_qulification=sel_qualifsp_new;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Catgary_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

             //   str_catgary = String.valueOf(Catgary_SP.getSelectedItemPosition());
                Obj_ClassCatgaryListDetails = (Class_CatgaryListDetails) Catgary_SP.getSelectedItem();
                str_catgary = Obj_ClassCatgaryListDetails.getCatgaryId().toString();
                //  selected_sector = Obj_ClasssectorListDetails.getSectorName().toString();
                // str_SectorId = Obj_ClasssectorListDetails.getSectorId().toString();
                int sel_catgarysp_new = Catgary_SP.getSelectedItemPosition();
                if(sel_catgarysp_new!=sel_social_Category) {
                    sel_social_Category=sel_catgarysp_new;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        gotknow_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // str_gottoknow = String.valueOf(gotknow_sp.getSelectedItemPosition());
                str_gottoknow = gotknow_sp.getSelectedItem().toString();

               /* Obj_ClassNavodyamiListDetails= (Class_KnowNavodyamiListDetails) gotknow_sp.getSelectedItem();
                str_gottoknow = Obj_ClassNavodyamiListDetails.getName().toString();
                 sel_gottoknow = Integer.valueOf(Obj_ClassNavodyamiListDetails.getId());
                // str_SectorId = Obj_ClasssectorListDetails.getSectorId().toString();
                int sel_gottoknow_new = gotknow_sp.getSelectedItemPosition();
                if(sel_gottoknow_new!=sel_gottoknow) {
                    sel_gottoknow=sel_gottoknow_new;
                }*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        statelist_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                Obj_Class_stateDetails = (Class_StateListDetails) statelist_SP.getSelectedItem();
                sp_strstate_ID = Obj_Class_stateDetails.getState_id().toString();
                str_StateId = Obj_Class_stateDetails.getState_id().toString();
                selected_stateName = statelist_SP.getSelectedItem().toString();
                int sel_statesp_new = statelist_SP.getSelectedItemPosition();

                Update_districtid_spinner(sp_strstate_ID);
                if(sel_statesp_new!=sel_statesp) {
                    sel_statesp=sel_statesp_new;

                    districtlist_SP.setSelection(0);
                    taluklist_SP.setSelection(0);
                    villagelist_SP.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        districtlist_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_DistrictDetails = (Class_DistrictListDetails) districtlist_SP.getSelectedItem();
                sp_strdistrict_ID = Obj_Class_DistrictDetails.getDistrict_id();
                str_DistrictId = Obj_Class_DistrictDetails.getDistrict_id();
                sp_strdistrict_state_ID = Obj_Class_DistrictDetails.getState_id();
                selected_district = districtlist_SP.getSelectedItem().toString();
                int sel_districtsp_new = districtlist_SP.getSelectedItemPosition();

                Update_TalukId_spinner(sp_strdistrict_ID);

                if(sel_districtsp_new!=sel_districtsp) {
                    sel_districtsp=sel_districtsp_new;
                    taluklist_SP.setSelection(0);
                    villagelist_SP.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        taluklist_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_TalukDetails = (Class_TalukListDetails) taluklist_SP.getSelectedItem();
                sp_strTaluk_ID = Obj_Class_TalukDetails.getTaluk_id();
                str_TalukaId = Obj_Class_TalukDetails.getTaluk_id();
                selected_taluk = taluklist_SP.getSelectedItem().toString();
                int sel_taluksp_new = taluklist_SP.getSelectedItemPosition();

                Update_VillageId_spinner(sp_strTaluk_ID);

                if(sel_taluksp_new!=sel_taluksp) {
                    sel_taluksp=sel_taluksp_new;
                    /*ViewFarmerList_arraylist.clear();
                    farmerListViewAdapter.notifyDataSetChanged();
*/
                    villagelist_SP.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        villagelist_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_VillageListDetails = (Class_VillageListDetails) villagelist_SP.getSelectedItem();
                sp_strVillage_ID = Obj_Class_VillageListDetails.getVillage_id();
                str_VillageId = Obj_Class_VillageListDetails.getVillage_id();
                selected_village = villagelist_SP.getSelectedItem().toString();

                int sel_villagesp_new = villagelist_SP.getSelectedItemPosition();

                if(sel_villagesp_new!=sel_villagesp) {
                    sel_villagesp=sel_villagesp_new;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spin_Sector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_ClasssectorListDetails = (Class_SectorListDetails) spin_Sector.getSelectedItem();
                sp_strsector_ID = Obj_ClasssectorListDetails.getSectorId().toString();
                selected_sector = Obj_ClasssectorListDetails.getSectorName().toString();
                str_SectorId = Obj_ClasssectorListDetails.getSectorId().toString();
                int sel_sectorsp_new = spin_Sector.getSelectedItemPosition();
                if(sel_sectorsp_new!=sel_sector) {
                    sel_sector=sel_sectorsp_new;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       /* btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FName = edt_FirstName.getText().toString();
                MName = edt_MiddleName.getText().toString();
                LName = edt_LastName.getText().toString();
                MobileNo = edt_MobileNo.getText().toString();
                EmailId = edt_EmailId.getText().toString();
                BusinessName = edt_BusinessName.getText().toString();

                if(validation()) {
                    if (isInternetPresent) {
                        UpdateEnquiryDetails updateEnquiryDetails = new UpdateEnquiryDetails(AddApplicationOneActivity.this);
                        updateEnquiryDetails.execute(FName, MName, LName, MobileNo, EmailId, BusinessName, sp_strstate_ID, sp_strdistrict_ID, sp_strTaluk_ID, sp_strVillage_ID, sp_strsector_ID, str_UserId,str_gender);
                    }
                }
            }
        });*/

        Next_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FName = edt_FirstName.getText().toString();
                MName = edt_MiddleName.getText().toString();
                LName = edt_LastName.getText().toString();
                MobileNo = edt_MobileNo.getText().toString();
                EmailId = edt_EmailId.getText().toString();
                BusinessName = edt_BusinessName.getText().toString();

                str_whatsapp = edt_whatsappMobileNo.getText().toString();
                str_businessAddrs = edt_BusinessAddress.getText().toString();
                str_yearInBusiness = edt_yearInBusiness.getText().toString();
                str_yearInCurrentbusiness = edt_yearIncurrentBusiness.getText().toString();
                str_yearNavo = edt_navodyami.getText().toString();
                str_yearUNDP = edt_undp.getText().toString();
                str_Aadhar=edt_Aadhar.getText().toString();
                str_Street=edt_Street.getText().toString();
                // str_gottoknow=edt_gottoknow.getText().toString();
                str_sectorBusiness = edt_sectorBusiness.getText().toString();

                Log.e("tag","sp_strdistrict_ID="+sp_strdistrict_ID+"sp_strTaluk_ID="+sp_strTaluk_ID);
                if(edt_navodyami.getText().length() == 0){
                    str_yearNavo="0";
                }
                if(edt_undp.getText().length() == 0){
                    str_yearUNDP="0";
                }
                if (edt_yearInBusiness.getText().length() == 0){
                    str_yearInBusiness="0";
                }
                if (edt_yearIncurrentBusiness.getText().length()==0){
                    str_yearInCurrentbusiness="0";
                }
                addApplicationList.clear();
                Log.e("tag","Application_SlnoNew class=="+Application_SlnoNew);
                Log.e("tag","ApplicationOne EnquiryId class=="+EnquiryId);

                if(validation()) {
                    internetDectector = new Class_InternetDectector(getApplicationContext());
                    isInternetPresent = internetDectector.isConnectingToInternet();
                    if (isInternetPresent) {
                        if(EnquiryId.equalsIgnoreCase("0")&& Application_SlnoNew.equalsIgnoreCase("0")){
                            //  DBCreate_AddApplicationDetails_insert_2SQLiteDB(addApplicationList);
                            // DBCreate_PersonalListApplicationDetails_2SQLiteDB(addApplicationList);
                            addApplicationThreeList.clear();
                            addApplicationTwoList.clear();
                            Class_AddApplicationDetails class_addApplicationDetails = new Class_AddApplicationDetails(FName, MName, LName, MobileNo, EmailId, str_StateId, str_DistrictId, str_TalukaId, str_VillageId, str_SectorId, BusinessName, "MOB", str_UserId, EnquiryId,str_gender,str_economic,str_whatsapp,str_dob,str_education,str_catgary,str_businessAddrs,str_yearInBusiness,str_yearInCurrentbusiness,str_sectorBusiness,str_gottoknow,int_UNDP,str_yearUNDP,int_navo,str_yearNavo,str_Aadhar,str_Street,str_appldate,"0","0");
                            addApplicationList.add(class_addApplicationDetails);

                            Class_AddApplicationTwoDetails class_addApplicationTwoDetails = new Class_AddApplicationTwoDetails("0", "0", "", "",
                                    "", "", "", "", "", "", "", "", "", "",
                                    "", "", "", "0", "0", "", "", "", "",
                                    "", "", "", "0", "", "", EnquiryId, "", Application_SlnoNew);
                            addApplicationTwoList.add(class_addApplicationTwoDetails);
                            Class_AddApplicationThreeDetails class_addApplicationThreeDetails = new Class_AddApplicationThreeDetails("", "0,0,0,0,0", "", "0", "", "", "", "", EnquiryId, "", "0", "0", "0", "0", "0", Application_SlnoNew, "0", "", "", "", "","online",IsAccountVerified);
                            addApplicationThreeList.add(class_addApplicationThreeDetails);
                            DBCreate_ListApplicationDetails_2SQLiteDB(addApplicationList,addApplicationTwoList,addApplicationThreeList);
                            get_TempIdData();
                            /*ApplicationListModel item = new ApplicationListModel("00",FName,MobileNo, BusinessName,"Success","0","0","0","online");
                            addApplicationListModule.add(item);
                            DBCreate_ListApplicationDetails_2SQLiteDB(addApplicationListModule);*/
                            Intent intent1 = new Intent(AddApplicationOneActivity.this, AddApplicationTwoActivity.class);
                            intent1.putExtra("EnquiryId",EnquiryId);
                            intent1.putExtra("Application_SlnoNew",Application_SlnoNew);
                            intent1.putExtra("isApplicant",isApplicant);
                            intent1.putExtra("tempId",tempId);
                            startActivity(intent1);
                        }else {
                           /* ApplicationListModel item = new ApplicationListModel(ApplicationSlno,FName,MobileNo, BusinessName,"Success","0","0",EnquiryId,"online");
                            addApplicationListModule.add(item);
                            DBCreate_ListApplicationDetails_2SQLiteDB(addApplicationListModule);*/
                            get_TempIdData();
                            UpdateApplicationDetails updateApplicationDetails = new UpdateApplicationDetails(AddApplicationOneActivity.this);
                            updateApplicationDetails.execute(FName, MName, LName, MobileNo, EmailId, BusinessName, str_StateId, str_DistrictId, str_TalukaId, str_VillageId, str_SectorId, str_UserId, str_gender);
                            //   DBCreate_AddApplicationDetails_insert_2SQLiteDB(addApplicationList);
                            //  DBCreate_ListApplicationDetails_2SQLiteDB(addApplicationList,addApplicationTwoList,addApplicationThreeList);
                        }
                    }else{
                        if(EnquiryId.equalsIgnoreCase("0")&& ApplicationSlno.equalsIgnoreCase("0")) {
                           /* ApplicationListModel item = new ApplicationListModel("00", FName, MobileNo, BusinessName, "Success", "0", "0", EnquiryId, "offline");
                            addApplicationListModule.add(item);*/

                            Class_AddApplicationDetails class_addApplicationDetails2 = new Class_AddApplicationDetails(FName, MName, LName, MobileNo, EmailId, str_StateId, str_DistrictId, str_TalukaId, str_VillageId, str_SectorId, BusinessName, "MOB", str_UserId, EnquiryId,str_gender,str_economic,str_whatsapp,str_dob,str_education,str_catgary,str_businessAddrs,str_yearInBusiness,str_yearInCurrentbusiness,str_sectorBusiness,str_gottoknow,int_UNDP,str_yearUNDP,int_navo,str_yearNavo,str_Aadhar,str_Street,str_appldate,"0","0");
                            addApplicationList.add(class_addApplicationDetails2);
                            Class_AddApplicationTwoDetails class_addApplicationTwoDetails = new Class_AddApplicationTwoDetails("0", "0", "", "",
                                    "", "", "", "", "", "", "", "", "", "",
                                    "", "", "", "0", "0", "", "", "", "",
                                    "", "", "", "0", "", "", EnquiryId, "", Application_SlnoNew);
                            addApplicationTwoList.add(class_addApplicationTwoDetails);
                            Class_AddApplicationThreeDetails class_addApplicationThreeDetails = new Class_AddApplicationThreeDetails("", "0,0,0,0,0", "", "0", "", "", "", "", EnquiryId, "", "0", "0", "0", "0", "0", Application_SlnoNew, "0", "", "", "", "","offline",IsAccountVerified);
                            addApplicationThreeList.add(class_addApplicationThreeDetails);
                            DBCreate_ListApplicationDetails_2SQLiteDB(addApplicationList,addApplicationTwoList,addApplicationThreeList);
                            RemoveFromEnquiryListDB();
                            if(tempId==null||tempId.equalsIgnoreCase("")) {
                                get_TempIdData();
                            }
                        }else{
                            /*ApplicationListModel item = new ApplicationListModel(ApplicationSlno, FName, MobileNo, BusinessName, "Success", "0", "0", EnquiryId, "offline");
                            addApplicationListModule.add(item);*/
                            Class_AddApplicationDetails class_addApplicationDetails3 = new Class_AddApplicationDetails(FName, MName, LName, MobileNo, EmailId, str_StateId, str_DistrictId, str_TalukaId, str_VillageId, str_SectorId, BusinessName, "MOB", str_UserId, EnquiryId,str_gender,str_economic,str_whatsapp,str_dob,str_education,str_catgary,str_businessAddrs,str_yearInBusiness,str_yearInCurrentbusiness,str_sectorBusiness,str_gottoknow,int_UNDP,str_yearUNDP,int_navo,str_yearNavo,str_Aadhar,str_Street,str_appldate,ApplicationSlno,"0");
                            addApplicationList.add(class_addApplicationDetails3);
                            DBUpdate_ApplicationOneDetails_2SQLiteDB(addApplicationList);
                            get_TempIdData();
                            GetDataFromDB_CompliteApplicationDetails(EnquiryId,tempId);
                            RemoveFromEnquiryListDB();
                        }

                        //  DBCreate_AddApplicationDetails_insert_2SQLiteDB(addApplicationList);
                        // get_TempIdData();
                        Intent intent1 = new Intent(AddApplicationOneActivity.this, AddApplicationTwoActivity.class);
                        intent1.putExtra("EnquiryId",EnquiryId);
                        intent1.putExtra("Application_SlnoNew",ApplicationSlno);
                        intent1.putExtra("isApplicant",isApplicant);
                        intent1.putExtra("tempId",tempId);
                        startActivity(intent1);
                    }
                }
            }
        });

    }

    public void onRadioButtonGenderClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.rdb_male:
                if (checked) {
                    str_gender="M";
                }
                break;
            case R.id.rdb_female:
                if (checked) {
                    str_gender="F";
                }
                break;
        }
    }

    public void onRadioButtonEconomicClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.rdb_apl:
                if (checked) {
                    str_economic="APL";
                }
                break;
            case R.id.rdb_bpl:
                if (checked) {
                    str_economic="BPL";
                }
                break;
        }
    }

    public void onRadioButtonUNDPClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.rdb_UNDPyes:
                if (checked) {
                    str_undp="YES";
                    edt_undp.setVisibility(View.VISIBLE);
                    undp_years=edt_undp.getText().toString();
                    int_UNDP="1";
                }
                break;
            case R.id.rdb_UNDPno:
                if (checked) {
                    str_undp="NO";
                    edt_undp.setVisibility(View.GONE);
                    undp_years="0";
                    int_UNDP="0";
                }
                break;
        }
    }

    public void onRadioButtonNavodyamiClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.rdb_navoyes:
                if (checked) {
                    str_navo="YES";
                    edt_navodyami.setVisibility(View.VISIBLE);
                    navo_years=edt_navodyami.getText().toString();
                    int_navo="1";
                }
                break;
            case R.id.rdb_navono:
                if (checked) {
                    str_navo="NO";
                    edt_navodyami.setVisibility(View.GONE);
                    navo_years="0";
                    int_navo="0";
                }
                break;
        }
    }

    public void Update_districtid_spinner(String str_stateid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS DistrictList(DistrictID VARCHAR,DistrictName VARCHAR,Distr_Stateid VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM DistrictList WHERE Distr_Stateid='" + str_stateid + "'", null);
        //Cursor cursor1 = db1.rawQuery("select * from DistrictList D, TalukList T where D.DistrictID=T.Taluk_districtid AND D.Distr_Stateid='" + str_stateid + "'",null);

        int x = cursor1.getCount();
        Log.d("cursor Dcount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_DistrictListDetails2 = new Class_DistrictListDetails[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_DistrictListDetails innerObj_Class_AcademicList = new Class_DistrictListDetails();
                innerObj_Class_AcademicList.setDistrict_id(cursor1.getString(cursor1.getColumnIndex("DistrictID")));
                innerObj_Class_AcademicList.setDistrict_name(cursor1.getString(cursor1.getColumnIndex("DistrictName")));
                //innerObj_Class_AcademicList.setYear_id(cursor1.getString(cursor1.getColumnIndex("Distr_yearid")));
                innerObj_Class_AcademicList.setState_id(cursor1.getString(cursor1.getColumnIndex("Distr_Stateid")));


                arrayObj_Class_DistrictListDetails2[i] = innerObj_Class_AcademicList;
                i++;
            } while (cursor1.moveToNext());
        }//if ends


        db1.close();
        for(int i1=0; i1 < arrayObj_Class_DistrictListDetails2.length; i1++) {
            if (arrayObj_Class_DistrictListDetails2[i1].district_id.equalsIgnoreCase(str_DistrictId))
                sel_districtsp = i1;
        }
        if (x > 0) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_DistrictListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercustomstyle);
            districtlist_SP.setAdapter(dataAdapter);
            if(x>sel_districtsp) {
                districtlist_SP.setSelection(sel_districtsp);
            }
        }

    }
    public void Update_TalukId_spinner(String str_distid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS TalukList(TalukID VARCHAR,TalukName VARCHAR,Taluk_districtid VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM TalukList WHERE Taluk_districtid='" + str_distid + "'", null);
        int x = cursor1.getCount();
        Log.d("cursor Tcount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_TalukListDetails2 = new Class_TalukListDetails[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_TalukListDetails innerObj_Class_talukList = new Class_TalukListDetails();
                innerObj_Class_talukList.setTaluk_id(cursor1.getString(cursor1.getColumnIndex("TalukID")));
                innerObj_Class_talukList.setTaluk_name(cursor1.getString(cursor1.getColumnIndex("TalukName")));
                innerObj_Class_talukList.setDistrict_id(cursor1.getString(cursor1.getColumnIndex("Taluk_districtid")));


                arrayObj_Class_TalukListDetails2[i] = innerObj_Class_talukList;
                //Log.e("taluk_name",cursor1.getString(cursor1.getColumnIndex("TalukName")));
                i++;
            } while (cursor1.moveToNext());
        }//if ends


        db1.close();
        for(int i1=0; i1 < arrayObj_Class_TalukListDetails2.length; i1++) {
            if (arrayObj_Class_TalukListDetails2[i1].taluk_id.equalsIgnoreCase(str_TalukaId))
                sel_taluksp = i1;
        }
        if (x > 0) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_TalukListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercustomstyle);
            taluklist_SP.setAdapter(dataAdapter);
            if(x>sel_taluksp) {
                taluklist_SP.setSelection(sel_taluksp);
            }
        }

    }
    public void Update_VillageId_spinner(String str_talukid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS VillageList(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR,SyncSlno VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM VillageList WHERE TalukID='" + str_talukid + "'", null);
        int x = cursor1.getCount();
        Log.d("cursor Vcount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_VillageListDetails2 = new Class_VillageListDetails[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_VillageListDetails innerObj_Class_villageList = new Class_VillageListDetails();
                innerObj_Class_villageList.setVillage_id(cursor1.getString(cursor1.getColumnIndex("VillageID")));
                innerObj_Class_villageList.setVillage_name(cursor1.getString(cursor1.getColumnIndex("Village")));
                innerObj_Class_villageList.setTaluk_id(cursor1.getString(cursor1.getColumnIndex("TalukID")));
                innerObj_Class_villageList.setTaluk_id(cursor1.getString(cursor1.getColumnIndex("SyncSlno")));


                arrayObj_Class_VillageListDetails2[i] = innerObj_Class_villageList;
                // Log.e("village_name", cursor1.getString(cursor1.getColumnIndex("TalukName")));
                i++;
            } while (cursor1.moveToNext());
        }//if ends


        db1.close();

        for(int i1=0; i1 < arrayObj_Class_VillageListDetails2.length; i1++) {
            if (arrayObj_Class_VillageListDetails2[i1].village_id.equalsIgnoreCase(str_VillageId)) {
                sel_villagesp = i1;
                Log.e("tag", "sel_village pos==" + sel_villagesp);
                Log.e("tag", "sel_villageID==" + arrayObj_Class_VillageListDetails2[i1].village_id);
                Log.e("tag", "sel_villageName==" + arrayObj_Class_VillageListDetails2[i1].village_name);
            }
        }

        if (x > 0) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_VillageListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercustomstyle);
            villagelist_SP.setAdapter(dataAdapter);
            if(x>sel_villagesp) {
                villagelist_SP.setSelection(sel_villagesp);
            }

        }

    }

    public void uploadfromDB_KnowNavodyamiList() {

        SQLiteDatabase db_KnowNavodyami = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_KnowNavodyami.execSQL("CREATE TABLE IF NOT EXISTS KnowNavodyamiList(KnowNavodyamiId VARCHAR,KnowNavodyamiName VARCHAR);");
        Cursor cursor = db_KnowNavodyami.rawQuery("SELECT DISTINCT * FROM KnowNavodyamiList", null);
        int x = cursor.getCount();
        Log.d("tag","cursor count KnowNavodyami"+ Integer.toString(x));

        int i = 0;
        arrayObj_Class_NavodyamiDetails2 = new Class_KnowNavodyamiListDetails[x];
        if (cursor.moveToFirst()) {

            do {
                Class_KnowNavodyamiListDetails innerObj = new Class_KnowNavodyamiListDetails();
                innerObj.setId(cursor.getString(cursor.getColumnIndex("KnowNavodyamiId")));
                innerObj.setName(cursor.getString(cursor.getColumnIndex("KnowNavodyamiName")));

                arrayObj_Class_NavodyamiDetails2[i] = innerObj;
                Log.e("tag","arrayObj_Class_NavodyamiDetails2[]="+arrayObj_Class_NavodyamiDetails2[i].toString());
                listKnow.add(cursor.getString(cursor.getColumnIndex("KnowNavodyamiName")));
                i++;

            } while (cursor.moveToNext());


        }//if ends

        db_KnowNavodyami.close();
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

    public void uploadfromDB_CatgaryList() {

        SQLiteDatabase db_SocialCategorylist = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_SocialCategorylist.execSQL("CREATE TABLE IF NOT EXISTS SocialCategoryList(SocialCategoryId VARCHAR,SocialCategoryName VARCHAR);");
        Cursor cursor = db_SocialCategorylist.rawQuery("SELECT DISTINCT * FROM SocialCategoryList", null);
        int x = cursor.getCount();
        Log.d("tag","cursor count Category"+ Integer.toString(x));

        int i = 0;
        arrayObj_Class_CatgaryDetails2 = new Class_CatgaryListDetails[x];
        if (cursor.moveToFirst()) {

            do {
                Class_CatgaryListDetails innerObj = new Class_CatgaryListDetails();
                innerObj.setCatgaryId(cursor.getString(cursor.getColumnIndex("SocialCategoryId")));
                innerObj.setCatgaryName(cursor.getString(cursor.getColumnIndex("SocialCategoryName")));

                arrayObj_Class_CatgaryDetails2[i] = innerObj;
                Log.e("tag","arrayObj_Class_CatgaryDetails2[]="+arrayObj_Class_CatgaryDetails2[i].toString());
                i++;

            } while (cursor.moveToNext());


        }//if ends

        db_SocialCategorylist.close();
        if (x > 0) {
            Log.e("tag","arrayObj_Class_CatgaryDetails="+arrayObj_Class_CatgaryDetails2.toString());
            ArrayAdapter dataAdapter2 = new ArrayAdapter(this, R.layout.spinnercustomstyle, arrayObj_Class_CatgaryDetails2);
            dataAdapter2.setDropDownViewResource(R.layout.spinnercustomstyle);
            // attaching data adapter to spinner
            Catgary_SP.setAdapter(dataAdapter2);

            if(x>sel_social_Category) {
                Catgary_SP.setSelection(sel_social_Category);
            }
        }

    }

    public void uploadfromDB_QualificationList() {

        SQLiteDatabase db_Qualification = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_Qualification.execSQL("CREATE TABLE IF NOT EXISTS QualificationList(QualificationId VARCHAR,QualificationName VARCHAR);");
        Cursor cursor = db_Qualification.rawQuery("SELECT DISTINCT * FROM QualificationList", null);
        int x = cursor.getCount();
        Log.d("cursor count Qualif", Integer.toString(x));

        int i = 0;
        arrayObj_Class_QualificationDetails2 = new Class_QualificationListDetails[x];
        if (cursor.moveToFirst()) {

            do {
                Class_QualificationListDetails innerObj_Class_QualificationList = new Class_QualificationListDetails();
                innerObj_Class_QualificationList.setQualificationId(cursor.getString(cursor.getColumnIndex("QualificationId")));
                innerObj_Class_QualificationList.setQualificationName(cursor.getString(cursor.getColumnIndex("QualificationName")));

                arrayObj_Class_QualificationDetails2[i] = innerObj_Class_QualificationList;
                Log.e("tag","arrayObj_Class_QualificationDetails2[]="+arrayObj_Class_QualificationDetails2[i].toString());

                i++;

            } while (cursor.moveToNext());


        }//if ends

        db_Qualification.close();
        if (x > 0) {
            Log.e("tag","arrayObj_Class_QualificationDetails="+arrayObj_Class_QualificationDetails2.toString());
            ArrayAdapter dataAdapter2 = new ArrayAdapter(this, R.layout.spinnercustomstyle, arrayObj_Class_QualificationDetails2);
            dataAdapter2.setDropDownViewResource(R.layout.spinnercustomstyle);
            // attaching data adapter to spinner
            sp_Education.setAdapter(dataAdapter2);

            if(x>sel_qulification) {
                sp_Education.setSelection(sel_qulification);
            }
        }

    }

    public void uploadfromDB_Sectorlist() {

        SQLiteDatabase db_sector = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_sector.execSQL("CREATE TABLE IF NOT EXISTS SectorList(SectorId VARCHAR,SectorName VARCHAR);");
        Cursor cursor = db_sector.rawQuery("SELECT DISTINCT * FROM SectorList", null);
        int x = cursor.getCount();
        Log.d("cursor count sector", Integer.toString(x));

        int i = 0;
        arrayObj_Class_sectorDetails2 = new Class_SectorListDetails[x];
        if (cursor.moveToFirst()) {

            do {
                Class_SectorListDetails innerObj_Class_sectorList = new Class_SectorListDetails();
                innerObj_Class_sectorList.setSectorId(cursor.getString(cursor.getColumnIndex("SectorId")));
                innerObj_Class_sectorList.setSectorName(cursor.getString(cursor.getColumnIndex("SectorName")));

                arrayObj_Class_sectorDetails2[i] = innerObj_Class_sectorList;
                i++;

            } while (cursor.moveToNext());


        }//if ends

        db_sector.close();
        if (x > 0) {

            ArrayAdapter dataAdapter2 = new ArrayAdapter(this, R.layout.spinnercustomstyle, arrayObj_Class_sectorDetails2);
            dataAdapter2.setDropDownViewResource(R.layout.spinnercustomstyle);
            // attaching data adapter to spinner
            spin_Sector.setAdapter(dataAdapter2);

            if(x>sel_sector) {
                spin_Sector.setSelection(sel_sector);
            }
        }

    }
    public void uploadfromDB_Statelist() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS StateList(StateID VARCHAR,StateName VARCHAR,state_status VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM StateList", null);
        int x = cursor1.getCount();
        Log.d("cursor Scount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_stateDetails2 = new Class_StateListDetails[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_StateListDetails innerObj_Class_SandboxList = new Class_StateListDetails();
                innerObj_Class_SandboxList.setState_id(cursor1.getString(cursor1.getColumnIndex("StateID")));
                innerObj_Class_SandboxList.setState_name(cursor1.getString(cursor1.getColumnIndex("StateName")));
                innerObj_Class_SandboxList.setState_staus(cursor1.getString(cursor1.getColumnIndex("state_status")));


                arrayObj_Class_stateDetails2[i] = innerObj_Class_SandboxList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends
        for(int i1=0; i1 < arrayObj_Class_stateDetails2.length; i1++) {
            if (arrayObj_Class_stateDetails2[i1].state_id.equalsIgnoreCase(str_StateId))
                sel_statesp = i1;
        }
        db1.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_stateDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercustomstyle);
            statelist_SP.setAdapter(dataAdapter);

            //  sel_statesp = dataAdapter.getPosition(Integer.parseInt(str_StateId));

            if(x>sel_statesp) {
                statelist_SP.setSelection(sel_statesp);
            }
        }

    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }
    private void setCatgarySpinner() {
        final ArrayList listCat = new ArrayList();
        listCat.add("Select");
        listCat.add("General");
        listCat.add("SC");
        listCat.add("ST");
        listCat.add("OBC");

        // listCat.add("Do not want to disclose");

        //ArrayAdapter dataAdapterSem = new ArrayAdapter(context, R.layout.simple_spinner_semester, listsemester);
        dataAdapter_catgary = new ArrayAdapter(AddApplicationOneActivity.this, R.layout.simple_spinner_items, listCat);

        // Drop down layout style - list view with radio button
        dataAdapter_catgary.setDropDownViewResource(R.layout.spinnercustomstyle);

        // attaching data adapter to spinner
        Catgary_SP.setAdapter(dataAdapter_catgary);
        // sp_Education.setSupportBackgroundTintList(ContextCompat.getColorStateList(AddApplicationOneActivity.this, R.color.colorBlack));

    }

    private void setKnowSpinner() {
      //  final ArrayList listKnow = new ArrayList();
        /*listKnow.add("Select");
        listKnow.add("News paper");
        listKnow.add("Field staff");
        listKnow.add("Friends");*/
      /*  ArrayList<Class_KnowNavodyamiListDetails> innerObj = new ArrayList<>();
        for (int i=0;i<innerObj.size();i++){
            listKnow.add(innerObj.get(i).getName());
        }
*/
        //ArrayAdapter dataAdapterSem = new ArrayAdapter(context, R.layout.simple_spinner_semester, listsemester);
        dataAdapter_gotknow = new ArrayAdapter(AddApplicationOneActivity.this, R.layout.simple_spinner_items, listKnow);

        // Drop down layout style - list view with radio button
        dataAdapter_gotknow.setDropDownViewResource(R.layout.spinnercustomstyle);

        // attaching data adapter to spinner
        gotknow_sp.setAdapter(dataAdapter_gotknow);
    }

    private void setEducationSpinner() {
        final ArrayList listEdu = new ArrayList();
        listEdu.add("Select");
        listEdu.add("Below 10th");
        listEdu.add("10th pass");
        listEdu.add("12th pass");
        listEdu.add("Graduate");
        listEdu.add("Post graduate");
        /*listBg.add("AB-");
        listBg.add("O+");
        listBg.add("O-");
        listBg.add("Bombay");*/

        //ArrayAdapter dataAdapterSem = new ArrayAdapter(context, R.layout.simple_spinner_semester, listsemester);
        dataAdapter_education = new ArrayAdapter(AddApplicationOneActivity.this, R.layout.simple_spinner_items, listEdu);

        // Drop down layout style - list view with radio button
        dataAdapter_education.setDropDownViewResource(R.layout.spinnercustomstyle);

        // attaching data adapter to spinner
        sp_Education.setAdapter(dataAdapter_education);
        // sp_Education.setSupportBackgroundTintList(ContextCompat.getColorStateList(AddApplicationOneActivity.this, R.color.colorBlack));

    }
    private void setDateOfBirth() {

        final Calendar c = Calendar.getInstance();

        edt_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mYear = c.get(Calendar.YEAR)-15;
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddApplicationOneActivity.this, R.style.DatePickerTheme,
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
                                    dobseterror_tv.setVisibility(View.GONE);
                                    edt_dob.setText(dateFormatDisplay.format(d).toString());
                                    str_dob=dateFormat.format(d);

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

    private void setApplicationDate() {

        final Calendar c = Calendar.getInstance();

        edt_appldate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mYear = c.get(Calendar.YEAR)-15;
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddApplicationOneActivity.this, R.style.DatePickerTheme,
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
                                    appldate_seterror_TV.setVisibility(View.GONE);
                                    edt_appldate.setText(dateFormatDisplay.format(d).toString());
                                    str_appldate=dateFormat.format(d);

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

    public class GetEnquiryDetails extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;

        //private ProgressBar progressBar;

        GetEnquiryDetails (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];

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

            /*---------------------------------Soap Response----------------------------
            <vmEnquiry_Listing>
<EnquiryId>10</EnquiryId>
<FirstName>offline five</FirstName>
<MiddleName>test</MiddleName>
<LastName>test</LastName>
<MobileNo>885522233</MobileNo>
<EmailId>test</EmailId>
<StateId>12</StateId>
<DistrictId>557</DistrictId>
<TalukaId>5500</TalukaId>
<VillageId>7748</VillageId>
<PanchayatId>0</PanchayatId>
<SectorId>9</SectorId>
<BusinessName>test</BusinessName>
<isApplicant>0</isApplicant>
<Status>Success</Status>
</vmEnquiry_Listing>

             */
            if(result != null){
                SoapPrimitive S_StateId,S_DistrictId,S_TalukaId,S_VillageId,S_SectorId,S_LeadId, S_FirstName,S_MiddleName,S_LastName, S_EmailId, S_BusinessName, S_MobileNo, S_isApplicant, S_Status, S_Gender;
                Object O_StateId,O_DistrictId,O_TalukaId,O_VillageId,O_SectorId,O_LeadId, O_FirstName,O_MiddleName,O_LastName, O_EmailId, O_BusinessName, O_MobileNo, O_isApplicant, O_Status, O_Gender;
                String str_leadid = null, str_FirstName = null,str_MiddleName = null,str_LastName=null, str_EmailId = null, str_BusinessName = null, str_MobileNo = null, str_isApplicant = null, str_Status = null;

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

                        O_LeadId = list.getProperty("EnquiryId");
                        if (!O_LeadId.toString().equals("anyType{}") && !O_LeadId.toString().equals(null)) {
                            S_LeadId = (SoapPrimitive) list.getProperty("EnquiryId");
                            str_leadid = S_LeadId.toString();
                            Log.d("str_EnquiryId", str_leadid);
                        }

                        O_FirstName = list.getProperty("FirstName");
                        if (!O_FirstName.toString().equals("anyType{}") && !O_FirstName.toString().equals(null)) {
                            S_FirstName = (SoapPrimitive) list.getProperty("FirstName");
                            str_FirstName = S_FirstName.toString();
                            edt_FirstName.setText(str_FirstName);
                            Log.d("FirstName", str_FirstName);
                        }

                        O_MiddleName = list.getProperty("MiddleName");
                        if (!O_MiddleName.toString().equals("anyType{}") && !O_MiddleName.toString().equals(null)) {
                            S_MiddleName = (SoapPrimitive) list.getProperty("MiddleName");
                            str_MiddleName = S_MiddleName.toString();
                            edt_MiddleName.setText(str_MiddleName);
                            Log.d("MiddleName", str_MiddleName);
                        }

                        O_LastName = list.getProperty("LastName");
                        if (!O_LastName.toString().equals("anyType{}") && !O_LastName.toString().equals(null)) {
                            S_LastName = (SoapPrimitive) list.getProperty("LastName");
                            str_LastName = S_LastName.toString();
                            edt_LastName.setText(str_LastName);
                            Log.d("LastName", str_LastName);
                        }

                        O_MobileNo = list.getProperty("MobileNo");
                        if (!O_MobileNo.toString().equals("anyType{}") && !O_MobileNo.toString().equals(null)) {
                            S_MobileNo = (SoapPrimitive) list.getProperty("MobileNo");
                            Log.d("S_MobileNo", S_MobileNo.toString());
                            str_MobileNo = S_MobileNo.toString();
                            edt_MobileNo.setText(str_MobileNo);
                        }

                        O_EmailId = list.getProperty("EmailId");
                        if (!O_EmailId.toString().equals("anyType{}") && !O_EmailId.toString().equals(null)) {
                            S_EmailId = (SoapPrimitive) list.getProperty("EmailId");
                            Log.d("S_EmailId", S_EmailId.toString());
                            str_EmailId = S_EmailId.toString();
                            Log.d("Str_EmailId", str_EmailId);
                            edt_EmailId.setText(str_EmailId);
                        }

                        O_BusinessName = list.getProperty("BusinessName");
                        if (!O_BusinessName.toString().equals("anyType{}") && !O_BusinessName.toString().equals(null)) {
                            S_BusinessName = (SoapPrimitive) list.getProperty("BusinessName");
                            Log.d("S_BusinessName", S_BusinessName.toString());
                            str_BusinessName = S_BusinessName.toString();
                            Log.d("str_BusinessName", str_BusinessName);
                            edt_BusinessName.setText(str_BusinessName);
                            /// collegeNameLst.add(str_collegeName);

                        }
                        if (O_BusinessName.toString().equals("anyType{}") || O_BusinessName.toString().equals(null)) {
                            str_BusinessName = "";
                            edt_BusinessName.setText("");

                            //collegeNameLst.add(str_collegeName);
                        }



                        O_isApplicant = list.getProperty("isApplicant");
                        if (!O_isApplicant.toString().equals("anyType{}") && !O_isApplicant.toString().equals(null)) {
                            S_isApplicant = (SoapPrimitive) list.getProperty("isApplicant");
                            Log.d("S_isApplicant", S_isApplicant.toString());
                            str_isApplicant = S_isApplicant.toString();
                        }

                        O_StateId = list.getProperty("StateId");
                        if (!O_StateId.toString().equals("anyType{}") && !O_StateId.toString().equals(null)) {
                            S_StateId = (SoapPrimitive) list.getProperty("StateId");
                            Log.d("S_StateId", S_StateId.toString());
                            str_StateId = S_StateId.toString();
                            //  sel_statesp = Integer.parseInt(S_StateId.toString());
                        }
                        O_DistrictId = list.getProperty("DistrictId");
                        if (!O_DistrictId.toString().equals("anyType{}") && !O_DistrictId.toString().equals(null)) {
                            S_DistrictId = (SoapPrimitive) list.getProperty("DistrictId");
                            Log.d("str_DistrictId", S_DistrictId.toString());
                            str_DistrictId = S_DistrictId.toString();
                            // sel_districtsp = Integer.parseInt(S_DistrictId.toString());
                            //   districtlist_SP.setSelection(sel_districtsp);

                        }
                        O_TalukaId = list.getProperty("TalukaId");
                        if (!O_TalukaId.toString().equals("anyType{}") && !O_TalukaId.toString().equals(null)) {
                            S_TalukaId = (SoapPrimitive) list.getProperty("TalukaId");
                            Log.d("Str_TalukaId", S_TalukaId.toString());
                            str_TalukaId = S_TalukaId.toString();
                            //     sel_taluksp = Integer.parseInt(S_TalukaId.toString());

                        }
                        O_VillageId = list.getProperty("VillageId");
                        if (!O_VillageId.toString().equals("anyType{}") && !O_VillageId.toString().equals(null)) {
                            S_VillageId = (SoapPrimitive) list.getProperty("VillageId");
                            Log.d("str_VillageId", S_VillageId.toString());
                            str_VillageId = S_VillageId.toString();
                            //   sel_villagesp = Integer.parseInt(S_VillageId.toString());
                        }
                        O_SectorId = list.getProperty("SectorId");
                        if (!O_SectorId.toString().equals("anyType{}") && !O_SectorId.toString().equals(null)) {
                            S_SectorId = (SoapPrimitive) list.getProperty("SectorId");
                            Log.d("Str_SectorId", S_SectorId.toString());
                            str_SectorId = S_SectorId.toString();
                            sel_sector =  Integer.parseInt(S_SectorId.toString());
                            spin_Sector.setSelection(sel_sector);
                        }

                        O_Gender = list.getProperty("Gender");
                        if (!O_Gender.toString().equals("anyType{}") && !O_Gender.toString().equals(null)) {
                            S_Gender = (SoapPrimitive) list.getProperty("Gender");
                            Log.d("Str_Gender", S_Gender.toString());
                            str_gender = S_Gender.toString();
                            if(str_gender.equals("M"))
                            {
                                gender_radiogroup.check(R.id.rdb_male); }
                            else
                            { gender_radiogroup.check(R.id.rdb_female); }

                        }


                        /*EnquiryListModel item = null;
                        if (!str_collegeName.isEmpty()) {
                            item = new EnquiryListModel(str_studentName, str_leadid, str_registrationDate, str_collegeName, str_MobileNo, str_IsFeesPaid);
                            feesList.add(item);
                        }*/


                    }

                }

                /*originalList = new ArrayList<EnquiryListModel>();
                originalList.addAll(feesList);

                adapter.notifyDataSetChanged();
                initCollegeSpinner();*/
            }
            progressDialog.dismiss();
            uploadfromDB_Statelist();
           /* uploadfromDB_Districtlist();
            uploadfromDB_Taluklist();
            uploadfromDB_Villagelist();*/
            uploadfromDB_Sectorlist();
            uploadfromDB_QualificationList();
            uploadfromDB_CatgaryList();
       //     uploadfromDB_KnowNavodyamiList();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapObject getEnquiryListing() {
        String METHOD_NAME = "Enquiry_Listing";
        String SOAP_ACTION1 = "http://mis.navodyami.org/Enquiry_Listing";
        String NAMESPACE = "http://mis.navodyami.org/";

        try{

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
            request.addProperty("AcademicCode",0);
            request.addProperty("EnquiryId",Long.parseLong(EnquiryId));

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
                Log.d("tag","soapresponse Enquiry List="+envelope.getResponse().toString());


                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("tag","soapresponse enquiry="+response.toString());

                //return null;

                return response;

            }
            catch(OutOfMemoryError ex){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(AddApplicationOneActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());
                final String exceptionStr = t.getMessage().toString();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(AddApplicationOneActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        catch(OutOfMemoryError ex){
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(AddApplicationOneActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());
            final String exceptionStr = t.getMessage().toString();
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(AddApplicationOneActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;

    }


   /* public class GetApplicationDetails extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;

        //private ProgressBar progressBar;

        GetApplicationDetails (Context ctx){
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
      *//*      progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*//*
            progressDialog.setMessage("Loading");
            progressDialog.setProgressStyle(android.R.attr.progressBarStyleSmall);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(SoapObject result) {

            *//*---------------------------------Soap Response----------------------------
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

             *//*
            if(result != null){
                SoapPrimitive S_StateId,S_DistrictId,S_TalukaId,S_VillageId,S_SectorId, S_FirstName,S_MiddleName,S_LastName, S_EmailId, S_BusinessName, S_MobileNo, S_isApplicant, S_Status, S_Gender,S_Remark,SApplication_Date;
                Object O_StateId,O_DistrictId,O_TalukaId,O_VillageId,O_SectorId, O_FirstName,O_MiddleName,O_LastName, O_EmailId, O_BusinessName, O_MobileNo, O_isApplicant, O_Status, O_Gender,O_Remark,OApplication_Date;
                String  str_FirstName = null,str_MiddleName = null,str_LastName=null, str_EmailId = null, str_BusinessName = null, str_MobileNo = null, str_isApplicant = null, str_Status = null, str_Application_Date=null;

                SoapPrimitive S_Which_Machine,S_Earn_Most_Channel,S_Where_Sell_Products,S_TotalAmountProfit,S_TotalAmount,S_TotalFemale,S_TotalMale,S_p_Year,SSocial_Category,SYears_In_Navodyami,SNavodyami_Member_Before,SYears_In_UNDP,SUNDP_Member_Before,SKnow_Navodyami,SEarly_Sector,Syear_In_Current_Business,SYears_In_Business,SStreet,S_Application_Slno,S_User_Id,S_Enquiry_Id,S_DOB,S_Qualification,S_Economic_Status,S_WhatApp_No,SAadhar_No,SApplication_Fees,SReceipt_No,SReceipt_Date,SBusiness_Address;
                Object O_Which_Machine,O_Earn_Most_Channel,O_Where_Sell_Products,O_TotalAmountProfit,O_TotalAmount,O_TotalFemale,O_TotalMale,O_p_Year,OSocial_Category,OYears_In_Navodyami,ONavodyami_Member_Before,OYears_In_UNDP,OUNDP_Member_Before,OKnow_Navodyami,OEarly_Sector,Oyear_In_Current_Business,OYears_In_Business,OStreet,O_Application_Slno,O_User_Id,O_Enquiry_Id,O_DOB,O_Qualification,O_Economic_Status,O_WhatApp_No,OAadhar_No,OApplication_Fees,OReceipt_No,OReceipt_Date,OBusiness_Address;
                String str_Which_Machine=null,str_Earn_Most_Channel=null,str_Where_Sell_Products=null,str_TotalAmountProfit1=null,str_TotalAmountProfit2=null,str_TotalAmountProfit3=null,str_TotalAmountProfit=null,str_TotalAmount3=null,str_TotalAmount2=null,str_TotalAmount1=null,str_TotalAmount=null,str_TotalFemale3=null,str_TotalFemale2=null,str_TotalFemale1=null,str_TotalFemale=null,str_TotalMale3=null,str_TotalMale2=null,str_TotalMale1=null,str_TotalMale=null,str_p_Year3=null,str_p_Year2=null,str_str_p_Year1=null,str_p_Year=null,str_Social_Category=null,str_Years_In_Navodyami,str_Navodyami_Member_Before,str_Years_In_UNDP=null,str_UNDP_Member_Before,str_Know_Navodyami=null,str_Early_Sector=null,str_Application_Slno=null,str_User_Id=null,str_Enquiry_Id=null,str_DOB=null,str_Qualification=null,str_Economic_Status=null,str_Business_Address=null;
                String str_year_In_Current_Business=null,str_Years_In_Business=null,str_Street=null,str_WhatApp_No=null,str_Aadhar_No=null,str_Application_Fees=null,str_Receipt_No=null,str_Receipt_Date=null,str_Remark=null;

                SoapPrimitive SOwnership,SOwn_Rent_Machine,SHire_Outside_Family,STake_Skilled_Employee,SPermanent_Employee,STotal_Employee,SBusiness_Year,SWhich_License,SisHave_License,SSell_Product3,SSell_Product2,SSell_Product1,SisManufacture;
                Object OOwnership,OOwn_Rent_Machine,OHire_Outside_Family,OTake_Skilled_Employee,OPermanent_Employee,OTotal_Employee,OBusiness_Year,OWhich_License,OisHave_License,OSell_Product3,OSell_Product2,OSell_Product1,OisManufacture;
                String str_Ownership=null,str_Own_Rent_Machine=null,str_Hire_Outside_Family=null,str_Take_Skilled_Employee=null,str_Permanent_Employee=null,str_Total_Employee=null,str_Business_Year=null,str_Which_License=null,str_isHave_License=null,str_Sell_Product3=null,str_Sell_Product2=null,str_Sell_Product1=null,str_isManufacture=null;

                SoapPrimitive SPayment_Mode,SAcademic_Year,SLoan_For_Finance_Trading,SLoan_For_Equipment,SLoan_For_Land,SLoan_For_Working_Expenses,SLoan_For_Product_Improvement,SRepayment_Period,SInterest_Rate,SSanctioned_Loan_Amount,SApplied_Loan_Amount,SisKnowledge_Loan_Process,SInitial_Investment,SSource_Of_Business;
                Object OPayment_Mode,OAcademic_Year,OLoan_For_Finance_Trading,OLoan_For_Equipment,OLoan_For_Land,OLoan_For_Working_Expenses,OLoan_For_Product_Improvement,ORepayment_Period,OInterest_Rate,OSanctioned_Loan_Amount,OApplied_Loan_Amount,OisKnowledge_Loan_Process,OInitial_Investment,OSource_Of_Business;
                String str_Payment_Mode=null,str_Academic_Year=null,str_Loan_For_Finance_Trading=null,str_Loan_For_Equipment=null,str_Loan_For_Land=null,str_Loan_For_Working_Expenses=null,str_Loan_For_Product_Improvement=null,str_Repayment_Period=null,str_Interest_Rate=null,str_Sanctioned_Loan_Amount=null,str_Applied_Loan_Amount=null,str_isKnowledge_Loan_Process=null,str_Initial_Investment=null,str_Source_Of_Business=null;

                Log.e("tag","getPropertyCount="+result.getPropertyCount());
                for (int i = 0; i < result.getPropertyCount(); i++)
                {
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
                            ApplicationSlno=str_Application_Slno;
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
                            EnquiryId=str_Enquiry_Id;
                            Log.d("str_EnquiryId", str_Enquiry_Id);
                        }

                        O_FirstName = list.getProperty("First_Name");
                        if (!O_FirstName.toString().equals("anyType{}") && !O_FirstName.toString().equals(null)) {
                            S_FirstName = (SoapPrimitive) list.getProperty("First_Name");
                            str_FirstName = S_FirstName.toString();
                            edt_FirstName.setText(str_FirstName);
                            Log.d("FirstName", str_FirstName);
                        }

                        O_MiddleName = list.getProperty("Middle_Name");
                        if (!O_MiddleName.toString().equals("anyType{}") && !O_MiddleName.toString().equals(null)) {
                            S_MiddleName = (SoapPrimitive) list.getProperty("Middle_Name");
                            str_MiddleName = S_MiddleName.toString();
                            edt_MiddleName.setText(str_MiddleName);
                            Log.d("MiddleName", str_MiddleName);
                        }

                        O_LastName = list.getProperty("Last_Name");
                        if (!O_LastName.toString().equals("anyType{}") && !O_LastName.toString().equals(null)) {
                            S_LastName = (SoapPrimitive) list.getProperty("Last_Name");
                            str_LastName = S_LastName.toString();
                            edt_LastName.setText(str_LastName);
                            Log.d("LastName", str_LastName);
                        }

                        O_MobileNo = list.getProperty("Mobile_No");
                        if (!O_MobileNo.toString().equals("anyType{}") && !O_MobileNo.toString().equals(null)) {
                            S_MobileNo = (SoapPrimitive) list.getProperty("Mobile_No");
                            Log.d("S_MobileNo", S_MobileNo.toString());
                            str_MobileNo = S_MobileNo.toString();
                            edt_MobileNo.setText(str_MobileNo);
                        }

                        O_EmailId = list.getProperty("Email_Id");
                        if (!O_EmailId.toString().equals("anyType{}") && !O_EmailId.toString().equals(null)) {
                            S_EmailId = (SoapPrimitive) list.getProperty("Email_Id");
                            Log.d("S_EmailId", S_EmailId.toString());
                            str_EmailId = S_EmailId.toString();
                            Log.d("Str_EmailId", str_EmailId);
                            edt_EmailId.setText(str_EmailId);
                        }

                        O_BusinessName = list.getProperty("Business_Name");
                        if (!O_BusinessName.toString().equals("anyType{}") && !O_BusinessName.toString().equals(null)) {
                            S_BusinessName = (SoapPrimitive) list.getProperty("Business_Name");
                            Log.d("S_BusinessName", S_BusinessName.toString());
                            str_BusinessName = S_BusinessName.toString();
                            Log.d("str_BusinessName", str_BusinessName);
                            edt_BusinessName.setText(str_BusinessName);
                            /// collegeNameLst.add(str_collegeName);

                        }
                      *//*  if (O_BusinessName.toString().equals("anyType{}") || O_BusinessName.toString().equals(null)) {
                            str_BusinessName = "";
                            edt_BusinessName.setText("");

                            //collegeNameLst.add(str_collegeName);
                        }*//*



     *//* O_isApplicant = list.getProperty("isApplicant");
                        if (!O_isApplicant.toString().equals("anyType{}") && !O_isApplicant.toString().equals(null)) {
                            S_isApplicant = (SoapPrimitive) list.getProperty("isApplicant");
                            Log.d("S_isApplicant", S_isApplicant.toString());
                            str_isApplicant = S_isApplicant.toString();
                        }*//*

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
                            sel_sector =  Integer.parseInt(S_SectorId.toString());
                            spin_Sector.setSelection(sel_sector);
                        }
                        O_DOB = list.getProperty("DOB");
                        if (!O_DOB.toString().equals("anyType{}") && !O_DOB.toString().equals(null)) {
                            S_DOB = (SoapPrimitive) list.getProperty("DOB");
                            Log.d("str_DOB new", S_DOB.toString());
                            str_DOB = S_DOB.toString();
                           *//* SimpleDateFormat dateFormatDisplay= new SimpleDateFormat("dd-MM-yyyy");
                            Date date= null;
                            try {
                                date = dateFormatDisplay.parse(str_DOB);
                                Log.d("str_DOB date", date.toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }*//*
                            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
                            SimpleDateFormat dateFormatDisplay= new SimpleDateFormat("dd-MM-yyyy");
                            try {
                                Date d=dateFormat.parse(str_DOB);
                                System.out.println("Formated"+dateFormat.format(d));
                                dobseterror_tv.setVisibility(View.GONE);
                                edt_dob.setText(dateFormatDisplay.format(d).toString());
                                str_dob=dateFormat.format(d);

                            }
                            catch(Exception e) {
                                System.out.println("Excep"+e);
                            }
                        }
                        O_Qualification = list.getProperty("Qualification");
                        if (!O_Qualification.toString().equals("anyType{}") && !O_Qualification.toString().equals(null)) {
                            S_Qualification = (SoapPrimitive) list.getProperty("Qualification");
                            Log.d("Qualification", S_Qualification.toString());
                            str_Qualification = S_Qualification.toString();
                            sel_qulification= Integer.parseInt(str_Qualification);
                            sp_Education.setSelection(sel_qulification);
                        }
                        OSocial_Category = list.getProperty("Social_Category");
                        if (!OSocial_Category.toString().equals("anyType{}") && !OSocial_Category.toString().equals(null)) {
                            SSocial_Category = (SoapPrimitive) list.getProperty("Social_Category");
                            Log.d("Social_Category", SSocial_Category.toString());
                            str_Social_Category = SSocial_Category.toString();
                            sel_social_Category= Integer.parseInt(str_Social_Category);
                            Catgary_SP.setSelection(sel_social_Category);
                            str_catgary=SSocial_Category.toString();
                        }

                        O_Gender = list.getProperty("Gender");
                        if (!O_Gender.toString().equals("anyType{}") && !O_Gender.toString().equals(null)) {
                            S_Gender = (SoapPrimitive) list.getProperty("Gender");
                            Log.d("Str_Gender", S_Gender.toString());
                            str_gender = S_Gender.toString();
                            if(str_gender.equals("M"))
                            {
                                gender_radiogroup.check(R.id.rdb_male); }
                            else
                            { gender_radiogroup.check(R.id.rdb_female); }

                        }
                        O_Economic_Status = list.getProperty("Economic_Status");
                        if (!O_Economic_Status.toString().equals("anyType{}") && !O_Economic_Status.toString().equals(null)) {
                            S_Economic_Status = (SoapPrimitive) list.getProperty("Economic_Status");
                            Log.d("Str_Economic_Status", S_Economic_Status.toString());
                            str_Economic_Status = S_Economic_Status.toString();
                            if(str_Economic_Status.equals("APL"))
                            {
                                economic_radiogroup.check(R.id.rdb_apl);
                                str_economic="APL";
                            }
                            else
                            { economic_radiogroup.check(R.id.rdb_bpl);
                            str_economic="BPL";
                            }

                        }
                        O_WhatApp_No = list.getProperty("WhatApp_No");
                        if (!O_WhatApp_No.toString().equals("anyType{}") && !O_WhatApp_No.toString().equals(null)) {
                            S_WhatApp_No = (SoapPrimitive) list.getProperty("WhatApp_No");
                            str_WhatApp_No = S_WhatApp_No.toString();
                            Log.d("S_WhatApp_No", str_WhatApp_No);
                            edt_whatsappMobileNo.setText(str_WhatApp_No);
                        }
                        OAadhar_No = list.getProperty("Aadhar_No");
                        if (!OAadhar_No.toString().equals("anyType{}") && !OAadhar_No.toString().equals(null)) {
                            SAadhar_No = (SoapPrimitive) list.getProperty("Aadhar_No");
                            str_Aadhar_No = SAadhar_No.toString();
                            Log.d("S_Aadhar_No", str_Aadhar_No);
                            edt_Aadhar.setText(str_Aadhar_No);
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
                            edt_BusinessAddress.setText(str_Business_Address);
                        }
                        OStreet= list.getProperty("Street");
                        if (!OStreet.toString().equals("anyType{}") && !OStreet.toString().equals(null)) {
                            SStreet = (SoapPrimitive) list.getProperty("Street");
                            str_Street = SStreet.toString();
                            Log.d("S_Street", str_Street);
                            edt_Street.setText(str_Street);
                        }
                        OYears_In_Business= list.getProperty("Years_In_Business");
                        if (!OYears_In_Business.toString().equals("anyType{}") && !OYears_In_Business.toString().equals(null)) {
                            SYears_In_Business = (SoapPrimitive) list.getProperty("Years_In_Business");
                            str_Years_In_Business = SYears_In_Business.toString();
                            Log.d("OYears_In_Business", str_Years_In_Business);
                            edt_yearInBusiness.setText(str_Years_In_Business);
                        }
                        Oyear_In_Current_Business= list.getProperty("year_In_Current_Business");
                        if (!Oyear_In_Current_Business.toString().equals("anyType{}") && !Oyear_In_Current_Business.toString().equals(null)) {
                            Syear_In_Current_Business = (SoapPrimitive) list.getProperty("year_In_Current_Business");
                            str_year_In_Current_Business = Syear_In_Current_Business.toString();
                            Log.d("S_Street", str_year_In_Current_Business);
                            edt_yearIncurrentBusiness.setText(str_year_In_Current_Business);
                        }
                        OEarly_Sector= list.getProperty("Early_Sector");
                        if (!OEarly_Sector.toString().equals("anyType{}") && !OEarly_Sector.toString().equals(null)) {
                            SEarly_Sector = (SoapPrimitive) list.getProperty("Early_Sector");
                            str_Early_Sector = SEarly_Sector.toString();
                            Log.d("Early_Sector", str_Early_Sector);
                            edt_sectorBusiness.setText(str_Early_Sector);
                        }
                        OKnow_Navodyami= list.getProperty("Know_Navodyami");
                        if (!OKnow_Navodyami.toString().equals("anyType{}") && !OKnow_Navodyami.toString().equals(null)) {
                            SKnow_Navodyami = (SoapPrimitive) list.getProperty("Know_Navodyami");
                            str_Know_Navodyami = SKnow_Navodyami.toString();
                            Log.d("Know_Navodyami", str_Know_Navodyami);
                          //  edt_gottoknow.setText(str_Know_Navodyami);
                            if (str_Know_Navodyami != null) {
                                int spinnerPosition = dataAdapter_gotknow.getPosition(str_Know_Navodyami);
                                gotknow_sp.setSelection(spinnerPosition);
                            }
                        }
                        OUNDP_Member_Before= list.getProperty("UNDP_Member_Before");
                        if (!OUNDP_Member_Before.toString().equals("anyType{}") && !OUNDP_Member_Before.toString().equals(null)) {
                            SUNDP_Member_Before = (SoapPrimitive) list.getProperty("UNDP_Member_Before");
                            str_UNDP_Member_Before = SUNDP_Member_Before.toString();
                            Log.d("UNDP_Member_Before", str_UNDP_Member_Before);

                            if(str_UNDP_Member_Before.equals("1"))
                            {
                                undp_radiogroup.check(R.id.rdb_UNDPyes);
                                int_UNDP="1";
                                edt_undp.setVisibility(View.VISIBLE);
                            }
                            else
                            { undp_radiogroup.check(R.id.rdb_UNDPno);
                            int_UNDP="0";
                                edt_undp.setVisibility(View.GONE);
                            }
                        }
                        OYears_In_UNDP= list.getProperty("Years_In_UNDP");
                        if (!OYears_In_UNDP.toString().equals("anyType{}") && !OYears_In_UNDP.toString().equals(null)) {
                            SYears_In_UNDP = (SoapPrimitive) list.getProperty("Years_In_UNDP");
                            str_Years_In_UNDP = SYears_In_UNDP.toString();
                            Log.d("Years_In_UNDP", str_Years_In_UNDP);
                            edt_undp.setText(str_Years_In_UNDP);
                           // int_UNDP=str_Years_In_UNDP;
                        }
                        ONavodyami_Member_Before= list.getProperty("Navodyami_Member_Before");
                        if (!ONavodyami_Member_Before.toString().equals("anyType{}") && !ONavodyami_Member_Before.toString().equals(null)) {
                            SNavodyami_Member_Before = (SoapPrimitive) list.getProperty("Navodyami_Member_Before");
                            str_Navodyami_Member_Before = SNavodyami_Member_Before.toString();
                            Log.d("Navodyami_Member_Before", str_Navodyami_Member_Before);

                            if(str_Navodyami_Member_Before.equals("1"))
                            {
                                navodyami_radiogroup.check(R.id.rdb_navoyes);
                                int_navo="1";
                                edt_navodyami.setVisibility(View.VISIBLE);
                            }
                            else
                            { navodyami_radiogroup.check(R.id.rdb_navono);
                                int_navo="0";
                                edt_navodyami.setVisibility(View.GONE);
                            }
                        }
                        OYears_In_Navodyami= list.getProperty("Years_In_Navodyami");
                        if (!OYears_In_Navodyami.toString().equals("anyType{}") && !OYears_In_Navodyami.toString().equals(null)) {
                            SYears_In_Navodyami = (SoapPrimitive) list.getProperty("Years_In_Navodyami");
                            str_Years_In_Navodyami = SYears_In_Navodyami.toString();
                            Log.d("Years_In_Navodyami", str_Years_In_Navodyami);
                            edt_navodyami.setText(str_Years_In_Navodyami);
                        }
                        OApplication_Date= list.getProperty("Application_Date");
                        if (!OApplication_Date.toString().equals("anyType{}") && !OApplication_Date.toString().equals(null)) {
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
                            }
                        }


                      //-----------------------------------Business Details----------------------------------------
                        OisManufacture= list.getProperty("isManufacture");
                        if (!OisManufacture.toString().equals("anyType{}") && !OisManufacture.toString().equals(null)) {
                            SisManufacture = (SoapPrimitive) list.getProperty("isManufacture");
                            str_isManufacture = SisManufacture.toString();
                            Log.d("isManufacture", str_isManufacture);
                        }
                        OSell_Product1= list.getProperty("Sell_Product1");
                        if (!OSell_Product1.toString().equals("anyType{}") && !OSell_Product1.toString().equals(null)) {
                            SSell_Product1 = (SoapPrimitive) list.getProperty("Sell_Product1");
                            str_Sell_Product1 = SSell_Product1.toString();
                            Log.d("Sell_Product1", str_Sell_Product1);
                        }
                        OSell_Product2= list.getProperty("Sell_Product2");
                        if (!OSell_Product2.toString().equals("anyType{}") && !OSell_Product2.toString().equals(null)) {
                            SSell_Product2 = (SoapPrimitive) list.getProperty("Sell_Product2");
                            str_Sell_Product2 = SSell_Product2.toString();
                            Log.d("Sell_Product2", str_Sell_Product2);
                        }
                        OSell_Product3= list.getProperty("Sell_Product3");
                        if (!OSell_Product3.toString().equals("anyType{}") && !OSell_Product3.toString().equals(null)) {
                            SSell_Product3 = (SoapPrimitive) list.getProperty("Sell_Product3");
                            str_Sell_Product3 = SSell_Product3.toString();
                            Log.d("Sell_Product3", str_Sell_Product3);
                        }
                        OisHave_License= list.getProperty("isHave_License");
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
                       *//* OPermanent_Employee = list.getProperty("Permanent_Employee");
                        if (!OPermanent_Employee.toString().equals("anyType{}") && !OPermanent_Employee.toString().equals(null)) {
                            SPermanent_Employee = (SoapPrimitive) list.getProperty("Permanent_Employee");
                            str_Permanent_Employee = SPermanent_Employee.toString();
                            Log.d("Permanent_Employee", str_Permanent_Employee);
                        }*//*
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
                            String status=vmPermanent_Employee.getProperty("Status").toString();
                            if(status.equalsIgnoreCase("Success")) {
                              //  int icount=j;
                                if(j==0) {
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
                                        str_TotalFemale1= S_TotalFemale.toString();
                                    }
                                }
                                if(j==1) {
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
                                if(j==2) {
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
                                *//*O_TotalMale = vmPermanent_Employee.getProperty("TotalMale");
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
                                }*//*
                                Log.e("tag", "TotalFemale=" + str_TotalFemale + "str_TotalMale=" + str_TotalMale + "str_p_Year=" + str_p_Year);
                                *//*if (j == 0) {
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
                                }*//*
                            }
                        }

                      *//*  SoapObject Permanent_Employee = (SoapObject) result.getProperty("p_Permanent_Employee");
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
                        }*//*
                        if(list.toString().contains("p_TurnOver"))
                        {
                            SoapObject Permanent_EmployeeList = (SoapObject) list.getProperty("p_TurnOver");
                            for (int j = 0; j < Permanent_EmployeeList.getPropertyCount(); j++)
                            {
                                SoapObject vmPermanent_Employee = (SoapObject) Permanent_EmployeeList.getProperty(j);


                                String status=vmPermanent_Employee.getProperty("Status").toString();

                                if(status.equalsIgnoreCase("Success")) {

                                   *//* O_p_Year = vmPermanent_Employee.getProperty("p_Year");
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
                                    }*//*
                                    if (j == 0) {
                                        str_p_Year=null;
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
                                        if(str_str_p_Year1==null){
                                            str_str_p_Year1=str_p_Year;
                                        }

                                        //str_TotalAmount1 = str_TotalAmount;
                                    }
                                    if (j == 1) {
                                        str_p_Year=null;
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
                                        if(str_p_Year2==null){
                                            str_p_Year2=str_p_Year;
                                        }
                                       // str_TotalAmount2 = str_TotalAmount;
                                    }
                                    if (j == 2) {
                                        str_p_Year=null;
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
                                        if(str_p_Year3==null){
                                            str_p_Year3=str_p_Year;
                                        }
                                       // str_TotalAmount3 = str_TotalAmount;
                                    }
                                }
                            }
                        }
                        if(list.toString().contains("p_Profit"))
                        {
                            SoapObject Permanent_EmployeeList = (SoapObject) list.getProperty("p_Profit");
                            for (int j = 0; j < Permanent_EmployeeList.getPropertyCount(); j++)
                            {
                                SoapObject vmPermanent_Employee = (SoapObject) Permanent_EmployeeList.getProperty(j);
                                String status=vmPermanent_Employee.getProperty("Status").toString();
                                if(status.equalsIgnoreCase("Success")) {


                                   *//* O_p_Year = vmPermanent_Employee.getProperty("p_Year");
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
                                    }*//*
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
                                        if(str_str_p_Year1==null){
                                            str_str_p_Year1=str_p_Year;
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
                                        if(str_p_Year2==null){
                                            str_p_Year2=str_p_Year;
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
                                        if(str_p_Year2==null){
                                            str_p_Year3=str_p_Year;
                                        }

                                       // str_TotalAmountProfit3 = str_TotalAmountProfit;
                                    }
                                }
                            }
                        }
                        Class_AddApplicationTwoDetails class_addApplicationTwoDetails = new Class_AddApplicationTwoDetails(str_isManufacture,str_isHave_License,str_Which_License,str_Sell_Product1,
                                str_Sell_Product2,str_Sell_Product3,str_Business_Year,str_Ownership,str_str_p_Year1,str_TotalFemale1,str_TotalMale1,str_p_Year2,str_TotalFemale2,str_TotalMale2,
                                str_p_Year3,str_TotalFemale3,str_TotalMale3,str_Take_Skilled_Employee,str_Hire_Outside_Family,str_TotalAmount1,str_TotalAmount2,str_TotalAmount3,str_TotalAmountProfit1,
                                str_TotalAmountProfit2,str_TotalAmountProfit3,str_Which_Machine,str_Own_Rent_Machine,str_Where_Sell_Products,str_Earn_Most_Channel,EnquiryId,str_Total_Employee,str_Application_Slno);
                        addApplicationTwoList.add(class_addApplicationTwoDetails);
                        DBCreate_AddApplicationTwoDetails_insert_2SQLiteDB(addApplicationTwoList);

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
                            Log.d("tag","isKnowledge_Loan_Process"+ str_isKnowledge_Loan_Process);
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
                            Log.d("tag","Loan_For_Product_Improvement"+ str_Loan_For_Product_Improvement);
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
                            Log.d("tag","Loan_For_Working_Expenses"+ str_Loan_For_Working_Expenses);
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
                            Log.d("tag","Loan_For_Finance_Trading"+ str_Loan_For_Finance_Trading);
                        }
                        OAcademic_Year = list.getProperty("Academic_Year");
                        if (!OAcademic_Year.toString().equals("anyType{}") && !OAcademic_Year.toString().equals(null)) {
                            SAcademic_Year = (SoapPrimitive) list.getProperty("Academic_Year");
                            str_Academic_Year = SAcademic_Year.toString();
                            Log.d("Academic_Year", str_Academic_Year);
                        }
                        String str_LastLoan=str_Loan_For_Product_Improvement+","+str_Loan_For_Working_Expenses+","+str_Loan_For_Land+","+str_Loan_For_Finance_Trading+","+str_Loan_For_Equipment;
                      //  String str_BusinessSource="1";
                        Log.e("tag","str_LastLoan="+str_LastLoan);
                        Class_AddApplicationThreeDetails class_addApplicationThreeDetails = new Class_AddApplicationThreeDetails(str_Source_Of_Business,str_LastLoan,str_Initial_Investment,str_isKnowledge_Loan_Process,str_Applied_Loan_Amount,str_Sanctioned_Loan_Amount,str_Interest_Rate,str_Loan_For_Product_Improvement,EnquiryId,str_Repayment_Period,str_Loan_For_Product_Improvement,str_Loan_For_Working_Expenses,str_Loan_For_Land,str_Loan_For_Equipment,str_Loan_For_Finance_Trading,str_Application_Slno,str_Application_Fees,str_Receipt_Date,str_Remark,str_Receipt_No,str_Payment_Mode);
                        addApplicationThreeList.add(class_addApplicationThreeDetails);

                        DBCreate_AddApplicationThreeDetails_insert_2SQLiteDB(addApplicationThreeList);

                    }

                }

                *//*originalList = new ArrayList<EnquiryListModel>();
                originalList.addAll(feesList);

                adapter.notifyDataSetChanged();
                initCollegeSpinner();*//*
            }
            progressDialog.dismiss();
            uploadfromDB_Statelist();
           *//* uploadfromDB_Districtlist();
            uploadfromDB_Taluklist();
            uploadfromDB_Villagelist();*//*
            uploadfromDB_Sectorlist();


        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapObject getApplicationListing() {
        String METHOD_NAME = "Get_Application_Details";
        String SOAP_ACTION1 = "http://mis.navodyami.org/Get_Application_Details";
        String NAMESPACE = "http://mis.navodyami.org/";

        try{

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
            request.addProperty("Application_Slno",ApplicationSlno);
           // request.addProperty("EnquiryId",Long.parseLong(EnquiryId));

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Login.toString().trim());

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

             //   SoapPrimitive response1 = (SoapPrimitive) envelope.getResponse();
                Log.d("tag","soapresponse Application Details="+envelope.getResponse().toString());


                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("tag","soapresponse Application details res="+response.toString());

                //return null;

                return response;

            }
            catch(OutOfMemoryError ex){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(AddApplicationOneActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());
                final String exceptionStr = t.getMessage().toString();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(AddApplicationOneActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        catch(OutOfMemoryError ex){
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(AddApplicationOneActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());
            final String exceptionStr = t.getMessage().toString();
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(AddApplicationOneActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;

    }*/

    private class UpdateApplicationDetails extends AsyncTask<String, Void, Void>
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

        public UpdateApplicationDetails(AddApplicationOneActivity activity) {
            context = activity;
            dialog = new ProgressDialog(context);
        }
        @Override
        protected Void doInBackground(String... params)
        {
            Log.i("TAG", "doInBackground");
            Update( params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9] ,params[10],params[11],params[12]);
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            Log.i("TAG", "onPostExecute");

            Log.d("tag","Application Responseisss: "+status.toString());

            dialog.dismiss();

            if(status.equalsIgnoreCase("success")) {
                Log.e("tag","Application_SlnoNew response1=="+Application_SlnoNew);
                Log.e("tag","ApplicationSlno response=="+ApplicationSlno);

                if(Application_SlnoNew.equalsIgnoreCase("0")){
                    Application_SlnoNew=ApplicationSlno;
                }
                else {
                    //DBUpdate_ApplicationNo(Application_SlnoNew,EnquiryId);
                }
                Log.e("tag","Application_SlnoNew response2=="+Application_SlnoNew);
                Toast toast= Toast.makeText(AddApplicationOneActivity.this, " Application Updated Successfully ",Toast.LENGTH_SHORT);
                Intent intent1  = new Intent (AddApplicationOneActivity.this, AddApplicationTwoActivity.class);
                intent1.putExtra("Application_SlnoNew",Application_SlnoNew);
                intent1.putExtra("EnquiryId",EnquiryId);
                intent1.putExtra("isApplicant",isApplicant);
                intent1.putExtra("tempId",tempId);
                startActivity(intent1);

                View view =toast.getView();
                view.setBackgroundColor( Color.parseColor("#009900")); //any color your want
                toast.show();
            }
            else{
                //  Toast.makeText(AddApplicationOneActivity.this, "Update Request Failed " , Toast.LENGTH_LONG).show();
                // Toast toast= Toast.makeText(AddApplicationOneActivity.this, "  Update Request Failed  " , Toast.LENGTH_LONG);
                Toast toast= Toast.makeText(context, "  "+status.toString()+"  " , Toast.LENGTH_LONG);
                View view =toast.getView();
                view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
                toast.show();
            }
        }
    }

    public void Update(String FName,String MName,String LName,String MobileNo,String EmailId,String BusinessName,String sp_strstate_ID,String sp_strdistrict_ID,String sp_strTaluk_ID,String sp_strVillage_ID,String sp_strsector_ID,String str_UserId,String Gender){
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


            request.addProperty("Enquiry_Id",Integer.valueOf(EnquiryId));
            request.addProperty("Application_Slno",Integer.valueOf(ApplicationSlno));
            request.addProperty("First_Name", FName);
            request.addProperty("Middle_Name", MName);
            request.addProperty("Last_Name", LName);
            request.addProperty("Mobile_No", MobileNo);
            request.addProperty("Email_Id", EmailId);
            request.addProperty("WhatApp_No", str_whatsapp);
            request.addProperty("Aadhar_No", str_Aadhar);
            request.addProperty("State_Id", Integer.valueOf(sp_strstate_ID));
            request.addProperty("District_Id", Integer.valueOf(sp_strdistrict_ID));
            request.addProperty("Taluka_Id",Integer.valueOf(sp_strTaluk_ID));
            request.addProperty("Village_Id",Integer.valueOf(sp_strVillage_ID));
            request.addProperty("Gender",Gender);
            request.addProperty("DOB",str_dob);
            request.addProperty("Qualification_Id",Integer.valueOf(str_education));
            request.addProperty("Category_Id",Integer.valueOf(str_catgary));
            request.addProperty("Economic_Status",str_economic);
            request.addProperty("Sector_Id",Integer.valueOf(sp_strsector_ID));
            request.addProperty("Business_Name",BusinessName);
            request.addProperty("Business_Address",str_businessAddrs);
            request.addProperty("Years_In_Business",Integer.valueOf(str_yearInBusiness));
            request.addProperty("Years_In_Current_Business",Integer.valueOf(str_yearInCurrentbusiness));
            request.addProperty("Earlier_Sector",str_sectorBusiness);
            request.addProperty("Know_Navodyami",str_gottoknow);
            request.addProperty("isNavodyami_Member_Before",Integer.valueOf(int_navo));
            request.addProperty("Year_in_Navodyami",Integer.valueOf(str_yearNavo));
            request.addProperty("isUNDP_Member_Before",Integer.valueOf(int_UNDP));
            request.addProperty("Year_in_UNDP",Integer.valueOf(str_yearUNDP));
            request.addProperty("User_Id",Integer.valueOf(str_UserId));
            request.addProperty("Application_Date",str_appldate);
            request.addProperty("Business_Street",str_Street);
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

    public boolean validation()
    {

        Boolean bfname=true,bmobileno=true,bBusinessname=true,bsector=true,beducation=true,b_dob=true;
        if(spin_Sector.getSelectedItem().toString().equalsIgnoreCase("")){
            Toast.makeText(AddApplicationOneActivity.this," Data is empty please sync data ",Toast.LENGTH_LONG).show();
        }
        String str_Sector = spin_Sector.getSelectedItem().toString();
        String str_Education = sp_Education.getSelectedItem().toString();
        String str_SocialCat = Catgary_SP.getSelectedItem().toString();

        String str_navodyami = gotknow_sp.getSelectedItem().toString();
        if( edt_FirstName.getText().toString().length() == 0 ){
            edt_FirstName.setError( "First name is required!" );bfname=false;}

        if (edt_MobileNo.getText().toString().length() == 0 || edt_MobileNo.getText().toString().length() < 10) {
            edt_MobileNo.setError("InValid Mobile Number");
            bmobileno=false;
        }
        if (edt_whatsappMobileNo.getText().toString().length() != 0 && edt_whatsappMobileNo.getText().toString().length() < 10) {
            edt_whatsappMobileNo.setError("InValid Mobile Number");
            bmobileno=false;
        }
        if (edt_Aadhar.getText().toString().length() != 0 && edt_Aadhar.getText().toString().length() < 12) {
            edt_Aadhar.setError("InValid Aadhar Number");
            bmobileno=false;
        }
        /*if( edt_MobileNo.getText().toString().length() == 0 ){
            edt_MobileNo.setError( "Mobile No required!" );bmobileno=false;}
*/
        if( edt_BusinessName.getText().toString().length() == 0 ){
            edt_BusinessName.setError( "Business Name is required!" );bBusinessname=false;}

        if(str_Sector.length()==0){

            TextView errorText = (TextView)spin_Sector.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Please Sync Data");
            // Toast.makeText(AddEnquiryActivity.this,"Please select the Sector",Toast.LENGTH_LONG).show();
            bsector=false;
            // return false;
        }
        if(str_Education.contains("Select")){

            TextView errorText = (TextView)sp_Education.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Please select the Education");
            // Toast.makeText(AddEnquiryActivity.this,"Please select the Sector",Toast.LENGTH_LONG).show();
            beducation=false;
            // return false;
        }
        if(str_Sector.contains("Select")){

            TextView errorText = (TextView)spin_Sector.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Please select the Sector");
            // Toast.makeText(AddEnquiryActivity.this,"Please select the Sector",Toast.LENGTH_LONG).show();
            beducation=false;
            // return false;
        }
        if(str_SocialCat.contains("Select")){

            str_catgary="0";
           /* TextView errorText = (TextView)sp_Education.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Please select the Education");
            // Toast.makeText(AddEnquiryActivity.this,"Please select the Sector",Toast.LENGTH_LONG).show();
            beducation=false;*/
            // return false;
        }
        if(str_navodyami.contains("Select")){

           /* TextView errorText = (TextView)gotknow_sp.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Please select how know about Navodyami");
            // Toast.makeText(AddEnquiryActivity.this,"Please select the Sector",Toast.LENGTH_LONG).show();
            beducation=false;*/
            str_gottoknow=" ";
            // return false;
        }
        if (edt_dob.getText().toString().length() == 0 ||edt_dob.getText().toString().length()<=5)
        {
            dobseterror_tv.setVisibility(View.VISIBLE);
            dobseterror_tv.setError("Enter the DOB!");
            b_dob=false;
        }
        if (edt_appldate.getText().toString().length() == 0 ||edt_appldate.getText().toString().length()<=5)
        {
            appldate_seterror_TV.setVisibility(View.VISIBLE);
            appldate_seterror_TV.setError("Enter the Application Date!");
            b_dob=false;
        }
        if (gender_radiogroup.getCheckedRadioButtonId() == -1)
        {
            // no radio buttons are checked
            //  Toast.makeText(getApplicationContext(), "Please select Gender", Toast.LENGTH_SHORT).show();
            Toast toast= Toast.makeText(getApplicationContext(), "  Please select Gender  " , Toast.LENGTH_LONG);
            View view =toast.getView();
            view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
            toast.show();
            b_dob=false;
        }
        String email = edt_EmailId.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(str_Sector==null){
            TextView errorText = (TextView)spin_Sector.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Please Sync Data");
        }
        if(edt_EmailId.getText().toString().length() != 0) {
            if (!email.matches(emailPattern)) {

                edt_EmailId.setError("Invalid Email Id");

                beducation = false;
            }
        }else{
            beducation = true;
        }



        if(bfname&&bmobileno&&bBusinessname&&bsector&&beducation&&b_dob) {
            return true;
        }else{
            Toast toast= Toast.makeText(getApplicationContext(), "  Please fill mandatory fields" , Toast.LENGTH_LONG);
            View view =toast.getView();
            view.setBackgroundColor(	Color.parseColor("#DC143C")); //any color your want
            toast.show();
            return false;}
    }

    ///////////////////////////////// DB Creation ///////////////////////////////////////////
/*
 <Enquiry_Id>string</Enquiry_Id><First_Name>string</First_Name><Middle_Name>string</Middle_Name><Last_Name>string</Last_Name><DOB>string</DOB>
<Gender>string</Gender><Qualification>string</Qualification><Social_Category>string</Social_Category><Economic_Status>string</Economic_Status>
<Mobile_No>string</Mobile_No><Email_Id>string</Email_Id><WhatApp_No>string</WhatApp_No><Aadhar_No>string</Aadhar_No><Application_Fees>int</Application_Fees>
<Receipt_Date>string</Receipt_Date><Business_Address>string</Business_Address><Street>string</Street><State_Id>string</State_Id>
<District_Id>string</District_Id><Taluka_Id>string</Taluka_Id><Village_Id>string</Village_Id><Years_In_Business>int</Years_In_Business>
<Year_In_Current_Business>int</Year_In_Current_Business><Early_Sector_Id>string</Early_Sector_Id><Know_Navodyami>string</Know_Navodyami>
<UNDP_Member_Before>int</UNDP_Member_Before><Years_In_UNDP>int</Years_In_UNDP><Navodyami_Member_Before>int</Navodyami_Member_Before>
<Years_In_Navodyami>int</Years_In_Navodyami><Business_Name>string</Business_Name><Sector_Id>string</Sector_Id><isManufacture>int</isManufacture>
<isHave_License>int</isHave_License><Which_License>string</Which_License><Business_Year>int</Business_Year><Ownership>string</Ownership>
<Total_Employee>int</Total_Employee><Permanent_Employee>int</Permanent_Employee><Take_Skilled_Employee>int</Take_Skilled_Employee>
<Hire_Outside_Family>int</Hire_Outside_Family><Which_Machine>string</Which_Machine><Own_Rent_Machine>int</Own_Rent_Machine>
<isAttended_MarketLinkage>int</isAttended_MarketLinkage><Earn_Most_Channel>string</Earn_Most_Channel><Source_Of_Business>string</Source_Of_Business>
<Initial_Investment>int</Initial_Investment><isKnowledge_Loan_Process>int</isKnowledge_Loan_Process><User_Id>int</User_Id>
<Entreprenuer_Slno>long</Entreprenuer_Slno><Applied_Loan_Amount>int</Applied_Loan_Amount><Sanctioned_Loan_Amount>int</Sanctioned_Loan_Amount>
<Interest_Rate>double</Interest_Rate><Repayment_Period>int</Repayment_Period><Applied_At>string</Applied_At><Loan_For_Product_Improvement>int</Loan_For_Product_Improvement>
<Loan_For_Working_Expenses>int</Loan_For_Working_Expenses><Loan_For_Land>int</Loan_For_Land><Loan_For_Equipment>int</Loan_For_Equipment>
<Loan_For_Finance_Trading>int</Loan_For_Finance_Trading><Permanent_Employees>string</Permanent_Employees><TurnOver>string</TurnOver>
<Profit>string</Profit><Sell_Product1>string</Sell_Product1><Sell_Product2>string</Sell_Product2><Sell_Product3>string</Sell_Product3>
 */
    public void DBCreate_AddApplicationDetails_insert_2SQLiteDB(ArrayList<Class_AddApplicationDetails> str_addenquirys)
    {
        SQLiteDatabase db_addEnquiry = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);

        db_addEnquiry.execSQL("CREATE TABLE IF NOT EXISTS AddApplicationDetailsNew(FName VARCHAR,MName VARCHAR,LName VARCHAR,MobileNo VARCHAR," +
                "EmailId VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,BusinessName VARCHAR," +
                "DeviceType VARCHAR,UserId VARCHAR,EnquiryId VARCHAR,Gender VARCHAR,Economic VARCHAR,whatsappNo VARCHAR,DOB VARCHAR,education VARCHAR, " +
                "socialCatgary VARCHAR, businessAddress VARCHAR,yearInBusiness VARCHAR,yearInCurrentBusiness VARCHAR,sectorBusiness VARCHAR," +
                "gottoknow VARCHAR,UNDP VARCHAR,yearUNDP VARCHAR,navodyami VARCHAR,yearNavodyami VARCHAR,aadhar VARCHAR,street VARCHAR,Application_Date VARCHAR,Application_Slno VARCHAR);");

        String Application_Slno,FName,LName,MName,MobileNo,EmailId,BusinessName,stateId,districtId,talukId,villegeId,sectorId,deviceType,userId,EnquiryId,Gender,Application_Date;

        String Economic,whatsappNo,DOB,education, socialCatgary, businessAddress,yearInBusiness,yearInCurrentBusiness,sectorBusiness,gottoknow,UNDP,yearUNDP,navodyami,yearNavodyami,aadhar,street;

        for(int s=0;s<str_addenquirys.size();s++) {
            Log.e("tag","BusinessName="+str_addenquirys.get(s).getBusinessName()+"Fname="+str_addenquirys.get(s).getFName());

            FName=str_addenquirys.get(s).getFName();
            MName=str_addenquirys.get(s).getMName();
            LName=str_addenquirys.get(s).getLName();
            MobileNo=str_addenquirys.get(s).getMobileNo();
            EmailId=str_addenquirys.get(s).getEmailId();
            BusinessName=str_addenquirys.get(s).getBusinessName();
            stateId=str_addenquirys.get(s).getStateId();
            districtId=str_addenquirys.get(s).getDistrictId();
            talukId=str_addenquirys.get(s).getTalukaI();
            villegeId=str_addenquirys.get(s).getVillageId();
            sectorId=str_addenquirys.get(s).getSectorId();
            deviceType=str_addenquirys.get(s).getDeviceType();
            userId=str_addenquirys.get(s).getUserId();
            EnquiryId=str_addenquirys.get(s).getEnquiryId();
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
            yearUNDP =str_addenquirys.get(s).getYearUNDP();
            navodyami=str_addenquirys.get(s).getNavodyami();
            yearNavodyami = str_addenquirys.get(s).getYearNavodyami();
            aadhar = str_addenquirys.get(s).getAadhar();
            street=str_addenquirys.get(s).getStreet();
            Application_Date = str_addenquirys.get(s).getApplication_Date();
            Application_Slno = str_addenquirys.get(s).getApplication_Slno();
            Log.e("tag","Enqury No="+EnquiryId);
            Log.e("tag","talukId="+talukId);
            Log.e("tag","Application_Slno insert="+Application_Slno);

           /* SQLiteDatabase db1 = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);

            db1.execSQL("CREATE TABLE IF NOT EXISTS AddApplicationDetails(FName VARCHAR,MName VARCHAR,LName VARCHAR,MobileNo VARCHAR," +
                    "EmailId VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,BusinessName VARCHAR," +
                    "DeviceType VARCHAR,UserId VARCHAR,EnquiryId VARCHAR,Gender VARCHAR,Economic VARCHAR,whatsappNo VARCHAR,DOB VARCHAR,education VARCHAR, " +
                    "socialCatgary VARCHAR, businessAddress VARCHAR,yearInBusiness VARCHAR,yearInCurrentBusiness VARCHAR,sectorBusiness VARCHAR," +
                    "gottoknow VARCHAR,UNDP VARCHAR,yearUNDP VARCHAR,navodyami VARCHAR,yearNavodyami VARCHAR);");*/

            Cursor cursor1 = db_addEnquiry.rawQuery("SELECT DISTINCT * FROM AddApplicationDetailsNew WHERE EnquiryId='"+EnquiryId+"'", null);
            int x = cursor1.getCount();
            Log.d("tag","cursor Application Count"+ Integer.toString(x));
            if(x==0) {
                String SQLiteQuery = "INSERT INTO AddApplicationDetailsNew (FName, MName,LName,MobileNo,EmailId,StateId,DistrictId,TalukaId,VillageId," +
                        "SectorId,BusinessName,DeviceType,UserId,EnquiryId,Gender,Economic,whatsappNo,DOB,education, socialCatgary, businessAddress," +
                        "yearInBusiness,yearInCurrentBusiness,sectorBusiness,gottoknow,UNDP,yearUNDP,navodyami,yearNavodyami,aadhar,street,Application_Date,Application_Slno)" +
                        " VALUES ('" + FName + "','" + MName + "','" + LName + "','" + MobileNo + "','" + EmailId + "','" + stateId + "','" +
                        districtId + "','" + talukId + "','" + villegeId + "','" + sectorId + "','" + BusinessName + "','" + deviceType + "','" +
                        userId + "','" + EnquiryId + "','" + Gender + "','" + Economic + "','" + whatsappNo + "','" + DOB + "','" + education + "','" +
                        socialCatgary + "','" + businessAddress + "','" + yearInBusiness + "','" + yearInCurrentBusiness + "','" +
                        sectorBusiness + "','" + gottoknow + "','" + UNDP + "','" + yearUNDP + "','" + navodyami + "','" + yearNavodyami +"','" + aadhar +"','" + street +"','" + Application_Date + "','" + Application_Slno +"');";
                db_addEnquiry.execSQL(SQLiteQuery);
                //   db_addEnquiry.close();

            }else{
               /* String strSQL = "UPDATE AddApplicationDetails SET FName = FName,MName=MName,LName=LName,MobileNo=MobileNo,EmailId=EmailId,StateId=stateId,DistrictId=districtId,TalukaId=talukId,VillageId=villegeId,SectorId=sectorId,BusinessName=BusinessName,DeviceType=deviceType,UserId=userId,EnquiryId=EnquiryId,Gender=Gender,Economic=Economic,whatsappNo=whatsappNo,DOB=DOB,education=education, socialCatgary=socialCatgary, businessAddress=businessAddress,yearInBusiness=yearInBusiness,yearInCurrentBusiness=yearInCurrentBusiness,sectorBusiness=sectorBusiness,gottoknow=gottoknow,UNDP=UNDP,yearUNDP=yearUNDP,navodyami=navodyami,yearNavodyami=yearNavodyami WHERE EnquiryId = "+ EnquiryId;
                db_addEnquiry.execSQL(strSQL);*/

                String fname=FName;
                String str_talukid=talukId;
                //  String str_villageId="570686";


                ContentValues cv = new ContentValues();
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
                cv.put("EnquiryId",EnquiryId);
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
                cv.put("Application_Slno",Application_Slno);

                db_addEnquiry.update("AddApplicationDetailsNew", cv, "EnquiryId = ?", new String[]{EnquiryId});
                //     db_addEnquiry.close();
            }
        }

        // ApplicationCount();


        db_addEnquiry.close();
    }
    public void DBCreate_AddApplicationTwoDetails_insert_2SQLiteDB(ArrayList<Class_AddApplicationTwoDetails> str_addenquirys)
    {
        SQLiteDatabase db_addApplicationtwo = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);

        db_addApplicationtwo.execSQL("CREATE TABLE IF NOT EXISTS AddApplicationDetailsTwo(Manufacture VARCHAR,Licence VARCHAR,WhichLicence VARCHAR,ProductOne VARCHAR," +
                "ProductTwo VARCHAR,ProductThree VARCHAR,BusinessYear VARCHAR,Ownership VARCHAR,YearOne VARCHAR,Female_year1 VARCHAR,Male_year1 VARCHAR," +
                "YearTwo VARCHAR,Female_year2 VARCHAR,Male_year2 VARCHAR,YearThree VARCHAR,Female_year3 VARCHAR,Male_year3 VARCHAR,Labour VARCHAR,OutsidefamilyLabour VARCHAR, " +
                "Trunover_year1 VARCHAR,Trunover_year2 VARCHAR,Trunover_year3 VARCHAR,Profit_year1 VARCHAR,Profit_year2 VARCHAR,Profit_year3 VARCHAR,Which_Machine VARCHAR,Machine VARCHAR,Sell_products VARCHAR,Last_quarter VARCHAR,EnquiryId VARCHAR,Total_Employee VARCHAR,Application_Slno VARCHAR);");

     /*   String FName,LName,MName,MobileNo,EmailId,BusinessName,stateId,districtId,talukId,villegeId,sectorId,deviceType,userId,EnquiryId,Gender;

        String Economic,whatsappNo,DOB,education, socialCatgary, businessAddress,yearInBusiness,yearInCurrentBusiness,sectorBusiness,gottoknow,UNDP,yearUNDP,navodyami,yearNavodyami;
*/
        String manufacture, licence, whichLicence, productOne, productTwo, productThree, businessYear, ownership;
        String yearOne, female_year1, male_year1, yearTwo, female_year2, male_year2, yearThree, female_year3;
        String male_year3, labour, outsidefamilyLabour, trunover_year1, trunover_year2, trunover_year3, profit_year1;
        String profit_year2, profit_year3, machine, sell_products, last_quarter,enquiryId,total_Employee,Application_Slno,Which_Machine;

        for(int s=0;s<str_addenquirys.size();s++) {
            //    Log.e("tag","BusinessName="+str_addenquirys.get(s).getBusinessName()+"Fname="+str_addenquirys.get(s).getFName());

            manufacture=str_addenquirys.get(s).getManufacture();
            licence=str_addenquirys.get(s).getLicence();
            whichLicence=str_addenquirys.get(s).getWhichLicence();
            productOne=str_addenquirys.get(s).getProductOne();
            productTwo=str_addenquirys.get(s).getProductTwo();
            productThree=str_addenquirys.get(s).getProductThree();
            businessYear=str_addenquirys.get(s).getBusinessYear();
            total_Employee=str_addenquirys.get(s).getTotal_Employee();
            ownership=str_addenquirys.get(s).getOwnership();
            yearOne=str_addenquirys.get(s).getYearOne();
            female_year1=str_addenquirys.get(s).getFemale_year1();
            male_year1=str_addenquirys.get(s).getMale_year1();
            yearTwo=str_addenquirys.get(s).getYearTwo();
            female_year2=str_addenquirys.get(s).getFemale_year2();
            male_year2=str_addenquirys.get(s).getMale_year2();
            yearThree = str_addenquirys.get(s).getYearThree();
            female_year3 = str_addenquirys.get(s).getFemale_year3();
            male_year3 = str_addenquirys.get(s).getMale_year3();
            labour = str_addenquirys.get(s).getLabour();
            outsidefamilyLabour = str_addenquirys.get(s).getOutsidefamilyLabour();
            trunover_year1 = str_addenquirys.get(s).getTrunover_year1();
            trunover_year2 = str_addenquirys.get(s).getTrunover_year2();
            trunover_year3 = str_addenquirys.get(s).getTrunover_year3();
            profit_year1 = str_addenquirys.get(s).getProfit_year1();
            profit_year2 = str_addenquirys.get(s).getProfit_year2();
            profit_year3 = str_addenquirys.get(s).getProfit_year3();
            machine = str_addenquirys.get(s).getMachine();
            sell_products =str_addenquirys.get(s).getSell_products();
            last_quarter=str_addenquirys.get(s).getLast_quarter();
            enquiryId = str_addenquirys.get(s).getEnquiryId();
            Application_Slno = str_addenquirys.get(s).getApplication_Slno();
            Which_Machine = str_addenquirys.get(s).getWhichmachine();

          /*  Log.e("tag","Enqury No="+EnquiryId);
            Log.e("tag","talukId="+talukId);*/

           /* SQLiteDatabase db1 = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);

            db1.execSQL("CREATE TABLE IF NOT EXISTS AddApplicationDetails(FName VARCHAR,MName VARCHAR,LName VARCHAR,MobileNo VARCHAR," +
                    "EmailId VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,BusinessName VARCHAR," +
                    "DeviceType VARCHAR,UserId VARCHAR,EnquiryId VARCHAR,Gender VARCHAR,Economic VARCHAR,whatsappNo VARCHAR,DOB VARCHAR,education VARCHAR, " +
                    "socialCatgary VARCHAR, businessAddress VARCHAR,yearInBusiness VARCHAR,yearInCurrentBusiness VARCHAR,sectorBusiness VARCHAR," +
                    "gottoknow VARCHAR,UNDP VARCHAR,yearUNDP VARCHAR,navodyami VARCHAR,yearNavodyami VARCHAR);");*/

            Cursor cursor1 = db_addApplicationtwo.rawQuery("SELECT DISTINCT * FROM AddApplicationDetailsTwo WHERE EnquiryId ='"+enquiryId+"'", null);
            int x = cursor1.getCount();
            Log.d("tag","cursor Application two Count"+ Integer.toString(x));
            if(x==0) {
                String SQLiteQuery = "INSERT INTO AddApplicationDetailsTwo (Manufacture, Licence,WhichLicence,ProductOne,ProductTwo,ProductThree,BusinessYear,Ownership," +
                        "YearOne,Female_year1,Male_year1,YearTwo,Female_year2,Male_year2,YearThree,Female_year3,Male_year3,Labour,OutsidefamilyLabour, Trunover_year1, Trunover_year2," +
                        "Trunover_year3,Profit_year1,Profit_year2,Profit_year3,Which_Machine,Machine,Sell_products,Last_quarter,EnquiryId,Total_Employee,Application_Slno)" +
                        " VALUES ('" + manufacture + "','" + licence + "','" + whichLicence + "','" + productOne + "','" + productTwo + "','" + productThree + "','" +
                        businessYear + "','" + ownership + "','" + yearOne + "','" + female_year1 + "','" + male_year1 + "','" + yearTwo + "','" +
                        female_year2 + "','" + male_year2 + "','" + yearThree + "','" + female_year3 + "','" + male_year3 + "','" + labour + "','" + outsidefamilyLabour + "','" +
                        trunover_year1 + "','" + trunover_year2 + "','" + trunover_year3 + "','" + profit_year1 + "','" +
                        profit_year2 + "','" + profit_year3 +"','" + Which_Machine+ "','" + machine + "','" + sell_products + "','" + last_quarter+ "','" + enquiryId + "','"+ total_Employee+"','"+ Application_Slno+"');";
                db_addApplicationtwo.execSQL(SQLiteQuery);
                //   db_addEnquiry.close();

            }else{
               /* String strSQL = "UPDATE AddApplicationDetails SET FName = FName,MName=MName,LName=LName,MobileNo=MobileNo,EmailId=EmailId,StateId=stateId,DistrictId=districtId,TalukaId=talukId,VillageId=villegeId,SectorId=sectorId,BusinessName=BusinessName,DeviceType=deviceType,UserId=userId,EnquiryId=EnquiryId,Gender=Gender,Economic=Economic,whatsappNo=whatsappNo,DOB=DOB,education=education, socialCatgary=socialCatgary, businessAddress=businessAddress,yearInBusiness=yearInBusiness,yearInCurrentBusiness=yearInCurrentBusiness,sectorBusiness=sectorBusiness,gottoknow=gottoknow,UNDP=UNDP,yearUNDP=yearUNDP,navodyami=navodyami,yearNavodyami=yearNavodyami WHERE EnquiryId = "+ EnquiryId;
                db_addEnquiry.execSQL(strSQL);*/

              /*  String fname=FName;
                String str_talukid=talukId;
                String str_villageId="570686";*/
                /*String strSQL = "UPDATE AddApplicationDetailsNew SET FName=fname, MName=MName,LName=LName,MobileNo=MobileNo,EmailId=EmailId,StateId=stateId," +
                        "DistrictId=districtId,TalukaId='4357',VillageId=str_villageId,SectorId=sectorId,BusinessName=BusinessName,DeviceType=deviceType," +
                        "UserId=userId,EnquiryId=EnquiryId,Gender=Gender,Economic=Economic,whatsappNo=whatsappNo,DOB=DOB,education=education, " +
                        "socialCatgary=socialCatgary, businessAddress=businessAddress,yearInBusiness=yearInBusiness," +
                        "yearInCurrentBusiness=yearInCurrentBusiness,sectorBusiness=sectorBusiness,gottoknow=gottoknow,UNDP=UNDP,yearUNDP=yearUNDP," +
                        "navodyami=navodyami,yearNavodyami = yearNavodyami WHERE EnquiryId = "+ EnquiryId;

                db_addEnquiry.execSQL(strSQL); */

                ContentValues cv = new ContentValues();
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
                cv.put("EnquiryId",enquiryId);
                cv.put("Total_Employee",total_Employee);
                cv.put("Application_Slno",Application_Slno);

                db_addApplicationtwo.update("AddApplicationDetailsTwo", cv, "EnquiryId = ?", new String[]{EnquiryId});
                //     db_addEnquiry.close();



            }
        }

        // ApplicationCount();


        db_addApplicationtwo.close();
    }
    /*  public void DBCreate_AddApplicationThreeDetails_insert_2SQLiteDB(ArrayList<Class_AddApplicationThreeDetails> str_addapplication)
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
          String Repaymentperiod,Application_Slno,ApplicationFees,VerifiedDate,Remark,Manual_Receipt_No,Payment_Mode;

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
              Manual_Receipt_No=str_addapplication.get(s).getManual_Receipt_No();
              Payment_Mode = str_addapplication.get(s).getPayment_Mode();

              Log.e("tag","BusinessSource DB="+BusinessSource);
              Log.e("tag","LastLoan DB="+LastLoan);

              Cursor cursor1 = db_addApplicationtwo.rawQuery("SELECT DISTINCT * FROM AddApplicationDetailsThree WHERE EnquiryId ='"+EnquiryId+"'", null);
              int x = cursor1.getCount();
              Log.d("tag","cursor Application three Count"+ Integer.toString(x));
              if(x==0) {
                  String SQLiteQuery = "INSERT INTO AddApplicationDetailsThree (BusinessSource, LastLoan,Investment,Knowledge,AppliedAmt,SanctionedAmt,InterestRate,AppliedAt,EnquiryId,Repaymentperiod,Application_Slno,ApplicationFees,VerifiedDate,Remark,Manual_Receipt_No,Payment_Mode)" +
                          " VALUES ('" + BusinessSource + "','" + LastLoan + "','" + Investment + "','" + Knowledge + "','" + AppliedAmt + "','" + SanctionedAmt + "','" +
                          InterestRate + "','" + AppliedAt + "','" + EnquiryId  + "','" + Repaymentperiod+"','" + Application_Slno +"','" + ApplicationFees+"','" + VerifiedDate+"','" + Remark+"','" + Manual_Receipt_No+"','" + Payment_Mode+"');";
                  db_addApplicationtwo.execSQL(SQLiteQuery);
                  //   db_addEnquiry.close();

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
                  cv.put("Manual_Receipt_No",Manual_Receipt_No);
                  cv.put("Payment_Mode",Payment_Mode);

                  db_addApplicationtwo.update("AddApplicationDetailsThree", cv, "EnquiryId = ?", new String[]{EnquiryId});
                  //     db_addEnquiry.close();
              }
          }

          // ApplicationCount();


          db_addApplicationtwo.close();
      }*/
    public void DBCreate_ListApplicationDetails_2SQLiteDB(ArrayList<Class_AddApplicationDetails> str_addenquirys,ArrayList<Class_AddApplicationTwoDetails> str_addenquirystwo,ArrayList<Class_AddApplicationThreeDetails> str_addapplicationthree) {
        SQLiteDatabase db_addApplication = openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_addApplication.execSQL("CREATE TABLE IF NOT EXISTS CompliteApplicationDetails(tempId INTEGER PRIMARY KEY AUTOINCREMENT,EnquiryId VARCHAR,Application_Slno VARCHAR,Academic_Year VARCHAR,dataSyncStatus VARCHAR,FName VARCHAR,MName VARCHAR,LName VARCHAR,MobileNo VARCHAR," +
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

        //    String Business_Name,Application_Slno,First_Name,Mobile_No,Status,YearCode,Application_Fees,dataSyncStatus,Enquiry_Id;

        String Application_Slno,EnquiryId,FName,LName,MName,MobileNo,EmailId,BusinessName,stateId,districtId,talukId,villegeId,sectorId,deviceType,userId,Gender,Application_Date,Academic_Year;
        String Economic,whatsappNo,DOB,education, socialCatgary, businessAddress,yearInBusiness,yearInCurrentBusiness,sectorBusiness,gottoknow,UNDP,yearUNDP,navodyami,yearNavodyami,aadhar,street;

        String manufacture, licence, whichLicence, productOne, productTwo, productThree, businessYear, ownership;
        String yearOne, female_year1, male_year1, yearTwo, female_year2, male_year2, yearThree, female_year3;
        String male_year3, labour, outsidefamilyLabour, trunover_year1, trunover_year2, trunover_year3, profit_year1;
        String profit_year2, profit_year3, machine, sell_products, last_quarter,total_Employee,Which_Machine;

        String BusinessSource,LastLoan,Investment,Knowledge,AppliedAmt,SanctionedAmt,InterestRate,AppliedAt;
        String Repaymentperiod,ApplicationFees,VerifiedDate,Remark,Manual_Receipt_No,Payment_Mode,dataSyncStatus,IsAccountVerified;

        for(int s=0;s<str_addenquirys.size();s++) {
/*

            Business_Name=str_listApplication.get(s).getBusiness_Name();
            Application_Slno=str_listApplication.get(s).getApplication_Slno();
            First_Name=str_listApplication.get(s).getFirst_Name();
            Mobile_No=str_listApplication.get(s).getMobile_No();
            Status = str_listApplication.get(s).getStatus();
            YearCode = str_listApplication.get(s).getYearCode();
            Application_Fees =str_listApplication.get(s).getApplFees();
            dataSyncStatus = str_listApplication.get(s).getDataSyncStatus();
            Enquiry_Id= str_listApplication.get(s).getEnquiry_Id();*/

            EnquiryId=str_addenquirys.get(s).getEnquiryId();
            Application_Slno=str_addenquirys.get(s).getApplication_Slno();
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
            Log.e("tag","labour="+labour);
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
          /*  manufacture=str_addenquirystwo.get(s).getManufacture();
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
            Log.e("tag","labour="+labour);
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
            dataSyncStatus=str_addapplicationthree.get(s).getDataSyncStatus();*/

         /* if(Application_Slno.equalsIgnoreCase("0")&&EnquiryId.equalsIgnoreCase("0")){
              Application_Slno= String.valueOf(s);
          }*/

              /*  String SQLiteQuery = "INSERT INTO CompliteApplicationDetails (EnquiryId,Application_Slno,dataSyncStatus,First_Name,Business_Name,Mobile_No,Status,YearCode,Application_Fees,dataSyncStatus)" +
                        " VALUES ('" + Enquiry_Id + "','" + Application_Slno +"','" + dataSyncStatus + "','" + First_Name + "','" + Business_Name + "','" + Mobile_No + "','" + Status + "','" + YearCode + "','" + Application_Fees + "');";
                db_addApplication.execSQL(SQLiteQuery);
*/
            Log.e("tag","EnquiryId="+EnquiryId+"Application_Slno="+Application_Slno+"tempId="+tempId);
            Log.e("tag","Appliedat onee="+AppliedAt);

            Cursor cursor1 = db_addApplication.rawQuery("SELECT DISTINCT * FROM CompliteApplicationDetails WHERE EnquiryId='" + EnquiryId + "' AND Application_Slno='"+Application_Slno +"' AND tempId='"+tempId +"'", null);
            int x = cursor1.getCount();
            Log.e("tag","cursor persnoal count x="+x);
            if(x==0) {
              /*  if(Application_Slno.equalsIgnoreCase("0")&&EnquiryId.equalsIgnoreCase("0")) {
                    Cursor cursor2 = db_addApplication.rawQuery("SELECT DISTINCT * FROM CompliteApplicationDetails WHERE tempId='" + tempId + "'", null);
                    int x1 = cursor2.getCount();
                    if(x1==0) {*/
                String SQLiteQuery = "INSERT INTO CompliteApplicationDetails (EnquiryId,Application_Slno,Academic_Year,dataSyncStatus,FName, MName,LName,MobileNo,EmailId,StateId,DistrictId,TalukaId,VillageId," +
                        "SectorId,BusinessName,DeviceType,UserId,Gender,Economic,whatsappNo,DOB,education, socialCatgary, businessAddress," +
                        "yearInBusiness,yearInCurrentBusiness,sectorBusiness,gottoknow,UNDP,yearUNDP,navodyami,yearNavodyami,aadhar,street,Application_Date," +
                        "Manufacture, Licence,WhichLicence,ProductOne,ProductTwo,ProductThree,BusinessYear,Ownership,YearOne,Female_year1,Male_year1,YearTwo," +
                        "Female_year2,Male_year2,YearThree,Female_year3,Male_year3,Labour,OutsidefamilyLabour, Trunover_year1, Trunover_year2," +
                        "Trunover_year3,Profit_year1,Profit_year2,Profit_year3,Which_Machine,Machine,Sell_products,Last_quarter,Total_Employee," +
                        "BusinessSource, LastLoan,Investment,Knowledge,AppliedAmt,SanctionedAmt,InterestRate,AppliedAt,Repaymentperiod,ApplicationFees,VerifiedDate,Remark,Manual_Receipt_No,Payment_Mode,IsAccountVerified)" +
                        "VALUES ('" + EnquiryId + "','" + Application_Slno + "','" + Academic_Year + "','" + dataSyncStatus + "','" + FName + "','" + MName + "','" + LName + "','" + MobileNo + "','" + EmailId + "','" + stateId + "','" +
                        districtId + "','" + talukId + "','" + villegeId + "','" + sectorId + "','" + BusinessName + "','" + deviceType + "','" + userId + "','" + Gender + "','" + Economic + "','" +
                        whatsappNo + "','" + DOB + "','" + education + "','" + socialCatgary + "','" + businessAddress + "','" + yearInBusiness + "','" + yearInCurrentBusiness + "','" +
                        sectorBusiness + "','" + gottoknow + "','" + UNDP + "','" + yearUNDP + "','" + navodyami + "','" + yearNavodyami + "','" + aadhar + "','" + street + "','" + Application_Date +
                        "','" + manufacture + "','" + licence + "','" + whichLicence + "','" + productOne + "','" + productTwo + "','" + productThree + "','" +
                        businessYear + "','" + ownership + "','" + yearOne + "','" + female_year1 + "','" + male_year1 + "','" + yearTwo + "','" +
                        female_year2 + "','" + male_year2 + "','" + yearThree + "','" + female_year3 + "','" + male_year3 + "','" + labour + "','" + outsidefamilyLabour + "','" +
                        trunover_year1 + "','" + trunover_year2 + "','" + trunover_year3 + "','" + profit_year1 + "','" +
                        profit_year2 + "','" + profit_year3 + "','" + Which_Machine + "','" + machine + "','" + sell_products + "','" + last_quarter + "','" + total_Employee + "','" +
                        BusinessSource + "','" + LastLoan + "','" + Investment + "','" + Knowledge + "','" + AppliedAmt + "','" + SanctionedAmt + "','" +
                        InterestRate + "','" + AppliedAt + "','" + Repaymentperiod + "','" + ApplicationFees + "','" + VerifiedDate + "','" + Remark + "','" + Manual_Receipt_No + "','" + Payment_Mode + "','" + IsAccountVerified + "');";

                 /*VALUES ('" + EnquiryId + "','"+ Application_Slno+"','" +Academic_Year+"','" + "0"+"','"+ FName + "','" + MName + "','" + LName + "','" + MobileNo + "','" + EmailId + "','" + stateId + "','" +
                        districtId + "','" + talukId + "','" + villegeId + "','" + sectorId + "','" + BusinessName + "','" + deviceType + "','" + userId  + "','" + Gender + "','" + Economic + "','" +
                        whatsappNo + "','" + DOB + "','" + education + "','" + socialCatgary + "','" + businessAddress + "','" + yearInBusiness + "','" + yearInCurrentBusiness + "','" +
                        sectorBusiness + "','" + gottoknow + "','" + UNDP + "','" + yearUNDP + "','" + navodyami + "','" + yearNavodyami + "','" + aadhar + "','" + street + "','" + Application_Date +
                        "','" + null + "','" + null + "','" + null + "','" + null + "','" + null + "','" + null + "','" +
                        null + "','" + null + "','" + null + "','" + null + "','" + null + "','" + null + "','" +
                        null + "','" + null + "','" + null + "','" + null + "','" + null + "','" + null + "','" + null + "','" +
                        null + "','" + null + "','" + null + "','" + null + "','" +
                        null + "','" + null +"','" + null+ "','" + null + "','" + null + "','" + null + "','"+ null+"','"+
                        null + "','" + null + "','" + null + "','" + null + "','" + null + "','" + null + "','" +
                        null + "','" + null  + "','" + null +"','" + null+"','" + null+"','" + null+"','" + null+"','" + null+"');";
*/
                db_addApplication.execSQL(SQLiteQuery);
            } else{
                ContentValues cv = new ContentValues();
                cv.put("EnquiryId",EnquiryId);
                cv.put("Application_Slno",Application_Slno);
                cv.put("Academic_Year",Academic_Year);
                //cv.put("dataSyncStatus",dataSyncStatus);
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

                db_addApplication.update("CompliteApplicationDetails", cv, "tempId = ?", new String[]{tempId});

            }
        }

        db_addApplication.close();
    }
    public void DBUpdate_ApplicationOneDetails_2SQLiteDB(ArrayList<Class_AddApplicationDetails> str_addenquirys) {
        SQLiteDatabase db_addApplication = openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_addApplication.execSQL("CREATE TABLE IF NOT EXISTS CompliteApplicationDetails(tempId INTEGER PRIMARY KEY AUTOINCREMENT,EnquiryId VARCHAR,Application_Slno VARCHAR,Academic_Year VARCHAR,dataSyncStatus VARCHAR,FName VARCHAR,MName VARCHAR,LName VARCHAR,MobileNo VARCHAR," +
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

        //    String Business_Name,Application_Slno,First_Name,Mobile_No,Status,YearCode,Application_Fees,dataSyncStatus,Enquiry_Id;

        String Application_Slno,EnquiryId,FName,LName,MName,MobileNo,EmailId,BusinessName,stateId,districtId,talukId,villegeId,sectorId,deviceType,userId,Gender,Application_Date,Academic_Year;
        String Economic,whatsappNo,DOB,education, socialCatgary, businessAddress,yearInBusiness,yearInCurrentBusiness,sectorBusiness,gottoknow,UNDP,yearUNDP,navodyami,yearNavodyami,aadhar,street;

        String manufacture, licence, whichLicence, productOne, productTwo, productThree, businessYear, ownership;
        String yearOne, female_year1, male_year1, yearTwo, female_year2, male_year2, yearThree, female_year3;
        String male_year3, labour, outsidefamilyLabour, trunover_year1, trunover_year2, trunover_year3, profit_year1;
        String profit_year2, profit_year3, machine, sell_products, last_quarter,total_Employee,Which_Machine;

        String BusinessSource,LastLoan,Investment,Knowledge,AppliedAmt,SanctionedAmt,InterestRate,AppliedAt;
        String Repaymentperiod,ApplicationFees,VerifiedDate,Remark,Manual_Receipt_No,Payment_Mode,dataSyncStatus;

        for(int s=0;s<str_addenquirys.size();s++) {
/*

            Business_Name=str_listApplication.get(s).getBusiness_Name();
            Application_Slno=str_listApplication.get(s).getApplication_Slno();
            First_Name=str_listApplication.get(s).getFirst_Name();
            Mobile_No=str_listApplication.get(s).getMobile_No();
            Status = str_listApplication.get(s).getStatus();
            YearCode = str_listApplication.get(s).getYearCode();
            Application_Fees =str_listApplication.get(s).getApplFees();
            dataSyncStatus = str_listApplication.get(s).getDataSyncStatus();
            Enquiry_Id= str_listApplication.get(s).getEnquiry_Id();*/

            EnquiryId=str_addenquirys.get(s).getEnquiryId();
            Application_Slno=str_addenquirys.get(s).getApplication_Slno();
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

          /*  manufacture=str_addenquirystwo.get(s).getManufacture();
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
            Log.e("tag","labour="+labour);
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
            dataSyncStatus=str_addapplicationthree.get(s).getDataSyncStatus();*/
          /*  manufacture=str_addenquirystwo.get(s).getManufacture();
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
            Log.e("tag","labour="+labour);
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
            dataSyncStatus=str_addapplicationthree.get(s).getDataSyncStatus();*/

         /* if(Application_Slno.equalsIgnoreCase("0")&&EnquiryId.equalsIgnoreCase("0")){
              Application_Slno= String.valueOf(s);
          }*/

              /*  String SQLiteQuery = "INSERT INTO CompliteApplicationDetails (EnquiryId,Application_Slno,dataSyncStatus,First_Name,Business_Name,Mobile_No,Status,YearCode,Application_Fees,dataSyncStatus)" +
                        " VALUES ('" + Enquiry_Id + "','" + Application_Slno +"','" + dataSyncStatus + "','" + First_Name + "','" + Business_Name + "','" + Mobile_No + "','" + Status + "','" + YearCode + "','" + Application_Fees + "');";
                db_addApplication.execSQL(SQLiteQuery);
*/
            Log.e("tag","App One ->EnquiryId="+EnquiryId+"Application_Slno="+Application_Slno+"tempId="+tempId);
            Cursor cursor1 = db_addApplication.rawQuery("SELECT DISTINCT * FROM CompliteApplicationDetails WHERE EnquiryId='" + EnquiryId + "' AND Application_Slno='"+Application_Slno +"' AND tempId='"+tempId +"'", null);
            int x = cursor1.getCount();
            Log.e("tag","cursor persnoal count x="+x);
            if(x==0) {
              /*  if(Application_Slno.equalsIgnoreCase("0")&&EnquiryId.equalsIgnoreCase("0")) {
                    Cursor cursor2 = db_addApplication.rawQuery("SELECT DISTINCT * FROM CompliteApplicationDetails WHERE tempId='" + tempId + "'", null);
                    int x1 = cursor2.getCount();
                    if(x1==0) {*/
                String SQLiteQuery = "INSERT INTO CompliteApplicationDetails (EnquiryId,Application_Slno,Academic_Year,dataSyncStatus,FName, MName,LName,MobileNo,EmailId,StateId,DistrictId,TalukaId,VillageId," +
                        "SectorId,BusinessName,DeviceType,UserId,Gender,Economic,whatsappNo,DOB,education, socialCatgary, businessAddress," +
                        "yearInBusiness,yearInCurrentBusiness,sectorBusiness,gottoknow,UNDP,yearUNDP,navodyami,yearNavodyami,aadhar,street,Application_Date," +
                        "Manufacture, Licence,WhichLicence,ProductOne,ProductTwo,ProductThree,BusinessYear,Ownership,YearOne,Female_year1,Male_year1,YearTwo," +
                        "Female_year2,Male_year2,YearThree,Female_year3,Male_year3,Labour,OutsidefamilyLabour, Trunover_year1, Trunover_year2," +
                        "Trunover_year3,Profit_year1,Profit_year2,Profit_year3,Which_Machine,Machine,Sell_products,Last_quarter,Total_Employee," +
                        "BusinessSource, LastLoan,Investment,Knowledge,AppliedAmt,SanctionedAmt,InterestRate,AppliedAt,Repaymentperiod,ApplicationFees,VerifiedDate,Remark,Manual_Receipt_No,Payment_Mode,IsAccountVerified)" +
                        "VALUES ('" + EnquiryId + "','" + Application_Slno + "','" + Academic_Year + "','" + "offline" + "','" + FName + "','" + MName + "','" + LName + "','" + MobileNo + "','" + EmailId + "','" + stateId + "','" +
                        districtId + "','" + talukId + "','" + villegeId + "','" + sectorId + "','" + BusinessName + "','" + deviceType + "','" + userId + "','" + Gender + "','" + Economic + "','" +
                        whatsappNo + "','" + DOB + "','" + education + "','" + socialCatgary + "','" + businessAddress + "','" + yearInBusiness + "','" + yearInCurrentBusiness + "','" +
                        sectorBusiness + "','" + gottoknow + "','" + UNDP + "','" + yearUNDP + "','" + navodyami + "','" + yearNavodyami + "','" + aadhar + "','" + street + "','" + Application_Date +
                        "','" + "0" + "','" + "0" + "','" + "" + "','" + "" + "','" + "" + "','" + "" + "','" +
                        "" + "','" + "" + "','" + "" + "','" + "" + "','" + "" + "','" + "" + "','" +
                        "" + "','" + "" + "','" + "" + "','" + "" + "','" + "" + "','" + "0" + "','" + "0" + "','" +
                        "" + "','" + "" + "','" + "" + "','" + "" + "','" +
                        "" + "','" + "" + "','" + "" + "','" + "0" + "','" + "" + "','" + "" + "','" + "" + "','" +
                        "" + "','" + "0,0,0,0,0" + "','" + "" + "','" + "0" + "','" + "" + "','" + "" + "','" +
                        "" + "','" + "" + "','" + "" + "','" + "0" + "','" + "" + "','" + "" + "','" + "" + "','" + "" + "','" + "" + "');";
                /*VALUES ('" + EnquiryId + "','"+ Application_Slno+"','" +Academic_Year+"','" + "0"+"','"+ FName + "','" + MName + "','" + LName + "','" + MobileNo + "','" + EmailId + "','" + stateId + "','" +
                        districtId + "','" + talukId + "','" + villegeId + "','" + sectorId + "','" + BusinessName + "','" + deviceType + "','" + userId  + "','" + Gender + "','" + Economic + "','" +
                        whatsappNo + "','" + DOB + "','" + education + "','" + socialCatgary + "','" + businessAddress + "','" + yearInBusiness + "','" + yearInCurrentBusiness + "','" +
                        sectorBusiness + "','" + gottoknow + "','" + UNDP + "','" + yearUNDP + "','" + navodyami + "','" + yearNavodyami + "','" + aadhar + "','" + street + "','" + Application_Date +
                        "','" + null + "','" + null + "','" + null + "','" + null + "','" + null + "','" + null + "','" +
                        null + "','" + null + "','" + null + "','" + null + "','" + null + "','" + null + "','" +
                        null + "','" + null + "','" + null + "','" + null + "','" + null + "','" + null + "','" + null + "','" +
                        null + "','" + null + "','" + null + "','" + null + "','" +
                        null + "','" + null +"','" + null+ "','" + null + "','" + null + "','" + null + "','"+ null+"','"+
                        null + "','" + null + "','" + null + "','" + null + "','" + null + "','" + null + "','" +
                        null + "','" + null  + "','" + null +"','" + null+"','" + null+"','" + null+"','" + null+"','" + null+"');";
*/
                db_addApplication.execSQL(SQLiteQuery);
            } else{
                ContentValues cv = new ContentValues();
                cv.put("EnquiryId",EnquiryId);
                cv.put("Application_Slno",Application_Slno);
                cv.put("Academic_Year",Academic_Year);
                //cv.put("dataSyncStatus",dataSyncStatus);
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

                db_addApplication.update("CompliteApplicationDetails", cv, "tempId = ?", new String[]{tempId});

            }
        }

        db_addApplication.close();
    }

    public void get_TempIdData() {
        SQLiteDatabase db_addApplication = openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_addApplication.execSQL("CREATE TABLE IF NOT EXISTS CompliteApplicationDetails(tempId INTEGER PRIMARY KEY AUTOINCREMENT,EnquiryId VARCHAR,Application_Slno VARCHAR,Academic_Year VARCHAR,dataSyncStatus VARCHAR,FName VARCHAR,MName VARCHAR,LName VARCHAR,MobileNo VARCHAR," +
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

        Cursor cursor1 = db_addApplication.rawQuery("SELECT DISTINCT * FROM CompliteApplicationDetails WHERE EnquiryId ='" + EnquiryId + "' AND Application_Slno='" + ApplicationSlno + "'", null);

        int x = cursor1.getCount();
        Log.d("tag", "cursor Application tempId Count" + Integer.toString(x));

        int i = 0;

        if (cursor1.moveToFirst()) {

            do {

                tempId = cursor1.getString(cursor1.getColumnIndex("tempId"));

                i++;

            } while (cursor1.moveToNext());

        }
    }

    public void RemoveFromEnquiryListDB() {

        SQLiteDatabase db_editEnquiry = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db_editEnquiry.execSQL("CREATE TABLE IF NOT EXISTS ListEnquiryDetails(tempId INTEGER PRIMARY KEY AUTOINCREMENT,FName VARCHAR,MobileNo VARCHAR,EmailId VARCHAR,BusinessName VARCHAR,EnquiryId VARCHAR,Status VARCHAR,YearCode VARCHAR,MName VARCHAR,LName VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,isApplicant VARCHAR,Gender VARCHAR,dataSyncStatus VARCHAR );");
      /*  ContentValues cv = new ContentValues();

        cv.put("isApplicant","1");
        long result =  db_editEnquiry.update("ListEnquiryDetails", cv, "EnquiryId = ?", new String[]{EnquiryId});
       */
        Cursor cursor = db_editEnquiry.rawQuery("SELECT DISTINCT * FROM ListEnquiryDetails WHERE tempId='"+EnquiryTemptId+"'", null);
        int x = cursor.getCount();
        Log.d("tag","cursor delete count ListEnquiryDetails="+ Integer.toString(x));
        if (x != 0) {
            String where="tempId=?";
            //  int result = db_addEnquiry.rawQuery(" Delete from CompliteApplicationDetails where tempId = tempId", null);
            //  int numberOFEntriesDeleted= db_addEnquiry.delete(CompliteApplicationDetails, where, new String[]{tempId}) ;
            int result=db_editEnquiry.delete("ListEnquiryDetails", where, new String[]{EnquiryTemptId});
            Log.e("tag","result isApplicant"+result);
        }

    }
    /* public void DBCreate_PersonalListApplicationDetails_2SQLiteDB(ArrayList<Class_AddApplicationDetails> str_addenquirys) {
         SQLiteDatabase db_addApplication = openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
         db_addApplication.execSQL("CREATE TABLE IF NOT EXISTS CompliteApplicationDetails(tempId INTEGER PRIMARY KEY AUTOINCREMENT,EnquiryId VARCHAR,Application_Slno VARCHAR,Academic_Year VARCHAR,dataSyncStatus VARCHAR,FName VARCHAR,MName VARCHAR,LName VARCHAR,MobileNo VARCHAR," +
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
                 "AppliedAt VARCHAR,Repaymentperiod VARCHAR,ApplicationFees VARCHAR,VerifiedDate VARCHAR,Remark VARCHAR,Manual_Receipt_No VARCHAR,Payment_Mode VARCHAR);");

         String Application_Slno,FName,LName,MName,MobileNo,EmailId,BusinessName,stateId,districtId,talukId,villegeId,sectorId,deviceType,userId,EnquiryId,Gender,Application_Date;

         String Economic,whatsappNo,DOB,education, socialCatgary, businessAddress,yearInBusiness,yearInCurrentBusiness,sectorBusiness,gottoknow,UNDP,yearUNDP,navodyami,yearNavodyami,aadhar,street;

         for(int s=0;s<str_addenquirys.size();s++) {

             FName=str_addenquirys.get(s).getFName();
             MName=str_addenquirys.get(s).getMName();
             LName=str_addenquirys.get(s).getLName();
             MobileNo=str_addenquirys.get(s).getMobileNo();
             EmailId=str_addenquirys.get(s).getEmailId();
             BusinessName=str_addenquirys.get(s).getBusinessName();
             stateId=str_addenquirys.get(s).getStateId();
             districtId=str_addenquirys.get(s).getDistrictId();
             talukId=str_addenquirys.get(s).getTalukaI();
             villegeId=str_addenquirys.get(s).getVillageId();
             sectorId=str_addenquirys.get(s).getSectorId();
             deviceType=str_addenquirys.get(s).getDeviceType();
             userId=str_addenquirys.get(s).getUserId();
             EnquiryId=str_addenquirys.get(s).getEnquiryId();
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
             yearUNDP =str_addenquirys.get(s).getYearUNDP();
             navodyami=str_addenquirys.get(s).getNavodyami();
             yearNavodyami = str_addenquirys.get(s).getYearNavodyami();
             aadhar = str_addenquirys.get(s).getAadhar();
             street=str_addenquirys.get(s).getStreet();
             Application_Date = str_addenquirys.get(s).getApplication_Date();
             Application_Slno = str_addenquirys.get(s).getApplication_Slno();

             Cursor cursor1 = db_addApplication.rawQuery("SELECT DISTINCT * FROM CompliteApplicationDetails WHERE EnquiryId='" + Enquiry_Id + "' AND Application_Slno='"+Application_Slno+"'", null);
             int x = cursor1.getCount();
             if(x==0) {
                 String SQLiteQuery = "INSERT INTO CompliteApplicationDetails (EnquiryId,Application_Slno,First_Name,Business_Name,Mobile_No,Status,YearCode,Application_Fees,dataSyncStatus)" +
                         " VALUES ('" + Enquiry_Id + "','" + Application_Slno + "','" + First_Name + "','" + Business_Name + "','" + Mobile_No + "','" + Status + "','" + YearCode + "','" + Application_Fees +"','" + dataSyncStatus + "');";
                 db_addApplication.execSQL(SQLiteQuery);

             }else{
                 ContentValues cv = new ContentValues();
                 cv.put("Business_Name",Business_Name);
                 cv.put("Application_Slno",Application_Slno);
                 cv.put("First_Name",First_Name);
                 cv.put("Mobile_No",Mobile_No);
                 cv.put("Status",Status);
                 cv.put("YearCode",YearCode);
                 cv.put("Application_Fees",Application_Fees);
                 cv.put("EnquiryId",Enquiry_Id);
                 cv.put("dataSyncStatus",dataSyncStatus);

                 db_addApplication.update("CompliteApplicationDetails", cv, "Enquiry_Id = ?", new String[]{Enquiry_Id});

             }
         }

         db_addApplication.close();
     }
    */
    public void UploadFromDB_AddApplicationDetails(){
        SQLiteDatabase db_addEnquiry = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);

        db_addEnquiry.execSQL("CREATE TABLE IF NOT EXISTS AddApplicationDetailsNew(FName VARCHAR,MName VARCHAR,LName VARCHAR,MobileNo VARCHAR," +
                "EmailId VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,BusinessName VARCHAR," +
                "DeviceType VARCHAR,UserId VARCHAR,EnquiryId VARCHAR,Gender VARCHAR,Economic VARCHAR,whatsappNo VARCHAR,DOB VARCHAR,education VARCHAR, " +
                "socialCatgary VARCHAR, businessAddress VARCHAR,yearInBusiness VARCHAR,yearInCurrentBusiness VARCHAR,sectorBusiness VARCHAR," +
                "gottoknow VARCHAR,UNDP VARCHAR,yearUNDP VARCHAR,navodyami VARCHAR,yearNavodyami VARCHAR,aadhar VARCHAR,street VARCHAR,Application_Date VARCHAR,Application_Slno VARCHAR);");

        String FName,LName,MName,MobileNo,EmailId,BusinessName,stateId,districtId,talukId,villegeId,sectorId,deviceType,userId,Gender;

        String Economic,whatsappNo,DOB,education, socialCatgary, businessAddress,yearInBusiness,yearInCurrentBusiness,sectorBusiness,gottoknow,UNDP,yearUNDP,navodyami,yearNavodyami,aadhar,street,Application_Date;
        Cursor cursor = db_addEnquiry.rawQuery("SELECT DISTINCT * FROM AddApplicationDetailsNew WHERE Application_Slno ='"+ApplicationSlno+"'", null);
        int x = cursor.getCount();
        Log.d("tag","cursor count AddApplicationDetailsTwo"+ Integer.toString(x));

        int i = 0;
        arrayObj_Class_ApplicationDetails = new Class_AddApplicationDetails[x];
        if (cursor.moveToFirst()) {

            do {
                FName = cursor.getString(cursor.getColumnIndex("FName"));
                edt_FirstName.setText(FName);
                MName = cursor.getString(cursor.getColumnIndex("MName"));
                edt_LastName.setText(MName);
                LName = cursor.getString(cursor.getColumnIndex("LName"));
                edt_LastName.setText(LName);
                MobileNo = cursor.getString(cursor.getColumnIndex("MobileNo"));
                edt_MobileNo.setText(MobileNo);
                EmailId = cursor.getString(cursor.getColumnIndex("EmailId"));
                edt_EmailId.setText(EmailId);
                str_StateId = cursor.getString(cursor.getColumnIndex("StateId"));
                str_DistrictId = cursor.getString(cursor.getColumnIndex("DistrictId"));
                str_TalukaId = cursor.getString(cursor.getColumnIndex("TalukaId"));
                str_VillageId = cursor.getString(cursor.getColumnIndex("VillageId"));
                str_SectorId = cursor.getString(cursor.getColumnIndex("SectorId"));
                sel_sector =  Integer.parseInt(str_SectorId.toString());
                spin_Sector.setSelection(sel_sector);
                BusinessName = cursor.getString(cursor.getColumnIndex("BusinessName"));
                edt_BusinessName.setText(BusinessName);
                deviceType = cursor.getString(cursor.getColumnIndex("DeviceType"));
                userId = cursor.getString(cursor.getColumnIndex("UserId"));
                EnquiryId = cursor.getString(cursor.getColumnIndex("EnquiryId"));
                str_gender = cursor.getString(cursor.getColumnIndex("Gender"));
                if(str_gender.equals("M"))
                {
                    gender_radiogroup.check(R.id.rdb_male); }
                else
                { gender_radiogroup.check(R.id.rdb_female); }
                Economic = cursor.getString(cursor.getColumnIndex("Economic"));
                if(Economic.equals("APL"))
                {
                    economic_radiogroup.check(R.id.rdb_apl);
                    str_economic="APL";
                }
                else
                { economic_radiogroup.check(R.id.rdb_bpl);
                    str_economic="BPL";
                }
                whatsappNo = cursor.getString(cursor.getColumnIndex("whatsappNo"));
                edt_whatsappMobileNo.setText(whatsappNo);
                DOB = cursor.getString(cursor.getColumnIndex("DOB"));
                SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat dateFormatDisplay= new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date d=dateFormat.parse(DOB);
                    System.out.println("Formated"+dateFormat.format(d));
                    dobseterror_tv.setVisibility(View.GONE);
                    edt_dob.setText(dateFormatDisplay.format(d).toString());
                    str_dob=dateFormat.format(d);

                }
                catch(Exception e) {
                    System.out.println("Excep"+e);
                }
                education = cursor.getString(cursor.getColumnIndex("education"));
                sel_qulification= Integer.parseInt(education);
                sp_Education.setSelection(sel_qulification);
                socialCatgary = cursor.getString(cursor.getColumnIndex("socialCatgary"));
                sel_social_Category= Integer.parseInt(socialCatgary);
                Catgary_SP.setSelection(sel_social_Category);
                str_catgary=socialCatgary.toString();
                businessAddress = cursor.getString(cursor.getColumnIndex("businessAddress"));
                edt_BusinessAddress.setText(businessAddress);
                yearInBusiness = cursor.getString(cursor.getColumnIndex("yearInBusiness"));
                edt_yearInBusiness.setText(yearInBusiness);
                yearInCurrentBusiness = cursor.getString(cursor.getColumnIndex("yearInCurrentBusiness"));
                edt_yearIncurrentBusiness.setText(yearInCurrentBusiness);
                sectorBusiness = cursor.getString(cursor.getColumnIndex("sectorBusiness"));
                edt_sectorBusiness.setText(sectorBusiness);
                gottoknow = cursor.getString(cursor.getColumnIndex("gottoknow"));
                Log.e("tag","gottoknow="+gottoknow);
                if (gottoknow != null) {
                    sel_gottoknow = dataAdapter_gotknow.getPosition(gottoknow);
                    gotknow_sp.setSelection(sel_gottoknow);
                   // gotknow_sp.setSelection(getIndex(gotknow_sp, gottoknow));
                  //  sel_gottoknow=gotknow_sp.getSelectedItemPosition();
                }
                UNDP = cursor.getString(cursor.getColumnIndex("UNDP"));
                if(UNDP.equals("1"))
                {
                    undp_radiogroup.check(R.id.rdb_UNDPyes);
                    int_UNDP="1";
                    edt_undp.setVisibility(View.VISIBLE);
                }
                else
                { undp_radiogroup.check(R.id.rdb_UNDPno);
                    int_UNDP="0";
                    edt_undp.setVisibility(View.GONE);
                }
                yearUNDP = cursor.getString(cursor.getColumnIndex("yearUNDP"));
                edt_undp.setText(yearUNDP);
                navodyami = cursor.getString(cursor.getColumnIndex("navodyami"));
                if(navodyami.equals("1"))
                {
                    navodyami_radiogroup.check(R.id.rdb_navoyes);
                    int_navo="1";
                    edt_navodyami.setVisibility(View.VISIBLE);
                }
                else
                { navodyami_radiogroup.check(R.id.rdb_navono);
                    int_navo="0";
                    edt_navodyami.setVisibility(View.GONE);
                }
                yearNavodyami = cursor.getString(cursor.getColumnIndex("yearNavodyami"));
                edt_navodyami.setText(yearNavodyami);
                aadhar = cursor.getString(cursor.getColumnIndex("aadhar"));
                edt_Aadhar.setText(aadhar);
                street = cursor.getString(cursor.getColumnIndex("street"));
                edt_Street.setText(street);
                Application_Date = cursor.getString(cursor.getColumnIndex("Application_Date"));
                //   SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
                //   SimpleDateFormat dateFormatDisplay= new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date d=dateFormat.parse(Application_Date);
                    System.out.println("Formated"+dateFormat.format(d));
                    appldate_seterror_TV.setVisibility(View.GONE);
                    edt_appldate.setText(dateFormatDisplay.format(d).toString());
                    str_appldate=dateFormat.format(d);

                }
                catch(Exception e) {
                    System.out.println("Excep"+e);
                }
            } while (cursor.moveToNext());
        }
        uploadfromDB_Statelist();
        uploadfromDB_Sectorlist();
        uploadfromDB_QualificationList();
        uploadfromDB_CatgaryList();
     //   uploadfromDB_KnowNavodyamiList();
    }
    public void UploadFromDB_AddCompliteApplicationOneDetails(){
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


        String FName,LName,MName,MobileNo,EmailId,BusinessName,stateId,districtId,talukId,villegeId,sectorId,deviceType,userId,Gender;

        String Economic,whatsappNo,DOB,education, socialCatgary, businessAddress,yearInBusiness,yearInCurrentBusiness,sectorBusiness,gottoknow,UNDP,yearUNDP,navodyami,yearNavodyami,aadhar,street,Application_Date;
        Cursor cursor = db_addEnquiry.rawQuery("SELECT DISTINCT * FROM CompliteApplicationDetails WHERE tempId='"+tempId +"'", null);
        int x = cursor.getCount();
        Log.d("tag","cursor count AddApplicationDetailsOne"+ Integer.toString(x));

        int i = 0;
        arrayObj_Class_ApplicationDetails = new Class_AddApplicationDetails[x];
        if (cursor.moveToFirst()) {

            do {
                FName = cursor.getString(cursor.getColumnIndex("FName"));
                edt_FirstName.setText(FName);
                MName = cursor.getString(cursor.getColumnIndex("MName"));
                if(MName==null||MName.equals("")||MName.equalsIgnoreCase("null")){
                    edt_MiddleName.setText("");
                }else{
                    edt_MiddleName.setText(MName);
                }
                LName = cursor.getString(cursor.getColumnIndex("LName"));
                if(LName==null||LName.equals("")){
                    edt_LastName.setText("");
                }else{
                    edt_LastName.setText(LName);
                }
                MobileNo = cursor.getString(cursor.getColumnIndex("MobileNo"));
                edt_MobileNo.setText(MobileNo);
                EmailId = cursor.getString(cursor.getColumnIndex("EmailId"));
                if(EmailId==null||EmailId.equalsIgnoreCase("0")||EmailId.equals("")||EmailId.equals("null")){
                    edt_EmailId.setText("");
                }else {
                    edt_EmailId.setText(EmailId);}
                str_StateId = cursor.getString(cursor.getColumnIndex("StateId"));
                str_DistrictId = cursor.getString(cursor.getColumnIndex("DistrictId"));
                str_TalukaId = cursor.getString(cursor.getColumnIndex("TalukaId"));
                str_VillageId = cursor.getString(cursor.getColumnIndex("VillageId"));
                str_SectorId = cursor.getString(cursor.getColumnIndex("SectorId"));
                sel_sector =  Integer.parseInt(str_SectorId.toString());
                spin_Sector.setSelection(sel_sector);
                BusinessName = cursor.getString(cursor.getColumnIndex("BusinessName"));
                edt_BusinessName.setText(BusinessName);
                deviceType = cursor.getString(cursor.getColumnIndex("DeviceType"));
                userId = cursor.getString(cursor.getColumnIndex("UserId"));
                EnquiryId = cursor.getString(cursor.getColumnIndex("EnquiryId"));
                str_gender = cursor.getString(cursor.getColumnIndex("Gender"));
                if(str_gender.equals("M"))
                {
                    gender_radiogroup.check(R.id.rdb_male); }
                else
                { gender_radiogroup.check(R.id.rdb_female); }
                Economic = cursor.getString(cursor.getColumnIndex("Economic"));
                if(Economic.equals("APL"))
                {
                    economic_radiogroup.check(R.id.rdb_apl);
                    str_economic="APL";
                }
                else
                { economic_radiogroup.check(R.id.rdb_bpl);
                    str_economic="BPL";
                }
                whatsappNo = cursor.getString(cursor.getColumnIndex("whatsappNo"));
                Log.e("tag","whatsappNo="+whatsappNo);
                if(whatsappNo==null||whatsappNo.equalsIgnoreCase("0")||whatsappNo.equals("")||whatsappNo.equals("null")){
                    edt_whatsappMobileNo.setText("");
                }else {
                    edt_whatsappMobileNo.setText(whatsappNo);}
                DOB = cursor.getString(cursor.getColumnIndex("DOB"));
                SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat dateFormatDisplay= new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date d=dateFormat.parse(DOB);
                    System.out.println("Formated"+dateFormat.format(d));
                    dobseterror_tv.setVisibility(View.GONE);
                    edt_dob.setText(dateFormatDisplay.format(d).toString());
                    str_dob=dateFormat.format(d);

                }
                catch(Exception e) {
                    System.out.println("Excep"+e);
                }
                education = cursor.getString(cursor.getColumnIndex("education"));
                sel_qulification= Integer.parseInt(education);
                sp_Education.setSelection(sel_qulification);
                socialCatgary = cursor.getString(cursor.getColumnIndex("socialCatgary"));
                sel_social_Category= Integer.parseInt(socialCatgary);
                Catgary_SP.setSelection(sel_social_Category);
                str_catgary=socialCatgary.toString();
                businessAddress = cursor.getString(cursor.getColumnIndex("businessAddress"));
                if(businessAddress==null||businessAddress.equalsIgnoreCase("")){
                    edt_BusinessAddress.setText("");
                }else{
                    edt_BusinessAddress.setText(businessAddress);
                }

                yearInBusiness = cursor.getString(cursor.getColumnIndex("yearInBusiness"));
                if(yearInBusiness==null||yearInBusiness.equalsIgnoreCase("")){
                    edt_yearInBusiness.setText("");
                }else{
                    edt_yearInBusiness.setText(yearInBusiness);
                }

                yearInCurrentBusiness = cursor.getString(cursor.getColumnIndex("yearInCurrentBusiness"));
                if(yearInCurrentBusiness==null||yearInCurrentBusiness.equalsIgnoreCase("")){
                    edt_yearIncurrentBusiness.setText("");
                }else{
                    edt_yearIncurrentBusiness.setText(yearInCurrentBusiness);
                }
                sectorBusiness = cursor.getString(cursor.getColumnIndex("sectorBusiness"));
                if(sectorBusiness==null||sectorBusiness.equalsIgnoreCase("")){
                    edt_sectorBusiness.setText("");
                }else{
                    edt_sectorBusiness.setText(sectorBusiness);
                }
                gottoknow = cursor.getString(cursor.getColumnIndex("gottoknow"));
                Log.e("tag","gottoknow="+gottoknow);
                if (gottoknow != null) {
                    sel_gottoknow = dataAdapter_gotknow.getPosition(gottoknow);
                    gotknow_sp.setSelection(sel_gottoknow);
                   // gotknow_sp.setSelection(((ArrayAdapter<String>)gotknow_sp.getAdapter()).getPosition(gottoknow));
                   // gotknow_sp.setSelection(getIndex(gotknow_sp, gottoknow));
                }
                UNDP = cursor.getString(cursor.getColumnIndex("UNDP"));
                if(UNDP.equals("1"))
                {
                    undp_radiogroup.check(R.id.rdb_UNDPyes);
                    int_UNDP="1";
                    edt_undp.setVisibility(View.VISIBLE);
                }
                else
                { undp_radiogroup.check(R.id.rdb_UNDPno);
                    int_UNDP="0";
                    edt_undp.setVisibility(View.GONE);
                }
                yearUNDP = cursor.getString(cursor.getColumnIndex("yearUNDP"));
                if(yearUNDP==null||yearUNDP.equalsIgnoreCase("")||yearUNDP.equalsIgnoreCase("0")){
                    edt_undp.setText("");
                }else {
                    edt_undp.setText(yearUNDP);
                }
                navodyami = cursor.getString(cursor.getColumnIndex("navodyami"));
                if(navodyami.equals("1"))
                {
                    navodyami_radiogroup.check(R.id.rdb_navoyes);
                    int_navo="1";
                    edt_navodyami.setVisibility(View.VISIBLE);
                }
                else
                { navodyami_radiogroup.check(R.id.rdb_navono);
                    int_navo="0";
                    edt_navodyami.setVisibility(View.GONE);
                }
                yearNavodyami = cursor.getString(cursor.getColumnIndex("yearNavodyami"));
                if(yearNavodyami==null||yearNavodyami.equalsIgnoreCase("")||yearNavodyami.equalsIgnoreCase("0")){
                    edt_navodyami.setText("");
                }else {
                    edt_navodyami.setText(yearNavodyami);
                }
                aadhar = cursor.getString(cursor.getColumnIndex("aadhar"));
                if(aadhar==null||aadhar.equals("")||aadhar.equals("0")||aadhar.equals("null")){
                    edt_Aadhar.setText("");
                }else{
                    edt_Aadhar.setText(aadhar);}
                street = cursor.getString(cursor.getColumnIndex("street"));
                if(street==null||street.equalsIgnoreCase("")){
                    edt_Street.setText("");
                }else{
                    edt_Street.setText(street);
                }

                Application_Date = cursor.getString(cursor.getColumnIndex("Application_Date"));
                IsAccountVerified = cursor.getString(cursor.getColumnIndex("IsAccountVerified"));
                //   SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
                //   SimpleDateFormat dateFormatDisplay= new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date d=dateFormat.parse(Application_Date);
                    System.out.println("Formated"+dateFormat.format(d));
                    appldate_seterror_TV.setVisibility(View.GONE);
                    edt_appldate.setText(dateFormatDisplay.format(d).toString());
                    str_appldate=dateFormat.format(d);

                }
                catch(Exception e) {
                    System.out.println("Excep"+e);
                }
            } while (cursor.moveToNext());
        }
        uploadfromDB_Statelist();
        uploadfromDB_Sectorlist();
        uploadfromDB_QualificationList();
        uploadfromDB_CatgaryList();
        //uploadfromDB_KnowNavodyamiList();
    }
    public void GetDataFromDB_CompliteApplicationDetails(String passEnquiry_Id,String passTempId){

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

        Cursor cursor = db_addEnquiry.rawQuery("SELECT DISTINCT * FROM CompliteApplicationDetails WHERE EnquiryId ='"+passEnquiry_Id+"' AND tempId='"+passTempId+"'", null);
        int x = cursor.getCount();
        Log.d("tag","cursor count compliteApplicationDetails="+ Integer.toString(x));

        int i = 0;
        arrayObj_Class_ApplicationDetails = new Class_AddApplicationDetails[x];
        if (cursor.moveToFirst()) {

            do {
                // Enquiry_Id = cursor.getString(cursor.getColumnIndex("EnquiryId"));

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
                if(LastLoan!=null || !LastLoan.equals("")) {
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
                Log.e("tag","Appliedat one="+AppliedAt);
                Repaymentperiod=cursor.getString(cursor.getColumnIndex("Repaymentperiod"));
                //    ApplicationSlnoNew=cursor.getString(cursor.getColumnIndex("Application_Slno"));
                ApplicationFees=cursor.getString(cursor.getColumnIndex("ApplicationFees"));
                VerifiedDate=cursor.getString(cursor.getColumnIndex("VerifiedDate"));
                Remark=cursor.getString(cursor.getColumnIndex("Remark"));
                Manual_Receipt_No=cursor.getString(cursor.getColumnIndex("Manual_Receipt_No"));
                Payment_Mode=cursor.getString(cursor.getColumnIndex("Payment_Mode"));
                IsAccountVerified=cursor.getString(cursor.getColumnIndex("IsAccountVerified"));

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
        Class_AddApplicationTwoDetails class_addApplicationTwoDetails = new Class_AddApplicationTwoDetails(manufacture, licence, whichLicence, productOne,
                productTwo, productThree, businessYear, ownership, str_yearOne, female_year1, male_year1,str_yearTwo, female_year2, male_year2,
                str_yearThree, female_year3, male_year3, labour, outsidefamilyLabour, trunover_year1, trunover_year2, trunover_year3, profit_year1,
                profit_year2, profit_year3, Which_Machine, machine, sell_products, last_quarter, EnquiryId, total_Employee, ApplicationSlno);
        addApplicationTwoList.add(class_addApplicationTwoDetails);
        Class_AddApplicationThreeDetails class_addApplicationThreeDetails = new Class_AddApplicationThreeDetails(BusinessSource, LastLoan, Investment, Knowledge, AppliedAmt, SanctionedAmt, InterestRate, AppliedAt, EnquiryId, Repaymentperiod, int_Product_Improvement, int_Working_Expenses, int_Land, int_Equipment, int_Finance_Trading, ApplicationSlno, ApplicationFees, VerifiedDate, Remark, Manual_Receipt_No, Payment_Mode,"offline",IsAccountVerified);
        addApplicationThreeList.add(class_addApplicationThreeDetails);
        DBCreate_ListApplicationDetails_2SQLiteDB(addApplicationList,addApplicationTwoList,addApplicationThreeList);

    }

    public void List_offlineEnquiryDetails() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS ListEnquiryDetails(tempId INTEGER PRIMARY KEY AUTOINCREMENT,FName VARCHAR,MobileNo VARCHAR,EmailId VARCHAR,BusinessName VARCHAR,EnquiryId VARCHAR,Status VARCHAR,YearCode VARCHAR,MName VARCHAR,LName VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,isApplicant VARCHAR,Gender VARCHAR,dataSyncStatus VARCHAR );");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM ListEnquiryDetails WHERE tempId='"+EnquiryTemptId+"' AND EnquiryId='" + EnquiryId + "'", null);
        int x = cursor1.getCount();
        Log.d("cursor Enquiry Count", Integer.toString(x));

        int i = 0;
        // arrayObj_Class_GrampanchayatListDetails2 = new Class_GrampanchayatListDetails[x];

        if (cursor1.moveToFirst()) {

            // if(YearCode.equalsIgnoreCase(String.valueOf(acadamicCode))) {

            do {

                String FName = cursor1.getString(cursor1.getColumnIndex("FName"));
                edt_FirstName.setText(FName);
                String MName=cursor1.getString(cursor1.getColumnIndex("MName"));
                edt_MiddleName.setText(MName);
                String LName=cursor1.getString(cursor1.getColumnIndex("LName"));
                edt_LastName.setText(LName);
                String MobileNo = cursor1.getString(cursor1.getColumnIndex("MobileNo"));
                edt_MobileNo.setText(MobileNo);
                String EmailId = cursor1.getString(cursor1.getColumnIndex("EmailId"));
                edt_EmailId.setText(EmailId);
                String BusinessName = cursor1.getString(cursor1.getColumnIndex("BusinessName"));
                edt_BusinessName.setText(BusinessName);
                String EnquiryId = cursor1.getString(cursor1.getColumnIndex("EnquiryId"));
                String Status = cursor1.getString(cursor1.getColumnIndex("Status"));
                String YearCode = cursor1.getString(cursor1.getColumnIndex("YearCode"));
                str_StateId=cursor1.getString(cursor1.getColumnIndex("StateId"));
                if(str_StateId.equals("")||str_StateId==null){
                    str_StateId="0";
                }
                str_DistrictId=cursor1.getString(cursor1.getColumnIndex("DistrictId"));
                if(str_DistrictId.equals("")||str_DistrictId==null){
                    str_DistrictId="0";
                }
                str_TalukaId=cursor1.getString(cursor1.getColumnIndex("TalukaId"));
                if(str_TalukaId.equals("")||str_TalukaId==null){
                    str_TalukaId="0";
                }
                str_VillageId=cursor1.getString(cursor1.getColumnIndex("VillageId"));
                if(str_VillageId.equals("")||str_VillageId==null){
                    str_VillageId="0";
                }
                str_SectorId=cursor1.getString(cursor1.getColumnIndex("SectorId"));
                if(str_SectorId.equals("")||str_SectorId==null){
                    str_SectorId="0";
                }
                sel_sector =  Integer.parseInt(str_SectorId.toString());
                spin_Sector.setSelection(sel_sector);
                // cursor1.getString(cursor1.getColumnIndex("DeviceType"));
                //   str_UserId= cursor1.getString(cursor1.getColumnIndex("UserId"));
                str_gender= cursor1.getString(cursor1.getColumnIndex("Gender"));
                // TempId=cursor1.getString(cursor1.getColumnIndex("tempId"));
                if(str_gender.equals("M"))
                {
                    gender_radiogroup.check(R.id.rdb_male); }
                else
                { gender_radiogroup.check(R.id.rdb_female); }

                //UpdateEnquiryDetails updateEnquiryDetails = new UpdateEnquiryDetails(EditEnquiryActivity.this);
                //  updateEnquiryDetails.execute(FName, MName, LName, MobileNo, EmailId, BusinessName, sp_strstate_ID, sp_strdistrict_ID, sp_strTaluk_ID, sp_strVillage_ID, sp_strsector_ID, str_UserId,str_Gender,EnquiryId);

                i++;


            } while (cursor1.moveToNext());

        }//if ends
        uploadfromDB_Statelist();
        uploadfromDB_Sectorlist();
        uploadfromDB_QualificationList();
        uploadfromDB_CatgaryList();
      //  uploadfromDB_KnowNavodyamiList();
    }
    /*public void DBUpdate_ApplicationNo(String ApplNo,String EnquiryNo){
        SQLiteDatabase db_updateAppNo = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);

        db_updateAppNo.execSQL("CREATE TABLE IF NOT EXISTS AddApplicationDetailsNew(FName VARCHAR,MName VARCHAR,LName VARCHAR,MobileNo VARCHAR," +
                "EmailId VARCHAR,StateId VARCHAR,DistrictId VARCHAR,TalukaId VARCHAR,VillageId VARCHAR,SectorId VARCHAR,BusinessName VARCHAR," +
                "DeviceType VARCHAR,UserId VARCHAR,EnquiryId VARCHAR,Gender VARCHAR,Economic VARCHAR,whatsappNo VARCHAR,DOB VARCHAR,education VARCHAR, " +
                "socialCatgary VARCHAR, businessAddress VARCHAR,yearInBusiness VARCHAR,yearInCurrentBusiness VARCHAR,sectorBusiness VARCHAR," +
                "gottoknow VARCHAR,UNDP VARCHAR,yearUNDP VARCHAR,navodyami VARCHAR,yearNavodyami VARCHAR,aadhar VARCHAR,street VARCHAR);");

        ContentValues cv = new ContentValues();
        cv.put("UserId",str_UserId);
        cv.put("EnquiryId",EnquiryNo);
        db_updateAppNo.update("AddApplicationDetailsNew", cv, "EnquiryId = ?", new String[]{EnquiryId});

        SQLiteDatabase db_addApplicationtwo = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);

        db_addApplicationtwo.execSQL("CREATE TABLE IF NOT EXISTS AddApplicationDetailsTwo(Manufacture VARCHAR,Licence VARCHAR,WhichLicence VARCHAR,ProductOne VARCHAR," +
                "ProductTwo VARCHAR,ProductThree VARCHAR,BusinessYear VARCHAR,Ownership VARCHAR,YearOne VARCHAR,Female_year1 VARCHAR,Male_year1 VARCHAR," +
                "YearTwo VARCHAR,Female_year2 VARCHAR,Male_year2 VARCHAR,YearThree VARCHAR,Female_year3 VARCHAR,Male_year3 VARCHAR,Labour VARCHAR,OutsidefamilyLabour VARCHAR, " +
                "Trunover_year1 VARCHAR,Trunover_year2 VARCHAR,Trunover_year3 VARCHAR,Profit_year1 VARCHAR,Profit_year2 VARCHAR,Profit_year3 VARCHAR,Which_Machine VARCHAR,Machine VARCHAR,Sell_products VARCHAR,Last_quarter VARCHAR,EnquiryId VARCHAR,Total_Employee VARCHAR,Application_Slno VARCHAR);");

        ContentValues cv2 = new ContentValues();
        cv2.put("UserId",str_UserId);
        cv2.put("EnquiryId",EnquiryNo);
        cv2.put("Application_Slno",ApplNo);
        db_addApplicationtwo.update("AddApplicationDetailsTwo", cv2, "EnquiryId = ?", new String[]{EnquiryNo});

        SQLiteDatabase db_addApplicationthree = this.openOrCreateDatabase("NavodyamiDB", Context.MODE_PRIVATE, null);

        db_addApplicationthree.execSQL("CREATE TABLE IF NOT EXISTS AddApplicationDetailsThree(BusinessSource VARCHAR,LastLoan VARCHAR,Investment VARCHAR,Knowledge VARCHAR," +
                "AppliedAmt VARCHAR,SanctionedAmt VARCHAR,InterestRate VARCHAR,AppliedAt VARCHAR,EnquiryId VARCHAR,Repaymentperiod VARCHAR,Application_Slno VARCHAR);");

        ContentValues cv3 = new ContentValues();
        cv3.put("UserId",str_UserId);
        cv3.put("EnquiryId",EnquiryNo);
        cv3.put("Application_Slno",ApplNo);
        db_addApplicationthree.update("AddApplicationDetailsThree", cv3, "EnquiryId = ?", new String[]{EnquiryNo});


    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(AddApplicationOneActivity.this, BottomActivity.class);
        i.putExtra("frgToLoad", "0");
        startActivity(i);

        finish();

    }

}
